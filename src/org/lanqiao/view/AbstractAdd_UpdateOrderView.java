package org.lanqiao.view;

import org.lanqiao.global.LayoutValue;
import org.lanqiao.model.Greens;
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
import java.util.Map;
import java.util.List;

public abstract class AbstractAdd_UpdateOrderView {
    //初始化数据
    private AbstractOrderView abstractOrderView;
    private JFrame jframe = new JFrame();
    private JPanel panel = new JPanel(new BorderLayout());//容器
    //上部分
    private JPanel panel1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
    String[] greensData = GetDataUtils.getGreensNames();
    public JComboBox<String> greensBox = new JComboBox<String>(greensData);//菜品的下拉列表
    JButton button1 = new JButton("  新增  ");
    JButton button2 = new JButton("  删除  ");
    String[] deskData = GetDataUtils.getDeskNames();
    public JComboBox<String> deskBox = new JComboBox<String>(deskData);//餐桌的下拉列表
    //中部分
    public DefaultTableModel defaultTableModel = new DefaultTableModel();
    JTable table = new JTable(defaultTableModel);


    //下部分
    private JPanel panel2 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    JButton button3 = new JButton("保存");
    JButton button4 = new JButton("保存");
    JButton button5 = new JButton("取消");

    public Order order;//修改有数据，新增无数据
    public List<OrderItem> orderItems;

