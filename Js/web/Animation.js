----------------------
Animation
----------------------
	# Web 动画接口，继承 EventTarget
	# 构造函数
		var animation = new Animation(effect, timeline);

			effect 可选
				* 将 KeyframeEffect 对象分配给动画。
				* 在将来，其他类型的效果，如 SequenceEffects 或 GroupEffects 是可能被实现的，但现在，唯一的效果是 KeyframeEffect。

			timeline 可选
				* 指定与动画关联的时间轴。
				* 目前唯一可用的时间轴类型是DocumentTimeline，但在将来我会有与手势或滚动相关联的时间轴。
				* 默认为Document.timeline。这也可以设置为 null。
----------------------
event
----------------------

	cancel
	finish
	remove

----------------------
this
----------------------

	currentTime
	effect
	finished
	id
	pending
	playbackRate
	playState
	ready
	replaceState
	startTime
	timeline


	cancel()
	commitStyles()
	finish()
	pause()
	persist()
	play()
	reverse()
	updatePlaybackRate()


----------------------
static
----------------------
