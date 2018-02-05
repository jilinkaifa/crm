package com.pandawork.crm.common.enums.other;

/**
 * 文件上传之后的存放位置枚举
 * 方便统一管理
 * 文件的建议存放方式为枚举中定义的path + id + 文件名
 * 文件路径前后都要加"/"
 *
 * @author: zhangteng
 * @time: 2015/8/21 22:48
 **/
public enum FileUploadPathEnums {

    EventAttachmentPath("/event/", "活动附件存放目录"),

    ;

    // 路径
    private String path;

    // 描述
    private String description;

    FileUploadPathEnums(String path, String description) {
        this.path = path;
        this.description = description;
    }

    public String getPath() {
        return path;
    }

    public String getDescription() {
        return description;
    }
}
