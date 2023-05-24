package com.jkx.algorithm.practice;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
class BinarySearchTest {
    private BinarySearch binarySearch;
    private int[] sortedArray;

    @BeforeEach
    public void setUp() {
        binarySearch = new BinarySearch();
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