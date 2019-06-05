package com.chinaedustar.publish.label;

import com.chinaedustar.common.util.StringHelper;
import com.chinaedustar.publish.model.*;
import com.chinaedustar.template.core.AttributeCollection;
import com.chinaedustar.template.core.LocalContext;

/**
 * 通用网站函数标签。
 * 
 * @author liujunxing
 *
 */
public class FunctionSiteLabel extends GroupLabelBase {
	
	/**
	 * 注册 LabelHandler
	 */
	public static final void registerHandler(LabelHandlerMap map) {
		// logger.info("FunctionSiteLabel 注册标签处理器");

		map.put("HtmlEncode", new HtmlEncodeLabelHandler());
		map.put("PageNav", new PageNavHandler());
		map.put("Repeater", new RepeaterLabelHandler());
		
	}

	/**
	 * 对内部代码进行 HTML 编码的标签，可用于测试
	 */
	static final class HtmlEncodeLabelHandler extends AbstractLabelHandler {
		@Override public int handleLabel() {
			if (label.hasChild() == false) return PROCESS_SIBLING;
			
			// 获得内部标签的输出，进行 htmlEncode，然后重新输出。
			String inner_html = env.processChild(label);
			if (inner_html != null && inner_html.length() > 0) {
				inner_html = StringHelper.htmlEncode(inner_html);
				out(inner_html);
			}

			// 执行兄弟结点。
			return PROCESS_SIBLING;
		}
	}
	
	/**
	 * 分页导航栏标签 #{PageNav } 此标签需要由其父标签提供 totalCount（总记录数）、pageSize（每页显示个数）、currPage（当前页数）。
	 * 并且从jsp页面用PublishUtil.setCurrentPage(Map vars, int currPage)方法提供从 currentPage（当前页数）。
	 * currPage可调用PublishUtil.getCurrentPage(InternalProcessEnvironment env)方法得到。
	 * 
	 * #{PageNav pageSize='20' url='search.jsp?page={page}'}
	 * #{PageNav pageSize='20' url='search.jsp?page={page}'}#{pinfo.selector}#{/PageNav} 
	 * 使用自定义标签时，pInfo是一个PaginationInfo对象，其中用pInfo.selector可显示一个 select 页面跳转 的下拉框。
	 * 其他参数的使用可参考PaginationInfo类。 
	 * 
	 * @param info_source 在系统自定义标签中定义PaginationInfo对象的来源。
	 * 
	 * @param url 导航链接地址。链接形式为：XXX.jsp?page={page}，其中{page}将自动被解释。必填。
	 * @param info_source PaginationInfo对象的来源，在系统自带的标签时使用。
	 * @param firstPageUrl 第一页地址。默认按pageUrl给定的形式解析。
	 * @param lastPageUrl 最后一页地址。默认按pageUrl给定的形式解析。
	 * @param isStatic 是否按照静态方式解析链接。默认为false。
	 * @param itemName 项目名称。默认为当前频道的项目名称，频道不存在则为”文章“；
	 * @param itemUnit 项目单位。默认为当前频道的项目单位，频道不存在则为”个“；
	 * @param linkStyle 链接文字的样式表。
	 * 
	 * @author dengxiaolong
	 */
	static final class PageNavHandler extends AbstractLabelHandler {
		/** #PageNav 标签使用的缺省模板名字, = ".builtin.pagenav"。 */
		private static final String DEFAULT_TEMPLATE = ".builtin.pagenav";
		/*
		 * (non-Javadoc)
		 * @see com.chinaedustar.publish.label.AbstractLabelHandler#handleLabel()
		 */
		@Override protected int handleLabel() {
			// 查找分页支持对象。
			PaginationInfo page_info = getPaginationInfo();
			if (page_info == null) return PROCESS_DEFAULT;
			if (page_info.getUsePage() == false) {
				out("#{?? PageNav 分页不支持，可能未设置 usePage 属性}");
				return PROCESS_SIBLING;
			}

			setDefaultAttr(page_info);
			
			// 如果有子节点，则让子节点负责显示整个模板。 
			if (label.hasChild()) {
				LocalContext local_ctxt = env.acquireLocalContext(label, 0);
				local_ctxt.setVariable("page_info", page_info);
				return PROCESS_DEFAULT;
			} else {
				String template = super.getTemplateName(DEFAULT_TEMPLATE);
				BuiltinLabel builtin_label = getBuiltinLabel(template);
				String result = builtin_label.getProcessedResult(env, new Object[] {page_info});
				out(result);
				return PROCESS_DEFAULT;
			}
		}
	
		// 查找分页支持对象。
		protected PaginationInfo getPaginationInfo() {
			PaginationInfo page_info = null;
			
			// 尝试从 #PageNav info_source 属性找局部的 page_info。 
			String info_source = label.getAttributes().safeGetStringAttribute("info_source", "page_info");
			if (info_source != null && info_source.trim().length() > 0) {
				Object obj_info = env.findVariable(info_source);					
				if (obj_info != null && obj_info instanceof PaginationInfo) {
					page_info = (PaginationInfo)obj_info;
					return page_info;
				}
			}
			
			// 从环境中找缺省的。
			page_info = (PaginationInfo)env.findVariable(PAGINATION_INFO_NAME);
			if (page_info != null) return page_info;
			
			// 还没有，则自己创建一个。
			page_info = new PaginationInfo();
			page_info.setCurrPage(1);
			page_info.setPageSize(20);
			page_info.setUsePage(true);
			return page_info;
		}
		
