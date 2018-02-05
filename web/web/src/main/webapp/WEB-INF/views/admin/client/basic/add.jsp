<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.pandawork.net/tags" %>
<html>
<head>
    <title>新增患者</title>
    <jsp:include flush="true" page="/WEB-INF/views/admin/common/head.jsp"></jsp:include>
</head>
<body>
<jsp:include flush="true" page="/WEB-INF/views/admin/common/header.jsp"></jsp:include>
<div class="page clearfix">
    <div class="holder">
        <div class="container">
            <!-- Start content -->
            <!-- 头部 -->
            <div class="row">
                <div class="col-sm-12">
                    <ol class="breadcrumb">
                        <li><a href="/admin"><i class="fa fa-home"></i>&nbsp;首页</a></li>
                        <li><a href="/admin/client/basic">基础信息管理</a></li>
                        <li class="active">信息添加</li>
                    </ol>
                </div>
                <!-- 蓝色条 -->
                <div class="col-sm-12 margin-top--10">
                    <div class="panel panel-info">
                        <div class="panel-heading">
                            <h4>添加</h4>
                        </div>
                        <div class="panel-body">
                            <form class="form-horizontal J_tableForm" method="get" action="" autocomplete="off">
                                <input type="hidden" name='id' class="J_tableId" value="1"/>
                                <div class="container">
                                    <!-- 左侧内容start -->
                                    <div class="col-sm-6">
                                        <div class="form-group">
                                            <label class="col-sm-4 control-label">
                                                <span class="requires">*</span>姓名
                                            </label>
                                            <div class="col-sm-6">
                                                <input class="w200 J_userName" type="text" name="name" value=""/>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-sm-4 control-label">
                                                <span class="requires">*</span>电话
                                            </label>
                                            <div class="col-sm-6">
                                                <input class="w200 J_phoneNum" type="text" name="tel" value=""/>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-sm-4 control-label">
                                                <span class="requires"></span>性别
                                            </label>
                                            <div class="col-sm-6">
                                                <select class="form-control J_tableArea w200" name="gender">
                                                    <option value="">请选择</option>
                                                    <option value="1">男</option>
                                                    <option value="2">女</option>
                                                    <option value="3">其他</option>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-sm-4 control-label">
                                                <span class="requires"></span>身高
                                            </label>
                                            <div class="col-sm-6">
                                                <input class="w200" type="text" name="height">&nbsp;cm
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-sm-4 control-label">
                                                <span class="requires"></span>住址
                                            </label>
                                            <div class="col-sm-6">
                                                <input class="w200" type="text" name="address">
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-sm-4 control-label">
                                                <span class="requires"></span>联系人
                                            </label>
                                            <div class="col-sm-6">
                                                <input class="w200 J_secContact" type="text" name="secContact">
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-sm-4 control-label">
                                                <span class="requires"></span>医保类型
                                            </label>
                                            <div class="col-sm-6">
                                                <select class="form-control J_tableArea w200" name="dicMCIType">
                                                    <option value="">请选择..</option>
                                                    <c:forEach items="${dicMCITypeList}" var="dicMCIType">
                                                        <option value="${dicMCIType.id}">${dicMCIType.name}</option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-sm-4 control-label">
                                                <span class="requires"></span>医保卡号
                                            </label>
                                            <div class="col-sm-6">
                                                <input class="w200" type="text" name="MCINum">
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-sm-4 control-label">
                                                <span class="requires"></span>用户类型
                                            </label>
                                            <div class="col-sm-6">
                                                <select class="form-control J_tableArea w200" readonly="readonly" name="dicType">
                                                        <option value="${dicType.id}">${dicType.name}</option>
                                                </select>
                                            </div>
                                        </div>
                                    </div>
                                    <!-- 右侧内容start -->
                                    <div class="col-sm-6">
                                        <div class="form-group">
                                            <label class="col-sm-3 control-label">
                                                <span class="requires"></span>身份证号码
                                            </label>
                                            <div class="col-sm-6">
                                                <input class="w200 J_idcardNum" type="text" name="idCardNum"/>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-sm-3 control-label">
                                                <span class="requires"></span>年龄
                                            </label>
                                            <div class="col-sm-6">
                                                <input class="w200 J_age" type="text" readonly="readonly" name="age" >
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-sm-3 control-label">
                                                <span class="requires"></span>民族
                                            </label>
                                            <div class="col-sm-6">
                                                <select class="form-control J_tableArea w200" name="dicNation">
                                                    <option value="">请选择..</option>
                                                    <c:forEach items="${dicNationList}" var="nation">
                                                        <option value="${nation.id}">${nation.name}</option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-sm-3 control-label">
                                                <span class="requires"></span>体重
                                            </label>
                                            <div class="col-sm-6">
                                                <input class="w200" type="text" name="weight" >&nbsp;kg
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-sm-3 control-label">
                                                <span class="requires"></span>工作单位
                                            </label>
                                            <div class="col-sm-6">
                                                <input class="w200" type="text" name="company" >
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-sm-3 control-label">
                                                <span class="requires"></span>联系人电话
                                            </label>
                                            <div class="col-sm-6">
                                                <input class="w200 J_secTel" type="text" name="secTel" >
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-sm-3 control-label">
                                                <span class="requires"></span>联系人关系
                                            </label>
                                            <div class="col-sm-6">
                                                <select class="form-control J_tableArea J_dicSecRelation w200" name="dicSecRelation">
                                                    <option value="">请选择..</option>
                                                    <c:forEach items="${dicSecRelationList}" var="secRelation">
                                                        <option value="${secRelation.id}">${secRelation.name}</option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-sm-3 control-label">
                                                <span class="requires"></span>诊断
                                            </label>
                                            <div class="col-sm-6">
                                                <textarea class="w200  form-control" rows="3" name="diagnoseType"></textarea>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </form>
                        </div>
                        <div class="panel-footer">
                            <div class="row">
                                <div class="col-sm-6 col-sm-offset-4">
                                    <div class="btn-toolbar">
                                        <button class="btn-success btn J_save" type="submit">
                                            <i class="fa fa-save"></i>&nbsp;保存
                                        </button>
                                        <button class="btn-default btn J_reset" type="reset">
                                            <i class="fa fa-undo"></i>&nbsp;重置
                                        </button>
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
<script type="text/javascript" src="/js/client-management/basic-info-management/basic-info-management-add.js"></script>
</body>
</html>
