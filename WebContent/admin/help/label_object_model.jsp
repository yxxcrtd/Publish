<%@ page language="java" contentType="text/html; charset=gb2312" pageEncoding="UTF-8"
%><!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>发布系统中对象模型简介</title>
<link href='help.css' rel='stylesheet' type='text/css' />
</head>
<body>

<h2>发布系统中对象模型简介</h2>

<p>
  中教育星发布系统中站点、频道、栏目、专题、文章、图片、下载等都是对象，这些对象在相应的页面中可以直接访问，
  访问是基于安全的 java bean 模式，对内部系统不会造成任何更改。这些对象模型简表如下：
</p>

<table id='label_table' width="100%" border="0" cellspacing="1" cellpadding="2" align='center'>
  <thead>
  <tr>
    <th width="180">对象名字</th>
    <th width='*'>简要说明</th>
  </tr>
  </thead>
  <tbody>
  <tr>
    <td>站点 site</td>
    <td>
      <p>站点对象表示整个站点的信息及其配置，其在模板中使用的标准名称为 <span class='object'>site</span> (注意大小写，是完全小写的 site)。
      站点对象在任何页面中都存在，因此站点对象及其属性在任何页面都能够访问到。</p>
      <p>在有 site 对象的任何模板，标签对之中都能使用<a href='label_site.jsp'>站点标签</a>，如 #{SiteName }。</p>
    </td>
  </tr>
  <tr>
    <td>频道 channel</td>
    <td>
     <p>频道对象表示一个站点中频道的信息及其配置，其在模板中使用的标准名称为 <span class='object'>channel</span> (同样注意大小写)。</p>
     <p>频道对象存在于任何有频道的页面，如频道主页、频道下栏目页、频道下专题页（全站专题页中没有频道）、频道下文章图片下载等项目页，
     以及在某些特定标签如 #{Channel id='nnn'}...#{/Channel }对之内，存在有 channel 对象变量的地方都可以使用。
     </p>
     <p>
       在有频道 channel 对象的任何模板，标签对之中都能使用<a href='label_channel.jsp'>频道标签</a>，如 #{ChannelName }, #{ChannelUrl } 等。
       当然直接写 #{channel.name }, #{channel.pageUrl } 速度更快一些。
     </p>
    </td>
  </tr>
  <tr>
    <td>栏目 column</td>
    <td>
     <p>栏目对象表示一个频道中栏目的信息及其配置，其在模板中使用的标准名称为 <span class='object'>column</span>。 </p>
     <p>栏目对象存在于栏目页面、频道下文章、图片、下载等页面中，以及某些特定标签如 #{ShowColumns }, 
       #{ShowChildColumn } 中。在标签中能够使用栏目的地方请参见这些标签的详细说明。</p>
     <p>
       在有栏目 column 对象的任何模板、标签对之中都能使用<a href='label_column.jsp'>栏目标签</a>，如 #{ColumnUrl }, #{ShowChildColumns } 等。
     </p>
    </td>
  </tr>
  <tr>
    <td>通用项目 item</td>
    <td>
      <p>通用项目指文章、图片、下载等对象通用的信息组成的对象，其在模板中使用的标准名称为 <span class='object'>item</span>。</p>
      <p>通用项目存在于文章、图片、下载、搜索结果等页面中，以及某些特定标签如 #{ShowArticleList } ... #{/ShowArticleList }, 
        #{ShowSiteSearchResult }... #{/ShowSiteSearchResult } 对之中。
        在有文章、图片、下载对象的地方一定有通用项目对象。</p>
      <p>在实际实现中，文章、图片、下载对象都是以类继承的方式继承自 item 对象，因而文章、图片、下载对象具有通用项目的所有功能。</p>
    </td>
  </tr>
  <tr>
    <td>文章 article</td>
    <td>
      <p>文章对象指表示文章频道中文章信息的对象，其在模板中使用的标准名称为 <span class='object'>article</span>。</p>
      <p>文章对象存在于文章频道中文章显示页面，以及某些特定标签如 #{FlashNews }, #{ShowChildColumnItems } 等标签对中。
      </p>
      <p>在有文章对象的任何模板、标签对中都能使用<a href='label_article.jsp'>文章标签</a>，如 #{ArticleTitle } 等。 </p>
    </td>
  </tr>
  <tr>
    <td>图片 photo</td>
    <td>
      <p>图片对象指表示图片频道中图片信息的对象，其在模板中使用的标准名称为 <span class='object'>photo</span>。</p>
      <p>图片对象存在于图片频道中图片显示页面，以及某些特定标签如 #{ShowPhotoList } 等标签对中。
      </p>
      <p>在有图片对象的任何模板、标签对中都能使用<a href='label_photo.jsp'>图片标签</a>，如 #{PhotoUrl } 等。 </p>
    </td>
  </tr>
  <tr>
    <td>下载 soft</td>
    <td>
      <p>下载对象指表示下载频道中下载信息的对象，其在模板中使用的标准名称为 <span class='object'>soft</span>。</p>
      <p>下载对象存在于下载频道中下载显示页面，以及某些特定标签如 #{ShowSoftList } 等标签对中。
      </p>
      <p>在有下载对象的任何模板、标签对中都能使用<a href='label_soft.jsp'>下载标签</a>，如 #{SoftVersion } 等。 </p>
    </td>
  </tr>
  <tr>
    <td>专题 special</td>
    <td>
      <p>专题对象表示站点或频道下一个专题信息的对象，其在模板中使用的标准名称为 <span class='object'>special</span>。</p>
      <p>专题对象存在于全站专题、频道专题显示页面中，以及某些特定标签如 #{ShowSpecialList } 等标签对中。</p>
      <p>在有专题对象的任何模板页、标签对中都能使用<a href='label_special.jsp'>专题标签</a>，如 #{SpecialName } 等。</p>
    </td>
  </tr>
  <tr>
    <td>作者 author</td>
    <td>
      TODO:
    </td>
  </tr>
  <tr>
    <td>用户 user</td>
    <td>
      TODO:
    </td>
  </tr>
  <tr>
    <td>公告 announce</td>
    <td>
      TODO:
    </td>
  </tr>
  <tr>
    <td>搜索 search</td>
    <td>
      TODO:
    </td>
  </tr>
  <tr>
    <td>特殊对象 this</td>
    <td>
     <p>
      <span class='object'>this</span> 特殊对象指当前页面哪一个对象是主对象，如在网站主页，this 就是 site 对象。
      在频道主页中，this 就是 channel 对象，同样在栏目页面 this = column，文章页面 this = article。
     </p>
    </td>
  </tr>
  </tbody>
