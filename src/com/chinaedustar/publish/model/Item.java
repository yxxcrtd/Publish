package com.chinaedustar.publish.model;

import java.util.List;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.chinaedustar.common.util.StringHelper;
import com.chinaedustar.publish.PublishContext;
import com.chinaedustar.publish.itfc.RepeatNameProvide;

/**
 * 定义通用的项目类。
 * 
 * @author liujunxing
 * 
 * 其现在具有三个子类: Article, Photo, Soft 
 * 
 */
public class Item extends AbstractPageModelBase implements RepeatNameProvide {

	// 所属频道、栏目标识。
	
	/** 所属的频道标识。外键关联到Cor_Channel表。 可以为 0，表示不属于任何频道。 */
	private int channelId;
	
	/** 所属的栏目表示。外键关联到Cor_Column表，且其栏目的ChannelId必须和这里的ChannelId相同。
	    可以为 0 ，表示不属于任何栏目。 */
	private int columnId;
	
	// 项目的通用属性。
	
	/** 项目类型。通过项目类型能够得知这个项目具体实现类，如 'article', 'photo', 'soft' 等。
	 * 这个值一旦设定，就不能更改。 */
	private String itemClass = "item";
	
	/** 并发修改版本号数字，每次自动 + 1。 */
	private int usn;
	
	/** 此项目的创建时间。（第一次插入时自动产生，以后不能修改） */
	private java.util.Date createTime;
	
	/** 最后修改时间。（修改的时候自动更新） */
	private java.util.Date lastModified;
	
	/** 在查询时使用 - 不区分审核状态 = null。 */
	public static final Integer STATUS_ALL = null;
	
	/** 在查询时使用 - 未审核 = 0。 */
	public static final Integer STATUS_UNAPPR = 0;
	
	/** 在查询时使用 - 审核通过 = 1。 */
	public static final Integer STATUS_APPR = 1;
	
	/** 草稿状态 = -1。 */
	public static final Integer STATUS_SCRIPT = -1;
	
	/** 被退稿的状态 = -2。 */
	public static final Integer STATUS_REJECTED = -2;

	
	/** 项目状态，-2：退稿；-1：草稿，0：未审核，1：审核通过。 */
	private int status;
	
	/** 为此项目评定的星级。 */
	private int stars;
	
	/** 是否置顶。 */
	private boolean top;
	
	/** 是否精华。 */
	private boolean elite;
	
	/** 是否已经删除。 */
	private boolean deleted;
	
	/** 是否编辑推荐。 */
	private boolean commend;
	
	/** 是否是热门。 */
	private boolean hot;
	
	/** 点击数，这是一个统计字段。最好是批量更新，一个一个更新比较慢。 */
	private int hits;
	
	/** 权限标志。 */
	private int privilege;
	
	/** 计费标志。 */
	private int charge;
	
	/** 自定义属性标志。 */
	private int custom;
	
	/** 投票属性标志。 */
	private int voteFlag;
	
	/** Blog属性标志。 */
	private int blogFlag;
	
	/** BBS属性标志。 */
	private int bbsFlag;
	
	/** 评论属性标志。 */
	private int commentFlag;
	
	/** 评论条数。 */
	private int commentCount;
	
	// 一般项目属性

	/** 标题。一般总是给出。 */
	private String title;
	
	/** 短的标题，可选使用。 */
	private String shortTitle;
	
	/** 此项目的作者，或拥有者。 */
	private String author;
	
	/** 此项目的来源。 */
	private String source;
	
	/** 此项目的录入者。 */
	private String inputer;
	
	/** 此项目的编辑(审核者)。 */
	private String editor;
	
	/** 查找此项目用的关键字。 */
	private String keywords;
	
	/** 内容项的描述信息，不能超过255个字符。 */
	private String description;
	
	/**
	 * 缺省构造函数。
	 *
	 */
	public Item() {
		
	}
	
