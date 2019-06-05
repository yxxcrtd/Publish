<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="pub" uri="/WEB-INF/publish.tld" %>
<%-- 这是一个被包含页面，其用于定义 Tabs/Tab 显示模板 --%>

<%--  tab_js - 属性页(Tabs)客户端切换 javascript 脚本。--%>
<pub:template name="tab_js">
<script language="javascript">
<!--
var __defaultTabName = '';
function showTab(name) {
  if (__defaultTabName != name) {
    setTabTitleStyle(__defaultTabName, 'title5');
    setTabTitleStyle(name, 'title6');
    setTabDisplay(name, '');
    setTabDisplay(__defaultTabName, 'none');
    __defaultTabName = name;
  }
}

// 设置指定标识为 'Tabs' 的显示属性.
function setTabDisplay(name, display) {
  for (var i = 0; i < Tabs.length; ++i) {
    if (Tabs[i].name == name) {
      Tabs[i].style.display = display;
      return;
    }
  }
}

// 设置指定 id 为 'TabTitle' 的显示属性.
function setTabTitleStyle(name, style) {
  for (var i = 0; i < TabTitle.length; ++i) {
    if (TabTitle[i].name == name) {
      TabTitle[i].className = style;
      return;
    }
  }
  return null;
}

// 隐藏、显示除第一个标签以外的其他标签.
function HideTabTitle(displayValue,tempType){
  for (var i = 1; i < TabTitle.length; i++) {
    if(tempType==0&&i==2) {
        TabTitle[i].style.display='none';
    }
    else{
        TabTitle[i].style.display=displayValue;
    }
  }
}
//-->
</script>
</pub:template>

<%-- 
  tab_header - 属性页(Tabs)的头显示模板。
  @param tabs - 属性页集合对象，由调用者传递进来。
--%>
<pub:template name="tab_header">
#{param tabs}
<table width="100%" border="0" cellpadding="0" cellspacing="0">
 <tr align="center" height="22">
 #{foreach tab in tabs}
  <td id="TabTitle" name="#{tab.name}" class="#{iif(tab@is_first, 'title6', 'title5')}" 
    onclick="showTab('#{tab.name}')">
  #{tab.text}
  </td>
 #{/foreach}
  <td>&nbsp;</td>
 </tr>
</table>
<script language="javascript"><!--
// 默认显示的标签页.
__defaultTabName = '#{(tabs.default.name)}';
// -->
</script>
</pub:template>

<%--
  tab_content - 属性页(Tabs)内容显示模板，其遍历所有属性页并动态调用属性页的显示模板。
  @param tabs - 属性页对象，由调用者传递进来。
--%>
<pub:template name="tab_content">
#{param tabs}
<table width="100%" border="0" cellpadding="5" cellspacing="1" class="border">
 <tr>
  <td class="tdbg">
  #{foreach tab in tabs}
  <div id='Tabs' name='#{tab.name}' style="display:#{iif(tab@is_first, '', 'none')};">
   #{call dynamic(tab.template, tab) }
  </div>
  #{/foreach}
   </td>
 </tr>
</table>
</pub:template>
