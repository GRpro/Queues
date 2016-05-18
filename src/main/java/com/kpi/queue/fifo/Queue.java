package com.kpi.queue.fifo;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.PriorityQueue;

public class Queue implements ActionListener {
    private java.util.Queue<Task> queue;
    private String name;

    public Queue(String name) {
        this.name = name;
//        queue = new PriorityQueue<>(10, new TaskPriorityComparator());
    }

    public synchronized void addTask(Task task) {
        queue.add(task);
        notifyAll();
    }

    public synchronized Task removeTask() throws InterruptedException {
        while (queue.size() == 0) {
            wait();
        }
        return queue.poll();
    }

    public synchronized int size() {
        return queue.size();
    }

    public String toString() {
        return name + " : " + size();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        synchronized (this) {
            Iterator<Task> it = queue.iterator();
            while (it.hasNext()) {
//                Task current = it.next();
//                if (current.getPriority() < Generator.MAX_TASK_PRIORITY)
//                    current.setPriority(current.getPriority() + 1);
            }
        }
    }
}