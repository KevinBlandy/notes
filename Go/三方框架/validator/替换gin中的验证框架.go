------------------
gin 替换高版本
------------------
	package main

	import (
		"github.com/gin-gonic/gin/binding"
		"github.com/go-playground/validator/v10"
		"reflect"
		"sync"
	)


	func main() {
		binding.Validator = new(defaultValidator)
	}

	type defaultValidator struct {
		once     sync.Once
		validate *validator.Validate
	}

	var _ binding.StructValidator = &defaultValidator{}

	func (v *defaultValidator) ValidateStruct(obj interface{}) error {
		if kindOfData(obj) == reflect.Struct {
			v.lazying()
			if err := v.validate.Struct(obj); err != nil {
				return err
			}
		}
		return nil
	}

	func (v *defaultValidator) Engine() interface{} {
		v.lazying()
		return v.validate
	}

	func (v *defaultValidator) lazying() {
		v.once.Do(func() {
			v.validate = validator.New()
			v.validate.SetTagName("binding")

			// TODO 添加自定义的校验器
		})
	}

	func kindOfData(data interface{}) reflect.Kind {

		value := reflect.ValueOf(data)
		valueType := value.Kind()

		if valueType == reflect.Ptr {
			valueType = value.Elem().Kind()
		}
		return valueType
	}
