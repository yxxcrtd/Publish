<%@ page language="java" contentType="text/html; charset=gb2312" pageEncoding="UTF-8"
%><!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>文章对象介绍</title>
<link href='help.css' rel='stylesheet' type='text/css' />
</head>
<body>

<h2>文章对象介绍</h2>

<p>文章对象 article 以 item 对象为基类，item 对象所具有的属性和支持的标签 article 也都全部支持。
</p>
<p>下表列出的是 article 除了具有 item 对象的属性之外还具有的属性，<a 
    href='object_model_item.jsp'>点击这里查看 item 对象具有的属性</a>，
    仍要记得 item 具有的所有属性 article 都全部具有。
</p>

<table id='label_table' width="100%" border="0" cellspacing="1" cellpadding="2" align='center'>
  <thead>
  <tr>
    <th width="240">属性名</th>
    <th width='*'>详细说明</th>
  </tr>
  </thead>
  <tbody>
  <!-- subheading -->
  <tr>
    <td width="240">subheading</td>
    <td>
    <p>获得文章的副标题。</p>
    <p>使用示例： #{article.subheading}, #{item.subheading@html }。</p>
    <p>标签 #{ArticleSubheading } 内部使用 item.subheading 属性实现。</p>
    </td>
  </tr>

  <!-- content -->
  <tr>
    <td><font color='blue'>content</font></td>
    <td>
    <p>获得文章的正文内容，文章的正文内容一般拥有较长内容，以及拥有格式的 html。</p>
    <p>使用示例： #{article.content }, #{item.content@erase_html(120) }。</p>
    <p>标签 #{ArticleContent } 内部使用 item.content 属性实现。</p>
    </td>
  </tr>
  
  <!-- includePic -->
  <tr>
    <td>includePic</td>
    <td>
    <p>获得文章的图文标志，当前设定为 0 - 无，1 - [图文]，2 - [组图]，3 - [推荐]，4 - [注意]。</p>
    <p>使用示例： #{article.includePic }。(未来可能会扩展此属性并实现对象化包装)</p>
    </td>
  </tr>

  <!-- defaultPicUrl -->
  <tr>
    <td><nobr>defaultPicUrl</nobr>, <nobr>defaultPicUrlAbs</nobr></td>
    <td>
    <p>defaultPicUrl 得到缺省图片的链接地址，该地址是基于文章所在频道目录相对保存的，一般不直接使用。<br/>
    defaultPicUrlAbs 得到经过绝对化处理的缺省图片地址，其链接地址正确的根据网站配置计算为合适的地址。</p>
    <p>使用示例： #{article.defaultPicUrl }, #{article.defaultPicUrl@uri(channel) }, 
      #{article.defaultPicUrlAbs }。
    (未来可能会扩展此属性并实现对象化包装)</p>
    </td>
  </tr>
  
  <!-- titleFontColor -->
  <tr>
    <td>titleFontColor</td>
    <td>
    <p>titleFontColor 得到标题的颜色。<br/>
    <p>使用示例： #{article.titleFontColor }</p>
    </td>
  </tr>
  
  <!-- titleFontType -->
  <tr>
    <td>titleFontType</td>
    <td>
    <p>titleFontType 得到标题字体类型, 0 表示正常体，1 表示粗体，2 表示斜体，3 表示粗体+斜体。<br/>
    <p>使用示例： #{article.titleFontType }</p>
    </td>
  </tr>
  
  <!-- prevArticle -->
  <tr>
    <td>prevArticle</td>
    <td>
      <p>获得该文章的前一篇文章。<br/>
      <p>使用示例： #{article.prevArticle}</p>
  <pre>
    #{PrevArticle } 
       // 注意：在 PrevArticle 标签对之内，article 对象被替换为当前文章对象的前一篇文章，在
       // #{/PrevArticle} 之后，article 重新恢复为原来的文章对象。
      #{article.pageUrl }, #{article.title }
    #{/PrevArticle }
  </pre>
      <p>注意：prevArticle, nextArticle 都会导致系统立刻加载该文章的前、后文章，所以在使用时要考虑到性能问题。</p>
    </td>
  </tr>

  <!-- nextArticle -->
  <tr>
    <td>nextArticle</td>
    <td>
    <p>获得该文章的前一篇文章。<br/>
    <p>使用示例： #{article.nextArticle}, 另参见 prevArticle 的例子。
    </p>
    </td>
  </tr>

  <!-- prevColumnArticle -->
  <tr>
    <td><nobr>prevColumnArticle</nobr>, <nobr>nextColumnArticle</nobr></td>
    <td>
    <p>获得该文章的同栏目的前一篇文章、后一篇文章。<br/>
    <p>使用示例： #{article.nextColumnArticle}, #{article.prevColumnArticle}
    </p>
    </td>
  </tr>

  
  </tbody>
</table>

</body>
</html>
