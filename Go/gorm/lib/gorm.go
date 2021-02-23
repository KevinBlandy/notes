-----------------------
var
-----------------------
	var (
		// ErrRecordNotFound record not found error
		ErrRecordNotFound = errors.New("record not found")
		// ErrInvalidTransaction invalid transaction when you are trying to `Commit` or `Rollback`
		ErrInvalidTransaction = errors.New("no valid transaction")
		// ErrNotImplemented not implemented
		ErrNotImplemented = errors.New("not implemented")
		// ErrMissingWhereClause missing where clause
		ErrMissingWhereClause = errors.New("WHERE conditions required")
		// ErrUnsupportedRelation unsupported relations
		ErrUnsupportedRelation = errors.New("unsupported relations")
		// ErrPrimaryKeyRequired primary keys required
		ErrPrimaryKeyRequired = errors.New("primary key required")
		// ErrModelValueRequired model value required
		ErrModelValueRequired = errors.New("model value required")
		// ErrInvalidData unsupported data
		ErrInvalidData = errors.New("unsupported data")
		// ErrUnsupportedDriver unsupported driver
		ErrUnsupportedDriver = errors.New("unsupported driver")
		// ErrRegistered registered
		ErrRegistered = errors.New("registered")
		// ErrInvalidField invalid field
		ErrInvalidField = errors.New("invalid field")
		// ErrEmptySlice empty slice found
		ErrEmptySlice = errors.New("empty slice found")
		// ErrDryRunModeUnsupported dry run mode unsupported
		ErrDryRunModeUnsupported = errors.New("dry run mode unsupported")
	)

