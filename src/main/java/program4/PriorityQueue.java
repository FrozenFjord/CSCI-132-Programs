// Lachlan Mullen
package main.java.program4;

import java.util.LinkedList;

public class PriorityQueue {
    private LinkedList<Customer> queue;
    private int customersServed;

    public PriorityQueue() {
        queue = new LinkedList<>();
    }

    public void enqueue(Customer customer) {
        queue.add(customer);
    }

    public Customer dequeue() {
        return queue.poll();
    }

    public void customerServed() {
        customersServed++;
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }

    public int size() {
        return queue.size();
    }

    public void printQueue() {
        for (Customer customer : queue) {
            System.out.println(customer);
        }
    }

    public void printQueueStatistics() {
        // To be implemented
        System.out.println("Test Queue Statistics");
    }
}
