package com.chinaedustar.publish.model;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

import com.chinaedustar.publish.ParamUtil;
import com.chinaedustar.publish.PublishException;

/**
 * 表示一个频道搜索的信息
 * 
 * @author liujunxing
 */
public final class ChannelSearch {
	/** 获取参数的辅助对象。 */
	private ParamUtil param_util;
	
	/** 栏目的标识 */
	private int columnId;
	
	/** 项目的标题 */
	private String title;
	
	/** 项目的简介 */
	private String description;
	
	/** 项目的作者 */
	private String author;
	
	/** 项目的输入者 */
	private String inputer;
	
	/** 项目的来源 */
	private String source;
	
	/** 项目的关键字，注意，区别于keyword。 */
	private String keywords;
	
	/** 最小点数 */
	private int minPoint;
	
	/** 最大点数 */
	private int maxPoint;
	
	/** 开始时间 */
	private Date beginDate;
	
	/** 结束时间 */
	private Date endDate;
	
	// ************** 普通搜索的字段
	/** 查询字段 */
	private String field;
	
	/** 查询关键字 */
	private String keyword;
	
	
	// ************** 文章独有的搜索字段
	/** 文章的内容 */
	private String articleContent;
	
	// ************** 图片独有的搜索字段
	
	// ************** 软件独有的搜索字段
	/** 软件的语言 */
	private String softLanguage;
	
	/** 软件的类别 */
	private String softType;
	
	/** 软件的版本 */
	private String softVersion;
	
	/** 软件的授权 */
	private String softCopyright;
	
	/**
	 * 网址的参数。
	 */
	private String urlParam = "";
	
	/**
	 * 设置网址解析的编码格式。
	 */
	private String encoding = "UTF-8";
	
	/**
	 * 是否为高级搜索。
	 */
	private boolean isAdvanceSearch;
	
	/**
	 * 是否有搜索的请求
	 */
	private boolean haveSearchRequest;
	
	/**
	 * 搜索结果的标题
	 */
	private String searchResultTitle;

	/**
	 * @return articleContent 文章的内容
	 */
	public String getArticleContent() {
		return articleContent;
	}

	/**
	 * @param articleContent 要设置的 文章的内容
	 */
	public void setArticleContent(String articleContent) {
		this.articleContent = articleContent;
	}

	/**
	 * @return author 项目的作者
	 */
	public String getAuthor() {
		return author;
	}

	/**
	 * @param author 要设置的 项目的作者
	 */
	public void setAuthor(String author) {
		this.author = author;
	}

	/**
	 * @return beginDate 开始时间
	 */
	public Date getBeginDate() {
		return beginDate;
	}

	/**
	 * @param beginDate 要设置的 开始时间
	 */
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	/**
	 * @return columnId 栏目的标识
	 */
	public int getColumnId() {
		return columnId;
	}

	/**
	 * @param columnId 要设置的 栏目的标识
	 */
	public void setColumnId(int columnId) {
		this.columnId = columnId;
	}

	/**
	 * @return description 项目的简介
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description 要设置的 项目的简介
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return endDate 结束时间
	 */
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate 要设置的 结束时间
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	/**
	 * @return field 搜索的字段
	 */
	public String getField() {
		return field;
	}

	/**
	 * @param field 要设置的 搜索的字段
	 */
	public void setField(String field) {
		this.field = field;
	}

	/**
	 * @return inputer 项目的输入者
	 */
	public String getInputer() {
		return inputer;
	}

	/**
	 * @param inputer 要设置的 项目的输入者
	 */
	public void setInputer(String inputer) {
		this.inputer = inputer;
	}

	/**
	 * @return keywords 项目的关键字，此为项目的一个字段，区别于keyword。
	 */
	public String getKeywords() {
		return keywords;
	}

	/**
	 * @param keywords 要设置的 项目的关键字，此为项目的一个字段，区别于keyword。
	 */
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	/**
	 * @return maxPoint 最大点数
	 */
	public int getMaxPoint() {
		return maxPoint;
	}

	/**
	 * @param maxPoint 要设置的 最大点数
	 */
	public void setMaxPoint(int maxPoint) {
		this.maxPoint = maxPoint;
	}

