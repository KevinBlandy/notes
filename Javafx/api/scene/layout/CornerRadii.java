------------------------
CornerRadii
------------------------
	# public class CornerRadii

	# 构造函数
		public CornerRadii(@NamedArg("radius") double radius)
		public CornerRadii(@NamedArg("radius") double radius, @NamedArg("asPercent") boolean asPercent)
		public CornerRadii(@NamedArg("topLeft") double topLeft, @NamedArg("topRight") double topRight, @NamedArg("bottomRight") double bottomRight, @NamedArg("bottomLeft") double bottomLeft, @NamedArg("asPercent") boolean asPercent)
		public CornerRadii(@NamedArg("topLeftHorizontalRadius") double topLeftHorizontalRadius, @NamedArg("topLeftVerticalRadius") double topLeftVerticalRadius, @NamedArg("topRightVerticalRadius") double topRightVerticalRadius, @NamedArg("topRightHorizontalRadius") double topRightHorizontalRadius,
						@NamedArg("bottomRightHorizontalRadius") double bottomRightHorizontalRadius, @NamedArg("bottomRightVerticalRadius") double bottomRightVerticalRadius, @NamedArg("bottomLeftVerticalRadius") double bottomLeftVerticalRadius, @NamedArg("bottomLeftHorizontalRadius") double bottomLeftHorizontalRadius,
						@NamedArg("topLeftHorizontalRadiusAsPercent") boolean topLeftHorizontalRadiusAsPercent, @NamedArg("topLeftVerticalRadiusAsPercent") boolean topLeftVerticalRadiusAsPercent, @NamedArg("topRightVerticalRadiusAsPercent") boolean topRightVerticalRadiusAsPercent,
						@NamedArg("topRightHorizontalRadiusAsPercent") boolean topRightHorizontalRadiusAsPercent, @NamedArg("bottomRightHorizontalRadiusAsPercent") boolean bottomRightHorizontalRadiusAsPercent, @NamedArg("bottomRightVerticalRadiusAsPercent") boolean bottomRightVerticalRadiusAsPercent,
						@NamedArg("bottomLeftVerticalRadiusAsPercent") boolean bottomLeftVerticalRadiusAsPercent, @NamedArg("bottomLeftHorizontalRadiusAsPercent") boolean bottomLeftHorizontalRadiusAsPercent)
	
	# 静态属性
		 public static final CornerRadii EMPTY = new CornerRadii( 0, 0, 0, 0, 0, 0, 0, 0, false, false, false, false, false, false, false, false);
		

