package com.chinaedustar.publish.model;

import java.net.URISyntaxException;
import java.util.List;

import com.chinaedustar.publish.PublishContext;
import com.chinaedustar.publish.PublishException;
import com.chinaedustar.publish.PublishUtil;
import com.chinaedustar.publish.itfc.ShowPathSupport;

/**
 * 频道实体对象实现类。
 * 
 * @author liujunxing
 */
@SuppressWarnings({ "rawtypes" })
public class Channel extends AbstractChannelBase implements UrlResolver, ShowPathSupport {
	
	// === Channel 的持久和内存状态 =============================================
	
	/** 正常状态 = 0。 */
	public static final ChannelStatus CHANNEL_STATUS_NORMAL = new ChannelStatus(0, "正常");
	
	/** 此频道被禁用 = 1。 */
	public static final ChannelStatus CHANNEL_STATUS_DISABLED = new ChannelStatus(1, "禁用");
	
	/** 此频道正在被删除 = 2。 */
	public static final ChannelStatus CHANNEL_STATUS_DELETING = new ChannelStatus(2, "删除");
	
	/** 内存状态 - 已经加载到了内存，并初始化完毕。 */
	public static final ChannelStatus CHANNEL_STATUS_MEM_OK = new ChannelStatus(-1, "加载成功");
	
	/** 内存状态 - 此频道正在向内存中加载。 */
	public static final ChannelStatus CHANNEL_STATUS_MEM_INITING = new ChannelStatus(-2, "正在加载频道");
	
	/** 内存状态 - 此频道试图加载到内存中的时候发生异常。 */
	public static final ChannelStatus CHANNEL_STATUS_MEM_EXCEPTION = new ChannelStatus(-3, "加载频道异常");
	
	/** 此频道的状态非法。 */
	public static final ChannelStatus CHANNEL_STATUS_INVALID = new ChannelStatus(-4, "非法状态");
	
	// === member ====
	
	/** 项目名字。 */
	private String itemName;
	
	/** 项目单位。 */
	private String itemUnit;
	
	/** 打开方式。 0：在原窗口打开；1：在新窗口打开 **/
	private int openType;

	/** 频道的简要提示。不支持html,标签。 */
	private String tips;
	
	/** 频道的详细说明。可以支持html,标签。 */
	private String description;
	
	/** 频道存放的物理路径。此路径被严格限制为只能用英文名字。 */
	private String channelDir;

	/** 频道的url地址。如果是外部频道准备链接到别的地方时候使用；或者把此频道变更为一个子网站的时候使用。 */
	private String channelUrl;

	/** 频道图片地址。 */
	private String channelPicUrl;
	
	/** 频道使用的支持功能模块标识。 */
	private int moduleId;
	
	/** 频道中所对应的栏目集合的根栏目标识。 */
	// TODO: 研究一下，可否不要这个字段，本质上是不要根栏目了。
	private int rootColumnId;
	
	/** 频道的排列顺序。 */
	private int channelOrder;	
	
	/** 频道状态：0 – 正常；1 – 暂时关闭，禁用；2-正在删除。也许以后还有其它状态，如调试状态。 */
	private int status;

	/** 频道类型：系统频道 = 0 */
	public static final int CHANNEL_TYPE_SYSTEM = 0;
	/** 频道类型：内部频道 = 1 */
	public static final int CHANNEL_TYPE_INTERNAL = 1;
	/** 频道类型：外部频道 = 2 */
	public static final int CHANNEL_TYPE_EXTERNAL = 2;
	
	/** 频道类型：0 – 系统频道；1 – 内部频道；2 – 外部频道。 */
	private int channelType;
	
	/**  点记率为N是属于热门状态。 */
	private int hitsOfHot;

	/** 权限字段: =0 表示继承父对象权限；=1 表示该频道设置有自定义的权限。 */
	private int privilege_flag;

	/** 计费字段: =0 表示继承父对象的计费设置；=1 表示有自定义收费设置。 */
	private int charge_flag;
	
	/**是否允许上传*/
	private boolean enableUploadFile;
	
	/**上传文件的保存目录*/
    private String uploadDir;
    
    /**允许上传的最大文件大小*/
    private int maxFileSize;
    
    /**允许上传的文件类型*/
    private String upFileType;
    
    
    // ********** 生成属性 ****************
    
    /** 生成HTML方式, = 0 表示不生成 */
    public static final int CHANNEL_CREATE_HTML_NONE = 0;
    /** 生成HTML方式, = 1 全部生成 */
    public static final int CHANNEL_CREATE_HTML_ALL = 1;
    /** 生成HTML方式, = 2 首页和内容页为HTML，栏目和专题页为JSP */
    public static final int CHANNEL_CREATE_HTML_SIMPLE = 2;
    /** 生成HTML方式, = 3 首页、内容页、栏目和专题的首页为HTML，其他页为JSP(推荐) */
    public static final int CHANNEL_CREATE_HTML_SMART = 3;
    
    /** 生成HTML方式, = 0 表示不生成, = 1 全部生成, = 2 首页和内容页为HTML，栏目和专题页为JSP, 
     * = 3 首页、内容页、栏目和专题的首页为HTML，其他页为JSP(推荐) */
    private int useCreateHTML;
    
    /** 栏目、专题列表更新页数：添加内容后自动更新的栏目及专题列表页数。  */
    private int updatePages;
    
    /** 自动生成HTML时的生成方式 */
    private int autoCreateType;
    
    /** 
     * 栏目列表文件的存放位置 
     */
    private int listFileType;
    
    /** 目录结构方式 */
    private int structureType;
    
    /** 内容页文件的命名方式 */
    private int fileNameType;
    
    /** 频道首页的扩展名 */
    private int fileExtIndex;
    
    /** 栏目、专题页的扩展名  */
    private int fileExtColumn;
    
    /** 内容页的扩展名 */
    private int fileExtItem;
    
