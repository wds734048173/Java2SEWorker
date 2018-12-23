package org.lanqiao.view;

import org.lanqiao.global.LayoutValue;
import org.lanqiao.model.Desk;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public abstract class AbstractAdd_UpdateDeskView {
    //初始化数据
    private AbstractDeskView abstractDeskView;
    private JFrame jframe = new JFrame();
    private JPanel panel = new JPanel();//容器

    JLabel titleText = new JLabel();

    JLabel deskIdText = new JLabel("餐桌id：");
    public JTextField deskId = new JTextField();

    JLabel deskNameText = new JLabel("餐桌名称：");
    public JTextField deskName = new JTextField();

    JLabel deskRemarkText = new JLabel("餐桌备注：");
    public JTextField deskRemark = new JTextField();

    JButton button1 = new JButton("新增");
    JButton button2 = new JButton("修改");
    JButton button3 = new JButton("取消");

    public Desk desk;//修改有数据，新增无数据

    //构造器
    public AbstractAdd_UpdateDeskView(AbstractDeskView abstractDeskView, Desk desk) {
        this.desk = desk;
        this.abstractDeskView = abstractDeskView;
        componetInit();
        init();
        addAction();
    }
    //设置元素大小等属性（每个按钮的大小，位置等）
    public void componetInit(){
        panel.setBackground(LayoutValue.BK_COLOR);
        jframe.setSize(LayoutValue.IFRAME_WIDTH,LayoutValue.IFRAME_HEIGHT);
        jframe.setLocationRelativeTo(null);
        jframe.setResizable(false);
        if(desk == null){
            jframe.setTitle("新增餐桌");
            titleText.setText("新增餐桌");
            deskId.setText("系统自生成");
            panel.add(button1);
        }else{
            jframe.setTitle("修改餐桌");
            titleText.setText("修改餐桌");
            panel.add(button2);
            deskId.setText(""+desk.getId());
            deskName.setText(desk.getName());
            deskRemark.setText(desk.getRemark());
        }
        panel.setLayout(null);
        deskId.setEditable(false);
        deskId.setBackground(Color.GRAY);
        titleText.setFont(new Font("华文楷体",Font.PLAIN,20));
        titleText.setBounds(50,50,LayoutValue.TEXT_WIDTH,LayoutValue.TEXT_HEIGHT);
        deskIdText.setBounds(50,100,LayoutValue.TEXT_WIDTH,LayoutValue.TEXT_HEIGHT);
        deskId.setBounds(150,100,LayoutValue.TEXT_WIDTH,LayoutValue.TEXT_HEIGHT);

        deskNameText.setBounds(50,150,LayoutValue.TEXT_WIDTH,LayoutValue.TEXT_HEIGHT);
        deskName.setBounds(150,150,LayoutValue.TEXT_WIDTH,LayoutValue.TEXT_HEIGHT);

        deskRemarkText.setBounds(50,200,LayoutValue.TEXT_WIDTH,LayoutValue.TEXT_HEIGHT);
        deskRemark.setBounds(150,200,LayoutValue.TEXT_WIDTH,LayoutValue.TEXT_HEIGHT);

        panel.setSize(LayoutValue.IFRAME_WIDTH,LayoutValue.IFRAME_HEIGHT);
        button1.setBounds(50,300,LayoutValue.BNT_WIDTH,LayoutValue.BNT_HEIGHT);
        button2.setBounds(50,300,LayoutValue.BNT_WIDTH,LayoutValue.BNT_HEIGHT);
        button3.setBounds(200,300,LayoutValue.BNT_WIDTH,LayoutValue.BNT_HEIGHT);
    }
    //设置布局（把元素放到对应的容器中）
    public void init(){
        panel.add(titleText);
        panel.add(deskId);
        panel.add(deskIdText);
        panel.add(deskName);
        panel.add(deskNameText);
        panel.add(deskRemark);
        panel.add(deskRemarkText);
        panel.add(button3);
        jframe.add(panel);
    }
    //添加事件
    public void addAction(){
        //新增
        button1.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent arg0) {
            String name = String.valueOf(deskName.getText());
            if("".equals(name)){
                JOptionPane.showMessageDialog(panel,"请输入餐桌名称！！！","",JOptionPane.PLAIN_MESSAGE);
                return;
            }
            int result = add();
            if(result == 1){
                JOptionPane.showMessageDialog(panel,"添加成功！！！","",JOptionPane.PLAIN_MESSAGE);
                frameHide();
                abstractDeskView.setData();
                abstractDeskView.table.repaint();
                abstractDeskView.frameShow();
            }else{
                JOptionPane.showMessageDialog(panel,"添加失败，请重新添加！！！","",JOptionPane.PLAIN_MESSAGE);
                return;
            }
            }
        });
        //修改
        button2.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent arg0) {
            String name = String.valueOf(deskName.getText());
            if("".equals(name)){
                JOptionPane.showMessageDialog(panel,"请输入餐桌名称！！！","",JOptionPane.PLAIN_MESSAGE);
                return;
            }
            int result = update();
            if(result == 1){
                JOptionPane.showMessageDialog(panel,"修改成功！！！","",JOptionPane.PLAIN_MESSAGE);
                frameHide();
                abstractDeskView.setData();
                abstractDeskView.table.repaint();
                abstractDeskView.frameShow();
            }else{
                JOptionPane.showMessageDialog(panel,"修改失败，请重新修改！！！","",JOptionPane.PLAIN_MESSAGE);
                return;
            }
            }
        });
        //取消
        button3.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            frameHide();
            abstractDeskView.getFrame().setVisible(true);
            }
        });
    }
    //新增
    public abstract int add();
    //修改
    public abstract int update();

    //布局的隐藏方法
    public void  frameHide(){
        jframe.setVisible(false);
    }
    //布局的显示方法
    public void frameShow(){
        jframe.setVisible(true);
    }
}
