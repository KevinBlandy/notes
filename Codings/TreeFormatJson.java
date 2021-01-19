// 把树形结构, 序列化为JSON

/**
CREATE TABLE `foo` (
	`id` INT ( 10 ) UNSIGNED NOT NULL AUTO_INCREMENT,
	`name` VARCHAR ( 255 ) COLLATE utf8mb4_croatian_ci DEFAULT NULL,
	`parent_id` INT ( 10 ) UNSIGNED DEFAULT NULL,
PRIMARY KEY ( `id` ) 
) ENGINE = INNODB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_croatian_ci;
 
 */
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import org.sql2o.Connection;
import org.sql2o.Sql2o;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

class Foo {
	private Integer id;
	private String name;
	private Integer parentId;
	private List<Foo> childs;

	public Foo(int id, String name, Integer parentId) {
		super();
		this.id = id;
		this.name = name;
		this.parentId = parentId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public List<Foo> getChilds() {
		return childs;
	}

	public void setChilds(List<Foo> childs) {
		this.childs = childs;
	}

	@Override
	public String toString() {
		return "Foo [id=" + id + ", name=" + name + ", parentId=" + parentId + "]";
	}
}

public class Main {
	public static void main(String[] args) throws SQLException {
		System.out.println(JSON.toJSONString(getFoos(), SerializerFeature.PrettyFormat));

	}

	public static List<Foo> getFoos() {
		Sql2o sql2o = new Sql2o(
				"jdbc:mysql://localhost:3306/demo?useUnicode=true&characterEncoding=utf8&autoReconnect=true&allowMultiQueries=true&serverTimezone=GMT%2b8",
				"root", "root");
		
		Connection connection = sql2o.beginTransaction();

		// 检索顶级记录
		List<Foo> foos = connection.createQuery("SELECT * FROM `foo` WHERE `parent_id` = 0;")
				.addColumnMapping("parent_id", "parentId").executeAndFetch(Foo.class);

		LinkedList<Foo> queue = new LinkedList<Foo>();
		for (Foo foo : foos) {
			queue.addLast(foo);
		}

		// 遍历所有子记录
		while (!queue.isEmpty()) {

			Foo foo = queue.pollLast();

			List<Foo> subFoos = connection.createQuery("SELECT * FROM `foo` WHERE `parent_id` = :id;")
					.addParameter("id", foo.getId()).addColumnMapping("parent_id", "parentId")
					.executeAndFetch(Foo.class);

			if (!subFoos.isEmpty()) {
				for (Foo subFoo : subFoos) {
					queue.addLast(subFoo);
				}
				foo.setChilds(subFoos);
			}
		}
		return foos;
	}
}
