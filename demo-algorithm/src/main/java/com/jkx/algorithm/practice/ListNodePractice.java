package com.jkx.algorithm.practice;

import java.util.Arrays;
import java.util.Random;
import java.util.function.Function;

/**
 * @author jkx
 * @date 2023/3/2
 */
public class ListNodePractice {

    public static void main(String[] args) {
        ListNodePractice obj = new ListNodePractice();
        obj.reverseListTest();
        obj.reversePrintListTest();
        obj.findListMiddleTest();
        obj.findReverseKTest();
        obj.removeReverseKTest();
        obj.mergeTwoListsTest();
        obj.mergeKListsTest();
    }

    /**
     * 分隔链表
     * 对链表进行节点挪移处理，使得小于指定数的所有节点都挪到大于等于该数的节点左边，要求不改变其余节点的相对顺序
     *
     * 时间复杂度为o(n)，空间复杂度为o(1)
     * leetcode测试已通过
     *
     * @param head
     * @param x
     * @return
     */
    public Node partition(Node head, int x) {
        // 使用拆分链表的技巧，HashMap扩容过程中迁移数据的源码中就用了这种技巧
        Node leftHead = null, leftTail = null, rightHead = null, rightTail = null;
        Node cur = head;
        while (cur != null) {
            if (cur.val < x) {
                if (leftTail == null) {
                    leftHead = cur;
                } else {
                    leftTail.next = cur;
                }
                leftTail = cur;
            } else {
                if (rightTail == null) {
                    rightHead = cur;
                } else {
                    rightTail.next = cur;
                }
                rightTail = cur;
            }
            cur = cur.next;
        }
        if (leftHead != null) {
            leftTail.next = rightHead;
            // 避免形成环状结构
            if (rightHead != null) {
                rightTail.next = null;
            }
            return leftHead;
        }
        return rightHead;
    }

    /**
     * 求两个链表的相交节点，不存在就返回null
     * 假设两链表相交，公共部分有c个节点，链表A共a个节点，链表b共b个节点
     * 那么链表A私有的节点有a-c个，链表B则有b-c个，这两者无法保证相等
     * 但若是让两个指针分别从链表A、B的起点同时同速前行，到尾部后跳转至另一个链表的头结点继续前进，就可以保证相遇于相交节点
     * 因为从逻辑上来说，从链表A开始前行，走到末尾回到链表B头部继续前行至相交节点处，要走a+(b-c)步
     * 同样的，对于从链表B开始，则是b+(a-c)步，发现两者是一样的，都是a+b-c步
     * 所以只要按照这套走法，就能求得两个链表的相交节点，就是第一次相交的时候
     * 如果两链表不相交，按这种走法，最终都会位于空指针处，也是第一次相交，所以不用编写额外的处理逻辑
     *
     * 时间复杂度为o(n)，空间复杂度为o(1)
     * leetcode测试已通过
     *
     * @param headA 链表A的头结点
     * @param headB 链表B的头结点
     * @return
     */
    public Node getIntersectionNode(Node headA, Node headB) {
        Node pointerA = headA, pointerB = headB;
        while (pointerA != pointerB) {
            // 两指针同时同速前行，到达尾部后跳转至另一个链表的头结点开始前行，直到第一次相交为止
            // 若存在公共节点，则相交时必定位于非空节点处，若不存在，相交时必定位于空节点处
            pointerA = (pointerA != null ? pointerA.next : headB);
            pointerB = (pointerB != null ? pointerB.next : headA);
        }
        return pointerA;
    }

    /**
     * 将多个升序链表合并为一个新的升序链表
     * 时间复杂度为o(n^2)，空间复杂度为o(1)
     *
     * @param lists 升序链表列表
     * @return
     */
    public Node mergeKLists(Node[] lists) {
        if (lists == null || lists.length == 0) {
            return null;
        }
        Node newList = lists[0];
        for (int i = 1; i < lists.length; i++) {
            newList = mergeTwoLists(newList, lists[i]);
        }
        return newList;
    }

