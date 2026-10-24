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
        dealHand();

        input = new Scanner(System.in);
    }

    public void userInput() {
        int choice = -1;

        System.out.println("Welcome to Single Player Uno.");

        discardPile.push(deck.peek());
        deck.pop();

        // Catches when the starting card is a draw
        if (discardPile.peek().getValue() == 'D') {
            drawCards(2);
        }

        System.out.println("We will now deal your cards.");
        
        do {
            UnoPrinter.printGame(hand, discardPile.peek(), deck.peek(), deck.size());

            if (deck.size() == 0 && hand.size() == 0) {
                System.out.println("You have played every card. YOU WIN!");
                break;
            } else if (checkLoseCondition()) {
                System.out.println("You can play no more cards. You lose...");
                break;
            }
            
            System.out.print("Enter a number corresponiding to the card you wish to play: ");
            choice = input.nextInt();

            playCard(choice);
            
                

        } while (deck.size() != 0);

        System.out.println("Goodbye!");
    }

    private Deck createDrawPile() {
        ArrayList<Card> cards = new ArrayList<>();
        char[] colors = {'R', 'G', 'B'};

        for (int i = 0; i < colors.length; i++) {
            cards.add(new Card(colors[i], 'D'));
            cards.add(new Card(colors[i], 'D'));
            cards.add(new Card(colors[i], 'W'));
            cards.add(new Card(colors[i], '1'));
            cards.add(new Card(colors[i], '2'));
            cards.add(new Card(colors[i], '3'));
            cards.add(new Card(colors[i], '4'));
            cards.add(new Card(colors[i], (char) ('1' + i)));
        }

        java.util.Collections.shuffle(cards);

        Deck drawPile = new Deck();

        for (Card currentCard : cards) {
            drawPile.push(currentCard);
        }

        return drawPile;
    }

    private void dealHand() {
        for (int i = 0; i < 7; i++) {
            hand.add(deck.peek());
            deck.pop();
        }
    }

    private void playCard(int handIndex) {
        // Catches outOfBound card picks
        if (handIndex > hand.size()) {
            System.out.println("That is not a valid card number.");
            System.out.print("Pick a new one: ");
            playCard(input.nextInt() - 1);
        }

        handIndex -= 1;
        Card currentCard = hand.get(handIndex);
        Card topCard = discardPile.peek();

        if (currentCard.getValue() == 'W' || topCard.getValue() == 'W') {
            hand.remove(currentCard);
            discardPile.push(currentCard);

        } else if (compareCards(currentCard, topCard)) {
            hand.remove(handIndex);
            discardPile.push(currentCard);

            if (currentCard.getValue() == 'D') {
                drawCards(2);
            }
        } else {
            System.out.println("You cannot play a " + currentCard + " on a " + topCard);
            System.out.print("Pick a new one: ");
            playCard(input.nextInt());
        }
    }

    // Helper method to make the playCard method logic look cleaner
    private boolean compareCards(Card played, Card discard) {
        return 
        played.getValue() == discard.getValue() || 
        played.getColor() == discard.getColor();
    } 
    
    // Adds cards to hand based on the parameter amount and removes added cards from the deck
    private void drawCards(int amount) {
        for (int i = 0; i < amount; i++) {
            hand.add(deck.peek());
            deck.pop();
        }
    }

    // Checks whether any cards can be played with the current hand and discard pile
    private boolean checkLoseCondition() {
        boolean lose = true;

        Card discard = discardPile.peek();

        for (Card card : hand) {
            // Checks if there are any matching colors or values
            if (card.getValue() == discard.getValue() || card.getColor() == discard.getColor()) {
                lose = false;
            }

            // Checks for wilds
            if (card.getValue() == 'W' || discard.getValue() == 'W') {
                lose = false;
            }
        }

        return lose;
    }
}
