package tcpserver

import (
	"context"
	"errors"
	"net"
	"os"
	"sync"
	"sync/atomic"
	"time"
)

var (
	ErrServerRunning = errors.New("server is running")
	ErrServerStopped = errors.New("server is stopped")
	ErrConnClosed    = errors.New("conn is closed")
)

type Channel struct {
	id          int64 // 连接 ID
	lock        *sync.RWMutex
	closed      bool
	server      *TCPServer
	conn        *net.TCPConn // 底层连接
	connectTime time.Time    // 连接时间
	writeChan   chan []byte  // 写入队列
}

func NewChannel(id int64, conn *net.TCPConn, server *TCPServer) *Channel {
	return &Channel{
		id:          id,
		lock:        &sync.RWMutex{},
		closed:      false,
		server:      server,
		conn:        conn,
		connectTime: time.Now(),
		writeChan:   make(chan []byte, 64),
	}
}

func (ch *Channel) startWritten() {
	for data := range ch.writeChan {
		if _, err := ch.conn.Write(data); err != nil {
			ch.server.OnError(ch, err)
		}
	}
}

func (ch *Channel) Read(data []byte) (int, error) {
	return ch.conn.Read(data)
}

func (ch *Channel) Write(data []byte) (err error) {
	ch.lock.RLock()
	defer ch.lock.RUnlock()
	if ch.closed {
		return ErrConnClosed
	}
	defer func() {
		//防御性容错，writeChan 被关闭了
		if r := recover(); r != nil {
			err = ErrConnClosed
		}
	}()
	ch.writeChan <- data
	return
}

func (ch *Channel) WriteContext(ctx context.Context, data []byte) (err error) {
	ch.lock.RLock()
	defer ch.lock.RUnlock()
	if ch.closed {
		return ErrConnClosed
	}
	defer func() {
		//防御性容错，writeChan 被关闭了
		if r := recover(); r != nil {
			err = ErrConnClosed
		}
	}()
	select {
	case ch.writeChan <- data:
	case <-ctx.Done():
		err = ctx.Err()
	}
	return
}

func (ch *Channel) Close() error {
	ch.lock.Lock()
	defer ch.lock.Unlock()
	if ch.closed {
		return ErrConnClosed
	}
	close(ch.writeChan)
	err := ch.conn.Close()
	ch.server.onDisconnect(ch)
	ch.closed = true
	return err
}

type TCPServer struct {
	lock        *sync.RWMutex    // 锁
	closed      bool             // 关闭状态
	id          *atomic.Int64    //ID 生成
	listener    *net.TCPListener // TCP 服务
	channelMap  *sync.Map        // 连接 Map
	connections *atomic.Int64    // 连接数量

	OnConnect        func(*Channel)        // 连接事件
	OnConnectTimeout func(error)           // 连接超时
	OnDisconnect     func(*Channel)        // 连接断开事件
	OnError          func(*Channel, error) // 连接异常事件
}

func NewTCPServer(listener *net.TCPListener) *TCPServer {
	return &TCPServer{
		lock:         new(sync.RWMutex),
		closed:       true,
		id:           new(atomic.Int64),
		listener:     listener,
		channelMap:   new(sync.Map),
		connections:  new(atomic.Int64),
		OnConnect:    func(ch *Channel) {},
		OnDisconnect: func(ch *Channel) {},
		OnError:      func(ch *Channel, err error) {},
	}
}

func (server *TCPServer) ListenAndServe() error {
	server.lock.Lock()
	if !server.closed {
		server.lock.Unlock()
		return ErrServerRunning
	}
	server.closed = false
	server.lock.Unlock()
	for {
		_ = server.listener.SetDeadline(time.Now().Add(5 * time.Second))
		conn, err := server.listener.AcceptTCP()
		if err != nil {
			// 连接超时
			if os.IsTimeout(err) {
				server.OnConnectTimeout(err)
				continue
			}
			return err
		}
		go server.onConnect(conn)
	}
}

func (server *TCPServer) onDisconnect(channel *Channel) {
	server.channelMap.Delete(channel.id)
	server.connections.Add(-1)
	server.OnDisconnect(channel)
}

func (server *TCPServer) onConnect(conn *net.TCPConn) {
	server.lock.RLock()
	defer server.lock.RUnlock()
	if server.closed {
		// 极端情况，服务器已停止，直接断开连接
		_ = conn.Close()
		return
	}

	channel := NewChannel(server.id.Add(1), conn, server)

	server.channelMap.Store(channel.id, channel)
	server.connections.Add(1)

	// 写队列轮询
	go channel.startWritten()

	// 发布连接事件
	server.OnConnect(channel)
}

func (server *TCPServer) Shutdown() error {
	server.lock.Lock()
	defer server.lock.Unlock()
	if server.closed {
		return ErrServerStopped
	}

	// 关闭 Listener
	err := server.listener.Close()

	// 100 个协程对连接进行批量关闭
	closeChan := make(chan struct{}, 100)
	defer func() {
		close(closeChan)
	}()

	// 批量关闭所有连接
	server.channelMap.Range(func(_, value any) bool {
		closeChan <- struct{}{}
		go func() {
			defer func() {
				<-closeChan
			}()
			_ = value.(*Channel).Close()
		}()
		return true
	})
	server.closed = true
	return err
}
