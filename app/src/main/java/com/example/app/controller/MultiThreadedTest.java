package com.example.app.controller;

/**
 * @author XRS
 * @date 2024-08-14 下午 6:39
 */
public class MultiThreadedTest {
    /*
    多线程是值在同一个程序中执行不同的任务，每个线程都有自己的调用栈和程序计数器（PC）
    另外，多线程是共享堆和方法区的，多线程实现有两种方法：
    1，继承Thread类
    2，实现Runnable接口
     */
    public static final int thread_count = 300;
    public static final int append_count = 5000;

    public static void main(String[] args) throws InterruptedException {
        //testStringBuilder();
        testStringBuffer();
    }

    private static void testStringBuilder() throws InterruptedException {
        StringBuilder sb = new StringBuilder();

        //开了一个Thread类型的数组，长度丢了一个int
        Thread[] threads = new Thread[thread_count];

        //看起来像 不停的创建Thread，其内部把sb拼接5000次字符a
        //接着把Thread(线程对象)装入threads
        //每次拼五千，sb有点长
        for (int i = 0; i < thread_count; i++) {
            threads[i] = new Thread(() -> {
                //干什么? ()是谁 指向一个循环
                for (int j = 0; j < append_count; j++) {
                    sb.append("a");
                }
            });

            //Thread类提供了start()方法，‌该方法用于启动一个新线程
            //这个启动位置看起来也不知道启动了什么
            threads[i].start();
        }

        //必须要抛异常 ？  throws InterruptedException
        for (Thread thread : threads) {
            thread.join(); //等待线程全部执行完毕
        }

        if (sb.length() != thread_count * append_count) {
            throw new RuntimeException(sb.length() + ",Builder多线程拼接出错了");
        }

    }

    private static void testStringBuffer() throws InterruptedException {
        StringBuffer sb = new StringBuffer();

        Thread[] threads = new Thread[thread_count];

        for (int i = 0; i < thread_count; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < append_count; j++) {
                    sb.append("b");
                }
            });

            //每个线程拼接完后启动一下？？？？
            threads[i].start();
        }

        for (Thread thread : threads) {
            thread.join();
        }

        if (sb.length() != thread_count * append_count) {
            throw new RuntimeException(sb.length() + ",听说buffer多线程不会出错？数字稳对");
        } else {
            System.out.println("必然");
        }
    }
}
