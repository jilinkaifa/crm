<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.pandawork.net/tags" %>
<html>
<head>
    <title>问卷详情</title>
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
                            <a href="/admin/client/basic">基本信息管理</a>
                        </li>
                        <li class="active">问卷详情</li>
                    </ol>
                    <%--<div class="alert alert-success J_tip" role="alert">刷新成功!</div>--%>
                </div>
                <div class="col-sm-12 margin-top--10">
                    <form class="form-horizontal J_searchForm">
                        <div class="panel panel-info">
                            <div class="panel-heading">
                                <h4>问卷</h4>
                            </div>
                            <input type="hidden" class="pageDataCount" value="${dataCount}">
                            <input type="hidden" class="clientId" name="clientId" value="${clientId}">
                            <div class="panel-body">
                                <div class="form-group">
                                    <label class="col-sm-1 control-label">问卷项</label>
                                    <div class="col-sm-2">
                                        <select id="J_select" class="form-control w180" name="questItemId">
                                            <option value="">请选择..</option>
                                            <c:forEach items="${questItemList}" var="item">
                                                        <option value="${item.id}">${item.name}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <label class="col-sm-2 control-label">记录时间</label>
                                    <div class="col-sm-7">
                                        <input id="start" name="startDate" readonly="readonly" class="Wdate J_startTime w200" type="text" onclick="WdatePicker({maxDate:'#F{$dp.$D(\'end\')}'})"/>
                                        <span class="to">~</span>
                                        <input id="end" name="endDate" readonly="readonly" class="Wdate J_startTime w200" type="text" onclick="WdatePicker({minDate:'#F{$dp.$D(\'start\')}'})"/>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-sm-12 margin-bottom-10 margin-top--10 padding-left">
                            <shiro:checkPermission name="Admin:Client:Quest:Detail">
                                <button class="btn btn-success btn-radius-no J_search" type="button"><i class="fa fa-search"></i>&nbsp;查询</button>
                            </shiro:checkPermission>
                        </div>
                    </form>
                </div>
                <div class="col-sm-12">
                    <div class="panel panel-info">
                        <!-- <div class="panel-heading">
                            <h4>用户列表</h4>
                        </div>	 -->
                        <div class="panel-body">
                            <div class="table-responsive">
                                <table class="table table-hover table-bordered">
                                    <thead>
                                    <tr>
                                        <th>序号</th>
                                        <th>问卷项</th>
                                        <th>问卷内容</th>
                                        <th>记录开始时间</th>
                                        <th>记录结束时间</th>
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
<script src="/js/tool/calendar/WdatePicker.js"></script>
<script src="/js/client-management/questionnaire-management/questionnaire-management-detail.js"></script>
</body>
</html>