    private Node mergeKListsTest() {
        System.out.println("*********测试合并k个升序链表*********");
        Random random = new Random();
        Node[] lists = new Node[1 + random.nextInt(8)];
        for (int i = 0; i < lists.length; i++) {
            lists[i] = createSortListNode(1 + random.nextInt(8));
            System.out.println(String.format("链表%d：%s", i + 1, lists[i].getListStr()));
        }
        Node newList = mergeKLists(lists);
        System.out.println("结果：" + newList.getListStr());
        System.out.println("");
        return null;
    }

    /**
     * 将两个升序链表合并为一个新的升序链表
     * 时间复杂度为o(n)，空间复杂度为o(1)
     *
     * @param list1
     * @param list2
     * @return
     */
    public Node mergeTwoLists(Node list1, Node list2) {
        Node virtualHead = new Node(0);
        Node cur = virtualHead;
        // 比较链表的剩余节点，将较小的拼接到新链表上
        while (list1 != null && list2 != null) {
            if (list1.val < list2.val) {
                cur.next = list1;
                list1 = list1.next;
            } else {
                cur.next = list2;
                list2 = list2.next;
            }
            cur = cur.next;
        }
        // 合并剩余的链表到新链表上
        cur.next = (list1 != null ? list1 : list2);
        return virtualHead.next;
    }

    private Node mergeTwoListsTest() {
        System.out.println("*********测试合并两个升序链表*********");
        Random random = new Random();
        int length1 = 1 + random.nextInt(10);
        int length2 = 1 + random.nextInt(10);
        Node list1 = createSortListNode(length1);
        Node list2 = createSortListNode(length2);
        System.out.println(String.format("链表1：%s\n链表2：%s", list1.getListStr(), list2.getListStr()));
        Node newList = mergeTwoLists(list1, list2);
        System.out.println("结果：" + newList.getListStr());
        System.out.println("");
        return null;
    }

