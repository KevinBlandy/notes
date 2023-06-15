------------------------------
�ﻯ·��					  |
------------------------------
	# ʹ��һ�ű�������ϵ

	# ���ݱ�(user)
		id			int			����
		parent_id	int			����id
		name		varchar		����
	
	# ��ϵ��(user_chain)
		id		int		�û�id
		depth	int		�����ĵȼ�
		chain	text	��ϵ��
		
		PRIMARY KEY (`id`)
		UNION KEY(`path`)

		* chain �洢��ϵ���б�, ʹ������ָ���: | - / 
		* �����ϵ����, chain �ֶλ�ܳ�
	

	# �������
		* ��Ҫ��˼�����, �Ѹ����û���chain�����Լ���id�洢�ڵ�ǰ�û��ļ�¼��, �γ�һ�������Ĺ�ϵ��
	
	# ��ѯָ���ڵ��µ�����/ֱ�ӽڵ�
		SELECT
			-- ���߼���
			`t1`.`level` - `t`.`level` AS `level`,
			-- �����û���Ϣ
			`t2`.*,
		FROM
			`user_chain` AS `t`
			INNER JOIN `user_chain` AS `t1` ON `t1`.`chain` LIKE CONCAT(`t`.`chain`, '-', '%')
			INNER JOIN `user` AS `t2` ON `t2`.`id` = `t1`.`user_id`
		WHERE
			`t`.`user_id` = #{userId}
		AND 
			-- ��������ָ����ָ�����߼���
			`t1`.`level` = `t`.`level` + #{level}
		ORDER BY 
			`level` ASC

	# ͳ��ָ��/�������߼���������
		SELECT
			-- ���߼���
			`t1`.`level` - `t`.`level` AS `level`,

			-- ������
			COUNT(`t1`.`level`) AS `count`
		FROM
			`user_chain` AS `t`
			INNER JOIN `user_chain` AS `t1` ON `t1`.`chain` LIKE CONCAT(`t`.`chain`, '-', '%')
		WHERE
			`t`.`user_id` = #{userId}
		AND 
			-- ����ͳ��ָ����ָ�����߼���
			`t1`.`level` <= `t`.`level` + #{maxLevel}
		GROUP 
			BY `t1`.`level`


------------------------------
�հ���(Closure Table)		  |
------------------------------
	# �հ����˼·���ﻯ·�����, ���ǿռ任ʱ��

	# ���ݱ�(user)
		id		int			����
		name	varchar		����
	
	# ��ϵ��(user_tree)
		ancestor			����id
		descendant			�Ӽ�id
		distance			�������Ӽ���·������
		PRIMARY KEY (ancestor, descendant, distance)	
		
		* ÿ���û�, ���������������洢N����¼�������Լ���λ����Ϣ, һֱ��Դ������ڵ�
		* �������ֶε������Ψһ��, ��Ϊ������, һ��·�����Ա�ʶһ���ڵ�, ���Կ���ֱ�Ӱ����ǵ������Ϊ����
	
	
	# �������
	

	# ��ѯָ���ڵ��µ�ֱ�ӽڵ�

	# ��ѯָ���ڵ��µ����нڵ�

	# ��ѯָ������

	# ��ѯָ���ڵ������ĵȼ�

	# ɾ��ָ���Ľڵ�


------------------------------
ʹ�ö��б���
------------------------------
	class Node {
		private Integer id;
		private String name;
		private Integer parentId;
		private Collection<Node> childNodes;
	}

	public static List<Node> tree (Collection<Node> nodes, Integer parentId) {
		
		// ����Ľڵ�
		List<Node> retVal = nodes.stream()
			.filter(node -> node.getParentId().equals(parentId))
			.collect(Collectors.toList());
		
		if (!retVal.isEmpty()) {
			// ʹ�ö��б���
			LinkedList<Node> queue = new LinkedList<Node>();
			
			queue.addAll(retVal);
			
			while (!queue.isEmpty()) {
				
				Node lastNode = queue.removeLast();
				
				// �����ӽڵ�
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
һ���Լ��ص��ڴ�󣬵ݹ����
------------------------------
type menuService struct {}

// Tree ����ϵͳ�������Ĳ˵���
func (m menuService) Tree(ctx context.Context) (menus []*model.Menu, err error) {
	// ��ѯ�����еĲ˵���¼
	data := make([]*model.Menu, 0)
	if err = dao.Tx(ctx).Select(&data, "SELECT `id`, `parent_id`, `name`, `type`, `icon`, `instruction` FROM `menu` ORDER BY `sort` DESC, `create_at` ASC"); err != nil {
		return
	}
	// ���ڴ��й������ṹ
	menus = make([]*model.Menu, 0)
	// ������¼ID
	for _, v := range data {
		if v.ParentId.Equals(constant.TreeTopId) {
			menus = append(menus, v)
		}
	}
	// �ݹ�����Ӳ˵�
	for _, v := range menus {
		v.Sub = m.subMenus(v.Id, data)
	}
	return
}

// subMenus ����id��menus�����ҳ����id�µ��Ӽ�¼
// ����еݹ����
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
