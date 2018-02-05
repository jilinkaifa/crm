package com.pandawork.crm.common.dto.client.basic;

/**
 * GenderDto
 * Created by shura
 * Date:  2017/7/28.
 * Time:  11:03
 */
public class GenderDto {

    //性别枚举id 1：男 2：女 3：其他
    private Integer id;

    //性别名称
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
