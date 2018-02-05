<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.pandawork.net/tags" %>
<!DOCTYPE html>
<html>
<head>
    <title>积分管理列表</title>
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
                        <li><a href="/admin"><i class="fa fa-home"></i>&nbsp;首页</a></li>
                        <li><a href="/admin/client/basic">患者管理</a></li>
                        <li class="active">积分管理</li>
                    </ol>
                    <%--<div class="alert alert-success J_tip" role="alert">刷新成功!</div>--%>
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
                                        <input id="J_selectName" type="text" class="form-control w180" autocomplete="off" name="clientName"/>
                                    </div>

                                    <label class="col-sm-2 control-label">身份证号</label>
                                    <div class="col-sm-2">
                                        <input id="J_selectId" type="text" class="form-control w180" autocomplete="off" name="clientIdcardNum"/>
                                    </div>
                                </div>    
                                    <div class="form-group">
                                    <label class="col-sm-1 control-label">电话</label>
                                    <div class="col-sm-2">
                                        <input id="J_selectPhone" type="text" class="form-control w180" autocomplete="off" name="clientTel"/>
                                    </div>
                                    <label class="col-sm-2 control-label">最后消费日期</label>
                                    <div class="col-sm-7">
                                        <div class="time-row">
                                            <input type="text" class="Wdate calendar J_startTime w180" onFocus="WdatePicker({lang:'zh-cn'})" name="startDate">
                                            <span class="to">~</span>
                                            <input type="text" class="Wdate calendar J_startTime w180" onFocus="WdatePicker({lang:'zh-cn'})" name="endDate">
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-sm-12 margin-bottom-10 margin-top--10 padding-left">
                            <shiro:checkPermission name="Admin:Client:Points:List">
                                <button type="submit" class="btn J_search btn-success btn-radius-no"><i class="fa fa-search"></i>&nbsp;查询</button>
                            </shiro:checkPermission>
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
                                        <th class="control-width width1">姓名</th>
                                        <th>身份证号</th>
                                        <th>电话</th>
                                        <th>累积消费金额</th>
                                        <th>累计消费次数</th>
                                        <th>最后消费日期</th>
                                        <th>累计积分</th>
                                        <th>当前积分</th>
                                        <th>积分情况</th>
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
<script src="/js/tool/calendar/WdatePicker.js"></script>
<script src="/js/client-management/accumlate-points-management/accumlate-points-management-list.js"></script>

</body>
</html>