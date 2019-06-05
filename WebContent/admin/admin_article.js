// 提供给 admin_article_xxx.jsp 页面使用的.

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

// 选中所有的专题.
function selectAll() {
 var object = document.getElementById("specialIds");
 for (var i = 0; i < object.length; i++) {
  object[i].selected = true;
 }
}

// 取消选中所有专题.
function unSelectAll() {
 var object = document.getElementById("specialIds");
 for (var i = 0; i < object.length; i++) {
  object[i].selected = false;
 }
}

// 将内容页中所有的链接的目标窗口都设置为弹出新窗口.
function setOpenNew() {
 var links = editor.HtmlEdit.document.getElementsByTagName("A");
 for (var i = 0; i < links.length; i++) {
  links[i].setAttribute("target", "_blank");
 }
}

//禁用“自动生成”选项.
function disableCreate() {
  var obj = document.getElementById("createImmediate");
  obj.checked = false;
  obj.disabled = true;
}

//允许“自动生成”选项.
function enabelCreate() {
  var obj = document.getElementById("createImmediate");
  obj.disabled = false;
  obj.checked = true;
}

// 从 editor_modifypic.jsp 页面调用, 例子: 
// AddItem(92, 'nnnnnnnn.jpg', 'http://localhost:8080/PubWeb/xxx/xxx/nnnnnnnn.jpg')
function AddItem(id, fileName, url){ // strFileName
  var fileExt = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length).toLowerCase();
  var needAddPic = false; // 是否需要添加图片到默认图片中.
  // 将上传的文件保存到 uploadFiles 中.
  if (document.myform.uploadFiles.value == '') {
    document.myform.uploadFiles.value = id + "|";
    needAddPic = true;
  } else {
   if (document.myform.uploadFiles.value.indexOf(id + "|") == -1) {
    document.myform.uploadFiles.value += id + "|";
      needAddPic = true;
    }
  }
  
  if (needAddPic) {
    if (fileExt=='gif'||fileExt=='jpg'||fileExt=='jpeg'||fileExt=='jpe'||fileExt=='bmp'||fileExt=='png'){
     // 如果上传的是一个图片.
      if(document.myform.includePic.selectedIndex < 2){
        document.myform.includePic.selectedIndex += 1;
      }
      document.all.frmPreview.src = url;
      document.myform.defaultPicUrl.value = url;
      document.myform.DefaultPicList.options[document.myform.DefaultPicList.length] = new Option(fileName, url);
      document.myform.DefaultPicList.selectedIndex += 1;
    }
  }
}

function selectPaginationType() {
  document.myform.PaginationType.value = 2;
}

function rUseLinkUrl() {
  if(document.myform.UseLinkUrl.checked==true) {
    document.myform.LinkUrl.disabled=false;
    ArticleContent.style.display='none';
  }
  else{
    document.myform.LinkUrl.disabled=true;
    ArticleContent.style.display='';
  }
}

// has error
function SelectUser(){
  var arr = showModalDialog('admin_user_select.jsp?defaultValue='+document.myform.InceptUser.value,
    '','dialogWidth:600px; dialogHeight:450px; help: no; scroll: yes; status: no');
  if (arr != null){
    document.myform.InceptUser.value = arr;
  }
}

// not used (2007-8-20)
function getPayMoney(){
   document.myform.CopyMoney1.value=document.myform.PerWordMoney.value*document.myform.WordNumber.value/1000;
}

function IsDigit(){
  return ((event.keyCode >= 48) && (event.keyCode <= 57));
}

function CopyTitle(){
  if (document.myform.VoteTitle.value==''){
     document.myform.VoteTitle.value = document.myform.Title.value;
  }
}

function moreitem(inputname,listnum,ichannelid,inputype){
    var chedkurl = '../inc/checklist.jsp';
    var CheckDOM = new ActiveXObject("Microsoft.XMLDOM");
    CheckDOM.async=false;
    var p = CheckDOM.createProcessingInstruction('xml','version=\"1.0\" encoding=\"gb2312\"'); 
    CheckDOM.appendChild(p); 
    var CheckRoot = CheckDOM.createElement('root');
    var CField = CheckDOM.createNode(1,'text',''); 
    CField.text = $F(inputname);
    CheckRoot.appendChild(CField);
    CField = CheckDOM.createNode(1,'lnum',''); 
    CField.text = listnum;
    CheckRoot.appendChild(CField);
    CField = CheckDOM.createNode(1,'channelid',''); 
    CField.text = ichannelid;
    CheckRoot.appendChild(CField);
    CField = CheckDOM.createNode(1,'type',''); 
    CField.text = inputype;
    CheckRoot.appendChild(CField);
    CField = CheckDOM.createNode(1,'inputname',''); 
    CField.text = inputname;
    CheckRoot.appendChild(CField);
    CheckDOM.appendChild(CheckRoot);
    var CHttp = getHTTPObject();
    CHttp.open('POST',chedkurl,true);
    CHttp.onreadystatechange = function () 
    {
        if(CHttp.readyState == 4 && CHttp.status==200){
            if(CHttp.responseText == ''){
                Element.hide(inputype);
            }else{
                Element.show(inputype);
                $(inputype).innerHTML=CHttp.responseText;
            }
        }
    }
    CHttp.send(CheckDOM);
}

function addinput(iname,ivalue){
  if(iname!='' && ivalue!=''){
     $(iname).value=ivalue;
  }
}

// 批量删除文章.
function deleteItems() {
 var ids = getSelectItemIds();
 if (ids.length < 1) {
  alert('没有选择任何' + getItemName() + '。');
 } else {
  if (confirm('确定要删除选中的这些' + getItemName() + '吗？')) {
   formAction.action = "admin_article_action.jsp";
   formAction.command.value = 'batch_delete';
   formAction.itemIds.value = ids;
   formAction.submit();
  }
 }
}

// 批量移动文章.
function moveToColumn() {
 var ids = getSelectItemIds();
 if (ids.length < 1) {
  alert('没有选择任何' + getItemName() + '。');
 } else {
  byId("moveArticleIds").value = ids;
  if (ids.length < 1) {
   byId("moveSubmit").disabled = true;
  } else {
   byId("moveSubmit").disabled = false;
  }
  byId("article_move").style.display="";
  byId("article_move_back").style.display="";
 }
}

// 审核/取消审核.
function approItems(approve) {
  var ids = getSelectItemIds();
  if (ids.length < 1) {
    alert('没有选择任何' + getItemName() + '。');
    return;
  }
 
  var status = approve ? 1 : 0;
  formAction.action = "admin_article_action.jsp";
  formAction.command.value = 'batch_appr';
  formAction.itemIds.value = ids;
  formAction.status.value = status;
  formAction.submit();
}

