package com.pandawork.crm.common.exception;

import com.pandawork.core.common.exception.IBizExceptionMes;

/**
 * PartyException
 *
 * @author: zhangteng
 * @time: 15/10/10 下午9:40
 */
public enum PartyException implements IBizExceptionMes {

    SystemException("系统内部异常!", 1),
    UploadLicenceFailed("上传注册文件失败", 2),

    PermissionInsertFailed("添加权限失败", 1020),
    PermissionQueryFailed("查询权限失败", 1021),
    PermissionUpdateFailed("更新权限失败", 1022),
    PermissionDeleteFailed("删除权限失败", 1023),
    PermissionExpressionNotNull("权限表达式不能为空!", 1001),
    PermissionExpressionExist("权限表达式已存在!", 1002),
    PermissionIdNotNull("权限ID不能为空!", 1003),
    PermissionNotExist("权限不存在!", 1019),

    UserIdNotNull("用户ID不能为空!", 1004),
    LoginNameNotNull("登录名不能为空!", 1009),
    PasswordNotNull("密码不能为空!", 1010),
    AccountTypeNotNull("账户类型不能为空!", 1011),
    UserStatusIllegal("用户状态不合法!", 1012),
    UserNotExist("用户不存在!", 1016),
    UserDisabled("用户被禁用!", 1017),
    UserNotNull("用户不能为空",1018),
    WaiterLoginFail("用户登录失败",1019),
    BarLoginFail("吧台登录失败",1020),

    SecurityGroupInsertFailed("添加安全组失败", 1025),
    SecurityGroupQueryFailed("查询安全组失败", 1026),
    SecurityGroupUpdateFailed("更新安全组失败", 1027),
    SecurityGroupDeleteFailed("删除安全组失败", 1028),
    SecurityGroupPermissionInsertFailed("添加安全组-权限失败", 1029),
    SecurityGroupPermissionQueryFailed("查询安全组-权限失败", 1030),
    SecurityGroupPermissionUpdateFailed("更新安全组-权限失败", 1031),
    SecurityGroupPermissionDeleteFailed("删除安全组-权限失败", 1032),
    SecurityGroupIdNotNull("安全组ID不能为空!", 1005),
    SecurityGroupNameNotNull("安全组名称不能为空!", 1006),
    SecurityGroupNameIsExist("安全组名称已存在!", 1007),
    SecurityGroupNotExist("安全组不存在", 1037),
    QuerySecurityGroupByUserIdFail("根据userId获取安全组失败", 1038),
    QueryNotSelectedPermissionFailed("获取为选择的权限列表失败", 1024),

    PartyIdNotNull("当时ID不能为空!", 1008),

    SubjectKeyNotNull("T票的key不能为空!", 1013),
    SubjectNotNull("T票不能为空!", 1014),
    LoginTokenNotNull("登陆token不能为空!", 1015),
    LoginNameOrPasswordNotCorrect("用户名或密码不正确!", 1016),

    UserGroupInsertFailed("添加用户-安全组失败", 1033),
    UserGroupQueryFailed("查询用户-安全组失败", 1034),
    UserGroupUpdateFailed("更新用户-安全组失败", 1035),
    UserGroupDeleteFailed("删除用户-安全组失败", 1036),

    /****************会员组*****************/
    MemberGroupInsertFailed("添加会员组失败", 1037),
    MemberGroupQueryFailed("查询会员组失败", 1038),
    MemberGroupNameIsExist("会员组名称已存在!", 1039),
    MemberGroupDeleteFailed("删除会员组失败", 1040),
    MemberGroupUpdateFailed("更新会员组失败", 1041),
    MemberGroupIdNotNull("会员组ID不能为空!", 1042),
    MemberGroupNameNotNull("会员组名称不能为空!", 1043),
    MemberGroupQueryIsNotDeleted("查询未被删除的会员组信息失败!", 1044),


    //字典管理模块
    NewDictionaryFail("新增字典值失败",1100),
    ListDictionaryFail("字典值列表查询失败",1101),
    DelDictionaryFail("删除字典值失败",1102),
    UpdateDictionaryFail("修改字典值失败",1103),
    DictionaryNameIsNull("字典值名称是否为空",1104),
    DictionaryIdError("字典值id错误",1105),
    DictionaryNameIsExist("字典值名称已经存在",1106),
    QueryDictionaryFail("查询字典值失败",1107),
    ListAllDictionaryFail("查询所有字典值列表失败",1108),
    DictionaryIsParent("该字典值为父类字典值无法删除",1109),
    DictionaryIsSystem("该字典值为系统配置无法删除",1110)
    ;

    private String msg;

    private int code;

    PartyException(String msg, int code) {
        this.msg = msg;
        this.code = code;
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
