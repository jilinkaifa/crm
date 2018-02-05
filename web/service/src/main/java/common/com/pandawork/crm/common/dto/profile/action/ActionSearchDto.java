package com.pandawork.crm.common.dto.profile.action;

/**
 * ActionDto
 * Author： linjie
 * Date: 2017/8/2
 * Time: 9:16
 */
public class ActionSearchDto {
    //医保类型
    private Integer dicMciType;
    //民族
    private Integer dicNation;
    //性别
    private Integer gender;
    //标签项1
    private Integer labelItemFirst;
    //标签项2
    private Integer labelItemSecond;
    //标签项3
    private Integer labelItemThird;

//    // 分页大小
    private Integer pageSize;

    //标签项类型1
    private Integer labelTypeFirst;

    //标签项类型2
    private Integer labelTypeSecond;

    //标签项类型3
    private Integer labelTypeThird;

    // 偏移量
   private Integer offset;

    //页码
    private Integer pageNo;

    //用户类型
    private Integer UserType;

    //判断and或or所在的位置
    private  Integer signPosition;

    //判断标签不为空的查询条件个数
    private Integer conditionCount;


    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public Integer getDicNation() {
        return dicNation;
    }

    public void setDicNation(Integer dicNation) {
        this.dicNation = dicNation;
    }

    public Integer getDicMciType() {
        return dicMciType;
    }

    public void setDicMciType(Integer dicMciType) {
        this.dicMciType = dicMciType;
    }

    public Integer getUserType() {
        return UserType;
    }

    public void setUserType(Integer userType) {
        UserType = userType;
    }

   public Integer getPageSize() {return pageSize;}

    public void setPageSize(Integer pageSize) {this.pageSize = pageSize;}

    public Integer getOffset() {return offset;}

    public void setOffset(Integer offset) {this.offset = offset;}


    public Integer getlabelTypeFirst() {
        return labelTypeFirst;
    }

    public void setlabelTypeFirst(Integer labelTypeFirst) {
        this.labelTypeFirst = labelTypeFirst;
    }

    public Integer getlabelTypeSecond() {
        return labelTypeSecond;
    }

    public void setlabelTypeSecond(Integer labelTypeSecond) {
        this.labelTypeSecond = labelTypeSecond;
    }

    public Integer getlabelTypeThird() {
        return labelTypeThird;
    }

    public void setlabelTypeThird(Integer labelTypeThird) {
        this.labelTypeThird = labelTypeThird;
    }

    public Integer getLabelItemFirst() {
        return labelItemFirst;
    }

    public void setLabelItemFirst(Integer labelItemFirst) {
        this.labelItemFirst = labelItemFirst;
    }

    public Integer getLabelItemSecond() {
        return labelItemSecond;
    }

    public void setLabelItemSecond(Integer labelItemSecond) {
        this.labelItemSecond = labelItemSecond;
    }

    public Integer getLabelItemThird() {
        return labelItemThird;
    }

    public void setLabelItemThird(Integer labelItemThird) {
        this.labelItemThird = labelItemThird;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getSignPosition() {
        return signPosition;
    }

    public void setSignPosition(Integer signPosition) {
        this.signPosition = signPosition;
    }

    public Integer getConditionCount() {
        return conditionCount;
    }

    public void setConditionCount(Integer conditionCount) {
        this.conditionCount = conditionCount;
    }
}