	/** 自定义字段：自定义字段的数量。 */
	private int custom_flag;	

	/** 是否显示在导航条上面。 */
	private boolean showInNav = true;
	
	// TODO: 统计信息
			
	/** 频道的内存状态。 */
	private transient ChannelStatus channel_mem_status;
	
	/** 这个频道使用的模块。 */
	private transient ChannelModule channel_module;
	
	// ========================================================================
	
	/**
	 * 缺省构造函数。
	 *
	 */
	public Channel() {
		
	}
	
	/**
	 * 复制构造函数。
	 * @param src
	 */
	public Channel(Channel src) {
		this.copy(src);
	}
	
	/**
	 * 获得这个频道的状态。
	 * @return
	 */
	public ChannelStatus getChannelStatus() {
		return this.channel_mem_status;
	}

	/**
	 * 获得这个频道使用的模块。
	 * @return
	 */
	public ChannelModule getChannelModule() {
		return this.channel_module;
	}
	
	/**
	 * 获得此频道使用的模块的名字。
	 * @return
	 */
	public String getModuleName() {
		if (this.channel_module == null) return null;
		return this.channel_module.getModuleTitle();
	}
	
	/**
	 * 得到管理的项目类型。
	 * @return
	 */
	public String getItemClass() {
		if (this.channel_module == null) return null;
		return this.channel_module.getItemClass();
	}
	
	/**
	 * 复制。
	 * @param src
	 */
	void copy(Channel src) {
		if (src == null) throw new IllegalArgumentException("src == null"); 
		if (this == src) return;
		synchronized (this) {
			synchronized (src) {
				super.copy(src);
				this.itemName = src.itemName;
				this.itemUnit = src.itemUnit;
				this.openType = src.openType;
				this.tips = src.tips;
				this.description = src.description;
				this.channelDir = src.channelDir;
				this.channelUrl = src.channelUrl;
				this.channelPicUrl = src.channelPicUrl;
				this.moduleId = src.moduleId;
				this.rootColumnId = src.rootColumnId;
				this.channelOrder = src.channelOrder;	
				this.status = src.status;
				this.channelType = src.channelType;
				this.privilege_flag = src.privilege_flag;
				this.charge_flag = src.charge_flag;
				this.hitsOfHot = src.hitsOfHot;
				this.custom_flag = src.custom_flag;				
				this.enableUploadFile = src.enableUploadFile;
				this.uploadDir = src.uploadDir;
				this.maxFileSize = src.maxFileSize;
				this.upFileType = src.upFileType;
				this.useCreateHTML = src.useCreateHTML;
				this.updatePages = src.updatePages;
				this.autoCreateType = src.autoCreateType;
				this.listFileType = src.listFileType;
				this.structureType = src.structureType;
				this.fileNameType = src.fileNameType;
				this.fileExtIndex = src.fileExtIndex;
				this.fileExtColumn = src.fileExtColumn;
				this.fileExtItem = src.fileExtItem;
			}
		}
	}

