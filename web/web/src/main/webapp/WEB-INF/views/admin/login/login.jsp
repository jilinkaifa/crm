<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>用户登录</title>
    <jsp:include flush="true" page="/WEB-INF/views/admin/common/head.jsp"></jsp:include>
    <link rel="stylesheet" href="/css/login/login.css"/>
    <script type="text/javascript" src="/js/login/login.js"></script>
</head>
<body>
<div class="page clearfix">
    <div class="row">
        <div class="col-sm-12 title">
            <h1>正大国际医院CRM客户关系管理系统</h1>
        </div>
        <div class="col-sm-12 login-all">
            <div class="col-sm-5 col-sm-offset-2 login-bg">

            </div>
            <div class="col-sm-4 login-body login-box">
                <form  id="J_form">
                    <div class="form-group">
                        <div class="input-group">
                                <span class="input-group-addon">
                                    <span class="fa fa-user"></span>
                                </span>
                            <input class="J_username form-control" type="text" placeholder="请填写用户的账号" name="loginName" value="">
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="input-group">
                                <span class="input-group-addon">
                                    <span class="fa fa-lock"></span>
                                </span>
                            <input class="J_pwd form-control" type="password" placeholder="请填写登录密码" name="pwd" value="">
                        </div>
                    </div>
                    <div class="entry">
                        <button class="J_submitBtn btn btn-success" type="submit">
                            登录
                        </button>
                        <button class="J_resetBtn btn btn-default reset-btn" type="reset">
                            重置
                        </button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
</body>
</html>


