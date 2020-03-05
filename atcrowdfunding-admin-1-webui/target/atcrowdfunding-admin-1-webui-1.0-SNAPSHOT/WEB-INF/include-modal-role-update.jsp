<%--
  Created by IntelliJ IDEA.
  User: 17673
  Date: 2020/3/2
  Time: 20:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div id="editModal" class="modal fade" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <form role="form">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"
                            aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                    <h4 class="modal-title">popup</h4>
                </div>
                <div class="modal-body">
                    <input type="text" id="roleNameInputEdit" class="form-control"
                           placeholder="请输入角色名称" />
                </div>
                <div class="modal-footer">
                    <button id="editModalBtn" type="button" class="btn btn-warning">
                        <i class="glyphicon glyphicon-edit"></i> 更新
                    </button>
                    <button type="reset" class="btn btn-primary">
                        <i class="glyphicon glyphicon-refresh"></i> 重置
                    </button>				</div>
            </form>
        </div>
    </div>
</div>

