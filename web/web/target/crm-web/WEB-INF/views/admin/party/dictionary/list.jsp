<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>字典值管理</title>
    <jsp:include flush="true" page="/WEB-INF/views/admin/common/head.jsp"></jsp:include>
    <script type="text/javascript" src="/js/tool/zTree/js/jquery.ztree.core.js"></script>
    <script type="text/javascript" src="/js/tool/zTree/js/jquery.ztree.excheck.js"></script>
    <script type="text/javascript" src="/js/tool/zTree/js/jquery.ztree.exedit.js"></script>
    <link rel="stylesheet" href="/js/tool/zTree/css/zTreeStyle/zTreeStyle.css" type="text/css">
    <link rel="stylesheet" href="/css/authority-management/dictionary-management.css" type="text/css">
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
                            <a href="/admin/party/security/group/list">系统管理</a>
                        </li>
                        <li class="active">字典值管理</li>
                    </ol>
                </div>
                <div class="col-sm-12 margin-top--10">
                    <div class="panel panel-info">
                        <div class="panel-heading">
                            <h4>字典值列表</h4>
                        </div>
                        <div class="panel-body">
                            <ul id="treeDemo" class="ztree"></ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<!--#include file="/html/common/footer.html"-->
<jsp:include flush="true" page="/WEB-INF/views/admin/common/footer.jsp"></jsp:include>
<script src="/js/authority-management/dictionary-management.js"></script>
</body>
</html>
