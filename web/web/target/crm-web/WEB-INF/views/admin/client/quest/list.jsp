<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.pandawork.net/tags" %>
<html>
<head>
    <title>问卷管理</title>
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
                        <li class="active">问卷管理</li>
                    </ol>
                    <%--<div class="alert alert-success J_tip" role="alert">刷新成功!</div>--%>
                    <input type="hidden" class="pageDataCount" value="${dataCount}">
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
                                        <input id="J_selectId" type="text" class="form-control w180" autocomplete="off" name="idCardNum"/>
                                    </div>
                                    <label class="col-sm-2 control-label">问卷次数</label>
                                    <div class="col-sm-2">
                                        <input class="J_questCount w200" type="text" name="questCount" value="">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-1 control-label">电话</label>
                                    <div class="col-sm-2">
                                        <input id="J_selectPhone" type="text" class="form-control w180" name="tel"/>
                                    </div>
                                    <label class="col-sm-2 control-label">下次计划问卷时间</label>
                                    <div class="col-sm-7">
                                        <input id="start" name="startDate" readonly="readonly" class="Wdate w200" type="text" onclick="WdatePicker({maxDate:'#F{$dp.$D(\'end\')}'})"/>
                                        <span class="to">~</span>
                                        <input id="end" name="endDate" readonly="readonly" class="Wdate w200" type="text" onclick="WdatePicker({minDate:'#F{$dp.$D(\'start\')}'})"/>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-sm-12 margin-bottom-10 margin-top--10 padding-left">
                            <shiro:checkPermission name="Admin:Client:Quest:L:ist">
                                <button class="btn btn-success btn-radius-no J_search" type="button"><i class="fa fa-search"></i>&nbsp;搜索</button>
                            </shiro:checkPermission>
                        </div>
                    </form>
                </div>
                <div class="col-sm-12">
                    <div class="panel panel-info">
                        <div class="panel-body">
                            <div class="table-responsive">
                                <table class="J_table table table-hover table-bordered">
                                    <thead>
                                    <tr>
                                        <th>序号</th>
                                        <th class="control-width width1">姓名</th>
                                        <th>身份证号</th>
                                        <th>电话</th>
                                        <th>累计消费金额</th>
                                        <th>会员分组</th>
                                        <th>会员级别</th>
                                        <th>问卷次数</th>
                                        <th>下次计划问卷日期</th>
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
<script src="/js/client-management/questionnaire-management/questionnaire-management-list.js"></script>
</body>
</html>
