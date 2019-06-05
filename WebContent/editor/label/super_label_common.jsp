<%@page language="java" contentType="text/html; charset=gb2312" pageEncoding="utf-8"%>
<%@taglib prefix="pub" uri="/WEB-INF/publish.tld"%>

<%-- 频道选择, 几乎所有 super label 页面都用到 --%>
<pub:template name="channel_select">
 #{param width, height }
   <select id='ChannelId' name='channelId' __def_value='0' onchange='document.myform.submit();'>
    <option value='0' #{iif (channelId == 0, "selected", "") }>当前频道(缺省)</option>
   #{foreach channel in channel_list }
    <option value='#{channel.id }' #{iif (channelId == channel.id, "selected", "") }>#{channel.name }</option>
   #{/foreach }
    <option value='-1' #{iif (channelId == -1, "selected", "") }>当前频道的所有同类频道</option>
    <option value='-2' #{iif (channelId == -2, "selected", "") }>任何频道</option>
   </select>
   <input type='hidden' name='width' value='#{(width) }' />
   <input type='hidden' name='height' value='#{(height) }' />
</pub:template>

<%-- 栏目选择 --%>
<pub:template name="column_select">
   <select name='ColumnId' id="columnId" __def_value='0' size='1'>
    <option value='0' style=''>当前栏目(缺省)</option>
    <option value='-1' style=''>当前频道的所有栏目</option>
    #{call dropDownColumns(channelId, dropdown_columns) }
   </select> 
   <input type='checkbox' id='includeChild' name='IncludeChild' __def_value='true' 
      value='true' checked='checked' />包含子栏目&nbsp;&nbsp;<br />
</pub:template>

<%-- 专题选择 --%>
<pub:template name="special_select">
   <select name='SpecialId' id='specialId' __def_value='0'>
    <option value='0'>当前专题(缺省)</option>
    <option value='-1'>当前频道的所有专题</option>
    <option value='-2'>不对专题考虑</option>
   #{foreach special in special_list }
    <option value='#{special.id }'>#{special.name }(#{iif (special.channelId == 0, "全站", "本频道") })</option>
   #{/foreach }
   </select>
</pub:template>

<%-- 没有指定频道时候的缺省栏目,专题值 --%>
<pub:template name="tr_default_column_special">
 <tr style="display: none;"><td></td>
  <td>
   <input type="hidden" value="0" __def_value='0' name="ColumnId" id="columnId" />
   <input type="checkbox" name='IncludeChild' value='true' __def_value='true' id="includeChild" checked="checked" />
   <input type="hidden" name="SpecialId" value="0" __def_value='0' id="specialId" />
  </td>
 </tr>
</pub:template>

<pub:template name="tr_label_desc">
 <tr class='tdbg'>
  <td height='25' align='right' class='tdbg5'><strong>标签说明：</strong></td>
  <td height='25'>
    <input type='text' name='LabelDesc' value='' __def_value='' id='labelDesc' size='15' maxlength='20'>
    &nbsp;&nbsp;<font style='font-size:12px' color='blue'>请在这里填写标签的使用说明方便以后的查找</font>
  </td>
 </tr>
</pub:template>

<pub:template name="tr_item_num">
 <tr class='tdbg'>
  <td height='25' align='right' class='tdbg5'><strong>#{itemName}数目：</strong></td>
  <td height='25'>
   <input name='ItemNum' type='text' value='6' __def_value='20' size='5' id='itemNum' 
    maxlength='3'>&nbsp;&nbsp;&nbsp;&nbsp;<font color='#FF0000'>如果为0，将显示缺省20个#{itemName }。</font>
  </td>
 </tr>
</pub:template>

<pub:template name="tr_item_filter">
 <tr class='tdbg'>
  <td height='25' align='right' class='tdbg5'><strong>#{itemName }属性：</strong></td>
  <td height='25'>
   <input name='IsTop' type='checkbox' id='isTop' value='true' __false_value='' 
      __def_value='' /> 固顶#{itemName }&nbsp;&nbsp;
   <input name='IsCommend' type='checkbox' id='isCommend' value='true' __false_value='' 
      __def_value='' />推荐#{itemName }&nbsp;&nbsp;
   <input name='IsElite' type='checkbox' id='isElite' value='true' __false_value='' 
      __def_value='' />精华#{itemName }&nbsp;&nbsp;
   <input name='IsHot' type='checkbox' id='isHot' value='true' __false_value='' 
      __def_value='' /> 热门#{itemName }&nbsp;&nbsp;
   <font color='#FF0000'>如果都不选，将显示所有#{itemName }。</font>
  </td>
 </tr>
</pub:template>


<pub:template name="tr_item_author">
 <tr class='tdbg'>
  <td height='25' align='right' class='tdbg5'><strong>作者姓名：</strong></td>
  <td height='25'>
   <input name='Author' id='author' type='text' value='' __def_value='' size='10' maxlength='10'>&nbsp;&nbsp;&nbsp;&nbsp;
   <font color='#FF0000'>如果不为空，则只显示指定录入者的文章，用于个人文集。</font>
  </td>
 </tr>
