<%@ page language="java" contentType="text/html; charset=gb2312" pageEncoding="UTF-8"
%><!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>ShowChildColumn -- 显示指定栏目的子栏目列表</title>
<link href='help.css' rel='stylesheet' type='text/css' />
</head>
<body>

<h2>ShowChildColumn -- 显示指定栏目的子栏目列表</h2>

<!-- ShowChildColumn -->
<table id='label_table' cellSpacing=1 cellPadding=1 width="100%" border=0>
  <tr>
    <th width='160'>标签名：</th>
    <th>#{ShowChildColumn}</th>
  </tr>
  <tr>
    <td>作用：</td>
    <td>显示指定栏目的子栏目列表(只显示一级子栏目)。</td>
  </tr>
</table>

<h3>参数</h3>
<table id='label_table' cellSpacing=1 cellPadding=1 width="100%" border=0>
  <tr>
    <td>channelId </td>
    <td>频道的标识，0：当前频道；&gt; 0：特定的频道。</td>
  </tr>
  <tr>
    <td>columnId</td>
    <td>栏目的标识，默认为当前栏目(=0)。&gt; 0: 特定的栏目。-1 表示根栏目。</td>
  </tr>
  <tr>
    <td>imageType</td>
    <td>栏目前的小图标类型。0：没有；1：符号■；2：小图片：images/column[columnId].gif。默认为没有。</td>
  </tr>
  <tr>
    <td>openType</td>
    <td>打开方式。0：本窗口；1：新窗口。默认为本窗口。</td>
  </tr>
  <tr>
    <td>cols</td>
    <td>每行显示的子栏目个数，默认为1。</td>
  </tr>
  <tr>
    <td>style</td>
    <td>显示的样式表。</td>
  </tr>
  <tr>
    <td>classTitle</td>
    <td>标题的CSS样式表中的类名称。</td>
  </tr>
  <tr>
    <td>classContent</td>
    <td>内容的CSS样式表中的类名称。</td>
  </tr>
  <tr>
    <td>classBottom</td>
    <td>底部的CSS样式表中的类名称。</td>
  </tr>
</table>

<h3>数据说明</h3>
<table id='label_table' cellSpacing=1 cellPadding=1 width="100%" border=0>
  <tr>
    <td width='160'><nobr>子栏目列表</nobr></td>
    <td>
    column_list, 在子标签和模板中，缺省的子栏目列表对象的名字是 'column_list'，其内部形式为 
      List&lt;Column&gt;。 您可以通过循环 #foreach 语法访问这个子栏目列表，例如：
      <pre>
      #{ShowChildColumn columnId='current'}
       #{foreach column in column_list }
        li. column = #{column }
       #{/foreach }
      #{/ShowChildColumn }
      </pre>
      其输出的一个示例为：
      <pre>
      li. Column{id = 8, name = 欧洲新闻, parentId = 3, channelId = 1}
      li. Column{id = 10, name = 亚洲新闻, parentId = 3, channelId = 1}
      li. Column{id = 85, name = 美洲新闻, parentId = 3, channelId = 1}
      </pre>
      也可以得知子栏目的数量，如：
      <pre>
       column_list@size = #{column_list@size }
      </pre>
      结果为：
      <pre>
       column_list@size = 3
      </pre>
      
     通过索引访问
      <pre>
       #{foreach i in range(0, column_list@size) }
        li. column_list[#{i}] = #{column_list[i] }
       #{/foreach }
      </pre>
     结果为：
      <pre>
        column_list[0] = Column{id = 8, name = 欧洲新闻, parentId = 3, channelId = 1}
        column_list[1] = Column{id = 10, name = 亚洲新闻, parentId = 3, channelId = 1}
        column_list[2] = Column{id = 85, name = 美洲新闻, parentId = 3, channelId = 1} 
      </pre>
      
     <p>子栏目列表仅提供指定的栏目的第一级子栏目。每个子栏目都是一个栏目对象，可以访问该栏目对象的所有属性。
     </p>
    </td>
  </tr>
  <tr>
    <td>选项</td>
    <td>
      options, 在子标签和模板中，缺省的选项列表对象的名字是 'options'，其内部形式为 
        Map&lt;String, Object&gt;， 表示所有在标签属性中给出的参数的集合。例如：
      <pre>
      #{ShowChildColumn columnId='current' myprop='hello'}
       li. options = #{options }
       li. options.openType = #{options.openType }
       li. options.myprop = #{options.myprop }
      #{/ShowChildColumn }
      </pre>
      结果为：
      <pre>
       options = {columnId=3, channelId=1, openType=1, colNum=1, imageType=0, myprop=hello}
       options.openType = 1
       options.myprop = hello
      </pre>
      
      <p>在标签中可以自行指定任何自定义的属性，如上例中 myprop='hello' 属性，这些属性值将以字符串形式出现在 
        options 中，可以在子标签和模板中自由的使用。</p>
    </td>
  </tr>
</table>

<h3>内建标签模板说明</h3>
<p>#{ShowChildColumn } 内部使用系统标签 '.builtin.showchildcolumn' 显示子栏目，如果希望定制显示的样式，
  可以复制一个该系统标签，修改之后在 #{ShowChildColumn } 中指定使用哪一个内建标签显示。
  例如我们建立一个新的系统标签 '.mybuiltin.show_child_column', 里面写好模板之后，使用：
</p>
  <pre>
   #{ShowChildColumn template='.mybuiltin.show_child_column' } 
  </pre>
<p>
  其使用您建立的系统标签 '.mybuiltin.show_child_column' 进行显示。
  和在 #{ShowChildColumn }... #{/ShowChildColumn } 的形式相比，可以更好的在多个地方重复使用。
</p>

</body>
</html>
