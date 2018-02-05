package com.pandawork.crm.common.enums.event;

import java.util.HashMap;
import java.util.Map;

/**
 * EventStatusEnums
 * 活动通知状态
 *
 * @author Flying
 * @date 2017/8/5 19:18
 */
public enum EventStatusEnums {

    NotNotice (1, "未通知"),
    Notice (2, "通知期"),
    Process (3, "执行期"),
    Archived (3, "已归档"),

    ;

    private Integer id;
    private String status;

    EventStatusEnums(Integer id, String status){
        this.id = id;
        this.status = status;
    }

    private static Map<Integer, EventStatusEnums> map = new HashMap<Integer, EventStatusEnums>();

    static {
        for (EventStatusEnums enums : EventStatusEnums.values()) {
            map.put(enums.getId(), enums);
        }
    }

    public static EventStatusEnums valueOf(int id) {
        return valueOf(id, null);
    }

    public static EventStatusEnums valueOf(int id, EventStatusEnums defaultValue) {
        EventStatusEnums enums = map.get(id);
        return enums == null ? defaultValue : enums;
    }

    public Integer getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }
}
