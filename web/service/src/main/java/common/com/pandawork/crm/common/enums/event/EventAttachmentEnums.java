package com.pandawork.crm.common.enums.event;

import java.util.HashMap;
import java.util.Map;

/**
 * EventAttachmentEnums
 *
 * @author Flying
 * @date 2017/7/30 18:02
 */
public enum EventAttachmentEnums {

    Using(1,"正在使用"),
    UnUsing(0,"未使用");

    private Integer id;
    private String description;


    EventAttachmentEnums(Integer id, String description){
        this.id = id;
        this.description = description;
    }

    private static Map<Integer, EventAttachmentEnums> map = new HashMap<Integer, EventAttachmentEnums>();

    static {
        for (EventAttachmentEnums enums : EventAttachmentEnums.values()){
            map.put(enums.getId(), enums);
        }
    }


    public static EventAttachmentEnums valueOf(int id){
        return valueOf(id, null);
    }

    public static EventAttachmentEnums valueOf(int id, EventAttachmentEnums defaultValue){
        EventAttachmentEnums enums = map.get(id);
        return enums == null ? defaultValue : enums;
    }

    public Integer getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }
}
