package org.lanqiao.view;

import org.apache.commons.dbutils.QueryRunner;
import org.lanqiao.global.LayoutValue;
import org.lanqiao.util.DataSourceUtils;
import org.lanqiao.util.MD5Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AbstractRegisterView {
    private JFrame loginFrame = null;
    //初始化数据
    private JFrame frame = new JFrame();//窗口
    private JPanel panel = new Loginpanel("imgs/注册背景.jpg");
    private JButton button = new JButton();//按钮
    private JButton button1 = new JButton();
    JLabel registerusername = new JLabel("注册用户");//注册用户四个字
    JLabel username = new JLabel("请输入账号");
    JTextField usernamelable = new JTextField("输入格式为大小写字母");
    JLabel password = new JLabel("请输入密码");
    JTextField passwordlable = new JTextField("输入格式为字母数字");
    JLabel sexText = new JLabel("请选择性别");
    String[] sexData = LayoutValue.sexData;
    JComboBox<String> sexBox = new JComboBox<String>(sexData);
    JLabel email = new JLabel("请输入邮箱");
    JTextField emaillable = new JTextField("输入格式为正确邮箱");
    JLabel cellphone = new JLabel("请输入手机号");
    JTextField cellphonelable = new JTextField("输入格式为手机号");
    JLabel location = new JLabel("请输入地址");
    JTextField locationlable = new JTextField();
    private JLabel errlable = new JLabel();

    //构造器
    public AbstractRegisterView(JFrame frame) {
        loginFrame = frame;
        init();
        componetInit();
        frameHide();
        frameShow();
    }

    //设置元素大小等属性（每个按钮的大小，位置等）
    public void componetInit(){
        //frame设置
        frame.setSize(450,636);//大小
        frame.setLocationRelativeTo(null);//居中
        frame.setTitle("点餐系统注册界面");
        frame.setIconImage(new ImageIcon("12.jpg").getImage());//Title图片
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//关闭

        //panel设置
        panel.setLayout(null);//无布局
        //注册用户
        registerusername.setBounds(40,85,100,30);
        registerusername.setFont(new Font("华文楷体",Font.BOLD,24));
        //请输入账号
        username.setBounds(40,135,100,30);
//        username.setFont(new Font("华文楷体",Font.BOLD,17));

        usernamelable.setBounds(160,135,200,30);
        usernamelable.setBorder(null);
        //请输入密码
        password.setBounds(40,185,100,30);

        passwordlable.setBounds(160,185,200,30);
        passwordlable.setBorder(null);

        //请选择性别
        sexText.setBounds(40,235,100,30);
        sexBox.setBounds(160,235,200,30);
        sexBox.setBorder(null);

        //请输入邮箱
        email.setBounds(40,285,100,30);
        emaillable.setBounds(160,285,200,30);
        emaillable.setBorder(null);
        //请输入手机号
        cellphone.setBounds(30,335,150,30);
        cellphonelable.setBounds(160,335,200,30);
        cellphonelable.setBorder(null);

        //请输入地址
        location.setBounds(40,385,200,30);
        locationlable.setBounds(160,385,200,30);
        locationlable.setBorder(null);
        //按钮
        button.setText("提交");
        button.setLocation(45,445);
        button.setSize(320,35);
        button1.setText("返回登录");
        button1.setLocation(45,495);
        button1.setSize(320,35);

        //失去焦点判断是否输入正确
        usernamelable.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                //获得焦点执行的代码
                String str = usernamelable.getText();
                if (str.equals("输入格式为大小写字母")){
                    usernamelable.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                //失去焦点执行的代码
                String usernamelableText1 = usernamelable.getText();
                Pattern p1 = Pattern.compile("^.{1,20}$");
                Matcher m1 = p1.matcher(usernamelableText1);
                if (m1.matches()){
                    errlable.setText("");
                }else {
                    errlable.setText("用户名输入错误请重新输入！");
                }
            }
        });

        locationlable.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                //获得焦点执行的代码
                String str = usernamelable.getText();
                if (str.equals("请输入地址")){
                    usernamelable.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                //失去焦点执行的代码
                errlable.setText("");
            }
        });

        passwordlable.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                //获得焦点执行的代码
                String str = passwordlable.getText();
                if (str.equals("输入格式为字母数字")){
                    passwordlable.setText("");
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                //失去焦点执行的代码
                String passwordlableText1 = passwordlable.getText();
                Pattern p1 = Pattern.compile("(^[A-Za-z0-9]+$)");
                Matcher m1 = p1.matcher(passwordlableText1);
                if (m1.matches()){
                    errlable.setText("");
                }else {
                    errlable.setText("密码输入错误请重新输入！");
                }
            }
        });

        emaillable.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                //获得焦点执行的代码
                String str = emaillable.getText();
                if(str.equals("输入格式为正确邮箱")){
                    emaillable.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                //失去焦点执行的代码
                String emaillableText1 = emaillable.getText();
                Pattern p1 = Pattern.compile("(^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$)");
                Matcher m1 = p1.matcher(emaillableText1);
                if (m1.matches()){
                    errlable.setText("");
                }else {
                    errlable.setText("邮箱输入错误请重新输入！");
                }
            }
        });

        cellphonelable.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                //获得焦点执行的代码
                String str = cellphonelable.getText();
                if(str.equals("输入格式为手机号")){
                    cellphonelable.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                //失去焦点执行的代码
                String cellphonelableText1 = cellphonelable.getText();
                Pattern p1 = Pattern.compile("(^(13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9])\\d{8}$)");
                Matcher m1 = p1.matcher(cellphonelableText1);
                if (m1.matches()){
                    errlable.setText("");
                }else {
                    errlable.setText("手机号输入错误请重新输入！");
                }
            }
        });


        //注册成功事件
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            int sexText = Integer.valueOf(sexBox.getSelectedIndex());
            String addressText = locationlable.getText();

            String usernamelableText1 = usernamelable.getText();
            Pattern p1 = Pattern.compile("^.{1,20}$");
            Matcher m1 = p1.matcher(usernamelableText1);
            if (!m1.matches()) {
                JOptionPane.showMessageDialog(panel,"账号格式不对！","",JOptionPane.PLAIN_MESSAGE);
                return;
            }
            String passwordlableTest1 = passwordlable.getText();
            Pattern p2 = Pattern.compile("(^[A-Za-z0-9]+$)");
            Matcher m2 = p2.matcher(passwordlableTest1);
            if (!m2.matches()) {
                JOptionPane.showMessageDialog(panel,"密码格式不对！","",JOptionPane.PLAIN_MESSAGE);
                return;
            }

            String emaillableText1 = emaillable.getText();
            Pattern p4 = Pattern.compile("(^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$)");
            Matcher m4 = p4.matcher(emaillableText1);
            if (!m4.matches()) {
                JOptionPane.showMessageDialog(panel,"邮箱格式不对！","",JOptionPane.PLAIN_MESSAGE);
                return;
            }

            String cellphonelableText1 = cellphonelable.getText();
            Pattern p5 = Pattern.compile("(^(13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9])\\d{8}$)");
            Matcher m5 = p5.matcher(cellphonelableText1);
            if (!m5.matches()) {
                JOptionPane.showMessageDialog(panel,"手机号格式不对！","",JOptionPane.PLAIN_MESSAGE);
                return;
            }
            //注册代码
            QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
            String sql = "INSERT INTO `user` (username,password,tel,email,sex,address) VALUES (?,?,?,?,?,?)";
            int result = -1;
            try {
                result = qr.update(sql,usernamelableText1, MD5Utils.MD5(passwordlableTest1),cellphonelableText1,emaillableText1,sexText,addressText);
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            if(result == 1){
                JOptionPane.showMessageDialog(panel,"注册成功！","",JOptionPane.PLAIN_MESSAGE);
                loginFrame.setVisible(true);
                frameHide();
            }
            }
        });
        //返回登录事件
        button1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                frameHide();
                loginFrame.setVisible(true);
            }
        });

        //设置提示信息---------
