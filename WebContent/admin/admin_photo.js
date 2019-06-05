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

// 设置表单的命令.
function setCommand(command) {
  byId("command").value = command;
}

// 检查是否选择了要操作的项目.
function checkSelect() {
  var ids = getSelectItemIds();
  if (ids.length < 1) {
    alert("没有选择任何" + getItemName() + "。");
    return false;
  } 
  return true;
}

// 批量删除图片.
function deleteItems() {
 var ids = getSelectItemIds();
 if (ids.length < 1) {
  alert("没有选择任何" + getItemName() + "。");
 } else {
  if (confirm("确定要删除选中的这些" + getItemName() + "吗？")) {
    var formAction = byId('formAction');
    formAction.action = 'admin_photo_action.jsp';
    formAction.command.value = 'batch_delete';
    formAction.itemIds.value = ids;
    formAction.submit();
  }
 }
}

// 批量移动图片.
function moveToColumn() {
 alert('TODO: 暂时没有实现 admin_photo.js moveToColumn() 批量移动操作。');
 return;
 var ids = getSelectItemIds();
 if (ids.length < 1) {
  alert("没有选择任何" + getItemName() + "。");
 } else {
  byId("movePhotoIds").value = ids;
  if (ids.length < 1) {
   byId("moveSubmit").disabled = true;
  } else {
   byId("moveSubmit").disabled = false;
  }
  byId("photo_move").style.display="";
  byId("photo_move_back").style.display="";
 }
}

// 审核/取消审核。 approve = true 表示审核通过, = false 表示不通过.
function approItems(approve) {
  var ids = getSelectItemIds();
  if (ids.length < 1) {
    alert("没有选择任何" + getItemName() + "。");
    return;
  }
  
  var status = approve ? 1 : 0;
  formAction.action = 'admin_photo_action.jsp';
  formAction.command.value = 'batch_appr';
  formAction.itemIds.value = ids;
  formAction.status.value = status;
  formAction.submit();
}
