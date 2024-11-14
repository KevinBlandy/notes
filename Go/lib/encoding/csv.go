--------------------------
csv
--------------------------
	# CSV 格式的编解码

--------------------------
var
--------------------------
	var (
		ErrBareQuote  = errors.New("bare \" in non-quoted-field")
		ErrQuote      = errors.New("extraneous or missing \" in quoted-field")
		ErrFieldCount = errors.New("wrong number of fields")

		// Deprecated: ErrTrailingComma is no longer used.
		ErrTrailingComma = errors.New("extra delimiter at end of line")
	)

--------------------------
type
--------------------------

	# type ParseError struct {
			StartLine int   // Line where the record starts
			Line      int   // Line where the error occurred
			Column    int   // Column (1-based byte index) where the error occurred
			Err       error // The actual error
		}

		func (e *ParseError) Error() string
		func (e *ParseError) Unwrap() error
	
	# type Reader struct {
			// Comma is the field delimiter.
			// It is set to comma (',') by NewReader.
			// Comma must be a valid rune and must not be \r, \n,
			// or the Unicode replacement character (0xFFFD).
			Comma rune

			// Comment, if not 0, is the comment character. Lines beginning with the
			// Comment character without preceding whitespace are ignored.
			// With leading whitespace the Comment character becomes part of the
			// field, even if TrimLeadingSpace is true.
			// Comment must be a valid rune and must not be \r, \n,
			// or the Unicode replacement character (0xFFFD).
			// It must also not be equal to Comma.
			Comment rune

			// FieldsPerRecord is the number of expected fields per record.
			// If FieldsPerRecord is positive, Read requires each record to
			// have the given number of fields. If FieldsPerRecord is 0, Read sets it to
			// the number of fields in the first record, so that future records must
			// have the same field count. If FieldsPerRecord is negative, no check is
			// made and records may have a variable number of fields.
			FieldsPerRecord int

			// If LazyQuotes is true, a quote may appear in an unquoted field and a
			// non-doubled quote may appear in a quoted field.
			LazyQuotes bool

			// If TrimLeadingSpace is true, leading white space in a field is ignored.
			// This is done even if the field delimiter, Comma, is white space.
			TrimLeadingSpace bool

			// ReuseRecord controls whether calls to Read may return a slice sharing
			// the backing array of the previous call's returned slice for performance.
			// By default, each call to Read returns newly allocated memory owned by the caller.
			ReuseRecord bool

			// Deprecated: TrailingComma is no longer used.
			TrailingComma bool
			// contains filtered or unexported fields
		}
		func NewReader(r io.Reader) *Reader
		func (r *Reader) FieldPos(field int) (line, column int)
		func (r *Reader) InputOffset() int64
		func (r *Reader) Read() (record []string, err error)
		func (r *Reader) ReadAll() (records [][]string, err error)
	
	# type Writer struct {
			Comma   rune // Field delimiter (set to ',' by NewWriter)
			UseCRLF bool // True to use \r\n as the line terminator
			// contains filtered or unexported fields
		}
		func NewWriter(w io.Writer) *Writer
		func (w *Writer) Error() error
		func (w *Writer) Flush()
		func (w *Writer) Write(record []string) error
		func (w *Writer) WriteAll(records [][]string) error

--------------------------
func
--------------------------

--------------------------
Demo
--------------------------
	# 读取

		package main

		import (
			"encoding/csv"
			"fmt"
			"io"
			"os"
		)

		func main() {
			file, err := os.Open("D:\\members.csv")
			if err != nil {
				panic(err.Error())
			}
			defer func() {
				_ = file.Close()
			}()

			err = ReadCSV(file, func(line []string) (bool, error) {
				fmt.Println(len(line), line)
				return true, nil
			})
			if err != nil {
				panic(err.Error())
			}
		}

		func ReadCSV(r io.Reader, handler func([]string) (bool, error)) error {
			reader := csv.NewReader(r)
			for {
				record, err := reader.Read()
				if err != nil {
					if err == io.EOF {
						return nil
					}
					return err
				}
				ok, err := handler(record)
				if err != nil {
					return err
				}
				if !ok {
					return nil
				}
			}
		}
