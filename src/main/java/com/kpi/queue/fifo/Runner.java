package com.kpi.queue.fifo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.java.Log;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;


/**
 *
 */
@Log
public class Runner {

  static {
    ConsoleHandler handler = new ConsoleHandler();
    handler.setLevel(Level.FINE);
    log.setLevel(Level.FINE);
    log.addHandler(handler);
  }


  private BlockingQueue<Task> queue;
  private Thread consumer;
  private Thread producer;
  private ThreadGroup group;

  @Getter
  private Metric metric;


  public Runner() {
    queue = new LinkedBlockingQueue<>();
    group = new ThreadGroup("FIFO");
    producer = new Producer(group, "Producer");
    consumer = new Consumer(group, "Consumer");
    metric = new Metric();
  }

  public void start() {
    consumer.start();
    producer.start();
  }

  public void stop() {
    group.interrupt();
  }


  @ToString
  private class Metric {
    AtomicInteger avgQueueLen;
    AtomicInteger avgTaskTimeInQueue;
    AtomicInteger avgTaskSysTime;
  }

  private class Producer extends Thread {
    Producer(ThreadGroup group, String name) {
      super(group, name);
    }

    @Override
    public void run() {
      log.fine("Producer started");
      try {
        while (!isInterrupted()) {
          Task task = new Task(Util.genProcTime());
          queue.put(task);
          log.finer("Produced task: " + task);
          Thread.sleep(Util.genTaskDelay());
        }
      } catch (InterruptedException e) {
        // do nothing
      } finally {
        log.fine("Producer interrupted");
      }
    }
  }

  private class Consumer extends Thread {
    Consumer(ThreadGroup group, String name) {
      super(group, name);
    }

    @Override
    public void run() {
      log.fine("Consumer started");
      try {
        while (!isInterrupted()) {
          Task task = queue.take();
          log.finer("Consumed task: " + task);
          Thread.sleep(Util.genProcTime());
        }
      } catch (InterruptedException e) {
        // do nothing
      } finally {
        log.fine("Consumer interrupted");
      }
    }
  }

  @ToString
  @Getter @Setter
  class Task {
    private int weight;
    /** creation time */
    private long crTime;

    Task(int weight) {
      this.weight = weight;
      this.crTime = System.currentTimeMillis();
    }
  }


  public static void main(String[] args) {

    Runner runner = new Runner();
    runner.start();
    try {
      Thread.sleep(5000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    runner.stop();

    log.info(runner.getMetric().toString());
  }
}



