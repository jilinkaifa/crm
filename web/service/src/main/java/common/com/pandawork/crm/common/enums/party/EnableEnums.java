package com.pandawork.crm.common.enums.party;

import java.util.HashMap;
import java.util.Map;

/**
 * 启用、禁用枚举
 *
 * @author: zhangteng
 * @time: 15/10/16 上午9:29
 */
public enum EnableEnums {

    Enabled(1, "启用"),
    Disabled(0, "禁用");

    private Integer id;
    private String status;

    EnableEnums(Integer id, String status) {
        this.id = id;
        this.status = status;
    }

    private static Map<Integer, EnableEnums> map = new HashMap<Integer, EnableEnums>();

    static {
        for (EnableEnums enums : EnableEnums.values()) {
            map.put(enums.getId(), enums);
        }
    }

    public static EnableEnums valueOf(int id) {
        return valueOf(id, null);
    }

    public static EnableEnums valueOf(int id, EnableEnums defaultValue) {
        EnableEnums enums = map.get(id);
        return enums == null ? defaultValue : enums;
    }

    public Integer getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }
}