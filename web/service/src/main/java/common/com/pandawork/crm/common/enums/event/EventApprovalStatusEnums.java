package com.pandawork.crm.common.enums.event;

import java.util.HashMap;
import java.util.Map;

/**
 * EventApprovalStatus
 *
 * @author Flying
 * @date 2017/7/25 9:35
 */
public enum EventApprovalStatusEnums {

    None(0, "无"),
    Pending (1, "待审批"),
    Adopt (2, "审批通过"),
    Rejection (3, "审批驳回");


    private Integer id;
    private String status;

    EventApprovalStatusEnums(Integer id, String status){
        this.id = id;
        this.status = status;
    }

    private static Map<Integer, EventApprovalStatusEnums> map = new HashMap<Integer, EventApprovalStatusEnums>();

    static {
        for (EventApprovalStatusEnums enums : EventApprovalStatusEnums.values()) {
            map.put(enums.getId(), enums);
        }
    }

    public static EventApprovalStatusEnums valueOf(int id) {
        return valueOf(id, null);
    }

    public static EventApprovalStatusEnums valueOf(int id, EventApprovalStatusEnums defaultValue) {
        EventApprovalStatusEnums enums = map.get(id);
        return enums == null ? defaultValue : enums;
    }

    public Integer getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

}
