package com.example.employeemanagementsystem;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.example.employeemanagementsystem.bean.Employee;
import com.example.employeemanagementsystem.bean.Jobs;
import com.example.employeemanagementsystem.bean.Pay_level;


public class DiaLog<T> extends DialogFragment {
    private T data;
    private int show_layout_id;
    private Object addr;
    private onChangeListener changeListener;
    public DiaLog(){}
    @SuppressLint("ValidFragment")
    public DiaLog(T data, int layout_id) {
        this.data = data;
        this.show_layout_id = layout_id;
    }
    @SuppressLint("ValidFragment")
    public DiaLog(T data, int show_layout_id, onChangeListener changeListener) {
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
            Button delete = view.findViewById(R.id.delete);
            Button change = view.findViewById(R.id.change);
            LinearLayout position = view.findViewById(R.id.position);
            LinearLayout salary = view.findViewById(R.id.salary);
            LinearLayout staff = view.findViewById(R.id.staff);
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
                staffId.setText("工号：" + ((Employee) data).getId());
                staffJob.setText("职位：" + ((Employee) data).getJob());
                staffPay_level.setText("薪资水平：" + ((Employee) data).getPay_level());
                staffPay.setText("基础工资：" + ((Employee) data).getBase_pay());
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
                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Jobs jobs=new Jobs();
                        EditText name = view.findViewById(R.id.addpositionName);
                        EditText description = view.findViewById(R.id.adddescription);
                        if(!name.getText().toString().equals("")) {
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
                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Pay_level pay_level=new Pay_level();
                        EditText salaryName = view.findViewById(R.id.addsalaryName);
                        EditText base_pay = view.findViewById(R.id.addbase_pay);
                        if(!salaryName.getText().toString().equals("")) {
                            pay_level.setName(salaryName.getText().toString());
                            pay_level.setBase_pay(Double.valueOf(base_pay.getText().toString()));
                            addr=pay_level;
                            if(changeListener.getData(addr));
                                getDialog().dismiss();
                        }else
                            Toast.makeText(getContext(),"薪资水平不能为空！",Toast.LENGTH_SHORT).show();
                    }
                });

            } else if (data instanceof Employee) {
                addposition.setVisibility(View.GONE);
                addsalary.setVisibility(View.GONE);
                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Employee employee=new Employee();
                        EditText staffName = view.findViewById(R.id.addstaffName);
                        EditText staffId = view.findViewById(R.id.addstaffId);
                        EditText staffJob = view.findViewById(R.id.addstaffJob);
                        EditText staffPay_level = view.findViewById(R.id.addstaffPay_level);
                        EditText staffPay = view.findViewById(R.id.addstaffPay);
                        if(!staffName.getText().toString().equals("")&&!staffId.getText().toString().equals("")) {
                            employee.setId(Integer.parseInt(staffId.getText().toString()));
                            employee.setName(staffName.getText().toString());
                            employee.setJob(staffJob.getText().toString());
                            employee.setPay_level(staffPay_level.getText().toString());
                            employee.setBase_pay(Double.valueOf(staffPay.getText().toString()));
                            addr=employee;
                            if(changeListener.getData(addr));
                                getDialog().dismiss();
                        }else
                            Toast.makeText(getContext(),"员工姓名和工号不能为空！",Toast.LENGTH_SHORT).show();
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
        boolean getData(Object data);
    }
}
