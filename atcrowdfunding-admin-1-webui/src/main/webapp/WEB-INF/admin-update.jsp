<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html lang="UTF-8">
<%@ include file="/WEB-INF/include-head.jsp" %>
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
                    <form:form action="admin/do/update.html" method="post"
                               modelAttribute="admin">
<%--                       模型里面包含的属性可以用 form：hidden --%>
                        <form:hidden path="id"/>
<%--                        模型不包含的不能用 ,获取请求参数中的 pageNum 更新完之后保持之前所在页码--%>
                        <input type="hidden" name="pageNum" value="${param.pageNum}">
                        <input type="hidden" name="keyword" value="${param.keyword}">


                        <div class="form-group">
                            <label>登录账号</label>
                            <form:input path="loginAcct" cssClass="form-control" cssStyle="width: 150px;"/>
                        </div>
                        <div class="form-group">
                            <label>登录密码</label>
                            <form:input path="userPswd" cssClass="form-control " cssStyle="width: 150px;"/>
                        </div>
                        <div class="form-group">
                            <label>用户昵称</label>
                            <form:input path="userName" cssClass="form-control" cssStyle="width: 150px;"/>
                        </div>

                        <div class="form-group">
                            <label>邮箱</label>
                            <form:input path="email" cssClass="form-control" cssStyle="width: 150px;"/>
                        </div>
                        <button type="submit" class="btn btn-success"><i class="glyphicon glyphicon-edit"></i> 修改</button>
                        <button type="reset" class="btn btn-danger"><i class="glyphicon glyphicon-refresh"></i> 重置</button>
                    </form:form>
                </div>
            </div>
        </div>
    </div>
</div>

</body>
</html>
