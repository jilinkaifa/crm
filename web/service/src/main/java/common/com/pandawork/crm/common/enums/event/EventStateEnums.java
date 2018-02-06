package com.pandawork.crm.common.enums.event;

import java.util.HashMap;
import java.util.Map;

/**
 * EventStateEnums
 *
 * @author Flying
 * @date 2017/9/11 10:01
 */
public enum EventStateEnums {

    Prepare (1, "待开展"),
    Doing (2, "进行中"),
    Complete(3,"已完成"),
    Logoff(4,"已注销")
    ;

    private Integer id;
    private String state;

    EventStateEnums(Integer id, String state){
        this.id = id;
        this.state = state;
    }

    private static Map<Integer, EventStateEnums> map = new HashMap<Integer, EventStateEnums>();

    static {
        for (EventStateEnums enums : EventStateEnums.values()) {
            map.put(enums.getId(), enums);
        }
    }

    public static EventStateEnums valueOf(int id) {
        return valueOf(id, null);
    }

    public static EventStateEnums valueOf(int id, EventStateEnums defaultValue) {
        EventStateEnums enums = map.get(id);
        return enums == null ? defaultValue : enums;
    }

    public Integer getId() {
        return id;
    }

    public String getState() {
        return state;
    }
}
