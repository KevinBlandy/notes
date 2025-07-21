------------------------
new
------------------------
	# 动态内存管理库的一部分，特别提供了低级内存管理功能。
	
		* https://en.cppreference.com/w/cpp/header/new.html
	

------------------------
简介
------------------------
	// all freestanding
	namespace std {
	  // storage allocation errors
	  class bad_alloc;
	  class bad_array_new_length;
	 
	  struct destroying_delete_t
	  {
		explicit destroying_delete_t() = default;
	  };
	  inline constexpr destroying_delete_t destroying_delete{};
	 
	  // global operator new control
	  enum class align_val_t : size_t
	  {
	  };
	 
	  struct nothrow_t
	  {
		explicit nothrow_t() = default;
	  };
	  extern const nothrow_t nothrow;
	 
	  using new_handler = void (*)();
	  new_handler get_new_handler() noexcept;
	  new_handler set_new_handler(new_handler new_p) noexcept;
	 
	  // pointer optimization barrier
	  template<class T> constexpr T* launder(T* p) noexcept;
	 
	  // hardware interference size
	  inline constexpr size_t hardware_destructive_interference_size =
		/* implementation-defined */;
	  inline constexpr size_t hardware_constructive_interference_size =
		/* implementation-defined */;
	}
	 
	// storage allocation and deallocation
	void*
	operator new(std::size_t size);
	void*
	operator new(std::size_t size, std::align_val_t alignment);
	void*
	operator new(std::size_t size, const std::nothrow_t&) noexcept;
	void*
	operator new(std::size_t size,
				 std::align_val_t alignment,
				 const std::nothrow_t&) noexcept;
	 
	void
	operator delete(void* ptr) noexcept;
	void
	operator delete(void* ptr, std::size_t size) noexcept;
	void
	operator delete(void* ptr, std::align_val_t alignment) noexcept;
	void
	operator delete(void* ptr, std::size_t size, std::align_val_t alignment) noexcept;
	void
	operator delete(void* ptr, const std::nothrow_t&) noexcept;
	void
	operator delete(void* ptr, std::align_val_t alignment, const std::nothrow_t&) noexcept;
	 
	void*
	operator new[](std::size_t size);
	void*
	operator new[](std::size_t size, std::align_val_t alignment);
	void*
	operator new[](std::size_t size, const std::nothrow_t&) noexcept;
	void*
	operator new[](std::size_t size,
				   std::align_val_t alignment,
				   const std::nothrow_t&) noexcept;
	 
	void
	operator delete[](void* ptr) noexcept;
	void
	operator delete[](void* ptr, std::size_t size) noexcept;
	void
	operator delete[](void* ptr, std::align_val_t alignment) noexcept;
	void
	operator delete[](void* ptr, std::size_t size, std::align_val_t alignment) noexcept;
	void
	operator delete[](void* ptr, const std::nothrow_t&) noexcept;
	void
	operator delete[](void* ptr, std::align_val_t alignment, const std::nothrow_t&) noexcept;
	 
	constexpr void*
	operator new(std::size_t size, void* ptr) noexcept;
	constexpr void*
	operator new[](std::size_t size, void* ptr) noexcept;
	void
	operator delete(void* ptr, void*) noexcept;
	void
	operator delete[](void* ptr, void*) noexcept;

