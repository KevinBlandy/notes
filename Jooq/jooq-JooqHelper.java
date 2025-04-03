

import java.util.ArrayList;
import java.util.List;

import org.jooq.DSLContext;
import org.jooq.Select;
import org.jooq.SelectLimitStep;
import org.jooq.SelectOrderByStep;
import org.jooq.SortField;
import org.jooq.SortOrder;
import org.jooq.impl.DSL;

import app.Paged;
import app.Pager;


/*

	通用的 JOOQ 分页工具

*/
public class JooqHelper {

	static final DSLContext DSL_CONTEXT = null;

	/**
	 * Count 查询
	 * 
	 * @param dsl
	 * @param statement
	 * @return
	 */
	public static int count(DSLContext dsl, Select<?> statement) {
		return dsl.selectCount().from(statement).fetchOneInto(Integer.class);
	}

	/**
	 * 分页查询
	 * @param <T>
	 * @param dsl
	 * @param statement
	 * @param modelClass
	 * @param pager
	 * @return
	 */
	public static <T> Paged<T> page(DSLContext dsl, Select<?> statement, Class<T> modelClass,
			Pager pager) {

		Paged<T> ret = Paged.empty();

		if (pager.count()) {
			int total = count(dsl, statement);
			if (total == 0) {
				return ret;
			}

			ret.setTotal(total);
		}

		ret.setList(sortAndLimitAndOffset(pager, statement).fetchInto(modelClass));

		return ret;
	}

	static boolean empty(Pager pager, int total) {
		if (total < 1 || pager.limit() < 1) {
			return false;
		}
		return pager.page() > Math.ceil((double) total / pager.limit());
	}

	static Select<?> offset(Pager pager, Select<?> statment) {
		if (pager.page() > 0) {
			((SelectLimitStep<?>)statment).offset(pager.offset());
		}
		return statment;
	}

	static Select<?> limit(Pager pager, Select<?> statment) {
		if (pager.limit() > 0) {
			((SelectLimitStep<?>)statment).limit(pager.limit());
		}
		return statment;
	}

	static Select<?> sort(Pager pager, Select<?> statment) {

		var sort = pager.sort();
		var order = pager.order();

		if (sort == null || sort.length == 0) {
			return statment;
		}

		List<SortField<?>> sortFields = new ArrayList<>();

		for (int i = 0; i < sort.length; i++) {

			var field = sort[i];

			if (!field.matches("^[a-zA-Z_][a-zA-Z0-9_]{0,63}$")) {
				throw new IllegalArgumentException("不合法的排序字段：" + field);
			}

			SortOrder sortOrder = SortOrder.ASC;

			if (order != null && order.length > i && "DESC".equalsIgnoreCase(order[i])) {
				sortOrder = SortOrder.DESC;
			}

			sortFields.add(DSL.field(field).sort(sortOrder));
		}
		((SelectOrderByStep<?>)statment).orderBy(sortFields);

		return statment;
	}

	static Select<?> sortAndLimitAndOffset(Pager pager, Select<?> statement) {

		sort(pager, statement);
		limit(pager, statement);
		offset(pager, statement);

		return statement;
	}
}

------------------
用法
------------------
		

		// 表
		var T_MEMBER = Tables.T_MEMBER;
		

		// 条件构造
		Condition condition = DSL.noCondition();
		
		// Condition
		if (request != null) {
			if (request.getTitle() != null && !request.getTitle().isBlank()) {
				condition = T_MEMBER.TITLE.like("%" + request.getTitle() + "%");
			}
		}
		
		// 基础的 Select 语句 + 条件
		var statement = dsl
				.select(T_MEMBER)
				.from(T_MEMBER)
				.where(condition)
				.forUpdate()
				;
		
		// 进行分页查询，返回分页结果
		Paged<MemberResponse> pageRequest = JooqHelper.page(dsl, statement, MemberResponse.class,  
											// 分页
											Pager.of(1, 10)
											// 排序
											.sort(new String[] {"id", "title"})
											.order(new String[]{"desc"})
		);