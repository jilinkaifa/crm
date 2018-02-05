package com.pandawork.crm.common.enums.other;

/**
 * ModuleEnums
 *
 * @author: zhangteng
 * @time: 15/10/16 上午10:42
 */
public enum ModuleEnums {

    Null(""),

    // 后台
    Admin("Admin"),

    // 后台首页
    AdminIndex("Admin:Index"),

    // 超级管理
    AdminSAdmin("Admin:SAdmin"),

    // 权限
    AdminParty("Admin:SAdmin:Party"),
    AdminPartySecurity("Admin:SAdmin:Party:Security"),

    // 字典值管理
    AdminPartyDictionary("Admin:SAdmin:Party:Dictionary"),
    AdminPartyDictionaryList("Admin:SAdmin:Party:Dictionary:List"),
    AdminPartyDictionaryNew("Admin:SAdmin:Party:Dictionary:New"),
    AdminPartyDictionaryUpdate("Admin:SAdmin:Party:Dictionary:Update"),
    AdminPartyDictionaryDelete("Admin:SAdmin:Party:Dictionary:Delete"),
    // 基础权限管理
    AdminPartySecurityPermission("Admin:SAdmin:Party:Security:Permission"),
    AdminPartySecurityPermissionList("Admin:SAdmin:Party:Security:Permission:List"),
    AdminPartySecurityPermissionNew("Admin:SAdmin:Party:Security:Permission:New"),
    AdminPartySecurityPermissionUpdate("Admin:SAdmin:Party:Security:Permission:Update"),
    AdminPartySecurityPermissionDelete("Admin:SAdmin:Party:Security:Permission:Delete"),
    // 安全组管理
    AdminPartySecurityGroup("Admin:SAdmin:Party:Security:Group"),
    AdminPartySecurityGroupList("Admin:SAdmin:Party:Security:Group:List"),
    AdminPartySecurityGroupNew("Admin:SAdmin:Party:Security:Group:New"),
    AdminPartySecurityGroupUpdate("Admin:SAdmin:Party:Security:Group:Update"),
    AdminPartySecurityGroupDel("Admin:SAdmin:Party:Security:Group:Del"),
    AdminPartySecurityGroupPermission("Admin:SAdmin:Party:Security:Group:Permission"),
    AdminPartySecurityGroupPermissionList("Admin:SAdmin:Party:Security:Group:Permission:List"),
    AdminPartySecurityGroupPermissionNew("Admin:SAdmin:Party:Security:Group:Permission:New"),
    AdminPartySecurityGroupPermissionDelete("Admin:SAdmin:Party:Security:Group:Permission:Delete"),
    //会员组管理
    AdminPartyMemberGroup("Admin:SAdmin:Party:Member:Group"),
    AdminPartyMemberGroupList("Admin:SAdmin:Party:Member:Group:List"),
    AdminPartyMemberGroupNew("Admin:SAdmin:Party:Member:Group:New"),
    AdminPartyMemberGroupUpdate("Admin:SAdmin:Party:Member:Group:Update"),
    AdminPartyMemberGroupDel("Admin:SAdmin:Party:Member:Group:Del"),

    //积分转换管理
    AdminPartyPointsConvert("Admin:SAdmin:Party:PointsConvert"),
    AdminPartyPointsConvertList("Admin:SAdmin:Party:PointsConvert:List"),
    AdminPartyPointsConvertUpdate("Admin:SAdmin:Party:PointsConvert:Update"),

    // 用户管理
    AdminUserManagement("Admin:SAdmin:User:Management"),

    // 员工管理
    AdminUserManagementEmployee("Admin:SAdmin:User:Management:Employee"),
    AdminUserManagementEmployeeList("Admin:SAdmin:User:Management:Employee:List"),
    AdminUserManagementEmployeeUpdate("Admin:SAdmin:User:Management:Employee:Update"),
    AdminUserManagementEmployeeDelete("Admin:SAdmin:User:Management:Employee:Delete"),
    AdminUserManagementEmployeeNew("Admin:SAdmin:User:Management:Employee:New"),
    AdminUserManagementEmployeeDetail("Admin:SAdmin:User:Management:Employee:Detail"),
    AdminUserManagementEmployeeSetRole("Admin:SAdmin:User:Management:Employee:SetRole"),

