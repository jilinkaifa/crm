package com.pandawork.crm.common.dto.profile;

/**
 * LabelItemDto
 * 标签项Dto
 *
 * @author Flying
 * @date 2017/8/8 19:59
 */
public class LabelItemDto {

    //标签类型id
    private Integer typeId;

    //所有标签项
    private String itemList;

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public String getItemList() {
        return itemList;
    }

    public void setItemList(String itemList) {
        this.itemList = itemList;
    }
}
