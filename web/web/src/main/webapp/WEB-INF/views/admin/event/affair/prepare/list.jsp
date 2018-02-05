<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>待办事务</title>
    <jsp:include flush="true" page="/WEB-INF/views/admin/common/head.jsp"></jsp:include>
</head>
<body>
<jsp:include flush="true" page="/WEB-INF/views/admin/common/header.jsp"></jsp:include>
<div class="page clearfix">
    <div class="holder">
        <div class="container">
            <div class="row">
                <div class="col-sm-12">
                    <input type="hidden" class="pageDataCount" name="pageDataCount" value="${dataCount}">
                    <ol class="breadcrumb">
                        <li>
                            <a href="/admin"><i class="fa fa-home"></i>&nbsp;首页</a>
                        </li>
                        <li>
                            <a href="/admin/event/prepare/list">客户关怀</a>
                        </li>
                        <li class="active">待办事务</li>
                    </ol>
                </div>
                <div class="col-sm-12 margin-top--10">
                    <div class="panel panel-info">
                        <div class="panel-heading">
                            <h4>事务列表</h4>
                        </div>
                        <div class="panel-body">
                            <div class="table-responsive">
                                <table class="table table-hover table-bordered">
                                    <thead>
                                    <tr>
                                        <th>序号</th>
                                        <th>活动名称</th>
                                        <th>创建日期</th>
                                        <th>类型</th>
                                        <th class="control-width width3">内容</th>
                                        <th>操作</th>
                                    </tr>
                                    </thead>
                                    <tbody id="J_template"></tbody>
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
<!-- 存储id -->
<input class="hidId" type="hidden" value=""></input>
<!-- 处理对话框 -->
<div class="modal fade" id="approveDialog" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">活动审批</h4>
            </div>
            <div class="modal-body">
                <form action="" class="form-horizontal J_editForm">
                    <div class="form-group">
                        <label class="col-sm-4 control-label">审批</label>
                        <div class="col-sm-6">
                            <select class="form-control w180  J_examineApprove" name ="examineApprove" value="1">
                                <option value="0">请选择</option>
                                <option value="2">通过</option>
                                <option value="3">驳回</option>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-4 control-label">备注</label>
                        <div class="col-sm-6">
                            <textarea class="form-control w180 J_remarks" name ="remaks" value="2"></textarea>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-success J_approveDlg">确定</button>
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
<script src="/js/e-crb/backlog-management/backlog-management-list.js"></script>
</body>
</html>
