<%@page language="java" contentType="text/html; charset=gb2312" 
   pageEncoding="UTF-8" errorPage="admin_error.jsp"
%><%@taglib prefix="pub" uri="/WEB-INF/publish.tld"
%><%@page import="com.chinaedustar.publish.admin.ChannelManage"
%><%
  // 初始化管理页面数据.
  ChannelManage admin_data = new ChannelManage(pageContext);
  admin_data.initEditPage();
  
%><!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
 <meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
 <title>频道属性</title>
 <link href='admin_style.css' rel='stylesheet' type='text/css' />
</head>
<body leftmargin='2' topmargin='0' marginwidth='0' marginheight='0' onload="if(document.myform.ChannelType[0].checked){document.myform.ChannelType[0].click();}">

<pub:declare>
<!-- 定义系统内建 '频道属性' 的属性页. -->
<pub:tabs var="channel_tabs" scope="page" purpose="频道属性的属性页集合">
 <pub:tab name="channel_prop" text="基本设置"  template="channel_prop_template" default='true' />
 <pub:tab name="channel_prop_ex" text="更多设置"  template="channel_prop_ex_template" />
 <pub:tab name="channel_upload" text="上传选项"  template="channel_upload_template" />
 <pub:tab name="channel_generate" text="生成选项" template="channel_generate_template" />
 <pub:tab name="extendsInfo" text="扩展属性" template="object_extends_edit" />
</pub:tabs>

<%@ include file="element_channel.jsp"%>
<%@ include file="tabs_tmpl2.jsp"%>
<%@ include file="extends_prop.jsp" %>

<!-- 主执行模板定义 -->
<pub:template name="main">
#{call channel_manage_navigator }
#{if (channel.id == 0) }
 #{call channel_manage_position("添加新频道") }
#{else }
 #{call channel_manage_position("修改频道设置", channel.name) }
#{endif }
 
<form name="myform" id="myform" method="POST" action="admin_channel_action.jsp" onSubmit='return CheckForm();'>
 #{call tab_js}
 #{call tab_header(channel_tabs)}
 #{call tab_content(channel_tabs)}
 #{call form_button}
 #{call validate_js}
</form>
</pub:template>

<!-- 频道基本信息属性页模板的定义 -->
<pub:template name="channel_prop_template">
<table width='100%' border='0' align='center' cellpadding='2' cellspacing='1' bgcolor='white'>
<tr class='tdbg'>
 <td width='200' class='tdbg5'>
  <strong>频道名称：</strong>
 </td>
 <td>
  <input name='Name' type='text' id='Name' size='49' maxlength='30'
   value='#{channel.name@html}'>
  <font color='#FF0000'>*</font>
 </td>
</tr>
<tr class='tdbg'>
 <td width='200' class='tdbg5'>
  <strong>频道提示：</strong>
  <br>
  鼠标移至频道名称上时将显示设定的说明文字（不支持HTML）
 </td>
 <td valign='middle'>
  <input name='Tips' type='text' id='Tips' size='49' maxlength='200'
   value='#{channel.tips@html}'>
 </td>
</tr>
<tr class='tdbg'>
 <td width='200' class='tdbg5'>
  <strong>频道详细说明：</strong>
  <br>
  这个频道的详细说明（支持HTML）
 </td>
 <td valign='middle'>
  <textarea name='Description' cols='40' rows='3' id='Description'
   type="_moz">#{channel.description@html}</textarea>
 </td>
