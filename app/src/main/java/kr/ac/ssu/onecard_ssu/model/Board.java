package kr.ac.ssu.onecard_ssu.model;

import android.content.Context;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;

import kr.ac.ssu.onecard_ssu.R;

/**
 * Created by unseon on 2016-05-30.
 */
public class Board {
    public int playerNum;
    public Deque<Card> QCard;
    public User[] user;
    public ArrayList<Card> c;
    private Context context;

    public Board(int playerNum, Context context){
        this.playerNum=playerNum;
        QCard=new LinkedList<Card>();
        user=new User[playerNum];
        c=new ArrayList<Card>();
        this.context = context;

        int id=0;
        for(int i=1;i<=13;i++){
            c.add(new Card(id++,'h', i, "h" + i, context.getResources().getIdentifier( "h"+i, "drawable",  "kr.ac.ssu.onecard_ssu")));
            c.add(new Card(id++,'d',i,"d"+i,context.getResources().getIdentifier( "d"+i, "drawable", "kr.ac.ssu.onecard_ssu" )));
            c.add(new Card(id++,'s',i,"s"+i,context.getResources().getIdentifier( "s"+i, "drawable", "kr.ac.ssu.onecard_ssu" )));
            c.add(new Card(id++,'c', i, "c" + i,context.getResources().getIdentifier( "c"+i, "drawable", "kr.ac.ssu.onecard_ssu" )));

        }
        c.add(new Card(id++,'j', 100, "j" + 100,context.getResources().getIdentifier( "j"+100, "drawable", "kr.ac.ssu.onecard_ssu" )));
        c.add(new Card(id++,'j',200,"j"+200,context.getResources().getIdentifier( "j"+200, "drawable", "kr.ac.ssu.onecard_ssu" )));

//        while(c.size()>0){
//            int i = (int)(Math.random()*c.size());
//            QCard.offer(c.get(i));
//            c.remove(i);
//        }
    }
    public int getPlayerNum(){return playerNum;    }



}
