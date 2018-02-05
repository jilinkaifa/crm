package com.pandawork.crm.common.enums.client;

import java.util.HashMap;
import java.util.Map;

/**
 * ISFixedEnums
 * Created by shura
 * Date:  2017/7/20.
 * Time:  9:11
 */
public enum IsFixedEnums {

    UnFixed(0,"非定点单位"),
    Fixed(1,"定点单位");

    private Integer id;
    private String isFixed;

    IsFixedEnums(Integer id, String isFixed){
        this.id = id;
        this.isFixed = isFixed;
    }

    private static Map<Integer, IsFixedEnums> map = new HashMap<Integer, IsFixedEnums>();

    static {
        for (IsFixedEnums enums : IsFixedEnums.values()) {
            map.put(enums.getId(), enums);
        }
    }

    public static IsFixedEnums valueOf(int id) {
        return valueOf(id, null);
    }

    public static IsFixedEnums valueOf(int id, IsFixedEnums defaultValue) {
        IsFixedEnums enums = map.get(id);
        return enums == null ? defaultValue : enums;
    }

    public Integer getId() {
        return id;
    }

    public String getIsFixed() {
        return isFixed;
    }
}
