<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>患者参与活动列表</title>
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
                        <li class="active">参与活动列表</li>
                    </ol>
                    <input type="hidden" class="clientId" value="${clientId}">
                </div>
                <div class="col-sm-12">
                    <div class="panel panel-info">
                        <div class="panel-heading">
                            <h4>参与活动列表</h4>
                        </div>
                        <div class="panel-body">
                            <div class="table-responsive">
                                <table class="table table-hover table-bordered">
                                    <thead>
                                    <tr>
                                        <th class="control-width width2">活动名称</th>
                                        <th>活动类型</th>
                                        <th>活动级别</th>
                                        <th>本期开始时间</th>
                                        <th class="control-width width1">活动人员</th>
                                        <th>活动创建人</th>
                                        <th>活动创建日期</th>
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
                            <div class="pull-right"><ul id="pageLimit"></ul></div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<jsp:include flush="true" page="/WEB-INF/views/admin/common/footer.jsp"></jsp:include>
<script src="/js/e-crb/activities-management/all-activities-details.js"></script>
</body>
</html>
