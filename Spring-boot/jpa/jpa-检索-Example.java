------------------
Example
------------------
	# Example api的组成
		Example				由Probe和ExampleMatcher组成，用于查询。
		Probe				含有对应字段的实例对象
		ExampleMatcher		ExampleMatcher携带有关如何匹配特定字段的详细信息,相当于匹配条件

	# 限制
		* 属性不支持嵌套或者分组约束,比如这样的查询 firstname = ? 0 or (firstname = ? 1 and lastname = ? 2)
		* 灵活匹配只支持字符串类型，其他类型只支持精确匹配

		 // 匹配所有非 null 字段
        ExampleMatcher.matching();
        // 同上
        ExampleMatcher.matchingAll();
        // 匹配任何非 null 字段
        ExampleMatcher.matchingAny();

		// matcher属性名是对象的属性名称(驼峰),而不是DB列名
        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                //模糊查询匹配开头，即{username}%
                .withMatcher("username", ExampleMatcher.GenericPropertyMatchers.startsWith())
                //模糊查询匹配结尾，即%{username}
                .withMatcher("userName", ExampleMatcher.GenericPropertyMatchers.endsWith())
                //全部模糊查询，即%{address}%
                .withMatcher("address" ,ExampleMatcher.GenericPropertyMatchers.contains())

                //忽略字段，即不管password是什么值都不加入查询条件
                .withIgnorePaths("password");

        Example<UserDTO> userDTOExample = Example.of(userDTO,exampleMatcher);