    /**
     * 确定链表是否存在环，存在就返回入环的第一个节点，否则返回null
     * 设置快慢两根指针，快指针的前行速度是慢指针的两倍
     * 两指针同时出发，若存在环，两者在某一时刻必定相遇，不存在环的话，快指针必定会遇到空指针
     * 假设两环相遇时，慢指针走了m步，则快指针走了2m步，假设从链表起点到环起点要走a步，从环起点走回到环起点要走b步
     * 那么存在关系m=kb, k为正整数，也就是慢指针此时走了kb步
     * 另外还存在一个数学关系，即一个指针从链表起点走到环起点，再走kb步必定回到环起点，也就是走a步与走（a+kb）步必定相交于环起点
     * 现让快指针回到链表起点，并让两指针同时同速前行a步，两指针必定相遇，这时快指针位于环的起点
     *
     * @param head 头结点
     * @return
     */
    public Node detectCycle(Node head) {
        // 先确定是否存在环
        Node fast = head, slow = head;
        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
            if (fast == slow) {
                break;
            }
        }
        // 排除没有环的情况
        if (fast == null || fast.next == null) {
            return null;
        }
        fast = head;
        while (fast != slow) {
            fast = fast.next;
            slow = slow.next;
        }
        return fast;
    }

    /**
     * 判断链表是否有环
     * 设置快慢两根指针，快指针的前行速度是慢指针的两倍
     * 两指针同时出发，若存在环，两者在某一时刻必定相遇，不存在环的话，快指针必定会遇到空指针
     *
     * @param head 头结点
     * @return
     */
    public boolean hasCycle(Node head) {
        Node fast = head, slow = head;
        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
            if (fast == slow) {
                return true;
            }
        }
        return false;
    }

    /**
     * 移除链表倒数第k个节点
     * 与寻找链表倒数第k个节点思路类似，只是边界情况的处理要更复杂些
     *
     * 下面讨论快指针停止移动时的边界情况
     * 1、慢指针位于中间位置，需要记录下结束时慢指针的前一个节点，然后执行移除操作
     * 2、慢指针位于尾节点处，仅当k=1时出现，此时按照1的方式处理
     * 3、慢指针位于头结点处，仅当k=链表长度时出现，这时返回第二个节点即可
     * 另外，当链表长度为1时，会出现返回值为null的情况
     *
     * @param head 头结点
     * @param k    要移除的倒数位置
     * @return
     */
    public Node removeReverseK(Node head, int k) {
        if (k <= 0) {
            throw new IllegalArgumentException("k必须大于0");
        }
        Node slow = head, fast = head, pre = null;
        // 先走k步
        for (int i = 0; i < k; i++) {
            if (fast == null) {
                throw new IllegalArgumentException("k超过链表长度");
            }
            fast = fast.next;
        }
        // 同时同速前行，直到快指针走到尾节点处，同时记录慢指针的前一个位置
        while (fast != null) {
            pre = slow;
            fast = fast.next;
            slow = slow.next;
        }
        // 处理慢指针位于头结点时的情况
        if (slow == head) {
            Node second = head.next;
            // 断开引用
            head.next = null;
            return second;
        }
        // 处理慢指针位于中间位置的情况
        pre.next = pre.next.next;
        slow.next = null;
        return head;
    }

    private void removeReverseKTest() {
        System.out.println("*********测试移除链表倒数第k个节点*********");
        Random random = new Random();
        int length = 1 + random.nextInt(10);
        int k = length > 1 ? random.nextInt(length) + 1 : 1; // [1, length]
        Node head = createListNode(length);
        System.out.println(String.format("链表：%s，要移除倒数第%d个节点", head.getListStr(), k));
        Node newHead = removeReverseK(head, k);
        String str = newHead != null ? newHead.getListStr() : "null";
        System.out.println("结果：" + str);
        System.out.println("");
    }

    /**
     * 寻找链表倒数第k个节点
     * 倒数第k个节点即第n-k+1个节点，从头结点走到该位置需要走n-k步
     * 设置快慢指针，初始均指向头结点
     * 快指针先行k步，然后两指针同时先行，知道快指针到达空节点处
     * 此时慢指针走了n-k步，刚好到达倒数第k个节点
     * <p>
     * 这题和找中点考点一样，都是考能不能发现其中的数学关系，发现不了的话，感觉就只能多遍历一次求解了
     *
     * @param head 头结点
     * @param k    要找的倒数位置
     * @return
     */
    public Node findReverseK(Node head, int k) {
        if (k <= 0) {
            throw new IllegalArgumentException("k必须大于0");
        }
        Node slow = head, fast = head;
        // 先走k步
        for (int i = 0; i < k; i++) {
            if (fast == null) {
                throw new IllegalArgumentException("k超过链表长度");
            }
            fast = fast.next;
        }
        // 同时同速前行，直到快指针走到空节点处
        while (fast != null) {
            fast = fast.next;
            slow = slow.next;
        }
        return slow;
    }

    private void findReverseKTest() {
        System.out.println("*********测试寻找链表倒数第k个节点*********");
        Random random = new Random();
        int length = 1 + random.nextInt(10);
        int k = length > 1 ? random.nextInt(length) + 1 : 1; // [1, length]
        Node head = createListNode(length);
        System.out.println(String.format("链表：%s，要找倒数第%d个节点", head.getListStr(), k));
        Node listMiddle = findReverseK(head, k);
        System.out.println("结果：" + listMiddle.val);
        System.out.println("");
    }

    /**
     * 寻找链表的中间节点
     * 记链表长度为l，若l为奇数，则中间节点位置为(l+1)/2，若l为偶数，则中间节点位置为(l/2+1)
     * 思路是使用快慢指针，每前进行一次，快指针走两步，慢指针走一步，假设前进了n次，慢指针就走到第(n+1)个位置
     * 只要链表长度大于1，两个指针至少能前进一次
     * 其中长度为奇数时，前进停止时快指针走到最后一个节点，这时走了(l-1)/2轮，那么慢指针位于第(l+1)/2个节点，为中间节点
     * 其中长度为偶数时，前进停止时快指针走到空节点，这时走了l/2轮，那么慢指针位于第(l/2+1)个节点，为中间节点
     * <p>
     * 假如长度为偶数时，记l/2处为中间节点，那么只需少前进一轮即可，也就是让快指针走到倒数第二个节点结束
     * 需要在while()的判断中再加上 && fast.next.next != null，
     *
     * @param head
     * @return
     */
    public Node findListMiddle(Node head) {
        Node slow = head, fast = head;
        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
        }
        return slow;
    }

    private void findListMiddleTest() {
        System.out.println("*********测试寻找链表的中间节点*********");
        Node head = createListNode(1 + new Random().nextInt(8));
        System.out.println("链表：" + head.getListStr());
        Node listMiddle = findListMiddle(head);
        System.out.println("结果：" + listMiddle.val);
        System.out.println("");
    }

    /**
     * 反向打印链表
     * 递归写法，时间复杂度为o(n)，空间复杂度为o(n)
     *
     * @param head 链表头结点
     */
    public void reversePrintList(Node head) {
        // 递归出口
        if (head == null) {
            return;
        }
        reversePrintList(head.next);
        System.out.print(head.val + "  ");
    }

    private void reversePrintListTest() {
        System.out.println("*********测试反向打印链表*********");
        Node head = createListNode(10);
        System.out.println("链表：" + head.getListStr());
        System.out.print("结果：");
        reversePrintList(head);
        System.out.println("\n");
    }

    /**
     * 反转链表
     * 循环写法，时间复杂度为o(n)，空间复杂度为o(1)
     *
     * @param head 链表头结点
     */
    public Node reverseList(Node head) {
        if (head == null) {
            throw new NullPointerException();
        }

        Node pre = null, cur = head;
        while (cur != null) {
            Node node = cur.next;
            cur.next = pre;
            pre = cur;
            cur = node;
        }

        return pre;
    }

