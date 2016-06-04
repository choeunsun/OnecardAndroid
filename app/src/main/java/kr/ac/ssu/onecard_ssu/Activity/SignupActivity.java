package kr.ac.ssu.onecard_ssu.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import kr.ac.ssu.onecard_ssu.R;

/**
 * Created by unseon on 2016-06-03.
 */
public class SignupActivity extends AppCompatActivity{
    EditText et_email;
    EditText et_password;
    EditText et_pwCheck;
    EditText et_nickname;
    Button btn_signup;
    String email;
    String password;
    String pwCheck;
    String nick;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_layout);
        context = getApplicationContext();
        init();

        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                email= et_email.getText().toString();
                password=et_password.getText().toString();
                pwCheck=et_pwCheck.getText().toString();
                nick=et_nickname.getText().toString();

                if(password.equals(pwCheck)) {
                    try {
                        RequestUtil.get("http://133.130.115.228:7010/user/join?user_id="
                                + email + "&user_pw=" + password + "&nick_name="
                                + URLEncoder.encode(et_nickname.getText().toString(), "UTF-8")
                                , new Request() {
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
                                                Toast.makeText(getBaseContext(), "success", Toast.LENGTH_LONG);
                                            }
                                        });
                                        Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                                        startActivity(i);
                                    } else {
                                        //todo 실패했을 때 reation

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
                else{
                    Toast.makeText(getApplicationContext(),"패스워드가 달라요",Toast.LENGTH_LONG);
                    //todo 패스워드가 다를 때
                }
            }
        });
    }

    public void init(){
        et_email=(EditText)findViewById(R.id.et_signup_email);
        et_password=(EditText)findViewById(R.id.et_signup_pw);
        et_pwCheck=(EditText)findViewById(R.id.et_signup_pwcheck);
        et_nickname=(EditText)findViewById(R.id.et_signup_nickname);
        btn_signup=(Button)findViewById(R.id.btn_signup_signup);
    }
}
