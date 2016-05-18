package com.kpi.queue.fifo;

import javax.swing.Timer;

public class Main {
    public static void main(String[] args) {
        Queue queue = new Queue("queue");
        Generator generator = new Generator(queue);
        Processor processor = new Processor("cpu", queue);
        Timer timer = new Timer(Generator.MAX_TASK_LENGTH * 10, queue);
        Thread generatorThread = new Thread(generator);
        Thread cpuThread = new Thread(processor);
        generatorThread.start();
        cpuThread.start();
        timer.start();
    }
}
       