	/**
	 * 安全的转换一个 item 到一个简洁表示的字符串。
	 * @param item
	 * @return
	 */
	public static String toInfoString(Item item) {
		if (item == null) return "";
		Channel channel = item.getChannel();
		String itemClassName = channel == null ? "项目" : channel.getItemName();
		return itemClassName + " '" + item.getTitle() + "'(id=" + item.getId() + ")";
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.chinaedustar.publish.model.AbstractPageModelBase#get(java.lang.String)
	 */
	@Override public Object get(String name) {
		if ("channelId".equalsIgnoreCase(name)) {
			return channelId;
		} else if ("columnId".equalsIgnoreCase(name)) {
			return columnId;
		} else if ("itemClass".equalsIgnoreCase(name)) {
			return itemClass;
		} else if ("usn".equalsIgnoreCase(name)) {
			return usn;
		} else if ("createTime".equalsIgnoreCase(name)) {
			return createTime;
		} else if ("lastModified".equalsIgnoreCase(name)) {
			return lastModified;
		} else if ("status".equalsIgnoreCase(name)) {
			return status;
		} else if ("stars".equalsIgnoreCase(name)) {
			return stars;
		} else if ("top".equalsIgnoreCase(name)) {
			return top;
		} else if ("elite".equalsIgnoreCase(name)) {
			return elite;
		} else if ("deleted".equalsIgnoreCase(name)) {
			return deleted;
		} else if ("commend".equalsIgnoreCase(name)) {
			return commend;
		} else if ("isHot".equalsIgnoreCase(name)) {
			return hot;
		} else if  ("hits".equalsIgnoreCase(name)) {
			return hits;
		} else if ("privilege".equalsIgnoreCase(name)) {
			return privilege;
		} else if ("charge".equalsIgnoreCase(name)) {
			return charge;
		} else if ("custom".equalsIgnoreCase(name)) {
			return custom;
		} else if ("voteFlag".equalsIgnoreCase(name)) {
			return voteFlag;
		} else if ("blogFlag".equalsIgnoreCase(name)) {
			return blogFlag;
		} else if ("bbsFlag".equalsIgnoreCase(name)) {
			return bbsFlag;
		} else if ("commentFlag".equalsIgnoreCase(name)) {
			return commentFlag;
		} else if ("commentCount".equalsIgnoreCase(name)) {
			return commentCount;
		} else if ("title".equalsIgnoreCase(name)) {
			return title;
		} else if ("shortTitle".equalsIgnoreCase(name)) {
			return shortTitle;
		} else if ("author".equalsIgnoreCase(name)) {
			return author;
		} else if ("source".equalsIgnoreCase(name)) {
			return source;
		} else if ("inputer".equalsIgnoreCase(name)) {
			return inputer;
		} else if ("editor".equalsIgnoreCase(name)) {
			return editor;
		} else if ("keywords".equalsIgnoreCase(name)) {
			return keywords;
		} else if ("description".equalsIgnoreCase(name)) {
			return description;
		} else {
			return super.get(name);
		}
	}

	/**
	 * 得到所属的频道标识。外键关联到Cor_Channel表。 可以为 NULL，表示不属于任何频道。
	 * @return
	 */
	public int getChannelId() {
		return this.channelId;
	}
	
	/**
	 * 设置所属的频道标识。外键关联到Cor_Channel表。 可以为 NULL，表示不属于任何频道。
	 * @param channelId
	 */
	public void setChannelId(int channelId) {
		this.channelId = channelId;
	}
	
	/**
	 * 得到所属的栏目表示。外键关联到Cor_Column表，且其栏目的ChannelId必须和这里的ChannelId相同。可以为NULL，表示不属于任何栏目。
	 * @return
	 */
	public int getColumnId() {
		return this.columnId;
	}
	
	/**
	 * 设置所属的栏目表示。外键关联到Cor_Column表，且其栏目的ChannelId必须和这里的ChannelId相同。可以为NULL，表示不属于任何栏目。
	 * @param columnId
	 */
	public void setColumnId(int columnId) {
		this.columnId = columnId;
	}
	
	/**
	 * 得到项目类型。通过项目类型能够得知这个项目具体实现类，如 'article', 'photo', 'soft' 等。
	 * @return
	 */
	public String getItemClass() {
		return this.itemClass;
	}
	
	/**
	 * 设置项目类型。通过项目类型能够得知这个项目具体实现类，如 'article', 'photo', 'soft' 等。
	 * @param itemClass
	 */
	public void setItemClass(String itemClass) {
		this.itemClass = itemClass;
	}
	
	/**
	 * 得到并发修改版本号数字。
	 * @return
	 */
	public int getUsn() {
		return this.usn;
	}
	
	/**
	 * 设置并发修改版本号数字。
	 * @param usn
	 */
	public void setUsn(int usn) {
		this.usn = usn;
	}
	
	/**
	 * 得到此项目的创建时间。（第一次插入时自动产生，以后不能修改）
	 * @return
	 */
	public java.util.Date getCreateTime() {
		return this.createTime;
	}
	
	/**
	 * 设置此项目的创建时间。（第一次插入时自动产生，以后不能修改）
	 * @param createTime
	 */
	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}
	
