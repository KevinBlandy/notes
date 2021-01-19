-----------------------
BeanInfo			   |
-----------------------
	# 描述一个Java Bean的接口
		
		BeanDescriptor getBeanDescriptor();

		EventSetDescriptor[] getEventSetDescriptors();

		int getDefaultEventIndex();
		PropertyDescriptor[] getPropertyDescriptors();

		int getDefaultPropertyIndex();
		MethodDescriptor[] getMethodDescriptors();
		BeanInfo[] getAdditionalBeanInfo();

		Image getIcon(int iconKind);

		final static int ICON_COLOR_16x16 = 1;

		final static int ICON_COLOR_32x32 = 2;

		final static int ICON_MONO_16x16 = 3;

		final static int ICON_MONO_32x32 = 4;