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

// 选中所有的专题.
function selectAll() {
 var object = byId("specialIds");
 for (var i = 0; i < object.length; i++) {
  object[i].selected = true;
 }
}

// 取消选中所有专题.
function unSelectAll() {
 var object = byId("specialIds");
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
 var obj = byId("createImmediate");
 obj.checked = false;
 obj.disabled = true;
}

//允许“自动生成”选项.
function enabelCreate() {
 var obj = byId("createImmediate");
 obj.disabled = false;
 obj.checked = true;
}

// 从上传的文件中选择图片/软件.
function SelectFile(FieldName, channelId){
 var arr=showModalDialog('admin_upload_list.jsp?selectFile=true&channelId=' + channelId, '', 'dialogWidth:820px; dialogHeight:600px; help: no; scroll: yes; status: no');
 // id$$$fileName&&&filePath&&&fileSize
 if(arr != null){
  var ss = arr.split('$$$');
  if (FieldName == 'softThumbPic') {
   var fileName = ss[1];
   var fileExt = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length);
   var imageExts = "jpg,jpeg,tif,bmp,bm,png,psd,gif";
   if (imageExts.indexOf(fileExt.toLowerCase()) == -1) {
    alert("不支持扩展名为 " + fileExt + " 的图片文件，只允许扩展名为 " + imageExts + " 的图片文件。");
    return;
   }   
    byId(FieldName).value=ss[2];
  } else {
   var select = byId(FieldName);
   var url = "下载地址" + (select.length + 1) + "|" + ss[1];
   select.options[select.length] = new Option(url, url);
   byId("softSize").value = ss[3];
   
   setDownloadUrls();
  }
 }
}

function AddUrl(itemName){
 var select = byId('downloadUrl');
 var thisurl= itemName +'地址'+ (select.length + 1) + '|http://'; 
 var url = prompt('请输入' + itemName + '地址名称和链接，中间用“|”隔开：', thisurl);
 if(url != null && trim(url) != ''){
    select.options[select.length] = new Option(url, url);
   }
   
   setDownloadUrls();
}

function ModifyUrl(itemName){
 var select = byId('downloadUrl');
 if(select.length == 0) return false;
 var thisurl = select.value; 
 if (thisurl == '') {
  alert('请先选择一个' + itemName + '地址，再点修改按钮！');
  return false;
 }
 var url = prompt('请输入' + itemName + '地址名称和链接，中间用“|”隔开：', thisurl);
 if(url != thisurl && url != null && url != ''){
  select.options[select.selectedIndex]=new Option(url,url);
 }
 
 setDownloadUrls();
}

function DeleteUrl(itemName){
 var select = byId('downloadUrl');
 if(select.length == 0) return false;
 var thisurl = select.value; 
 if (thisurl == '') {
  alert('请先选择一个' + itemName + '地址，再点删除按钮！');
  return false;
 }
 select.options[select.selectedIndex] = null;
 
 setDownloadUrls();
}

function setDownloadUrls() {
 var select = byId('downloadUrl');
 var urls = "";
 for (var i = 0; i < select.length; i++) {
  if (i > 0) urls += "$$$";  
  urls += select.options[i].value;
 }
 byId('downloadUrls').value = urls;
}



// 上传完一个文件后会调用此函数.
function setFileOptions(options) {
 if (options.caller == 'uploadThumbPic') {
  byId('softThumbPic').value = options.url;
 } else {
  var select = byId('downloadUrl');
  var thisUrl = getItemName() + "地址" + (select.length + 1) + "|" + options.url;
  select.options[select.length] = new Option(thisUrl, thisUrl);

  // TODO：增加上传文件的大小的处理.
  setDownloadUrls();
 }
}

// 验证表单提交.
function checkForm() {
 var CurrentMode=editor.CurrentMode;
 if (CurrentMode==0){
  setOpenNew();
  document.myform.description.value=editor.HtmlEdit.document.body.innerHTML; 
 } else if (CurrentMode==1){
  setOpenNew();
  document.myform.description.value=editor.HtmlEdit.document.body.innerText;
 } else {
  alert('预览状态不能保存！请先回到编辑状态后再保存');
  return false;
 }
 
 if (document.myform.softName.value == '') {
  showTab("baseInfo");
  alert(getItemName() + "标题不能为空！");
  document.myform.softName.focus();
  return false;
 } 
 
 if (document.myform.keywords.value == ''){
     showTab("baseInfo");
     alert('关键字不能为空！');
     document.myform.keywords.focus();
     return false;
 } 

 
    if (document.myform.description.value==''){
       showTab("baseInfo");
       alert(getItemName() + '简介不能为空！');
       editor.HtmlEdit.focus();
       return false;
    }
 
 var obj=document.myform.columnId;
 var iCount=0;
 for(var i=0;i<obj.length;i++){
  if(obj.options[i].selected==true){
        iCount=iCount+1;
        if(obj.options[i].value==''){
          showTab("baseInfo");
          alert(getItemName() + '所属栏目不能指定为外部栏目！');
          obj.focus();
          return false;
        }
        if(obj.options[i].selected == true && obj.options[i].value == ''){
          showTab("baseInfo");
          alert('指定的栏目不允许添加' + getItemName() +'！只允许在其子栏目中添加' + getItemName() + '。');
          obj.focus();
          return false;
        }
     }
 }
 if (iCount==0){
     showTab("baseInfo");
     alert('请选择所属栏目！');
     obj.focus();
     return false;
 }
 return true;
}
