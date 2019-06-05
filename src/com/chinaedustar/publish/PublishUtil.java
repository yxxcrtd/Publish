package com.chinaedustar.publish;

import com.chinaedustar.publish.model.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.PageContext;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import com.chinaedustar.common.util.StringHelper;
import com.chinaedustar.publish.comp.PagedArrayList;
import com.chinaedustar.publish.comp.PagedDataTable;
import com.chinaedustar.publish.impl.PageVariableResolver;
import com.chinaedustar.publish.impl.TemplateProcessor;
import com.chinaedustar.publish.itfc.QueryCallback;
import com.chinaedustar.rtda.wrapper.DefaultObjectWrapper;
import com.chinaedustar.template.comp.StandardExpressionCalculator;
import com.chinaedustar.template.expr.Expression;
import com.chinaedustar.template.expr.ExpressionParser;

/**
 * 发布 Facade 包装类。
 * 
 * @author liujunxing
 */
@SuppressWarnings("rawtypes")
public class PublishUtil {
	
	/** 管理员登录后在页面会话中保存的变量名。 */
	public static final String ADMIN_USERNAME = "..ADMIN_USERNAME";
	
	/** 会员的用户名。 */
	public static final String USER_USERNAME = "..USER_USERNAME";
	
	/** 防止表单重复提交、存储在Session的Attribute里边的变量名。  */
	public static final String FORM_FORMVALIDNAME = "..FORM_VALID_NAME";
	
	// TODO: 对这些方法进行一下分组。
	
	/**
	 * 判断指定字符串是否为 Null 或者为空串。
	 */
	public static boolean isEmptyString(String v) {
		if (v == null) return true;
		if (v.length() == 0) return true;
		return false;
	}
	
	/**
	 * 判断给定的字符串是否是一个整数。
	 */
	public static boolean isInteger(String v) {
		if (v == null || v.length() == 0) return false;
		try {
			Integer.parseInt(v);
			return true;
		} catch (NumberFormatException ex) {
		}
		return false;
	}
	
	/**
	 * 安全的解析一个整数。
	 * @param v
	 * @param def_val
	 * @return
	 */
	public static int safeParseInt(String v, int def_val) {
		if (v == null || v.length() == 0) return def_val;
		if (isInteger(v))
			return Integer.parseInt(v);
		else
			return def_val;
	}
	
	/**
	 * 根据一个 scope 字符串得到 PageContext 中定义的 scope code.
	 * @param scope
	 * @return
	 */
	public static int fromScopeValue(String scope) {
    	if (scope == null || scope.length() == 0)
    		return PageContext.PAGE_SCOPE;
    	scope = scope.toLowerCase();
    	if (scope.equals("page"))
    		return PageContext.PAGE_SCOPE;
    	if (scope.equals("request"))
    		return PageContext.REQUEST_SCOPE;
    	if (scope.equals("session"))
    		return PageContext.SESSION_SCOPE;
    	if (scope.equals("application"))
    		return PageContext.APPLICATION_SCOPE;
    	// 都不是，缺省用 PAGE_SCOPE.
    	return PageContext.PAGE_SCOPE;
	}

	/** 线程绑定的发布系统环境对象。 */
	private static ThreadLocal<PublishContext> pub_ctxt = new ThreadLocal<PublishContext>();
	
	/**
	 * 获得当前线程的 PublishContext 对象。
	 * @return
	 */
	public static final PublishContext getPublishContext() {
		return pub_ctxt.get();
	}
	
	/**
	 * 设置当前线程的 PublishContext 对象。在 DefaultFilter 每个页面之前会设置此对象。
	 * @param pub_ctxt
	 */
	public static final void setPublishContext(PublishContext pub_ctxt) {
		PublishUtil.pub_ctxt.set(pub_ctxt);
	}

	/**
	 * 获得当前保存在 ServletContext 中的 PublishContext 环境对象。
	 * PublishContext 环境对象是在 StartupServlet 中创建出来的。
	 * @param app
	 * @return
	 */
	public static final PublishContext getPublishContext(ServletContext app) {
		return (PublishContext)app.getAttribute(PublishContext.PUBLISH_CONTEXT_KEY);
	}

	/**
	 * 获得当前保存在 PageContext 中的 PublishContext 环境对象。
	 * PublishContext 环境对象是在 StartupServlet 中创建出来的。
	 * @param page - 页面。
	 * @return
	 */
	public static final PublishContext getPublishContext(PageContext page) {
		return getPublishContext(page.getServletContext());
	}

