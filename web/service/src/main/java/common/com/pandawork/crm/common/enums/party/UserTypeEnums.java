package com.pandawork.crm.common.enums.party;

import java.util.HashMap;
import java.util.Map;

/**
 * 用户状态 Enums
 * @author chenyuting
 * @date 2015/10/23  10:51
 */
public enum UserTypeEnums {

    SAdmin(1, "超级管理员"),
    Admin(2, "系统用户");

    private Integer id;
    private String status;

    UserTypeEnums(Integer id, String status) {
        this.id = id;
        this.status = status;
    }

    private static Map<Integer, UserTypeEnums> map = new HashMap<Integer, UserTypeEnums>();

    static {
        for (UserTypeEnums userTypeEnums : UserTypeEnums.values()) {
            map.put(userTypeEnums.getId(), userTypeEnums);
        }
    }

    public static UserTypeEnums valueOf(int id) {
        return valueOf(id, null);
    }

    public static UserTypeEnums valueOf(int id, UserTypeEnums defaultValue) {
        UserTypeEnums userTypeEnums = map.get(id);
        return userTypeEnums == null ? defaultValue : userTypeEnums;
    }

    public Integer getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

}
