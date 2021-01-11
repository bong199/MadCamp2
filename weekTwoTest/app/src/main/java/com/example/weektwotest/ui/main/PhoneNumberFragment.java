package com.example.weektwotest.ui.main;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.weektwotest.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class PhoneNumberFragment extends Fragment {
    private static final String ARG_SECTION_NUMBER = "section_number";
    PhoneNumberAdapter adapter;
    ArrayList<PhoneNumber> phoneNumbers = new ArrayList<>();;
    private ListView listView;
    private TextView tempTextView;

    public static PhoneNumberFragment newInstance(int index) {
        PhoneNumberFragment fragment = new PhoneNumberFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }
    @Override
    public void onStart() {
        super.onStart();
//        adapter = new PhoneNumberAdapter(getContext(), phoneNumbers);
//        listView.setAdapter(adapter);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        System.out.println("fffffff2");
        View view = inflater.inflate(R.layout.phonenumber_fragment, container, false);
        //tempTextView = view.findViewById(R.id.tempTV);
        listView = view.findViewById(R.id.cont_lv);
        FloatingActionButton fab = view.findViewById(R.id.fab);
        System.out.println("called11111");
        phoneNumbers.clear();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), PhoneNumberAdd.class);
                startActivity(intent);
            }
        });
        System.out.println("reached1");
        new JSONTask().execute("http://192.249.18.247:3030/users");
        return view;
    }
    public class JSONTask extends AsyncTask<String, String, String>{
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
                    con.connect();
                    InputStream stream = con.getInputStream();
                    reader = new BufferedReader(new InputStreamReader(stream));
                    StringBuffer buffer = new StringBuffer();

//                    con.setRequestMethod("GET");//POST방식으로 보냄
//                    con.setRequestProperty("Cache-Control", "no-cache");//캐시 설정
//                    con.setRequestProperty("Content-Type", "application/json");//application JSON 형식으로 전송
//                    con.setRequestProperty("Accept", "text/html");//서버에 response 데이터를 html로 받음
//                    con.setDoOutput(true);//Outstream으로 post 데이터를 넘겨주겠다는 의미
//                    con.setDoInput(true);//Inputstream으로 서버로부터 응답을 받겠다는 의미
//                    Log.d("connect false/true","connecting?");
//                    con.connect();
                    System.out.println("reached2");
                    //서버로 보내기위해서 스트림 만듬
//                    OutputStream outStream = con.getOutputStream();
//                    //버퍼를 생성하고 넣음
//                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outStream));
//                    writer.write(jsonObject.toString());
//                    writer.flush();
//                    writer.close();//버퍼를 받아줌
                    //서버로 부터 데이터를 받음
//                    InputStream stream = con.getInputStream();
//                    reader = new BufferedReader(new InputStreamReader(stream));
//                    StringBuffer buffer = new StringBuffer();
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
            String json = "{contacts:"+result+"}";
            try {
                JSONObject jsonObject = new JSONObject(json);
                JSONArray contactArray = jsonObject.getJSONArray("contacts");
                for(int i=0; i<contactArray.length(); i++) {
                    jsonObject = contactArray.getJSONObject(i);
                    System.out.println(jsonObject.getString("name"));
                    System.out.println(jsonObject.getString("number"));
                    System.out.println(jsonObject.getString("email"));
                    phoneNumbers.add(new PhoneNumber(jsonObject.getString("name"), jsonObject.getString("number"), jsonObject.getString("email")));
                }
                adapter = new PhoneNumberAdapter(getContext(), phoneNumbers);
                listView.setAdapter(adapter);
                listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(getActivity(), PhoneNumberEdit.class);
                        intent.putExtra("Name", phoneNumbers.get(position).getName());
                        intent.putExtra("Number", phoneNumbers.get(position).getNumber());
                        intent.putExtra("Email", phoneNumbers.get(position).getEmail());
                        startActivity(intent);
                        return false;
                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
            }
            //tempTextView.setText(result);
        }
    }
}