</table>

<p>下表给出在不同的页面中可以使用的标准发布系统模型对象的简表：</p>
<table id='label_table' width="100%" border="0" cellspacing="1" cellpadding="2" align='center'>
  <thead>
  <tr>
    <th width="180">页面</th>
    <th width='*'>简要说明</th>
  </tr>
  </thead>
  <tbody>
  <tr>
    <td>网站主页</td>
    <td>
      <p>
       网站主页标准名字是 index.jsp，其可使用的模型对象为：<span class='object'>site</span>, 
         <span class='object'>user</span>, <span class='object'>this</span> = site.
      </p>
    </td>
  </tr>
  <tr>
    <td>频道主页</td>
    <td>
      <p>
       频道主页标准名字是 channelDir/index.jsp，其可使用的模型对象为：<span class='object'>site</span>, 
        <span class='object'>channel</span>, <span class='object'>user</span>, 
        <span class='object'>this</span> = channel.
      </p>
    </td>
  </tr>
  <tr>
    <td>栏目主页</td>
    <td>
      <p>
       栏目主页标准名字是 channelDir/showColumn.jsp，其可使用的模型对象为：<span class='object'>site</span>, 
        <span class='object'>channel</span>, <span class='object'>column</span>, 
        <span class='object'>user</span>, <span class='object'>this</span> = column.
      </p>
    </td>
  </tr>
  <tr>
    <td>文章页面</td>
    <td>
      <p>
       文章页面标准名字是 channelDir/showArticle.jsp，其可使用的模型对象为：<span class='object'>site</span>, 
        <span class='object'>channel</span>, <span class='object'>column</span>, 
        <span class='object'>article</span>, <span class='object'>item</span>, 
        <span class='object'>user</span>, <span class='object'>this</span> = article.
      </p>
    </td>
  </tr>
  <tr>
    <td>图片页面</td>
    <td>
      <p>
       图片页面标准名字是 channelDir/showPhoto.jsp，其可使用的模型对象为：<span class='object'>site</span>, 
        <span class='object'>channel</span>, <span class='object'>column</span>, 
        <span class='object'>photo</span>, <span class='object'>item</span>, 
        <span class='object'>user</span>, <span class='object'>this</span> = photo.
      </p>
    </td>
  </tr>
  <tr>
    <td>下载页面</td>
    <td>
      <p>
       下载页面标准名字是 channelDir/showSoft.jsp，其可使用的模型对象为：<span class='object'>site</span>, 
        <span class='object'>channel</span>, <span class='object'>column</span>, 
        <span class='object'>soft</span>, <span class='object'>item</span>, 
        <span class='object'>user</span>, <span class='object'>this</span> = soft.
      </p>
    </td>
  </tr>
  <tr>
    <td>专题页面</td>
    <td>
      <p>
       专题页面标准名字是 showSpecial.jsp(全站专题时), channelDir/showSoft.jsp(频道专题时)，
          其可使用的模型对象为：<span class='object'>site</span>, 
        <span class='object'>channel</span> (频道专题的时候有), <span class='object'>special</span>, 
        <span class='object'>user</span>, <span class='object'>this</span> = special.
      </p>
    </td>
  </tr>
  </tbody>
</table>

<p>
  下面详细给出这些对象模型的说明：
</p>

<table id='label_table' cellSpacing=1 cellPadding=2 width="100%" border=0 align='center'>
  <tr>
    <td vAlign="top">
    <p><img src="images/article_elite.gif" alt="推荐文章">&nbsp;<a
      class="listA" href="object_model_site.jsp" title=""
      target="_blank">站点 site 对象介绍</a><br />
      
      <img src="images/article_elite.gif" alt="推荐文章">&nbsp;<a
      class="listA" href="object_model_channel.jsp" title=""
      target="_blank">频道 channel 对象介绍</a><br />

      <img src="images/article_elite.gif" alt="推荐文章">&nbsp;<a
      class="listA" href="object_model_column.jsp" title=""
      target="_blank">栏目 column 对象介绍</a><br />

      <img src="images/article_elite.gif" alt="推荐文章">&nbsp;<a
      class="listA" href="object_model_item.jsp" title=""
      target="_blank">通用项目 item 对象介绍</a><br />

      <img src="images/article_elite.gif" alt="推荐文章">&nbsp;<a
      class="listA" href="object_model_article.jsp" title=""
      target="_blank">文章 article 对象介绍</a><br />

      <img src="images/article_elite.gif" alt="推荐文章">&nbsp;<a
      class="listA" href="object_model_photo.jsp" title=""
      target="_blank">图片 photo 对象介绍</a><br />

      <img src="images/article_elite.gif" alt="推荐文章">&nbsp;<a
      class="listA" href="object_model_soft.jsp" title=""
      target="_blank">软件 soft 对象介绍</a><br />



    </p>
    </td>
  </tr>
</table>

</body>
</html>
