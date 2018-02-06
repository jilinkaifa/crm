<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>活动详情</title>
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
                            <a href="/admin/event/prepare/list">客户关怀</a>
                        </li>
                        <li class="active">活动详情</li>
                    </ol>
                    <%--<div class="alert alert-success J_tip" role="alert">提示信息!</div>--%>
                    <input type="hidden" class="pageDataCount" value="10">
                </div>
                <div class="col-sm-12 margin-bottom-30 margin-top--10">
                    <form class="form-horizontal J_detailForm">
                        <div class="panel panel-info">
                            <div class="panel-heading">
                                <h4>活动详情</h4>
                            </div>
                            <div class="panel-body">
                                <input type="hidden" name="id" value="2">
                                <div class="col-sm-6">
                                    <div class="form-group">
                                        <label class="col-sm-5 control-label">活动名称</label>
                                        <div class="col-sm-6">
                                            <input class="w200" type="text" value="${event.name}" readonly="readonly"/>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-sm-5 control-label">活动级别</label>
                                        <div class="col-sm-6">
                                            <input class="w200" type="text" readonly="readonly"
                                                    <c:if test='${event.level == 1}'> value="一级" </c:if>
                                                    <c:if test='${event.level == 2}'> value="二级" </c:if>
                                                    <c:if test='${event.level == 3}'> value="三级" </c:if>
                                            />
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-sm-5 control-label">开始时间</label>
                                        <div class="col-sm-6">
                                            <input readonly="readonly" class="w200" type="text"
                                                   value="<fmt:formatDate value="${event.startDate}" type="date"></fmt:formatDate>"
                                            />
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <label class="col-sm-5 control-label">是否关联积分</label>
                                        <div class="col-sm-6">
                                            <input readonly="readonly" class="w200" type="text"
                                                    <c:if test='${event.isPointsRelated == 1}'> value="是"</c:if>
                                                    <c:if test='${event.isPointsRelated == 0}'> value="否"</c:if>
                                            />
                                        </div>
                                    </div>
                                    <c:if test='${event.type == 1}'>
                                        <div class="form-group">
                                            <label class="col-sm-5 control-label">循环粒度</label>
                                            <div class="col-sm-6">
                                                <input readonly="readonly" class="w200" type="text"
                                                       <c:if test='${event.pollingTime == 1}'>value="年"</c:if>
                                                       <c:if test='${event.pollingTime == 2}'>value="月"</c:if>
                                                       <c:if test='${event.pollingTime == 3}'>value="无"</c:if>
                                                />
                                            </div>
                                        </div>
                                    </c:if>
                                </div>
                                <div class="col-sm-6">
                                    <div class="form-group">
                                        <label class="col-sm-3 control-label">活动类型</label>
                                        <div class="col-sm-6">
                                            <input readonly="readonly" class="w200" type="text"
                                                   <c:if test='${event.type == 1}'>value="会员关怀型"</c:if>
                                                   <c:if test='${event.type == 2}'>value="营销型"</c:if>
                                            />
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-sm-3 control-label">提醒时间</label>
                                        <div class="col-sm-5">
                                            <input readonly="readonly" class="w200" type="text" value="活动前${event.remindTime}天"/>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-sm-3 control-label">到</label>
                                        <div class="col-sm-6">
                                            <input class="w200" type="text" readonly="readonly"
                                                   value="<fmt:formatDate value="${event.endDate}" type="date"></fmt:formatDate>"
                                            />
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-sm-3 control-label">是否关联检查项</label>
                                        <div class="col-sm-6">
                                            <input readonly="readonly" class="w200" type="text"
                                                    <c:if test='${event.isCheckItemRelated == 1}'> value="是"</c:if>
                                                    <c:if test='${event.isCheckItemRelated == 0}'> value="否"</c:if>
                                            />
                                        </div>
                                    </div>
                                    <c:if test='${event.type == 1}'>
                                        <div class="form-group">
                                            <label class="col-sm-3 control-label">活动人员</label>
                                            <div class="col-sm-6">
                                                <input readonly="readonly" class="w200" type="text" value="${event.memberGroupName}"/>
                                            </div>
                                        </div>
                                    </c:if>
                                </div>

                                <c:if test="${event.approvalStatus == 2 || event.approvalStatus == 3 }">
                                    <div class="form-group col-sm-12" id="cc">
                                        <h4>最新审批结果</h4>
                                        <hr>
                                        <div id="shenpixinxi">
                                            <p>${event.approvalStatusValue}:${event.approvalComment}</p>
                                        </div>
                                    </div>
                                </c:if>

                                <c:if test="${checkItemList!=null && fn:length(checkItemList) > 0}">
                                    <div class="form-group col-sm-12" id="aa">
                                        <h4>检查项</h4>
                                        <hr>
                                        <div id="jianchaxiang">
                                            <c:forEach var="checkItem" items="${checkItemList}">
                                                <p>${checkItem.name}:${checkItem.content}</p>
                                            </c:forEach>
                                        </div>
                                    </div>
                                </c:if>

                                <c:if test="${pointsItemList!=null && fn:length(pointsItemList) > 0}">
                                    <div class="form-group col-sm-12" id="bb">
                                        <h4>积分项</h4>
                                        <hr>
                                        <div id="jifenxiang">
                                            <c:forEach var="pointsItem" items="${pointsItemList}">
                                                <p> ${pointsItem.name}:${pointsItem.pointsValue}</p>
                                            </c:forEach>
                                        </div>
                                    </div>
                                </c:if>

                                <div class="form-group col-sm-12">
                                    <h4>活动具体内容</h4>
                                    <hr>
                                    <textarea readonly="readonly" class="col-sm-12 h100">${event.content}</textarea>
                                    <label class="margin-top-10">附件：
                                        <c:if test="${eventAttachmentName.length() > 0}">
                                            <a href="/admin/file/ajax/download?id=${event.attachment}" >${eventAttachmentName}</a>
                                        </c:if>
                                        <c:if test="${eventAttachmentName == ''}">无</c:if>
                                    </label>
                                </div>
                                <div class="form-group col-sm-12">
                                    <h4>通知内容</h4>
                                    <hr>
                                    <textarea readonly="readonly" class="col-sm-12 h100">${event.noticeContent}</textarea>
                                </div>
                            </div>
                            <div class="panel-footer">  
                                <div class="row">
                                    <div class="col-sm-6 col-sm-offset-5">
                                        <div class="btn-toolbar">
                                            <button href="" class="btn-sm btn-default" onclick="window.history.go(-1);"><i class="fa fa-undo"></i>&nbsp;返回
                                            </button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </form>
                </div>
                <div class="col-sm-12 margin-top--10">

                </div>
            </div>
        </div>
    </div>
