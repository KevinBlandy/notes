----------------------
Optional-创建		  |
----------------------
	1,声明一个空的 Optional
		Optional<Car> optCar = Optional.empty();
	
	2,根据一个值创建非空的 Optional
		Optional<Car> optCar = Optional.of(car);
		* 如果 car 值为 null,那么会抛出 空指针异常
	
	3,可以接受 null 的 Optional
		Optional<Car> optCar = Optional.ofNullable(car);

----------------------
Optional-方法		  |
----------------------
	isPresent();
		* 如果有值返回 true,源码 return value != null;

	ifPresent(Consumer<? super T> consumer);
		* 如果值不为 null,那么把 value 传递给 consumer 执行

	get();
		* 获取值,如果值不存在则抛出 NoSuchElement 异常

	orElse(T data);
		* 会在值存在的时候返回值,如果返回一个默认值(形参)
	
	orElseGet(Supplier<? extends T> other);
		* 会在值存在的时候返回值,如果值不存在则调用 other Lambda的的 get方法返回默认值
	
	orElseThrow();
		* 会在值存在的时候返回值,如果不存在则抛出异常
	
	map(Function function);
		* 如果有值,就调用 function 进行处理
		* 没有值,就返回 Optional.empty();
	
	flatMap(Function function);
		* 跟map差不多,要求返回值必须是 Optional
	
	
	void ifPresentOrElse(Consumer<? super T> action, Runnable emptyAction)

	Optional<T> or(Supplier<? extends Optional<? extends T>> supplier)

	Stream<T> stream()
		* 如果对象为 null, 返回空的 Stream
		* 方法允许延迟地处理可选对象
			long count = Stream.of(
					Optional.of(1),
					Optional.empty(),
					Optional.of(2)
				).flatMap(Optional::stream)
				.count();
				System.out.println(count);  // 2 （Optiona l 流中包含 3 个 元素，其中只有 2 个有值。在使用 flatMap 之后，结果流中包含了 2 个值）
		* 源码
			if (!isPresent()) {
				return Stream.empty();
			} else {
				return Stream.of(value);
			}


	

	