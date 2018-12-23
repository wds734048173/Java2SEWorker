package org.lanqiao.model;

import java.util.List;

public class Order {
    private int id;
    private int deskId;
    private int allPrice;//获取总价需要计算
    private int isPay;//0未付，1已付
    private String remark;

    public Order() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDeskId() {
        return deskId;
    }

    public void setDeskId(int deskId) {
        this.deskId = deskId;
    }

    public int getAllPrice() {
        return allPrice;
    }

    public void setAllPrice(int allPrice) {
        this.allPrice = allPrice;
    }

    public int getIsPay() {
        return isPay;
    }

    public void setIsPay(int isPay) {
        this.isPay = isPay;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Order(int id, int deskId, int allPrice, int isPay, String remark) {
        this.id = id;
        this.deskId = deskId;
        this.allPrice = allPrice;
        this.isPay = isPay;
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", deskId=" + deskId +
                ", allPrice=" + allPrice +
                ", isPay=" + isPay +
                ", remark='" + remark + '\'' +
                '}';
    }
}
