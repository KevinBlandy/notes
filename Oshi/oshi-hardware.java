------------------------
Hardware
------------------------
	HardwareAbstractionLayer hardware = systemInfo.getHardware();

		ComputerSystem getComputerSystem();		// 系统
		CentralProcessor getProcessor();		// CPU
		GlobalMemory getMemory();				// 内存
		PowerSource[] getPowerSources();		// 电源信息
		HWDiskStore[] getDiskStores();			// 磁盘
		NetworkIF[] getNetworkIFs();			// 网卡
		Sensors getSensors();					// 传感器
		UsbDevice[] getUsbDevices(boolean tree);// USB设备， tree， 表示是否按照树形来组织返回结果
		SoundCard[] getSoundCards();			// 声卡 
	

	ComputerSystem 电脑系统
		String getManufacturer();		// 制造商 （HP）
		String getModel();				// 计算机系统类型 （OMEN by HP Laptop 17-an0xx）
		String getSerialNumber();		// 序列号
		Firmware getFirmware();			// 固件信息
		Baseboard getBaseboard();		// 主板信息
	
	Firmware 固件信息（BIOS）
		String getManufacturer();		// 制造商
		String getName();				// 名称
		String getDescription();		// 描述
		String getVersion();			// 版本号
		String getReleaseDate();		// 出厂日期
	
	Baseboard 主板信息（BIOS）
		String getManufacturer();		// 制造商
		String getModel();				// 主板类型
		String getVersion();			// 版本号
		String getSerialNumber();		// 序列化
	
	
	
		