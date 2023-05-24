package com.jkx.algorithm.practice;

import java.util.Arrays;
import java.util.Random;
import java.util.function.Function;

/**
 * @author jkx
 * @date 2023/2/24
 */
public class Sort {
    private static final int[] EXAMPLE = {8, 3, 1, 7, 27, 13, 19};

    public static void main(String[] args) {
        Sort sort = new Sort();
        sort.sortTest(20);
        sort.speedTest(500000);
    }

    /**
     * 冒泡排序，升序排序
     * 每轮不断将相邻两数比较，较大的移动至后方，这样一轮过后最后一个数必定是最大值，下一轮排序排除该值进行
     */
    public int[] bubbleSort(int[] nums) {
        int temp;
        for (int i = nums.length; i > 0; i--) {
            for (int j = 0; j < i - 1; j++) {
                if (nums[j] > nums[j + 1]) {
                    temp = nums[j];
                    nums[j] = nums[j + 1];
                    nums[j + 1] = temp;
                }
            }
        }
        return nums;
    }

    /**
     * 选择排序，升序排序
     * 每轮确定最小数并移动至开头，下一轮排序时排除该值进行
     * 这样进行n轮后便整体有序
     * 和插入排序类似，都是每轮将一个数从未排序区放到已排序区中
     * 不同的是，对于插入排序，每轮是将一个数放到已排序区中再进行排序
     * 而选择排序则是每轮找出最小的数追加到已排序区末尾
     */
    public int[] selectSort(int[] nums) {
        for (int i = 0; i < nums.length; i++) {
            int index = i;
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[j] < nums[index]) {
                    index = j;
                }
            }
            // 将最小数移动到开头
            if (i != index) {
                int temp = nums[i];
                nums[i] = nums[index];
                nums[index] = temp;
            }
        }
        return nums;
    }

    /**
     * 插入排序，升序排序
     * 一个数必然有序，第二个数进来只要确定好它的位置，整体也就有序，然后依次放入第三个、第四个等，直到所有的数字排序完毕
     */
    public int[] insertSort(int[] nums) {
        for (int i = 1; i < nums.length; i++) {
            int j = i, value = nums[j];
            while (j > 0 && nums[j - 1] > value) {
                nums[j] = nums[j - 1];
                j--;
            }
            if (nums[j] != value) {
                nums[j] = value;
            }
        }
        return nums;
    }

    /**
     * 快速排序，升序排序
     * 每轮为一个数排序，保证这个数左边的都小于等于它，右边的都大于等于它
     * 之后再针对两侧分别进行这样的排序，不断进行下去，直到整体有序为止
     */
    public int[] quickSort(int[] nums) {
        quickSortHelper(nums, 0, nums.length - 1);
        return nums;
    }

    /**
     * 快速排序辅助函数
     * 细说下每轮循环换数的逻辑吧
     * 先从右边开始循环找小于目标数字的数，会停下来，要么是找到这个数了，要么就是i和j重合了
     * 是后者，那说明选定数组是有序的，不用排序
     * 是前者，则开始从左边循环找大于目标的数，同样的，停下来要么是找到这个数了，要么就是i和j重合了
     * 后者的话，那么此时i位置的数是小于目标数的，左侧的均小于等于目标数，右侧的均大于等于目标数，那把i位置的数和目标数互换，整体就有序了
     * 前者的话，那就是正常的两边互换数的逻辑，互换后开始后续的互换过程
     */
    private void quickSortHelper(int[] nums, int head, int tail) {
        // 递归出口
        if (head >= tail) {
            return;
        }
        // 确定该数的位置
        int i = head, j = tail, target = nums[head];
        while (i < j) {
            // 从右边找到小于选定数的索引
            while (nums[j] >= target && i < j) {
                j--;
            }
            // 从左边找到大于选定数的索引
            while (nums[i] <= target && i < j) {
                i++;
            }
            // 两数位置互换
            if (i < j) {
                int temp = nums[i];
                nums[i] = nums[j];
                nums[j] = temp;
            }
        }
        // 让该数回到应在的位置
        nums[head] = nums[i];
        nums[i] = target;
        // 针对两侧进行排序
        quickSortHelper(nums, head, i - 1);
        quickSortHelper(nums, i + 1, tail);
    }

    /**
     * 归并排序
     * 原理是先将两边排好序，然后再按照大小顺序进行合并
     */
    public int[] mergeSort(int[] nums) {
        mergeSortHelper(nums, 0, nums.length - 1);
        return nums;
    }

    private void mergeSortHelper(int[] nums, int left, int right) {
        // 递归出口
        if (left >= right) {
            return;
        }
        int middle = left + (right - left) / 2;
        mergeSortHelper(nums, left, middle);
        mergeSortHelper(nums, middle + 1, right);
        mergeSortedArray(nums, left, middle, right);
    }

    /**
     * 合并两个有序数组
     *
     * @param nums left到middel有序，middle+1到right有序
     */
    private void mergeSortedArray(int[] nums, int left, int middle, int right) {
        int[] leftArray = Arrays.copyOfRange(nums, left, middle + 1);
        int[] rightArray = Arrays.copyOfRange(nums, middle + 1, right + 1);
        int leftPos = 0, rightPos = 0, originPos = left;
        // 相互比较，将较小的先放入
        while (leftPos < leftArray.length && rightPos < rightArray.length) {
            if (leftArray[leftPos] <= rightArray[rightPos]) {
                nums[originPos] = leftArray[leftPos];
                leftPos++;
            } else {
                nums[originPos] = rightArray[rightPos];
                rightPos++;
            }
            originPos++;
        }
        // 此时左右两个数组中必定有一个为空，将另一个剩余的数依次放入
        while (leftPos < leftArray.length) {
            nums[originPos] = leftArray[leftPos];
            leftPos++;
            originPos++;
        }
        while (rightPos < rightArray.length) {
            nums[originPos] = rightArray[rightPos];
            rightPos++;
            originPos++;
        }
    }

    /**
     * 希尔排序
     * 本质上还是利用了插入排序，只不过提前分组，将相隔较远的数直接进行排序互换，从而节省了交换步骤数
     * 比如{5,4,3,2,1}，对于1来说，要排到第一位
     * 若使用插入排序的话，需要进行四次比较换位
     * 而使用希尔排序的话，第一次分组为{5,3,1}，{4,2}，只需要进行两次比较换位
     * 在一种情况下速度肯定慢于插入排序，即完全正序的情况，因为对于完全正序的数组，希尔排序最后一轮的操作和插入排序完全一样，但是前面还有几轮操作
     */
    public int[] shellSort(int[] nums) {
        for (int groupCount = nums.length / 2; groupCount > 0; groupCount /= 2) {
            // 由于每组相邻数的间隔一样，所以互换代码是通用的，直接全体遍历过去就行
            for (int i = groupCount; i < nums.length; i++) {
                int j = i, value = nums[i];
                while (j >= groupCount && nums[j - groupCount] > value) {
                    nums[j] = nums[j - groupCount];
                    j -= groupCount;
                }
                nums[j] = value;
            }
        }
        return nums;
    }

    /**
     * 排序测试
     */
    public void sortTest() {
        sortTest(null);
    }

    /**
     * 排序测试
     *
     * @param count 样本量，输入null则分配默认数组
     */
    public void sortTest(Integer count) {
        int[] toSortNums;
        if (count != null) {
            // 设置上限，排序测试，样本量太大的话，看起来不方便
            int max = 100;
            if (count <= 0 || count > max) {
                String errorMessage = String.format("样本量有误，应当介于1与%d之间", max);
                throw new RuntimeException(errorMessage);
            }
            // 生成待排序样本
            toSortNums = getRandomNums(count);
        } else {
            toSortNums = Arrays.copyOf(EXAMPLE, EXAMPLE.length);
            ;
            count = EXAMPLE.length;
        }
        System.out.println("排序测试，样本量=" + count);

        printResult(toSortNums, "originNums", i -> i);
        printResult(toSortNums, "bubbleSort", this::bubbleSort);
        printResult(toSortNums, "selectSort", this::selectSort);
        printResult(toSortNums, "insertSort", this::insertSort);
        printResult(toSortNums, "quickSort", this::quickSort);
        printResult(toSortNums, "mergeSort", this::mergeSort);
        printResult(toSortNums, "shellSort", this::shellSort);
    }

    /**
     * 性能测试
     *
     * @param count 样本量
     */
    public void speedTest(int count) {
        int max = Integer.MAX_VALUE;
        if (count <= 0 || count > max) {
            String errorMessage = String.format("样本量有误，应当介于1与%d之间", max);
            throw new RuntimeException(errorMessage);
        }

        // 生成待排序样本
        int[] toSortNums = getRandomNums(count);
        System.out.println("处理速度测试，样本量=" + count);

        if (count <= 50000) {
            // 样本量太大的话，下面的排序较为耗时，故只在样本量较小时测试这些排序方法
            printHandleTime(toSortNums, "bubbleSort", this::bubbleSort);
            printHandleTime(toSortNums, "selectSort", this::selectSort);
            printHandleTime(toSortNums, "insertSort", this::insertSort);
        }
        printHandleTime(toSortNums, "quickSort", this::quickSort);
        printHandleTime(toSortNums, "mergeSort", this::mergeSort);
        printHandleTime(toSortNums, "shellSort", this::shellSort);
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

    /**
     * 调用方法排序数组并输出排序结果
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

    /**
     * 统计排序所花费时间
     *
     * @param toSortNums 待排序数组
     * @param methodName 方法名
     * @param function
     */
    private void printHandleTime(int[] toSortNums, String methodName, Function<int[], int[]> function) {
        int[] numsCopy = Arrays.copyOf(toSortNums, toSortNums.length);
        long start = System.currentTimeMillis();
        int[] result = function.apply(numsCopy);
        long total = System.currentTimeMillis() - start;
        String str = String.format("=======样本量%d，%s:%d毫秒", numsCopy.length, methodName, total);
        System.out.println(str);
    }
}
