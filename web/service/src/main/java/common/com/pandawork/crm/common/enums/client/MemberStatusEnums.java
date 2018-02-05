package com.pandawork.crm.common.enums.client;

import java.util.HashMap;
import java.util.Map;

/**
 * MemberEnums
 * Created by shura
 * Date:  2017/7/20.
 * Time:  9:11
 */
public enum MemberStatusEnums {

    IsUsed(0,"在用"),
    Locked(1,"锁定");

    private Integer id;
    private String memberStatus;

    MemberStatusEnums(Integer id, String memberStatus){
        this.id = id;
        this.memberStatus = memberStatus;
    }

    private static Map<Integer, MemberStatusEnums> map = new HashMap<Integer, MemberStatusEnums>();

    static {
        for (MemberStatusEnums memberStatusEnums : MemberStatusEnums.values()) {
            map.put(memberStatusEnums.getId(), memberStatusEnums);
        }
    }

    public static MemberStatusEnums valueOf(int id) {
        return valueOf(id, null);
    }

    public static MemberStatusEnums valueOf(int id, MemberStatusEnums defaultValue) {
        MemberStatusEnums memberStatusEnums = map.get(id);
        return memberStatusEnums == null ? defaultValue : memberStatusEnums;
    }

    public Integer getId() {
        return id;
    }

    public String getMemberStatus() {
        return memberStatus;
    }
}
