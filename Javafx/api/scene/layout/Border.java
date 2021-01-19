----------------------------------
Border
----------------------------------
	# public final class Border
	
	# 构造方法
		public Border(@NamedArg("images") BorderImage... images)
		public Border(@NamedArg("strokes") BorderStroke... strokes)
		public Border(@NamedArg("strokes") BorderStroke[] strokes, @NamedArg("images") BorderImage[] images)
		public Border(@NamedArg("strokes") List<BorderStroke> strokes, @NamedArg("images") List<BorderImage> images)


	# 静态属性
		public static final Border EMPTY = new Border((BorderStroke[])null, null);
	