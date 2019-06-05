<%@ page language="java" contentType="text/html; charset=gb2312"
 pageEncoding="UTF-8" errorPage="admin_error.jsp" 
%>
<%@page import="com.chinaedustar.publish.admin.ArticleManage" %>
<%@taglib prefix="pub" uri="/WEB-INF/publish.tld" %>

<%
  // 初始化页面数据。
  ArticleManage admin_data = new ArticleManage(pageContext);
  admin_data.initEditPage(); 

%><!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
 <meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
 <title>文章管理</title>
 <link href='admin_style.css' rel='stylesheet' type='text/css' />
 <script type="text/javascript" src="main.js"></script>
 <script type="text/javascript" src='admin_article.js'></script>
</head>
<body leftmargin='2' topmargin='0' marginwidth='0' marginheight='0'>
<pub:declare>

<!-- 定义标签页集合。 -->
<pub:tabs var="item_tabs">
 <pub:tab name="baseInfo" text="基本信息" template="temp_baseInfo" default="true" />
 <pub:tab name="specialSet" text="所属专题" template="temp_specialSet" default="false" />
 <pub:tab name="propertySet" text="属性设置" template="temp_propertySet" default="false" />
</pub:tabs>

<%@ include file="element_article.jsp" %>
<%@ include file="element_column.jsp" %>
<%@ include file="tabs_tmpl2.jsp" %>
<%@ include file="admin_article_add_in.jsp" %>

<pub:template name="main">
 #{call article_manage_navigator("添加" + channel.itemName) } <br />
 #{call your_position}

<form action='admin_article_action.jsp' method='post' name='myform' onsubmit='return checkForm();' target='_self'>
<div>
 #{call tab_js }
 #{call tab_header(item_tabs) }
 #{call tab_content(item_tabs) }
</div>
 #{call form_buttons}
</form>

 #{call check_form_script }
</pub:template>



<pub:template name="debug_info">
  <br/><br/><hr/><h2>DEBUG INFO</h2>
  <li>dropdown_columns.schema = #{dropdown_columns.schema }
  <li>dropdown_columns = #{(dropdown_columns)}
  <li>channel = #{(channel) }
  <li>article = #{(article) }
  <li>keyword_list = #{(keyword_list) }
  <li>author_list = #{(author_list)  }
  <li>source_list = #{(source_list) }
  <li>channel_specials = #{(channel_specials) }
  <li>channel_specials.schema = #{channel_specials.schema }
  <li>templ_list = #{(templ_list) }
  <li>templ_list.schema = #{templ_list.schema }
  <li>skin_list = #{(skin_list) }
  <li>article.specialIds = #{article.specialIds }, @contains(6) = #{article.specialIds@contains(6) }, @contains(4) = #{article.specialIds@contains(4) }
  <li>article.uploadFilesColl = #{article.uploadFilesColl}
  <br/><br/>
</pub:template>


<!-- 文章基本信息 -->
<pub:template name="temp_baseInfo">
<table width='98%' border='0' cellpadding='2' cellspacing='1' bgcolor='#FFFFFF'>
 <tr class='tdbg'>
  <td width='120' align='right' class='tdbg5'>所属栏目：</td>
  <td>#{call select_column_list}</td>
 </tr>
 <tr class='tdbg'>
  <td width='120' align='right' class='tdbg5'>#{channel.itemName}标题：</td>
  <td>#{call input_title_td}</td>
 </tr>
 <tr class='tdbg'>
  <td width='120' align='right' class='tdbg5'>关键字：</td>
  <td>#{call input_keyword_td}</td>
 </tr>
 <tr class='tdbg'>
  <td width='120' align='right' class='tdbg5'>#{channel.itemName}作者：</td>
  <td>#{call input_author_td}</td>
 </tr>
 <tr class='tdbg'>
  <td width='120' align='right' class='tdbg5'>#{channel.itemName}来源：</td>
  <td>#{call input_source_td}</td>
 </tr>
