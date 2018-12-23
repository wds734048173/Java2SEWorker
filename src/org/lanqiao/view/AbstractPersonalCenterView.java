package org.lanqiao.view;

import org.lanqiao.global.LayoutValue;
import org.lanqiao.model.User;
import org.lanqiao.util.GetDataUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public abstract class AbstractPersonalCenterView {
    private JFrame frame = null;
    private User user = null;
    //初始化数据
    private JPanel panel = null;//容器
    //顺序：id，用户名，密码，电话，性别，地址，邮箱
    JLabel usernameText = new JLabel("用户名：");
    JTextField username = new JTextField();
    JLabel passwordText = new JLabel("密  码：");
    JTextField password = new JTextField();
    JLabel telText = new JLabel("电  话：");
    JTextField tel = new JTextField();
    JLabel sexText = new JLabel("性  别：");
    String[] sexData = LayoutValue.sexData;
    JComboBox<String> sexBox = new JComboBox<String>(sexData);
    JLabel addressText = new JLabel("地  址：");
    JTextField address = new JTextField();
    JLabel emailText = new JLabel("邮  箱：");
    JTextField email = new JTextField();
    JButton button = new JButton("修改");

    //构造器
    public AbstractPersonalCenterView(JFrame frame, User user) {
        this.frame = frame;
        this.user = user;
        componetInit();
        init();
        addAction();
    }
    //设置元素大小等属性（每个按钮的大小，位置等）
    public void componetInit(){
        panel = new JPanel(null){
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                //添加背景图片
                ImageIcon img = new ImageIcon("imgs/背景1.jpg");
                g.drawImage(img.getImage(),0,0,null);
            }
        };
        //添加数据
        username.setText(user.getUsername());
        password.setText(user.getPassword());
        tel.setText(user.getTel());
        sexBox.setSelectedIndex(user.getSex());
        address.setText(user.getAddress());
        email.setText(user.getEmail()
        );
        //用户名不可重复
        Object[][] objects = GetDataUtils.getUser();
        usernameText.setBounds(500,50, LayoutValue.TEXT_WIDTH,LayoutValue.TEXT_HEIGHT);
        username.setBounds(600,50,LayoutValue.TEXT_WIDTH,LayoutValue.TEXT_HEIGHT);
        passwordText.setBounds(500,100,LayoutValue.TEXT_WIDTH,LayoutValue.TEXT_HEIGHT);
        password.setBounds(600,100,LayoutValue.TEXT_WIDTH,LayoutValue.TEXT_HEIGHT);
        telText.setBounds(500,150, LayoutValue.TEXT_WIDTH,LayoutValue.TEXT_HEIGHT);
        tel.setBounds(600,150,LayoutValue.TEXT_WIDTH,LayoutValue.TEXT_HEIGHT);
        sexText.setBounds(500,200,LayoutValue.TEXT_WIDTH,LayoutValue.TEXT_HEIGHT);
        sexBox.setBounds(600,200,LayoutValue.TEXT_WIDTH,LayoutValue.TEXT_HEIGHT);
        addressText.setBounds(500,250, LayoutValue.TEXT_WIDTH,LayoutValue.TEXT_HEIGHT);
        address.setBounds(600,250,LayoutValue.TEXT_WIDTH,LayoutValue.TEXT_HEIGHT);
        emailText.setBounds(500,300,LayoutValue.TEXT_WIDTH,LayoutValue.TEXT_HEIGHT);
        email.setBounds(600,300,LayoutValue.TEXT_WIDTH,LayoutValue.TEXT_HEIGHT);
        button.setBounds(550,350,LayoutValue.BNT_WIDTH,LayoutValue.BNT_HEIGHT);
    }
    //设置布局（把元素放到对应的容器中）
    public void init(){
        panel.add(usernameText);
        panel.add(username);
        panel.add(passwordText);
        panel.add(password);
        panel.add(telText);
        panel.add(tel);
        panel.add(sexText);
        panel.add(sexBox);
        panel.add(addressText);
        panel.add(address);
        panel.add(emailText);
        panel.add(email);
        panel.add(button);
    }
    //添加事件
    public void addAction(){
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            User newUser = new User();
            //顺序：id，用户名，密码，电话，性别，地址，邮箱
            newUser.setId(user.getId());
            newUser.setUsername(username.getText());
            newUser.setPassword(password.getText());
            newUser.setTel(tel.getText());
            newUser.setSex(Integer.valueOf(sexBox.getSelectedIndex()));
            newUser.setAddress(address.getText());
            newUser.setEmail(email.getText());
            update(frame,newUser);
            }
        });
    }
    public JPanel getPanel(){
        return panel;
    }

    public abstract void update(JFrame frame,User user);
}
