<%@ page language="java" contentType="text/html; charset=gb2312" pageEncoding="UTF-8"
%><!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>频道管理</title>
<link href='help.css' rel='stylesheet' type='text/css' />
</head>
<body>

<h2>频道管理</h2>

<p>在发布系统中，频道是指系统某一功能模块的集合，如文章模块、下载模块、图片模块等。
您可以管理网站的各个频道的功能模块，如文章、下载、图片等功能频道，以建设不同的网站内容。
</p>

<p>本系统有任意添加网站频道的功能，您利用系统提供的文章、下载、图片功能模块，复制出任意多个具有相同功能的频道。
您也可以随时将某些频道禁用，不显示在网站前台。
</p>

<h3>频道属性</h3>

<p>频道可分为系统内部频道与外部频道二类。
 系统的一些重要功能，如生成HTML功能、频道的审核功能、上传文件类型、顶部导航栏每行显示的栏目数、底部栏目导航的显示方式等都在此进行设置。
</p>

<h4>修改频道属性</h4>

<p>1.在后台的“网站管理导航”中，单击“系统设置”-&gt;“网站频道管理”，右栏出现“频道管理”的界面。
</p>
<center><img src='images/manage_channel_menu.jpg' border='1' /></center>
<center><img src='images/manage_channel_list.jpg' border='1' /></center>

<p>2.要修改某频道的属性，请单击该频道的名称或该频道“操作”列中的“修改”，即可对频道的各个参数进行设置。
您也可以对本频道进行“禁用”、“删除”的操作，请注意：只有新添加的频道才能执行“删除”的操作，默认提供的频道因为具有默认复制的相关文件只能禁用而不能执行“删除”操作。
</p>
<p>如果禁用了本频道，则本频道不会在前台显示。</p>
<p>3.单击“保存修改结果”按钮，返回频道管理界面
</p>

<h4>添加新频道</h4>

<p>1.在后台的“网站管理导航”中，单击“系统设置”-&gt;“网站频道管理”->“添加新频道”，右栏出现“添加新频道”的界面。
</p>
<center><img src='images/manage_channel_add.jpg' border='1' /></center>

<p>2.填写相关参数：
<br/>如果添加的是外部频道（指链接到本系统以外地址的频道），则链接地址中写上绝对地址，如/news/index.jsp或http://***.***.***/news/index.jsp。
<br/>如果添加的是系统内部频道，请填写相关参数（参数说明请参阅下一节“频道参数说明”）。
</p>

<p>！无论添加的是内部频道还是外部频道，请慎重选择频道类型。频道一旦添加后就不能再更改频道类型，您可以删除添加的频道重新添加一个新的频道，以重新选择频道类型。
</p>

<p>3.填写好相关频道后单击“添加”按钮，以添加新频道。系统返回频道管理界面。
</p>

<h4>频道参数说明</h4>
<ul>
<li>频道名称：填写显示在前台频道标签处的频道名称。</li>
<li>频道图片：以图片方式显示频道的名称。如果在这里填写了图片地址，则前台优先显示频道图片。</li>
<li>频道说明：填写鼠标移至频道名称上时将显示设定的说明文字（不支持HTML）。</li>
<li>频道类型：系统提供外部频道与内部频道二种方式。
 如果添加的是外部频道（指链接到本系统以外地址的频道），则链接地址中写上绝对地址，如/news/index.jsp或http://***/news/index.jsp。如果添加的是系统内部频道，请参考下面的说明填写相关参数。
 <br/> 请慎重选择频道类型。频道一旦添加后就不能再更改频道类型。您可以禁用或删除后重新添加。</li>
<li>频道使用的功能模块：系统提供文章、下载、图片和商城的功能模块。在添加新频道只提供文章、下载和图片功能频道。</li>
<li>频道目录：填写本频道内的文件存放位置文件夹的英文名，名称可以自定义。请注意一旦填写便不可以修改。频道目录名只能是英文，不能带空格或“\”、“/”等符号。如：News、Article、Soft。</li>
<li>频道链接地址（子域名）：如果您要在前台将此频道做为主站的一个独立子站点来访问，请输入完整的网址（如：http://news.powereasy.net）；否则，请保持为空。注意不能带目录。如果要启用此功能，必须在“网站选项”中将“链接地址方式”改为“绝对路径”。</li>
<li>项目名称：显示在左侧网站导航管理中本频道简称。如：频道名称为“网络学院”，其项目名称可以为“文章”或“教程”。左侧网站导航管理中显示为“添加文章（或教程）”。</li>
<li>项目单位：填写本频道中项目的单位。如：“篇”、“条”、“个”。</li>
<li>打开方式：可选择在原窗口或新窗口打开链接窗口的方式。</li>
<li>禁用本频道：若选择了“否”，则禁用了本频道，频道的功能都被禁用，频道名称也不在前台显示。您可以随时禁用暂时用不到的功能频道，也可以随时开启禁用的频道。</li>
</ul>

</body>
</html>