	/**
	 * 得到最后修改时间。（修改的时候自动更新）
	 * @return
	 */
	public java.util.Date getLastModified() {
		return this.lastModified;
	}
	
	/**
	 * 设置最后修改时间。（修改的时候自动更新）
	 * @param lastModified
	 */
	public void setLastModified(java.util.Date lastModified) {
		this.lastModified = lastModified;
	}
	
	/**
	 * 得到项目状态，-2：退稿，-1：草稿，0：未审核，1：审核通过。
	 * @return
	 */
	public int getStatus() {
		return this.status;
	}
	
	/**
	 * 设置项目状态，-2：退稿，-1：草稿，0：未审核，1：审核通过。
	 * @param status
	 */
	public void setStatus(int status) {
		this.status = status;
	}
	
	/**
	 * 得到为此项目评定的星级。
	 * @return
	 */
	public int getStars() {
		return this.stars;
	}
	
	/**
	 * 根据星数返回一个字符串。
	 * @return
	 */
	public String getStarStr() {
		switch (this.stars) {
		case 0: return "";
		case 1: return "★";
		case 2: return "★★";
		case 3: return "★★★";
		case 4: return "★★★★";
		case 5: return "★★★★★";
		default:
			if (stars < 0) return "";
			StringBuilder strbuf = new StringBuilder();
			for(int i = 0; i < stars; ++i)
				strbuf.append('★');
			return strbuf.toString();
		}
	}
	
	/**
	 * 设置为此项目评定的星级。
	 * @param stars
	 */
	public void setStars(int stars) {
		this.stars = stars;
	}
	
	/**
	 * 得到是否置顶。
	 * @return
	 */
	@Deprecated
	public boolean getOnTop() {
		return this.top;
	}
	
	/**
	 * 设置是否置顶。
	 * @param onTop
	 */
	@Deprecated
	public void setOnTop(boolean onTop) {
		this.top = onTop;
	}
	
	/**
	 * 得到是否置顶。
	 * @return
	 */
	public boolean getTop() {
		return this.top;
	}
	
	/**
	 * 设置是否置顶。
	 * @param isTop
	 */
	public void setTop(boolean top) {
		this.top = top;
	}
	
	/**
	 * 得到是否精华。
	 * @return
	 */
	public boolean getElite() {
		return this.elite;
	}
	
	/**
	 * 设置是否精华。
	 * @param elite
	 */
	public void setElite(boolean elite) {
		this.elite = elite;
	}
	
	/**
	 * 得到是否已经删除。
	 * @return
	 */
	public boolean getDeleted() {
		return this.deleted;
	}
	
	/**
	 * 设置是否已经删除。
	 * @param deleted
	 */
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
	
	/**
	 * 得到是否编辑推荐。
	 * @return
	 */
	@Deprecated
	public boolean getRecommend() {
		return this.commend;
	}
	
	/**
	 * 设置是否编辑推荐。
	 * @param recommend
	 */
	@Deprecated
	public void setRecommend(boolean recommend) {
		this.commend = recommend;
	}
	
