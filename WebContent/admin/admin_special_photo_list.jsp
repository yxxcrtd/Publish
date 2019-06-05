<%@ page language="java" contentType="text/html; charset=gb2312"
 pageEncoding="UTF-8" errorPage="admin_error.jsp"
%><%@taglib prefix="pub" uri="/WEB-INF/publish.tld"
%><%@page import="com.chinaedustar.publish.admin.PhotoManage"
%><%
  PhotoManage manager = new PhotoManage(pageContext);
  manager.initSpecialItemListPage();
  
%><!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
 <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
 <title>专题图片管理</title>
 <link href='admin_style.css' rel='stylesheet' type='text/css'>
 <script type="text/javascript" src="main.js"></script>
 <script type='text/javascript' src='admin_special_item.js'></script>
</head>
<body>

<pub:declare>

<%@ include file="element_column.jsp" %>
<%@ include file="element_photo.jsp" %>
 
<pub:template name="main">
 #{call photo_manage_navigator("专题" + channel.itemName + "管理") }
 #{call photo_manage_options }<br />
 
 #{call show_special_list }<br />
 #{call your_position }
 #{call show_photo_list }
 #{call pagination_bar(page_info) }
 #{call form_action }
 
 #{call photo_search_bar }<br />
 #{call photo_property_description }
</pub:template>


<pub:template name="show_special_list">
<table width='100%' border='0' align='center' cellpadding='2' cellspacing='1' class='border'>
  <tr class='title'>
   <td height='22'>|
    #{foreach special in special_list }
    #{if (special.channelId != 0) }
    <a href="admin_special_photo_list.jsp?channelId=#{special.channelId }&amp;specialId=#{special.id }"> #{special.name } </a> | 
    #{/if }
    #{/foreach }
  </tr>
</table>
</pub:template>


<pub:template name="your_position">
<table width='100%' border='0' align='center' cellpadding='0' cellspacing='0'>
  <tr>
   <td height='22'>
   您现在的位置：&nbsp;<a href="admin_photo_list.jsp?channelId=#{channel.id }">#{channel.name }管理</a>&nbsp;&gt;&gt;&nbsp;<a
    href='admin_special_photo_list.jsp?channelId=#{channel.id }'>专题#{channel.itemName}管理</a>
    &nbsp;&gt;&gt;&nbsp;所有#{channel.itemName}</td>
  </tr>
</table>
</pub:template>


<pub:template name="show_photo_list">
<form name="myform" method="post" action="" onsubmit="return ConfirmDel();">
 <table width='100%' border='0' cellpadding='0' cellspacing='2' class='border'>
  #{foreach photo in photo_list }
  #{if photo@index % 4 == 0 }<tr>#{/if }
   <td class='tdbg' width='25%' valign='top' align='center'>
   <table width='98%' cellpadding='0' cellspacing='0' class='tdbg' onmouseout="this.className='tdbg'" onmouseover="this.className='tdbgmouseover'">
    <tr>
     <td colspan='2' align='center'>
      <a href='admin_photo_add.jsp?channelId=#{channel.id }&photoId=#{photo.id }'><img
        src='#{photo.thumbPicAbs}' width='130' height='90' border='0'></a>
     </td>
    </tr>
    <tr>
     <td align='right'><nobr>#{channel.itemName }名称：</nobr></td>
     <td><a href='admin_photo_view.jsp?channelId=#{channel.id }&photoId=#{photo.id }'
       title='名&nbsp;&nbsp;&nbsp;&nbsp;称：#{photo.title@html }
          &#13;作    者：#{photo.author@html }
          &#13;更新时间：#{photo.lastModified@format}
          &#13;查看次数：#{photo.hits }
          &#13;关 键 字：#{photo.keywords }
          &#13;推荐等级：#{'★'@repeat(photo.stars) }'>#{photo.title@html }</a>
     </td>
    </tr>
    <tr>
     <td align='right'>所属专题：</td>
     <td>#{photo.specialName }</td>
    </tr>
    <tr>
     <td align='right'>添 加 者：</td>
     <td><a
       href='admin_photo_list.jsp?channelId=#{channel.id }&field=inputer&keyWord=#{photo.inputer }'
       title='点击将查看此用户录入的所有图片'>#{photo.inputer }</a>
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
      #{if photo.commend }<font color="green">荐</font> #{/if }
      #{if photo.elite }<font color="green">精</font> #{/if }
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
      <input name='itemId' id='itemId' type='checkbox' value='#{photo.refid }' />
     </td>
    </tr>
    <tr>
     <td colspan='2' align='center'>
     <a href="admin_photo_add.jsp?channelId=#{channel.id }&photoId=#{photo.id }">修改</a> 
     <a href="admin_photo_action.jsp?command=delete&channelId=#{channel.id }&photoId=#{photo.id }"
      onclick="return confirm('确定要删除此#{channel.itemName}吗？删除后你还可以从回收站中还原。');">删除</a> 
     #{if photo.top }
      <a href="admin_photo_action.jsp?command=top&channelId=#{channel.id }&photoId=#{photo.id }">解固</a> 
     #{else }
      <a href="admin_photo_action.jsp?command=untop&channelId=#{channel.id }&photoId=#{photo.id }">固顶</a> 
     #{/if }
     #{if photo.commend }
      <a href="admin_photo_action.jsp?command=commend&channelId=#{channel.id }&photoId=#{photo.id }">取消推荐</a>
     #{else }
      <a href="admin_photo_action.jsp?command=uncommend&channelId=#{channel.id }&photoId=#{photo.id }">设为推荐</a>
     #{/if }
     </td>
    </tr>
   </table>
  </td>
  #{if photo@index % 4 == 3 || photo@is_last }</tr>#{/if }
 #{/foreach }
</table>

<table width='100%' border='0' cellpadding='0' cellspacing='0'>
  <tr>
   <td width='200' height='30'><input name="chkAll"
    type="checkbox" id="chkAll" onclick='CheckAll(this)'
    value="checkbox" /> 选中本页显示的所有#{channel.itemName }</td>
   <td>
    <input type='button' onClick="removeSpecials()"   value='从当前专题中移除' /> 
    <input type='button' onClick="copySpecialItems(true)"   value='添加到其他专题中' /> 
    <input type='button' onClick="copySpecialItems(false)"  value='移动到其他专题中' />    
   </td>
  </tr>
</table>
</form>
</pub:template>


</pub:declare>

<pub:process_template name="main" />

</body>
</html>
