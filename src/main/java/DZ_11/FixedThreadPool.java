package DZ_11;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Реализация фиксированного пула потоков (FixedThreadPool).
 * Количество потоков задается в конструкторе и остается неизменным.
 */
class FixedThreadPool implements ThreadPool {
    private final int threadCount;
    private final BlockingQueue<Runnable> taskQueue;
    private final Thread[] threads;
    private final AtomicBoolean isRunning = new AtomicBoolean(false);

    /**
     * Конструктор FixedThreadPool.
     *
     * @param threadCount количество потоков в пуле
     * @throws IllegalArgumentException если threadCount меньше или равно 0
     */
    public FixedThreadPool(int threadCount) {
        if (threadCount <= 0) {
            throw new IllegalArgumentException("Thread count must be greater than 0");
        }
        this.threadCount = threadCount;
        this.taskQueue = new LinkedBlockingQueue<>();
        this.threads = new Thread[threadCount];
    }

    /**
     * Запускает потоки пула. Потоки ожидают задачи в очереди.
     */
    @Override
    public void start() {
        if (isRunning.compareAndSet(false, true)) {
            for (int i = 0; i < threadCount; i++) {
                threads[i] = new Worker();
                threads[i].start();
            }
        }
    }

    /**
     * Добавляет задачу в очередь выполнения.
     *
     * @param runnable задача для выполнения
     * @throws IllegalStateException если пул потоков не запущен
     */
    public void execute(Runnable runnable) {
        if (!isRunning.get()) {
            throw new IllegalStateException("ThreadPool not started");
        }
        try {
            taskQueue.put(runnable);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Внутренний класс Worker. Обрабатывает задачи из очереди.
     */
    private class Worker extends Thread {
        @Override
        public void run() {
            while (isRunning.get()) {
                try {
                    Runnable task = taskQueue.take(); // Waits for a task
                    task.run();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
}
