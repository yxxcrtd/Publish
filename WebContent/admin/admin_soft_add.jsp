<%@page language="java" contentType="text/html; charset=gb2312" 
   pageEncoding="UTF-8" errorPage="admin_error.jsp"
%><%@taglib prefix="pub" uri="/WEB-INF/publish.tld"
%><%@page import="com.chinaedustar.publish.admin.SoftManage"
%><%
  // 初始化页面数据。
  SoftManage admin_data = new SoftManage(pageContext);
  admin_data.initEditPage();
  
%><!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>软件管理</title>
<link href='admin_style.css' rel='stylesheet' type='text/css' />
<script type="text/javascript" src="main.js"></script>
<script type="text/javascript" src='admin_soft_add.js'></script>
</head>
<body leftmargin='2' topmargin='0' marginwidth='0' marginheight='0'>

<pub:declare>

<!-- 定义标签页集合。 -->
<pub:tabs var="contentTabs" scope="page" purpose="">
 <pub:tab name="baseInfo" text="基本信息" template="temp_baseInfo" default="true" />
 <pub:tab name="specialSet" text="所属专题" template="temp_specialSet" />
 <pub:tab name="softParam" text="软件参数" template="temp_softParam" />
 <pub:tab name="propertySet" text="属性设置" template="temp_propertySet" />
</pub:tabs>

<%@ include file="element_soft.jsp" %>
<%@ include file="element_column.jsp" %>
<%@ include file="tabs_tmpl2.jsp" %>


<pub:template name="main">
 #{call soft_manage_navigator("添加" + channel.itemName) }<br />
 #{call your_position }
<form method='post' name='myform' onSubmit='return checkForm();' action='admin_soft_save.jsp' target='_self'>
<div>
 #{call tab_js }
 #{call tab_header(contentTabs) }
 #{call tab_content(contentTabs) }
</div>
<p align='center'>
 <input name='command' type='hidden' value='save' />
 <input name='__itemName' type='hidden' value='#{channel.itemName }' />
 <input name='channelId' type='hidden' id='channelId' value='#{soft.channelId }' />
 <input name='softId' type="hidden" value="#{soft.id }" />
 <input name='editorName' type="hidden" value="#{soft.editor@html }" />
 
 <input name='add' type='submit' id='Add' value=' #{iif (soft.id > 0, "修 改", "添 加") } '
   onclick="document.myform.action='admin_soft_action.jsp';document.myform.target='_self';"
   style='cursor:hand;' /> &nbsp; 
 <input name='Preview' type='submit'id='preview' value=' 预 览 '
   onclick="document.myform.action='admin_soft_preview.jsp';document.myform.target='_blank';" style='cursor:hand;' />
 <input name='Cancel' type='button' id='Cancel' value=' 取 消 ' onclick="history.go(-1);" style='cursor:hand;' />
</p>
<br />
</form>
</pub:template>



<pub:template name="your_position">
<table width='100%'>
 <tr>
  <td align='left'>
  您现在的位置： <a href='admin_soft_list.jsp?channelId=#{channel.id }'>#{channel.name }管理</a>
    &gt;&gt; 添加#{channel.itemName }
  </td>
 </tr>
</table>
</pub:template>



