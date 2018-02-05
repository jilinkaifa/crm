<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="shiro" uri="http://shiro.pandawork.net/tags" %>
<html>
<head>
    <title>用户管理</title>
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
                    <%--<div class="alert alert-success J_tip" role="alert">刷新成功！</div>--%>
                </div>
                <div class="col-sm-12 margin-top--10">
                    <form class="form-horizontal J_searchForm">
                        <div class="panel panel-info">
                            <div class="panel-heading">
                                <h4>用户管理</h4>
                            </div>
                            <div class="panel-body">
                                <div class="form-group">
                                    <label class="col-sm-1 control-label">真实姓名
                                    </label>
                                    <div class="col-sm-2">
                                        <input class="form-control new200 J_name" type="text" name="name" value="" />
                                    </div>
                                    <div class="col-sm-3">
                                        <button type="submit"  class="btn J_search btn-success btn-radius-no margin-left-10"><i class="fa fa-search"></i>&nbsp;查询</button>
                                        <button type="reset" class="btn btn-success"><i class="fa fa-undo"></i>&nbsp;重置</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-sm-12 margin-bottom-10 margin-top--10 padding-left btn-toolbar">
                            <shiro:checkPermission name="Admin:SAdmin:User:Management:Employee:New">
                            <button class="btn btn-success btn-radius-no J_add"><i class="fa fa-plus"></i>&nbsp;新增</button>
                            </shiro:checkPermission>
                            <shiro:checkPermission name="Admin:SAdmin:User:Management:Employee:Update">
                            <a href="#" class="btn btn-success btn-radius-no" id="edit_model"><i class="fa fa-pencil"></i>&nbsp;批量修改</a>
                            </shiro:checkPermission>
                            <shiro:checkPermission name="Admin:SAdmin:User:Management:Employee:Delete">
                            <a href="#" class="btn btn-success btn-radius-no" id="del_model"><i class="fa fa-trash"></i>&nbsp;批量删除</a>
                            </shiro:checkPermission>
                            <shiro:checkPermission name="Admin:SAdmin:User:Management:Employee:SetRole">
                            <a href="#" class="btn btn-success btn-radius-no" id="allot_model"><i class="fa fa-user"></i>&nbsp;分配角色</a>
                            </shiro:checkPermission>
                        </div>
                    </form>
                </div>
                <div class="col-sm-12">
                    <div class="panel panel-info">
                        <div class="panel-body">
                            <form action="" class="J_form">
                                <div class="table-responsive">
                                    <table class="table table-hover table-bordered">
                                        <thead>
                                        <tr>
                                            <th rowspan="2"><input type="checkbox" id="checkAll" class="J_selectAll"></th>
                                            <th>真实姓名</th>
                                            <th>手机号码</th>
                                            <th>用户角色</th>
                                            <th>添加时间</th>
                                            <th>用户类型</th>
                                            <th>所属部门</th>
                                            <th>当前职位</th>
                                            <th>直属上级</th>
                                            <th>状态</th>
                                            <th>操作</th>
                                        </tr>
                                        </thead>
                                        <tbody id="J_template"></tbody>
                                    </table>
                                    <div class="pull-left record">
                                        共<span class="J_record"></span>条记录
                                    </div>
                                    <div class="pull-right"><ul id="pageLimit"></ul></div>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- 删除对话框 -->
<input class="hidId" type="hidden" />
<div class="modal fade" id="delDialog" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" >提示信息</h4>
            </div>
            <div class="modal-body">
                <p>确定删除该用户吗？</p>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-success J_delDlg">确定</button>
            </div>
        </div>
    </div>
</div>
<!--批量删除对话框 -->
<!-- <input class="hidId" type="hidden"></input> -->
<div class="modal fade" id="modalDel" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" >提示信息</h4>
            </div>
            <div class="modal-body">
                <p>请至少选择一项</p>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-success J_delAllDlg" data-dismiss="modal">确定</button>
            </div>
        </div>
    </div>
</div>
<!-- 批量删除对话框 -->
<input class="hidId" type="hidden"></input>
<div class="modal fade" id="modalAll" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" >提示信息</h4>
            </div>
            <div class="modal-body">
                <p>确定全部删除吗？</p>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-success J_delAllDlg" >确定</button>
            </div>
        </div>
    </div>
</div>
<!-- 操作失败对话框 -->
<div class="modal fade" id="modalDialog" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" >提示信息</h4>
            </div>
            <div class="modal-body">
                <p>操作失败，请重新操作!</p>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-success" >确定</button>
            </div>
        </div>
    </div>
</div>
<!-- 批量编辑对话框 -->
<div class="modal fade" id="editDialog" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" >提示信息</h4>
            </div>
            <div class="modal-body">
                <form action="" class="form-horizontal J_editForm">
                    <div class="form-group">
                        <label class="col-sm-3 control-label">用户类型</label>
                        <div class="col-sm-6">
                            <select class="form-control w180" id="customerType" >
                                <option value="">---请选择---</option>
                                <option value="1">超级管理员</option>
                                <option value="2">系统用户</option>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">所属部门</label>
                        <div class="col-sm-6">
                            <select class="form-control w180" id="department" >
                                <option value="">---请选择---</option>
                                <c:forEach var="department" items="${departments}">
                                    <option value="${department.id}">${department.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">当前职位</label>
                        <div class="col-sm-6">
                            <select class="form-control w180" id="position" >
                                <option value="">---请选择---</option>
                                <c:forEach var="position" items="${positions}">
                                    <option value="${position.id}">${position.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">直属上级</label>
                        <div class="col-sm-6">
                            <select class="form-control w180" id="lead" >
                                <option value="">---请选择---</option>
                                <c:forEach var="immediateS" items="${immediateSs}">
                                    <option value="${immediateS.id}">${immediateS.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-success J_editDlg">确定</button>
            </div>
        </div>
    </div>
</div>
<!-- 批量分配角色 -->
<div class="modal fade" id="allotDialog" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" >提示信息</h4>
            </div>
            <div class="modal-body">
                <form action="" class="form-horizontal J_editForm">
                    <div class="form-group">
                        <label class="col-sm-3 control-label">角色</label>
                        <div class="col-sm-6">
                            <select class="form-control w180 J_role" id="role" value="1">
                                <option value="">---请选择---</option>
                                <c:forEach var="role" items="${roles}">
                                    <option value="${role.id}">${role.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <div class="form-group hidden J_group" >
                        <label class="col-sm-3 control-label">分组</label>
                        <div class="col-sm-6">
                            <select class="form-control w180 J_memberGroup" id="memberGroup" value="1">
                                <option value="">---请选择---</option>
                                <c:forEach var="memberGroup" items="${memberGroups}">
                                    <option value="${memberGroup.id}">${memberGroup.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-success J_allotDlg">确定</button>
            </div>
        </div>
    </div>
</div>
<jsp:include flush="true" page="/WEB-INF/views/admin/common/footer.jsp"></jsp:include>
<script src="/js/authority-management/user-management.js"></script>
</body>
</html>