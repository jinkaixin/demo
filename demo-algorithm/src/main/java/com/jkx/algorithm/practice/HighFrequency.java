package com.jkx.algorithm.practice;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * 高频面试题
 * https://codetop.cc/home
 */
public class HighFrequency {

    private final static Random random = new Random(System.currentTimeMillis());

    public static void main(String[] args) {
        Map<Integer, Integer> map = new HashMap<>();
        Integer value = map.get(1);
    }

    /**
     * 无重复字符的最长子串，给定一个字符串 s ，请你找出其中不含有重复字符的 最长子串的长度
     * leetcode-3，频度-569
     */
    public int lengthOfLongestSubstring(String s) {
        Map<Character, Integer> window = new HashMap<>();
        int left = 0, right = 0, maxLen = 0;
        while (right < s.length()) {
            char c1 = s.charAt(right);
            right++;
            // 窗口数据更新
            window.put(c1, window.getOrDefault(c1, 0) + 1);
            while (window.get(c1) > 1) {
                char c2 = s.charAt(left);
                left++;
                // 窗口数据更新
                window.put(c2, window.get(c2) - 1);
            }
            maxLen = Math.max(maxLen, right - left);
        }
        return maxLen;
    }

    /**
     * 反转链表
     * leetcode-206，频度-540
     * 迭代解法，时间复杂度为o(n)，空间复杂度为o(1)
     */
    public ListNode reverseList(ListNode head) {
        ListNode cur = head, pre = null;
        while (cur != null) {
            ListNode next = cur.next;
            cur.next = pre;
            pre = cur;
            cur = next;
        }
        return pre;
    }

//    /**
//     * 反转链表
//     * leetcode-206
//     * 递归解法，时间复杂度为o(n)，空间复杂度为o(n)
//     */
//    public ListNode reverseList(ListNode head) {
//        // 递归出口
//        if (head == null || head.next == null) {
//            return head;
//        }
//        // 递推关系式
//        ListNode newHead = reverseList(head.next);
//        head.next.next = head;
//        head.next = null;
//        // 返回结果
//        return newHead;
//    }

    /**
     * 找到数组中的第K个最大元素，要求时间复杂度为 O(n)
     * leetcode-215，频度-388
     */
    public int findKthLargest(int[] nums, int k) {
        int head = 0, tail = nums.length - 1;
        while (true) {
            int index = partition(nums, head, tail);
            if (index == nums.length - k) {
                return nums[index];
            } else if (index > nums.length - k) {
                // 往左边找
                tail = index - 1;
            } else {
                // 往右边找
                head = index + 1;
            }
        }
    }

    /**
     * 分区函数，确定一个数在数组中的位置，并适当排序，保证左边的都小于它，右边的都大于它
     * 返回排序后该数的索引
     */
    private int partition(int[] nums, int head, int tail) {
        int randomIndex = head + random.nextInt(tail - head + 1);
        swap(nums, head, randomIndex);
        // 以最左边的作为当前轮要确定位置的数，并进行数据互换，让小的集中到左边，大的集中到右边
        int target = nums[head], left = head, right = tail;
        while (left < right) {
            // 从右边找小于目标值的数
            while (left < right && nums[right] >= target) {
                right--;
            }
            // 从左边找大于目标值的数
            while (left < right && nums[left] <= target) {
                left++;
            }
            // 两数互换
            swap(nums, left, right);
        }
        // 两指针重叠后，再和目标值进行互换，这样当前这轮排序就完成了
        nums[head] = nums[left];
        nums[left] = target;
        return left;
    }

    /**
     * 互换两数位置
     */
    private void swap(int[] nums, int left, int right) {
        if (left != right) {
            int temp = nums[left];
            nums[left] = nums[right];
            nums[right] = temp;
        }
    }

    /**
     * K 个一组翻转链表，给定链表的头节点 head ，每 k 个节点一组进行翻转，返回修改后的链表
     * leetcode-25，频度-298
     */
    public ListNode reverseKGroup(ListNode head, int k) {
        // 确定递归子区间
        ListNode pre = null, cur = head;
        for (int i = 0; i < k; i++) {
            // 递归出口
            if (cur == null) {
                return head;
            }
            pre = cur;
            cur = cur.next;
        }
        // 递推关系式
        ListNode childrenHead = reverseKGroup(cur, k);
        pre.next = childrenHead;
        ListNode newHead = reverseK(head, k);
        return newHead;
    }

    /**
     * 反转链表指定区间
     */
    public ListNode reverseBetween(ListNode head, int left, int right) {
        if (left == 1) {
            return reverseK(head, right);
        } else {
            ListNode cur = head, pre = null;
            while (left > 1) {
                pre = cur;
                cur = cur.next;
                left--;
                right--;
            }
            ListNode childrenHead = reverseK(cur, right);
            pre.next = childrenHead;
            return head;
        }
    }

    /**
     * 反转链表的前k个节点
     */
    private ListNode reverseK(ListNode head, int k) {
        ListNode cur = head, pre = null;
        while (k > 0) {
            ListNode next = cur.next;
            cur.next = pre;
            pre = cur;
            cur = next;
            k--;
        }
        head.next = cur;
        return pre;
    }

    /**
     * 最大子数组和，给你一个整数数组 nums ，请你找出一个具有最大和的连续子数组（子数组最少包含一个元素），返回其最大和
     * leetcode-53，频度-243
     */
    public int maxSubArray(int[] nums) {
        return -1;
    }

