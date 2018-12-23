package org.lanqiao.model;

public class OrderItem {
    private int id;
    private int orderId;
    private int greensId;
    //冗余字段，防止修改价格，菜名等造成数据不准的问题
    private String greensName;
    private int number;
    private int greensPrice;

    public OrderItem() {
    }

    public OrderItem(int id, int orderId, int greensId, String greensName, int number, int greensPrice) {
        this.id = id;
        this.orderId = orderId;
        this.greensId = greensId;
        this.greensName = greensName;
        this.number = number;
        this.greensPrice = greensPrice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getGreensId() {
        return greensId;
    }

    public void setGreensId(int greensId) {
        this.greensId = greensId;
    }

    public String getGreensName() {
        return greensName;
    }

    public void setGreensName(String greensName) {
        this.greensName = greensName;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getGreensPrice() {
        return greensPrice;
    }

    public void setGreensPrice(int greensPrice) {
        this.greensPrice = greensPrice;
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "id=" + id +
                ", orderId=" + orderId +
                ", greensId=" + greensId +
                ", greensName='" + greensName + '\'' +
                ", number=" + number +
                ", greensPrice=" + greensPrice +
                '}';
    }
}
