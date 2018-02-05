package com.pandawork.crm.common.dto.profile;

/**
 * ProfileDto
 * 患者画像信息dto
 *
 * @author Flying
 * @date 2017/7/29 16:26
 */
public class ProfileDto {

    //标签类型id
    private Integer typeId;

    //标签类型名称
    private String typeName;

    //标签项id
    private Integer itemId;

    //标签项名称
    private String itemName;

    //一个标签类型下的所有标签项
    private String itemList;

    //是否多选
    private Integer isMultiple;

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Integer getIsMultiple() {
        return isMultiple;
    }

    public void setIsMultiple(Integer isMultiple) {
        this.isMultiple = isMultiple;
    }

    public String getItemList() {
        return itemList;
    }

    public void setItemList(String itemList) {
        this.itemList = itemList;
    }
}
