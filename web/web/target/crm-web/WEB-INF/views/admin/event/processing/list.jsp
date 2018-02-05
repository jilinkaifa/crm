<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>进行中活动列表</title>
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
                            <a href="/admin/event/prepare/list">活动管理</a>
                        </li>
                        <li class="active">进行中活动管理</li>
                    </ol>
                </div>
                <div class="col-sm-12 margin-top--10">
                    <form action="" class="form-horizontal J_searchForm">
                        <div class="panel panel-info">
                            <div class="panel-heading">
                                <h4>搜索</h4>
                            </div>
                            <div class="panel-body">
                                <div class="form-group">
                                    <label class="control-label col-sm-1">姓名</label>
                                    <div class="col-sm-2">
                                        <input id="J_selectName" type="text" class="form-control w180" autocomplete="off" name="createdPartyName"/>
                                    </div>
                                    <label class="col-sm-2 control-label">身份证号</label>
                                    <div class="col-sm-2">
                                        <input id="J_selectId" type="text" class="form-control w180" autocomplete="off" name="idcardNum"/>
                                    </div>
                                    <label class="col-sm-2 control-label">活动名称</label>
                                    <div class="col-sm-2">
                                        <input id="J_selectActivity" type="text" class="form-control w180" autocomplete="off" name="name">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-1 control-label">活动类型</label>
                                    <div class="col-sm-2">
                                        <select name="type" class="form-control w180">
                                            <option value="">---请选择---</option>
                                            <option value="1">会员关怀型</option>
                                            <option value="2">营销型</option>
                                        </select>
                                    </div>
                                    <label class="col-sm-2 control-label">活动级别</label>
                                    <div class="col-sm-2">
                                        <select class="form-control w180" name="level">
                                            <option value="">---请选择---</option>
                                            <option value="1">一级</option>
                                            <option value="2">二级</option>
                                            <option value="3">三级</option>
                                        </select>
                                    </div>
                                    <label class="col-sm-2 control-label">活动状态</label>
                                    <div class="col-sm-2">
                                        <select class="form-control w180" name="status">
                                            <option value="">---请选择---</option>
                                            <option value="1">通知期</option>
                                            <option value="2">执行期</option>
                                        </select>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-sm-12 margin-bottom-10 margin-top--10 padding-left">
                            <button class="btn btn-success btn-radius-no J_search" type="button"><i class="fa fa-search"></i>&nbsp;搜索</button>
                        </div>
                    </form>
                </div>
                <div class="col-sm-12">
                    <div class="panel panel-info">
                        <div class="panel-body">
                            <div class="table-responsive">
                                <table class="table table-hover table-bordered">
                                    <thead class="small-padding">
                                    <tr>
                                        <th class="control-width width1">活动名称</th>
                                        <th>类型</th>
                                        <th>级别</th>
                                        <th>状态</th>
                                        <th>待通知人数</th>
                                        <th>本期开始时间</th>
                                        <th>本期结束时间</th>
                                        <th class="control-width width1">活动人员</th>
                                        <th>待完成人数</th>
                                        <th class="control-width width1">创建人</th>
                                        <th>创建时间</th>
                                        <th>操作</th>
                                    </tr>
                                    </thead>
                                    <tbody class="small-padding" id="J_template"></tbody>
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
<input class="hidId" type="hidden"/>
<!-- 暂停下一期对话框 -->
<div class="modal fade" id="stopDialog" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">提示信息</h4>
            </div>
            <div class="modal-body">
                <p>确定暂停下一期活动吗？</p>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-success J_stopDlg">确定</button>
            </div>
        </div>
    </div>
</div>
<!-- 暂停所有期活动对话框 -->
<div class="modal fade" id="stopAllDialog" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">提示信息</h4>
            </div>
            <div class="modal-body">
                <p>确定暂停所有期活动吗？</p>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-success J_stopAllDlg">确定</button>
            </div>
        </div>
    </div>
</div>
<!-- 操作失败对话框 -->
<div class="modal fade" id="modalDialog" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
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
<jsp:include flush="true" page="/WEB-INF/views/admin/common/footer.jsp"></jsp:include>
<script src="/js/e-crb/activities-management/activities-management-ing/activities-management-ing-list.js"></script>
</body>
</html>