	/**
	 * 读取指定的文本文件的所有内容。
	 * @param fileName - 要读取的文件的全路径。
	 * @return - 返回读取的正文内容。
	 * @throws Exception
	 */
	public static String readTextFile(String fileName) throws Exception {
		java.io.FileReader fr = null;
		try {
			fr = new java.io.FileReader(fileName);
			
			java.io.BufferedReader br = new java.io.BufferedReader(fr);
			StringBuilder strbuf = new StringBuilder();
			while (br.ready()) {
				String line = br.readLine();
				strbuf.append(line).append("\r\n");
			}
			br.close();
			return strbuf.toString();
		} finally {
			if (fr != null) fr.close();
		}
	}
	
	/**
	 * 使用指定的编码读取指定的文本文件的所有内容。
	 * @param fileName 文件的完整路径。
	 * @param encoding 字符集，默认为操作系统的默认文件字符集。
	 * @return
	 * @throws java.io.IOException
	 */
	public static String readTextFile(String fileName, String encoding) throws java.io.IOException {
		FileInputStream finput = null;
		if (encoding == null || encoding.trim().length() < 1) {
			encoding = System.getProperty("file.encoding");
			if (encoding == null || encoding.trim().length() < 1)
				encoding = "GB2312";
		}
		try {
			finput = new FileInputStream(fileName);
			java.io.InputStreamReader reader = new java.io.InputStreamReader(finput, encoding);
			int readLength = 0;
			char[] buf = new char[1024];
			StringBuffer outString = new StringBuffer();
			while ((readLength = reader.read(buf, 0, buf.length)) > 0) {
				outString.append(buf, 0, readLength);
			}
			reader.close();
			return outString.toString();
		} finally {
			finput.close();
		}
	}

	/**
	 * 在指定的页面环境中运行指定的模板。
	 * @param page
	 * @param tmpl_content
	 */
	public static final String showTemplatePage(PublishContext pub_ctxt, String tmpl_content, java.util.Map vars) {
		if (tmpl_content == null) tmpl_content = "";
		TemplateProcessor tp = new TemplateProcessor(pub_ctxt);
		String result = tp.processTemplate(tmpl_content, vars, null);
		// page.getOut().write(result);
		return result;
	}

	/**
	 * 从 request 中读取指定键的参数，并转换为整数。
	 * @param request
	 * @param key - 该键的参数。
	 * @param defval - 缺省值。
	 * @return - 如果不存在或者不是一个整数则返回缺省值。
	 */
	public static final int getIntParameter(ServletRequest request, String key, int defval) {
		try {
			String value = request.getParameter(key);
			if (value == null || value.length() == 0) return defval;
			return Integer.parseInt(value);
		} catch (NumberFormatException ex) {
			return defval;
		}
	}

	/**
	 * 使用模板语法访问一个表达式。
	 * @param expr_str - 待求取的表达式的值。
	 * @param page_ctxt - 页面环境。
	 * @return
	 */
	public static final Object evalPageExpression(String expr_str, PageContext page_ctxt) {
		// 解析表达式。
		Expression expr = (new ExpressionParser(expr_str)).parse();
		
		// 建立简易计算器环境。
		PageVariableResolver var_r = new PageVariableResolver(null, page_ctxt);
		DefaultObjectWrapper obj_w = new DefaultObjectWrapper();
		StandardExpressionCalculator calc = new StandardExpressionCalculator(var_r, obj_w);
		
		// 计算结果。
		return obj_w.unwrap(expr.eval(calc));
	}
	
	/**
	 * 执行指定的 HQL 查询语句，返回第一行第一列的整数结果。
	 * @param dao - 数据访问对象。
	 * @param hql - 查询语句。
	 * @return - 如果查询结果为 null 则返回 0；否则试图返回一个整数值。
	 */
	public static final int executeIntScalar(DataAccessObject dao, String hql) {
		java.util.List list = dao.list(hql);
		if (list == null) return 0;
		if (list.size() <= 0) return 0;
		
		Object value = list.get(0);
		if (value instanceof Number) return ((Number)value).intValue();
		return 0;
	}
	
