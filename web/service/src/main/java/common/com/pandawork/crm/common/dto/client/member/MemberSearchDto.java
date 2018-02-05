package com.pandawork.crm.common.dto.client.member;

/**
 * MemberSearchDto
 * Created by shura
 * Date:  2017/7/25.
 * Time:  13:02
 */
public class MemberSearchDto {

    //姓名
    private String name;

    //身份证号
    private String idCardNum;

    //电话
    private String tel;

    //会员分组id
    private Integer memberGroupId;

    //会员状态
    private Integer memberStatus;

    //会员等级
    private Integer level;

    // 页码
    private Integer page;

    // 分页大小
    private Integer pageSize;

    // 偏移量
    private Integer offset;

    //是否定点单位 1：是     0：否
    private Integer isFixed;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdCardNum() {
        return idCardNum;
    }

    public void setIdCardNum(String idCardNum) {
        this.idCardNum = idCardNum;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public Integer getMemberGroupId() {
        return memberGroupId;
    }

    public void setMemberGroupId(Integer memberGroupId) {
        this.memberGroupId = memberGroupId;
    }

    public Integer getMemberStatus() {
        return memberStatus;
    }

    public void setMemberStatus(Integer memberStatus) {
        this.memberStatus = memberStatus;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getIsFixed() {
        return isFixed;
    }

    public void setIsFixed(Integer isFixed) {
        this.isFixed = isFixed;
    }
}
