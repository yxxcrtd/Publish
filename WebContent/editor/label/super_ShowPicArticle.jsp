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
<%@ include file="element_label.jsp"%>
<%@ include file="super_label_common.jsp" %>

<!-- 主模板定义 -->
<pub:template name="main">
 #{call label_main_temp("ShowPicArticleList", "图片文章列表函数标签")}
 
<script Language="JavaScript">
function change_item(element) {
  if(element.selectedIndex != -1)
    var selectednumber = element.options[element.selectedIndex].value;

  if (selectednumber == 1) {
    objFiles.style.display="";
    
    $("common").src = "../images/article/article_common.gif";
    $("elite").src = "../images/article/article_elite.gif";
    $("ontop").src = "../images/article/article_ontop.gif";
  } else if (selectednumber==0) {
    objFiles.style.display="none";
  } else if (selectednumber==2) {
    objFiles.style.display="none";
  } else if (selectednumber>=3) {
        selectednumber = selectednumber - 1
        objFiles.style.display="";
        $("common").src = "../images/article/article_common" + selectednumber + ".gif";
        $("elite").src = "../images/article/article_elite" + selectednumber + ".gif";
        $("ontop").src = "../images/article/article_ontop" + selectednumber + ".gif";
    }
}
</script>
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
  <form action='super_ShowPicArticle.jsp' method='get' name='myform' id='myform' style='margin:0px;'>
   <select id='channelId' name='channelId' __def_value='0' onchange='document.myform.submit();'>
    <option value='0' #{iif (channelId == 0, "selected", "") }>当前频道(缺省)</option>
   #{foreach channel in channel_list }
    <option value='#{channel.id }' #{iif (channelId == channel.id, "selected", "") }>#{channel.name }</option>
   #{/foreach }
    <option value='-1' #{iif (channelId == -1, "selected", "") }>所有同类频道</option>
    <option value='-2' #{iif (channelId == -2, "selected", "") }>任何频道</option>
   </select>
  </form>
   </td>
  </tr>
  #{if (channelId > 0) }
  <tr class='tdbg'>
   <td height='25' align='right' class='tdbg5'><strong>所属栏目：</strong></td>
   <td height='25'>
   <select name='columnId' id="columnId" __def_value='0' size='1'>
    <option value='0' style=''>任何栏目</option>
    <option value='-1' style=''>当前栏目</option>
    #{call dropDownColumns(channelId, dropdown_columns) }
   </select> 
   <input type='checkbox' id='includeChild' name='includeChild' 
     __def_value='true' value='true' checked='checked' />包含子栏目<br>
  </td>
  </tr>
  <tr class='tdbg'>
   <td height='25' align='right' class='tdbg5'><strong>所属专题：</strong></td>
   <td height='25'>
   <select name='specialId' id='specialId' __def_value='0'>
    <option value='0'>当前专题</option>
    <option value='-1'>当前频道的所有专题</option>
    <option value='-2'>不对专题考虑</option>
   #{foreach special in special_list }
    <option value='#{special.id }'>#{special.name }(#{iif (special.channelId == 0, "全站", "本频道") })</option>
   #{/foreach }
   </select>
   </td>
  </tr>
  #{else }
  <tr style="display: none;"><td></td><td>
   <input type="hidden" value="0" name="columnId" id="columnId" __def_value='0' />
   <input type="checkbox" name='includeChild' __def_value='true' value='true' id="includeChild" checked="checked" />
   <input type="hidden" name="specialId" value="0" id="specialId" __def_value='0' />
  </td>
  </tr>
  #{/if }
 <tr class='tdbg'>
  <td height='25' align='right' class='tdbg5'><strong>标签说明：</strong></td>
  <td height='25'><INPUT TYPE='text' NAME='labelDesc' value=''
   id='labelDesc' size='15' maxlength='20'>&nbsp;&nbsp;<FONT
   style='font-size:12px' color='blue'>请在这里填写标签的使用说明方便以后的查找</FONT></td>
 </tr>
 <tr class='tdbg'>
  <td height='25' align='right' class='tdbg5'><strong>文章数目：</strong></td>
  <td height='25'><input id='itemNum' name='itemNum' type='text' __def_value='4' value='4' size='5'
   maxlength='3'> <font color='#FF0000'>如果为0，将显示所有文章。</font></td>
 </tr>
 #{call tr_item_filter }
 <tr class='tdbg'>
  <td height='25' align='right' class='tdbg5'><strong>日期范围：</strong></td>
  <td height='25'>只显示最近 <input name='DateScope' type='text'
   id='dateScope' value='0' __def_value='0' size='5' maxlength='3'>
  天内更新的文章&nbsp;&nbsp;&nbsp;&nbsp;<font color='#FF0000'>&nbsp;&nbsp;如果为空或0，则显示所有天数的文章</font></td>
 </tr>
 <tr class='tdbg'>
  <td height='25' align='right' class='tdbg5'><strong>排序方法：</strong></td>
  <td height='25'><select name='OrderType' id='OrderType' __def_value='0'>
   <option value='0'>文章ID（降序）</option>
   <option value='1'>文章ID（升序）</option>
   <option value='2'>更新时间（降序）</option>
   <option value='3'>更新时间（升序）</option>
   <option value='4'>点击次数（降序）</option>
   <option value='5'>点击次数（升序）</option>
  </select></td>
 </tr>
 <tr class='tdbg' style="display:none">
  <td height='25' align='right' class='tdbg5'><strong>显示样式：</strong></td>
  <td height='25'><select name='ShowType' id='ShowType' __def_value='1'>
   <option value='1'>图片+标题+内容简介：上下排列</option>
   <option value='2'>（图片+标题：上下排列）+内容简介：左右排列</option>
   <option value='3'>图片+（标题+内容简介：上下排列）：左右排列</option>
   <option value='4'>输出DIV格式</option>
  </select></td>
 </tr>
 <tr class='tdbg'>
  <td height='25' align='right' class='tdbg5'><b>图片设置：</b></td>
  <td height='25'>&nbsp;宽度： <input name='picWidth' type='text'
   id='picWidth' value='' size='5' maxlength='3' __def_value='' />
  像素&nbsp;&nbsp;&nbsp;&nbsp; 高度： <input name='picHeight' type='text'
   id='picHeight' value='' size='5' maxlength='3' __def_value=''> 像素
   <br/>如果只设置宽度则图片显示为等宽，只设置高度则为等高</td>
 </tr>
 <tr class='tdbg'>
  <td height='25' align='right' class='tdbg5'><strong>标题最多字符数：</strong></td>
  <td height='25'><input name='titleCharNum' type='text' id='titleCharNum'
   value='0' __def_value='0' size='5' maxlength='3'> &nbsp;&nbsp;&nbsp;&nbsp;<font
   color='#FF0000'>若为0，则显示完整标题；若为-1，则不显示标题。字母算一个字符，汉字算两个字符。</font></td>
 </tr>
 <tr class='tdbg'>
  <td height='25' align='right' class='tdbg5'><strong>文章内容字符数：</strong></td>
  <td height='25'><input name='ContentNum' type='text'
   id='contentNum' value='0' __def_value='0' size='5' maxlength='3'>
  &nbsp;&nbsp;&nbsp;&nbsp;<font color='#FF0000'>如果大于0，则显示指定字数的文章内容</font></td>
 </tr>
  <tr class='tdbg'>
   <td height='50' align='right' class='tdbg5'><strong>显示内容：</strong></td>
   <td height='50'>
   <table width='100%' border='0' cellpadding='1' cellspacing='2'>
    <tr>
     <td><input name='ShowColumn' type='checkbox' 
      value='true' __def_value='false' id='showColumn' />所属栏目</td>
     <td><input name='ShowAuthor' type='checkbox' id='showAuthor' 
       value='true' __def_value='false' />作者</td>
     <td>更新时间 <select name='LastModified' id='lastModified' __def_value='0'>
      <option value='0'>不显示</option>
      <option value='1'>年月日</option>
      <option value='2'>月日</option>
      <option value='3'>月-日</option>
      <option value='4'>年-月-日</option>
      <option value='5'>年-月-日 小时:分</option>
     </select></td>
    </tr>
    <tr>
     <td><input name='ShowHits' type='checkbox' id='showHits'
      value='true' __def_value='false' />点击次数</td>
     <td><input name='ShowHot' type='checkbox' id='showHot'
      value='true' __def_value='false' />热点文章标志</td>
     <td><input name='ShowNew' type='checkbox' id='showNew'
      value='true' __def_value='false' />最新文章标志</td>
    </tr>
   </table>
   </td>
  </tr>
 <tr class='tdbg'>
  <td height='25' align='right' class='tdbg5'><strong>每行显示文章数：</strong></td>
  <td height='25'><select name='colNum' id='colNum' __def_value='0'>
   <option value='0'>缺省</option>
   <option value='1'>1</option>
   <option value='2'>2</option>
   <option value='3'>3</option>
   <option value='4'>4</option>
   <option value='5'>5</option>
   <option value='6'>6</option>
   <option value='7'>7</option>
   <option value='8'>8</option>
   <option value='9'>9</option>
   <option value='10'>10</option>
   <option value='11'>11</option>
   <option value='12'>12</option>
  </select> &nbsp;&nbsp;&nbsp;&nbsp;超过指行列数就会换行</td>
 </tr>
</table>
</pub:template>


<pub:process_template name="main" />
