<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="shiro" uri="http://shiro.pandawork.net/tags" %>
<!DOCTYPE html>
<html>
<head>
    <title>画像功能</title>
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
                        <li class="active">画像功能</li>
                    </ol>
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
                                        <input id="J_selectName" type="text" class="form-control w180" autocomplete="off" name="name"/>
                                    </div>
                                    <label class="col-sm-2 control-label">身份证号</label>
                                    <div class="col-sm-2">
                                        <input id="J_selectId" type="text" class="form-control w180" autocomplete="off" name="idCardNum"/>
                                    </div>
                                    <label class="col-sm-2 control-label">用户类型</label>
                                    <div class="col-sm-2">
                                        <select id="editable-select3" class="form-control J_selectPhone w180" name="level">
                                            <option value="">请选择</option>
                                            <c:forEach var="list" items="${dictionaryList}">
                                                <option value="${list.id}">${list.name}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-sm-12 margin-bottom-10 margin-top--10 padding-left">
                            <button type="submit"  class="btn J_search btn-success"><i class="fa fa-search"></i>&nbsp;查询</button>
                            <shiro:checkPermission name="Admin:Profile:Portrayal:Update">
                            <a href="javascript:;" class="btn btn-success J_editAll"><i class="fa fa-pencil"></i>&nbsp;批量修改</a>
                            </shiro:checkPermission>
                        </div>
                    </form>
                </div>
                <div class="col-sm-12">
                    <div class="panel panel-info">
                        <div class="panel-body">
                            <div class="table-responsive">
                                <table class="table table-hover table-bordered">
                                    <thead>
                                    <tr>
                                        <th><input type="checkbox" class="J_selectAll"></th>
                                        <th class="control-width width1">姓名</th>
                                        <th>性别</th>
                                        <th>身份证号</th>
                                        <th>电话</th>
                                        <th>用户类型</th>
                                        <th>医保类型</th>
                                        <th>累计消费金额</th>
                                        <th>操作人</th>
                                        <th>操作时间</th>
                                        <th>操作</th>
                                    </tr>
                                    </thead>
                                    <tbody id="J_template">
                                    </tbody>
                                </table>
                            </div>
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
<!-- 批量修改选择为空对话框 -->
<div class="modal fade" id="editTipDialog" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
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
<jsp:include flush="true" page="/WEB-INF/views/admin/common/footer.jsp"></jsp:include>
<script src="/js/portrayal-management/portrayal-function/portrayal-function-list.js"></script>
</body>
</html>
