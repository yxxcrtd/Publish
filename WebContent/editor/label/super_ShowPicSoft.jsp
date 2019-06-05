<%@ page language="java" contentType="text/html; charset=gb2312" pageEncoding="utf-8"%>
<%@page import="com.chinaedustar.publish.*"%>
<%@taglib prefix="pub" uri="/WEB-INF/publish.tld"%>
<%@page import="com.chinaedustar.publish.admin.SuperLabel"%>
<%
  // 初始化页面数据。
  SuperLabel page_init = new SuperLabel(pageContext);
  page_init.initSuperItemPage();

%>

<%@ include file="../../admin/element_column.jsp" %> 
<%@ include file="element_label.jsp" %>
<%@ include file="super_label_common.jsp" %>

<!-- 主模板定义 -->
<pub:template name="main">
 #{call label_main_temp("ShowPicSoftList","图片下载函数标签") }
</pub:template>



<pub:template name="label_property_init">
  setLabelProp('channelId');
  setLabelProp('columnId');
  setLabelProp('specialId');
  setLabelProp('includeChild');
  setLabelProp('labelDesc');
  setLabelProp('itemNum');
  setLabelProp('isTop');
  setLabelProp('isCommend');
  setLabelProp('isElite');
  setLabelProp('isHot');
  setLabelProp('dateScope');
  setLabelProp('orderType');
  setLabelProp('ShowType');
  setLabelProp('picWidth');
  setLabelProp('picHeight');
  setLabelProp('titleCharNum');
  setLabelProp('contentNum');
  setLabelProp('showColumn');
  setLabelProp('showAuthor');
  setLabelProp('lastModified');
  setLabelProp('showHits');
  setLabelProp('showNew');
  setLabelProp('colNum');
</pub:template>


<pub:template name="label_param_setting">
<table width='100%' bgcolor='white' cellspacing='1' cellpadding='1'>
 <tr class='tdbg'>
  <td height='25' align='right' class='tdbg5'><strong>所属频道：</strong></td>
  <td height='25'>
  <form action='super_ShowPicSoft.jsp' method='get' name='myform' id='myform' style='margin:0px;'>
   #{call channel_select(600, 480) }
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
 #{call tr_item_num }
 #{call tr_item_filter }
 #{call tr_date_scope }
 #{call tr_order_type }
 <tr class='tdbg' style="display:none">
  <td height='25' align='right' class='tdbg5'><strong>显示样式：</strong></td>
  <td height='25'>
   <select name='ShowType' id='ShowType' __def_value='0'>
    <option value='0'>图片+标题+内容简介：上下排列</option>
    <option value='1'>（图片+标题：上下排列）+内容简介：左右排列</option>
    <option value='2'>图片+（标题+内容简介：上下排列）：左右排列</option>
    <option value='3'>输出DIV格式</option>
   </select>
  </td>
 </tr>
 #{call tr_pic_size }
 #{call tr_title_len }
 #{call tr_content_len('none') }
 <tr class='tdbg'>
  <td height='50' align='right' class='tdbg5'><strong>显示内容：</strong></td>
  <td height='50'>
  <table width='100%' border='0' cellpadding='1' cellspacing='2'>
   <tr>
    <td>#{call show_column }</td>
    <td>#{call show_author }</td>
    <td>#{call show_last_modified }
    </td>
   </tr>
   <tr>
    <td>#{call show_hits }</td>
    <td>#{call show_hot }</td>
    <td>#{call show_new }</td>
   </tr>
  </table>
  </td>
 </tr>
 #{call tr_col_num }
</table>

<script Language="JavaScript">
function change_item(element) {
  if(element.selectedIndex!=-1)
  var selectednumber = element.options[element.selectedIndex].value;

  if(selectednumber==1) {
    objFiles.style.display="";
    
    $("common").src = "../../images/soft/soft_common.gif"
    $("elite").src = "../../images/soft/soft_elite.gif"
    $("ontop").src = "../../images/soft/soft_ontop.gif"
  } else if (selectednumber==0) {
    objFiles.style.display="none";
  } else if (selectednumber==2) {
    objFiles.style.display="none";
  } else if (selectednumber>=3) {
    selectednumber = selectednumber - 1
    objFiles.style.display="";
      
    $("common").src = "../../images/soft/soft_common" + selectednumber + ".gif"
    $("elite").src = "../../images/soft/soft_elite" + selectednumber + ".gif"
    $("ontop").src = "../../images/soft/soft_ontop" + selectednumber + ".gif"
  }
}
</script>
</pub:template>

<pub:process_template name="main" />