    //构造器
    public AbstractAdd_UpdateOrderView(AbstractOrderView abstractOrderView,Order order,List<OrderItem> orderItems) {
        this.abstractOrderView = abstractOrderView;
        this.order = order;
        this.orderItems = orderItems;
        componetInit();
        init();
        addAction();
    }
    //设置元素大小等属性（每个按钮的大小，位置等）
    public void componetInit(){
        defaultTableModel.addColumn("菜品id");
        defaultTableModel.addColumn("菜系名称");
        defaultTableModel.addColumn("菜品价格");
        defaultTableModel.addColumn("数量");
        panel.setBackground(LayoutValue.BK_COLOR);
        jframe.setSize(LayoutValue.IFRAME_WIDTH,LayoutValue.IFRAME_HEIGHT);
        jframe.setLocationRelativeTo(null);
        jframe.setResizable(false);
        if(order == null){
            jframe.setTitle("新增订单");
            panel2.add(button3);
        }else{
            jframe.setTitle("修改订单");
            int deskId = order.getDeskId();
            String deskName = "";
            Map<Integer,String> map = MapUtils.getDeskIdNameMap();
            if(map.containsKey(deskId)){
                deskName = map.get(deskId);
            }
            deskBox.setSelectedItem(deskName);
            for(int i = 0; i < orderItems.size(); i++){
                Object[] objects = {orderItems.get(i).getGreensId(),orderItems.get(i).getGreensName(),orderItems.get(i).getGreensPrice(),orderItems.get(i).getNumber()};
                defaultTableModel.addRow(objects);
            }
            panel2.add(button4);
        }

        panel.setSize(LayoutValue.IFRAME_WIDTH,LayoutValue.IFRAME_HEIGHT);
        button1.setSize(LayoutValue.BNT_WIDTH,LayoutValue.BNT_HEIGHT);
        button2.setSize(LayoutValue.BNT_WIDTH,LayoutValue.BNT_HEIGHT);
        button3.setSize(LayoutValue.BNT_WIDTH,LayoutValue.BNT_HEIGHT);
        button4.setSize(LayoutValue.BNT_WIDTH,LayoutValue.BNT_HEIGHT);
        button5.setSize(LayoutValue.BNT_WIDTH,LayoutValue.BNT_HEIGHT);
    }
    //设置布局（把元素放到对应的容器中）
    public void init(){
        panel1.add(greensBox);
        panel1.add(button1);
        panel1.add(button2);
        panel1.add(deskBox);
        panel2.add(button5);


        JScrollPane scrollPane1 = new JScrollPane(table);

        //透明化设置，和选中变色
        table.setDefaultRenderer(Object.class, new EvenOddRenderer());

        table.setOpaque(false);
        scrollPane1.setOpaque(false);
        scrollPane1.getViewport().setOpaque(false);
        scrollPane1.setColumnHeaderView(table.getTableHeader());//设置头部（HeaderView部分）
        scrollPane1.getColumnHeader().setOpaque(false);//再取出头部，并设置为透明
        panel.add(panel1,BorderLayout.NORTH);
        panel.add(scrollPane1,BorderLayout.CENTER);
        panel.add(panel2,BorderLayout.SOUTH);
        jframe.add(panel);
    }
    //添加事件
    public void addAction(){
        //新增
        button1.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent arg0) {
            String greensName = greensBox.getSelectedItem().toString();
            Greens greens = null;
            if("全部".equals(greensName)){
                JOptionPane.showMessageDialog(panel,"请选择你要添加的商品！！！","",JOptionPane.PLAIN_MESSAGE);
                return;
            }else{
                Map<String, Greens> map = MapUtils.getGreensNameIdMap();
                if(map.containsKey(greensName)){
                    greens = map.get(greensName);
                }
            }
            Boolean mark = false;
            for(int i = 0; i < defaultTableModel.getRowCount(); i++){
                if(defaultTableModel.getValueAt(i,1).toString().equals(greensName)){
                    mark = true;
                }
            }
            if(mark){
                JOptionPane.showMessageDialog(panel,"该商品已经存在，不可重复添加！","",JOptionPane.PLAIN_MESSAGE);
                return;
            }
            Object[] row = {greens.getId(),greens.getName(),greens.getPrice(),1};
            defaultTableModel.addRow(row);
            }
        });
        //删除
        button2.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent arg0) {
            int row = table.getSelectedRow();
            if(row == -1){
                //提示框
                JOptionPane.showMessageDialog(panel,"请选择你要删除的行！！！","",JOptionPane.PLAIN_MESSAGE);
                return;
            }else {
                int select = JOptionPane.showConfirmDialog(panel,"你确定要删除吗？","删除（确认对话框）",JOptionPane.YES_NO_CANCEL_OPTION);
                //yes0   no1    取消2
                if(select == 0){
                    defaultTableModel.removeRow(row);
                }
            }
            }
        });
        //新增保存
        button3.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent arg0) {
            //订单主表详情
            Order newOrder = new Order();
            if(defaultTableModel.getRowCount() < 1){
                JOptionPane.showMessageDialog(panel,"没有菜品信息，请添加菜品！","",JOptionPane.PLAIN_MESSAGE);
                return;
            }
            //餐桌
            String deskName = deskBox.getSelectedItem().toString();
            if("全部".equals(deskName)){
                JOptionPane.showMessageDialog(panel,"请选择餐桌！","",JOptionPane.PLAIN_MESSAGE);
                return;
            }
            int deskId = -1;
            Map<String,Integer> deskMap = MapUtils.getDeskNameIdMap();
            if(deskMap.containsKey(deskName)){
                deskId = deskMap.get(deskName);
            }
            Object[][] objects = GetDataUtils.getFreeDesk();
            Boolean mark = false;
            for(int i = 0; i < objects.length; i++){
                if(deskId == Integer.valueOf(objects[i][0].toString())){
                    mark = true;//该餐桌是空闲状态
                }
            }
            if(!mark){
                JOptionPane.showMessageDialog(panel,"该餐桌正在使用，请选择其他餐桌！","",JOptionPane.PLAIN_MESSAGE);
                return;
            }
            newOrder.setDeskId(deskId);
            int allPrice = 0;
            //订单子表详情
            List<OrderItem> orderItemList = new ArrayList<>();
            for(int i = 0; i < defaultTableModel.getRowCount(); i++){
                OrderItem orderItem = new OrderItem();
                orderItem.setGreensId(Integer.valueOf(defaultTableModel.getValueAt(i,0).toString()));
                orderItem.setGreensName(defaultTableModel.getValueAt(i,1).toString());
                int greensPrice = Integer.valueOf(defaultTableModel.getValueAt(i,2).toString());
                orderItem.setGreensPrice(greensPrice);
                int number = Integer.valueOf(defaultTableModel.getValueAt(i,3).toString());
                orderItem.setNumber(number);
                allPrice += greensPrice * number;
                orderItemList.add(orderItem);
            }
            newOrder.setAllPrice(allPrice);
            int result = addOrder(newOrder,orderItemList);
            if(result == 1){
                JOptionPane.showMessageDialog(panel,"添加成功！！！","",JOptionPane.PLAIN_MESSAGE);
                frameHide();
                abstractOrderView.setData(null);
                abstractOrderView.table.repaint();
                abstractOrderView.frameShow();
            }else{
                JOptionPane.showMessageDialog(panel,"添加失败，请重新添加！！！","",JOptionPane.PLAIN_MESSAGE);
                return;
            }
            }
        });
        //修改保存
        button4.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent arg0) {
            int orderId = order.getId();
            if(defaultTableModel.getRowCount() < 1){
                JOptionPane.showMessageDialog(panel,"没有菜品信息，请添加菜品！","",JOptionPane.PLAIN_MESSAGE);
                return;
            }
            //订单主表详情
            Order newOrder = new Order();
            //餐桌
            String deskName = deskBox.getSelectedItem().toString();
            if("全部".equals(deskName)){
                JOptionPane.showMessageDialog(panel,"请选择餐桌！","",JOptionPane.PLAIN_MESSAGE);
                return;
            }
            int deskId = -1;
            Map<String,Integer> deskMap = MapUtils.getDeskNameIdMap();
            if(deskMap.containsKey(deskName)){
                deskId = deskMap.get(deskName);
            }
            if(deskId != order.getDeskId()){
                Object[][] objects = GetDataUtils.getFreeDesk();
                Boolean mark = false;
                for(int i = 0; i < objects.length; i++){
                    if(deskId == Integer.valueOf(objects[i][0].toString())){
                        mark = true;//该餐桌是空闲状态
                    }
                }
                if(!mark){
                    JOptionPane.showMessageDialog(panel,"该餐桌正在使用，请选择其他餐桌！","",JOptionPane.PLAIN_MESSAGE);
                    return;
                }
            }

            newOrder.setId(orderId);
            newOrder.setDeskId(deskId);
            int allPrice = 0;
            //订单子表详情
            List<OrderItem> orderItemList = new ArrayList<>();
            for(int i = 0; i < defaultTableModel.getRowCount(); i++){
                OrderItem orderItem = new OrderItem();
                orderItem.setGreensId(Integer.valueOf(defaultTableModel.getValueAt(i,0).toString()));
                orderItem.setGreensName(defaultTableModel.getValueAt(i,1).toString());
                int greensPrice = Integer.valueOf(defaultTableModel.getValueAt(i,2).toString());
                orderItem.setGreensPrice(greensPrice);
                int number = Integer.valueOf(defaultTableModel.getValueAt(i,3).toString());
                orderItem.setNumber(number);
                allPrice += greensPrice * number;
                orderItemList.add(orderItem);
            }
            newOrder.setAllPrice(allPrice);
            int result = updateOrder(newOrder,order,orderItemList);
            if(result == 1){
                JOptionPane.showMessageDialog(panel,"修改成功！！！","",JOptionPane.PLAIN_MESSAGE);
                frameHide();
                abstractOrderView.setData(null);
                abstractOrderView.table.repaint();
                abstractOrderView.frameShow();
            }else{
                JOptionPane.showMessageDialog(panel,"修改失败，请重新修改！！！","",JOptionPane.PLAIN_MESSAGE);
                return;
            }
            }
        });
        //取消
        button5.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            frameHide();
            abstractOrderView.getFrame().setVisible(true);
            }
        });
    }

    //布局的隐藏方法
    public void  frameHide(){
        jframe.setVisible(false);
    }
    //布局的显示方法
    public void frameShow(){
        jframe.setVisible(true);
    }

    public abstract int addOrder(Order order,List<OrderItem> orderItems);

    public abstract int updateOrder(Order newOrder,Order oldOrder,List<OrderItem> orderItems);
}