</pub:template>


<pub:template name="tr_pic_type">
<tr class='tdbg'>
 <td height='25' align='right' class='tdbg5'><strong>#{itemName }属性图片：</strong></td>
 <td height='25'>
 <table border='0' cellpadding='0' cellspacing='0' width='100%' height='100%' valign='top'>
  <tr>
   <td width='100'>
   <select name='PicType' id='picType' __def_value='0' onchange="javascript:change_item(this)">
    <option value='0'>不显示</option>
    <option value='2'>符号</option>
    <option value='1'>小图片（样式 1）</option>
    <option value='3'>小图片（样式 2）</option>
    <option value='4'>小图片（样式 3）</option>
    <option value='5'>小图片（样式 4）</option>
    <option value='6'>小图片（样式 5）</option>
    <option value='7'>小图片（样式 6）</option>
    <option value='8'>小图片（样式 7）</option>
    <option value='9'>小图片（样式 8）</option>
    <option value='10'>小图片（样式 9）</option>
   </select></td>
   <td id='objFiles' style='display:none'>
    &nbsp;普通图片&nbsp;<img id="common" src='../../images/item/common.gif' border='0' alt='普通图片'>
    &nbsp;推荐图片&nbsp;<img src='../images/item/elite.gif' id="elite" border='0' alt='推荐图片'>
    &nbsp;固顶图片&nbsp;<img src='../images/item/ontop.gif' id="ontop" border='0' alt='固顶图片'>
    </td>
  </tr>
 </table>
<script language='javascript'>
function change_item(element){
  if(element.selectedIndex!=-1)
  var selectednumber = element.options[element.selectedIndex].value;

  if(selectednumber==1){
    objFiles.style.display="";
      
    $("common").src = "../../images/item/common.gif";
    $("elite").src = "../../images/item/elite.gif";
    $("ontop").src = "../../images/item/ontop.gif";
  } else if (selectednumber==0) {
    objFiles.style.display="none";
  } else if (selectednumber==2) {
    objFiles.style.display="none";
  } else if (selectednumber>=3) {
    selectednumber = selectednumber - 1
    objFiles.style.display="";
    $("common").src = "../../images/item/common" + selectednumber + ".gif";
    $("elite").src = "../../images/item/elite" + selectednumber + ".gif";
    $("ontop").src = "../../images/item/ontop" + selectednumber + ".gif";
  }
}
</script>
 </td>
</tr>
</pub:template>


<pub:template name="tr_date_scope">
 <tr class='tdbg'>
  <td height='25' align='right' class='tdbg5'><strong>日期范围：</strong></td>
  <td height='25'>
    只显示最近 <input name='DateScope' type='text' id='dateScope' value='0' 
     __def_value='0' size='5' maxlength='3'> 天内更新的#{itemName }&nbsp;&nbsp;&nbsp;&nbsp;
    <font color='#FF0000'>&nbsp;&nbsp;如果为空或0，则显示所有天数的#{itemName }。</font>
  </td>
 </tr>
</pub:template>

<pub:template name="tr_order_type">
 <tr class='tdbg'>
  <td height='25' align='right' class='tdbg5'><strong>排序方法：</strong></td>
  <td height='25'>
   <select name='OrderType' id='orderType' __def_value=''>
    <option value=''>#{itemName }ID（降序）</option>
    <option value='1' _value='id ASC'>#{itemName }ID（升序）</option>
    <option value='2' _value='lastModified DESC'>更新时间（降序）</option>
    <option value='3' _value='lastModified ASC'>更新时间（升序）</option>
    <option value='4' _value='hits DESC'>点击次数（降序）</option>
    <option value='5' _value='hits ASC'>点击次数（升序）</option>
    <!-- more order type is declared by specified channel -->
   </select>
  </td>
 </tr>
</pub:template>

<pub:template name="tr_pic_size">
 <tr class='tdbg'>
  <td height='25' align='right' class='tdbg5'><b>图片设置：</b></td>
  <td height='25'>
   &nbsp;宽度： <input name='PicWidth' type='text' id='picWidth' value='' __def_value='' 
    size='5' maxlength='3' /> 像素&nbsp;&nbsp;&nbsp;
   &nbsp;高度： <input name='PicHeight' type='text' id='picHeight' value='' __def_value='' 
    size='5' maxlength='3' /> 像素.  (不填写表示使用缺省值)
  </td>
 </tr>
</pub:template>

<pub:template name="tr_title_len">
 <tr class='tdbg'>
  <td height='25' align='right' class='tdbg5'><strong>标题最多字符数：</strong></td>
  <td height='25'>
   <input name='TitleCharNum' type='text' id='titleCharNum' value='' __def_value='' 
     size='5' maxlength='3'> &nbsp;&nbsp;&nbsp;&nbsp;
     <font color='#FF0000'>如果为空，则显示完整标题。字母算一个字符，汉字算两个字符。</font>
  </td>
 </tr>
