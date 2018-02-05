package com.pandawork.crm.common.enums.party;

import java.util.HashMap;
import java.util.Map;

/**
 * PartyTypeEnums
 *
 * @author: zhangteng
 * @time: 15/10/14 下午8:56
 */
public enum   PartyTypeEnums {
    Employee(1, "员工"),
    Vip(2, "会员"),
    Supplier(3, "供货商")
    ;

    private Integer id;
    private String name;

    PartyTypeEnums(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    private static Map<Integer, PartyTypeEnums> map = new HashMap<Integer, PartyTypeEnums>();

    static {
        for (PartyTypeEnums enums : PartyTypeEnums.values()) {
            map.put(enums.getId(), enums);
        }
    }

    public static PartyTypeEnums valueOf(int id) {
        return valueOf(id, null);
    }

    public static PartyTypeEnums valueOf(int id, PartyTypeEnums defaultValue) {
        PartyTypeEnums enums = map.get(id);
        return enums == null ? defaultValue : enums;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