</tr>
<tr class='tdbg'>
 <td width='200' class='tdbg5'>
  <strong>频道类型：</strong>
  <br>
  <font color=red>请慎重选择，频道一旦添加后就不能再更改频道类型。</font>
 </td>
 <td>
  <input name='ChannelType' type='radio' value='2'
   onclick="ChannelSetting.style.display='none'" #{if(channel.channelType==2) } checked #{/if} #{if(channel.id!=0 && channel.channelType!=2) }disabled#{/if}/>
  <font color=blue><b>外部频道</b>
  </font>&nbsp;&nbsp;&nbsp;&nbsp;外部频道指链接到本系统以外的地址中。当此频道准备链接到网站中的其他系统时，请使用这种方式。
  <br />
  &nbsp;&nbsp;&nbsp;&nbsp;外部频道的链接地址：
  <input name='ChannelUrl' type='text' id='ChannelUrl' value='#{channel.channelUrl@html}' size='40' maxlength='200' #{if(channel.id!=0 && channel.channelType!=2) }disabled#{/if}>
  <br />
  <br />
  <input name='ChannelType' type='radio' value='#{if(channel.id==0) }1#{else }#{channel.channelType }#{/if}' #{if(channel.channelType!=2) } checked #{/if}
    onclick="ChannelSetting.style.display=''" #{if(channel.id!=0 && channel.channelType==2 ) } disabled #{/if}>
  <font color=blue><b>系统内部频道</b>
  </font>&nbsp;&nbsp;&nbsp;&nbsp;系统内部频道指的是在本系统现有
  功能模块（新闻、文章、图片等）基础上添加新的频道，新频道具备和所使用功能模块完全相同的功能。例如，添加一个
  名为“网络学院”的新频道，新频道使用“文章”模块的功能，则新添加的“网络学院”频道具有原文章频道的所有功能。
  <br />
  <table id='ChannelSetting' width='100%' border='0' cellpadding='2'
   cellspacing='1' bgcolor='#FFFFFF' #{if(channel.channelType==2) }style='display:none'#{endif }>
   <tr align='center' class='tdbg'>
    <td colspan='2'>
     <strong>内部频道参数设置</strong>
    </td>
   </tr>
   <tr class='tdbg'>
    <td width='200' class='tdbg5'>
     <strong>频道使用的功能模块：</strong>
    </td>
    <td>
     <select name='ModuleId' id='ModuleId' #{if(channel.id!=0)}disabled#{/if}>
      #{foreach module in module_list }
      <option value='#{module.id }' #{if(module.id==channel.moduleId)}selected#{/if}>#{module.title@html}</option>
      #{/foreach }
     </select>
     #{if(channel.id!=0)}
      <input type="hidden" name="ModuleId" id="ModuleId" value="#{channel.moduleId }"/>
     #{endif }
    </td>
   </tr>
   <tr class='tdbg'>
    <td width='200' class='tdbg5'>
     <strong>频道目录：</strong>（频道英文名）
     <br />
     <font color='#FF0000'>只能是英文，不能带空格或“\”、“/”等符号。</font>
     <br />
     <font color='#0000FF'>样例：</font>News、Article、Soft
    </td>
    <td>
     <input name='ChannelDir#{if(channel.id!=0)}Rep#{endif }' type='text' id='ChannelDir#{if(channel.id!=0)}Rep#{endif }'
      value='#{channel.channelDir}' size='20' maxlength='50'  #{if(channel.id!=0)}disabled#{endif }/>
     <font color='#FF0000'>*</font>
     #{if(channel.id!=0)}
      <input type="hidden" name="ChannelDir" id="ChannelDir" value="#{channel.channelDir }"/>
     #{endif }
    </td>
   </tr>
   <tr class='tdbg'>
    <td width='200' class='tdbg5'>
     <strong>项目名称：</strong>
     <br />
     例如：频道名称为“网络学院”，其项目名称为“文章”或“教程”
    </td>
    <td>
     <input name='ItemName' type='text' id='ItemName' size='20'
      maxlength='30' value='#{channel.itemName}'>
     <font color='#FF0000'>*</font>
    </td>
   </tr>
   <tr class='tdbg'>
    <td width='200' class='tdbg5'>
     <strong>项目单位：</strong>
     <br />
     例如：“篇”、“条”、“个”
    </td>
    <td>
     <input name='ItemUnit' type='text' id='ItemUnit' size='10'
      maxlength='30' value='#{channel.itemUnit}' />
    </td>
   </tr>
  </table>
 </td>
</tr>
<tr class='tdbg'>
 <td width='200' class='tdbg5'>
  <strong>打开方式：</strong>
 </td>
 <td>
  <input type='radio' name='OpenType' value='0' #{iif(channel.openType == 0, "checked", "" ) }/>
  在原窗口打开&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
  <input type='radio' name='OpenType' value='1' #{iif(channel.openType == 1, "checked", "" ) }/>
  在新窗口打开
 </td>
