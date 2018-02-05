<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.pandawork.net/tags" %>
<html>
<head>
    <title>角色权限管理</title>
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
                            <a href="#"><i class="fa fa-home"></i>&nbsp;首页</a>
                        </li>
                        <li>
                            <a href="#">系统管理</a>
                        </li>
                        <li class="active">角色权限管理</li>
                    </ol>
                    <%--<div class="alert alert-success J_tip" role="alert">刷新成功!</div>--%>
                </div>
                <div class="col-sm-12">
                    <form action="" class="J_form">
                        <div class="panel panel-info">
                            <div class="panel-heading">
                                <h4>角色名称：${securityGroup.name}</h4>
                                <input type="hidden" class="J_id" value="${securityGroup.id}">
                            </div>
                            <div class="panel-body">
                                <shiro:checkPermission name="Admin:SAdmin:Party:Security:Group:Permission:New">
                                <div class="margin-left--15">
                                    <input type="hidden" value="1" name="id">
                                    <div class="col-sm-3">
                                        <select name="permissionId" class="form-control J_select">
                                            <option value="-1">请选择</option>
                                            <c:forEach var="item" items="${noSelectedPermissionList}">
                                                <option value="${item.id}">${item.description}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                                <a href="#" class="btn btn-success margin-bottom-15 J_add"><i class="fa fa-plus"></i>&nbsp;添加权限</a>
                                </shiro:checkPermission>
                                <div class="table-responsive">
                                    <table class="table table-bordered table-hover">
                                        <thead>
                                        <tr>
                                            <th>表达式</th>
                                            <th>描述</th>
                                            <th>操作</th>
                                        </tr>
                                        </thead>
                                        <tbody id="J_template">

                                        </tbody>
                                    </table>
                                </div>
                                <div class="pull-left record">
                                    共<span class="J_record"></span>条记录
                                </div>
                                <div class="pull-right">
                                    <ul id="pageLimit"></ul>
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
<!-- 存储id，表达式，描述 -->
<input class="hidId" type="hidden">
<!-- <input class="hidExp" type="hidden"></input>
<input class="hidDes" type="hidden"></input> -->
<!-- 操作失败对话框 -->
<div class="modal fade" id="modalDialog" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">提示信息</h4>
            </div>
            <div class="modal-body">
                <p>操作失败，请重新操作!</p>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-success" data-dismiss="modal">确定</button>
            </div>
        </div>
    </div>
</div>
<!-- 删除对话框 -->
<div class="modal fade" id="delDialog" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">提示信息</h4>
            </div>
            <div class="modal-body">
                <p>确定删除该权限吗？</p>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-success J_delDlg">确定</button>
            </div>
        </div>
    </div>
</div>
<!-- 添加对话框 -->
<div class="modal fade" id="addDialog" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">提示信息</h4>
            </div>
            <div class="modal-body">
                <p>请选择权限!</p>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-success J_addDlg" data-dismiss="modal">确定</button>
            </div>
        </div>
    </div>
</div>
<script src="/js/authority-management/security-group-authority-management.js"></script>
</body>
</html>