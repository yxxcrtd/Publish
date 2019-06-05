package com.chinaedustar.publish.label;

import java.util.*;

import com.chinaedustar.publish.PublishContext;
import com.chinaedustar.publish.PublishUtil;
import com.chinaedustar.publish.comp.OpenType;
import com.chinaedustar.publish.model.*;
import com.chinaedustar.template.core.*;
 
/**
 * 栏目标签。
 * 
 * @author liujunxing
 *
 */
public final class GeneralColumnLabel extends GroupLabelBase {
	/** 不需要实例化。 */
	private GeneralColumnLabel() {
	}
	
	/** 注册标签处理器。 */
	public static final void registerHandler(LabelHandlerMap map) {
		// logger.info("registerHandler() batch set LabelHandler.");
	
		map.put("ColumnID", new ColumnPropertyHandler());	// 调试通过。
		map.put("ColumnName", new ColumnPropertyHandler());	// 调试通过。
		map.put("ColumnUrl", new ColumnUrlLabelHandler());	// 
		map.put("ColumnDir", new ColumnPropertyHandler());	// 调试通过。
		map.put("ParentDir", new ColumnPropertyHandler());	// 调试通过。
		map.put("Readme", new ColumnPropertyHandler());		// 调试通过。
		map.put("ColumnPicUrl", new ColumnPropertyHandler());
		
		map.put("Meta_Keywords_Column", new ColumnPropertyHandler());
		map.put("Meta_Description_Column", new ColumnPropertyHandler());

		map.put("ShowChildColumn", new ShowChildColumnHandler());	// 调试通过，还需完善。
		map.put("ShowAllChildColumn", new ShowAllChildColumnLabelHandler());	// 调试通过，需要有默认图片/images/nodePic.gif和leafPic.gif
		
		// 还未测试
		map.put("ShowColumns", new ShowColumnsLabelHandler()); 
		map.put("ShowChildColumnItems", new ShowChildColumnItemsHandler());
	}

	/**
	 * 提供 Column 通用属性的处理器。
	 * @label #{ColumnDir} - 显示当前栏目的目录。如果当前栏目是频道的根栏目，那么显示为频道的目录。
	 * @label #{ParentDir} - 显示当前栏目的父栏目的目录。如果当前栏目是频道的根栏目或者父栏目是频道
	 *    的根栏目，那么父栏目的目录是频道的目录。
	 * @label #{ReadMe} - 显示当前栏目的说明。如果当前栏目是频道的根栏目，那么显示为频道的说明。
	 * @label #{ColumnName} - 显示当前栏目的名称。
	 * @label #{ColumnID} - 显示当前栏目的标识。 
	 * @lable #{ColumnPicUrl} - 栏目图片地址。 (column.logo)
	 * @label #{Meta_Keywords_Column} - 针对搜索引擎的关键字
	 * @label #{Meta_Description_Column} - 针对搜索引擎的说明
	 * 注意：当前环境中必须有栏目对象。
	 * 
	 * @author liujunxing
	 */
	private static final class ColumnPropertyHandler extends AbstractSimpleLabelHandler {
		/*
		 * (non-Javadoc)
		 * @see com.chinaedustar.publish.itfc.LabelHandler#handleLabel(com.chinaedustar.publish.PublishContext, com.chinaedustar.template.core.InternalProcessEnvironment, com.chinaedustar.template.core.AbstractLabelElement)
		 */
		public int handleLabel(PublishContext pub_ctxt, InternalProcessEnvironment env, AbstractLabelElement label) {
			String label_name = label.getLabelName();
			Column column = AbstractLabelHandler.getCurrentColumn(env);
			String result = "";
			
			// 部分不需要 column 对象的.
			if ("Meta_Keywords_Column".equals(label_name))
				result = column_meta_key(column);
			else if ("Meta_Description_Column".equals(label_name))
				result = column_meta_desc(column);
			else if (column == null) {
				env.getOut().write("#{?? " + label_name + " 当前页面中没有栏目。");
				return PROCESS_DEFAULT;
			}
			
			// 必须要 column 对象的.
			if ("ColumnDir".equals(label_name))
				result = column_dir(column);
			else if ("ParentDir".equals(label_name))
				result = parent_dir(column);
			else if ("Readme".equals(label_name))
				result = read_me(column);
			else if ("ColumnName".equals(label_name))
				result = column_name(column);
			else if ("ColumnID".equals(label_name))
				result = String.valueOf(column.getId());
			else if ("ColumnPicUrl".equals(label_name))
				result = column.getLogo();
			
			env.getOut().write(result);
			return PROCESS_DEFAULT;
		}
		
