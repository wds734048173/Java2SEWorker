package org.lanqiao.controller;

import org.apache.commons.dbutils.QueryRunner;
import org.lanqiao.model.Greens;
import org.lanqiao.util.DataSourceUtils;
import org.lanqiao.util.MapUtils;
import org.lanqiao.view.AbstractAdd_UpdateGreensView;
import org.lanqiao.view.AbstractGreensView;

import java.sql.SQLException;
import java.util.Map;

public class Add_UpdateGreensController extends AbstractAdd_UpdateGreensView {
    QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
    public Add_UpdateGreensController(AbstractGreensView abstractGreensView, Greens greens,String greensClassName) {
        super(abstractGreensView, greens,greensClassName);
    }

    @Override
    public int add() {
        String name = String.valueOf(greensName.getText());
        String greensPrice = String.valueOf(price.getText());
        int greensClassId = -1;
        String greensClassName = String.valueOf(greensClassBox.getSelectedItem());
        Map<String,Integer> map = MapUtils.getGreensClassNameIdMap();
        if(map.containsKey(greensClassName)){
            greensClassId = map.get(greensClassName);
        }
        String sql = "INSERT INTO greens (`Name`,price,greensClassId) VALUES (?,?,?)";
        int result = -1;
        try {
            result = qr.update(sql,name,greensPrice,greensClassId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public int update() {
        int id = greens.getId();
        String name = String.valueOf(greensName.getText());
        int greensPrice = Integer.valueOf(String.valueOf(price.getText()));
        int greensClassId = -1;
        String greensClassName = String.valueOf(greensClassBox.getSelectedItem());
        Map<String,Integer> map = MapUtils.getGreensClassNameIdMap();
        if(map.containsKey(greensClassName)){
            greensClassId = map.get(greensClassName);
        }
        String sql = "UPDATE greens SET `Name` = ?,price = ?,greensClassId = ? WHERE Id = ?;";
        int result = -1;
        try {
            result = qr.update(sql,name,greensPrice,greensClassId,id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public void getData() {

    }
}