<tr class='tdbg'>
 <td width='120' align='right' class='tdbg5'><font color='red'> 转向链接： </font></td>
 <td>
  <input name='LinkUrl' type='text' id='LinkUrl' value='http://' size='50' maxlength='255' disabled="true" />
  <input name='UseLinkUrl' type='checkbox' id='UseLinkUrl' value='Yes'
    onclick='rUseLinkUrl();' /> <font color='red'> 使用转向链接</font></td>
</tr>
<tr class='tdbg'>
 <td width='120' align='right' class='tdbg5'>#{channel.itemName}简介：</td>
 <td>
 <textarea name='description' cols='80' rows='4' type="_moz">#{article.description@html }</textarea>
 </td>
</tr>
<tr class='tdbg' id='ArticleContent' style="display:''">
 <td width='120' align='right' valign="middle" class='tdbg5'>
 <div>#{channel.itemName}内容：</div>
 <br />
 <font color='red'> 换行请按Shift+Enter <br />
 <br />
 另起一段请按Enter </font>
 <br />
 <img id='frmPreview' width="120" height="150" src="#{iif(article.defaultPicUrlAbs != "", 
   article.defaultPicUrlAbs, "../images/nopic.gif") }" border="1" />
 
 <br /><br />
  <font color='red'>注意</font>：
  您在编辑新闻内容时看到的显示是使用 DefaultSkin.css 样式，如果您在内容模板中选择了别的样式，则可能这里所见并非最终显示所得。
   
 </td>
 <td>
 <textarea name='content' style="display:none; width:100%; height:380;">#{article.content@html}</textarea>
 <iframe ID='editor' src='../editor/editor_new.jsp?channelId=#{channel.id}&showType=0&tContentID=content' 
  frameborder='1' scrolling='no' width='600' height='600' ></iframe>
 </td>
 </tr>
 <tr class='tdbg'>
  <td width='120' align='right' class='tdbg5'><font color='red'> 首页图片： </font></td>
 <td>
  <input name='defaultPicUrl' type='text' id='defaultPicUrl' value="#{if article.defaultPicUrlAbs != ""}#{article.defaultPicUrlAbs}#{/if}"
    size='70' maxlength='200' /> 用于在首页的图片#{channel.itemName}处显示 <br />
  直接从上传图片中选择： <select name='DefaultPicList' id='DefaultPicList'
  onchange="defaultPicUrl.value=this.value;document.all.frmPreview.src=((this.value == '') ? '../images/nopic.gif' : this.value);">
  <option selected='selected'>不指定首页图片</option>
  #{foreach upfile in article.uploadFilesColl }
   <option value="#{upfile.filePath@uri}">#{upfile.name@html}(#{upfile.oldName@html})</option>
  #{/foreach }
 </select> 
 <input name='uploadFiles' type='hidden' id='uploadFiles' value="#{article.uploadFiles@html }" /></td>
</tr>
 <tr class='tdbg'>
  <td width='120' align='right' class='tdbg5'>#{channel.itemName}状态：</td>
 <td>
 <input name='status' type='radio' id='status' value='-1' #{iif (article.status == -1, "checked ", "") } onclick="disableCreate()"/>
 草稿 &nbsp; &nbsp; 
 <input name='status' Type='Radio' id='status' value='0' #{iif (article.status == 0, "checked ", "") } onclick="disableCreate()"/>
 待审核 &nbsp; &nbsp; 
 <input name='status' Type='Radio' Id='status' value='1' #{iif (article.status == 1, "checked ", "") } onclick="enabelCreate()"/>
 终审通过 &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp; &nbsp; &nbsp; 
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
  <td>
   <select name='specialIds' size='2' multiple='true' style='height:300px;width:260px;'>
 #{foreach special in channel_specials }
  <option value='#{special.id}' #{iif (article.specialIds@contains(special.id), "selected", "")}>#{special.name}(#{iif (special.channelId==0, "全站", "本频道") })</option>
 #{/foreach }
 </select> <br />
 <input type='button' name='Submit' value='  选定所有专题  ' onclick='selectAll()' /> <br />
 <input type='button' name='Submit' value='取消选定所有专题' onclick='unSelectAll()' />
  </td>
 </tr>
