package com.jkx.thread.practice;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author jkx
 * @date 2023/10/17
 */
public class Practice {

    public static void main(String[] args) throws InterruptedException {
        Practice practice = new Practice();
        practice.testCountDownLatch();
        practice.testCyclicBarrier();
    }

    public void testCountDownLatch() throws InterruptedException {
        CountDownLatch begin = new CountDownLatch(1);
        CountDownLatch end = new CountDownLatch(5);
        ExecutorService threadPool = Executors.newFixedThreadPool(5);

        for (int i = 0; i < 5; i++) {
            int finalI = i + 1;
            long milliseconds = (long) (Math.random() * 5000 + 100);
            threadPool.submit(() -> {
                try {
                    // 陷入等待状态
                    System.out.println(String.format("任务%d已就绪", finalI));
                    begin.await();
                    // 模拟任务耗费时间
                    TimeUnit.MILLISECONDS.sleep(milliseconds);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    System.out.println(String.format("任务%d执行完毕，耗费时间=%d", finalI, milliseconds));
                    end.countDown();
                }
            });
        }
        System.out.println("等待各任务就绪");
        TimeUnit.SECONDS.sleep(2);
        System.out.println("开始执行任务");
        begin.countDown();
        try {
            end.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("finish");
    }

    public void testCyclicBarrier() throws InterruptedException {
        int taskNum = 5;
        CyclicBarrier cyclicBarrier = new CyclicBarrier(taskNum, () -> System.out.println("各任务均已就绪，开始后续操作"));
        ExecutorService threadPool = Executors.newFixedThreadPool(taskNum);
        for (int i = 0; i < taskNum; i++) {
            int finalI = i + 1;
            threadPool.submit(() -> {
                try {
                    // 模拟准备时间
                    long time = (long) (Math.random() * 10000);
                    TimeUnit.MILLISECONDS.sleep(time);
                    System.out.println(String.format("任务%d已就绪, 耗时=%d", finalI, time));
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            });
        }
        threadPool.shutdown();
        threadPool.awaitTermination(30, TimeUnit.SECONDS);
    }
}
