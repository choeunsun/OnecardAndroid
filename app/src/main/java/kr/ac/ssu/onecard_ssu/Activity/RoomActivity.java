package kr.ac.ssu.onecard_ssu.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import kr.ac.ssu.onecard_ssu.R;

/**
 * Created by unseon on 2016-06-04.
 */

public class RoomActivity extends AppCompatActivity {
    LinearLayout room1, room2, room3, room4, room5, room6;
    Button btn_room_makeroom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.room_layout);

        init();
        btn_room_makeroom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(), MakeroomActivity.class);
                startActivity(i);
            }
        });


    }

    public void init(){
        room1=(LinearLayout)findViewById(R.id.ll_room_room1);
        room2=(LinearLayout)findViewById(R.id.ll_room_room2);
        room3=(LinearLayout)findViewById(R.id.ll_room_room3);
        room4=(LinearLayout)findViewById(R.id.ll_room_room4);
        room5=(LinearLayout)findViewById(R.id.ll_room_room5);
        room6=(LinearLayout)findViewById(R.id.ll_room_room6);
        btn_room_makeroom=(Button)findViewById(R.id.btn_room_make);
    }
}