</table>
</pub:template>



<!-- 属性设置 -->
<pub:template name="temp_propertySet"> 
<table width='98%' border='0' cellpadding='2' cellspacing='1' bgcolor='#FFFFFF'>
 <tr class='tdbg'>
  <td width='120' align='right' class='tdbg5'>#{channel.itemName}属性：</td>
  <td>
   <input name='top' type='checkbox' id='Top' value='yes' #{iif (article.top, "checked", "") } 
     />固顶#{channel.itemName}&nbsp;&nbsp;
   <input name='commend' type='checkbox' id='Commend' value='yes' #{iif (article.commend, "checked", "") } 
     />推荐#{channel.itemName}&nbsp;&nbsp;
   <input name='elite' type='checkbox' id='Elite' value='yes' #{iif (article.elite, "checked", "") } 
     />精华#{channel.itemName}&nbsp;&nbsp;
   <input name='hot' type='checkbox' id='Hot' value='yes' onclick="javascript:document.myform.hits.value='#{channel.hitsOfHot}'" 
     #{iif (article.elite, "checked", "") } />热门#{channel.itemName}&nbsp;&nbsp;
  #{channel.itemName}评分等级： <select name='stars' id='stars'>
   <option value='5' #{iif (article.stars == 5, "selected", "") }>★★★★★</option>
   <option value='4' #{iif (article.stars == 4, "selected", "") }>★★★★</option>
   <option value='3' #{iif (article.stars == 3, "selected", "") }>★★★</option>
   <option value='2' #{iif (article.stars == 2, "selected", "") }>★★</option>
   <option value='1' #{iif (article.stars == 1, "selected", "") }>★</option>
   <option value='0' #{iif (article.stars == 0, "selected", "") }>无</option>
  </select></td>
 </tr>
 <tr class='tdbg'>
  <td width='120' align='right' class='tdbg5'>点击数初始值：</td>
  <td><input name='hits' type='text' id='hits' value='#{article.hits }'
   size='10' maxlength='10' style='text-align:center' /> &nbsp;
  &nbsp; <font color='blue'>这功能是提供给管理员作弊用的。不过尽量不要用呀！^_^ </font></td>
 </tr>
 <tr class='tdbg'>
  <td width='120' align='right' class='tdbg5'>录入时间：</td>
  <td>
   <input name='createTime' type='text' id='createTime' value='#{article.createTime@format }' maxlength='50' />
    时间格式为“年-月-日 时:分:秒”，如： <font color='blue'> 2003-05-12 12:32:47 </font>
  </td>
 </tr> 
 <tr class='tdbg'>
  <td width='120' align='right' class='tdbg5'>配色风格：</td>
  <td>
  <select name='skinId' id='skinId'>
   <option value='#{channel.skinId}' #{iif(article.skinId == 0, "selected", "") }>使用本频道的默认皮肤</option>
  #{foreach skin in skin_list }
   <option value='#{skin.id }' #{iif(article.skinId == skin.id, "selected", "") }>#{skin.name}#{if(skin.isDefault)}(默认)#{/if}</option>
  #{/foreach }
  </select> &nbsp; 相关模板中包含CSS、颜色、图片等信息
  </td>
 </tr>
 <tr class='tdbg'>
  <td width='120' align='right' class='tdbg5'>版面设计模板：</td>
  <td>
   <select name='templateId'>
    <option value='0' #{iif (article.templateId == 0, "selected", "") }>系统默认内容页模板</option>
   #{foreach templ in templ_list }
    <option value='#{templ.id }' #{iif(article.templateId == templ.id, "selected", "") }>#{templ.name }#{if(templ.isDefault)}(默认)#{/if }</option>
   #{/foreach }
   </select> &nbsp; 相关模板中包含了版面设计的版式等信息
  </td>
 </tr>
</table>
</pub:template>


</pub:declare>

<pub:process_template name="main" />

</body>
</html>
