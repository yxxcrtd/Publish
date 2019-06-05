<%@ page language="java" pageEncoding="utf-8"%>
<%@taglib prefix="pub" uri="/WEB-INF/publish.tld" %>

<%-- admin_template_xxx 页面使用的公共元素：管理页面头
  @param themeName 方案名称 --%>
<pub:template name="admin_template_help">
<table width='100%' border='0' align='center' cellpadding='2' cellspacing='1' class='border'>
 <tr class='topbg'>
  <td height='22' colspan='10'>
   <table width='100%'>
    <tr class='topbg'>
     <td align='center'><b>#{theme.name }----#{current_group.title }管理</b></td>
     <td width='60' align='right'>
      <a href='help/index.jsp' target='_blank'><img src='images/help.gif' border='0'></a>
     </td>
    </tr>
   </table>
  </td>
 </tr>
 <tr class='tdbg'>
  <td width='70' height='30'>
   <strong>管理导航：</strong></td>
  <td align="left">
   <a href='admin_template_list.jsp?themeId=#{theme.id}&channelId=#{channel.id}&showGroup=#{request.showGroup}'>模板管理首页</a> | 
   <a href='admin_template_add.jsp?themeId=#{theme.id}&channelId=#{channel.id}&typeId=#{current_type.id}'>添加模板</a> | 
   <a href='admin_template_channel_copy.jsp?themeId=#{theme.id}&channelId=#{channel.id}&showGroup=#{request.showGroup}'>频道模板复制</a> | 
  <!--   <a href='Admin_Template.jsp?ChannelID=0&Action=Import&&TemplateProjectID=2'>导入模板</a> | 
   <a href='Admin_Template.jsp?ChannelID=0&Action=Export&&TemplateProjectID=2'>导出模板</a> | -->
  <!--   <a href='Admin_Template.jsp?Action=BatchReplace&ChannelID=0&&TemplateProjectID=2'>批量替换模板代码</a> | 
   <a href='Admin_Template.jsp?Action=Main&ChannelID=0&downright=1&&TemplateProjectID=2'>模板回收站管理</a> | 
   <a href='Admin_Template.jsp?Action=BatchDefault'>模板默认批量设置</a> -->
   <a href='admin_template_recycle.jsp?themeId=#{theme.id}&groupId=#{current_group.id}&channelId=#{channel.id}'>模板回收站管理</a> | 
   <a href='admin_template_action.jsp?command=refresh' title='刷新发布系统内部缓存的模板信息'>刷新模板缓存</a>
  </td>
 </tr>
</table>
<br/>
</pub:template>


<%-- 模板类型组的横向列表，例子：| 网站通用模板 | 新闻中心 | 图片中心  --%>
<pub:template name="show_group_list">
<table width='100%' border='0' align='center' cellpadding='2' cellspacing='1' class='border'>
 <tr class='title'>
  <td>
  #{foreach group in template_group_list}
  | <a href="?themeId=#{theme.id}&channelId=#{group.channelId}&groupId=#{group.id}&showGroup=#{request.showGroup}">
  	#{if group == current_group}
    <font color='red'>#{group.title}</font>
   #{else}
    #{group.title}
   #{/if}
  	</a>
  #{/foreach} |
  </td>
 </tr>
</table>
</pub:template>


<%-- 模板分类列表 --%>
<pub:template name="show_type_list">
<table width='100%' border='0' align='center' cellpadding='2' cellspacing='1' class='border'>
 <tr class='tdbg'>
  <td>
   #{foreach type in template_type_list}
   <nobr>| <a href="admin_template_list.jsp?themeId=#{theme.id}&channelId=#{channel.id}&groupId=#{current_group.id}&typeId=#{type.id}&showGroup=#{request.showGroup}">
   #{if type == current_type}
    <font color='red'>#{type.title@replace("#itemName#", channel.itemName)}</font>
   #{else}
    #{type.title@replace("#itemName#", channel.itemName)}
   #{/if}
   </a></nobr>
   #{/foreach} |
  </td>
 </tr>
</table>
</pub:template>
