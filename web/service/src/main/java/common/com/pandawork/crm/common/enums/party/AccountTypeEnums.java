package com.pandawork.crm.common.enums.party;

import java.util.HashMap;
import java.util.Map;

/**
 * AccountTypeEnums
 *
 * @author: zhangteng
 * @time: 15/10/16 上午9:32
 */
public enum AccountTypeEnums {

    Normal(1, "正常账户"),
    Wechat(2, "微信账户")
    ;
    private Integer id;
    private String type;

    AccountTypeEnums(Integer id, String type) {
        this.id = id;
        this.type = type;
    }

    private static Map<Integer, AccountTypeEnums> map = new HashMap<Integer, AccountTypeEnums>();

    static {
        for (AccountTypeEnums enums : AccountTypeEnums.values()) {
            map.put(enums.getId(), enums);
        }
    }

    public static AccountTypeEnums valueOf(int id) {
        return valueOf(id, null);
    }

    public static AccountTypeEnums valueOf(int id, AccountTypeEnums defaultValue) {
        AccountTypeEnums enums = map.get(id);
        return enums == null ? defaultValue : enums;
    }

    public Integer getId() {
        return id;
    }

    public String getType() {
        return type;
    }
}
