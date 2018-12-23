package org.lanqiao.controller;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.lanqiao.model.Greens;
import org.lanqiao.util.DataSourceUtils;
import org.lanqiao.util.GetDataUtils;
import org.lanqiao.util.MapUtils;
import org.lanqiao.view.AbstractGreensView;

import javax.swing.*;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class GreensController extends AbstractGreensView {

    public GreensController(JFrame frame) {
        super(frame);
    }

    @Override
    public Object[][] getGreens(Object object) {
        QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
        Object[][] objects = null;
        if(object == null){
            objects = GetDataUtils.getGreens();
        }else{
            String[] args = object.toString().split("_");
            int greensClassId = Integer.valueOf(args[0]);
            String greensName = "";
            if(args.length > 1){
                greensName = object.toString().split("_")[1];
            }
            if(greensClassId == -1){
                if(greensName == ""){
                    objects = GetDataUtils.getGreens();
                }else{
                    String sql = "SELECT * FROM greens WHERE Name LIKE ?";
                    List<Greens> list = null;
                    try {
                        list = qr.query(sql,new BeanListHandler<Greens>(Greens.class),'%' + greensName + '%');
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    objects = new Object[list.size()][4];
                    for(int i = 0; i < list.size(); i++){
                        objects[i][0] = list.get(i).getId();
                        objects[i][1] = list.get(i).getName();
                        objects[i][2] = list.get(i).getPrice();
                        objects[i][3] = list.get(i).getGreensClassId();
                    }
                }
            }else{
                List<Greens> list = null;
                if(greensName == ""){
                    String sql = "SELECT * FROM greens WHERE greensClassId = ?";
                    try {
                        list = qr.query(sql,new BeanListHandler<Greens>(Greens.class),greensClassId);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                }else{
                    String sql = "SELECT * FROM greens WHERE greensClassId = ? AND Name LIKE ?";
                    try {
                        list = qr.query(sql,new BeanListHandler<Greens>(Greens.class),greensClassId,'%' + greensName + '%');
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                objects = new Object[list.size()][4];
                for(int i = 0; i < list.size(); i++){
                    objects[i][0] = list.get(i).getId();
                    objects[i][1] = list.get(i).getName();
                    objects[i][2] = list.get(i).getPrice();
                    objects[i][3] = list.get(i).getGreensClassId();
                }
            }
        }
        for(int i = 0; i < objects.length; i++){
            int greensClassId = Integer.valueOf(String.valueOf(objects[i][3]));
            String greensClassName = "";
            Map<Integer,String> map = MapUtils.getGreensClassIdNameMap();
            if(map.containsKey(greensClassId)){
                greensClassName = map.get(greensClassId);
            }
            objects[i][3] = greensClassName;
        }
        return objects;
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM greens WHERE id = ?";
        QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
        try {
            qr.update(sql,id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
