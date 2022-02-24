-------------------
parse
-------------------

-------------------
var
-------------------


-------------------
type
-------------------
	# type ActionNode struct {
			NodeType
			Pos

			Line int       // The line number in the input. Deprecated: Kept for compatibility.
			Pipe *PipeNode // The pipeline in the action.
			// contains filtered or unexported fields
		}
		func (a *ActionNode) Copy() Node
		func (a *ActionNode) String() string
	
	# type BoolNode struct {
			NodeType
			Pos

			True bool // The value of the boolean constant.
		}
		func (b *BoolNode) Copy() Node
		func (b *BoolNode) String() string
	
	# type BranchNode struct {
			NodeType
			Pos

			Line     int       // The line number in the input. Deprecated: Kept for compatibility.
			Pipe     *PipeNode // The pipeline to be evaluated.
			List     *ListNode // What to execute if the value is non-empty.
			ElseList *ListNode // What to execute if the value is empty (nil if absent).
			// contains filtered or unexported fields
		}
		func (b *BranchNode) Copy() Node
		func (b *BranchNode) String() string
	
	# type ChainNode struct {
			NodeType
			Pos

			Node  Node
			Field []string // The identifiers in lexical order.
			// contains filtered or unexported fields
		}
		func (c *ChainNode) Add(field string)
		func (c *ChainNode) Copy() Node
		func (c *ChainNode) String() string
	
	# type CommandNode struct {
			NodeType
			Pos

			Args []Node // Arguments in lexical order: Identifier, field, or constant.
			// contains filtered or unexported fields
		}
		func (c *CommandNode) Copy() Node
		func (c *CommandNode) String() string
	
	# type CommentNode struct {
			NodeType
			Pos

			Text string // Comment text.
			// contains filtered or unexported fields
		}
		func (c *CommentNode) Copy() Node
		func (c *CommentNode) String() string
	
	# type DotNode struct {
			NodeType
			Pos
			// contains filtered or unexported fields
		}
		func (d *DotNode) Copy() Node
		func (d *DotNode) String() string
		func (d *DotNode) Type() NodeType

	# type FieldNode struct {
			NodeType
			Pos

			Ident []string // The identifiers in lexical order.
			// contains filtered or unexported fields
		}
		func (f *FieldNode) Copy() Node
		func (f *FieldNode) String() string
	
	# type IdentifierNode struct {
			NodeType
			Pos

			Ident string // The identifier's name.
			// contains filtered or unexported fields
		}
		func NewIdentifier(ident string) *IdentifierNode
		func (i *IdentifierNode) Copy() Node
		func (i *IdentifierNode) SetPos(pos Pos) *IdentifierNode
		func (i *IdentifierNode) SetTree(t *Tree) *IdentifierNode
		func (i *IdentifierNode) String() string
	
	# type IfNode struct {
			BranchNode
		}
		func (i *IfNode) Copy() Node
	
	# type ListNode struct {
			NodeType
			Pos

			Nodes []Node // The element nodes in lexical order.
			// contains filtered or unexported fields
		}
		func (l *ListNode) Copy() Node
		func (l *ListNode) CopyList() *ListNode
		func (l *ListNode) String() string
	
	# type Mode uint
		const (
			ParseComments Mode = 1 << iota // parse comments and add them to AST
			SkipFuncCheck                  // do not check that functions are defined
		)
	
	# type NilNode struct {
			NodeType
			Pos
			// contains filtered or unexported fields
		}
		func (n *NilNode) Copy() Node
		func (n *NilNode) String() string
		func (n *NilNode) Type() NodeType
	
	# type Node interface {
			Type() NodeType
			String() string
			// Copy does a deep copy of the Node and all its components.
			// To avoid type assertions, some XxxNodes also have specialized
			// CopyXxx methods that return *XxxNode.
			Copy() Node
			Position() Pos // byte position of start of node in full original input string
			// contains filtered or unexported methods
		}
	
	# type NodeType int
		func (t NodeType) Type() NodeType
		const (
			NodeText    NodeType = iota // Plain text.
			NodeAction                  // A non-control action such as a field evaluation.
			NodeBool                    // A boolean constant.
			NodeChain                   // A sequence of field accesses.
			NodeCommand                 // An element of a pipeline.
			NodeDot                     // The cursor, dot.

			NodeField      // A field or method name.
			NodeIdentifier // An identifier; always a function name.
			NodeIf         // An if action.
			NodeList       // A list of Nodes.
			NodeNil        // An untyped nil constant.
			NodeNumber     // A numerical constant.
			NodePipe       // A pipeline of commands.
			NodeRange      // A range action.
			NodeString     // A string constant.
			NodeTemplate   // A template invocation action.
			NodeVariable   // A $ variable.
			NodeWith       // A with action.
			NodeComment    // A comment.
		)
	






	# type Tree struct {
			Name      string    // name of the template represented by the tree.
			ParseName string    // name of the top-level template during parsing, for error messages.
			Root      *ListNode // top-level root of the tree.
			Mode      Mode      // parsing mode.
			// contains filtered or unexported fields
		}
		func New(name string, funcs ...map[string]interface{}) *Tree
		func (t *Tree) Copy() *Tree
		func (t *Tree) ErrorContext(n Node) (location, context string)
		func (t *Tree) Parse(text, leftDelim, rightDelim string, treeSet map[string]*Tree, ...) (tree *Tree, err error)

-------------------
func
-------------------
	func IsEmptyTree(n Node) bool
	func Parse(name, text, leftDelim, rightDelim string, funcs ...map[string]interface{}) (map[string]*Tree, error)
