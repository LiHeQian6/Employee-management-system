package com.example.employeemanagementsystem;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.example.employeemanagementsystem.bean.Employee;
import com.example.employeemanagementsystem.bean.Jobs;
import com.example.employeemanagementsystem.bean.Pay_level;
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


public class DiaLog extends DialogFragment {
    private Object data;
    private int show_layout_id;
    private Object addr;
    private List<String> Position=new ArrayList<>();
    private List<String> salary=new ArrayList<>();
    private List<Double> base_pay=new ArrayList<>();
    private onChangeListener changeListener;
    private ArrayAdapter adapter1;
    private ArrayAdapter adapter2;
    private View view;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            EditText staffName = view.findViewById(R.id.addstaffName);
            EditText staffId = view.findViewById(R.id.addstaffId);
            Spinner staffJob = view.findViewById(R.id.addstaffJob);
            Spinner staffPay_level = view.findViewById(R.id.addstaffPay_level);
            switch (msg.what){
                case 101:
                    Position.clear();
                    Position.addAll((List<String>) msg.obj);
                    adapter1=new ArrayAdapter(getContext(),R.layout.support_simple_spinner_dropdown_item,Position);
                    staffJob.setAdapter(adapter1);
                    if(((Employee) data).getId()!=0){
                        staffId.setText(((Employee) data).getNum()+"");
                        staffName.setText(((Employee) data).getName());
                        staffJob.setSelection(adapter1.getPosition(((Employee) data).getJob()));
                    }
                    break;
                case 102:
                    salary.clear();
                    salary.addAll((List<String>) msg.obj);
                    break;
                case 103:
                    base_pay.clear();
                    base_pay.addAll((List<Double>) msg.obj);
                    adapter2=new ArrayAdapter(getContext(),R.layout.support_simple_spinner_dropdown_item,salary);
                    staffPay_level.setAdapter(adapter2);
                    if(((Employee) data).getId()!=0){
                        staffPay_level.setSelection(adapter2.getPosition(((Employee) data).getPay_level()));
                    }
                    break;
            }
        }
    };
    public DiaLog(){}
    @SuppressLint("ValidFragment")
    public DiaLog(Object data, int layout_id) {
        this.data = data;
        this.show_layout_id = layout_id;
    }
    @SuppressLint("ValidFragment")
    public DiaLog(Object data, int show_layout_id, onChangeListener changeListener) {
        this.data = data;
        this.show_layout_id = show_layout_id;
        this.changeListener = changeListener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view=inflater.inflate(R.layout.dialog_layout,container,false);
        if (getDialog().getWindow() != null) {
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            getDialog().getWindow().setDimAmount(0f);//去除遮罩
            getDialog().setCanceledOnTouchOutside(false);//点击外部取消
        }
        this.view=view;
        ImageView close=view.findViewById(R.id.close);
        LinearLayout show = view.findViewById(R.id.show);
        LinearLayout add = view.findViewById(R.id.add);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });
        if(show_layout_id==0) {
            add.setVisibility(View.GONE);
            final Button delete = view.findViewById(R.id.delete);
            final Button change = view.findViewById(R.id.change);
            final LinearLayout position = view.findViewById(R.id.position);
            final LinearLayout salary = view.findViewById(R.id.salary);
            final LinearLayout staff = view.findViewById(R.id.staff);
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (changeListener.isDelete(true)) {
                        Toast.makeText(getContext(), "本地数据删除成功", Toast.LENGTH_SHORT).show();
                        getDialog().dismiss();
                    }
                }
            });
            change.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final FragmentManager fragmentManager=getFragmentManager();
                    FragmentTransaction transaction=fragmentManager.beginTransaction();
                    DiaLog diaLog=new DiaLog(data, 1, new onChangeListener() {
                        @Override
                        public boolean getData(Object obj) {
                            if(changeListener.getData(obj)){
                                if (obj instanceof Jobs) {
                                    salary.setVisibility(View.GONE);
                                    staff.setVisibility(View.GONE);
                                    TextView name = view.findViewById(R.id.positionName);
                                    TextView description = view.findViewById(R.id.description);
                                    name.setText("职位名称：" + ((Jobs) obj).getName());
                                    description.setText("职位描述：" + ((Jobs) obj).getDescription());
                                } else if (obj instanceof Pay_level) {
                                    position.setVisibility(View.GONE);
                                    staff.setVisibility(View.GONE);
                                    TextView salaryName = view.findViewById(R.id.salaryName);
                                    TextView base_pay = view.findViewById(R.id.base_pay);
                                    salaryName.setText("薪资水平：" + ((Pay_level) obj).getName());
                                    base_pay.setText("基础工资：" + ((Pay_level) obj).getBase_pay());
                                } else if (obj instanceof Employee) {
                                    position.setVisibility(View.GONE);
                                    salary.setVisibility(View.GONE);
                                    TextView staffName = view.findViewById(R.id.staffName);
                                    TextView staffId = view.findViewById(R.id.staffId);
                                    TextView staffJob = view.findViewById(R.id.staffJob);
                                    TextView staffPay_level = view.findViewById(R.id.staffPay_level);
                                    TextView staffPay = view.findViewById(R.id.staffPay);
                                    staffName.setText("员工姓名：" + ((Employee) obj).getName());
                                    staffId.setText("工号：" + ((Employee) obj).getNum());
                                    staffJob.setText("职位：" + ((Employee) obj).getJob());
                                    staffPay_level.setText("薪资水平：" + ((Employee) obj).getPay_level());
                                    staffPay.setText("基础工资：" + ((Employee) obj).getBase_pay()+"￥");
                                }
                                data=obj;
                                return true;
                            }
                            return false;
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
            if (data instanceof Jobs) {
                salary.setVisibility(View.GONE);
                staff.setVisibility(View.GONE);
                TextView name = view.findViewById(R.id.positionName);
                TextView description = view.findViewById(R.id.description);
                name.setText("职位名称：" + ((Jobs) data).getName());
                description.setText("职位描述：" + ((Jobs) data).getDescription());
            } else if (data instanceof Pay_level) {
                position.setVisibility(View.GONE);
                staff.setVisibility(View.GONE);
                TextView salaryName = view.findViewById(R.id.salaryName);
                TextView base_pay = view.findViewById(R.id.base_pay);
                salaryName.setText("薪资水平：" + ((Pay_level) data).getName());
                base_pay.setText("基础工资：" + ((Pay_level) data).getBase_pay());
            } else if (data instanceof Employee) {
                position.setVisibility(View.GONE);
                salary.setVisibility(View.GONE);
                TextView staffName = view.findViewById(R.id.staffName);
                TextView staffId = view.findViewById(R.id.staffId);
                TextView staffJob = view.findViewById(R.id.staffJob);
                TextView staffPay_level = view.findViewById(R.id.staffPay_level);
                TextView staffPay = view.findViewById(R.id.staffPay);
                staffName.setText("员工姓名：" + ((Employee) data).getName());
                staffId.setText("工号：" + ((Employee) data).getNum());
                staffJob.setText("职位：" + ((Employee) data).getJob());
                staffPay_level.setText("薪资水平：" + ((Employee) data).getPay_level());
                staffPay.setText("基础工资：" + ((Employee) data).getBase_pay()+"￥");
            }
        }else if(show_layout_id==1){
            show.setVisibility(View.GONE);
            Button save = view.findViewById(R.id.save);
            Button back = view.findViewById(R.id.back);
            LinearLayout addposition = view.findViewById(R.id.addposition);
            LinearLayout addsalary = view.findViewById(R.id.addsalary);
            LinearLayout addstaff = view.findViewById(R.id.addstaff);
            if (data instanceof Jobs) {
                addsalary.setVisibility(View.GONE);
                addstaff.setVisibility(View.GONE);
                final EditText name = view.findViewById(R.id.addpositionName);
                final EditText description = view.findViewById(R.id.adddescription);
                if(0!=((Jobs) data).getId()){
                    name.setText(((Jobs) data).getName());
                    description.setText(((Jobs) data).getDescription());
                }
                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Jobs jobs=new Jobs();
                        if(!name.getText().toString().equals("")) {
                            jobs.setId(-1);//添加标识
                            if(0!=((Jobs) data).getId())
                                jobs.setId(((Jobs) data).getId());//修改标识
                            jobs.setName(name.getText().toString());
                            jobs.setDescription(description.getText().toString());
                            addr = jobs;
                            if (changeListener.getData(addr))
                                getDialog().dismiss();
                        }else
                            Toast.makeText(getContext(),"职位名称不能为空！",Toast.LENGTH_SHORT).show();
                    }
                });

            } else if (data instanceof Pay_level) {
                addposition.setVisibility(View.GONE);
                addstaff.setVisibility(View.GONE);
                final EditText salaryName = view.findViewById(R.id.addsalaryName);
                final EditText base_pay = view.findViewById(R.id.addbase_pay);
                if(0!=((Pay_level) data).getLevel_id()){
                    salaryName.setText(((Pay_level) data).getName());
                    base_pay.setText(((Pay_level) data).getBase_pay()+"");
                }
                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Pay_level pay_level=new Pay_level();
                        if(!salaryName.getText().toString().equals("")&&!base_pay.getText().toString().equals("")) {
                            pay_level.setLevel_id(-1);
                            if(0!=((Pay_level) data).getLevel_id())
                                pay_level.setLevel_id(((Pay_level) data).getLevel_id());//修改标识
                            pay_level.setName(salaryName.getText().toString());
                            pay_level.setBase_pay(Double.parseDouble(base_pay.getText().toString()));
                            addr=pay_level;
                            if(changeListener.getData(addr));
                                getDialog().dismiss();
                        }else
                            Toast.makeText(getContext(),"薪资水平和基础工资不能为空！",Toast.LENGTH_SHORT).show();
                    }
                });

            } else if (data instanceof Employee) {
                addposition.setVisibility(View.GONE);
                addsalary.setVisibility(View.GONE);
                final Employee employee=new Employee();
                final Spinner staffJob = view.findViewById(R.id.addstaffJob);
                final Spinner staffPay_level = view.findViewById(R.id.addstaffPay_level);
                getPosition();
                getPay();
                staffJob.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        employee.setJob(Position.get(position));
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        employee.setJob(Position.get(0));
                    }
                });
                staffPay_level.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        employee.setPay_level(salary.get(position));
                        employee.setBase_pay(base_pay.get(position));
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        employee.setPay_level(salary.get(0));
                        employee.setBase_pay(base_pay.get(0));
                    }
                });
                final EditText staffName = view.findViewById(R.id.addstaffName);
                final EditText staffId = view.findViewById(R.id.addstaffId);
                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(!staffName.getText().toString().equals("")&&!staffId.getText().toString().equals("")) {
                            employee.setNum(Integer.parseInt(staffId.getText().toString()));
                            if(((Employee) data).getId()!=0)
                                employee.setId(((Employee) data).getId());
                            else
                                employee.setId(-1);
                            employee.setName(staffName.getText().toString());
                            addr=employee;
                            if(changeListener.getData(addr))
                                getDialog().dismiss();
                        }else
                            Toast.makeText(getContext(),"员工姓名和员工工号不能为空！",Toast.LENGTH_SHORT).show();
                    }
                });

            }
            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getDialog().dismiss();
                }
            });
        }
        return view;
    }
    public interface onChangeListener{
        boolean getData(Object obj);
        boolean isDelete(boolean is);
    }
    public void getPosition(){
        new Thread() {
            @Override
            public void run() {

                try {
                    URL url = new URL("http://"+getResources().getString(R.string.IP)+":8080/EmployeeManagementSystem_war_exploded/PositionServlet");
                    URLConnection conn = url.openConnection();
                    InputStream in = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in, "utf-8"));
                    String jsonString = reader.readLine();
                    List<String> list = new ArrayList();
                    try {
                        JSONArray array=new JSONArray(jsonString);
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject jsonObject=array.getJSONObject(i);
                            Jobs jobs=new Jobs();
                            jobs.setId(jsonObject.getInt("id"));
                            jobs.setName(jsonObject.getString("name"));
                            jobs.setDescription(jsonObject.getString("description"));
                            list.add(jobs.getName());
                        }
                        Message message=new Message();
                        message.what=101;
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
    public void getPay(){
        new Thread() {
            @Override
            public void run() {
                try {
                    URL url = new URL("http://"+getResources().getString(R.string.IP)+":8080/EmployeeManagementSystem_war_exploded/SalaryServlet");
                    URLConnection conn = url.openConnection();
                    InputStream in = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in, "utf-8"));
                    String jsonString = reader.readLine();
                    List list1 = new ArrayList();
                    List list2 = new ArrayList();
                    try {
                        JSONArray array=new JSONArray(jsonString);
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject jsonObject=array.getJSONObject(i);
                            Pay_level pay_level=new Pay_level();
                            pay_level.setLevel_id(jsonObject.getInt("level_id"));
                            pay_level.setName(jsonObject.getString("name"));
                            pay_level.setBase_pay(jsonObject.getDouble("base_pay"));
                            list1.add(pay_level.getName());
                            list2.add(pay_level.getBase_pay());
                        }
                        Message message=new Message();
                        message.what=102;
                        message.obj=list1;
                        handler.sendMessage(message);
                        Message message2=new Message();
                        message2.what=103;
                        message2.obj=list2;
                        handler.sendMessage(message2);
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