	/**
	 * 执行指定的 HQL 查询语句，返回 top n 个项目。
	 * @param dao
	 * @param hql
	 * @param num
	 * @return
	 */
	public static final List queryTopResult(DataAccessObject dao, String hql, final int num) {
		return (List)dao.query(hql, new QueryCallback() {
			public List doInQuery(Query query) {
				query.setFirstResult(0);
				query.setMaxResults(num);
				return query.list();
			}
		});
	}
	
	
	/**
	 * 指定指定的 HQL 查询语句，返回第一个对象。
	 *  一般用于获得单一一个对象，如 hql = "FROM Channel WHERE name = '新闻中心'"。
	 * 也可以用于查询返回一个单一字符串、单一数据的查询。
	 * @param dao
	 * @param hql
	 * @return 如果没有任何对象则返回 null，否则返回第一个对象。
	 */
	public static final Object executeSingleObjectQuery(DataAccessObject dao, String hql) {
		java.util.List list = dao.list(hql);
		if (list == null || list.size() == 0) return null;
		
		return list.get(0);
	}
	
	/**
	 * 执行一个分页查询。
	 * @param dao - 数据访问对象。
	 * @param hqlCount - 查找总数用的查询语句。
	 * @param hql - 查询语句。
	 * @param currPage - 要查询的当前页，从 1 开始。
	 * @param pageSize - 每页数据的大小，缺省 = 20。
	 * @return
	 */
	public static final PagedArrayList executePagedQuery(DataAccessObject dao, String hqlCount, 
			String hql, int currPage, int pageSize) {
		// 检查参数。
		if (currPage <= 0) currPage = 1;
		if (pageSize <= 0) pageSize = 20;
		int totalCount = 0;
		
		// 查找总数。
		if (hqlCount != null) 
			totalCount = executeIntScalar(dao, hqlCount);
		
		// 执行查找。 
		List list = dao.getHibernateTemplate().executeFind(new PageQueryCallback(hql, currPage, pageSize));
		
		if (hqlCount == null) {
			totalCount = (currPage - 1)*pageSize + list.size();
		}
		
		// 返回结果。
		return new PagedArrayList(list, totalCount, currPage, pageSize);
	}
	
	/**
	 * 将一个 PagedArrayList 包装为一个 PagedDataTable 对象。
	 * @param schema
	 * @param list
	 * @return
	 */
	public static final PagedDataTable wrapPagedArray(DataSchema schema, PagedArrayList list) {
		return new PagedDataTable(schema, list);
	}

	/**
	 * 将一个 PagedArrayList 包装为一个 PagedDataTable 对象。
	 * @param schema
	 * @param list
	 * @return
	 */
	public static final PagedDataTable wrapPagedArray(String columns, PagedArrayList list) {
		return new PagedDataTable(columnsToSchema(columns), list);
	}

	/**
	 * 分解一个以 ',' 分隔的列名的字符串，并返回一个 SimpleSchema 对象。
	 * @param columns
	 * @return
	 */
	public static final DataSchema columnsToSchema(String columns) {
		String[] cols = StringHelper.split(columns, ",", false);
		DataSchema schema = new DataSchema();
		for (int index = 0; index < cols.length; ++index) {
			schema.add(cols[index].trim());
		}
		return schema;
	}
	
	/**
	 * 将一个列表的数据加入到 DataTable 中。
	 * @param list
	 * @param dt
	 */
	public static final void addToDataTable(java.util.List list, DataTable dt) {
		if (dt == null) throw new IllegalArgumentException("dt == null");
		if (list == null) return;
		for (int i = 0; i < list.size(); ++i) {
			dt.addRow(dt.newRow((Object[])list.get(i)));
		}
	}
	
	/**
	 * 得到当前登录的管理员对象。
	 * @param session JSP 会话环境对象。
	 * @return
	 */
	public static Admin getCurrentAdmin(HttpSession session) {
		Admin admin = (Admin)session.getAttribute(ADMIN_USERNAME);
		//com.chinaedustar.publish.model.Admin admin = getPublishContext(session.getServletContext()).getSite().getAdminCollection().getAdmin("admin");
		return admin;
	}
	
	/**
	 * 设置当前登录的管理员对象。
	 * @param adminName
	 * @param pageContext
	 */
	public static void setCurrentAdmin(String adminName, PageContext pageContext) {
		AdminCollection admins = getPublishContext(pageContext).getSite().getAdminCollection();
		Admin admin = admins.getAdmin(adminName);
		pageContext.getSession().setAttribute(ADMIN_USERNAME, admin);
	}

