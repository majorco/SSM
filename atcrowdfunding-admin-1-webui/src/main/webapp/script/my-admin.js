// 声明函数封装导航条初始化操作
function initPagination() {

    // 声明变量存储总记录数
    //var totalRecord = ${requestScope['PAGE-INFO'].total};

    // 声明变量存储分页导航条显示时的属性设置
    var paginationProperties = {
        num_edge_entries : 3,			//边缘页数
        num_display_entries : 5,		//主体页数
        callback : pageselectCallback,	//回调函数
        items_per_page : window.pageSize,	//每页显示数据数量，就是pageSize
        current_page : (window.pageNum-1),//当前页页码
        prev_text : "上一页",			//上一页文本
        next_text : "下一页"			//下一页文本
    };

    // 显示分页导航条
    $("#Pagination").pagination(window.totalRecord, paginationProperties);
}


// 在每一次点击“上一页”、“下一页”、“页码”时执行这个函数跳转页面
function pageselectCallback(pageIndex, jq) {

    // pageIndex从0开始，pageNum从1开始
    // pageIndex 是字符串
    window.pageNum = pageIndex + 1;

    // 跳转页面 每翻一页都是一次 带上 pageNum 跟KeyWord 的一次 请求
    window.location.href = "admin/query/for/search.html?pageNum="+window.pageNum+"&keyword="+window.keyword;

    return false;
}


//封装批量删除函数
function batchRemove(adminIdArray) {
    $.ajax({
        url : "admin/batch/remove.json",
        type : "post",
        data : JSON.stringify(adminIdArray),
        contentType : "application/json;charset=UTF-8",
        dataType : "JSON",
        success : function (response) {
            console.log(response);
            var result=response.result;
            if (result=="SUCCESS"){
                window.location.href="admin/query/for/search.html?pageNum="+window.pageNum+"&keyword="+window.keyword;
            }
            if (result=="FAILED"){alert(response.message)}
        },
        error : function (response) {
            alert(response.message);
        }

    });

}
