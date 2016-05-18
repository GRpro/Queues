package com.kpi.queue.fifo;

import java.util.Random;

/**
 *
 */
public class Util {
  public static final int MAX_TASK_WEIGHT = 100;
  public static final int MIN_TASK_WEIGHT = 0;
  public static final int MAX_TASK_DELAY = 60;
  public static final int MIN_TASK_DELAY = 60;

  private static final Random random = new Random();

  public static int genTaskWeight() {
    return random.nextInt(MAX_TASK_WEIGHT - MIN_TASK_WEIGHT + 1)
        + MIN_TASK_WEIGHT;
  }

  public int genTaskDelay() {
    return random.nextInt(MAX_TASK_DELAY - MIN_TASK_DELAY + 1)
        + MIN_TASK_DELAY;
  }
}
