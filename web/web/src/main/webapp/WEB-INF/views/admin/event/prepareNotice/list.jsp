<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
    <title>待办通知</title>
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
                        <li><a href="/admin/event/prepareNotice/list">待办任务</a></li>
                        <li class="active">待办通知</li>
                    </ol>
                    <%--<div class="alert alert-success J_tip" role="alert">刷新成功!</div>--%>
                </div>
                <div class="col-sm-12 margin-top--10">
                    <form action="" method="" class="form-horizontal J_form">
                        <div class="panel panel-info">
                            <div class="panel-heading">
                                <h4>搜索</h4>
                            </div>
                            <div class="panel-body">
                                <div class="form-group">
                                    <label class="col-sm-1 control-label">级别</label>
                                    <div class="col-sm-2">
                                        <select class="col-sm-2 form-control w180" name="level">
                                            <option value="-1">请选择</option>
                                            <option value="1">一级</option>
                                            <option value="2">二级</option>
                                            <option value="3">三级</option>
                                        </select>
                                    </div>
                                    <label class="col-sm-1 control-label">截止日期</label>
                                    <div class="col-sm-5">
                                        <input id="start" name="startTime" readonly="readonly" class="Wdate w180" type="text"onclick="WdatePicker({maxDate:'#F{$dp.$D(\'endTime\')}'})"/>
                                        <span>~</span>
                                        <input id="endTime" class="Wdate w180" type="text" name="endTime" readonly="readonly" onfocus="WdatePicker({minDate:'#F{$dp.$D(\'start\')}'})"/>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-sm-12 margin-bottom-10 margin-top--10 padding-left">
                            <button class="btn btn-success btn-radius-no J_search" type="button"><i class="fa fa-search"></i>&nbsp;搜索</button>
                        </div>
                    </form>
                </div>
                <div class="col-sm-12">
                    <div class="panel panel-info">
                        <div class="panel-body">
                            <div class="table-responsive">
                                <table class="table table-hover table-bordered">
                                    <thead>
                                    <tr>
                                        <th>序号</th>
                                        <th class="control-width width150">活动名称</th>
                                        <th>活动类型</th>
                                        <th>活动级别</th>
                                        <th>本期活动开始时间</th>
                                        <th class="control-width width2">活动人员</th>
                                        <th>待通知人数</th>
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
                </div>
            </div>
        </div>
    </div>
</div>
<jsp:include flush="true" page="/WEB-INF/views/admin/common/footer.jsp"></jsp:include>
<script src="/js/tool/calendar/WdatePicker.js"></script>
<script src="/js/e-crb/backlog-management/notification-management-list.js"></script>
</body>
</html>
