<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>新增用户信息</title>
    <jsp:include flush="true" page="/WEB-INF/views/admin/common/head.jsp"></jsp:include>
</head>
<body>
<jsp:include flush="true" page="/WEB-INF/views/admin/common/header.jsp"></jsp:include>
<div class="page">
<div class="holder">
<div class="container">
<!-- 头部 -->
    <div class="row">
        <div class="col-sm-12">
            <ol class="breadcrumb">
                <li>
                    <a href="/admin"><i class="fa fa-home"></i>&nbsp;首页</a>
                </li>
                <li>
                    <a href="/admin/party/security/group/list">系统管理</a>
                </li>
                <li class="active">用户添加</li>
            </ol>
        </div>
        <div class="col-sm-12 margin-top--10">
            <form class="form-horizontal J_tableForm" method="" action="" autocomplete="off">
                <div class="panel panel-info">
                    <div class="panel-heading">
                        <h4>添加</h4>
                    </div>
                    <div class="panel-body">
                        <input type="hidden" name='id' class="J_tableId" value="1"/>
                        <div class="container">
                            <!-- 左侧内容start -->
                            <div class="col-sm-6">
                                <div class="form-group">
                                    <label class="col-sm-4 control-label">
                                        <span class="requires">*</span>真实姓名
                                    </label>
                                    <div class="col-sm-6">
                                        <input class="w200 J_userName" type="text" name="name" value=""/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-4 control-label">
                                        <span class="requires">*</span>账号
                                    </label>
                                    <div class="col-sm-6">
                                        <input class="w200 J_loginName" type="text" name="loginName" value=""/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-4 control-label">
                                        <span class="requires"></span>邮箱
                                    </label>
                                    <div class="col-sm-6">
                                        <input class="w200 J_eMail" type="text" name="email" value=""/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-4 control-label">
                                        <span class="requires"></span>所属部门
                                    </label>
                                    <div class="col-sm-6">
                                        <select class="form-control J_tableArea w200" name="dicDepartment">
                                            <option value="">请选择...</option>
                                             <c:forEach var="department" items="${departments}">
                                                <option value="${department.id}">${department.name}</option>
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
                                            <option value="">请选择...</option>
                                            <c:forEach var="position" items="${positions}">
                                                <option value="${position.id}">${position.name}</option>
                                            </c:forEach>
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
                                        <input class="w200 J_phoneNum" type="text" name="phone" value=""/>
                                        <input class="" type="text" value="" style="width: 0px;height: 0px;padding: 0.1px;border: none;"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-3 control-label">
                                        <span class="requires">*</span>密码
                                    </label>
                                    <div class="col-sm-6">
                                        <input class="w200 J_password" type="password" name="password" value=""/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-3 control-label">
                                        <span class="requires"></span>身份证号码
                                    </label>
                                    <div class="col-sm-6">
                                        <input class="w200 J_tableName" type="text" name="idcardNum" value=""/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-3 control-label">
                                        <span class="requires"></span>直接上级
                                    </label>
                                    <div class="col-sm-6">
                                        <select class="form-control J_tableArea w200" name="dicImmediateS">
                                            <option value="">请选择...</option>
                                            <c:forEach var="immediateS" items="${immediateSs}">
                                                <option value="${immediateS.id}">${immediateS.name}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-3 control-label">
                                        <span class="requires"></span>账号类型
                                    </label>
                                    <div class="col-sm-6">
                                        <select class="form-control J_tableArea w200" name="userType">
                                            <option value="">请选择...</option>
                                            <option value="1">超级管理员</option>
                                            <option value="2">系统用户</option>
                                        </select>
                                    </div>
                                </div>
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
                                    <!-- <button class="btn-default btn" onclick="window.history.go(-1);" type="button">
                                    <i class="fa fa-undo"></i>&nbsp;返回</button> -->
                                    <button class="btn-default btn J_reset" type="reset">
                                        <i class="fa fa-undo"></i>&nbsp;重置
                                    </button>
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
<!-- 账号重复对话框 -->
<div class="modal fade" id="modalDialog" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">提示信息</h4>
            </div>
            <div class="modal-body">
                <p>该账号已存在，请重新填写!</p>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-success" data-dismiss="modal">确定</button>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript" src="/js/authority-management/user-management-add.js"></script>
</body>
</html>