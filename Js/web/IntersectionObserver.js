-----------------------
IntersectionObserver
-----------------------
	# IntersectionObserver 接口（从属于 Intersection Observer API）提供了一种异步观察目标元素与其祖先元素或顶级文档视口（viewport）交叉状态的方法。
		* 其祖先元素或视口被称为根（root）。
		
		https://developer.mozilla.org/zh-CN/docs/Web/API/IntersectionObserver
	
	# 构造函数
		var observer = new IntersectionObserver(callback[, options]);
		
			callback
				* 当元素可见比例超过指定阈值后，会调用一个回调函数，此回调函数接受两个参数：

					entries
						* 一个 IntersectionObserverEntry 对象的数组，每个被触发的阈值，都或多或少与指定阈值有偏差。

					observer
						* 被调用的 IntersectionObserver 实例。
	
			options 可选
				* 一个可以用来配置 observer 实例的对象。如果options未指定，observer 实例默认使用文档视口作为 root，并且没有 margin，阈值为 0%（意味着即使一像素的改变都会触发回调函数）。你可以指定以下配置：

					root 
						* 监听元素的祖先元素 Element 对象，其边界盒将被视作视口。
						* 目标在根的可见区域的任何不可见部分都会被视为不可见。

					rootMargin
						* 一个在计算交叉值时添加至根的边界盒 (bounding_box) 中的一组偏移量，类型为字符串 (string) ，可以有效的缩小或扩大根的判定范围从而满足计算需要。
						* 语法大致和 CSS 中的margin 属性等同; 可以参考 intersection root 和 root margin 来深入了解 margin 的工作原理及其语法。
						* 默认值是 "0px 0px 0px 0px"。

					threshold
						* 规定了一个监听目标与边界盒交叉区域的比例值，可以是一个具体的数值或是一组 0.0 到 1.0 之间的数组。
						* 若指定值为 0.0，则意味着监听元素即使与根有 1 像素交叉，此元素也会被视为可见。
						* 若指定值为 1.0，则意味着整个元素都在可见范围内时才算可见。（默认值）

-----------------------
this
-----------------------

root
rootMargin
thresholds

disconnect()
observe()
takeRecords()
unobserve()


-----------------------
static
-----------------------


-----------------------
demo
-----------------------

	# 图片的进入视口后才加载
	
		const observer = new IntersectionObserver((entries, server) => {
			entries
				// 产生了交叉的元素（即新出现在了视口中的元素）
				.filter(e => e.isIntersecting)
				// 修改这个元素的 src 为 data-src，即加载真正图片
				.forEach(img => {
					console.log(img);

					// 图片已经完成了加载，继续监听没有意义了
					server.unobserve(img);
				});
				;
		}, {
			threshold: 0,
		});


		// 获取所有需要滚动加载的图片
		const images = document.querySelectorAll('img[data-src]');

		// 监听每个图片
		images.forEach(image => {
			observer.observe(image);
		});
	
	