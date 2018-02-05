package com.pandawork.crm.common.enums.party;

import java.util.HashMap;
import java.util.Map;

/**
 * LoginTypeEnums
 * 登陆类型
 * @author xiaozl
 * @date: 2016/6/24
 */
public enum LoginTypeEnums {

    BackgroundLogin(1,"后台登录"),
    WaiterLogin(2,"服务员端登录"),
    BackKitchenLogin(3,"后厨登录"),
    BarLogin(4, "吧台客户端登录"),
    ;

    private Integer id;
    private String name;

    LoginTypeEnums(Integer id, String name){
        this.id = id;
        this.name = name;
    }

    private static Map<Integer,LoginTypeEnums> map = new HashMap<Integer, LoginTypeEnums>();

    static {
        for (LoginTypeEnums enums : LoginTypeEnums.values()){
            map.put(enums.getId(),enums);
        }
    }

    public static LoginTypeEnums valueOf(int id){
        return valueOf(id,null);
    }

    public static LoginTypeEnums valueOf(int id,LoginTypeEnums defaultValue){
        LoginTypeEnums enums = map.get(id);
        return enums == null ? defaultValue : enums;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
