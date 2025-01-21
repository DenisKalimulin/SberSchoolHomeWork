package DZ_11;

public class Main {
    public static void main(String[] args) {
        ThreadPool pool = new ScalableThreadPool(2, 5);
        pool.start();

        for (int i = 0; i < 10; i++) {
            int taskId = i;
            pool.execute(() -> {
                System.out.println("Task " + taskId + " is running");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }
    }
}