</tr>
<tr class='tdbg'>
 <td width='200' class='tdbg5'>
  <strong>禁用本频道：</strong>
 </td>
 <td>
  <input name='Disabled' type='radio' value='1'
   #{if(channel.status==1) }checked#{endif}/>
  是 &nbsp;&nbsp;&nbsp;&nbsp;
  <input name='Disabled' type='radio' value='0'
   #{if(channel.status==0) }checked#{endif}/>
  否
 </td>
</tr>
<tr class='tdbg'>
 <td width='200' class='tdbg5'>
  <strong>是否显示在导航条：</strong>
 </td>
 <td>
  <input name='ShowInNav' type='radio' value='1'
   #{if channel.showInNav}checked#{/if} />
  是 &nbsp;&nbsp;&nbsp;&nbsp;
  <input name='ShowInNav' type='radio' value='0'
   #{if channel.showInNav == false}checked#{/if} />
  否
 </td>
</tr>
</table>
</pub:template>

<pub:template name="channel_prop_ex_template">
<table width='100%' border='0' align='center' cellpadding='2' cellspacing='1' bgcolor='white'>
<tr class='tdbg'>
 <td width='200' class='tdbg5'>
  <strong>LOGO地址：</strong>
  <br>
  请填写完整URL地址
 </td>
 <td>
  <input name='Logo' type='text' id='Logo' value='#{channel.logo }'
   size='40' maxlength='255'>
 </td>
</tr>
<tr class='tdbg'>
 <td width='200' class='tdbg5'>
  <strong>Banner地址：</strong>
  <br>
  请填写完整URL地址
 </td>
 <td>
  <input name='Banner' type='text' id='Banner'
   value='#{channel.banner }' size='40' maxlength='255'>
 </td>
</tr>
<tr class='tdbg'>
 <td width='200' class='tdbg5'>
  <strong>版权信息：</strong>
  <br>
  支持HTML标记，不能使用双引号
 </td>
 <td>
  <textarea name='Copyright' cols='60' rows='4' id='Copyright' type="_moz">#{channel.copyright@html }</textarea>
 </td>
</tr>
<tr class='tdbg'>
 <td width='200' class='tdbg5'>
  <strong>网站META关键词：</strong>
  <br>
  针对搜索引擎设置的关键词
 </td>
 <td>
  <textarea name='MetaKey' cols='60' rows='2' id='MetaKey' type="_moz">#{channel.metaKey@html }</textarea>
 </td>
</tr>
<tr class='tdbg'>
 <td width='200' class='tdbg5'>
  <strong>网站META网页描述：</strong>
 <br>
  针对搜索引擎设置的网页描述
 </td>
 <td>
  <textarea name='MetaDesc' cols='60' rows='2' id='MetaDesc' type="_moz">#{channel.metaDesc@html}</textarea>
 </td>
</tr>
#{if(channel.id > 0) }
<tr class='tdbg'>
 <td width='200' class='tdbg5'>
  <strong>本频道的首页模板</strong>
 </td>
 <td>
  <select name='TemplateId' id='TemplateId'>
    <option value='0' #{iif(channel.templateId == 0,"selected","") }>系统默认首页模板</option>
    #{foreach template in templ_home_templates }
      <option value='#{template.id }' #{iif(channel.templateId == template.id,"selected","") }>#{template.name }#{if(template.isDefault)}（默认）#{/if }</option>
    #{/foreach }
  </select>
 </td>
</tr>
#{/if }
<tr class='tdbg'>
 <td width='200' class='tdbg5'><strong>本频道的默认皮肤：</strong></td>
 <td>
  <select name='SkinId' id='SkinId'>
   <option value='0'  #{iif(channel.skinId == 0, "selected", "") }>使用系统的默认皮肤</option>
   #{foreach skin in skin_list }
   <option value='#{skin.id }' #{iif(channel.skinId == skin.id, "selected", "") }>#{skin.name }#{if(skin.isDefault)}（默认）#{/if }</option>
   #{/foreach }
  </select>
 </td>
</tr>
</table>
</pub:template>



