// ��õ�ǰҳ������Ŀ���֣���ٶ�����һ����ʶΪ '__itemName' �� hidden input��ʹ���� value ��Ϊ��Ŀ���֡�
var __saved_itemName = null;
function getItemName() {
  if (__saved_itemName != null) return __saved_itemName;
  var itemName_obj = document.getElementById("__itemName");
  if (itemName_obj != null)
    __saved_itemName = itemName_obj.value;
  else
    __saved_itemName = '��Ŀ';
  return __saved_itemName;
}

// ����ɾ������.
function destroyItems() {
  var ids = getSelectItemIds();
  if (ids.length < 1) {
    alert("û��ѡ���κ�" + getItemName() + "��");
  } else {
    if (confirm("ȷ��Ҫ����ɾ��ѡ�е���Щ" + getItemName() + "��") == false) return;
    formAction.action = "admin_recycle_action.jsp";
    formAction.command.value = 'batch_destroy';
    formAction.itemIds.value = ids;
    formAction.submit();
  }
}

// ��ջ���վ.
function clearItems() {
  if (confirm("ȷ��Ҫ��ջ���������" + getItemName() + "��")){
    formAction.action = "admin_recycle_action.jsp";
    formAction.command.value = 'clear';
    formAction.submit();
  }
}

// ��ԭѡ��������.
function recoverItems(){
  var ids = getSelectItemIds();
  if(ids.length<1){
    alert("û��ѡ���κ�" + getItemName() + "��");
  }else{
    formAction.action = "admin_recycle_action.jsp";
    formAction.command.value = 'batch_recover';
    formAction.itemIds.value = ids;
    formAction.submit();
  }
}

// ��ԭ����վ��������.
function recoverAllItems(){
  if (confirm('ȷ��Ҫȫ���ָ���Ƶ��������' + getItemName() + '��?')) {
	  formAction.action = "admin_recycle_action.jsp";
	  formAction.command.value = 'recover_all';
	  formAction.submit();
	}
}
