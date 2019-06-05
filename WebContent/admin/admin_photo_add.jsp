<%@page language="java" contentType="text/html; charset=gb2312" 
   pageEncoding="UTF-8" errorPage="admin_error.jsp"
%><%@taglib prefix="pub" uri="/WEB-INF/publish.tld"
%><%@page import="com.chinaedustar.publish.admin.PhotoManage"
%><%
  // 初始化页面数据。
  PhotoManage admin_data = new PhotoManage(pageContext);
  admin_data.initEditPage();
  
%><!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
 <meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
 <title>图片管理</title>
 <link href='admin_style.css' rel='stylesheet' type='text/css' />
 <script type="text/javascript" src="main.js"></script>
 <script language="javascript" src='admin_photo_add.js'></script>
</head>
<body leftmargin='2' topmargin='0' marginwidth='0' marginheight='0'>

<pub:declare>

<!-- 定义标签页集合。 -->
<pub:tabs var="contentTabs" scope="page" purpose="">
 <pub:tab name="baseInfo" text="基本信息" template="temp_baseInfo" default="true" />
 <pub:tab name="specialSet" text="所属专题" template="temp_specialSet" default="false" />
 <pub:tab name="propertySet" text="属性设置" template="temp_propertySet" default="false" />
</pub:tabs>

<%@ include file="element_photo.jsp" %>
<%@ include file="element_column.jsp" %>
<%@ include file="tabs_tmpl2.jsp" %>

<pub:template name="main">
 #{call photo_manage_navigator("添加" + channel.itemName) }<br />
 #{call your_position}
<form method='post' name='myform' onSubmit='return checkForm();' action='admin_photo_action.jsp' target='_self'>
<div style='padding:1px;'>
 #{call tab_js }
 #{call tab_header(contentTabs) }
 #{call tab_content(contentTabs) }
</div>

<p align='center'>
 <input name="__itemName" type='hidden' id="__itemName" value="#{channel.itemName}" />
 <input name='command' type='hidden' value='save' />
 <input name='channelId' type='hidden' id='channelId' value='#{channel.id }' />
 <input name='photoId' type="hidden" value="#{photo.id }" />
 <input name='add' type='submit' id='Add' value=' #{iif (photo.id > 0, "修 改", "添 加") } '
  onclick="document.myform.action='admin_photo_action.jsp';document.myform.target='_self';"
 style='cursor:hand;' /> &nbsp; 
 <input name='Preview' type='submit' id='preview' value=' 预 览 '
  onclick="document.myform.action='admin_photo_preview.jsp';document.myform.target='_blank';" style='cursor:hand;' />
 <input name='Cancel' type='button' id='Cancel' value=' 取 消 ' onClick="history.go(-1);" style='cursor:hand;' />
</p>
<br />
</form>
 <% if (true) { %>
 #{call debug_info}
 <% } %>
</pub:template>



<pub:template name="debug_info">
<br/><br/><hr/><h2>DEBUG INFO</h2>
<li>dropdown_columns.schema = #{dropdown_columns.schema }
<li>dropdown_columns = #{(dropdown_columns)}
<li>channel = #{(channel) }
<li>photo = #{(photo) }
<li>keyword_list = #{(keyword_list) }
<li>author_list = #{(author_list)  }
<li>source_list = #{(source_list) }
<li>channel_specials = #{(channel_specials) }
<li>channel_specials.schema = #{channel_specials.schema }
<li>templ_list = #{(templ_list) }
<li>templ_list.schema = #{templ_list.schema }
<li>skin_list = #{(skin_list) }
<li>photo.pictureList = #{photo.pictureList }
<li>photo.specialIds = #{photo.specialIds }, @contains(6) = #{photo.specialIds@contains(6) }, @contains(4) = #{photo.specialIds@contains(4) }
<br/><br/>
</pub:template>


<pub:template name="your_position">
<table width='100%'>
 <tr>
  <td align='left'>
  您现在的位置： <a href='admin_photo_list.jsp?channelId=#{channel.id }'>#{channel.name }管理</a>
   &gt;&gt; 添加#{channel.itemName }
  </td>
 </tr>
