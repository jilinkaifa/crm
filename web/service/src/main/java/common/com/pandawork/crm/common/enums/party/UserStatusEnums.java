package com.pandawork.crm.common.enums.party;

import java.util.HashMap;
import java.util.Map;

/**
 * 用户状态 Enums
 * @author chenyuting
 * @date 2015/10/23  10:51
 */
public enum UserStatusEnums {

    Enabled(1, "可用"),
    Disabled(2, "停用"),
    Deleted(3, "删除");

    private Integer id;
    private String status;

    UserStatusEnums(Integer id, String status) {
        this.id = id;
        this.status = status;
    }

    private static Map<Integer, UserStatusEnums> map = new HashMap<Integer, UserStatusEnums>();

    static {
        for (UserStatusEnums userStatusEnums : UserStatusEnums.values()) {
            map.put(userStatusEnums.getId(), userStatusEnums);
        }
    }

    public static UserStatusEnums valueOf(int id) {
        return valueOf(id, null);
    }

    public static UserStatusEnums valueOf(int id, UserStatusEnums defaultValue) {
        UserStatusEnums userStatusEnums = map.get(id);
        return userStatusEnums == null ? defaultValue : userStatusEnums;
    }

    public Integer getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

}
