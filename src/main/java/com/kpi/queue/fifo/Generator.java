package com.kpi.queue.fifo;

import java.util.Random;

public class Generator implements Runnable {
    public static final int MAX_TASK_LENGTH = 100;
    public static final int MIN_TASK_LENGTH = 0;
    public static final int MAX_DELAY_BETWEEN_TASKS = 60;
    public static final int MIN_DELAY_BETWEEN_TASKS = 60;
    public static final int MAX_TASK_PRIORITY = 5;
    public static final int MIN_TASK_PRIORITY = 1;
    private Queue queue;
    private Random random = new Random();

    public Generator(Queue queue) {
        this.queue = queue;
    }

    public int generateTaskLength() {
        return random.nextInt(MAX_TASK_LENGTH - MIN_TASK_LENGTH + 1)
                + MIN_TASK_LENGTH;
    }

    public int generateDelayBetweenTasks() {
        return random.nextInt(MAX_DELAY_BETWEEN_TASKS - MIN_DELAY_BETWEEN_TASKS
                + 1)
                + MIN_DELAY_BETWEEN_TASKS;
    }

    public int generateTaskPriority() {
        return random.nextInt(MAX_TASK_PRIORITY - MIN_TASK_PRIORITY + 1)
                + MIN_TASK_PRIORITY;
    }

    public void run() {
        while (true) {
            queue.addTask(new Task(generateTaskLength(), generateTaskPriority()));
            try {
                Thread.sleep(generateDelayBetweenTasks());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}



