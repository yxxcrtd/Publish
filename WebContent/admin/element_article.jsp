<%@ page language="java" contentType="text/html; charset=gb2312" pageEncoding="UTF-8"%>
<%@taglib prefix="pub" uri="/WEB-INF/publish.tld" %>

<%@ include file="element_item.jsp" %>

<%-- 产生通用的文章列表 --%>
<pub:template name="show_article_list">
#{param oper_header}
<table class="border" border="0" cellspacing="1" width="100%" cellpadding="0">
 <tr class="title" height="22">
  <td height="22" width="30" align="center"><strong>选中</strong></td>
  <td width="25" align="center"><strong>ID</strong></td>
  <td align="center"><strong>#{channel.itemName}标题</strong></td>
  <td width="60" align="center"><strong>录入者</strong></td>
  <td width="40" align="center"><strong>点击数</strong></td>
  <td width="80" align="center"><strong>#{channel.itemName}属性</strong></td>
  <td width="60" align="center"><strong>审核状态</strong></td>
  <td width="40" align="center"><strong>已生成</strong></td>
  <td width="150" align="center"><strong>#{iif(oper_header==null, "管理操作", oper_header)}</strong></td>
 </tr>
#{if article_list@size == 0 }
 <tr class='tdbg' height='40'>
  <td colspan='9' align='center' valign='center'>没有符合条件的#{channel.itemName}</td>
 </tr>
