<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>已归档活动</title>
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
                        <li class="active">已归档活动</li>
                    </ol>
                    <%--<div class="alert alert-success J_tip" role="alert">刷新成功！</div>--%>
                    <input type="hidden" class="pageDataCount" name="pageDataCount" value="${dataCount}">
                </div>
                <div class="col-sm-12 margin-top--10">
                    <form class="form-horizontal J_searchForm">
                        <div class="panel panel-info">
                            <div class="panel-heading">
                                <h4>搜索</h4>
                            </div>
                            <div class="panel-body">
                                <div class="form-group">
                                    <label class="col-sm-1 control-label">姓名</label>
                                    <div class="col-sm-2">
                                        <input id="J_selectName" type="text" class="form-control w180" autocomplete="off" name="name"/>
                                    </div>
                                    <label class="col-sm-2 control-label">身份证号</label>
                                    <div class="col-sm-2">
                                        <input id="J_selectId" type="text" class="form-control w180" autocomplete="off" name="idcardNum"/>
                                    </div>
                                    <label class="col-sm-2 control-label">活动名称</label>
                                    <div class="col-sm-2">
                                        <input id="J_selectActivity" type="text" class="form-control w180" autocomplete="off" name="eventName"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-1 control-label">活动类型</label>
                                    <div class="col-sm-2">
                                        <select name="eventType" class="form-control w180">
                                            <option value="">请选择</option>
                                            <option value="1">客户关怀型</option>
                                            <option value="2">营销型</option>
                                        </select>
                                    </div>
                                    <label class="col-sm-2 control-label">活动级别</label>
                                    <div class="col-sm-2">
                                        <select name="eventLevel" class="form-control w180">
                                            <option value="">请选择</option>
                                            <option value="1">一级</option>
                                            <option value="2">二级</option>
                                            <option value="3">三级</option>
                                        </select>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-sm-12 margin-bottom-10 margin-top--10 padding-left">
                            <button class="btn btn-success btn-radius-no J_search" type="submit"><i class="fa fa-search"></i>&nbsp;查询</button>
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
                                        <th class="control-width width1">活动名称</th>
                                        <th>活动类型</th>
                                        <th>活动级别</th>
                                        <th>活动状态</th>
                                        <th>待通知人数</th>
                                        <th>本期开始时间</th>
                                        <th class="control-width width2">活动人员</th>
                                        <th>活动结束日期</th>
                                        <th>待完成人数</th>
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
<!--#include file="/html/common/footer.html"-->
<script src="/js/e-crb/activities-management/finished-activities/finished-activities.js"></script>
</body>
</html>