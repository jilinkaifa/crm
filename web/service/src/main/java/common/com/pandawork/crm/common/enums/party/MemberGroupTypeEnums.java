package com.pandawork.crm.common.enums.party;

import com.pandawork.crm.common.enums.event.EventTypeEnums;

import java.util.HashMap;
import java.util.Map;

/**
 * MemberGroupEnums
 * 会员组类型表
 *
 * @author Flying
 * @date 2017/8/5 14:12
 */
public enum MemberGroupTypeEnums {

    Member (1, "会员组"),
    Analysis (2, "行为分析组");

    private Integer id;
    private String type;

    MemberGroupTypeEnums(Integer id, String type){
        this.id = id;
        this.type = type;
    }

    private static Map<Integer, MemberGroupTypeEnums> map = new HashMap<Integer, MemberGroupTypeEnums>();

    static {
        for (MemberGroupTypeEnums enums : MemberGroupTypeEnums.values()) {
            map.put(enums.getId(), enums);
        }
    }

    public static MemberGroupTypeEnums valueOf(int id) {
        return valueOf(id, null);
    }

    public static MemberGroupTypeEnums valueOf(int id, MemberGroupTypeEnums defaultValue) {
        MemberGroupTypeEnums enums = map.get(id);
        return enums == null ? defaultValue : enums;
    }

    public Integer getId() {
        return id;
    }

    public String getType() {
        return type;
    }
}
