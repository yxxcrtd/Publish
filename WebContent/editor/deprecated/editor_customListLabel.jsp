<%@ page language="java" contentType="text/html; charset=gb2312"
    pageEncoding="UTF-8"%>
<%@page import="com.chinaedustar.publish.ParamUtil"%>
<%!private String GetSpecial_Option(int iChannelId, int specialId) {
	/*
Dim sqlSpecial, rsSpecial, strOption,strOptionTemp
sqlSpecial = "select ChannelID,SpecialID,SpecialName,OrderID from PE_Special where ChannelID=0 or ChannelID=" & iChannelID & "   order by ChannelID,OrderID"
Set rsSpecial = Conn.Execute(sqlSpecial)
If LCase(SpecialID) <> "specialid" Then
    If PE_CLng(SpecialID) = 0 Then
        strOption = "<option value='0'>不属于任何专题</option>"
    Else
        strOption = "<option value='0' selected>不属于任何专题</option>"
    End If
End If
If rsSpecial.bof And rsSpecial.bof Then
Else
    Do While Not rsSpecial.EOF
        If rsSpecial("ChannelID") > 0 Then
            strOptionTemp = rsSpecial("SpecialName") & "（本频道）"
        Else
            strOptionTemp = rsSpecial("SpecialName") & "（全站）"
        End If
        If rsSpecial("SpecialID") = PE_CLng(SpecialID) Then
            strOption = strOption & "<option value='" & rsSpecial("SpecialID") & "' selected>" & strOptionTemp & "</option>"
        Else
            strOption = strOption & "<option value='" & rsSpecial("SpecialID") & "'>" & strOptionTemp & "</option>"
        End If
        rsSpecial.movenext
    Loop
End If
rsSpecial.Close
Set rsSpecial = Nothing
strOption = strOption & "<option value='SpecialID'"
If SpecialID = "SpecialID" Then strOption = strOption & " selected"
strOption = strOption & ">当前频道</option>"
*/
//	return strOption;
return "";
}

private String GetChannel_Option(int iModuleType, int channelId) {
	/*
Dim strChannel, sqlChannel, rsChannel
sqlChannel = "select ChannelID,ChannelName from PE_Channel  where ModuleType=" & iModuleType & " and Disabled=" & PE_False & " and ChannelType<=1 order by OrderID"
Set rsChannel = Conn.Execute(sqlChannel)
Do While Not rsChannel.EOF
    If rsChannel(0) = PE_CLng(ChannelID) Then
        strChannel = strChannel & "<option value='" & rsChannel(0) & "' selected>" & rsChannel(1) & "</option>"
    Else
        strChannel = strChannel & "<option value='" & rsChannel(0) & "'>" & rsChannel(1) & "</option>"
    End If
    rsChannel.movenext
Loop
rsChannel.Close
Set rsChannel = Nothing
strChannel = strChannel & "<option value='0'"
If ChannelID = "0" Then strChannel = strChannel & " selected"
strChannel = strChannel & ">所有同类频道</option>"
strChannel = strChannel & "<option value='ChannelID'"
If ChannelID = "ChannelID" Then strChannel = strChannel & " selected"
strChannel = strChannel & ">当前频道</option>"
*/
//	return strChannel;
return "";
}

private String GetClass_Channel(int channelId, int columnId, boolean nColumnId) {
	/*
Dim rsClass, sqlClass, strClass_Option, tmpDepth, i, classcss
Dim arrShowLine(20)
For i = 0 To UBound(arrShowLine)
arrShowLine(i) = False
Next
sqlClass = "Select * from PE_Class where ChannelID=" & ChannelID & " order by RootID,OrderID"
Set rsClass = Conn.Execute(sqlClass)
If rsClass.bof And rsClass.bof Then
strClass_Option = strClass_Option & "<option value='0'>请先添加栏目</option>"
Else
    Do While Not rsClass.EOF
        tmpDepth = rsClass("Depth")
        If rsClass("NextID") > 0 Then
            arrShowLine(tmpDepth) = True
        Else
            arrShowLine(tmpDepth) = False
        End If

        If rsClass("ClassType") = 2 Then
            strClass_Option = strClass_Option & "<option value=''"
        Else
            strClass_Option = strClass_Option & "<option value='" & rsClass("ClassID") & "'"
            If NClassID = False Then
                If ClassID <> "rsClass_arrChildID" Or ClassID <> "ClassID" Or ClassID <> "arrChildID" Then
                    If rsClass("ClassID") = PE_CLng(ClassID) Then
                        strClass_Option = strClass_Option & " selected"
                    End If
                End If
            Else
                If FoundInArr(ClassID, rsClass("ClassID"), "|") = True Then
                    strClass_Option = strClass_Option & " selected"
                End If
            End If
        End If
        strClass_Option = strClass_Option & ">"
        
        If tmpDepth > 0 Then
        For i = 1 To tmpDepth
            strClass_Option = strClass_Option & "&nbsp;&nbsp;"
            If i = tmpDepth Then
            If rsClass("NextID") > 0 Then
                strClass_Option = strClass_Option & "├&nbsp;"
            Else
                strClass_Option = strClass_Option & "└&nbsp;"
            End If
            Else
            If arrShowLine(i) = True Then
                strClass_Option = strClass_Option & "│"
            Else
                strClass_Option = strClass_Option & "&nbsp;"
            End If
            End If
        Next
        End If
        strClass_Option = strClass_Option & rsClass("ClassName")
        If rsClass("ClassType") = 2 Then
            strClass_Option = strClass_Option & "（外）"
        End If
        strClass_Option = strClass_Option & "</option>"
        rsClass.movenext
    Loop
End If
rsClass.Close
Set rsClass = Nothing
If NClassID = False Then
    classcss = "style=''"
Else
    classcss = "style='background:red'"
End If

If Trim(ClassID) = "rsClass_arrChildID" Then
    strClass_Option = strClass_Option & "<option value='rsClass_arrChildID' " & classcss & " selected >栏目循环中的栏目</option>"
Else
    strClass_Option = strClass_Option & "<option value='rsClass_arrChildID' " & classcss & " >栏目循环中的栏目</option>"
End If
If Trim(ClassID) = "ClassID" Then
    strClass_Option = strClass_Option & "<option value='ClassID' " & classcss & " selected>当前栏目（不包含子栏目）</option>"
Else
    strClass_Option = strClass_Option & "<option value='ClassID' " & classcss & ">当前栏目（不包含子栏目）</option>"
End If
If Trim(ClassID) = "arrChildID" Then
    strClass_Option = strClass_Option & "<option value='arrChildID' " & classcss & " selected>当前栏目及子栏目</option>"
Else
    strClass_Option = strClass_Option & "<option value='arrChildID' " & classcss & ">当前栏目及子栏目</option>"
End If
If Trim(ClassID) = "0" Then
    strClass_Option = strClass_Option & "<option value='0' " & classcss & " selected>显示所有栏目</option>"
Else
    strClass_Option = strClass_Option & "<option value='0' " & classcss & ">显示所有栏目</option>"
End If
*/
//return strClass_Option;
    return "";
}

