<%@ page language="java" contentType="text/html; charset=gb2312" pageEncoding="UTF-8"%>
<%@page import="com.chinaedustar.publish.*"%>
<%@page import="com.chinaedustar.publish.admin.SuperLabel"%>
<%@taglib prefix="pub" uri="/WEB-INF/publish.tld"%>
<%
  // 初始化页面数据。
  SuperLabel page_init = new SuperLabel(pageContext);
  page_init.initSuperItemPage();

%>

<%@ include file="../../admin/element_column.jsp"%>
<%@ include file="element_label.jsp"%>

<!-- 主执行模板定义 -->
<pub:template name="main">
 #{call label_main_temp("ShowArticleList", "文章自定义列表标签")}
 
<script language="javascript">
function insertLabel(strLabel) {
 $("Content").focus();
 var str = document.selection.createRange();
 str.text = strLabel;
}
</script>
</pub:template>

<pub:template name="label_property_init">
  setLabelProp("channelId");
  setLabelProp("columnId");
  setLabelProp("specialId");
  setLabelProp("includeChild");
  setLabelProp("labelDesc");
  setLabelProp("itemNum");
  setLabelProp("isHot");
  setLabelProp("isElite");
  setLabelProp("author");
  setLabelProp("dateScope");
  setLabelProp("orderType");
  setLabelProp("titleCharNum");
  setLabelProp("contentNum");
  setLabelProp("usePage");
  
  label.template = $("Content").value;
</pub:template>

