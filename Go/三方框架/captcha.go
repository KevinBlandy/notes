------------------
captcha
------------------
	# 简单，不依赖第三方框架的一个验证码图片类
		https://github.com/steambap/captcha
		
		* 支持Gif/Png/Jpeg/算术计算
	
	
------------------
Demo
------------------
package service

import (
	"context"
	"github.com/gin-gonic/gin"
	"github.com/redis/go-redis/v9"
	cpt "github.com/steambap/captcha"
	"go.uber.org/zap"
	"springdoc/common/constant/header"
	"springdoc/common/errors"
	"springdoc/common/response"
	"springdoc/common/util/id"
	"springdoc/log"
	"springdoc/rdb"
	"strconv"
	"strings"
	"time"
)

func init() {
	// 加载字体
	//err := cpt.LoadFont(goregular.TTF)
	//if err != nil {
	//	panic(err)
	//}
}

type captcha struct {
	namespace string
	duration  time.Duration
}

func (c captcha) key(id string) string {
	return c.namespace + id
}

// Gen 生成图片验证码
func (c captcha) Gen(ctx *gin.Context) error {

	// 生成验证码图片
	image, err := cpt.New(150, 50, func(options *cpt.Options) {
		options.TextLength = 6  // 字符长度
		options.CurveNumber = 4 // 干扰线数量
		//options.Noise = 4.0     // 噪点数量。默认情况下，每28个像素就画一个噪声点。默认是1.0。
	})
	if err != nil {
		return err
	}

	// id & 文本
	captchaId := strconv.FormatInt(id.Next(), 10)
	text := image.Text

	log.Info("captcha", zap.String("id", captchaId), zap.String("text", text))

	// 刷到redis缓存
	if err := rdb.Client().Set(context.Background(), c.key(captchaId), text, time.Second*30).Err(); err != nil {
		return err
	}

	ctx.Header(header.XCaptchaId, captchaId)
	ctx.Header(header.ContentType, "image/png")
	ctx.Header(header.CacheControl, "no-cache, no-store")
	ctx.Header(header.Pragma, "no-cache")

	//return image.WriteGIF(ctx.Writer, &gif.Options{
	//	NumColors: 256, // NumColors是图像中使用的最大颜色数。它的范围是1到256。
	//	Quantizer: nil, //palette.Plan9被用来代替nil Quantizer，它被用来生成一个具有NumColors大小的调色板。
	//	Drawer:    nil, // draw.FloydSteinberg用于将源图像转换为所需的调色板。Draw.FloydSteinberg用于替代一个无的Drawer。
	//})
	return image.WriteImage(ctx.Writer)
}

// Validate 校验验证码是否正确
func (c captcha) Validate(id string, text string) (bool, error) {
	cmd := rdb.Client().GetDel(context.Background(), c.key(id))
	if cmd.Err() == redis.Nil {
		return false, errors.New(response.Fail(response.StatusCodeCaptchaRequired).SetMessage("需要验证码"))
	}
	if cmd.Err() != nil {
		return false, cmd.Err()
	}
	if !strings.EqualFold(cmd.Val(), text) {
		return false, errors.New(response.Fail(response.StatusCodeWrongCaptcha).SetMessage("验证码错误"))
	}
	return true, nil
}

var Captcha = &captcha{namespace: "CAPTCHA::", duration: time.Second * 30}

