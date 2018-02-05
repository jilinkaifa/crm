<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>会员详情</title>
    <jsp:include flush="true" page="/WEB-INF/views/admin/common/head.jsp"></jsp:include>
    <link rel="stylesheet"  href="/css/client-management/member-management/member-management.css"/>

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
                <form class="J_form" method="" action="">
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
                                            <input class="w180" type="text" name="name" readonly="readonly" value="${name}"/>
                                        </div>
                                    </div>
                                    <div class="form-group col-sm-12">
                                        <label class="col-sm-5 control-label">身份证号</label>
                                        <div class="col-sm-6">
                                            <input class="w180" type="text" name="idCardNum" readonly="readonly" value="${idCardNum}"/>
                                        </div>
                                    </div>
                                    <div class="form-group col-sm-12">
                                        <label class="col-sm-5 control-label">性别</label>
                                        <div class="col-sm-6">
                                            <input class="w180" type="text" name="gender" readonly="readonly" value="${gender}"/>
                                        </div>
                                    </div>
                                    <div class="form-group col-sm-12">
                                        <label class="col-sm-5 control-label">年龄</label>
                                        <div class="col-sm-6">
                                            <input class="w180" type="text" name="age" readonly="readonly" value="${age}"/>
                                        </div>
                                    </div>
                                    <div class="form-group col-sm-12">
                                        <label class="col-sm-5 control-label">联系电话</label>
                                        <div class="col-sm-6">
                                            <input class="w180" type="text" name="secTel" readonly="readonly" value="${tel}"/>
                                        </div>
                                    </div>
                                    <div class="form-group col-sm-12">
                                        <label class="col-sm-5 control-label">联系人</label>
                                        <div class="col-sm-6">
                                            <input class="w180" type="text" name="secContact" readonly="readonly" value="${secContact}"/>
                                        </div>
                                    </div>
                                    <div class="form-group col-sm-12">
                                        <label class="col-sm-5 control-label">联系人关系</label>
                                        <div class="col-sm-6">
                                            <input class="w180" type="text" name="dicSecRelation" readonly="readonly" value="${dicSecRelation}"/>
                                        </div>
                                    </div>
                                    <div class="form-group col-sm-12">
                                        <label class="col-sm-5 control-label">用户类型</label>
                                        <div class="col-sm-6">
                                            <input class="w180" type="text" name="dicType" readonly="readonly" value="普通用户"/>
                                        </div>
                                    </div>
                                    <div class="form-group col-sm-12">
                                        <label class="col-sm-5 control-label">诊断</label>
                                        <div class="col-sm-6">
                                            <textarea class="w180" name="diagnoseType" readonly="readonly" rows="1">${diagnoseType}</textarea>
                                        </div>
                                    </div>
                                </div>
                                <!-- 右侧内容start -->
                                <div class="col-sm-7">
                                    <div class="form-group col-sm-12">
                                        <label class="col-sm-3 control-label">身高</label>
                                        <div class="col-sm-6">
                                            <input class="w180" type="text" name="height" readonly="readonly" value="${height}"/>
                                        </div>
                                    </div>
                                    <div class="form-group col-sm-12">
                                        <label class="col-sm-3 control-label">体重</label>
                                        <div class="col-sm-6">
                                            <input class="w180" type="text" name="weight" readonly="readonly" value="${weight}"/>
                                        </div>
                                    </div>
                                    <div class="form-group col-sm-12">
                                        <label class="col-sm-3 control-label">民族</label>
                                        <div class="col-sm-6">
                                            <input class="w180" type="text" name="dicNation" readonly="readonly" value="${dicNation}"/>
                                        </div>
                                    </div>
                                    <div class="form-group col-sm-12">
                                        <label class="col-sm-3 control-label">工作单位</label>
                                        <div class="col-sm-6">
                                            <input class="w180" type="text" name="company" readonly="readonly" value="${company}"/>
                                        </div>
                                    </div>
                                    <div class="form-group col-sm-12">
                                        <label class="col-sm-3 control-label">住址</label>
                                        <div class="col-sm-6">
                                            <input class="w180" type="text" name="address" readonly="readonly" value="${address}"/>
                                        </div>
                                    </div>
                                    <div class="form-group col-sm-12">
                                        <label class="col-sm-3 control-label">医保类型</label>
                                        <div class="col-sm-6">
                                            <input class="w180" type="text" name="dicMciType" readonly="readonly" value="${dicMCIType}"/>
                                        </div>
                                    </div>
                                    <div class="form-group col-sm-12">
                                        <label class="col-sm-3 control-label">医保卡号</label>
                                        <div class="col-sm-6">
                                            <input class="w180" type="text" name="mciNum" readonly="readonly" value="${MCINum}"/>
                                        </div>
                                    </div>
                                    <div class="form-group col-sm-12">
                                        <label class="col-sm-3 control-label">累计消费金额</label>
                                        <div class="col-sm-6">
                                            <input class="w180" type="text" name="allCost" readonly="readonly" value="${allCost}"/>
                                            <a href="/admin/client/visit/detailList?clientId=${id}">查看详情</a>
                                        </div>
                                    </div>
                                    <div class="form-group col-sm-12">
                                        <label class="col-sm-3 control-label">参与活动数量</label>
                                        <div class="col-sm-6">
                                            <input class="w180" type="text" name="eventCount" readonly="readonly" value="${eventCount}"/>
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
                                            <input class="w180" type="text" name="recordId" readonly="readonly" value="${recordId}"/>
                                        </div>
                                    </div>
                                    <div class="form-group col-sm-12">
                                        <label class="col-sm-5 control-label"><span class="requires">*</span>建档日期</label>
                                        <div class="col-sm-6">
                                            <input id="start" name="recordDate" readonly="readonly" class="J_startTime w180" type="text" value="${recordDate}" />
                                        </div>
                                    </div>
                                    <div class="form-group col-sm-12">
                                        <label class="col-sm-5 control-label"><span class="requires">*</span>会员状态</label>
                                        <div class="col-sm-6">
                                            <select class="w180 form-control J_memberStatus" disabled="disabled" name="memberStatus">
                                                <option value="">${memberStatus}</option>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="form-group col-sm-12">
                                        <label class="col-sm-5 control-label">会员截止日期</label>
                                        <div class="col-sm-6">
                                            <input id="end" name="memberDeadline" readonly="readonly" class="J_startTime w180" type="text" value="${deadLine}">
                                        </div>
                                    </div>
                                    <div class="form-group col-sm-12">
                                        <label class="col-sm-5 control-label">累积积分</label>
                                        <div class="col-sm-6">
                                            <input class="w180" type="text" name="memberPoints" readonly="readonly" value="${allPoints}"/>
                                        </div>
                                    </div>
                                    <div class="form-group col-sm-12">
                                        <label class="col-sm-5 control-label">住院情况</label>
                                        <div class="col-sm-6">
                                            <textarea class="w180" name="hospitalization" readonly="readonly" rows="1">${hospitalization}</textarea>
                                        </div>
                                    </div>
                                </div>
                                <!-- 右侧内容start -->
                                <div class="col-sm-7">
                                    <div class="form-group col-sm-12">
                                        <label class="col-sm-3 control-label"><span class="requires">*</span>会员分组</label>
                                        <div class="col-sm-6">
                                            <select class="w180 form-control" disabled="disabled" name="memberGroupId">
                                                <option value="">${memberGroup}</option>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="form-group col-sm-12">
                                        <label class="col-sm-3 control-label"><span class="requires">*</span>会员级别</label>
                                        <div class="col-sm-6">
                                            <select class="w180 form-control" disabled="disabled" name="level">
                                                <option value="">${level}</option>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="form-group col-sm-12">
                                        <label class="col-sm-3 control-label">当前积分</label>
                                        <div class="col-sm-6">
                                            <input class="w180" type="text" name="currentIntegral" readonly="readonly" value="${memberPoints}"/>
                                            <a href="/admin/client/points/detailList?clientId=${id}">查看详情</a>
                                        </div>
                                    </div>
                                    <div class="form-group col-sm-12">
                                        <label class="col-sm-3 control-label">问卷次数</label>
                                        <div class="col-sm-6">
                                            <input class="w180" type="text" name="questCount" readonly="readonly" value="${questCount}"/>
                                            <a href="/admin/client/quest/detail?id=${id}">查看详情</a>
                                        </div>
                                    </div>
                                    <div class="form-group col-sm-12">
                                        <label class="col-sm-3 control-label">是否定点单位</label>
                                        <div class="col-sm-6">
                                            <select class="w180 form-control" disabled="disabled" name="isFixed">
                                                <option value="">${isFixed}</option>
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
                                                <input class="new999 J_radio" type="text" name="nation" readonly="readonly" value="${list.itemName}"/>
                                            </div>
                                            <div class="col-sm-5">
                                                <input class="new999 checkboxWidth margin-left-10" title="${list.itemList}" type="text" readonly="readonly" value="${list.itemList}"/>
                                            </div>
                                        </div>
                                    </div>
                                        </c:if>
                                        <c:if test='${list.isMultiple == 1}'>
                                    <div class="form-group col-sm-12">
                                        <div class="col-sm-10">
                                            <label class="col-sm-2 control-label">${list.typeName}</label>
                                            <input type="hidden" name="labelTypeId" value="${list.typeId}">
                                            <div class="col-sm-5">
                                                <textarea rows="1" class="J_checkBox new999 " name="drugAllergy" readonly="readonly" value="">${list.itemName}</textarea>
                                            </div>
                                            <div class="col-sm-5">
                                                <textarea rows="1" title="" class="new999 margin-left-10" readonly="readonly">${list.itemList}</textarea>
                                            </div>
                                        </div>
                                    </div>
                                        </c:if>
                                    </c:forEach>
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
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<jsp:include flush="true" page="/WEB-INF/views/admin/common/footer.jsp"></jsp:include>
</body>
</html>
