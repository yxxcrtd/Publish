function deleteUsers() {
	var ids = getSelectItemIds();
	if (ids.length < 1) {
		alert("û��ѡ���κλ�Ա��");
		return;
	}
	if (confirm('ȷ��Ҫɾ��ѡ�еĻ�Ա��')) {
		actionForm.command.value = "batch_delete";
		actionForm.userIds.value = ids;
		actionForm.submit();
	}
}

// �����Ա״̬.
function doStatus(value) {
	var ids = getSelectItemIds();
	if (ids.length < 1) {
		alert("û��ѡ���κλ�Ա��");
		return;
	}
	actionForm.command.value = value == 0 ? 'batch_enable' : 'batch_disable';
	actionForm.userIds.value = ids;
	actionForm.submit();
}

// �����Ƿ�����Ͷ��.
function doInputer(value) {
	var ids = getSelectItemIds();
	if (ids.length < 1) {
		alert("û��ѡ���κλ�Ա��");
		return;
	}
	actionForm.command.value = value ? 'batch_input' : 'batch_uninput' ;
	actionForm.userIds.value = ids;
	actionForm.submit();
}
