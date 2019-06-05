<%@ page language="java" contentType="text/html; charset=gb2312"
    pageEncoding="UTF-8"%>
<%@taglib prefix="pub" uri="/WEB-INF/publish.tld" %>

<%@ include file="element_item.jsp" %>

<pub:template name="show_photo_list">
<table width='100%' border='0' cellpadding='0' cellspacing='2' class='border'>
 #{if photo_list@size == 0}
  <tr class='tdbg'><td width='100%' align='center'><br/>没有任何#{channel.itemName}<br/><br/></td></tr>
 #{/if}
  
  #{foreach photo in photo_list }
   #{if (photo@index % 4) == 0 }<tr>#{/if}
   <td class='tdbg' width='25%' valign='top' align='center'>
   <div align='center'>
      <a href='admin_photo_view.jsp?channelId=#{channel.id}&photoId=#{photo.id }'><img
        src='#{photo.thumbPic@uri(channel)}'
        width='130' height='90' border='0'></a>
   </div>
   <table width='98%' cellpadding='0' cellspacing='0' class='tdbg' onmouseout="this.className='tdbg'" onmouseover="this.className='tdbgmouseover'">
    #{if(photo.columnId != request.columnId) }
    <tr>
     <td align='right' width='70'><nobr>栏目名称：</nobr></td>
     <td width='90%'>
      <a href='?channelId=#{channel.id}&columnId=#{photo.columnId }'>[#{photo.columnName }]</a>
     </td>
    </tr>
    #{/if }
    <tr>
     <td align='right' width='70'><nobr>图片名称：</nobr></td>
     <td width='90%'><a href='admin_photo_view.jsp?channelId=#{channel.id}&photoId=#{photo.id}'
       title='名&nbsp;&nbsp;&nbsp;&nbsp;称：#{photo.title@html}
        &#13;作    者：#{photo.author@html }
        &#13;更新时间：#{photo.lastModified@format }
        &#13;查看次数：#{photo.hits }
        &#13;关 键 字：#{photo.keywords@html}
        &#13;推荐等级：#{'★'@repeat(photo.stars) }'>#{photo.title }</a>
     </td>
    </tr>
    <tr>
     <td align='right'>添 加 者：</td>
     <td><a
       href='?channelId=#{channel.id}&field=inputer&keyword=#{photo.inputer@url}'
       title='点击将查看此用户录入的所有图片'>#{photo.inputer@html}</a>
     </td>
    </tr>
    <tr>
     <td align='right'>点 击 数：</td>
     <td>#{photo.hits }</td>
    </tr>
    <tr>
     <td align='right'>图片属性：</td>
     <td>
      #{if photo.top }<font color="blue">顶</font> #{/if}
      #{if photo.commend }<font color="green">荐</font> #{/if}
      #{if photo.elite }<font color="green">精</font> #{/if}
      #{if photo.hot }<font color="red">热</font> #{/if}
     </td>
    </tr>
    <tr>
     <td align='right'>图片状态：</td>
     <td>
      #{if (photo.status == 1) }
      <font color="black">终审通过</font>
      #{elseif (photo.status == 0) }
      <font color="red">未审批</font>
      #{elseif (photo.status == -1) }
      <font color="black">草稿</font>
      #{else }
       未知
      #{/if }
     </td>
    </tr>
    <tr>
     <td align='right'>已 生 成：</td>
     <td>
      #{if(photo.staticPageUrl != null && photo.staticPageUrl != "") }
      <a href='#' title='文件位置：#{photo.staticPageUrl }'><b>√</b></a>
      #{else }
      <font color='red'><b>×</b></font>
      #{/if }
     </td>
    </tr>
    <tr>
     <td align='right'>操作选项：</td>
     <td>
      <input name='itemId' id='itemId' type='checkbox' value='#{photo.id}' />
     </td>
    </tr>
    <tr>
     <td colspan='2' align='center'>
      #{call photo_operate(photo) }
     </td>
    </tr>
   </table>
  </td>
  #{if (photo@index+1)%4 == 0 || photo@is_last}</tr>#{/if}
 #{/foreach }
</table>
</pub:template>


<%-- 缺省操作 --%>
<pub:template name="photo_operate">
#{param photo}
 <a href="admin_photo_add.jsp?channelId=#{channel.id }&columnId=#{photo.columnId }&photoId=#{photo.id }">修改</a> 
 <a href="admin_photo_action.jsp?command=delete&amp;channelId=#{channel.id}&amp;photoId=#{photo.id}"
   onclick="return confirm('确定要删除此#{channel.itemName}吗？删除后你还可以从回收站中还原。');">删除</a> 
 #{if (photo.top) }
  <a href="admin_photo_action.jsp?command=untop&amp;channelId=#{channel.id}&amp;photoId=#{photo.id}">解固</a> 
 #{else }
  <a href="admin_photo_action.jsp?command=top&amp;channelId=#{channel.id }&amp;photoId=#{photo.id}">固顶</a> 
 #{/if }
 #{if (photo.elite) }
  <a href="admin_photo_action.jsp?command=unelite&amp;channelId=#{channel.id}&amp;photoId=#{photo.id}">取消推荐</a>
 #{else }
  <a href="admin_photo_action.jsp?command=elite&amp;channelId=#{channel.id}&amp;photoId=#{photo.id}">设为推荐</a>
 #{/if }
</pub:template>

<!-- 
@param channel 频道对象
 -->
<pub:template name="photo_manage_navigator">
#{param page_text }
<table width="100%" border="0" align="center" cellpadding="2" cellspacing="1" class="border">
<tr class="topbg">
 <td height="22" colspan="10">
 <table width="100%">
  <tr class="topbg">
   <td align="center"><b>#{channel.name }管理----#{page_text }</b></td>
   <td width="60" align="right"><a
    href="help/index.jsp" target="_blank">
   <img src="images/help.gif" border="0" /> </a></td>
  </tr>
 </table>
 </td>
</tr>
<tr class="tdbg">
 <td width="70" height="30"><strong>管理导航：</strong></td>
 <td colspan="5">
 <a href="admin_photo_list.jsp?channelId=#{channel.id}"> #{channel.itemName}管理首页 </a> | 
 <a href="admin_photo_add.jsp?channelId=#{channel.id}"> 添加#{channel.itemName} </a> | 
 <a href="admin_photo_approv.jsp?channelId=#{channel.id}&status=0"> 审核#{channel.itemName} </a> | 
 <a href="admin_special_photo_list.jsp?channelId=#{channel.id}"> 专题#{channel.itemName}管理 </a> | 
 <a href="admin_recycle_photo_list.jsp?channelId=#{channel.id}"> #{channel.itemName}回收站管理 </a> 
 <!-- | <a href="admin_photo_generate.jsp?channelId=#{channel.id }"><b>生成HTML管理</b> </a> -->
 </td>
</tr>
</table>
</pub:template>


<!-- 
@param channel 频道对象
 -->
<pub:template name="photo_manage_options">
<table width="100%" border="0" align="center" cellpadding="2" cellspacing="1" class="border">
<tr class="tdbg">
 <td width="70" height="30"><strong>#{channel.itemName}选项：</strong></td>
 <td>
  <input name="status" type="radio" onclick="location = setUrlParam(location.href, 'status', 'null');"
  value="" #{if request.status == null}checked#{/if } /> 所有#{channel.itemName}
  <input name="status" type="radio" onclick="location = setUrlParam(location.href, 'status', 0);" 
  value="0" #{if request.status == 0}checked#{/if } /> 待审核的#{channel.itemName} 
  <input name="status" type="radio" onclick="location = setUrlParam(location.href, 'status', 1);" 
  value="1" #{if request.status == 1}checked#{/if } /> 已审核的#{channel.itemName}
 </td>
 <td>
  <input name="isTop" type="checkbox" onclick="location = setUrlParam(location.href, 'isTop', this.checked?true:'false');"
   value="true" #{if request.isTop}checked#{/if} /> 固顶#{channel.itemName} 
  <input name="isCommend" type="checkbox" onclick="location = setUrlParam(location.href, 'isCommend', this.checked?true:'false');" 
   value="true" #{if request.isCommend}checked#{/if} /> 推荐#{channel.itemName}
  <input name="isElite" type="checkbox" onclick="location = setUrlParam(location.href, 'isElite', this.checked?true:'false');" 
   value="true" #{if request.isElite}checked#{/if} /> 精华#{channel.itemName}
  <input name="isHot" type="checkbox" onclick="location = setUrlParam(location.href, 'isHot', this.checked?true:'false');" 
   value="true" #{if request.isHot}checked#{/if} /> 热门#{channel.itemName}
 </td>
</tr>
</table>
</pub:template>


<pub:template name="photo_my_manage_options">
<table width="100%" border="0" align="center" cellpadding="2" cellspacing="1" class="border">
<tr class="tdbg">
 <td width="70" height="30"><strong>#{channel.itemName}选项：</strong></td>
 <td>
  <input name="status" type="radio" onclick="location = setUrlParam(location.href, 'status', 'null');"
  value="" #{if request.status == null}checked#{/if} /> 所有#{channel.itemName}
  <input name="status" type="radio" onclick="location = setUrlParam(location.href, 'status', -1);"
  value="-1" #{if request.status == -1}checked#{/if} /> 草稿
  <input name="status" type="radio" onclick="location = setUrlParam(location.href, 'status', 0);" 
  value="0" #{if request.status == 0}checked#{/if} /> 待审核的#{channel.itemName}
  <input name="status" type="radio" onclick="location = setUrlParam(location.href, 'status', 1);" 
  value="1" #{if request.status == 1}checked#{/if} /> 已审核的#{channel.itemName}
 </td>
 <td>
  <input name="isTop" type="checkbox" onclick="location = setUrlParam(location.href, 'isTop', this.checked?true:'');"
   value="true" #{if request.top}checked#{/if} /> 固顶#{channel.itemName} 
  <input name="isCommend" type="checkbox" onclick="location = setUrlParam(location.href, 'isCommend', this.checked?true:'');" 
   value="true" #{if request.commend}checked#{/if} /> 推荐#{channel.itemName}
  <input name="isElite" type="checkbox" onclick="location = setUrlParam(location.href, 'isElite', this.checked?true:'');" 
   value="true" #{if request.commend}checked#{/if} /> 精华#{channel.itemName}
  <input name="isHot" type="checkbox" onclick="location = setUrlParam(location.href, 'isHot', this.checked?true:'');" 
   value="true" #{if request.hot}checked#{/if} /> 热门#{channel.itemName}
 </td>
</tr>
</table>
</pub:template>



<!-- #{channel.itemName}属性的说明。 -->
<pub:template name="photo_property_description">
 <b>说明：</b>
 <br>&nbsp;&nbsp;&nbsp;&nbsp;#{channel.itemName}属性中的各项含义：<font color=blue>顶</font>----固顶#{channel.itemName}，<font
  color=red>热</font>----热门#{channel.itemName}，<font color=green>荐</font>----推荐#{channel.itemName}，<font
  color=blue>图</font>----首页#{channel.itemName}#{channel.itemName}<br>
 <br>
</pub:template>


<%-- 图片搜索栏 --%>
<pub:template name="photo_search_bar">
 <form method="get" style='margin:0px;'>
 <table width="100%" border="0" cellpadding="0" cellspacing="0" class="border">
  <tr class="tdbg">
   <td width="80" align="right"><strong>#{channel.itemName}搜索：</strong></td>
   <td><select name="field" size="1">
    <option value="title" selected="selected">#{channel.itemName}标题</option>
    <option value="content">#{channel.itemName}内容</option>
    <option value="author">#{channel.itemName}作者</option>
    <option value="inputer">录入者</option>
   </select> 
   <select name="columnId" onclick="channelColumn='#{channel.id}&amp;columnId='+this.value;">
    <option value="0">所有栏目</option>
    #{call dropDownColumns(0, dropdown_columns) }
   </select>
   <input type="text" name="keyWord" size="20" value="关键字" maxlength="50" onFocus="this.select();" /> 
   <input type="submit" name="Submit" value="搜索"/>
   <input name="channelId" type="hidden" id="channelId" value="#{channel.id }" />
   </td>
  </tr>
 </table>
 </form>
</pub:template>