	/**
	 * 得到是否编辑推荐。
	 * @return
	 */
	public boolean getCommend() {
		return this.commend;
	}
	
	/**
	 * 设置是否编辑推荐。
	 * @param recommend
	 */
	public void setCommend(boolean commend) {
		this.commend = commend;
	}
	
	/** 是否是热门。 */
	@Deprecated
	public boolean getIsHot() {
		return this.hot;
	}
	
	/** 是否是热门。 */
	@Deprecated
	public void setIsHot(boolean isHot) {
		this.hot = isHot;
	}

	/** 是否是热门。 */
	public boolean getHot() {
		return this.hot;
	}
	
	/** 是否是热门。 */
	public void setHot(boolean hot) {
		this.hot = hot;
	}
	
	/**
	 * 得到点击数，这是一个统计字段。最好是批量更新，一个一个更新比较慢。
	 * @return
	 */
	public int getHits() {
		return this.hits;
	}
	
	/**
	 * 设置点击数，这是一个统计字段。最好是批量更新，一个一个更新比较慢。
	 * @param hits
	 */
	public void setHits(int hits) {
		this.hits = hits;
	}
	
	/**
	 * 得到权限标志。
	 * @return
	 */
	public int getPrivilege() {
		return this.privilege;
	}
	
	/**
	 * 设置权限标志。
	 * @param privilege
	 */
	public void setPrivilege(int privilege) {
		this.privilege = privilege;
	}
	
	/**
	 * 得到计费标志。
	 * @return
	 */
	public int getCharge() {
		return this.charge;
	}
	
	/**
	 * 设置计费标志。
	 * @param charge
	 */
	public void setCharge(int charge) {
		this.charge = charge;
	}
	
	/**
	 * 得到自定义属性标志。
	 * @return
	 */
	public int getCustom() {
		return this.custom;
	}
	
	/**
	 * 设置自定义属性标志。
	 * @param custom
	 */
	public void setCustom(int custom) {
		this.custom = custom;
	}
	
	/**
	 * 得到投票属性标志。
	 * @return
	 */
	public int getVoteFlag() {
		return this.voteFlag;
	}
	
	/**
	 * 设置投票属性标志。
	 * @param voteFlag
	 */
	public void setVoteFlag(int voteFlag) {
		this.voteFlag = voteFlag;
	}
	
	/**
	 * 得到Blog属性标志。
	 * @return
	 */
	public int getBlogFlag() {
		return this.blogFlag;
	}
	
	/**
	 * 设置Blog属性标志。
	 * @param blogFlag
	 */
	public void setBlogFlag(int blogFlag) {
		this.blogFlag = blogFlag;
	}
	
	/**
	 * 得到BBS属性标志。
	 * @return
	 */
	public int getBbsFlag() {
		return this.bbsFlag;
	}
	
	/**
	 * 设置BBS属性标志。
	 * @param bbsFlag
	 */
	public void setBbsFlag(int bbsFlag) {
		this.bbsFlag = bbsFlag;
	}
	
	/**
	 * 得到评论属性标志。
	 * @return
	 */
	public int getCommentFlag() {
		return this.commentFlag;
	}
	
	/**
	 * 设置评论属性标志。
	 * @param comments
	 */
	public void setCommentFlag(int commentFlag) {
		this.commentFlag = commentFlag;
	}
	
	/**
	 * 得到评论条数。
	 * @return
	 */
	public int getCommentCount() {
		return commentCount;
	}
	
	/**
	 * 设置评论条数
	 * @param commentCount
	 */
	public void setCommentCount(int commentCount) {
		this.commentCount = commentCount;
	}

	// 一般项目属性
	/**
	 * 得到标题。一般总是给出。
	 * @return
	 */
	public String getTitle() {
		return this.title;
	}
	
	/**
	 * 设置标题。一般总是给出。
	 * @param title
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	
	/**
	 * 得到短的标题，可选使用。
	 * @return
	 */
	public String getShortTitle() {
		return this.shortTitle;
	}
	
