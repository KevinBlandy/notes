------------------------------
ResultTransformer<T>
------------------------------
	# º¯Êý½Ó¿Ú
		public interface ResultTransformer<T> {

			/**
			 * Execute the given query and transform the results
			 *
			 * @param query query to be used for execution
			 * @return transformed results
			 */
			T transform(FetchableQuery<?, ?> query);

		}
