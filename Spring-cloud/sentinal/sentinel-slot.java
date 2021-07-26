---------------------------
Slot
---------------------------
	# Slot ������һ��Filter�����������������ִ����
		* �� Sentinel ���棬���е���Դ����Ӧһ����Դ���ƣ�resourceName����ÿ����Դ���ö��ᴴ��һ�� Entry ����
		* Entry ������ʱ��ͬʱҲ�ᴴ��һϵ�й��ܲ�ۣ�slot chain������Щ����в�ͬ��ְ��

	# Slot �Ľӿ���ϵ
		ProcessorSlot<T>
			|-AbstractLinkedProcessorSlot<T> 
		

		* Sentinel �� ProcessorSlot ��Ϊ SPI �ӿڽ�����չ��1.7.2 �汾��ǰ SlotChainBuilder ��Ϊ SPI��

	# ϵͳԤ�����
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