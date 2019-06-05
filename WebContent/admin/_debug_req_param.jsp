<h3>Request.Parameters</h3>
<%
  java.util.Enumeration attr_names = request.getAttributeNames();
  while (attr_names.hasMoreElements()) {
	  String attr_key = attr_names.nextElement().toString();
	  out.println("<li>request.attr[" + attr_key + "] = " + request.getAttribute(attr_key));
  }
  
  java.util.Enumeration para_names = request.getParameterNames();
  while (para_names.hasMoreElements()) {
	  String para_key = para_names.nextElement().toString();
     String[] values = request.getParameterValues(para_key);
     for (int i = 0; i < values.length; ++i) {
	    out.println("<li>request.para[" + para_key + "][" + i + "] = " + 
			  com.chinaedustar.common.util.StringHelper.htmlEncode(values[i]));
     }
  }
%>
<br/><br/>
