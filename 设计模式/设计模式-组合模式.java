----------------------------
组合模式					|
----------------------------
	* 在处理树形结构的时候,就可以使用组合模式
	* 组合模式的核心
		* 抽象构件(Component),定义叶子节点和容器节点的共同点
		* 叶子(Leaf),无子节点
		* 容器(Composite),包含了子节点

----------------------------
demo						|
----------------------------
import java.util.ArrayList;
import java.util.List;

//公共组件
public interface Component {
	//叶子,容器节点都具备的方法
	void operation();
}

//叶子组件
interface Leaf extends Component{
	
}


//图片文件
class ImgFile implements Leaf{
	public void operation() {
		System.out.println("图片文件");
	}
}
//文本文件
class TextFile implements Leaf{
	//叶子操作的实现
	public void operation() {
		System.out.println("文本文件");
	}
}

//文件夹
class Folder implements Component{
	
	private List<Component> subFiles = new ArrayList<>();
	
	//添加叶子
	public void add(Component component) {
		this.subFiles.add(component);
	}
	
	//删除叶子
	public void remove(Component component) {
		this.subFiles.remove(component);
	}

	/**
	 * 
	 * 容器操作的实现,就是遍历叶子,然后调用叶子的操作
	 * 如果叶子也是一个容器,就是一个天然的递归了
	 * 
	 */
	@Override
	public void operation() {
		for (Component component : subFiles) {
			component.operation();
		}
	}
}