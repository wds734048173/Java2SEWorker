package org.lanqiao.model;

public class User {
    private int id;
    private String username;
    private String password;
    private String tel;
    private int sex; //性别：1男2女
    private String address;
    private String email;

    public User() {
    }

    public User(int id, String username, String password, String tel, int sex, String address, String email) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.tel = tel;
        this.sex = sex;
        this.address = address;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", tel='" + tel + '\'' +
                ", sex=" + sex +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
