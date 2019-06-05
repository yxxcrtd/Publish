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

// ѡ�����е�ר��.
function selectAll() {
 var object = byId("specialIds");
 for (var i = 0; i < object.length; i++) {
  object[i].selected = true;
 }
}

// ȡ��ѡ������ר��.
function unSelectAll() {
 var object = byId("specialIds");
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
 var obj = byId("createImmediate");
 obj.checked = false;
 obj.disabled = true;
}

//�����Զ����ɡ�ѡ��.
function enabelCreate() {
 var obj = byId("createImmediate");
 obj.disabled = false;
 obj.checked = true;
}

// ���ϴ����ļ���ѡ��ͼƬ/���.
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
    alert("��֧����չ��Ϊ " + fileExt + " ��ͼƬ�ļ���ֻ������չ��Ϊ " + imageExts + " ��ͼƬ�ļ���");
    return;
   }   
    byId(FieldName).value=ss[2];
  } else {
   var select = byId(FieldName);
   var url = "���ص�ַ" + (select.length + 1) + "|" + ss[1];
   select.options[select.length] = new Option(url, url);
   byId("softSize").value = ss[3];
   
   setDownloadUrls();
  }
 }
}

function AddUrl(itemName){
 var select = byId('downloadUrl');
 var thisurl= itemName +'��ַ'+ (select.length + 1) + '|http://'; 
 var url = prompt('������' + itemName + '��ַ���ƺ����ӣ��м��á�|��������', thisurl);
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
  alert('����ѡ��һ��' + itemName + '��ַ���ٵ��޸İ�ť��');
  return false;
 }
 var url = prompt('������' + itemName + '��ַ���ƺ����ӣ��м��á�|��������', thisurl);
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
  alert('����ѡ��һ��' + itemName + '��ַ���ٵ�ɾ����ť��');
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



// �ϴ���һ���ļ������ô˺���.
function setFileOptions(options) {
 if (options.caller == 'uploadThumbPic') {
  byId('softThumbPic').value = options.url;
 } else {
  var select = byId('downloadUrl');
  var thisUrl = getItemName() + "��ַ" + (select.length + 1) + "|" + options.url;
  select.options[select.length] = new Option(thisUrl, thisUrl);

  // TODO�������ϴ��ļ��Ĵ�С�Ĵ���.
  setDownloadUrls();
 }
}

// ��֤���ύ.
function checkForm() {
 var CurrentMode=editor.CurrentMode;
 if (CurrentMode==0){
  setOpenNew();
  document.myform.description.value=editor.HtmlEdit.document.body.innerHTML; 
 } else if (CurrentMode==1){
  setOpenNew();
  document.myform.description.value=editor.HtmlEdit.document.body.innerText;
 } else {
  alert('Ԥ��״̬���ܱ��棡���Ȼص��༭״̬���ٱ���');
  return false;
 }
 
 if (document.myform.softName.value == '') {
  showTab("baseInfo");
  alert(getItemName() + "���ⲻ��Ϊ�գ�");
  document.myform.softName.focus();
  return false;
 } 
 
 if (document.myform.keywords.value == ''){
     showTab("baseInfo");
     alert('�ؼ��ֲ���Ϊ�գ�');
     document.myform.keywords.focus();
     return false;
 } 

 
    if (document.myform.description.value==''){
       showTab("baseInfo");
       alert(getItemName() + '��鲻��Ϊ�գ�');
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
          alert(getItemName() + '������Ŀ����ָ��Ϊ�ⲿ��Ŀ��');
          obj.focus();
          return false;
        }
        if(obj.options[i].selected == true && obj.options[i].value == ''){
          showTab("baseInfo");
          alert('ָ������Ŀ���������' + getItemName() +'��ֻ������������Ŀ�����' + getItemName() + '��');
          obj.focus();
          return false;
        }
     }
 }
 if (iCount==0){
     showTab("baseInfo");
     alert('��ѡ��������Ŀ��');
     obj.focus();
     return false;
 }
 return true;
}
