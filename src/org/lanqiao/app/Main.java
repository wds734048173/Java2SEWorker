package org.lanqiao.app;

import org.lanqiao.controller.LoginController;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        try {
            org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
        } catch(Exception e) {
            //TODO exception
        }
        UIManager.put("RootPane.setupButtonVisible", false);

        LoginController loginContraller = new LoginController();
        loginContraller.frameShow();
    }
}
