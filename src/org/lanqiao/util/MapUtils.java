package org.lanqiao.util;

import org.lanqiao.global.LayoutValue;
import org.lanqiao.model.Greens;

import java.util.HashMap;
import java.util.Map;

public class MapUtils {
    //把餐桌状态id与name做成map
    public static Map<Integer,String> getDeskStateIdNameMap(){
        //state状态：0，空闲；1使用中
        Map<Integer,String> map = new HashMap<>();
        String[] deskStateData = LayoutValue.deskStateData;
        for(int i = 0; i < deskStateData.length; i ++){
            map.put(i,deskStateData[i]);
        }
        return map;
    }
    //把餐桌状态name与id做成map
    public static Map<String,Integer> getDeskStateNameIdMap(){
        Map<String,Integer> map = new HashMap<>();
        String[] deskStateData = LayoutValue.deskStateData;
        for(int i = 0; i < deskStateData.length; i ++){
            map.put(deskStateData[i],i);
        }
        return map;
    }
    //把餐桌id与名称做成map
    public static Map<Integer,String> getDeskIdNameMap(){
        Object[][] objects = GetDataUtils.getDesk();
        Map<Integer,String> map = new HashMap<>();
        for(int i = 0; i < objects.length; i++){
            int id = Integer.valueOf(String.valueOf(objects[i][0]));
            String name = String.valueOf(objects[i][1]);
            map.put(id,name);
        }
        return map;
    }
    //把餐桌名与id做成map
    public static Map<String,Integer> getDeskNameIdMap(){
        Object[][] objects = GetDataUtils.getDesk();
        Map<String,Integer> map = new HashMap<>();
        for(int i = 0; i < objects.length; i++){
            int id = Integer.valueOf(String.valueOf(objects[i][0]));
            String name = String.valueOf(objects[i][1]);
            map.put(name,id);
        }
        return map;
    }
    //把菜系id与餐系名称做成map
    public static Map<Integer,String> getGreensClassIdNameMap(){
        Object[][] objects = GetDataUtils.getGreensClass();
        Map<Integer,String> map = new HashMap<>();
        for(int i = 0; i < objects.length; i++){
            int id = Integer.valueOf(String.valueOf(objects[i][0]));
            String name = String.valueOf(objects[i][1]);
            map.put(id,name);
        }
        return map;
    }
    //把菜系名称与菜系id做成map
    public static Map<String,Integer> getGreensClassNameIdMap(){
        Object[][] objects = GetDataUtils.getGreensClass();
        Map<String,Integer> map = new HashMap<>();
        for(int i = 0; i < objects.length; i++){
            int id = Integer.valueOf(String.valueOf(objects[i][0]));
            String name = String.valueOf(objects[i][1]);
            map.put(name,id);
        }
        return map;
    }
    //把订单状态名称和id做成map
    public static Map<String,Integer> getIsPayNameIdMap(){
        String[] isPayData = LayoutValue.isPayData;
        Map<String,Integer> map = new HashMap<>();
        for(int i = 0; i < isPayData.length; i++){
            map.put(isPayData[i],i);
        }
        return map;
    }
    //把订单状态id和名称做成map
    public static Map<Integer,String> getIsPayIdNameMap(){
        String[] isPayData = LayoutValue.isPayData;
        Map<Integer,String> map = new HashMap<>();
        for(int i = 0; i < isPayData.length; i++){
            map.put(i,isPayData[i]);
        }
        return map;
    }

    //获取菜名和id的键值对
    public static Map<String, Greens> getGreensNameIdMap(){
        Map<String,Greens> map = new HashMap<>();
        Object[][] objects = GetDataUtils.getGreens();
        for(int i = 0; i < objects.length; i++){
            Greens greens = new Greens();
            int greensId = Integer.valueOf(objects[i][0].toString());
            String greensName = objects[i][1].toString();
            int greensPrice = Integer.valueOf(objects[i][2].toString());
            int greensClassId = Integer.valueOf(objects[i][3].toString());
            greens.setGreensClassId(greensClassId);
            greens.setPrice(greensPrice);
            greens.setId(greensId);
            greens.setName(greensName);
            map.put(greensName,greens);
        }
        return map;
    }
}
