package com.example.employeemanagementsystem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.employeemanagementsystem.bean.Employee;
import com.example.employeemanagementsystem.bean.Jobs;
import com.example.employeemanagementsystem.bean.Pay_level;


import java.util.List;

public class CustomAdapter extends BaseAdapter {
    private Context context;
    private List data;
    private int item_layout_id;

    public CustomAdapter() {
    }

    public CustomAdapter(Context context, List data, int item_layout_id) {
        this.context = context;
        this.data = data;
        this.item_layout_id = item_layout_id;
    }

    @Override
    public int getCount() {
        if(data!=null)
            return data.size();
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (null != data)
            return data.get(position);
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (null == convertView) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(item_layout_id,null);
        }
        if(data.get(0) instanceof Jobs){
            TextView job=convertView.findViewById(R.id.job);
            job.setText(((Jobs)data.get(position)).getName());
        }else if(data.get(0) instanceof Pay_level){
            TextView salary=convertView.findViewById(R.id.salary);
            TextView money=convertView.findViewById(R.id.money);
            salary.setText(((Pay_level)data.get(position)).getName());
            money.setText(((Pay_level)data.get(position)).getBase_pay()+"￥");
        }else if(data.get(0) instanceof Employee){
            TextView id=convertView.findViewById(R.id.staffId);
            TextView name=convertView.findViewById(R.id.staffName);
            id.setText(((Employee)data.get(position)).getNum()+"");
            name.setText(((Employee)data.get(position)).getName());
        }
        return convertView;
    }
}