-----------------------
type
-----------------------
	# type Association struct {
			DB           *DB
			Relationship *schema.Relationship
			Error        error
		}
		func (association *Association) Append(values ...interface{}) error
		func (association *Association) Clear() error
		func (association *Association) Count() (count int64)
		func (association *Association) Delete(values ...interface{}) error
		func (association *Association) Find(out interface{}, conds ...interface{}) error
		func (association *Association) Replace(values ...interface{}) error

	# type ColumnType interface {
			Name() string
			DatabaseTypeName() string
			Length() (length int64, ok bool)
			DecimalSize() (precision int64, scale int64, ok bool)
			Nullable() (nullable bool, ok bool)
		}

	# type Config struct {
			// GORM perform single create, update, delete operations in transactions by default to ensure database data integrity
			// You can disable it by setting `SkipDefaultTransaction` to true
			SkipDefaultTransaction bool
			// NamingStrategy tables, columns naming strategy
			NamingStrategy schema.Namer
			// FullSaveAssociations full save associations
			FullSaveAssociations bool
			// Logger
			Logger logger.Interface
			// NowFunc the function to be used when creating a new timestamp
			NowFunc func() time.Time
			// DryRun generate sql without execute
			DryRun bool
			// PrepareStmt executes the given query in cached statement
			PrepareStmt bool
			// DisableAutomaticPing
			DisableAutomaticPing bool
			// DisableForeignKeyConstraintWhenMigrating
			DisableForeignKeyConstraintWhenMigrating bool
			// DisableNestedTransaction disable nested transaction
			DisableNestedTransaction bool
			// AllowGlobalUpdate allow global update
			AllowGlobalUpdate bool
			// QueryFields executes the SQL query with all fields of the table
			QueryFields bool
			// CreateBatchSize default create batch size
			CreateBatchSize int

			// ClauseBuilders clause builder
			ClauseBuilders map[string]clause.ClauseBuilder
			// ConnPool db conn pool
			ConnPool ConnPool
			// Dialector database dialector
			Dialector
			// Plugins registered plugins
			Plugins map[string]Plugin
			// contains filtered or unexported fields
		}
	
	# type ConnPool interface {
			PrepareContext(ctx context.Context, query string) (*sql.Stmt, error)
			ExecContext(ctx context.Context, query string, args ...interface{}) (sql.Result, error)
			QueryContext(ctx context.Context, query string, args ...interface{}) (*sql.Rows, error)
			QueryRowContext(ctx context.Context, query string, args ...interface{}) *sql.Row
		}
	
	# type ConnPoolBeginner interface {
			BeginTx(ctx context.Context, opts *sql.TxOptions) (ConnPool, error)
		}
	
	# type DB struct {
			*Config
			Error        error
			RowsAffected int64
			Statement    *Statement
			// contains filtered or unexported fields
		}
		func Open(dialector Dialector, config *Config) (db *DB, err error)
		func (db *DB) AddError(err error) error
		func (db *DB) Assign(attrs ...interface{}) (tx *DB)
		func (db *DB) Association(column string) *Association
		func (db *DB) Attrs(attrs ...interface{}) (tx *DB)
		func (db *DB) AutoMigrate(dst ...interface{}) error
			* 为指定的model，创建表结构

		func (db *DB) Begin(opts ...*sql.TxOptions) *DB
		func (db *DB) Callback() *callbacks
		func (db *DB) Clauses(conds ...clause.Expression) (tx *DB)
		func (db *DB) Commit() *DB
		func (db *DB) Count(count *int64) (tx *DB)
		func (db *DB) Create(value interface{}) (tx *DB)
			* 存储记录

		func (db *DB) CreateInBatches(value interface{}, batchSize int) (tx *DB)
		func (db *DB) DB() (*sql.DB, error)
			* 获取到原始的DB

		func (db *DB) Debug() (tx *DB)
		func (db *DB) Delete(value interface{}, conds ...interface{}) (tx *DB)
			* 删除记录

		func (db *DB) Distinct(args ...interface{}) (tx *DB)
		func (db *DB) Exec(sql string, values ...interface{}) (tx *DB)
		func (db *DB) Find(dest interface{}, conds ...interface{}) (tx *DB)
		func (db *DB) FindInBatches(dest interface{}, batchSize int, fc func(tx *DB, batch int) error) *DB
		func (db *DB) First(dest interface{}, conds ...interface{}) (tx *DB)
			* 读取唯一的一条记录，conds可以是主键ID，可以是条件和填充值
				db.First(p, 5)					// 根据ID查找
				db.First(p, "Code = ?", "1")	// 根据Code条件查找，可以是列名称，或者是字段名称

		func (db *DB) FirstOrCreate(dest interface{}, conds ...interface{}) (tx *DB)
		func (db *DB) FirstOrInit(dest interface{}, conds ...interface{}) (tx *DB)
		func (db *DB) Get(key string) (interface{}, bool)
		func (db *DB) Group(name string) (tx *DB)
		func (db *DB) Having(query interface{}, args ...interface{}) (tx *DB)
		func (db *DB) InstanceGet(key string) (interface{}, bool)
		func (db *DB) InstanceSet(key string, value interface{}) *DB
		func (db *DB) Joins(query string, args ...interface{}) (tx *DB)
		func (db *DB) Last(dest interface{}, conds ...interface{}) (tx *DB)
		func (db *DB) Limit(limit int) (tx *DB)
		func (db *DB) Migrator() Migrator
		func (db *DB) Model(value interface{}) (tx *DB)
			* 绑定到Model，然后可以针对这个Model进行操作

		func (db *DB) Not(query interface{}, args ...interface{}) (tx *DB)
		func (db *DB) Offset(offset int) (tx *DB)
		func (db *DB) Omit(columns ...string) (tx *DB)
		func (db *DB) Or(query interface{}, args ...interface{}) (tx *DB)
		func (db *DB) Order(value interface{}) (tx *DB)
		func (db *DB) Pluck(column string, dest interface{}) (tx *DB)
		func (db *DB) Preload(query string, args ...interface{}) (tx *DB)
		func (db *DB) Raw(sql string, values ...interface{}) (tx *DB)
		func (db *DB) Rollback() *DB
		func (db *DB) RollbackTo(name string) *DB
		func (db *DB) Row() *sql.Row
		func (db *DB) Rows() (*sql.Rows, error)
		func (db *DB) Save(value interface{}) (tx *DB)
		func (db *DB) SavePoint(name string) *DB
		func (db *DB) Scan(dest interface{}) (tx *DB)
		func (db *DB) ScanRows(rows *sql.Rows, dest interface{}) error
		func (db *DB) Scopes(funcs ...func(*DB) *DB) *DB
		func (db *DB) Select(query interface{}, args ...interface{}) (tx *DB)
		func (db *DB) Session(config *Session) *DB
		func (db *DB) Set(key string, value interface{}) *DB
		func (db *DB) SetupJoinTable(model interface{}, field string, joinTable interface{}) error
		func (db *DB) Table(name string, args ...interface{}) (tx *DB)
			* 绑定一张表，接下来对这张表进行操作

		func (db *DB) Take(dest interface{}, conds ...interface{}) (tx *DB)
		func (db *DB) Transaction(fc func(tx *DB) error, opts ...*sql.TxOptions) (err error)
		func (db *DB) Unscoped() (tx *DB)

		func (db *DB) Update(column string, value interface{}) (tx *DB)
			* 更新单个字段
				db.Model(&Product{Model: &gorm.Model{ID: 2}}).Update("code", "我是Code")
			
		func (db *DB) UpdateColumn(column string, value interface{}) (tx *DB)
		func (db *DB) UpdateColumns(values interface{}) (tx *DB)
		func (db *DB) Updates(values interface{}) (tx *DB)
			* 更新多个字段
			* 根据对象，仅仅更新非零字段
				db.Model(&Product{Model: &gorm.Model{ID: 2}})		// 通过Model绑定条件
					.Updates(Product{Code: "10010"})				// 更新字段
			
			* 也可以通过Map来设置值
				db.Model(&Product{Model: &gorm.Model{ID: 2}}).Updates(map[string] interface{} {
					"Code": "New Code",
				})

		func (db *DB) Use(plugin Plugin) error
		func (db *DB) Where(query interface{}, args ...interface{}) (tx *DB)
		func (db *DB) WithContext(ctx context.Context) *DB

	# type DeletedAt sql.NullTime
		func (DeletedAt) DeleteClauses(f *schema.Field) []clause.Interface
		func (n DeletedAt) MarshalJSON() ([]byte, error)
		func (DeletedAt) QueryClauses(f *schema.Field) []clause.Interface
		func (n *DeletedAt) Scan(value interface{}) error
		func (n *DeletedAt) UnmarshalJSON(b []byte) error
		func (n DeletedAt) Value() (driver.Value, error)
	
	# type Dialector interface {
			Name() string
			Initialize(*DB) error
			Migrator(db *DB) Migrator
			DataTypeOf(*schema.Field) string
			DefaultValueOf(*schema.Field) clause.Expression
			BindVarTo(writer clause.Writer, stmt *Statement, v interface{})
			QuoteTo(clause.Writer, string)
			Explain(sql string, vars ...interface{}) string
		}

	# type Migrator interface {
			// AutoMigrate
			AutoMigrate(dst ...interface{}) error

			// Database
			CurrentDatabase() string
			FullDataTypeOf(*schema.Field) clause.Expr

			// Tables
			CreateTable(dst ...interface{}) error
			DropTable(dst ...interface{}) error
			HasTable(dst interface{}) bool
			RenameTable(oldName, newName interface{}) error

			// Columns
			AddColumn(dst interface{}, field string) error
			DropColumn(dst interface{}, field string) error
			AlterColumn(dst interface{}, field string) error
			MigrateColumn(dst interface{}, field *schema.Field, columnType ColumnType) error
			HasColumn(dst interface{}, field string) bool
			RenameColumn(dst interface{}, oldName, field string) error
			ColumnTypes(dst interface{}) ([]ColumnType, error)

			// Views
			CreateView(name string, option ViewOption) error
			DropView(name string) error

			// Constraints
			CreateConstraint(dst interface{}, name string) error
			DropConstraint(dst interface{}, name string) error
			HasConstraint(dst interface{}, name string) bool

			// Indexes
			CreateIndex(dst interface{}, name string) error
			DropIndex(dst interface{}, name string) error
			HasIndex(dst interface{}, name string) bool
			RenameIndex(dst interface{}, oldName, newName string) error
		}
	
	# type Model struct {
			ID        uint `gorm:"primarykey"`
			CreatedAt time.Time
			UpdatedAt time.Time
			DeletedAt DeletedAt `gorm:"index"`
		}
	
	# type User struct {
		  gorm.Model
		}
	
	# type Plugin interface {
			Name() string
			Initialize(*DB) error
		}

	# type PreparedStmtDB struct {
			Stmts       map[string]Stmt
			PreparedSQL []string
			Mux         *sync.RWMutex
			ConnPool
		}
		func (db *PreparedStmtDB) BeginTx(ctx context.Context, opt *sql.TxOptions) (ConnPool, error)
		func (db *PreparedStmtDB) Close()
		func (db *PreparedStmtDB) ExecContext(ctx context.Context, query string, args ...interface{}) (result sql.Result, err error)
		func (db *PreparedStmtDB) QueryContext(ctx context.Context, query string, args ...interface{}) (rows *sql.Rows, err error)
		func (db *PreparedStmtDB) QueryRowContext(ctx context.Context, query string, args ...interface{}) *sql.Row
	
	# type PreparedStmtTX struct {
			*sql.Tx
			PreparedStmtDB *PreparedStmtDB
		}
		func (tx *PreparedStmtTX) Commit() error
		func (tx *PreparedStmtTX) ExecContext(ctx context.Context, query string, args ...interface{}) (result sql.Result, err error)
		func (tx *PreparedStmtTX) QueryContext(ctx context.Context, query string, args ...interface{}) (rows *sql.Rows, err error)
		func (tx *PreparedStmtTX) QueryRowContext(ctx context.Context, query string, args ...interface{}) *sql.Row
		func (tx *PreparedStmtTX) Rollback() error
	
	# type SavePointerDialectorInterface interface {
			SavePoint(tx *DB, name string) error
			RollbackTo(tx *DB, name string) error
		}
	
	# type Session struct {
			DryRun                   bool
			PrepareStmt              bool
			NewDB                    bool
			SkipHooks                bool
			SkipDefaultTransaction   bool
			DisableNestedTransaction bool
			AllowGlobalUpdate        bool
			FullSaveAssociations     bool
			QueryFields              bool
			Context                  context.Context
			Logger                   logger.Interface
			NowFunc                  func() time.Time
			CreateBatchSize          int
		}
	
	# type SoftDeleteDeleteClause struct {
			Field *schema.Field
		}
		func (sd SoftDeleteDeleteClause) Build(clause.Builder)
		func (sd SoftDeleteDeleteClause) MergeClause(*clause.Clause)
		func (sd SoftDeleteDeleteClause) ModifyStatement(stmt *Statement)
		func (sd SoftDeleteDeleteClause) Name() string
	
	# type SoftDeleteQueryClause struct {
			Field *schema.Field
		}
		func (sd SoftDeleteQueryClause) Build(clause.Builder)
		func (sd SoftDeleteQueryClause) MergeClause(*clause.Clause)
		func (sd SoftDeleteQueryClause) ModifyStatement(stmt *Statement)
		func (sd SoftDeleteQueryClause) Name() string
	
	# type Statement struct {
			*DB
			TableExpr            *clause.Expr
			Table                string
			Model                interface{}
			Unscoped             bool
			Dest                 interface{}
			ReflectValue         reflect.Value
			Clauses              map[string]clause.Clause
			Distinct             bool
			Selects              []string // selected columns
			Omits                []string // omit columns
			Joins                []join
			Preloads             map[string][]interface{}
			Settings             sync.Map
			ConnPool             ConnPool
			Schema               *schema.Schema
			Context              context.Context
			RaiseErrorOnNotFound bool
			SkipHooks            bool
			SQL                  strings.Builder
			Vars                 []interface{}
			CurDestIndex         int
			// contains filtered or unexported fields
		}
		func (stmt *Statement) AddClause(v clause.Interface)
		func (stmt *Statement) AddClauseIfNotExists(v clause.Interface)
		func (stmt *Statement) AddVar(writer clause.Writer, vars ...interface{})
		func (stmt *Statement) Build(clauses ...string)
		func (stmt *Statement) BuildCondition(query interface{}, args ...interface{}) []clause.Expression
		func (stmt *Statement) Changed(fields ...string) bool
		func (stmt *Statement) Parse(value interface{}) (err error)
		func (stmt *Statement) Quote(field interface{}) string
		func (stmt *Statement) QuoteTo(writer clause.Writer, field interface{})
		func (stmt *Statement) SelectAndOmitColumns(requireCreate, requireUpdate bool) (map[string]bool, bool)
		func (stmt *Statement) SetColumn(name string, value interface{}, fromCallbacks ...bool)
		func (stmt *Statement) WriteByte(c byte) error
		func (stmt *Statement) WriteQuoted(value interface{})
		func (stmt *Statement) WriteString(str string) (int, error)

	# type StatementModifier interface {
			ModifyStatement(*Statement)
		}
	
	# type Stmt struct {
			*sql.Stmt
			Transaction bool
		}
	
	# type TxBeginner interface {
			BeginTx(ctx context.Context, opts *sql.TxOptions) (*sql.Tx, error)
		}
	
	# type TxCommitter interface {
			Commit() error
			Rollback() error
		}
	
	# type Valuer interface {
			GormValue(context.Context, *DB) clause.Expr
		}
	
	# type ViewOption struct {
			Replace     bool
			CheckOption string
			Query       *DB
		}

-----------------------
func
-----------------------
	func Expr(expr string, args ...interface{}) clause.Expr
	func Scan(rows *sql.Rows, db *DB, initialized bool)