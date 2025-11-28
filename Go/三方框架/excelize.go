--------------------
excelize
--------------------
	# xlsx 解析框架
		"github.com/xuri/excelize/v2"
	

--------------------
Demo
--------------------

# 解析出 xlsx 中的所有内容

package xlsx

import (
	"io"

	"github.com/xuri/excelize/v2"
)

// Read 解析 xlsx 中的所有内容
func Read(reader io.Reader) (map[string][][]string, error) {

	f, err := excelize.OpenReader(reader)
	if err != nil {
		return nil, err
	}
	defer func() {
		_ = f.Close()
	}()

	m := make(map[string][][]string)

	sheets := f.GetSheetList()

	for _, sheet := range sheets {
		rows, err := f.GetRows(sheet)
		if err != nil {
			return nil, err
		}
		m[sheet] = rows
	}
	return m, err
}
