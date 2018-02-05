<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://shiro.pandawork.net/tags" prefix="shiro"%>
<!DOCTYPE html>
<html>
<head>
    <title>模板管理</title>
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
                            <a href="/admin/event/prepare/list">客户关怀</a>
                        </li>
                        <li class="active">模板管理</li>
                    </ol>
                    <%--<div class="alert alert-success J_tip" role="alert">刷新成功!</div>--%>
                    <input type="hidden" class="pageDataCount" value="${pageCount}">
                </div>
                <div class="col-sm-12 margin-top--10">
                    <form class="form-horizontal J_searchForm" autocomplete="off">
                        <div class="panel panel-info">
                            <div class="panel-heading">
                                <h4>搜索</h4>
                            </div>
                            <div class="panel-body">
                                <div class="form-group">
                                    <label class="col-sm-1 control-label">活动名称</label>
                                    <div class="col-sm-2">
                                        <input type="text" class="col-sm-2 form-control new185" name="name">
                                    </div>
                                    <label class="col-sm-1 control-label">模板创建人</label>
                                    <div class="col-sm-2">
                                        <input type="text" class="col-sm-2 form-control new185" name="createdPartyName">
                                    </div>
                                    <label class="col-sm-1 control-label">活动类型</label>
                                    <div class="col-sm-2">
                                        <select name="type" class="form-control new185">
                                            <option value="-1">请选择</option>
                                            <option value="1">会员关怀型</option>
                                            <option value="2">营销型</option>
                                        </select>
                                    </div>
                                    <label class="col-sm-1 control-label">活动级别</label>
                                    <div class="col-sm-2">
                                        <select class="form-control new185" name="level">
                                            <option value="-1">请选择</option>
                                            <option value="1">一级</option>
                                            <option value="2">二级</option>
                                            <option value="3">三级</option>
                                        </select>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-sm-12 margin-bottom-10 margin-top--10 padding-left">
                            <button class="btn btn-success btn-radius-no J_search" type="button"><i class="fa fa-search"></i>&nbsp;搜索</button>
                            <shiro:checkPermission name="Admin:E-CRB:Template:New">
                            <a href="/admin/event/template/new" class="btn btn-success margin-left-15 btn-radius-no btn-green"><i class="fa fa-plus"></i>&nbsp;新增</a>
                            </shiro:checkPermission>
                        </div>
                    </form>
                </div>
                <div class="col-sm-12">
                    <div class="panel panel-info">
                        <div class="panel-body">
                            <input type="hidden" name="userId" class="J_userId" value="${partyId}">
                            <div class="table-responsive">
                                <table class="table table-hover table-bordered">
                                    <thead>
                                    <tr>
                                        <th>序号</th>
                                        <th class="control-width width150">模板名称</th>
                                        <th>活动类型</th>
                                        <th>活动级别</th>
                                        <th>活动开始日期</th>
                                        <th>活动结束日期</th>
                                        <th>活动人员</th>
                                        <th>模板创建人</th>
                                        <th>模板创建时间</th>
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
<jsp:include flush="true" page="/WEB-INF/views/admin/common/footer.jsp"></jsp:include>
<input class="hidId" type="hidden"></input>
<!-- 删除对话框 -->
<div class="modal fade" id="delDialog" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">提示信息</h4>
            </div>
            <div class="modal-body">
                <p>确定删除该模板吗？</p>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-success J_delDlg">确定</button>
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
<script src="/js/e-crb/template-management/template-management-list.js"></script>
</body>
</html>