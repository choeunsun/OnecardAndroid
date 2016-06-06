package kr.ac.ssu.onecard_ssu.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import kr.ac.ssu.onecard_ssu.R;
import kr.ac.ssu.onecard_ssu.model.Room;

/**
 * Created by unseon on 2016-06-04.
 */

public class RoomActivity extends AppCompatActivity {
    LinearLayout room1, room2, room3, room4, room5, room6;
    LinearLayout[] room;
    Button btn_room_makeroom, btn_room_prev, btn_room_next;
    ArrayList<Room> roomlist;
    int pos=0, room_count=0;
    TextView name1, crt1, all1;
    ImageView lock1;
    TextView name2, crt2, all2;
    ImageView lock2;
    TextView name3, crt3, all3;
    ImageView lock3;
    TextView name4, crt4, all4;
    ImageView lock4;
    TextView name5, crt5, all5;
    ImageView lock5;
    TextView name6, crt6, all6;
    ImageView lock6;
    String user_id, user_nickname;
    SharedPreferences user_info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.room_layout);

        init();
        room_count=0;
        resetRoom();
        if(room_count==0){

        }

        btn_room_makeroom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), MakeroomActivity.class);
                startActivity(i);
            }
        });
        btn_room_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pos++;
                resetRoomAll();
            }
        });
        btn_room_prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pos > 0)
                    pos--;
                resetRoomAll();
            }
        });

    }

    public void resetRoomAll(){
        int n=0;
        if(n<room_count) {
            resetRoom1(roomlist.get(n).getRoom_name(), roomlist.get(n).getUser_cnt()
                    , roomlist.get(n).getRoom_limit()
                    , roomlist.get(n).getIs_private());
            n++;
        }
        if(n<room_count) {
            resetRoom2(roomlist.get(n).getRoom_name(), roomlist.get(n).getUser_cnt()
                    , roomlist.get(n).getRoom_limit()
                    , roomlist.get(n).getIs_private());
            n++;
        }
        if(n<room_count) {
            resetRoom3(roomlist.get(n).getRoom_name(), roomlist.get(n).getUser_cnt()
                    , roomlist.get(n).getRoom_limit()
                    , roomlist.get(n).getIs_private());
            n++;
        }
        if(n<room_count) {
            resetRoom4(roomlist.get(n).getRoom_name(), roomlist.get(n).getUser_cnt()
                    , roomlist.get(n).getRoom_limit()
                    , roomlist.get(n).getIs_private());
            n++;
        }
        if(n<room_count) {
            resetRoom5(roomlist.get(n).getRoom_name(), roomlist.get(n).getUser_cnt()
                    , roomlist.get(n).getRoom_limit()
                    , roomlist.get(n).getIs_private());
            n++;
        }
        if(n<room_count) {
            resetRoom6(roomlist.get(n).getRoom_name(), roomlist.get(n).getUser_cnt()
                    , roomlist.get(n).getRoom_limit()
                    , roomlist.get(n).getIs_private());
            n++;
        }
    }

    public void resetRoom1(String name, String crt,String all, String lock){
        name1.setText(name);
        crt1.setText(crt);
        all1.setText(all);
        if(lock.equals("on"))
            lock1.setVisibility(View.VISIBLE);
        else
            lock1.setVisibility(View.INVISIBLE);
    }
    public void resetRoom2(String name, String crt,String all, String lock){
        name2.setText(name);
        crt2.setText(crt);
        all2.setText(all);
        if(lock.equals("on"))
            lock2.setVisibility(View.VISIBLE);
        else
            lock2.setVisibility(View.INVISIBLE);
    }
    public void resetRoom3(String name, String crt,String all, String lock){
        name3.setText(name);
        crt3.setText(crt);
        all3.setText(all);
        if(lock.equals("on"))
            lock3.setVisibility(View.VISIBLE);
        else
            lock3.setVisibility(View.INVISIBLE);
    }
    public void resetRoom4(String name, String crt,String all, String lock){
        name4.setText(name);
        crt4.setText(crt);
        all4.setText(all);
        if(lock.equals("on"))
            lock4.setVisibility(View.VISIBLE);
        else
            lock4.setVisibility(View.INVISIBLE);
    }
    public void resetRoom5(String name, String crt,String all, String lock){
        name5.setText(name);
        crt5.setText(crt);
        all5.setText(all);
        if(lock.equals("on"))
            lock5.setVisibility(View.VISIBLE);
        else
            lock5.setVisibility(View.INVISIBLE);
    }
    public void resetRoom6(String name, String crt,String all, String lock){
        name6.setText(name);
        crt6.setText(crt);
        all6.setText(all);
        if(lock.equals("on"))
            lock6.setVisibility(View.VISIBLE);
        else
            lock6.setVisibility(View.INVISIBLE);
    }

    public void resetRoom(){
        //네트워크 연결 부분
        //리스트를 받아와서 roomlist에 넣어줌.
        RequestUtil.get("http://133.130.115.228:7010/user/roomlist?start_index=" + (pos * 6)
                + "&end_index=" + (pos * 6 + 5), new Request() {
            @Override
            public void onSuccess(String receiveData) {
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(receiveData);
                    int result_code = jsonObject.optInt("result_code", -1);
                    if (result_code == 0) {
                        String roomArrayString = jsonObject.getString("list_channel");
                        JSONArray jsonArray = new JSONArray(roomArrayString);
                        roomlist.clear();
                        room_count = jsonArray.length();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject j = jsonArray.getJSONObject(i);
                            Room r = new Room(j.getString("room_name"), j.getString("is_private")
                                    , j.getString("room_pw"), j.getString("room_limit")
                                    , j.getString("user_cnt"), j.getString("room_id"));
                            roomlist.add(r);
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                resetRoomAll();
                            }
                        });

                    } else {
                        room_count = 0;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFail(String url, String error) {
                // 서버와 연결이 되지 않았을 때
            }
        });
    }


   public void onClick1(View v){
       Toast.makeText(getApplicationContext(),"room",Toast.LENGTH_LONG).show();

        try{
       RequestUtil.get("http://133.130.115.228:7010/user/joinroom?room_id="
               + roomlist.get(0).getRoom_id()
               + "&user_nick=" + URLEncoder.encode(user_nickname, "UTF-8")
               + "&user_id="+URLEncoder.encode(user_id, "UTF-8"), new Request() {
           @Override
           public void onSuccess(String receiveData) {
               JSONObject jsonObject = null;
               try {
                   jsonObject = new JSONObject(receiveData);
                   int result_code = jsonObject.optInt("result_code", -1);
                   if (result_code == 0) {

                       //todo BoardActivity 미완
                       Intent i = new Intent(getApplicationContext(), BoardActivity.class);
                       i.putExtra("user_id",user_id);
                       i.putExtra("user_nickname",user_nickname);
                       i.putExtra("room_id",roomlist.get(0).getRoom_id());
                       startActivity(i);
                       finish();

                   } else {
                       //todo 풀방
                   }
               } catch (JSONException e) {
                   e.printStackTrace();
               }
           }

           @Override
           public void onFail(String url, String error) {
               // 서버와 연결이 되지 않았을 때
           }
       });
        }catch(UnsupportedEncodingException e) {
           e.printStackTrace();
       }

   }
    public void onClick2(View v){
        Toast.makeText(getApplicationContext(),"room",Toast.LENGTH_LONG).show();

        try{
            RequestUtil.get("http://133.130.115.228:7010/user/joinroom?room_id="
                    + roomlist.get(1).getRoom_id()
                    + "&user_nick=" + URLEncoder.encode(user_nickname, "UTF-8")
                    + "&user_id="+URLEncoder.encode(user_id, "UTF-8"), new Request() {
                @Override
                public void onSuccess(String receiveData) {
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(receiveData);
                        int result_code = jsonObject.optInt("result_code", -1);
                        if (result_code == 0) {

                            //todo BoardActivity 미완
                            Intent i = new Intent(getApplicationContext(), BoardActivity.class);
                            i.putExtra("user_id",user_id);
                            i.putExtra("user_nickname",user_nickname);
                            i.putExtra("room_id",roomlist.get(1).getRoom_id());
                            startActivity(i);
                            finish();

                        } else {
                            //todo 풀방
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFail(String url, String error) {
                    // 서버와 연결이 되지 않았을 때
                }
            });
        }catch(UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }
    public void onClick3(View v){
        Toast.makeText(getApplicationContext(),"room",Toast.LENGTH_LONG).show();

        try{
            RequestUtil.get("http://133.130.115.228:7010/user/joinroom?room_id="
                    + roomlist.get(2).getRoom_id()
                    + "&user_nick=" + URLEncoder.encode(user_nickname, "UTF-8")
                    + "&user_id="+URLEncoder.encode(user_id, "UTF-8"), new Request() {
                @Override
                public void onSuccess(String receiveData) {
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(receiveData);
                        int result_code = jsonObject.optInt("result_code", -1);
                        if (result_code == 0) {

                            //todo BoardActivity 미완
                            Intent i = new Intent(getApplicationContext(), BoardActivity.class);
                            i.putExtra("user_id",user_id);
                            i.putExtra("user_nickname",user_nickname);
                            i.putExtra("room_id",roomlist.get(2).getRoom_id());
                            startActivity(i);
                            finish();

                        } else {
                            //todo 풀방
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFail(String url, String error) {
                    // 서버와 연결이 되지 않았을 때
                }
            });
        }catch(UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }
    public void onClick4(View v){
        Toast.makeText(getApplicationContext(),"room",Toast.LENGTH_LONG).show();

        try{
            RequestUtil.get("http://133.130.115.228:7010/user/joinroom?room_id="
                    + roomlist.get(3).getRoom_id()
                    + "&user_nick=" + URLEncoder.encode(user_nickname, "UTF-8")
                    + "&user_id="+URLEncoder.encode(user_id, "UTF-8"), new Request() {
                @Override
                public void onSuccess(String receiveData) {
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(receiveData);
                        int result_code = jsonObject.optInt("result_code", -1);
                        if (result_code == 0) {

                            //todo BoardActivity 미완
                            Intent i = new Intent(getApplicationContext(), BoardActivity.class);
                            i.putExtra("user_id",user_id);
                            i.putExtra("user_nickname",user_nickname);
                            i.putExtra("room_id",roomlist.get(3).getRoom_id());
                            startActivity(i);
                            finish();

                        } else {
                            //todo 풀방
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFail(String url, String error) {
                    // 서버와 연결이 되지 않았을 때
                }
            });
        }catch(UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }
    public void onClick5(View v){
        Toast.makeText(getApplicationContext(),"room",Toast.LENGTH_LONG).show();

        try{
            RequestUtil.get("http://133.130.115.228:7010/user/joinroom?room_id="
                    + roomlist.get(4).getRoom_id()
                    + "&user_nick=" + URLEncoder.encode(user_nickname, "UTF-8")
                    + "&user_id="+URLEncoder.encode(user_id, "UTF-8"), new Request() {
                @Override
                public void onSuccess(String receiveData) {
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(receiveData);
                        int result_code = jsonObject.optInt("result_code", -1);
                        if (result_code == 0) {

                            //todo BoardActivity 미완
                            Intent i = new Intent(getApplicationContext(), BoardActivity.class);
                            i.putExtra("user_id",user_id);
                            i.putExtra("user_nickname",user_nickname);
                            i.putExtra("room_id",roomlist.get(4).getRoom_id());
                            startActivity(i);
                            finish();
                        } else {
                            //todo 풀방
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                @Override
                public void onFail(String url, String error) {
                    // 서버와 연결이 되지 않았을 때
                }
            });
        }catch(UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
    public void onClick6(View v){
        Toast.makeText(getApplicationContext(),"room",Toast.LENGTH_LONG).show();

        try{
            RequestUtil.get("http://133.130.115.228:7010/user/joinroom?room_id="
                    + roomlist.get(5).getRoom_id()
                    + "&user_nick=" + URLEncoder.encode(user_nickname, "UTF-8")
                    + "&user_id="+URLEncoder.encode(user_id, "UTF-8"), new Request() {
                @Override
                public void onSuccess(String receiveData) {
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(receiveData);
                        int result_code = jsonObject.optInt("result_code", -1);
                        if (result_code == 0) {

                            //todo BoardActivity 미완
                            Intent i = new Intent(getApplicationContext(), BoardActivity.class);
                            i.putExtra("user_id",user_id);
                            i.putExtra("user_nickname",user_nickname);
                            i.putExtra("room_id",roomlist.get(5).getRoom_id());
                            startActivity(i);
                            finish();

                        } else {
                            //todo 풀방
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFail(String url, String error) {
                    // 서버와 연결이 되지 않았을 때
                }
            });
        }catch(UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }

    public void init(){
        room=new LinearLayout[6];
        room1=(LinearLayout)findViewById(R.id.ll_room_room1);
        room2=(LinearLayout)findViewById(R.id.ll_room_room2);
        room3=(LinearLayout)findViewById(R.id.ll_room_room3);
        room4=(LinearLayout)findViewById(R.id.ll_room_room4);
        room5=(LinearLayout)findViewById(R.id.ll_room_room5);
        room6=(LinearLayout)findViewById(R.id.ll_room_room6);
        btn_room_makeroom=(Button)findViewById(R.id.btn_room_make);
        roomlist=new ArrayList<Room>();
        btn_room_prev=(Button)findViewById(R.id.btn_room_prev);
        btn_room_next= (Button)findViewById(R.id.btn_room_next);
        name1=(TextView)findViewById(R.id.tv_room_name1);
        crt1=(TextView)findViewById(R.id.tv_room_crt1);
        all1=(TextView)findViewById(R.id.tv_room_all1);
        lock1=(ImageView)findViewById(R.id.iv_room_lock1);
        name2=(TextView)findViewById(R.id.tv_room_name2);
        crt2=(TextView)findViewById(R.id.tv_room_crt2);
        all2=(TextView)findViewById(R.id.tv_room_all2);
        lock2=(ImageView)findViewById(R.id.iv_room_lock2);
        name3=(TextView)findViewById(R.id.tv_room_name3);
        crt3=(TextView)findViewById(R.id.tv_room_crt3);
        all3=(TextView)findViewById(R.id.tv_room_all3);
        lock3=(ImageView)findViewById(R.id.iv_room_lock3);
        name4=(TextView)findViewById(R.id.tv_room_name4);
        crt4=(TextView)findViewById(R.id.tv_room_crt4);
        all4=(TextView)findViewById(R.id.tv_room_all4);
        lock4=(ImageView)findViewById(R.id.iv_room_lock4);
        name5=(TextView)findViewById(R.id.tv_room_name5);
        crt5=(TextView)findViewById(R.id.tv_room_crt5);
        all5=(TextView)findViewById(R.id.tv_room_all5);
        lock5=(ImageView)findViewById(R.id.iv_room_lock5);
        name6=(TextView)findViewById(R.id.tv_room_name6);
        crt6=(TextView)findViewById(R.id.tv_room_crt6);
        all6=(TextView)findViewById(R.id.tv_room_all6);
        lock6=(ImageView)findViewById(R.id.iv_room_lock6);
        user_info=getSharedPreferences("user_info", MODE_PRIVATE);
        user_id = user_info.getString("email", "");
        user_nickname = user_info.getString("nickname","");
    }

}