#{/if }
#{foreach article in article_list }
 <tr class="tdbg" onmouseout="this.className='tdbg'" onmouseover="this.className='tdbgmouseover'">
  <td width="30" align="center">
   <input name="itemId" type="checkbox" value="#{article.id }" />
  </td>
  <td width="25" align="center">#{article.id }</td>
  <td>
 #{if (article.columnId != request.columnId) }
  <a href="?channelId=#{channel.id}&amp;columnId=#{article.columnId}">[#{article.columnName }]</a> 
 #{/if }
  <font color="blue">
 #{if (article.includePic == 1) }[图文]
 #{elseif (article.includePic == 2) }[组图]
 #{elseif (article.includePic == 3) }[推荐]
 #{elseif (article.includePic == 4) }[注意]#{/if }
  </font> 
  <a href="admin_article_view.jsp?channelId=#{channel.id}&amp;articleId=#{article.id}" title="标    题：#{article.title@html}
&#13;作    者：#{article.author@html}
&#13;来    源：#{article.source@html}
&#13;更新时间：#{article.lastModified}
&#13;点 击 数：#{article.hits}
&#13;关 键 字：#{article.keywords@html}
&#13;推荐等级：#{'★'@repeat(article.stars) }">#{article.title@html}</a></td>
 <td width="60" align="center">
 <a href="?channelId=#{channel.id }&inputer=#{article.inputer@url}"
  title="点击将查看此用户录入的所有#{channel.itemName}"> #{article.inputer@html} </a>
 </td>
 <td width="40" align="center">#{article.hits }</td>
 <td width="80" align="center">
  #{if article.top }<font color="blue">顶</font> #{/if}
  #{if article.commend }<font color="green">荐</font> #{/if}
  #{if article.elite }<font color='green'>精</font> #{/if}
  #{if article.hot}<font color='red'>热</font> #{/if}
  #{if (article.includePic == 1 || article.includePic == 2) }<font color="blue">图</font> #{/if}
 </td>
 <td width="60" align="center">
  #{if (article.status == 1) }<font color="black">终审通过</font> 
  #{elseif (article.status == 0) }<font color="red">未审批</font>
  #{elseif (article.status == -1) }<font color="black">草稿</font>
  #{elseif (article.status == -2) }<font color="gray">退回</font>#{else }未知#{/if }
 </td>
 <td width="40" align="center">
  #{if article.isGenerated}<font color='blue'><b>√</b></font>
  #{else}<font color='red'><b>×</b></font>#{/if}
 </td>
 <td width="150" align="left">
  #{call article_operate(article)} <%-- call back to article_operate --%>
 </td>
 </tr>
#{/foreach}
</table>
</pub:template>

<pub:template name="article_operate">无操作</pub:template>



<%-- 文章管理导航
 @param page_text - 管理的功能名字，如 '新闻审核'
 --%>
<pub:template name="article_manage_navigator">
#{param page_text }
<table width="100%" border="0" align="center" cellpadding="2" cellspacing="1" class="border">
 <tr class="topbg">
  <td height="22" colspan="10">
  <table width="100%">
   <tr class="topbg">
    <td align="center"><b>#{channel.name }管理----#{(page_text)}</b></td>
    <td width="60" align="right">
     <a href="help/index.jsp" target="_blank"><img src="images/help.gif" border="0" /></a>
    </td>
   </tr>
  </table>
  </td>
 </tr>
 <tr class="tdbg">
  <td width="70" height="30"><strong>管理导航：</strong></td>
  <td colspan="5">
  <a href='admin_article_list.jsp?channelId=#{channel.id}'> #{channel.itemName}管理首页 </a> | 
  <a href='admin_article_add.jsp?channelId=#{channel.id}'> 添加#{channel.itemName} </a> | 
  <a href='admin_article_approv.jsp?channelId=#{channel.id}&status=0'> 审核#{channel.itemName} </a> | 
  <a href='admin_special_article_list.jsp?channelId=#{channel.id}'> 专题#{channel.itemName}管理 </a> | 
  <a href='admin_recycle_article_list.jsp?channelId=#{channel.id}'> #{channel.itemName}回收站管理 </a> |
  <a href='admin_article_generate.jsp?channelId=#{channel.id }'>生成HTML管理</a>
  </td>
 </tr>
</table>
</pub:template>


<%-- 文章管理选项 --%>
<pub:template name="article_manage_options">
<table width="100%" border="0" align="center" cellpadding="2" cellspacing="1" class="border">
<tr class="tdbg">
 <td width="70" height="30"><strong>#{channel.itemName}选项：</strong></td>
 <td>
  <input name="status" type="radio" onclick="location = setUrlParam(location.href, 'status', 'null');"
   value="" #{if request.status == null}checked#{/if} /> 所有#{channel.itemName}
  <input name="status" type="radio" onclick="location = setUrlParam(location.href, 'status', 0);" 
   value="0" #{if request.status == 0}checked#{/if} /> 待审核的#{channel.itemName} 
  <input name="status" type="radio" onclick="location = setUrlParam(location.href, 'status', 1);" 
   value="1" #{if request.status == 1}checked#{/if} /> 已审核的#{channel.itemName}
 </td>
 <td>
  <input name="isTop" type="checkbox" onclick="location = setUrlParam(location.href, 'isTop', this.checked?true:'false');"
   value="true" #{if request.isTop}checked#{/if} /> 固顶#{channel.itemName} 
  <input name="isCommend" type="checkbox" onclick="location = setUrlParam(location.href, 'isCommend', this.checked?true:'false');" 
   value="true" #{if request.isCommend}checked#{/if} /> 推荐#{channel.itemName} 
  <input name="isElite" type="checkbox" onclick="location = setUrlParam(location.href, 'isElite', this.checked?true:'false');" 
   value="true" #{if request.isElite}checked#{/if} /> 精华#{channel.itemName} 
  <input name="isHot" type="checkbox" onclick="location = setUrlParam(location.href, 'isHot', this.checked?true:'');" 
   value="true" #{if request.isHot}checked#{/if} /> 热门#{channel.itemName}
 </td>
</tr>
</table>
</pub:template>

<%-- 我的文章管理选项 --%>
<pub:template name="article_my_manage_options">
<table width="100%" border="0" align="center" cellpadding="2" cellspacing="1" class="border">
<tr class="tdbg">
 <td width="70" height="30"><strong>#{channel.itemName}选项：</strong></td>
 <td>
  <input name="status" type="radio" onclick="location = setUrlParam(location.href, 'status', null);"
   value="null" #{if request.status == null}checked #{/if} /> 所有#{channel.itemName}
  <input name="status" type="radio" onclick="location = setUrlParam(location.href, 'status', -1);"
   value="-1" #{if request.status == -1}checked #{/if} /> 草稿
  <input name="status" type="radio" onclick="location = setUrlParam(location.href, 'status', 0);" 
   value="0" #{if request.status == 0}checked #{/if} /> 待审核的#{channel.itemName} 
  <input name="status" type="radio" onclick="location = setUrlParam(location.href, 'status', 1);" 
   value="1" #{if request.status == 1}checked #{/if} /> 已审核的#{channel.itemName}
 </td>
 <td>
  <input name="isTop" type="checkbox" onclick="location = setUrlParam(location.href, 'isTop', this.checked?true:'');"
   value="true" #{if request.isTop}checked#{/if} /> 固顶#{channel.itemName} 
  <input name="isCommend" type="checkbox" onclick="location = setUrlParam(location.href, 'isCommend', this.checked?true:'');" 
   value="true" #{if request.isElite}checked#{/if} /> 推荐#{channel.itemName} 
  <input name="isElite" type="checkbox" onclick="location = setUrlParam(location.href, 'isElite', this.checked?true:'');" 
   value="true" #{if request.isElite}checked#{/if} /> 精华#{channel.itemName} 
  <input name="isHot" type="checkbox" onclick="location = setUrlParam(location.href, 'isHot', this.checked?true:'');" 
   value="true" #{if request.isHot}checked#{/if} /> 热门#{channel.itemName}
 </td>
</tr>
</table>
</pub:template>


<%-- #{channel.itemName}属性的说明。 --%>
<pub:template name="aritlce_property_description">
 <b>说明：</b>
 <br>&nbsp;&nbsp;&nbsp;&nbsp;#{channel.itemName}属性中的各项含义：<font color=blue>顶</font>----固顶#{channel.itemName}，<font
  color=red>热</font>----热门#{channel.itemName}，<font color=green>荐</font>----推荐#{channel.itemName}，<font
  color=blue>图</font>----首页图片#{channel.itemName}<br>
 <br>
</pub:template>



<%-- 
 #{channel.itemName}搜索栏
 @param dropDownColumns 选择搜索的栏目。
--%>
<pub:template name="article_search_bar">
<form method="get">
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="border">
<tr class="tdbg">
 <td width="80" align="right"><strong>#{channel.itemName}搜索：</strong></td>
 <td><select name="field" size="1">
  <option value="title" selected="selected">#{channel.itemName}标题</option>
  <option value="content">#{channel.itemName}内容</option>
  <option value="author">#{channel.itemName}作者</option>
  <option value="inputer">录入者</option>
 </select>
 <select name="columnId">
  <option value="0">所有栏目</option>
  #{call dropDownColumns(0, dropdown_columns) }
 </select>
 <input type="text" name="keyWord" size="20" value="关键字" maxlength="50" onfocus="this.select();" /> 
 <input type="submit" name="Submit" value="搜索" />
 <input name="channelId" type="hidden" id="channelId" value="#{channel.id }" />
 </td>
</tr>
</table>
</form>
</pub:template>
