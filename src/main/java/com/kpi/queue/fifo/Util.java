package com.kpi.queue.fifo;

import java.util.Random;

/**
 *
 */
public class Util {
  public static final int MAX_TASK_PROC_TIME = 600;
  public static final int MIN_TASK_PROC_TIME = 600;
  public static final int MAX_TASK_DELAY = 500;
  public static final int MIN_TASK_DELAY = 500;

  private static final Random random = new Random();

  /**
   * Generates task processing time in milliseconds
   * @return time in milliseconds
   */
  public static int genProcTime() {
    return random.nextInt(MAX_TASK_PROC_TIME - MIN_TASK_PROC_TIME + 1) + MIN_TASK_PROC_TIME - 1;
  }

  /**
   * Generates delay between tasks
   * @return time milliseconds
   */
  public static long genTaskDelay() {
    return random.nextInt(MAX_TASK_DELAY - MIN_TASK_DELAY + 1) + MIN_TASK_DELAY - 1;
  }
}
