// Lachlan Mullen

package main.java.program3;

import java.util.LinkedList;
import program3.Card;

public class Deck {
    private LinkedList<Card> deck;
    private Card top;
    private int size;

    public Deck () {
        this.deck = new LinkedList<>();
        this.top = null;
        this.size = 0;
    }

    public void push (Card newCard) {
        deck.addFirst(newCard);
        top = newCard;
        size++;
    }

    public void pop () {
        if (isEmpty()) {
            System.out.println("Deck is Empty...");
            return;
        } 
        deck.removeFirst();
        size--;

        if (isEmpty()) {
            top = null;
        } else {
            top = deck.getFirst();
        }
    }
    
    public Card peek () {
        return top;
    }

    public boolean isEmpty () {
        if (size == 0) {
            return true;
        }
        return false;
    }

    public int size() {
        return size;
    }

    
}
