<%@ page language="java" contentType="text/html; charset=gb2312"
	pageEncoding="UTF-8"%>
<jsp:directive.page import="java.text.SimpleDateFormat"/>
<%@taglib prefix="pub" uri="/WEB-INF/publish.tld"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<link href="admin_style.css" rel="stylesheet" type="text/css" />
<title>生成管理</title>
<script type="text/javascript" src="../js/dojo.js"></script>
<script type="text/javascript" src="main.js"></script>
<script type="text/javascript">
<!--
	dojo.require("dojo.widget.DropdownDatePicker");
//-->
</script>
</head>
<body>
<!-- 定义系统内建 '生成管理' 的属性页. -->
<pub:tabs var="generate_tabs" scope="page" purpose="频道属性的属性页集合">
	<pub:tab name="item_generate" text="内容页生成" template="item_generate_template" />
	<pub:tab name="column_generate" text="栏目页生成" template="column_generate_template" />
	<pub:tab name="special_generate" text="专题页生成" template="special_generate_template" />
	<pub:tab name="else_generate" text="其他页生成" template="else_generate_template" />
	<!--  更多 Tab 放在这里 -->
</pub:tabs>

<!-- 为这个页面获取 Channel 的数据 -->
<pub:data var="channel" scope="page" 
	provider="com.chinaedustar.publish.admin.ChannelDataProvider" />
	
<!-- 定义下拉栏目列表的数据。 -->
<pub:data var="dropdown_columns" 
	provider="com.chinaedustar.publish.admin.ColumnsDropDownDataProvider"
	param="0" />
	
<!-- 获得频道的专题列表的数据。 -->
<pub:data var="special_list" 
	provider="com.chinaedustar.publish.admin.ChannelSpecialsDataProvider"
	 />
	
<%@ include file="tabs_tmpl2.jsp"%>
<%@ include file="element_column.jsp"%>

<!-- 主模板 -->
<pub:template name="main">
  <table width='100%' border='0' align='center' cellpadding='2' cellspacing='1' class='border'>
  <tr class='topbg'>
    <td height='22' colspan='2' align='center'><strong>#{channel.name }生成管理</strong></td>
  </tr>
  <tr class='tdbg'>
    <td width='70' height='30'><strong>生成说明：</strong></td>
    <td>生成操作比较消耗系统资源及费时，每次生成时，请尽量减少要生成的文件量。    </td>
  </tr>
</table>
<br/>
#{call tab_js()}
#{call tab_header(generate_tabs)}
#{call tab_content(generate_tabs)}
</pub:template>


<!-- 内容页生成 -->
<pub:template name="item_generate_template">
	<tr class='tdbg'>
		<td align="center">
			<table width='600' border='0' align='center' cellpadding='0' cellspacing='0' style="margin:10px;">
				<tr>
					<td>
						<form method='post' action='admin_page_generate_action.jsp?command=newItem'>
							生成最新<input name='count' id='count' value='50' size="8" maxlength='10'>#{channel.itemUnit }#{channel.itemName }&nbsp;
							<input name='channelId' type='hidden' id='channelId' value='#{channel.id }'>
							<input name='submit' type='submit' id='submit' value='开始生成>>'>
						</form>
					</td>
				</tr>
				<tr>
					<td>
						<form  method='post' action='admin_page_generate_action.jsp?command=timeScope'>
							生成更新时间为	<input readonly="readonly" name='beginDate' maxlength='20' dojoType="dropdowndatepicker" 
			value="<%=new SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date()) %>" containerToggle="explode" containerToggleDuration="100"> 到
							<input name='endDate' readonly maxlength='20' dojoType="dropdowndatepicker" 
			value="<%=new SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date()) %>" containerToggle="explode" containerToggleDuration="100"> 的#{channel.itemName }
							<input name='channelId' type='hidden' id='channelId' value='#{channel.id }'>
							<input name='submit' type='submit' id='submit' value='开始生成>>'>
						</form>
					</td>
				</tr>
				<tr>
					<td>
						<form method='post' action='admin_page_generate_action.jsp?command=idScope'>
							生成ID号为<input name='beginId' type='text' id='beginId' value='1' size="8" maxlength='10'> 到
							<input name='endId' type='text' id='endId' value='100' size=8 maxlength='10'> 的#{channel.itemName }
							<input name='channelId' type='hidden' id='channelId' value='#{channel.id }'>
							<input name='submit' type='submit' id='submit' value='开始生成>>'>
						</form>
					</td>
				</tr>
				<tr>
					<td>
						<form method='post' action='admin_page_generate_action.jsp?command=certainItem'>
							生成指定ID的#{channel.itemName }（多个ID可用逗号隔开）：
							<input name='itemId' type='text' id='itemId' value='1,3,5' size='20'>							
							<input name='channelId' type='hidden' id='channelId' value='#{channel.id }'>
							<input name='submit' type='submit' id='submit' value='开始生成>>'>
						</form>
					</td>
				</tr>
				<tr>
					<td>
						<form method='post' action='admin_page_generate_action.jsp?command=certainColumn'>
							生成指定栏目的#{channel.itemName }：
							<select name='columnId'>
								#{call dropDownColumns(0, dropdown_columns) }
							</select>
							<input type="checkbox" name="includeChild" id="includeChild"/>包括子栏目
							<input name='channelId' type='hidden' id='channelId' value='#{channel.id }'>
							<input name='submit' type='submit' id='submit' value='开始生成>>'>
						</form>
					</td>
				</tr>
				<tr>
					<td>
						<form method='post' action='admin_page_generate_action.jsp?command=ungenerated'>
							生成所有未生成的#{channel.itemName }
							<input name='channelId' type='hidden' id='channelId' value='#{channel.id }'>
							<input name='submit' type='submit' id='submit' value='开始生成>>'>
						</form>
					</td>
				</tr>
				<tr>
					<td>
						<form method='post' action='admin_page_generate_action.jsp?command=allItems'>
							生成所有#{channel.itemName }
							<input name='channelId' type='hidden' id='channelId' value='#{channel.id }'>
							<input name='submit' type='submit' id='submit' value='开始生成>>'>
						</form>
					</td>
				</tr>
			</table>
		</td>
	</tr>
