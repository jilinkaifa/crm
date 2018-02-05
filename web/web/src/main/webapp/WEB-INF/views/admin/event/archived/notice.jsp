<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>已归档活动</title>
    <jsp:include flush="true" page="/WEB-INF/views/admin/common/head.jsp"></jsp:include>
</head>
<body>
<jsp:include flush="true" page="/WEB-INF/views/admin/common/header.jsp"></jsp:include>
<!--#include file="/html/common/header.html"-->
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
                        <li class="active">已完成活动详情</li>
                    </ol>
                    <%--<div class="alert alert-success J_tip" role="alert">刷新成功！</div>--%>
                </div>
                <div class="col-sm-12  margin-top--10">
                    <form class="form-horizontal J_searchForm">
                        <div class="panel panel-info">
                            <div class="panel-heading">
                                <h4>活动详情</h4>
                            </div>
                            <div class="panel-body">
                                <input type="hidden" name="id" class="eventId" value="${event.id}">
                                <div class="col-sm-6">
                                    <div class="form-group">
                                        <label class="col-sm-5 control-label"><span class="requires">*</span>活动名称</label>
                                        <div class="col-sm-6">
                                            <input class="w200" type="text" value="${event.name}" readonly="readonly"/>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-sm-5 control-label">活动级别</label>
                                        <div class="col-sm-6">
                                            <input class="w200" type="text"  readonly="readonly"
                                                   <c:if test="${event.level=='1'}">value = "一级"</c:if>
                                                   <c:if test="${event.level=='2'}">value = "二级"</c:if>
                                                   <c:if test="${event.level=='2'}">value = "三级"</c:if>
                                            />
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-sm-5 control-label">活动时间</label>
                                        <div class="col-sm-6">
                                            <input readonly="readonly" class="w200" type="text" value="<fmt:formatDate value="${event.startDate}" type="date"></fmt:formatDate>"/>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-sm-5 control-label">循环粒度</label>
                                        <div class="col-sm-6">
                                            <input readonly="readonly" class="w200" type="text"
                                                   <c:if test="${event.pollingTime=='1'}">value = "年"</c:if>
                                                   <c:if test="${event.pollingTime=='2'}">value = "月"</c:if>
                                            />
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-sm-5 control-label">是否关联积分</label>
                                        <div class="col-sm-6">
                                            <input readonly="readonly" class="w200" type="text"
                                                   <c:if test="${event.isPointsRelated=='1'}">value = "是"</c:if>
                                                   <c:if test="${event.isPointsRelated=='0'}">value = "否"</c:if>
                                            />
                                        </div>
                                    </div>

                                </div>
                                <div class="col-sm-6">
                                    <div class="form-group">
                                        <label class="col-sm-3 control-label">活动类型</label>
                                        <div class="col-sm-6">
                                            <input readonly="readonly" class="w200" type="text"
                                                   <c:if test="${event.type=='1'}">value = "会员关怀型"</c:if>
                                                   <c:if test="${event.type=='2'}">value = "营销型"</c:if>
                                            />
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-sm-3 control-label">提醒时间</label>
                                        <div class="col-sm-5">
                                            <input readonly="readonly" class="w200" type="text" value="活动前"/>
                                        </div>
                                        <div class="col-sm-3">
                                            <input class="w50" type="text" name="time" value="${event.remindTime}" >&nbsp;日
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-sm-3 control-label">到</label>
                                        <div class="col-sm-6">
                                            <input class="w200" type="text" readonly="readonly" value="<fmt:formatDate value="${event.endDate}" type="date"></fmt:formatDate>" />
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-sm-3 control-label">活动人员</label>
                                        <div class="col-sm-6">
                                            <input readonly="readonly" class="w200" type="text"
                                                   <c:if test="${event.memberGroupId=='1'}">value = "A组"</c:if>
                                                   <c:if test="${event.memberGroupId=='2'}">value = "B组"</c:if>
                                                   <c:if test="${event.memberGroupId=='2'}">value = "C组"</c:if>
                                                   <c:if test="${event.memberGroupId=='2'}">value = "D组"</c:if>
                                                   <c:if test="${event.memberGroupId=='2'}">value = "E组"</c:if>
                                            />
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-sm-3 control-label">是否关联检查项</label>
                                        <div class="col-sm-6">
                                            <input readonly="readonly" class="w200" type="text"
                                                   <c:if test="${event.isCheckItemRelated=='1'}">value = "是"</c:if>
                                                   <c:if test="${event.isCheckItemRelated=='0'}">value = "否"</c:if>
                                            />
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group col-sm-12">
                                    <h4>活动具体内容</h4>
                                    <hr>
                                    <textarea readonly="readonly" class="col-sm-12 h100">${event.content}</textarea>
                                    <label class="margin-top-10"><a href="/i/w3school_logo_white.gif" download="XXXXXXX"></a></label>
                                </div>
                                <div class="form-group col-sm-12">
                                    <h4>通知内容</h4>
                                    <hr>
                                    <textarea readonly="readonly" class="col-sm-12 h100">${event.noticeContent}</textarea>
                                </div>

                            </div>
                        </div>
                    </form>
                </div>
                <div class="col-sm-12 margin-top--10">
                    <div class="panel panel-info">
                        <div class="panel-body">
                            <div class="table-responsive">
                                <table class="table table-hover table-bordered">
                                    <thead>
                                    <tr>
                                        <th class="control-width width150">姓名</th>
                                        <th>身份证号</th>
                                        <th>电话</th>
                                        <th>状态</th>
                                        <th>备注</th>
                                        <th>通知时间</th>
                                        <th>通知人</th>
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
<!--#include file="/html/common/footer.html"-->
<script src="/js/e-crb/activities-management/finished-activities/finished-activities-detail-inform.js"></script>
</body>
</html>