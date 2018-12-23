package org.lanqiao.view;

import org.lanqiao.controller.Add_UpdateGreensClassController;
import org.lanqiao.global.LayoutValue;
import org.lanqiao.model.GreensClass;
import org.lanqiao.common.EvenOddRenderer;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * 菜系类型
 * */
public abstract class AbstractGreensClassView {
    AbstractGreensClassView abstractGreensClassView = this;
    private JFrame frame = null;
    //初始化数据
    private JPanel panel = null;//容器
    private JPanel panel1 = new JPanel();//容器，按钮/搜索功能
    JButton button1 = new JButton("  新增  ");
    JButton button2 = new JButton("  修改  ");
    JButton button3 = new JButton("  删除  ");
    public DefaultTableModel defaultTableModel = new DefaultTableModel(){
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    JTable table = new JTable(defaultTableModel);
    //构造器
    public AbstractGreensClassView(JFrame frame) {
        this.frame = frame;
        componetInit();
        init();
        addAction();
        setData();
    }
    //设置元素大小等属性（每个按钮的大小，位置等）
    public void componetInit(){
        defaultTableModel.addColumn("菜系id");
        defaultTableModel.addColumn("菜系名称");
        panel1.setOpaque(false);
        panel = new JPanel(null){
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                //添加背景图片
                ImageIcon img = new ImageIcon("imgs/背景1.jpg");
                g.drawImage(img.getImage(),0,0,null);
            }
        };
        panel.setSize(LayoutValue.SCREEN_WIDTH,LayoutValue.SCREEN_HEIGHT);
        panel1.setSize(LayoutValue.SCREEN_WIDTH,LayoutValue.BNT_HEIGHT);
        panel1.setBackground(Color.red);
    }
    //设置布局（把元素放到对应的容器中）
    public void init(){
        panel.setLayout(new BorderLayout());
        panel1.setLayout(new FlowLayout(FlowLayout.LEFT));
        panel1.add(button1);
        panel1.add(button2);
        panel1.add(button3);
        //table属性定义
        table.setDragEnabled(false);
        JScrollPane scrollPane1 = new JScrollPane(table);

        //透明化设置，和选中变色
        table.setOpaque(false);
        table.setDefaultRenderer(Object.class, new EvenOddRenderer());

        scrollPane1.setOpaque(false);
        scrollPane1.getViewport().setOpaque(false);
        scrollPane1.setColumnHeaderView(table.getTableHeader());//设置头部（HeaderView部分）
        scrollPane1.getColumnHeader().setOpaque(false);//再取出头部，并设置为透明
        panel.add(panel1,BorderLayout.NORTH);
        panel.add(scrollPane1,BorderLayout.CENTER);
    }
    //添加事件
    public void addAction(){
        //新增
        button1.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent arg0) {
            //新开页面
            Add_UpdateGreensClassController addGreensClassController = new Add_UpdateGreensClassController(abstractGreensClassView,null);
            addGreensClassController.frameShow();
            frame.setVisible(false);
            }
        });
        //编辑
        button2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            // 新开页面
            int row = table.getSelectedRow();
            if(row == -1){
                JOptionPane.showMessageDialog(panel,"请选择你要修改的行！！！","",JOptionPane.PLAIN_MESSAGE);
            }else {
                int id = Integer.valueOf(String.valueOf(defaultTableModel.getValueAt(row, 0)));
                String name = String.valueOf(defaultTableModel.getValueAt(row, 1));
                GreensClass greensClass = new GreensClass();
                greensClass.setId(id);
                greensClass.setName(name);
                Add_UpdateGreensClassController updateGreensClassController = new Add_UpdateGreensClassController(abstractGreensClassView, greensClass);
                updateGreensClassController.frameShow();
                frame.setVisible(false);
            }
            }
        });
        //删除
        button3.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            int row = table.getSelectedRow();
            if(row == -1){
                //提示框
                JOptionPane.showMessageDialog(panel,"请选择你要删除的行！！！","",JOptionPane.PLAIN_MESSAGE);
            }else{
                //选择提示框
                int select = JOptionPane.showConfirmDialog(panel,"你确定要删除吗？","删除（确认对话框）",JOptionPane.YES_NO_CANCEL_OPTION);
                //yes0   no1    取消2
                if(select == 0){
                    int id = Integer.valueOf(String.valueOf(model.getValueAt(row, 0)));
                    delete(id);
                    setData();

                }
            }
            }
        });
    }
    public JPanel getPanel(){
        return panel;
    }
    //获取数据
    public void setData(){
        Object[][] objects = getGreensClasss();
        //清空原始数据
        while(defaultTableModel.getRowCount()>0){
            defaultTableModel.removeRow(defaultTableModel.getRowCount()-1);
        }
        //写入新数据
        for(int i = 0; i < objects.length; i++){
            defaultTableModel.addRow(objects[i]);
        }
    }
    public abstract Object[][] getGreensClasss();
    //删除数据
    public abstract void delete(int id);

    public JFrame getFrame(){
        return frame;
    }

    //布局的隐藏方法
    public void  frameHide(){
        frame.setVisible(false);
    }
    //布局的显示方法
    public void frameShow(){
        setData();
        frame.setVisible(true);
    }
}

