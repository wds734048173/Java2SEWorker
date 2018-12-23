package org.lanqiao.controller;

import org.apache.commons.dbutils.QueryRunner;
import org.lanqiao.util.DataSourceUtils;
import org.lanqiao.util.GetDataUtils;
import org.lanqiao.util.MapUtils;
import org.lanqiao.view.AbstractDeskView;

import javax.swing.*;
import java.sql.SQLException;
import java.util.Map;

public class DeskController extends AbstractDeskView {

    public DeskController(JFrame frame) {
        super(frame);
    }

    //获取数据
    @Override
    public Object[][] getDesks(){
        Object[][] objects = GetDataUtils.getDesk();
        for(int i = 0; i < objects.length; i++) {
            int state = Integer.valueOf(String.valueOf(objects[i][2]));
            String stateStr = "";
            Map<Integer,String> map = MapUtils.getDeskStateIdNameMap();
            if(map.containsKey(state)){
                stateStr = map.get(state);
            }
            objects[i][2] = stateStr;
        }
        return objects;
    }

    @Override
    public void addData() {

    }

    @Override
    public void updateData(int id, String[] data) {

    }

    @Override
    public int delete(int id) {
        String sql = "DELETE FROM desk WHERE id = ?";
        QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
        int result = -1;
        try {
            result = qr.update(sql,id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
