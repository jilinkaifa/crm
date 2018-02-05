<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>设置积分兑换规则</title>
    <link rel="stylesheet"  href="/css/client-management/accumulate-points-management/accumulate-points-management-rules.css"/>
    <jsp:include flush="true" page="/WEB-INF/views/admin/common/head.jsp"></jsp:include>
</head>
<body>
<jsp:include flush="true" page="/WEB-INF/views/admin/common/header.jsp"></jsp:include>
<div class="page clearfix">
    <div class="holder">
        <div class="container">
            <div class="row">
                <div class="col-sm-12 ">
                    <ol class="breadcrumb">
                        <li>
                            <a href="/admin"><i class="fa fa-home"></i>&nbsp;首页</a>
                        </li>
                        <li>
                            <a href="/admin/party/security/group/list">系统管理</a>
                        </li>
                        <li class="active">积分兑换规则</li>
                    </ol>
                    <input type="hidden" class="pageDataCount" value="10">
                </div>
                <div class="col-sm-12 margin-top--10">
                    <form class="form-horizontal  J_tableForm">
                        <div class="panel panel-info">
                            <div class="panel-heading">
                                <h4>兑换</h4>
                            </div>
                            <div class="panel-body">
                                <div class="form-group form-box">
                                    <span class="new180">
                                        <input type="text" value="1元" name="activity" readonly="readonly">
                                    </span>
                                    <span class="points-name">兑换</span>
                                    <span class=" new180 ">
                                        <input name="exchangePoints" type="text" value="${pointsConvert}" >
                                    </span>
                                    <span class="points">积分</span>
                                </div>
                            </div>
                            <div class="panel-footer">
                                <div class="row">
                                        <div class="col-sm-12 margin-bottom-10 margin-top--10 padding-left">
                                            <button type="submit"  class="btn-sm btn-success J_submit"><i class="fa fa-save"></i>&nbsp;确定</button>
                                            <button class="btn-default btn J_reset" type="reset"><i class="fa fa-undo"></i>&nbsp;重置</button>
                                        </div>
                                </div>
                            </div>
                        </div>

                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
<jsp:include flush="true" page="/WEB-INF/views/admin/common/footer.jsp"></jsp:include>
<!-- 兑换积分对话框 -->
<div class="modal fade" id="pointsDialog" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel">提示信息</h4>
            </div>
            <div class="modal-body">
                <p>确定修改积分比率吗？</p>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-success J_points ">确定</button>
            </div>
        </div>
    </div>
<!-- 操作失败对话框 -->
<div class="modal fade" id="tipDialog" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
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
                <button type="button" class="btn btn-scuuess" data-dismiss="modal">确定</button>
            </div>
        </div>
    </div>
</div>
<!--#include file="/html/common/footer.html"-->
<script src="/js/client-management/accumlate-points-management/accumlate-points-management-rules.js"></script>
</body>
</html>
