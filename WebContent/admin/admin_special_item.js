// 获得当前页面中项目名字，其假定存在一个标识为 '__itemName' 的 hidden input，使用其 value 做为项目名字.
var __saved_itemName = null;
function getItemName() {
  if (__saved_itemName != null) return __saved_itemName;
  var itemName_obj = document.getElementById("__itemName");
  if (itemName_obj != null)
    __saved_itemName = itemName_obj.value;
  else
    __saved_itemName = '项目';
  return __saved_itemName;
}

// 添加、移动到其他专题的操作.
function copySpecialItems(copy){
  var ids = getSelectItemIds();
  if(ids.length < 1){
    alert("没有选择任何" + getItemName() + "。");
  }else{
    formAction.action = 'admin_special_item_copy.jsp';
    formAction.command.value = copy ? 'copy_ref' : 'move_ref';
    formAction.refids.value = ids;
    formAction.submit();
  }
}

// 从当前专题中移除文章.
function removeSpecials(refids) {
  var ids = getSelectItemIds();
  if (refids != null){
    ids = refids;
  }
  if(ids.length < 1){
    alert("没有选择任何" + getItemName() + "。");
  }else{
    if (confirm("确定要移除专题中的这些" + getItemName() + "吗？")==false) return;
    formAction.action = 'admin_special_action.jsp';
    formAction.command.value = 'remove_ref';
    formAction.refids.value = ids;
    formAction.submit();
  }
}