		// #{ColumnDir}
		private static final String column_dir(Column column) {
			if (column.getParentId() != 0) {
				return column.getColumnDir();
			} else {
				return column.getChannel().getChannelDir();
			}
		}
		
		// #{ParentDir}
		private static final String parent_dir(Column column) {
			String parentDir = "";
			Object parent = column.getParent();
			if (parent == null)
				parentDir = "";
			else if (parent instanceof Channel)
				parentDir = ((Channel)parent).getChannelDir();
			else 
				parentDir = ((Column)parent).getColumnDir();
			return parentDir;
		}
	
		// #{ReadMe}
		private static final String read_me(Column column) {
			if (column.getParentId() == 0)
				return column.getChannel().getDescription();
			else
				return column.getDescription();
		}
		
		// #{ColumnName}
		private static final String column_name(Column column) {
			if (column.getParentId() == 0)
				return column.getChannel().getName() + "的根栏目";
			else
				return column.getName();
		}
	
		// #{Meta_Keywords_Column}
		private static final String column_meta_key(Column column) {
			if (column == null || column.getMetaKey() == null) return "";
			if (column.getMetaKey().length() == 0) return "";
			
			StringBuffer strbuf = new StringBuffer();
			// 输出为: <meta name="keywords" content="the keywords..." />
			strbuf.append("<meta name=\"keywords\" content=\"")
				.append(PublishUtil.HtmlEncode(column.getMetaKey()))
				.append("\" />");
			return strbuf.toString();
		}
		
		private static final String column_meta_desc(Column column) {
			if (column == null || column.getMetaDesc() == null) return "";
			if (column.getMetaDesc().length() == 0) return "";
			
			StringBuffer strbuf = new StringBuffer();
			// 输出为: <meta name="description" content="the descriptions..." />
			strbuf.append("<meta name=\"description\" content=\"")
				.append(PublishUtil.HtmlEncode(column.getMetaDesc()))
				.append("\" />");
			return strbuf.toString();
		}
	}
	
	/** 
	 * 显示当前栏目的链接地址，可能是动态的JSP地址，也可能是一个静态的地址，也可能是一个外部地址。
	 * #{ColumnUrl channelId="1" columnId="1" }
	 * @param channelId - 频道的标识，0：当前频道；>0：特定的频道。
	 * @param columnId - 栏目的标识，0：当前栏目；>0：特定的标识。 
	 * 如果当前栏目是频道的根栏目，显示出频道的链接地址。
	 */
	static final class ColumnUrlLabelHandler extends AbstractLabelHandler {
		@Override public int handleLabel() {
			int channelId = label.getAttributes().safeGetIntAttribute("channelId", 0);
			int columnId = label.getAttributes().safeGetIntAttribute("columnId", 0);
			Column column = null;
			Channel channel = null;
			if (channelId == 0) {
				channel = getCurrentChannel();
			} else {
				channel = pub_ctxt.getSite().getChannels().getChannel(channelId);
			}
			if (columnId == 0) {
				column = getCurrentColumn();
			} else {
				if (columnId == channel.getRootColumnId()) {
					column = channel.getColumnTree().getColumn(columnId);
				} else {
					column = channel.getColumnTree().getColumn(columnId);
				}
			}
			if (column != null) {
				if (column.getParentId() == 0) {	// 频道的根栏目。
					out(channel.calcPageUrl());
				} else {
					out(column.calcPageUrl());	// ColumnUrl 还需要在数据库中设计。
				}
			} else {
				out("{?? #ColumnUrl 当前页面中不存在栏目对象(column)}");
			}
			return PROCESS_DEFAULT;
		}
	}
	
