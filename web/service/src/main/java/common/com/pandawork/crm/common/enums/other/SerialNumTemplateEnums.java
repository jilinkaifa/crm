package com.pandawork.crm.common.enums.other;

/**
 * 流水号枚举
 * 对系统中所有的流水号格式进行管理
 * 流水号格式为：前缀+时间+数字
 * 中间用 | 隔开
 *
 * @author: zhangteng
 * @time: 2015/11/11 17:46
 **/
public enum SerialNumTemplateEnums {

    StockInSerialNum("入库单流水号", "RKD-|yyyyMMdd|####"),
    StockOutSerialNum("出库单流水号", "CKD-|yyyyMMdd|####"),
    IncomeOnSerialNum("盘盈单流水号", "PYD-|yyyyMMdd|####"),
    LossOnSerialNum("盘亏单流水号", "PKD-|yyyyMMdd|####"),
    SettlementSerialNum("结算表流水号", "JSD-|yyyyMMdd|####"),
    IngredientNum("原配料编号","IDG-|yyyyMMdd|####"),
    CostCardSerialNum("成本卡流水号", "CBK-|yyyyMMdd|####"),
    StorageItemNum("库存物品编号","KCI-|yyyyMMdd|####"),
    ;

    private String name;

    private String template;

    SerialNumTemplateEnums(String name, String template) {
        this.name = name;
        this.template = template;
    }

    public String getName() {
        return name;
    }

    public String getTemplate() {
        return template;
    }
}
