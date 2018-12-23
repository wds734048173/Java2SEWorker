package org.lanqiao.model;

public class Desk {
    private int id;
    private String name;
    private int state;//state状态：0，空闲；1预定；2使用中
    private String remark;

    public Desk() {
    }

    public Desk(int id, String name, int state, String remark) {
        this.id = id;
        this.name = name;
        this.state = state;
        this.remark = remark;
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

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "Desk{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", state=" + state +
                ", remark='" + remark + '\'' +
                '}';
    }
}
