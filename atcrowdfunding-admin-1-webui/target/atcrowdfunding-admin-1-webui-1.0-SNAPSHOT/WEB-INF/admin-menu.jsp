<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="UTF-8">

<head>
    <%@ include file="/WEB-INF/include-head.jsp" %>
    <link rel="stylesheet" href="ztree/zTreeStyle.css"/>
    <script type="text/javascript" src="ztree/jquery.ztree.all-3.5.min.js"></script>
    <script type="text/javascript" src="script/my-menu.js"></script>
    <script type="text/javascript">
        $(function () {
            initWholeTreeNode();

            //单击响应函数
            $("#menuAddBtn").unbind('click').bind('click',function () {
                //获取父代id 空格表示所有子元素 [] 表示 子元素中 name=”name”的元素
                var name = $("#menuAddModal [name='name']").val();
                var url = $("#menuAddModal [name='url']").val();
                var icon = $("#menuAddModal [name='icon']:checked").val();
                if (name == null || name == ""){
                    layer.msg("填写名字，傻逼");
                    return ;
                }
                if (url == null || url == ""){
                    layer.msg("填写访问地址，傻逼");
                    return ;
                }
                $.ajax({
                    url : "menu/addMenu.json",
                    type : "post",
                    data : {
                        "name" : name,
                        "url" : url,
                        "pid" : window.menuId,
                        "icon" : icon
                    },
                    dataType : "json",
                    success : function (response) {
                        var result=response.result;
                        if (result == "SUCCESS"){
                            layer.msg("add success");
                            initWholeTreeNode();
                        }
                        if (result == "FAILED"){
                            layer.msg(response.message);
                        }
                    },
                    error : function (response) {
                        layer.msg(response.message);
                    }
                });
                $("#menuAddModal").modal("hide");
                $("#menuAddModal [name='url']").val("");
                $("#menuAddModal [name='name']").val("");
                $("#menuAddModal [name='icon'][value='"+icon+"']").attr("checked",false);
            });

            $("#menuEditBtn").unbind('click').bind('click',function () {

                // 收集表单填写的数据
                var name = $.trim($("#menuEditModal [name='name']").val());
                var url = $.trim($("#menuEditModal [name='url']").val());
                var icon = $("#menuEditModal [name='icon']:checked").val();

                if(name == null || name == "") {
                    layer.msg("请填写菜单项名称！");
                    return ;
                }

                if(url == null || url == "") {
                    layer.msg("请填写菜单项对应的访问地址！");
                    return ;
                }

                // 发送Ajax请求
                $.ajax({
                    url:"menu/update.json",
                    type:"post",
                    dataType:"json",
                    data:{
                        "id":window.menuId,
                        "name":name,
                        "url":url,
                        "icon":icon
                    },
                    "success":function(response){

                        var result = response.result;

                        if(result == "SUCCESS") {
                            layer.msg("操作成功！");

                            initWholeTreeNode();
                        }

                        if(result == "FAILED") {
                            layer.msg(response.message);
                        }

                    },
                    "error":function(response){
                        layer.msg(response.message);
                    }
                });
                $("#menuEditModal").modal("hide");
            });


            $("#menuRemoveBtn").unbind('click').bind('click',function () {
                $.ajax({
                    url:"menu/delete/"+window.menuId+".json",
                    type:"get",
                    dataType:"json",
                    "success":function(response){

                        var result = response.result;

                        if(result == "SUCCESS") {
                            layer.msg("操作成功！");

                            initWholeTreeNode();
                        }

                        if(result == "FAILED") {
                            layer.msg(response.message);
                        }

                    },
                    "error":function(response){
                        layer.msg(response.message);
                    }
                });
                $("#confirmModal").modal("hide");

            });


            $("#menuRemoveResetBtn").unbind('click').bind('click',function () {

                $("#confirmModal").modal("hide");

            });

            });




    </script>
</head>
<body>
<%@ include file="/WEB-INF/include-nav.jsp" %>
<div class="container-fluid">
    <div class="row">
        <%@ include file="/WEB-INF/include-sidebar.jsp" %>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">

            <div class="panel panel-default">
                <div class="panel-heading">
                    <i class="glyphicon glyphicon-th-list"></i> 权限菜单列表
                    <div style="float: right; cursor: pointer;" data-toggle="modal"
                         data-target="#myModal">
                        <i class="glyphicon glyphicon-question-sign"></i>
                    </div>
                </div>
                <div class="panel-body">
                    <ul id="treeDemo" class="ztree"></ul>
                </div>
            </div>
        </div>
    </div>
</div>

</body>
<%@ include file="/WEB-INF/include-modal-menu-add.jsp"%>
<%@ include file="/WEB-INF/include-modal-menu-edit.jsp"%>
<%@ include file="/WEB-INF/include-modal-menu-remove.jsp"%>

</html>
