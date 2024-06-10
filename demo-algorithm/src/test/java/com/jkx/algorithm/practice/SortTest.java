package com.jkx.algorithm.practice;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;

class SortTest {

    private Sort sort;
    private int[] array;
    private int[] sortedArray;

    @BeforeEach
    public void setUp() {
        sort = new Sort();
        array = getRandomNums(20000);
        sortedArray = Arrays.copyOf(array, array.length);
        Arrays.sort(sortedArray);
    }

    @Test
    void bubbleSort() {
        sort.bubbleSort(array);
        Assertions.assertArrayEquals(sortedArray, array);
    }

    @Test
    void selectSort() {
        sort.selectSort(array);
        Assertions.assertArrayEquals(sortedArray, array);
    }

    @Test
    void insertSort() {
        sort.insertSort(array);
        Assertions.assertArrayEquals(sortedArray, array);
    }

    @Test
    void quickSort() {
        sort.quickSort(array);
        Assertions.assertArrayEquals(sortedArray, array);
    }

    @Test
    void mergeSort() {
        sort.mergeSort(array);
        Assertions.assertArrayEquals(sortedArray, array);
    }

    @Test
    void shellSort() {
        sort.shellSort(array);
        Assertions.assertArrayEquals(sortedArray, array);
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