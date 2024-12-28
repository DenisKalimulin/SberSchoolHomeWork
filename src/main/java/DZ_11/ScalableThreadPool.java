package DZ_11;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Реализация масштабируемого пула потоков (ScalableThreadPool).
 * Количество потоков динамически изменяется в пределах от minThreads до maxThreads.
 */
class ScalableThreadPool implements ThreadPool {
    private final int minThreads;
    private final int maxThreads;
    private final BlockingQueue<Runnable> taskQueue;
    private final AtomicBoolean isRunning = new AtomicBoolean(false);
    private final Object lock = new Object();
    private int currentThreads;
    private final ThreadGroup workerGroup;

    /**
     * Конструктор ScalableThreadPool.
     *
     * @param minThreads минимальное количество потоков
     * @param maxThreads максимальное количество потоков
     * @throws IllegalArgumentException если minThreads <= 0 или maxThreads < minThreads
     */
    public ScalableThreadPool(int minThreads, int maxThreads) {
        if (minThreads <= 0 || maxThreads < minThreads) {
            throw new IllegalArgumentException("Invalid thread pool size range");
        }
        this.minThreads = minThreads;
        this.maxThreads = maxThreads;
        this.taskQueue = new LinkedBlockingQueue<>();
        this.workerGroup = new ThreadGroup("ScalableThreadPoolGroup");
        this.currentThreads = 0;
    }

    /**
     * Запускает потоки пула. Изначально создается минимальное количество потоков.
     */
    @Override
    public void start() {
        if (isRunning.compareAndSet(false, true)) {
            synchronized (lock) {
                for (int i = 0; i < minThreads; i++) {
                    addWorker();
                }
            }
        }
    }

    /**
     * Добавляет задачу в очередь выполнения. При необходимости увеличивает количество потоков.
     *
     * @param runnable задача для выполнения
     * @throws IllegalStateException если пул потоков не запущен
     */
    @Override
    public void execute(Runnable runnable) {
        if (!isRunning.get()) {
            throw new IllegalStateException("ThreadPool not started");
        }
        taskQueue.offer(runnable);
        synchronized (lock) {
            if (taskQueue.size() > currentThreads && currentThreads < maxThreads) {
                addWorker();
            }
        }
    }

    /**
     * Добавляет новый поток в пул.
     */
    private void addWorker() {
        currentThreads++;
        Thread worker = new Worker(workerGroup, "Worker-" + currentThreads);
        worker.start();
    }

    /**
     * Уменьшает количество потоков при простое.
     */
    private void removeWorker() {
        synchronized (lock) {
            currentThreads--;
        }
    }

    /**
     * Внутренний класс Worker. Обрабатывает задачи из очереди.
     */
    private class Worker extends Thread {
        public Worker(ThreadGroup group, String name) {
            super(group, name);
        }

        @Override
        public void run() {
            while (isRunning.get() || !taskQueue.isEmpty()) {
                try {
                    Runnable task = taskQueue.poll();
                    if (task != null) {
                        task.run();
                    } else {
                        synchronized (lock) {
                            if (currentThreads > minThreads && taskQueue.isEmpty()) {
                                removeWorker();
                                break;
                            }
                        }
                    }
                } catch (Exception e) {
                    // Handle exception
                }
            }
        }
    }
}

