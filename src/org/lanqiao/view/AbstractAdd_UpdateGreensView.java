package org.lanqiao.view;

import org.lanqiao.global.LayoutValue;
import org.lanqiao.model.Greens;
import org.lanqiao.util.GetDataUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public abstract class AbstractAdd_UpdateGreensView {
    //初始化数据
    private JFrame jframe = new JFrame();
    private JPanel panel = new JPanel();//容器

    JLabel titleText = new JLabel();

    JLabel greensIdText = new JLabel("菜 名 id：");
    public JTextField greensId = new JTextField();

    JLabel greensNameText = new JLabel("菜名名称：");
    public JTextField greensName = new JTextField();

    //做成下拉：菜系
    JLabel greensClassIdText = new JLabel("菜  系：");
    String[] greensClassData = GetDataUtils.getGreensClassNames();
    public JComboBox<String> greensClassBox = new JComboBox<String>(greensClassData);

    JLabel priceText = new JLabel("菜品价格：");
    public JTextField price = new JTextField();

    JButton button1 = new JButton("新增");
    JButton button2 = new JButton("修改");
    JButton button3 = new JButton("取消");

    public Greens greens;//修改有数据，新增无数据
    public String greensClassName;
    private AbstractGreensView abstractGreensView;

    //构造器
    public AbstractAdd_UpdateGreensView(AbstractGreensView abstractGreensView, Greens greens,String greensClassName) {
        this.greens = greens;
        this.abstractGreensView = abstractGreensView;
        this.greensClassName = greensClassName;
        componetInit();
        init();
        addAction();
    }
    //设置元素大小等属性（每个按钮的大小，位置等）
    public void componetInit(){
        panel.setBackground(LayoutValue.BK_COLOR);
        jframe.setSize(LayoutValue.IFRAME_WIDTH,LayoutValue.IFRAME_HEIGHT);
        jframe.setLocationRelativeTo(null);
        if(greens == null){
            jframe.setTitle("新增菜名");
            titleText.setText("新增菜名");
            greensId.setText("系统自生成");
            panel.add(button1);
        }else{
            jframe.setTitle("修改菜名");
            titleText.setText("修改菜名");
            panel.add(button2);
            greensId.setText(""+greens.getId());
            greensName.setText(greens.getName());
            price.setText(""+greens.getPrice());
            greensClassBox.setSelectedItem(greensClassName);
        }

        panel.setLayout(null);
        greensId.setEditable(false);
        greensId.setBackground(Color.GRAY);
        greensIdText.setBounds(50,50,LayoutValue.TEXT_WIDTH,LayoutValue.TEXT_HEIGHT);
        greensId.setBounds(120,50,LayoutValue.TEXT_WIDTH,LayoutValue.TEXT_HEIGHT);

        greensNameText.setBounds(50,100,LayoutValue.TEXT_WIDTH,LayoutValue.TEXT_HEIGHT);
        greensName.setBounds(120,100,LayoutValue.TEXT_WIDTH,LayoutValue.TEXT_HEIGHT);

        greensClassIdText.setBounds(50,150,LayoutValue.TEXT_WIDTH,LayoutValue.TEXT_HEIGHT);
        greensClassBox.setBounds(120,150,LayoutValue.TEXT_WIDTH,LayoutValue.TEXT_HEIGHT);

        priceText.setBounds(50,200,LayoutValue.TEXT_WIDTH,LayoutValue.TEXT_HEIGHT);
        price.setBounds(120,200,LayoutValue.TEXT_WIDTH,LayoutValue.TEXT_HEIGHT);

        panel.setSize(LayoutValue.IFRAME_WIDTH,LayoutValue.IFRAME_HEIGHT);
        button1.setBounds(100,250,LayoutValue.BNT_WIDTH,LayoutValue.BNT_HEIGHT);
        button2.setBounds(100,250,LayoutValue.BNT_WIDTH,LayoutValue.BNT_HEIGHT);
        button3.setBounds(250,250,LayoutValue.BNT_WIDTH,LayoutValue.BNT_HEIGHT);
    }
    //设置布局（把元素放到对应的容器中）
    public void init(){
        panel.add(greensIdText);
        panel.add(greensId);
        panel.add(greensNameText);
        panel.add(greensName);
        panel.add(greensClassIdText);
        panel.add(greensClassBox);
        panel.add(priceText);
        panel.add(price);
        panel.add(button3);
        jframe.add(panel);
    }
    //添加事件
    public void addAction(){
        //保存
        button1.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent arg0) {
            //数据验证
            String name = String.valueOf(greensName.getText());
            String greensPrice = String.valueOf(price.getText());
            int greensClassId = Integer.valueOf(greensClassBox.getSelectedIndex());
            if(name.equals("")){
                JOptionPane.showMessageDialog(panel,"请输入菜品名称！！！","",JOptionPane.PLAIN_MESSAGE);
               return;
            }
            if(greensPrice.equals("")){
                JOptionPane.showMessageDialog(panel,"请输入菜品价格！！！","",JOptionPane.PLAIN_MESSAGE);
                return;
            }
            if(greensClassId == 0){
                JOptionPane.showMessageDialog(panel,"请选择菜系！！！","",JOptionPane.PLAIN_MESSAGE);
                return;
            }
            int result = add();
            if(result == 1){
                JOptionPane.showMessageDialog(panel,"添加成功！！！","",JOptionPane.PLAIN_MESSAGE);
                frameHide();
                abstractGreensView.setData(null);
                abstractGreensView.table.repaint();
                abstractGreensView.frameShow();
            }else{
                JOptionPane.showMessageDialog(panel,"添加失败，请重新添加！！！","",JOptionPane.PLAIN_MESSAGE);
                return;
            }
            }
        });
        //修改
        button2.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent arg0) {
                String name = String.valueOf(greensName.getText());
                String greensPrice = String.valueOf(price.getText());
                int greensClassId = Integer.valueOf(greensClassBox.getSelectedIndex());
                if(name.equals("")){
                    JOptionPane.showMessageDialog(panel,"请输入菜品名称！！！","",JOptionPane.PLAIN_MESSAGE);
                    return;
                }
                if(greensPrice.equals("")){
                    JOptionPane.showMessageDialog(panel,"请输入菜品价格！！！","",JOptionPane.PLAIN_MESSAGE);
                    return;
                }
                if(greensClassId == 0){
                    JOptionPane.showMessageDialog(panel,"请选择菜系！！！","",JOptionPane.PLAIN_MESSAGE);
                    return;
                }
                int result = update();
                if(result == 1){
                    JOptionPane.showMessageDialog(panel,"修改成功！！！","",JOptionPane.PLAIN_MESSAGE);
                    frameHide();
                    abstractGreensView.setData(null);
                    abstractGreensView.table.repaint();
                    abstractGreensView.frameShow();
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
                abstractGreensView.getFrame().setVisible(true);
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

    public abstract void getData();
}
