package com.pandawork.crm.common.enums.client;

import java.util.HashMap;
import java.util.Map;

/**
 * VisitFromEnums
 * Created by shura
 * Date:  2017/8/6.
 * Time:  21:02
 */
public enum VisitFromEnums {

    Hospitalized(1,"住院"),
    Outpatient(0,"门诊");

    private Integer id;
    private String visitFrom;

    VisitFromEnums(Integer id, String visitFrom){
        this.id = id;
        this.visitFrom = visitFrom;
    }

    private static Map<Integer, VisitFromEnums> map = new HashMap<Integer, VisitFromEnums>();

    static {
        for (VisitFromEnums visitFromEnums : VisitFromEnums.values()) {
            map.put(visitFromEnums.getId(), visitFromEnums);
        }
    }

    public static VisitFromEnums valueOf(int id) {
        return valueOf(id, null);
    }

    public static VisitFromEnums valueOf(int id, VisitFromEnums defaultValue) {
        VisitFromEnums visitFromEnums = map.get(id);
        return visitFromEnums == null ? defaultValue : visitFromEnums;
    }

    public Integer getId() {
        return id;
    }

    public String getVisitFrom() {
        return visitFrom;
    }
}