	/**
	 * @return minPoint 最小点数
	 */
	public int getMinPoint() {
		return minPoint;
	}

	/**
	 * @param minPoint 要设置的 最小点数
	 */
	public void setMinPoint(int minPoint) {
		this.minPoint = minPoint;
	}

	/**
	 * @return softCopyright 软件的授权
	 */
	public String getSoftCopyright() {
		return softCopyright;
	}

	/**
	 * @param softCopyright 要设置的 软件的授权
	 */
	public void setSoftCopyright(String softCopyright) {
		this.softCopyright = softCopyright;
	}

	/**
	 * @return softLanguage 软件的语言
	 */
	public String getSoftLanguage() {
		return softLanguage;
	}

	/**
	 * @param softLanguage 要设置的 软件的语言
	 */
	public void setSoftLanguage(String softLanguage) {
		this.softLanguage = softLanguage;
	}

	/**
	 * @return softType 软件的类别
	 */
	public String getSoftType() {
		return softType;
	}

	/**
	 * @param softType 要设置的 软件的类别
	 */
	public void setSoftType(String softType) {
		this.softType = softType;
	}

	/**
	 * @return softVersion 软件的版本
	 */
	public String getSoftVersion() {
		return softVersion;
	}

	/**
	 * @param softVersion 要设置的 软件的版本
	 */
	public void setSoftVersion(String softVersion) {
		this.softVersion = softVersion;
	}

	/**
	 * @return source 软件的来源
	 */
	public String getSource() {
		return source;
	}

	/**
	 * @param source 要设置的 软件的来源
	 */
	public void setSource(String source) {
		this.source = source;
	}

	/**
	 * @return title 软件的标题
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title 要设置的 软件的标题
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	
	/**
	 * @return 是否为高级搜索
	 */
	public boolean getIsAdvanceSearch() {
		return this.isAdvanceSearch;
	}
	
	/**
	 * @param isAdvanceSearch 要设置的 是否为高级搜索
	 */
	public void setIsAdvanceSearch(boolean isAdvanceSearch) {
		this.isAdvanceSearch = isAdvanceSearch;
	}
	
	
	/**
	 * @return keyword 查询关键字
	 */
	public String getKeyword() {
		return keyword;
	}

	/**
	 * @param keyword 要设置的 查询关键字
	 */
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	/**
	 * @return urlParam 网址的参数
	 */
	public String getUrlParam() {
		return urlParam;
	}

	/**
	 * @param urlParam 要设置的 网址的参数
	 */
	public void setUrlParam(String urlParam) {
		this.urlParam = urlParam;
	}

	/**
	 * @return encoding CGI参数值的编码
	 */
	public String getEncoding() {
		return encoding;
	}

	/**
	 * @param encoding 要设置的 CGI参数值的编码
	 */
	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	/**
	 * @return haveSearchRequest 是否有搜索的请求
	 */
	public boolean getHaveSearchRequest() {
		return haveSearchRequest;
	}

	/**
	 * @param haveSearchRequest 要设置的 是否有搜索的请求
	 */
	public void setHaveSearchRequest(boolean haveSearchRequest) {
		this.haveSearchRequest = haveSearchRequest;
	}

	/**
	 * @return searchResultTitle 搜索结果的标题
	 */
	public String getSearchResultTitle() {
		return searchResultTitle;
	}

	
	/**
	 * @param searchResultTitle 要设置的 搜索结果的标题
	 */
	public void setSearchResultTitle(String searchResultTitle) {
		this.searchResultTitle = searchResultTitle;
	}

	// *************
	public ChannelSearch() {
		
	}
	
	private ChannelSearch(PageContext pageContext) {
		HttpServletRequest request = (HttpServletRequest)pageContext.getRequest();
		haveSearchRequest = request.getParameterMap() != null && !request.getParameterMap().isEmpty();
		if (!haveSearchRequest) {
			return;
		}
		initPageContext(pageContext);
		if (param_util == null) {
			throw new PublishException("没有给SearchInfo类初始化PageContext属性。");
		}
		
		field = param_util.safeGetStringParam("field", null);
		appendParam("field", field);
		
		isAdvanceSearch = field == null || field.trim().length() < 1;
		encoding = param_util.getPublishContext().getUriTargetEncoding();
	}
	
