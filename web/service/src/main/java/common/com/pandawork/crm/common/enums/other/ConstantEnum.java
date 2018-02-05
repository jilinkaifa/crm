package com.pandawork.crm.common.enums.other;

/**
 * 常量表枚举
 * @author gaoyang
 * @date 2013-08-08
 */
public enum ConstantEnum {

    SaleRank("SaleRank","销量排行"),//销量排行
    WebDomain("WebDomain", "网站域名"),   // 网站域名，用于二维码生成
    DishCategoryLayers("DISH_CATEGORY_LAYERS", "菜品分类等级数"),
    RoundingMode("ROUNDING_MODE","库存计算时是否使用四舍五入, 0为不启用(即直接截断), 1为启用"),
    ;


    private String key;
    private String description;

    ConstantEnum(String key, String description) {
        this.key = key;
        this.description = description;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getDescription() {
        return description;
    }
}
