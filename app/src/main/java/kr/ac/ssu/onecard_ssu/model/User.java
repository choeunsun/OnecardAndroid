package kr.ac.ssu.onecard_ssu.model;

import java.util.ArrayList;

/**
 * Created by unseon on 2016-05-30.
 */
public class User {
    private int cardCount;
    private ArrayList<Card> havingCard;

    public User(){
        havingCard= new ArrayList<Card>();
        cardCount=0;
    }

    public int getCardCount(){
       return this.cardCount;
    }

    ArrayList<Card> getHavingCard(){
        return havingCard;
    }

    public void receiveCard(Card c){
        havingCard.add(c);
        cardCount++;
    }

    public void sendCard(int index){
        cardCount--;
        havingCard.remove(index);
    }

    public Card getCard(int index){
        return havingCard.get(index);
    }
}
