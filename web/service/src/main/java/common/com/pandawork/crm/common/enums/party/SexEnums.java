package com.pandawork.crm.common.enums.party;

import java.util.HashMap;
import java.util.Map;

/**
 * 性别 Enums
 * @author chenyuting
 * @date 2015/10/23 10:59
 */
public enum SexEnums {

    NotExplain(0,"未说明"),
    Male(1,"男"),
    Female(2,"女"),
    other(3,"其他");

    private Integer id;
    private String sex;

    SexEnums(Integer id, String sex) {
        this.id = id;
        this.sex = sex;
    }

    private static Map<Integer, SexEnums> map = new HashMap<Integer, SexEnums>();

    static {
        for (SexEnums sexEnums : SexEnums.values()) {
            map.put(sexEnums.getId(), sexEnums);
        }
    }

    public static SexEnums valueOf(int id) {
        return valueOf(id, null);
    }

    public static SexEnums valueOf(int id, SexEnums defaultValue) {
        SexEnums sexEnums = map.get(id);
        return sexEnums == null ? defaultValue : sexEnums;
    }

    public Integer getId() {
        return id;
    }

    public String getSex() {
        return sex;
    }

}
