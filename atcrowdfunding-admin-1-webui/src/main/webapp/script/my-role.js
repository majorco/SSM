// 初始化全局变量
function initGlobalVariable() {
	window.pageSize = 5;
	window.pageNum = 1;
	window.keyword = "";
}

// 给服务器发送请求获取分页数据（pageInfo），并在页面上显示分页效果（主体、页码导航条）
function showPage() {
	
	// 给服务器发送请求获取分页数据：PageInfo
	var pageInfo = getPageInfo();
	if (pageInfo == null){
		return ;
	}
	// 在页面上的表格中tbody标签内显示分页的主体数据
	generateTableBody(pageInfo);
	
	// 在页面上的表格中tfoot标签内显示分页的页码导航条
	initPagination(pageInfo);
}

// 获取分页数据：PageInfo
/**
 * 成功 返回 ResultEntity.date ，这里是 pageHelper 的 pageInfo info 里面包装了 list，这里的 list 是 RoleList
 * @returns {null|*}
 */
function getPageInfo() {
	
	// 以同步请求方式调用$.ajax()函数并获取返回值（返回值包含全部响应数据）
	var ajaxResult = $.ajax({
		url:"admin/query/forRole/search.json",
		type:"post",
		data:{
			pageNum:(window.pageNum == undefined)?1:window.pageNum,
			pageSize:(window.pageSize == undefined)?5:window.pageSize,
			keyword:(window.keyword == undefined)?"":window.keyword
		},
		dataType:"json",
		async:false	// 为了保证getPageInfo()函数能够在Ajax请求拿到响应后获取PageInfo，需要设置为同步操作
	});
	//
	// console.log(ajaxResult);
	// console.log(ajaxResult.responseJSON);
	
	// 从全部响应数据中获取JSON格式的响应体数据
	var resultEntity = ajaxResult.responseJSON;
	
	// 从响应体数据中获取result，判断当前请求是否成功
	var result = resultEntity.result;
	
	// 如果成功获取PageInfo
	if(result == "SUCCESS") {
		return resultEntity.data;
	}
	
	if(result == "FAILED") {
		layer.msg(resultEntity.message);
	}
	
	return null;
}

// 使用PageInfo数据在tbody标签内显示分页数据
function generateTableBody(pageInfo) {
	
	// 执行所有操作前先清空
	$("#roleTableBody").empty();
	
	// 获取数据集合
	var list = pageInfo.list;
	
	// 判断list是否有效
	if(list == null || list.length == 0) {

		$("#roleTableBody").append("<tr><td colspan='4' style='text-align:center;'>没有查询到数据！</td></tr>");
		
		return ;
	}
	for(var i = 0; i < list.length; i++) {

		var role = list[i];
		
		var checkBtn = "<button roleID='"+role.id+"' type='button' class='btn btn-success btn-xs checkBtn'><i class=' glyphicon glyphicon-check'></i></button>";
		var pencilBtn = "<button roleID='"+role.id+"'type='button' class='btn btn-primary btn-xs updateBtn'><i class=' glyphicon glyphicon-pencil'></i></button>";
		var removeBtn = "<button roleID='"+role.id+"' type='button' class='btn btn-danger btn-xs removeBtn'><i class=' glyphicon glyphicon-remove'></i></button>";
		
		var numberTd = "<td>"+(i+1)+"</td>";
		var checkBoxTd = "<td><input roleid='"+role.id+"' class='checkItemBox' type='checkbox'></td>";
		var roleNameTd = "<td>"+role.name+"</td>";
		var btnTd = "<td>"+checkBtn+" "+pencilBtn+" "+removeBtn+"</td>";
		
		var tr = "<tr>"+numberTd+checkBoxTd+roleNameTd+btnTd+"</tr>";
		
		// 将前面拼好的HTML代码追加到#roleTableBody中
		$("#roleTableBody").append(tr);
	}
}

//声明函数封装导航条初始化操作
function initPagination(pageInfo) {
	
	// 声明变量存储分页导航条显示时的属性设置
	var paginationProperties = {
		num_edge_entries : 3,			//边缘页数
		num_display_entries : 5,		//主体页数
		callback : pageselectCallback,	//回调函数
		items_per_page : window.pageSize,	//每页显示数据数量，就是pageSize
		current_page : (window.pageNum - 1),//当前页页码
		prev_text : "上一页",			//上一页文本
		next_text : "下一页"			//下一页文本
	};
	
	// 显示分页导航条
	$("#Pagination").pagination(pageInfo.total, paginationProperties);
}

// 在每一次点击“上一页”、“下一页”、“页码”时执行这个函数跳转页面
function pageselectCallback(pageIndex,jq) {

	// 将全局变量中的pageNum修改为最新值
	// pageIndex从0开始，pageNum从1开始
	//这里分页数目加上后 发送给服务器调用
	window.pageNum = pageIndex + 1;
	
	// 调用分页函数重新执行分页
	showPage();
	
	return false;
}

//根据 RoleId 数组 发送到 handler ，查询 Role 数组
/**
 *  返回 数据 ，这里是 RoleList
 * @param roleIdArray
 * @returns {null|*}
 */
function getRoleListByRoleIArray(roleIdArray) {
	var requestBody=JSON.stringify(roleIdArray);
	//发送 Ajax请求
	var ajaxResult=$.ajax({
		url : "role/getList/byIdArray/list.json",
		type : "post",
		data : requestBody,
		contentType : "application/json;charset=UTF-8",
		dataType: "json",
		async: false

	});
	//获取 JSON 对象响应体
	var resultEntity=ajaxResult.responseJSON;
	//验证是否成功
	//console.log(resultEntity);
	var result=resultEntity.result;
	if (result == "SUCCESS"){

		return resultEntity.data;
	}
	if (result == "FAILED"){
		layer.msg(resultEntity.message);
		return null;
	}
	return null;
}

/**
 * 拼 模态框 <tbody>
 */
function showRemoveConfirmModal() {
	//1.将模态框显示出来 #号 取id
	$("#confirmModal").modal("show");
	// 2.获取roleList
	var roleList=getRoleListByRoleIArray(window.IdArray);
	// 3.每次拼之前先清空
	$("#confirmModalTableBody").empty();
	// 4.拼 tbody
	for (var i=0;i<roleList.length;i++){
		var role=roleList[i];
		var id=role.id;
		var name=role.name;
		var trHTML="<tr><td>"+id+"</td><td>"+name+"</td></tr>";
		$("#confirmModalTableBody").append(trHTML);
	}

}