    /**
     * 排序数组，频度-242
     */
    public int[] sortArray(int[] nums) {
        quickSort(nums, 0, nums.length - 1);

        return nums;
    }

    /**
     * 快速排序
     * 每轮确定一个数在排序数组中的位置，即左边的都小于它，右边的都大于它
     * 这样经过多轮排序后，整体便有序
     */
    private void quickSort(int[] nums, int head, int tail) {
        // 递归出口
        if (head >= tail) {
            return;
        }
        // 确定子递归区间
        int index = partition(nums, head, tail);
        // 递推关系式
        quickSort(nums, head, index - 1);
        quickSort(nums, index + 1, tail);
    }

    /**
     * 合并两个有序链表，两个链表均为升序链表
     * leetcode-21，频度-224
     * 时间复杂度为o(n)，空间复杂度为o(1)
     */
    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        ListNode virtualHead = new ListNode(), cur = virtualHead;
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
        // 此时将剩下的那个链表拼接到新链表即可
        cur.next = (list1 != null ? list1 : list2);
        return virtualHead.next;
    }

    /**
     * 找两个和为目标值的数，返回索引，不可为同一个数
     * leetcode-1，频度-218
     * 这里采用哈希表解法，遍历过程中检查哈希表中是否有需要的数，没有就存入当前数
     * 不可以先遍历一遍把数据存放到哈希表，再遍历一遍查询需要的值，因为有相同值会进行覆盖
     * 时间复杂度为o(n)，空间复杂度为o(n)
     * <p>
     * 还有一种解法为暴力解法，两个for循环查找数据，时间复杂度为o(n^2)，空间复杂度为o(1)
     *
     * @param nums
     * @param target
     * @return
     */
    public int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            if (map.containsKey(target - nums[i])) {
                return new int[]{i, map.get(target - nums[i])};
            }
            map.put(nums[i], i);
        }
        return null;
    }

    /**
     * 最长回文子串
     * leetcode-5，频度-199
     *
     * @param s 给定字符串
     * @return 回文串
     */
    public String longestPalindrome(String s) {
        // 依次构造回文串，找出最长的那个
        String result = s.substring(0, 1);
        for (int i = 0; i < s.length(); i++) {
            // 回文串分为奇数长度的和偶数长度的，需要分别寻找
            String str1 = getPalindrome(s, i, i);
            String str2 = getPalindrome(s, i, i + 1);
            if (str1.length() > result.length()) {
                result = str1;
            }
            if (str2.length() > result.length()) {
                result = str2;
            }
        }
        return result;
    }

    /**
     * 构造回文串，从中心向两边拓展比较，直到不相等为止，就得到了一个回文串
     */
    private String getPalindrome(String s, int left, int right) {
        while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
            left--;
            right++;
        }
        return s.substring(left + 1, right);
    }

    /**
     * 判断链表是否有环
     * leetcode-141，频度-195
     * 设置快慢指针，快指针前行速度为慢指针两倍
     * 有环的话两指针必定相交于环内某处，否则快指针必会走到空节点处
     * <p>
     * 时间复杂度为o(n)，空间复杂度为o(1)
     */
    public boolean hasCycle(ListNode head) {
        // 先不同速前行，等相交后停止
        ListNode fast = head, slow = head;
        while (fast != null && fast.next != null) {
            // 先走一轮，让快指针领先，没环的话后续是不可能相交的，相交必定是有环
            fast = fast.next.next;
            slow = slow.next;
            if (fast == slow) {
                return true;
            }
        }
        return false;
    }

    /**
     * 找环的起点
     * leetcode-142，频度-146
     * 设置快慢指针，快指针前行速度为慢指针两倍
     * 有环的话两指针必定相交于环内某处，否则快指针必会走到空节点处
     * 设慢指针所走步数为m，如果让快指针回到起点处和慢指针保持同样速度前行，m步后两指针必定再次相交
     * 事实上，由于两指针同速前行了，所以走m-1步时两者交于上个节点，走m-2步时两者交于上上个节点
     * 以此类推，最开始相交的节点就是环的起点处
     * 所以只要找到第二次前行时两者的首次相交点，环的起点就找到了
     * <p>
     * 时间复杂度为o(n)，空间复杂度为o(1)
     */
    public ListNode detectCycle(ListNode head) {
        // 先不同速前行，等相交后停止
        ListNode fast = head, slow = head;
        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
            if (fast == slow) {
                break;
            }
        }
        // 排除无环的情况
        if (fast == null || fast.next == null) {
            return null;
        }
        // 第二次同速前行，等相交时停止
        fast = head;
        while (fast != slow) {
            fast = fast.next;
            slow = slow.next;
        }
        return fast;
    }


    static class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }

        /**
         * 输出当前节点到尾节点的所有值
         */
        public String getListStr() {
            ListNode cur = this;
            StringBuilder sb = new StringBuilder();
            while (cur.next != null) {
                sb.append(cur.val + "->");
                cur = cur.next;
            }
            sb.append(cur.val);
            return sb.toString();
        }
    }

    static ListNode genList(int[] vals) {
        ListNode head = null, last = null;
        for (int val : vals) {
            if (last == null) {
                head = new ListNode(val);
                last = head;
            } else {
                last.next = new ListNode(val);
                last = last.next;
            }
        }
        return head;
    }
}
