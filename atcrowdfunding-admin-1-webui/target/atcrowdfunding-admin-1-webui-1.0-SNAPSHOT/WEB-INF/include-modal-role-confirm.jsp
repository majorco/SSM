<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div id="confirmModal" class="modal fade" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title">popup(弹窗)</h4>
            </div>
            <div class="modal-body">
                <p>Are you sure to delete following?</p>
                <table class="table  table-bordered">
                    <thead>
                    <tr>
                        <th width="30">#</th>
                        <th>名称</th>
                    </tr>
                    </thead>
                    <tbody id="confirmModalTableBody"></tbody>
                </table>
            </div>
            <div class="modal-footer">
                <button id="modalOK" type="button" class="btn btn-primary">OK</button>
            </div>
        </div>
    </div>
</div>
