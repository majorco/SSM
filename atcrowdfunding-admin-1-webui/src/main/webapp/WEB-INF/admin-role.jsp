<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="UTF-8">
<%@ include file="/WEB-INF/include-head.jsp"%>
<link rel="stylesheet" href="css/pagination.css" />
<script type="text/javascript" src="jquery/jquery-pagination.js"></script>
<script type="text/javascript" src="script/my-role.js"></script>
<script type="text/javascript">
    $(function(){

        // 调用分页参数初始化方法
        initGlobalVariable();

        // 执行分页
        showPage();
        //查询按钮函数
        $("#searchBtn").click(function () {
            //获取文本框数据
            var keyword=$.trim($("#keywordInput").val());
            //检测是否有效
            if (keyword == null || keyword == ""){
                window.keyword="";
                showPage();
            }else {
                window.keyword = keyword;
                showPage();
            }
        });

        //全选
        $("#checkAllBox").click(function () {
            var statusCheckAllBox=this.checked;
            $(".checkItemBox").prop("checked",this.checked);
        });

        //批量删除
        $("#batchRemove").click(function () {
            //存入全局变量
           window.IdArray=new Array();

            $(".checkItemBox:checked").each(function () {
                var id=$(this).attr("roleid");
                IdArray.push(id);
            });
            if (IdArray==null || IdArray.length<1){
                 layer.msg("选择数据，傻逼");
                 return ;
            }
          //调用 拼模态框的函数
            showRemoveConfirmModal();
            //提交模态框 OK
            $("#modalOK").click(function () {
                var idArray=window.IdArray;
                var ajaxResult= $.ajax({
                    url : "do/deleteRole/byIdArray.json",
                    type : "post",
                    data : JSON.stringify(idArray),
                    dataType : "json",
                    contentType : "application/json;charset=UTF-8",
                    success : function (response) {
                        var result = response.result;
                        if (result == "SUCCESS") {
                            showPage();
                            layer.msg("delete succeed");
                    }
                        if (result == "FAILED"){
                            layer.msg(response.message);
                        }
                        $("#confirmModal").modal('hide');
                    },
                    error : function (response) {
                            layer.msg(response.message);
                    }
                });
            });

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
                    <form class="form-inline" role="form" style="float: left;">
                        <div class="form-group has-feedback">
                            <div class="input-group">
                                <div class="input-group-addon">查询条件</div>
                                <input id="keywordInput" class="form-control has-success" type="text"
                                       placeholder="请输入查询条件">
                            </div>
                        </div>
                        <button id="searchBtn" type="button" class="btn btn-warning">
                            <i class="glyphicon glyphicon-search"></i> 查询
                        </button>
                    </form>
                    <button id="batchRemove" type="button" class="btn btn-danger"
                            style="float: right; margin-left: 10px;">
                        <i class=" glyphicon glyphicon-remove"></i> 删除
                    </button>
                    <button type="button" class="btn btn-primary"
                            style="float: right;">
                        <i class="glyphicon glyphicon-plus"></i> 新增
                    </button>
                    <br>
                    <hr style="clear: both;">
                    <div class="table-responsive">
                        <table class="table  table-bordered">
                            <thead>
                            <tr>
                                <th width="30">#</th>
                                <th width="30"><input id="checkAllBox" type="checkbox"></th>
                                <th>名称</th>
                                <th width="100">操作</th>
                            </tr>
                            </thead>
                            <tbody id="roleTableBody"></tbody>
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
<%@ include file="/WEB-INF/include-modal-role-confirm.jsp"%>
</html>