	/**
	 * 显示指定栏目的子栏目列表(只显示一级子栏目)。
	 * #{ShowChildColumn channelId="1" columnId="0" colNum="1" style="default_css" isHorizontal="true" target="" }
	 * @param channelId - 频道的标识，0：当前频道；>0：特定的频道。
	 * @param columnId - 栏目的标识，默认为当前栏目(=0)。>0: 特定的栏目。-1 表示根栏目。
	 * TODO: 不支持，名字要更改。 @param columnNum - 最多显示的子栏目个数。 默认为所有的子栏目。
	 * @param imageType - 栏目前的小图标类型。0：没有；1：■；2：小图片：/images/column[columnId].gif。默认为没有。
	 * @param openType - 打开方式。0：本窗口；1：新窗口。默认为本窗口。
	 * TODO: 名字更改。 @param cols - 每行显示的子栏目个数，默认为1。
	 * @param style - 显示的样式表。
	 * @param classTitle - 标题的CSS样式表中的类名称。
	 * @param classContent - 内容的CSS样式表中的类名称。
	 * @param classBottom - 底部的CSS样式表中的类名称。
	 * 
	 * <pre> 
	 * #{ShowChildColumn columnId="0" colNum="1" } 
	 *   <a href="#{column.pageUrl}" target="_blank">#{column.name}</a> 
	 * #{/ShowChildColumn }
	 * </pre>
	 * 显示子栏目列表的循环自定义标签（只显示一级子栏目）。
	 * @param - columnId, colNum 同上。
	 * 说明：默认的以一列显示出栏目列表，指定 colNum > 1，多列显示栏目列表。
	 * @author wangyi
	 * 
	 * (也许我们要指定更多获取数据方式的属性)
	 */
	static final class ShowChildColumnHandler extends AbstractLabelHandler2 {
		/** 缺省模板名。 */
		private static final String DEFAULT_TEMPLATE = ".builtin.showchildcolumn";
		private int channelId;
		private int columnId;
		private int cols;
		private Map<String, Object> options;			// 选项。
		private List<Column> column_list;				// 子栏目数据。
		
		/*
		 * (non-Javadoc)
		 * @see com.chinaedustar.publish.label.AbstractLabelHandler#handleLabel()
		 */
		@Override public int handleLabel() {
			// 准备参数。
			makeParam();
			
			// 获得数据。
			column_list = getChildColumnList();
			if (column_list == null) return PROCESS_SIBLING;
			
			// 执行。
			if (label.hasChild())
				return processHasChild();
			else
				return processWithTemplate();
		}
		
		/*
		 * (non-Javadoc)
		 * @see com.chinaedustar.publish.label.AbstractLabelHandler2#getBuiltinName()
		 */
		@Override public String getBuiltinName() {
			return DEFAULT_TEMPLATE;
		}
		
		/** 准备参数。 */
		private void makeParam() {
			AttributeCollection coll = label.getAttributes();
			channelId = super.getChannelAttrib().channelId;
			columnId = super.getColumnAttrib().getFirstColumnId(); // coll.safeGetIntAttribute("columnId", 0);
			cols = coll.safeGetIntAttribute("cols", 1);
			if (cols <= 0) {
				cols = 1;
			}
			
			options = coll.attrToOptions();
			options.put("imageType", coll.safeGetIntAttribute("imageType", 0));
			options.put("openType", OpenType.fromString(coll.safeGetStringAttribute("openType", null)));
			options.put("channelId", channelId);
			options.put("columnId", columnId);
			options.put("cols", cols);
		}
		
		/**
		 * 获得指定频道下指定栏目的所有子栏目。
		 * @param channelId
		 * @param columnId
		 * @return
		 */
		private List<Column> getChildColumnList() {
			Channel channel = null;
			if (channelId <= 0) {
				channel = super.getCurrentChannel();
			} else {
				channel = pub_ctxt.getSite().getChannel(channelId);
			}
			if (channel == null) return null;
			
			if (columnId == -1) {
				// 获取根栏目的子栏目，也就是频道的第一级栏目列表。
				return channel.getColumnTree().getChildColumnList(0);
			} else if (columnId > 0) {
				// 获得指定栏目的子栏目。
				return channel.getColumnTree().getChildColumnList(columnId);
			} else if (columnId == 0) {
				// 获得当前栏目的子栏目。
				Column column = getCurrentColumn();
				if (column == null) {
					// out("{?? #ShowChildColumn 当前页面中不存在栏目对象(column) }");
					return channel.getColumnTree().getChildColumnList(0);
				} else {
					return channel.getColumnTree().getChildColumnList(column.getId());
				}
			} else {
				// out("{?? #ShowChildColumn columnId 的参数设置不正确 }");
				return null;
			}
		}
	
