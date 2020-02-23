<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="UTF-8">
<%@ include file="/WEB-INF/include-head.jsp"%>
<link rel="stylesheet" href="css/pagination.css" />
<script type="text/javascript" src="jquery/jquery-pagination.js"></script>
<%--引入外部的 JavaScript 文件 注意引入文件的顺序--%>
<%--a.js 用到了 b.js 中的函数 那么引入的顺序 b.js -> a.js--%>
<script type="text/javascript" src="script/my-admin.js"></script>
<script type="text/javascript">
    $(function() {
        //声明全局变量 在 my-admin.js 中取 因为 js 是 游览器 解析的 不能使用 Jsp 表达式
        //游览器发请求->服务器解析请求->服务器编译 Jsp.java ，响应给 游览器,js 代码完全有游览器解析
        window.totalRecord =${requestScope['PAGE-INFO'].total};
        window.pageSize =${requestScope['PAGE-INFO'].pageSize};
        window.pageNum =${requestScope['PAGE-INFO'].pageNum};
        //从请求参数取 不加 “ ” 会认为是变量 AA 不是字符串 “AA”
        window.keyword ="${param.keyword}";
        // 对分页导航条显示进行初始化
        initPagination();
        //全选，全不选功能
        $("#summaryBox").click(function () {
            //获取当前 勾选状态
            //this 代表当前多选框对象（DOM对象）
            //checked true 勾选，反之
            var checkStatus=this.checked;
            //使用checkStatus 设置 itemBox状态
           /* attr函数 只能设置一次
            prop 能多次*/
            $(".itemBox").prop("checked",this.checked)
        });
        // 给 批量删除 按钮绑定单击响应函数
        $("#batchRemoveBtn").click(function () {
            //存储 loginAcct 用于 删除数据是显示
            var loginAcctArray=new Array();
            // 创建一个数组对象
            var adminIdArray=new Array();
            // 拿到数据对应的id 删除  通过 jquery 选择器 定位到被选中 itemBox 然后遍历

            $(".itemBox:checked").each(function () {
            <%-- <input adminId="${admin.id }" class="itemBox" type="checkbox"> --%>
            //     将this转换成jQuery对象
            //     this.adminId 拿不到值 ，原因：this 是DOM 对象无法读取HTML标签没有的属性
                var adminId=$(this).attr("adminId");
                // 将数据存入到数组
                adminIdArray.push(adminId);

               var loginAcct= $(this).parent("td").next().text();
               //将账户名称 存入数组
               loginAcctArray.push(loginAcct);
            });
            //检查 数组是否包含有效数据 ，没数据不发请求
            if (adminIdArray.length ==0){
                alert("你还没勾选数据，傻逼");
                return ;
            }
            //给出 提示
            var confirmResult = window.confirm("您真的要删除"+loginAcctArray+"吗，操作不可逆，请谨慎决定!");
            //用户点击了取消  本来返回 true
            if(!confirmResult){
                return;
            }

            // 这是 GET 请求 拼的串有 大小限制
            //  window.location.href="admin/batch/remove.html?adminId=2&adminId=3&adminId=4"
            //发送 Ajax 请求，两种方式：提交表单，整个请求体
            batchRemove(adminIdArray);
        });
        //给单条删除按钮响应单击响应函数
        $(".itemRemoveBtn").click(function () {
            var adminID=$(this).attr("adminId");
            //获取当前记录的 loginAcct
            var loginAcct=$(this).parents("tr").children("td:eq(2)").text();
            var confirmResult=confirm("真的要删除"+loginAcct+"吗");
            if (!confirmResult){return;}
            var adminArray=new Array();
            adminArray.push(adminID);
            batchRemove(adminArray);
        });
    });
</script>
<body>
<%@ include file="/WEB-INF/include-nav.jsp"%>
<div class="container-fluid">
    <div class="row">
        <%@ include file="/WEB-INF/include-sidebar.jsp"%>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title">
                        <i class="glyphicon glyphicon-th"></i> 数据列表
                    </h3>
                </div>
                <div class="panel-body">
                    <form action="admin/query/for/search.html" class="form-inline" role="form" style="float: left;" method="post">
                        <div class="form-group has-feedback">
                            <div class="input-group">
                                <div class="input-group-addon">查询条件</div>
                                <input
                                        name="keyword"
                                        class="form-control has-success"
                                        type="text"
                                        placeholder="请输入查询条件">
                            </div>
                        </div>
                        <button  type="submit" class="btn btn-warning">
                            <i class="glyphicon glyphicon-search"></i> 查询
                        </button>
                    </form>
                    <button
<%--                            批量 删除按钮 ID--%>
                            id="batchRemoveBtn"
                            type="button"
                            class="btn btn-danger"
                            style="float: right; margin-left: 10px;">
                        <i class=" glyphicon glyphicon-remove"></i> 删除
                    </button>
                    <button type="button" class="btn btn-primary"
                            style="float: right;" onclick="window.location.href='admin/to/add/page.html'">
                        <i class="glyphicon glyphicon-plus"></i> 新增
                    </button>
                    <br>
                    <hr style="clear: both;">
                    <div class="table-responsive">
                        <table class="table  table-bordered">
                            <thead>
                            <tr>
                                <th width="30">#</th>
                                <th width="30"><input id="summaryBox" type="checkbox"></th>
                                <th>账号</th>
                                <th>名称</th>
                                <th>邮箱地址</th>
                                <th width="100">操作</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:if test="${empty requestScope['PAGE-INFO'].list }">
                                <tr>
                                    <td style="text-align: center;" colspan="6">抱歉！没有符合您要求的查询结果！</td>
                                </tr>
                            </c:if>
                            <c:if test="${!empty requestScope['PAGE-INFO'].list }">
                                <c:forEach items="${requestScope['PAGE-INFO'].list }"
                                           var="admin" varStatus="myStatus">
                                    <tr>
                                        <td>${myStatus.count }</td>
<%--                                        遍历时 保存 主键ID  adminId 是html 本身并没有，强行设置的--%>
                                        <td><input adminId="${admin.id }" class="itemBox" type="checkbox"></td>
                                        <td>${admin.loginAcct }</td>
                                        <td>${admin.userName }</td>
                                        <td>${admin.email }</td>
                                        <td>
                                            <button type="button" class="btn btn-success btn-xs">
                                                <i class=" glyphicon glyphicon-check"></i>
                                            </button>
                                            <button type="button" class="btn btn-primary btn-xs">
                                                <i class=" glyphicon glyphicon-pencil"></i>
                                            </button>
                                            <button adminId="${admin.id}" type="button" class="btn btn-danger btn-xs itemRemoveBtn">
                                                <i class=" glyphicon glyphicon-remove"></i>
                                            </button>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </c:if>
                            </tbody>
                            <tfoot>
                            <tr>
                                <td colspan="6" align="center">
                                    <div id="Pagination" class="pagination">
                                        <!-- 这里显示分页 -->

                                    </div>
                                </td>
                            </tr>
                            </tfoot>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

</body>
</html>