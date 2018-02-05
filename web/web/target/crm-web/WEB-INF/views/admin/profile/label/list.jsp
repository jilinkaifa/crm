<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="shiro" uri="http://shiro.pandawork.net/tags" %>
<!DOCTYPE html>
<html>
<head>
    <title>标签管理</title>
    <jsp:include flush="true" page="/WEB-INF/views/admin/common/head.jsp"></jsp:include>
    <style>
        .labelT{
            width: 150px !important;
            overflow: hidden;
            text-overflow: ellipsis;
            white-space: nowrap;
            cursor: pointer;
        }
    </style>
</head>
<body>
<jsp:include flush="true" page="/WEB-INF/views/admin/common/header.jsp"></jsp:include>
<div class="page clearfix">
    <%--<jsp:include flush="true" page="/WEB-INF/views/admin/common/sidebar.jsp"></jsp:include>--%>
    <div class="holder">
        <div class="container">
            <div class="row">
                <div class="col-sm-12">
                    <ol class="breadcrumb">
                        <li>
                            <a href="/admin"><i class="fa fa-home"></i>&nbsp;首页</a>
                        </li>
                        <li>
                            <a href="/admin/profile/label/list">画像管理</a>
                        </li>
                        <li class="active">标签管理</li>
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
                                    <label class="col-sm-1 control-label">标签类型</label>
                                    <div class="col-sm-2">
                                        <input type="text" class="col-sm-2 form-control new200 J_tagType" name="tagType">
                                    </div>
                                    <div class="col-sm-3">
                                        <button class="btn btn-success btn-radius-no J_search margin-left-10" type="button"><i class="fa fa-search"></i>&nbsp;搜索</button>
                                        <button class="btn btn-success btn-radius-no" type="reset"><i class="fa fa-undo"></i>&nbsp;重置</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-sm-12 margin-bottom-10 margin-top--10 padding-left">
                            <shiro:checkPermission name="Admin:Profile:Label:Type:New">
                            <a href="#" class="btn btn-success btn-radius-no" data-toggle="modal" data-target="#addDialog"><i class="fa fa-plus"></i>&nbsp;新增</a>
                            </shiro:checkPermission>
                            <shiro:checkPermission name="Admin:Profile:Label:Type:Delete">
                            <a href="#" class="btn btn-success btn-radius-no J_delAll"><i class="fa fa-times"></i>&nbsp;删除</a>
                            </shiro:checkPermission>
                        </div>
                    </form>
                </div>
                <div class="col-sm-12">
                    <div class="panel panel-info">
                        <!-- <div class="panel-heading">
                            <h4>标签列表</h4>
                        </div> -->
                        <div class="panel-body">
                            <div class="table-responsive">
                                <table class="table table-hover table-bordered">
                                    <thead>
                                    <tr>
                                        <th><input type="checkbox" class="J_selectAll"></th>
                                        <th>序号</th>
                                        <th class="control-width width1">标签类型</th>
                                        <th>数量</th>
                                        <th class="labelT">标签项内容</th>
                                        <th class="control-width width2">备注</th>
                                        <th>是否多选</th>
                                        <th>添加时间</th>
                                        <th>添加人</th>
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
<input class="hidId" type="hidden">
<input class="tagType" type="hidden">
<!-- <input class="remarks" type="hidden"></input>
<input class="radio" type="hidden"></input>
<input class="people" type="hidden"></input> -->
<input class="tagId" type="hidden"></input>
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
                <button type="button" class="btn btn-success" data-dismiss="modal">确定</button>
            </div>
        </div>
    </div>
</div>
<!-- 批量删除选择为空对话框 -->
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
<!-- 批量删除对话框 -->
<div class="modal fade" id="delAllDialog" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">提示信息</h4>
            </div>
            <div class="modal-body">
                <p>删除数据不可恢复，确定删除所选标签吗？</p>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-success J_delAllDlg">确定</button>
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
                <p>确定删除该标签吗？</p>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-success J_delDlg">确定</button>
            </div>
        </div>
    </div>