<pub:template name="channel_perm_template">
<table width='100%' border='0' align='center' cellpadding='2' cellspacing='1' bgcolor='white'>
<tr>
 <td>
  <h3>
    频道的权限设置
  </h3>
 </td>
</tr>
<tr>
 <td>
  TODO: 开放、认证设置；浏览权限设置；审核权限设置。
 </td>
</tr>
</table>
</pub:template>

<pub:template name="channel_upload_template">
<table width='100%' border='0' align='center' cellpadding='2' cellspacing='1' bgcolor='white'>
<tr class='tdbg'>
 <td width='200' class='tdbg5'><strong>是否允许在本频道上传文件：</strong></td>
 <td><input name='EnableUploadFile' type='radio' value='true' #{if(channel.enableUploadFile) }checked#{endif }>是
  &nbsp;&nbsp;&nbsp;&nbsp;<input name='EnableUploadFile' type='radio' value='false' #{if(!channel.enableUploadFile) }checked#{endif }>否</td>
</tr>
<tr class='tdbg'>
 <td width='200' class='tdbg5'><strong>上传文件的保存目录：</strong><br><font color='red'>你可以定期或不定期的更改上传目录，以防其他网站盗链</font></td>
 <td><input name='UploadDir' type='text' id='UploadDir' value='#{channel.uploadDir }' size='20' maxlength='20'>&nbsp;&nbsp;<font color='red'>只能是英文和数字，不能带空格或“\”、“/”等符号。</font></td>
</tr>
<tr class='tdbg'>
 <td width='200' class='tdbg5'><strong>允许上传的最大文件大小：</strong></td>
 <td><input name='MaxFileSize' type='text' id='MaxFileSize' value='#{channel.maxFileSize }' size='10' maxlength='10'> KB&nbsp;&nbsp;&nbsp;&nbsp;<font color=blue>提示：1 KB = 1024 Byte，1 MB = 1024 KB</font>
 </td>
</tr>
<tr class='tdbg'>
 <td width='200' class='tdbg5'><strong>允许上传的文件类型：</strong><br>多种文件类型之间以“|”分隔</td>
 <td>
  <textarea name='UpFileType' id='UpFileType' rows="4" cols="40" type="_moz">#{channel.upFileType }</textarea><br/>
  常用文件格式：<br/>
  图片文件：jpg,gif,bmp,png,tif,psd等。<br/>    
  音频文件：mp3,wma,wav,midi,asf等。<br/>
  视频文件：avi,asf,mpg,mpeg,rm,rmvb等。<br/>
  其他文件：exe(可执行文件);rar,zip,gz,tar(压缩文件);doc(WORD文档);txt(文本文件);swf(Flash文件)。
 </td>
</tr>
</table>
</pub:template>



<pub:template name="channel_generate_template">
<table width='100%' border='0' align='center' cellpadding='2' cellspacing='1' bgcolor='white'>
<tr class='tdbg'>
 <td width='200' class='tdbg5'>
  <strong><font color=red>生成HTML方式：</font></strong>
  <br>请谨慎选择！以后在每一次更改生成方式前，你最好先删除所有以前生成的文件，然后在保存频道参数后再重新生成所有文件。
 </td>
 <td>
  <input name='UseCreateHTML' type='radio' value='0' #{if(channel.useCreateHTML == 0) }checked#{/if}>不生成&nbsp;&nbsp;&nbsp;&nbsp;
  <font color='blue'>当频道中的信息量比较少（≤1000）时，可以选用此种方式，此方式最耗费系统资源。</font>
  <br>
  <input type='radio' name='UseCreateHTML' value='1' #{if(channel.useCreateHTML == 1) }checked#{/if}>全部生成&nbsp;&nbsp;&nbsp;&nbsp;
  <font color='blue'>此方式在生成后将最节省系统资源，但当信息量大时，生成过程将比较长。</font>
  <br>
  <input type='radio' name='UseCreateHTML' value='2' #{if(channel.useCreateHTML == 2) }checked#{/if}>首页和内容页为HTML，栏目和专题页为JSP
  <br>
  <input type='radio' name='UseCreateHTML' value='3' #{if(channel.useCreateHTML == 3) }checked#{/if}>首页、内容页、栏目和专题的首页为HTML，其他页为JSP
  <font color='red'><b>（推荐）</b>
  </font>
 </td>
