package org.lanqiao.view;

import org.lanqiao.controller.*;
import org.lanqiao.global.LayoutValue;
import org.lanqiao.model.User;
import org.lanqiao.util.GetDataUtils;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

public class AbstractMenuView {
    //初始化数据
    private JFrame frame = new JFrame();//窗口
    private JPanel panel = null;//容器
    private User user = null;

    AbstractDeskView abstractDeskView = new DeskController(frame);
    AbstractGreensClassView abstractGreensClassView = new GreensClassController(frame);
    AbstractGreensView abstractGreensView = new GreensController(frame);
    AbstractOrderView abstractOrderView = new OrderController(frame);

    JTabbedPane tabs = new JTabbedPane(JTabbedPane.TOP);
    JPanel card0 = new IndexController(frame).getPanel();
    JPanel card1 = abstractDeskView.getPanel();
    JPanel card2 = abstractGreensClassView.getPanel();
    JPanel card3 = abstractGreensView.getPanel();
    JPanel card4 = abstractOrderView.getPanel();
    JPanel card5 = null;

    //构造器
    public AbstractMenuView(User user) {
        this.user = user;
        componetInit(user.getUsername());
        init();
        addAction();
    }

    //设置元素大小等属性（每个按钮的大小，位置等）
    public void componetInit(String username){
        //frame设置
        frame.setSize(LayoutValue.SCREEN_WIDTH,LayoutValue.SCREEN_HEIGHT);//大小
        frame.setLocationRelativeTo(null);//居中
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//关闭
        frame.setTitle("点餐系统界面");
        frame.setIconImage(new ImageIcon("imgs/店铺图标.jpg").getImage());
        frame.setResizable(false);

        //panel设置（边框布局）
        panel = new JPanel(new BorderLayout()){
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                //添加背景图片
                ImageIcon img = new ImageIcon("登录背景1.jpg");
                g.drawImage(img.getImage(),0,0,null);
            }
        };

        card5 = new PersonalCenterController(frame,user).getPanel();
    }

    //设置布局（把元素放到对应的容器中）
    public void init(){
        tabs.add("    首页  ",card0);
        tabs.add("  餐桌管理  ",card1);
        tabs.add("  菜系管理  ",card2);
        tabs.add("  菜品管理  ",card3);
        tabs.add("  订单管理  ",card4);
        tabs.add("  个人中心  ",card5);

        panel.add(tabs);
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

    //添加事件
    public void addAction(){
        tabs.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                JTabbedPane tabbedPane = (JTabbedPane) e.getSource();
                int selectedIndex = tabbedPane.getSelectedIndex();
                /**
                 * 从0开始
                 * */
                if (selectedIndex == 1){
                    abstractDeskView.setData();
                }else if(selectedIndex == 2){
                    abstractGreensClassView.setData();
                }else if(selectedIndex == 3){
                    abstractGreensView.setData(null);
                    String[] greensClassData = GetDataUtils.getGreensClassNames();
                    abstractGreensView.setGreensClassBox(greensClassData);
                }else if(selectedIndex == 4){
                    abstractOrderView.setData(null);
                    String[] deskData = GetDataUtils.getDeskNames();
                    abstractOrderView.setDeskBox(deskData);
                }
            }
        });
    }
}