private void FoundInArr(String strArr, String strItem, String strSplit) {
	/*
Dim arrTemp, i
FoundInArr = False
If InStr(strArr, strSplit) > 0 Then
    arrTemp = Split(strArr, strSplit)
    For i = 0 To UBound(arrTemp)
        If Trim(arrTemp(i)) = Trim(strItem) Then
            FoundInArr = True
            Exit For
        End If
    Next
Else
    If Trim(strArr) = Trim(strItem) Then
        FoundInArr = True
    End If
End If
*/
}%>
<%
ParamUtil paramUtil = new ParamUtil(pageContext);
String action, title, channelShortName, channelShowType, imageproperty;

String labletemp;
boolean nColumnId, editLabel, includeChild;
int moduleType, columnId, specialId, num, productType, isHot, isElite, authorName, dateNum;
int orderType, showType, titleLen, contentLen, showClassName, showPropertyType, showIncludePic, showAuthor;
int showDateType, showHits, showHotSign, showNewSign, showTips, showCommentLink, usePage, openType, cols;
int imgWidth, imgHeight, iTimeOut, urltype, cssNameA, cssName1, cssName2, effectId;
int templateId, template;
int channelId, iChannelId, dChannelId;

channelId = paramUtil.safeGetIntParam("channelId", 0);
dChannelId = paramUtil.safeGetIntParam("dChannelId", 0);

templateId = paramUtil.safeGetIntParam("templateId", 0);
iChannelId = 0;
imageproperty = "";
channelShortName = "";
columnId = 0;
productType = 0;
nColumnId = false;
includeChild = false;
editLabel = false;

if (dChannelId == 0) {
   dChannelId = channelId;
}
if (channelId == 0 && iChannelId ==0) {
    out.println("频道参数丢失！");
    out.close();
}

if (channelId == 0) {
    iChannelId = dChannelId;
} else {
    iChannelId = channelId;
}

action = paramUtil.getRequestParam("action");
title =  paramUtil.getRequestParam("title");
moduleType =  paramUtil.safeGetIntParam("moduleType");
channelShowType =  paramUtil.getRequestParam("channelShowType");
   
if (moduleType == 1) {
    channelShortName = "文章";
    imageproperty = "Article";
} else if (moduleType == 2) {
    channelShortName = "下载";
    imageproperty = "Soft";
} else if (moduleType == 3) {
    channelShortName = "图片";
    imageproperty = "Photo";
} else if (moduleType == 5) {
    iChannelId = 1000;
    channelShortName = "商品";
    imageproperty = "Product";
}

if (!"".equalsIgnoreCase(paramUtil.getRequestParam("editLabel"))) {
    editLabel = true;
}

