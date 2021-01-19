
----------------------------------------
Array-自定义原型方法,可以遍历多维数组	|
----------------------------------------
	Array.prototype.each = function(fun){
		try{
			//预定义初始化变量
			this.i || (this.i = 0);
			//确定当前数组有元素,并且传递的参数是一个函数
			if(this.length > 0 && fun.constructor == Function){
				while(this.length > this.i){
					//获取每一个元素
					var temp = this[this.i];
					if(temp && temp.constructor == Array){
						//如果当前元素还是一个数组,递归调用
						temp.each(fun);
					}else{
						//如果不是,则执行传递进来的函数
						var obj = true;
						fun.call(temp,temp);
					}
					//释放变量内存
					this.i ++;
				}
				this.i = null;
			}
		}catch(e){
			//TODO
		}
		return this;
	}
	var arr = [5,6,7,8,9,[10,11,12,[55,[66]]]];
	arr.each(function(val){
		console.log(val);
	});

----------------------------------------
Date-获取当前时间						|
----------------------------------------
	function now(){
		var date = new Date();
		var year = date.getFullYear();
		var month = date.getMonth() + 1;	
		if(month < 10){
			month = "0" + month;
		}
		var dayOfMonth = date.getDate();
		if(dayOfMonth < 10){
			dayOfMonth = "0" + dayOfMonth;
		}
		var hours = date.getHours();		
		if(hours < 10){
			hours = "0" + hours;
		}
		var minutes = date.getMinutes();	
		if(minutes < 10){
			minutes = "0" + minutes;
		}
		var seconds = date.getSeconds();
		if(seconds < 10){
			seconds = "0" + seconds;
		}
		return year + "年" + month + "月" + dayOfMonth + "日 " + hours + ":" + minutes + ":" + seconds;
	}

----------------------------------------
Date-显示本地图片						|
----------------------------------------
	var input = document.getElementsByTagName("input")[0];
	input.onchange = function (e) {
		var url = window.URL.createObjectURL(this.files[0]);
		document.getElementsByTagName("img")[0].src = url;
		//window.URL.revokeObjectURL(url);					
	};

----------------------------------------
location-获取URL后的请求参数			|
----------------------------------------
	function urlArgs(){
		var args = {};
		//获取参数字符串,删除?号
		var query = location.search.substring(1);
		//根据 & 符合分割字符串
		var pairs = query.split("&");
		for(var x = 0;x < pairs.length;x++){
			//查找name=value
			var pos = pairs[x].indexOf("=");
			if(pos == -1){
				continue;
			}
			var name = pairs[x].substring(0,pos);
			var value = pairs[x].substring(pos + 1);
			//URI解码
			value = decodeURIComponent(value);
			if(args.hasOwnProperty(name)){
				//属性已经存在,以','号分隔
				value = args[name] + "," + value;
			}
			args[name] = value;
		}
		return args;
	}

----------------------------------------
doucment-获取所有的Cookie				|
----------------------------------------
	function getCookies (){
		var cookie = {};
		var all = document.cookie;
		if(all === ''){
			return cookie;				
		}
		var list = all.split(';');
		for(var x = 0; x < list.length ; x++){
			var cookieStr = list[x];
			var point = cookieStr.indexOf('=');
			var name = cookieStr.substring(0,point);
			var value = decodeURIComponent(cookieStr.substring(point + 1));
			cookie[name] = value;
		}
		return cookie;
	}

----------------------------------------
监听文字复制事件,并且添加自己的数据		|
----------------------------------------
document.addEventListener('copy', function (event) {
    var clipboardData = event.clipboardData || window.clipboardData;
    if (!clipboardData) { 
		return; 
	}
	//复制到的文字信息
    var text = window.getSelection().toString();
    if (text) {
        event.preventDefault();
		//修改原来的文字信息
        clipboardData.setData('text/plain', text + '\n\njavaweb开发者社区版权所有');
    }
});

----------------------------------------
获取到粘贴的图片						|
----------------------------------------
document.addEventListener('paste', function(event) {
	let items = event.clipboardData && event.clipboardData.items;
	let file = null;
	if (items && items.length) {
		// 检索剪切板items
		for (let i = 0; i < items.length; i++) {
			if (items[i].type.indexOf('image') !== -1) {
				file = items[i].getAsFile();
				if(!file){
					// 文件读取失败，可能是复制了文件系统的图片
				}
				break;
			}
		}
	}
	// 此时file就是剪切板中的图片文件
});

