-------------------
MySQL 高级查询		|
--------------------


-------------------
Exam-第一题			|
--------------------
	# 查询出至少有5个员工的部门,显示部门编号,部门名字,部门位置,部门人数
	# 列:d.deptno,d.dname,d.loc,部门人数
	# 表:dept d,emp e
	# 条件:e.deptno = d.deptno	
	
		SELECT
			d.deptno,
			d.dname,
			d.loc,
			e.count
		FROM
			dept d,
			(SELECT deptno,COUNT(*) `count`  FROM emp GROUP BY deptno HAVING COUNT(*) >= 5) e
		WHERE
			d.deptno = e.deptno
		

	# 查询出所有部门的信息,以及其部门的员工数
		SELECT
			d.deptno,
			d.dname,
			d.loc,
			ifnull(e.count,0) AS `count`
		FROM
			dept d
		LEFT JOIN
			(SELECT deptno,COUNT(*) `count`  FROM emp GROUP BY deptno) e
		ON
			d.deptno = e.deptno;
	
-------------------
Exam-第二题			|
--------------------
	# 获取所有员,及其直属上级的姓名
		SELECT
			t.ename AS `员工`,
			ifnull(t1.ename,'BOSS') AS `上级`
		FROM
			emp t
		LEFT JOIN
			emp t1 ON t.mgr = t1.empno;

	# 获取,所有员工里面有直属上级的员工及其领导
		SELECT
			e.ename as `员工`,
			m.ename as `上级`
		FROM
			emp e,
			emp m
		WHERE
			e.mgr = m.empno;
		
	

-------------------
Exam-第三题			|
--------------------
-------------------
Exam-第四题			|
--------------------
	# 列出受雇日期早于直接上级的所有员工的编号,姓名,部门名称
		SELECT
			t.ename,
			t2.dname
		FROM 
			emp t,
			emp t1,
			dept t2
		WHERE 
			t.mgr = t1.empno
		AND
			t.hiredate < t1.hiredate
		AND
			t.deptno = t2.deptno;

-------------------
Exam-第五题			|
--------------------
	# 列出部门名称,和这些部门的员工信息,同时列出那些没有员工的部门
		SELECT
			t.dname AS `部门`,
			ifnull(e.ename,'没有部门') AS `员工`
		FROM
			dept t
		LEFT JOIN
			emp e ON t.deptno = e.deptno;

-------------------
Exam-第六题			|
--------------------
	
-------------------
Exam-第七题			|
--------------------
	# 列出最低薪资大于15000的各种工作,以及从事此工作的人数
		SELECT 
			job AS `工作`,
			count(*) AS `人数`
		FROM
			emp
		GROUP BY job HAVING min(sal) >= 15000;

-------------------
Exam-第八题			|
--------------------
	# 列出在销售部工作的员工姓名,假定不知道销售部的部门编号
		SELECT 
			e.ename
		FROM
			emp e,
			dept d
		WHERE
			d.dname = '销售部'
		AND
			e.deptno = d.deptno;
	
-------------------
Exam-第九题			|
--------------------
-------------------
	# 列出薪金高于公司平均薪金的员工信息,所在部门名称,上级领导,薪资等级
	SELECT
	  e.ename AS `员工`,
	  d.dname AS `部门`,
	  m.ename AS `上级`,
	  s.grade AS `薪资等级`
	FROM
		emp e 
	LEFT JOIN 
		dept d ON d.deptno = e.deptno
	LEFT JOIN 
		emp m ON e.mgr = m.empno
	LEFT JOIN 
		salgrade s ON e.sal BETWEEN  s.losal AND s.hisal
	WHERE
		e.sal > (SELECT avg(sal) FROM emp);

--------------------
Exam-第十题			|
--------------------
	# 列出与庞统从事相同工作的所有员工及部门名称
	SELECT
		e.ename AS `员工`,
		d.dname AS `部门`
	FROM 
		emp e,
		dept d
	WHERE
		e.job = (SELECT job FROM emp WHERE ename = '庞统')
	AND
		e.deptno = d.deptno;

-------------------
Exam-第十一题		|
--------------------
	# 列出薪金高于,在30部门工作的所有员工的薪资.
		SELECT
			e.ename,
			e.sal,
			d.dname
		FROM
			emp e,
			dept d
		WHERE
			e.deptno = d.deptno
		AND
			e.sal > ALL (SELECT sal FROM emp WHERE deptno = 30);
-------------------
Exam-第十二题		|
--------------------
	
-------------------
Exam-第十三题		|
--------------------
-------------------
Exam-第十四题		|
--------------------
-------------------
Exam-第十五题		|
--------------------
-------------------
Exam-第十六题		|
--------------------