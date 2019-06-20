package com.example.employeemanagementsystem.bean;

public class Employee {
    private int id;
    private String name;
    private int num;
    private String job;
    private String pay_level;
    private double base_pay;

    public Employee() { }

    public Employee(int id, String name, int num, String job, String pay_level, double base_pay) {
        this.id = id;
        this.name = name;
        this.num = num;
        this.job = job;
        this.pay_level = pay_level;
        this.base_pay = base_pay;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getPay_level() {
        return pay_level;
    }

    public void setPay_level(String pay_level) {
        this.pay_level = pay_level;
    }

    public double getBase_pay() {
        return base_pay;
    }

    public void setBase_pay(double base_pay) {
        this.base_pay = base_pay;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", num=" + num +
                ", job='" + job + '\'' +
                ", pay_level='" + pay_level + '\'' +
                ", base_pay=" + base_pay +
                '}';
    }
}
