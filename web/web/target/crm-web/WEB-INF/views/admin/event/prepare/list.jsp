<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://shiro.pandawork.net/tags" prefix="shiro"%>
<!DOCTYPE html>
<html>
<head>
    <title>待开展活动</title>
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
                        <li class="active">待开展活动</li>
                    </ol>
                    <%--<div class="alert alert-success J_tip hidden" role="alert">刷完成功!</div>--%>
                </div>
                <div class="col-sm-12 margin-top--10">
                    <form class="form-horizontal J_searchForm">
                        <div class="panel panel-info">
                            <div class="panel-heading">
                                <h4>搜索</h4>
                            </div>
                            <div class="panel-body">
                                <div class="form-group">
                                    <label class="col-sm-1 control-label">活动名称</label>
                                    <div class="col-sm-2">
                                        <input id="J_selectActivity" type="text" class="form-control w180" autocomplete="off" name="name"/>
                                    </div>
                                    <label class="col-sm-1 control-label">活动类型</label>
                                    <div class="col-sm-2">
                                        <select id="activityType" class="form-control J_activityType new185" name="type">
                                            <option value="-1">请选择</option>
                                            <option value="1">会员关怀型</option>
                                            <option value="2">营销型</option>
                                        </select>
                                    </div>
                                    <label class="col-sm-1 control-label">活动状态</label>
                                    <div class="col-sm-2">
                                        <select id="activityState" class="form-control J_activityState new185" name="approvalStatus">
                                        <option value="-1">请选择</option>
                                        <option value="1">待审批</option>
                                        <option value="2">审批通过</option>
                                        <option value="3">审批驳回</option>
                                        </select>
                                    </div>
                                    <label class="col-sm-1 control-label">活动级别</label>
                                    <div class="col-sm-2">
                                        <select id="activityLevel" class="form-control J_activitylevel new185" name="level">
                                            <option value="-1">请选择</option>
                                            <option value="1">一级</option>
                                            <option value="2">二级</option>
                                            <option value="3">三级</option>
                                        </select>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-sm-12 margin-bottom-10 margin-top--10 padding-left">
                            <button type="submit"  class="btn J_search btn-success btn-radius-no"><i class="fa fa-search"></i>&nbsp;查询</button>
                            <%--<shiro:checkPermission name="Admin:E-CRB:Event:Prepare:New">--%>
                                <c:if test="${realRoleId == 3}">
                                    <a href="/admin/event/prepare/new?newguanhuai=1" class="btn btn-success btn-radius-no" id="edit_model"><i class="fa fa-plus"></i>&nbsp;新建关怀型活动</a>
                                </c:if>
                                <c:if test="${realRoleId == 3 || realRoleId == 5}">
                                    <a href="/admin/event/prepare/new?newyingxiao=2" class="btn btn-success btn-radius-no" id="edit_model"><i class="fa fa-plus"></i>&nbsp;新建营销型活动</a>
                                </c:if>
                                <%--<input type="text" value="${realRoleId}"></input>--%>
                            <%--</shiro:checkPermission>--%>
                        </div>
                    </form>
                </div>
                <div class="col-sm-12">
                    <div class="panel panel-info">
                        <div class="panel-body">
                            <input type="hidden" name="userId" class="J_userId" value="${partyId}">
                            <form action="" class="J_form">
                                <div class="table-responsive">
                                    <table class="table table-hover table-bordered">
                                        <thead>
                                        <tr>
                                            <th class="control-width width150">活动名称</th>
                                            <th>活动类型</th>
                                            <th>活动级别</th>
                                            <th>审批状态</th>
                                            <th>活动状态</th>
                                            <th>活动开始日期</th>
                                            <th>活动结束日期</th>
                                            <th>当前期数</th>
                                            <th class="control-width width2">活动人员</th>
                                            <th>活动创建人</th>
                                            <th>活动创建时间</th>
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
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- 注销对话框 -->
<input class="hidId" type="hidden"></input>
<div class="modal fade" id="cancleDialog" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel">提示信息</h4>
            </div>
            <div class="modal-body">
                <p>确定要注销吗？</p>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-success J_cancleDlg" >确定</button>
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
                <h4 class="modal-title">提示信息</h4>
            </div>
            <div class="modal-body">
                <p>操作失败，请重新操作!</p>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-success" >确定</button>
            </div>
        </div>
    </div>
</div>
<!-- 审批对话框 -->
<div class="modal fade" id="approveDialog" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">活动审批</h4>
            </div>
            <div class="modal-body">
                <form action="" class="form-horizontal J_editForm">
                    <div class="form-group">
                        <label class="col-sm-4 control-label">审批</label>
                        <div class="col-sm-6">
                            <select class="form-control w180  J_examineApprove" name ="examineApprove" value="1">
                                <option value="0">请选择</option>
                                <option value="2">通过</option>
                                <option value="3">驳回</option>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-4 control-label">备注</label>
                        <div class="col-sm-6">
                                <textarea class="form-control w180 J_remarks" name ="remaks" value="2"></textarea>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-success J_approveDlg">确定</button>
            </div>
        </div>
    </div>
</div>
<jsp:include flush="true" page="/WEB-INF/views/admin/common/footer.jsp"></jsp:include>
<script src="/js/e-crb/activities-management/carried-out-activity/carried-out-activity-list.js"></script>
</body>
</html>