	// === PublishModelObject impl ============================================
	
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jpub.model.AbstractPublishModelBase#_init(cn.edustar.jpub.PublishContext, cn.edustar.jpub.model.PublishModelObject)
	 */
	@Override public void _init(PublishContext pub_ctxt, PublishModelObject owner_obj) {
		super._init(pub_ctxt, owner_obj);
		
		// 如果这个频道的模块标识大于0，则表示其并非外部频道。
		if (this.moduleId > 0) {
			Module module = pub_ctxt.getSite().getModules().getModule(this.moduleId);
			if (module != null)
				this.channel_module = (ChannelModule)module.getPublishModule();
		} else {
			this.channel_module = null;
		}
		this.channel_mem_status = CHANNEL_STATUS_MEM_OK;
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jpub.model.AbstractPublishModelBase#_destroy()
	 */
	@Override public void _destroy() {
		super._destroy();
	}
	
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jpub.model.AbstractChannelBase#_getContainerId()
	 */
	@Override public int getChannelId() {
		return this.getId();
	}

	// === collection ========================================================= 
	
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jpub.model.AbstractModelBase#getParent()
	 */
	@Override public Site getParent() {
		// channel 的父对象一定是 site.
		if (this.owner_obj instanceof Site) 
			return (Site)this.owner_obj;
		if (this.owner_obj instanceof ChannelCollection) 
			return ((ChannelCollection)owner_obj).getSite(); 
		return pub_ctxt.getSite();
	}
	
	/**
	 * 获得站点对象。
	 * @return
	 */
	public Site getSite() {
		return this.getParent();
	}
	
	/**
	 * 得到频道中的栏目集合对象。
	 * @return
	 */
	public ColumnTree getColumnTree() {
		// 从线程中找现有缓存的。
		ThreadCurrentMap tcm = ThreadCurrentMap.default_current();
		String key_name = "channel.column_tree." + this.getObjectUuid();
		ColumnTree column_tree = (ColumnTree)tcm.getNamedObject(key_name);
		if (column_tree != null) return column_tree;
		
		// 如果没有则新建一个。这种机制能够保证在一个线程中运行的时候，column_tree 被缓存重用。
		column_tree = new ColumnTree();
		column_tree._init(_getPublishContext(), this);
		tcm.putNamedObject(key_name, column_tree);
		
		return column_tree;
	}

	/**
	 * 得到频道下的上传文件的集合。
	 * @return
	 */
	public UpFileCollection getUpFileCollection() {
		UpFileCollection upfile_coll = new UpFileCollection();
		upfile_coll._init(_getPublishContext(), this);
		return upfile_coll;
	}
	
	/**
	 * 得到评论集合对象。
	 * @return
	 */
	public CommentCollection getCommentCollection() {
		CommentCollection comm_coll = new CommentCollection();
		comm_coll._init(pub_ctxt, this);
		return comm_coll;
	}
	
	
	/**
	 * 获得此频道下所有文章数（含审核和未审核的）
	 * @return
	 */
	public long getTotalItemCount() {
		return getItemCount(2);
	}
	
	/**
	 * 获得此频道下未审核的文章数。
	 * @return
	 */
	public long getUnapprItemCount() {
		return getItemCount(0);
	}
	
	/**
	 * 得到指定类型的内容项的总数（已删除的不计算）。
	 * @param status 内容项的状态，-2:退稿，-1：草稿，0：未审核，1：审核通过，2:审核+未审核内容项的总数，3：审核+未审核+草稿内容项的总数，4：全部内容项的总数。
	 * @return 记录总数。
	 */
	public long getItemCount(int status) {
		String hql = "select count(id) from Item where channelId=:channelId and deleted=:deleted";
		if (status == 2) {
			hql += " and (status=1 or status=0)";
		} else if (status == 3) {
			hql += " and (status=1 or status=0 or stauts=-1)";
		} else if (status == 4) {
			// 全部的，不过滤。
		} else {
			hql += " and status=" + status;
		}
		List list = pub_ctxt.getDao().queryByNamedParam(hql, new String[] {"channelId", "deleted"}, new Object[] {getId(), false});
		if (list == null || list.size() == 0) return 0;
		return PublishUtil.safeGetLongVal(list.get(0));
	}
	
	// === getter =============================================================
	
	/**
	 * 获得此频道中项目的名字。 例如：新闻，软件。
	 * @return
	 */
	public String getItemName() {
		return this.itemName;
	}
	
	/**
	 * 获得项目的单位用字。如：篇，个。
	 * @return
	 */
	public String getItemUnit() {
		return this.itemUnit;
	}
	
	/**
	 * 打开方式。 
	 * @return 0：在原窗口打开；1：在新窗口打开
	 */
	public int getOpenType(){
		return this.openType;
	}
	
	/** 频道的简要提示。不支持html,标签。 */
	public String getTips() {
		return this.tips;
	}
	
	/** 频道的详细说明。可以支持html,标签。 */
	public String getDescription() {
		return this.description;
	}
	
	/** 频道存放的物理路径。此路径被严格限制为只能用英文名字。 */
	public String getChannelDir() {
		return this.channelDir;
	}

	/** 频道的url地址。如果是外部频道准备链接到别的地方时候使用；或者把此频道变更为一个子网站的时候使用。 */
	public String getChannelUrl() {
		return this.channelUrl;
	}
	
	public String getChannelPicUrl() {
		return this.channelPicUrl;
	}

	/** 频道使用的支持功能模块标识。 */
	public int getModuleId() {
		return this.moduleId;
	}
	
	/** 频道中所对应的栏目集合的根栏目标识。 */
	public int getRootColumnId() {
		// TODO: 从 ColumnTree 里面获得这个数据更好。
		return this.rootColumnId;
	}
	
	/** 频道的排列顺序。 */
	public int getChannelOrder() {
		return this.channelOrder;
	}
	
	/**
	 * 获得频道状态。0：正常；1：禁用；2：正在删除
	 * @return
	 */
	public int getStatus() {
		return this.status;
	}
	
	/** 频道类型：0 – 系统频道；1 – 内部频道；2 – 外部频道。 */
	public int getChannelType() {
		return this.channelType;
	}
	
	/**
	 * 点记率为N是属于热门状态。
	 * @return
	 */
	public int getHitsOfHot() {
		return hitsOfHot;
	}
	
	/** 权限字段: =0 表示继承父对象权限；=1 表示该频道设置有自定义的权限。 */
	public int getPrivilegeFlag() {
		return this.privilege_flag;
	}

	/** 计费字段: =0 表示继承父对象的计费设置；=1 表示有自定义收费设置。 */
	public int getChargeFlag() {
		return this.charge_flag;
	}
		
	// TODO: 统计信息
			
	/** 自定义字段：自定义字段的数量。 */
	public int getCustomFlag() {
		return this.custom_flag;
	}
	
	 /**是否允许上传*/
    public boolean getEnableUploadFile() {
        return this.enableUploadFile;
    }

    /**上传文件的保存目录*/
    public String getUploadDir() {
        return this.uploadDir;
    }
    
    /**允许上传的最大文件大小*/
    public int getMaxFileSize() {
        return this.maxFileSize;
    }
    
    /**允许上传的文件类型*/
    public String getUpFileType() {
        return this.upFileType;
    }
    
    /**
     * 获取生成HTML方式:
     * <li>0 - 不生成；
     * <li>1 - 全部生成；
     * <li>2 - 首页和内容页为HTML，栏目和专题页为JSP；
     * <li>3 - 首页、内容页、栏目和专题的首页为HTML，其他页为JSP。
     * @return 生成HTML方式
     */
    public int getUseCreateHTML() {
    	return this.useCreateHTML;
    }
    
    /**
     * 获取栏目、专题列表更新页数：添加内容后自动更新的栏目及专题列表页数。
     * @return
     */
    public int getUpdatePages() {
    	return this.updatePages;
    }
    
    /**
     * 获取自动生成HTML时的生成方式。
     * 0：不自动生成，由管理员手工生成相关页面；
     * 1：自动生成全部所需页面，当“生成HTML方式”设置为“全部生成”时，将生成所有页面；当“生成HTML方式”设置为后两种时，会根据设置的选项生成有关页面；
     * 2：自动生成部分所需页面，仅当“生成HTML方式”设置为“全部生成”时方有效。此方式只生成首页、内容页、栏目和专题的首页，其他页面由管理员手工生成。
     * @return
     */
    public int getAutoCreateType() {
    	return this.autoCreateType;
    }
    
    /**
     * 获取栏目、专题列表文件的存放位置。
     * <li>0 - 列表文件分目录保存在所属栏目的文件夹中<br>
     *    如 Article/ASP/JiChu/index.html（栏目首页）<br>
     *       Article/ASP/JiChu/List_2.html（第二页）
     * <li>1 - 列表文件统一保存在指定的“List”文件夹中；<br>
     *    如 Article/List/List_236.html（栏目首页）<br>
     *       Article/List/List_236_2.html（第二页）
     * <li>2 - 列表文件统一保存在频道文件夹中。<br>
     *    如 Article/List_236.html（栏目首页）<br>
     *       Article/List_236_2.html（第二页）
     * @return
     */
    public int getListFileType() {
    	return this.listFileType;
    }
    
    /**
     * 目录结构方式。
     * <li>0：频道/大类/小类/月份/文件（栏目分级，再按月份保存），例：Article/ASP/JiChu/200408/1368.html；
     * <li>1：频道/大类/小类/日期/文件（栏目分级，再按日期分，每天一个目录），例：Article/ASP/JiChu/2004-08-25/1368.html；
     * <li>2：频道/大类/小类/文件（栏目分级，不再按月份），例：Article/ASP/JiChu/1368.html；
     * <li>3：频道/栏目/月份/文件（栏目平级，再按月份保存），例：Article/JiChu/200408/1368.html；
     * ...
     * @return
     * 
     * <li>0 - 频道/大类/小类/月份/文件（缺省：栏目分级，再按月份保存） 
     *   例：Article/ASP/JiChu/200408/1368.html
	 * <li>1 - 频道/大类/小类/日期/文件（栏目分级，再按日期分，每天一个目录）
	 *   例：Article/ASP/JiChu/2004-08-25/1368.html
	 * <li>2 - 频道/大类/小类/文件（栏目分级，不再按月份）
     *   例：Article/ASP/JiChu/1368.html
     * <li>3 - 频道/栏目/月份/文件（栏目平级，再按月份保存）
     *   例：Article/JiChu/200408/1368.html
     * <li>4 - 频道/栏目/日期/文件（栏目平级，再按日期分，每天一个目录）
     *   例：Article/JiChu/2004-08-25/1368.html
     * <li>5 - 频道/栏目/文件（栏目平级，不再按月份）
     *   例：Article/JiChu/1368.html
     * <li>6 - 频道/文件（直接放在频道目录中）
     *   例：Article/1368.html
     * <li>7 - 频道/HTML/文件（直接放在指定的“HTML”文件夹中）
     *   例：Article/HTML/1368.html
     * <li>8 - 频道/年份/文件（直接按年份保存，每年一个目录）
     *   例：Article/2004/1368.html
     * <li>9 - 频道/月份/文件（直接按月份保存，每月一个目录）
     *   例：Article/200408/1368.html
     * <li>10 - 频道/日期/文件（直接按日期保存，每天一个目录）
     *   例：Article/2004-08-25/1368.html
     * <li>11 - 频道/年份/月份/文件（先按年份，再按月份保存，每月一个目录）
     *   例：Article/2004/200408/1368.html
     * <li>12 - 频道/年份/日期/文件（先按年份，再按日期分，每天一个目录）
     *   例：Article/2004/2004-08-25/1368.html
     * <li>13 - 频道/月份/日期/文件（先按月份，再按日期分，每天一个目录）
     *   例：Article/200408/2004-08-25/1368.html
     * <li>14 - 频道/年份/月份/日期/文件（先按年份，再按日期分，每天一个目录）
     *   例：Article/2004/200408/2004-08-25/1368.html
     */
    public int getStructureType() {
    	return this.structureType;
    }
    
    /**
     * 获取内容页文件的命名方式。
     * <li>0：文章ID.html    例：1358.html；
     * <li>1：更新时间.html    例：20040828112308.html；
     * <li>2：频道英文名_文章ID.html    例：Article_1358.html；
     * <li>3：频道英文名_更新时间.html    例：Article_20040828112308.html；
     * <li>4：更新时间_ID.html    例：20040828112308_1358.html；
     * <li>5：频道英文名_更新时间_ID.html    例：Article_20040828112308_1358.html。
     * @return
     */
    public int getFileNameType() {
    	return this.fileNameType;
    }
    
    /**
     * 获取频道首页的扩展名。 0：html；1：htm；2：shtml；3：shtm；4：jsp。
     * @return
     */
    public int getFileExtIndex() {
    	return this.fileExtIndex;
    }
    
    /**
     * 获取栏目、专题页的扩展名。0：html；1：htm；2：shtml；3：shtm；4：jsp。
     * @return
     */
    public int getFileExtList() {
    	return this.fileExtColumn;
    }
    
    /**
     * 获取内容页的扩展名。0：html；1：htm；2：shtml；3：shtm；4：jsp。
     * @return
     */
    public int getFileExtItem() {
    	return this.fileExtItem;
    }

    /** 是否显示在导航条上面。 */
    public boolean getShowInNav() {
    	return this.showInNav;
    }
    
    /**
     * 返回此频道主页是否需要生成。
     * @return true 表示需要生成, false 表示对象没有设置为静态化支持所以不需要生成。
     */
    public boolean getNeedGenerate() {
    	if (this.status != Channel.CHANNEL_STATUS_NORMAL.getCode() ||
    			this.channelType == CHANNEL_TYPE_EXTERNAL) return false;
    	if (this.useCreateHTML == CHANNEL_CREATE_HTML_NONE)
    		return false;
    	return this.fileExtIndex != Site.PAGE_URL_TYPE_JSP;
    }
    
    /**
     * 返回此频道是否需要生成(静态化)其栏目、专题及其页面。
     * @return
     */
    public boolean getNeedGenerateColumn() {
    	// 频道状态不正常，或者是外部频道则不生成。
    	if (this.status != Channel.CHANNEL_STATUS_NORMAL.getCode() ||
    			this.channelType == CHANNEL_TYPE_EXTERNAL) return false;
    	// 设置不生成栏目则不生成
    	if (this.useCreateHTML == CHANNEL_CREATE_HTML_NONE)
    		return false;
    	if (this.useCreateHTML == Channel.CHANNEL_CREATE_HTML_SIMPLE)
    		return false;
    	// 栏目页扩展名为 jsp 则不生成。
    	if (this.fileExtColumn == Site.PAGE_URL_TYPE_JSP) return false;
    	
    	return true;
    }
    
    /**
     * 返回此频道是否需要生成其专题页面。
     * @return
     */
    public boolean getNeedGenerateSpecial() {
    	// 频道状态不正常，或者是外部频道则不生成。
    	if (this.status != Channel.CHANNEL_STATUS_NORMAL.getCode() ||
    			this.channelType == CHANNEL_TYPE_EXTERNAL) return false;
    	// 设置不生成专题则不生成
    	if (this.useCreateHTML == CHANNEL_CREATE_HTML_NONE)
    		return false;
    	if (this.useCreateHTML == Channel.CHANNEL_CREATE_HTML_SIMPLE)
    		return false;
    	// 专题页扩展名为 jsp 则不生成。
    	if (this.fileExtColumn == Site.PAGE_URL_TYPE_JSP) return false;
    	
    	return true;
    }

    /**
     * 返回此频道是否需要生成其项目页面。
     * @return
     */
    public boolean getNeedGenerateItem() {
    	// 频道状态不正常，或者是外部频道则不生成。
    	if (this.status != Channel.CHANNEL_STATUS_NORMAL.getCode() ||
    			this.channelType == CHANNEL_TYPE_EXTERNAL) return false;
    	// 如果设置为不生成则不生成。 
    	if (this.useCreateHTML == CHANNEL_CREATE_HTML_NONE)
    		return false;
    	if (this.fileExtItem == Site.PAGE_URL_TYPE_JSP) 
    		return false;
    	
    	return true;
    }
    
    /**
     * 返回此频道是否可管理，可管理意味着状态正常，不是外部频道。
     * @return
     */
    public boolean getManagable() {
    	if (this.status != 0) return false;
    	if (this.channelType == CHANNEL_TYPE_EXTERNAL) return false;
    	
    	return true;
    }
    
	// === setter =============================================================
	
    /** 频道中项目的名字。 例如：新闻，软件。 */
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	
	/** 项目的单位用字。如：篇，个。 */
	public void setItemUnit(String itemUnit) {
		this.itemUnit = itemUnit;
	}
	
	/**打开方式 0：在原窗口打开；1：在新窗口打开 */
	public void setOpenType(int openType) {
		this.openType = openType;
	}
	
	/** 频道的简要提示。不支持html,标签。 */
	public void setTips(String tips) {
		this.tips = tips;
	}
	
	/** 频道的详细说明。可以支持html,标签。 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	/** 频道存放的物理路径。此路径被严格限制为只能用英文名字。 */
	public void setChannelDir(String channelDir) {
		this.channelDir = channelDir;
	}

	/** 频道的url地址。如果是外部频道准备链接到别的地方时候使用；或者把此频道变更为一个子网站的时候使用。 */
	
	public void setChannelUrl(String channelUrl) {
		this.channelUrl = channelUrl;
	}
	
	/** 频道图片地址。 */
	public void setChannelPicUrl(String channelPicUrl) {
		this.channelPicUrl = channelPicUrl;
	}

	/** 频道使用的支持功能模块标识。 */

	public void setModuleId(int moduleId) {
		this.moduleId = moduleId;
	}
	
	/** 频道中所对应的栏目集合的根栏目标识。 */
	public void setRootColumnId(int rootColumnId) {
		this.rootColumnId = rootColumnId;
	}
	
	/** 频道的排列顺序。 */
	public void setChannelOrder(int channelOrder) {
		this.channelOrder = channelOrder;
	}
	
	/** 频道状态：0 – 正常；1 – 暂时关闭，禁用；2-正在删除。也许以后还有其它状态，如调试状态。 */
	public void setStatus(int status) {
		this.status = status;
	}

	/** 频道类型：0 – 系统频道；1 – 内部频道；2 – 外部频道。 */

	public void setChannelType(int channelType) {
		this.channelType = channelType;
	}


	/**
	 * 点记率为N是属于热门状态。
	 * @param hitsOfHot
	 */
	public void setHitsOfHot(int hitsOfHot) {
		this.hitsOfHot = hitsOfHot;
	}

	/** 权限字段: =0 表示继承父对象权限；=1 表示该频道设置有自定义的权限。 */
	
	public void setPrivilegeFlag(int privilege_flag) {
		this.privilege_flag = privilege_flag;
	}

	/** 计费字段: =0 表示继承父对象的计费设置；=1 表示有自定义收费设置。 */
	
	public void setChargeFlag(int charge_flag) {
		this.charge_flag = charge_flag;
	}
	
	// TODO: 统计信息
			
	/** 自定义字段：自定义字段的数量。 */
	public void setCustomFlag(int custom_flag) {
		this.custom_flag = custom_flag;
	}

	/**是否允许上传*/
    
	public void setEnableUploadFile(boolean enableUploadFile) {
        this.enableUploadFile = enableUploadFile;
    }
    
	/**上传文件的保存目录*/
    public void setUploadDir(String uploadDir) {
        this.uploadDir = uploadDir;
    }
    
    /**允许上传的最大文件大小*/
    public void setMaxFileSize(int maxFileSize) {
        this.maxFileSize = maxFileSize;
    }
    
    /**允许上传的文件类型*/
    public void setUpFileType(String upFileType) {
        this.upFileType = upFileType;
    }
    
    /**
     * 设置生成HTML方式
     * 0：不生成；
     * 1：全部生成；
     * 2：首页和内容页为HTML，栏目和专题页为JSP；
     * ；3：首页、内容页、栏目和专题的首页为HTML，其他页为JSP。
     * @return
     */
    public void setUseCreateHTML(int useCreateHTML) {
    	this.useCreateHTML = useCreateHTML;
    }
    
    /**
     * 设置栏目、专题列表更新页数：添加内容后自动更新的栏目及专题列表页数。
     * @return
     */
    public void setUpdatePages(int updatePages) {
    	this.updatePages = updatePages;
    }
    
    /**
     * 设置自动生成HTML时的生成方式。
     * 0：不自动生成，由管理员手工生成相关页面；
     * 1：自动生成全部所需页面，当“生成HTML方式”设置为“全部生成”时，将生成所有页面；当“生成HTML方式”设置为后两种时，会根据设置的选项生成有关页面；
     * 2：自动生成部分所需页面，仅当“生成HTML方式”设置为“全部生成”时方有效。此方式只生成首页、内容页、栏目和专题的首页，其他页面由管理员手工生成。
     * @return
     */
    public void setAutoCreateType(int autoCreateType) {
    	this.autoCreateType = autoCreateType;
    }
    
    /**
     * 设置栏目列表文件的存放位置。
     * 0：列表文件分目录保存在所属栏目的文件夹中；
     * 1：列表文件统一保存在指定的“List”文件夹中；
     * 2：列表文件统一保存在频道文件夹中。
     * @return
     */
    public void setListFileType(int listFileType) {
    	this.listFileType = listFileType;
    }
    
    /**
     * 目录结构方式。
     * 参见 getStructureType()
     * @return
     */
    public void setStructureType(int structureType) {
    	this.structureType = structureType;
    }
    
    /**
     * 设置内容页文件的命名方式。
     * 0：文章ID.html    例：1358.html；
     * 1：更新时间.html    例：20040828112308.html；
     * 2：频道英文名_文章ID.html    例：Article_1358.html；
     * 3：频道英文名_更新时间.html    例：Article_20040828112308.html；
     * 4：更新时间_ID.html    例：20040828112308_1358.html；
     * 5：频道英文名_更新时间_ID.html    例：Article_20040828112308_1358.html。
     * @return
     */
    public void setFileNameType(int fileNameType) {
    	this.fileNameType = fileNameType;
    }
    
    /**
     * 设置频道首页的扩展名。 0：html；1：htm；2：shtml；3：shtm；4：jsp。
     * @return
     */
    public void setFileExtIndex(int fileExtIndex) {
    	this.fileExtIndex = fileExtIndex;
    }
    
    /**
     *设置栏目、专题页的扩展名。0：html；1：htm；2：shtml；3：shtm；4：jsp。
     * @return
     */
    public void setFileExtList(int fileExtList) {
    	this.fileExtColumn = fileExtList;
    }
    
    /**
     * 设置内容页的扩展名。0：html；1：htm；2：shtml；3：shtm；4：jsp。
     * @return
     */
    public void setFileExtItem(int fileExtItem) {
    	this.fileExtItem = fileExtItem;
    }

    /** 是否显示在导航条上面。 */
    public void setShowInNav(boolean v) {
    	this.showInNav = v;
    }
    
    // === ModelObject ==============================================================

    /*
     * (non-Javadoc)
     * @see cn.edustar.jpub.model.AbstractModelBase#getUrlResolver()
     */
	@Override public UrlResolver getUrlResolver() {
		// Channel 使用 Site 做为其 Url 解析器。
		return this.getSite();
	}

    /*
     * (non-Javadoc)
     * @see cn.edustar.jpub.model.ModelObject#resolvePath(java.lang.String)
     */
	public String resolvePath(String child_obj_path) {
		String root_dir = pub_ctxt.getRootDir();
		java.io.File f = new java.io.File(root_dir + "\\" + this.channelDir + "\\" + child_obj_path);
		return f.getAbsolutePath();
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jpub.model.ModelObject#resolveUrl(java.lang.String)
	 * 此函数解析基于频道的对象的地址。
	 */
	public String resolveUrl(String rel_url) {
		if (rel_url == null || rel_url.length() == 0) return null;
		// 如果地址已经是绝对地址了，则不再处理，节省一些时间。
		if (rel_url.startsWith("http://") || rel_url.startsWith("https://"))
			return rel_url;
		
		if (rel_url.startsWith("/"))
			return getParent().resolveUrl(rel_url);
		
		// 获得频道的基准地址。
		String base_url = this.channelType == CHANNEL_TYPE_EXTERNAL ?
			this.channelUrl :
			getParent().resolveUrl(this.channelDir + "/");		
		
		try {
			java.net.URI base_uri = new java.net.URI(base_url);
			return String.valueOf(base_uri.resolve(rel_url));
		} catch (URISyntaxException ex) {
			return base_url + rel_url;
		} catch (IllegalArgumentException ex) {
			return base_url + rel_url;
		}
    }
    
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jpub.model.UrlResolver#relativizeUrl(java.lang.String)
	 */
	public String relativizeUrl(String abs_url) {
		if (abs_url == null || abs_url.length() == 0) return null;
		String base_url = getParent().resolveUrl(this.channelDir + "/");
		
		try {
			java.net.URI base_uri = new java.net.URI(base_url);
			java.net.URI abs_uri = new java.net.URI(abs_url);
			return String.valueOf(base_uri.relativize(abs_uri));
		} catch (URISyntaxException ex) {
			// 如果链接语法不对，则返回原来地址。
			return abs_url;
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jpub.model.AbstractPageModelBase#calcPageUrl()
	 */
	@Override public String calcPageUrl() {
		// 如果是外部频道，则直接返回外部地址。
		if (channelType == CHANNEL_TYPE_EXTERNAL) {
			return this.channelUrl;
		}
		
		// 如果使用动态页面则返回动态页面地址。
		if (this.useCreateHTML == CHANNEL_CREATE_HTML_NONE)
			return pub_ctxt.getSite().resolveUrl(this.channelDir + "/index.jsp");
		
		// 否则使用静态页面地址。
		return pub_ctxt.getSite().resolveUrl(getStaticPageUrl());
	}
	
	/**
	 * 得到新的静态化地址。
	 * @return
	 */
	protected String getNewStaticPageUrl(PublishContext pub_ctxt) {
		if (this.getNeedGenerate() == false) return "";
		return channelDir + "/index" + getFileExtName(fileExtIndex);
	}

	// === ShowPathSupport 接口实现 ====================================================

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jpub.itfc.ShowPathSupport#isShowInPath()
	 */
	public boolean isShowInPath() {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jpub.itfc.ShowPathSupport#getPathTitle()
	 */
	public String getPathTitle() {
		return this.getName();
	}
	
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jpub.itfc.ShowPathSupport#getPathTarget()
	 */
	public String getPathTarget() {
		return this.getOpenType() == 0 ? "" : "_blank";
	}
	
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jpub.itfc.ShowPathSupport#getPathPageUrl()
	 */
	public String getPathPageUrl() {
		return this.getPageUrl();
	}

	// === 业务方法 ==================================================================
	
	/** 缓存起来的根栏目标识。 */
	private Column cached_root_column = null;
	
	/**
	 * 获得或创建此频道下的根栏目对象。此方法不希望从模板调用。
	 * @return 根栏目对象，如果不存在，则创建一个。注意：根栏目对象仅用于内部，其不进行模型初始化。
	 */
	public Column _getCreateRootColumn() {
		// 如果已经缓存，则直接返回。
		if (cached_root_column != null) return cached_root_column;
		
		Column root_column = null;
		// 从数据库中加载, parentId = 0 表示是根栏目。
		String hql = "FROM Column WHERE channelId = " + this.getId() + " AND parentId = 0 ORDER BY id ASC ";
		List result = pub_ctxt.getDao().list(hql);
		if (result == null || result.size() == 0) {
			// 数据库里面没有，我们现在创建一个。
			root_column = this._createRootColumn();
		} else {
			root_column = (Column)result.get(0);
		}
		
		// 模型化。
		root_column._init(pub_ctxt, this);
		if (this.rootColumnId != root_column.getId()) {
			// 更新当前数据字段 rootColumnId
			_updateRootColumnId(root_column.getId());
		}
		this.cached_root_column = root_column;
		return root_column;
	}

	// 更新当前频道的 rootColumnId 字段。
	private void _updateRootColumnId(int rootColumnId) {
		String hql = "UPDATE Channel SET rootColumnId = " + rootColumnId + " WHERE id = " + this.getId();
		pub_ctxt.getDao().bulkUpdate(hql);
		this.rootColumnId = rootColumnId;
	}
	
	/** 创建此频道的根栏目，调用前要确保此频道没有根栏目。*/
	private Column _createRootColumn() {
		// 创建根栏目。
		Column root_column = new Column();
		root_column.setChannelId(this.getId());
		root_column.setName(this.getName() + "根栏目");
		root_column.setParentPath("/");		// TODO: 这些数据从 TreeViewModel 获取更可靠一些。
		root_column.setOrderPath("./0001/");
		root_column.setColumnDir("");
		pub_ctxt.getDao().insert(root_column);

		return root_column;
	}

	/**
	 * 获得频道统计数据对象。
	 * @return
	 */
	public ChannelStatistics getStatisticsData() {
		ChannelStatistics stat = new ChannelStatistics(_getPublishContext(), this);
		return stat;
	}
	

	/**
	 * 获得此频道是否是一个可管理的频道。可管理的频道指具有后台管理项，非外部频道，状态正常。
	 * @return
	 */
	public boolean getIsManagedChannel() {
		if (this.status != 0) return false;
		if (!(this.channelType == 0 || this.channelType == 1)) return false;
		
		return true;
	}
	

	// === ExtendPropertySupport 支持 =================================================
	
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jpub.ExtendPropertySupport#hintPropNum()
	 */
	@Override public int hintPropNum() {
		return this.custom_flag;
	}
	
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jpub.ExtendPropertySupport#propNumChanged(int)
	 */
	@Override public void propNumChanged(int num) {
		// 更新在内存中缓存的那个频道，而不是自己。
		Channel channel = pub_ctxt.getSite().getChannel(this.getId());
		if (channel != this && channel != null) {
			channel.propNumChanged(num);
		} else {
			super.propNumChanged(num);
			this.custom_flag = num;
			String hql = "UPDATE Channel SET customFlag = " + num + " WHERE id = " + this.getId();
			pub_ctxt.getDao().bulkUpdate(hql);
		}
	}

	// === Item 业务方法 =============================================================
	
	/**
	 * 加载指定标识的项目。
	 * @param itemId - 项目标识。
	 * @return 返回模型化了的对象，其可能是 Article, Photo, Soft 的一种。
	 */
	public Item loadItem(int itemId) {
		Item item = this.getChannelModule().loadItem(itemId);
		if (item != null) {
			item._init(pub_ctxt, this);
			if (item.getChannelId() == this.getId())
				item.setChannel(this);
		}
		return item;
	}
	
	/**
	 * 加载指定标识指定类型的项目。当前只支持类型 'article', 'photo', 'soft', 'item'
	 *   也许我们需要某种机制从 itemClass 影射到 Class.
	 * @param itemId - 项目标识。
	 * @param itemClass - 项目类型。
	 * @return
	 */
	public Item loadItem(int itemId, String itemClass) {
		Class<?> clazz = mapItemClass(itemClass);
		Item item = (Item)pub_ctxt.getDao().get(clazz, itemId);
		initItemModel(item);
		return item;
	}
	
	private Class<?> mapItemClass(String itemClass) {
		if ("article".equals(itemClass))
			return Article.class;
		else if ("photo".equals(itemClass))
			return Photo.class;
		else if ("soft".equals(itemClass))
			return Soft.class;
		else
			return Item.class;
	}
	
	/**
	 * 加载指定标识的文章对象。一般只在确定项目类型为 Article 的时候调用此方法。
	 * @param articleId
	 * @return
	 */
	public Article loadArticle(int articleId) {
		Article article = (Article)pub_ctxt.getDao().get(Article.class, articleId);
		initItemModel(article);
		return article;
	}
	
	/**
	 * 加载指定标识的图片对象。一般只在确定项目类型为 Photo 的时候再调用此方法。
	 * @param photoId - 图片标识。
	 */
	public Photo loadPhoto(int photoId) {
		Photo photo = (Photo)pub_ctxt.getDao().get(Photo.class, photoId);
		initItemModel(photo);
		return photo;
	}
	
	/**
	 * 加载指定标识的软件对象。一般只在确定项目类型为 Soft 的时候再调用此方法。
	 * @param softId
	 * @return
	 */
	public Soft loadSoft(int softId) {
		Soft soft = (Soft)pub_ctxt.getDao().get(Soft.class, softId);
		initItemModel(soft);
		return soft;
	}
	

	private void initItemModel(Item item) {
		if (item == null) return;
		item._init(pub_ctxt, this);
		if (item.getChannelId() == this.getId())
			item.setChannel(this);
	}
	
	/**
	 * 新建/更新一个 Article, Photo, Soft 项目扩展对象。
	 * @param item - 可以是 article, photo, soft
	 */
	public void saveItem(Item item) {
		// 1. 找到所在栏目。
		Column column = null;
		if (item.getColumnId() == 0)
			column = this._getCreateRootColumn();
		else
			column = this.getColumnTree().getColumn(item.getColumnId());
		if (column == null) throw new PublishException("指定的栏目不存在。");
		
		// 确保对象被模型化了。
		item._init(pub_ctxt, column);
		
		// 2. 保存对象。
		if (item.getId() == 0)
			column.insertItem(item);
		else
			column.updateItem(item);
	}
	
	/**
	 * 删除一个项目(文章、图片、软件等)。
	 * @param photo
	 */
	public boolean deleteItem(Item item) {
		// 本来需要一个栏目的，但有可能文章(图片)所在栏目不存在，而我们仍然要删除，所以
		//   用根栏目对象来删除。
		Column column = this.getColumnTree().getColumn(this.getRootColumnId());
		if (column == null) throw new PublishException("无法加载根栏目对象。");
		
		// 删除对象。
		return column.deleteItem(item);
	}
	

	/**
	 * 设置项目的置顶状态。
	 * @param item
	 * @param is_top
	 * @return
	 */
	public boolean setItemTop(Item item, boolean is_top) {
		Column column = this.getColumnTree().getColumn(this.getRootColumnId());
		if (column == null) throw new PublishException("无法加载根栏目对象。");

		return column.setItemTop(item, is_top);
	}
	
	/**
	 * 设置项目的推荐状态。
	 * @param item
	 * @param is_commend
	 * @return
	 */
	public boolean setItemCommend(Item item, boolean is_commend) {
		Column column = this.getColumnTree().getColumn(this.getRootColumnId());
		if (column == null) throw new PublishException("无法加载根栏目对象。");

		return column.setItemCommend(item, is_commend);
	}
	
	/**
	 * 设置项目的精华状态。
	 * @param item
	 * @param is_elite
	 * @return
	 */
	public boolean setItemElite(Item item, boolean is_elite) {
		Column column = this.getColumnTree().getColumn(this.getRootColumnId());
		if (column == null) throw new PublishException("无法加载根栏目对象。");

		return column.setItemElite(item, is_elite);
	}
	
	/**
	 * 退稿。
	 * @param item
	 */
	public boolean rejectItem(Item item) {
		Column column = this.getColumnTree().getColumn(this.getRootColumnId());
		if (column == null) throw new PublishException("无法加载根栏目对象。");

		return column.rejectItem(item);
	}
	
	/**
	 * 彻底销毁指定项目。
	 * @param item
	 * @return
	 */
	public boolean destroyItem(Item item) {
		Column column = this.getColumnTree().getColumn(this.getRootColumnId());
		if (column == null) throw new PublishException("无法加载根栏目对象。");

		return column.destroyItem(item);
	}
	
	/**
	 * 批量删除一组内容项，放入回收站中。
	 * @param item_ids
	 */
	public List<Integer> batchDeleteItems(List<Integer> item_ids) {
		Column column = this.getColumnTree().getColumn(this.getRootColumnId());
		if (column == null) throw new PublishException("无法加载根栏目对象。");

		return column.batchDeleteItems(item_ids);
	}
	
	/**
	 * 批量审核一组内容项。
	 * @param item_ids
	 * @param apprvoed
	 * @return
	 */
	public List<Integer> batchApproveItems(List<Integer> item_ids, boolean approved) {
		Column column = this.getColumnTree().getColumn(this.getRootColumnId());
		if (column == null) throw new PublishException("无法加载根栏目对象。");

		return column.batchApproveItems(item_ids, approved);
	}
	
	/**
	 * 批量彻底删除项目。
	 * @param item_ids
	 * @return
	 */
	public List<Integer> batchDestroyItems(List<Integer> item_ids) {
		Column column = this.getColumnTree().getColumn(this.getRootColumnId());
		if (column == null) throw new PublishException("无法加载根栏目对象。");

		return column.batchDestroyItems(item_ids);
	}
	
	/**
	 * 批量恢复一组项目。
	 * @param item_ids
	 * @return
	 */
	public List<Integer> batchRestoreItems(List<Integer> item_ids) {
		Column column = this.getColumnTree().getColumn(this.getRootColumnId());
		if (column == null) throw new PublishException("无法加载根栏目对象。");

		return column.batchRestoreItems(item_ids);
	}
	
	public Object _builtin_site(Object[] args) {
		return getSite();
	}
}
