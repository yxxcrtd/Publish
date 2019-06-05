<%@ page language="java" contentType="text/html; charset=gb2312"
    pageEncoding="UTF-8" 
%><%@page import="com.chinaedustar.publish.ParamUtil"
%><%
ParamUtil paramUtil = new ParamUtil(pageContext);
int channelId = paramUtil.safeGetIntParam("channelId");	// 频道的标识。
String TrueSiteUrl = request.getHeader("host").trim();	// localhost:8080
String InstallDir, strInstallDir, skinCss;

//获取安装目录及设置fso
strInstallDir = InstallDir = paramUtil.getPublishContext().getSite().getInstallDir();
skinCss = "";
%>
<HTML>
<HEAD>
<TITLE>HtmlEdit - 全屏编辑</TITLE>
<META http-equiv=Content-Type content="text/html; charset=gb2312" />
</HEAD>
<body leftmargin=0 topmargin=0 onunload="Minimize()">
<input type="hidden" id="ContentFullScreen" name="ContentFullScreen" value="">

<script language=VBScript>
  Dim Matches, Match, arrContent, Content1, Content2,Content3,Content5
  Dim strTemp, strTemp2, StrBody,TemplateContent

  Set regEx = New RegExp
  ContentFullScreen.value = opener.editor.HtmlEdit.document.body.innerHTML
  TemplateContent = opener.document.form1.Content.value
  ContentFullScreen.value ="<html><head><META http-equiv=Content-Type content=text/html; charset=gb2312><link href='<%=InstallDir%>Skin/DefaultSkin.css' rel='stylesheet' type='text/css'></head><body leftmargin=0 topmargin=0 >" & ContentFullScreen.value
  document.Write "<iframe ID='EditorFullScreen' src='editor_new.jsp?channelId=<%=channelId%>&ShowType=1&TemplateType=3&tContentID=ContentFullScreen' frameborder='0' scrolling=no width='100%' HEIGHT='100%'></iframe>"

Function  Resumeblank(byval Content)
  Dim strHtml,strHtml2,Num,Numtemp,Strtemp
  strHtml=Replace(Content, "<DIV", "<div")
  strHtml=Replace(strHtml, "</DIV>", "</div>")
  strHtml=Replace(strHtml, "<TABLE", "<table")
  strHtml=Replace(strHtml, "</TABLE>", vbCrLf & "</table>"& vbCrLf)
  strHtml=Replace(strHtml, "<TBODY>", "")
  strHtml=Replace(strHtml, "</TBODY>","" & vbCrLf)
  strHtml=Replace(strHtml, "<TR", "<tr")
  strHtml=Replace(strHtml, "</TR>", vbCrLf & "</tr>"& vbCrLf)
  strHtml=Replace(strHtml, "<TD", "<td")
  strHtml=Replace(strHtml, "</TD>", "</td>")
  strHtml=Replace(strHtml, "<!--", vbCrLf & "<!--")
  strHtml=Replace(strHtml, "<SELECT",vbCrLf & "<Select")
  strHtml=Replace(strHtml, "</SELECT>",vbCrLf & "</Select>")
  strHtml=Replace(strHtml, "<OPTION",vbCrLf & "  <Option")
  strHtml=Replace(strHtml, "</OPTION>","</Option>")
  strHtml=Replace(strHtml, "<INPUT",vbCrLf & "  <Input")
  strHtml=Replace(strHtml, "<script",vbCrLf & "<script")
  strHtml=Replace(strHtml, "&amp;","&")
  strHtml=Replace(strHtml, "{$--",vbCrLf & "<!--$")
  strHtml=Replace(strHtml, "--}","$-->")
  arrContent = Split(strHtml,vbCrLf)
  For i = 0 To UBound(arrContent)
    Numtemp=false
    if Instr(arrContent(i),"<table")>0 then
      Numtemp=True
      if Strtemp<>"<table" and Strtemp <>"</table>" then
        Num=Num+2
      End if 
      Strtemp="<table"
    Elseif Instr(arrContent(i),"<tr")>0 then
      Numtemp=True
      if Strtemp<>"<tr" and Strtemp<>"</tr>" then
        Num=Num+2
      End if 
      Strtemp="<tr"
    elseif Instr(arrContent(i),"<td")>0 then
      Numtemp=True
      if Strtemp<>"<td" and Strtemp<>"</td>" then
        Num=Num+2
      End if 
      Strtemp="<td"
    elseif Instr(arrContent(i),"</table>")>0 then
      Numtemp=True
      if Strtemp<>"</table>" and Strtemp<>"<table" then
        Num=Num-2
      End if 
      Strtemp="</table>"
    elseif Instr(arrContent(i),"</tr>")>0 then
      Numtemp=True
      if Strtemp<>"</tr>" and Strtemp<>"<tr" then
        Num=Num-2
      End if 
      Strtemp="</tr>"
    elseif Instr(arrContent(i),"</td>")>0 then
      Numtemp=True
      if Strtemp<>"</td>" and Strtemp<>"<td" then
        Num=Num-2
      End if 
      Strtemp="</td>"
    elseif Instr(arrContent(i),"<!--")>0 then
      Numtemp=True
    End if
    
    if Num< 0 then Num = 0
    if trim(arrContent(i))<>"" then
      if i=0 then
        strHtml2= string(Num," ") & arrContent(i) 
      elseif Numtemp=True then
        strHtml2= strHtml2 & vbCrLf & string(Num," ") & arrContent(i) 
      else
        strHtml2= strHtml2 & vbCrLf & arrContent(i) 
      end if
    end if
  Next
  Resumeblank=strHtml2
