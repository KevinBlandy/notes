-----------------
webp
-----------------
	* webp实现了一个WEBP图像的解码器。

-----------------
var
-----------------

-----------------
type
-----------------

-----------------
func
-----------------
	func Decode(r io.Reader) (image.Image, error)
		* 解码从r中读取WEBP图像，并将其作为一个image.Image返回。

	func DecodeConfig(r io.Reader) (image.Config, error)
		* DecodeConfig返回WEBP图像的颜色模型和尺寸，而无需对整个图像进行解码。
	