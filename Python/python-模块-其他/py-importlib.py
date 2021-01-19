------------------
importlib		  |
------------------
	importlib.metadata 
		* 提供了从第三方包读取元数据的(临时)支持
		* 例如, 它可以提取一个已安装软件包的版本号, 入口点列表等等:
			from importlib.metadata import version, requires, files
			print(version('requests'))
			print(list(requires('requests')))
			print(list(files('requests'))[:5])

			2.22.0
			['chardet (<3.1.0,>=3.0.2)', 'idna (<2.9,>=2.5)', 'urllib3 (!=1.25.0,!=1.25.1,<1.26,>=1.21.1)', 'certifi (>=2017.4.17)', "pyOpenSSL (>=0.14) ; extra == 'security'", "cryptography (>=1.3.4) ; extra == 'security'", "idna (>=2.0.0) ; extra =='security'", "PySocks (!=1.5.7,>=1.5.6) ; extra == 'socks'", 'win-inet-pton ; (sys_platform == "win32" and python_version == "2.7") and extra == \'socks\'']
			[PackagePath('requests-2.22.0.dist-info/INSTALLER'), PackagePath('requests-2.22.0.dist-info/LICENSE'), PackagePath('requests-2.22.0.dist-info/METADATA'), PackagePath('requests-2.22.0.dist-info/RECORD'), PackagePath('requests-2.22.0.dist-inf