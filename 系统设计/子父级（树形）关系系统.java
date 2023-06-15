------------------------------
物化路径					  |
------------------------------
	# 使用一张表描述关系

	# 数据表(user)
		id			int			主键
		parent_id	int			父级id
		name		varchar		名称
	
	# 关系表(user_chain)
		id		int		用户id
		depth	int		所处的等级
		chain	text	关系链
		
		PRIMARY KEY (`id`)
		UNION KEY(`path`)

		* chain 存储关系链列表, 使用任意分隔符: | - / 
		* 如果关系过深, chain 字段会很长
	

	# 添加数据
		* 主要的思想就是, 把父级用户的chain加上自己的id存储在当前用户的记录中, 形成一个完整的关系链
	
	# 查询指定节点下的所有/直接节点
		SELECT
			-- 下线级别
			`t1`.`level` - `t`.`level` AS `level`,
			-- 下线用户信息
			`t2`.*,
		FROM
			`user_chain` AS `t`
			INNER JOIN `user_chain` AS `t1` ON `t1`.`chain` LIKE CONCAT(`t`.`chain`, '-', '%')
			INNER JOIN `user` AS `t2` ON `t2`.`id` = `t1`.`user_id`
		WHERE
			`t`.`user_id` = #{userId}
		AND 
			-- 仅仅检索指定的指定下线级别
			`t1`.`level` = `t`.`level` + #{level}
		ORDER BY 
			`level` ASC

	# 统计指定/所有下线级别总人数
		SELECT
			-- 下线级别
			`t1`.`level` - `t`.`level` AS `level`,

			-- 总人数
			COUNT(`t1`.`level`) AS `count`
		FROM
			`user_chain` AS `t`
			INNER JOIN `user_chain` AS `t1` ON `t1`.`chain` LIKE CONCAT(`t`.`chain`, '-', '%')
		WHERE
			`t`.`user_id` = #{userId}
		AND 
			-- 仅仅统计指定的指定下线级别
			`t1`.`level` <= `t`.`level` + #{maxLevel}
		GROUP 
			BY `t1`.`level`


------------------------------
闭包表(Closure Table)		  |
------------------------------
	# 闭包表的思路和物化路径差不多, 都是空间换时间

	# 数据表(user)
		id		int			主键
		name	varchar		名称
	
	# 关系表(user_tree)
		ancestor			父级id
		descendant			子级id
		distance			父级到子级的路径长度
		PRIMARY KEY (ancestor, descendant, distance)	
		
		* 每个用户, 都会在这个表里面存储N条记录来描述自己的位置信息, 一直溯源到最顶级节点
		* 这三个字段的组合是唯一的, 因为在树中, 一条路径可以标识一个节点, 所以可以直接把它们的组合作为主键
	
	
	# 添加数据
	

	# 查询指定节点下的直接节点

	# 查询指定节点下的所有节点

	# 查询指定区间

	# 查询指定节点所处的等级

	# 删除指定的节点


------------------------------
使用队列遍历
------------------------------
	class Node {
		private Integer id;
		private String name;
		private Integer parentId;
		private Collection<Node> childNodes;
	}

	public static List<Node> tree (Collection<Node> nodes, Integer parentId) {
		
		// 最顶级的节点
		List<Node> retVal = nodes.stream()
			.filter(node -> node.getParentId().equals(parentId))
			.collect(Collectors.toList());
		
		if (!retVal.isEmpty()) {
			// 使用队列遍历
			LinkedList<Node> queue = new LinkedList<Node>();
			
			queue.addAll(retVal);
			
			while (!queue.isEmpty()) {
				
				Node lastNode = queue.removeLast();
				
				// 检索子节点
				List<Node> childNodes = nodes.stream()
					.filter(node -> node.getParentId().equals(lastNode.getId()))
					.collect(Collectors.toList());
				
				if (!childNodes.isEmpty()) {
					lastNode.setChildNodes(childNodes);
					queue.addAll(childNodes);
				}
			}
		}
		return retVal;
	}


------------------------------
一次性加载到内存后，递归组合
------------------------------
type menuService struct {}

// Tree 检索系统中完整的菜单树
func (m menuService) Tree(ctx context.Context) (menus []*model.Menu, err error) {
	// 查询出所有的菜单记录
	data := make([]*model.Menu, 0)
	if err = dao.Tx(ctx).Select(&data, "SELECT `id`, `parent_id`, `name`, `type`, `icon`, `instruction` FROM `menu` ORDER BY `sort` DESC, `create_at` ASC"); err != nil {
		return
	}
	// 在内存中构建树结构
	menus = make([]*model.Menu, 0)
	// 顶级记录ID
	for _, v := range data {
		if v.ParentId.Equals(constant.TreeTopId) {
			menus = append(menus, v)
		}
	}
	// 递归检索子菜单
	for _, v := range menus {
		v.Sub = m.subMenus(v.Id, data)
	}
	return
}

// subMenus 根据id从menus里面找出这个id下的子记录
// 会进行递归查找
func (m menuService) subMenus(id t.ID, menus []*model.Menu) (sub []*model.Menu) {
	sub = make([]*model.Menu, 0)
	for _, v := range menus {
		if v.ParentId == id {
			sub = append(sub, v)
		}
	}
	for _, v := range sub {
		v.Sub = m.subMenus(v.Id, menus)
	}
	return
}

var Menu = &menuService{}
