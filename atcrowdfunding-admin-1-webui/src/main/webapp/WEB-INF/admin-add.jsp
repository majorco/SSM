<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="UTF-8">
<head>
    <%@ include file="/WEB-INF/include-head.jsp" %>
</head>

<body>

<%@ include file="/WEB-INF/include-nav.jsp" %>
<div class="container-fluid">
    <div class="row">
        <%@ include file="/WEB-INF/include-sidebar.jsp" %>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <ol class="breadcrumb">
                <li><a href="admin/to/main/page.html">首页</a></li>
                <li><a href="admin/query/for/search.html?pageNum=${param.pageNum}&keyword=${param.keyword}">数据列表</a></li>
                <li class="active">新增</li>
            </ol>
            <div class="panel panel-default">
                <div class="panel-heading">表单数据</div>
                <div class="panel-body">
                    <form role="form" action="admin/do/add.html" method="post">
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        <div class="form-group">
                            <label for="exampleInputloginAcct">登录账号</label>
                            <input
<%--                            <input style="width:111px;height:111px">--%>
                                    style="width:150px;"
                                    name="loginAcct"
                                    type="text"
                                    class="form-control"
                                    id="exampleInputloginAcct"
                                    placeholder="请输入登录账号">
                        </div>
                        <div class="form-group">
                            <label for="exampleInputuserPswd">登录密码</label>
                            <input
                                    style="width:150px;"
                                    name="userPswd"
                                    type="password"
                                    class="form-control"
                                    id="exampleInputuserPswd"
                                    placeholder="请输入登录密码">
                        </div>
                        <div class="form-group">
                            <label for="exampleInputUserName">用户昵称</label>
                            <input
                                    style="width:150px;"
                                    name="userName"
                                    type="text"
                                    class="form-control"
                                    id="exampleInputUserName"
                                    placeholder="请输入用户昵称">
                        </div>
                        <div class="form-group">
                            <label for="exampleInputEmail1">邮箱地址</label>
                            <input
                                    style="width:150px;"
                                    name="email"
                                    type="email"
                                    class="form-control"
                                    id="exampleInputEmail1"
                                    placeholder="xxxx@xxxx.com">
                        </div>
                        <button type="submit" class="btn btn-success"><i class="glyphicon glyphicon-edit"></i> 增加</button>
                        <button type="reset" class="btn btn-danger"><i class="glyphicon glyphicon-refresh"></i> 重置</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

</body>
</html>
