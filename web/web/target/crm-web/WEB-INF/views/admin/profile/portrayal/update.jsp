<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8" />
    <title>画像修改</title>
    <link rel="stylesheet"  href="/css/portrayal-management/portrayal-function-update.css"/>
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
                            <a href="/admin/profile/label/list">画像管理</a>
                        </li>
                        <li class="active">批量画像修改</li>
                    </ol>
                    <%--<div class="alert alert-success J_tip hidden" role="alert">修改成功!</div>--%>
                </div>
                <form class="form-horizontal J_form" method="" action="">
                    <div class="col-sm-12 margin-top--10">
                        <div class="panel panel-info">
                            <div class="panel-heading">
                                <h4>患者画像</h4>
                            </div>
                            <div class="panel-body">
                                <div class="col-sm-12">
                                    <c:forEach var="list" items="${labelTypeList}">
                                    <c:if test='${list.isMultiple == 0}'>
                                    <div class="form-group col-sm-12">
                                        <div class="col-sm-10">
                                            <label class="col-sm-2 control-label">${list.name}</label>
                                            <input type="hidden" name="labelTypeId" value="${list.id}">
                                            <div class="col-sm-5">
                                                <input class="new999 J_radio" type="text" name="${list.name}" readonly="readonly" value=""/>
                                            </div>
                                            <div class="col-sm-5">
                                                <input class="new999 checkboxWidth readText" type="text" readonly="readonly"
                                                     <c:forEach var="item" items="${labelItemDtoList}">
                                                         <c:if test='${item.typeId == list.id}'>
                                                            value="${item.itemList}" title="${item.itemList}"
                                                         </c:if>
                                                     </c:forEach>
                                                />
                                            </div>
                                        </div>
                                        <div class="col-sm-1">
                                            <a href="#" class="btn btn-success J_selectRadio margin-left-10" data-toggle="modal" data-target="#radioDialog">选择</a>
                                        </div>
                                    </div>
                                    </c:if>
                                    <c:if test='${list.isMultiple == 1}'>
                                    <div class="form-group col-sm-12">
                                        <div class="col-sm-10">
                                            <label class="col-sm-2 control-label">${list.name}</label>
                                            <input type="hidden" name="labelTypeId" value="${list.id}">
                                            <div class="col-sm-5">
                                                <textarea rows="1" class="J_checkBox new999" name="${list.name}" readonly="readonly" value=""></textarea>
                                            </div>
                                            <div class="col-sm-5">
                                                <input class="J_hidId" type="hidden"
                                                    <c:forEach var="item" items="${labelItemDtoList}">
                                                        <c:if test='${item.typeId == list.id}'>
                                                            value="${item.itemList}"
                                                        </c:if>
                                                    </c:forEach>
                                                />
                                                <textarea rows="1" class="new999  readText J_textArea " readonly="readonly" ></textarea>
                                            </div>
                                        </div>
                                        <div class="col-sm-1">
                                            <a href="#" class="btn btn-success J_selectCheckbox margin-left-10" data-toggle="modal" data-target="#checkBoxDialog">选择</a>
                                        </div>
                                    </div>
                                    </c:if>
                                    </c:forEach>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="panel-footer">
                        <div class="row">
                            <div class="col-sm-6 col-sm-offset-4">
                                <div class="btn-toolbar">
                                    <button class="btn btn-default" onclick="window.history.go(-1);"><i class="fa fa-undo"></i>&nbsp;返回</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<!-- 隐藏input用来存放被点击选择的标签id和name值 -->
<input type="hidden" class="J_tagId" value="">
<input type="hidden" class="J_inputName" value="">
<input type="hidden" class="J_textareaName" value="">
<!-- 操作失败对话框 -->
<div class="modal fade" id="errorDialog" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
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
<!-- 第一次选择单选对话框 -->
<div class="modal fade" id="firstDialog" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">提示信息</h4>
            </div>
            <div class="modal-body">
                <p class="J_p">确认修改全部吗!</p>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-success J_secondDialog" data-dismiss="modal">确定</button>
            </div>
        </div>
    </div>
</div>
<!-- 单选-民族选择对话框 -->
<div class="modal fade" id="radioDialog" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">选择标签</h4>
            </div>
            <div class="modal-body">
                <form class="J_radioForm form-horizontal">
                    <div class="form-group">
                        <label class="J_tagName col-sm-3 control-label"></label>
                        <div class="col-sm-5">
                            <select name="radioSelect" class="J_radioSelect form-control col-sm-2 w180">
                                <option value="-1">请选择</option>
                            </select>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-success J_radioDlg">确定</button>
            </div>
        </div>
    </div>
</div>
<!-- 第一次多选对话框 -->
<div class="modal fade" id="checkBoxFirstDialog" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">提示信息</h4>
            </div>
            <div class="modal-body">
                <p class="J_dP">确认修改全部吗!</p>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-success J_checkBoxSecondDialog" data-dismiss="modal">确定</button>
            </div>
        </div>
    </div>
</div>
<!-- 多选-药物过敏史选择对话框 -->
<div class="modal fade" id="checkBoxDialog" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title J_tagName"></h4>
            </div>
            <div class="modal-body">
                <form class="J_checkBoxForm">
                    <div class="col-sm-12">
                        <label class="selectBox"><input type="checkbox" name="selectAll" class="J_selectAll">全选</label>
                    </div>
                    <div class="J_checkboxSelect clearfix"></div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-success J_checkBoxDlg">确定</button>
            </div>
        </div>
    </div>
</div>

<jsp:include flush="true" page="/WEB-INF/views/admin/common/footer.jsp"></jsp:include>
<script src="/js/portrayal-management/portrayal-function/portrayal-function-update.js"></script>
</body>
</html>




