package org.lanqiao.view;

import org.lanqiao.controller.Add_UpdateGreensController;
import org.lanqiao.global.LayoutValue;
import org.lanqiao.model.Greens;
import org.lanqiao.common.EvenOddRenderer;
import org.lanqiao.util.GetDataUtils;
import org.lanqiao.util.MapUtils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Map;

/**
 * 菜名
 * */
public abstract class AbstractGreensView {
    AbstractGreensView abstractGreensView = this;
    private JFrame frame = null;
    //初始化数据
    private JPanel panel = null;//容器
    private JPanel panel1 = new JPanel();//容器，按钮/搜索功能
    JButton button1 = new JButton("  新增  ");
    JButton button2 = new JButton("  修改  ");
    JButton button3 = new JButton("  删除  ");
    JTextField searchField = new JTextField(20);//搜索框，菜名
    JComboBox greensClassBox = null;//菜系的下拉列表
    public String[] greensClassData = GetDataUtils.getGreensClassNames();
    JButton button4 = new JButton("  查询  ");
    public DefaultTableModel defaultTableModel = new DefaultTableModel(){
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    JTable table = new JTable(defaultTableModel);
    //构造器
    public AbstractGreensView(JFrame frame) {
        this.frame = frame;
        setGreensClassBox(greensClassData);
        componetInit();
        init();
        addAction();

    }
    //设置元素大小等属性（每个按钮的大小，位置等）
    public void componetInit(){
        defaultTableModel.addColumn("菜品id");
        defaultTableModel.addColumn("菜品名称");
        defaultTableModel.addColumn("菜品价格");
        defaultTableModel.addColumn("菜品类别");
        setData(null);
        searchField.setText("请输入您要查询的菜名");
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
    }
    //设置布局（把元素放到对应的容器中）
    public void init(){
        panel.setLayout(new BorderLayout());
        panel1.setLayout(new FlowLayout(FlowLayout.LEFT));
        panel1.add(button1);
        panel1.add(button2);
        panel1.add(button3);
        panel1.add(greensClassBox);
        panel1.add(searchField);
        panel1.add(button4);
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

        //文本获取焦点，置空信息
        searchField.addFocusListener(new FocusAdapter() {
            //获取焦点
            @Override
            public void focusGained(FocusEvent e) {
            String str = searchField.getText();
            if("请输入您要查询的菜名".equals(str)){
                searchField.setText("");
            }
            }
        });
        //新增
        button1.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent arg0) {
            Add_UpdateGreensController addGreensController = new Add_UpdateGreensController(abstractGreensView,null,null);
            addGreensController.frameShow();
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
                int price = Integer.valueOf(String.valueOf(defaultTableModel.getValueAt(row, 2)));
                String greensClassName = String.valueOf(defaultTableModel.getValueAt(row,3));
//                int greensClassId = -1;
//                Map<String,Integer> map = MapUtils.getGreensClassNameIdMap();
//                if(map.containsKey(greensClassName)){
//                    greensClassId = map.get(greensClassName);
//                }
                Greens greens = new Greens();
                greens.setId(id);
                greens.setName(name);
                greens.setPrice(price);
                Add_UpdateGreensController updateGreensController = new Add_UpdateGreensController(abstractGreensView, greens,greensClassName);
                updateGreensController.frameShow();
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
                    delete(id);
                    defaultTableModel.removeRow(row);
                }
            }
            }
        });
        //查询
        button4.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            //获取数据，并调用方法
            //菜系
            String greensClass = greensClassBox.getSelectedItem().toString();
            int greensClassId = -1;
            String greensName = "";
            if(!greensClass.equals("全部")){
                Map<String,Integer> map = MapUtils.getGreensClassNameIdMap();
                if(map.containsKey(greensClass)){
                    greensClassId = map.get(greensClass);
                }
            }
            //菜名
            if(!"请输入您要查询的菜名".equals(searchField.getText())){
                greensName = searchField.getText().trim();
            }
            Object object = greensClassId + "_" + greensName;
            setData(object);
            }
        });
    }
    public JPanel getPanel(){
        return panel;
    }
    //获取数据
    public void setData(Object o){
        Object[][] objects = getGreens(o);
        //清空原始数据
        while(defaultTableModel.getRowCount()>0){
            defaultTableModel.removeRow(defaultTableModel.getRowCount()-1);
        }
        //写入新数据
        for(int i = 0; i < objects.length; i++){
            defaultTableModel.addRow(objects[i]);
        }
    }
    public abstract Object[][] getGreens(Object obj);
    //删除数据
    public abstract void delete(int id);

    //布局的隐藏方法
    public void  frameHide(){
        frame.setVisible(false);
    }
    //布局的显示方法
    public void frameShow(){
        setData(null);
        frame.setVisible(true);
    }

    public JFrame getFrame(){
        return frame;
    }

    public void setGreensClassBox(String[] greensClassData){
        if(greensClassBox == null){
            greensClassBox = new JComboBox(greensClassData);
        }else{
            greensClassBox.removeAllItems();
            for(int i = 0; i < greensClassData.length; i++){
                greensClassBox.addItem(greensClassData[i]);
            }
        }
    }
}

