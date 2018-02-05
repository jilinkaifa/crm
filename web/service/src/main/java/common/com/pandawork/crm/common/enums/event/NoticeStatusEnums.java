package com.pandawork.crm.common.enums.event;

import java.util.HashMap;
import java.util.Map;

/**
 * NoticeStatus
 * Author： csy
 * Date: 2017/7/31
 * Time: 19:36
 */
public enum NoticeStatusEnums {

    NotNotified (1,"未通知"),
    Notified (2,"已通知");

    private Integer id;
    private String status;

    NoticeStatusEnums(Integer id,String status){
        this.id = id;
        this.status = status;
    }
    private static Map<Integer, NoticeStatusEnums> map = new HashMap<Integer, NoticeStatusEnums>();

    static {
        for (NoticeStatusEnums enums : NoticeStatusEnums.values()) {
            map.put(enums.getId(), enums);
        }
    }

    public static NoticeStatusEnums valueOf(int id) {
        return valueOf(id, null);
    }

    public static NoticeStatusEnums valueOf(int id, NoticeStatusEnums defaultValue) {
        NoticeStatusEnums enums = map.get(id);
        return enums == null ? defaultValue : enums;
    }

    public Integer getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

}
