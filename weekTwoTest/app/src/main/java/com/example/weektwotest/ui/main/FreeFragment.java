package com.example.weektwotest.ui.main;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.icu.text.SymbolTable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
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
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class FreeFragment extends Fragment{
    private static final String ARG_SECTION_NUMBER = "section_number";
    TimetableView timetableView;
    ImageButton addSubjBtn;
    ImageButton writeBtn;
    RecyclerView postView;
    String friendsJSONStr;
    String poJsonString;
    FreeFragmentHAdapter freeFragmentHAdapter;
    Boolean wait;
    //ArrayList<Post> posts;

    public static FreeFragment newInstance(int index) {
        FreeFragment fragment = new FreeFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    public void showDialog(){
        final EditText edittext = new EditText(getContext());

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("AlertDialog Title");
        builder.setView(edittext);
        System.out.println("showdialog");
        builder.setPositiveButton("입력",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        System.out.println("showdialog in");
                        new JSONTaskWW(((MainActivity)getActivity()).getMyId(), edittext.getText().toString()).execute("http://192.249.18.247:3000/add_post");
                    }
                });
        builder.setNegativeButton("취소",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        builder.show();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
//        String taskResult = "mm";
//        try {
//            taskResult = new JSONTaskFL(((MainActivity)getActivity()).getMyId()).execute("http://192.249.18.227:3000/first_get_friends_of").get();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        System.out.println(taskResult);
        wait = true;
        View view = inflater.inflate(R.layout.free_fragment, container, false);
//        AlertDialog.Builder ad = new AlertDialog.Builder(getContext());
//        ad.setTitle("Title");

//        final EditText et = new EditText(getContext());

//        if (et.getParent() != null) {
//            ((ViewGroup) et.getParent()).removeView(et);
//        }
//        ad.setView(et);

//        ad.setPositiveButton("Save", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                new JSONTaskWW(((MainActivity)getActivity()).getMyId(), et.getText().toString()).execute("http://192.249.18.247:3000/add_post");
//            }
//        });
//
//        ad.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.dismiss();
//            }
//        });
        addSubjBtn = view.findViewById(R.id.subj_add_btn);
        addSubjBtn.setImageResource(R.drawable.plus_icon);
        writeBtn = view.findViewById(R.id.write_btn);
        writeBtn.setImageResource(R.drawable.write_icon);
        writeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
        timetableView = view.findViewById(R.id.timetable);
        ArrayList<Schedule> schedules = new ArrayList<Schedule>();
        Schedule schedule = new Schedule();
        schedule.setClassTitle("Algorithm"); // sets subject
        schedule.setClassPlace("IT-601"); // sets place
        schedule.setProfessorName("Won Kim"); // sets professor
        schedule.setStartTime(new Time(10,0)); // sets the beginning of class time (hour,minute)
        schedule.setEndTime(new Time(13,30)); // sets the end of class time (hour,minute)
        schedule.setDay(3);
        schedules.add(schedule);
        timetableView.add(schedules);
        timetableView.setOnStickerSelectEventListener(new TimetableView.OnStickerSelectedListener() {
            @Override
            public void OnStickerSelected(int idx, ArrayList<Schedule> schedules) {
                Intent intent = new Intent(getActivity(), SubjectInfoActivity.class);
                intent.putExtra("subject name", schedules.get(idx).getClassTitle());
                intent.putExtra("startH", schedules.get(idx).getStartTime().getHour());
                intent.putExtra("startM", schedules.get(idx).getStartTime().getMinute());
                intent.putExtra("endH", schedules.get(idx).getEndTime().getHour());
                intent.putExtra("endM", schedules.get(idx).getEndTime().getMinute());
                intent.putExtra("location", schedules.get(idx).getClassPlace());
                intent.putExtra("myId", ((MainActivity)getActivity()).getMyId());
                startActivity(intent);
            }
        });

        //posts = new ArrayList<Post>();
        postView = view.findViewById(R.id.recyclerview_free_fragment);
        postView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        postView.addItemDecoration(new DividerItemDecoration(postView.getContext(), DividerItemDecoration.HORIZONTAL));
//        new JSONTaskPP(((MainActivity)getActivity()).getFriendsJSONStr()).execute("http://192.249.18.227:3000/get_posts_of");
        try {
            String toWait = new JSONTaskPP(((MainActivity)getActivity()).getFriendsJSONStr()).execute("http://192.249.18.247:3000/get_posts_of").get();
//            freeFragmentHAdapter = new FreeFragmentHAdapter(getContext(), poJsonString);
//            postView.setAdapter(freeFragmentHAdapter);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // JSONTask 만들어서 거기서 adapter 설정




        return view;
    }


    public class JSONTaskPP extends AsyncTask<String, String, String> {

        String friendListJSonStr;

        ProgressDialog dialog;

        public JSONTaskPP(String friendListJSonStr){
            this.friendListJSonStr = friendListJSonStr;
        }

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            dialog = new ProgressDialog(getContext());
            dialog.setIndeterminate(true);
            dialog.setMessage("Please Wait...");
            dialog.setTitle("Loading Messages");
            dialog.setCancelable(true);
            dialog.show();
        }

        @Override
        protected String doInBackground(String... urls) {
            try {
                //JSONObject를 만들고 key value 형식으로 값을 저장해준다.
                System.out.println(friendListJSonStr);
                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("friendJSONArr", friendListJSonStr);
                HttpURLConnection con = null;
                BufferedReader reader = null;
                try{
                    //URL url = new URL("http://192.249.18.247:3000/users");
                    URL url = new URL(urls[0]);
                    System.out.println(url);
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
                    System.out.println(friendListJSonStr);
                    System.out.println(jsonObject.toString());
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
            result = result.substring(0, result.length()-1);
            result = "["+result+"]";
//            //Log.d("request ===>>>>>   ",result);
            String json = "{posts:"+result+"}";
            poJsonString = json;
            freeFragmentHAdapter = new FreeFragmentHAdapter(getContext(), poJsonString);
            postView.setAdapter(freeFragmentHAdapter);
            System.out.println("hadptreeafdsafds");
            wait = false;
            dialog.dismiss();
        }
    }

    public class JSONTaskWW extends AsyncTask<String, String, String>{

        String myId;
        String titleContent;

        public JSONTaskWW(String myId, String titleContent){
            this.myId = myId;
            this.titleContent = titleContent;
        }

        @Override
        protected String doInBackground(String... urls) {
            try {
                //JSONObject를 만들고 key value 형식으로 값을 저장해준다.
                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("myId", myId);
                jsonObject.accumulate("titleContent", titleContent);
                HttpURLConnection con = null;
                BufferedReader reader = null;
                try{
                    //URL url = new URL("http://192.249.18.247:3000/users");
                    URL url = new URL(urls[0]);
                    System.out.println(url);
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
                    System.out.println(jsonObject.toString());
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
            Log.d("add_post dkjglsdkfj",result);
        }
    }
}