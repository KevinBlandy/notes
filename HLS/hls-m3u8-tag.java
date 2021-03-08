#EXTM3U
	* 必须在开头第一个，表示当前文件是一个m3u8文件

#EXT-X-VERSION:3
	* 指定版本，一般是3，但是现在最新的都到7了

#EXT-X-MEDIA-SEQUENCE:64
	* 第一个分片的序列号（整数值）。
	* 如果M3U8文件中没有该字段，则playlist中第一个分片的序列号必须是0

#EXT-X-TARGETDURATION:12
	* 分片的最长时长，单位是秒

#EXTINF:<duration>,[<title>]
	* 切片的实际时长，这个仅对其后面的 URI 有效，对于每个分片，必须有该字段
	* duration：表示持续的时间（秒）

	* #EXT-X-VERSION小于3的情况下，duration必须是整数；其他情况下duration可以是浮点数和整数。
	* title是一个可选字段，仅用于增强可读性


#EXT-X-BYTERANGE:<n>[@o]
	* 表示媒体段是一个媒体 URI 资源中的一段，只对其后的 media URI 有效

#EXT-X-ENDLIST 
	* 该字段表示分片结束，这是一个点播源，不会在playlist文件中添加新的分片

#EXT-X-STREAM-INF:<attribute-list>
	* 在master playlist中，标识一个Variant Stream
	* 这是由一系列的Redition组成的。该标签的属性列表中包含了Variant Stream的描述信息

	BANDWIDTH
		* 表示Variant Stream中的峰值比特率，单位bits/s
	AVERAGE-BANDWIDTH
		* 表示Variant Stream中的平均比特率，单位bits/s
	CODECS
		* 包含Variant Stream中音视频编码格式相关的信息
	RESOLUTION
		* 包含Variant Stream中对应视频流的分辨率
	FRAME-RATE
		* 表示Variant Stream中的视频帧率
	
#EXT-X-PLAYLIST-TYPE
	* 该标签只有两个值：EVENT、VOD
	* EVENT指的是分片工具只能在M3U8末尾添加新的分片的信息，但不能删除老的分片，通常比较适用于直播+录播的情况（既要提供给客户端点播功能，也要对实时场景进行录制，直播完成之后EVENT就自然退化为VOD）

	* M3U8中存在#EXT-X-ENDLIST标签时，可以忽略EXT-X-PLAYLIST-TYPE
	* 如果M3U8中不存在#EXT-X-ENDLIST以及#EXT-X-PLAYLIST-TYPE标签，则服务器端可以任意更新playlist内容。


#EXT-X-ALLOW-CACHE:NO
	* 是否允许客户端对下载的视频分段缓存用于以后播放

#EXT-X-DISCONTINUITY
	* 暗示当前的视频分段和它之前及之后的视频分段有不同的编码不连续性