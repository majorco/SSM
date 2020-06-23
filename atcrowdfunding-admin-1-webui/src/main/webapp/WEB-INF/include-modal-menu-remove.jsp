<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="UTF-8">

<div id="confirmModal" class="modal fade in" tabindex="-1" role="dialog">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close">
					<span aria-hidden="true">×</span>
				</button>
				<h4 class="modal-title">popup</h4>
			</div>
			<form>
				<div class="modal-body">
					<p>Are you sure to delete following?</p>
					<table class="table  table-bordered">
						<thead>
						<tr>
							<th width="70">节点名称</th>
							<th>节点URL</th>
						</tr>
						</thead>
						<tbody id="confirmModalTableBody"></tbody>
					</table>
				</div>
				<div class="modal-footer">
					<button id="menuRemoveBtn" type="button" class="btn btn-success"><i class="glyphicon glyphicon-plus"></i> 确认</button>
					<button id="menuRemoveResetBtn" type="button" class="btn btn-primary"><i class="glyphicon glyphicon-refresh"></i> 取消</button>
				</div>
			</form>
		</div>
	</div>
</div>