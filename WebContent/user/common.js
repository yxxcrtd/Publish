// 通过节点的ID得到该节点对象。
var byId = function (nodeId) {
	return document.getElementById(nodeId);
}

// 通过节点的名称得到节点对象的数组。
var byName = function (nodeName) {
	return document.getElementsByName(nodeName);
}

// 替换URL中指定的 param 的值，返回新的URL。
function setUrlParam(url, param, value) {
	/*
	获取param的开始位置：
		查找是否存在 ?param=
		如果存在，赋值。

		如果不存在

			查找是否存在 &param= 
			如果存在，赋值。

			如果不存在，赋值为 -1 。

	判断param的开始位置：
	如果为 -1 （URL 中不存在 param 的参数）
		判断URL中是否存在 ?
		如果存在，返回新的URL = url + "&" + param + "=" + value 。

		如果不存在，返回新的URL = url + "?" + param + "=" + value 。

	如果不为 -1 （URL 中存在 param 的参数），替换原来的 param 的值

		查找并判断 param 后面一个 & 符号的 index， endIndex
		如果 endIndex 为 -1 ，返回新的URL = url.substring(0, startIndex + 1) + param + "=" + value 。

		如果 endIndex 不为 -1，返回新的URL = url.substring(0, startIndex + 1) + param + "=" + value + url.substring(endIndex, url.length) 。

	*/
	// 例：admin_article_list.jsp?param=value&channelId=1
	var startIndex = url.indexOf("?" + param + "=");
	var endIndex;
	if (startIndex == -1) {
		// 例：admin_article_list.jsp?channelId=1&param=value
		startIndex = url.indexOf("&" + param + "=");
	}
	if (startIndex == -1) {	// URL 中不存在 param 的参数。
		// 例：a.jsp?channelId=1
		if (url.indexOf("?") > 5) {
			return (url + "&" + param + "=" + value);
		} else {
			return (url + "?" + param + "=" + value);
		}
	} else {				// 替换原来的 URL 中 param 的值。
		endIndex = url.indexOf("&", startIndex + 2);
		if (endIndex == -1) {
			return (url.substring(0, startIndex + 1) + param + "=" + value);
		} else {
			return (url.substring(0, startIndex + 1) + param + "=" + value + url.substring(endIndex, url.length));
		}
	}
}

// 得到 URL 中指定参数 param 的值，如果不存在 param 的参数，返回 null 。
function getUrlParam(url, param) {
	var startIndex = url.indexOf("?" + param + "=");
	var endIndex;
	if (startIndex == -1) {
		// 例：admin_article_list.jsp?channelId=1&param=value
		startIndex = url.indexOf("&" + param + "=");
	}
	if (startIndex == -1) {	// URL 中不存在 param 的参数。

		return null;
	} else {				// 替换原来的 URL 中 param 的值。
		endIndex = url.indexOf("&", startIndex + 2);
		if (endIndex == -1) {
			return url.substring(startIndex + param.length + 2, url.length);
		} else {
			return url.substring(startIndex + param.length + 2, endIndex);
		}
	}
}

//=================================================
//过程名：isDigit()
//作  用：输入为数字
//=================================================
function isDigit() {
  return ((event.keyCode >= 48) && (event.keyCode <= 57));
}

// 过滤掉字符串的前后空格。
function trim(str){
	return str.replace(/^\s+|\s+$/g,"");
}

// 如果输入空格或者没有输入，认为是 null 。
function isEmpty(str) {
	if (str == null || (typeof str == "undefined") || trim(str).length < 1) {
		return true;
	} else {
		return false;
	}
}

// ======================== 与发布系统有关联的JS方法 ========================

// 选中文章（全选/取消全选），文章ID的 checkBox 的name属性必须为 itemId 。
function CheckAll(object) {
	var items = byName("itemId");
	for (var i = 0; i < items.length; i++) {
		items[i].checked = object.checked;
	}
}

// 获取选中的项目的标识，项目ID的 checkBox 的name属性必须为 itemId 。
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

// 设置指定 id 的对象的 display 样式。
function setDisplay(objectId, style) {
	var object = byId(objectId);
	if (object) {
		object.style.display = style;
	} else {
		alert("指定的元素（" + objectId + "）不存在。");
	}
}

// 验证指定的图片地址是否是正确的地址。
// param: img1 显示图片的元素 img 的 ID。
// param: url 图片地址的元素 的 ID。
// param: errImg 图片地址图正确的时候执行的方法，没有任何参数。
function checkImage(/** 图片的ID */img1, /** 图片地址的ID */url, /** 错误是的处理 */errImg){
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

// 将URL编码成UTF-8格式
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


