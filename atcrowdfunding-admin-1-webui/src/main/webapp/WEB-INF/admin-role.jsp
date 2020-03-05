<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="UTF-8">
<%@ include file="/WEB-INF/include-head.jsp"%>
<%@ include file="/WEB-INF/include-modal-role-confirm.jsp"%>
<%@ include file="/WEB-INF/include-modal-role-insert.jsp"%>
<%@ include file="/WEB-INF/include-modal-role-update.jsp"%>
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
            $("#modalOK").unbind('click').bind('click',(function () {
                var idArray=window.IdArray;
                $.ajax({
                    url : "do/deleteRole/byIdArray.json",
                    type : "post",
                    data : JSON.stringify(idArray),
                    dataType : "json",
                    contentType : "application/json;charset=UTF-8",
                    success : function (response) {
                        var result = response.result;
                        if (result == "SUCCESS") {
                            showPage();
                            $("#confirmModal").modal('hide');
                            layer.msg("delete succeed");
                            return ;
                        }
                        if (result == "FAILED"){
                            $("#confirmModal").modal('hide');
                            layer.msg(response.message);
                            return;
                        }
                    },
                    error : function (response) {
                        $("#confirmModal").modal('hide');
                        layer.msg(response.message);
                    },

                });
            }));

        });
        // 下面的方法绑定单击函数，重新动态填充JS生成的元素后，不再生效，只能生效一次
        // $(".removeBtn").click(function ()
        //单条删除

        //$(动态元素所依附的静态元素).on(事件类型,具体要绑定事件的动态元素的选择器,处理函数)
        $("#roleTableBody").on("click",".removeBtn",function () {
            var roleID=$(this).attr("roleID");
            window.IdArray=new Array();
            IdArray.push(roleID);
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

        //新增按钮

        $("#insertRole").unbind('click').bind('click',(function () {
            $("#addModal").modal("show");
            $("#ok").unbind('click').bind('click',(function () {
                var name=$.trim($("#roleNameInput").val());
                if (name == null || name == ""){
                    layer.msg("输入名字，傻逼");
                    return;
                }
                $.ajax({
                    url : "do/save/role.json",
                    type : "post",
                    data : {
                        name : name
                    },
                    dataType : "json",
                    success:function(response){

                        var result = response.result;

                        if(result == "SUCCESS") {
                            window.keyword=name.charAt(0);
                            window.pageNum=1;
                            showPage();
                            layer.msg("操作成功！");
                        }
                        if(result == "FAILED") {
                            layer.msg(response.message);
                            window.keyword=name.charAt(0);
                            showPage();
                        }

                        // 4.不管成功还是失败，关闭模态框
                        $("#addModal").modal("hide");

                        // 5.清理本次在文本框填写的数据
                        $("#roleNameInput").val("");

                    },
                    "error":function(response){
                        layer.msg(response.message);
                    }
                });

            }));

        }));

        //跟新操作 点击小缸壁时
        $("#roleTableBody").on("click",".updateBtn",function () {
             window.roleID=$(this).attr("roleID");
              var name = $(this).parents("tr").children("td:eq(2)").text();
              $("#roleNameInputEdit").val(name);
                $("#editModal").modal("show");
        });

        //点击提交时
        $("#editModalBtn").unbind('click').bind('click',function () {
            var name=$("#roleNameInputEdit").val();
            if (name == null || name == ""){
                layer.msg("输入有效的用户名,傻逼!");
                return ;
            }
            $.ajax({
                url : "do/updateRole/byID.json",
                type : "post",
                data : {
                    id : roleID,
                    name : name
                },
                dataType : "json",
                success : function (response) {
                    var result=response.result;
                    if (result == "SUCCESS"){
                        showPage();
                        $("#editModal").modal("hide");
                        layer.msg("update success!");
                        return;
                    }
                    if (result == "FAILED"){
                        showPage();
                        $("#editModal").modal("hide");
                        layer.msg(response.message);
                        return;
                    }
                },
                error : function (response) {
                    layer.msg(response.message);
                }


            });


        });






    });
</script>
<link rel="stylesheet" href="css/pagination.css" />
<script type="text/javascript" src="jquery/jquery-pagination.js"></script>
<script type="text/javascript" src="script/my-role.js"></script>
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
                    <button id="insertRole" type="button" class="btn btn-primary"
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
</html>