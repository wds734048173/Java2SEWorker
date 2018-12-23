package org.lanqiao.view;

import org.lanqiao.global.LayoutValue;
import org.lanqiao.util.SwingUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public abstract class AbstractLoginView {
    //初始化数据
    private JFrame frame = new JFrame();//窗口
    private JPanel panel = null;//容器
    private JButton button1 = new JButton();//登陆
    private JButton button2 = new JButton();//重置
    private JButton button3 = new JButton();//注册
    private JTextField username = new JTextField("");//用户名
    private JPasswordField password = new JPasswordField();//密码
    private JLabel errlable = new JLabel();

    //构造器
    public AbstractLoginView() {
        componetInit();
        init();
        addAction();
    }
    //设置元素大小等属性（每个按钮的大小，位置等）
    public void componetInit(){
        //frame设置
        frame.setSize(1045,560);//大小
        frame.setLocationRelativeTo(null);//居中
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//关闭
        frame.setTitle("点餐系统登陆界面");
        frame.setIconImage(new ImageIcon("imgs/店铺图标.jpg").getImage());
        frame.setResizable(false);

        //panel设置
        panel = new JPanel(null){
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                //添加背景图片
                ImageIcon img = new ImageIcon("imgs/登录背景.jpg");
                g.drawImage(img.getImage(),0,0,null);
            }
        };

        //用户名设置
        username.setSize(LayoutValue.TEXT_WIDTH,LayoutValue.TEXT_HEIGHT);
        username.setLocation(425,206);
        username.setBorder(null);
        username.setBackground(new Color(255,251,240));

        //密码设置
        password.setSize(LayoutValue.TEXT_WIDTH,LayoutValue.TEXT_HEIGHT);
        password.setLocation(425,259);
        password.setBorder(null);
        password.setBackground(new Color(255,251,240));
//        password.setEchoChar((char)0);//设置密码可见

        //提示错误框设置
        errlable.setSize(150,20);//大小
        errlable.setLocation(425,300);//定位
        errlable.setForeground(Color.red);//字体颜色

        //登陆按钮设置
        button1.setText("登陆");
        button1.setBounds(576,338,58,28);
        button1.setFont(new Font("华文楷体",1,10));

        button2.setText("重置");
        button2.setBounds(655,338,58,28);
        button2.setFont(new Font("华文楷体",1,10));

        button3.setText("注册");
        button3.setBounds(734,338,58,28);
        button3.setFont(new Font("华文楷体",1,10));
    }
    //设置布局（把元素放到对应的容器中）
    public void init(){
        //把控件添加到panel中
        panel.add(username);
        panel.add(password);
        panel.add(username);
        panel.add(password);
        panel.add(errlable);

        //把容器等发到frame中
        frame.add(button1);
        frame.add(button2);
        frame.add(button3);
        frame.add(panel);
    }
    //布局的隐藏方法
    public void  frameHide(){
        frame.setVisible(false);
    }
    //布局的显示方法
    public void frameShow(){
        frame.setVisible(true);
    }

    //添加事件
    public void addAction(){
        button1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            String usernameText = username.getText();
            String passwordText = new String(password.getPassword());
            login(usernameText,passwordText);
            }
        });
        button2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            username.setText(null);
            password.setText(null);
            }
        });
        button3.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            new AbstractRegisterView(frame).frameShow();
            frameHide();
            }
        });
        SwingUtils.enterPressesWhenFocused(password, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String usernameText = username.getText();
                String passwordText = new String(password.getPassword());
                login(usernameText,passwordText);
            }
        });
    }
    //给错误提示赋值
    public void setErrlable(String str){
        errlable.setText(str);
    }
    //登陆功能
    public  abstract  void login(String username,String password);
}