	/**
	 * 设置短的标题，可选使用。
	 * @param shortTitle
	 */
	public void setShortTitle(String shortTitle) {
		this.shortTitle = shortTitle;
	}
	
	/**
	 * 得到此项目的作者，或拥有者。
	 * @return
	 */
	public String getAuthor() {
		return this.author;
	}
	
	/**
	 * 设置此项目的作者，或拥有者。
	 * @param author
	 */
	public void setAuthor(String author) {
		this.author = author;
	}
	
	/**
	 * 得到此项目的来源。
	 * @return
	 */
	public String getSource() {
		return this.source;
	}
	
	/**
	 * 设置此项目的来源。
	 * @param source
	 */
	public void setSource(String source) {
		this.source = source;
	}
	
	/**
	 * 得到此项目的录入者。
	 * @return
	 */
	public String getInputer() {
		return this.inputer;
	}
	
	/**
	 * 设置此项目的录入者。
	 * @param inputer
	 */
	public void setInputer(String inputer) {
		this.inputer = inputer;
	}
	
	/**
	 * 得到此项目的编辑(审核者)。
	 * @return
	 */
	public String getEditor() {
		return this.editor;
	}
	
	/**
	 * 设置此项目的编辑(审核者)。
	 * @param editor
	 */
	public void setEditor(String editor) {
		this.editor = editor;
	}
	
	/**
	 * 得到查找此项目用的关键字。
	 * @return
	 */
	public String getKeywords() {
		return this.keywords;
	}
	
	/**
	 * 设置查找此项目用的关键字。
	 * @param keywords
	 */
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	
	/**
	 * 得到内容项的描述信息，不能超过255个字符。
	 * @return
	 */
	public String getDescription() {
		return description;
	}
	
	/**
	 * 设置内容项的描述信息，不能超过255个字符。
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	// === 更新事件 ===========================================================
	
	/**
	 * 保存之前发生的事件，派生类根据事件进行额外处理。当栏目对象保存项目时此方法被调用。<br>
	 *   一般用于将某些数据进行格式化处理，保证其能够以正确的方式保存到数据库中。<br>
	 * 缺省 Item 基类设置了 lastModifed 时间，更新了 usn 更新计数器。
	 */
	public void beforeSave(Object sender) {
		java.util.Date curr_date = new java.util.Date();
		this.lastModified = curr_date;
		this.usn++;
	}
	
	/**
	 * 保存之后发生的事件，派生类根据事件进行额外处理。当栏目对象保存项目完成之后此方法被调用。<br>
	 * 缺省 Item 基类不做额外处理。
	 */
	public void afterSave(Object sender) {
		
	}
	
	// === 相关对象 ===========================================================
	
	private Channel channel = null;
	
	/** 获得当前项目所属频道。 */
	public Channel getChannel() {
		if (channel != null && channel.getId() == this.channelId) return channel;
		if (pub_ctxt == null) return null;
		this.channel = pub_ctxt.getSite().getChannel(this.channelId);
		return channel;
	}
	
	/** 设置项目的相关频道对象。 */
	public void setChannel(Channel channel) {
		this.channel = channel;
	}
	
	/** 所属栏目对象 - 不一定是，因此在 getColumn() 中有检测。 */
	private Column column = null;
	
	/** 获得当前项目所属栏目。 */
	public Column getColumn() {
		if (column != null && column.getId() == this.columnId) return column;
		if (columnId == 0 && column != null && column.getParentId() == 0) return column;
		if (pub_ctxt == null) return null;
		
		Channel channel = this.getChannel();
		if (columnId == 0 || columnId == channel._getCreateRootColumn().getId()) 
			return channel._getCreateRootColumn();
		
		this.column = channel.getColumnTree().getColumn(columnId);
		return column;
	}
	
	/** 设置项目的相关栏目对象。 */
	public void setColumn(Column column) {
		this.column = column;
	}

	/*
	 * (non-Javadoc)
	 * @see com.chinaedustar.publish.model.AbstractModelBase#getParent()
	 */
	@Override public ModelObject getParent() {
		return getColumn();
	}

