// ͨ���ڵ��ID�õ��ýڵ����
var byId = function (nodeId) {
	return document.getElementById(nodeId);
}

// ͨ���ڵ�����Ƶõ��ڵ��������顣
var byName = function (nodeName) {
	return document.getElementsByName(nodeName);
}

// �滻URL��ָ���� param ��ֵ�������µ�URL��
function setUrlParam(url, param, value) {
	/*
	��ȡparam�Ŀ�ʼλ�ã�
		�����Ƿ���� ?param=
		������ڣ���ֵ��

		���������

			�����Ƿ���� &param= 
			������ڣ���ֵ��

			��������ڣ���ֵΪ -1 ��

	�ж�param�Ŀ�ʼλ�ã�
	���Ϊ -1 ��URL �в����� param �Ĳ�����
		�ж�URL���Ƿ���� ?
		������ڣ������µ�URL = url + "&" + param + "=" + value ��

		��������ڣ������µ�URL = url + "?" + param + "=" + value ��

	�����Ϊ -1 ��URL �д��� param �Ĳ��������滻ԭ���� param ��ֵ

		���Ҳ��ж� param ����һ�� & ���ŵ� index�� endIndex
		��� endIndex Ϊ -1 �������µ�URL = url.substring(0, startIndex + 1) + param + "=" + value ��

		��� endIndex ��Ϊ -1�������µ�URL = url.substring(0, startIndex + 1) + param + "=" + value + url.substring(endIndex, url.length) ��

	*/
	// ����admin_article_list.jsp?param=value&channelId=1
	var startIndex = url.indexOf("?" + param + "=");
	var endIndex;
	if (startIndex == -1) {
		// ����admin_article_list.jsp?channelId=1&param=value
		startIndex = url.indexOf("&" + param + "=");
	}
	if (startIndex == -1) {	// URL �в����� param �Ĳ�����
		// ����a.jsp?channelId=1
		if (url.indexOf("?") > 5) {
			return (url + "&" + param + "=" + value);
		} else {
			return (url + "?" + param + "=" + value);
		}
	} else {				// �滻ԭ���� URL �� param ��ֵ��
		endIndex = url.indexOf("&", startIndex + 2);
		if (endIndex == -1) {
			return (url.substring(0, startIndex + 1) + param + "=" + value);
		} else {
			return (url.substring(0, startIndex + 1) + param + "=" + value + url.substring(endIndex, url.length));
		}
	}
}

// �õ� URL ��ָ������ param ��ֵ����������� param �Ĳ��������� null ��
function getUrlParam(url, param) {
	var startIndex = url.indexOf("?" + param + "=");
	var endIndex;
	if (startIndex == -1) {
		// ����admin_article_list.jsp?channelId=1&param=value
		startIndex = url.indexOf("&" + param + "=");
	}
	if (startIndex == -1) {	// URL �в����� param �Ĳ�����

		return null;
	} else {				// �滻ԭ���� URL �� param ��ֵ��
		endIndex = url.indexOf("&", startIndex + 2);
		if (endIndex == -1) {
			return url.substring(startIndex + param.length + 2, url.length);
		} else {
			return url.substring(startIndex + param.length + 2, endIndex);
		}
	}
}

//=================================================
//��������isDigit()
//��  �ã�����Ϊ����
//=================================================
function isDigit() {
  return ((event.keyCode >= 48) && (event.keyCode <= 57));
}

// ���˵��ַ�����ǰ��ո�
function trim(str){
	return str.replace(/^\s+|\s+$/g,"");
}

// �������ո����û�����룬��Ϊ�� null ��
function isEmpty(str) {
	if (str == null || (typeof str == "undefined") || trim(str).length < 1) {
		return true;
	} else {
		return false;
	}
}

// ======================== �뷢��ϵͳ�й�����JS���� ========================

// ѡ�����£�ȫѡ/ȡ��ȫѡ��������ID�� checkBox ��name���Ա���Ϊ itemId ��
function CheckAll(object) {
	var items = byName("itemId");
	for (var i = 0; i < items.length; i++) {
		items[i].checked = object.checked;
	}
}

// ��ȡѡ�е���Ŀ�ı�ʶ����ĿID�� checkBox ��name���Ա���Ϊ itemId ��
function getSelectItemIds() {
	var items = byName("itemId");
	var temp = "";
	for (var i = 0; i < items.length; i++) {
		if (items[i].checked) {
			temp += items[i].value + ",";
		}
	}
	if (temp.length > 0) {
		temp = temp.substring(0, temp.length - 1);
	}
	return temp;
}

// ����ָ�� id �Ķ���� display ��ʽ��
function setDisplay(objectId, style) {
	var object = byId(objectId);
	if (object) {
		object.style.display = style;
	} else {
		alert("ָ����Ԫ�أ�" + objectId + "�������ڡ�");
	}
}

// ��ָ֤����ͼƬ��ַ�Ƿ�����ȷ�ĵ�ַ��
// param: img1 ��ʾͼƬ��Ԫ�� img �� ID��
// param: url ͼƬ��ַ��Ԫ�� �� ID��
// param: errImg ͼƬ��ַͼ��ȷ��ʱ��ִ�еķ�����û���κβ�����
function checkImage(/** ͼƬ��ID */img1, /** ͼƬ��ַ��ID */url, /** �����ǵĴ��� */errImg){
	var img = byId(img1);
	if (img == null) {
		img = document.createElement("img");
	}
	img.detachEvent("onerror", errImg);
	var newUrl = byId(url).value;
//	if(/^.+\.(gif|jpg|png|bmp|jpeg)$/i.test(newUrl)){
		img.attachEvent("onerror", errImg);
		img.src = newUrl;
//	} else {
//		errImageType();
//	}
}

