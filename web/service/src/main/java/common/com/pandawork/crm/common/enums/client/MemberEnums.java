package com.pandawork.crm.common.enums.client;

import java.util.HashMap;
import java.util.Map;

/**
 * MemberEnums
 * Created by shura
 * Date:  2017/7/20.
 * Time:  9:11
 */
public enum MemberEnums {

    NotMember(0,"普通患者"),
    Member(1,"普通会员"),
    DiamondMember(2,"钻石会员");

    private Integer id;
    private String member;

    MemberEnums(Integer id, String member){
        this.id = id;
        this.member = member;
    }

    private static Map<Integer, MemberEnums> map = new HashMap<Integer, MemberEnums>();

    static {
        for (MemberEnums memberEnums : MemberEnums.values()) {
            map.put(memberEnums.getId(), memberEnums);
        }
    }

    public static MemberEnums valueOf(int id) {
        return valueOf(id, null);
    }

    public static MemberEnums valueOf(int id, MemberEnums defaultValue) {
        MemberEnums memberEnums = map.get(id);
        return memberEnums == null ? defaultValue : memberEnums;
    }

    public Integer getId() {
        return id;
    }

    public String getMember() {
        return member;
    }
}
