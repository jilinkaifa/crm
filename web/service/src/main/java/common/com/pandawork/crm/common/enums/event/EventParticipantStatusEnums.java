package com.pandawork.crm.common.enums.event;

import java.util.HashMap;
import java.util.Map;

/**
 * EventParticipantStatusEnums
 * Author： csy
 * Date: 2017/7/31
 * Time: 20:24
 */
public enum EventParticipantStatusEnums {

    Finish (1,"完成"),
    NOtFinish (0,"未完成");

    private Integer id;
    private String status;

    EventParticipantStatusEnums(Integer id,String status){
        this.id = id;
        this.status = status;
    }
    private static Map<Integer, EventParticipantStatusEnums> map = new HashMap<Integer, EventParticipantStatusEnums>();

    static {
        for (EventParticipantStatusEnums enums : EventParticipantStatusEnums.values()) {
            map.put(enums.getId(), enums);
        }
    }

    public static EventParticipantStatusEnums valueOf(int id) {
        return valueOf(id, null);
    }

    public static EventParticipantStatusEnums valueOf(int id, EventParticipantStatusEnums defaultValue) {
        EventParticipantStatusEnums enums = map.get(id);
        return enums == null ? defaultValue : enums;
    }

    public Integer getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

}
