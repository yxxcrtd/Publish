<%@ page language="java" contentType="text/html; charset=gb2312" pageEncoding="UTF-8"
%><!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>发布系统频道标签</title>
<link href='help.css' rel='stylesheet' type='text/css' />
</head>
<body>

<h2>发布系统频道标签</h2>

<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="*" valign="top">
    <p>
    网站频道适用于各个频道及频道内栏目、专题、项目页面，在主页或全站级页面中没有频道则不能使用。以下将详细说明各标签的作用：
    </p>
    
    <!-- ChannelName -->
    <table id='label_table' cellSpacing=1 cellPadding=1 width="100%" border=0>
      <tr>
        <th width='160'>标签名：</th>
        <th>#{ChannelName}</th>
      </tr>
      <tr>
        <td>作用：</td>
        <td>显示频道名称</td>
      </tr>
      <tr>
        <td>参数：</td>
        <td>
          id -- 要获取的频道信息的标识，缺省 = 0 表示是当前频道。如果给出非 0 值则获取该频道的名称。
        </td>
      </tr>
      <tr>
        <td>等效写法：</td>
        <td>#{channel.name} (对当前频道有效)</td>
      </tr>
    </table>
    
    <!-- ChannelDir -->
    <table id='label_table' cellSpacing=1 cellPadding=1 width="100%" border=0>
      <tr>
        <th width='160'>标签名：</th>
        <th>#{ChannelDir}</th>
      </tr>
      <tr>
        <td>作用：</td>
        <td>显示频道目录</td>
      </tr>
      <tr>
        <td>等效写法：</td>
        <td>#{channel.channelDir}</td>
      </tr>
      <tr>
        <td>结果示例：</td>
        <td>假定在系统缺省的新闻频道使用结果为： news </td>
      </tr>
    </table>
    
    <!-- ChannelID -->
    <table id='label_table' cellSpacing=1 cellPadding=1 width="100%" border=0>
      <tr>
        <th width='160'>标签名：</th>
        <th>#{ChannelID}</th>
      </tr>
      <tr>
        <td>作用：</td>
        <td>显示频道 id 标识</td>
      </tr>
      <tr>
        <td>等效写法：</td>
        <td>#{channel.id}</td>
      </tr>
      <tr>
        <td>结果示例：</td>
        <td>假定在系统缺省的新闻频道使用结果为： 1 </td>
      </tr>
    </table>

    <!-- ChannelUrl -->
    <table id='label_table' cellSpacing=1 cellPadding=1 width="100%" border=0>
      <tr>
        <th width='160'>标签名：</th>
        <th>#{ChannelUrl}</th>
      </tr>
      <tr>
        <td>作用：</td>
        <td>显示频道链接地址</td>
      </tr>
      <tr>
        <td>参数：</td>
        <td>
          id -- 要获取的频道信息的标识，缺省 = 0 表示是当前频道。如果给出非 0 值则获取该频道的链接地址。
        </td>
      </tr>
      <tr>
        <td>等效写法：</td>
        <td>#{channel.pageUrl} (对当前频道有效)</td>
      </tr>
      <tr>
        <td>结果示例：</td>
        <td>假定在系统缺省的新闻频道使用结果为： 'http://your-site-url/site-dir/news/index.html' </td>
      </tr>
    </table>

    <!-- UploadDir -->
    <table id='label_table' cellSpacing=1 cellPadding=1 width="100%" border=0>
      <tr>
        <th width='160'>标签名：</th>
        <th>#{UploadDir}</th>
      </tr>
      <tr>
        <td>作用：</td>
        <td>显示当前频道上载目录</td>
      </tr>
      <tr>
        <td>等效写法：</td>
        <td>#{channel.uploadDir}</td>
      </tr>
      <tr>
        <td>结果示例：</td>
        <td>假定在系统缺省的新闻频道使用结果为： UploadFiles (取决于设置)</td>
      </tr>
    </table>

    <!-- ChannelPicUrl -->
    <table id='label_table' cellSpacing=1 cellPadding=1 width="100%" border=0>
      <tr>
        <th width='160'>标签名：</th>
        <th>#{ChannelPicUrl}</th>
      </tr>
      <tr>
        <td>作用：</td>
        <td>得到频道图片的Url地址</td>
      </tr>
      <tr>
        <td>等效写法：</td>
        <td>#{channel.channelPicUrl}</td>
      </tr>
    </table>
    
    <!-- ChannelItemName -->
    <table id='label_table' cellSpacing=1 cellPadding=1 width="100%" border=0>
      <tr>
        <th width='160'>标签名：</th>
        <th>#{ChannelItemName}</th>
      </tr>
      <tr>
        <td>作用：</td>
        <td>得到频道项目名称</td>
      </tr>
      <tr>
        <td>等效写法：</td>
        <td>#{channel.itemName}</td>
      </tr>
      <tr>
        <td>结果示例：</td>
        <td>新闻</td>
      </tr>
    </table>

    <!-- ChannelItemUnit -->
    <table id='label_table' cellSpacing=1 cellPadding=1 width="100%" border=0>
      <tr>
        <th width='160'>标签名：</th>
        <th>#{ChannelItemUnit}</th>
      </tr>
      <tr>
        <td>作用：</td>
        <td>得到频道项目单位</td>
      </tr>
      <tr>
        <td>等效写法：</td>
        <td>#{channel.itemUnit}</td>
      </tr>
      <tr>
        <td>结果示例：</td>
        <td>篇</td>
      </tr>
    </table>
    
    <!-- Meta_Keywords_Channel -->
    <table id='label_table' cellSpacing=1 cellPadding=1 width="100%" border=0>
      <tr>
        <th width='160'>标签名：</th>
        <th>#{Meta_Keywords_Channel}</th>
      </tr>
      <tr>
        <td>作用：</td>
        <td>频道META关键词，针对搜索引擎设置的关键词</td>
      </tr>
    </table>

    <!-- Meta_Description_Channel -->
    <table id='label_table' cellSpacing=1 cellPadding=1 width="100%" border=0>
      <tr>
        <th width='160'>标签名：</th>
        <th>#{Meta_Description_Channel}</th>
      </tr>
      <tr>
        <td>作用：</td>
        <td>频道META网页描述，针对搜索引擎设置的网页描述</td>
      </tr>
    </table>

    <!-- ShowChannelCount -->
    <table id='label_table' cellSpacing=1 cellPadding=1 width="100%" border=0>
      <tr>
        <th width='160'>标签名：</th>
        <th>#{ShowChannelCount}</th>
      </tr>
      <tr>
        <td>作用：</td>
        <td>统计频道信息：内容项总数，待审核内容项。</td>
      </tr>
      <tr>
        <td>定制：</td>
        <td>
         在标签中提供 stat 对象，其包含频道的简要统计信息。(TODO: 更多说明)
        </td>
      </tr>
    </table>

    <!-- ShowColumnMenu -->
    <table id='label_table' cellSpacing=1 cellPadding=1 width="100%" border=0>
      <tr>
        <th width='160'>标签名：</th>
        <th>#{ShowColumnMenu}</th>
      </tr>
      <tr>
        <td>作用：</td>
        <td>显示频道的栏目导航条。引入一个自动生成的JS，达到显示层级菜单的效果。</td>
      </tr>
      <tr>
        <td>定制：</td>
        <td>
          根据用户不同的需要，引入不同的 js，可以得到不同的栏目菜单效果。
        </td>
      </tr>
    </table>

    <!-- ShowColumnGuide -->
    <table id='label_table' cellSpacing=1 cellPadding=1 width="100%" border=0>
      <tr>
        <th width='160'>标签名：</th>
        <th>#{ShowColumnGuide}</th>
      </tr>
      <tr>
        <td>作用：</td>
        <td> 显示此频道的栏目地图。</td>
      </tr>
      <tr>
        <td>参数：</td>
        <td>
          channelId -- 频道标识，默认为 0 表示当前频道；<br/>
          openType -- 打开方式。0：当前窗口，1：新窗口，默认为当前窗口；<br/>
          cols -- 每行显示多少个子栏目。<br/>
          template -- 使用哪一个模板显示栏目地图。缺省会使用系统内建标签 '.builtin.showcolumnguide' 
              产生显示。根据用户的需要，可以复制一个新的内建标签，然后进行适当修改。
        </td>
      </tr>
      <tr>
        <td>内部数据</td>
        <td>
        <p>
        column_list - 子栏目列表，其内部为 List&lt;Column&gt;, 其中每个 Column 都含有 
          childColumns 对象可用，表示这个栏目的第一级子栏目。</p>
        <p>
        options - 选项，所有从标签属性转换过来的选项值 Map.
        </p>
        </td>
      </tr>
      <tr>
        <td>详细说明</td>
        <td>
          <a href='showColumnGuide.jsp'>ShowColumnGuide 详细说明</a>
        </td>
      </tr>
    </table>
    
    
    </td>
  </tr>
</table>

</body>
</html>
