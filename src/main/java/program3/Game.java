package main.java.program3;

import java.util.Scanner;
import java.util.ArrayList;

public class Game {
    private Deck deck;
    
    public Game () {
        deck = createDrawPile();
    }

    public void userInput() {
        System.out.println("Welcome to Single Player Uno.");

        System.out.println("We will now deal your cards.");
    }

    private Deck createDrawPile() {
        ArrayList<Card> cards = new ArrayList<>();

        cards.add(new Card('R', 'D'));
        cards.add(new Card('R', 'D'));
        cards.add(new Card('R', 'W'));
        cards.add(new Card('R', '1'));
        cards.add(new Card('R', '1'));
        cards.add(new Card('R', '2'));
        cards.add(new Card('R', '3'));
        cards.add(new Card('R', '4'));

        cards.add(new Card('G', 'D'));
        cards.add(new Card('G', 'D'));
        cards.add(new Card('G', 'W'));
        cards.add(new Card('G', '1'));
        cards.add(new Card('G', '2'));
        cards.add(new Card('G', '2'));
        cards.add(new Card('G', '3'));
        cards.add(new Card('G', '4'));

        cards.add(new Card('B', 'D'));
        cards.add(new Card('B', 'D'));
        cards.add(new Card('B', 'W'));
        cards.add(new Card('B', '1'));
        cards.add(new Card('B', '2'));
        cards.add(new Card('B', '2'));
        cards.add(new Card('B', '3'));
        cards.add(new Card('B', '4')); 

        java.util.Arrays.shuffle(cards);

        Deck drawPile = new Deck();

        for (Card currentCard : cards) {
            drawPile.push(currentCard);
        }

        return drawPile;
    }
}