// ��URL�����UTF-8��ʽ
function URLEncode(strURL)
{
	var strSpecialUrl = " <>\"#%{}|^~[]`'&?+";
	var strEncode="";
	var i, j, chUrl, iCode, iCodeBin, num;
	var tempBin;
	var leadingzeros;
	
	strURL+="";
	for (i=0; i<strURL.length; i++) {
	chUrl = strURL.charAt(i);
	iCode = chUrl.charCodeAt(0);
	if (iCode<=parseInt("0x7F")) {
		if (strSpecialUrl.indexOf(chUrl)!=-1) {
		//chUrl is a special character that needs to be Url encoded
		strEncode+="%"+iCode.toString(16).toUpperCase();
		} else {
		//otherwise chrUrl is normal
		strEncode+=chUrl;
		}
	} else {
		leadingzeros="";
		iCodeBin=iCode.toString(2)
		if (iCode<=parseInt(0x7FF)) {
		//glyph is represented by two chars
		
		//check leading zeros on iCodeBin (should be 11 digits)
		for (j=11; j>iCodeBin.length; j--) leadingzeros+="0";
		iCodeBin=leadingzeros+iCodeBin
		
		tempBin="110"+iCodeBin.substr(0,5);
		strEncode+="%"+parseInt(tempBin,2).toString(16).toUpperCase()
		tempBin="10"+iCodeBin.substr(5,6);
		strEncode+="%"+parseInt(tempBin,2).toString(16).toUpperCase()
		} else {
		if (iCode<=parseInt(0xFFFF)) {
		//glyph is represented by three chars
		
		//check leading zeros on iCodeBin (should be 16 digits)
		for (j=16; j>iCodeBin.length; j--) leadingzeros+="0";
		iCodeBin=leadingzeros+iCodeBin
		
		tempBin="1110"+iCodeBin.substr(0,4);
		strEncode+="%"+parseInt(tempBin,2).toString(16).toUpperCase()
		tempBin="10"+iCodeBin.substr(4,6);
		strEncode+="%"+parseInt(tempBin,2).toString(16).toUpperCase()
		tempBin="10"+iCodeBin.substr(10,6);
		strEncode+="%"+parseInt(tempBin,2).toString(16).toUpperCase()
		} else {
		if (iCode<=parseInt(0x1FFFFF)) {
		//glyph is represented by four chars
		
		//check leading zeros on iCodeBin (should be 21 digits)
		for (j=21; j>iCodeBin.length; j--) leadingzeros+="0";
		iCodeBin=leadingzeros+iCodeBin
		
		tempBin="11110"+iCodeBin.substr(0,3);
		strEncode+="%"+parseInt(tempBin,2).toString(16).toUpperCase()
		tempBin="10"+iCodeBin.substr(3,6);
		strEncode+="%"+parseInt(tempBin,2).toString(16).toUpperCase()
		tempBin="10"+iCodeBin.substr(9,6);
		strEncode+="%"+parseInt(tempBin,2).toString(16).toUpperCase()
		tempBin="10"+iCodeBin.substr(15,6);
		strEncode+="%"+parseInt(tempBin,2).toString(16).toUpperCase()
		} else {
		if (iCode<=parseInt(0x3FFFFFF)) {
		//glyph is represented by five chars
		
		//check leading zeros on iCodeBin (should be 26 digits)
		for (j=26; j>iCodeBin.length; j--) leadingzeros+="0";
		iCodeBin=leadingzeros+iCodeBin
		
		tempBin="111110"+iCodeBin.substr(0,2);
		strEncode+="%"+parseInt(tempBin,2).toString(16).toUpperCase()
		tempBin="10"+iCodeBin.substr(2,6);
		strEncode+="%"+parseInt(tempBin,2).toString(16).toUpperCase()
		tempBin="10"+iCodeBin.substr(8,6);
		strEncode+="%"+parseInt(tempBin,2).toString(16).toUpperCase()
		tempBin="10"+iCodeBin.substr(14,6);
		strEncode+="%"+parseInt(tempBin,2).toString(16).toUpperCase()
		tempBin="10"+iCodeBin.substr(20,6);
		strEncode+="%"+parseInt(tempBin,2).toString(16).toUpperCase()
		} else {
		if (iCode<=parseInt(0x7FFFFFFF)) {
		//glyph is represented by six chars
		
		//check leading zeros on iCodeBin (should be 31 digits)
		for (j=31; j>iCodeBin.length; j--) leadingzeros+="0";
		iCodeBin=leadingzeros+iCodeBin
		
		tempBin="1111110"+iCodeBin.substr(0,1);
		strEncode+="%"+parseInt(tempBin,2).toString(16).toUpperCase()
		tempBin="10"+iCodeBin.substr(1,6);
		strEncode+="%"+parseInt(tempBin,2).toString(16).toUpperCase()
		tempBin="10"+iCodeBin.substr(7,6);
		strEncode+="%"+parseInt(tempBin,2).toString(16).toUpperCase()
		tempBin="10"+iCodeBin.substr(13,6);
		strEncode+="%"+parseInt(tempBin,2).toString(16).toUpperCase()
		tempBin="10"+iCodeBin.substr(19,6);
		strEncode+="%"+parseInt(tempBin,2).toString(16).toUpperCase()
		tempBin="10"+iCodeBin.substr(25,6);
		strEncode+="%"+parseInt(tempBin,2).toString(16).toUpperCase()
		}
		}
		}
		}
		}
		}
	}
	return strEncode;
}


