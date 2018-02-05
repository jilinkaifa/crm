package com.pandawork.crm.common.enums.event;

import java.util.HashMap;
import java.util.Map;

/**
 * EventLevelEnmus
 *
 * @author Flying
 * @date 2017/7/27 14:04
 */
public enum EventLevelEnums {

    None(0, "无"),
    OneLevel (1, "一级"),
    TwoLevel (2, "二级"),
    ThereLevel (3, "三级")
    ;

    private Integer id;
    private String level;

    EventLevelEnums(Integer id, String level){
        this.id = id;
        this.level = level;
    }

    private static Map<Integer, EventLevelEnums> map = new HashMap<Integer, EventLevelEnums>();

    static {
        for (EventLevelEnums enums : EventLevelEnums.values()) {
            map.put(enums.getId(), enums);
        }
    }

    public static EventLevelEnums valueOf(int id) {
        return valueOf(id, null);
    }

    public static EventLevelEnums valueOf(int id, EventLevelEnums defaultValue) {
        EventLevelEnums enums = map.get(id);
        return enums == null ? defaultValue : enums;
    }

    public Integer getId() {
        return id;
    }

    public String getLevel() {
        return level;
    }
}