End function

Function Minimize()
  regEx.IgnoreCase = True
  regEx.Global = True
  regEx.Pattern = "(\<body)(.[^\<]*)(\>)"
  Set Matches = regEx.Execute(TemplateContent)
  For Each Match In Matches
    StrBody = Match.Value
  Next
  arrContent = Split(TemplateContent, StrBody)
  Content1 = arrContent(0) & StrBody
  Content2 = arrContent(1)
  Content5 = EditorFullScreen.HtmlEdit.document.Body.innerHTML

  regEx.Pattern = "\<IMG(.[^\<]*)\}\'\>"
  Set Matches = regEx.Execute(Content5)
  For Each Match In Matches
    regEx.Pattern = "\{\$(.*)\}"
    Set strTemp = regEx.Execute(Match.Value)
    For Each Match2 In strTemp
      strTemp2 = Replace(Match2.Value, "?", """")
      Content5 = Replace(Content5, Match.Value, "<!--"&strTemp2&"-->")
    Next
  Next

  regEx.Pattern = "\<IMG(.[^\<]*)\$\>"
  Set Matches = regEx.Execute(Content5)
  For Each Match In Matches
    regEx.Pattern = "\#(.*)\#"
    Set strTemp = regEx.Execute(Match.Value)
    For Each Match2 In strTemp
      strTemp2 = Replace(strTemp2, "#", "")
      strTemp2 = Replace(strTemp2, "[!", "<")
      strTemp2 = Replace(strTemp2, "!]", ">")
      Content5 = Replace(Content5, Match.Value, strTemp2)
    Next
  Next
  Content5=Replace(Content5, "http://localhost/", "{$InstallDir}")
  Content5=Replace(Content5, "http://localhost/", "{$InstallDir}")
  opener.editor.HtmlEdit.document.body.innerHTML=Resumeblank(EditorFullScreen.getHTML())
  opener.document.form1.Content.value = Content1& vbCrLf & Resumeblank(Content5) & vbCrLf &"</body>"& vbCrLf &"</html>"
  opener.editor.showBorders()
  opener.editor.showBorders()
  Set regEx = Nothing
End function

function setstatus()
End function

function setContent(zhi,TemplateType)
End function
</script>
<script language = 'JavaScript'>
   setTimeout("EditorFullScreen.showBorders()", 2000);
</script>
</BODY>
</HTML>
