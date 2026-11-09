// Lachlan Mullen
package main.java.program4;

import java.util.Collections;
import java.util.Scanner;

public class Pizzeria {
    private PriorityQueue queue;

    public Pizzeria() {
        queue = new PriorityQueue();
    }   

    public void userInput() {
        Scanner scanner = new Scanner(System.in);

        int userChoice;
        do {

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

            Collections.sort(queue);
            
        } while (userChoice != 7);
    }

    private void addCustomerToQueue(boolean isVip) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter customer name: ");
        String name = scanner.nextLine();
        System.out.print("Enter order: ");
        String order = scanner.nextLine();

        Customer customer = new Customer(name, order, isVip);
        queue.addCustomer(customer);        
    }

    private void serveNextCustomer() {
        // Implementation for serving the next customer
    }

    private void removeCustomerFromQueue() {
        // Implementation for removing a customer from the queue
    }

    public static void main(String[] args) {
        Pizzeria pizzeria = new Pizzeria();
        pizzeria.userInput();
    }
}
