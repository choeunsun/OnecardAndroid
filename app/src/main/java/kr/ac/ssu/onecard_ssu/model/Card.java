package kr.ac.ssu.onecard_ssu.model;

/**
 * Created by unseon on 2016-05-30.
 */
public class Card {
    private char shape; //카드모양 j=joker검 s=space파 c=clover초 d=diamond노 h=heart빨
    private int index; //카드 번호 최대 13
    private String id; //13*4+2=54
    private int image;

    public Card(char shape, int index, String id){
        this.shape=shape;
        this.index=index;
        this.id=id;
        //this.image=image;
    }
    public void setCard(char shape, int index, String id){
        this.shape=shape;
        this.index=index;
        this.id=id;
    }

    public char getShape(){return shape;}
    public int getIndex(){return index;}
    public String getId(){return id;}
    public int getImage(){return image;}
}
