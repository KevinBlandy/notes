-----------------------
exception
-----------------------
	# 异常处理标准库
		
		* https://en.cppreference.com/w/cpp/header/exception.html



-----------------------
exception
-----------------------
	namespace std {
	  class exception;
	  class bad_exception;
	  class nested_exception;
	 
	  using terminate_handler = void (*)();
	  terminate_handler get_terminate() noexcept;
	  terminate_handler set_terminate(terminate_handler f) noexcept;
	  [[noreturn]] void terminate() noexcept;
	 
	  constexpr int uncaught_exceptions() noexcept;
	 
	  using exception_ptr = /* unspecified */;
	 
	  constexpr exception_ptr current_exception() noexcept;
	  [[noreturn]] constexpr void rethrow_exception(exception_ptr p);
	  template<class E> constexpr exception_ptr make_exception_ptr(E e) noexcept;
	 
	  template<class T> [[noreturn]] constexpr void throw_with_nested(T&& t);
	  template<class E> constexpr void rethrow_if_nested(const E& e);
	}

