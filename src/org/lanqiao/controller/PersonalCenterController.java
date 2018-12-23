package org.lanqiao.controller;

import org.apache.commons.dbutils.QueryRunner;
import org.lanqiao.model.User;
import org.lanqiao.util.DataSourceUtils;
import org.lanqiao.util.MD5Utils;
import org.lanqiao.view.AbstractPersonalCenterView;

import javax.swing.*;
import java.sql.SQLException;

public class PersonalCenterController extends AbstractPersonalCenterView {

    public PersonalCenterController(JFrame frame, User user) {
        super(frame,user);
    }

    @Override
    public void update(JFrame frame,User user) {
        QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "UPDATE `user` SET username = ?,`password` = ?,tel = ?,sex = ?,address = ?,email = ? WHERE id = ?";
        int id = user.getId();
        String username = user.getUsername();
        String password = user.getPassword();
        String tel = user.getTel();
        int sex = user.getSex();
        String address = user.getAddress();
        String email = user.getEmail();
        int index = 0;
        try {
            index = qr.update(sql,username, MD5Utils.MD5(password),tel,sex,address,email,id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(index == 1){
            JOptionPane.showMessageDialog(new JPanel(),"修改成功，请重新登录!");
            frame.setVisible(false);
            LoginController loginContraller = new LoginController();
            loginContraller.frameShow();
        }
    }
}
