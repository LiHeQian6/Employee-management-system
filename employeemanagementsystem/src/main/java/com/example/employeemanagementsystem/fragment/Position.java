package com.example.employeemanagementsystem.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.example.employeemanagementsystem.CustomAdapter;
import com.example.employeemanagementsystem.R;
import com.example.employeemanagementsystem.bean.Jobs;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;


public class Position extends Fragment {
    @SuppressLint("HandlerLeak")
    /**
     * 接收数据并绑定适配器
     */
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 100:
                    list.clear();
                    data.clear();
                    list.addAll((List) msg.obj);
                    data.addAll((List) msg.obj);
                    adapter=new CustomAdapter(getContext(),list,R.layout.item_layout);
                    listView.setAdapter(adapter);
                    break;
            }
        }
    };
    private List list = new ArrayList();
    private List data = new ArrayList();
    private ListView listView;
    private CustomAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view=inflater.inflate(R.layout.position,container,false);
        ImageView add=view.findViewById(R.id.add);
        Button quary=view.findViewById(R.id.query);
        listView=view.findViewById(R.id.lv_name);
        GetData();
        quary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText text=view.findViewById(R.id.text);
                if("".equals(text.getText().toString()))
                    GetData();
                else
                    QuaryPosition(text.getText().toString());
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return view;
    }

    /**
     * 查询单个职位
     * @param text
     */
    private void QuaryPosition(String text) {
        list.clear();
        list.addAll(data);
        for (int i = 0; i <list.size() ; i++) {
            if (!((Jobs)list.get(i)).getName().contains(text))
                list.remove(i--);
        }
        if (list.size()==0)
            Toast.makeText(getContext(),"不存在该职位信息",Toast.LENGTH_SHORT).show();
        adapter.notifyDataSetChanged();
    }
    /***
     * 从服务端获取数据，并发送到主线程
     */
    public void GetData(){
        new Thread() {
            @Override
            public void run() {

                try {
                    URL url = new URL("http://192.168.1.102:8080/EmployeeManagementSystem_war_exploded/PositionServlet");
                    URLConnection conn = url.openConnection();
                    InputStream in = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in, "utf-8"));
                    String jsonString = reader.readLine();
                    List list = new ArrayList();
                    try {
                        JSONArray array=new JSONArray(jsonString);

                        for (int i = 0; i < array.length(); i++) {
                            JSONObject jsonObject=array.getJSONObject(i);
                            Jobs jobs=new Jobs();
                            jobs.setId(jsonObject.getInt("id"));
                            jobs.setName(jsonObject.getString("name"));
                            jobs.setDescription(jsonObject.getString("description"));
                            list.add(jobs);
                        }
                        Message message=new Message();
                        message.what=100;
                        message.obj=list;
                        handler.sendMessage(message);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();

    }
}
