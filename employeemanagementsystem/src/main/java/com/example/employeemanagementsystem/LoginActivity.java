package com.example.employeemanagementsystem;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TabHost;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class LoginActivity extends AppCompatActivity {
    private Button login;
    private EditText name;
    private EditText pwd;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String info = (String) msg.obj;
            if("true".equals(info)) {
                Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent();
                intent.setClass(LoginActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
            else
                Toast.makeText(LoginActivity.this,"用户名或密码错误",Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login = findViewById(R.id.login);
        name = findViewById(R.id.name);
        pwd=findViewById(R.id.pwd);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendToServer();
            }
        });
    }

    private void sendToServer() {
        new Thread() {
            @Override
            public void run() {
                try {
                    URL url = new URL("http://192.168.1.102:8080/EmployeeManagementSystem_war_exploded/LoginServlet?name="+name.getText().toString()+"&pwd="+pwd.getText().toString());
                    URLConnection conn = url.openConnection();
                    InputStream in = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in, "utf-8"));
                    String info = reader.readLine();
                    Message msg = Message.obtain();
                    msg.obj = info;
                    handler.sendMessage(msg);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
