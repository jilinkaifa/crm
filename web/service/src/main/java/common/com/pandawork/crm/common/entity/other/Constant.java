package com.pandawork.crm.common.entity.other;


import com.pandawork.core.common.entity.AbstractEntity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/** 常量表
 * Created with IntelliJ IDEA.
 * User: gaoyang
 * Date: 13-8-9
 * Time: 上午9:00
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name="t_constant")
public class Constant extends AbstractEntity {

    private static final long serialVersionUID = 2823288880837100573L;

    @Id
    private Integer Id;

    //键
    private String key;

    //值
    private String value;


    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