out.println("<html>");
out.println("<head>");
out.println("<title>" + title + "</title>");
out.println("<meta http-equiv='Content-Type' content='text/html; charset=gb2312'>");
out.println("<script language=\"javascript\">");
out.println("function NClassIDChild(){");
out.println("    if (document.myform.NClassChild.checked==true){");
out.println("        document.myform.ClassID.size=2;");
out.println("        document.myform.ClassID.style.height=250;");
out.println("        document.myform.ClassID.style.width=400;");
out.println("        document.myform.ClassID.multiple=true");
out.println("        for(var i=0;i<document.myform.ClassID.length;i++){");
out.println("            if (document.myform.ClassID.options[i].value==\"rsClass_arrChildID\"||document.myform.ClassID.options[i].value==\"ClassID\"||document.myform.ClassID.options[i].value==\"arrChildID\"||document.myform.ClassID.options[i].value==\"0\"){");
out.println("                document.myform.ClassID.options[i].style.background=\"red\";");
out.println("            }");
out.println("        }");
out.println("    }else{");
out.println("        document.myform.ClassID.size=1;");
out.println("        document.myform.ClassID.style.width=200;");
out.println("        document.myform.ClassID.multiple=false;");
out.println("        for(var i=0;i<document.myform.ClassID.length;i++){");
out.println("            if (document.myform.ClassID.options[i].value==\"rsClass_arrChildID\"||document.myform.ClassID.options[i].value==\"ClassID\"||document.myform.ClassID.options[i].value==\"arrChildID\"||document.myform.ClassID.options[i].value==\"0\"){");
out.println("                document.myform.ClassID.options[i].style.background=\"\";");
out.println("            }");
out.println("        }");
out.println("    }");
out.println("}");
out.println("function objectTag() {");
out.println("    var strJS,OrderType;");
out.println("    if (document.myform.ClassID.value==''){");
out.println("        alert('所属栏目不能指定为外部栏目！');");
out.println("        document.myform.ClassID.focus();");
out.println("        return false;");
out.println("    }");
out.println("    var UsePage,ShowAll;");
out.println("    for (var i=0;i<document.myform.UsePage.length;i++){");
out.println("    var PowerEasy = document.myform.UsePage[i];");
out.println("    if (PowerEasy.checked==true)       ");
out.println("        UsePage = PowerEasy.value");
out.println("    }");
//If ModuleType = 2 Then
//    out.println("    for (var i=0;i<document.myform.ShowAll.length;i++){");
//    out.println("    var PowerEasy = document.myform.ShowAll[i];");
//    out.println("    if (PowerEasy.checked==true)       ");
//    out.println("        ShowAll = PowerEasy.value");
//    out.println("    }");
//End If
out.println("    strJS=\"【" + imageproperty + "List(\";");
if (moduleType == 1 || moduleType == 2 || moduleType == 3) {
    out.println("strJS+=document.myform.iChannelID.value+ \",\";");
}
out.println("    if (document.myform.NClassChild.checked==true){");
out.println("        var Nclassidzhi=\"\"");
out.println("        for(var i=0;i<document.myform.ClassID.length;i++){");
out.println("            if (document.myform.ClassID.options[i].selected==true){");
out.println("                if (document.myform.ClassID.options[i].value==\"rsClass_arrChildID\"||document.myform.ClassID.options[i].value==\"ClassID\"||document.myform.ClassID.options[i].value==\"arrChildID\"||document.myform.ClassID.options[i].value==\"0\"){");
out.println("                    alert(\"您在多选中选择了红色部分，多选栏目中是不能包含那部分的。\");");
out.println("                    return false");
out.println("                }else{");
out.println("                    if (Nclassidzhi==\"\"){");
out.println("                        Nclassidzhi+=document.myform.ClassID.options[i].value;");
out.println("                    }else{");
out.println("                        Nclassidzhi+=\"|\"+document.myform.ClassID.options[i].value;");
out.println("                    }");
out.println("                }");
out.println("            }");
out.println("        }");
out.println("        strJS+=Nclassidzhi;");
out.println("    }else{");
out.println("        strJS+=document.myform.ClassID.value;");
out.println("    }");
if (moduleType == 1 || moduleType == 2 || moduleType == 3 || moduleType == 5) {
    out.println("strJS+=\",\"+document.myform.IncludeChild.checked;");
    out.println("strJS+=\",\"+document.myform.SpecialID.value;");
    out.println("strJS+=\",\"+document.myform.ItemNum.value;");
}
if (moduleType == 5) {
    out.println("strJS+=\",\"+document.myform.ProductType.value;");
}
out.println("strJS+=\",\"+document.myform.IsHot.checked;");
out.println("strJS+=\",\"+document.myform.IsElite.checked;");
if (moduleType != 5) {
    out.println("strJS+=\",\"+document.myform.AuthorName.value;");
}
out.println("strJS+=\",\"+document.myform.DateNum.value;");
out.println("strJS+=\",\"+document.myform.OrderType.value;");
out.println("strJS+=\",\"+UsePage;");

if (moduleType == 1 || moduleType == 2 || moduleType == 3 || moduleType == 5) {
    out.println("strJS+=\",\"+document.myform.TitleLen.value;");
}
out.println("strJS+=\",\"+document.myform.ContentLen.value;");
out.println("strJS+=\")】\";");
out.println(" if (document.myform.Cols.value!=0){");
out.println("    strJS+=\"【Cols=\"+document.myform.Cols.value+\"|\"+document.myform.ColsHtml.value+\"】\";");
out.println("}");
out.println(" if (document.myform.Rows.value!=0){");
out.println("    strJS+=\"【Rows=\"+document.myform.Rows.value+\"|\"+document.myform.RowsHtml.value+\"】\";");
out.println("}");
out.println("strJS+=document.myform.Content.value;");
out.println("strJS+=\"【/" + imageproperty + "List】\";");
out.println("window.returnValue = strJS;");
out.println("window.close();");
out.println("}");
out.println("function insertLabel(strLabel)");
out.println("{");
out.println("  myform.Content.focus();");
out.println("  var str = document.selection.createRange();");
out.println("  str.text = strLabel");
out.println("}");
out.println(" function previewContent() {");
out.println("    var Content=document.myform.Content.value");
out.println("    Content = Content.replace(\"&\", \"{$ID}\");");
out.println("    window.showModalDialog(\"editor_previewContent.jsp?Content=\"+Content+\"&ChannelID=" + channelId + ",toolbar=no, menubar=no, top=0,left=0,dialogwidth=800,dialogheight=600,help: no; scroll:yes; status: yes\");");
out.println(" }");
out.println("</script>");

out.println("<link href='admin_style.css' rel='stylesheet' type='text/css'>");
out.println("<base target=\"_self\">");
out.println("</head>");
out.println("<body>");
out.println("<form action='editor_CustomListLabel.jsp' method='post' name='myform' id='myform'>");
out.println("<table width='700' border='0' align='center' cellpadding='2' cellspacing='1' class='border'>  ");
out.println("    <tr class=title>");
out.println("      <td align=middle colSpan=2 height=22><STRONG>" + channelShortName + "自定义列表标签 </STRONG></td>");
out.println("    </tr>");