----------------------------------------
获取到拖曳的图片						|
----------------------------------------
document.addEventListener('dragenter', function (event) {
    event.preventDefault();
});
document.addEventListener('dragover', function (event) {
    event.preventDefault();
});
document.addEventListener('drop', function (event) {
    event.preventDefault();
    let files = event.dataTransfer.files;
    if (files) {
    	// 获取到拖曳的图片
    	console.log(files);
    }
});
document.addEventListener('dragend', function (event) {
    event.preventDefault();
});

----------------------------------------
序列化表单为form/json字符串					|
----------------------------------------
function formEncode(form, format='form') {
    if (!form || form.nodeName !== 'FORM') {
        console.log(form + ' is not form element');
        return;
    }

    const FORM_ELEMENTS = ['INPUT', 'TEXTAREA', 'SELECT'];
    const map = new Map();
    const queue = [...form.childNodes];
    while (queue.length > 0) {
        const element = queue.shift();
        if (FORM_ELEMENTS.includes(element.nodeName)) {
            const name = element.name;
            if (!name) {
                // 忽略没有定义name属性的表单项
                continue;
            }
            if (element.nodeName === 'INPUT' && element.type === 'file') {
                // 忽略文件表单项
                console.log('ignore file input, please use \'new FormData(form);\'');
                continue;
            }
            let value = null;
            if (element.nodeName === 'SELECT') {
                // 下拉框
                for (const option of element.selectedOptions) {
                    const optionValue = option.value;
                    if (value == null) {
                        value = optionValue;
                    } else {
                        if (Array.isArray(value)){
                            value.push(optionValue);
                        } else {
                            value = [value, optionValue]
                        }
                    }
                }
            } else if (element.type === 'checkbox' || element.type === 'radio') {
                // 多/单选框
                if (element.checked){
                    value = element.value;
                }
            } else {
                // 普通文本框
                value = element.value;
            }
            if (value == null){
                continue;
            }
            if (map.has(name)) {
                const existsVal = map.get(name);
                if (Array.isArray(existsVal)) {
                    if (Array.isArray(value)){
                        existsVal.push(...value);
                    } else {
                        existsVal.push(value);
                    }
                } else {
                    if (Array.isArray(value)){
                        map.set(name, [existsVal, ...value]);
                    } else {
                        map.set(name, [existsVal, value]);
                    }
                }
            } else {
                map.set(name, value);
            }
        } else {
            // 深度优先遍历
            queue.unshift(...element.childNodes);
        }
    }

    if (format === 'form'){
        const params = new URLSearchParams();
        map.forEach(function(value, key, map) {
            if (Array.isArray(value)){
                value.forEach(item => params.append(key, item));
            } else {
                params.append(key, value);
            }
        });
        return params.toString();
    } else if (format === 'json'){
        let object = Object.create(null);
        for (let [key, value] of map) {
            object[key] = value;
        }
        return JSON.stringify(object);
    } else {
        throw `unknow format type: ${format}`;
    }
}
const retVal = formEncode(document.querySelector('form'), 'json');
console.log(retVal);

----------------------------------------
对数据进行html编码
----------------------------------------
/**
 * 对数据进行html编码
 * @param input
 * @returns
 */
function htmlEscape(input) {
	const div = document.createElement("div");
	div.innerText = input;
	return div.innerHTML;
}


----------------------------------------
Array-分片上传文件
----------------------------------------
	const CHUNK_SIZE = 1024 * 500;  // 文件分片大小
	function upload (file) {
		if (!(file instanceof File)){
			// TODO 不是文件对象
			return ;
		}
		// 文件名称
		const name = file.name;
		// 文件类型
		const type = file.type;
		// 文件总大小
		const size = file.size;
		if (size == 0){
			// TODO 空文件
			return null;
		}
		// 总分片数量
		let totalChunk = Math.ceil(size / CHUNK_SIZE);
		
		for (let i = 0; i < totalChunk; i ++){
			let start = i * CHUNK_SIZE;
	        let end = ((start + CHUNK_SIZE) >= size) ? size : start + CHUNK_SIZE;
	        // 分片的文件
	        let chunk = file.slice(start, end);
		}
	}


----------------------------------------
js触发下载
----------------------------------------
function download (content, fileName){
	  const donwLoadLink = document.createElement('a');
	  donwLoadLink.download = fileName;
	  donwLoadLink.href = URL.createObjectURL(new Blob([content]));
	  donwLoadLink.click();
}