</pub:template>

<pub:template name="tr_content_len">
#{param style_display }
 <tr class='tdbg' style='display: #{style_display }'>
  <td height='25' align='right' class='tdbg5'><strong>#{itemName }介绍字符数：</strong></td>
  <td height='25'>
   <input name='ContentNum' type='text' id='contentNum' value='' __def_value='' size='5' 
    maxlength='3' /> &nbsp;&nbsp;&nbsp;&nbsp;
     <font color='#FF0000'>如果大于0，则在标题下方面显示指定字数的#{itemName }介绍</font>
  </td>
 </tr>
</pub:template>


<pub:template name="tr_open_type">
 <tr class='tdbg'>
  <td height='25' align='right' class='tdbg5'><strong>#{itemName }打开方式：</strong></td>
  <td height='25'><select name='OpenType' id='openType' __def_value=''>
   <option value=''>在原窗口打开</option>
   <option value='_blank'>在新窗口打开</option>
   </select>
  </td>
 </tr>
</pub:template>


<pub:template name="show_column">
 <input name='ShowColumn' type='checkbox'
      id='showColumn' value='true' __def_value='false' />所属栏目
</pub:template>

<pub:template name="show_author">
 <input name='ShowAuthor' type='checkbox' id='showAuthor'
     value='false' __def_value='false' />显示作者
</pub:template>

<pub:template name="show_last_modified">
   更新时间
   <select name='LastModified' id='lastModified' __def_value=''>
    <option value=''>不显示</option>
    <option value='MM-dd mm:ss'>月-日 小时:分(#{today()@format('MM-dd mm:ss') })</option>
    <option value='yyyyMMdd'>年月日(#{today()@format('yyyyMMdd') })</option>
    <option value='MMdd'>月日(#{today()@format('MMdd') })</option>
    <option value='MM-dd'>月-日(#{today()@format('MM-dd') })</option>
    <option value='yyyy-MM-dd'>年-月-日(#{today()@format('yyyy-MM-dd') })</option>
    <option value='yyyy-MM-dd mm:ss'>年-月-日 小时:分(#{today()@format('yyyy-MM-dd mm:ss') })</option>
    <option value='yyyy年M月d日'>年月日(#{today()@format('yyyy年M月d日') })</option>
    <option value=''>更多格式可以参照上述方式给出...</option>
   </select>
</pub:template>

<pub:template name="show_hits">
     <input name='ShowHits' type='checkbox' id='showHits' value='true' 
      __def_value='false' />点击次数
</pub:template>

<pub:template name="show_hot">
     <input name='ShowHot' type='checkbox' id='showHot' value='true' 
      __def_value='false' />热点#{itemName }标志
</pub:template>

<pub:template name="show_new">
     <input name='ShowNew' type='checkbox' id='showNew' value='true'
      __def_value='false' />最新#{itemName }标志
</pub:template>

<pub:template name="tr_col_num">
 <tr class='tdbg'>
  <td height='25' align='right' class='tdbg5'><strong>每行显示#{itemName }数：</strong></td>
  <td height='25'>
   <select name='ColNum' id='colNum' __def_value=''>
    <option value=''>缺省(不分列)</option>
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
   </select> &nbsp;&nbsp;&nbsp;&nbsp;超过指行列数就会换行
  </td>
 </tr>
</pub:template>

<pub:template name="tr_css_3">
  <tr class='tdbg'>
   <td height='25' align='right' class='tdbg5'><strong>CSS类名：</strong></td>
   <td height='25'><input name='Style' id='style' type='text' value='' __def_value=''
    size='10' maxlength='20'>&nbsp;&nbsp;&nbsp;&nbsp;<font
    color='#FF0000'>列表中文字链接调用的CSS类名，可选参数(仅在表格式有效)</font></td>
  </tr>
  <tr class='tdbg'>
   <td height='25' align='right' class='tdbg5'><strong>风格样式1：</strong></td>
   <td height='25'><input name='Class1' id='class1' type='text' value='' __def_value=''
    size='10' maxlength='20'>&nbsp;&nbsp;&nbsp;&nbsp;<font
    color='#FF0000'>列表中奇数行的CSS效果的类名，可选参数(仅在表格式有效)</font></td>
  </tr>
  <tr class='tdbg'>
   <td height='25' align='right' class='tdbg5'><strong>风格样式2：</strong></td>
   <td height='25'><input name='Class2' id='class2' type='text' value='' __def_value=''
    size='10' maxlength='20'>&nbsp;&nbsp;&nbsp;&nbsp;<font
    color='#FF0000'>列表中偶数行的CSS效果的类名，可选参数(仅在表格式有效)</font></td>
  </tr>
</pub:template>
