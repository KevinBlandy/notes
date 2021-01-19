
----------------------------
静态 import					|
----------------------------
	# Demo代码
		<script type="module">
		  import { pColor } from './firstBlood.mjs';// 导入firstBlood模块
		  pColor('red');	// 设置颜色为红色
		</script>

		// export一个改变<p>元素颜色的方法
		export function pColor (color) {
		  const p = document.querySelector('p');
		  p.style.color = color;
		}

	# 对于需要引入模块的<script>元素,我们需要添加type="module"
		* 这个时候,浏览器会把这段内联script或者外链script认为是ECMAScript模块

	# 模块JS文件,业界或者官方约定俗成命名为.mjs文件格式
		* 一来可以和普通JavaScript文件(.js后缀)进行区分,一看就知道是模块文件
		* 二来Node.js中ES6的模块化特性只支持.mjs后缀的脚本,可以和Node.js保持一致
		* 当然,我们直接使用.js作为模块JS文件的后缀也是可以的

	# 在浏览器侧进行import模块引入,其对模块JS文件的mime type要求非常严格,务必和JS文件一致
		* 这就导致,如果我们使用.mjs文件格式,则需要在服务器配置mime type类型,否则会报错:
			Failed to load module script: The server responded with a non-JavaScript MIME type of “”. Strict MIME type checking is enforced for module scripts per HTML spec.
	
	# 目前import不支持裸露的说明符
		* import的地址前面不能是光秃秃的,例如下面这些就不支持()
		// 目前不支持，以后可能支持
		import {foo} from 'bar.mjs';
		import {foo} from 'utils/bar.mjs';

		* 下面这些则支持，可以是根路径的/，同级路径./亦或者是父级../，甚至完整的非相对地址也是可以的。

		// 支持
		import {foo} from 'https://www.zhangxinxu.com/utils/bar.mjs';
		import {foo} from '/utils/bar.mjs';
		import {foo} from './bar.mjs';
		import {foo} from '../bar.mjs';
	
	# 默认Defer行为
		* 传统<script>属性支持一个名为defer的属性值，可以让JS资源异步加载，同时保持顺序
		* 对于type="module"的<script>元素,天然外挂defer特性,也就是天然异步,所有module脚本按顺序
		* 内联script同样defer特性,传统的内联<script>是没有defer这种概念的，从不异步
			<script defer>
			  console.log("Inline script执行");
			</script>
	
	# 支持async
		* 内联的module <script>还是外链的<script>，都支持async这个异步标识属性。
		* 这个有别于传统的<script>，也就是传统<script>仅外链JS才支持async，内联JS直接忽略async(内联的<script>非module,直接忽略 async,因为它本身就是加载就执行)
		* async和defer都可以让JavaScript异步加载，区别在于defer保证执行顺序，而async谁先加载好谁先执行
		* 这个特性表现在type="module"的<script>元素这里同样适用
			<!-- firstBlood模块一加载完就会执行 -->
			<script async type="module">
			  import { pColor } from './firstBlood.mjs';
			  pColor('red');
			</script>

			<!-- doubleKill模块一加载完就会执行 -->
			<script async type="module" src="./doubleKill.mjs"></script>

	# 模块只会执行一次
		* 传统的<script>如果引入的JS文件地址是一样的，则JS会执行多次。但是，对于type="module"的<script>元素，即使模块地址一模一样，也只会执行一次
	
	# 总是CORS跨域
		* 传统JS文件的加载，我们直接跨域也可以解析，例如，我们会使用一些大网站的CDN服务，例如，加载个百度提供的jQuery地址
		* 如果是module模式下import脚本资源，则不会执行
			<script type="module" src="//apps.bdimg.com/.../jquery.min.js"></script>
			<script>
				window.addEventListener('DOMContentLoaded', function () {
					console.log(window.$);
				});
			</script>
		
	# 无凭证
		* 如果请求来自同一个源（域名一样），大多数基于CORS的API将发送凭证（如cookie等），但fetch()和模块脚本是例外 
		* 除非您要求，否则它们不会发送凭证。

			<!-- ① 获取资源会带上凭证（如cookie等）-->
			<script src="1.js"></script>

			<!-- ② 获取资源不带凭证 本站-->
			<script type="module" src="1.mjs"></script>

			<!-- ③ 获取资源带凭证 本站-->
			<script type="module" crossorigin src="1.mjs?"></script>

			<!-- ④ 获取资源不带凭证 其他站-->
			<script type="module" crossorigin src="//cdn.zhangxinxu.com/.../1.mjs"></script>

			<!-- ⑤ 获取资源带凭证 其他站-->
			<script type="module" crossorigin="use-credentials" src="//cdn.zhangxinxu.com/.../1.mjs?"></script>

		*  crossOrigin可以有下面两个值
			anonymous		元素的跨域资源请求不需要凭证标志设置。
			use-credentials	元素的跨域资源请求需要凭证标志设置，意味着该请求需要提供凭证。

			* 只要crossOrigin的属性值不是use-credentials，全部都会解析为anonymous
		
	# 天然严格模式
		* import的JS模块代码天然严格模式，如果里面有不太友好的代码会报错
	

