package org.lanqiao.model;

public class Greens {
    private int id;
    private String name;
    private int price;
    private int greensClassId;

    public Greens() {
    }

    public Greens(int id, String name, int price, int greensClassId) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.greensClassId = greensClassId;
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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getGreensClassId() {
        return greensClassId;
    }

    public void setGreensClassId(int greensClassId) {
        this.greensClassId = greensClassId;
    }

    @Override
    public String toString() {
        return "Greens{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", greensClassId=" + greensClassId +
                '}';
    }
}
