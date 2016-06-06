package kr.ac.ssu.onecard_ssu.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import kr.ac.ssu.onecard_ssu.R;
import kr.ac.ssu.onecard_ssu.SockJSImpl;
import kr.ac.ssu.onecard_ssu.model.Board;
import kr.ac.ssu.onecard_ssu.model.Card;

/**
 * Created by lk on 16. 6. 6..
 */
public class BoardActivity extends AppCompatActivity {
    private SockJSImpl sockJS;
    private String channel_id = "cbb9319e-3de5-473a-abdc-fcc1cd0d30ee";
    private String user_id;
    private String user_nick;
    private String title;
    private Board board;
    private Button btn_onecard;
    private Button btn_turnoff;
    private Button btn_quit;
    private Button btn_chat;
    private Button btn_gamestart;
    private ImageView iv_topCard;
    private int playerCnt = 3;
    private LinearLayout myDeck;
    private TextView myNick;
    private TextView[] pNick;


    private ArrayList<Card> listCard;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);
        init();
        Queue<Card> list = new LinkedList<>();

        myDeck = (LinearLayout) findViewById(R.id.ll_board_mydeck);
        for (int i = 0; i < 0; i++) {
            final Card c = list.poll();
            ImageButton imageButton = new ImageButton(getApplicationContext());

            LinearLayout.LayoutParams params =
                    new LinearLayout.LayoutParams(300, ViewGroup.LayoutParams.MATCH_PARENT);
            imageButton.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            imageButton.setBackgroundColor(Color.alpha(255));
            imageButton.setLayoutParams(params);
            imageButton.setImageResource(c.getImage());
            imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    JSONObject obj = send();
                    try {
                        obj.put("msg", c.getId());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    sockJS.send(obj);
                }
            });
            myDeck.addView(imageButton);
        }
        connectSockJS();
    }

    private void init() {
        btn_onecard = (Button) findViewById(R.id.btn_board_onecard);
        btn_turnoff = (Button) findViewById(R.id.btn_board_turnoff);
        btn_quit = (Button) findViewById(R.id.btn_board_quit);
        btn_chat = (Button) findViewById(R.id.btn_board_chat);
        btn_gamestart = (Button) findViewById(R.id.btn_board_gamestart);
        iv_topCard = (ImageView) findViewById(R.id.iv_board_topcard);
        myNick = (TextView) findViewById(R.id.tv_board_myNick);
        pNick = new TextView[4];
        pNick[0] = (TextView) findViewById(R.id.tv_p1_nick);
        pNick[1] = (TextView) findViewById(R.id.tv_p2_nick);
        pNick[2] = (TextView) findViewById(R.id.tv_p3_nick);
        pNick[3] = (TextView) findViewById(R.id.tv_p4_nick);

        btn_onecard.setVisibility(View.INVISIBLE);
        btn_turnoff.setVisibility(View.INVISIBLE);
        btn_gamestart.setVisibility(View.VISIBLE);
        iv_topCard.setVisibility(View.INVISIBLE);

        intent=getIntent();
        user_id =intent.getStringExtra("user_id");
        channel_id=intent.getStringExtra("room_id");
        user_nick = intent.getStringExtra("user_nick");

        playerCnt = intent.getIntExtra("playerNum", 1);

        for(int k=0; k< playerCnt; k++){
            pNick[k].setText(intent.getStringExtra("p"+k+"nick"));
        }

        myNick.setText(user_nick);

        btn_onecard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btn_turnoff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ImageButton imageButton = new ImageButton(getApplicationContext());
                final Card c =  board.QCard.poll();
                //listCard.add(c);
                LinearLayout.LayoutParams params =
                        new LinearLayout.LayoutParams(300, ViewGroup.LayoutParams.MATCH_PARENT);
                imageButton.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                imageButton.setBackgroundColor(Color.alpha(255));
                imageButton.setLayoutParams(params);
                imageButton.setImageResource(c.getImage());
                imageButton.setId(c.get_id());
                imageButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        JSONObject obj = new JSONObject();
                        try {
                            obj.put("type", "publish");
                            obj.put("address", "to.server.channel");
                            JSONObject body = new JSONObject();
                            body.put("type", "system_play");
                            body.put("channel_id", channel_id);
                            body.put("sender_id", "ssss");
                            body.put("sender_nick", user_id);
                            body.put("msg", "카드를 냄");
                            obj.put("body", body);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("onClick", e.toString());
                        }

                        try {
                            obj.put("msg", c.get_id());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        sockJS.send(obj);
                        iv_topCard.setImageResource(board.c.get(view.getId()).getImage());
                        view.setVisibility(View.GONE);
                    }
                });
                myDeck.addView(imageButton);
            }
        });

        btn_quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btn_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        board = new Board(playerCnt, getApplicationContext());

        btn_gamestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (playerCnt < 2) {
                    Toast.makeText(getApplicationContext(), "게임에 필요한 인원이 부족합니다.", Toast.LENGTH_SHORT).show();
                } else {
                    btn_onecard.setVisibility(View.VISIBLE);
                    btn_turnoff.setVisibility(View.VISIBLE);
                    btn_gamestart.setVisibility(View.INVISIBLE);
                    iv_topCard.setVisibility(View.VISIBLE);

                    RequestUtil.get("http://133.130.115.228:7010/user/gamestart?room_id="
                            + channel_id, new Request() {
                        @Override
                        public void onSuccess(String receiveData) {
                            System.out.println(receiveData);
                        }

                        @Override
                        public void onFail(String url, String error) {

                        }
                    });
                }
            }
        });

    }


    private void connectSockJS() {
        try {
            sockJS = new SockJSImpl("http://133.130.115.228:7030" + "/eventbus", channel_id, user_id, title) {
                //channel_
                @Override
                public void parseSockJS(String s) {
                    try {
                        //System.out.println(s);
                        s = s.replace("\\\"", "\"");
                        s = s.replace("\\\\", "\\");
                        // s = s.replace("\\","");

                        //s = s.replace("\\\\\"", "\"");
                        //s = s.replace("\\\"", "\"");
                        s = s.substring(3, s.length() - 2); // a[" ~ "] 없애기
                        Log.i("Reci", s);

                        JSONObject json = new JSONObject(s);
                        String type = json.getString("type");
                        String address = json.getString("address");
//                        final JSONObject body = json.getJSONObject("body");
                        final JSONObject body = new JSONObject(json.getString("body"));
                        String bodyType = body.getString("type");
//                        final String msg = body.getString("msg");
//                        String user_id = body.getString("sender_nick");
//                        Date myDate = new Date();
//                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd. HH:mm");
//                        String date = sdf.format(myDate);
//                        final String data =  bodyType + "/&" +user_id + "/&" + msg + "/&" + date;
                        if (("to.channel." + channel_id).equals(address)) {
                            if ("system".equals(bodyType)) {      // 카드가 들어왔을 때
                                String id = body.getString("id");
                                if (id.equals(user_id)) {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            try {
                                                for (int i = 0; i < 5; i++) {
                                                    ImageButton imageButton = new ImageButton(getApplicationContext());
                                                    final Card c = board.c.get(body.getInt("card" + i));
                                                    //listCard.add(c);
                                                    LinearLayout.LayoutParams params =
                                                            new LinearLayout.LayoutParams(300, ViewGroup.LayoutParams.MATCH_PARENT);
                                                    imageButton.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                                                    imageButton.setBackgroundColor(Color.alpha(255));
                                                    imageButton.setLayoutParams(params);
                                                    imageButton.setImageResource(c.getImage());
                                                    imageButton.setId(body.getInt("card"+i));
                                                    imageButton.setOnClickListener(new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View view) {
                                                            JSONObject obj = new JSONObject();
                                                            try {
                                                                obj.put("type", "publish");
                                                                obj.put("address", "to.server.channel");
                                                                JSONObject body = new JSONObject();
                                                                body.put("type", "system_play");
                                                                body.put("channel_id", channel_id);
                                                                body.put("sender_id", "ssss");
                                                                body.put("sender_nick", user_id);
                                                                body.put("msg", "카드를 냄");
                                                                obj.put("body", body);
                                                            } catch (JSONException e) {
                                                                e.printStackTrace();
                                                                Log.e("onClick", e.toString());
                                                            }

                                                            try {
                                                                obj.put("msg", c.getId());
                                                            } catch (JSONException e) {
                                                                e.printStackTrace();
                                                            }
                                                            sockJS.send(obj);
                                                            board.QCard.addLast(board.c.get(view.getId()));
                                                            iv_topCard.setImageResource(board.c.get(view.getId()).getImage());
                                                            view.setVisibility(View.GONE);
                                                        }
                                                    });
                                                    myDeck.addView(imageButton);
                                                }


                                                board.c.get(body.getInt("card0"));
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    });
                                }
                            } else if ("system_Qcard".equals(bodyType)) {
                                int size = body.getInt("size");
                                for(int i=0; i<size; i++){
                                    board.QCard.add(board.c.get(body.getInt("card"+i)));
                                }
                                System.out.println(board.QCard.toString());
                            }
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    //Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            };
            boolean b = sockJS.connectBlocking();

        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @NonNull
    private JSONObject send() {
        JSONObject obj = new JSONObject();
        try {
            obj.put("type", "publish");
            obj.put("address", "to.server.channel");
            JSONObject body = new JSONObject();
            body.put("type", "system");
            body.put("channel_id", channel_id);
            body.put("sender_id", "ssss");
            body.put("sender_nick", user_id);
            body.put("msg", "카드를 냄");
            obj.put("body", body);
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("onClick", e.toString());
        }
        return obj;
    }
}
