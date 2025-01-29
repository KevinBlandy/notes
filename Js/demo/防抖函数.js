/*
 * 防抖，避免频率高的耗时任务重复执行，其实只需要执行最后一个。
 * 解决办法就是使用延迟执行
 */

// 封装防抖函数
function debounce(fn, duration = 300){
    let timerId = null;
    return function(...args){
        clearTimeout(timerId)
        timerId = window.setTimeout(() => {
            fn(...args)
        }, duration);
    }
}


// 带有防抖的处理器
const newHandler = debounce((event) => {
    console.log(event.target);
});


// 使用防抖处理器
window.addEventListener('resize', newHandler)