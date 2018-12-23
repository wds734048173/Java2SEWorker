package org.lanqiao.controller;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.lanqiao.model.Order;
import org.lanqiao.util.DataSourceUtils;
import org.lanqiao.util.GetDataUtils;
import org.lanqiao.util.MapUtils;
import org.lanqiao.view.AbstractOrderView;

import javax.swing.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class OrderController extends AbstractOrderView {
    QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
    Connection conn = DataSourceUtils.getConntion();
    public OrderController(JFrame frame) {
        super(frame);
    }

    @Override
    public Object[][] getOrders(Object o) {
        QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
        Object[][] objects = null;
        if(o == null){
            objects = GetDataUtils.getOrder();
        }else{
            String[] args = o.toString().split("_");
            int deskIdSearch = Integer.valueOf(args[0]);
            int orderStateId = Integer.valueOf(args[1]);
            if(deskIdSearch == -1){
                if(orderStateId == -1){
                    objects = GetDataUtils.getOrder();
                }else{
                    String sql = "SELECT * FROM `order` WHERE isPay = ?";
                    List<Order> list = null;
                    try {
                        list = qr.query(sql,new BeanListHandler<Order>(Order.class), orderStateId);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    objects = new Object[list.size()][4];
                    for(int i = 0; i < list.size(); i++){
                        objects[i][0] = list.get(i).getId();
                        int deskId = list.get(i).getDeskId();
                        String deskName = "";
                        Map<Integer,String> deskMap = MapUtils.getDeskIdNameMap();
                        if(deskMap.containsKey(deskId)){
                            deskName = deskMap.get(deskId);
                        }
                        objects[i][1] = deskName;
                        objects[i][2] = list.get(i).getAllPrice();
                        orderStateId = list.get(i).getIsPay();
                        Map<Integer,String> isPayMap = MapUtils.getIsPayIdNameMap();
                        String orderStateName = "";
                        if (isPayMap.containsKey(orderStateId)){
                            orderStateName = isPayMap.get(orderStateId);
                        }
                        //0已结帐，1未结账
                        objects[i][3] = orderStateName;
                    }
                }
            }else{
                List<Order> list = null;
                if(orderStateId == -1){
                    String sql = "SELECT * FROM `order` WHERE deskId = ?";
                    try {
                        list = qr.query(sql,new BeanListHandler<Order>(Order.class), deskIdSearch);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }else{
                    String sql = "SELECT * FROM `order` WHERE isPay = ? AND deskId = ?";
                    try {
                        list = qr.query(sql,new BeanListHandler<Order>(Order.class), orderStateId,deskIdSearch);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                objects = new Object[list.size()][4];
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
                    orderStateId = list.get(i).getIsPay();
                    Map<Integer,String> isPayMap = MapUtils.getIsPayIdNameMap();
                    String orderStateName = "";
                    if (isPayMap.containsKey(orderStateId)){
                        orderStateName = isPayMap.get(orderStateId);
                    }
                    //0已结帐，1未结账
                    objects[i][3] = orderStateName;
                }
            }
        }
        return objects;
    }

    @Override
    public Object[][] getOrderItemsByOrderId(int id) {
        return GetDataUtils.getOrderItem(id);
    }

    @Override
    public int delete(int orderId,int deskId) {
        //运用事物处理，删除订单，同时删除订单详情，并把餐桌置成空闲状态
        int result = -1;
        int result1 = -1;
        int result2 = -1;
        int result3 = -1;
        try {
            conn.setAutoCommit(false);
            String sql1 = "DELETE FROM `order` WHERE id = ?";
            String sql2 = "DELETE FROM `orderitem` WHERE orderId = ?";
            String sql3 = "UPDATE desk SET state = 0 WHERE id = ?";
            result1 = qr.update(sql1,orderId);
            result2 = qr.update(sql2,orderId);
            result3 = qr.update(sql3,deskId);
            conn.commit();
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
        if(result1 == 1 && result2 == 1 && result3 == 1){
            result = 1;
        }
        return result;
    }

    @Override
    public int updateState(int orderId,int deskId) {
        //运用事物处理，结账修改订单信息，并把餐桌置成空闲状态
        int result = -1;
        int result1 = -1;
        int result2 = -1;
        try {
            conn.setAutoCommit(false);//关闭自动提交，开启事物
            String sql1 = "UPDATE `order` SET isPay = 1 WHERE id = ?";
            String sql2 = "UPDATE desk SET state = 0 WHERE id = ?";
            result1 = qr.update(sql1,orderId);
            result2 = qr.update(sql2,deskId);
            conn.commit();
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
        if(result1 == 1 && result2 == 1){
            result = 1;
        }
        return result;
    }
}