</pub:template>

<!-- 栏目页生成 -->
<pub:template name="column_generate_template">
	<tr class="tdbg">
		<td>
			<form method='post' action='admin_page_generate_action.jsp?command=columnList'>
			<table width='600' border='0' align='center' cellpadding='0' cellspacing='0' style="margin:10px;">
				<tr>
					<td>
						<select name='columnId' size='2' multiple style='height:300px;width:300px;'>
						  #{call dropDownColumns(0, dropdown_columns) }
						</select>
					</td>
					<td>
						<input name='param' type='hidden' id='cParam' value='0'/>
						<input name='channelId' type='hidden' id='channelId' value='#{channel.id }'>
						<input type='submit' name='submit' value='生成选定栏目的首页' style='cursor:pointer;' onclick='byId("cParam").value=2;'><br/><br/>
						<input type='submit' name='submit' onclick='byId("cParam").value=3;' value='生成所有栏目的首页' style='cursor:pointer;'> <br/><br/>
						
						<input type='submit' name='submit' value='生成选定栏目的列表页' style='cursor:pointer;' onclick='byId("cParam").value=0;'><br/><br/>
						<input type='submit' name='submit' onclick='byId("cParam").value=1;' value='生成所有栏目的列表页' style='cursor:pointer;'> <br/>
						 提示：<br>可以按住“CTRL”或“Shift”键进行多选 
					</td>
				</tr>
			</table>
			</form>
		</td>
	</tr>
</pub:template>

<!-- 专题页生成 -->
<pub:template name="special_generate_template">
	<tr class="tdbg">
		<td>
			<form method='post' action='admin_page_generate_action.jsp?command=specialList'>
			<table width='600' border='0' align='center' cellpadding='0' cellspacing='0' style="margin:10px;">
				<tr>
					<td>
						<select name='specialId' size='2' multiple style='height:300px;width:300px;'>
						  #{foreach special in special_list }
						  <option value="#{special.id }">#{special.name }</option>
						  #{/foreach }
						</select>
					</td>
					<td>
						<input name='param' type='hidden' id='sParam' value='0'/>
						<input name='channelId' type='hidden' id='channelId' value='#{channel.id }'>
						<input type='submit' name='submit' value='生成选定专题的列表页' style='cursor:pointer;' onclick='byId("sParam").value=0;'><br/><br/>
						<input type='submit' name='submit' onclick='byId("sParam").value=1;' value='生成所有专题的列表页' style='cursor:pointer;'> <br/>
						 提示：<br>可以按住“CTRL”或“Shift”键进行多选 
					</td>
				</tr>
			</table>
			</form>
		</td>
	</tr>
</pub:template>

<!-- 其他页生成 -->
<pub:template name="else_generate_template">
	<tr class="tdbg">
		<td>
			<form method='post' action='admin_page_generate_action.jsp?command=channel'>
			<table width='600' border='0' align='center' cellpadding='0' cellspacing='0' style="margin:10px;">
				<tr>
					<td>
						<input name='channelId' type='hidden' id='channelId' value='#{channel.id }'>
						<input name='submit' type='submit' id='submit' value=' 生成新闻中心首页 '>
					</td>
				</tr>
			</table>
			</form>
		</td>
	</tr>
</pub:template>

<pub:process_template name="main" />
</body>
</html>
