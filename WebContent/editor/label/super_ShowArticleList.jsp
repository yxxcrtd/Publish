<%@page language="java" contentType="text/html; charset=gb2312" pageEncoding="utf-8"
%><%@taglib prefix="pub" uri="/WEB-INF/publish.tld"
%><%@page import="com.chinaedustar.publish.admin.SuperLabel"
%><%
  // 初始化页面数据。
  SuperLabel page_init = new SuperLabel(pageContext);
  page_init.initSuperItemPage();
  
%>

<%@ include file="../../admin/element_column.jsp" %> 
<%@ include file="element_label.jsp"%>
<%@ include file="super_label_common.jsp" %>

<!-- 主执行模板定义 -->
<pub:template name="main">
 #{call label_main_temp("ShowArticleList","文章列表函数标签") }
</pub:template>

<pub:template name="label_property_init">
  initMyLabelProp();
</pub:template>

<pub:template name="label_param_setting">
<table width='100%' bgcolor='white' cellspacing='1' cellpadding='1'>
  <tr class='tdbg'>
   <td height='25' align='right' class='tdbg5'><strong>所属频道：</strong></td>
   <td height='25'>
  <form action='super_ShowArticleList.jsp' method='post' name='myform' id='myform' style='margin:0px;'>
    #{call channel_select(700, 600) }
  </form>
   </td>
  </tr>
 #{if (channelId > 0) }
  <tr class='tdbg'>
   <td height='25' align='right' class='tdbg5'><strong>所属栏目：</strong></td>
   <td height='25'>
    #{call column_select }
   </td>
  </tr>
  <tr class='tdbg'>
   <td height='25' align='right' class='tdbg5'><strong>所属专题：</strong></td>
   <td height='25'>
    #{call special_select }
   </td>
  </tr>
 #{else }
  #{call tr_default_column_special}
 #{/if }
 #{call tr_label_desc }

  <tr class='tdbg'>
   <td height='25' align='right' class='tdbg5'><strong>显示样式：</strong></td>
   <td height='25'><select name='ShowType' id='showType' __def_value='0'>
    <option value='0'>普通列表</option>
    <option value='1'>表格式</option>
    <!--<option value='2'>各项独立式</option>
    <option value='3'>输出DIV格式</option>-->
   </select></td>
  </tr>
 #{call tr_item_num }
 #{call tr_item_filter }
 #{call tr_item_author }
 #{call tr_pic_type }
 #{call tr_date_scope }
 #{call tr_order_type }
 #{call tr_title_len }
 #{call tr_content_len('') }
 #{call tr_col_num }

  <tr class='tdbg'>
   <td height='50' align='right' class='tdbg5'><strong>显示内容选项：</strong></td>
   <td height='50'>
   <table width='100%' border='0' cellpadding='1' cellspacing='2'>
    <tr>
     <td>#{call show_column }</td>
     <td><input name='showPicArticle' type='checkbox' id='showPicArticle' value='true' __def_value='false' />"图文"标志</td>
     <td>#{call show_author }</td>
     <td colspan='3'>#{call show_last_modified }</td>
    </tr>
    <tr>
     <td>#{call show_hot }</td>
     <td>#{call show_hits }</td>
     <td>#{call show_new }</td>
     <td><input name='showDescription' type='checkbox' id='showDescription' value='true' __def_value='false' />显示提示信息</td>
     <td><input name='showComment' type='checkbox' id='showComment' value='true' __def_value='false'/>显示评论链接</td>
     <td><input name='usePage' type='checkbox' id='usePage' value='true' __def_value='false' />显示分页</td>
    </tr>
    
   </table>
   </td>
  </tr>
 #{call tr_open_type }
 #{call tr_css_3 }
</table>

<script language="JavaScript">

function initMyLabelProp() {
  setLabelProp('channelId');
  setLabelProp('columnId');
  setLabelProp('specialId');
  setLabelProp('includeChild');
  setLabelProp('labelDesc');
  setLabelProp('showType');
  setLabelProp('itemNum');
  setLabelProp('isTop');
  setLabelProp('isCommend');
  setLabelProp('isElite');
  setLabelProp('isHot');
  setLabelProp('author');
  setLabelProp('picType');
  setLabelProp('dateScope');
  setLabelProp('orderType');
  setLabelProp('titleCharNum');
  setLabelProp('contentNum');
  setLabelProp('colNum');
  setLabelProp('showColumn');
  setLabelProp('showPicArticle');
  setLabelProp('showAuthor');
  setLabelProp('lastModified');
  setLabelProp('showHits');
  setLabelProp('showHot');
  setLabelProp('showNew');
  setLabelProp('showDescription');
  setLabelProp('showComment');
  setLabelProp('usePage');
  setLabelProp('openType');
  setLabelProp('style');
  setLabelProp('class1');
  setLabelProp('class2');
}
</script>
</pub:template>

<%-- 执行模板 --%>
<pub:process_template name="main"/>


