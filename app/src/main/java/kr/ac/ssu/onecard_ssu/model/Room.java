package kr.ac.ssu.onecard_ssu.model;

/**
 * Created by unseon on 2016-06-06.
 */
public class Room {
    private String room_name;
    private String is_private;
    private String room_pw;
    private String room_limit;
    private String user_cnt;
    private String room_id;

    public Room(String room_name, String is_private, String room_pw, String room_limit,String user_cnt, String room_id) {
        this.room_name = room_name;
        this.is_private = is_private;
        this.room_pw = room_pw;
        this.room_limit = room_limit;
        this.user_cnt=user_cnt;
        this.room_id = room_id;
    }

    public String getRoom_name() {
        return room_name;
    }

    public String getIs_private() {
        return is_private;
    }

    public String getRoom_pw() {
        return room_pw;
    }

    public String getRoom_limit() {
        return room_limit;
    }
    public String getUser_cnt()  {return user_cnt;}

    public String getRoom_id(){
        return room_id;
    }
}
