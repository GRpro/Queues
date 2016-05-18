package com.kpi.queue.fifo;

public class Processor implements Runnable {
    private String name;
    private Queue inputTasksQueue;
    private Task currentTask;
    private int finishedTasksCounter = 0;
    private long workingTime = 0;
    private long startTime;
    private double returnTime = 0;
    private double waitingTime = 0;
    private double[] wait = {0, 0, 0, 0, 0};
    private double[] count = {0, 0, 0, 0, 0};

    public Processor(String name, Queue inputQueue) {
        this.name = name;
        inputTasksQueue = inputQueue;
        startTime = System.currentTimeMillis();
    }

    @Override
    public void run() {
//        while (true) {
//            try {
//                currentTask = inputTasksQueue.removeTask();
//                long currentWaitingTime = System.currentTimeMillis()
//                        - currentTask.getCreationTime();
//                waitingTime += currentWaitingTime;
//                for (int i = 0; i < wait.length; i++)
//                    wait[i] += currentWaitingTime;
//                count[currentTask.getPriority() - 1]++;
//                Thread.sleep(currentTask.getLength());
//                finishedTasksCounter++;
//                workingTime += currentTask.getLength();
//                double lifeTime = System.currentTimeMillis() - startTime;
//                double cpu = (workingTime / lifeTime) * 100;
//                long currentReturnTime = System.currentTimeMillis()
//                        - currentTask.getCreationTime();
//                returnTime += currentReturnTime;
//                double throughput = finishedTasksCounter / (lifeTime / 1000);
//                System.out
//                        .printf("%d\t%02.2f%%\t%03.2f\t%05.2f ms\t%05.2f ms\t%.2f\t%.2f\t%.2f\t%.2f\t%.2f\n",
//                                finishedTasksCounter, cpu, throughput,
//                                (returnTime / finishedTasksCounter),
//                                (waitingTime / finishedTasksCounter), wait[4]
//                                        / count[4], wait[3] / count[3], wait[2]
//                                        / count[2], wait[1] / count[1], wait[0]
//                                        / count[0]);
//            } catch (InterruptedException e) {
//                System.out.println("interrupt on sleep");
//                Thread.currentThread().interrupt();
//            }
//        }
    }

    private void stat(Task task) {
    }
}
