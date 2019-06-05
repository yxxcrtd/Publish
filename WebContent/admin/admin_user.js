function deleteUsers() {
	var ids = getSelectItemIds();
	if (ids.length < 1) {
		alert("没有选择任何会员。");
		return;
	}
	if (confirm('确定要删除选中的会员吗？')) {
		actionForm.command.value = "batch_delete";
		actionForm.userIds.value = ids;
		actionForm.submit();
	}
}

// 处理会员状态.
function doStatus(value) {
	var ids = getSelectItemIds();
	if (ids.length < 1) {
		alert("没有选择任何会员。");
		return;
	}
	actionForm.command.value = value == 0 ? 'batch_enable' : 'batch_disable';
	actionForm.userIds.value = ids;
	actionForm.submit();
}

// 处理是否允许投稿.
function doInputer(value) {
	var ids = getSelectItemIds();
	if (ids.length < 1) {
		alert("没有选择任何会员。");
		return;
	}
	actionForm.command.value = value ? 'batch_input' : 'batch_uninput' ;
	actionForm.userIds.value = ids;
	actionForm.submit();
}
