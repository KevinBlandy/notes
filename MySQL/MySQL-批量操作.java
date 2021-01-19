------------------------
批量更新操作			|
------------------------
	UPDATE `test` 
	SET `name` =
	CASE
		`id` 
		WHEN 1 THEN
		'@1' 
		WHEN 2 THEN
		'@2' 
	END 
	WHERE
		`id` IN ( 1, 2 )