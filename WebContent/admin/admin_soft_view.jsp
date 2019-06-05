<%@page language="java" contentType="text/html; charset=gb2312" 
   pageEncoding="UTF-8" errorPage="admin_error.jsp"
%><%@taglib prefix="pub" uri="/WEB-INF/publish.tld"
%><%@page import="com.chinaedustar.publish.admin.SoftManage"
%><%
  // 初始化页面数据。
  SoftManage admin_data = new SoftManage(pageContext);
  admin_data.initViewPage();
  
%><!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>软件管理</title>
<link href='admin_style.css' rel='stylesheet' type='text/css' />
<script type="text/javascript" src="main.js"></script>
</head>
<body leftmargin='2' topmargin='0' marginwidth='0' marginheight='0'>

<pub:declare>

<!-- 定义标签页集合。 -->
<pub:tabs var="contentTabs" scope="page" purpose="">
 <pub:tab name="baseInfo" text="基本信息" template="temp_baseInfo" default="true" />
 <pub:tab name="specialSet" text="所属专题" template="temp_specialSet" />
</pub:tabs>

<%@ include file="element_soft.jsp" %>
<%@ include file="element_column.jsp" %>
<%@ include file="tabs_tmpl2.jsp" %>


<pub:template name="main">
 #{call soft_manage_navigator("添加" + channel.itemName) }<br />
 #{call your_position }
 <div>
  #{call tab_js }
  #{call tab_header(contentTabs) }
  #{call tab_content(contentTabs) }
 </div>

 #{call operate_form }<br/><br/><br/>
</pub:template>



<pub:template name="your_position">
<table width='100%'>
 <tr>
  <td align='left'>
  您现在的位置： <a href='admin_soft_list.jsp?channelId=#{channel.id }'>#{channel.name }管理</a>
    &gt;&gt; 查看#{channel.itemName }信息:#{soft.title@html }
  </td>
 </tr>
</table>
</pub:template>



<!-- 基本信息 -->
<pub:template name="temp_baseInfo">
<table width='98%' border='0' cellpadding='2' cellspacing='1' bgcolor='#FFFFFF'>
  <tr class='tdbg'>
    <td width='100' align='right' class='tdbg5'>软件名称：</td>
    <td colspan='3'><strong>#{soft.title@html }</strong></td>
  </tr>
  <tr class='tdbg'>
    <td width='100' align='right' class='tdbg5'>文件大小：</td>
    <td width='300'>#{soft.size}&nbsp;K</td>
    <td colspan='2' rowspan='6' align=center valign='middle'>
     <img src='#{soft.thumbPic@uri(channel) }' width='150'>
    </td>
  </tr>
  <tr class='tdbg'>
    <td width='100' align='right' class='tdbg5'>开 发 商：</td>
    <td width='300'>#{soft.author@html }</td>
  </tr>
  <tr class='tdbg'>
    <td width='100' align='right' class='tdbg5'>软件来源：</td>
    <td width='300'>#{soft.source }</td>
  </tr>
  <tr class='tdbg'>
    <td width='100' align='right' class='tdbg5'>软件平台：</td>
    <td width='300'>#{soft.OS }</td>
  </tr>
  <tr class='tdbg'>
    <td width='100' align='right' class='tdbg5'>软件类别：</td>
    <td width='300'>#{soft.type }</td>
  </tr>
  <tr class='tdbg'>
    <td width='100' align='right' class='tdbg5'>软件属性：</td>
    <td width='300'>
      #{if soft.top}<font color="blue">顶</font> #{/if }
      #{if soft.commend}<font color="green">荐</font> #{/if } 
      #{if soft.elite}<font color="green">精</font> #{/if } 
      #{if soft.hot }<font color="red">热</font> #{/if }
    </td>
  </tr>
  <tr class='tdbg'>
    <td width='100' align='right' class='tdbg5'>软件语言：</td>
    <td width='300'>#{soft.language }</td>
    <td width='100' align='right' class='tdbg5'>授权形式：</td>
    <td width='300'>#{soft.copyrightType }</td>
  </tr>
  <tr class='tdbg'>
    <td width='100' align='right' class='tdbg5'>演示地址：</td>
    <td width='300'><a href='#{soft.demoUrl }' target='_blank'>#{soft.demoUrl }</a></td>
    <td width='100' align='right' class='tdbg5'>注册地址：</td>
    <td width='300'><a href='#{soft.registUrl }' target='_blank'>#{soft.registUrl }</a></td>
  </tr>
  <tr class='tdbg'>
    <td width='100' align='right' class='tdbg5'>解压密码：</td>
    <td width='300'>#{soft.decompPwd }</td>
    <td width='100' align='right' class='tdbg5'>评分等级：</td>
    <td>#{'★'@repeat(soft.stars) }</td>
  </tr>
  <tr class='tdbg'>
    <td width='100' align='right' class='tdbg5'>软件添加：</td>
    <td width='300'>#{soft.inputer }</td>
    <td width='100' align='right' class='tdbg5'>责任编辑：</td>
    <td>#{soft.editor }</td>
  </tr>
  <tr class='tdbg'>
    <td width='100' align='right' class='tdbg5'>添加时间：</td>
    <td width='300'>#{soft.createTime@format }</td>
    <td width='100' align='right' class='tdbg5'>下载次数：</td>
    <td colspan='3'>
      本日：#{soft.dayHits }&nbsp;&nbsp;&nbsp;&nbsp;
      本周：#{soft.weekHits }&nbsp;&nbsp;&nbsp;&nbsp;
      本月：#{soft.monthHits }&nbsp;&nbsp;&nbsp;&nbsp;
      总计：#{soft.hits }
  </tr>
  <tr class='tdbg'>
    <td width='100' align='right' class='tdbg5'>下载地址：</td>
    <td colspan='3'>
     #{soft.downloadUrls }
    </td>
  </tr>
  <tr class='tdbg'>
    <td width='100' align='right' class='tdbg5'>软件简介：</td>
    <td height='100' colspan='3'>#{soft.description }</td>
  </tr>
