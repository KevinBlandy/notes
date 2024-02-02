------------------------
流式检索
------------------------
	
	import static org.hibernate.jpa.QueryHints.HINT_FETCH_SIZE;
	
	// AvailableHints.HINT_FETCH_SIZE
	@QueryHints(value = @QueryHint(name = HINT_FETCH_SIZE, value = "" + Integer.MIN_VALUE))
	@Query(value = "select t from Todo t")
	Stream<Todo> streamAll();