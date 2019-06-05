<%@ page language="java" contentType="text/html; charset=gb2312" pageEncoding="UTF-8"
%><!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>发布系统文章标签</title>
<link href='help.css' rel='stylesheet' type='text/css' />
</head>
<body>

<h2>发布系统文章标签</h2>

<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="*" valign="top">
    <p>
    文章标签适用于文章显示页面，以及嵌有文章的其它标签之内，在没有文章对象的地方则不能使用。以下将详细说明各标签的作用：
    </p>
    
    <!-- ArticleID -->
    <table id='label_table' cellSpacing=1 cellPadding=1 width="100%" border=0>
      <tr>
        <th width='160'>标签名：</th>
        <th>#{ArticleID}</th>
      </tr>
      <tr>
        <td>作用：</td>
        <td>显示文章标识。</td>
      </tr>
      <tr>
        <td>等效写法：</td>
        <td>#{article.id }, #{item.id }</td>
      </tr>
    </table>

    <!-- ArticleProperty -->
    <table id='label_table' cellSpacing=1 cellPadding=1 width="100%" border=0>
      <tr>
        <th width='160'>标签名：</th>
        <th>#{ArticleProperty}</th>
      </tr>
      <tr>
        <td>作用：</td>
        <td>显示文章的推荐、等级、精华属性。</td>
      </tr>
      <tr>
        <td>等效写法：</td>
        <td>使用文章的推荐、等级、精华等属性可以自己写出类似结果，参见这些属性的说明。</td>
      </tr>
    </table>
    
    <!-- ArticleTitle -->
    <table id='label_table' cellSpacing=1 cellPadding=1 width="100%" border=0>
      <tr>
        <th width='160'>标签名：</th>
        <th>#{ArticleTitle}</th>
      </tr>
      <tr>
        <td>作用：</td>
        <td>显示文章的标题。</td>
      </tr>
      <tr>
        <td>等效写法：</td>
        <td>#{article.title@html }</td>
      </tr>
    </table>
    
    <!-- ArticleAuthor -->
    <table id='label_table' cellSpacing=1 cellPadding=1 width="100%" border=0>
      <tr>
        <th width='160'>标签名：</th>
        <th>#{ArticleAuthor}</th>
      </tr>
      <tr>
        <td>作用：</td>
        <td>显示文章的作者。</td>
      </tr>
      <tr>
        <td>等效写法：</td>
        <td>#{article.author@html }</td>
      </tr>
    </table>

    <!-- ArticleUrl -->
    <table id='label_table' cellSpacing=1 cellPadding=1 width="100%" border=0>
      <tr>
        <th width='160'>标签名：</th>
        <th>#{ArticleUrl}</th>
      </tr>
      <tr>
        <td>作用：</td>
        <td>显示文章链接地址。</td>
      </tr>
      <tr>
        <td>等效写法：</td>
        <td>#{article.pageUrl }</td>
      </tr>
    </table>

    <!-- ArticleShortTitle -->
    <table id='label_table' cellSpacing=1 cellPadding=1 width="100%" border=0>
      <tr>
        <th width='160'>标签名：</th>
        <th>#{ArticleShortTitle}</th>
      </tr>
      <tr>
        <td>作用：</td>
        <td>显示文章简单标题。</td>
      </tr>
      <tr>
        <td>等效写法：</td>
        <td>#{article.shortTitle@html }</td>
      </tr>
    </table>

    <!-- ArticleSubheading -->
    <table id='label_table' cellSpacing=1 cellPadding=1 width="100%" border=0>
      <tr>
        <th width='160'>标签名：</th>
        <th>#{ArticleSubheading}</th>
      </tr>
      <tr>
        <td>作用：</td>
        <td>显示文章的副标题。</td>
      </tr>
      <tr>
        <td>等效写法：</td>
        <td>#{article.subheading@html }</td>
      </tr>
    </table>

    <!-- ArticleContent -->
    <table id='label_table' cellSpacing=1 cellPadding=1 width="100%" border=0>
      <tr>
        <th width='160'>标签名：</th>
        <th>#{ArticleContent}</th>
      </tr>
      <tr>
        <td>作用：</td>
        <td>显示文章的内容。</td>
      </tr>
      <tr>
        <td>等效写法：</td>
        <td>#{article.content }, 显示部分去掉 html 的： #{article.content@erase_html(200) }</td>
      </tr>
    </table>

    <!-- ArticleAction -->
    <table id='label_table' cellSpacing=1 cellPadding=1 width="100%" border=0>
      <tr>
        <th width='160'>标签名：</th>
        <th>#{ArticleAction}</th>
      </tr>
      <tr>
        <td>作用：</td>
        <td>显示文章的功能：告诉好友，打印，关闭，发表评论等。</td>
      </tr>
      <tr>
        <td>等效写法：</td>
        <td>根据文章的标识 id 、标题 title 等信息，可以自己生成必要的 html.</td>
      </tr>
    </table>

    <!-- ArticleSummary -->
    <table id='label_table' cellSpacing=1 cellPadding=1 width="100%" border=0>
      <tr>
        <th width='160'>标签名：</th>
        <th>#{ArticleSummary}</th>
      </tr>
      <tr>
        <td>作用：</td>
        <td> 显示文章综合信息。（类型、精华、置项、推荐、热门、星级）。</td>
      </tr>
      <tr>
        <td>等效写法：</td>
        <td>根据文章的标识 id 等属性能够自己生成必要的 html.</td>
      </tr>
    </table>

    <!-- PrevArticle -->
    <table id='label_table' cellSpacing=1 cellPadding=1 width="100%" border=0>
      <tr>
        <th width='160'>标签名：</th>
        <th>#{PrevArticle}</th>
      </tr>
      <tr>
        <td>作用：</td>
        <td>显示当前文章的前一文章。</td>
      </tr>
      <tr>
        <td>等效写法：</td>
        <td>#{article.prevArticle }, #{article.prevColumnArticle }</td>
      </tr>
    </table>

    <!-- NextArticle -->
    <table id='label_table' cellSpacing=1 cellPadding=1 width="100%" border=0>
      <tr>
        <th width='160'>标签名：</th>
        <th>#{NextArticle}</th>
      </tr>
      <tr>
        <td>作用：</td>
        <td>显示当前文章的后一文章。</td>
      </tr>
      <tr>
        <td>等效写法：</td>
        <td>#{article.nextArticle }, #{article.nextColumnArticle }</td>
      </tr>
    </table>













    
    </td>
  </tr>
</table>

</body>
</html>
    