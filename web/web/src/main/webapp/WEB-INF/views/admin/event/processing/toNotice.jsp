<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>进行中活动列表</title>
    <link rel="stylesheet" type="text/css" href="/css/e-crb/template-management/template-management.css">
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
                        <li class="active">待办通知</li>
                    </ol>
                    <input type="hidden" class="eventTermId" value="${event.id}">
                </div>
                <div class="col-sm-12 margin-bottom-30 margin-top--10">
                    <form class="form-horizontal J_detailForm">
                        <div class="panel panel-info">
                            <div class="panel-heading">
                                <h4>待办通知详情</h4>
                            </div>
                            <div class="panel-body">
                                <div class="col-sm-6">
                                    <div class="form-group">
                                        <label class="col-sm-5 control-label"><span class="requires">*</span>活动名称</label>
                                        <div class="col-sm-6">
                                            <input class="w200" type="text" name="name" value="${event.name}" readonly="readonly"/>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-sm-5 control-label">活动级别</label>
                                        <div class="col-sm-6">
                                            <input class="w200" type="text" name="level" value="${event.levelValue}" readonly="readonly"/>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-sm-5 control-label">活动时间</label>
                                        <div class="col-sm-6">
                                            <input readonly="readonly" name="startDate" class="w200" type="text"
                                                   value="<fmt:formatDate value="${event.eventStartDate}" type="date"></fmt:formatDate>"
                                            />
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-sm-5 control-label">循环粒度</label>
                                        <div class="col-sm-6">
                                            <input readonly="readonly" name="pollingTime" class="w200" type="text"
                                                   <c:if test="${event.pollingTime == 1}">value="年"</c:if>
                                                   <c:if test="${event.pollingTime == 2}">value="月"</c:if>
                                                   <c:if test="${event.pollingTime == 3}">value="无"</c:if>
                                            />
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-sm-5 control-label">是否关联积分</label>
                                        <div class="col-sm-6">
                                            <input readonly="readonly" name="isPointsRelated" class="w200" type="text"
                                                   <c:if test="${event.isPointsRelated == 0}">value="否"</c:if>
                                                   <c:if test="${event.isPointsRelated == 1}">value="是"</c:if>
                                            />
                                        </div>
                                    </div>
                                </div>
                                <div class="col-sm-6">
                                    <div class="form-group">
                                        <label class="col-sm-3 control-label">活动类型</label>
                                        <div class="col-sm-6">
                                            <input readonly="readonly" name="type" class="w200" type="text" value="${event.typeValue}"/>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-sm-3 control-label">提醒时间</label>
                                        <div class="col-sm-4">
                                            <input readonly="readonly" name="remindTime" class="w200" type="text" value="活动前"/>
                                        </div>
                                        <div class="col-sm-3">
                                            <input class="w50 J_date" type="text" readonly="readonly" name="time" value="${event.remindTime}">&nbsp;日
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-sm-3 control-label">到</label>
                                        <div class="col-sm-6">
                                            <input class="w200" name="endDate" type="text" readonly="readonly"
                                                   value="<fmt:formatDate value="${event.eventStartDate}" type="date"></fmt:formatDate>"
                                            />
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-sm-3 control-label">本期开始时间</label>
                                        <div class="col-sm-6">
                                            <input class="w200" name="startDate" type="text" readonly="readonly"
                                                   value="<fmt:formatDate value="${event.startDate}" type="date"></fmt:formatDate>"
                                            />
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-sm-3 control-label">活动人员</label>
                                        <div class="col-sm-6">
                                            <input readonly="readonly" name="memberGroupId" class="w200" type="text" value="${event.memberGroupName}"/>
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group col-sm-12">
                                    <h4>活动内容</h4>
                                    <hr>
                                    <textarea readonly="readonly" name="content" class="col-sm-12 h100">${event.content}</textarea>
                                </div>
                                <div class="form-group col-sm-12">
                                    <h4>通知内容</h4>
                                    <hr>
                                    <textarea readonly="readonly" name="noticeContent" class="col-sm-12 h100">${event.noticeContent}</textarea>
                                </div>
                            </div>
                        </div>
                    </form>
                </div>

                <div class="col-sm-12 margin-top--10">
                    <form class="form-horizontal J_searchForm">
                        <div class="panel panel-info margin-top--10">
                            <div class="panel-heading">
                                <h4>搜索</h4>
                            </div>
                            <div class="panel-body">
                                <div class="form-group">
                                    <label class="col-sm-1 control-label">姓名</label>
                                    <div class="col-sm-2">
                                        <input id="J_selectName" type="text" class="form-control w180"
                                               autocomplete="off" name="participantName"/>
                                    </div>
                                    <label class="col-sm-2 control-label">身份证号</label>
                                    <div class="col-sm-2">
                                        <input id="J_selectId" type="text" class="form-control w180" autocomplete="off"
                                               name="participantIdcard"/>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-sm-12 margin-bottom-10 margin-top--10 padding-left">
                            <shiro:checkPermission name="Admin:E-CRB:Event:Processing:List">
                                <button type="submit" class="btn J_search btn-success"><i class="fa fa-search"></i>&nbsp;查询
                                </button>
                            </shiro:checkPermission>
                            <shiro:checkPermission name="Admin:E-CRB:Event:Processing:BatchPoints">
                                <c:if test="${event.status == 2}">
                                    <a href="#" class="btn btn-success J_addIntegral"><i class="fa fa-pencil"></i>&nbsp;批量通知</a>
                                </c:if>
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
                                        <th><input type="checkbox" class="J_selectAll"></th>
                                        <th>姓名</th>
                                        <th>身份证号</th>
                                        <th>电话</th>
                                        <th>状态</th>
                                        <th>备注</th>
                                        <th>通知时间</th>
                                        <th>通知人</th>
                                        <th>操作</th>
                                    </tr>
                                    </thead>
                                    <tbody id="J_template"></tbody>
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
<script src="/js/e-crb/backlog-management/notification-management-detail.js"></script>
</body>
</html>