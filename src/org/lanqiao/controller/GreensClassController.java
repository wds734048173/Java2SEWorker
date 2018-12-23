package org.lanqiao.controller;

import org.apache.commons.dbutils.QueryRunner;
import org.lanqiao.util.DataSourceUtils;
import org.lanqiao.util.GetDataUtils;
import org.lanqiao.view.AbstractGreensClassView;

import javax.swing.*;
import java.sql.SQLException;


public class GreensClassController extends AbstractGreensClassView{

    public GreensClassController(JFrame frame) {
        super(frame);
    }

    @Override
    public Object[][] getGreensClasss() {
        return GetDataUtils.getGreensClass();
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM greensClass WHERE id = ?";
        QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
        try {
            qr.update(sql,id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
