package com.pandawork.crm.common.exception;

import com.pandawork.core.common.exception.IBizExceptionMes;
import com.pandawork.crm.common.entity.event.CheckItemResult;

/**
 * 异常处理类
 * @Author: zhangteng
 * @Time: 2015/9/30 17:20.
 */
public enum CrmException implements IBizExceptionMes {
    SystemException("系统内部异常", 1),
    PhoneError("电话号码错误", 2),
    SendSmsError("发送短信失败", 3),

    ImageCompressedFail("图片压缩失败!", 1001),
    UploadFileNotNull("上传的文件不能为空!", 1002),
    UploadPathNotNull("上传的文件路径不能为空!", 1003),
    UploadDirCreateFail("上传文件夹创建失败!", 1004),
    UploadFileFail("文件上传失败", 1005),
    UploadNameNotNull("文件名称不能为空!", 1006),
    SerialNumTemplateNotNull("流水号模板不能为空", 1019),

    //用户管理模块
    QueryEmployeeInfoFail("获取员工信息失败",6001),
    InsertWaiterTableFail("添加服务员-餐台信息失败", 6002),
    PartyNotNull("添加当事人不能为空！",6003),
    UpdateUserPwdFail("修改员工密码失败",6004),
    UpdateEmployeeStateFail("修改员工状态失败",6005),
    QueryEmployeeException("查询员工信息失败",6007),
    EmployeeIsActivity("员工处于激活状态，请先将它转为未激活！",6008),
    DeleteEmployeeFail("删除员工信息失败",6009),
    PartyIdError("当事人ID为空或者小于0", 6010),
    EmployeeNameNotNull("员工姓名不能为空",6011),
    EmployeeNumberNotNull("员工编号不能为空",6012),
    CheckEmployeeNumberFail("检查员工编号重名失败",6013),
    CheckEmployeePhoneFail("检查员工电话重名失败",6014),
    CheckLoginNameFail("检查登录名重名失败",6015),
    OldPasswordWrong("原密码错误", 6016),
    PasswordNotEqual("两遍密码不一致", 6017),
    PasswordWrong("密码错误", 6018),
    EmployeeStatusNotNull("员工状态不能为空",6019),

    ListAllEmployeeNotDeleteByPageFail("根据页码获取未删除的系统用户失败",6020),
    QueryByEmployeeIdFail("根据id获取系统用户失败",6021),
    UpdateByEmployeePartyIdFail("根据partyId修改用户信息失败",6022),
    QueryNameByPartyIdFail("根据partyId查询用户姓名失败",6023),
    QueryByPartyIdFail("根据partyId查询用户失败",6024),
    ListEmployeeByIdFail("根据id获取系统用户失败",6025),
    DelByEmployeeIdFail("根据id将employee逻辑删除失败",6026),
    CreatedPartyIdError("创建者Id错误",6027),
    QueryEmployeeListByNameFail("根据name模糊查询系统用户失败",6028),
    GetGroupIdByPartyIdFail("根据partyId获取会员组id失败",6029),
    SetExtraEmployeeDataFail("设置系统用户枚举值失败",6030),
    CountEmployeeListByNameFail("根据条件获取系统用户数目失败",6031),
    DelRoleByIdFail("根据用户id删除已分配角色失败",6032),
    ListImmediateSByPartyIdFail("根据partyId获取直接上级失败",6033),
    QueryEmployeeByPartyIdWithUsingFail("根据partyId获取在用状态系统用户失败",6034),
    ListUnderlingsByPartyIdFail("根据partyId获取所有下级失败",6035),
    QuerySecurityGroupByPartyIdFail("根据partyId获取失败",6036),

