package kr.ac.ssu.onecard_ssu.Activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import kr.ac.ssu.onecard_ssu.MainActivity;
import kr.ac.ssu.onecard_ssu.R;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {
    Button btn_login_attempt;
    Button btn_signup;
    EditText et_id;
    EditText et_pw;
    String id,pw;
    boolean success=false;
    RequestUtil requestUtil=null;
    SharedPreferences user_info;
    String user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);

       init();

        btn_login_attempt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id = et_id.getText().toString();
                pw = et_pw.getText().toString();

                RequestUtil.get("http://133.130.115.228:7010/user/login?user_id=" + id
                        + "&user_pw=" + pw, new Request() {
                    @Override
                    public void onSuccess(String receiveData) {
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(receiveData);
                            int result_code = jsonObject.optInt("result_code", -1);
                            if (result_code == 0) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getApplicationContext(), "success", Toast.LENGTH_LONG);
                                    }
                                });
                                String nickname = jsonObject.getString("nick_name");
                                SharedPreferences.Editor editor=user_info.edit();
                                editor.putString("email",id);
                                editor.putString("nickname",nickname);
                                editor.commit();

                                Intent i=new Intent(getApplicationContext(), RoomActivity.class);
                                startActivity(i);
                                finish();

                            } else {
                                //todo 로그인 정보가 잘못되었을 떄1

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
        });

        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(getApplicationContext(),SignupActivity.class);
                startActivity(i);
            //    finish();
            }
        });
    }

    public void init(){
        btn_login_attempt=(Button)findViewById(R.id.btn_login_login);
        btn_signup=(Button)findViewById(R.id.btn_login_signup);
        et_id=(EditText)findViewById(R.id.et_login_id);
        et_pw=(EditText)findViewById(R.id.et_login_password);
        user_info=getSharedPreferences("user_info", MODE_PRIVATE);
        user_id = user_info.getString("email", "");
        /*
        if(user_id.contains("@")){
            Intent i= new Intent(getApplicationContext(),RoomActivity.class);
            startActivity(i);
            finish();
        }
        */
    }
}

