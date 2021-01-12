package com.example.weektwotest.ui.main;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.weektwotest.MainActivity;
import com.example.weektwotest.R;


import org.json.JSONArray;
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
import java.util.ArrayList;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

public class FacebookActivity extends AppCompatActivity {

    /*for new login*/
    private Button loginButton2;
    private EditText idEdit;
    private EditText pwEdit;
    private JSONObject loginAccount;
    private JSONObject loginAccount_fd;

    private CallbackManager callbackManager;
    private LoginButton loginButton;
    private static ArrayList<String> fbIDs;
    private static ArrayList<String> fbNAMEs;
    //private static JSONObject user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        loginButton2 = findViewById(R.id.custom_login_button);
        idEdit = findViewById(R.id.id_edit);
        pwEdit = findViewById(R.id.pw_edit);
        //user_id = new JSONObject();


        loginButton = findViewById(R.id.login_button);
        callbackManager = CallbackManager.Factory.create();
        //.setPublishPermissions("user_friends");
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) { // access token

                Log.d("Demo","Login Successful!");

            }

            @Override
            public void onCancel() {
                Log.d("Demo","Login canceled");
            }

            @Override
            public void onError(FacebookException error) {
                Log.d("Demo","Login error.");
            }

        });



        loginButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idString = idEdit.getText().toString();
                String pwString = pwEdit.getText().toString();
                try {
                    loginAccount = new JSONObject();
                    loginAccount.accumulate("ID", idString);
                    loginAccount.accumulate("PW", pwString);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                new JSONTask().execute("http://192.249.18.247:3030/post_login");
                new JSONTask().execute("http://192.249.18.247:3000/post_login");
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
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
        GraphRequest graphReques = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        Log.d("Demo",object.toString());
                        try {
                            //String name = object.getString("name");
                            //String id = object.getString("id");
                            loginAccount = new JSONObject();
                            loginAccount.accumulate("user_id",object.getString("id"));
                            new JSONTask().execute("http://192.249.18.247:3030/post_face");//phone
                            new JSONTask().execute("http://192.249.18.247:3000/post_face");//갤러리

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
        });
        Bundle bundle = new Bundle();
        bundle.putString("fields", "gender, name, id, first_name, last_name");
        graphReques.setParameters(bundle);
        graphReques.executeAsync(); //test1 contents

        GraphRequest graphRequestFriends = GraphRequest.newMyFriendsRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONArrayCallback() {
            @Override
            public void onCompleted(JSONArray objects, GraphResponse response) {
                Log.d("Demo", objects.toString());
                fbIDs = new ArrayList<String>();
                fbNAMEs = new ArrayList<String>();
                for (int i = 0; i < objects.length(); i++) {
                    try {
                        JSONObject object = objects.getJSONObject(i);
                        loginAccount_fd = new JSONObject();
                        loginAccount_fd.accumulate("friend_id",object.getString("id"));
                        loginAccount_fd.accumulate("friend_name",object.getString("name"));
                        //new JSONTask_fd().execute("http://192.249.18.247:3030/post_friend");
                        fbIDs.add(object.getString("id"));
                        fbNAMEs.add(object.getString("name"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }


            }
        });
        graphRequestFriends.executeAsync();

    }


    AccessToken accessToken = AccessToken.getCurrentAccessToken();
    boolean isLoggedIn = accessToken != null && !accessToken.isExpired();

    /*public class JSONTask_fd extends AsyncTask<String, String, String> {
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
                    writer.write(loginAccount_fd.toString());
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
                    *//*try {
                        if(reader != null){
                            reader.close();//버퍼를 닫아줌
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }*//*
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result){
            Log.d("friend's input rrrrr",result);
            super.onPostExecute(result);

        }
    }*/
}

