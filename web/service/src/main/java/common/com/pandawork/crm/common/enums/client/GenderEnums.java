package com.pandawork.crm.common.enums.client;

import java.util.HashMap;
import java.util.Map;

/**
 * GenderEnums
 * Created by shura
 * Date:  2017/7/20.
 * Time:  9:11
 */
public enum GenderEnums {

    Male(1,"男"),
    Female(2,"女"),
    Other(3,"其他");

    private Integer id;
    private String gender;

    GenderEnums(Integer id, String gender){
        this.id = id;
        this.gender = gender;
    }

    private static Map<Integer, GenderEnums> map = new HashMap<Integer, GenderEnums>();

    static {
        for (GenderEnums enums : GenderEnums.values()) {
            map.put(enums.getId(), enums);
        }
    }

    public static GenderEnums valueOf(int id) {
        return valueOf(id, null);
    }

    public static GenderEnums valueOf(int id, GenderEnums defaultValue) {
        GenderEnums enums = map.get(id);
        return enums == null ? defaultValue : enums;
    }

    public Integer getId() {
        return id;
    }

    public String getGender() {
        return gender;
    }
}