		// 设置分页对象的各个缺省属性，其用于 #PageNav 标签中。
		private void setDefaultAttr(PaginationInfo page_info) {
			// 获得其它属性。
			AttributeCollection coll = label.getAttributes();
			String url = coll.safeGetStringAttribute("url", null);
			String firstPageUrl = coll.safeGetStringAttribute("firstPageUrl", null);
			String lastPageUrl = coll.safeGetStringAttribute("lastPageUrl", null);
			// ?? String linkStyle = coll.safeGetStringAttribute("linkStyle", "");
			String itemName = coll.safeGetStringAttribute("itemName", null);
			String itemUnit = coll.safeGetStringAttribute("itemUnit", null);

			// 根据这些属性，以及当前频道等缺省属性继续设置 page_info.
			if (url != null) page_info.setUrlPattern(url);
			page_info.init();
			
			if (firstPageUrl != null) page_info.setFirstPageUrl(firstPageUrl);
			if (lastPageUrl != null) page_info.setLastPageUrl(lastPageUrl);
			
			// 得到当前频道
			Channel channel = getCurrentChannel();
			if (itemName == null && page_info.getItemName() == null && channel != null)
				page_info.setItemName(channel.getItemName());

			if (itemUnit == null && page_info.getItemUnit() == null && channel != null)
				page_info.setItemUnit(channel.getItemUnit());

			if (itemName != null) page_info.setItemName(itemName);
			if (itemUnit != null) page_info.setItemUnit(itemUnit);
			if (page_info.getItemName() == null) page_info.setItemName("项目");
			if (page_info.getItemUnit() == null) page_info.setItemUnit("个");
		}
		
	}
	
	/**
	 * 循环标签 #{Repeater}
	 * @param coll_obj 数据提供者，如果有父标签，数据从父标签中自动得到。优先从标签中获取数据。
	 * 		(coll_obj支持的数据类型有支持Iterator接口的数据（常用的有DataTable、List、Map等）、
	 *      以及数组(比如int[]、String[]等)。)
	 * @param var_name 循环时的变量名称，如果有父标签，将会从父标签中自动得到。优先从标签中获取变量名称。
	 * 
	 * <h3>使用实例</h3><br>
	 * <p>比如一个显示文章列表的标签:
	 * <pre>
	 * <table>
	 *   #{ShowArticleList}
	 *     <tr><td>
	 *     #{Repeater}  <!-- coll_obj、col_name从父标签ShowArticleList中得到。 -->
	 *       <!--下面的article的名称是由ShowArticleList标签给出的，也可以在Repeater标签中通过var_name属性指定自己喜欢的名称。-->
	 *       <a href="#{article.pageUrl}">#{article.name}</a>  
	 *     #{/Repeater}
	 *     </td></tr>
	 *   #{/ShowArticleList}
	 * </table>
	 * </pre>
	 * 
	 * <p>如果为独立标签，集合数据的名称须在标签中提供。如：#{Repeater coll_obj="column_articles" var_name="column"}#{column.name}#{/Repeater}</p>
	 * <p>如果嵌套在其他标签中使用，则其父标签将为其提供集合数据，不需要设置任何属性。如#{GetArticleList}#{Repeater}#{article.title}#{/Repeater}#{/GetArticleList}</p>
	 * @author dengxiaolong
	 *
	 */
	static final class RepeaterLabelHandler extends AbstractLabelHandler {
		/*
		 * (non-Javadoc)
		 * @see com.chinaedustar.publish.label.AbstractLabelHandler#handleLabel()
		 */
		@Override protected int handleLabel() {
			if (label.hasChild() == false) return PROCESS_DEFAULT;
			
			// 得到 coll_obj 对象。
			Object coll_obj = getCollObj();			
			if (coll_obj == null || (coll_obj instanceof String && ((String)coll_obj).length() == 0))
				return PROCESS_SIBLING;

			// 得到 var_name。
			String var_name = getVarName();
			
			// 执行 foreach
			env.foreach(label, var_name, coll_obj, RepeatNameSetter.Instance);

			// 执行兄弟结点，不再执行子节点了。
			return PROCESS_SIBLING;
		}
		
		/**
		 * 得到 coll_obj 对象。
		 * @param env
		 * @param label
		 * @return
		 */
		private Object getCollObj() {
			Object coll_obj = null;
			
			AttributeCollection coll = label.getAttributes();
			String label_coll_obj = coll.safeGetStringAttribute("coll_obj", null);
			// 如果在标签中有对 coll_obj 的设置，则从标签属性中获取
			if (label_coll_obj != null && label_coll_obj.trim().length() > 0) {	
				coll_obj = label_coll_obj;
			} else {
				coll_obj = env.findVariable(REPEATER_DATA_PROVIDER_NAME);
			}
			return coll_obj;
		}
		
		/**
		 * 得到 var_name。
		 * @param env
		 * @param label
		 * @return
		 */
		private String getVarName() {
			String var_name;
			// 标签中指定的变量名称。
			String label_var_name = label.getAttributes().safeGetStringAttribute("var_name", null);	
			// 如果在标签中没有定义变量名称，则从父标签中获取相应的变量名称。
			if (label_var_name != null && label_var_name.trim().length() >0) {	
				var_name = label_var_name;
			} else {
				var_name = (String)env.findVariable(REPEATER_VAR_NAME);
			}		
			
			// 如果没有得到变量的名称，则默认用 'object'
			if (var_name == null || var_name.trim().length() < 1) {
				var_name = "object";
			}	
			return var_name.trim();
		}
	}
}
