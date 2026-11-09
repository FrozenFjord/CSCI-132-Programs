// Lachlan Mullen
package main.java.program4;

import java.util.LinkedList;

public class PriorityQueue {
    private LinkedList<Customer> queue;
    private LinkedList<Double> waitTimes;
    private int customersServed;

    public PriorityQueue() {
        queue = new LinkedList<>();
        waitTimes = new LinkedList<>();
        customersServed = 0;
    }

    public void enqueue(Customer customer) {
        queue.add(customer);
    }

    public Customer dequeue() {
        Customer customer = queue.poll();
        long waitTime = System.nanoTime() - customer.getWaitTime();

        customer.setWaitTime(waitTime);
        waitTimes.add((double) (waitTime / 1_000_000_000.0));

        return customer;
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
        System.out.println("Customers Served: " + customersServed);
        double averageWaitTime = 0;

        if (!waitTimes.isEmpty()) {
            double totalWaitTime = 0;
            for (Double time : waitTimes) {
                totalWaitTime += time;
            }
            averageWaitTime = totalWaitTime / waitTimes.size();
        } 

        System.out.println("Average Wait Time: " + averageWaitTime);
    }
}
