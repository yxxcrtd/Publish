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

// ��ӡ��ƶ�������ר��Ĳ���.
function copySpecialItems(copy){
  var ids = getSelectItemIds();
  if(ids.length < 1){
    alert("û��ѡ���κ�" + getItemName() + "��");
  }else{
    formAction.action = 'admin_special_item_copy.jsp';
    formAction.command.value = copy ? 'copy_ref' : 'move_ref';
    formAction.refids.value = ids;
    formAction.submit();
  }
}

// �ӵ�ǰר�����Ƴ�����.
function removeSpecials(refids) {
  var ids = getSelectItemIds();
  if (refids != null){
    ids = refids;
  }
  if(ids.length < 1){
    alert("û��ѡ���κ�" + getItemName() + "��");
  }else{
    if (confirm("ȷ��Ҫ�Ƴ�ר���е���Щ" + getItemName() + "��")==false) return;
    formAction.action = 'admin_special_action.jsp';
    formAction.command.value = 'remove_ref';
    formAction.refids.value = ids;
    formAction.submit();
  }
}
