package com.pandawork.crm.common.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * DeletedEnums
 * Created by shura
 * Date:  2017/7/20.
 * Time:  9:32
 */
public enum DeletedEnums {

    Deleted(1,"已删除"),
    UnDeleted(0,"未删除");

    private Integer id;
    private String status;

    DeletedEnums(Integer id,String status){
        this.id = id;
        this.status = status;
    }
    private static Map<Integer, DeletedEnums> map = new HashMap<Integer, DeletedEnums>();

    static {
        for (DeletedEnums deletedEnums : DeletedEnums.values()) {
            map.put(deletedEnums.getId(), deletedEnums);
        }
    }

    public static DeletedEnums valueOf(int id) {
        return valueOf(id, null);
    }

    public static DeletedEnums valueOf(int id, DeletedEnums defaultValue) {
        DeletedEnums deletedEnums = map.get(id);
        return deletedEnums == null ? defaultValue : deletedEnums;
    }

    public Integer getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }
}
