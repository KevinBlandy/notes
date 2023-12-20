
---------------------------------------
笛卡尔积计算
---------------------------------------

	# 场景就是，商品有多个属性，每个属性组合都是一个 SKU，需要根据已知属性计算出所有 SKU。

	import java.util.ArrayList;
	import java.util.List;

	public class Main {

		public static void main(String[] args) throws Exception {

			List<List<String>> ret = cartesian(
				List.of(List.of("红", "白", "蓝"),	// 颜色
				List.of("大", "中", "小"), 			// 尺寸
				List.of("钢", "铁", "铝"),			// 材料
				List.of("X", "L", "M")				// 规格
			));

			ret.stream().forEach(System.out::println);
		}

		public static List<List<String>> cartesian(List<List<String>> items) {
			List<List<String>> ret = new ArrayList<>();
			forEach(items, ret, new ArrayList<String>(), 0);
			return ret;
		}

		/**
		 * 递归遍历
		 * @param items
		 * @param container
		 * @param line
		 * @param depth
		 */
		public static void forEach(List<List<String>> items, List<List<String>> container, List<String> line, int depth) {
			if (items.size() == depth) {
				// 一轮遍历到底了
				container.add(new ArrayList<>(line));
				return;
			}

			List<String> current = items.get(depth);
			for (String item : current) {
				line.add(item);
				forEach(items, container, line, depth + 1);
				line.remove(line.size() - 1);
			}
		}
	}