<pub:template name="label_param_setting">
<table width='100%' bgcolor='white' cellspacing='1' cellpadding='1'>
  <tr class='tdbg'>
   <td height='25' align='right' class='tdbg5'><strong>所属频道：</strong></td>
   <td height='25'>
  <form action='super_CustomArticleList.jsp' method='get' name='myform' id='myform' style='margin: 0px;'>
   <select id="channelId" name='channelId' __def_value='0' onchange='document.myform.submit();'>
    <option value='0' #{iif (channelId == 0, "selected", "") }>当前频道(缺省)</option>
   #{foreach channel in channel_list }
    <option value='#{channel.id }' #{iif (channelId == channel.id, "selected", "") }>#{channel.name }</option>
   #{/foreach }
    <option value='-1' #{iif (channelId == -1, "selected", "") }>所有同类频道</option>
    <option value='-2' #{iif (channelId == -2, "selected", "") }>任何频道</option>
   </select>
  </form>
   </td>
  </tr>
  #{if (channelId > 0) }
  <tr class='tdbg'>
   <td height='25' align='right' class='tdbg5'><strong>所属栏目：</strong></td>
   <td height='25'>
   <select  name='ColumnId' id="columnId" size='1' __def_value='0'>
    <option value='0' style=''>任何栏目</option>
    <option value='-1' style=''>当前栏目</option>
    #{call dropDownColumns(channelId, dropdown_columns) }
   </select> 
   <input type='checkbox' id="includeChild" name='includeChild' value='true' 
     __def_value='true' checked='checked' >包含子栏目
  </td>
  </tr>
  <tr class='tdbg'>
   <td height='25' align='right' class='tdbg5'><strong>所属专题：</strong></td>
   <td height='25'>
   <select name='SpecialId' id='specialId' __def_value='0'>
    <option value='0'>当前专题</option>
    <option value='-1'>当前频道的所有专题</option>
    <option value='-2'>不对专题考虑</option>
   #{foreach special in special_list }
    <option value='#{special.id }'>#{special.name }(#{iif (special.channelId == 0, "全站", "本频道") })</option>
   #{/foreach }
   </select>
   </td>
  </tr>
  #{else }
  <tr style="display: none;"><td></td><td>
  <input type="hidden" value="0" name="ColumnId" id="columnId" __def_value='0' />
  <input type="checkbox" name='includeChild' value='true' __def_value='true' id="includeChild" checked="checked" />
  <input type="hidden" name="SpecialId" value="0" id="specialId" __def_value='0' />
  </td>
  </tr>
  #{/if }
  <tr class='tdbg'>
   <td height='25' align='right' class='tdbg5'><strong>标签说明：</strong></td>
   <td height='25'><INPUT TYPE='text' NAME='LabelDesc' value='' __def_value=''
    id='labelDesc' size='15' maxlength='20'>&nbsp;&nbsp;<FONT
    style='font-size:12px' color='blue'>请在这里填写标签的使用说明方便以后的查找</FONT></td>
  </tr>
  <tr class='tdbg'>
   <td height='25' align='right' class='tdbg5'><strong>文章数目：</strong></td>
   <td height='25'><input name='ItemNum' id='itemNum' type='text' value='20'
    __def_value='20' size='5' maxlength='3'>&nbsp;&nbsp;&nbsp;&nbsp;<font
    color='#FF0000'>如果为0，将显示所有文章。</font></td>
  </tr>
  <tr class='tdbg'>
   <td height='25' align='right' class='tdbg5'><strong>文章属性：</strong></td>
   <td height='25'>
    <input name='IsHot' type='checkbox' id='isHot' value='true' __false_value='' __def_value='' />
      热门文章&nbsp;&nbsp;&nbsp;&nbsp;
    <input name='IsElite' type='checkbox' id='isElite' value='0' __false_value='' __def_value='' />
      推荐文章&nbsp;&nbsp;&nbsp;&nbsp;<font color='#FF0000'>如果都不选，将显示所有文章。</font>
   </td>
  </tr>
  <tr class='tdbg'>
   <td height='25' align='right' class='tdbg5'><strong>作者姓名：</strong></td>
   <td height='25'><input name='Author' id='author' type='text' value=''
    __def_value='' size='10' maxlength='10'>&nbsp;&nbsp;&nbsp;&nbsp;<font
    color='#FF0000'>如果不为空，则只显示指定录入者的文章，用于个人文集。</font></td>
  </tr>
  <tr class='tdbg'>
   <td height='25' align='right' class='tdbg5'><strong>日期范围：</strong></td>
   <td height='25'>只显示最近 <input name='DateScope' type='text'
    id='dateScope' value='' __def_value='' size='5' maxlength='3'>
   天内更新的文章&nbsp;&nbsp;&nbsp;&nbsp;<font color='#FF0000'>&nbsp;&nbsp;如果为空或0，则显示所有天数的文章。</font></td>
  </tr>
  <tr class='tdbg'>
   <td height='25' align='right' class='tdbg5'><strong>排序方法：</strong></td>
   <td height='25'><select name='OrderType' id='orderType' __def_value=''>
    <option value=''>文章ID（降序）</option>
    <option value='1'>文章ID（升序）</option>
    <option value='2'>更新时间（降序）</option>
    <option value='3'>更新时间（升序）</option>
    <option value='4'>点击次数（降序）</option>
    <option value='5'>点击次数（升序）</option>
   </select></td>
  </tr>
  <tr class='tdbg' style='display:none'>
   <td height='25' align='right' class='tdbg5'><strong>标题最多字符数：</strong></td>
   <td height='25'><input name='TitleCharNum' type='text' id='titleCharNum'
    value='' __def_value='' size='5' maxlength='3'> &nbsp;&nbsp;&nbsp;&nbsp;<font
    color='#FF0000'>如果为0，则显示完整标题。字母算一个字符，汉字算两个字符。</font></td>
  </tr>
  <tr class='tdbg' style='display:none'>
   <td height='25' align='right' class='tdbg5'><strong>文章内容字符数：</strong></td>
   <td height='25'><input name='ContentNum' type='text'
    id='contentNum' value='' __def_value='' size='5' maxlength='3'>
   &nbsp;&nbsp;&nbsp;&nbsp;<font color='#FF0000'>如果大于0，则在标题下方面显示指定字数的文章内容</font></td>
  </tr>
  <tr class='tdbg'>
   <td height='25' align='right' class='tdbg5'><strong>是否显示分页栏：</strong></td>
   <td height='25'>
   <input type="radio" value="true" __def_value='false' name="usePage" id="usePage" />显示
   <input type="radio" value="false" __def_value='false' checked="checked" name="usePage" />不显示
   </td>
 </tr>
 <tr class=tdbg>
  <td width="130" class='tdbg5' align=right height=25><STRONG>循环标签支持标签：</STRONG></td>
  <td>
  <table border='0' cellpadding='0' cellspacing='0' width='100%' height='100%' align='center'>
   <tr>
    <td valign='top'><a href="javascript:insertLabel('\#{ArticleTitle}')"
     title="文章正标题, 也可以写为\#{article.title}">\#{ArticleTitle}</a></td>
    <td valign='top'><a href="javascript:insertLabel('\#{ArticleUrl}')" 
     title="文章的链接地址, 也可以写为 \#{article.pageUrl}">\#{ArticleUrl}</a></td>
    <td valign='top'><a href="javascript:insertLabel('\#{ArticleID}')" 
     title="文章的ID, 也可以写为 \#{article.id}">\#{ArticleID}</a></td>
    <td valign='top'><a href="javascript:insertLabel('\#{ArticleAuthor}')"
     title="文章作者, 也可以写为 \#{article.author}">\#{ArticleAuthor}</a></td>
    <td valign='top'><a href="javascript:insertLabel('\#{ArticleContent}')"
     title="文章正文内容">\#{ArticleContent}</a></td>
   </tr>
   <tr>
    <td valign='top'><a href="javascript:insertLabel('\#{article.stars}')"
     title="文章评分等级">\#{article.stars}</a></td>
    <td valign='top'><a href="javascript:insertLabel('\#{article.top}')"
     title="固顶">\#{article.top}</a></td>
    <td valign='top'><a href="javascript:insertLabel('\#{article.commend}')"
     title="推荐">\#{article.commend}</a></td>
    <td valign='top'><a href="javascript:insertLabel('\#{article.lastModified}')" 
     title="文章更新时间">\#{article.lastModified}</a></td>
    <td valign='top'><a href="javascript:insertLabel('\#{article.hits}')"
     title="点击次数">\#{article.hits}</a></td>
   </tr>
   <tr>
    <td valign='top'><a href="javascript:insertLabel('\#{ArticleProperty}')" 
     title="文章属性">\#{ArticleProperty}</a></td>
    <td valign='top'><a href="javascript:insertLabel('\#{ArticleSubheading}')" 
     title="自定义列表副标题">\#{ArticleSubheading}</a></td>
    <td valign='top'><a href="javascript:insertLabel('\#{article.description}')"
     title="文章简介">\#{article.description}</a></td>
    <td valign='top'><a href="javascript:insertLabel('\#{article.editor}')"
     title="责任编辑">\#{article.editor}</a></td>
    <td valign='top'><a href="javascript:insertLabel('\#{article.source}')" 
     title="文章来源">\#{article.source}</a></td>
   </tr>
   <tr>
    <td valign='top'><a href="javascript:insertLabel('\#{article.inputer}')"
     title="文章录入者">\#{article.inputer}</a></td>
    <td valign='top'><a href="javascript:insertLabel('\#{ArticleInfo}')"
     title="显示文章信息，整体显示文章作者、文章来源、点击数、更新时间信息。">\#{ArticleInfo}</a></td>
   </tr>
  </table>
  </td>
 </tr>
 <tr class='tdbg'>
  <td width="130" class='tdbg5' align=right height=25><STRONG>请输入循环标签Html代码：</STRONG>
  <br>
  <select name='TemplateID' onChange='document.myform.submit();'>
   <option value="0" selected>还没有列表模板！</option>
  </select></td>
  <td><textarea name='Content' id="Content" style='width:600px;height:240px'>\#{Repeater}
  &lt;!-- 在此循环标签里面填写文章标签及 html 代码 --&gt;
  &lt;li&gt;&lt;a href='\#{ArticleUrl}'&gt;\#{ArticleTitle}&lt;/a&gt;&lt;/li&gt;
  
\#{/Repeater}</textarea>
  </td>
 </tr>
</table>
</pub:template>

<pub:process_template name="main" />