	/**
	 * 得到一个 SearchInfo的实例，如果没有请求搜索的信息，则返回null。
	 * @param pageContext
	 * @return
	 */
	public static ChannelSearch getInstance(PageContext pageContext) {
		ChannelSearch si = new ChannelSearch(pageContext);
		if (!si.getHaveSearchRequest()) return null;
		
		return si;
	}
	
	/**
	 * 初始化软件的 pageContext。
	 * @param pageContext
	 */
	private void initPageContext(PageContext pageContext) {
		param_util = new ParamUtil(pageContext);
	}
	
	/**
	 * 初始化搜索的字段
	 * @param type 0：公用的字段；1：文章；2：图片；3：软件
	 */
	public void init(int type) {
		initBasic();
		if (!isAdvanceSearch) {		// 如果不是高级搜索，则需要初始化 field 属性。
			initField(type);
			return;
		}
		initCommon();
		if (type == 0) return;
		switch (type) {
		case 1:
			initArticle();
			break;
		case 2:
			initPhoto();
			break;
		case 3:
			initSoft();
			break;
		}
	}
	
	/**
	 * 初始化field字段。
	 * @param type 0：公用的字段；1：文章；2：图片；3：软件
	 */
	private void initField(int type) {
		switch (type) {
		case 0:
			field = "title";
			searchResultTitle = "搜索结果";
			break;
		case 1:
			chooseFiled(new String[]{"title", "content", "author", "inputer", "keywords"});
			break;
		case 2:
			chooseFiled(new String[]{"title", "description", "author", "inputer"});
			break;
		case 3:
			chooseFiled(new String[]{"title", "description", "author", "inputer"});
		}
	}
	
	/**
	 * 得到字段名称的中文名称。
	 * @return
	 */
	public String getFieldName() {
		if (field.equals("title")) {
			return "标题";
		} else if (field.equals("content")){
			return "内容";
		} else if (field.equals("author")) {
			return "作者";
		} else if (field.equals("inputer")) {
			return "录入者";
		} else if (field.equals("keywords")) {
			return "关键词";
		} else if (field.equals("description")) {
			return "简介";
		}
		return "";
	}
	
	
	/**
	 * 选择搜索的字段名称，没有在标准的字段名称里边没有找到，则默认为title。
	 * @param standardFields 标准的搜索字段名。
	 * @return
	 */
	private void chooseFiled(String[] standardFields) {
		for (int i = 0; i < standardFields.length; i++) {
			if (standardFields[i].equals(this.field)) {
				return;
			}
		}
		this.field = "title";
	}
	
	/**
	 * 初始化基本字段。
	 *
	 */
	private void initBasic() {
		keyword = param_util.safeGetChineseParameter("keyword", "");
		columnId = param_util.safeGetIntParam("columnId");
		
		appendParam("keyword", keyword);
		appendParam("columnId", String.valueOf(columnId));
	}
	
	/**
	 * 初始化公用的字段。
	 *
	 */
	private void initCommon() {
		title = param_util.safeGetChineseParameter("title");
		description = param_util.safeGetChineseParameter("description");
		author = param_util.safeGetChineseParameter("author");
		inputer = param_util.safeGetChineseParameter("inputer");
		source = param_util.safeGetChineseParameter("source");
		keywords = param_util.safeGetChineseParameter("keywords");
		minPoint = param_util.safeGetIntParam("minPoint");
		maxPoint = param_util.safeGetIntParam("maxPoint");
		try {
			beginDate = new SimpleDateFormat("yyyy-MM-dd").parse(param_util.safeGetStringParam("beginDate"));
		} catch (Exception ex) {
			beginDate = null;
		}
		try {
			endDate = new SimpleDateFormat("yyyy-MM-dd").parse(param_util.safeGetStringParam("endDate"));
		} catch (Exception ex) {
			endDate = null;
		}
		appendParam("title", title);
		appendParam("description", description);
		appendParam("author", author);
		appendParam("inputer", inputer);
		appendParam("source", source);
		appendParam("keywords", keywords);
		
		if (minPoint > 0 && maxPoint > 0 && minPoint > maxPoint) {
			appendParam("minPoint", String.valueOf(minPoint));
			appendParam("maxPoint", String.valueOf(maxPoint));
		}
		if (beginDate != null && endDate != null && (beginDate.equals(endDate) || beginDate.before(endDate))) {
			appendParam("beginDate", new SimpleDateFormat("yyyy-MM-dd").format(beginDate));
			appendParam("endDate", new SimpleDateFormat("yyyy-MM-dd").format(endDate));
		}
		
	}
	
