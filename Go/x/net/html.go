-----------------------
html
-----------------------

-----------------------
var
-----------------------
	var ErrBufferExceeded = errors.New("max buffer exceeded")

-----------------------
type
-----------------------

	# type Attribute struct {
			Namespace, Key, Val string
		}
	
	# type Node struct {
			Parent, FirstChild, LastChild, PrevSibling, NextSibling *Node

			Type      NodeType
			DataAtom  atom.Atom
			Data      string
			Namespace string
			Attr      []Attribute
		}

		func Parse(r io.Reader) (*Node, error)
		func ParseFragment(r io.Reader, context *Node) ([]*Node, error)
		func ParseFragmentWithOptions(r io.Reader, context *Node, opts ...ParseOption) ([]*Node, error)
		func ParseWithOptions(r io.Reader, opts ...ParseOption) (*Node, error)
		func (n *Node) AppendChild(c *Node)
		func (n *Node) InsertBefore(newChild, oldChild *Node)
		func (n *Node) RemoveChild(c *Node)
	
	# type NodeType uint32
		const (
			ErrorNode NodeType = iota
			TextNode
			DocumentNode
			ElementNode
			CommentNode
			DoctypeNode
			// RawNode nodes are not returned by the parser, but can be part of the
			// Node tree passed to func Render to insert raw HTML (without escaping).
			// If so, this package makes no guarantee that the rendered HTML is secure
			// (from e.g. Cross Site Scripting attacks) or well-formed.
			RawNode
		)
	
	# type ParseOption func(p *parser)

		func ParseOptionEnableScripting(enable bool) ParseOption
	
	# type Token struct {
			Type     TokenType
			DataAtom atom.Atom
			Data     string
			Attr     []Attribute
		}

		func (t Token) String() string
	
	# type TokenType uint32

		func (t TokenType) String() string
	
	# type Tokenizer struct {
			// contains filtered or unexported fields
		}
		
		func NewTokenizer(r io.Reader) *Tokenizer
		func NewTokenizerFragment(r io.Reader, contextTag string) *Tokenizer
		func (z *Tokenizer) AllowCDATA(allowCDATA bool)
		func (z *Tokenizer) Buffered() []byte
		func (z *Tokenizer) Err() error
		func (z *Tokenizer) Next() TokenType
		func (z *Tokenizer) NextIsNotRawText()
		func (z *Tokenizer) Raw() []byte
		func (z *Tokenizer) SetMaxBuf(n int)
		func (z *Tokenizer) TagAttr() (key, val []byte, moreAttr bool)
		func (z *Tokenizer) TagName() (name []byte, hasAttr bool)
		func (z *Tokenizer) Text() []byte
		func (z *Tokenizer) Token() Token


-----------------------
func
-----------------------
	func EscapeString(s string) string
	func Render(w io.Writer, n *Node) error
	func UnescapeString(s string) string




-----------------------
func
-----------------------
	# 解析出HTML文档中的所有IMG标签
		import (
			"golang.org/x/net/html"
			"strings"
		)

		// ParseImages 解析HTML文档中的image地址
		func ParseImages(htmlContent string) ([]string, error) {
			if htmlContent == "" {
				return nil, nil
			}
			document, err := html.Parse(strings.NewReader(htmlContent))
			if err != nil {
				return nil, err
			}

			image := make(map[string]struct{})

			mapValue := struct{}{}

			var handle func(*html.Node)

			handle = func(node *html.Node) {
				if node.Type == html.ElementNode && node.Data == "img" {
					for _, attr := range node.Attr {
						if attr.Key == "src" {
							src := attr.Val
							if strings.HasPrefix(src, "/") { // 相对路径
								image[strings.TrimSpace(src)] = mapValue
							}
							break
						}
					}
				}
				// 递归解析
				for child := node.FirstChild; child != nil; child = child.NextSibling {
					handle(child)
				}
			}

			handle(document)

			ret := make([]string, 0)
			for key, _ := range image {
				ret = append(ret, key)
			}

			return ret, nil
		}
