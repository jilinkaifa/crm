<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.pandawork.net/tags" %>
<!DOCTYPE html>
<html>
<head>
    <title>来访详情管理列表</title>
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
                            <a href="/admin/client/visit">来访管理</a>
                        </li>
                        <li class="active">来访详情</li>
                    </ol>
                    <%--<div class="alert alert-success J_tip" role="alert">刷新成功!</div>--%>
                    <input type="hidden"  class="clientId" name="clientId" value="${clientId}">
                    <input type="hidden" class="pageDataCount" name="pageDataCount" value="${dataCount}">
                </div>
                <div class="col-sm-12 margin-top--10">
                    <form class="form-horizontal J_searchForm">
                        <div class="panel panel-info">
                            <div class="panel-heading">
                                <h4>查询</h4>
                            </div>
                            <div class="panel-body">
                                <div class="col-sm-3">
                                    <div class="form-group">
                                        <label class="col-sm-4 control-label">
                                            <span class="requires"></span>来访来源
                                        </label>
                                        <div class="col-sm-6">
                                            <select name="visitFrom" class="w180 J_userName form-control">
                                                <option value="">请选择</option>
                                                <option value="0">门诊</option>
                                                <option value="1">住院</option>
                                            </select>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-sm-3">
                                    <div class="form-group">
                                        <label class="col-sm-4 control-label">
                                            <span class="requires"></span>来访内容
                                        </label>
                                        <div class="col-sm-6">
                                            <input class="w180 J_tableName" type="text" name="visitContent"/>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-sm-6">
                                    <div class="form-group ">
                                        <label class="col-sm-2 control-label">来访日期</label>
                                        <div class="col-sm-10"> 
                                            <input id="start" name="startDate" readonly="readonly" class="Wdate w180" type="text" onclick="WdatePicker({maxDate:'#F{$dp.$D(\'end\')}'})"/> 
                                            <span class="to">~</span>
                                            <input id="end" name="endDate" readonly="readonly" class="Wdate w180" type="text" onclick="WdatePicker({minDate:'#F{$dp.$D(\'start\')}'})"/>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-sm-12 margin-bottom-10 margin-top--10 padding-left">
                            <shiro:checkPermission name="Admin:Client:Visit:DetailList">
                                <button class="btn btn-success btn-radius-no J_search" type="button"><i class="fa fa-search"></i>&nbsp;查询</button>
                            </shiro:checkPermission>
                            <shiro:checkPermission name="Admin:Client:Visit:DetailNew">
                                <a href="#" class="btn btn-success btn-radius-no J_add" data-toggle="modal" data-target="#addDialog"><i class="fa fa-plus"></i>&nbsp;新增</a>
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
                                        <th>序号</th>
                                        <th>姓名</th>
                                        <th>来访来源</th>
                                        <th>来访日期</th>
                                        <th>来访内容</th>
                                        <th>消费金额</th>
                                        <th>操作人</th>
                                        <th>操作时间</th>
                                        <th>操作</th>
                                    </tr>
                                    </thead>
                                    <tbody id="J_template">
                                    </tbody>
                                </table>
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
</div>
<input class="hidId" type="hidden"></input>
<!-- 删除对话框 -->
<div class="modal fade" id="delDialog" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">提示信息</h4>
            </div>
            <div class="modal-body">
                <p>确定删除该来访记录吗？</p>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-success J_delDlg">确定</button>
            </div>
        </div>
    </div>
</div>
<!-- 添加对话框 -->
<div class="modal fade" id="addDialog" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">添加</h4>
            </div>
            <div class="modal-body J_visitTimeValid">
                <form action="" class="form-horizontal J_addForm">
                    <div class="form-group">
                        <label class="col-sm-4 control-label">来访日期<span class="requires">&nbsp;*</span></label>
                        <div class="col-sm-6">
                            <input readonly="readonly" class="Wdate J_addVisitTime w200" type="text" name="visitTime" onclick="WdatePicker({maxDate:'%y-%M-%d'})"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-4 control-label">来访来源<span class="requires">&nbsp;*</span></label>
                        <div class="col-sm-6">
                            <select class="form-control J_addVisitFromInput w200" name="visitFrom">
                                <option value="">--请选择--</option>
                                <option value="0">门诊</option>
                                <option value="1">住院</option>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-4 control-label">消费金额<span class="requires">&nbsp;*</span></label>
                        <div class="col-sm-6">
                            <input type="text" class="form-control  w200  J_addCostInput" name="cost"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-4 control-label">来访内容<span class="requires">&nbsp;*</span></label>
                        <div class="col-sm-6">
                            <textarea type="text" class="  w200 form-control J_addVisitContentInput" name="visitContent"></textarea>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-success J_addDlg">确定</button>
            </div>
        </div>
    </div>
</div>
<!-- 编辑对话框 -->
<div class="modal fade" id="editDialog" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">编辑</h4>
            </div>
            <div class="modal-body J_visitTimeValid">
                <form action="" class="form-horizontal J_editForm">
                    <div class="form-group">
                        <label class="col-sm-4 control-label">姓名<span class="requires">&nbsp;</span></label>
                        <div class="col-sm-6">
                            <input type="text" class="form-control J_editNameInput w200" name="clientName"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-4 control-label">来访来源<span class="requires">&nbsp;*</span></label>
                        <div class="col-sm-6">
                            <select class="form-control J_editVisitFromInput w200" name="visitFrom">
                                <option value="">--请选择--</option>
                                <option value="0">门诊</option>
                                <option value="1">住院</option>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-4 control-label">来访日期<span class="requires">&nbsp;*</span></label>
                        <div class="col-sm-6">
                            <input id="" class="Wdate J_editVisitTime w200" type="text" name="visitTime" onclick="WdatePicker({maxDate:'%y-%M-%d'})"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-4 control-label">来访内容<span class="requires">&nbsp;*</span></label>
                        <div class="col-sm-6">
                            <textarea type="text" class=" w200 form-control J_editVisitContentInput" name="visitContent"></textarea>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-4 control-label">消费金额<span class="requires">&nbsp;*</span></label>
                        <div class="col-sm-6">
                            <input type="text" class="form-control J_editCostInput  w200" name="cost"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-4 control-label">操作人<span class="requires">&nbsp;</span></label>
                        <div class="col-sm-6">
                            <input type="text" class="form-control J_editPartyNameInput  w200" name="partyName"/>
                        </div>
                    </div>
                    <%--<div class="form-group">--%>
                        <%--<label class="col-sm-4 control-label">操作时间<span class="requires">&nbsp;</span></label>--%>
                        <%--<div class="col-sm-6">--%>
                            <%--<input type="text" class="form-control J_editPartyDateInput" name="partyDate"/>--%>
                        <%--</div>--%>
                    <%--</div>--%>

                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-success J_editDlg">确定</button>
            </div>
        </div>
    </div>
</div>
<!--#include file="/html/common/footer.html"-->
<script src="/js/tool/calendar/WdatePicker.js"></script>
<script src="/js/client-management/visit-management/visit-management-details-list.js"></script>
</body>
</html>
