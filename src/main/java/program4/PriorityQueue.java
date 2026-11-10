// Lachlan Mullen
package main.java.program4;

import java.util.Collections;
import java.util.LinkedList;
import java.util.Iterator;

public class PriorityQueue implements Iterable<Customer> {
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
        Collections.sort(queue);
    }

    public Customer dequeue() {
        Customer customer = queue.poll();
        return customer;
    }

    public void dequeue(Customer customer) {
        queue.remove(customer);
    }
    

    public void customerServed() {
        customersServed++;
    }

    public void addWaitTime(double waitTime) {
        waitTimes.add(waitTime);
    }
    
    public boolean isEmpty() {
        return queue.isEmpty();
    }

    public int size() {
        return queue.size();
    }

    @Override
    public Iterator<Customer> iterator() {
        return queue.iterator();
    }

    public void printQueue() {

        if (queue.isEmpty()) {
            System.out.println("Queue is empty.");
        } else {
            for (Customer customer : queue) {
                System.out.println(customer);
            }
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

        System.out.printf("Average Wait Time: %.2f seconds%n", averageWaitTime);
    }
}