	/*
	 * (non-Javadoc)
	 * @see com.chinaedustar.publish.model.AbstractModelBase#getUrlResolver()
	 */
	@Override public UrlResolver getUrlResolver() {
		// 这里假定 Item 一定在一个频道中, 且栏目不是其解析器。
		return this.getChannel();
	}
	
	/** 这个项目所属专题的标识数组。 */
	private List<Integer> special_ids = null;
	
	/**
	 * 获得这个项目所属专题的标识数组，仅有标识而没有专题其它属性。
	 * 如果没有加载则立刻加载。
	 */
	public List<Integer> getSpecialIds() {
		if (pub_ctxt == null || this.getId() == 0) return null;
		
		if (this.special_ids == null) {
			this.special_ids = loadSpecialIds();
		}
		return special_ids;
	}
	
	/**
	 * 加载当前项目所属专题。
	 * @return
	 */
	public List<Integer> loadSpecialIds() {
		// 现在加载此属性。
		String hql = "SELECT specialId FROM RefSpecialItem WHERE itemId = " + this.getId();
		@SuppressWarnings("unchecked")
		List<Integer> result = pub_ctxt.getDao().list(hql);
		return result;
	}
	
	/**
	 * 获得内部的 special_ids 数据，和 getSpecialIds 不同之处在于，如果没有加载则不加载。
	 * 此方法用于更新项目时候使用。
	 * 
	 * 页面收集到项目所属专题数据之后，通过 setSpecialIds() 方法设置到 Item 里面，
	 *   更新的时候通过 _getSpecialIds() 得到此数据，如果 == null 则不更新所属专题数据，
	 *   否则根据 special_ids 更新所属专题数据。
	 * @return
	 */
	public List<Integer> _getSpecialIds() {
		return special_ids;
	}
	
	/**
	 * 设置这个项目的所属专题标识，如果设置了这个标识，则在创建/更新的时候会更新对应数据库。
	 * @param special_ids
	 */
	public void setSpecialIds(List<Integer> special_ids) {
		this.special_ids = special_ids;
	}

	// === 接口 RepeatNameProvide 的实现 ======================================

	/*
	 * (non-Javadoc)
	 * @see com.chinaedustar.publish.itfc.RepeatNameProvide#getRepeatItemNames()
	 */
	public String[] getRepeatItemNames() {
		// 我们在循环的时候提供三个名字： 'article|photo|soft', 'item', 'object' 
		String[] names = new String[] { this.getItemClass(), "item", "object" };
		return names;
	}

	// === 业务方法 ===========================================================
	
	/**
	 * 得到评论集合对象。
	 * @return 评论集合对象。
	 */
	public CommentCollection getCommentCollection() {
		CommentCollection collection = new CommentCollection();
		collection._init(pub_ctxt, this);
		return collection;
	}

	/*
	 * (non-Javadoc)
	 * @see com.chinaedustar.publish.model.AbstractPageModelBase#calcPageUrl()
	 */
	public String calcPageUrl() {
		String className = StringHelper.capFirst(this.getItemClass());

		Channel channel = this.getChannel();
		if (channel == null) return "";
		String page_url = "";
		if (channel.getNeedGenerateItem()) {
			page_url = getStaticPageUrl();
		} else {
			page_url = "show" + className + ".jsp?" + className.toLowerCase() + "Id=" + getId(); 
		}
		return channel.resolveUrl(page_url);
	}
	
	/**
	 * 得到新的静态化地址。
	 * @return
	 */
	protected String getNewStaticPageUrl(PublishContext pub_ctxt) {
		Channel channel = this.getChannel();
		if (channel.getNeedGenerateItem() == false) return "";
		
		Column column = this.getColumn();
		return getItemDirPath(channel, column) + getItemFileName(channel, this) + getFileExtName(channel.getFileExtItem());
	}
	
