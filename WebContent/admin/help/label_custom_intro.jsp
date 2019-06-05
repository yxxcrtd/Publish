<%@ page language="java" contentType="text/html; charset=gb2312"
  pageEncoding="UTF-8"%><%@ taglib prefix="pub" uri="/WEB-INF/publish.tld"
%><!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>自定义标签概述</title>
<link href='help.css' rel='stylesheet' type='text/css' />
</head>
<body>

<h2>自定义标签概述</h2>

<p>发布系统中提供了大量的标签，如 #{ShowArticleList }, #{ShowChildColumn } 等，
 这些标签在各自的介绍中有说明，这个帮助主要介绍如何制作自定义的标签。
</p>

<p>为了做自定义标签，需要了解一些发布系统标签的原理。发布系统标签支持几种形式的使用：</p>
<p>1). <b>直接使用</b>。如 #{ShowArticleList channelId='current' columnId='current' itemNum='6' }<br/>
  这种形式使用起来最简单，只需要给出几个属性就能够得到一个文章列表。系统设计了一组属性来提供显示样式上面的变化，
  这些属性您可以参考标签的详细说明获得。一般在标签的制作对话框中可以方便的选择，并且在预览中能够看到最终产生的标签语法。
</p>
<p>2). <b>使用子标签</b>。如:</p>
 <pre>
  #{ShowArticleList channelId='current' itemNum='8' isCommend='true' }
   #{Repeater }
    &lt;li&gt; #{ArticleTitle }, #{ArticleUrl }, #{article.id }
   #{/Repeater }
  #{/ShowArticleList }
 </pre>
 
<p> 发布系统中大部分标签都支持使用子标签。使用子标签的时候， #{ShowArticleList } 和 #{/ShowArticleList }
  对之间的部分被称为子标签。在子标签中能够访问到主标签获取到的数据。</p>
 
<p> 以 #{ShowArticleList } 为例，在该标签对中能够使用的数据为 #{article_list } （别名： item_list)。
 这个数据内部是一个 List&lt;Article&gt;，也就是一组文章的集合。</p>
 
<p> 标准的 #{Repeater } #{/Repeater } 标签对能够循环这个文章集合，其等同于使用语法：
  #{foreach article in article_list } #{/foreach } </p>
 
<p> 在循环中能够使用文章对象的各种属性，例如： #{article.pageUrl } 等。能够使用的属性列表，请参见 
 <a href='object_model_item.jsp'>item 对象介绍</a> 和 <a href='object_model_article.jsp'>article 对象介绍</a>。</p>
 
<p> 在循环中还有一些特殊属性能够访问，它们被称之为内建属性(仅在循环中):</p>
<ul>
 <li> object@index - 得到对象在数组中的整数序号，也就是循环到第几个对象了。起始值 = 0。<br/>
    例子： #{article@index }</li>
 <li> object@is_first - 是不是循环的第一个对象，返回 true 是第一个对象，否则不是第一个。<br/>
    例子： #{if article.@is_first }第一个#{/if }</li>
 <li> object@is_last - 是不是循环的最后一个对象，返回 true 是最后一个对象，否则不是。<br/>
    例子： #{if article@is_last }最后一个#{/if }</li>    
</ul>

<p>这种方式最为灵活，可以在页面模板中方便的显示一组文章，并且按照自己定义的方式。
</p>

<p>3). <b>使用其它内建标签</b>。如：</p>
<p>#{ShowArticleList channelId=0 itemNum=10 template='显示文章列表的内建标签名字' }</p>
<p>以 #{ShowArticleList } 为例，系统内部使用 '.builtin.showarticlelist' 来显示文章列表，
  您可以修改定制一个新的内建标签，然后在使用 #{ShowArticleList } 的时候指定 template 参数。
  这样就引用了别的内建标签来进行输出。
</p>
<p>这种方式可以制作出符合自己需要的显示，并且可以方便地在多个页面模板中使用（和方式2相比）。
</p>

</body>
</html>
