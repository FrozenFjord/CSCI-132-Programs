// Lachlan Mullen
package main.java.program4;


import java.util.Scanner;

public class Pizzeria {
    private PriorityQueue queue;
    private Scanner scanner;

    public Pizzeria() {
        queue = new PriorityQueue();
        scanner = new Scanner(System.in);
        userInput();
    }   

    private void userInput() {
        System.out.println("Welcome to the Pizzeria!");

        int userChoice;

        do {

            System.out.println();

            System.out.println("Enter your choice:");
            System.out.println("1. View queue");
            System.out.println("2. Add customer to queue");
            System.out.println("3. Add VIP to queue");
            System.out.println("4. Serve next customer");
            System.out.println("5. Remove customer from queue");
            System.out.println("6. Print queue statistics");
            System.out.println("7. Exit");
            System.out.print("Your choice: ");

            userChoice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character left by nextInt()

            switch (userChoice) {
            case 1:
                queue.printQueue();
                break;
            case 2:
                addCustomerToQueue(false);
                break;
            case 3:
                addCustomerToQueue(true);
                break;
            case 4:
                serveNextCustomer();
                break;
            case 5:
                removeCustomerFromQueue();
                break;
            case 6:
                queue.printQueueStatistics();
                break;
            case 7:
                System.out.println("Exiting...");
                break;
            default:
                System.out.println("Invalid choice.");
                break;
            }
            
        } while (userChoice != 7);

        scanner.close();
    }

    private void addCustomerToQueue(boolean isVip) {
        System.out.print("Enter customer name: ");
        String name = scanner.nextLine();
        System.out.print("Enter order: ");
        String order = scanner.nextLine();

        Customer customer = new Customer(name, order, isVip);
        queue.enqueue(customer);
    }

    private void serveNextCustomer() {
        if (!queue.isEmpty()) {
            Customer customer = queue.dequeue();

            int waitTime = (int) calculateWaitTime(customer);
            queue.addWaitTime(waitTime);
            System.out.printf("%d seconds%n", waitTime);
            printReview(customer, waitTime);
            
            queue.customerServed();
        } else {
            System.out.println("Queue is empty.");
        }
    }

    private void removeCustomerFromQueue() {
        if (queue.isEmpty()) {
            System.out.println("Queue is empty.");
            return;
        }

        System.out.print("Enter customer name to remove: ");
        String name = scanner.nextLine();
        Customer customer = null;

        for (Customer queuedCustomer : queue) {
            if (queuedCustomer.getName().equals(name)) {
                customer = queuedCustomer;
                break;
            }
        }

        if (customer != null) {
            int waitTime = (int) calculateWaitTime(customer);
            System.out.printf("%d seconds%n", waitTime);
            queue.dequeue(customer);
        } else {
            System.out.println("Customer not found.");
        }
    }


    private void printReview(Customer customer, double waitTime) {
       if (waitTime > 60) {
            System.out.println(customer.getName() + "'s review: *");
        } else if (waitTime >= 30) {
            System.out.println(customer.getName() + "'s review: **");
        } else {
            System.out.println(customer.getName() + "'s review: ***");
        } 
    }

    private double calculateWaitTime(Customer customer) {
        double waitTime = ((System.nanoTime() - customer.getWaitTime()) / 1_000_000_000.0);
        return waitTime;
    }

    public static void main(String[] args) {
        Pizzeria pizzeria = new Pizzeria();
    }
}