</table>
</pub:template>


<!-- 基本信息 -->
<pub:template name="temp_baseInfo">
<table width='100%' border='0'>
 <tr class='tdbg'>
  <td width='120' align='right' class='tdbg5'>所属栏目：</td>
  <td>
  <select name="columnId">     
  #{call dropDownColumns(photo.columnId, dropdown_columns, false) }
  <option value="0" parentPath="/">频道根栏目</option>
  </select>
  &nbsp; &nbsp; <font color='red'><strong>注意：</strong></font>
  <font color='blue'>不能指定为外部栏目，栏目颜色为灰时不能添加#{channel.itemName }</font>
  </td>
 </tr>
 <tr class='tdbg'>
  <td width='120' align='right' class='tdbg5'>
   #{channel.itemName }名称：
  </td>
  <td>
   <input name='photoTitle' id='photoTitle' type='text' value='#{photo.title@html}' size='50' maxlength='255'/>
   <font color='#FF0000'>*</font>
   <input type='button' name='checksame' value='检查是否存在相同的#{channel.itemName }名'
    onclick="showModalDialog('admin_check_same_title.jsp?channelId=#{channel.id }&title=' + URLEncode(byId('photoTitle').value),'checksame','dialogWidth:350px; dialogHeight:250px; help: no; scroll: no; status: no');">
   <br />
   <input name='commentFlag' type='checkbox' id='commentFlag' value='1'  #{iif(photo.commentFlag == 1, "checked", "") }/> 显示#{channel.itemName}列表时在标题旁显示评论链接
  </td>
 </tr>
 <tr class='tdbg'>
  <td width='120' align='right' class='tdbg5'>关键字：</td>
  <td>
  <div style="clear: both;">
   <input name='keywords' type='text' style="clear:both" id='keywords' value='#{photo.keywords@html }' 
    autocomplete='off' size='50' maxlength='255' />
   <font color='#FF0000'> * </font> 
  #{foreach keyword in keyword_list}
   <font color='blue'>【<font  color='green' onclick="byId('keywords').value+=(byId('keywords').value==''?'':'|')+'#{keyword }'" style="cursor:hand;">#{keyword }</font>】
  #{/foreach }
  【<font color='green' onclick="window.open('admin_keyword_choose.jsp?channelId=#{channel.id }', 'KeywordList', 'width=600,height=450,resizable=0,scrollbars=yes');"
   style="cursor:hand;">更多</font>】 </font></div>
  <div id="skey" style='display:none'></div>
  <font color='#0000FF'> 用来查找相关#{channel.itemName }，可输入多个关键字，中间用 <font
   color='#FF0000'> “|” </font> 隔开。不能出现&quot;'&amp;?;:()等字符。 </font></td>
 </tr>
 <tr class='tdbg'>
  <td width='120' align='right' class='tdbg5'>#{channel.itemName }作者：</td>
  <td>
  <div style="clear: both;"><input name='author' type='text'
   id='author' value='#{photo.author@html }' autocomplete='off' size='50' maxlength='100' />
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
   <input name='source' type='text' id='source' value='#{photo.source@html }' autocomplete='off' size='50' maxlength='100' /> 
  #{foreach source in source_list }
   <font  color='blue'> 【<font color='green'  onclick="document.myform.source.value='#{source }'" style="cursor:hand;">#{source }</font>】
  #{/foreach }
  【<font color='green' onclick="window.open('admin_item_source.jsp?channelId=#{channel.id }', 'copyFromList', 'width=600,height=450,resizable=0,scrollbars=yes');"
   style="cursor:hand;">更多</font>】 </font></div>
  <div id="scopyfrom" style='display:none'></div>
  </td>
 </tr>
 <tr class='tdbg'>
  <td width='120' align='right' class='tdbg5'>
   #{channel.itemName }简介：
  </td>
  <td><textarea name='description' cols='80' rows='10' id='description' style='display:none'>#{photo.description@html }</textarea>
   <iframe ID='editor' src='../editor/editor_new.jsp?channelId=1&showType=3&tContentID=description' frameborder='1' scrolling='no' width='700' height='200' ></iframe>
  </td>
 </tr>
 <tr class='tdbg'>
  <td width='120' align='right' class='tdbg5'> 缩略图： </td>
  <td><input name='thumbPic' type='text' id='thumbPic' value="#{photo.thumbPic@html }"
   size='70' maxlength='200' /> 
   <input type='button' name='selfromuploaded' value='从已上传图片中选择' onclick="SelectFile('thumbPic', #{channel.id })">
 </tr>
 <tr class='tdbg'>
  <td width='120' align='right' class='tdbg5'> #{channel.itemName }地址： </td>
  <td>
   <table>
    <tr>
     <td>
      <input type='hidden' name='pictureUrls' id='pictureUrls' value='#{photo.pictureUrls }'>
      <select name='pictureUrl' id='pictureUrl' style='width:400;height:100' size='8' ondblclick='return ModifyUrl("#{channel.itemName}");'>
       #{foreach entry in photo.pictureList}
       <option value="#{entry.name + '|' + entry.url}">#{entry.name + '|' + entry.url}</option>
       #{/foreach }
      </select>
     </td>
     <td>
      <input type='button' name='photoselect' value='从已上传#{channel.itemName }中选择' onclick='SelectFile("pictureUrl", #{channel.id}, "#{channel.itemName }")'><br>
      <input type='button' name='addurl' value='设为缩略图' onclick='SetThumb("#{channel.itemName }");'><br>
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
   <iframe style='top:2px' ID='uploadFiles' src='../editor/upload.jsp?channelId=#{channel.id }' frameborder=0 scrolling=no width='450' height='25'></iframe>
   <input type='button' value='继续上传' id='btnContinueUpload' style='display:none' onclick='byId("uploadFiles").src="../editor/upload.jsp?channelId=#{channel.id }&rnd=" + Math.random()'/>
  </td>
 </tr>
 <tr class='tdbg'>
  <td width='120' align='right' class='tdbg5'>#{channel.itemName }状态：</td>
  <td>
  <input name='status' type='radio' id='status' value='-1' #{iif (photo.status == -1, "checked ", "") } onclick="disableCreate()"/>
  草稿 &nbsp; &nbsp; 
  <input name='status' type='radio' id='status' value='0' #{iif (photo.status == 0, "checked ", "") } onclick="disableCreate()"/>
  待审核 &nbsp; &nbsp; 
  <input name='status' type='radio' id='status' value='1' #{iif (photo.status == 1, "checked ", "") } onclick="enabelCreate()"/>
  终审通过 &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp; &nbsp; &nbsp; 
  <input name='createImmediate' id='createImmediate' type='checkbox' value='true' #{if photo.status != 1}disabled=''#{/if } />立即生成
  </td>
 </tr>
