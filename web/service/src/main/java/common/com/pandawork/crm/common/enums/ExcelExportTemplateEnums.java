package com.pandawork.crm.common.enums;

import com.pandawork.core.pweio.excel.ExcelTemplateEnum;

/**
 * ExcelExportTemplateEnums
 * Excel模板枚举
 * @author dujuan
 * @date 2015/12/1
 */
public enum ExcelExportTemplateEnums implements ExcelTemplateEnum {

    AdminClientTemplate("患者模板",
            "classpath:template/excel/admin_client_excel_template.xls");


    private String name;
    private String filePath;

    ExcelExportTemplateEnums(String name, String filePath) {
        this.name = name;
        this.filePath = filePath;
    }

    public String getName() {
        return name;
    }

    public String getFilePath() {
        return filePath;
    }
}
