// Lachlan Mullen

package main.java.program3;

import java.util.Scanner;

import program3.UnoPrinter;
import program3.Card;

import java.util.ArrayList;

public class Game {
    private Deck deck;
    private Deck discardPile;
    private ArrayList<Card> hand;
    private Scanner input;
    
    public Game () {
        deck = createDrawPile();
        discardPile = new Deck();

        hand = new ArrayList<>();
        drawCards(6);

        input = new Scanner(System.in);
    }

    public void userInput() {
        int choice = -1;

        System.out.println("Welcome to Single Player Uno.");

        // Adds the first card to the discard pile
        discardPile.push(deck.peek());
        deck.pop();

        // Catches when the starting card is a draw
        if (discardPile.peek().getValue() == 'D') {
            drawCards(2);
        }

        System.out.println("We will now deal your cards.");
        
        do {
            
            // Checks if the player has met the win or lose conditions
            if (deck.size() == 0 && hand.size() == 0) {
                System.out.println("You have played every card. YOU WIN!");
                break;
            } else if (checkLoseCondition() && hand.size() != 0) {
                UnoPrinter.printGame(hand, discardPile.peek(), deck.peek(), deck.size());
                System.out.println("You can play no more cards. You lose...");
                break;
            }
            
            // Adds new cards if the player has played all the ones in their hand
            if (hand.size() == 0) {
                drawCards(6);
            }

            // Catches when the player has one card left and needs to say uno
            if (hand.size() == 1) {
                input.nextLine();

                if (sacredRule()) {
                    System.out.println("You lose.");
                    break;
                } else {
                   System.out.print("Enter a number corresponiding to the card you wish to play: ");
                    choice = input.nextInt();
                }

            } else {
                UnoPrinter.printGame(hand, discardPile.peek(), deck.peek(), deck.size());
    
                System.out.print("Enter a number corresponiding to the card you wish to play: ");
                choice = input.nextInt();
            }
            
            playCard(choice);
        } while (true); // Infinite loop because the win and lose conditions break out anyways

        System.out.println("Goodbye!");
    }

    // A method to populate the initial deck with the required cards
    private Deck createDrawPile() {
        ArrayList<Card> cards = new ArrayList<>(); // Arraylist used for a more efficient shuffle method
        char[] colors = {'R', 'G', 'B'};

        for (int i = 0; i < colors.length; i++) {
            cards.add(new Card(colors[i], 'D'));
            cards.add(new Card(colors[i], 'D'));
            cards.add(new Card(colors[i], 'W'));
            cards.add(new Card(colors[i], '1'));
            cards.add(new Card(colors[i], '2'));
            cards.add(new Card(colors[i], '3'));
            cards.add(new Card(colors[i], '4'));
            cards.add(new Card(colors[i], (char) ('1' + i))); // Used for the differing numbers required for the starting deck
        }

        java.util.Collections.shuffle(cards);

        Deck drawPile = new Deck();

        for (Card currentCard : cards) {
            drawPile.push(currentCard);
        }

        return drawPile;
    }

    /* A method that takes the user choice for a card and converts it into the actual card played
     * It also checks whether the number is valid, card played is valid, and when a draw card is played
     */
    private void playCard(int handIndex) {
        // Catches outOfBound card picks
        if (handIndex > hand.size() || handIndex <= 0) {
            System.out.println("That is not a valid card number.");
            System.out.print("Pick a new one: ");
            playCard(input.nextInt());
            return;
        }

        handIndex -= 1; // Normalizes the choice to work as an acutal index
        Card currentCard = hand.get(handIndex);
        Card topOfDiscard = discardPile.peek();

        if (compareCards(currentCard, topOfDiscard)) {
            hand.remove(handIndex);
            discardPile.push(currentCard);

        } else {
            // Handles when the chosen card is a valid index but invalid card
            System.out.println("You cannot play a " + currentCard + " on a " + topOfDiscard);
            System.out.print("Pick a new one: ");
            playCard(input.nextInt());
            return;
        }

        if (currentCard.getValue() == 'D') {
            drawCards(2);
        }
    }

    // Adds cards to hand based on the parameter amount and removes added cards from the deck
    private void drawCards(int amount) {
        if (amount > deck.size()) {
            amount = deck.size();
        }
        
        for (int i = 0; i < amount; i++) {
            hand.add(deck.peek());
            deck.pop();
        }
    }
    
    // Checks whether the lose condition of no playable cards has been met
    private boolean checkLoseCondition() {
        boolean lose = true;
        
        for (Card card : hand) {
            if (compareCards(card, discardPile.peek())) {
                lose = false;
            }
        }
        return lose;
    }
    
    // You musn't break it
    public boolean sacredRule() {
        UnoPrinter.printGame(hand, discardPile.peek(), deck.peek(), deck.size()); 
        
        System.out.print("Enter a number corresponiding to the card you wish to play: ");
        String s = input.nextLine();
        s = s.toUpperCase(); // Allows any capitalization variation of 'uno'
        if (!s.equals("UNO")) {
            System.out.println("You failed to say UNO...");
            return true;
        }
        return false;
    }

    /* Helper method to make the if statement logic in playCard and checkLoseCondition cleaner
     * Checks whether the card the user wants to play is valid
     */
    private boolean compareCards(Card played, Card discard) {
        return 
        played.getValue() == discard.getValue() || 
        played.getColor() == discard.getColor() || 
        played.getValue() == 'W';
    } 
}
