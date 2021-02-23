#EXTM3U
	* 必须在开头第一个

#EXT-X-VERSION:3
	* 指定版本，一般是3，但是现在最新的都到7了

#EXT-X-MEDIA-SEQUENCE:64

#EXT-X-TARGETDURATION:12
	* 所有切片的最大时长

#EXTINF:<duration>,<title>
	* 切片的实际时长，这个仅对其后面的 URI 有效，
	* duration：表示持续的时间（秒）


#EXT-X-BYTERANGE:<n>[@o]
	* 表示媒体段是一个媒体 URI 资源中的一段，只对其后的 media URI 有效

	