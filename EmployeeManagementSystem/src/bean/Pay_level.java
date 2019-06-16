package bean;

public class Pay_level {
    private int level_id;
    private String name;
    private double base_pay;

    public Pay_level() {
    }

    public Pay_level(int level_id, String name, double base_pay) {
        this.level_id = level_id;
        this.name = name;
        this.base_pay = base_pay;
    }

    public int getLevel_id() {
        return level_id;
    }

    public void setLevel_id(int level_id) {
        this.level_id = level_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getBase_pay() {
        return base_pay;
    }

    public void setBase_pay(double base_pay) {
        this.base_pay = base_pay;
    }

    @Override
    public String toString() {
        return "Pay_level{" +
                "level_id=" + level_id +
                ", name='" + name + '\'' +
                ", base_pay=" + base_pay +
                '}';
    }
}