</table>
</pub:template>



<!-- 所属专题 -->
<pub:template name="temp_specialSet">
<table width='100%' border='0'>
 <tr class='tdbg'>
  <td width='120' align='right' class='tdbg5'>所属专题：</td>
  <td>
   <select name='specialIds' size='2' multiple='true' style='height:300px;width:260px;'>
   #{foreach special in channel_specials }
   <option value='#{special.id }' #{if photo.specialIds@contains(special.id)}selected#{/if} >#{special.name } (#{iif (special.channelId==0, "全站", "本频道") })</option>
   #{/foreach }
  </select> <br />
  <input type='button' name='Submit' value='  选定所有专题  ' onclick='selectAll()' /><br />
  <input type='button' name='Submit' value='取消选定所有专题' onclick='unSelectAll()' />
  <br />按下 Ctrl 键用鼠标可以多选所属专题。
  </td>
 </tr>
</table>
</pub:template>



<!-- 属性设置 -->
<pub:template name="temp_propertySet"> 
<table width='100%' border='0'>
 <tr class='tdbg'>
  <td width='120' align='right' class='tdbg5'>#{channel.itemName }属性：</td>
  <td>
   <input name='top' type='checkbox' id='Top' value='true' #{iif (photo.top, "checked", "") } />
     固顶#{channel.itemName } &nbsp;&nbsp;
   <input name='hot' type='checkbox' id='Hot' value='true' onclick="javascript:document.myform.hits.value='#{channel.hitsOfHot}'" #{iif (photo.hot, "checked", "") }/>
     热门#{channel.itemName } &nbsp;&nbsp;
   <input name='commend' type='checkbox' id='Commend' value='true' #{iif (photo.commend, "checked", "") } /> 
     推荐#{channel.itemName } &nbsp;&nbsp;
   <input name='elite' type='checkbox' id='Elite' value='true' #{iif (photo.elite, "checked", "") } /> 
     精华#{channel.itemName } &nbsp;&nbsp;
   #{channel.itemName }评分等级： <select name='stars' id='stars'>
   <option value='5' #{iif (photo.stars == 5, "selected", "") }>★★★★★</option>
   <option value='4' #{iif (photo.stars == 4, "selected", "") }>★★★★</option>
   <option value='3' #{iif (photo.stars == 3, "selected", "") }>★★★</option>
   <option value='2' #{iif (photo.stars == 2, "selected", "") }>★★</option>
   <option value='1' #{iif (photo.stars == 1, "selected", "") }>★</option>
   <option value='0' #{iif (photo.stars == 0, "selected", "") }>无</option>
  </select></td>
 </tr>
 <tr class='tdbg'>
  <td width='120' align='right' class='tdbg5'>下载次数：</td>
  <td>
   本日：<input name='dayHits' type='text' id='dayHits' value='#{photo.dayHits }' size='10' maxlength='10'>
   &nbsp;&nbsp;&nbsp;&nbsp; 本周：<input name='weekHits' type='text' id='WeekHits' value='#{photo.weekHits }' size='10' maxlength='10'>
   &nbsp;&nbsp;&nbsp;&nbsp; 本月：<input name='monthHits' type='text' id='MonthHits' value='#{photo.monthHits }' size='10' maxlength='10'>
   &nbsp;&nbsp;&nbsp;&nbsp; 总计：<input name='hits' type='text' id='hits' value='#{photo.hits }' size='10' maxlength='10'>
  </td>
 </tr>
 <!-- 
 <tr class='tdbg'>
  <td width='120' align='right' class='tdbg5'>录入时间：</td>
  <td>
    <input name='createTime' type='text' id='createTime' value='#{photo.createTime@format() }' maxlength='50' />
    时间格式为“年-月-日 时:分:秒”，如： <font color='#0000FF'> 2003-05-12 12:32:47 </font>
  </td>
 </tr> 
 -->
 <tr class='tdbg'>
  <td width='120' align='right' class='tdbg5'>配色风格：</td>
  <td><select name='skinId' id='skinId'>
    <option value='#{channel.skinId }'  #{iif(photo.skinId == 0, "selected", "") }>使用本频道的默认皮肤</option>
    #{foreach skin in skin_list }
    <option value='#{skin.id }' #{iif(photo.skinId == skin.id, "selected", "") }>#{skin.name }#{if(skin.isDefault)}（默认）#{/if }</option>
    #{/foreach }
   </select> &nbsp; 相关模板中包含CSS、颜色、图片等信息</td>
 </tr>
 <tr class='tdbg'>
  <td width='120' align='right' class='tdbg5'>版面设计模板：</td>
  <td><select name='templateId'>
   <option value='0' #{iif (photo.templateId == 0, "selected", "") }>系统默认内容页模板</option>
   #{foreach templ in templ_list }
   <option value='#{templ.id }' #{iif(photo.templateId == templ.id, "selected", "") }>#{templ.name }#{if(templ.themeDefault)}（默认）#{/if }</option>
   #{/foreach }
  </select> &nbsp; 相关模板中包含了版面设计的版式等信息</td>
 </tr>
</table>
</pub:template>

</pub:declare>

<pub:process_template name="main" />

</body>
</html>
