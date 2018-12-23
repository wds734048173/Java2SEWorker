package org.lanqiao.view;

import javax.swing.*;
import java.awt.*;

public abstract class AbstractIndexView {
    private JFrame frame = null;
    //初始化数据
    private JPanel panel = null;//容器
    JLabel label = new JLabel();

    //构造器
    public AbstractIndexView(JFrame frame) {
        this.frame = frame;
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

        label.setText("XXX点餐系统");

        label.setFont(new Font("华文楷体",1,50));
        label.setForeground(Color.red);
        label.setLocation(500,150);
        label.setSize(1000,200);
        label.setVisible(true);
    }
    //设置布局（把元素放到对应的容器中）
    public void init(){
        panel.add(label);
    }
    //添加事件
    public void addAction(){

    }
    public JPanel getPanel(){
        return panel;
    }
}
