<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>模板详情</title>
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
                        <li class="active">模板管理</li>
                    </ol>
                </div>
                <div class="col-sm-12 margin-bottom-30 margin-top--10">
                    <form class="form-horizontal J_form" action="" method="">
                        <div class="panel panel-info">
                            <div class="panel-heading">
                                <h4>模板详情</h4>
                            </div>
                            <div class="panel-body">
                                <div class="col-sm-6">
                                    <div class="form-group">
                                        <label class="col-sm-5 control-label"><span class="requires">*</span>活动名称</label>
                                        <div class="col-sm-6">
                                            <input class="w180 J_name" type="text" value="${template.name}" name="name" readonly="readonly"/>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-sm-5 control-label">活动级别</label>
                                        <div class="col-sm-6">
                                            <input class="w180" type="text" value="${template.levelValue}" name="level" readonly="readonly"/>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-sm-5 control-label">活动时间</label>
                                        <div class="col-sm-6">
                                            <input id="start" name="startDate" readonly="readonly" class="w180" type="text"
                                                   value="<fmt:formatDate value="${template.startDate}" type="date"></fmt:formatDate>"
                                            />
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-sm-5 control-label">是否关联积分</label>
                                        <div class="col-sm-6">
                                            <input name="isPointsRelated" readonly="readonly" class="w180" type="text"
                                                    <c:if test='${template.isPointsRelated == 1}'> value="是"</c:if>
                                                    <c:if test='${template.isPointsRelated == 0}'> value="否"</c:if>
                                            />
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-sm-5 control-label">循环粒度</label>
                                        <div class="col-sm-6">
                                            <input name="pollingTime" readonly="readonly" class="w180" type="text"
                                                    <c:if test='${template.pollingTime == 1}'> value="年"</c:if>
                                                    <c:if test='${template.pollingTime == 2}'> value="年"</c:if>
                                                    <c:if test='${template.pollingTime == null}'> value=""</c:if>
                                            />
                                        </div>
                                    </div>
                                </div>
                                <div class="col-sm-6">
                                    <div class="form-group">
                                        <label class="col-sm-3 control-label">活动类型</label>
                                        <div class="col-sm-6">
                                            <input name="type" readonly="readonly" class="w180 J_detail" id="${template.type}" type="text" value="${template.typeValue}"/>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-sm-3 control-label">提醒时间</label>
                                        <div class="col-sm-5">
                                            <label class="col-sm-5 control-label">活动前</label>
                                            <div class="col-sm-7">
                                                <input class="w50 J_date" type="text" readonly="readonly" name="remindTime" value="${template.remindTime}">&nbsp;日
                                            </div>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-sm-3 control-label">到</label>
                                        <div class="col-sm-6">
                                            <input id="endTime" class="w180" type="text" name="endDate" readonly="readonly"
                                                   value="<fmt:formatDate value="${template.endDate}" type="date"></fmt:formatDate>"
                                            />
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-sm-3 control-label">是否关联检查项</label>
                                        <div class="col-sm-6">
                                            <input name="isCheckItemRelated" readonly="readonly" class="w180" type="text"
                                                    <c:if test='${template.isPointsRelated == 1}'> value="是"</c:if>
                                                    <c:if test='${template.isPointsRelated == 0}'> value="否"</c:if>
                                            />
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-sm-3 control-label">活动人员</label>
                                        <div class="col-sm-6">
                                            <input name="memberGroupId" readonly="readonly" class="w180" type="text" value="${template.memberGroupName}"/>
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group col-sm-12">
                                    <h4>活动内容</h4>
                                    <hr>
                                    <textarea readonly="readonly" class="col-sm-12 h100 J_content">${template.content}</textarea>
                                    <label class="margin-top-10">附件：
                                    <c:if test="${eventAttachmentName.length() > 0}">
                                        <a href="/admin/file/ajax/download?id=${template.attachment}" >${eventAttachmentName}</a>
                                    </c:if>
                                    <c:if test="${eventAttachmentName == ''}">无</c:if>
                                    </label>
                                </div>
                                <div class="form-group col-sm-12">
                                    <h4>通知内容</h4>
                                    <hr>
                                    <textarea readonly="readonly" class="col-sm-12 h100 J_noticeContent">${template.noticeContent}</textarea>
                                </div>
                            </div>
                            <div class="panel-footer">
                                <div class="row">
                                    <div class="col-sm-6 col-sm-offset-4">
                                        <div class="btn-toolbar">
                                            <button class="btn-default btn" onclick="window.history.go(-1);" type="button"><i class="fa fa-undo"></i>&nbsp;返回上一页</button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
<script>
    $(function () {
        $('#Menu3,#logoMenu3').trigger('click');
    })
</script>
<jsp:include flush="true" page="/WEB-INF/views/admin/common/footer.jsp"></jsp:include>
</body>
</html>