package com.pandawork.crm.common.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * TrueEnums
 *
 * @author: zhangteng
 * @time: 15/10/22 下午10:29
 */
public enum TrueEnums {

    True(1),
    False(0);
    private Integer id;

    TrueEnums(Integer id) {
        this.id = id;
    }

    private static Map<Integer, TrueEnums> map = new HashMap<Integer, TrueEnums>();

    static {
        for (TrueEnums enums : TrueEnums.values()) {
            map.put(enums.getId(), enums);
        }
    }

    public static TrueEnums valueOf(int id) {
        return valueOf(id, null);
    }

    public static TrueEnums valueOf(int id, TrueEnums defaultValue) {
        TrueEnums enums = map.get(id);
        return enums == null ? defaultValue : enums;
    }

    public Integer getId() {
        return id;
    }
}
