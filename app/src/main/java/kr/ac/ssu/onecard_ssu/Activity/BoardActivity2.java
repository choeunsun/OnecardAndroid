package kr.ac.ssu.onecard_ssu.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.Scanner;

import kr.ac.ssu.onecard_ssu.R;
import kr.ac.ssu.onecard_ssu.model.Board;
import kr.ac.ssu.onecard_ssu.model.Card;
import kr.ac.ssu.onecard_ssu.model.User;

/**
 * Created by unseon on 2016-05-30.
 */
public class BoardActivity2 extends AppCompatActivity {
    Board board;
    int turn=0;
    Card topCard;

    /*
     setContentView(R.layout.activity_board);

        RelativeLayout frameLayout_p1 = (RelativeLayout) findViewById(R.id.fl_board_p1_card);
        for(int i=0; i<30; i++){
            ImageView iv = new ImageView(getApplicationContext());
            iv.setBackgroundResource(R.mipmap.ic_launcher);
            frameLayout_p1.addView(iv);
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) iv.getLayoutParams();
            params.leftMargin = i * 50;
            iv.setLayoutParams(params);
     */

    boolean turn_plus=true;
    boolean jump=false;
    boolean oneMore=false;

    int attack=0;
    char sevenShape;

    public void onCreate(Bundle saveInstance){
        super.onCreate(saveInstance);
        setContentView(R.layout.activity_main); // todo Make Layout

        init();

        while(true){
            play();
        }

    }

    public void init(){
        board=new Board(4,getApplicationContext());

        for(int i=0;i<7;i++){
            for(int j=0;i<board.getPlayerNum();j++){
                board.user[j].receiveCard(board.QCard.poll());
            }
        }
        topCard = board.QCard.poll();
    }

    public void play(){
        Scanner s;
        int playerPickNum;
        Card c;

        System.out.println(turn+"'s turn");

        for(int i=0;i<board.user[turn].getCardCount();i++) {
            System.out.println("top card: "+topCard.getId());
            System.out.print(board.user[turn].getCard(i).getId() + "  ");
            System.out.println("\n0부터 n 중 카드를 고르세요~(하트)");
        }
        s=new Scanner(System.in);
        playerPickNum=s.nextInt();
        if(checkCanPickCard(board.user[turn].getCard(playerPickNum))){
            board.QCard.push(board.user[turn].getCard(playerPickNum));
            topCard=board.user[turn].getCard(playerPickNum);
            board.user[turn].sendCard(playerPickNum);
            nextTurn(board.getPlayerNum());
        }
        else{ //todo turn 넘기기 button 추가
            for(int i=0;i<attack;i++){
                board.user[turn].receiveCard(board.QCard.poll());
            }
            attack=0;
            nextTurn(board.getPlayerNum());
        }

    }

    public boolean checkCanPickCard(Card card)
    {
        boolean flag=false;
        if(card.getShape()=='j'){
            attack+=10;
            flag = true;
        }
        else if(topCard.getShape()==card.getShape()){
            if(topCard.getIndex()==7){
                if(sevenShape==card.getShape())
                    flag=true;
            }
            if(attack>0){
                if(!((card.getIndex()==1) || (card.getIndex()==2))){
                    flag=false;
                }
            }
        }
        else if(topCard.getIndex()==card.getIndex()){
            flag=true;
        }
        else{
            flag=false;
        }

        if(flag){
            if(topCard.getIndex()==1){
                if(topCard.getShape()=='s')
                    attack+=5;
                else
                   attack+=3;
            }
            else if(topCard.getIndex()==2){
                attack+=2;
            }
            else if(topCard.getIndex()==11){
                jump=true;
            }
            else if(topCard.getIndex()==12){
                turn_plus=!turn_plus;
            }
            else if(topCard.getIndex()==13) {
                oneMore = true;
            }
            else if(topCard.getIndex()==7){
                selectShape();
            }
        }
        return flag;
    }

    public void selectShape()
    {
        System.out.println("c s d h");
        Scanner s = new Scanner(System.in);
        sevenShape = s.next().charAt(0);
    }

    public void nextTurn(int playerNum)
    {
        int plus;

        if(oneMore){
            plus=0;
            oneMore=!oneMore;
        }
        else if(jump){
            jump=!jump;
            plus=2;
        }
        else{
            plus=1;
        }

        if(!turn_plus){
            plus*=-1;
        }

        turn = (turn + plus) % playerNum;
    }
}