    /*******************患者管理模块************************/
    //基本信息管理
    NewClientFailed("新增患者失败",7101),
    UpdateClientFailed("修改患者信息失败",7102),
    DelClientFailed("删除患者失败",7103),
    ListClientFailed("获取患者列表失败",71014),
    AddMemberFailed("加入会员失败",7104),
    ClientNameIsNull("患者姓名不能为空",7105),
    ClientIdError("患者id错误",7106),
    ClientTelIsNull("患者联系方式不能为空",7107),
    QueryClientFailed("查询患者失败",7108),
    IdCardNumIsExist("身份证号已经存在",7109),
    ClientIsMemberIsNull("是否会员不能为空",7110),
    MemberGroupIdsNull("会员所属分组不能为空",7111),
    OffsetAndPageSizeError("偏移量或页码错误",7112),
    CountClientFailed("统计患者数量失败",7113),
    IdCardNumExistCannotDelete("填写身份证号的患者不能删除",7114),
    UpdateAllPointsAndMemberPointsFailed("更新患者积分与累计积分失败",7115),
    DownLoadTemplateFailed("下载模板失败",7116),
    MemberIdCardIsNotNull("会员身份证号不能为空",7117),

    //会员管理
    UpdateMemberFailed("修改会员信息失败",7321),
    ListMemberFailed("查询会员失败",7114),
    UpdateMemberStatusFailed("修改会员状态失败",7115),
    CountMemberByGroupIdFailed("根据会员组id统计会员数量失败",7322),
    CountMemberFailed("统计会员数量失败",7323),
    CountRecordIdFailed("统计会员档案号相同数量失败",7325),
    RecordIdIsExit("统计会员档案号已经存在",7326),
    ClearPointsFailed("清空会员积分失败",7327),

    //问卷管理
    NewQuestItemFailed("添加问卷项失败",7116),
    QuestItemIsNotNull("问卷详情不为空",7117),
    QuestItemIdError("问卷项字典值id错误",7118),
    QuestIdError("问卷id错误",7119),
    ListQuestItemFailed("查询问卷详情列表失败",7120),
    NewQuestFailed("添加问卷失败",7121),
    NewQuestDtoFailed("添加问卷dto失败",7122),
    CountBySearchDtoFailed("根据searchDto统计问卷项数量失败",7123),
    QueryQuestByClientFailed("根据患者id查询问卷失败",7124),
    CountByClientIdFailed("根据患者id统计问卷次数失败",7125),
    UpdateQuestCountFailed("更新问卷次数失败",7126),
    UpdateAllCostFailed("更新累计消费金额失败",7127),

    //来访信息管理
    QueryVisitFailed("查询来访信息失败",7150),
    QueryVisitByIdFailed("查询来访详情失败",7151),
    QueryVisitByVisitDetailSearchDto("关键字查询来访详情失败",7152),
    VisitIdError("来访id错误",7153),
    DelById("删除来访详情失败",7154),
    UpdateVisit("修改来访详情失败",7155),
    QueryVisitByClientIdFailed("根据患者id查询来访信息失败",7156),
    NewVisitFailed("新增来访详情失败",7157),
    OffsetOrPageSizeError("偏移量或页码错误",7158),
    VClientIdError("来访者id错误",7159),
    CountVisitFailed("去重统计数量错误",7160),
    DelByClientIdFailed("删除患者来访记录失败",7161),
    GetAllCostFailed("计算消费总金额失败",7162),
    ConvertFailed("金额转换为积分失败",7163),
    CountCostByDate("根据日期统计消费总计失败",7164),
    UpdatePointsConvertFailed("修改积分转换比率失败",7165),
    PointsConvertIsNotNull("积分转换比率不可为空",7166),
    PointsIConvertIdError("积分转换比率id错误",7167),



    //积分管理
    QueryPointsFailed("查询积分失败",7200),
    NewPointsFailed("新增积分失败",7201),
    DelPointsFailed("删除积分失败",7202),
    CountPointsFailed("计算积分条数失败",7203),
    QueryByVisitFailed("根据来访查询积分失败",7204),
    UpdatePointsFailed("修改积分失败",7205),
    PointsIdError("积分记录id错误",7206),