</tr>
<tr class='tdbg'>
 <td width='200' class='tdbg5'>
  <strong><font color=red>栏目、专题列表更新页数：</font></strong>
  <br>
  添加内容后自动更新的栏目及专题列表页数。
 </td>
 <td>
  <input name='UpdatePages' type='text' id='UpdatePages' value='#{channel.updatePages }' size='5' maxlength='5' >页
  <font color='#FF0000'>*</font>&nbsp;&nbsp;
  <font color='blue'>如：更新页数设为3，则每次自动更新前三页，第4页以后的分页为固定生成的页面，当新增内容数超过一页，则再生成一个固定页面，在总记录数不是每页记录数的整数倍时，交叉页（第3、4页）会有部分记录重复。</font>
 </td>
</tr>
<tr>
 <td colspan='2'>
  <font color='red'><b>以下参数仅当“生成HTML方式”设为后三者时才有效。<br>请谨慎选择！以后在每一次更改以下参数前，你最好先删除所有以前生成的文件，然后在保存参数设置后再重新生成所有文件。</b>
  </font>
 </td>
</tr>
<tr class='tdbg'>
 <td width='200' class='tdbg5'>
  <strong><font color=red>自动生成HTML时的生成方式：</font></strong>
  <br>
  添加/修改信息时，系统可以自动生成有关页面文件，请在这里选择自动生成时的方式。
 </td>
 <td>
  <input name='AutoCreateType' type='radio' value='0' #{if(channel.autoCreateType == 0) }checked#{/if}>
  不自动生成，由管理员手工生成相关页面
  <br>
  <input name='AutoCreateType' type='radio' value='1' #{if(channel.autoCreateType == 1) }checked#{/if}>
  自动生成全部所需页面
  <br>
  &nbsp;&nbsp;&nbsp;&nbsp;
  <font color='blue'>当“生成HTML方式”设置为“全部生成”时，将生成所有页面；当“生成HTML方式”设置为后两种时，会根据设置的选项生成有关页面。</font>
  <br>
  <input name='AutoCreateType' type='radio' value='2' #{if(channel.autoCreateType == 2) }checked#{/if}>
  自动生成部分所需页面
  <br>
  &nbsp;&nbsp;&nbsp;&nbsp;
  <font color='blue'>仅当“生成HTML方式”设置为“全部生成”时方有效。此方式只生成首页、内容页、栏目和专题的首页，其他页面由管理员手工生成。</font>
  <br>
 </td>
</tr>
<tr class='tdbg'>
 <td width='200' class='tdbg5'>
  <strong><font color=red>栏目列表文件的存放位置：</font></strong>
 </td>
 <td>
  <input name='ListFileType' type='radio' value='0' #{if(channel.listFileType == 0) }checked#{/if}>
  列表文件分目录保存在所属栏目的文件夹中
  <br>
  &nbsp;&nbsp;&nbsp;&nbsp;
  <font color='blue'>例：Article/JSP/JiChu/index.html（栏目首页）<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Article/JSP/JiChu/List_2.html（第二页）</font>
  <br>
  <input name='ListFileType' type='radio' value='1' #{if(channel.listFileType == 1) }checked#{/if}>
  列表文件统一保存在指定的“List”文件夹中
  <br>
  &nbsp;&nbsp;&nbsp;&nbsp;
  <font color='blue'>例：Article/List/List_236.html（栏目首页）<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Article/List/List_236_2.html（第二页）</font>
  <br>
  <input name='ListFileType' type='radio' value='2' #{if(channel.listFileType == 2) }checked#{/if}>
  列表文件统一保存在频道文件夹中
  <br>
  &nbsp;&nbsp;&nbsp;&nbsp;
  <font color='blue'>例：Article/List_236.html（栏目首页）<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Article/List_236_2.html（第二页）</font>
  <br>
 </td>
