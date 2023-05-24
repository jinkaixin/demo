package com.jkx.algorithm.practice;

import java.util.Arrays;
import java.util.Random;

/**
 * @author jkx
 * @date 2023/3/1
 * 递归
 */
public class Recursion {

    public static void main(String[] args) {
        Recursion recursion = new Recursion();
        recursion.sumTest();
        recursion.findMaxTest();
        recursion.handleFibonacciTest();
    }

    /**
     * 计算从1加到max
     * 时间复杂度为o(n)，空间复杂度为o(n)
     *
     * @param max 最大值
     */
    public int sum(int max) {
        // 递归出口
        if (max == 1) {
            return max;
        }
        // 递推关系式
        return sum(max - 1) + max;
    }

    public void sumTest() {
        // 递归出口
        int max = 100;
        int sum = sum(max);
        System.out.println();
        String output = String.format("sum测试 [max=%s, sum=%s]", max, sum);
        System.out.println(output);
    }

    /**
     * 计算从1加到max
     * 利用循环求解
     * 时间复杂度为o(n)，空间复杂度为o(1)
     */
//    public int sum(int max) {
//        int sum = 0;
//        for (int i = 1; i <= max; i++) {
//            sum += i;
//        }
//        return sum;
//    }

    /**
     * 找到数组中的最大值
     * 时间复杂度为o(n)，空间复杂度为o(n)
     */
    public int findMax(int[] nums) {
        return findMaxHelper(nums, 0, nums.length - 1);
    }

    private int findMaxHelper(int[] nums, int left, int right) {
        // 参数合法性校验
        if (left < 0 || right >= nums.length) {
            throw new IllegalArgumentException("索引越界");
        }
        // 递归出口
        if (left == right) {
            return nums[left];
        }
        // 递推关系式
        return Math.max(findMaxHelper(nums, left, right - 1), nums[right]);
    }

    /**
     * 找到数组中的最大值
     * 利用循环求解
     * 时间复杂度为o(n)，空间复杂度为o(1)
     */
//    public int findMax(int[] nums) {
//        int max = nums[0];
//        for (int i = 1; i < nums.length; i++) {
//            if (nums[i] > max) {
//                max = nums[i];
//            }
//        }
//        return max;
//    }
    public void findMaxTest() {
        int[] nums = getRandomNums(10);
        int max = findMax(nums);
        String output = String.format("findMax测试 [数组=%s, 最大值=%s]", Arrays.toString(nums), max);
        System.out.println(output);
    }

    /**
     * 求解斐波那契数列第n个位置的值
     * 用递归求解时，生成的方法栈数量呈斐波那契数列变化，所以求解时间复杂度也就转为求解斐波那契数列通项
     * 至于时间复杂度
     * f(3)=f(2)+f(1)	2个方法栈
     * f(4)=f(3)+f(2)   3个方法栈
     * f(5)=f(4)+f(3)   5个方法栈
     * f(6)=f(5)+f(4)   8个方法栈
     * f(7)=f(6)+f(5)   13个方法栈
     * f(8)=f(7)+f(6)   21个方法栈
     * 总的来说，呈现出类似于指数变化的趋势，类似于2^n，所以用递归法求解斐波那契数列时，不适合传入过大的参数
     */
    public int handleFibonacci(int n) {
        // 递归出口
        if (n == 1 || n == 2) {
            return 1;
        }
        // 递推关系式f(n) = f(n-1) + f(n-2)
        return handleFibonacci(n - 1) + handleFibonacci(n - 2);
    }

    /**
     * 求解斐波那契数列第n个位置的值
     * 利用循环求解
     * 时间复杂度为o(n)，空间复杂度为o(1)
     */
//    public int handleFibonacci(int n) {
//        if (n == 1 || n == 2) {
//            return 1;
//        }
//        int left = 1, right = 1, temp = 1;
//        for (int i = 3; i <= n; i++) {
//            temp = left + right;
//            left = right;
//            right = temp;
//        }
//        return right;
//    }

    public void handleFibonacciTest() {
        // 取值区间为[3, 30)
        int n = 3 + new Random().nextInt(27);
        int result = handleFibonacci(n);
        String output = String.format("handleFibonacci测试 [位置=%s, 值=%s]", n, result);
        System.out.println(output);
    }

    /**
     * 生成随机数组
     *
     * @param count 数组大小
     * @return
     */
    private int[] getRandomNums(int count) {
        int[] toSortNums = new int[count];
        Random random = new Random();
        int temp = (10 * count < Integer.MAX_VALUE ? 10 * count : count);
        for (int i = 0; i < toSortNums.length; i++) {
            toSortNums[i] = random.nextInt(temp);
        }
        return toSortNums;
    }
}
