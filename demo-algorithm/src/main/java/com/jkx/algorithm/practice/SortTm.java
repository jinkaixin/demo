package com.jkx.algorithm.practice;

import java.util.Arrays;

/**
 * @author jkx
 * @date 2023/8/5
 */
public class SortTm extends Sort {

    /*
     * 对于冒泡、插入和选择这三种排序来说，都涉及到已排序区和未排序区的概念
     * 做法都是每一轮将未排序区中一个数放到已排序区中
     * 这样n轮过后，所有的数都已排序完毕
     *
     * 只不过各自实现的思路不同
     * 冒泡排序是，相邻两数进行对比，较大的后移，这样全部数字比较完毕后，就可以把最大的挪到最后，同时还能够保证原先数字间的相对顺序不变
     * 选择排序和冒泡排序类似，也是每轮找到最大数放到末尾，不同点是，比较后只记录较大值，而不会进行挪移，只在最后将最大的挪到末尾
     * 插入排序，是每轮选择未排序区中最左边的放入已排序区中并进行排序
     */

    @Override
    public int[] bubbleSort(int[] nums) {
        for (int i = 0; i < nums.length; i++) {
            for (int j = 0; j < nums.length - i - 1; j++) {
                if (nums[j] > nums[j + 1]) {
                    int temp = nums[j + 1];
                    nums[j + 1] = nums[j];
                    nums[j] = temp;
                }
            }
        }
        return nums;
    }

    @Override
    public int[] selectSort(int[] nums) {
        for (int i = 0; i < nums.length; i++) {
            int maxIndex = 0;
            for (int j = 1; j < nums.length - i; j++) {
                if (nums[j] > nums[maxIndex]) {
                    maxIndex = j;
                }
            }
            int temp = nums[nums.length - i - 1];
            nums[nums.length - i - 1] = nums[maxIndex];
            nums[maxIndex] = temp;
        }
        return nums;
    }

    @Override
    public int[] insertSort(int[] nums) {
        for (int i = 1; i < nums.length; i++) {
            int j = i, value = nums[j];
            while (j >= 1 && nums[j - 1] > value) {
                nums[j] = nums[j - 1];
                j--;
            }
            nums[j] = value;
        }
        return nums;
    }

    /**
     * 快速排序的思路是，每回合让一个数处于它该在的位置，当所有数都处于该在的位置后，整体就有序了
     * 至于怎么确定这个数的位置，可以利用双指针法不断向内检索，左边找比起大的数，右边找比其小的数，找到了就互换
     * 最后将目标数与两指针相交位置互换即可
     *
     * @param nums
     * @return
     */
    @Override
    public int[] quickSort(int[] nums) {
        quickSortHelper(nums, 0, nums.length - 1);
        return nums;
    }

    private void quickSortHelper(int[] nums, int head, int tail) {
        // 递归出口
        if (nums == null || nums.length == 0 || head >= tail) {
            return;
        }
        // 确定子递归区间
        int i = head, j = tail, choose = nums[head];
        while (i < j) {
            while (nums[j] >= choose && i < j) {
                j--;
            }
            while (nums[i] <= choose && i < j) {
                i++;
            }
            if (i < j) {
                int temp = nums[i];
                nums[i] = nums[j];
                nums[j] = temp;
            }
        }
        nums[head] = nums[i];
        nums[i] = choose;
        // 递推关系式
        quickSortHelper(nums, head, i - 1);
        quickSortHelper(nums, i + 1, tail);
    }

    /**
     * 归并排序的思路是，只有一个元素的数组必定是有序的，那么两个这样的数组就可以合并成一个更大的有序数组
     * 就这样不停地合并下去，直到整体有序为止
     * 如果说快速排序是从上往下的递归，那么归并排序就是从下往上的递归
     *
     * @param nums
     * @return
     */
    @Override
    public int[] mergeSort(int[] nums) {
        mergeSortHelper(nums, 0, nums.length - 1);
        return nums;
    }

    private void mergeSortHelper(int[] array, int left, int right) {
        // 递归出口
        if (array == null || array.length == 0 || left >= right) {
            return;
        }
        // 确定子递归区间
        int middle = left + (right - left) / 2;
        // 递推关系
        mergeSortHelper(array, left, middle);
        mergeSortHelper(array, middle + 1, right);
        mergeSortedArray(array, left, middle, right);
    }

    /**
     * 将左右区间都有序的数组整理成一个整体有序的数组
     * @param array 数组
     * @param left 起点
     * @param middle 区间交界处
     * @param right 末尾
     */
    private void mergeSortedArray(int[] array, int left, int middle, int right) {
        int[] leftArray = Arrays.copyOfRange(array, left, middle + 1);
        int[] rightArray = Arrays.copyOfRange(array, middle + 1, right + 1);
        int leftIndex = 0, rightIndex = 0, totalIndex = left;
        // 先放入较小的
        while (leftIndex < leftArray.length && rightIndex < rightArray.length) {
            if (leftArray[leftIndex] < rightArray[rightIndex]) {
                array[totalIndex++] = leftArray[leftIndex++];
            } else {
                array[totalIndex++] = rightArray[rightIndex++];
            }
        }
        // 放入剩余的所有元素
        while (leftIndex < leftArray.length) {
            array[totalIndex++] = leftArray[leftIndex++];
        }
        while (rightIndex < rightArray.length) {
            array[totalIndex++] = rightArray[rightIndex++];
        }
    }

    @Override
    public int[] shellSort(int[] nums) {
        return super.shellSort(nums);
    }
}
