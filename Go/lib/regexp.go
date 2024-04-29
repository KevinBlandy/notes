-------------
regexp
-------------
-------------
����
-------------
-------------
type
-------------
	# type Regexp struct 

		* ������ʽ�Ľṹ��

		func Compile(expr string) (*Regexp, error)
		func CompilePOSIX(expr string) (*Regexp, error)
			* CompilePOSIX �� Compile �Ĳ�ͬ������ POSIX ����ʹ�� POSIX �﷨����ʹ���������ʽ����
			* �� Compile �ǲ��õ���ֻ��������ʽ���� 
			
			* ���� [a-z]{2,4} ����һ��������ʽ��Ӧ���� "aa09aaa88aaaa" ����ı���ʱ��CompilePOSIX ������ aaaa���� Compile �ķ��ص��� aa


		func MustCompile(str string) *Regexp
		func MustCompilePOSIX(str string) *Regexp
			* ͬ�ϣ����������벻��������쳣

		func (re *Regexp) Copy() *Regexp
		func (re *Regexp) Expand(dst []byte, template []byte, src []byte, match []int) []byte
		func (re *Regexp) ExpandString(dst []byte, template string, src string, match []int) []byte
		func (re *Regexp) Find(b []byte) []byte
		func (re *Regexp) FindAll(b []byte, n int) [][]byte
		func (re *Regexp) FindAllIndex(b []byte, n int) [][]int
		func (re *Regexp) FindAllString(s string, n int) []string
			* FindString �������汾������ n������ֻ����ǰn��ƥ�䣬ָ�� -1 �Ļ���������� s �ַ��������з��ϱ��ʽ���Ӵ�
		
		func (re *Regexp) FindAllStringIndex(s string, n int) [][]int
			* FindStringIndex �������汾������ n������ֻ����ǰn��ƥ�䣬ָ�� -1 �Ļ���������� s �ַ��������з��ϱ��ʽ���±�
			* ��������ڣ��򷵻� nill

		func (re *Regexp) FindAllStringSubmatch(s string, n int) [][]string
		func (re *Regexp) FindAllStringSubmatchIndex(s string, n int) [][]int
		func (re *Regexp) FindAllSubmatch(b []byte, n int) [][][]byte
		func (re *Regexp) FindAllSubmatchIndex(b []byte, n int) [][]int
		func (re *Regexp) FindIndex(b []byte) (loc []int)
		func (re *Regexp) FindReaderIndex(r io.RuneReader) (loc []int)
		func (re *Regexp) FindReaderSubmatchIndex(r io.RuneReader) []int

		func (re *Regexp) FindString(s string) string
			* ��ȡ s �з��ϱ��ʽ���Ӵ�

		func (re *Regexp) FindStringIndex(s string) (loc []int)
			* �������ı����� s �н���ƥ����ң����ڵ�һ��ƥ��ɹ�ʱ����ƥ��λ�ã���ʼ�ͽ���λ�ã���
			* ��������ڣ��򷵻� nill

		func (re *Regexp) FindStringSubmatch(s string) []string


		func (re *Regexp) FindStringSubmatchIndex(s string) []int
		func (re *Regexp) FindSubmatch(b []byte) [][]byte
		func (re *Regexp) FindSubmatchIndex(b []byte) []int
		func (re *Regexp) LiteralPrefix() (prefix string, complete bool)
		func (re *Regexp) Longest()
		func (re *Regexp) MarshalText() ([]byte, error)

		func (re *Regexp) Match(b []byte) bool
		func (re *Regexp) MatchReader(r io.RuneReader) bool
		func (re *Regexp) MatchString(s string) bool
			* �Ƿ����ƥ��ָ�����ֽ�����/Reader/�ַ���

		func (re *Regexp) NumSubexp() int
		func (re *Regexp) ReplaceAll(src, repl []byte) []byte
		func (re *Regexp) ReplaceAllFunc(src []byte, repl func([]byte) []byte) []byte
		func (re *Regexp) ReplaceAllLiteral(src, repl []byte) []byte
		func (re *Regexp) ReplaceAllLiteralString(src, repl string) string
		func (re *Regexp) ReplaceAllString(src, repl string) string
		func (re *Regexp) ReplaceAllStringFunc(src string, repl func(string) string) string
			* ��src�з���this������ַ���������func�����������滻

		func (re *Regexp) Split(s string, n int) []string
		func (re *Regexp) String() string
		func (re *Regexp) SubexpIndex(name string) int
		func (re *Regexp) SubexpNames() []string
		func (re *Regexp) UnmarshalText(text []byte) error
	
-------------
����
-------------
	func Match(pattern string, b []byte) (matched bool, err error)
	func MatchReader(pattern string, r io.RuneReader) (matched bool, err error)
	func MatchString(pattern string, s string) (matched bool, err error)
		* �ж�ָ�����ֽ�����/Reader/�ַ��� �Ƿ���ϱ��ʽ

	func QuoteMeta(s string) string

----------------------
ʾ��
----------------------
	package main

	import (
		"fmt"
		"regexp"
	)

	func main() {
		text := "Hello123World456"
		reg := regexp.MustCompile("\\d{3}")

		// ��ȡƥ�䵽���ִ���ʼ������λ��
		// [5 8]
		fmt.Println(reg.FindStringIndex(text))
		// [[5 8] [13 16]]
		fmt.Println(reg.FindAllStringIndex(text, -1))

		// ��ȡƥ�䵽���ִ�
		// 123
		fmt.Println(reg.FindString(text))
		// [123 456]
		fmt.Println(reg.FindAllString(text, -1))

		// ��ƥ�䵽���Ӵ������滻
		ret := reg.ReplaceAllStringFunc(text, func(s string) string {
			return "[" + s + "]"
		})
		//Hello[123]World[456]
		fmt.Println(ret)
	}
