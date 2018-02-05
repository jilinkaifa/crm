<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="shiro" uri="http://shiro.pandawork.net/tags" %>
<html>
<head>
    <title>进行中活动列表</title>
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
                            <a href="/admin/event/prepare/list">活动管理</a>
                        </li>
                        <li class="active">通知处理</li>
                    </ol>
                    <input type="hidden" class="clientId" value="${clientId}">
                </div>
                <shiro:checkPermission name="Admin:E-CRB:Event:Processing:BatchNotice">
                <div class="col-sm-12 ">
                    <div class="col-sm-12 margin-bottom-10 margin-top--10 padding-left">
                        <a href="#" class="btn btn-success btn-radius-no J_inform" data-toggle="modal" data-target="#addDialog">
                            <i class="fa fa-plus"></i>&nbsp;批量通知</a>
                    </div>
                </div>
                </shiro:checkPermission>
                <div class="col-sm-12">
                    <div class="panel panel-info">
                        <div class="panel-body">
                            <div class="table-responsive">
                                <table class="table table-hover table-bordered">
                                    <thead>
                                    <tr>
                                        <th><input type="checkbox" class="J_selectAll"></th>
                                        <th>活动名称</th>
                                        <th>活动类别</th>
                                        <th>活动级别</th>
                                        <th>本期开始时间</th>
                                        <th>通知内容</th>
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
                </div>
            </div>
        </div>
    </div>
</div>
<input class="hidId" type="hidden"></input>

<!-- 批量通知对话框 -->
<div class="modal fade" id="informDialog" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">结果记录</h4>
            </div>
            <div class="modal-body">
                <form action="" class="form-horizontal J_editForm">
                    <div class="form-group">
                        <label class="col-sm-3 control-label">状态</label>
                        <div class="col-sm-6">
                            <select id="editable-select1" class="form-control J_selectName w200" name="status">
                                <option value="1">未通知</option>
                                <option value="2">已通知</option>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">备注</label>
                        <div class="col-sm-6">
                            <textarea name="comment" class="form-control J_editRemInput" cols="30" rows="5"></textarea>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-success J_delAllDlg" data-dismiss="modal">确定</button>
            </div>
        </div>
    </div>
</div>

<!-- 选择为空对话框 -->
<div class="modal fade" id="delTipDialog" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">提示信息</h4>
            </div>
            <div class="modal-body">
                <p>请至少选择一个通知项!</p>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-success" data-dismiss="modal">确定</button>
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
<jsp:include flush="true" page="/WEB-INF/views/admin/common/footer.jsp"></jsp:include>
<script src="/js/e-crb/activities-management/activities-management-ing/activities-management-ing-inform.js"></script>
</body>
</html>