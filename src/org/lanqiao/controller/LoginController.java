package org.lanqiao.controller;

import org.lanqiao.model.User;
import org.lanqiao.util.GetDataUtils;
import org.lanqiao.util.MD5Utils;
import org.lanqiao.view.AbstractLoginView;
import org.lanqiao.view.AbstractMenuView;

import javax.swing.*;

public class LoginController extends AbstractLoginView {
    @Override
    public void login(String username, String password) {
        if(username != null && !username.equals("")){
            if(password != null && !password.equals("")){
                String passwordMd5 = MD5Utils.MD5(password);
                Boolean mark = false;
                User user = new User();
                Object[][] objects = GetDataUtils.getUser();
                for(int i = 0; i < objects.length; i++){
                    if((objects[i][1]).equals(username) && (objects[i][2].equals(passwordMd5))){
                        user.setId(Integer.valueOf(objects[i][0].toString()));
                        user.setUsername(objects[i][1].toString());
                        user.setPassword(objects[i][2].toString());
                        user.setTel(objects[i][3].toString());
                        user.setSex(Integer.valueOf(objects[i][4].toString()));
                        user.setAddress(objects[i][5].toString());
                        user.setEmail(objects[i][6].toString());
                        mark = true;
                        break;
                    }
                }
                if(mark){
                    JOptionPane.showMessageDialog(new JPanel(),"登陆成功!");
                    frameHide();
                    AbstractMenuView menuView = new AbstractMenuView(user);
                    menuView.init();
                }else{
                    setErrlable("用户名或密码错误！");
                }
            }else{
                setErrlable("请输入密码！");
            }
        }else{
            setErrlable("请输入用户名！");
        }
    }
}
