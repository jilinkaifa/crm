package com.pandawork.crm.common.enums.party;

/**
 * 供货商状态枚举
 *
 * @author: zhangteng
 * @time: 2015/11/7 16:18
 **/
public enum  SupplierStatusEnums {

    Normal(1, "正常"),
    Deleted(2, "已删除");

    private Integer id;

    private String status;

    SupplierStatusEnums(Integer id, String status) {
        this.id = id;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }
}
