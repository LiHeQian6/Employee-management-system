package com.example.employeemanagementsystem;

import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import com.example.employeemanagementsystem.fragment.Position;
import com.example.employeemanagementsystem.fragment.Salary;
import com.example.employeemanagementsystem.fragment.Staff;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private Map<String,ImageView> imageViewMap=new HashMap<>();
    private Map<String,TextView> textViewMap=new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentTabHost fragmentTabHost=findViewById(android.R.id.tabhost);
        fragmentTabHost.setup(this,getSupportFragmentManager(),android.R.id.tabcontent);
        TabHost.TabSpec tabSpec1=fragmentTabHost.newTabSpec("Position").setIndicator(getTabSpecView("Position",R.drawable.position1,"职位管理"));
        fragmentTabHost.addTab(tabSpec1, Position.class,null);
        TabHost.TabSpec tabSpec2=fragmentTabHost.newTabSpec("Salary").setIndicator(getTabSpecView("Salary",R.drawable.salary1,"薪资管理"));
        fragmentTabHost.addTab(tabSpec2, Salary.class,null);
        TabHost.TabSpec tabSpec3=fragmentTabHost.newTabSpec("Staff").setIndicator(getTabSpecView("Staff",R.drawable.staff1,"员工管理"));
        fragmentTabHost.addTab(tabSpec3, Staff.class,null);
        fragmentTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                textViewMap.get(tabId).setTextColor(getResources().getColor(R.color.colorBlack));
                switch (tabId){
                    case "Position":
                        imageViewMap.get("Position").setImageResource(R.drawable.position2);
                        imageViewMap.get("Salary").setImageResource(R.drawable.salary1);
                        imageViewMap.get("Staff").setImageResource(R.drawable.staff1);
                        textViewMap.get("Salary").setTextColor(getResources().getColor(android.R.color.darker_gray));
                        textViewMap.get("Staff").setTextColor(getResources().getColor(android.R.color.darker_gray));
                        break;
                    case "Salary":
                        imageViewMap.get("Position").setImageResource(R.drawable.position1);
                        imageViewMap.get("Salary").setImageResource(R.drawable.salary2);
                        imageViewMap.get("Staff").setImageResource(R.drawable.staff1);
                        textViewMap.get("Position").setTextColor(getResources().getColor(android.R.color.darker_gray));
                        textViewMap.get("Staff").setTextColor(getResources().getColor(android.R.color.darker_gray));
                        break;
                    case "Staff":
                        imageViewMap.get("Position").setImageResource(R.drawable.position1);
                        imageViewMap.get("Salary").setImageResource(R.drawable.salary1);
                        imageViewMap.get("Staff").setImageResource(R.drawable.staff2);
                        textViewMap.get("Salary").setTextColor(getResources().getColor(android.R.color.darker_gray));
                        textViewMap.get("Position").setTextColor(getResources().getColor(android.R.color.darker_gray));
                        break;
                }
            }
        });
        fragmentTabHost.setCurrentTab(1);
        fragmentTabHost.setCurrentTab(0);

    }
    public View getTabSpecView(String tag, int imgId, String title){
        //LayoutInflater layoutInflater=LayoutInflater.from(this);
        LayoutInflater layoutInflater=getLayoutInflater();
        View view=layoutInflater.inflate(R.layout.tab_spec,null);
        ImageView imageView=view.findViewById(R.id.iv_icon);
        imageView.setImageResource(imgId);
        TextView textView=view.findViewById(R.id.tv_txt);
        textView.setText(title);
        imageViewMap.put(tag,imageView);
        textViewMap.put(tag,textView);
        return view;
    }
}
