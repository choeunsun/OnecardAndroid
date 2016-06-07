package kr.ac.ssu.onecard_ssu.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import kr.ac.ssu.onecard_ssu.R;

/**
 * Created by unseon on 2016-06-08.
 */
public class SevenActivity extends Activity {
    Button heart, diamond, space, clover;
    int shape;
    private EditText text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message_layout);
        Log.e("enter", "enter");
        text = (EditText) findViewById(R.id.editText);
        Button bottn = (Button) findViewById(R.id.btn_message_cancel);
        bottn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Button send = (Button) findViewById(R.id.btn_message_send);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                //intent.putExtra("777", shape);
                intent.putExtra("msg", text.getText());
                setResult(1, intent);
                finish();
            }
        });
    }

    public void onClick(View v){


    }

    public void init(){
        heart=(Button)findViewById(R.id.btn_seven_heart);
        diamond=(Button)findViewById(R.id.btn_seven_diamond);
        space=(Button)findViewById(R.id.btn_seven_space);
        clover=(Button)findViewById(R.id.btn_seven_clover);
    }
}