if (moduleType != 5) {
    out.println("    <tr class='tdbg'>");
    out.println("      <td height='25' width='130'  class='tdbg5' align='right'><strong>所属频道：</strong></td>");
    out.println("      <td height='25' class='tdbg5'><input type='hidden' name='iChannelID' value='" + channelId + "'><select name='ChannelID' onChange='document.myform.submit();'>" + GetChannel_Option(moduleType, channelId) + "</select></td>");
    out.println("    </tr>");
}
if (iChannelId > 0) {
    out.println("    <tr class='tdbg'>");
    out.println("      <td height='25'  class='tdbg5' width='130' align='right'><strong>所属栏目：</strong></td>");
    out.println("      <td height='25' ><select name='ClassID' ");
    if (nColumnId == true) {
        out.println("size='2' multiple style='height:250px;width:400px;'");
    } else {
        out.println("size='1'");
    }
    out.println(">" + GetClass_Channel(iChannelId, columnId, nColumnId) + "</select>");
    out.println(" <input type='checkbox' name='IncludeChild' value='1' ");
    if (includeChild == true) {
	    out.println(" checked ");
    }
    out.println(" >包含子栏目&nbsp;&nbsp;<font color='red'><b>注意：</b></font>不能指定为外部栏目 </font>");
    out.println("  <br><input type='checkbox' name='NClassChild' value='1' onClick=\"javascript:NClassIDChild()\" ");
    if (nColumnId == true) {
	    out.println(" checked ");
    }
    out.println(" >是否选择多个栏目&nbsp;&nbsp;<font color='red'><b>注意：</b></font>多选红色的栏目不能选 </font>");
    out.println("      </td>");
    out.println("    </tr>");
} else {
    out.println("<INPUT TYPE='hidden' name='ClassID' value='0' >");
    out.println("<INPUT TYPE='hidden' name='NClassChild' value='0' >");
    out.println("<INPUT TYPE='hidden' name='IncludeChild' value='true' >");
    out.println("<INPUT TYPE='hidden' name='SpecialID' value='0' >");
}
out.println("    <tr class=tdbg>");
out.println("      <td width='130' class='tdbg5' align='right' height=25><STRONG>" + channelShortName + "数：</STRONG></td>");
out.println("      <td height=25><input type='text' name='ItemNum' value='0' size=\"8\">&nbsp;&nbsp;&nbsp;<font color='#FF0000'>如果为0，将显示所有" + channelShortName + "。</font></td>");
out.println("    </tr>");

if (moduleType == 5) {
    out.println("    <tr class='tdbg'>");
    out.println("      <td height='25' class='tdbg5' align='right'><strong> 产品类型：</strong></td>");
    out.println("      <td height='25' ><select name='ProductType' id='ProductType'>");
    out.println("        <option value='1'");
    if (productType == 1) out.println("selected");
    out.println(">正常销售商品</option>");
    out.println("        <option value='2'");
    if (productType == 2) out.println("selected");
    out.println(">涨价商品</option>");
    out.println("        <option value='3'");
    if (productType == 3) out.println("selected");
    out.println(">降价商品</option>");
    out.println("        <option value='4'");
    if (productType == 4) out.println("selected");
    out.println(">促销礼品</option>");
    out.println("        <option value='5'");
    if (productType == 5) out.println("selected");
    out.println(">正常销售和涨价商品</option>");
    out.println("        <option value='0'");
    if (productType == 0) out.println("selected");
    out.println(">所有商品</option>");
    out.println("        </select> </td>");
    out.println("    </tr>");
}
out.println("    <tr class=tdbg>");
out.println("      <td width=\"130\" class='tdbg5' align=right height=25><STRONG>" + channelShortName + "属性：</STRONG></td>");
out.println("      <td height=25 >");
out.println("        <Input id=IsHot type=checkbox value=1 name=IsHot> 热门" + channelShortName + "&nbsp;&nbsp;&nbsp;&nbsp;");
out.println("        <Input id=IsElite type=checkbox value=1 name=IsElite> 推荐" + channelShortName + "&nbsp;&nbsp;&nbsp;&nbsp;<FONT color=#ff0000>如果都不选，将显示所有文章。</FONT></td>");
out.println("    </tr>");

if (moduleType != 5) {
    out.println("    <tr class=tdbg>");
    out.println("      <td width=\"130\" class='tdbg5' align=right height=25><STRONG>显示指定作者的" + channelShortName + "：</STRONG></td>");
    out.println("      <td height=25 > ");
    out.println("         <Input id=AuthorName  maxLength=10 size=10 value='' name=AuthorName>&nbsp;&nbsp;&nbsp;&nbsp;<FONT color=#ff0000>如果都不添，将不指定。</FONT>");
    out.println("    </tr>");
}
out.println("    <tr class=tdbg>");
out.println("      <td width=\"130\" class='tdbg5' align=right height=25><STRONG>日期范围：</STRONG></td>");
out.println("      <td height=25>只显示最近 ");
out.println("        <Input id=DateNum maxLength=3 size=5 value=30 name=DateNum> 天内更新的"+ channelShortName + "&nbsp;&nbsp;&nbsp;&nbsp;<FONT color=#ff0000>&nbsp;&nbsp;如果为空或0，则显示所有天数的文章。</FONT></td>");
out.println("    </tr>");
out.println("    <tr class=tdbg>");
out.println("      <td width=\"130\" class='tdbg5' align=right height=25><STRONG>排序方法：</STRONG></td>");
out.println("      <td height=25 >");
out.println("        <Select id='OrderType' name='OrderType'> ");
out.println("          <Option value=1 selected>"+ channelShortName + "ID（降序）</Option> ");
out.println("          <Option value=2>"+ channelShortName + "ID（升序）</Option> ");
out.println("          <Option value=3>更新时间（降序）</Option> ");
out.println("          <Option value=4>更新时间（升序）</Option> ");
out.println("          <Option value=5>点击次数（降序）</Option> ");
out.println("          <Option value=6>点击次数（升序）</Option>");
out.println("        </Select>");
out.println("      </td>");
out.println("    </tr>");
out.println("    <tr class=tdbg>");
out.println("      <td width=\"130\"  class='tdbg5' align=right height=25><STRONG>是否分页显示：</STRONG></td>");
out.println("      <td height=25 >");
out.println("        <input type=\"radio\" name=\"UsePage\" checked value=\"True\">是");
out.println("        <input type=\"radio\" name=\"UsePage\" value=\"False\">否</td> ");
out.println("    </tr>");

