package org.lanqiao.util;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.lanqiao.model.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

//从文件获取数据的封装
public class GetDataUtils {
    public static QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
    //获取餐桌信息
    public static Object[][] getDesk() {
        String sql = "SELECT * FROM desk";
        List<Desk> list = null;
        try {
            list = qr.query(sql,new BeanListHandler<Desk>(Desk.class));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Object[][] objects = new Object[list.size()][4];
        for(int i = 0; i < list.size(); i++){
            objects[i][0] = list.get(i).getId();
            objects[i][1] = list.get(i).getName();
            objects[i][2] = list.get(i).getState();
            objects[i][3] = list.get(i).getRemark();
        }
        return objects;
    }
    //获取餐桌名称列表
    public static String[] getDeskNames() {
        String sql = "SELECT * FROM desk";
        List<Desk> list = null;
        try {
            list = qr.query(sql,new BeanListHandler<Desk>(Desk.class));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String[] deskNames = new String[list.size() + 1];
        deskNames[0] = "全部";
        for(int i = 0; i < list.size(); i++){
            deskNames[i + 1] = list.get(i).getName();
        }
        return deskNames;
    }

    //获取菜品名称
    public static String[] getGreensNames() {
        String sql = "SELECT * FROM greens";
        List<Greens> list = null;
        try {
            list = qr.query(sql,new BeanListHandler<Greens>(Greens.class));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String[] greensNames = new String[list.size() + 1];
        greensNames[0] = "全部";
        for(int i = 0; i < list.size(); i++){
            greensNames[i + 1] = list.get(i).getName();
        }
        return greensNames;
    }

    //获取菜系信息
    public static Object[][] getGreensClass() {
        String sql = "SELECT * FROM greensclass";
        List<GreensClass> list = null;
        try {
            list = qr.query(sql,new BeanListHandler<GreensClass>(GreensClass.class));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Object[][] objects = new Object[list.size()][2];
        for(int i = 0; i < list.size(); i++){
            objects[i][0] = list.get(i).getId();
            objects[i][1] = list.get(i).getName();
        }
        return objects;
    }

    //获取菜系名称
    public static String[] getGreensClassNames() {
        String sql = "SELECT * FROM greensclass";
        List<GreensClass> list = null;
        try {
            list = qr.query(sql,new BeanListHandler<GreensClass>(GreensClass.class));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String[] greensClassNames = new String[list.size() + 1];
        greensClassNames[0] = "全部";
        for(int i = 0; i < list.size(); i++){
            greensClassNames[i + 1] = list.get(i).getName();
        }
        return greensClassNames;
    }

    //获取商品信息（菜）
    public static Object[][] getGreens() {
        String sql = "SELECT * FROM greens";
        List<Greens> list = null;
        try {
            list = qr.query(sql,new BeanListHandler<Greens>(Greens.class));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Object[][] objects = new Object[list.size()][4];
        for(int i = 0; i < list.size(); i++){
            objects[i][0] = list.get(i).getId();
            objects[i][1] = list.get(i).getName();
            objects[i][2] = list.get(i).getPrice();
            objects[i][3] = list.get(i).getGreensClassId();
        }
        return objects;
    }

    //获取订单信息
    public static Object[][] getOrder() {
        String sql = "SELECT * FROM `order`";
        List<Order> list = null;
        try {
            list = qr.query(sql,new BeanListHandler<Order>(Order.class));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Object[][] objects = new Object[list.size()][4];
        for(int i = 0; i < list.size(); i++){
            objects[i][0] = list.get(i).getId();
            int deskId = list.get(i).getDeskId();
            String deskName = "";
            Map<Integer,String> map = MapUtils.getDeskIdNameMap();
            if(map.containsKey(deskId)){
                deskName = map.get(deskId);
            }
            objects[i][1] = deskName;
            objects[i][2] = list.get(i).getAllPrice();
            Map<Integer,String> isPayMap = MapUtils.getIsPayIdNameMap();
            String orderStateName = "";
            int orderStateId = list.get(i).getIsPay();
            if (isPayMap.containsKey(orderStateId)){
                orderStateName = isPayMap.get(orderStateId);
            }
            //0已结帐，1未结账
            objects[i][3] = orderStateName;
        }
        return objects;
    }

    //获取订单详情
    public static Object[][] getOrderItem(int id) {
        String sql = "SELECT * FROM orderitem WHERE orderId = ?";
        List<OrderItem> list = null;
        try {
            list = qr.query(sql,new BeanListHandler<OrderItem>(OrderItem.class),id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Object[][] objects = new Object[list.size()][5];
        for(int i = 0; i < list.size(); i++){
            objects[i][0] = list.get(i).getId();
            objects[i][1] = list.get(i).getGreensName();
            objects[i][2] = list.get(i).getGreensPrice();
            objects[i][3] = list.get(i).getNumber();
            objects[i][4] = list.get(i).getGreensId();
        }
        return objects;
    }

    //获取登陆信息
    public static Object[][] getUser() {
        String sql = "SELECT * FROM user";
        List<User> list = null;
        try {
            list = qr.query(sql,new BeanListHandler<User>(User.class));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Object[][] objects = new Object[list.size()][7];
        for(int i = 0; i < list.size(); i++){
            objects[i][0] = list.get(i).getId();
            objects[i][1] = list.get(i).getUsername();
            objects[i][2] = list.get(i).getPassword();
            objects[i][3] = list.get(i).getTel() == null ? "" : list.get(i).getTel();
            objects[i][4] = list.get(i).getSex();
            objects[i][5] = list.get(i).getAddress() == null ? "" : list.get(i).getAddress();
            objects[i][6] = list.get(i).getEmail() == null ? "" : list.get(i).getEmail();
        }
        return objects;
    }

    //获取空闲的餐桌信息
    public static Object[][] getFreeDesk(){
        String sql = "SELECT * FROM desk WHERE state = 0";
        List<Desk> list = null;
        try {
            list = qr.query(sql,new BeanListHandler<Desk>(Desk.class));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Object[][] objects = new Object[list.size()][2];
        for(int i = 0; i < list.size(); i++){
            objects[i][0] = list.get(i).getId();
            objects[i][1] = list.get(i).getName();
        }
        return objects;
    }
}
