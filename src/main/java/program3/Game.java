package main.java.program3;

import java.util.Scanner;

import program3.UnoPrinter;
import program3.Card;

import java.util.ArrayList;

public class Game {
    private Deck deck;
    private Deck discardPile;
    private ArrayList<Card> hand;
    
    public Game () {
        deck = createDrawPile();
        discardPile = new Deck();

        hand = new ArrayList<>();
        dealHand();
    }

    public void userInput() {
        System.out.println("Welcome to Single Player Uno.");

        discardPile.push(deck.peek());
        deck.pop();

        System.out.println("We will now deal your cards.");
        UnoPrinter.printGame(hand, discardPile.peek(), deck.peek(), deck.size());
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

}
