<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.pandawork.net/tags" %>
<html>
<head>
    <title>会员基本信息管理</title>
    <style type="text/css">
        .w90{
            width: 90% !important;
        }
    </style>
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
                            <a href="/admin/client/basic">患者管理</a>
                        </li>
                        <li class="active">会员管理</li>
                    </ol>
                    <%--<div class="alert alert-success J_tip" role="alert">刷新成功!</div>--%>
                    <input type="hidden" class="pageDataCount" value="${pageCount}">
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
                                        <input id="J_selectName" type="text" class="form-control w90" autocomplete="off" name="name"/>
                                    </div>
                                    <label class="col-sm-1 control-label">身份证号</label>
                                    <div class="col-sm-2">
                                        <input id="J_selectId" type="text" class="form-control w90" autocomplete="off" name="idCardNum"/>
                                    </div>
                                    <label class="col-sm-1 control-label">电话</label>
                                    <div class="col-sm-2">
                                        <input id="J_selectPhone" type="text" class="form-control w90" autocomplete="off" name="tel"/>
                                    </div>
                                    <label class="col-sm-1 control-label">会员分组</label>
                                    <div class="col-sm-2">
                                        <select class="col-sm-2 form-control w90" name="memberGroupId">
                                            <option value="">请选择</option>
                                            <c:forEach items="${memberGroupList}" var="memberGroup">
                                                <option value="${memberGroup.id}">${memberGroup.name}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-1 control-label">会员状态</label>
                                    <div class="col-sm-2">
                                        <select class="col-sm-2 form-control w90" name="memberStatus">
                                            <option value="">请选择</option>
                                            <option value="0">在用</option>
                                            <option value="1">锁定</option>
                                        </select>
                                    </div>
                                    <label class="col-sm-1 control-label">会员级别</label>
                                    <div class="col-sm-2">
                                        <select class="col-sm-2 form-control w90" name="level">
                                            <option value="">请选择</option>
                                            <c:forEach items="${levelList}" var="item">
                                                <c:if test="${item.id != normalClient}">
                                                <option value="${item.id}">${item.name}</option>
                                                </c:if>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <label class="col-sm-1 control-label">定点单位</label>
                                    <div class="col-sm-2">
                                        <select class="col-sm-2 form-control w90" name="isFixed">
                                            <option value="">请选择</option>
                                            <option value="1">是</option>
                                            <option value="0">否</option>
                                            </select>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-sm-12 margin-bottom-10 margin-top--10 padding-left">
                            <shiro:checkPermission name="Admin:Client:Member:List">
                                <button class="btn btn-success btn-radius-no J_search" type="submit"><i class="fa fa-search"></i>&nbsp;搜索</button>
                            </shiro:checkPermission>
                            <!-- <a href="#" class="btn btn-success margin-left-15 btn-radius-no btn-green"><i class="fa fa-plus"></i>&nbsp;新增会员</a> -->
                        </div>
                    </form>
                </div>
                <div class="col-sm-12">
                    <div class="panel panel-info">
                       <!--  <div class="panel-heading">
                            <h4>会员管理列表</h4>
                        </div> -->
                        <div class="panel-body">
                            <div class="table-responsive">
                                <table class="table table-hover table-bordered">
                                    <thead>
                                    <tr>
                                        <th>序号</th>
                                        <th class="control-width width1">姓名</th>
                                        <th>身份证号</th>
                                        <th>电话</th>
                                        <th>档案号</th>
                                        <th>会员级别</th>
                                        <th>累计消费金额</th>
                                        <th>建档日期</th>
                                        <th>会员状态</th>
                                        <th>会员截止日期</th>
                                        <th>是否定点单位</th>
                                        <th>操作</th>
                                    </tr>
                                    </thead>
                                    <tbody id="J_template">
                                    </tbody>
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
<script src="/js/client-management/member-management/member-management-list.js"></script>
</body>
</html>
