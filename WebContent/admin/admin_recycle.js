// 获得当前页面中项目名字，其假定存在一个标识为 '__itemName' 的 hidden input，使用其 value 做为项目名字。
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

// 批量删除文章.
function destroyItems() {
  var ids = getSelectItemIds();
  if (ids.length < 1) {
    alert("没有选择任何" + getItemName() + "。");
  } else {
    if (confirm("确定要彻底删除选中的这些" + getItemName() + "吗？") == false) return;
    formAction.action = "admin_recycle_action.jsp";
    formAction.command.value = 'batch_destroy';
    formAction.itemIds.value = ids;
    formAction.submit();
  }
}

// 清空回收站.
function clearItems() {
  if (confirm("确定要清空回收内所有" + getItemName() + "吗？")){
    formAction.action = "admin_recycle_action.jsp";
    formAction.command.value = 'clear';
    formAction.submit();
  }
}

// 还原选定的文章.
function recoverItems(){
  var ids = getSelectItemIds();
  if(ids.length<1){
    alert("没有选定任何" + getItemName() + "。");
  }else{
    formAction.action = "admin_recycle_action.jsp";
    formAction.command.value = 'batch_recover';
    formAction.itemIds.value = ids;
    formAction.submit();
  }
}

// 还原回收站所有文章.
function recoverAllItems(){
  if (confirm('确定要全部恢复此频道的所有' + getItemName() + '吗?')) {
	  formAction.action = "admin_recycle_action.jsp";
	  formAction.command.value = 'recover_all';
	  formAction.submit();
	}
}
