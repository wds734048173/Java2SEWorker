package org.lanqiao.controller;

import org.apache.commons.dbutils.QueryRunner;
import org.lanqiao.model.GreensClass;
import org.lanqiao.util.DataSourceUtils;
import org.lanqiao.view.AbstractAdd_UpdateGreensClassView;
import org.lanqiao.view.AbstractGreensClassView;

import java.sql.SQLException;

public class Add_UpdateGreensClassController extends AbstractAdd_UpdateGreensClassView {
    QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
    public Add_UpdateGreensClassController(AbstractGreensClassView abstractGreensClassView, GreensClass greensClass) {
        super(abstractGreensClassView, greensClass);
    }

    @Override
    public int add() {
        String name = String.valueOf(greensClassName.getText());
        String sql = "INSERT INTO greensClass (name) VALUES (?)";
        int result = -1;
        try {
            result = qr.update(sql,name);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public int update() {
        int id = greensClass.getId();
        String name = String.valueOf(greensClassName.getText());
        String sql = "UPDATE greensClass SET `name` = ? WHERE id = ?";
        int result = -1;
        try {
            result = qr.update(sql,name,id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

}