</tr>
<tr class='tdbg'>
 <td width='200' class='tdbg5'>
  <strong><font color=red>目录结构方式：</font>
  </strong>
 </td>
 <td>
  <input name='StructureType' type='radio' value='0' #{if(channel.structureType == 0) }checked#{/if}>
  频道/大类/小类/月份/文件（栏目分级，再按月份保存）
  <br>
  &nbsp;&nbsp;&nbsp;&nbsp;
  <font color='blue'>例：Article/JSP/JiChu/200408/1368.html</font>
  <br>
  <input name='StructureType' type='radio' value='1' #{if(channel.structureType == 1) }checked#{/if}>
  频道/大类/小类/日期/文件（栏目分级，再按日期分，每天一个目录）
  <br>
  &nbsp;&nbsp;&nbsp;&nbsp;
  <font color='blue'>例：Article/JSP/JiChu/2004-08-25/1368.html</font>
  <br>
  <input name='StructureType' type='radio' value='2' #{if(channel.structureType == 2) }checked#{/if}>
  频道/大类/小类/文件（栏目分级，不再按月份）
  <br>
  &nbsp;&nbsp;&nbsp;&nbsp;
  <font color='blue'>例：Article/JSP/JiChu/1368.html</font>
  <br>
  <input name='StructureType' type='radio' value='3' #{if(channel.structureType == 3) }checked#{/if}>
  频道/栏目/月份/文件（栏目平级，再按月份保存）
  <br>
  &nbsp;&nbsp;&nbsp;&nbsp;
  <font color='blue'>例：Article/JiChu/200408/1368.html</font>
  <br>
  <input name='StructureType' type='radio' value='4' #{if(channel.structureType == 4) }checked#{/if}>
  频道/栏目/日期/文件（栏目平级，再按日期分，每天一个目录）
  <br>
  &nbsp;&nbsp;&nbsp;&nbsp;
  <font color='blue'>例：Article/JiChu/2004-08-25/1368.html</font>
  <br>
  <input name='StructureType' type='radio' value='5' #{if(channel.structureType == 5) }checked#{/if}>
  频道/栏目/文件（栏目平级，不再按月份）
  <br>
  &nbsp;&nbsp;&nbsp;&nbsp;
  <font color='blue'>例：Article/JiChu/1368.html</font>
  <br>
  <input name='StructureType' type='radio' value='6' #{if(channel.structureType == 6) }checked#{/if}>
  频道/文件（直接放在频道目录中）
  <br>
  &nbsp;&nbsp;&nbsp;&nbsp;
  <font color='blue'>例：Article/1368.html</font>
  <br>
  <input name='StructureType' type='radio' value='7' #{if(channel.structureType == 7) }checked#{/if}>
  频道/HTML/文件（直接放在指定的“HTML”文件夹中）
  <br>
  &nbsp;&nbsp;&nbsp;&nbsp;
  <font color='blue'>例：Article/HTML/1368.html</font>
  <br>
  <input name='StructureType' type='radio' value='8' #{if(channel.structureType == 8) }checked#{/if}>
  频道/年份/文件（直接按年份保存，每年一个目录）
  <br>
  &nbsp;&nbsp;&nbsp;&nbsp;
  <font color='blue'>例：Article/2004/1368.html</font>
  <br>
  <input name='StructureType' type='radio' value='9' #{if(channel.structureType == 9) }checked#{/if}>
  频道/月份/文件（直接按月份保存，每月一个目录）
  <br>
  &nbsp;&nbsp;&nbsp;&nbsp;
  <font color='blue'>例：Article/200408/1368.html</font>
  <br>
  <input name='StructureType' type='radio' value='10' #{if(channel.structureType == 10) }checked#{/if}>
  频道/日期/文件（直接按日期保存，每天一个目录）
  <br>
  &nbsp;&nbsp;&nbsp;&nbsp;
  <font color='blue'>例：Article/2004-08-25/1368.html</font>
  <br>
  <input name='StructureType' type='radio' value='11' #{if(channel.structureType == 11) }checked#{/if}>
  频道/年份/月份/文件（先按年份，再按月份保存，每月一个目录）
  <br>
  &nbsp;&nbsp;&nbsp;&nbsp;
  <font color='blue'>例：Article/2004/200408/1368.html</font>
  <br>
  <input name='StructureType' type='radio' value='12' #{if(channel.structureType == 12) }checked#{/if}>
  频道/年份/日期/文件（先按年份，再按日期分，每天一个目录）
  <br>
  &nbsp;&nbsp;&nbsp;&nbsp;
  <font color='blue'>例：Article/2004/2004-08-25/1368.html</font>
  <br>
  <input name='StructureType' type='radio' value='13' #{if(channel.structureType == 13) }checked#{/if}>
  频道/月份/日期/文件（先按月份，再按日期分，每天一个目录）
  <br>
  &nbsp;&nbsp;&nbsp;&nbsp;
  <font color='blue'>例：Article/200408/2004-08-25/1368.html</font>
  <br>
  <input name='StructureType' type='radio' value='14' #{if(channel.structureType == 14) }checked#{/if}>
  频道/年份/月份/日期/文件（先按年份，再按日期分，每天一个目录）
  <br>
  &nbsp;&nbsp;&nbsp;&nbsp;
  <font color='blue'>例：Article/2004/200408/2004-08-25/1368.html</font>
 </td>
