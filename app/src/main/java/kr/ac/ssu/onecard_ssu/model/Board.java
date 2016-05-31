package kr.ac.ssu.onecard_ssu.model;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by unseon on 2016-05-30.
 */
public class Board {
    public int playerNum;
    public Deque<Card> QCard;
    public User[] user;
    public ArrayList<Card> c;

    public Board(int playerNum){
        this.playerNum=playerNum;
        QCard=new LinkedList<Card>();
        user=new User[playerNum];
        c=new ArrayList<Card>();

        int id=0;
        for(int i=1;i<=13;i++){
            c.add(new Card('h', i, "h" + i));
            c.add(new Card('d',i,"d"+i));
            c.add(new Card('s',i,"s"+i));
            c.add(new Card('c', i, "c" + i));
        }
        c.add(new Card('j', 100, "j" + 100));
        c.add(new Card('j',200,"j"+200));

        while(c.size()>0){
            int i = (int)(Math.random()*c.size());
            QCard.offer(c.get(i));
            c.remove(i);
        }
    }
    public int getPlayerNum(){return playerNum;    }



}