</table>
</pub:template>



<!-- 所属专题 -->
<pub:template name="temp_specialSet">
<table width='98%' border='0' cellpadding='2' cellspacing='1' bgcolor='#FFFFFF'>
 <tr class='tdbg'>
  <td width='120' align='right' class='tdbg5'>所属专题：</td>
  <td>
   <select disabled name='specialIds' size='2' multiple='true' style='height:300px;width:260px;'>
    #{foreach special in channel_specials }
     <option value='#{special.id }' #{iif (soft.specialIds@contains(special.id), 
        "selected", "")}>#{special.name }（#{iif (special.channelId==0, "全站", "本频道") }）</option>
    #{/foreach }
   </select> <br />
  <input disabled type='button' name='Submit' value='  选定所有专题  ' onclick='selectAll()' /> <br />
  <input disabled type='button' name='Submit' value='取消选定所有专题' onclick='unSelectAll()' /></td>
 </tr>
</table>
</pub:template>



<pub:template name="operate_form">
<form name='formAction' method='get' action='admin_soft_action.jsp'>
<div align='center'>
 <input type='hidden' name='channelId' value='#{channel.id}' />
 <input type='hidden' name='softId' value='#{soft.id}' />
 <input type='hidden' name='itemId' value='#{soft.id}' />
 <input type='hidden' name='command' value='' />
#{if soft.deleted == false }
 <input type='button' name='submit' value='修改/审核' 
  onclick="window.location='admin_soft_add.jsp?channelId=#{channel.id}&softId=#{soft.id}'" />&nbsp;&nbsp;
 <input type='submit' name='submit' value=' 删 除 ' 
  onclick="document.formAction.command.value='delete'" />&nbsp;&nbsp;
 <input type='submit' name='submit' value=' 移 动 ' 
  onclick="document.formAction.command.value='move_column'" />&nbsp;&nbsp;
 <input type='submit' name='submit' value='直接退稿' 
  onclick="document.formAction.command.value='reject'" />&nbsp;&nbsp;
 #{if soft.status == 1}
 <input type='submit' name='submit' value='取消审核' 
  onclick="document.formAction.command.value='unappr'" />&nbsp;&nbsp;
 #{else}
 <input type='submit' name='submit' value='审核通过' 
  onclick="document.formAction.command.value='appr'" />&nbsp;&nbsp;
 #{/if }
 #{if soft.top }
 <input type='submit' name='submit' value='取消固顶' 
  onclick="document.formAction.command.value='untop'" />&nbsp;&nbsp;
 #{else }
 <input type='submit' name='submit' value='固顶' 
  onclick="document.formAction.command.value='top'" />&nbsp;&nbsp;
 #{/if }
 #{if soft.commend }
 <input type='submit' name='submit' value='取消推荐' 
  onclick="document.formAction.command.value='unelite'" />
 #{else }
 <input type='submit' name='submit' value='设为推荐' 
  onclick="document.formAction.command.value='elite'" />
 #{/if }
#{else}
 <input type='submit' name='submit' value='从回收站恢复' 
  onclick="document.formAction.command.value='recover'" />
 <input type='submit' name='submit' value='从回收站彻底删除' 
  onclick="document.formAction.command.value='destroy'" />
#{/if}
</div>
</form>
</pub:template>



</pub:declare>

<pub:process_template name="main" />

</body>
</html>