</tr>
<tr class='tdbg'>
 <td width='200' class='tdbg5'>
  <strong><font color=red>内容页文件的命名方式：</font>
  </strong>
 </td>
 <td>
  <input name='FileNameType' type='radio' value='0' #{if(channel.fileNameType == 0) }checked#{/if}>
  文章ID.html&nbsp;&nbsp;&nbsp;&nbsp;
  <font color='blue'>例：1358.html</font>
  <br>
  <input name='FileNameType' type='radio' value='1' #{if(channel.fileNameType == 1) }checked#{/if}>
  更新时间.html&nbsp;&nbsp;&nbsp;&nbsp;
  <font color='blue'>例：20040828112308.html</font>
  <br>
  <input name='FileNameType' type='radio' value='2' #{if(channel.fileNameType == 2) }checked#{/if}>
  频道英文名_文章ID.html&nbsp;&nbsp;&nbsp;&nbsp;
  <font color='blue'>例：Article_1358.html</font>
  <br>
  <input name='FileNameType' type='radio' value='3' #{if(channel.fileNameType == 3) }checked#{/if}>
  频道英文名_更新时间.html&nbsp;&nbsp;&nbsp;&nbsp;
  <font color='blue'>例：Article_20040828112308.html</font>
  <br>
  <input name='FileNameType' type='radio' value='4' #{if(channel.fileNameType == 4) }checked#{/if}>
  更新时间_ID.html&nbsp;&nbsp;&nbsp;&nbsp;
  <font color='blue'>例：20040828112308_1358.html</font>
  <br>
  <input name='FileNameType' type='radio' value='5' #{if(channel.fileNameType == 5) }checked#{/if}>
  频道英文名_更新时间_ID.html&nbsp;&nbsp;&nbsp;&nbsp;
  <font color='blue'>例：Article_20040828112308_1358.html</font>
 </td>
</tr>
<tr class='tdbg'>
 <td width='200' class='tdbg5'>
  <strong><font color=red>频道首页的扩展名：</font>
  </strong>
 </td>
 <td>
  <input name='FileExtIndex' type='radio' value='0' #{if(channel.fileExtIndex == 0) }checked#{/if}>.html&nbsp;&nbsp;&nbsp;&nbsp;
  <input name='FileExtIndex' type='radio' value='1' #{if(channel.fileExtIndex == 1) }checked#{/if}>.htm&nbsp;&nbsp;&nbsp;&nbsp;
  <input name='FileExtIndex' type='radio' value='2' #{if(channel.fileExtIndex == 2) }checked#{/if}>.shtml&nbsp;&nbsp;&nbsp;&nbsp;
  <input name='FileExtIndex' type='radio' value='3' #{if(channel.fileExtIndex == 3) }checked#{/if}>.shtm&nbsp;&nbsp;&nbsp;&nbsp;
  <input name='FileExtIndex' type='radio' value='4' #{if(channel.fileExtIndex == 4) }checked#{/if}>.jsp
 </td>