		/** 当 #ShowChildColumn 有子节点的时候，当做内部模板来实现。 */
		private int processHasChild() {
			LocalContext local_ctxt = env.acquireLocalContext(label, 0);
			local_ctxt.setVariable("column_list", column_list);
			local_ctxt.setVariable("options", options);
			super.addRepeaterSupport(local_ctxt, "column", column_list);
			
			env.visit(label.getFirstChild(), true, true);
			return PROCESS_SIBLING;
		}
	
		/** 当没有子节点的时候，使用缺省模板进行处理。 */
		private int processWithTemplate() {
			// 装载模板。
			String template_name = super.getTemplateName(DEFAULT_TEMPLATE);
			BuiltinLabel builtin_label = getBuiltinLabel(template_name);
			if (builtin_label == null) return super.unexistBuiltinLabel(template_name);
			
			return builtin_label.process(env, new Object[]{column_list, options});
		}
	}

	/**
	 * 频道中特定栏目集合的循环自定义标签。
	 * 
	 * #{ShowColumns channelId="1" columnIds="3, 5, 7, 9, 11" loop="false"}
	 *  <a href="#{ColumnUrl}">#{ColumnName}, #{column.name}</a>
	 * #{/ShowColumns}
	 * 
	 * @param channelId - 频道的标识，0：当前频道；>0：指定的频道；
	 * @param columnIds - 栏目标识的集合。
	 * @param loop - 是否自动循环，缺省为 true
	 * 
	 * @author wangyi
	 *
	 */
	static final class ShowColumnsLabelHandler extends AbstractLabelHandler {
		@Override public int handleLabel() {
			if (label.hasChild() == false) return PROCESS_SIBLING;

			// 参数
			int channelId = label.getAttributes().safeGetIntAttribute("channelId", 0);
			String columnIds = label.getAttributes().safeGetStringAttribute("columnIds", "");
			boolean loop = label.getAttributes().safeGetBoolAttribute("loop", true);
			Map<String, Object> options = label.getAttributes().attrToOptions();
			
			// 获取数据
			ArrayList<Column> column_list = getColumnList(channelId, columnIds);
			if (column_list == null || column_list.isEmpty()) {
				return PROCESS_SIBLING;
			}
			
			// 执行模板
			LocalContext local_ctxt = env.acquireLocalContext(label, 0);
			local_ctxt.setVariable("column_list", column_list);
			local_ctxt.setVariable("options", options);
			
			if (loop) {
				// 直接对内部标签执行循环。
				env.foreach(label, "column", column_list, RepeatNameSetter.Instance);
				return PROCESS_SIBLING;
			} else {
				// 不进行循环，由内部自己执行循环。
				return PROCESS_DEFAULT;
			}
		}
		
		/**
		 * 获得指定频道下指定标识的栏目集合。
		 * @param channelId
		 * @param columnIds
		 * @return
		 */
		private ArrayList<Column> getColumnList(int channelId, String columnIds) {
			if (columnIds == null || columnIds.trim().length() < 1) {
				return null;
			} else {
				Channel channel = null;
				if (channelId == 0) {
					channel = getCurrentChannel();
				} else {
					channel = pub_ctxt.getSite().getChannels().getChannel(channelId);
				}
				if (channel == null) return null;
				
				String[] columns = columnIds.split(",");
				ArrayList<Column> list = new ArrayList<Column>();
				ColumnTree ctree = channel.getColumnTree();
				for (int i = 0; i < columns.length; i++) {
					String columnId = columns[i].trim();
					if (columnId.length() > 0) {
						Column column = ctree.getColumn(Integer.parseInt(columnId));
						if (column != null) {
							list.add(column);
						}
					}
				}
				return list;
			}
		}
	}

