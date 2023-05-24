package com.jkx.algorithm.practice;

/**
 * @author jkx
 * @date 2023/2/24
 * <p>
 * 利用二分查找从有序数组中找到需要的数
 */
public class BinarySearch {

    /**
     * 从有序数组中找到目标数字的所在位置
     *
     * @param sortedArray 升序数组
     * @param target      目标数
     * @return 有就返回对应索引，没有就返回-1
     */
    public int findEqual(int[] sortedArray, int target) {
        int left = 0, right = sortedArray.length - 1;
        while (left <= right) {
            int middle = (right - left) / 2 + left;
            if (sortedArray[middle] == target) {
                return middle;
            } else if (sortedArray[middle] > target) {
                right = middle - 1;
            } else {
                left = middle + 1;
            }
        }
        return -1;
    }

    /**
     * 从有序升序数组中第一个等于目标数字的数的所在位置
     *
     * @param sortedArray 升序数组
     * @param target      目标数
     * @return 有就返回对应索引，没有就返回-1
     */
    public int findFirstEqual(int[] sortedArray, int target) {
        int left = 0, right = sortedArray.length - 1;
        while (left <= right) {
            int middle = left + (right - left) / 2;
            if (sortedArray[middle] == target) {
                if (middle == 0 || sortedArray[middle - 1] < sortedArray[middle]) {
                    return middle;
                }
                right = middle - 1;
            } else if (sortedArray[middle] > target) {
                right = middle - 1;
            } else {
                left = middle + 1;
            }
        }
        return -1;
    }

    /**
     * 另外一种写法，性能上比上面那个略差
     */
//    public int findFirstEqual(int[] sortedArray, int target) {
//        int left = 0, right = sortedArray.length - 1;
//        int index = -1;
//        while (left <= right) {
//            int middle = left + (right - left) / 2;
//            if (sortedArray[middle] == target) {
//                index = middle;
//                right = middle - 1;
//            } else if (sortedArray[middle] > target) {
//                right = middle - 1;
//            } else {
//                left = middle + 1;
//            }
//        }
//        return index;
//    }

    /**
     * 从有序升序数组中第一个大于等于目标数字的数的所在位置
     *
     * @param sortedArray 升序数组
     * @param target      目标数
     * @return 有就返回对应索引，没有就返回-1
     */
    public int findFirstGe(int[] sortedArray, int target) {
        int left = 0, right = sortedArray.length - 1;
        while (left <= right) {
            int middle = left + (right - left) / 2;
            if (sortedArray[middle] >= target) {
                if (middle == 0 || sortedArray[middle - 1] < target) {
                    return middle;
                }
                right = middle - 1;
            } else {
                left = middle + 1;
            }
        }
        return -1;
    }

    /**
     * 另一种解法
     */
//    public int findFirstGe(int[] sortedArray, int target) {
//        int left = 0, right = sortedArray.length - 1;
//        int index = -1;
//        while (left <= right) {
//            int middle = left + (right - left) / 2;
//            if (sortedArray[middle] >= target) {
//                index = middle;
//                right = middle - 1;
//            } else {
//                left = middle + 1;
//            }
//        }
//        return index;
//    }
}
