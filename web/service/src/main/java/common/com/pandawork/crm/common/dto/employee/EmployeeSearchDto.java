package com.pandawork.crm.common.dto.employee;

/**
 * EmployeeSearchDto
 * Author： wychen
 * Date: 2017/7/26
 * Time: 21:47
 */
public class EmployeeSearchDto {

    private String name;

    private Integer offset;

    private Integer pageSize;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public EmployeeSearchDto(String name, Integer offset, Integer pageSize){
        this.name = name;
        this.offset = offset;
        this.pageSize = pageSize;
    }
}
