// �ṩ�� admin_article_xxx.jsp ҳ��ʹ�õ�.

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

// ѡ�����е�ר��.
function selectAll() {
 var object = document.getElementById("specialIds");
 for (var i = 0; i < object.length; i++) {
  object[i].selected = true;
 }
}

// ȡ��ѡ������ר��.
function unSelectAll() {
 var object = document.getElementById("specialIds");
 for (var i = 0; i < object.length; i++) {
  object[i].selected = false;
 }
}

// ������ҳ�����е����ӵ�Ŀ�괰�ڶ�����Ϊ�����´���.
function setOpenNew() {
 var links = editor.HtmlEdit.document.getElementsByTagName("A");
 for (var i = 0; i < links.length; i++) {
  links[i].setAttribute("target", "_blank");
 }
}

//���á��Զ����ɡ�ѡ��.
function disableCreate() {
  var obj = document.getElementById("createImmediate");
  obj.checked = false;
  obj.disabled = true;
}

//�����Զ����ɡ�ѡ��.
function enabelCreate() {
  var obj = document.getElementById("createImmediate");
  obj.disabled = false;
  obj.checked = true;
}

// �� editor_modifypic.jsp ҳ�����, ����: 
// AddItem(92, 'nnnnnnnn.jpg', 'http://localhost:8080/PubWeb/xxx/xxx/nnnnnnnn.jpg')
function AddItem(id, fileName, url){ // strFileName
  var fileExt = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length).toLowerCase();
  var needAddPic = false; // �Ƿ���Ҫ���ͼƬ��Ĭ��ͼƬ��.
  // ���ϴ����ļ����浽 uploadFiles ��.
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
     // ����ϴ�����һ��ͼƬ.
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

// ����ɾ������.
function deleteItems() {
 var ids = getSelectItemIds();
 if (ids.length < 1) {
  alert('û��ѡ���κ�' + getItemName() + '��');
 } else {
  if (confirm('ȷ��Ҫɾ��ѡ�е���Щ' + getItemName() + '��')) {
   formAction.action = "admin_article_action.jsp";
   formAction.command.value = 'batch_delete';
   formAction.itemIds.value = ids;
   formAction.submit();
  }
 }
}

// �����ƶ�����.
function moveToColumn() {
 var ids = getSelectItemIds();
 if (ids.length < 1) {
  alert('û��ѡ���κ�' + getItemName() + '��');
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

// ���/ȡ�����.
function approItems(approve) {
  var ids = getSelectItemIds();
  if (ids.length < 1) {
    alert('û��ѡ���κ�' + getItemName() + '��');
    return;
  }
 
  var status = approve ? 1 : 0;
  formAction.action = "admin_article_action.jsp";
  formAction.command.value = 'batch_appr';
  formAction.itemIds.value = ids;
  formAction.status.value = status;
  formAction.submit();
}

