<%@page language="java" contentType="text/html; charset=gb2312" 
   pageEncoding="UTF-8" errorPage="admin_error.jsp"
%><%@taglib prefix="pub" uri="/WEB-INF/publish.tld"
%>
<%@page import="com.chinaedustar.publish.admin.SpecialManage" %>

<%
  // 初始化页面数据。
  SpecialManage admin_data = new SpecialManage(pageContext);
  admin_data.initListPage();
  
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
 <meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
 <link href='admin_style.css' rel='stylesheet' type='text/css' />
 <title>专题管理</title>
</head>
<body leftmargin='2' topmargin='0' marginwidth='0' marginheight='0'>
<pub:declare>

<%@include file="element_special.jsp" %>

<pub:template name="main">
 #{call special_navigator("专题管理") }<br />
 #{call channel_nav}
 #{call temp_speicalList }<br />
 
 <b>注意：</b><br />
 &nbsp;&nbsp;&nbsp;&nbsp;
 若专题目录为红色，表示此专题还没有创建相关的目录。请到“生成HTML管理”页面使用“生成专题目录”功能重新创建此专题的目录。 <br />
</pub:template>


<pub:template name="channel_nav">
 <table width='100%' border='0' align='center' cellpadding='2'  cellspacing='1' class='border'>
  <tr class='title'>
  <td height='22'>| 
   <a href='admin_special_list.jsp?channelId=0'>全站专题</a>
   #{foreach channel0 in channel_list }
    #{if (channel0.id == channel.id) }
    | <font color='red'>#{channel0.name }</font> 
    #{else }
    | <a href='admin_special_list.jsp?channelId=#{channel0.id}'>#{channel0.name@html}</a>
    #{/if }
   #{/foreach }
  </td>
  </tr>
 </table>
</pub:template>


<pub:template name="temp_speicalList">
<br />
<table width='100%' border='0' align='center' cellpadding='2' cellspacing='1' class='border'>
 <tr class='title' align="center">
  <td height='22'><b>专题名称</b></td>
  <td width='80'><b>专题目录</b></td>
  <td width='80'><b>推荐专题</b></td>
  <td width='200'><b>专题提示</b></td>
  <td width='100' height='22'><b>常规操作</b></td>
 </tr>
#{foreach special in special_list}
 <tr class='tdbg' onmouseout="this.className='tdbg'" onmouseover="this.className='tdbgmouseover'">
  <td align='center'>
	#{if (special.specialDir@length > 0) }
	 <a href='admin_special_article_list.jsp?channelId=#{special.channelId }&specialId=#{special.id }'
		title='点击进入管理此专题的文章'>
		#{special.name }
    </a>
	#{else }
	 <a href='admin_special_article_list.jsp?channelId=#{special.channelId }&specialId=#{special.id }'
		title='点击进入管理此专题的文章' style="color: red;">
		#{special.name }
	 </a>
	#{/if }
  </td>
  <td width='80' align='center'>#{special.specialDir }</td>
  <td width='80' align='center'>
	<font color='green'>#{iif (special.isElite, "是", "否") }</font>
  </td>
  <td width='200'>#{special.tips@html }</td>
  <td width='100' align='center'>
	<a href='admin_special_add.jsp?channelId=#{special.channelId }&specialId=#{special.id }'
     >修改</a>
	<a href='admin_special_action.jsp?command=delete&amp;channelId=#{special.channelId}&specialId=#{special.id}'
		onclick="return confirm('确定要删除此专题吗？删除此专题后原属于此专题的文章将不属于此专题。');"
     >删除</a>
	<a href='admin_special_action.jsp?command=clear&amp;channelId=#{special.channelId  }&specialId=#{special.id }'
		onclick="return confirm('确定要清空此专题中的文章吗？本操作将原属于此专题的文章改为不属于此专题。');"
     >清空</a>
  </td>
 </tr>
#{/foreach}
</table>
<br /><br />
</pub:template>

</pub:declare>

<pub:process_template name="main" />

</body>
</html>