    /*******************画像管理模块************************/
    //标签类型管理
    QueryLabelTypeFail("获取标签类型失败",7001),
    InsertLabelTypeFail("新增标签类型失败",7002),
    DeleteLabelTypeFail("删除标签类型失败",7003),
    UpdateLabelTypeFail("修改标签类型失败",7004),
    LabelTypeIdError("标签类型id错误",7005),
    LabelTypeNameNotNull("标签类型名称不能为空",7006),
    LabelTypeNameIsExist("标签类型名称已经存在",7007),
    QueryLabelTypeByNameFail("根据标签类型名称查询失败",7008),

    //患者画像
    InsertProfileFail("添加患者画像失败", 8001),
    ClientIdNotNull("患者id不能为空", 8002),
    LabelItemIdNotNull("标签项id不能为空", 8003),
    LabelTypeIdNotNull("标签类型id不能为空", 8004),
    LabelAllIdNotNull("标签的所有id不能为空", 8005),
    ProfileIsExist("患者画像已经存在",8006),
    QueryProfileByClientIdFail("根据患者id查询画像信息失败",8007),
    QueryProfileByItemIdFail("根据标签项id查询画像信息失败",8008),


    QueryLabelItemFail("获取标签项失败",8007),
    InsertLabelItemFail("新增标签项失败",8008),
    DeleteLabelItemFail("删除标签项失败",8009),
    UpdateLabelItemFail("修改标签项失败",8010),
    LabelItemIdError("标签项id错误",8011),
    QueryLabelItemTextFail("获取标签项简略失败",8012),
    QueryLabelItemByNameFail("根据标签项名称查询失败",8013),
    LabelItemNameNotNull("标签项名称不能为空",8014),
    LabelItemNameIsExist("标签项名称已经存在",8015),

    //统计分析
    QueryAnalysisResultFail("查询行为分析结果失败", 8101),
    AnalysisResultIdNotNull("行为分析结果id不能为空", 8102),
    NewAnalysisResultFail("新增行为分析结果失败", 8103),
    AnalysisResultNotNull("行为分析不能为空", 8104),


    /*******************客户关怀模块************************/
    //活动
    QueryCarriedOutEventInfoFail("获取待开展活动信息失败",9001),
    QueryCarriedOutEventByIdFail("根据id获取待开展活动详情失败",9002),
    InsertNewEventFail("新建活动失败", 9003),
    EventNameIsExist("活动名称是已经存在", 9004),
    EventIdNotNull("活动id不能为空", 9005),
    UpdateEventFail("修改活动信息失败", 9006),
    QueryNotLogoutEventFail("获取所有未被注销的活动信息失败",9007),
    EventStatusNotNull("活动状态不能为空", 9008),
    UpdateEventStatusFail("修改活动状态失败", 9009),
    AutoLogoutEventFail("自动注销活动失败", 9010),
    QueryNotAdoptEventFail("获取所有未被注销的活动信息失败",9011),
    SQlWithoutNotAdoptEvent("数据库中没有审核未通过的活动",9012),
    LogoutEventFail("手动注销活动失败", 9013),
    QueryBySearchDtoFail("根据SearchDto查询失败",9014),
    CountBySearchDtoFail("根据SearchDto查询数量失败",9015),
    QueryArchivedEventByIdFail("根据SearchDto查询数量失败",9016),
    CheckIsCanLogoutByIdFail("根据id查询能否注销失败",9017),
    EventHaveLogout("活动已经被注销过了",9018),
    AdoptEventCanNotLogout("审批通过的活动不可被注销",9019),
    PartyCanNotLogoutEvent("此用户不可以注销此活动",9020),
    ApprovalPartyIdNotNull("审批人id不能为空", 9021),
    InsertNewAttachmentFail("添加活动附件失败", 9022),
    EventNameNotNull("活动名称不能为空", 9023),
    TemplateIdNotNull("模板id不能为空", 9024),
    UpdateTemplateStatusFail("修改模板状态失败", 9025),
    SetExtraDataFail("设置活动其他枚举值属性失败", 9026),
    QueryNotLogoutAndNotAdoptEventFail("获取所有未注销和未审核通过的活动失败", 9027),
    AutoCreateEventRecordNoticeAndEventTermFail("自动生成活动记录通知和本期活动记录失败", 9028),

