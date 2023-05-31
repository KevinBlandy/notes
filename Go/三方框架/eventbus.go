----------------
EventBus
----------------

package bus

import (
	"context"
	"errors"
	"fmt"
	"reflect"
)

/*
	Bus
	https://github.com/grafana/grafana/blob/main/pkg/bus/bus.go

	根据 HandlerFunc 的第二个参数类型的名称来确定 Handler
	HandlerFunc 函数第一个参数必须是 context, 第二个参数必须是事件对象指针，返回值必须是 error
*/

// HandlerFunc defines a handler function interface.
type HandlerFunc any

// Msg defines a message interface.
type Msg any

// ErrHandlerNotFound defines an error if a handler is not found.
var ErrHandlerNotFound = errors.New("handler not found")

// Bus type defines the bus interface structure.
type Bus interface {
	Publish(ctx context.Context, msg Msg) error
	AddEventListener(handler HandlerFunc)
}

// InProcBus defines the bus structure.
type InProcBus struct {
	listeners map[string][]HandlerFunc
}

func ProvideBus() *InProcBus {
	return &InProcBus{
		listeners: make(map[string][]HandlerFunc),
	}
}

// Publish function publish a message to the bus listener.
func (b *InProcBus) Publish(ctx context.Context, msg Msg) error {
	var msgName = reflect.TypeOf(msg).Elem().Name()

	var params []reflect.Value
	if listeners, exists := b.listeners[msgName]; exists {
		params = append(params, reflect.ValueOf(ctx))
		params = append(params, reflect.ValueOf(msg))
		if err := callListeners(listeners, params); err != nil {
			return err
		}
	}

	return ErrHandlerNotFound
}

func callListeners(listeners []HandlerFunc, params []reflect.Value) error {
	for _, listenerHandler := range listeners {
		ret := reflect.ValueOf(listenerHandler).Call(params)
		e := ret[0].Interface()
		if e != nil {
			err, ok := e.(error)
			if ok {
				return err
			}
			return fmt.Errorf("expected listener to return an error, got '%T'", e)
		}
	}
	return nil
}

func (b *InProcBus) AddEventListener(handler HandlerFunc) {
	handlerType := reflect.TypeOf(handler)
	eventName := handlerType.In(1).Elem().Name()
	_, exists := b.listeners[eventName]
	if !exists {
		b.listeners[eventName] = make([]HandlerFunc, 0)
	}
	b.listeners[eventName] = append(b.listeners[eventName], handler)
}

var bus = ProvideBus()

func Initialization(listener ...HandlerFunc) {
	for _, v := range listener {
		bus.AddEventListener(v)
	}
}
func Publish(ctx context.Context, msg Msg) error {
	return bus.Publish(ctx, msg)
}
