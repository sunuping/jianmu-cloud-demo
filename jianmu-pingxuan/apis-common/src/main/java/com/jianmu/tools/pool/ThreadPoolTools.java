package com.jianmu.tools.pool;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程池创建
 *
 * @author lime
 */
@Slf4j
public class ThreadPoolTools {
    public static final Integer CPU_NUM = Runtime.getRuntime().availableProcessors();

    public static ThreadPoolExecutor executor = new ThreadPoolExecutor(CPU_NUM + 1, CPU_NUM * 2, 10,
            TimeUnit.SECONDS,
            new ResizableLinkedBlockingQueue<>(100),
            new ThreadFactoryBuilder().setNameFormat("thread-pool-%d").build(),
            (r, executor) -> {
                if (!executor.isShutdown()) {
                    try {
                        ResizableLinkedBlockingQueue<?> queue = (ResizableLinkedBlockingQueue<?>) executor.getQueue();
                        if (executor.getQueue().remainingCapacity() < 1) {
                            log.debug("队列数量 {}", queue.size());
                            queue.setCapacity(queue.getCapacity() + 10);
                            print(executor, "当前队列已满自动扩增10");
                        }
                        executor.getQueue().put(r);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });

    public static void print(ThreadPoolExecutor executor, String name) {
        ResizableLinkedBlockingQueue<?> queue = (ResizableLinkedBlockingQueue<?>) executor.getQueue();
        log.debug(Thread.currentThread().getName() + "-" + name + "-:" + "核心线程数:" + executor.getCorePoolSize() +
                " 活动线程数:" + executor.getActiveCount() + " 最大线程数:" + executor.getMaximumPoolSize() +
                " 线程活跃度:" + divide(executor.getActiveCount(), executor.getMaximumPoolSize()) +
                " 任务完成数:" + executor.getCompletedTaskCount() +
                " 队列大小:" + (queue.size() + queue.remainingCapacity()) +
                " 当前排队线程数:" + queue.size() +
                " 队列剩余大小:" + queue.remainingCapacity() +
                " 队列使用度:" + divide(queue.size(), queue.size() + queue.remainingCapacity()));
    }

    public static String divide(int num1, int num2) {
        return String.format("%1.2f%%", Double.parseDouble(num1 + "") / Double.parseDouble(num2 + "") * 100);
    }

}