    //活动记录通知
    NewEventRecordNoticeFail("新增活动记录通知失败",9101),
    QueryByEventRecordNoticeFail("根据id获取活动记录通知失败",9102),
    CountByEventIdFail("获取该活动下的参与人员记录总数目失败",9103),
    SetEventRecordNoticeExtraDataFail("设置活动记录通知枚举值失败",9104),
    ListNameTop20ByEventTermIdFail("根据参与人名模糊查询前20条名称失败",9105),
    ListIdcardTop20ByEventTermIdFail("根据参与人idcard模糊查询前20条idcard失败",9106),

    //检查项
    ListAllCheckItemFail("获取检查项列表失败",9101),
    ListByEventIdCheckItemFail("根据活动id获取检查项列表失败",9102),
    NewCheckItemFail("新增检查项失败",9103),
    QueryByCheckItemIdFail("根据检查项id获取检查项失败",9104),
    CheckExistByCheckItemNameFail("检查检查项名称是否已存在失败",9105),
    DeleteCheckItemByEvenIdFail("根据活动id删除检查项失败",9106),

    //积分项
    PointsItemIsNotNull("积分项不能为空", 9801),
    NewPointsItemFail("添加积分项失败", 9802),
    ListPointsItemByEventIdFail("根据活动id获取所有的关联积分项失败", 9803),
    DeletePointsItemByEventIdFail("根据活动id删除所有的关联积分项失败", 9804),

    //检查项结果
    ListAllCheckItemResultFail("获取检查项结果列表失败",9201),
    ListByEventRecordNoticeIdCheckItemResultFail("根据活动通知记录id获取检查项结果列表失败",9202),
    NewCheckItemResultFail("新增检查项结果失败",9203),
    updateCheckItemResultFail("修改检查项结果失败",9204),
    CheckItemResultIdError("检查项结果Id为空或小于0",9205),
    EventRecordNoticeIdIsNull("活动通知记录id不能为空",9206),
    CheckItemIdIsNull("检查项id不能为空",9207),

    //已归档活动
    QueryEventArchivedFail("查询已归档活动失败",9301),
    QueryEventArchivedByIdFail("根据活动id获取已归档活动信息详情失败",9302),
    QueryEventArchivedNoticeByNameAndIdcardFail("根据患者姓名和身份证号获取活动通知记录失败",9302),
    QueryEventArchivedByPageFail("根据分页获取已归档活动失败",9303),
    QueryEventRecordNoticeByPageFail("根据分页获取活动记录通知失败",9304),
    QueryIdcardNumByIdcardNumFail("根据身份证号获取身份证号列表失败",9305),
    QueryOneEventTermByEventIdFail("根据身份证号获取身份证号列表失败",9306),

    //进行中活动
    CountProcessingEventFail("获取进行中活动数目失败",9401),
    ListProcessingEventFail("获取进行中活动列表失败",9402),
    ListProcessingEventBySearchDtoFail("根据查询条件获取进行中活动列表失败",9403),
    PauseTermFail("暂停一期活动失败",9404),
    PauseEventFail("暂停整个活动失败",9405),
    ListEventRecordNoticeByEventTermIdFail("根据该期活动id获取活动记录通知人员信息失败",9406),
    QueryEventTermById("根据id获取本期活动信息失败",9407),
    BatchNoticeByIdList("根据idList批量通知失败",9408),
    ListCountParticipantByIdList("根据活动记录通知id获取患者本期活动记录信息失败",9409),
    ListEventRecordNoticeByIdList("根据活动记录通知id获取患者本期活动记录信息失败",9410),
    CountByClientIdFail("计算患者其他活动通知信息数目失败",9411),
    HandleNoticeFail("处理活动记录通知失败",9412),
    QueryEventTermByEventIdFail("根据活动id获取所有期活动信息失败",9413),
    SetExtraValueFail("设置其他枚举值属性失败",9414),
    QueryEventTermByClientIdFail("根据患者id获取进入通知期但是未通知的活动列表失败",9415),
    CountEventTermByClientIdFail("根据患者id获取进入通知期但是未通知的活动列表数目失败",9416),
    CountToBeNoticedPersonFail("根据活动id获取该活动未通知人员数目失败",9417),
    CountToBeFinishedPersonFail("根据活动id获取该活动未完成人员数目失败",9418),
    totalPointsItemAndCheckItemFail("根据活动id获取关联积分项和检查项总数目失败",9419),
    ListEventByClientIdFail("根据患者id获取该患者参与的活动列表失败",9420),
    CountEventByClientIdFail("根据患者id获取该患者参与的活动数目失败",9421),
    CountParticipantByIdFail("根据活动通知id获取本期参与积分项和检查项数目失败",9421),
    CountAllParticipantFail("根据活动记录通知id获取所有期参与数目失败",9422),
    AllParticipantFail("根据活动记录通知id获取所有期参与情况失败",9423),
    ListGroupIdFail("根据组id获取列表信息失败",9424),
    AutoNextEventTermFail("根据上一期活动生成下一期活动失败",9425),
    ListTop20EmployeeByNameFail("根据name模糊搜索前20个创建人name失败",9426),
    ListTop20EmployeeByIdcardFail("根据idcard模糊搜索前20个创建人idcard失败",9427),


