class ListNode {
	int val;
	ListNode next;

	ListNode(int val) {
		this.val = val;
	}
}

/**
 * 
 * 删除节点中的指定值的所有元素
 * 
 * @author KevinBlandy
 *
 */
public class Solution01 {

	// 普通实现
	public ListNode removeElement(ListNode head, int val) {
		while (head != null && head.val == val) {
			// 处理头节点
			ListNode delNode = head;
			head = head.next;
			delNode.next = null;
		}
		if (head == null) {
			return head;
		}
		ListNode prev = head;
		while (prev.next != null) {
			// 处理中间元素
			if (prev.next.val == val) {
				prev.next = prev.next.next;
			} else {
				prev = prev.next;
			}
		}

		return head;			//返回的是移除节点后的头节点
	}

	// 使用虚拟头节点,解决方式更加的简洁
	public ListNode removeElement1(ListNode head, int val) {
		// 创建虚拟头节点
		ListNode dummyHead = new ListNode(-1);
		dummyHead.next = head;

		ListNode prev = dummyHead;
		while (prev.next != null) {
			// 处理中间元素
			if (prev.next.val == val) {
				prev.next = prev.next.next;
			} else {
				prev = prev.next;
			}
		}

		return dummyHead.next;		//返回的是移除节点后的头节点
	}

	// 使用递归实现
	public ListNode removeElement2(ListNode head, int val) {
		if (head == null) {
			return null;
		}
		head.next = removeElement2(head.next, val);
		return head.val == val ? head.next : head;
	}
}
