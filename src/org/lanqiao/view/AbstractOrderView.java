package org.lanqiao.view;

import org.lanqiao.controller.Add_UpdateOrderController;
import org.lanqiao.global.LayoutValue;
import org.lanqiao.model.Order;
import org.lanqiao.model.OrderItem;
import org.lanqiao.common.EvenOddRenderer;
import org.lanqiao.util.GetDataUtils;
import org.lanqiao.util.MapUtils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 订单
 * */
public abstract class AbstractOrderView {
    AbstractOrderView abstractOrderView = this;
    private JFrame frame = null;
    //初始化数据
    private JPanel panel = null;//容器
    private JPanel panel1 = new JPanel();//容器，按钮/搜索功能
    JButton button1 = new JButton("  新增  ");
    JButton button2 = new JButton("  修改  ");
    JButton button3 = new JButton("  删除  ");
    JButton button4 = new JButton("  结账  ");
    JComboBox deskBox = null;//餐桌的下拉列表
    public JComboBox orderStateBox = null;//订单状态的下拉列表
    public String[] deskData = GetDataUtils.getDeskNames();
    JButton button5 = new JButton("  查询  ");
    //禁止双击编辑
    public DefaultTableModel defaultTableModel = new DefaultTableModel() {
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    JTable table = new JTable(defaultTableModel);
    //构造器
    public AbstractOrderView(JFrame frame) {
        this.frame = frame;
        componetInit();
        init();
        addAction();
        setData(null);
    }
    //设置元素大小等属性（每个按钮的大小，位置等）
    public void componetInit(){
        defaultTableModel.addColumn("订单id");
        defaultTableModel.addColumn("餐桌名称");
        defaultTableModel.addColumn("价格");
        defaultTableModel.addColumn("是否结账");
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

        deskBox = new JComboBox(deskData);
        String[] orderStateData = {"全部","未支付","已支付"};
        orderStateBox = new JComboBox(orderStateData);
    }
    //设置布局（把元素放到对应的容器中）
    public void init(){
        panel.setLayout(new BorderLayout());
        panel1.setLayout(new FlowLayout(FlowLayout.LEFT));
        panel1.add(button1);
        panel1.add(button2);
        panel1.add(button3);
        panel1.add(deskBox);
        panel1.add(orderStateBox);
        panel1.add(button4);
        panel1.add(button5);
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
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            if(e.getClickCount() == 2){
                int index = table.getSelectedRow();//第几行数据
                int id = Integer.valueOf(String.valueOf(defaultTableModel.getValueAt(index,0)));

                //显示订单详情
                JFrame frame = new JFrame();
                frame.setSize(500,500);
                frame.setLocationRelativeTo(null);
                frame.setResizable(false);
                JPanel panel = new JPanel();//容器
                String[] classheads = {"订单id","菜名","单价","数量"};
                Object[][] classdata = getOrderItemsByOrderId(id);
                DefaultTableModel defaultTableModel = new DefaultTableModel(classdata,classheads);
                JTable table1 = new JTable(defaultTableModel);
                JScrollPane scrollPane1 = new JScrollPane(table1);
                panel.add(scrollPane1);
                frame.add(panel);
                frame.setVisible(true);
            }
            }
        });
        //新增
        button1.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent arg0) {
            //新开页面
            Add_UpdateOrderController addOrderController = new Add_UpdateOrderController(abstractOrderView,null,null);
            addOrderController.frameShow();
            frame.setVisible(false);
            }
        });
        //编辑
        button2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            //已结账的订单不可修改，已结账的餐桌可再次使用
            int row = table.getSelectedRow();
            if (row == -1) {
                //提示框
                JOptionPane.showMessageDialog(panel, "请选择你要修改的行！！！", "", JOptionPane.PLAIN_MESSAGE);
            } else {
                String orderStateStr = String.valueOf(defaultTableModel.getValueAt(row, 3));
                if (orderStateStr.equals("已支付")) {
                    JOptionPane.showMessageDialog(panel, "已结账的订单不可修改！", "", JOptionPane.PLAIN_MESSAGE);
                    return;
                } else {
                    Order order = new Order();
                    int orderId = Integer.valueOf(table.getValueAt(row, 0).toString());
                    int deskId = -1;
                    String deskName = String.valueOf(table.getValueAt(row, 1));
                    Map<String, Integer> map = MapUtils.getDeskNameIdMap();
                    if (map.containsKey(deskName)) {
                        deskId = map.get(deskName);
                    }
                    order.setId(orderId);
                    order.setDeskId(deskId);
                    //获取订单详情
                    Object[][] classdata = getOrderItemsByOrderId(orderId);
                    List<OrderItem> orderItems = new ArrayList<>();
                    for (int i = 0; i < classdata.length; i++) {
                        OrderItem orderItem = new OrderItem();
                        String orderItemId = classdata[i][0].toString();
                        String greensName = classdata[i][1].toString();
                        String price = classdata[i][2].toString();
                        String number = classdata[i][3].toString();
                        String greensId = classdata[i][4].toString();
                        orderItem.setGreensName(greensName);
                        orderItem.setGreensPrice(Integer.valueOf(price));
                        orderItem.setNumber(Integer.valueOf(number));
                        orderItem.setGreensId(Integer.valueOf(greensId));
                        orderItem.setId(Integer.valueOf(orderItemId));
                        orderItems.add(orderItem);
                    }
                    Add_UpdateOrderController updateOrderController = new Add_UpdateOrderController(abstractOrderView, order, orderItems);
                    updateOrderController.frameShow();
                    frame.setVisible(false);
                }
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
                    String orderStateStr = String.valueOf(defaultTableModel.getValueAt(row, 3));
                    if(orderStateStr.equals("已支付")){
                        JOptionPane.showMessageDialog(panel,"已结账的订单不可删除！","",JOptionPane.PLAIN_MESSAGE);
                        return;
                    }else{
                        int orderId = Integer.valueOf(String.valueOf(defaultTableModel.getValueAt(row, 0)));
                        int deskId = -1;
                        //以后封装，通过名字查id
                        String deskName = String.valueOf(defaultTableModel.getValueAt(row, 1));
                        Map<String,Integer> map = MapUtils.getDeskNameIdMap();
                        if(map.containsKey(deskName)){
                            deskId = map.get(deskName);
                        }
                        if(delete(orderId,deskId) == 1){
                            JOptionPane.showMessageDialog(panel,"删除成功！","",JOptionPane.PLAIN_MESSAGE);
                            setData(null);
                        }else{
                            JOptionPane.showMessageDialog(panel,"删除失败！","",JOptionPane.PLAIN_MESSAGE);
                        }
                    }
                }
            }
            }
        });
        //结账
        button4.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            int row = table.getSelectedRow();
            if(row == -1){
                //提示框
                JOptionPane.showMessageDialog(panel,"请选择你要操作的行！！！","",JOptionPane.PLAIN_MESSAGE);
            }else{
                //选择提示框
                int select = JOptionPane.showConfirmDialog(panel,"你确定要结账吗？","结账（确认对话框）",JOptionPane.YES_NO_CANCEL_OPTION);
                //yes0   no1    取消2
                if(select == 0){
                    String orderStateStr = String.valueOf(defaultTableModel.getValueAt(row, 3));
                    if(orderStateStr.equals("已支付")){
                        JOptionPane.showMessageDialog(panel,"已结账的订单不可重复结账！","",JOptionPane.PLAIN_MESSAGE);
                        return;
                    }else{
                        int orderId = Integer.valueOf(String.valueOf(defaultTableModel.getValueAt(row, 0)));
                        int deskId = -1;
                        //以后封装，通过名字查id
                        String deskName = String.valueOf(defaultTableModel.getValueAt(row, 1));
                        Map<String,Integer> map = MapUtils.getDeskNameIdMap();
                        if(map.containsKey(deskName)){
                            deskId = map.get(deskName);
                        }
                        if(updateState(orderId,deskId) == 1){
                            JOptionPane.showMessageDialog(panel,"结账成功！","",JOptionPane.PLAIN_MESSAGE);
                            setData(null);
                        }else{
                            JOptionPane.showMessageDialog(panel,"结账失败！","",JOptionPane.PLAIN_MESSAGE);
                        }
                    }
                }
            }
            }
        });
        //查询
        button5.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            //获取数据，并调用方法
            //餐桌
            String desk = deskBox.getSelectedItem().toString();
            int deskId = -1;
            int orderStateId = -1;
            if(!desk.equals("全部")){
                Map<String,Integer> map = MapUtils.getDeskNameIdMap();
                if(map.containsKey(desk)){
                    deskId = map.get(desk);
                }
            }
            //订单状态
            String orderState = orderStateBox.getSelectedItem().toString();
            if(!orderState.equals("全部")){
                Map<String,Integer> map = MapUtils.getIsPayNameIdMap();
                if(map.containsKey(orderState)){
                    orderStateId = map.get(orderState);
                }
            }
            Object object = deskId + "_" + orderStateId;
            setData(object);
            }
        });
    }
    public JPanel getPanel(){
        return panel;
    }
    //获取数据
    public void setData(Object o){
        Object[][] objects = getOrders(o);
        //清空原始数据
        while(defaultTableModel.getRowCount()>0){
            defaultTableModel.removeRow(defaultTableModel.getRowCount()-1);
        }
        //写入新数据
        for(int i = 0; i < objects.length; i++){
            defaultTableModel.addRow(objects[i]);
        }
    }
    public abstract Object[][] getOrders(Object o);
    //通过订单id获取订单详情信息
    public abstract Object[][] getOrderItemsByOrderId(int id);

    //删除
    public abstract int delete(int orderId,int deskId);

    //修改订单支付状态
    public abstract int updateState(int orderId,int deskId);

    public JFrame getFrame(){
        return frame;
    }

    //布局的隐藏方法
    public void  frameHide(){
        frame.setVisible(false);
    }
    //布局的显示方法
    public void frameShow(){
        frame.setVisible(true);
    }

    public void setDeskBox(String[] deskData){
        if(deskBox == null){
            deskBox = new JComboBox(deskData);
        }else{
            deskBox.removeAllItems();
            for(int i = 0; i < deskData.length; i++){
                deskBox.addItem(deskData[i]);
            }
        }
    }
}