	/**
	 * 显示栏目的子孙栏目列表，只是支持树型显示和平铺型显示。
	 * #{ShowAllChildColumn channelId="0" columnId="0" isTree="true" linkStyle="" style="" currentStyle="" mouseOverStyle="" padding_left="10" nodePic="Skin/blue/node.gif" leafPic="Skin/blue/leaf.gif" target="_self" }
	 * 
	 * @param channelId - 频道的标识，0：当前频道；> 0：指定的频道。
	 * @param colunnId - 栏目的标识，-1：频道的根栏目；0：当前栏目；>0：指定的栏目。默认为 0 。
	 * @param isTree - 是否是树型显示，不是的话以平铺显示。默认为 true 。
	 * @param linkStyle - 链接的样式表。
	 * @param style - 节点的样式表。
	 * @param currentStyle - 当前节点的样式表。
	 * @param mouseOverStyle - 鼠标移上去时节点的样式表。
	 * @param padding_left - 属性层级的缩进，以像素为单位（px），深度越大自动缩进越多，默认为 0（不缩进）。
	 * @param nodePic - 节点前面的图片地址，默认为 images/nodePic.gif 。
	 * @param leafPic - 叶子节点前面的图片地址，默认为 images/leafPic.gif。
	 * @param target - 链接的目标窗口，默认为 _self 。 
	 * @author wangyi
	 *
	 */
	static final class ShowAllChildColumnLabelHandler extends AbstractLabelHandler {
		private static final String DEFAULT_TEMPLATE = ".builtin.showallchildcolumn";
		/** 选项 */
		private java.util.Map<String, Object> options;
		/** 栏目列表数据. */
		private List<Column> column_list;
		private int channelId;
		private int columnId;
		private boolean isTree;
		private String linkStyle;
		private String style;
		private String currentStyle;
		private String mouseOverStyle;
		private int padding_left;
		private String nodePic;
		private String leafPic;
		private String target;
		
		@Override public int handleLabel() {
			// 参数
			makeOptions();
			
			// 数据
			this.column_list = getData();
			
			// 如果有子标签，则直接执行子标签。
			if (label.hasChild()) {
				LocalContext local_ctxt = env.acquireLocalContext(label, 0);
				local_ctxt.setVariable("column_list", column_list);
				local_ctxt.setVariable("options", options);
				addRepeaterSupport(local_ctxt, "column_list", column_list);
				return PROCESS_DEFAULT;
			}
			
			// 否则执行缺省模板。
			String template_name = super.getTemplateName(DEFAULT_TEMPLATE);
			BuiltinLabel builtLabel = getBuiltinLabel(template_name);
			if (builtLabel == null) return super.unexistBuiltinLabel(template_name);

			// 合成并输出。
			Object[] args = new Object[] {column_list, options};
			return builtLabel.process(env, args);
		}
		
		private void makeOptions() {
			AttributeCollection attrs = label.getAttributes();
			this.channelId = attrs.safeGetIntAttribute("channelId", 0);
			this.columnId = attrs.safeGetIntAttribute("columnId", 0);
			this.isTree = attrs.safeGetBoolAttribute("isTree", true);
			this.linkStyle = attrs.getNamedAttribute("linkStyle");
			this.style = attrs.getNamedAttribute("style");
			this.currentStyle = attrs.getNamedAttribute("currentStyle");
			this.mouseOverStyle = attrs.getNamedAttribute("mouseOverStyle");
			this.padding_left = attrs.safeGetIntAttribute("padding_left", 12);
			this.nodePic = attrs.safeGetStringAttribute("nodePic", "images/nodePic.gif");
			this.leafPic = attrs.safeGetStringAttribute("leafPic", "images/leafPic.gif");
			this.target = attrs.safeGetStringAttribute("target", "_self");
			
			options = attrs.attrToOptions();
			
			options.put("channelId", channelId);
			options.put("columnId", columnId);
			options.put("isTree", isTree);
			options.put("linkStyle", linkStyle);
			options.put("style", style);
			options.put("currentStyle", currentStyle);
			options.put("mouseOverStyle", mouseOverStyle);
			options.put("padding_left", padding_left);
			options.put("nodePic", nodePic);
			options.put("leafPic", leafPic);
			options.put("target", target);
		}
		
