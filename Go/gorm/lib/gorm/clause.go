----------------
clause
----------------

----------------
var
----------------
	const (
		PrimaryKey   string = "@@@py@@@" // primary key
		CurrentTable string = "@@@ct@@@" // current table
		Associations string = "@@@as@@@" // associations
	)
	var (
		PrimaryColumn = Column{Table: CurrentTable, Name: PrimaryKey}
	)

----------------
type
----------------
	# type AndConditions struct {
			Exprs []Expression
		}
		func (and AndConditions) Build(builder Builder)
	
	# type Assignment struct {
			Column Column
			Value  interface{}
		}
	
	# type Builder interface {
			Writer
			WriteQuoted(field interface{})
			AddVar(Writer, ...interface{})
		}
	
	# type Clause struct {
			Name                string // WHERE
			BeforeExpression    Expression
			AfterNameExpression Expression
			AfterExpression     Expression
			Expression          Expression
			Builder             ClauseBuilder
		}
		func (c Clause) Build(builder Builder)
	
	# type ClauseBuilder func(Clause, Builder)
	
	# type Column struct {
			Table string
			Name  string
			Alias string
			Raw   bool
		}
	
	# type Delete struct {
			Modifier string
		}
		func (d Delete) Build(builder Builder)
		func (d Delete) MergeClause(clause *Clause)
		func (d Delete) Name() string
	
	# type Eq struct {
			Column interface{}
			Value  interface{}
		}
		func (eq Eq) Build(builder Builder)
		func (eq Eq) NegationBuild(builder Builder)
	
	# type Expr struct {
			SQL                string
			Vars               []interface{}
			WithoutParentheses bool
		}
		func (expr Expr) Build(builder Builder)
	
	# type Expression interface {
			Build(builder Builder)
		}
		func And(exprs ...Expression) Expression
		func Not(exprs ...Expression) Expression
		func Or(exprs ...Expression) Expression
	
	# type From struct {
			Tables []Table
			Joins  []Join
		}
		func (from From) Build(builder Builder)
		func (from From) MergeClause(clause *Clause)
		func (from From) Name() string
	
	# type GroupBy struct {
			Columns []Column
			Having  []Expression
		}
		func (groupBy GroupBy) Build(builder Builder)
		func (groupBy GroupBy) MergeClause(clause *Clause)
		func (groupBy GroupBy) Name() string

	# type Gt Eq
		func (gt Gt) Build(builder Builder)
		func (gt Gt) NegationBuild(builder Builder)
	
	# type Gte Eq
		func (gte Gte) Build(builder Builder)
		func (gte Gte) NegationBuild(builder Builder)

----------------
func
----------------