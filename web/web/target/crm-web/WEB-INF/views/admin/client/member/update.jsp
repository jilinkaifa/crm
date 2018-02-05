<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>会员信息修改</title>
    <link rel="stylesheet"  href="/css/client-management/member-management/member-management.css"/>
    <jsp:include flush="true" page="/WEB-INF/views/admin/common/head.jsp"></jsp:include>
</head>
<body>
<jsp:include flush="true" page="/WEB-INF/views/admin/common/header.jsp"></jsp:include>
<div class="page clearfix">
    <div class="holder">
        <div class="container">
            <div class="row">
                <div class="col-sm-12 margin-top--30">
                    <ol class="breadcrumb">
                        <li><a href="/admin"><i class="fa fa-home"></i>&nbsp;首页</a></li>
                        <li><a href="/admin/client/basic">患者管理</a></li>
                        <li class="active">会员管理</li>
                    </ol>
                </div>
                <form class="form-horizontal J_form" method="" action="">
                    <div class="col-sm-12 margin-top--10">
                        <div class="panel panel-info">
                            <div class="panel-heading">
                                <h4>患者基本信息</h4>
                            </div>
                            <div class="panel-body">
                                <input type="hidden" name="id" class="J_id" value="${id}">
                                <div class="col-sm-5">
                                    <div class="form-group col-sm-12">
                                        <label class="col-sm-5 control-label">姓名</label>
                                        <div class="col-sm-6">
                                            <input class="w180" type="text" name="name" value="${name}"/>
                                        </div>
                                    </div>
                                    <div class="form-group col-sm-12">
                                        <label class="col-sm-5 control-label">身份证号</label>
                                        <div class="col-sm-6">
                                            <input class="w180" type="text" name="idCardNum" disabled="disabled" value="${idCardNum}"/>
                                        </div>
                                    </div>
                                    <div class="form-group col-sm-12">
                                        <label class="col-sm-5 control-label">性别</label>
                                        <div class="col-sm-6">
                                            <select class="form-control J_tableArea w180" name="gender">
                                                <option value="">请选择</option>
                                                <c:forEach items="${genderList}" var="item">
                                                    <option value="${item.id}" <c:if test="${item.id == gender}">selected="selected"</c:if>>${item.name}</option>
                                                </c:forEach>
                                            </select>
                                            <%--<input class="w180" type="text" name="gender" value="${gender}"/>--%>
                                        </div>
                                    </div>
                                    <div class="form-group col-sm-12">
                                        <label class="col-sm-5 control-label">年龄</label>
                                        <div class="col-sm-6">
                                            <input class="w180" type="text" name="age" disabled="disabled" value="${age}"/>
                                        </div>
                                    </div>
                                    <div class="form-group col-sm-12">
                                        <label class="col-sm-5 control-label">电话</label>
                                        <div class="col-sm-6">
                                            <input class="w180" type="text" name="tel" value="${tel}"/>
                                        </div>
                                    </div>
                                    <div class="form-group col-sm-12">
                                        <label class="col-sm-5 control-label">联系人</label>
                                        <div class="col-sm-6">
                                            <input class="w180 J_secContact" type="text" name="secContact" value="${secContact}"/>
                                        </div>
                                    </div>
                                    <div class="form-group col-sm-12">
                                        <label class="col-sm-5 control-label">联系人关系</label>
                                        <div class="col-sm-6">
                                            <select class="form-control J_tableArea J_dicSecRelation w180" name="dicSecRelation" disabled="disabled">
                                                <option value="">请选择..</option>
                                                <c:forEach items="${dicSecRelationList}" var="item">
                                                    <option value="${item.id}" <c:if test="${item.id == dicSecRelation}">selected="selected"</c:if>>${item.name}</option>
                                                </c:forEach>
                                            </select>
                                            <%--<input class="w180" type="text" name="dicSecRelation" value="${dicSecRelation}"/>--%>
                                        </div>
                                    </div>
                                    <div class="form-group col-sm-12">
                                        <label class="col-sm-5 control-label">联系人电话</label>
                                        <div class="col-sm-6">
                                            <input class="w180 J_secTel" type="text" disabled="disabled" name="secTel" value="${secTel}"/>
                                        </div>
                                    </div>
                                    <div class="form-group col-sm-12">
                                        <label class="col-sm-5 control-label">用户类型</label>
                                        <div class="col-sm-6">
                                            <input class="w180" type="text" name="dicType" disabled="disabled" value="${dicType}"/>
                                        </div>
                                    </div>
                                    <div class="form-group col-sm-12">
                                        <label class="col-sm-5 control-label">诊断</label>
                                        <div class="col-sm-6">
                                            <textarea class="w180" name="diagnoseType" rows="1">${diagnoseType}</textarea>
                                        </div>
                                    </div>
                                </div>
                                <!-- 右侧内容start -->
                                <div class="col-sm-7">
                                    <div class="form-group col-sm-12">
                                        <label class="col-sm-3 control-label">身高</label>
                                        <div class="col-sm-6">
                                            <input class="w180" type="text" name="height" value="${height}"/>
                                            <label>cm</label>
                                        </div>
                                    </div>
                                    <div class="form-group col-sm-12">
                                        <label class="col-sm-3 control-label">体重</label>
                                        <div class="col-sm-6">
                                            <input class="w180" type="text" name="weight" value="${weight}"/>
                                            <label>kg</label>
                                        </div>
                                    </div>
                                    <div class="form-group col-sm-12">
                                        <label class="col-sm-3 control-label">民族</label>
                                        <div class="col-sm-6">
                                            <select class="form-control J_tableArea w180" name="dicNation">
                                                <option value="">请选择..</option>
                                                <c:forEach items="${dicNationList}" var="item">
                                                    <option value="${item.id}" <c:if test="${item.id == dicNation}">selected="selected"</c:if>>${item.name}</option>
                                                </c:forEach>
                                            </select>
                                            <%--<input class="w180" type="text" name="dicNation" value="${dicNation}"/>--%>
                                        </div>
                                    </div>
                                    <div class="form-group col-sm-12">
                                        <label class="col-sm-3 control-label">工作单位</label>
                                        <div class="col-sm-6">
                                            <input class="w180" type="text" name="company" value="${company}"/>
                                        </div>
                                    </div>
                                    <div class="form-group col-sm-12">
                                        <label class="col-sm-3 control-label">住址</label>
                                        <div class="col-sm-6">
                                            <input class="w180" type="text" name="address" value="${address}"/>
                                        </div>
                                    </div>
                                    <div class="form-group col-sm-12">
                                        <label class="col-sm-3 control-label">医保类型</label>
                                        <div class="col-sm-6">
                                            <select class="form-control J_tableArea w180" name="dicMCIType">
                                                <option value="">请选择..</option>
                                                <c:forEach items="${dicMCITypeList}" var="item">
                                                            <option value="${item.id}" <c:if test="${item.id == dicMCIType}">selected="selected"</c:if>>${item.name}</option>
                                                </c:forEach>
                                            </select>
                                            <%--<input class="w180" type="text" name="dicMciType" value="${dicMCIType}"/>--%>
                                        </div>
                                    </div>
                                    <div class="form-group col-sm-12">
                                        <label class="col-sm-3 control-label">医保卡号</label>
                                        <div class="col-sm-6">
                                            <input class="w180" type="text" name="MCINum" value="${MCINum}"/>
                                        </div>
                                    </div>
                                    <div class="form-group col-sm-12">
                                        <label class="col-sm-3 control-label">累计消费金额</label>
                                        <div class="col-sm-6">
                                            <input class="w180" type="text" name="allCost" disabled="disabled" value="${allCost}元"/>
                                            <a href="/admin/client/quest/detail?id=${id}">查看详情</a>
                                        </div>
                                    </div>
                                    <div class="form-group col-sm-12">
                                        <label class="col-sm-3 control-label">参与活动数量</label>
                                        <div class="col-sm-6">
                                            <input class="w180" type="text" name="eventCount" disabled="disabled" value="${eventCount}个"/>
                                            <a href="/admin/event/processing/total/list?clientId=${id}">查看详情</a>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-12">
                        <div class="panel panel-info">
                            <div class="panel-heading">
                                <h4>会员信息</h4>
                            </div>
                            <div class="panel-body">
                                <div class="col-sm-5">
                                    <div class="form-group col-sm-12">
                                        <label class="col-sm-5 control-label"><span class="requires">*</span>档案号</label>
                                        <div class="col-sm-6">
                                            <input class="w180" type="text" name="recordId" <c:if test="${!empty recordId}">disabled="disabled"</c:if> value="${recordId}"/>
                                        </div>
                                    </div>
                                    <div class="form-group col-sm-12">
                                        <label class="col-sm-5 control-label"><span class="requires">*</span>建档日期</label>
                                        <div class="col-sm-6">
                                            <input id="start" name="recordDate" disabled="disabled" class="J_startTime w180" type="text" value="${recordDate}" />
                                        </div>
                                    </div>
                                    <div class="form-group col-sm-12">
                                        <label class="col-sm-5 control-label"><span class="requires">*</span>会员状态</label>
                                        <div class="col-sm-6">
                                            <select class="w180 form-control J_memberStatus" name="memberStatus">
                                                <option value="0" <c:if test="${memberStatus == 0}">selected="selected"</c:if>>在用</option>
                                                <option value="1" <c:if test="${memberStatus == 1}">selected="selected"</c:if>>锁定</option>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="form-group col-sm-12">
                                        <label class="col-sm-5 control-label"><span class="requires">*</span>会员截止日期</label>
                                        <div class="col-sm-6">
                                            <input id="end" name="deadLine" readonly="readonly" class="Wdate J_endTime w180" type="text" value="${deadLine}" onclick="WdatePicker({minDate:'#F{$dp.$D(\'start\')}'})"/>
                                        </div>
                                    </div>
                                    <div class="form-group col-sm-12">
                                        <label class="col-sm-5 control-label">累积积分</label>
                                        <div class="col-sm-6">
                                            <input class="w180 J_allPoints" type="text" name="allPoints" <c:if test="${isMember != 0}">disabled="disabled"</c:if>  value="${allPoints}"/>
                                        </div>
                                    </div>
                                    <div class="form-group col-sm-12">
                                        <label class="col-sm-5 control-label">住院情况</label>
                                        <div class="col-sm-6">
                                            <textarea class="w180" name="hospitalization" rows="1">${hospitalization}</textarea>
                                        </div>
                                    </div>
                                </div>
                                <!-- 右侧内容start -->
                                <div class="col-sm-7">
                                    <div class="form-group col-sm-12">
                                        <label class="col-sm-3 control-label"><span class="requires">*</span>会员分组</label>
                                        <div class="col-sm-6">
                                            <select class="w180 form-control" name="memberGroupId">
                                                <c:forEach items="${memberGroupList}" var="memberGroup">
                                                    <option value="${memberGroup.id}" <c:if test="${memberGroupId == memberGroup.id}">selected="selected"</c:if>>${memberGroup.name}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="form-group col-sm-12">
                                        <label class="col-sm-3 control-label"><span class="requires">*</span>会员级别</label>
                                        <div class="col-sm-6">
                                            <select class="w180 form-control J_memberLevel" name="level">
                                                <c:forEach items="${levelList}" var="item">
                                                    <c:if test="${item.id != normalClient}">
                                                        <option value="${item.id}"<c:if test="${item.id == level}">selected="selected"</c:if>>${item.name}</option>
                                                    </c:if>
                                                </c:forEach>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="form-group col-sm-12">
                                        <label class="col-sm-3 control-label">当前积分</label>
                                        <div class="col-sm-6">
                                            <input class="w180 J_memberPoints" type="text" name="memberPoints" <c:if test="${isMember != 0}">disabled="disabled"</c:if> value="${memberPoints}"/>
                                            <a href="/admin/client/points/detailList?clientId=${id}">查看详情</a>
                                        </div>
                                    </div>
                                    <div class="form-group col-sm-12">
                                        <label class="col-sm-3 control-label">问卷次数</label>
                                        <div class="col-sm-6">
                                            <input class="w180" type="text" name="questCount" disabled="disabled" value="${questCount}"/>
                                            <a href="/admin/client/quest/detail?id=${id}">查看详情</a>
                                        </div>
                                    </div>
                                    <c:if test="${empty nextQuestTime}">
                                    <div class="form-group col-sm-12">
                                        <label class="col-sm-3 control-label"><span class="requires">*</span>下次问卷日期</label>
                                        <div class="col-sm-6">
                                            <input name="nextQuestTime"class="Wdate w180 J_nextQuestTime" type="text"  value="${nextQuestTime}" onclick="WdatePicker({minDate: '%y-%M-%d'})"/>
                                        </div>
                                    </div>
                                    </c:if>
                                    <div class="form-group col-sm-12">
                                        <label class="col-sm-3 control-label"><span class="requires">*</span>是否定点单位</label>
                                        <div class="col-sm-6">
                                            <select class="w180 form-control J_memberLevel" name="isFixed">
                                                <option value="0"<c:if test="${isFixed == 0}">selected="selected"</c:if>>非定点单位</option>
                                                <option value="1"<c:if test="${isFixed == 1}">selected="selected"</c:if>>定点单位</option>
                                            </select>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-12">
                        <div class="panel panel-info">
                            <div class="panel-heading">
                                <h4>患者画像</h4>
                            </div>
                            <div class="panel-body">
                                <div class="col-sm-12">
                                    <c:forEach var="list" items="${profileDtoList}">
                                        <c:if test='${list.isMultiple == 0}'>
                                            <div class="form-group col-sm-12">
                                                <div class="col-sm-10">
                                                    <label class="col-sm-2 control-label">${list.typeName}</label>
                                                    <input type="hidden" name="labelTypeId" value="${list.typeId}">
                                                    <div class="col-sm-5">
                                                        <input class="new999 J_radio" type="text" name="${list.typeName}" readonly="readonly" value="${list.itemName}"/>
                                                    </div>
                                                    <div class="col-sm-5">
                                                        <input class="new999 checkboxWidth margin-left-10" title="${list.itemList}" type="text" readonly="readonly" value="${list.itemList}"/>
                                                    </div>
                                                </div>
                                                <div class="col-sm-1">
                                                    <a href="#" class="btn btn-success J_selectRadio margin-left-10" data-toggle="modal" data-target="#radioDialog"><i class="fa fa-tags"></i>&nbsp;选择</a>
                                                </div>
                                            </div>
                                        </c:if>
                                        <c:if test='${list.isMultiple == 1}'>
                                            <div class="form-group col-sm-12">
                                                <div class="col-sm-10">
                                                    <label class="col-sm-2 control-label">${list.typeName}</label>
                                                    <input type="hidden" name="labelTypeId" value="${list.typeId}">
                                                    <div class="col-sm-5">
                                                        <textarea rows="1" class="J_checkBox new999" name="${list.typeName}" readonly="readonly" value="">${list.itemName}</textarea>
                                                    </div>
                                                    <div class="col-sm-5">
                                                        <textarea rows="1" class="new999 margin-left-10" readonly="readonly">${list.itemList}</textarea>
                                                    </div>
                                                </div>
                                                <div class="col-sm-1">
                                                    <a href="#" class="btn btn-success J_selectCheckbox margin-left-10" data-toggle="modal" data-target="#checkBoxDialog"><i class="fa fa-tags"></i>&nbsp;选择</a>
                                                </div>
                                            </div>
                                        </c:if>
                                    </c:forEach>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row margin-bottom-20">
                        <div class="col-sm-6 col-sm-offset-4">
                            <div class="btn-toolbar">
                                <button class="btn btn-success J_save" type="submit"><i class="fa fa-save"></i>&nbsp;保存</button>
                                <button class="btn btn-default J_reset" type="reset"><i class="fa fa-undo"></i>&nbsp;重置</button>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<jsp:include flush="true" page="/WEB-INF/views/admin/common/footer.jsp"></jsp:include>