	/**
	 * 初始化文章的相关字段
	 *
	 */
	private void initArticle() {
		this.articleContent = param_util.safeGetStringParam("content");
		
		appendParam("content", this.articleContent);
	}

	/**
	 * 初始化图片的相关字段
	 *
	 */
	private void initPhoto() {
		//
	}
	
	/**
	 * 初始化软件的相关字段
	 *
	 */
	private void initSoft() {
		softLanguage = param_util.safeGetStringParam("language");
		softType = param_util.safeGetStringParam("type");
		softVersion = param_util.safeGetStringParam("version");
		softCopyright = param_util.safeGetStringParam("copyright");
		
		appendParam("language", softLanguage);
		appendParam("type", softType);
		appendParam("version", softVersion);
		appendParam("copyright", softCopyright);
	}

	/**
	 * 根据这个实例数据创建 ItemQueryOptionEx 对象以用于查询。
	 * @return
	 */
	public ItemQueryOptionEx createQueryOption() {
		ItemQueryOptionEx query_option = new ItemQueryOptionEx();
		// query_option.channelId = this.channelId;
		query_option.columnId = this.columnId;
		query_option.includeChildColumns = true;
		query_option.status = Item.STATUS_APPR;
		query_option.isDeleted = false;
		
		// 完全转换到 ItemQueryOption
		query_option.title = this.title;
		query_option.description = this.description;
		query_option.author = this.author;
		query_option.inputer = this.inputer;
		query_option.source = this.source;
		query_option.keywords = this.keywords;
		query_option.minPoint = this.minPoint;
		query_option.maxPoint = this.maxPoint;
		query_option.beginDate = this.beginDate;
		query_option.endDate = this.endDate;
		query_option.field = this.field;
		query_option.keyword = this.keyword;
		query_option.articleContent = this.articleContent;
		query_option.softCopyright = this.softCopyright;
		query_option.softLanguage = this.softLanguage;
		query_option.softType = this.softType;
		query_option.softVersion = this.softVersion;
		
		return query_option;
	}
	
	/**
	 * 给网址附加参数
	 * @param key
	 * @param value
	 */
	private void appendParam(String key, String value) {
		appendParam(key, value, encoding);
	}
	
	/**
	 * 给网址附加参数
	 * @param key 参数名称
	 * @param value 参数值
	 * @param encoding 编码
	 */
	public void appendParam(String key, String value, String encoding) {
		if (null == value || value.trim().length() < 1) return;
		if(!"".equals(urlParam)) urlParam += "&";
		urlParam += key + "=";
		
		if (null == encoding) {
			urlParam += value;
		} else {
			try {
				urlParam += URLEncoder.encode(value, encoding);
			} catch (UnsupportedEncodingException e) {
				urlParam += value;
			}
		}
	}	
	
	/**
	 * 得到URL地址。
	 * @param pageUrl
	 */
	public String getUrl(String pageUrl) {
		String url = pageUrl + "?page={page}";
		if (urlParam.trim().length() > 0) {
			url += "&" + urlParam;
		}
		return url;
	}
	
	/**
	 * 设置搜索结果的标题
	 * @param itemName 项目名称
	 */
	public void resetResultTitle(String itemName) {
		if (isAdvanceSearch) {
			searchResultTitle = "本次高级搜索的结果";
		} else {
			searchResultTitle = getFieldName() + "中含有 <font color='red'>" + keyword
					+ "</font> 的" + itemName;
		}
	}
}