	/**
	 * 得到当前登录的会员对象。
	 * @param session
	 * @return
	 */
	public static final User getCurrentUser(HttpSession session) {
		User user = (User)session.getAttribute(PublishUtil.USER_USERNAME);
		return user;
	}
	
	/**
	 * 设置当前的管理员对象。
	 * @param adminName
	 * @param pageContext
	 */
	public static final void setCurrentUser(User user, PageContext pageContext) {
		pageContext.getSession().setAttribute(USER_USERNAME, user);
	}
	
	// 分页查询使用的回调实现类。
	private static final class PageQueryCallback implements HibernateCallback {
		public String hql;
		public int currPage;
		public int pageSize;
		
		PageQueryCallback(String hql, int currPage, int pageSize) {
			this.hql = hql;
			this.currPage = currPage;
			this.pageSize = pageSize;
		}
		
		public Object doInHibernate(Session session) throws HibernateException, SQLException {
			Query qry = session.createQuery(hql);
			qry.setFirstResult((currPage - 1)*pageSize);
			qry.setMaxResults(pageSize);
			return qry.list();
		}
	}
	
	/**
	 * 将一个 id 数组转换为 SQL IN 子句所需格式：1,2,3 。
	 * @param ids
	 * @return
	 */ 
	public static String toSqlInString(int[] ids) {
		if (ids == null || ids.length == 0) return "";
		StringBuffer strbuf = new StringBuffer();
		strbuf.append(ids[0]);
		for (int i = 1; i < ids.length; ++i) 
			strbuf.append(",").append(ids[i]);
		return strbuf.toString();
	}
	
	/**
	 * 根据 seperator 切分一个 id 列表。
	 * @param id_list
	 * @param seperator
	 * @return
	 */
	public static List<Integer> splitIdList(String id_list, String seperator) {
		List<Integer> ids = new java.util.ArrayList<Integer>();
		if (id_list == null || id_list.length() == 0 
				|| seperator == null || seperator.length() == 0) return ids;
		String[] id_array = StringHelper.split(id_list, seperator, true);
		if (id_array == null) return ids;
		for (int i = 0; i < id_array.length; ++i) {
			if (id_array[i] == null || id_array[i].trim().length() == 0) continue;
			try {
				Integer val = Integer.parseInt(id_array[i]);
				ids.add(val);
			} catch (NumberFormatException ex) {
				
			}
		}
		return ids;
	}

	/**
	 * 将一个 id 数组转换为 SQL IN 子句所需格式：1,2,3 。
	 * @param ids
	 * @return
	 */ 
	public static String toSqlInString(List<Integer> ids) {
		if (ids == null || ids.size() == 0) return "";
		StringBuffer strbuf = new StringBuffer();
		strbuf.append(ids.get(0));
		for (int i = 1; i < ids.size(); ++i) 
			strbuf.append(",").append(ids.get(i));
		return strbuf.toString();
	}

	/**
	 * 初始化 PaginationInfo 的 pageContext 为当前页面的 PageContext。
	 * @param pageContext
	 * @deprecated - 不提供此静态方法了。
	 */
	@Deprecated
	public static void initPaginationInfo(PageContext pageContext) {
		// PaginationInfo.initPageContext(pageContext);
	}
	
	// *********防止表单重复提交******************
	/**
	 * 得到一个表单的验证标识。如果没有则新建一个标识，存取到session里边。
	 * @param session
	 */
	public static String getFormValidId(HttpSession session) {
		String id = (String)session.getAttribute(FORM_FORMVALIDNAME);
		if (id == null || id.trim().length() < 1) {
			id = java.util.UUID.randomUUID().toString().toUpperCase();
			session.setAttribute(FORM_FORMVALIDNAME, id);
		}
		return id;
	}
	/**
	 * 检验是否为表单的验证标识。并生成新的验证标识。
	 * @param session
	 * @param formId
	 * @return
	 */
	public static boolean isFormValidId(HttpSession session, String formId) {
		boolean isId = false;
		String id = (String)session.getAttribute(FORM_FORMVALIDNAME);
		if (formId != null && formId.trim().length() > 1 && formId.equals(id)) {
			isId = true;
		}
		id = java.util.UUID.randomUUID().toString().toUpperCase();
		session.setAttribute(FORM_FORMVALIDNAME, id);
		return isId;
	}
	
