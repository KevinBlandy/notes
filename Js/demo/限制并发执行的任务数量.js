/*
 * 并发执行任务，可以指定并发执行的数量
 */
function paralleTask (tasks, paralleCount = 3){
    return new Promise((resolve, reject) => {
        if (tasks.length == 0){
            resolve();
            return;
        }

        // 下一个任务指针
        let nextIndex = 0;

        // 已完成的任务数量
        let finishCount = 0;

        /*
         * 内部执行方法，在完成了一个任务后，再执行下一个任务
         */
        function _run (){
            // 下一个任务
            const task = tasks[nextIndex ++];
            // 执行任务
            task().then((val) => {

                console.log(`任务 ${val} 执行完毕`);

                // 任务完成
                finishCount ++;

                // 还有下一个任务，继续执行
                if (nextIndex < tasks.length){
                    _run();
                } else if (finishCount == tasks.length){
                    // 所有任务执行完毕，resolve 最终结果
                    resolve();
                }
            });
        }   

        // 按照最大并发，批量执行
        for (let i = 0 ; i < paralleCount && i < tasks.length; i ++){
            _run();
        }
    });
}

// 模拟初始化一批延时数据
const tasks = Array.from({length: 10}).map((_, i) => {
    return () => {
        return new Promise(resolve => {
            setTimeout(() => {
                resolve(i)
            }, 1000)
        });
    }
});


// 并发执行
paralleTask(tasks).then(() => {
    console.log('执行完毕');
}).catch(err => {
    console.log(err);
});
