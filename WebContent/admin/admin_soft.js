
// ��õ�ǰҳ������Ŀ���֣���ٶ�����һ����ʶΪ '__itemName' �� hidden input��ʹ���� value ��Ϊ��Ŀ����.
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

// ����ɾ�����.
function deleteItems() {
 var ids = getSelectItemIds();
 if (ids.length < 1) {
  alert("û��ѡ���κ�" + getItemName() + "��");
 } else {
  if (confirm("ȷ��Ҫɾ��ѡ�е���Щ" + getItemName() + "��")) {
    formAction.action = 'admin_soft_action.jsp';
    formAction.command.value = 'batch_delete';
    formAction.itemIds.value = ids;
    formAction.submit();
  }
 }
}

// �����ƶ����.
function moveToColumn() {
 var ids = getSelectItemIds();
 if (ids.length < 1) {
  alert('û��ѡ���κ�' + getItemName() + '��');
 } else {
  byId("moveSoftIds").value = ids;
  if (ids.length < 1) {
   byId("moveSubmit").disabled = true;
  } else {
   byId("moveSubmit").disabled = false;
  }
  byId("soft_move").style.display="";
  byId("soft_move_back").style.display="";
 }
}

// ���/ȡ�����.
function approItems(approve) {
  var ids = getSelectItemIds();
  if (ids.length < 1) {
    alert('û��ѡ���κ�' + getItemName() + '��');
    return;
  }
  
  var status = approve ? 1 : 0;
  formAction.action = 'admin_soft_action.jsp';
  formAction.command.value = 'batch_appr';
  formAction.itemIds.value = ids;
  formAction.status.value = status;
  formAction.submit();
}

// ���ñ�������.
function setCommand(command) {
 byId("command").value = command;
}

// ����Ƿ�ѡ����Ҫ��������Ŀ.
function checkSelect() {
 var ids = getSelectItemIds();
 if (ids.length < 1) {
  alert('û��ѡ���κ�' + getItemName() + '��');
  return false;
 } 
 return true;
}