<!-- 基本信息 -->
<pub:template name="temp_baseInfo">
<table width='98%' border='0' cellpadding='2' cellspacing='1' bgcolor='#FFFFFF'>
 <tr class='tdbg'>
  <td width='120' align='right' class='tdbg5'>所属栏目：</td>
  <td>
  <select name="columnId">     
   <option value="0" parentPath="/">频道根栏目</option>
   #{call dropDownColumns(soft.columnId, dropdown_columns, false) }
  </select>
   &nbsp;&nbsp;<font color='#FF0000'><strong>注意：</strong> </font> 
   <font color='#0000FF'>不能指定为外部栏目，栏目颜色为灰时不能添加#{channel.itemName}</font>
  </td>
 </tr>
 <tr class='tdbg'>
  <td width='120' align='right' class='tdbg5'>
   #{channel.itemName }名称：
  </td>
  <td>
   <input name='softName' type='text' value='#{soft.title }' size='50' maxlength='255'>
   <font color='#FF0000'>*</font>
   <input type='button' name='checksame' value='检查是否存在相同的#{channel.itemName }名'
    onclick="showModalDialog('admin_check_same_title.jsp?channelId=#{channel.id }&title=' + URLEncode(document.myform.softName.value),'checksame','dialogWidth:350px; dialogHeight:250px; help: no; scroll: no; status: no');">
   <br>
   <input name='commentFlag' type='checkbox' id='commentFlag' value='1'  #{iif(soft.commentFlag == 1, "checked", "") }/> 显示#{channel.itemName}列表时在标题旁显示评论链接
  </td>
 </tr>
 <tr class='tdbg'>
  <td width='120' align='right' class='tdbg5'>软件版本：</td>
  <td><input name='softVersion' id='softVersion' type='text' size='15' maxlength='100' value="#{soft.version }"></td>
 </tr> 
 <tr class='tdbg'>
  <td width='120' align='right' class='tdbg5'>关键字：</td>
  <td>
   <div style="clear: both;">
    <input name='keywords' type='text' style="clear:both" id='keywords' 
      value='#{soft.keywords@html }' autocomplete='off' size='50' maxlength='255' />
    <font color='#FF0000'> * </font>
    #{foreach keyword in keyword_list }
     <font color='blue'>【<font  color='green' onclick="byId('keywords').value+=(byId('keywords').value==''?'':'|')+'#{keyword}'" style="cursor:hand;">#{keyword}</font>】
    #{/foreach }
    【<font color='green' onclick="window.open('admin_keyword_choose.jsp?channelId=#{channel.id }', 'KeywordList', 'width=600,height=450,resizable=0,scrollbars=yes');"  style="cursor:hand;">更多</font>】 </font>
   </div>
   <div id="skey" style='display:none'></div>
   <font color='blue'> 用来查找相关#{channel.itemName}，可输入多个关键字，中间用 
    <font color='red'> “|” </font> 隔开。不能出现&quot;'&amp;?;:()等字符。 </font>
   </td>
 </tr>  
 <tr class='tdbg'>
  <td width='120' align='right' class='tdbg5'>缩略图：</td>
  <td>
   <input name='softThumbPic' type='text' id='softThumbPic' size='80' maxlength='200' value='#{soft.thumbPic }'>
   <input type='button' name='Button2' value='从已上传图片中选择' onclick="SelectFile('softThumbPic', #{channel.id })">
  </td>
 </tr> 
 <tr class='tdbg'>
 <td width='120' align='right' class='tdbg5'></td>
  <td valign="top">
  <table>
   <tr>
    <td>上传#{channel.itemName }缩略图片：</td>
    <td>
     <iframe style='top:2px' id='UploadFiles' src='../editor/upload.jsp?channelId=#{channel.id }&caller=uploadThumbPic&fileType=1' frameborder=0
      scrolling=no width='450' height='25' marginheight="0" marginwidth="0"></iframe> 
    </td>
   </tr>
  </table>     
  </td>
 </tr>  
 <tr class='tdbg'>
  <td width='120' align='right' class='tdbg5'>#{channel.itemName }简介：</td>
  <td>
   <textarea name='description' cols='80' rows='10' id='description' style='display:none'>#{soft.description@html }</textarea>
   <iframe ID='editor' src='../editor/editor_new.jsp?channelId=1&showType=3&tContentID=description' 
     frameborder='1' scrolling='no' width='700' height='200'></iframe>
  </td>
 </tr>  
 <tr class='tdbg'>
  <td width='120' align='right' class='tdbg5'>#{channel.itemName }地址：</td>
  <td>
   <table width='100%' border='0' cellpadding='0' cellspacing='0'>
    <tr>
     <td>
      <input type='hidden' name='downloadUrls' id='downloadUrls' value='#{soft.downloadUrls }'>
      <select name='downloadUrl' id='downloadUrl' style='width:400;height:100' size='2' ondblclick='return ModifyUrl("#{channel.itemName}");'>
       #{foreach url_entry in soft.downloadUrlList}
        <option value="#{url_entry}">#{url_entry}</option>
       #{/foreach }
      </select>
     </td>
     <td>
      <input type='button' name='softselect' value='从已上传#{channel.itemName }中选择' onclick='SelectFile("downloadUrl", #{channel.id })'><br><br>
      <input type='button' name='addurl' value='添加外部地址' onclick='AddUrl("#{channel.itemName }");'><br>
      <input type='button' name='modifyurl' value='修改当前地址' onclick='return ModifyUrl("#{channel.itemName }");'><br>
      <input type='button' name='deleteurl' value='删除当前地址' onclick='DeleteUrl("#{channel.itemName }");'>
     </td>
    </tr>
   </table>
  </td>
 </tr> 
 <tr class='tdbg'>
  <td width='120' align='right' class='tdbg5'>上传#{channel.itemName }：</td>
  <td>
   系统提供的上传功能只适合上传比较小的软件（如JSP源代码压缩包）。如果软件比较大（2M以上），请先使用FTP上传，而不要使用系统提供的上传功能，以免上传出错或过度占用服务器的CPU资源。FTP上传后请将地址复制到下面的地址框中。
   <br>
   <iframe style='top:2px' ID='UploadFiles' src='../editor/upload.jsp?channelId=#{channel.id }&caller=uploadSoft' frameborder=0 scrolling=no width='450' height='25'></iframe>
  </td>
 </tr> 
 <tr class='tdbg'>
  <td width='120' align='right' class='tdbg5'>#{channel.itemName }大小：</td>
  <td><input name='softSize' type='text' id='softSize' size='10' maxlength='10' 
    value='#{iif(soft.size >0, soft.size, "") }'> K</td>
 </tr>   
 <tr class='tdbg'>
  <td width='120' align='right' class='tdbg5'>#{channel.itemName }状态：</td>
  <td>
  <input name='status' type='radio' id='status' value='-1' #{iif (soft.status == -1, "checked ", "") } 
    onclick="disableCreate()"/>草稿 &nbsp; &nbsp; 
  <input name='status' Type='Radio' id='status' value='0' #{iif (soft.status == 0, "checked ", "") } 
    onclick="disableCreate()"/>待审核 &nbsp; &nbsp; 
  <input name='status' Type='Radio' Id='status' value='1' #{iif (soft.status == 1, "checked ", "") } 
    onclick="enabelCreate()"/>终审通过 &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp; &nbsp; &nbsp; 
  <input name='createImmediate' id='createImmediate' type='checkbox' value='true' />立即生成
  </td>
 </tr>
