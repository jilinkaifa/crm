package com.pandawork.crm.common.enums.event;


import java.util.HashMap;
import java.util.Map;

/**
 * EventTermStatus
 * Author： wychen
 * Date: 2017/8/1
 * Time: 9:58
 */
public enum EventTermStatusEnums {

    Noticing (1, "通知期"),
    Doing (2, "执行期"),
    Filed (3, "已归档"),
    Pause (4, "暂停"),
    Logout (5, "注销"),
    PauseAll (6, "暂停所有期")

    ;

    private Integer id;
    private String status;

    EventTermStatusEnums(Integer id, String status){
        this.id = id;
        this.status = status;
    }

    private static Map<Integer, EventTermStatusEnums> map = new HashMap<Integer, EventTermStatusEnums>();

    static {
        for (EventTermStatusEnums enums : EventTermStatusEnums.values()) {
            map.put(enums.getId(), enums);
        }
    }

    public static EventTermStatusEnums valueOf(int id) {
        return valueOf(id, null);
    }

    public static EventTermStatusEnums valueOf(int id, EventTermStatusEnums defaultValue) {
        EventTermStatusEnums enums = map.get(id);
        return enums == null ? defaultValue : enums;
    }

    public Integer getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }



}