    // 全局设置
    AdminConstant("Admin:Constant"),
    AdminConstantList("Admin:Constant:List"),
    AdminConstantUpdate("Admin:Constant:Update"),


    //患者基础信息管理
    AdminClientBasic("Admin:Client:Basic"),
    AdminClientBasicList("Admin:Client:Basic:List"),
    AdminClientBasicNew("Admin:Client:Basic:New"),
    AdminClientBasicDelete("Admin:Client:Basic:Delete"),
    AdminClientBasicUpdate("Admin:Client:Basic:Update"),
    AdminClientBasicDetail("Admin:Client:Basic:Detail"),
    AdminClientBasicAddMember("Admin:Client:Basic:AddMember"),

    //会员管理
    AdminClientMember("Admin:Client:Member"),
    AdminClientMemberList("Admin:Client:Member:List"),
    AdminClientMemberNew("Admin:Client:Member:New"),
    AdminClientMemberDelete("Admin:Client:Member:Delete"),
    AdminClientMemberUpdate("Admin:Client:Member:Update"),
    AdminClientMemberDetail("Admin:Client:Member:Detail"),

    //问卷管理
    AdminClientQuest("Admin:Client:Quest"),
    AdminClientQuestList("Admin:Client:Quest:List"),
    AdminClientQuestNew("Admin:Client:Quest:New"),
    AdminClientQuestDetail("Admin:Client:Quest:Detail"),

    //患者来访管理
    AdminClientVisit("Admin:Client:Visit"),
    AdminClientVisitList("Admin:Client:Visit:List"),
    AdminClientVisitDetailList("Admin:Client:Visit:DetailList"),
    AdminClientVisitDetailDelete("Admin:Client:Visit:DetailDelete"),
    AdminClientVisitDetailNew("Admin:Client:Visit:DetailNew"),
    AdminClientVisitDetailUpdate("Admin:Client:Visit:DetailUpdate"),

    //患者积分管理
    AdminClientPoints("Admin:Client:Points"),
    AdminClientPointsList("Admin:Client:Points:List"),
    AdminClientPointsDetailList("Admin:Client:Points:DetailList"),

    /***********************客户画像模块*********************/

    //标签类型
    AdminProfileLabelType("Admin:Profile:Label:Type"),
    AdminProfileLabelTypeList("Admin:Profile:Label:Type:List"),
    AdminProfileLabelTypeNew("Admin:Profile:Label:Type:New"),
    AdminProfileLabelTypeUpdate("Admin:Profile:Label:Type:Update"),
    AdminProfileLabelTypeDelete("Admin:Profile:Label:Type:Delete"),

    //标签项
    AdminProfileLabel("Admin:Profile:Label"),
    AdminProfileLabelItem("Admin:Profile:Label:Item"),
    AdminProfileLabelItemList("Admin:Profile:Label:Item:List"),
    AdminProfileLabelItemNew("Admin:Profile:Label:Item:New"),
    AdminProfileLabelItemUpdate("Admin:Profile:Label:Item:Update"),
    AdminProfileLabelItemDelete("Admin:Profile:Label:Item:Delete"),

    //画像管理
    AdminLabelType("Admin:Label:Type"),
    AdminLabelTypeList("Admin:Label:Type:List"),
    AdminLabelTypeNew("Admin:Label:Type:New"),
    AdminLabelTypeUpdate("Admin:Label:Type:Update"),
    AdminLabelTypeDelete("Admin:Label:Type:Delete"),

    //患者画像
    AdminProfilePortrayal("Admin:Profile:Portrayal"),
    AdminProfilePortrayalList("Admin:Profile:Portrayal:List"),
    AdminProfilePortrayalNew("Admin:Profile:Portrayal:New"),
    AdminProfilePortrayalUpdate("Admin:Profile:Portrayal:Update"),