</tr>
<tr class='tdbg'>
 <td width='200' class='tdbg5'>
  <strong><font color=red>栏目页、专题页的扩展名：</font>
  </strong>
 </td>
 <td>
  <input name='FileExtList' type='radio' value='0' #{if(channel.fileExtList == 0) }checked#{/if}>.html&nbsp;&nbsp;&nbsp;&nbsp;
  <input name='FileExtList' type='radio' value='1' #{if(channel.fileExtList == 1) }checked#{/if}>.htm&nbsp;&nbsp;&nbsp;&nbsp;
  <input name='FileExtList' type='radio' value='2' #{if(channel.fileExtList == 2) }checked#{/if}>.shtml&nbsp;&nbsp;&nbsp;&nbsp;
  <input name='FileExtList' type='radio' value='3' #{if(channel.fileExtList == 3) }checked#{/if}>.shtm&nbsp;&nbsp;&nbsp;&nbsp;
  <input name='FileExtList' type='radio' value='4' #{if(channel.fileExtList == 4) }checked#{/if}>.jsp
 </td>
</tr>
<tr class='tdbg'>
 <td width='200' class='tdbg5'>
  <strong><font color=red>内容页的扩展名：</font>
  </strong>
 </td>
 <td>
  <input name='FileExtItem' type='radio' value='0' #{if(channel.fileExtItem == 0) }checked#{/if}>.html&nbsp;&nbsp;&nbsp;&nbsp;
  <input name='FileExtItem' type='radio' value='1' #{if(channel.fileExtItem == 1) }checked#{/if}>.htm&nbsp;&nbsp;&nbsp;&nbsp;
  <input name='FileExtItem' type='radio' value='2' #{if(channel.fileExtItem == 2) }checked#{/if}>.shtml&nbsp;&nbsp;&nbsp;&nbsp;
  <input name='FileExtItem' type='radio' value='3' #{if(channel.fileExtItem == 3) }checked#{/if}>.shtm&nbsp;&nbsp;&nbsp;&nbsp;
  <input name='FileExtItem' type='radio' value='4' #{if(channel.fileExtItem == 4) }checked#{/if}>.jsp
 </td>
</tr>
</table>
</pub:template>



<pub:template name="test_info_template">
<table width='100%' border='0' align='center' cellpadding='2' cellspacing='1'>
<tr>
 <td>
  <h3>
   测试用的属性页
  </h3>
 </td>
</tr>
</table>
</pub:template>

<pub:template name="form_button">
<table width='100%' border='0' align='center'>
 <tr>
  <td colspan='2' align='center'>
   <br />
   <font color='red'>在更改频道有关参数前，你最好先删除所有以前生成的文件，更改参数后再重新生成所有文件。</font>
   <br />
   <br />
   <input name='channelId' type='hidden' id='channelId' value='#{channel.id}' />
   <input name='command' type='hidden' id='command' value='save' />
   <input name='Submit' type='submit' id='Submit' value='#{iif(channel.id==0," 添 加 "," 保存修改结果 ") }' />
   &nbsp;
   <input name='Cancel' type='button' id='Cancel' value=' 取 消 '
     onclick="window.location.href='admin_channel_list.jsp'"
    style='cursor:hand;' />
  </td>
 </tr>
</table>
</pub:template>


<pub:template name="validate_js">
<script type="text/javascript">
 function CheckForm(){
   if(document.myform.Name.value==''){
     showTab('channel_prop');
     alert('请输入频道名称！');
     document.myform.Name.focus();
     return false;
   }
   if(document.myform.ChannelType[1].checked==true){
     if(document.myform.ChannelDir.value==''){
       showTab('channel_prop');
       alert('请输入频道目录！');
       document.myform.ChannelDir.focus();
       return false;
     }
     if(document.myform.ItemName.value==''){
       showTab('channel_prop');
       alert('请输入项目名称！');
       document.myform.ItemName.focus();
       return false;
     }
   }
   else{
     if(document.myform.ChannelUrl.value==''){
       showTab('channel_prop');
       alert('请输入频道的链接地址！');
       document.myform.ChannelUrl.focus();         
       return false;
     }
   }
   return true;
 }   
</script>
</pub:template>

</pub:declare>

<pub:process_template name="main" />

 </body>
</html>
