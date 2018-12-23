package org.lanqiao.controller;

import org.apache.commons.dbutils.QueryRunner;
import org.lanqiao.model.Order;
import org.lanqiao.model.OrderItem;
import org.lanqiao.util.DataSourceUtils;
import org.lanqiao.view.AbstractAdd_UpdateOrderView;
import org.lanqiao.view.AbstractOrderView;

import java.sql.*;
import java.util.List;

public class Add_UpdateOrderController extends AbstractAdd_UpdateOrderView {
    QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
    Connection conn = DataSourceUtils.getConntion();

    @Override
    public int addOrder(Order order, List<OrderItem> orderItems) {
        //新增订单：新增主表，返回主表id
        //新增子表，修改餐桌状态
        //DBUtils执行插入操作的时候，无法返回自增主键，这是一个很严重的问题，当然不能怪DBUtils，可以通过变通的方法来实现，比如在MySQL中，执行完了一个插入SQL后，接着执行SELECT LAST_INSERT_ID()语句，就可以获取到自增主键。
        int result = -1;
        try {
            conn.setAutoCommit(false);
            String sql1 = "INSERT INTO `order` (deskId,allPrice,isPay,remark) VALUES (" + order.getDeskId() + "," + order.getAllPrice() + ",0,'')";
            String sql2 = "INSERT INTO orderitem (orderId,greensId,greensPrice,number,greensName) VALUES (?,?,?,?,?)";
            String sql3 = "UPDATE desk SET state = 1 WHERE id = ?";
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql1,Statement.RETURN_GENERATED_KEYS);
            ResultSet rs = stmt.getGeneratedKeys();
            int orderId = -1;
            while (rs.next()){
                orderId = rs.getInt(1);
            }
            for(int i = 0; i < orderItems.size(); i++){
                qr.update(sql2,orderId,orderItems.get(i).getGreensId(),orderItems.get(i).getGreensPrice(),orderItems.get(i).getNumber(),orderItems.get(i).getGreensName());
            }
            qr.update(sql3,order.getDeskId());
            conn.commit();
            result = 1;
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public int updateOrder(Order newOrder, Order oldOrder, List<OrderItem> orderItems) {
        //修改订单：修改主表，删除订单子表，重新插入订单子表，
        //如果餐桌改了，餐桌状态需要修改（新、旧）
        int result = -1;
        try {
            conn.setAutoCommit(false);
            String sql1 = "UPDATE `order` SET deskId = ?, allPrice = ?,remark = ''  WHERE id = ?";
            String sql2 = "DELETE FROM orderitem WHERE orderId = ?";
            String sql3 = "INSERT INTO orderitem (orderId,greensId,greensPrice,number,greensName) VALUES (?,?,?,?,?)";
            //修改餐桌
            String sql4 = "UPDATE desk SET state = 1 WHERE id = ?";//使用中
            String sql5 = "UPDATE desk SET state = 0 WHERE id = ?";//空闲

            qr.update(sql1,newOrder.getDeskId(),newOrder.getAllPrice(),newOrder.getId());
            qr.update(sql2,newOrder.getId());
            for(int i = 0; i < orderItems.size(); i++){
                qr.update(sql3,newOrder.getId(),orderItems.get(i).getGreensId(),orderItems.get(i).getGreensPrice(),orderItems.get(i).getNumber(),orderItems.get(i).getGreensName());
            }
            if(newOrder.getDeskId() != oldOrder.getDeskId()){
                qr.update(sql4,newOrder.getDeskId());
                qr.update(sql5,oldOrder.getDeskId());
            }
            conn.commit();
            result = 1;
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
        return result;
    }

    public Add_UpdateOrderController(AbstractOrderView abstractOrderView,Order order, List<OrderItem> orderItems) {
        super(abstractOrderView,order,orderItems);
    }
}
