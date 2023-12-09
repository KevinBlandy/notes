package main

import (
	"bufio"
	"io"
	"log/slog"
	"os"
	"time"
)

func init() {

	// log 日志记录器
	slog.SetDefault(slog.New(slog.NewTextHandler(os.Stdout, &slog.HandlerOptions{
		AddSource: true,
		Level:     slog.LevelDebug,
	})))
}

func main() {
	file, err := os.Open("C:\\Users\\KevinBlandy\\Desktop\\adsense.txt")
	if err != nil {
		slog.Error("err", slog.String("err", err.Error()))
		return
	}

	// 指针
	var position int64 = 0

	for {
		_, err := file.Seek(position, io.SeekStart)
		if err != nil {
			slog.Error("seek err", slog.String("err", err.Error()))
			return
		}

		scanner := bufio.NewScanner(file)
		scanner.Split(bufio.ScanLines)

		for scanner.Scan() {
			if err := scanner.Err(); err != nil {
				slog.Error("scan err", slog.String("err", err.Error()))
				return
			}
			line := scanner.Text()
			slog.Debug(line)
		}

		// 获取到文件指针位置
		count, err := file.Seek(0, io.SeekCurrent)
		if err != nil {
			slog.Error("seek err", slog.String("err", err.Error()))
			return
		}

		// 更新指针
		position = count

		time.Sleep(time.Millisecond * 10)
	}
}