----------------------------
动态 import					|
----------------------------
	* 静态import在首次加载时候会把全部模块资源都下载下来，但是，我们实际开发时候，有时候需要动态import（dynamic import）
	* 例如点击某个选项卡，才去加载某些新的模块，这个动态import特性浏览器也是支持的。

	# 使用一个长得像函数的import()
		* 注意，只是长得像函数，import()实际上就是个单纯的语法，类似于super()
		* 这就意味着import()不会从Function.prototype获得继承，因此您无法call或apply它
		* 并且const importAlias = import之类的东西不起作用，甚至import()都不是对象！
		*  语法为:import(moduleSpecifier);

			<script type="module">
			  // 导入doubleKill模块
			  import('./doubleKill.mjs').then((module) => {
					// 执行默认方法
					module.default();
					// 设置颜色为红色
					module.pColor('red');
			  });
			</script>

		* import()返回一个promise，所以，我们可以使用async/await来代替then这种回调形式。
			<script type="module">
				(async () => {
				  // 导入doubleKill模块
				  const module = await import('./doubleKill.mjs');
				  // 执行默认方法
				  module.default();
				  // 设置颜色为红色
				  module.pColor('red');
				})();
			</script>
	
	# 交互中的动态import
		* import只能用在<script type="module>"一样，动态import()也可以用在普通的script
			<script>
				const main = document.querySelector('main');

				const links = document.querySelectorAll('nav > a');

				for (const link of links) {

					link.addEventListener('click', async (event) => {
						const module = await import(`./${link.dataset.module}.mjs`);
						// 模块暴露名为`loadPageInto`的方法，内容是写入一段HTML
						module.loadPageInto(main);
					});
				}
			</script>


----------------------------
nomodule 与向下兼容			|
----------------------------
	# 模块脚本我们可以使用type="module"进行设定,对于并不支持export和import的浏览器,我们可以使用nomodule进行向下兼容
		<script type="module" src="module.mjs"></script>
		<script nomodule src="fallback.js"></script>
		
		* 对于支持ES6模块导入的浏览器,自然也支持原生的nomodule属性,此时fallback.js是忽略的
		* 对于不支持的老浏览器,无视nomodule,此时fallback.js就会执行,于是浏览器全兼顾
	



----------------------------
部分总结					|
----------------------------
	# defer和async
		'defer是渲染完再执行'
		'async是下载完就执行'

	# 内联<script>是没有defer这种概念的,从不不异步
		<script defer>
		  console.log("Inline script执行");
		</script>

		* 加载则立即执行
	
	# type="module"的<script>元素,天然defer(可以认为默认为 defer)
		<script defer type="module">
		  console.log("Inline script执行");
		</script>

		* 页面加载完毕,非 defer js执行完毕后执行
	
	# 内联的module <script>还是外链的<script>,都支持async这个异步标识属性
		* 内联的<script>非module,直接忽略 async,因为它本身就是加载就执行

		<!-- firstBlood模块一加载完就会执行 -->
		<script async type="module">
			import { pColor } from './firstBlood.mjs';
			pColor('red');
		</script>

		<!-- doubleKill模块一加载完就会执行 -->
		<script async type="module" src="./doubleKill.mjs"></script>