out.println("    <tr class=tdbg>");
out.println("      <td width=\"130\"  class='tdbg5' align=right height=25><STRONG>"+ channelShortName + "内容最多字符：</STRONG></td>");
out.println("      <td height=25>");
out.println("        <Input  maxLength=3 size=5 value=0 name=ContentLen> &nbsp;&nbsp;&nbsp;&nbsp;<FONT color=#ff0000>一个汉字=两个英文字符，为0时不显示</FONT></td>");
out.println("    </tr>");
out.println("    <tr class=tdbg>");
out.println("      <td width=\"130\"  class='tdbg5' align=right height=25><STRONG>"+ channelShortName + "循环列设置：</STRONG></td>");
out.println("      <td height=25>");
out.println("        每显示<Input  maxLength=3 size=2 value=0 name=Cols>列后,向自定义循环列表中插入 <Input size=10 value=\"\" name=ColsHtml>&nbsp;&nbsp;<FONT color=#ff0000>支持Html代码</FONT></td>");
out.println("    </tr>");
out.println("    <tr class=tdbg>");
out.println("      <td width=\"130\"  class='tdbg5' align=right height=25><STRONG>"+ channelShortName + "循环行设置：</STRONG></td>");
out.println("      <td height=25>");
out.println("        每显示<Input  maxLength=3 size=2 value=0 name=Rows>行后,向自定义循环列表中插入 <Input size=10 value=\"\" name=RowsHtml>&nbsp;&nbsp;<FONT color=#ff0000>支持Html代码</FONT></td>");
out.println("    </tr>");
out.println("    <tr class=tdbg>");
out.println("      <td width=\"130\" class='tdbg5' align=right height=25><STRONG>循环标签支持标签：</STRONG></td>");
out.println("      <td>");
out.println("        <table border='0' cellpadding='0' cellspacing='0' width='100%' height='100%' align='center'>");
if (moduleType == 1) {
    out.println("         <tr>");
    out.println("           <td valign='top'><a href=\"javascript:insertLabel('{$ArticleUrl}')\" title=\"文章的链接地址\">{$ArticleUrl}</a></td>");
    out.println("           <td valign='top'><a href=\"javascript:insertLabel('{$ArticleID}')\" title=\"文章的ID\">{$ArticleID}</a></td>");
    out.println("           <td valign='top'><a href=\"javascript:insertLabel('{$UpdateTime}')\" title=\"文章更新时间\">{$UpdateTime}</a></td>");
    out.println("           <td valign='top'><a href=\"javascript:insertLabel('{$Stars}')\" title=\"文章评分等级\">{$Stars}</a></td>");
    out.println("           <td valign='top'><a href=\"javascript:insertLabel('{$Author}')\" title=\"文章作者\">{$Author}</a></td>");
    out.println("           <td valign='top'><a href=\"javascript:insertLabel('{$CopyFrom}')\" title=\"文章来源\">{$CopyFrom}</a></td>");
    out.println("         </tr>");
    out.println("         <tr>");
    out.println("           <td valign='top'><a href=\"javascript:insertLabel('{$Hits}')\" title=\"点击次数\">{$Hits}</a></td>");
    out.println("           <td valign='top'><a href=\"javascript:insertLabel('{$Inputer}')\" title=\"文章录入者\">{$Inputer}</a></td>");
    out.println("           <td valign='top'><a href=\"javascript:insertLabel('{$Editor}')\" title=\"责任编辑\">{$Editor}</a></td>");
    out.println("           <td valign='top'><a href=\"javascript:insertLabel('{$ReadPoint}')\" title=\"阅读点数\">{$ReadPoint}</a></td>");
    out.println("           <td valign='top'><a href=\"javascript:insertLabel('{$Property}')\" title=\"文章属性\">{$Property}</a></td>");
    out.println("           <td valign='top'><a href=\"javascript:insertLabel('{$Top}')\" title=\"固顶\">{$Top}</a></td>");
    out.println("         </tr>");
    out.println("         <tr>");
    out.println("           <td valign='top'><a href=\"javascript:insertLabel('{$Elite}')\" title=\"推荐\">{$Elite}</a></td>");
    out.println("           <td valign='top'><a href=\"javascript:insertLabel('{$Hot}')\" title=\"热门\">{$Hot}</a></td>");
    out.println("           <td valign='top'><a href=\"javascript:insertLabel('{$Title}')\" title=\"文章正标题，字数由参数TitleLen控制\">{$Title}</a></td>");
    out.println("           <td valign='top'><a href=\"javascript:insertLabel('{$Subheading}')\" title=\"自定义列表副标题\">{$Subheading}</a></td>");
    out.println("           <td valign='top'><a href=\"javascript:insertLabel('{$Intro}')\" title=\"文章简介\">{$Intro}</a></td>");
    out.println("           <td valign='top'><a href=\"javascript:insertLabel('{$Content}')\" title=\"文章正文内容，字数由参数ContentLen控制\">{$Content}</a></td>");
    out.println("          </tr>");
    out.println("          <tr>");
    out.println("           <td valign='top'><a href=\"javascript:insertLabel('{$ArticlePic(130,90)}')\" title=\"显示图片文章，width为图片宽度，height为图片高度\">{$ArticlePic(130,90)}</a></td>");
    out.println("           <td valign='top'></td>");
    out.println("           <td valign='top'></td>");
    out.println("           <td valign='top'></td>");
    out.println("           <td valign='top'></td>");
    out.println("           <td valign='top'></td>");
    out.println("          </tr>");
} else if (moduleType == 2) {
    out.println("          <tr>");
    out.println("           <td valign='top'><a href=\"javascript:insertLabel('{$SoftUrl}')\" title=\"软件地址\">{$SoftUrl}</a></td>");
    out.println("           <td valign='top'><a href=\"javascript:insertLabel('{$SoftID}')\" title=\"软件ID\">{$SoftID}</a></td>");
    out.println("           <td valign='top'><a href=\"javascript:insertLabel('{$SoftName}')\" title=\"软件名称\">{$SoftName}</a></td>");
    out.println("           <td valign='top'><a href=\"javascript:insertLabel('{$SoftVersion}')\" title=\"软件版本\">{$SoftVersion}</a></td>");
    out.println("           <td valign='top'><a href=\"javascript:insertLabel('{$SoftProperty}')\" title=\"软件属性（固顶、推荐等）\">{$SoftProperty}</a></td>");
    out.println("           <td valign='top'><a href=\"javascript:insertLabel('{$SoftSize}')\" title=\"软件大小\">{$SoftSize}</a></td>");
    out.println("          </tr>");
    out.println("          <tr>");
    out.println("           <td valign='top'><a href=\"javascript:insertLabel('{$UpdateTime}')\" title=\"更新时间\">{$UpdateTime}</a></td>");
    out.println("           <td valign='top'><a href=\"javascript:insertLabel('{$CopyrightType}')\" title=\"版权类型\">{$CopyrightType}</a></td>");
    out.println("           <td valign='top'><a href=\"javascript:insertLabel('{$Stars}')\" title=\"评分等级\">{$Stars}</a></td>");
    out.println("           <td valign='top'><a href=\"javascript:insertLabel('{$SoftIntro}')\" title=\"软件简介\">{$SoftIntro}</a></td>");
    out.println("           <td valign='top'><a href=\"javascript:insertLabel('{$OperatingSystem}')\" title=\"运行平台\">{$OperatingSystem}</a></td>");
    out.println("           <td valign='top'><a href=\"javascript:insertLabel('{$SoftType}')\" title=\"软件类型\">{$SoftType}</a></td>");
    out.println("          </tr>");
    out.println("          <tr>");
    out.println("           <td valign='top'><a href=\"javascript:insertLabel('{$Hits}')\" title=\"点击数\">{$Hits}</a></td>");
    out.println("           <td valign='top'><a href=\"javascript:insertLabel('{$DayHits}')\" title=\"显示每日点击数\">{$DayHits}</a></td>");
    out.println("           <td valign='top'><a href=\"javascript:insertLabel('{$WeekHits}')\" title=\"显示每周点击数\">{$WeekHits}</a></td>");
    out.println("           <td valign='top'><a href=\"javascript:insertLabel('{$MonthHits}')\" title=\"显示每月点击数\">{$MonthHits}</a></td>");
    out.println("           <td valign='top'><a href=\"javascript:insertLabel('{$SoftPoint}')\" title=\"显示下载软件时所需的点数\">{$SoftPoint}</a></td>");
    out.println("           <td valign='top'><a href=\"javascript:insertLabel('{$SoftAuthor}')\" title=\"软件作者\">{$SoftAuthor}</a></td>");
    out.println("          </tr>");
    out.println("          <tr>");
    out.println("           <td valign='top'><a href=\"javascript:insertLabel('{$SoftLanguage}')\" title=\"语言种类\">{$SoftLanguage}</a></td>");
    out.println("           <td valign='top'><a href=\"javascript:insertLabel('{$AuthorEmail}')\" title=\"作者Email\">{$AuthorEmail}</a></td>");
    out.println("           <td valign='top'><a href=\"javascript:insertLabel('{$DemoUrl}')\" title=\"软件演示地址\">{$DemoUrl}</a></td>");
    out.println("           <td valign='top'><a href=\"javascript:insertLabel('{$RegUrl}')\" title=\"软件注册地址\">{$RegUrl}</a></td>");
    out.println("           <td valign='top'><a href=\"javascript:insertLabel('{$SoftPic(130,90)}')\" title=\"显示图片软件，width为图片宽度，height为图片高度\">{$SoftPic(130,90)}</a></td>");
    out.println("           <td valign='top'></td>");
    out.println("          </tr>");
} else if (moduleType == 3) {
    out.println("          <tr>");
    out.println("           <td valign='top'><a href=\"javascript:insertLabel('{$PhotoID}')\" title=\"图片ID\">{$PhotoID}</a></td>");
    out.println("           <td valign='top'><a href=\"javascript:insertLabel('{$PhotoUrl}')\" title=\"图片地址\">{$PhotoUrl}</a></td>");
    out.println("           <td valign='top'><a href=\"javascript:insertLabel('{$UpdateTime}')\" title=\"更新时间\">{$UpdateTime}</a></td>");
    out.println("           <td valign='top'><a href=\"javascript:insertLabel('{$Stars}')\" title=\"评分等级\">{$Stars}</a></td>");
    out.println("           <td valign='top'><a href=\"javascript:insertLabel('{$Author}')\" title=\"图片作者\">{$Author}</a></td>");
    out.println("           <td valign='top'><a href=\"javascript:insertLabel('{$CopyFrom}')\" title=\"图片来源\">{$CopyFrom}</a></td>");
    out.println("          </tr>");
    out.println("          <tr>");
    out.println("           <td valign='top'><a href=\"javascript:insertLabel('{$Hits}')\" title=\"点击数\">{$Hits}</a></td>");
    out.println("           <td valign='top'><a href=\"javascript:insertLabel('{$Inputer}')\" title=\"图片的录入作者\">{$Inputer}</a></td>");
    out.println("           <td valign='top'><a href=\"javascript:insertLabel('{$Editor}')\" title=\"图片的编辑者\">{$Editor}</a></td>");
    out.println("           <td valign='top'><a href=\"javascript:insertLabel('{$InfoPoint}')\" title=\"查看点数\">{$InfoPoint}</a></td>");
    out.println("           <td valign='top'><a href=\"javascript:insertLabel('{$Keyword}')\" title=\"关键字\">{$Keyword}</a></td>");
    out.println("           <td valign='top'><a href=\"javascript:insertLabel('{$Keyword}')\" title=\"关键字\">{$Keyword}</a></td>");
    out.println("          </tr>");
    out.println("          <tr>");
    out.println("           <td valign='top'><a href=\"javascript:insertLabel('{$Property}')\" title=\"图片属性（固顶，热门，推荐等）\">{$Property}</a></td>");
    out.println("           <td valign='top'><a href=\"javascript:insertLabel('{$Top}')\" title=\"显示固顶\">{$Top}</a></td>");
    out.println("           <td valign='top'><a href=\"javascript:insertLabel('{$Elite}')\" title=\"显示推荐\">{$Elite}</a></td>");
    out.println("           <td valign='top'><a href=\"javascript:insertLabel('{$Hot}')\" title=\"显示热门\">{$Hot}</a></td>");
    out.println("           <td valign='top'><a href=\"javascript:insertLabel('{$PhotoName}')\" title=\"显示图片名称\">{$PhotoName}</a></td>");
    out.println("           <td valign='top'><a href=\"javascript:insertLabel('{$PhotoName}')\" title=\"显示图片名称\">{$PhotoName}</a></td>");
    out.println("          </tr>");
    out.println("          <tr>");
    out.println("           <td valign='top'><a href=\"javascript:insertLabel('{$PhotoIntro}')\" title=\"显示图片介绍\">{$PhotoIntro}</a></td>");
    out.println("           <td valign='top'><a href=\"javascript:insertLabel('{$PhotoThumb}')\" title=\"显示图片的缩略图\">{$PhotoThumb}</a></td>");
    out.println("           <td valign='top'><a href=\"javascript:insertLabel('{$DayHits}')\" title=\"显示当日点击数\">{$DayHits}</a></td>");
    out.println("           <td valign='top'><a href=\"javascript:insertLabel('{$WeekHits}')\" title=\"显示本周点击数\">{$WeekHits}</a></td>");
    out.println("           <td valign='top'><a href=\"javascript:insertLabel('{$MonthHits}}')\" title=\"显示本月点击数\">{$MonthHits}</a></td>");
    out.println("           <td valign='top'></td>");
    out.println("          </tr>");
} else if (moduleType == 5) {
    out.println("         <tr>");
    out.println("           <td valign='top'><a href=\"javascript:insertLabel('{$ClassUrl}')\" title=\"栏目链接\">{$ClassUrl}</a></td>");
    out.println("           <td valign='top'><a href=\"javascript:insertLabel('{$ClassID}')\" title=\"栏目ID\">{$ClassID}</a></td>");
    out.println("           <td valign='top'><a href=\"javascript:insertLabel('{$ClassName}')\" title=\"栏目名称\">{$ClassName}</a></td>");
    out.println("           <td valign='top'><a href=\"javascript:insertLabel('{$ParentDir}')\" title=\"父目录\">{$ParentDir}</a></td>");
    out.println("           <td valign='top'><a href=\"javascript:insertLabel('{$ClassDir}')\" title=\"栏目目录\">{$ClassDir}</a></td>");
    out.println("          </tr>");
    out.println("          <tr>");
    out.println("           <td valign='top'><a href=\"javascript:insertLabel('{$ProductUrl}')\" title=\"商品链接\">{$ProductUrl}</a></td>");
    out.println("           <td valign='top'><a href=\"javascript:insertLabel('{$ProductID}')\" title=\"商品ID\">{$ProductID}</a></td>");
    out.println("           <td valign='top'><a href=\"javascript:insertLabel('{$ProductNum}')\" title=\"商品数\">{$ProductNum}</a></td>");
    out.println("           <td valign='top'><a href=\"javascript:insertLabel('{$ProductModel}')\" title=\"商品型号\">{$ProductModel}</a></td>");
    out.println("           <td valign='top'><a href=\"javascript:insertLabel('{$ProductStandard}')\" title=\"商品规格\">{$ProductStandard}</a></td>");
    out.println("          </tr>");
    out.println("          <tr>");
    out.println("           <td valign='top'><a href=\"javascript:insertLabel('{$Top}')\" title=\"固顶\">{$Top}</a></td>");
    out.println("           <td valign='top'><a href=\"javascript:insertLabel('{$Elite}')\" title=\"推荐\">{$Elite}</a></td>");
    out.println("           <td valign='top'><a href=\"javascript:insertLabel('{$Hot}')\" title=\"热门\">{$Hot}</a></td>");
    out.println("           <td valign='top'><a href=\"javascript:insertLabel('{$UpdateTime}')\" title=\"更新时间\">{$UpdateTime}</a></td>");
    out.println("           <td valign='top'><a href=\"javascript:insertLabel('{$Stars}')\" title=\"评分等级\">{$Stars}</a></td>");
    out.println("          </tr>");
    out.println("          <tr>");
    out.println("           <td valign='top'><a href=\"javascript:insertLabel('{$ProductName}')\" title=\"商品名称\">{$ProductName}</a></td>");
    out.println("           <td valign='top'><a href=\"javascript:insertLabel('{$ProductTypeName}')\" title=\"商品类别\">{$ProductTypeName}</a></td>");
    out.println("           <td valign='top'><a href=\"javascript:insertLabel('{$ProductIntro}')\" title=\"商品简介\">{$ProductIntro}</a></td>");
    out.println("           <td valign='top'><a href=\"javascript:insertLabel('{$ProductExplain}')\" title=\"显示商品说明\">{$ProductExplain}</a></td>");
    out.println("           <td valign='top'><a href=\"javascript:insertLabel('{$ProductThumb(130,90)}')\" title=\"显示商品图片，width为图片宽度，height为图片高度\">{$ProductThumb(130,90)}</a></td>");
    out.println("          </tr>");
    out.println("          <tr>");
    out.println("           <td valign='top'><a href=\"javascript:insertLabel('{$BeginDate}')\" title=\"显示优惠开始日期\">{$BeginDate}</a></td>");
    out.println("           <td valign='top'><a href=\"javascript:insertLabel('{$EndDate}')\" title=\"显示优惠结束日期\">{$EndDate}</a></td>");
    out.println("           <td valign='top'><a href=\"javascript:insertLabel('{$Discount}')\" title=\"降价折扣\">{$Discount}</a></td>");
    out.println("           <td valign='top'><a href=\"javascript:insertLabel('{$LimitNum}')\" title=\"限够数量\">{$LimitNum}</a></td>");
    out.println("          </tr>");
    out.println("          <tr>");
    out.println("           <td valign='top'><a href=\"javascript:insertLabel('{$Price_Original}')\" title=\"原始零售价\">{$Price_Original}</a></td>");
    out.println("           <td valign='top'><a href=\"javascript:insertLabel('{$Price_Market}')\" title=\"显示市场价\">{$Price_Market}</a></td>");
    out.println("           <td valign='top'><a href=\"javascript:insertLabel('{$Price_Member}')\" title=\"显示会员价\">{$Price_Member}</a></td>");
    out.println("           <td valign='top'><a href=\"javascript:insertLabel('{$Price}')\" title=\"显示商城价\">{$Price}</a></td>");
    out.println("          </tr>");
    out.println("          <tr>");
    out.println("           <td valign='top'><a href=\"javascript:insertLabel('{$ProducerName}')\" title=\"生 产 商\">{$ProducerName}</a></td>");
    out.println("           <td valign='top'><a href=\"javascript:insertLabel('{$TrademarkName}')\" title=\"品牌商标\">{$TrademarkName}</a></td>");
    out.println("           <td valign='top'><a href=\"javascript:insertLabel('{$PresentExp}')\" title=\"购物积分\">{$PresentExp}</a></td>");
    out.println("           <td valign='top'><a href=\"javascript:insertLabel('{$PresentPoint}')\" title=\"赠送的点数\">{$PresentPoint}</a></td>");
    out.println("          </tr>");
    out.println("          <tr>");
    out.println("           <td valign='top'><a href=\"javascript:insertLabel('{$PresentMoney}')\" title=\"返还的现金券\">{$PresentMoney}</a></td>");
    out.println("           <td valign='top'><a href=\"javascript:insertLabel('{$PointName}')\" title=\"点券的名称\">{$PointName}</a></td>");
    out.println("           <td valign='top'><a href=\"javascript:insertLabel('{$PointUnit}')\" title=\"点券的单位\">{$PointUnit}</a></td>");
    out.println("           <td valign='top'></td>");
    out.println("          </tr>");
}
out.println("        </table>");
out.println("    </td>");
out.println("    </tr>");
out.println("    <tr class='tdbg'>");
out.println("      <td height='10' colspan='2' align='center'>");
out.println("        <input name='Title' type='hidden' id='Title' value='" + title + "'>");
out.println("        <input name='Action' type='hidden' id='Action' value='" + action + "'>");
out.println("        <input name='editLabel' type='hidden' id='editLabel' value='" + editLabel + "'>");
out.println("        <input name='dChannelID' type='hidden' id='dChannelID' value='" + dChannelId + "'> ");
out.println("        <input name='ModuleType' type='hidden' id='ModuleType' value='" + moduleType + "'>");
out.println("        <input name='ChannelShowType' type='hidden' id='ChannelShowType' value='\" & ChannelShowType & \"'> ");
out.println("      </td>");
out.println("    </tr>");
out.println("    <tr class='tdbg'>");
out.println("        <td colspan=2 align='center'>");
out.println("          <input TYPE='button' value=' 确 定 ' onCLICK='objectTag()'>&nbsp;&nbsp;");
out.println("          <input name='EditorpreviewContent' type='button' id='EditorpreviewContent' value=' 预 览 ' onclick='previewContent();'>");
out.println("        </td>");
out.println("    </tr>");
out.println("  </table>");
out.println("</FORM>");
out.println("</body>");
out.println("</html>");
%>

