<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>用户信息详情</title>
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
                    <form class="form-horizontal"  action="" method="">
                        <div class="panel panel-info">
                            <div class="panel-heading">
                                <h4>用户详情</h4>
                            </div>
                            <div class="panel-body">
                                <input type="hidden" name="id" value="${employee.partyId}" >
                                <div class="col-sm-6">
                                    <div class="form-group">
                                        <label class="col-sm-4 control-label">真实姓名</label>
                                        <div class="col-sm-6">
                                            <input class="w180" type="text" name="name" readonly="readonly" value="${employee.name}"/>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-sm-4 control-label">账号</label>
                                        <div class="col-sm-6">
                                            <input class="w180 J_loginName" type="text" readonly name="loginName" value="${employee.loginName}"/>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-sm-4 control-label">身份证号码</label>
                                        <div class="col-sm-6">
                                            <input class="w180" type="text" name="idcardNum" readonly="readonly" value="${employee.idcardNum}"/>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-sm-4 control-label">用户电话</label>
                                        <div class="col-sm-6">
                                            <input class="w180" type="text" name="phone" readonly="readonly" value="${employee.phone}"/>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-sm-4 control-label">用户邮箱</label>
                                        <div class="col-sm-6">
                                            <input class="w180" type="text" name="email" readonly="readonly" value="${employee.email}"/>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-sm-4 control-label">所属部门</label>
                                        <div class="col-sm-6">
                                            <input class="w180" type="text" name="department" readonly="readonly" value="${employee.department}"/>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-sm-4 control-label">当前职位</label>
                                        <div class="col-sm-6">
                                            <input class="w180" type="text" name="position" readonly="readonly" value="${employee.position}"/>
                                        </div>
                                    </div>
                                </div>
                                <!-- 右侧内容start -->
                                <div class="col-sm-6">
                                    <div class="form-group">
                                        <label class="col-sm-3 control-label">直接上级</label>
                                        <div class="col-sm-6">
                                            <input class="w180" type="text" name="directSuperior" readonly="readonly" value="${employee.immediateS}"/>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-sm-3 control-label">密码</label>
                                        <div class="col-sm-6">
                                            <input class="w180 J_password" type="password" readonly name="password" value="${employee.password}"/>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-sm-3 control-label">创建时间</label>
                                        <div class="col-sm-6">
                                            <input class="w180" type="text" name="registrationTime" readonly="readonly"
                                                   value="<fmt:formatDate value="${employee.createdTime}" type="both"></fmt:formatDate>" />
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-sm-3 control-label">操作账号</label>
                                        <div class="col-sm-6">
                                            <input class="w180" type="text" name="operationAccount" readonly="readonly" value="${employee.accountValue}"/>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-sm-3 control-label">账号类型</label>
                                        <div class="col-sm-6">
                                            <input class="w180" type="text" name="accountType" readonly="readonly"
                                            <c:if test="${employee.userType=='1'}">value = "超级管理员"</c:if>
                                            <c:if test="${employee.userType=='2'}">value = "系统用户"</c:if>
                                            />
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-sm-3 control-label">用户状态</label>
                                        <div class="col-sm-6">
                                            <input class="w180" type="text" name="userStatus" readonly="readonly"
                                                   <c:if test="${employee.status=='1'}">value = "启用"</c:if>
                                                   <c:if test="${employee.status=='2'}">value = "禁用"</c:if>
                                            />

                                        </div>
                                    </div>
                                    <c:if test="${employee.groupId != null}">
                                        <div class="form-group">
                                            <label class="col-sm-3 control-label">管理会员组</label>
                                            <div class="col-sm-6">
                                                <input class="w180" type="text" name="memberGroup" readonly="readonly" value="${employee.memberGroupName}"/>
                                            </div>
                                        </div>
                                    </c:if>
                                </div>
                            </div>
                            <div class="panel-footer">
                                <div class="row">
                                    <div class="col-sm-6 col-sm-offset-5">
                                        <div class="btn-toolbar">
                                            <button class="btn-default btn" onclick="window.history.go(-1);" type="button"><i class="fa fa-undo"></i>&nbsp;返回上一页</button>
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
<script>
    $(function () {
        $('#Menu4,#logoMenu4').trigger('click');
    });
</script>
    <jsp:include flush="true" page="/WEB-INF/views/admin/common/footer.jsp"></jsp:include>
</body>
</html>