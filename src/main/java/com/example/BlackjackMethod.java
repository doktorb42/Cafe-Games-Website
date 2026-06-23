package com.example;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import utils.BlackjackCard;

public class BlackjackMethod {
    public static List<BlackjackCard> createDeck(){
        List<BlackjackCard> deck = new ArrayList<>();
        String[] symbolCards = {"♠", "♥", "♦", "♣"};
        String[] numberCards = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"};
        for (String symbolCard : symbolCards) {
            for (String numberCard : numberCards) {
                int value;

                if (numberCard.equals("J") || numberCard.equals("Q") || numberCard.equals("K")) {
                    value = 10;
                } else if (numberCard.equals("A")) {
                    value = 11;
                } else {
                    value = Integer.parseInt(numberCard);
                }

                deck.add(new BlackjackCard(numberCard, symbolCard, value));
            }
        }
        Collections.shuffle(deck);
        return deck;
    }

    public static BlackjackCard drawCard(List<BlackjackCard> deck) {
        return deck.remove(0);
    }
    public static int calculateScore(List<BlackjackCard> hand) {
        int score = 0;
        int aces = 0;

        for (BlackjackCard card : hand) {
            score += card.getvalue();

            if (card.getsymbolCard().equals("A")) {
                aces++;
            }
        }

        while (score > 21 && aces > 0) {
            score -= 10;
            aces--;
        }

        return score;
    }
}