		/** 获得数据。其中计算了 padding_left, nodePic 的额外属性，放置在 column.attributes 里面。 */
		private final List<Column> getData() {
			List<Column> column_list = getColumnList();
			if (column_list == null) return null;
			if (column_list.size() == 0) return column_list;
			
			// 为产生树状输出产生额外数据，额外数据存放在 column dyna attribute 里面。
			int ref_depth = column_list.get(0).getDepth();
			for (int i = 0; i < column_list.size(); i++) {
				Column column = column_list.get(i);
				if (isCurrentColumn(columnId)) 		// 是否是当前栏目。
					column.setAttribute("style", currentStyle);
				else 
					column.setAttribute("style", style);
				column.setAttribute("padding_left", (column.getDepth() - ref_depth) * padding_left);
				if (isLeafColumn(column_list, i)) {
					column.setAttribute("nodePic", leafPic);
				} else {
					column.setAttribute("nodePic", nodePic);
				}
				
				column.setAttribute("mouseOverStyle", currentStyle);
				column.setAttribute("linkStyle", linkStyle);
			}
			return column_list;
		}

		/**
		 * 返回 List&lt;Column&gt;
		 * @return
		 */
		private List<Column> getColumnList() {
			// 获得频道。
			Channel channel = null;
			if (channelId == 0) {
				channel = getCurrentChannel();
			} else {
				channel = pub_ctxt.getSite().getChannels().getChannel(channelId);
			}
			
			// 获得栏目。
			ColumnTree column_tree = null;
			if (columnId == -1) {
				if (channel == null) {
					out("{?? #ShowAllChildColumn 当前页面中不存在频道对象(channel) }");
					return null;
				}
				columnId = channel.getRootColumnId();
				column_tree = channel.getColumnTree();
			} else if (columnId == 0) {
				Column column = getCurrentColumn();
				if (column == null) {
					// 当做 columnId == -1
					if (channel == null) {
						out("{?? #ShowAllChildColumn 当前页面中不存在频道对象(channel) }");
						return null;
					}
					columnId = channel.getRootColumnId();
					column_tree = channel.getColumnTree();
				} else {				
					// ? 如果这个 column 不完整，则这里可能返回 null
					column_tree = column.getColumnTree();
				}
			} else if (columnId > 0) {
				if (channel == null) {
					out("{?? #ShowAllChildColumn 当前页面中不存在频道对象(channel) }");
					return null;
				}
				column_tree = channel.getColumnTree();
			}
			
			return column_tree.getAllChildColumnList(columnId);
		}
	
		/**
		 * 指定栏目是否是当前栏目。
		 * @param env
		 * @param columnId
		 * @return
		 */
		private boolean isCurrentColumn(int columnId) {
			Column column = getCurrentColumn();
			if (column == null) {
				return true;
			} else {
				if (column.getId() == columnId) {
					return true;
				} else {
					return false;
				}
			}
		}
		
		/**
		 * 当前节点是否是叶子节点。
		 * @param dt
		 * @param index
		 * @return
		 */
		private boolean isLeafColumn(List<Column> column_list, int index) {
			if (index == column_list.size() - 1) {
				return true;
			}

			int id = column_list.get(index).getId();
			String parentPath = column_list.get(index).getParentPath();
			String parentPath2 = column_list.get(index + 1).getParentPath();
			if (parentPath2.equals(parentPath + Integer.toString(id, 36) + "/")) {
				return false;
			} else {
				return true;
			}
		}
	}

	/** 
	 * 显示当前栏目的子栏目 Item 列表。
	 * #{ShowChildColumnItems titleNum="20" columnCss="" style="" cols = "2"} 
	 * @param channelId 频道标识。0：当前频道；>0：相应标识的频道。默认为0。
	 * @param columnId 栏目标识。-1：根栏目；0：当前栏目；>0：相应标识的栏目。默认为0。
	 * @param titleNum 标题长度。默认为20。
	 * @param columnCss 栏目的CSS样式
	 * @param style 项目的样式
	 * @param cols 每行显示多少个栏目
	 */
	static final class ShowChildColumnItemsHandler extends AbstractLabelHandler{
		private Map<String, Object> options;
		private boolean debug = false;
		