	/**
	 * 得到目录路径
	 * @param channel 频道
	 * @param column 栏目
	 * @return
	 */
	private String getItemDirPath(Channel channel, Column column) {
		String channelDir = ""; // channel.getChannelDir() + "/";
		if (column == null) column = channel._getCreateRootColumn();
		Date now = new Date();
		switch (channel.getStructureType()) {
		case 1:
			// <li>1 - 频道/大类/小类/日期/文件（栏目分级，再按日期分，每天一个目录）
			//   例：Article/ASP/JiChu/2004-08-25/1368.html
			return channelDir + column.getRealColumnPath() + getFormateDate("yyyy-MM-dd", now);
		case 2:
			// <li>2 - 频道/大类/小类/文件（栏目分级，不再按月份）
		    //   例：Article/ASP/JiChu/1368.html
			return channelDir + column.getRealColumnPath();
		case 3:
		    // <li>3 - 频道/栏目/月份/文件（栏目平级，再按月份保存）
		    //   例：Article/JiChu/200408/1368.html
			return channelDir + getColumnDir(channel, column) + getFormateDate("yyyyMM", now);
		case 4:
		    // <li>4 - 频道/栏目/日期/文件（栏目平级，再按日期分，每天一个目录）
		    //   例：Article/JiChu/2004-08-25/1368.html
			return channelDir + getColumnDir(channel, column) + getFormateDate("yyyy-MM-dd", now);
		case 5:
		    // <li>5 - 频道/栏目/文件（栏目平级，不再按月份）
		    //   例：Article/JiChu/1368.html
			return channelDir + getColumnDir(channel, column);
		case 6:
		    // <li>6 - 频道/文件（直接放在频道目录中）
		    //   例：Article/1368.html
			return channelDir;
		case 7:
		    // <li>7 - 频道/HTML/文件（直接放在指定的“HTML”文件夹中）
		    //   例：Article/HTML/1368.html
			return channel.getChannelDir() + "/html/";
		case 8:
		    // <li>8 - 频道/年份/文件（直接按年份保存，每年一个目录）
		    //   例：Article/2004/1368.html
			return channelDir + getFormateDate("yyyy", now);
		case 9:
		    // <li>9 - 频道/月份/文件（直接按月份保存，每月一个目录）
		    //    例：Article/200408/1368.html
			return channelDir + getFormateDate("yyyyMM", now);
		case 10:
		    // <li>10 - 频道/日期/文件（直接按日期保存，每天一个目录）
		    //   例：Article/2004-08-25/1368.html
			return channelDir + getFormateDate("yyyy-MM-dd", now);
		case 11:
		    // <li>11 - 频道/年份/月份/文件（先按年份，再按月份保存，每月一个目录）
		    //   例：Article/2004/200408/1368.html
			return channelDir + getFormateDate("yyyy", now) + getFormateDate("yyyyMM", now);
		case 12:
		    // <li>12 - 频道/年份/日期/文件（先按年份，再按日期分，每天一个目录）
		    //   例：Article/2004/2004-08-25/1368.html
			return channelDir + getFormateDate("yyyy", now) + getFormateDate("yyyy-MM-dd", now);
		case 13:
		    // <li>13 - 频道/月份/日期/文件（先按月份，再按日期分，每天一个目录）
		    //   例：Article/200408/2004-08-25/1368.html
			return channelDir + getFormateDate("yyyyMM", now) + getFormateDate("yyyy-MM-dd", now);
		case 14:
		    // <li>14 - 频道/年份/月份/日期/文件（先按年份，再按日期分，每天一个目录）
		    //   例：Article/2004/200408/2004-08-25/1368.html
			return channelDir + getFormateDate("yyyy", now) + getFormateDate("yyyyMM", now) + getFormateDate("yyyy-MM-dd", now);
		case 0:
		default:
			// 原来的缺省： return channelDir + column.getRealColumnPath();
		    // <li>0 - 频道/大类/小类/月份/文件（缺省：栏目分级，再按月份保存） 
		    //   例：Article/ASP/JiChu/200408/1368.html
			return channelDir + column.getRealColumnPath() + getFormateDate("yyyyMM", now);
		}
	}
	
