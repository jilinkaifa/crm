<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.pandawork.net/tags" %>
<html>
<head>
    <title>患者基本信息管理</title>
    <jsp:include flush="true" page="/WEB-INF/views/admin/common/head.jsp"></jsp:include>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <style type="text/css">
        .listStyle{
            width: 106px;
            overflow: hidden;
            text-overflow: ellipsis;
            white-space: nowrap;
            font-weight: normal;
            cursor:pointer;
        }
    </style>
</head>
<body>
<jsp:include flush="true" page="/WEB-INF/views/admin/common/header.jsp"></jsp:include>
<script src="/js/tool/ajaxFileUpload/ajaxfileupload.js"></script>
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
                            <a href="/admin/client/basic">基本信息管理</a>
                        </li>
                        <li class="active">患者管理</li>
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
                                    <label class="col-sm-1 control-label">姓名</label>
                                    <div class="col-sm-2">
                                        <input id="J_selectName" type="text" class="form-control w180" autocomplete="off" name="name"/>
                                        <%--<select id="editable-select1" class="form-control J_selectName w180" name="name"></select>--%>
                                    </div>
                                    <label class="col-sm-2 control-label">身份证号</label>
                                    <div class="col-sm-2">
                                        <input id="J_selectId" type="text" class="form-control w180" autocomplete="off" name="idCardNum"/>
                                    </div>
                                    <label class="col-sm-2 control-label">电话</label>
                                    <div class="col-sm-2">
                                        <input id="J_selectPhone" type="text" class="form-control w180" autocomplete="off" name="tel"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-1 control-label">用户类型</label>
                                    <div class="col-sm-2">
                                        <select name="dicType" class="form-control w180">
                                            <option value="">请选择</option>
                                            <c:forEach items="${dicTypeList}" var="dicType">
                                            <option value="${dicType.id}">${dicType.name}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <label class="col-sm-2 control-label">医保类型</label>
                                    <div class="col-sm-2">
                                        <select class="form-control w180" name="dicMCIType">
                                            <option value="">请选择</option>
                                            <c:forEach items="${mciTypeList}" var="dicMciType">
                                                <option value="${dicMciType.id}">${dicMciType.name}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <label class="col-sm-2 control-label">累计消费金额大于</label>
                                    <div class="col-sm-2">
                                        <input type="text" class="col-sm-2 form-control w180" name="allCost">
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-sm-12 margin-bottom-10 margin-top--10 padding-left">
                            <shiro:checkPermission name="Admin:Client:Basic:List">
                                <button class="btn btn-success btn-radius-no J_search" type="button"><i class="fa fa-search"></i>&nbsp;搜索</button>
                            </shiro:checkPermission>
                            <shiro:checkPermission name="Admin:Client:Basic:New">
                                <a href="/admin/client/basic/add" class="btn btn-success margin-left-15 btn-radius-no btn-green"><i class="fa fa-plus"></i>&nbsp;新增</a>
                                <a href="" class="btn btn-success margin-left-15 btn-radius-no" data-toggle="modal" data-target="#exportDialog"><i class="fa fa-sign-in"></i>&nbsp;批量导入</a>
                                <a href="/admin/client/basic/exportTemplate" class="btn btn-success margin-left-15 btn-radius-no" target="_blank"><i class="fa fa-download"></i>&nbsp;模板下载</a>
                            </shiro:checkPermission>
                        </div>
                    </form>
                </div>
                <div class="col-sm-12">
                    <div class="panel panel-info">
                        <!-- <div class="panel-heading">
                            <h4>用户列表</h4>
                        </div> -->
                        <div class="panel-body">
                            <div class="table-responsive">
                                <table class="table table-hover table-bordered table-font">
                                    <thead>
                                    <tr>
                                        <th>序号</th>
                                        <th class="control-width width1">姓名</th>
                                        <th>性别</th>
                                        <th>身份证号</th>
                                        <th>电话</th>
                                        <th class="listStyle">诊断</th>
                                        <th>用户类型</th>
                                        <th>医保类型</th>
                                        <th>累计消费金额</th>
                                        <th>操作</th>
                                    </tr>
                                    </thead>
                                    <tbody id="J_template"></tbody>
                                </table>
                                <div class="pull-left record">
                                    共<span class="J_record"></span>条记录
                                </div>
                                <div class="pull-right"><ul id="pageLimit"></ul></div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<jsp:include flush="true" page="/WEB-INF/views/admin/common/footer.jsp"></jsp:include>
<!-- 存储id，表达式，描述 -->
<input class="hidId" type="hidden"></input>
<!-- <input class="hidExp" type="hidden"></input>
<input class="hidDes" type="hidden"></input> -->
<!-- 导入数据对话框 -->
<div class="modal fade" id="exportDialog" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" >批量导入</h4>
            </div>
            <div class="modal-body">
                <form action="" class="form-horizontal">
                    <input type="file" name="fileToUpload" id="fileToUpload" size="300" style="width:300px">
                    <br>
                    <div id="loadingDiv">
                    </div>
                    <input type="button" name="btnUpload" class="J_import" value="上 传" id="btnUpload" style="width:60px" />
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-success" data-dismiss="modal">确定</button>
            </div>
        </div>
    </div>
</div>
<!-- 删除对话框 -->
<div class="modal fade" id="delDialog" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel">提示信息</h4>
            </div>
            <div class="modal-body">
                <p>确定删除该患者信息吗？</p>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-success J_delDlg">确定</button>
            </div>
        </div>
    </div>
</div>
<!--#include file="/html/common/footer.html"-->
<script src="/js/client-management/basic-info-management/basic-info-management-list.js"></script>
</body>
</html>
