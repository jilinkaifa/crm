<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<html>
<head>
    <title>用户信息编辑</title>
    <jsp:include flush="true" page="/WEB-INF/views/admin/common/head.jsp"></jsp:include>
</head>
<body>
<jsp:include flush="true" page="/WEB-INF/views/admin/common/header.jsp"></jsp:include>
<div class="page clearfix">
    <div class="holder">
        <div class="container">
            <div class="row">
                <div class="col-sm-12">
                    <ol class="breadcrumb">
                        <li>
                            <a href="/admin"><i class="fa fa-home"></i>&nbsp;首页</a>
                        </li>
                        <li>
                            <a href="/admin/party/security/group/list">系统管理</a>
                        </li>
                        <li class="active">用户管理</li>
                    </ol>
                </div>
                <div class="col-sm-12 margin-top--10">
                    <form class="form-horizontal J_tableForm"  action="" method="">
                        <div class="panel panel-info">
                            <div class="panel-heading">
                                <h4>用户信息编辑</h4>
                            </div>
                            <div class="panel-body">
                                <input type="hidden" name="id" value="${employee.id}" >
                                <input type="hidden" name="partyId" value="${employee.partyId}" >
                                <div class="container">
                                    <!-- 左侧内容start -->
                                    <div class="col-sm-6">
                                        <div class="form-group">
                                            <label class="col-sm-4 control-label">
                                                <span class="requires">*</span>真实姓名
                                            </label>
                                            <div class="col-sm-6">
                                                <input class="w200 J_userName" type="text" name="name" value="${employee.name}"/>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-sm-4 control-label">
                                                <span class="requires">*</span>账号
                                            </label>
                                            <div class="col-sm-6">
                                                <input class="w200 J_loginName" readonly type="text" name="loginName" value="${employee.loginName}"/>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-sm-4 control-label">
                                                <span class="requires"></span>邮箱
                                            </label>
                                            <div class="col-sm-6">
                                                <input class="w200 J_eMail" type="text" name="email" value="${employee.email}"/>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-sm-4 control-label">
                                                <span class="requires"></span>所属部门
                                            </label>
                                            <div class="col-sm-6">
                                                <select class="form-control J_tableArea w200" name="dicDepartment">
                                                    <option value="">请选择</option>
                                                    <c:forEach var="department" items="${departments}">
                                                        <option <c:if test='${department.id == employee.dicDepartment}'>selected="selected"</c:if>
                                                                value="${department.id}">${department.name}</option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-sm-4 control-label">
                                                <span class="requires"></span>当前职位
                                            </label>
                                            <div class="col-sm-6">
                                                <select class="form-control J_tableArea w200" name="dicPosition">
                                                    <option value="">请选择</option>
                                                    <c:forEach var="position" items="${positions}">
                                                        <option <c:if test='${position.id == employee.dicPosition}'>selected="selected"</c:if>
                                                                value="${position.id}">${position.name}</option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-sm-4 control-label">用户状态</label>
                                            <div class="col-sm-6">
                                                <select class="form-control J_tableArea w200"  name="status" id="status">
                                                    <option value="">请选择</option>
                                                    <option <c:if test="${employee.status == 1}">selected="selected"</c:if> value="1">启用</option>
                                                    <option <c:if test="${employee.status == 2}">selected="selected"</c:if> value="2">禁用</option>
                                                </select>
                                            </div>
                                        </div>
                                    </div>
                                    <!-- 右侧内容start -->
                                    <div class="col-sm-6">
                                        <div class="form-group">
                                            <label class="col-sm-3 control-label">
                                                <span class="requires">*</span>电话
                                            </label>
                                            <div class="col-sm-6">
                                                <input class="w200 J_phoneNum" type="text" name="phone" value="${employee.phone}"/>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-sm-3 control-label">
                                                密码
                                            </label>
                                            <div class="col-sm-6">
                                                <input class="w200 J_password" type="password" name="password" value="${employee.password}"/>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-sm-3 control-label">
                                                <span class="requires"></span>身份证号码
                                            </label>
                                            <div class="col-sm-6">
                                                <input class="w200 J_tableName" type="text" name="idcardNum" value="${employee.idcardNum}"/>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-sm-3 control-label">
                                                <span class="requires"></span>直接上级
                                            </label>
                                            <div class="col-sm-6">
                                                <select class="form-control J_tableArea w200" name="dicImmediateS">
                                                    <option value="">请选择</option>
                                                    <c:forEach var="immediateS" items="${immediateSs}">
                                                        <option <c:if test='${immediateS.id == employee.dicImmediateS}'>selected="selected"</c:if>
                                                                value="${immediateS.id}">${immediateS.name}</option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-sm-3 control-label">
                                                <span class="requires"></span>账号类型
                                            </label>
                                            <div class="col-sm-6">
                                                <select class="form-control w200"  name="userType" id="userType">
                                                    <option value="">请选择</option>
                                                    <option <c:if test="${employee.userType == 1}">selected="selected"</c:if> value="1">超级管理员</option>
                                                    <option <c:if test="${employee.userType == 2}">selected="selected"</c:if> value="2">系统用户</option>
                                                </select>
                                            </div>
                                        </div>
                                        <c:if test="${employee.groupId != null}">
                                            <div class="form-group">
                                                <label class="col-sm-3 control-label">管理会员组</label>
                                                <div class="col-sm-6">
                                                    <select class="form-control w200"  name="groupId" id="groupId">
                                                        <c:forEach var="memberGroup" items="${memberGroups}">
                                                            <option <c:if test="${employee.groupId == memberGroup.id}">selected="selected"</c:if> value="${memberGroup.id}">${memberGroup.name}</option>
                                                        </c:forEach>
                                                    </select>
                                                </div>
                                            </div>
                                        </c:if>
                                    </div>
                                </div>
                            </div>
                            <div class="panel-footer">
                                <div class="row">
                                    <div class="col-sm-2 col-sm-offset-5">
                                        <div class="btn-toolbar">
                                            <button class="btn-success btn J_save" type="submit">
                                                <i class="fa fa-save"></i>&nbsp;保存
                                            </button>
                                            <button class="btn-default btn" onclick="window.history.go(-1);" type="button">
                                            <i class="fa fa-undo"></i>&nbsp;返回</button>
                                            <!-- <button class="btn-default btn J_reset" type="reset">
                                                <i class="fa fa-undo"></i>&nbsp;重置
                                            </button> -->
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
    <jsp:include flush="true" page="/WEB-INF/views/admin/common/footer.jsp"></jsp:include>
<script type="text/javascript" src="/js/authority-management/user-management-edit.js"></script>
</body>
</html>