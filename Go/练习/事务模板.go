package db

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

func ExecuteContext[R any](ctx context.Context, fn func(context.Context) (R, error), options ...Option) (ret R, err error) {
	// 从数据库获取连接
	conn, err := database.Conn(context.Background())
	slog.DebugContext(ctx, "[transaction] init db conn")
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
			slog.ErrorContext(ctx, "[transaction] close db conn err", slog.String("err", closeErr.Error()))
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
	tx, err := conn.BeginTx(ctx, txOption)
	if err != nil {
		return
	}

	// 执行事务方法
	return run(ctx, fn, tx)
}

// Execute 执行事务方法
func Execute[R any](fn func(ctx context.Context) (R, error), options ...Option) (ret R, err error) {
	return ExecuteContext(context.Background(), fn, options...)
}

// run 在事务中执行逻辑，自动提交。err != nil 或者 panic 自动回滚
func run[R any](ctx context.Context, fn func(context.Context) (R, error), tx *sql.Tx) (ret R, err error) {
	defer func() {
		if r := recover(); r != nil {
			_ = rollback(ctx, tx)
			panic(r)
		}

		if err != nil {
			// 回滚事务
			slog.DebugContext(ctx, "[transaction] rollback tx")
			if rollbackErr := rollback(ctx, tx); rollbackErr != nil {
				err = errors.Join(rollbackErr, err)
			}
		} else {
			// 提交事务
			slog.DebugContext(ctx, "[transaction] commit tx")
			if commitErr := commit(ctx, tx); commitErr != nil {
				err = commitErr
			}
		}
	}()

	ret, err = fn(context.WithValue(ctx, ctxKeyTx, tx))

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
	slog.DebugContext(ctx, "[transaction] get concurrent tx")
	return tx
}

// rollback 回滚事务
func rollback(ctx context.Context, tx *sql.Tx) error {
	err := tx.Rollback()
	if err != nil {
		if errors.Is(err, sql.ErrConnDone) {
			return nil
		}
		slog.ErrorContext(ctx, "[transaction] rollback tx err", slog.String("err", err.Error()))
	}
	return err
}

// commit 提交事务
func commit(ctx context.Context, tx *sql.Tx) error {
	err := tx.Commit()
	if err != nil {
		slog.ErrorContext(ctx, "[transaction] commit tx err", slog.String("err", err.Error()))
	}
	return err
}
