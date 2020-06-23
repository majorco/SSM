//由 settings.view.addDiyDom 属性引用，负责显示自定义图标
function showMyIcon(treeId, treeNode) {

    // 获取当前节点的id
    var currentNodeId = treeNode.tId;

    // 获取新的class值用于替换
    var newClass = treeNode.icon;
    console.log(newClass);
    // 在当前节点id之后附加“_ico”得到目标span的id
    var targetSpanId = currentNodeId + "_ico";

    // 将目标span的旧class移除，添加新class
    $("#" + targetSpanId).removeClass().addClass(newClass).css("background", "");

}

//生成树形 结构函数，此处向 后台请求数据
function initWholeTreeNode() {
    // 包含 zTree 的设置属性
    var settings = {
        view: {
            // showMyIcon 是一个自定义函数，将默认图标修改为数据库的图标
            addDiyDom: showMyIcon,
            addHoverDom: addHoverDom,
            removeHoverDom: removeHoverDom
        },
        // 点击 url 会跳转，将 url 设置成一个不存在的属性
        data: {
            key: {
                url: "noExistProperty"
            }
        }

    };
    $.ajax({
        url: "admin/menu/getAll.json",
        type: "post",
        dataType: "json",
        success: function (response) {
            var result = response.result;
            if (result == "SUCCESS") {
                var zNodes = response.data;
                //执行树形结构的初始操作
               var expandAll= $.fn.zTree.init($("#treeDemo"), settings, zNodes);
                expandAll.expandAll(true);
            }
            if (result == "FAILED") {
                layer.msg("加载菜单失败，" + response.message);
            }
        },
        error: function (response) {
            layer.msg("未知错误");
        }
    });

}

// 在鼠标悬停 增加控件
function addHoverDom(treeId, treeNode) {
    // 判断添加过就不添加了

    // 组装按钮组所在的span标签id
    var btnGrpSpanId = treeNode.tId + "_btnGrp";
    var btnGrpSpanLength = $("#" + btnGrpSpanId).length;
    if (btnGrpSpanLength > 0) {
        return;
    }
    //添加到当前节点 的<a> 标签后面 就是那段文字 后面 anchor 抛锚
    var anchorId = treeNode.tId + "_a";
    //生成按钮组
    var btnGrpSpan = generateBtnGrp(treeNode);
    //在anchor 标签后添加
    $("#" + anchorId).after(btnGrpSpan);

}

// 鼠标 移除节点范围时 删除自定义控件
function removeHoverDom(treeId, treeNode) {
    // 组装按钮组所在的span标签id
    var btnGrpSpan = treeNode.tId + "_btnGrp";
    $("#" + btnGrpSpan).remove();
}

//生成按钮组
function generateBtnGrp(treeNode) {
    // 获取当前节点的id（HTML中li标签的id）
    var treeNodeId = treeNode.tId;
    // 获取当前节点在数据库中的id值
    // Menu实体类中的属性，都可以通过treeNode以“.属性名”的方式直接访问
    var menuId = treeNode.id;
    // 组装按钮组所在的span的id
    var btnGrpSpanId = treeNodeId + "_btnGrp";
    // 生成span标签对应的jQuery对象
    var $span = $("<span id='" + btnGrpSpanId + "'></span>");
    // 获取当前节点的level值
    var level = treeNode.level;
    // 声明三种按钮
    var addBtn = "<a onclick='showAddModal(this)' id='" + menuId + "' class='btn btn-info dropdown-toggle btn-xs' style='margin-left:10px;padding-top:0px;' title='添加子节点'>&nbsp;&nbsp;<i class='fa fa-fw fa-plus rbg'></i></a>";
    var editBtn = "<a onclick='showEditModal(this)' id='" + menuId + "' class='btn btn-info dropdown-toggle btn-xs' style='margin-left:10px;padding-top:0px;' title='编辑节点'>&nbsp;&nbsp;<i class='fa fa-fw fa-edit rbg'></i></a>";
    var removeBtn = "<a onclick='showConfirmModal(this)' id='" + menuId + "' class='btn btn-info dropdown-toggle btn-xs' style='margin-left:10px;padding-top:0px;' title='删除节点'>&nbsp;&nbsp;<i class='fa fa-fw fa-times rbg'></i></a>";
    // 根据level进行判断
    if (level == 0) {
        $span.append(addBtn);
    }
    if (level == 1) {
        if (treeNode.children.length > 0) {
            $span.append(addBtn + " " + editBtn);
        } else {
            $span.append(addBtn + " " + editBtn + " " + removeBtn);
        }
    }
    if (level == 2) {
        $span.append(editBtn + " " + removeBtn);
    }
    return $span;
}

//打开增加模态框
function showAddModal(currentBtn) {
    $("#menuAddModal").modal("show");
    window.menuId=currentBtn.id;
}
function showEditModal(currentBtn) {
    $("#menuEditModal").modal("show");
    window.menuId=currentBtn.id;
    $.ajax({
        url:"menu/get/"+window.menuId+".json",
        type:"get",
        dataType:"json",
        success:function(response) {

            var result = response.result;

            if(result == "SUCCESS") {

                // 从响应数据中获取Menu对象
                var menu = response.data;

                // 获取各个属性值
                var name = menu.name;

                var url = menu.url;

                var icon = menu.icon;

                // 设置各个对应的表单项
                $("#menuEditModal [name='name']").val(name);

                $("#menuEditModal [name='url']").val(url);

                // radio需要让value值和后端查询到的icon一致的设置为被选中
                $("#menuEditModal [name='icon'][value='"+icon+"']").attr("checked",true);
            }

            if(result == "FAILED") {
                layer.msg(response.message);
            }
        },
        error:function(response) {
            layer.msg(response.message);
        }
    });
}
function showConfirmModal(currentBtn){
    $("#confirmModal").modal("show");
    window.menuId=currentBtn.id;
    $("#confirmModalTableBody").empty();
    $.ajax({
        url : "menu/get/"+window.menuId+".json",
        type:"get",
        dataType:"json",
        success : function (response) {
            var result = response.result;
            if(result == "SUCCESS"){
                var menu=response.data;
                var name =menu.name;
                var url=menu.url;
                var trHTML="<tr><td>"+name+"</td><td>"+url+"</td></tr>";
                $("#confirmModalTableBody").append(trHTML);
            }
            if(result == "FAILED") {
                layer.msg(response.message);
            }
        },
        error:function(response) {
            layer.msg(response.message);
        }


    });
}




