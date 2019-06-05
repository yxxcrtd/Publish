<%@ page language="java" contentType="text/html; charset=gb2312" pageEncoding="UTF-8"
%><!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>发布系统栏目标签</title>
<link href='help.css' rel='stylesheet' type='text/css' />
</head>
<body>

<h2>发布系统栏目标签</h2>

<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="*" valign="top">
    <p>
    栏目标签适用于各个栏目、项目页面，在主页、全站级页面中、频道、专题中没有栏目的页面中则不能使用。以下将详细说明各标签的作用：
    </p>
    
    <!-- ColumnID -->
    <table id='label_table' cellSpacing=1 cellPadding=1 width="100%" border=0>
      <tr>
        <th width='160'>标签名：</th>
        <th>#{ColumnID}</th>
      </tr>
      <tr>
        <td>作用：</td>
        <td>显示栏目标识</td>
      </tr>
      <tr>
        <td>等效写法：</td>
        <td>#{column.id}</td>
      </tr>
    </table>

    <!-- ColumnName -->
    <table id='label_table' cellSpacing=1 cellPadding=1 width="100%" border=0>
      <tr>
        <th width='160'>标签名：</th>
        <th>#{ColumnName}</th>
      </tr>
      <tr>
        <td>作用：</td>
        <td>显示栏目名称</td>
      </tr>
      <tr>
        <td>等效写法：</td>
        <td>#{column.name}</td>
      </tr>
    </table>

    <!-- ColumnUrl -->
    <table id='label_table' cellSpacing=1 cellPadding=1 width="100%" border=0>
      <tr>
        <th width='160'>标签名：</th>
        <th>#{ColumnUrl}</th>
      </tr>
      <tr>
        <td>作用：</td>
        <td>显示栏目链接地址</td>
      </tr>
      <tr>
        <td>等效写法：</td>
        <td>#{column.pageUrl}</td>
      </tr>
    </table>
    
    <!-- ColumnDir -->
    <table id='label_table' cellSpacing=1 cellPadding=1 width="100%" border=0>
      <tr>
        <th width='160'>标签名：</th>
        <th>#{ColumnDir}</th>
      </tr>
      <tr>
        <td>作用：</td>
        <td>显示当前栏目的目录。如果当前栏目是频道的根栏目，那么显示为频道的目录。</td>
      </tr>
      <tr>
        <td>等效写法：</td>
        <td>#{column.columnDir}</td>
      </tr>
    </table>
    
    <!-- ParentDir -->
    <table id='label_table' cellSpacing=1 cellPadding=1 width="100%" border=0>
      <tr>
        <th width='160'>标签名：</th>
        <th>#{ParentDir}</th>
      </tr>
      <tr>
        <td>作用：</td>
        <td>显示当前栏目的父栏目的目录。如果当前栏目是频道的根栏目或者父栏目是频道的根栏目，
            那么父栏目的目录是频道的目录。</td>
      </tr>
      <!-- 
      <tr>
        <td>等效写法：</td>
        <td>#{column.parent.columnDir}, #{column.parent.channelDir}</td>
      </tr>
       -->
    </table>

    <!-- Readme -->
    <table id='label_table' cellSpacing=1 cellPadding=1 width="100%" border=0>
      <tr>
        <th width='160'>标签名：</th>
        <th>#{Readme}</th>
      </tr>
      <tr>
        <td>作用：</td>
        <td>显示当前栏目的说明。如果当前栏目是频道的根栏目，那么显示为频道的说明。</td>
      </tr>
      <tr>
        <td>等效写法：</td>
        <td>#{column.description}</td>
      </tr>
    </table>

    <!-- ColumnPicUrl -->
    <table id='label_table' cellSpacing=1 cellPadding=1 width="100%" border=0>
      <tr>
        <th width='160'>标签名：</th>
        <th>#{ColumnPicUrl}</th>
      </tr>
      <tr>
        <td>作用：</td>
        <td>栏目图片地址。</td>
      </tr>
      <tr>
        <td>等效写法：</td>
        <td>#{column.logo}</td>
      </tr>
    </table>
    
    <!-- Meta_Keywords_Column -->
    <table id='label_table' cellSpacing=1 cellPadding=1 width="100%" border=0>
      <tr>
        <th width='160'>标签名：</th>
        <th>#{Meta_Keywords_Column}</th>
      </tr>
      <tr>
        <td>作用：</td>
        <td>栏目META关键词，针对搜索引擎设置的关键词</td>
      </tr>
      <tr>
        <td>等效写法：</td>
        <td>#{column.metaKey@html}, 自己需要写外部的 meta key='keyword' content='#{column.metaKey@html}' </td>
      </tr>
    </table>
    
    <!-- Meta_Description_Column -->
    <table id='label_table' cellSpacing=1 cellPadding=1 width="100%" border=0>
      <tr>
        <th width='160'>标签名：</th>
        <th>#{Meta_Description_Column}</th>
      </tr>
      <tr>
        <td>作用：</td>
        <td>栏目META网页描述，针对搜索引擎设置的网页描述</td>
      </tr>
      <tr>
        <td>等效写法：</td>
        <td>#{column.metaDesc@html}, 自己需要写外部的 meta key='description' content='#{column.metaDesc@html}' </td>
      </tr>
    </table>

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
      <tr>
        <td>参数：</td>
        <td>
          channelId -- 频道的标识，0：当前频道；&gt; 0：特定的频道。<br/>
          columnId -- 栏目的标识，默认为当前栏目(=0)。&gt; 0: 特定的栏目。-1 表示根栏目。<br/>
          columnNum -- 最多显示的子栏目个数。 默认为所有的子栏目。<br/>
          imageType -- 栏目前的小图标类型。0：没有；1：符号■；2：小图片：images/column[columnId].gif。默认为没有。<br/>
          openType -- 打开方式。0：本窗口；1：新窗口。默认为本窗口。<br/>
          cols -- 每行显示的子栏目个数，默认为1。<br/>
          style -- 显示的样式表。<br/>
          classTitle -- 标题的CSS样式表中的类名称。<br/>
          classContent -- 内容的CSS样式表中的类名称。<br/>
          classBottom -- 底部的CSS样式表中的类名称。<br/>
        </td>
      </tr>
      <tr>
        <td>使用示例：</td>
        <td>
          使用缺省系统模板显示子栏目：<br/>
          <pre>
           #{ShowChildColumn }
          </pre>
         
          使用自己定义的内部显示模板：<br/>
          <pre>
           #{ShowChildColumn columnId="0" }
             #{Repeater }
               &lt;li&gt; &lt;a href="#{column.pageUrl}" target="_blank"&gt;#{column.name}&lt;/a&gt;
             #{/Repeater }
           #{/ShowChildColumn } 
          </pre>
          结果为(示例)：
          <ul>
          <li><a href='#栏目地址1'>栏目名字1</a></li>
          <li><a href='#栏目地址2'>栏目名字2</a></li>
          <li><a href='#栏目地址3'>栏目名字3</a></li>
          </ul>
        </td>
      </tr>
      <tr>
        <td>详细说明：</td>
        <td><a href='showChildColumn.jsp'>点击这里查看详细说明</a></td>
      </tr>
    </table>
    
    <!-- ShowAllChildColumn -->
    <table id='label_table' cellSpacing=1 cellPadding=1 width="100%" border=0>
      <tr>
        <th width='160'>标签名：</th>
        <th>#{ShowAllChildColumn}</th>
      </tr>
      <tr>
        <td>作用：</td>
        <td>显示栏目的子孙栏目列表，只是支持树型显示和平铺型显示。</td>
      </tr>
      <tr>
        <td>参数：</td>
        <td>
          channelId - 频道的标识，0：当前频道；&gt; 0：指定的频道。<br/>
          colunnId - 栏目的标识，-1：频道的根栏目；0：当前栏目；&gt; 0：指定的栏目。默认为 0 。<br/>
          isTree - 是否是树型显示，不是的话以平铺显示。默认为 true 。<br/>
          linkStyle - 链接的样式表。<br/>
          style - 节点的样式表。<br/>
          currentStyle - 当前节点的样式表。<br/>
          mouseOverStyle - 鼠标移上去时节点的样式表。<br/>
          padding_left - 属性层级的缩进，以像素为单位（px），深度越大自动缩进越多，默认为 0（不缩进）。<br/>
          nodePic - 节点前面的图片地址，默认为 images/nodePic.gif 。<br/>
          leafPic - 叶子节点前面的图片地址，默认为 images/leafPic.gif。<br/>
          target - 链接的目标窗口，默认为 _self 。 <br/>
        </td>
      </tr>
      <tr>
        <td>使用示例：</td>
        <td>
          使用缺省系统模板显示子栏目：<br/>
          <pre>
           #{ShowChildColumn }
          </pre>
         
          使用自己定义的内部显示模板：<br/>
          <pre>
           #{ShowChildColumn columnId="0" }
             #{Repeater }
               &lt;li&gt; &lt;a href="#{column.pageUrl}" target="_blank"&gt;#{column.name}&lt;/a&gt;
             #{/Repeater }
           #{/ShowChildColumn } 
          </pre>
          结果为(示例)：
          <ul>
          <li><a href='#栏目地址1'>栏目名字1</a></li>
          <li><a href='#栏目地址2'>栏目名字2</a></li>
          <li><a href='#栏目地址3'>栏目名字3</a></li>
          </ul>
        </td>
      </tr>
    </table>
    
    <!-- ShowColumns -->
    <table id='label_table' cellSpacing=1 cellPadding=1 width="100%" border=0>
      <tr>
        <th width='160'>标签名：</th>
        <th>#{ShowColumns}</th>
      </tr>
      <tr>
        <td>作用：</td>
        <td>频道中特定栏目集合的循环自定义标签。</td>
      </tr>
      <tr>
        <td>参数：</td>
        <td>
          channelId - 频道的标识，0：当前频道；&gt; 0：指定的频道。<br/>
          columnIds - 栏目标识的集合。<br/>
          loop - 是否自动循环，缺省为 true。<br/>
        </td>
      </tr>
      <tr>
        <td>使用示例：</td>
        <td>
          <p>此标签按照用户需要取出指定标识的栏目，并在标签内部的子标签中可以使用这些栏目。</p>
          <p>在该标签内可使用的变量为 'column_list', 当循环的时候，每次一个栏目，栏目对象名字为 'column'。
           </p>
          <pre>
          #{ShowColumns channelId="1" columnIds="2,3,5,7,11" }
            &lt;li&gt;&lt;a href="#{ColumnUrl}"&gt;#{ColumnName}, #{column.name}&lt;/a&gt;
          #{/ShowColumns}
          </pre>
          
          <p>结果为：</p>
          <ul>
          <li><a href='#栏目地址1'>栏目名字1, 栏目名字1</a></li>
          <li><a href='#栏目地址2'>栏目名字2, 栏目名字2</a></li>
          <li><a href='#栏目地址3'>栏目名字3, 栏目名字3</a></li>
          </ul>
        </td>
      </tr>
    </table>

    <!-- ShowChildColumnItems -->
    <table id='label_table' cellSpacing=1 cellPadding=1 width="100%" border=0>
      <tr>
        <th width='160'>标签名：</th>
        <th>#{ShowChildColumn}</th>
      </tr>
      <tr>
        <td>作用：</td>
        <td>显示当前栏目的子栏目项目列表。</td>
      </tr>
      <tr>
        <td>参数：</td>
        <td>
          channelId -- 频道标识。0：当前频道；&gt; 0：相应标识的频道。默认为0。<br/>
          columnId -- 栏目标识。-1：根栏目；0：当前栏目；&gt; 0：相应标识的栏目。默认为0。<br/>
          titleNum -- 标题长度。默认为20。<br/>
          columnCss -- 栏目的CSS样式<br/>
          style -- 项目的样式<br/>
          cols -- 每行显示多少个栏目<br/>
        </td>
      </tr>
      <tr>
        <td>使用示例：</td>
        <td>
         #{ShowChildColumnItems titleNum="20" columnCss="" style="" cols = "2"}
        </td>
      </tr>
    </table>

    </td>
  </tr>
</table>

</body>
</html>