</table>
</pub:template>



<!-- 所属专题 -->
<pub:template name="temp_specialSet">
<table width='98%' border='0' cellpadding='2' cellspacing='1' bgcolor='#FFFFFF'>
 <tr class='tdbg'>
  <td width='120' align='right' class='tdbg5'>所属专题：</td>
  <td><select name='specialIds' size='2' multiple='true'
   style='height:300px;width:260px;'>
   #{foreach special in channel_specials }
   <option value='#{special.id }' >#{special.name }（#{iif (special.channelId==0, "全站", "本频道") }）</option>
   #{/foreach }
  </select> <br />
  <input type='button' name='Submit' value='  选定所有专题  ' onclick='selectAll()' /> <br />
  <input type='button' name='Submit' value='取消选定所有专题' onclick='unSelectAll()' /></td>
 </tr>
</table>
</pub:template>



<!-- 软件属性 -->
<pub:template name="temp_softParam">
<table width='98%' border='0' cellpadding='2' cellspacing='1' bgcolor='#FFFFFF'>
 <tr class='tdbg'>
  <td width='120' align='right' class='tdbg5'>作者/开发商：</td>
  <td>
   <div style="clear: both;">
    <input name='author' type='text' id='author' value='#{soft.author@html }' autocomplete='off' size='50' maxlength='100' /> 
    #{foreach author in author_list }
     <font  color='blue'>【<font color='green'  onclick="document.myform.author.value='#{author }'" style="cursor:hand;">#{author }</font>】
    #{/foreach }
    【<font color='green' onclick="window.open('admin_item_author.jsp?channelId=#{channel.id }', 'AuthorList', 'width=600,height=450,resizable=0,scrollbars=yes');" style="cursor:hand;">更多</font>】 </font>
   </div>
   <div id="sauthor" style='display:none'></div>
  </td>
 </tr>
 <tr class='tdbg'>
  <td width='120' align='right' class='tdbg5'>#{channel.itemName }来源：</td>
  <td>
   <div style="clear: both;">
    <input name='source' type='text' id='souce' value='#{soft.source@html }' 
     autocomplete='off' size='50' maxlength='100' /> 
    #{foreach source in source_list }
     <font  color='blue'> 【<font color='green'  onclick="document.myform.source.value='#{source }'" style="cursor:hand;">#{source }</font>】
    #{/foreach }
    【<font color='green' onclick="window.open('admin_item_source.jsp?channelId=#{channel.id }', 'CopyFromList', 'width=600,height=450,resizable=0,scrollbars=yes');"
    style="cursor:hand;">更多</font>】 </font></div>
   <div id="scopyfrom" style='display:none'></div>
  </td>
 </tr>
 <tr class='tdbg'>
  <td width='120' align='right' class='tdbg5'>软件类别：</td>
  <td>
   <select name='softType' id='softType'>
    #{foreach type in soft_param_list.type }
    <option value='#{type }' #{iif(soft.type == type, "selected", "") }>#{type }</option>
    #{/foreach }
   </select>
   <!-- <script type="text/javascript">JSoftParam.Read2Select("type", byId("softType"), "#{soft.type }");</script> -->
  </td>
 </tr>
 <tr class='tdbg'>
  <td width='120' align='right' class='tdbg5'>软件语言：</td>
  <td>
   <select name='softLanguage' id='softLanguage'>
    #{foreach language in soft_param_list.language }
    <option value='#{language }' #{iif(soft.language == language, "selected", "") }>#{language }</option>
    #{/foreach }
   </select>
  </td>
 </tr>
 <tr class='tdbg'>
  <td width='120' align='right' class='tdbg5'>授权形式：</td>
  <td>
   <select name='softCopyrightType' id='SoftCopyrightType'>
    #{foreach copyrightType in soft_param_list.copyright }
    <option value='#{copyrightType }' #{iif(soft.copyrightType == copyrightType, "selected", "") }>#{copyrightType }</option>
    #{/foreach }
   </select>
  </td>
 </tr>
 <tr class='tdbg'>
  <td width='120' align='right' class='tdbg5'>软件平台：</td>
  <td>
   <input name='softOS' type='text' value='#{soft.OS }' size='80' maxlength='200'>
   <br>
   <script language='JavaScript'>
    function ToSystem(addTitle){
     var obj = document.getElementById("softOS");
        var str = obj.value;
        if (str == "") {
            obj.value = addTitle;
        }else{
            if (str.substr(str.length-1,1)=="/"){
                obj.value += addTitle;
            }else{
                obj.value += ("/"+addTitle);
            }
        }
        obj.focus();
    }
   </script>
   <font color='#808080'>平台选择：
   #{foreach os in soft_param_list.os}
     <a href="javascript:ToSystem('#{os }')">#{os }</a>/#{/foreach }
  </td>
 </tr>
 <tr class='tdbg'>
  <td width='120' align='right' class='tdbg5'>软件演示地址：</td>
  <td><input name='demoUrl' type='text' id='demoUrl' value='#{soft.demoUrl }' size='80' maxlength='200'></td>
 </tr>
 <tr class='tdbg'>
  <td width='120' align='right' class='tdbg5'>软件注册地址：</td>
  <td><input name='registUrl' type='text' id='registUrl' value='#{soft.registUrl }' size='80' maxlength='200'></td>
 </tr>
 <tr class='tdbg'>
  <td width='120' align='right' class='tdbg5'>解压密码：</td>
  <td><input name='decompPwd' type='text' id='decompPwd' value='#{soft.decompPwd }' size='30' maxlength='100'></td>
 </tr>