</div>
<!-- 标签项操作对话框 -->
<div class="modal fade" id="handleDialog" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close J_okay" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">标签操作</h4>
                <input type="hidden" class="J_tagId" value="">
            </div>
            <div class="modal-body">
                <div class="table-responsive">
                    <table class="table table-hover table-bordered">
                        <thead>
                        <tr>
                            <td>标签类型</td>
                            <td>标签项</td>
                            <td>操作</td>
                        </tr>
                        </thead>
                        <tbody class="J_tpl">
                        <tr data-id="1">
                            <td>民族</td>
                            <td>汉族</td>
                            <td>
                                <a href="#" class="label-info J_del"><i class="fa fa-times"></i>&nbsp;删除</a>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                    <form action="" class="form-horizontal margin-top-20">
                        <div class="form-group">
                            <div class="col-sm-10">
                                <select class="form-control J_newTag"></select>
                                    <input type="hidden" name="partyId" value="${createdPartyId}">
                            </div>
                            <shiro:checkPermission name="Admin:Profile:Label:Item:New">
                            <button class="btn btn-success J_addTag"><i class="fa fa-plus"></i>&nbsp;新增</button>
                            </shiro:checkPermission>
                        </div>
                    </form>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-success J_okay" data-dismiss="modal">确定</button>
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
            <div class="modal-body">
                <form action="" class="form-horizontal J_editForm">
                    <div class="form-group">
                        <label class="col-sm-3 control-label">标签类型<span class="requires">&nbsp;*</span></label>
                        <div class="col-sm-6">
                            <input type="text" class="form-control J_editTagTypeInput" name="name" readonly>
                        </div>
                    </div>
                    <%--<div class="form-group">--%>
                        <%--<label class="col-sm-3 control-label">标签数量<span class="requires">&nbsp;*</span></label>--%>
                        <%--<div class="col-sm-6">--%>
                            <%--<input type="text" class="form-control J_editAmountInput" name="labelCount">--%>
                        <%--</div>--%>
                    <%--</div>--%>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">是否多选<span class="requires">&nbsp;*</span></label>
                        <div class="col-sm-6">
                            <label class="radio-inline">
                                <input type="radio" name="isMultiple" value="1" checked="checked" class="J_editRadio1">是
                            </label>
                            <label class="radio-inline">
                                <input type="radio" name="isMultiple" value="0" class="J_editRadio2">否
                            </label>
                        </div>
                    </div>
                    <%--<div class="form-group">--%>
                        <%--<label class="col-sm-3 control-label">标签项内容<span class="requires">&nbsp;*</span></label>--%>
                        <%--<div class="col-sm-6">--%>
                            <%--<input type="text" class="form-control J_editConInput" name="labelText">--%>
                        <%--</div>--%>
                    <%--</div>--%>
                    <div class="form-group">

                        <label class="col-sm-3 control-label">添加人<span class="requires">&nbsp;*</span></label>
                        <div class="col-sm-6">
                            <input type="hidden" name="partyId" value="${createdPartyId}">
                            <input type="text" class="form-control J_editPeoInput" value="${createdName}" disabled="true">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">备注</label>
                        <div class="col-sm-6">
                            <textarea name="remarks" class="form-control J_editRemInput" cols="30" rows="5"></textarea>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-success J_editDlg">确定</button>
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
            <div class="modal-body">
                <form action="" class="form-horizontal J_addForm">
                    <div class="form-group">
                        <label class="col-sm-3 control-label">标签类型<span class="requires">&nbsp;*</span></label>
                        <div class="col-sm-6">
                            <select name="name" class="form-control J_addTagTypeInput">
                                <c:forEach var="dicTypes" items="${dicTypes}">
                                <option value="${dicTypes.name}">${dicTypes.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <%--<div class="form-group">--%>
                        <%--<label class="col-sm-3 control-label">标签数量<span class="requires">&nbsp;*</span></label>--%>
                        <%--<div class="col-sm-6">--%>
                            <%--<input type="text" class="form-control J_addTagTypeInput" name="labelCount">--%>
                        <%--</div>--%>
                    <%--</div>--%>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">是否多选<span class="requires">&nbsp;*</span></label>
                        <div class="col-sm-6">
                            <label class="radio-inline">
                                <input type="radio" name="isMultiple" value="1" checked="checked" class="J_addRadio2">是
                            </label>
                            <label class="radio-inline">
                                <input type="radio" name="isMultiple" value="0" class="J_addRadio1">否
                            </label>
                        </div>
                    </div>
                    <%--<div class="form-group">--%>
                        <%--<label class="col-sm-3 control-label">标签项内容<span class="requires">&nbsp;*</span></label>--%>
                        <%--<div class="col-sm-6">--%>
                            <%--<input type="text" class="form-control J_addConInput" name="labelText">--%>
                        <%--</div>--%>
                    <%--</div>--%>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">添加人<span class="requires">&nbsp;*</span></label>
                        <div class="col-sm-6">
                            <input type="hidden" name="partyId" value="${createdPartyId}">
                            <input type="text" class="form-control J_addPeoInput" name="createdPartyId" value="${createdName}" readonly="readonly">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">备注</label>
                        <div class="col-sm-6">
                            <textarea name="labelComment" class="form-control J_addRemInput" cols="30" rows="5"></textarea>
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
<!-- 详情对话框 -->
<div class="modal fade" id="detailDialog" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">详情</h4>
            </div>
            <div class="modal-body">
                <form action="" class="form-horizontal J_detailForm">
                    <div class="form-group">
                        <label class="col-sm-3 control-label">标签类型<span class="requires">&nbsp;*</span></label>
                        <div class="col-sm-6">
                            <input type="text" class="form-control J_detailTagTypeInput" name="labelType" disabled="true">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">标签数量<span class="requires">&nbsp;*</span></label>
                        <div class="col-sm-6">
                            <input type="text" class="form-control J_detailNumberInput" name="labelCount" disabled="true">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">是否多选<span class="requires">&nbsp;*</span></label>
                        <div class="col-sm-6">
                            <label class="radio-inline">
                                <input type="radio" name="radio" value="1" checked="checked" class="J_detailRadio1" disabled="true">是
                            </label>
                            <label class="radio-inline">
                                <input type="radio" name="radio" value="0" class="J_detailRadio2" disabled="true">否
                            </label>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">标签内容<span class="requires">&nbsp;*</span></label>
                        <div class="col-sm-6">
                            <input type="text" class="form-control J_detailContentInput" name="labelText" disabled="true">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">添加人<span class="requires">&nbsp;*</span></label>
                        <div class="col-sm-6">
                            <input type="text" class="form-control J_detailPeoInput" name="createdName" value="" readonly="readonly">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">备注</label>
                        <div class="col-sm-6">
                            <textarea type="text" class="form-control J_detailRemInput" name="remarks" cols="30" rows="5" disabled="true"></textarea>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-success" data-dismiss="modal">确定</button>
            </div>
        </div>
    </div>
</div>
<jsp:include flush="true" page="/WEB-INF/views/admin/common/footer.jsp"></jsp:include>
<script src="/js/portrayal-management/tag-management/tag-management-list.js"></script>
</body>
</html>