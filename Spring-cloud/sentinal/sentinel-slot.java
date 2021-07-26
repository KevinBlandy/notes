---------------------------
Slot
---------------------------
	# Slot 类似于一个Filter，它们组成了完整的执行链
		* 在 Sentinel 里面，所有的资源都对应一个资源名称（resourceName），每次资源调用都会创建一个 Entry 对象
		* Entry 创建的时候，同时也会创建一系列功能插槽（slot chain），这些插槽有不同的职责

	# Slot 的接口体系
		ProcessorSlot<T>
			|-AbstractLinkedProcessorSlot<T> 
		

		* Sentinel 将 ProcessorSlot 作为 SPI 接口进行扩展（1.7.2 版本以前 SlotChainBuilder 作为 SPI）

	# 系统预定义的
		@SpiOrder(-10000)
		public class NodeSelectorSlot extends AbstractLinkedProcessorSlot<Object> 

		@SpiOrder(-9000)
		public class ClusterBuilderSlot extends AbstractLinkedProcessorSlot<DefaultNode>

		@SpiOrder(-7000)
		public class StatisticSlot extends AbstractLinkedProcessorSlot<DefaultNode> 

		@SpiOrder(-2000)
		public class FlowSlot extends AbstractLinkedProcessorSlot<DefaultNode>

		@SpiOrder(-6000)
		public class AuthoritySlot extends AbstractLinkedProcessorSlot<DefaultNode>

		@SpiOrder(-1000)
		public class DegradeSlot extends AbstractLinkedProcessorSlot<DefaultNode> 

		@SpiOrder(-5000)
		public class SystemSlot extends AbstractLinkedProcessorSlot<DefaultNode> 

---------------------------
ProcessorSlot
---------------------------
	public interface ProcessorSlot<T> {
		void entry(Context context, ResourceWrapper resourceWrapper, T param, int count, boolean prioritized, Object... args) throws Throwable;
		void fireEntry(Context context, ResourceWrapper resourceWrapper, Object obj, int count, boolean prioritized, Object... args) throws Throwable;
		void exit(Context context, ResourceWrapper resourceWrapper, int count, Object... args);
		void fireExit(Context context, ResourceWrapper resourceWrapper, int count, Object... args);
	}