//    /**
//     * 反转链表
//     * 递归写法，时间复杂度为o(n)，空间复杂度为o(n)
//     * @param head 链表头结点
//     */
//    public Node reverseList(Node head) {
//        if (head == null) {
//            throw new NullPointerException();
//        }
//        // 递归出口
//        if (head.next == null) {
//            return head;
//        }
//        // 递推关系式
//        Node newHead = reverse(head.next);
//        head.next.next = head;
//        head.next = null;
//        // 返回结果
//        return newHead;
//    }

    private void reverseListTest() {
        System.out.println("*********测试反转链表*********");
        Node head = createListNode(10);
        System.out.println("链表：" + head.getListStr());
        Node reverse = reverseList(head);
        System.out.println("结果：" + reverse.getListStr());
        System.out.println("");
    }

    /**
     * 生成随机链表
     *
     * @param length 链表长度
     * @return
     */
    private Node createListNode(int length) {
        if (length <= 0) {
            throw new IllegalArgumentException("链表长度必须大于0");
        }

        Random random = new Random();
        int temp = Math.min(10 * length, Integer.MAX_VALUE);
        Node head = new Node(random.nextInt(temp));
        Node node = head;
        while (length > 1) {
            node.next = new Node(random.nextInt(temp));
            node = node.next;
            --length;
        }
        return head;
    }

    /**
     * 生成升序链表
     *
     * @param length 链表长度
     * @return
     */
    private Node createSortListNode(int length) {
        if (length <= 0) {
            throw new IllegalArgumentException("链表长度必须大于0");
        }

        Random random = new Random();
        int temp = Math.min(10 * length, Integer.MAX_VALUE);
        Node head = new Node(random.nextInt(temp));
        Node node = head;
        int preValue = 0;
        while (length > 1) {
            preValue = node.val;
            node.next = new Node(random.nextInt(temp) + preValue);
            node = node.next;
            --length;
        }
        return head;
    }

    /**
     * 调用方法处理链表并输出结果
     *
     * @param toSortNums 待排序数组
     * @param methodName 方法名
     * @param function
     */
    private void printResult(int[] toSortNums, String methodName, Function<int[], int[]> function) {
        int[] numsCopy = Arrays.copyOf(toSortNums, toSortNums.length);
        int[] result = function.apply(numsCopy);
        String str = String.format("=======%s:%s", methodName, Arrays.toString(result));
        System.out.println(str);
    }

    static class Node {
        int val;
        Node next;

        Node(int x) {
            val = x;
        }

        /**
         * 输出当前节点到尾节点的所有值
         */
        public String getListStr() {
            Node cur = this;
            StringBuilder sb = new StringBuilder();
            while (cur.next != null) {
                sb.append(cur.val + "->");
                cur = cur.next;
            }
            sb.append(cur.val);
            return sb.toString();
        }
    }
}
