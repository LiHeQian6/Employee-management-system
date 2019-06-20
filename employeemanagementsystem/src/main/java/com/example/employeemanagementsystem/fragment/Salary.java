package com.example.employeemanagementsystem.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.example.employeemanagementsystem.CustomAdapter;
import com.example.employeemanagementsystem.DiaLog;
import com.example.employeemanagementsystem.R;
import com.example.employeemanagementsystem.bean.Pay_level;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;



public class Salary extends Fragment {
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
                case 101:
                    if(((String)msg.obj).equals("数据删除失败,请确认没有员工属于该薪资水平！"))
                        Toast.makeText(getContext(),(String)msg.obj,Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };
    private List list = new ArrayList();
    private List data = new ArrayList();
    private List change = new ArrayList();
    private List delete = new ArrayList();
    private ListView listView;
    private CustomAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view=inflater.inflate(R.layout.salary,container,false);
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
                    QuarySalary(text.getText().toString());
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager=getFragmentManager();
                FragmentTransaction transaction=fragmentManager.beginTransaction();
                Pay_level pay_level=new Pay_level();
                DiaLog diaLog=new DiaLog(pay_level, 1, new DiaLog.onChangeListener() {
                    @Override
                    public boolean getData(Object obj) {
                        Pay_level pay_level=(Pay_level)obj;
                        for (int i = 0; i <list.size() ; i++) {
                            if(((Pay_level)list.get(i)).getName().equals(pay_level.getName())){
                                Toast.makeText(getContext(),"已存在该薪资水平",Toast.LENGTH_SHORT).show();
                                return false;
                            }
                        }
                        list.add(pay_level);
                        data.add(pay_level);
                        change.add(pay_level);
                        adapter.notifyDataSetChanged();
                        Toast.makeText(getContext(),"本地数据添加成功",Toast.LENGTH_SHORT).show();
                        return true;
                    }

                    @Override
                    public boolean isDelete(boolean is) {
                        return false;
                    }
                });
                if(!diaLog.isAdded())
                    transaction.add(diaLog,"INFO");
                transaction.show(diaLog);
                transaction.commit();
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                FragmentManager fragmentManager=getFragmentManager();
                FragmentTransaction transaction=fragmentManager.beginTransaction();
                DiaLog diaLog=new DiaLog(data.get(position),0, new DiaLog.onChangeListener() {
                    @Override
                    public boolean getData(Object obj) {
                        Pay_level pay_level=(Pay_level)obj;
                        if(pay_level.getName().equals("")){
                            Toast.makeText(getContext(),"薪资水平不能为空",Toast.LENGTH_SHORT).show();
                            return false;
                        }
                        if(pay_level.getLevel_id()==((Pay_level)list.get(position)).getLevel_id()&&pay_level.getName().equals(((Pay_level)list.get(position)).getName())&&pay_level.getBase_pay()==((Pay_level)list.get(position)).getBase_pay())
                            return true;
                        for (int i = 0; i <list.size() ; i++) {
                            if(i!=position&&((Pay_level)list.get(i)).getName().equals(pay_level.getName())){
                                Toast.makeText(getContext(),"已存在该薪资水平",Toast.LENGTH_SHORT).show();
                                return false;
                            }
                        }
                        for (int i = 0; i <change.size() ; i++) {
                            if(((Pay_level)change.get(i)).getName().equals(((Pay_level)list.get(position)).getName())){
                                change.remove(i--);
                            }
                        }
                        list.set(position,pay_level);
                        data.set(position,pay_level);
                        change.add(pay_level);
                        adapter.notifyDataSetChanged();
                        Toast.makeText(getContext(),"本地数据修改成功",Toast.LENGTH_SHORT).show();
                        return true;
                    }

                    @Override
                    public boolean isDelete(boolean is) {
                        if(is){
                            if(((Pay_level)data.get(position)).getLevel_id()!=-1)
                                delete.add(((Pay_level)data.get(position)).getLevel_id());
                            for (int i = 0; i <change.size() ; i++) {
                                if(((Pay_level)change.get(i)).getName().equals(((Pay_level)list.get(position)).getName())){
                                    change.remove(i--);
                                }
                            }
                            list.remove(position);
                            data.remove(position);
                            Log.e("薪资删除表",delete.toString());
                            adapter.notifyDataSetChanged();
                            return true;
                        }
                        return false;
                    }
                });
                if(!diaLog.isAdded())
                    transaction.add(diaLog,"INFO");
                transaction.show(diaLog);
                transaction.commit();
            }
        });
        return view;
    }

    /**
     * 上传数据
     */
    @Override
    public void onStop() {
        super.onStop();
        if(this.change.size()!=0) {
            String change = null;
            try {
                JSONArray changeArrary = new JSONArray();
                int length = this.change.size();
                for (int i = 0; i < length; i++) {
                    Pay_level pay_level = (Pay_level) this.change.get(i);
                    int Level_id = pay_level.getLevel_id();
                    String name = pay_level.getName();
                    double base_pay = pay_level.getBase_pay();
                    JSONObject Object = new JSONObject();
                    Object.put("name", name);
                    Object.put("id", Level_id);
                    Object.put("base_pay", base_pay);
                    changeArrary.put(Object);
                }
                change = changeArrary.toString();

            } catch (JSONException e) {
                e.printStackTrace();
            }
            final String finalChange = change;
            new Thread() {
                @Override
                public void run() {
                    try {
                        URL url = new URL("http://" + getResources().getString(R.string.IP) + ":8080/EmployeeManagementSystem_war_exploded/SalaryServlet?change=true");
                        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                        conn.setDoOutput(true);
                        conn.setRequestMethod("POST");
                        conn.setRequestProperty("Content-Type", "application/json");
                        conn.setRequestProperty("charset", "utf-8");
                        OutputStream stream = conn.getOutputStream();
                        stream.write(finalChange.getBytes());
                        stream.close();
                        InputStream in = conn.getInputStream();
                        BufferedReader reader = new BufferedReader(new InputStreamReader(in, "utf-8"));
                        String jsonString = reader.readLine();
                        Message message = new Message();
                        message.what = 101;
                        message.obj =jsonString;
                        handler.sendMessage(message);
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }.start();
            this.change.clear();
        }
        if(delete.size()!=0){
            String delete = null;
            try {
                JSONArray changeArrary = new JSONArray();
                int length = this.delete.size();
                for (int i = 0; i < length; i++) {
                    JSONObject Object = new JSONObject();
                    Object.put("id", this.delete.get(i));
                    changeArrary.put(Object);
                }
                delete = changeArrary.toString();

            } catch (JSONException e) {
                e.printStackTrace();
            }
            final String finalChange = delete;
            new Thread() {
                @Override
                public void run() {
                    try {
                        URL url = new URL("http://" + getResources().getString(R.string.IP) + ":8080/EmployeeManagementSystem_war_exploded/SalaryServlet?delete=true");
                        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                        conn.setDoOutput(true);
                        conn.setRequestMethod("POST");
                        conn.setRequestProperty("Content-Type", "application/json");
                        conn.setRequestProperty("charset", "utf-8");
                        OutputStream stream = conn.getOutputStream();
                        stream.write(finalChange.getBytes());
                        stream.close();
                        InputStream in = conn.getInputStream();
                        BufferedReader reader = new BufferedReader(new InputStreamReader(in, "utf-8"));
                        String jsonString = reader.readLine();
                        Message message = new Message();
                        message.what = 101;
                        message.obj =jsonString;
                        handler.sendMessage(message);
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }.start();
            this.delete.clear();
        }
    }



    /**
     * 查询单个薪资信息
     * @param text
     */
    private void QuarySalary(String text) {
        list.clear();
        list.addAll(data);
        for (int i = 0; i <list.size() ; i++) {
            if (!((Pay_level)list.get(i)).getName().contains(text))
                list.remove(i--);
        }
        if (list.size()==0)
            Toast.makeText(getContext(),"不存在该薪资信息",Toast.LENGTH_SHORT).show();
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
                    URL url = new URL("http://"+getResources().getString(R.string.IP)+":8080/EmployeeManagementSystem_war_exploded/SalaryServlet");
                    URLConnection conn = url.openConnection();
                    InputStream in = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in, "utf-8"));
                    String jsonString = reader.readLine();
                    List list = new ArrayList();
                    try {
                        JSONArray array=new JSONArray(jsonString);
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject jsonObject=array.getJSONObject(i);
                            Pay_level pay_level=new Pay_level();
                            pay_level.setLevel_id(jsonObject.getInt("level_id"));
                            pay_level.setName(jsonObject.getString("name"));
                            pay_level.setBase_pay(jsonObject.getDouble("base_pay"));
                            list.add(pay_level);
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