<!--#include file="/html/common/footer.html"-->
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
<!-- 会员分组和会员等级验证弹框 -->
<div class="modal fade" id="memberDialog" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">提示信息</h4>
            </div>
            <div class="modal-body">
                <p>会员级别为必填项!</p>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-success" data-dismiss="modal">确定</button>
            </div>
        </div>
    </div>
</div>
<!-- 修改会员状态弹出框 -->
<div class="modal fade" id="modalDialog" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">提示信息</h4>
            </div>
            <div class="modal-body">
                <p>是否调整会员状态，锁定后关于此患者待办任务将不会再提醒!</p>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-success" data-dismiss="modal">确定</button>
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
                <h4 class="modal-title">选择信息</h4>
            </div>
            <div class="modal-body">
                <form class="J_radioForm">
                    <label class="J_tagName"></label>
                    <select name="radioSelect" class="J_radioSelect form-controls">
                        <option value="-1">请选择</option>
                    </select>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-success J_radioDlg" data-dismiss="modal">确定</button>
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
                <button type="button" class="btn btn-success J_checkBoxDlg" data-dismiss="modal">确定</button>
            </div>
        </div>
    </div>
</div>
<script src="/js/tool/calendar/WdatePicker.js"></script>
<script src="/js/client-management/member-management/member-management-update.js"></script>
</body>
</html>