	/**
	 * 得到文件名称
	 * @param channel
	 * @param item
	 * @return
	 */
	private String getItemFileName(Channel channel, Item item) {
		switch (channel.getFileNameType()) {
		case 1:
		    // 1 - 更新时间.html    例：20040828112308.html；
			return getFormateDate("yyyyMMddhhmmss", item.getLastModified());
		case 2:
		    // 2 - 频道英文名_文章ID.html    例：Article_1358.html；
			return channel.getChannelDir() + "_" + item.getId();
		case 3:
		    // 3 - 频道英文名_更新时间.html    例：Article_20040828112308.html；
			return channel.getChannelDir() + "_" + getFormateDate("yyyyMMddhhmmss", item.getLastModified());
		case 4:
		    // 4 - 更新时间_ID.html    例：20040828112308_1358.html；
			return getFormateDate("yyyyMMddhhmmss", item.getLastModified()) + "_" + item.getId();
		case 5:
		    // 5 - 频道英文名_更新时间_ID.html    例：Article_20040828112308_1358.html。
			return channel.getChannelDir() + "_" + getFormateDate("yyyyMMddhhmmss", item.getLastModified()) + "_" + item.getId();
		case 0:
		default:
		    // 0 - 文章ID.html    例：1358.html；
			return String.valueOf(item.getId());
		}
	}
	
	/**
	 * 得到栏目目录
	 * @param channel 频道
	 * @param column 栏目
	 * @return
	 */
	private String getColumnDir(Channel channel, Column column) {
		return column.getId() == channel.getRootColumnId() ? "" : column.getColumnDir() + "/";
	}
	
	/**
	 * 得到格式化的日期格式
	 * @param format
	 * @return
	 */
	private static String getFormateDate(String format, Date now) {
		return new SimpleDateFormat(format).format(now) + "/";
	}

	/*
	 * (non-Javadoc)
	 * @see com.chinaedustar.publish.model.AbstractPageModelBase#updateGenerateStatus()
	 */
	@Override public void updateGenerateStatus() {
		super.updateGenerateStatus(Item.class, _getPublishContext());
	}

	/**
	 * 删除掉这个项目的所有专题引用。
	 * @return 返回删除掉的引用数量。
	 */
	public int deleteAllRsi() {
		String hql = "DELETE FROM RefSpecialItem WHERE itemId = " + this.getId();
		return pub_ctxt.getDao().bulkUpdate(hql);
	}

	/**
	 * 删除掉静态化的文件。同时更新是否已经静态化标志。
	 *
	 */
	public void removeHtml() {
		// 删除文件。
		String fileName = this.getStaticPageUrl();
		if (fileName != null && fileName.length() > 0) {
			fileName = this.getChannel().resolvePath(fileName);
			(new java.io.File(fileName)).delete();
		}
		
		// 更新生成状态。
		this.setIsGenerated(false);
		this.updateGenerateStatus();
	}
	
	// === 子类 =========================================================================
	
	/**
	 * 表示一个文章、图片、软件链接子项。
	 * 
	 * @author liujunxing
	 *
	 */
	public class UrlEntry {
		/** 此链接项的名字。 */
		private String name;
		
		/** 此链接项的链接地址。 */
		private String url;
		
		/**
		 * 构造函数。
		 *
		 */
		public UrlEntry() {
			
		}
		
		/**
		 * 使用指定的名字和链接地址构造。
		 * @param name
		 * @param url
		 */
		public UrlEntry(String name, String url) {
			this.name = name;
			this.url = url;
		}
		
		/*
		 * (non-Javadoc)
		 * @see java.lang.Object#toString()
		 */
		@Override public String toString() {
			return this.name + "|" + this.url;
		}
		
		/** 此图片项的名字。 */
		public String getName() {
			return this.name;
		}
		
		/** 此图片项的名字。 */
		public void setName(String name) {
			this.name = name;
		}
		
		/** 此图片项的链接地址。 */
		public String getUrl() {
			return this.url;
		}
		
		/** 此图片项的链接地址。 */
		public void setUrl(String url) {
			this.url = url;
		}
	}
}
