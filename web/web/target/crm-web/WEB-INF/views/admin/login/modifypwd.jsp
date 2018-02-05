<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>修改密码</title>
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
                        <li class="active">修改个人信息</li>
                    </ol>
                </div>
                <div class="col-sm-12 margin-top--10">
                    <form action="" class="form-horizontal J_searchForm">
                        <div class="panel panel-info">
                            <div class="panel-heading">
                                <h4>修改个人信息</h4>
                            </div>
                            <div class="panel-body">
                                <div class="form-group">
                                    <label class="col-sm-3 control-label"><span class="requires">*</span>用户名</label>
                                    <div class="col-sm-2">
                                        <input type="text" class="w190 form-control" name="name" readonly="readonly" value="${loginName}">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-3 control-label"><span class="requires">*</span>原密码</label>
                                    <div class="col-sm-2">
                                        <input type="password" class="w190 J_oldPassword form-control" name="oldPassword" value="">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-3 control-label"><span class="requires">*</span>新密码</label>
                                    <div class="col-sm-2">
                                        <input type="password" class="w190  J_newPassword form-control" name="newPassword" value="">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-3 control-label"><span class="requires">*</span>确认密码</label>
                                    <div class="col-sm-2">
                                        <input type="password" class="w190 J_confirmPassword form-control" name="confirmPassword" value="">
                                    </div>
                                </div>
                            </div>
                            <div class="panel-footer">
                                <div class="row">
                                    <div class="col-sm-6 col-sm-offset-4">
                                        <div class="btn-toolbar">
                                            <button class="btn btn-success btn-radius-no J_save" type="submit"><i class="fa fa-save"></i>&nbsp;保存</button>
                                            <button class="btn btn-default J_reset" type="reset"><i class="fa fa-undo"></i>&nbsp;重置</button>
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
</div>
<jsp:include flush="true" page="/WEB-INF/views/admin/common/footer.jsp"></jsp:include>
<!-- 操作失败对话框 -->
<div class="modal fade" id="modalDialog" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">提示信息</h4>
            </div>
            <div class="modal-body"></div>
            <div class="modal-footer">
                <button type="button" class="btn btn-success confirm" data-dismiss="modal">确定</button>
            </div>
        </div>
    </div>
</div>
<script src="/js/login/modify-password.js"></script>
</body>
</html>
