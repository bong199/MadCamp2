package com.example.weektwotest.ui.main;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weektwotest.MainActivity;
import com.example.weektwotest.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class CommentActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    EditText txt_send;
    ImageButton btn_send;
    private JSONObject loginAccount;
    String image_path;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comment_activity);
        recyclerView = findViewById(R.id.chat_recycler_view);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        loginAccount = new JSONObject();

        txt_send = findViewById(R.id.text_send);
        btn_send = findViewById(R.id.btn_send);

        Intent intent = getIntent();

        image_path = intent.getExtras().getString("image_path");

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String comments = txt_send.getText().toString();
                loginAccount = new JSONObject();
                try {
                    loginAccount.accumulate("comment", comments);
                    loginAccount.accumulate("image_path", image_path);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                new JSONTask().execute("http://192.249.18.227:3000/post_comment");
            }
        });


    }

    public class JSONTask extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... urls) {
            try {
                //JSONObject를 만들고 key value 형식으로 값을 저장해준다.
//                JSONObject jsonObject = new JSONObject();
//                jsonObject.accumulate("user_id", user_id);
                HttpURLConnection con = null;
                BufferedReader reader = null;
                try{
                    //URL url = new URL("http://192.249.18.247:3000/users");
                    URL url = new URL(urls[0]);
                    //연결을 함
                    con = (HttpURLConnection) url.openConnection();
                    con.setRequestMethod("POST");//POST방식으로 보냄
                    con.setRequestProperty("Cache-Control", "no-cache");//캐시 설정
                    con.setRequestProperty("Content-Type", "application/json");//application JSON 형식으로 전송
                    con.setRequestProperty("Accept", "text/html");//서버에 response 데이터를 html로 받음
                    con.setDoOutput(true);//Outstream으로 post 데이터를 넘겨주겠다는 의미
                    con.setDoInput(true);//Inputstream으로 서버로부터 응답을 받겠다는 의미
                    Log.d("connect false/true","connecting?");
                    con.connect();
                    //서버로 보내기위해서 스트림 만듬
                    OutputStream outStream = con.getOutputStream();
                    //버퍼를 생성하고 넣음
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outStream));
                    writer.write(loginAccount.toString());
                    writer.flush();
                    writer.close();//버퍼를 받아줌
                    System.out.println("cccccccccccccccc");
                    //서버로 부터 데이터를 받음
                    InputStream stream = con.getInputStream();
                    reader = new BufferedReader(new InputStreamReader(stream));
                    StringBuffer buffer = new StringBuffer();
                    String line = "";
                    while((line = reader.readLine()) != null){
                        buffer.append(line);
                    }
                    Log.d("buffer ===>>>>", buffer.toString());
                    return buffer.toString();//서버로 부터 받은 값을 리턴해줌 아마 OK!!가 들어올것임
                } catch (MalformedURLException e){
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if(con != null){
                        con.disconnect();
                    }
                    /*try {
                        if(reader != null){
                            reader.close();//버퍼를 닫아줌
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }*/
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result){
            //Log.d("qqqqqqqqqqqqqqq",result);
            super.onPostExecute(result);
            if(result.equals("YES")) {
                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                startActivity(intent);
                return;
            }
            else if(result.equals("NO")) {
                System.out.println("ㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋ");
                return;
            }
            else if(result.equals("FACE")) {
                System.out.println("wwwwwww");
                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                startActivity(intent);
            }

        }
    }
}
