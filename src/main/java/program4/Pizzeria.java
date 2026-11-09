// Lachlan Mullen
package main.java.program4;

import java.util.Scanner;

public class Pizzeria {
    private PriorityQueue queue;

    public Pizzeria() {
        queue = new PriorityQueue();
    }   

    public void userInput() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter your choice:");
        System.out.println("1. View queue");
        System.out.println("2. Add customer to queue");
        System.out.println("3. Add VIP to queue");
        System.out.println("4. Serve next customer");
        System.out.println("5. Remove customer from queue");
        System.out.println("6. Print queue statistics");
        System.out.println("7. Exit");
        System.out.print("Your choice: ");

        int userChoice = scanner.nextInt();

        switch (userChoice) {
            case 1:
                queue.printQueue();
                break;
            case 2:
                // Add customer to queue
                break;
            case 3:
                // Add VIP to queue
                break;
            case 4:
                break;
            case 5:
                // Remove customer from queue
                break;
            case 6:
                queue.printQueueStatistics();
                break;
            case 7:
                // Exit
                break;
            default:
                System.out.println("Invalid choice.");
                break;
        }
    }


}
