package com.chinaedustar.publish.tag;

import javax.servlet.jsp.tagext.BodyTagSupport;
import com.chinaedustar.common.util.StringHelper;
import com.chinaedustar.publish.PublishContext;
import com.chinaedustar.publish.PublishUtil;

/**
 * 标记实现的公共基类。
 * 
 * <pre>
 * 标记类的层次体系：
 * PublishBodyTagSupport
 *   ComponentTagSupport
 *     PartTag - 存储一块 html 内容，但是不立刻显示出来。后面可以被 RefPart 引用。
 *     RefPartTag - 使用以前存储的一块 html 内容。
 *     AbstractUITag
 *       AbstractClosingTag
 *         DivTag - html div 标记的实现。
 * </pre>
 * 
 * @author liujunxing
 */
public abstract class PublishBodyTagSupport extends BodyTagSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3829430852631424882L;

	/**
     * @s.tagattribute required="false" type="String"
     * description="The id of the tag element."
     */
    @Override public void setId(String string) {
        super.setId(string);
    }

    /**
     * 获得缓存的内容体。内容体根据 doStartTag 时是否返回了 EVAL_BODY_BUFFERED,
     *   如果返回该值，则 super.bodyContent 中会存放被缓存了的内容。 
     * @return
     */
    protected final String getBufferedBody() {
    	if (super.bodyContent == null)
    		return "";
    	else
    		return super.bodyContent.getString();
    }

    /**
     * 获得当前应用的发布系统环境对象。
     * @return
     */
    protected final PublishContext getPublishContext() {
    	PublishContext pub_ctxt = (PublishContext)pageContext.getAttribute(PublishContext.PUBLISH_CONTEXT_KEY);
    	if (pub_ctxt != null) return pub_ctxt;
    	
    	return (PublishContext)pageContext.getServletContext().getAttribute(PublishContext.PUBLISH_CONTEXT_KEY);
    }
    
    /**
     * 根据指定的字符串解析为一个 scope 代码值。
     * page - PageContext.PAGE_SCOPE, request - REQUEST_SCOPE,
     *   session - SESSION_SCOPE, application - APPLICATION_SCOPE
     * @param scope - 范围代码，缺省为 PAGE_SCOPE.
     * @return
     */
    public static int fromScopeString(String scope) {
    	return PublishUtil.fromScopeValue(scope);
    }
    
    /**
     * 执行 html 编码处理。
     * @param txt
     * @return
     */
    public static String htmlEncode(String txt) {
    	return StringHelper.htmlEncode(txt);
    }
    
    /*
    protected boolean altSyntax() {
        return ContextUtil.isUseAltSyntax(getStack().getContext());
    }

    protected ValueStack getStack() {
        return TagUtils.getStack(pageContext);
    }

    protected String findString(String expr) {
        return (String) findValue(expr, String.class);
    }

    protected Object findValue(String expr) {
        if (altSyntax()) {
            // does the expression start with %{ and end with }? if so, just cut it off!
            if (expr.startsWith("%{") && expr.endsWith("}")) {
                expr = expr.substring(2, expr.length() - 1);
            }
        }

        return getStack().findValue(expr);
    }

    protected Object findValue(String expr, Class toType) {
        if (altSyntax() && toType == String.class) {
            return translateVariables(expr, getStack());
        } else {
            if (altSyntax()) {
                // does the expression start with %{ and end with }? if so, just cut it off!
                if (expr.startsWith("%{") && expr.endsWith("}")) {
                    expr = expr.substring(2, expr.length() - 1);
                }
            }

            return getStack().findValue(expr, toType);
        }
    }

    protected String toString(Throwable t) {
        FastByteArrayOutputStream bout = new FastByteArrayOutputStream();
        PrintWriter wrt = new PrintWriter(bout);
        t.printStackTrace(wrt);
        wrt.close();

        return bout.toString();
    }

    public static String translateVariables(String expression, ValueStack stack) {
        while (true) {
            int x = expression.indexOf("%{");
            int y = expression.indexOf("}", x);

            if ((x != -1) && (y != -1)) {
                String var = expression.substring(x + 2, y);

                Object o = stack.findValue(var, String.class);

                if (o != null) {
                    expression = expression.substring(0, x) + o + expression.substring(y + 1);
                } else {
                    // the variable doesn't exist, so don't display anything
                    expression = expression.substring(0, x) + expression.substring(y + 1);
                }
            } else {
                break;
            }
        }

        return expression;
    }
    */
}