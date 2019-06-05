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

// ѡ�����е�ר��
function selectAll() {
 var object = document.getElementById("specialIds");
 for (var i = 0; i < object.length; i++) {
  object[i].selected = true;
 }
}

// ȡ��ѡ������ר��
function unSelectAll() {
 var object = document.getElementById("specialIds");
 for (var i = 0; i < object.length; i++) {
  object[i].selected = false;
 }
}

// ������ҳ�����е����ӵ�Ŀ�괰�ڶ�����Ϊ�����´��ڡ�
function setOpenNew() {
 var links = editor.HtmlEdit.document.getElementsByTagName("A");
 for (var i = 0; i < links.length; i++) {
  links[i].setAttribute("target", "_blank");
 }
}

//���á��Զ����ɡ�ѡ��
function disableCreate() {
 var obj = document.getElementById("createImmediate");
 obj.checked = false;
 obj.disabled = true;
}

//�����Զ����ɡ�ѡ��
function enabelCreate() {
 var obj = document.getElementById("createImmediate");
 obj.disabled = false;
 obj.checked = true;
}

// ���ϴ����ļ���ѡ��ͼƬ��
function SelectFile(FieldName, channelId, itemName){
  var arr = showModalDialog('admin_upload_list.jsp?selectFile=true&channelId=' + channelId, '', 'dialogWidth:820px; dialogHeight:600px; help: no; scroll: yes; status: no');
  // id$$$fileName&&&filePath&&&fileSize
  if(arr != null){
    var ss = arr.split('$$$');
    var fileName = ss[2];
    var fileExt = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length);
    var imageExts = "jpg,jpeg,tif,bmp,bm,png,psd,gif";
    if (imageExts.indexOf(fileExt.toLowerCase()) == -1) {
     alert("��֧����չ��Ϊ " + fileExt + " ��ͼƬ�ļ���ֻ������չ��Ϊ " + imageExts + " ��ͼƬ�ļ���");
     return;
    }
    //upfilename.value = arr;
    var obj = byId(FieldName);
    if (!obj.options) {
     obj.value = ss[2];
    } else {
     var url = itemName +'��ַ'+ (obj.length + 1) + ss[2];
     obj.options[obj.length] = new Option(url, url);     
     setPictureUrls();
    }
  }
}

function SetThumb(itemName){
 var select = byId('pictureUrl');
 if(select.length == 0) return false;
 if (select.selectedIndex < 0 || select.value == '') {
  alert('����ѡ��һ��' + itemName + '��ַ���ٵ���Ϊ����ͼ��ť��');
  return false;
 } else {
  byId('thumbPic').value = select.value.substring(select.value.indexOf('|') + 1);
 }
}

function AddUrl(itemName){
 var select = byId('pictureUrl');
 var thisurl= itemName +'��ַ'+ (select.length + 1) + '|http://'; 
 var url = prompt('������' + itemName + '��ַ���ƺ����ӣ��м��á�|��������', thisurl);
 if(url != null && trim(url) != ''){
    select.options[select.length] = new Option(url, url);
   }
   
   setPictureUrls();
}

//
function ModifyUrl(itemName) {
 var select = byId('pictureUrl');
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
 
 setPictureUrls();
}

function DeleteUrl(itemName){
 var select = byId('pictureUrl');
 if(select.length == 0) return false;
 var thisurl = select.value; 
 if (thisurl == '') {
  alert('����ѡ��һ��' + itemName + '��ַ���ٵ�ɾ����ť��');
  return false;
 }
 select.options[select.selectedIndex] = null;
 
 setPictureUrls();
}

function setPictureUrls() {
 var select = byId('pictureUrl');
 var urls = "";
 for (var i = 0; i < select.length; i++) {
  if (i > 0) urls += "$$$";  
  urls += select.options[i].value;
 }
 byId('pictureUrls').value = urls;
}

// �ϴ���һ���ļ������ô˺���
function setFileOptions(options) {
  var select = byId('pictureUrl');
  var thisUrl = getItemName() + "��ַ" + (select.length + 1) + "|" + options.url;
  select.options[select.length] = new Option(thisUrl, thisUrl);
 
  setPictureUrls();
 
  // ��������ͼ
  if (byId('thumbPic').value == '') {
    byId('thumbPic').value = options.url;
  }
 
  // ��ʾ �������ϴ���ť��
  if (byId('btnContinueUpload').style.display = 'none') {
    byId('btnContinueUpload').style.display = '';
  }
}

// ��֤���ύ��
function checkForm() {
 var CurrentMode = editor.CurrentMode;
 //return false;
 if (CurrentMode==0){
  setOpenNew();
  document.myform.description.value = editor.HtmlEdit.document.body.innerHTML; 
 } else if (CurrentMode == 1){
  setOpenNew();
  document.myform.description.value = editor.HtmlEdit.document.body.innerText;
 } else {
  alert('Ԥ��״̬���ܱ��棡���Ȼص��༭״̬���ٱ���');
  return false;
 }
 
 if (document.myform.photoTitle.value == '') {
  showTab("baseInfo");
  alert(getItemName() + "���ⲻ��Ϊ�գ�");
  document.myform.photoTitle.focus();
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
       alert(getItemName() + '���ݲ���Ϊ�գ�');
       editor.HtmlEdit.focus();
       return false;
    }
 
 var obj = document.myform.columnId;
 var iCount = 0;
 for(var i = 0; i < obj.length; i++){
  if(obj.options[i].selected == true){
        iCount = iCount + 1;
        if(obj.options[i].value == ''){
          showTab("baseInfo");
          alert(getItemName() + '������Ŀ����ָ��Ϊ�ⲿ��Ŀ��');
          obj.focus();
          return false;
        }
        if(obj.options[i].selected == true && obj.options[i].value == '0'){
          showTab("baseInfo");
          alert('ָ������Ŀ���������' + getItemName() + '��ֻ������������Ŀ�����' + getItemName() + '��');
          obj.focus();
          return false;
        }
     }
 }
 if (iCount == 0){
     showTab("baseInfo");
     alert('��ѡ��������Ŀ��');
     obj.focus();
     return false;
 }
 return true;
}