    //待办事务
    listAllToApprovalFail("查询待审批事务列表失败",9501),
    listAllRejectedFail("查询被驳回事务列表失败",9502),
    countRejectedFail("查询被驳回事务条数失败",9503),
    countToApprovalFail("查询待审批事务条数",9504),
    dateConvertFail("日期转换格式失败",9505),
    QueryEventByStatusFail("根据活动状态获取活动列表失败",9506),


    //活动记录积分项
    BatchNewPointsFail("批量添加积分项失败",9601),

    //待办事务通知
    ListPrepareNoticeFail("查询待办事务通知列表失败",9701),
    CountPrepareNoticeFail("查询待办事务通知数量失败",9702),

    //模板管理
    TemplateNameIsExist("模板名称是已经存在", 9901),
    QueryTemplateByIdFail("根据id获取模板详情失败",9902),
    InsertNewTemplateFail("新建活动失败", 9903),
    DelTemplateByIdFail("根据id删除活动失败", 9904),
    TemplateIdError("模板id错误", 9905),
    TemplateNameNotNull("模板名称不能为空", 9906),

    //行为分析
    queryIsMemberCountAllFail("查询患者类型数量失败",9801),
    queryDicMCITypeCountAllFail("查询医保类型数量失败",9802),
    queryGenderAllFail("查询性别种类失败",9803),
    queryDicNationNameAllFail("查询民族种类失败",9804),
    queryClientByActionSearchDtoFail("根据条件查询用户失败",9805),
    queryIsMemberCountByActionSearchDtoFail("根据条件查询用户类型数量失败",9806),
    queryDicMCITypeCountByActionSearchDtoFail("根据条件查询医保类型数量失败",9807),
    countByActionSearchDtoFail("根据条件查询总条数失败",9808),
    queryCountAllFail("查询总条数失败",9809),
    queryClientFail("查询用户失败",9810),
    listActionListDtoBySearchDtoFail("获取行为分析列表信息失败",9811),




    //文件上传下载
    AttachmentIdIsNull("附件id不能为空",9901),
    QueryAttachmentById("根据id获取附件失败",9902),


    //每期活动
    ListAllProcessingFail("获取所有进行中活动失败",10001),
    UpdateEventTermStatusFail("更新活动状态失败",10002),
    SearchEventTermByNameFail("根据活动名称模糊查询失败",10003),
    AutoUpdateEventTermStatusFail("自动修改本期活动状态失败", 10004)


    ;



    private String msg;

    private int code;

    CrmException(String msg, int code) {
        this.msg = msg;
        this.code = code;
    }

    CrmException(String msg) {
        this.msg = msg;
    }

    @Override
    public String getMes() {
        return msg;
    }

    @Override
    public int getCode() {
        return code;
    }
}
