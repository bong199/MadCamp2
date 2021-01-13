package com.example.weektwotest.ui.main;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

public class PostFragment extends Fragment implements View.OnClickListener{

    private static final String ARG_SECTION_NUMBER = "section_number";
    public PostAdapter adapter;
    static RecyclerView recyclerView2;

    public static PostFragment newInstance(int index) {
        PostFragment fragment = new PostFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.post_fragment, container, false);
        recyclerView2 = view.findViewById(R.id.recyclerview_post_images);
        recyclerView2.addItemDecoration(new DividerItemDecoration(view.getContext(), 1));
        recyclerView2.setHasFixedSize(true);
        recyclerView2.setLayoutManager(new GridLayoutManager(getContext(),1));
        new JSONTask().execute("http://192.249.18.227:3000/post_fd");
        return view;
    }


    public class JSONTask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... urls) {
            try {
                //JSONObject를 만들고 key value 형식으로 값을 저장해준다.
                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("user_id", "androidTest");
                jsonObject.accumulate("name", "yun");

                HttpURLConnection con = null;
                BufferedReader reader = null;
                System.out.println("doInBg");
                try{
                    //URL url = new URL("http://192.168.25.16:3000/users");
                    URL url = new URL(urls[0]);
                    //연결을 함
                    con = (HttpURLConnection) url.openConnection();

                    con.setRequestMethod("POST");//POST방식으로 보냄
                    con.setRequestProperty("Cache-Control", "no-cache");//캐시 설정
                    con.setRequestProperty("Content-Type", "application/json");//application JSON 형식으로 전송


                    con.setRequestProperty("Accept", "text/html");//서버에 response 데이터를 html로 받음
                    con.setDoOutput(true);//Outstream으로 post 데이터를 넘겨주겠다는 의미
                    con.setDoInput(true);//Inputstream으로 서버로부터 응답을 받겠다는 의미
                    con.connect();

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
                        System.out.println(line);
                        buffer.append(line);
                    }
                    System.out.println(buffer.toString());
                    Log.d("confirm!!!!!1",buffer.toString());
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
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            //System.out.println(result);
            result = result.substring(0,result.length()-1);
            result = "[" + result;
            result = result + "]";
            ArrayList<String> images = new ArrayList<>();
            ArrayList<ImageClass> imagecon = new ArrayList<ImageClass>();
            Log.d("onPostExcute ====  >>>>",result);
            String json = "{imgPaths:"+result+"}";
            try{
                JSONObject jsonObject = new JSONObject(json);
                JSONArray imageArray = jsonObject.getJSONArray("imgPaths");
                for(int i=0; i<imageArray.length(); i++) {
                    ImageClass imageclass = new ImageClass();
                    jsonObject = imageArray.getJSONObject(i);
                    imageclass.setImage(jsonObject.getString("path"));
                    imageclass.setContent(jsonObject.getString("context"));
                    imagecon.add(imageclass);
                    images.add(jsonObject.getString("path"));
//                    System.out.println(jsonObject.getString("path"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            adapter = new PostAdapter(getContext(), imagecon);
            Log.d("adapter_run","adapter_running!!!!!!!");
            recyclerView2.setAdapter(adapter);
        }
    }



    @Override
    public void onClick(View v) {

    }
}
