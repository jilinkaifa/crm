package com.pandawork.crm.common.dto.profile.action;

/**
 * CountSearchDto
 * Author： linjie
 * Date: 2017/8/3
 * Time: 18:06
 */
public class CountSearchDto {
    //性别
    private Integer gender;
    //民族
    private Integer dicNation;
    //医保类型
    private Integer dicMciType;
    //用户类型
    private Integer UserType;

    //标签项类型1
    private Integer labelTypeFirst;
    //标签项1
    private Integer labelItemFirst;
    //标签项类型1
    private Integer labelTypeSecond;
    //标签项1
    private Integer labelItemSecond;
    //标签项类型1
    private Integer labelTypeThird;
    //标签项1
    private Integer labelItemThird;

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

    public Integer getLabelTypeFirst() {
        return labelTypeFirst;
    }

    public void setLabelTypeFirst(Integer labelTypeFirst) {
        this.labelTypeFirst = labelTypeFirst;
    }

    public Integer getLabelItemFirst() {
        return labelItemFirst;
    }

    public void setLabelItemFirst(Integer labelItemFirst) {
        this.labelItemFirst = labelItemFirst;
    }

    public Integer getLabelTypeSecond() {
        return labelTypeSecond;
    }

    public void setLabelTypeSecond(Integer labelTypeSecond) {
        this.labelTypeSecond = labelTypeSecond;
    }

    public Integer getLabelItemSecond() {
        return labelItemSecond;
    }

    public void setLabelItemSecond(Integer labelItemSecond) {
        this.labelItemSecond = labelItemSecond;
    }

    public Integer getLabelTypeThird() {
        return labelTypeThird;
    }

    public void setLabelTypeThird(Integer labelTypeThird) {
        this.labelTypeThird = labelTypeThird;
    }

    public Integer getLabelItemThird() {
        return labelItemThird;
    }

    public void setLabelItemThird(Integer labelItemThird) {
        this.labelItemThird = labelItemThird;
    }

    public CountSearchDto(Integer gender, Integer dicNation,
                          Integer dicMciType, Integer userType,
                          Integer labelTypeFirst, Integer labelItemFirst,
                          Integer labelTypeSecond, Integer labelItemSecond,
                          Integer labelTypeThird, Integer labelItemThird) {
        this.gender = gender;
        this.dicNation = dicNation;
        this.dicMciType = dicMciType;
        UserType = userType;
        this.labelTypeFirst = labelTypeFirst;
        this.labelItemFirst = labelItemFirst;
        this.labelTypeSecond = labelTypeSecond;
        this.labelItemSecond = labelItemSecond;
        this.labelTypeThird = labelTypeThird;
        this.labelItemThird = labelItemThird;
    }
}
