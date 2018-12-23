package org.lanqiao.controller;

import org.apache.commons.dbutils.QueryRunner;
import org.lanqiao.model.Desk;
import org.lanqiao.util.DataSourceUtils;
import org.lanqiao.view.AbstractAdd_UpdateDeskView;
import org.lanqiao.view.AbstractDeskView;

import java.sql.SQLException;

public class Add_UpdateDeskController extends AbstractAdd_UpdateDeskView {
    QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());

    public Add_UpdateDeskController(AbstractDeskView abstractDeskView, Desk desk) {
        super(abstractDeskView, desk);
    }

    @Override
    public int add() {
        String name = String.valueOf(deskName.getText());
        String remark = String.valueOf(deskRemark.getText());
        String sql = "INSERT INTO desk (name,remark) VALUES (?,?)";
        int result = -1;
        try {
            result = qr.update(sql,name,remark);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public int update() {
        int id = desk.getId();
        String name = String.valueOf(deskName.getText());
        String remark = String.valueOf(deskRemark.getText());
        String sql = "UPDATE desk SET `name` = ?,remark = ? WHERE id = ?";
        int result = -1;
        try {
            result = qr.update(sql,name,remark,id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