//        errlable.setFont(new Font("华文楷体",Font.PLAIN,16));
        errlable.setBounds(160,100,190,22);
        errlable.setForeground(Color.red);
    }
    //设置布局（把元素放到对应的容器中）
    public void init(){
        //把控件添加到panel中
        panel.add(registerusername);
        panel.add(username);
        panel.add(password);
        panel.add(sexText);
        panel.add(sexBox);
        panel.add(email);
        panel.add(cellphone);
        panel.add(location);
        panel.add(usernamelable);
        panel.add(passwordlable);
        panel.add(emaillable);
        panel.add(cellphonelable);
        panel.add(locationlable);
        panel.add(button);
        panel.add(button1);
        panel.add(errlable);

        //把容器等发到frame中
        frame.add(panel);
        frame.setVisible(true);
    }
    //布局的隐藏方法
    public void  frameHide(){
        frame.setVisible(false);
    }
    //布局的显示方法
    public void frameShow(){
        frame.setVisible(true);
    }
    //背景图片
    static class Loginpanel extends JPanel{
        private Image image;
        public Loginpanel(String imgPath){
            this.image = new ImageIcon(imgPath).getImage();
        }

        @Override
        public void paintComponent(Graphics g) {
            g.drawImage(image,0,0,this);
        }
    }
}

