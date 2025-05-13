--------------
JdbcClient
--------------
	# 依赖
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-jdbc</artifactId>
		</dependency>

	# 静态方法创建实例
		static JdbcClient create(DataSource dataSource)
		static JdbcClient create(JdbcOperations jdbcTemplate)
		static JdbcClient create(NamedParameterJdbcOperations jdbcTemplate)
	
	# 唯一方法，设置 SQL，返回内部接口
		StatementSpec sql(String sql)
	
----------------------------
StatementSpec
----------------------------
	StatementSpec param(@Nullable Object value);
	StatementSpec param(int jdbcIndex, @Nullable Object value);
	StatementSpec param(int jdbcIndex, @Nullable Object value, int sqlType);
	StatementSpec param(String name, @Nullable Object value);
	StatementSpec param(String name, @Nullable Object value, int sqlType);
	StatementSpec params(Object... values);
	StatementSpec params(List<?> values);
	StatementSpec params(Map<String, ?> paramMap);

	StatementSpec paramSource(Object namedParamObject);
	tatementSpec paramSource(SqlParameterSource namedParamSource);

	ResultQuerySpec query();
	<T> MappedQuerySpec<T> query(Class<T> mappedClass);
	<T> MappedQuerySpec<T> query(RowMapper<T> rowMapper);
	void query(RowCallbackHandler rch);
	<T> T query(ResultSetExtractor<T> rse);

	int update();
	int update(KeyHolder generatedKeyHolder);
	int update(KeyHolder generatedKeyHolder, String... keyColumnNames);

----------------------------
ResultQuerySpec
----------------------------
	SqlRowSet rowSet();
	List<Map<String, Object>> listOfRows();
	Map<String, Object> singleRow();
	List<Object> singleColumn();
	Object singleValue()

----------------------------
MappedQuerySpec
----------------------------
	MappedQuerySpec
	List<T> list();
	Set<T> set()
	Optional<T> optional()
	T single()

----------------------------
基于 JDBCClient 的分页查询
----------------------------


import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.JdbcClient.StatementSpec;

import jakarta.annotation.Nullable;
import lombok.extern.slf4j.Slf4j;
import app.PageRequest;
import app.Paged;
import app.BeanHolder;

@Slf4j
public class PageHelper {

	/**
	 * 统计查询
	 * @param query
	 * @param binder
	 * @return
	 */
	public static Integer count(String query, @Nullable Function<StatementSpec, StatementSpec> binder) {

		Objects.requireNonNull(query);

		String countSql = "SELECT COUNT(*) AS `count` FROM (" + query + ") _tmp;";

		log.info("COUNT: {}", countSql);

		var statement = BeanHolder.JdbcClientHolder.client.sql(countSql);

		if (binder != null) {
			statement = binder.apply(statement);
		}

		return statement.query(Integer.class).single();
	}
	
	/**
	 * 分页查询，无参数
	 * @param <T>
	 * @param page
	 * @param query
	 * @param mapper
	 * @return
	 */
	public static <T> Paged<T> page(PageRequest page, String query, RowMapper<T> mapper) {
		return page(page, query, Function.identity(), mapper);
	}
	
	/**
	 * 分页查询，Map 参数
	 * @param <T>
	 * @param page
	 * @param query
	 * @param params
	 * @param mapper
	 * @return
	 */
	public static <T> Paged<T> page(PageRequest page, String query, @Nullable Map<String, Object> params, RowMapper<T> mapper) {
		Objects.requireNonNull(params);
		return page(page, query, statement -> statement.params(params), mapper);
	}

	/**
	 * 分页查询，自己绑定参数
	 * @param <T>
	 * @param page
	 * @param query
	 * @param binder
	 * @param mapper
	 * @return
	 */
	public static <T> Paged<T> page(PageRequest page, String query, @Nullable Function<StatementSpec, StatementSpec> binder, RowMapper<T> mapper) {

		Objects.requireNonNull(page);
		Objects.requireNonNull(query);

		Paged<T> ret = Paged.empty();

		if (page.count()) {

			var total = count(query, binder);

			ret.setTotal(total);

			if (total == 0 || (page.limit() > 0 && page.page() > (int) Math.ceil((double) total / page.limit()))) {
				// 无记录或页码大于了总页数
				return ret;
			}
		}

		String select = "SELECT `_tmp`.* FROM (" + query + ") AS `_tmp`";

		// Order
		var sort = page.sort();
		var order = page.order();

		if (sort != null && sort.length > 0) {

			select += " ORDER BY ";

			for (int i = 0; i < sort.length; i++) {

				if (i != 0) {
					select += ", ";
				}

				var prop = sort[i];

				// TODO 非法属性？

				if (order != null && order.length > i) {

					var direction = order[i];

					if (direction != null && Objects.equals(direction.toUpperCase(), "DESC")) {
						select += "`" + prop + "` DESC";
						continue;
					}
				}

				// 默认 ASC
				select += "`" + prop + "` ASC";
			}
		}

		if (page.limit() > 0) {
			select += " LIMIT " + page.limit();
		}

		if (page.page() > 0) {
			select += " OFFSET " + page.offset();
		}

		select += ";";

		log.info("LIST: {}", select);

		var statement = BeanHolder.JdbcClientHolder.client.sql(select);

		if (binder != null) {
			statement = binder.apply(statement);
		}

		ret.setList(statement.query(mapper).list());

		return ret;
	}
}