		private Channel channel;			// 当前频道
		private int columnId;				// 栏目标识。
		
		private List<Column> column_list;
		
		private boolean makeOption() {
			AttributeCollection coll = label.getAttributes();
			this.options = coll.attrToOptions();
			
			int channelId = coll.safeGetIntAttribute("channelId", 0);
			this.columnId = coll.safeGetIntAttribute("columnId", 0);
			
			// 获取频道参数。
			if (channelId == 0)
				this.channel = getCurrentChannel();				
			else
				this.channel = pub_ctxt.getSite().getChannel(channelId);
			
			if (this.channel == null) {
				out("#{?? ShowChildColumnItems 没有当前频道或未指定频道标识}");
				return false;
			}
			
			if (columnId == 0) {
				Column column = getCurrentColumn();
				if (column == null) {
					columnId = -1;
				} else {
					columnId = column.getId();
				}
			}
			if (columnId == -1) {
				columnId = channel.getRootColumnId();
			}

			this.debug = coll.safeGetBoolAttribute("debug", false);
			int cols = coll.safeGetIntAttribute("cols", 2);
			if (cols <= 0) cols = 2;
			String columnCss =  coll.safeGetStringAttribute("columnCss", "");
			String articleListCss = coll.safeGetStringAttribute("style", "");
			String widthPercent = coll.safeGetStringAttribute("widthPercent", null);
			if (widthPercent == null || widthPercent.length() == 0)
				widthPercent = (Integer)(100/cols) + "%";

			options.put("debug", debug);
			options.put("cols", cols);
			options.put("columnCss", columnCss);
			options.put("style", articleListCss);
			options.put("widthPercent", widthPercent);
			return true;
		}
		
		@SuppressWarnings("rawtypes")
		private void getData() {
			// 获得子栏目列表。
			this.column_list = channel.getColumnTree().getChildColumnList(columnId);
			if (column_list == null || column_list.size() == 0) return;
			
			for (int i = 0; i < column_list.size(); i++) {
				// 获得每个子栏目中文章列表。
				Column column = column_list.get(i);
				ItemQueryExecutor ibo = new ItemQueryExecutor(pub_ctxt);
				ItemQueryOption query_option = new ItemQueryOption();
				query_option.channelId = column.getChannelId();
				query_option.columnId = column.getId();
				query_option.status = Item.STATUS_APPR;		// 审核通过的。
				query_option.includeChildColumns = true;	// 包含子栏目。
				PaginationInfo page_info = new PaginationInfo(1, 6);	// TODO: pageSize=6
				
				System.out.println("66666666666666666666666666666666666666666666666666666666666666666666666");
				List item_list = ibo.getAdvItemList(query_option, page_info);
				
				column.setAttribute("item_list", item_list);
				// column.getItemList_Deprecated(includeChildColumn, isDeleted, status, isTop, isElite, isHot, isGenerated, inputer, page, maxPerPage, field, keyword, titleNum)
				
				// TODO:
				// map.put("article_list", column.getItemList(true, 
				//	  false, 1, null, null, null, null, null, 1, 8, null, null, titleNum));
				// columnList.add(map);
			}
		}
		
		private static final String DEFAULT_TEMPLATE = ".builtin.showchildcolumnitems";
		
		// 执行模板。
		private int process() {
			if (label.hasChild()) {
				// 有子节点则设置子节点支持并执行。
				LocalContext local_ctxt = env.acquireLocalContext(label, 0);
				local_ctxt.setVariable("column_list", column_list);
				super.addRepeaterSupport(local_ctxt, "column", column_list);
				return PROCESS_DEFAULT;
			}
			
			// 否则执行内建 Label。
			Object[] args = new Object[] { column_list, options };
			String template_name = super.getTemplateName(DEFAULT_TEMPLATE);
			BuiltinLabel builtin_label = getBuiltinLabel(template_name);
			if (builtin_label == null) return super.unexistBuiltinLabel(template_name);
			
			return builtin_label.process(env, args);
		}
		
		@Override public int handleLabel() {
			//获取参数
			if (makeOption() == false) return PROCESS_DEFAULT;

			getData();
			
			return process();
		}
	}

}