</table>
</pub:template>



<!-- 属性设置 -->
<pub:template name="temp_propertySet"> 
<table width='98%' border='0' cellpadding='2' cellspacing='1' bgcolor='#FFFFFF'>
 <tr class='tdbg'>
  <td width='120' align='right' class='tdbg5'>#{channel.itemName }属性：</td>
  <td>
   <input name='top' type='checkbox' id='Top' value='true' #{iif(soft.top, "checked", "") } />
     固顶#{channel.itemName } &nbsp;&nbsp; 
   <input name='commend' type='checkbox' id='Commend' value='true' #{iif (soft.commend, "checked", "") } /> 
     推荐#{channel.itemName } &nbsp;&nbsp; 
   <input name='elite' type='checkbox' id='Elite' value='true' #{iif (soft.elite, "checked", "") } /> 
     精华#{channel.itemName } &nbsp;&nbsp; 
   <input name='hot' type='checkbox' id='Hot' value='true' onclick="javascript:document.myform.hits.value='1000'" 
    #{iif (soft.hot, "checked", "") }/> 热门#{channel.itemName }&nbsp;&nbsp;
   #{channel.itemName }评分等级： 
   <select name='stars' id='stars'>
    <option value='5' #{iif (soft.stars == 5, "selected", "") }>★★★★★</option>
    <option value='4' #{iif (soft.stars == 4, "selected", "") }>★★★★</option>
    <option value='3' #{iif (soft.stars == 3, "selected", "") }>★★★</option>
    <option value='2' #{iif (soft.stars == 2, "selected", "") }>★★</option>
    <option value='1' #{iif (soft.stars == 1, "selected", "") }>★</option>
    <option value='0' #{iif (soft.stars == 0, "selected", "") }>无</option>
   </select>
   </td>
 </tr>
 <tr class='tdbg'>
  <td width='120' align='right' class='tdbg5'>下载次数：</td>
  <td>
   本日：<input name='dayHits' type='text' id='dayHits' value='#{soft.dayHits }' size='10' maxlength='10'>
   &nbsp;&nbsp;&nbsp;&nbsp; 本周：<input name='weekHits' type='text' id='WeekHits' value='#{soft.weekHits }' size='10' maxlength='10'>
   &nbsp;&nbsp;&nbsp;&nbsp; 本月：<input name='monthHits' type='text' id='MonthHits' value='#{soft.monthHits }' size='10' maxlength='10'>
   &nbsp;&nbsp;&nbsp;&nbsp; 总计：<input name='hits' type='text' id='hits' value='#{soft.hits }' size='10' maxlength='10'>
  </td>
 </tr>
 <tr class='tdbg'>
  <td width='120' align='right' class='tdbg5'>录入时间：</td>
  <td>
   <input name='createTime' type='text' id='createTime' value='#{soft.createTime@format }' maxlength='50' />
     时间格式为“年-月-日 时:分:秒”，如： <font color='#0000FF'> 2003-05-12 12:32:47 </font>
  </td>
 </tr> 
 <tr class='tdbg'>
  <td width='120' align='right' class='tdbg5'>配色风格：</td>
  <td><select name='skinId' id='skinId'>
    <option value='#{channel.skinId}'  #{iif(soft.skinId == 0, "selected", "") }>使用本频道的默认皮肤</option>
    #{foreach skin in skin_list }
    <option value='#{skin.id }' #{iif(soft.skinId == skin.id, "selected", "") }>#{skin.name }#{if(skin.isDefault)}（默认）#{/if }</option>
    #{/foreach }
   </select> &nbsp; 相关模板中包含CSS、颜色、图片等信息</td>
 </tr>
 <tr class='tdbg'>
  <td width='120' align='right' class='tdbg5'>版面设计模板：</td>
  <td><select name='templateId'>
   <option value='0' #{iif (soft.templateId == 0, "selected", "") }>系统默认内容页模板</option>
   #{foreach templ in templ_list }
   <option value='#{templ.id }' #{iif(soft.templateId == templ.id, "selected", "") }>#{templ.name }#{if(templ.themeDefault)}（默认）#{/if }</option>
   #{/foreach }
  </select> &nbsp; 相关模板中包含了版面设计的版式等信息</td>
 </tr>  
</table>
</pub:template>

</pub:declare>

<pub:process_template name="main" />

</body>
</html>
