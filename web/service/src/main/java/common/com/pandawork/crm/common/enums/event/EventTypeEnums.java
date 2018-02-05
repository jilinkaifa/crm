package com.pandawork.crm.common.enums.event;

import java.util.HashMap;
import java.util.Map;

/**
 * EventTypeEnums
 *
 * @author Flying
 * @date 2017/7/27 13:54
 */
public enum EventTypeEnums {

    None(0, "无"),
    Member (1, "会员关怀型"),
    Promotion (2, "营销型");

    private Integer id;
    private String type;

    EventTypeEnums(Integer id, String type){
        this.id = id;
        this.type = type;
    }

    private static Map<Integer, EventTypeEnums> map = new HashMap<Integer, EventTypeEnums>();

    static {
        for (EventTypeEnums enums : EventTypeEnums.values()) {
            map.put(enums.getId(), enums);
        }
    }

    public static EventTypeEnums valueOf(int id) {
        return valueOf(id, null);
    }

    public static EventTypeEnums valueOf(int id, EventTypeEnums defaultValue) {
        EventTypeEnums enums = map.get(id);
        return enums == null ? defaultValue : enums;
    }

    public Integer getId() {
        return id;
    }

    public String getType() {
        return type;
    }
}
