package processors;

import processing.*;

import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Sum implements Processor {
    private static int taskId = 0;
    private String result;

    @Override
    public boolean submitTask(String task, StatusListener sl) {
        taskId++;
        AtomicInteger ai = new AtomicInteger(0);

        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

        executorService.scheduleAtFixedRate(
                ()->{
                    System.out.println("running"); // do debbugowania
                    ai.incrementAndGet();
                    sl.statusChanged(new Status(taskId,ai.get()));
                },
                1, 100, TimeUnit.MILLISECONDS);

        ExecutorService executor = Executors.newSingleThreadExecutor();

        executor.submit(() -> {
            while (true) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (ai.get() >= 100) {
                    result = String.valueOf(Arrays.stream(task.split("\\+")).mapToInt(Integer::parseInt).sum());
                    System.out.println("finished");
                    executorService.shutdown();
                    executor.shutdown();
                    break;
                }
            }
        });

        return true;
    }

    @Override
    public String getInfo() {
        return "Sum of two numbers";
    }

    @Override
    public String getResult() {
        return result;
    }
}