</div>
<jsp:include flush="true" page="/WEB-INF/views/admin/common/footer.jsp"></jsp:include>
<input type="hidden" class="hidId">
<!-- 处理对话框 -->
<div class="modal fade" id="modalDialog" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">提示信息</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal J_form">
                    <div class="form-group">
                        <label class="col-sm-3 control-label">日期</label>
                        <div class="col-sm-9">
                            <input id="start" type="text" name="date" class="Wdate w200 form-control J_date" onclick="WdatePicker()" readonly="readonly" value="2017-07-1">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">检查项选择</label>
                        <div class="col-sm-9">
                            <input type="text" name="checkItem" class="w200 form-control">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">下次计划时间</label>
                        <div class="col-sm-9">
                            <input type="text" name="nextTime" class="Wdate w200 form-control J_nextTime" onclick="WdatePicker({minDate:'#F{$dp.$D(\'start\')}'})" readonly="readonly" value="2017-08-1">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">备注</label>
                        <div class="col-sm-9">
                            <textarea name="comment" cols="50" rows="5"></textarea>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-success J_save">确定</button>
            </div>
        </div>
    </div>
</div>
<!-- 操作失败对话框 -->
<div class="modal fade" id="tipDialog" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
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
<script src="/js/tool/calendar/WdatePicker.js"></script>
<script>
    $(function () {
        $('#Menu3,#logoMenu3').trigger('click');
    });
</script>
<%--<script src="/js/e-crb/activities-management/activities-management-ing/activities-management-ing-record.js"></script>--%>
</body>
</html>
