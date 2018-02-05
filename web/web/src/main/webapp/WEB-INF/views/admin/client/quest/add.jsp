<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>问卷添加</title>
    <link rel="stylesheet"  href="/css/client-management/questionnaire-management.css"/>
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
                        <li class="active">问卷管理</li>
                    </ol>
                </div>
                <div class="col-sm-12 margin-top--10">
                    <div class="panel panel-info">
                        <div class="panel-heading">
                            <h4>问卷内容</h4>
                        </div>
                        <div class="panel-body">
                            <form class="form-horizontal J_form" method="" action="">
                                <input type="hidden" name="clientId" value="${clientId}">
                                <div class="form-group">
                                    <label class="col-sm-4 control-label">开始日期</label>
                                    <div class="col-sm-3">
                                        <input class="new180" type="text" name="startDate"  readonly="readonly" value="${startDate}"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-4 control-label">结束日期</label>
                                    <div class="col-sm-3">
                                        <input id="endTime" class="new180" type="text" name="endDate" readonly="readonly"  value="${endDate}"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-4 control-label"><span class="requires">*</span>下次计划开始时间</label>
                                    <div class="col-sm-4 J_nextTime" >
                                        <input value="" id="nextTime" name="nextQuestTime" readonly="readonly" class="Wdate  new160" type="text" onclick="WdatePicker({minDate:'#F{$dp.$D(\'endTime\')}'})"/>
                                    </div>
                                </div>
                                <div class="form-group J_editRadio">
                                    <label class="col-sm-4 control-label"><span class="requires">&nbsp;*</span>是否提醒问卷</label>
                                    <div class="col-sm-4">
                                        <label class="radio-inline">
                                            <input type="radio" name="isRemindahead" value="0" checked="checked" class="J_editRadio1">不提醒
                                        </label>
                                        <label class="radio-inline">
                                            <input type="radio" name="isRemindahead" value="1" class="J_editRadio2">提醒
                                        </label>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-4 control-label">备注</label>
                                    <div class="col-sm-3">
                                        <input class="new180" type="text" name="comment"  value=""/>
                                    </div>
                                </div>
                            </form>
                            <!-- 可选问卷项 -->
                            <div class="form-horizontal ">
                                <c:forEach items="${questItemList}" var="item">
                                    <div class="form-group">
                                        <label  class="col-sm-4 control-label">${item.name}</label>
                                        <div class="col-sm-3 J_questItems">
                                            <input type="hidden" name="dicQuestItem" value="${item.id}">
                                            <input class="new180 J_questReset" type="text"  name="${item.id}"  value=""/>
                                        </div>
                                    </div>
                                </c:forEach>
                            </div>
                            <div class="col-sm-8-5 col-sm-offset-1-5 margin-bottom">
                                <div>
                                    <span class="h4">用药情况</span>
                                    <div class="right">
                                        <a href="#" class="btn btn-success J_add" data-toggle="modal" data-target="#addDialog"><i class="fa fa-plus"></i>&nbsp;添加</a>
                                        <a href="#" class="btn btn-success J_del" data-toggle="modal"><i class="fa fa-times" ></i>&nbsp;删除</a>
                                    </div>
                                </div>
                                <div class="table-responsive">
                                    <table class="table table-hover table-bordered">
                                        <thead>
                                        <tr>
                                            <input type="hidden" class="J_dicQuestItemId"  value="${dicDosageId}">
                                            <th ><input name="selectAll" type="checkbox" class="J_selectAll"></th>
                                            <th>药名</th>
                                            <th>剂量</th>
                                            <th hidden="hidden">剂量(剂)</th>
                                            <th hidden="hidden">剂量（量）</th>
                                            <th hidden="hidden">问卷项id</th>
                                            <th>次数/日</th>
                                        </tr>
                                        </thead>
                                        <tbody class="J_tbody">

                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>
                        <div class="panel-footer">
                            <div class="row">
                                <div class="col-sm-6 col-sm-offset-4">
                                    <div class="btn-toolbar margin-left">
                                        <button class="btn btn-success J_submit" type="button"><i class="fa fa-save"></i>&nbsp;保存</button>
                                        <button class="btn btn-default J_reset" type="reset"><i class="fa fa-undo "></i>&nbsp;重置</button>
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
<!-- 添加对话框 -->
<div class="modal fade" id="addDialog" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">添加用药</h4>
            </div>
            <div class="modal-body">
                <form action="" class="form-horizontal J_addForm">
                    <div class="form-group J_validMedicinal">
                        <label class="col-sm-3 control-label">药名<span class="requires">&nbsp;*</span></label>
                        <div class="col-sm-6">
                            <select id="J_medicinal" class="form-control J_medicinal   w220" name="dosageName" dic-quest-item = '999'>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">剂量<span class="requires">&nbsp;*</span></label>
                        <div class="col-sm-6">
                            <input type="text" class="J_dosage w210" name="dosageQuantity">
                            <select class="select J_unit" name="dicDosageUnit">
                                <c:forEach items="${unitList}" var="item">
                                    <option value="${item.id}">${item.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">次数/日<span class="requires">&nbsp;*</span></label>
                        <div class="col-sm-6">
                            <input type="text" class="w220 form-control J_times" name="dosageNum">
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
<!-- 删除对话框 -->
<div class="modal fade" id="delDialog" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" style="z-index: 10000;">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">提示信息</h4>
            </div>
            <div class="modal-body">
                <p>确定删除所选用药项吗？</p>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-success J_delDlg">确定</button>
            </div>
        </div>
    </div>
</div>
<!-- 删除选择为空对话框 -->
<div class="modal fade" id="delTipDialog" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">提示信息</h4>
            </div>
            <div class="modal-body">
                <p>请至少选择一个删除项!</p>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-success" data-dismiss="modal">确定</button>
            </div>
        </div>
    </div>
</div>
<!-- 提示表单必填项没有填写 -->
<div class="modal fade" id="tipDialog" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" style="z-index: 10000;">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">提示信息</h4>
            </div>
            <div class="modal-body">
                <p>请填写下次计划开始时间</p>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-success" data-dismiss="modal">确定</button>
            </div>
        </div>
    </div>
</div>
<div class="modal fade" id="daysDialog" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" style="z-index: 10000;">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">提示信息</h4>
            </div>
            <div class="modal-body">
                <p>请填写提前提醒天数</p>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-success" data-dismiss="modal">确定</button>
            </div>
        </div>
    </div>
</div>
<script src="/js/tool/calendar/WdatePicker.js"></script>
<script src="/js/client-management/questionnaire-management/questionnaire-management-add.js"></script>
</body>
</html>
