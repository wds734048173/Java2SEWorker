package org.lanqiao.view;

import org.lanqiao.controller.Add_UpdateDeskController;
import org.lanqiao.global.LayoutValue;
import org.lanqiao.model.Desk;
import org.lanqiao.common.EvenOddRenderer;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * 桌号
 * */
public abstract class AbstractDeskView {
    private JFrame frame = null;
    AbstractDeskView  abstractDeskView = this;
    //初始化数据
    private JPanel panel = null;//容器
    private JPanel panel1 = new JPanel();//容器，按钮/搜索功能
    private JPanel panel2 = new JPanel(new FlowLayout(FlowLayout.RIGHT));//分页
    JButton button1 = new JButton("  新增  ");
    JButton button2 = new JButton("  修改  ");
    JButton button3 = new JButton("  删除  ");
    JButton button4 = new JButton("上一页");//首页、尾页
    JComboBox pageBox = new JComboBox(new Integer[]{5,10,50});//每页显示多少数据

    JButton button5 = new JButton("下一页");
    JLabel textField = new JLabel();//第几页
    //多少条数据
    //多少页
    public DefaultTableModel defaultTableModel = new DefaultTableModel(){
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    JTable table = new JTable(defaultTableModel);
    //构造器
    public AbstractDeskView(JFrame frame) {
        this.frame = frame;
        componetInit();
        init();
        addAction();
        setData();
    }
    //设置元素大小等属性（每个按钮的大小，位置等）
    public void componetInit(){
        defaultTableModel.addColumn("餐桌id");
        defaultTableModel.addColumn("餐桌名称");
        defaultTableModel.addColumn("餐桌状态");
        defaultTableModel.addColumn("备注");

        panel1.setOpaque(false);
        panel2.setOpaque(false);

        //透明化设置，和选中变色
        table.setOpaque(false);
        table.setDefaultRenderer(Object.class, new EvenOddRenderer());

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
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        table.setSize(LayoutValue.SCREEN_WIDTH,LayoutValue.SCREEN_HEIGHT);
        JScrollPane scrollPane1 = new JScrollPane(table);

        scrollPane1.setOpaque(false);
        scrollPane1.getViewport().setOpaque(false);
        scrollPane1.setColumnHeaderView(table.getTableHeader());//设置头部（HeaderView部分）
        scrollPane1.getColumnHeader().setOpaque(false);//再取出头部，并设置为透明

        panel2.add(pageBox);
        panel2.add(button4);
        panel2.add(textField);
        panel2.add(button5);

        panel.add(panel1,BorderLayout.NORTH);
        panel.add(scrollPane1,BorderLayout.CENTER);
        panel.add(panel2,BorderLayout.SOUTH);
    }
    //添加事件
    public void addAction(){
        //新增
        button1.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent arg0) {
                Add_UpdateDeskController addDeskController = new Add_UpdateDeskController(abstractDeskView,null);
                addDeskController.frameShow();
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
                //提示框
                JOptionPane.showMessageDialog(panel,"请选择你要修改的行！！！","",JOptionPane.PLAIN_MESSAGE);
            }else {
                int id = Integer.valueOf(String.valueOf(defaultTableModel.getValueAt(row, 0)));
                String name = String.valueOf(defaultTableModel.getValueAt(row, 1));
                String remark = String.valueOf(defaultTableModel.getValueAt(row, 3));
                Desk desk = new Desk();
                desk.setId(id);
                desk.setName(name);
                desk.setRemark(remark);
                Add_UpdateDeskController updateDeskController = new Add_UpdateDeskController(abstractDeskView, desk);
                updateDeskController.frameShow();
                frame.setVisible(false);
            }
            }
        });
        //删除
        button3.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            int row = table.getSelectedRow();
            if(row == -1){
                //提示框
                JOptionPane.showMessageDialog(panel,"请选择你要删除的行！！！","",JOptionPane.PLAIN_MESSAGE);
            }else{
                //选择提示框
                int select = JOptionPane.showConfirmDialog(panel,"你确定要删除吗？","删除（确认对话框）",JOptionPane.YES_NO_CANCEL_OPTION);
                //yes0   no1    取消2
                if(select == 0){
                    int id = Integer.valueOf(String.valueOf(defaultTableModel.getValueAt(row, 0)));
                    if(delete(id) == 1){
                        JOptionPane.showMessageDialog(panel,"删除成功！","",JOptionPane.PLAIN_MESSAGE);
                        setData();
                    }else{
                        JOptionPane.showMessageDialog(panel,"删除失败！","",JOptionPane.PLAIN_MESSAGE);
                    }
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
        Object[][] objects = getDesks();
        //清空原始数据
        //方法一
//        while(defaultTableModel.getRowCount()>0){
//            defaultTableModel.removeRow(defaultTableModel.getRowCount()-1);
//        }
        //方法二
        defaultTableModel.getDataVector().clear();
        //写入新数据
        for(int i = 0; i < objects.length; i++){
            defaultTableModel.addRow(objects[i]);
        }
        int pageIndex = 1;
        int pageTotal = 5;
        textField.setText(pageIndex + "/" + pageTotal + "页");
    }

    //添加数据
    public abstract void addData();

    //修改数据
    public abstract void updateData(int id, String[] data);

    //删除数据
    public abstract int delete(int id);

    public abstract Object[][] getDesks();

    public JFrame getFrame(){
        return frame;
    }

    //布局的隐藏方法
    public void  frameHide(){
        frame.setVisible(false);
    }
    //布局的显示方法
    public void frameShow(){
        table.setModel(defaultTableModel);
        frame.setVisible(true);

    }
}
