------------------
SQLDialect
------------------
	# SQL ·½ÑÔÃ¶¾Ù
		public enum SQLDialect
	
	DEFAULT("", false, false),

	@Deprecated(forRemoval = true, since = "3.13")
    CUBRID("CUBRID", false, true),

	DERBY("Derby", false, true),

	FIREBIRD("Firebird", false, true),

	H2("H2", false, true),

	HSQLDB("HSQLDB", false, true),

	IGNITE("Ignite", false, true),

	MARIADB("MariaDB", false, true),

	MYSQL("MySQL", false, true),

	POSTGRES("Postgres", false, true),

	SQLITE("SQLite", false, true),

	public static final SQLDialect[] families()
	public static final Set<SQLDialect> predecessors(SQLDialect... dialects) 
	public static final Set<SQLDialect> supportedUntil(SQLDialect dialect) 
	public static final Set<SQLDialect> supportedUntil(SQLDialect... dialects)
	public static final Set<SQLDialect> supportedBy(SQLDialect dialect)
	public static final Set<SQLDialect> supportedBy(SQLDialect... dialects)
	public final boolean commercial()
	public final boolean supported()
	public final SQLDialect family()
	public final boolean isFamily()
	public final boolean isVersioned()
	public final SQLDialect predecessor()
	public final Set<SQLDialect> predecessors()
	public final boolean precedes(SQLDialect other)
	public final boolean supports(SQLDialect other)
	public final boolean supports(Collection<SQLDialect> other)
	public final String getName()
	public final String getNameLC()
	public final String getNameUC()
	public final ThirdParty thirdParty()
