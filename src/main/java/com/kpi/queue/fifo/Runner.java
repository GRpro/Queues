package com.kpi.queue.fifo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.java.Log;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;


/**
 *
 */
@Log
public class Runner {

  static {
//    ConsoleHandler handler = new ConsoleHandler();
//    handler.setLevel(Level.FINE);
//    log.addHandler(handler);
    log.setLevel(Level.INFO);

  }


  private BlockingQueue<Task> queue;
  private CountDownLatch fifoStopIndicator;
  private Thread consumer;
  private Thread producer;
  private ThreadGroup group;

  @Getter
  private final Metric metric;


  public Runner() {
    queue = new LinkedBlockingQueue<>();
    fifoStopIndicator = new CountDownLatch(2);
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
    try {
      fifoStopIndicator.await();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  @ToString
  private class Metric {

    int avgQueueLen = 0;
    @Setter long procStartTime = 0;
    @Setter long procEndTime = 0;

    long drafeTime = 0;

    private long lastModificationLen = System.currentTimeMillis();

    public synchronized void sayQueueLen(int len) {
      long curTime = System.currentTimeMillis();
      avgQueueLen += len * (curTime - lastModificationLen);
//      System.out.println("Len: " + len + " delta: " + (curTime - lastModificationLen));
      lastModificationLen = curTime;
    }

    public void addDrafeTime(long time) {
      drafeTime += time;
    }

    public double getAvgQueueLen() {
      return (double) avgQueueLen / (procEndTime - procStartTime);
    }

    public double getDrafeTime() {
      return (double) drafeTime / (procEndTime - procStartTime);
    }
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
          metric.sayQueueLen(queue.size());
          log.finer("Produced task: " + task);
          Thread.sleep(Util.genTaskDelay());
        }
      } catch (InterruptedException e) {
        // do nothing
      } finally {
        fifoStopIndicator.countDown();
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
      metric.setProcStartTime(System.currentTimeMillis());
      try {
        while (!isInterrupted()) {
          long t1 = System.currentTimeMillis();
          Task task = queue.take();
          long t2 = System.currentTimeMillis();
          metric.addDrafeTime(t2 - t1);
          metric.sayQueueLen(queue.size());
          log.finer("Consumed task: " + task);
          Thread.sleep(task.getWeight());
        }
      } catch (InterruptedException e) {
        // do nothing
      } finally {
        metric.setProcEndTime(System.currentTimeMillis());
        log.fine("Consumer interrupted");
        fifoStopIndicator.countDown();
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

    Metric m = runner.getMetric();
//    log.info(m.toString());
    log.info("Avg queue length: " + m.getAvgQueueLen());
    log.info("Avg proc drafe time: " + m.getDrafeTime());
  }
}



