这东西吧,勉强算是一个框架 !

	用于后台页面的布局

	1,创建框架页面
			<frameset rows="15%,*">
				<frame src="顶部页面" name="">
				<frameset cols="15%,*">
					<frame src="左边页面" name="">
					<frame src="右边页面" name="">
				</frameset>
			</frameset>

			* 注意,没有body标签!
		