    //行为分析
    AdminProfileAction("Admin:Profile:Action"),
    AdminProfileActionList("Admin:Profile:Action:List"),


    //统计分析
    AdminProfileStatistics("Admin:Profile:Statistics"),

    /***********************客户关怀模块*********************/
    //客户关怀
    AdminECRB("Admin:E-CRB"),
    
    //活动管理
    AdminECRBEvent("Admin:E-CRB:Event"),
    
    //待开展活动
    AdminECRBEventPrepare("Admin:E-CRB:Event:Prepare"),
    AdminECRBEventPrepareList("Admin:E-CRB:Event:Prepare:List"),
    AdminECRBEventPrepareNew("Admin:E-CRB:Event:Prepare:New"),
    AdminECRBEventPrepareUpdate("Admin:E-CRB:Event:Prepare:Update"),
    AdminECRBEventPrepareDetail("Admin:E-CRB:Event:Prepare:Detail"),
    AdminECRBEventPrepareLogout("Admin:E-CRB:Event:Prepare:Logout"),
    //AdminECRBEventPrepareApproval("Admin:E-CRB:Prepare:Approval"),
    AdminECRBEventPrepareApproval("Admin:E-CRB:Event:Prepare:Approval"),

    //进行中活动
    AdminECRBEventProcessing("Admin:E-CRB:Event:Processing"),
    AdminECRBEventProcessingList("Admin:E-CRB:Event:Processing:List"),
    AdminECRBEventProcessingDetail("Admin:E-CRB:Event:Processing:Detail"),
    AdminECRBEventProcessingPause("Admin:E-CRB:Event:Processing:Pause"),
    AdminECRBEventProcessingBatchNotice("Admin:E-CRB:Event:Processing:BatchNotice"),
    AdminECRBEventProcessingBatchPoints("Admin:E-CRB:Event:Processing:BatchPoints"),
    AdminECRBEventProcessingHandle("Admin:E-CRB:Event:Processing:Handle"),
    AdminECRBEventProcessingRecord("Admin:E-CRB:Event:Processing:Record"),

    //已归档活动
    AdminECRBEventArchived("Admin:E-CRB:Event:Archived"),
    AdminECRBEventArchivedList("Admin:E-CRB:Event:Archived:List"),
    AdminECRBEventArchivedDetail("Admin:E-CRB:Event:Archived:Detail"),
    AdminECRBEventArchivedDetailNotice("Admin:E-CRB:Event:Archived:Notice"),

    //模板管理
    AdminECRBTemplate("Admin:E-CRB:Template"),
    AdminECRBTemplateList("Admin:E-CRB:Template:List"),
    AdminECRBTemplateNew("Admin:E-CRB:Template:New"),
    AdminECRBTemplateUpdate("Admin:E-CRB:Template:Update"),
    AdminECRBTemplateDetail("Admin:E-CRB:Template:Detail"),
    AdminECRBTemplateDelete("Admin:E-CRB:Template:Delete"),

    //待办任务
    AdminECRBPrepare("Admin:E-CRB:Prepare"),
    //待办事务
    AdminECRBPrepareAffair("Admin:E-CRB:Prepare:Affair"),
    AdminECRBPrepareAffairList("Admin:E-CRB:Prepare:Affair:List"),

    //待办活动通知
    AdminECRBPrepareNotice("Admin:E-CRB:Prepare:Notice"),
    AdminECRBPrepareNoticeList("Admin:E-CRB:Prepare:Notice:List"),
    AdminECRBPrepareNoticeDetail("Admin:E-CRB:Prepare:Notice:Detail"),

    //待办问卷管理
    AdminECRBPrepareQuest("Admin:E-CRB:Prepare:Quest"),
    AdminECRBPrepareQuestList("Admin:E-CRB:Prepare:Quest:List")
    ;

    private String name;

    ModuleEnums(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
