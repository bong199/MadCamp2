package com.example.weektwotest.ui.main;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

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

public class SubjectInfoActivity extends AppCompatActivity implements SubjStudentsAdapter.FollowBtnClickListener {

    ListView studentsList;
    String selectedSubject;
    String startT;
    String endT;
    String loc;
    TextView subjectName;
    TextView startTime;
    TextView endTime;
    TextView location;
    JSONObject subjJSONObj;
//    ArrayList<String> regStudents;
    ArrayList<Student> regStudents;
    ArrayList<StudentListViewItem> items;
    SubjStudentsAdapter adapter;
    String myId;

    public String getMyId(){ return myId; }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subjectinfo);
        subjJSONObj = new JSONObject();
        regStudents = new ArrayList<Student>();
        items = new ArrayList<StudentListViewItem>();
        Intent intent = getIntent();
        selectedSubject = intent.getStringExtra("subject name");
        startT = Integer.toString(intent.getIntExtra("startH", 0)) + ":" + Integer.toString(intent.getIntExtra("startM", 0));
        if(intent.getIntExtra("startM", 0) == 0){
            startT = Integer.toString(intent.getIntExtra("startH", 0)) + ":" + "00";
        }
        endT = Integer.toString(intent.getIntExtra("endH", 0)) + ":" + Integer.toString(intent.getIntExtra("endM", 0));
        if(intent.getIntExtra("endM", 0) == 0){
            endT = Integer.toString(intent.getIntExtra("endH", 0)) + ":" + "00";
        }
        loc = intent.getStringExtra("location");
        subjectName = findViewById(R.id.subj_name);
        subjectName.setText(selectedSubject);
        startTime = findViewById(R.id.subj_start_time);
        startTime.setText(startT);
        endTime = findViewById(R.id.subj_end_time);
        endTime.setText(endT);
        location = findViewById(R.id.location);
        location.setText(loc);
        studentsList = findViewById(R.id.student_list);

        myId = intent.getStringExtra("myId");

        System.out.println("olllpppppjpjj");


        new JSONTask(this).execute("http://192.249.18.227:3000/get_subject_info"); // 수강생 목록 가져옴
    }

    public class JSONTask extends AsyncTask<String, String, String> {
        public SubjectInfoActivity activity;

        public JSONTask(SubjectInfoActivity a){
            this.activity = a;
        }

        @Override
        protected String doInBackground(String... urls) {
            try {
                //JSONObject를 만들고 key value 형식으로 값을 저장해준다.
                HttpURLConnection con = null;
                BufferedReader reader = null;
                try{
                    //URL url = new URL("http://192.249.18.247:3000/users");
                    URL url = new URL(urls[0]);
                    System.out.println(urls[0]);
                    //연결을 함
                    con = (HttpURLConnection) url.openConnection();
//                    con.connect();
//                    InputStream stream = con.getInputStream();
//                    reader = new BufferedReader(new InputStreamReader(stream));
//                    StringBuffer buffer = new StringBuffer();

                    con.setRequestMethod("POST");//POST방식으로 보냄
                    con.setRequestProperty("Cache-Control", "no-cache");//캐시 설정
                    con.setRequestProperty("Content-Type", "application/json");//application JSON 형식으로 전송
                    con.setRequestProperty("Accept", "text/html");//서버에 response 데이터를 html로 받음
                    con.setDoOutput(true);//Outstream으로 post 데이터를 넘겨주겠다는 의미
                    con.setDoInput(true);//Inputstream으로 서버로부터 응답을 받겠다는 의미
                    Log.d("connect false/true","connecting?");
                    con.connect();
                    System.out.println("reached2");
                    //서버로 보내기위해서 스트림 만듬
                    OutputStream outStream = con.getOutputStream();
                    System.out.println("????");
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.accumulate("subj_name", selectedSubject);
//                    //버퍼를 생성하고 넣음
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outStream));
                    System.out.println(selectedSubject);
                    writer.write(jsonObject.toString());
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
            //Log.d("request ===>>>>>   ",result);
            String json = "{students:"+result+"}";
            try {
                JSONObject jsonObject = new JSONObject(json);
                JSONArray studentsArray = jsonObject.getJSONArray("students");
                for(int i=0; i<studentsArray.length(); i++) {
                    jsonObject = studentsArray.getJSONObject(i);
                    System.out.println(jsonObject.getString("First name"));
                    System.out.println("aaa"+jsonObject.getString("ID"));
                    regStudents.add(new Student(jsonObject.getString("ID"), jsonObject.getString("First name")));
                    StudentListViewItem addItem = new StudentListViewItem();
                    addItem.setText(jsonObject.getString("First name"));
                    items.add(addItem);
                }
                adapter = new SubjStudentsAdapter(getBaseContext(), R.layout.single_reg_student2, items, activity, myId);
                studentsList.setAdapter(adapter);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            //tempTextView.setText(result);
        }
    }
    @Override
    public void onFollowBtnClick(int position){
        String newFId = regStudents.get(position).getId();
        System.out.println("follow button clk");
        System.out.println(newFId);
        new JSONTaskF(newFId).execute("http://192.249.18.227:3000/get_friends_of");
    }
    public class JSONTaskF extends AsyncTask<String, String, String> {

        String newFriendId;

        public JSONTaskF(String newFriendId){
            this.newFriendId = newFriendId;
        }

        @Override
        protected String doInBackground(String... urls) {

            try {
                //JSONObject를 만들고 key value 형식으로 값을 저장해준다.
                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("targetID", myId);
                jsonObject.accumulate("newFriendId", newFriendId);
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
                    writer.write(jsonObject.toString());
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
//            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//            startActivity(intent);
        }
    }
}