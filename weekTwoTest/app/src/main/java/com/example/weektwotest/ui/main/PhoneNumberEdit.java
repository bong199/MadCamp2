package com.example.weektwotest.ui.main;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

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

public class PhoneNumberEdit extends AppCompatActivity{
    private EditText nameEdit;
    private EditText numberEdit;
    private EditText emailEdit;
    private JSONObject targetContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.phonenumber_edit);
        nameEdit = findViewById(R.id.name_EditText2);
        nameEdit.setText(getIntent().getStringExtra("Name"));
        numberEdit = findViewById(R.id.number_EditText2);
        numberEdit.setText(getIntent().getStringExtra("Number"));
        emailEdit = findViewById(R.id.email_EditText2);
        emailEdit.setText(getIntent().getStringExtra("Email"));
        targetContact = new JSONObject();
        try {
            targetContact.accumulate("name", getIntent().getStringExtra("Name"));
            targetContact.accumulate("number", getIntent().getStringExtra("Number"));
            targetContact.accumulate("email", getIntent().getStringExtra("Email"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Button saveBtn = findViewById(R.id.save_button2);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new JSONTask().execute("http://192.249.18.247:3030/post1");
            }
        });
        Button cancelBtn = findViewById(R.id.cancel_button2);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
        Button deleteBtn = findViewById(R.id.delete_button);
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new JSONTaskD().execute("http://192.249.18.247:3030/post2");
            }
        });
    }
    public class JSONTask extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... urls) {
            String newName = nameEdit.getText().toString();
            String newNumber = numberEdit.getText().toString();
            String newEmail = emailEdit.getText().toString();
            try {
                //JSONObject를 만들고 key value 형식으로 값을 저장해준다.
//                JSONObject jsonObject = new JSONObject();
                targetContact.accumulate("newname", newName);
                targetContact.accumulate("newnumber", newNumber);
                targetContact.accumulate("newemail", newEmail);
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
                    System.out.println("reached1");
                    //서버로 보내기위해서 스트림 만듬
                    OutputStream outStream = con.getOutputStream();
                    //버퍼를 생성하고 넣음
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outStream));
                    writer.write(targetContact.toString());
                    writer.flush();
                    writer.close();//버퍼를 받아줌
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
                    try {
                        if(reader != null){
                            reader.close();//버퍼를 닫아줌
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result){
            super.onPostExecute(result);
//            //Log.d("request ===>>>>>   ",result);
//            String json = "{contacts:"+result+"}";
//            try {
//                JSONObject jsonObject = new JSONObject(json);
//                JSONArray contactArray = jsonObject.getJSONArray("contacts");
//                for(int i=0; i<contactArray.length(); i++) {
//                    jsonObject = contactArray.getJSONObject(i);
//                    System.out.println(jsonObject.getString("name"));
//                    System.out.println(jsonObject.getString("number"));
//                    phoneNumbers.add(new PhoneNumber(jsonObject.getString("name"), jsonObject.getString("number")));
//                }
//                adapter = new PhoneNumberAdapter(getContext(), phoneNumbers);
//                listView.setAdapter(adapter);
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//            //tempTextView.setText(result);
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }
    }
    public class JSONTaskD extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... urls) {
            String newName = nameEdit.getText().toString();
            String newNumber = numberEdit.getText().toString();
            String newEmail = emailEdit.getText().toString();
            try {
                //JSONObject를 만들고 key value 형식으로 값을 저장해준다.
//                JSONObject jsonObject = new JSONObject();
                targetContact.accumulate("newname", newName);
                targetContact.accumulate("newnumber", newNumber);
                targetContact.accumulate("newemail", newEmail);
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
                    System.out.println("reached1");
                    //서버로 보내기위해서 스트림 만듬
                    OutputStream outStream = con.getOutputStream();
                    //버퍼를 생성하고 넣음
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outStream));
                    writer.write(targetContact.toString());
                    writer.flush();
                    writer.close();//버퍼를 받아줌
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
                    try {
                        if(reader != null){
                            reader.close();//버퍼를 닫아줌
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result){
            super.onPostExecute(result);
//            //Log.d("request ===>>>>>   ",result);
//            String json = "{contacts:"+result+"}";
//            try {
//                JSONObject jsonObject = new JSONObject(json);
//                JSONArray contactArray = jsonObject.getJSONArray("contacts");
//                for(int i=0; i<contactArray.length(); i++) {
//                    jsonObject = contactArray.getJSONObject(i);
//                    System.out.println(jsonObject.getString("name"));
//                    System.out.println(jsonObject.getString("number"));
//                    phoneNumbers.add(new PhoneNumber(jsonObject.getString("name"), jsonObject.getString("number")));
//                }
//                adapter = new PhoneNumberAdapter(getContext(), phoneNumbers);
//                listView.setAdapter(adapter);
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//            //tempTextView.setText(result);
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }
    }
}