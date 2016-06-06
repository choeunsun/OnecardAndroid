package kr.ac.ssu.onecard_ssu.Activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import kr.ac.ssu.onecard_ssu.R;

/**
 * Created by unseon on 2016-06-05.
 */
public class MakeroomActivity extends Activity {
    EditText et_makeroom_title;
    EditText et_makeroom_pw;
    CheckBox cb_makeroom_yes;
    CheckBox cb_makeroom_no;
    CheckBox cb_makeroom_two, cb_makeroom_three, cb_makeroom_four, cb_makeroom_five;
    Button btn_makeroom_ok;
    Button btn_makeroom_cancel;
    String room_title,user_id, is_private, room_limit, nickname,room_pw, channel_id, user_nick;
    SharedPreferences user_info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.makeroom_layout);

        init();

        btn_makeroom_ok.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                user_id = user_info.getString("email","");
                user_nick=user_info.getString("nickname","");
                room_title = et_makeroom_title.getText().toString();
                if (cb_makeroom_yes.isChecked() == true)
                    is_private = "on";
                else
                    is_private = "off";
                if (cb_makeroom_two.isChecked() == true)
                    room_limit = "2";
                else if (cb_makeroom_three.isChecked() == true)
                    room_limit = "3";
                else if (cb_makeroom_four.isChecked() == true)
                    room_limit = "4";
                else if (cb_makeroom_five.isChecked() == true)
                    room_limit = "5";
                nickname = user_info.getString("nickname","");
                room_pw=et_makeroom_pw.getText().toString();

                try {
                RequestUtil.get("http://133.130.115.228:7010/user/makeroom?user_id="
                        + user_id + "&room_name=" + URLEncoder.encode(room_title, "UTF-8")
                        + "&is_private=" + is_private + "&room_limit=" + room_limit
                        + "&nick_name=" + URLEncoder.encode(nickname, "UTF-8")
                        +"&room_pw="+URLEncoder.encode(room_pw, "UTF-8"), new Request() {
                    @Override
                    public void onSuccess(String receiveData) {
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(receiveData);
                            int result_code = jsonObject.optInt("result_code", -1);
                            channel_id=jsonObject.getString("channel_id");
                            if (result_code == 0) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getApplicationContext(), "success", Toast.LENGTH_LONG);
                                    }
                                });
                                //todo 방 새성하고 방으로 들어가기
                                Intent i = new Intent(getApplicationContext(), BoardActivity.class);
                                i.putExtra("user_id",user_id);
                                i.putExtra("room_id",channel_id);
                                i.putExtra("user_nick", user_nick);
                                startActivity(i);
                                finish();

                            } else {
                                //todo 방 생성 실패

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
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        });
        btn_makeroom_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        cb_makeroom_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cb_makeroom_no.setChecked(false);
                et_makeroom_pw.setVisibility(View.VISIBLE);
            }
        });
        cb_makeroom_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cb_makeroom_yes.setChecked(false);
                et_makeroom_pw.setVisibility(View.INVISIBLE);
            }
        });
        cb_makeroom_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cb_makeroom_three.setChecked(false);
                cb_makeroom_four.setChecked(false);
                cb_makeroom_five.setChecked(false);
            }
        });
        cb_makeroom_three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cb_makeroom_two.setChecked(false);
                cb_makeroom_four.setChecked(false);
                cb_makeroom_five.setChecked(false);
            }
        });
        cb_makeroom_four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cb_makeroom_three.setChecked(false);
                cb_makeroom_two.setChecked(false);
                cb_makeroom_five.setChecked(false);
            }
        });
        cb_makeroom_five.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cb_makeroom_three.setChecked(false);
                cb_makeroom_four.setChecked(false);
                cb_makeroom_two.setChecked(false);
            }
        });
    }

    public void init(){
        et_makeroom_title=(EditText)findViewById(R.id.et_makeroom_title);
        cb_makeroom_yes=(CheckBox)findViewById(R.id.cb_makeroom_yes);
        cb_makeroom_no=(CheckBox)findViewById(R.id.cb_makeroom_no);
        cb_makeroom_five=(CheckBox)findViewById(R.id.cb_makeroom_five);
        cb_makeroom_two=(CheckBox)findViewById(R.id.cb_makeroom_two);
        cb_makeroom_three=(CheckBox)findViewById(R.id.cb_makeroom_three);
        cb_makeroom_four=(CheckBox)findViewById(R.id.cb_makeroom_four);
        btn_makeroom_ok=(Button)findViewById(R.id.btn_makeroom_ok);
        btn_makeroom_cancel=(Button)findViewById(R.id.btn_makeroom_cancel);
        et_makeroom_pw=(EditText)findViewById(R.id.et_makeroom_pw);
        user_info=getSharedPreferences("user_info", MODE_PRIVATE);
    }
}