	/**
	 * 执行 Html 编码。
	 * @param str
	 * @return
	 */
	public static String HtmlEncode(String str) {
		return com.chinaedustar.common.util.StringHelper.htmlEncode(str);
	}
	
	/**
	 * 将指定的文件内容全部读入内存。
	 * @param fileName
	 * @return
	 */
	public static String readFileContent(String fileName) {
		try {
			java.io.FileInputStream stream = new java.io.FileInputStream(fileName);
			java.io.InputStreamReader reader = new java.io.InputStreamReader(stream, "UTF-8");
			
			StringBuilder strbuf = new StringBuilder();
			char[] cbuf = new char[1024];
			while (true) {
				int len = reader.read(cbuf);
				if (len < 0) break;
				strbuf.append(cbuf, 0, len);
			}
			return strbuf.toString();
		} catch (java.io.IOException ex) {
			return null;
		}
	}

	/**
	 * 将指定文件内容写入指定正文文件，缺省使用 UTF-8 编码。
	 * @param fileName
	 * @param content
	 */
	public static void writeTextFile(String fileName, String content) throws java.io.IOException {
		writeTextFile(fileName, content, "UTF-8");
	}
	
	/**
	 * 将指定文件内容写入指定正文文件，使用指定的编码，我们建议使用 'GB2312' 或 'UTF-8'。
	 * @param fileName
	 * @param content
	 * @param charsetName - 字符集，参见 OutputStreamWriter()。
	 */
	public static void writeTextFile(String fileName, String content, String charsetName) throws java.io.IOException {
		FileOutputStream oStream = null;
		try {
			File file = new File(fileName);
			oStream = new FileOutputStream(file);
			java.io.Writer out = new OutputStreamWriter(oStream, charsetName);
			out.write(content);
			out.close();
		} finally {
			oStream.close();
		}
	}

	
	/**
	 * 使用指定的发布系统环境对象和父对象初始化列表中的模型对象。
	 * @param list - 数据列表。
	 * @param pub_ctxt - 环境对象。
	 * @param owner_obj - 拥有者对象。
	 * @return 返回这个列表，其中每个对象已经调用了 _init() 方法。
	 */
	public static List initModelList(List list, PublishContext pub_ctxt, PublishModelObject owner_obj) {
		if (list == null || list.size() == 0) return list;
		for (int i = 0; i < list.size(); ++i) {
			PublishModelObject model_obj = (PublishModelObject)list.get(i);
			model_obj._init(pub_ctxt, owner_obj);
		}
		return list;
	}


	/**
	 * 判定给定的字符串是否可以做为一个合法的目录的名字，合法名字由英文字母和数字组成，且第一个字符是字母。
	 * @param val
	 * @return true 表示名字合法；false 表示名字非法。
	 */
	public static boolean isValidDir(String val) {
		if (val == null || val.length() == 0) return false;

		// 判定名字是否合法。
		for (int i = 0; i < val.length(); i++) {
			char ch = val.charAt(i);
			if (ch >= 'A' && ch <= 'Z') continue;
			if (ch >= 'a' && ch <= 'z') continue;
			if (ch >= '0' && ch <= '9' && i > 0) continue;
			return false;
		}
		return true;
	}

	/**
	 * 判定所给的目录是否是系统目录名，系统目录名一般不允许做为 channel, column, special 的目录名。
	 *  当前 'admin', 'editor', 'images', 'js', 'META-INF', 'skin', 'user', 'WEB-INF' 不允许用户使用。
	 * @param dir
	 * @return
	 */
	public static boolean isSystemDir(String dir) {
		if ("admin".equalsIgnoreCase(dir)) return true;
		if ("editor".equalsIgnoreCase(dir)) return true;
		if ("images".equalsIgnoreCase(dir)) return true;
		if ("js".equalsIgnoreCase(dir)) return true;
		if ("META-INF".equalsIgnoreCase(dir)) return true;
		if ("skin".equalsIgnoreCase(dir)) return true;
		if ("user".equalsIgnoreCase(dir)) return true;
		if ("WEB-INF".equalsIgnoreCase(dir)) return true;
		return false; 
	}
	
	/**
	 * 安全的获得 long 型值。
	 * @param val
	 * @return
	 */
	public static long safeGetLongVal(Object val) {
		if (val == null) return 0;
		if (val instanceof Long) return (Long)val;
		if (val instanceof Integer) return (Integer)val;
		if (val instanceof Number) return ((Number)val).longValue();
		return 0;
	}
}
