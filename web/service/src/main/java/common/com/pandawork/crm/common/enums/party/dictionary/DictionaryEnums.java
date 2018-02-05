package com.pandawork.crm.common.enums.party.dictionary;

import java.util.HashMap;
import java.util.Map;

/**
 * DictionaryEnums
 * Created by shura
 * Date:  2017/7/21.
 * Time:  15:18
 */
public enum DictionaryEnums {

    DIC_MANAGEMENT(1,"字典值管理"),
    DIC_COMMON(2,"公共字典"),
    DIC_MEDICINE(3,"医药相关"),

    /************患者信息管理***********/
    DIC_CLIENT_TYPE(4,"患者类型"),
    DIC_CLIENT_MCI_TYPE(9,"医保类型"),
    DIC_CLIENT_TYPE_NORMAL(5,"普通患者"),
    DIC_CLIENT_TYPE_MEMBER(6,"普通会员"),
    DIC_CLIENT_TYPE_DIAMOND(7,"钻石会员"),
    DIC_NATION(8,"常用民族"),
    DIC_DEC_RELATION(13,"第二联系人关系"),

    /************问卷信息管理***********/
    DIC_QUEST_ITEM(15,"问卷项"),
    DIC_DOSAGE_UNIT(20,"药剂单位"),
    DIC_DOSAGE_NAME(19,"药品"),
    DIC_DOSAGE(16,"用药情况"),

    /************系统用户信息管理********/
    DIC_PARTY_DEPARTMENT(22,"部门"),
    DIC_PARTY_POSITION(23,"职位"),
    DIC_PARTY_IMMEDIATE_S(24,"直接上级"),
    DIC_SECURITY_GROUP(49,"系统用户角色"),

    /************标签管理********/
    DIC_LABEL(29,"标签"),
    DIC_LABEL_TYPE(30,"标签类型"),
    DIC_LABEL_ITEM(31,"标签项"),

    /*************关联检查项***********/
    DIC_CHECK_ITEM(26, "检查项"),

    /************关联积分项***********/
    DIC_POINTS_ITEM(27, "积分项")
    ;

    private Integer id;
    private String dictionary;

    DictionaryEnums(Integer id, String dictionary){
        this.id = id;
        this.dictionary = dictionary;
    }

    private static Map<Integer, DictionaryEnums> map = new HashMap<Integer, DictionaryEnums>();

    static {
        for (DictionaryEnums dictionaryEnums : DictionaryEnums.values()) {
            map.put(dictionaryEnums.getId(), dictionaryEnums);
        }
    }

    public static DictionaryEnums valueOf(int id) {
        return valueOf(id, null);
    }

    public static DictionaryEnums valueOf(int id, DictionaryEnums defaultValue) {
        DictionaryEnums dictionaryEnums = map.get(id);
        return dictionaryEnums == null ? defaultValue : dictionaryEnums;
    }

    public Integer getId() {
        return id;
    }

    public String getDictionary() {
        return dictionary;
    }

}
