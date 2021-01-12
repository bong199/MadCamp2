package com.example.weektwotest.ui.main;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.weektwotest.MainActivity;
import com.example.weektwotest.R;
import com.example.weektwotest.ui.main.PhoneNumber;

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

public class SubjStudentsAdapter extends ArrayAdapter<StudentListViewItem> implements View.OnClickListener {
    private Context context;
    private ArrayList<Student> regStudents;
    private String myId;

    public interface FollowBtnClickListener{
        void onFollowBtnClick(int position);
    }

    int resourceId;

    private FollowBtnClickListener followBtnClickListener;

    public SubjStudentsAdapter(Context context, int resource, ArrayList<StudentListViewItem> list, FollowBtnClickListener clickListener, String myId){
        super(context, -1, list);
        this.context = context;
        this.resourceId = resource;
        this.followBtnClickListener = clickListener;
        this.myId = myId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        final int pos = position;
        final Context context = parent.getContext();
//        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        View rowView = inflater.inflate(R.layout.single_reg_student, parent, false);

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(this.resourceId, parent, false);
        }

        final TextView nameTextView = (TextView) convertView.findViewById(R.id.textView1);
        final StudentListViewItem studentListViewItem = (StudentListViewItem)getItem(position);

        nameTextView.setText(studentListViewItem.getText());

//        Button button1 = (Button) convertView.findViewById(R.id.button1);
//        button1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });

        Button button2 = (Button) convertView.findViewById(R.id.button2);
        button2.setTag(position);
        button2.setOnClickListener(this);


//        TextView nameTextView = (TextView) rowView.findViewById(R.id.reg_name);
//        ImageButton imageButton = (ImageButton) rowView.findViewById(R.id.follow_btn);
////        imageButton.setImageResource(R.drawable.plus_icon);
//        imageButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                new JSONTask().execute("http://192.249.18.227:3000/get_friends_of");
//            }
//        });
//        nameTextView.setText(regStudents.get(position).getFirstName());

        return convertView;
    }
    public void onClick(View v){
        if (this.followBtnClickListener != null) {
            this.followBtnClickListener.onFollowBtnClick((int)v.getTag()); ;
        }
    }

    //아마 필요 없을듯> SubjectInfoActivity로 옮김
//    public class JSONTask extends AsyncTask<String, String, String> {
//        @Override
//        protected String doInBackground(String... urls) {
//
//            try {
//                //JSONObject를 만들고 key value 형식으로 값을 저장해준다.
//                JSONObject jsonObject = new JSONObject();
//                jsonObject.accumulate("targetID", myId);
//                jsonObject.accumulate("newFriendId", )
//                HttpURLConnection con = null;
//                BufferedReader reader = null;
//                try{
//                    //URL url = new URL("http://192.249.18.247:3000/users");
//                    URL url = new URL(urls[0]);
//                    //연결을 함
//                    con = (HttpURLConnection) url.openConnection();
//                    con.setRequestMethod("POST");//POST방식으로 보냄
//                    con.setRequestProperty("Cache-Control", "no-cache");//캐시 설정
//                    con.setRequestProperty("Content-Type", "application/json");//application JSON 형식으로 전송
//                    con.setRequestProperty("Accept", "text/html");//서버에 response 데이터를 html로 받음
//                    con.setDoOutput(true);//Outstream으로 post 데이터를 넘겨주겠다는 의미
//                    con.setDoInput(true);//Inputstream으로 서버로부터 응답을 받겠다는 의미
//                    Log.d("connect false/true","connecting?");
//                    con.connect();
//                    System.out.println("reached1");
//                    //서버로 보내기위해서 스트림 만듬
//                    OutputStream outStream = con.getOutputStream();
//                    //버퍼를 생성하고 넣음
//                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outStream));
//                    writer.write(jsonObject.toString());
//                    writer.flush();
//                    writer.close();//버퍼를 받아줌
//                    //서버로 부터 데이터를 받음
//                    InputStream stream = con.getInputStream();
//                    reader = new BufferedReader(new InputStreamReader(stream));
//                    StringBuffer buffer = new StringBuffer();
//                    String line = "";
//                    while((line = reader.readLine()) != null){
//                        buffer.append(line);
//                    }
//                    Log.d("buffer ===>>>>", buffer.toString());
//                    return buffer.toString();//서버로 부터 받은 값을 리턴해줌 아마 OK!!가 들어올것임
//                } catch (MalformedURLException e){
//                    e.printStackTrace();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                } finally {
//                    if(con != null){
//                        con.disconnect();
//                    }
//                    try {
//                        if(reader != null){
//                            reader.close();//버퍼를 닫아줌
//                        }
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(String result){
//            super.onPostExecute(result);
////            //Log.d("request ===>>>>>   ",result);
////            String json = "{contacts:"+result+"}";
////            try {
////                JSONObject jsonObject = new JSONObject(json);
////                JSONArray contactArray = jsonObject.getJSONArray("contacts");
////                for(int i=0; i<contactArray.length(); i++) {
////                    jsonObject = contactArray.getJSONObject(i);
////                    System.out.println(jsonObject.getString("name"));
////                    System.out.println(jsonObject.getString("number"));
////                    phoneNumbers.add(new PhoneNumber(jsonObject.getString("name"), jsonObject.getString("number")));
////                }
////                adapter = new PhoneNumberAdapter(getContext(), phoneNumbers);
////                listView.setAdapter(adapter);
////            } catch (JSONException e) {
////                e.printStackTrace();
////            }
////            //tempTextView.setText(result);
//            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//            startActivity(intent);
//        }
//    }
}