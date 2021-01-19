----------------
·¢²¼¶©ÔÄ		|
----------------

let publisher = (function(){
	let queue = {}
	function sub(event, listener){
		if (!queue[event]){
			queue[event] = []
		}
		queue[event].push(listener)
	}
	function push(event, resource){
		if (queue[event]){
			for (let listener of queue[event]){
				listener(resource)
			}
		}
	}
	return {
		sub,push
	}
})();

publisher.sub('foo', data => {
	console.log(data);
});
publisher.sub('foo', data => {
	console.log(data);
});

publisher.sub('foo', data => {
	console.log(data);
});


publisher.push('foo', 'Hello World');
publisher.push('bar', 'Hello World');