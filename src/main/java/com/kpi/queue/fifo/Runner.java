package com.kpi.queue.fifo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;


/**
 *
 */
public class Runner {
  private BlockingQueue<Task> queue = new LinkedBlockingQueue<>();
  private Thread producer;
  private Thread consumer;
  private ThreadGroup group;

  public Runner() {
    group = new ThreadGroup("FIFO");
    this.producer = new Producer();

    group.
    this.producer = new
  }

  public void start() {

  }

  public void stop() {

  }


  private interface Interruptable {
    void interrupt();
  }

  private class Producer implements Runnable, Interruptable {
    private AtomicBoolean interrupted = new AtomicBoolean(false);

    @Override
    public void run() {
      while (!interrupted.get()) {

      }
    }

    @Override
    public void interrupt() {
      interrupted.set(true);
    }
  }

  private class Consumer implements Runnable, Interruptable {
    private AtomicBoolean interrupted = new AtomicBoolean(true);

    @Override
    public void run() {
      while (!interrupted.get()) {

      }
    }

    @Override
    public void interrupt() {
      interrupted.set(true);
    }
  }


  public static void main(String[] args) {

  }
}



@ToString
@Getter @Setter
class Task {
  private int weight;
  /** creation time */
  private long crTime;

  public Task(int weight) {
    this.weight = weight;
    this.crTime = System.currentTimeMillis();
  }
}