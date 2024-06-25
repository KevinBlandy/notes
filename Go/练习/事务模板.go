package transaction

import (
	"context"
	"database/sql"
	"errors"
	"fmt"
	"log/slog"
)

const (
	// 在 Context 中存储 sql.Tx 的 Key
	ctxKeyTx = "__current_tx"
)

// DB 数据源
var DB *sql.DB

// Option 事务配置方法
type Option func(*sql.TxOptions)

// TxReadOnly 开启只读事务
var TxReadOnly = func(options *sql.TxOptions) {
	options.ReadOnly = true
}

// TxIsolationLevel 设置隔离级别
var TxIsolationLevel = func(level sql.IsolationLevel) Option {
	return func(options *sql.TxOptions) {
		options.Isolation = level
	}
}

// Execute 执行事务方法
func Execute[R any](serviceCall func(ctx context.Context) (R, error), options ...Option) (ret R, err error) {
	// 从数据库获取连接
	conn, err := DB.Conn(context.Background())
	slog.Info("[Transaction] Init DB Conn (Writeable)")
	if err != nil {
		return
	}
	defer func() {
		if r := recover(); r != nil {
			_ = conn.Close()
			panic(r)
		}

		// 最后关闭数据库连接
		if closeErr := conn.Close(); closeErr != nil {
			slog.Error("[Transaction] Close DB Conn Err", slog.String("err", closeErr.Error()))
			if err == nil {
				err = closeErr
			} else {
				err = errors.Join(closeErr, err)
			}
		}
	}()

	// 事务配置
	txOption := &sql.TxOptions{}
	for _, option := range options {
		option(txOption)
	}

	// 开启事务
	tx, err := conn.BeginTx(context.Background(), txOption)
	if err != nil {
		return
	}

	// 执行事务方法
	return run(serviceCall, tx)
}

// run 在事务中执行逻辑，自动提交。err != nil 或者 panic 自动回滚
func run[R any](service func(ctx context.Context) (R, error), tx *sql.Tx) (ret R, err error) {
	defer func() {
		if r := recover(); r != nil {
			_ = rollback(tx)
			panic(r)
		}

		if err != nil {
			// 回滚事务
			slog.Info("[Transaction] Rollback Tx")
			if rollbackErr := rollback(tx); rollbackErr != nil {
				err = errors.Join(rollbackErr, err)
			}
		} else {
			// 提交事务
			slog.Info("[Transaction] Commit Tx")
			if commitErr := commit(tx); commitErr != nil {
				err = commitErr
			}
		}
	}()

	ret, err = service(context.WithValue(context.Background(), ctxKeyTx, tx))

	return
}

// Tx 获取当前 Context 中绑定的事务对象
func Tx(ctx context.Context) *sql.Tx {
	ret := ctx.Value(ctxKeyTx)
	if ret == nil {
		panic(errors.New("context 中没有事务，请在事务上下文中调用"))
	}
	tx, ok := ret.(*sql.Tx)
	if !ok {
		panic(errors.New(fmt.Sprintf("%v 不是合法的 *sql.Tx 对象", ret)))
	}
	slog.Info("[Transaction] Get Concurrent Tx")
	return tx
}

// rollback 回滚事务
func rollback(tx *sql.Tx) error {
	err := tx.Rollback()
	if err != nil {
		if errors.Is(err, sql.ErrConnDone) {
			return nil
		}
		slog.Error("[Transaction] Rollback Tx Err", slog.String("err", err.Error()))
	}
	return err
}

// commit 提交事务
func commit(tx *sql.Tx) error {
	err := tx.Commit()
	if err != nil {
		slog.Error("[Transaction] Commit Tx Err", slog.String("err", err.Error()))
	}
	return err
}
