package queue

import (
	"context"
	"errors"
	"github.com/redis/go-redis/v9"
	"time"
)

var rdb *redis.Client

func init() {
	//rdb = redis.NewClient(&redis.Options{
	//	Addr: "localhost:6379",
	//})
}

var (
	keyQueue        = "__queue__"
	keyQueuePending = "__queue_pending__"
	keyQueueMeta    = "__queue_meta__"
)

// Push 消息入队列
func Push(ctx context.Context, id, content []byte) (int, error) {
	conn := rdb.Conn()
	if r := conn.HSet(ctx, keyQueueMeta, id, content); r.Err() != nil {
		return 0, r.Err()
	}
	count, err := conn.LPush(ctx, keyQueue, id).Result()
	return int(count), err
}

// Poll 轮询队列
func Poll(ctx context.Context, timeOut time.Duration) ([]byte, []byte, error) {
	conn := rdb.Conn()
	key, err := conn.BRPopLPush(ctx, keyQueue, keyQueuePending, timeOut).Bytes()
	if err != nil {
		if errors.Is(err, redis.Nil) {
			// TODO KEY 结果不存在
		}
		return nil, nil, err
	}
	val, err := conn.HGet(ctx, keyQueueMeta, string(key)).Bytes()
	if err != nil {
		if errors.Is(err, redis.Nil) {
			// TODO VALUE 结果不存在
		}
		return nil, nil, err
	}
	return key, val, nil
}

// Ack 消息
func Ack(ctx context.Context, id []byte) (int, error) {
	conn := rdb.Conn()
	count, err := conn.LRem(ctx, keyQueuePending, 0, id).Result()
	if err != nil {
		return 0, err
	}
	if count > 0 {
		count, err = conn.HDel(ctx, keyQueueMeta, string(id)).Result()
		return int(count), err
	}
	return 0, nil
}

// ReQueue 重新入队列
func ReQueue(ctx context.Context, count int) (int, error) {
	conn := rdb.Conn()
	var items int
	for range count {
		if err := conn.RPopLPush(ctx, keyQueuePending, keyQueue).Err(); err != nil {
			if errors.Is(err, redis.Nil) {
				break
			}
			return 0, err
		}
		items++
	}
	return items, nil
}

// Len 待消费队列长度
func Len(ctx context.Context) (int, error) {
	conn := rdb.Conn()
	size, err := conn.LLen(ctx, keyQueue).Result()
	return int(size), err
}

// PendingLen 待ACK队列长度
func PendingLen(ctx context.Context) (int, error) {
	conn := rdb.Conn()
	size, err := conn.LLen(ctx, keyQueuePending).Result()
	return int(size), err
}
