package com.jkx.algorithm.practice;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
class BinarySearchTmTest {
    private BinarySearchTm binarySearch;
    private int[] sortedArray;

    @BeforeEach
    public void setUp() {
        binarySearch = new BinarySearchTm();
        sortedArray = new int[]{1, 2, 4, 7, 9, 9, 9, 9, 9, 11, 23, 26, 58};
    }

    @Test
    public void testFindEqual() {
        assertEquals(-1, binarySearch.findEqual(sortedArray, 6));
        assertEquals(3, binarySearch.findEqual(sortedArray, 7));
        assertEquals(-1, binarySearch.findEqual(sortedArray, 0));
        assertEquals(6, binarySearch.findEqual(sortedArray, 9));
        assertEquals(-1, binarySearch.findEqual(sortedArray, 100));
    }

    @Test
    public void testFindFirstEqual() {
        assertEquals(-1, binarySearch.findFirstEqual(sortedArray, 6));
        assertEquals(3, binarySearch.findFirstEqual(sortedArray, 7));
        assertEquals(-1, binarySearch.findFirstEqual(sortedArray, 0));
        assertEquals(4, binarySearch.findFirstEqual(sortedArray, 9));
        assertEquals(-1, binarySearch.findFirstEqual(sortedArray, 100));
    }

    @Test
    public void testFindFirstGe() {
        assertEquals(3, binarySearch.findFirstGe(sortedArray, 6));
        assertEquals(3, binarySearch.findFirstGe(sortedArray, 7));
        assertEquals(0, binarySearch.findFirstGe(sortedArray, 0));
        assertEquals(4, binarySearch.findFirstGe(sortedArray, 9));
        assertEquals(-1, binarySearch.findFirstGe(sortedArray, 100));
    }
}

/**
 * 二分查找，利用有序序列的单调特性来减少要检索的数据，从而减少要执行的指令数
 */
class BinarySearchTm {
    /**
     * 寻找特定的值
     *
     * @param array
     * @param target
     * @return 不存在则返回-1
     */
    public int findEqual(int[] array, int target) {
        int left = 0, right = array.length - 1;
        while (left <= right) {
            int middle = left + (right - left) / 2;
            if (array[middle] == target) {
                return middle;
            } else if (array[middle] > target) {
                right = middle - 1;
            } else {
                left = middle + 1;
            }
        }
        return -1;
    }

    /**
     * 寻找特定值，如果存在多个，返回最左边的
     *
     * @param array
     * @param target
     * @return 不存在则返回-1
     */
    public int findFirstEqual(int[] array, int target) {
        int left = 0, right = array.length - 1, index = -1;
        while (left <= right) {
            int middle = left + (right - left) / 2;
            if (array[middle] == target) {
                index = middle;
                right = middle - 1;
            } else if (array[middle] > target) {
                right = middle - 1;
            } else {
                left = middle + 1;
            }
        }
        return index;
    }

//    public int findFirstEqual(int[] array, int target) {
//        int result = -1;
//        int left = 0, right = array.length - 1;
//        while (left <= right) {
//            int middle = left + (right - left) / 2;
//            if (array[middle] >= target) {
//                if (array[middle] == target) {
//                    result = middle;
//                }
//                right = middle - 1;
//            } else {
//                left = middle + 1;
//            }
//        }
//
//        return result;
//    }

    /**
     * 寻找大于等于特定值的第一个数
     *
     * @param array
     * @param target
     * @return 不存在则返回-1
     */
    public int findFirstGe(int[] array, int target) {
        int left = 0, right = array.length - 1, index = -1;
        while (left <= right) {
            int middle = left + (right - left) / 2;
            if (array[middle] >= target) {
                index = middle;
                right = middle - 1;
            } else {
                left = middle + 1;
            }
        }
        return index;
    }

//    public int findFirstGe(int[] array, int target) {
//        int result = -1;
//        int left = 0, right = array.length - 1;
//        while (left <= right) {
//            int middle = left + (right - left) / 2;
//            if (array[middle] >= target) {
//                result = middle;
//                right = middle - 1;
//            } else {
//                left = middle + 1;
//            }
//        }
//
//        return result;
//    }
}