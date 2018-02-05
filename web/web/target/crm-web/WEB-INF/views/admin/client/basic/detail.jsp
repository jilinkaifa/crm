<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.pandawork.net/tags" %>
<html>
<head>
    <title>患者详情</title>
    <%--<link rel="stylesheet"  href="/css/client-management/basic-info-management/basic-info-management-add.css"/>--%>
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
                        <li><a href="/admin/client/basic">基础患者</a></li>
                        <li><a href="/admin/client/basic" class="active">基础患者详情</a></li>
                    </ol>
                </div>
                <div class="col-sm-12">
                    <div class="panel panel-info">
                        <div class="panel-heading">
                            <h4>查看患者</h4>
                        </div>
                        <div class="panel-body">
                            <form class="form-horizontal J_searchForm  J_tableForm">
                                <div class="col-sm-6">
                                    <input type="hidden" name='id' id="J_clientId" value="${id}"/>
                                    <div class="form-group">
                                        <label class="col-sm-4 control-label">
                                            <span class="requires">*</span>姓名
                                        </label>
                                        <div class="col-sm-6">
                                            <input class="w180 J_tableName" type="text"  value="${name}" data-valid-rule="notNull" readonly="readonly"/>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-sm-4 control-label">
                                            <span class="requires">*</span>电话
                                        </label>
                                        <div class="col-sm-6">
                                            <input class="w180 J_tableName" type="text"  value="${tel}" data-valid-rule="notNull" readonly="readonly"/>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-sm-4 control-label">
                                            <span class="requires"></span>性别
                                        </label>
                                        <div class="col-sm-6">
                                            <input class="w180 J_tableName" type="text" name="name" value="${gender}" readonly="readonly"/>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-sm-4 control-label">
                                            <span class="requires"></span>身高
                                        </label>
                                        <div class="col-sm-6">
                                            <input class="w180 J_tableName" type="text"  value="${height}" readonly="readonly">&nbsp;cm
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-sm-4 control-label">
                                            <span class="requires"></span>住址
                                        </label>
                                        <div class="col-sm-6">
                                            <input class="w180 J_tableName" type="text" name="name" value="${address}" readonly="readonly">
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-sm-4 control-label">
                                            <span class="requires"></span>联系人
                                        </label>
                                        <div class="col-sm-6">
                                            <input class="w180 J_tableName" type="text" name="name" value="${secContact}" readonly="readonly">
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-sm-4 control-label">
                                            <span class="requires"></span>医保类型
                                        </label>
                                        <div class="col-sm-6">
                                            <input class="w180 J_tableName" type="text"   value="${dicMCIType}" readonly="readonly">
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-sm-4 control-label">
                                            <span class="requires"></span>医保卡号
                                        </label>
                                        <div class="col-sm-6">
                                            <input class="w180 J_tableName" type="text"  value="${MCINum}" readonly="readonly">
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-sm-4 control-label">
                                            <span class="requires"></span>用户类型
                                        </label>
                                        <div class="col-sm-6">
                                            <input class="w180 J_tableName" type="text"  readonly="readonly" value="${dicType}" >
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-sm-4 control-label">
                                            <span class="requires"></span>诊断
                                        </label>
                                        <div class="col-sm-6">
                                            <!-- <input class="w180 J_tableName" type="text" name="name" > -->
                                            <textarea class="w180  form-control " rows="3"  readonly="readonly">${diagnoseType}</textarea>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-sm-6">
                                    <div class="form-group">
                                        <label class="col-sm-3 control-label">
                                            <span class="requires"></span>身份证号码
                                        </label>
                                        <div class="col-sm-6">
                                            <input class="w180 J_tableName J_idCard" type="text" name="idcardNum" data-valid-rule="notNull&isNumber" value="${idCardNum}" readonly="readonly"/>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-sm-3 control-label">
                                            <span class="requires"></span>年龄
                                        </label>
                                        <div class="col-sm-6">
                                            <input class="w180 J_tableName" type="text"   value="${age}" readonly="readonly">
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-sm-3 control-label">
                                            <span class="requires"></span>民族
                                        </label>
                                        <div class="col-sm-6">
                                            <input class="w180 J_tableName" type="text"  value="${dicNation}" readonly="readonly" >
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-sm-3 control-label">
                                            <span class="requires"></span>体重
                                        </label>
                                        <div class="col-sm-6">
                                            <input class="w180 J_tableName" type="text" value="${weight}" readonly="readonly" >&nbsp;kg
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-sm-3 control-label">
                                            <span class="requires"></span>工作单位
                                        </label>
                                        <div class="col-sm-6">
                                            <input class="w180 J_tableName" type="text" value="${company}" readonly="readonly">
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-sm-3 control-label">
                                            <span class="requires"></span>联系人电话
                                        </label>
                                        <div class="col-sm-6">
                                            <input class="w180 J_tableName" type="text"   value="${secTel}" readonly="readonly">
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-sm-3 control-label">
                                            <span class="requires"></span>联系人关系
                                        </label>
                                        <div class="col-sm-6">
                                            <input class="w180 J_tableName" type="text"   value="${dicSecRelation}" readonly="readonly">
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-sm-3 control-label">
                                            <span class="requires" ></span>累积消费金额
                                        </label>
                                        <div class="col-sm-9">
                                            <input class="w180 J_tableName" type="text"   style="dispaly:inline" value="${allCost}" readonly="readonly">
                                            <a class="J_details" href="/admin/client/visit/detailList?clientId=${id}" >&nbsp;查看详情</a>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-sm-3 control-label">
                                            <span class="requires"></span>参加活动数量
                                        </label>
                                        <div class="col-sm-9">
                                            <input class="w180 J_tableName" type="text"  value="${eventCount}" readonly="readonly" >
                                            <a class="J_details" href="/admin/event/processing/total/list?clientId=${id}" >&nbsp;查看详情</a>
                                        </div>
                                    </div>
                                </div>
                            </form>
                        </div>
                        <div class="panel-footer">
                            <div class="row">
                                <div class="col-sm-6 col-sm-offset-4">
                                    <div class="btn-toolbar">
                                        <button class="btn btn-default" onclick="window.history.go(-1);"><i class="fa fa-undo"></i>&nbsp;返回
                                        </button>
                                        <shiro:checkPermission name="Admin:Client:Basic:AddMember">
                                            <c:if test="${isMember == 0}">
                                                <button class="btn-success btn J_save" style="background: #02DF82"  >
                                                    <i class=" fa fa-user"></i>&nbsp;加入会员
                                                </button>
                                            </c:if>
                                        </shiro:checkPermission>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<jsp:include flush="true" page="/WEB-INF/views/admin/common/footer.jsp"></jsp:include>
<!--#include file="/html/common/footer.html"-->
<!-- 加入会员对话框 -->
<div class="modal fade" id="joinDialog" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel">提示信息</h4>
            </div>
            <div class="modal-body">
                <p>确定加入会员吗？</p>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-success J_joinDlg J_join" >确定</button>
            </div>
        </div>
    </div>
</div>
<!-- 操作失败对话框 -->
<div class="modal fade" id="modalDialog" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel">提示信息</h4>
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

<script type="text/javascript"  src="/js/client-management/basic-info-management/basic-info-management-details.js">

</script>>
</body>
</html>
