package com.chinaedustar.publish;

import java.util.List;

import com.chinaedustar.publish.model.Admin;
import com.chinaedustar.publish.model.AdminCollection;
import com.chinaedustar.publish.model.AdminRightCollection;
import com.chinaedustar.publish.model.Announcement;
import com.chinaedustar.publish.model.AnnouncementCollection;
import com.chinaedustar.publish.model.Author;
import com.chinaedustar.publish.model.AuthorCollection;
import com.chinaedustar.publish.model.Channel;
import com.chinaedustar.publish.model.ChannelCollection;
import com.chinaedustar.publish.model.Column;
import com.chinaedustar.publish.model.ColumnTree;
import com.chinaedustar.publish.model.Comment;
import com.chinaedustar.publish.model.CommentCollection;
import com.chinaedustar.publish.model.ExtendProperty;
import com.chinaedustar.publish.model.ExtendPropertySet;
import com.chinaedustar.publish.model.Feedback;
import com.chinaedustar.publish.model.FeedbackCollection;
import com.chinaedustar.publish.model.FriendSite;
import com.chinaedustar.publish.model.FriendSiteCollection;
import com.chinaedustar.publish.model.FriendSiteKind;
import com.chinaedustar.publish.model.FriendSiteSpecial;
import com.chinaedustar.publish.model.Item;
import com.chinaedustar.publish.model.Keyword;
import com.chinaedustar.publish.model.KeywordCollection;
import com.chinaedustar.publish.model.Label;
import com.chinaedustar.publish.model.LabelCollection;
import com.chinaedustar.publish.model.LogCollection;
import com.chinaedustar.publish.model.PageTemplate;
import com.chinaedustar.publish.model.Site;
import com.chinaedustar.publish.model.Skin;
import com.chinaedustar.publish.model.Source;
import com.chinaedustar.publish.model.SourceCollection;
import com.chinaedustar.publish.model.SpecialCollection;
import com.chinaedustar.publish.model.SpecialWrapper;
import com.chinaedustar.publish.model.TemplateTheme;
import com.chinaedustar.publish.model.TemplateThemeCollection;
import com.chinaedustar.publish.model.UpFile;
import com.chinaedustar.publish.model.UpFileCollection;
import com.chinaedustar.publish.model.User;
import com.chinaedustar.publish.model.UserCollection;
import com.chinaedustar.publish.model.VoteCollection;
import com.chinaedustar.publish.model.VoteWrapper;
import com.chinaedustar.publish.model.WebPage;
import com.chinaedustar.publish.model.WebPageCollection;

/**
 * 发布系统需要事务支持的所有业务的接口定义。
 * 
 * @author liujunxing
 *
 */
public interface TransactionProxy {
	// ************ Site *******************************************************//
	/**
	 * 更新网站
	 * @param PublishContext 执行操作的业务对象。
	 * @param Site 网站对象。
	 * @return 更新后的网站。
	 */
	public void updateSite(PublishContext pub_ctxt, Site site);

	// ==== Item ================================================================

	/**
	 * 创建或更新一个项目对象。
	 * @param channel - 所在频道对象。
	 * @param item - 文章、图片、软件等对象。
	 */
	public void saveItem(Channel channel, Item item);

	/**
	 * 删除指定的图片对象。
	 * @param channel - 所在频道。
	 * @param item - 要删除的对象。
	 */
	public boolean deleteItem(Channel channel, Item item);

	/**
	 * 设置指定项目的 top 状态。
	 * @param channel - 所在频道。
	 * @param item - 要操作的对象。
	 * @param is_top - 是否置顶状态。
	 * @return
	 */
	public boolean setItemTop(Channel channel, Item item, boolean is_top);

	/**
	 * 设置指定项目的 commend 状态。
	 * @param channel - 所在频道。
	 * @param item - 要操作的对象。
	 * @param is_commend - 是否推荐状态。
	 * @return
	 */
	public boolean setItemCommend(Channel channel, Item item, boolean is_commend);
	
	/**
	 * 设置指定项目的 elite 状态。
	 * @param channel - 所在频道。
	 * @param item - 要操作的对象。
	 * @param is_elite - 是否精华状态。
	 * @return
	 */
	public boolean setItemElite(Channel channel, Item item, boolean is_elite);
	
	/**
	 * 退稿。
	 * @param channel - 所在频道。
	 * @param item - 要操作的对象。
	 * @return true 表示更新状态成功，false 表示更新不成功。
	 */
	public boolean rejectItem(Channel channel, Item item);
	
	/**
	 * 彻底删除掉一个项目。
	 * @param channel - 所在频道。
	 * @param item - 要操作的对象。
	 * @return
	 */
	public boolean destroyItem(Channel channel, Item item);
	
	/**
	 * 删除指定项目的静态化文件。
	 * @param channel 
	 * @param item
	 */
	public void removeHtmlItem(Channel channel, Item item);
	
	// ************ ColumnTree *************************************************

	/**
	 * 修改栏目。
	 * @param columnTree 执行操作的业务对象。
	 * @param column 栏目对象。
	 * @return 修改后的栏目对象。
	 */
	public void updateColumn(ColumnTree columnTree, Column column);

	/**
	 * 增加栏目信息。
	 * @param columnTree 执行操作的业务对象。
	 * @param column 栏目对象。
	 * @return 增加完成后的栏目对象。
	 */
	public void insertColumn(ColumnTree columnTree, Column column);

	/**
	 * 将第一个栏目合并到第二个栏目中，需要移动文章，修改栏目的排序。
	 * 1、将第一个栏目下面的文章移动到目标栏目下（不包括子孙栏目的文章）；
	 * 2、将第一个栏目下的子栏目移动到目标栏目下；
	 * 3、将目标栏目的子栏目进行重新排序。
	 * 4、删除第一个栏目。
	 * 注意：需要事务的支持。
	 * @param columnTree 执行操作的业务对象，栏目树对象。
	 * @param columnId 需要移动的栏目的标识。
	 * @param targetColumnId 移动的目标栏目的标识。
	 * @return 更新的记录数。
	 */
	public int uniteColumn(ColumnTree columnTree, int columnId, int targetColumnId);

	/**
	 * 删除一个栏目。
	 * 注意：需要事务的支持。
	 * @param columnTree 执行操作的业务对象，栏目树对象。
	 * @param column - 要删除的栏目。
	 * 
	 */
	public void deleteColumn(ColumnTree columnTree, Column column);

	// ************ Column *****************************************************//

	/**
	 * 删除多个内容项，放入回收站中。
	 * @param channel - 所在频道。
	 * @param item_ids - 内容项标识的数组。
	 */
	public List<Integer> batchDeleteItems(Channel channel, List<Integer> item_ids);
	
	/**
	 * 批量审核通过一组内容项。
	 * @param channel - 频道对象。
	 * @param item_ids - 内容项标识的数组。
	 * @param apprvoed - 审核通过与否。
	 * @return
	 */
	public List<Integer> batchApproveItems(Channel channel, List<Integer> item_ids, boolean apprvoed);
	
	/**
	 * 批量彻底销毁项目。
	 * @param channel
	 * @param item_ids
	 * @return
	 */
	public List<Integer> batchDestroyItems(Channel channel, List<Integer> item_ids);
	
	/**
	 * 实际的从数据库中删除一篇内容项。
	 * 注意：需要事务的支持。
	 * @param column 执行操作的业务对象，栏目对象。
	 * @param id 内容项的标识。
	 */
	// public void realDeleteItem(Column column, int itemId);

	/**
	 * 实际的从数据库中删除多篇内容项。
	 * 注意：需要事务的支持。
	 * @param column 执行操作的业务对象，栏目对象。
	 * @param id 内容项的标识。
	 */
	// public void realDeleteItems(Column column, int[] itemIds);

	/**
	 * 清空当前栏目下所有的内容项，放入回收站。
	 * 注意：需要事务的支持。
	 * @param column 执行操作的业务对象，栏目对象。
	 */
	public int clearItems(Column column);

	/**
	 * 
	 * 真是的从数据库中删除当前栏目下所有的内容项。
	 * @param column 执行操作的业务对象，栏目对象。
	 * @return 更新的记录数。
	 */
	public int realClearItems(Column column);

	/**
	 * 还原数据库中当前栏目下所有的内容项
	 * 注意：需要事务的支持。
	 * @param column 执行操作的业务对象，栏目对象。
	 * @return 更新的记录数。
	 */
	public int restoreAllItems(Column column);

	/**
	 * 重新排序当前节点的子节点。
	 * @param column 执行操作的业务对象，栏目对象。
	 * @param ids 全部字节点的标识数组，按照排序的顺序。
	 * @return 更新的记录数。
	 * @throws Exception
	 */
	public int reorderColumnChild(Column column, int[] ids);

	/**
	 * 更新内容项(Item)的推荐属性（elite）。
	 * 注意：需要事务的支持。
	 * @param column 执行操作的业务对象，栏目对象。
	 * @param itemId 内容项的标识。
	 * @param elite 是否推荐。
	 */
	public void eliteItem(Column column, int itemId, boolean elite);

	/**
	 * 还原多个内容项（Item），更新一个删除属性（deleted）。
	 * 注意：需要事务的支持。
	 * @param channel -  频道对象。
	 * @param item_ids - 内容项的标识。
	 */
	public List<Integer> batchRestoreItems(Channel channel, List<Integer> item_ids);

	/**
	 * 移动内容项到当前栏目中。
	 * 注意：需要事务的支持。
	 * @param column 执行操作的业务对象，栏目对象。
	 * @param itemId 内容项的标识。
	 */
	public void moveItem(Column column, int itemId);
	
	/**
	 * 移动内容项到当前栏目中。
	 * 注意：需要事务的支持。
	 * @param column 执行操作的业务对象，栏目对象。
	 * @param itemIds 内容项的标识的数组。
	 */
	public void moveItems(Column column, int[] itemIds);
	
	/**
	 * 更新内容项的状态，-2：退稿；-1：草稿；0：未审核；1：已审核；？？。
	 * 可以执行审核/取消审核的操作。
	 * 注意：需要事务的支持。
	 * @param column 执行操作的业务对象，栏目对象。
	 * @param itemId 内容项的标识。
	 * @param status 状态值。
	 */
	public void updateItemStatus(Column column, int itemId, String editor, int status);

	/**
	 * 更新多个内容项的状态，-2：退稿；-1：草稿；0：未审核；1：已审核；？？。
	 * 可以执行审核/取消审核的操作。
	 * 注意：需要事务的支持。
	 * @param column 执行操作的业务对象，栏目对象。
	 * @param itemIds 内容项标识的数组。
	 * @param status 状态值。
	 */
	public void updateItemsStatus(Column column, int[] itemIds, String editor, int status);

	// ************ ChannelCollection **************************************//
	
	/** 创建一个频道对象。
	 * @param collection 执行操作的业务对象，频道集合对象。
	 * @param special 频道对象。
	 */
	public void createChannel(ChannelCollection collection, Channel channel);

	/** 更新一个频道对象。
	 * @param collection 执行操作的业务对象，频道集合对象。
	 * @param special 频道对象。
	 */
	public void updateChannel(ChannelCollection collection, Channel channel);

	/** 删除一个频道对象，放入回收站。
	 * @param collection - 执行操作的业务对象，频道集合对象。
	 * @param channel - 要删除的频道对象。
	 */
	public void deleteChannel(ChannelCollection collection, Channel channel);

	/**
	 * 从回收站中恢复一个频道。
	 * @param collection
	 * @param channel
	 */
	public void recoverChannel(ChannelCollection collection, Channel channel);
	
	/**
	 * 彻底销毁一个频道，删除其相关所有内容。
	 * @param collection
	 * @param channel
	 */
	public void destroyChannel(ChannelCollection collection, Channel channel);

	/**
	 * 禁用/启用指定频道。
	 * @param channel_coll - 频道集合对象。
	 * @param channel - 要禁用/启用的频道。
	 * @param enable_status - = true 表示要启用，= false 表示要禁用。
	 */
	public void enableChannel(ChannelCollection channel_coll, Channel channel, boolean enable_status);
	
	/** 对指定的一组频道对象进行排序
	 * @param collection 执行操作的业务对象，频道集合对象。
	 * @param new_order_ids - 指定频道对象标识的数组, 要其其按照新顺序排列。
	 */
	public void reorderChannel(ChannelCollection collection, List<Integer> new_order_ids);
	
	// ************ SpecialCollection **************************************//
	
	/**
	 * 创建一个专题的信息。
	 * @param collection 执行操作的业务对象，专题集合对象。
	 * @param special 专题对象。
	 */
	public void insertSpecial(SpecialCollection collection, SpecialWrapper special);

	/**
	 * 更新一个专题的信息。
	 * @param collection 执行操作的业务对象，专题集合对象。
	 * @param special 专题对象。
	 */
	public void updateSpecial(SpecialCollection collection, SpecialWrapper special);

	/**
	 * 删除指定专题对象。
	 * @param collection 执行操作的业务对象，专题集合对象。
	 * @param special 专题对象。
	 * @return 更新的记录数。
	 */
	public int deleteSpecial(SpecialCollection collection, SpecialWrapper special);

	/**
	 * 清空指定专题的所有内容项。
	 * 注意：需要事务的支持。
	 * @param collection - 执行操作的业务对象，专题集合对象。
	 * @param special - 专题对象。
	 * @return 更新的记录数。
	 */
	public int clearSpecialItems(SpecialCollection collection, SpecialWrapper special);

	/**
	 * 删除多个专题内容引用对象。
	 * @param collection 执行操作的业务对象，专题集合对象。
	 * @param refids 引用对象标识集合。
	 * @return 更新的记录数。
	 */
	public int deleteRefSpecialItems(SpecialCollection collection, List<Integer> refids);

	/**
	 * 将指定的这些内容项移动到指定的这些专题中。
	 * @param collection - 执行操作的业务对象，专题集合对象。
	 * @param refids - 关联项标识的数组。
	 * @param specialIds - 专题标识的数组。
	 * @param copy - 是否是复制到，否则为移动到指定专题。
	 * @return
	 */
	public int copyMoveRefSpecialItems(SpecialCollection collection, List<Integer> refids, List<Integer> specialIds, boolean copy);
	
	/**
	 * 对专题进行重新排序。
	 * @param collection 执行操作的业务对象，专题集合对象。
	 * @param special_ids - 专题的标识数组，该数组的顺序决定了专题的顺序。
	 */
	public int reorderSpecial(SpecialCollection collection, List<Integer> special_ids);

	/**
	 * 将第一个专题合并到第二个专题中，所有的文章会转移到第二个专题中，第一个专题将会被删除。
	 * @param collection 执行操作的业务对象，专题集合对象。
	 * @param source_special - 源专题。
	 * @param target_special - 目标专题。
	 */
	public void uniteSpecial(SpecialCollection collection, SpecialWrapper source_special, SpecialWrapper target_special);

	// ************ AuthorCollection ****************************************//
	/**
	 * 创建一个新的作者。
	 * @param collection 执行操作的业务对象，作者集合对象。
	 * @param author 作者对象。
	 */
	public void insertAuthor(AuthorCollection collection, Author author);

	/**
	 * 更新一个作者。
	 * @param collection 执行操作的业务对象，作者集合对象。
	 * @param author
	 */
	public void updateAuthor(AuthorCollection collection, Author author);

	/**
	 * 删除多个作者。
	 * @param collection 执行操作的业务对象，作者集合对象。
	 * @param authorId 作者的标识。
	 */
	public void deleteAuthors(AuthorCollection collection, List<Integer> authorIds);

	// ************ KeywordCollection ***************************************//
	/**
	 * 创建关键字对象。
	 * @param collection 执行操作的业务对象，关键字集合对象。
	 * @param keyword
	 */
	public void insertKeyword(KeywordCollection collection, Keyword keyword);

	/**
	 * 更新关键字对象。
	 * @param collection 执行操作的业务对象，关键字集合对象。
	 * @param keyword
	 */
	public void updateKeyword(KeywordCollection collection, Keyword keyword);

	/**
	 * 删除多个的关键字对象。
	 * @param collection 执行操作的业务对象，关键字集合对象。
	 * @param keywordIds
	 */
	public void deleteKeywords(KeywordCollection collection, List<Integer> keywordIds);

	/**
	 * 清空当前频道下面的所有关键字。
	 * @param collection 执行操作的业务对象，关键字集合对象。
	 * @return 更新的记录数。
	 */
	public int clearKeywords(KeywordCollection collection);

	// ************ SourceCollection ****************************************//
	/**
	 * 创建来源对象。
	 * @param collection 执行操作的业务对象，来源集合对象。
	 * @param source
	 */
	public void insertSource(SourceCollection collection, Source source);

	/**
	 * 更新来源对象。
	 * @param collection 执行操作的业务对象，来源集合对象。
	 * @param source
	 */
	public void updateSource(SourceCollection collection, Source source);

	/**
	 * 删除指定的来源。
	 * @param collection 执行操作的业务对象，来源集合对象。
	 * @param sourceIds
	 */
	public void deleteSources(SourceCollection collection, List<Integer> sourceIds);

	// ************ UpFileCollection ****************************************//
	/**
	 * 保存一个上传文件对象。
	 * @collection 执行操作的业务对象，来源集合对象。
	 * @param upFile
	 */
	public void insertUpFile(UpFileCollection collection, UpFile upFile);

	/**
	 * 保存多个上传文件对象。
	 * @collection 执行操作的业务对象，来源集合对象。
	 * @param upFile
	 */
	public void insertUpFiles(UpFileCollection collection, UpFile[] upFiles);

	/**
	 * 删除指定的文件对象。
	 * @collection 执行操作的业务对象，来源集合对象。
	 * @param fileId 文件对象的标识。
	 */
	public void deleteUpFile(UpFileCollection collection, int fileId);

	/**
	 * 删除指定多个的文件对象。
	 * @collection 执行操作的业务对象，来源集合对象。
	 * @param fileId 文件对象的标识。
	 */
	public void deleteUpFiles(UpFileCollection collection, int[] fileIds);

	/**
	 * 删除指定的上传文件对象。
	 * @collection 执行操作的业务对象，来源集合对象。
	 * @param filePath 文件的路径，如：/PubWeb/news/UploadFiles/2007/03/20070313102030123.txt 。
	 */
	public void delete(UpFileCollection collection, String filePath);

	// === Theme, Template 操作 ========================================================
	
	/** 创建/更新一个模板方案对象。
	 * @param collection - 执行操作的业务对象，模板方案集合对象。
	 * @param theme - 模板方案对象。
	 */
	public void saveTemplateTheme(TemplateThemeCollection collection, TemplateTheme theme);

	/** 删除一个模板方案对象。
	 * @param collection 执行操作的业务对象，模板方案集合对象。
	 * @param theme - 模板方案对象。
	 */
	public void deleteTemplateTheme(TemplateThemeCollection collection, TemplateTheme theme);

	/**
	 * 新建或更新一个模板对象。
	 * @param theme - 执行操作的业务对象。
	 * @param pageTemplate - 需要保存的模板对象。
	 */
	public void savePageTemplate(TemplateTheme theme, PageTemplate pageTemplate);

	/**
	 * 设置指定的模板为该类的缺省模板。
	 * @param theme_coll
	 * @param template
	 */
	public boolean setDefaultTemplate(TemplateThemeCollection theme_coll, PageTemplate template);
	
	/**
	 * 删除一组模板对象，放入模板回收站中。
	 * @param theme_coll - 执行操作的业务对象。
	 * @param ids - 需要删除的模板对象的标识。
	 */
	public int deletePageTemplate(TemplateThemeCollection theme_coll, List<Integer> ids);

	/**
	 * 复制一个页面模板。
	 * @param theme_coll
	 * @param src_tmpl
	 * @return
	 */
	public PageTemplate copyPageTemplate(TemplateThemeCollection theme_coll, PageTemplate src_tmpl);

	/**
	 * 频道间复制模板。
	 * @param theme_coll
	 * @param template_ids
	 * @param target_channel_ids
	 */
	public void copyChannelTemplate(TemplateThemeCollection theme_coll, List<Integer> template_ids, List<Integer> target_channel_ids);
	
	/**
	 * 批量复制或移动一组模板到目标模板方案。
	 * @param source_theme - 源模板方案。
	 * @param destin_theme - 目标模板方案。
	 * @param template_ids - 要复制/移动的模板标识。
	 * @param copy - true 表示复制，false 表示移动。
	 */
	public Result batchCopyMoveTemplate(TemplateTheme source_theme, TemplateTheme destin_theme, List<Integer> template_ids, boolean copy);
	

	/**
	 * 实际地从数据库删除一个模板对象。
	 * @param theme - 执行操作的业务对象。
	 * @param id 需要删除的模板对象的标识。
	 */
	public void realDeletePageTemplate(TemplateThemeCollection theme_coll, List<Integer> ids);
	
	/**
	 * 从回收站还原一个模板对象。
	 * @param theme - 执行操作的业务对象。
	 * @param ids - 需要删除的模板对象的标识。
	 */
	public void restorePageTemplate(TemplateThemeCollection theme_coll, List<Integer> ids);

	/**
	 * 清空一个模板分类下回收站中所有的模板对象。
	 * @param theme
	 */
	public void clearAllPageTemplate(TemplateTheme theme);
	
	/**
	 * 还原一个模板分类下回收站中所有的末班对象。
	 * @param theme - 模板方案对象。
	 */
	public void restoreAllPageTemplate(TemplateTheme theme);

	
	// ************ Label **********************************************//
	/**
	 * 创建一个标签对象
	 * @collection 执行操作的业务对象，来源集合对象。
	 * @param collection 执行操作的业务对象，来源集合对象。
	 */
	public void insertLabel(LabelCollection collection, Label label);

	/**
	 * 更新一个标签对象
	 * @collection 执行操作的业务对象，来源集合对象。
	 * @param collection 执行操作的业务对象，来源集合对象。
	 */
	public void updateLabel(LabelCollection collection, Label label);

	/**
	 * 删除一个标签对象
	 * @collection 执行操作的业务对象，来源集合对象。
	 * @param collection 执行操作的业务对象，来源集合对象。
	 * @param id 需要设为默认模板的模版对象标识。
	 */
	public void deleteLabel(LabelCollection collection, int id);

	// ************ Admin **********************************************//

	/**
	 * 添加管理员。
	 * @param collection 执行操作的业务对象，管理员集合对象。
	 * @param admin 管理员对象。
	 */
	public void insertAdmin(AdminCollection collection, Admin admin);

	/**
	 * 修改管理员。
	 * @param collection 执行操作的业务对象，管理员集合对象。
	 * @param admin 管理员对象。
	 */
	public void updateAdmin(AdminCollection collection, Admin admin);

	/**
	 * 更新管理员 admin 的权限。
	 * @param admin - 管理员对象。
	 * @param new_rights - 新的权限。
	 * @param old_rights - 原来的权限集合。
	 */
	public void updateAdminRight(Admin admin, AdminRightCollection new_rights, AdminRightCollection old_rights);
	
	/**
	 * 批量删除管理员。
	 * @param collection 执行操作的业务对象，管理员集合对象。
	 * @param adminIds 管理员标识的数组。
	 */
	public void deleteAdmins(AdminCollection collection, List<Integer> adminIds);

	// ************ UserCollection **********************************************//
	
	/**
	 * 更新用户的密码，返回更新的结果，密码是否更新成功。
	 * @param collection 执行操作的业务对象，用户集合对象。
	 * @param userId 用户的标识。
	 * @param oldPassword 用户的旧密码。
	 * @param newPassword 用户的新密码。
	 * @throws Exception
	 * @return 密码是否更新成功。
	 */
	public boolean updateUserPassword(UserCollection collection, int userId, String oldPassword, String newPassword);

	/**
	 * 添加/修改会员信息。
	 * @param user_coll 执行操作的业务对象，用户集合对象。
	 * @param user
	 */
	public void saveUser(UserCollection user_coll, User user);

	/**
	 * 删除指定标识的会员。
	 * @param user_coll - 执行操作的业务对象，用户集合对象。
	 * @param userIds - 会员的标识。
	 * @return
	 */
	public int deleteUsers(UserCollection user_coll, List<Integer> userIds);

	/**
	 * 修改会员的状态信息。
	 * 需要事务支持。
	 * @param collection 执行操作的业务对象，用户集合对象。
	 * @param userIds 用户的标识的数组。
	 * @param status 状态值。
	 */
	public void updateUserStatus(UserCollection collection, List<Integer> userIds, int status);

	/**
	 * 修改会员的是否允许投稿的状态信息。
	 * 需要事务的支持。
	 * @param collection 执行操作的业务对象，用户集合对象。
	 * @param userId 用户的标识的数组。
	 * @param inputer 是否允许投稿。
	 */
	public void updateUserInputer(UserCollection collection, List<Integer> userIds, boolean inputer);

	//************ CommentCollection **********************************************//
	/**
	 * 创建一个评论对象
	 * @collection 执行操作的业务对象，评论集合对象。
	 * @param collection 执行操作的业务对象，评论集合对象。
	 */
	public void saveComment(CommentCollection collection, Comment comment);

	/**
	 * 删除若干个评论对象
	 * @collection 执行操作的业务对象，评论集合对象。
	 * @param collection 执行操作的业务对象，评论集合对象。
	 * @param commentIds 需要删除的评论对象标识的集合。
	 */
	public void deleteComment(CommentCollection collection, List<Integer> ids);

	/**
	 * 清空项目中的所有评论。
	 * @param collection 执行操作的业务对象，评论集合对象。
	 * @throws Exception 
	 */
	public void clearCommentReplys(CommentCollection collection);

	/**
	 * 更改评论审核状态。
	 * @param collection 执行操作的业务对象，评论集合对象。
	 * @param ids 需要进行操作的评论对象标识的数组。
	 * @param passed 是否通过审核。
	 * @throws Exception 
	 */
	public void updateCommentStatus(CommentCollection collection, List<Integer> ids, boolean passed);

	//	 ******************* 风格管理 ****************************************//

	/**
	 * 保存一个风格对象。
	 * @collection 执行操作的业务对象，来源集合对象。
	 * @param collection 执行操作的业务对象，来源集合对象。
	 * @param skin 需要保存的风格对象。
	 */
	public void saveSkin(TemplateThemeCollection coll, Skin skin);

	/**
	 * 删除指定的风格对象。
	 * @param collection - 执行操作的业务对象，风格集合对象。
	 * @param skinIds - 要删除的皮肤的标识。
	 */
	public void deleteSkin(TemplateThemeCollection coll, List<Integer> skinIds);

	/**
	 * 设置默认的皮肤
	 * @param collection - 执行操作的业务对象，风格集合对象。
	 * @param skin - 风格对象。
	 */
	public void setDefaultSkin(TemplateThemeCollection coll, Skin skin);

	/**
	 * 刷新所有的皮肤CSS文件
	 * @param collection 执行操作的业务对象，风格集合对象。
	 */
	public void refreshAllSkinCssFile(TemplateThemeCollection coll);

	// ************ FriendSiteCollection **************************************//
	
	/** 创建/更新一个友情链接对象。
	 * @param collection 执行操作的业务对象，友情链接集合对象。
	 * @param friendSite 友情链接对象。
	 */
	public void saveFriendSite(FriendSiteCollection collection, FriendSite friendSite);

	/** 将一个友情链接对象设为/取消精华。
	 * @param collection 执行操作的业务对象，友情链接集合对象。
	 * @param ids 友情链接对象标识的数组。
	 * @param elite_status 是否设为精华
	 */
	public int eliteFriendSite(FriendSiteCollection collection, List<Integer> ids, boolean elite_status);

	/** 更改友情链接对象的审核状态。
	 * @param collection 执行操作的业务对象，友情链接集合对象。
	 * @param ids 友情链接对象标识的数组。
	 * @param appr_status 是否审核通过
	 */
	public int approveFriendSite(FriendSiteCollection collection, List<Integer> ids, boolean appr_status);

	/** 删除友情链接对象。
	 * @param collection 执行操作的业务对象，友情链接集合对象。
	 * @param ids 要删除的友情链接对象的标识的数组。
	 */
	public int deleteFriendSite(FriendSiteCollection collection, List<Integer> ids);

	/** 移动指定的友情链接对象到指定类别。。
	 * @param collection 执行操作的业务对象，友情链接集合对象。
	 * @param ids 要移动的友情链接对象的标识的数组。
	 * @param kindId 要移动到的类别的标识。
	 */
	public int moveFriendSiteKind(FriendSiteCollection collection, List<Integer> ids, int kindId);

	/** 移动指定的友情链接对象到指定专题。。
	 * @param collection 执行操作的业务对象，友情链接集合对象。
	 * @param ids 要移动的友情链接对象的标识的数组。
	 * @param specialId 要移动到的专题的标识。
	 */
	public int moveFriendSiteSpecial(FriendSiteCollection collection, List<Integer> ids, int specialId);

	/** 对友情链接对象进行排序。
	 * @param collection 执行操作的业务对象，友情链接集合对象。
	 * @param ids 要排序的友情链接对象的标识的数组。
	 */
	public void reorderFriendSite(FriendSiteCollection collection, List<Integer> ids);

	// FsKind 相关业务
	
	/** 创建/更新一个友情链接类别对象。
	 * @param collection 执行操作的业务对象，友情链接集合对象。
	 * @param fsKind 友情链接类别对象。
	 */
	public void saveFsKind(FriendSiteCollection collection, FriendSiteKind kind);

	/** 删除一个友情链接类别对象。
	 * @param collection 执行操作的业务对象，友情链接集合对象。
	 * @param id 要删除的友情链接类别对象的标识。
	 */
	public FriendSiteKind deleteFsKind(FriendSiteCollection collection, int kindId);

	/** 清空指定的友情链接类别，属于该类别的友情链接将被设置为 null 类别。
	 * @param collection 执行操作的业务对象，友情链接集合对象。
	 * @param id 友情链接类别对象的标识。
	 */
	public FriendSiteKind clearFsKind(FriendSiteCollection collection, int kindId);

	// FsSpecial 相关业务
	
	/** 创建/更新一个友情链接专题对象。
	 * @param collection 执行操作的业务对象，友情链接集合对象。
	 * @param  友情链接专题对象。
	 */
	public void saveFsSpecial(FriendSiteCollection collection, FriendSiteSpecial special);

	/** 删除一个友情链接专题对象。
	 * @param collection 执行操作的业务对象，友情链接集合对象。
	 * @param id 要删除的友情链接专题对象的标识。
	 */
	public FriendSiteSpecial deleteFsSpecial(FriendSiteCollection collection, int specialId);

	/** 清空指定的友情链接专题，属于该专题的友情链接将被设置为 null 专题。
	 * @param collection 执行操作的业务对象，友情链接集合对象。
	 * @param id 友情链接专题对象的标识。
	 */
	public FriendSiteSpecial clearFsSpecial(FriendSiteCollection collection, int specialId);
	
	// === Vote 相关业务 ==================================================================
	
	/** 创建/更新一个调查对象。
	 * @param collection 执行操作的业务对象，调查集合对象。
	 * @param  调查对象。
	 */
	public void saveVote(VoteCollection collection, VoteWrapper vote);
	
	// ************ AnnouncementCollection **************************************//
	
	/** 创建/更新一个公告对象。
	 * @param collection 执行操作的业务对象，公告集合对象。
	 * @param announce 公告对象。
	 */
	public void saveAnnounce(AnnouncementCollection collection, Announcement announce);
	
	/** 
	 * 更改公告对象的选定状态。
	 * @param collection 执行操作的业务对象，公告集合对象。
	 * @param ids 公告对象标识的数组。
	 * @param appr_status 是否审核通过
	 */
	public void activeAnnounce(AnnouncementCollection collection, List<Integer> ids, boolean isSelected);
	
	/** 更改公告对象的显示方式。
	 * @param collection 执行操作的业务对象，公告集合对象。
	 * @param ids 公告对象标识的数组。
	 * @param appr_status 是否审核通过
	 */
	public void changeAnnounceShowType(AnnouncementCollection collection, List<Integer> ids, int showType);
	
	/** 删除公告对象。
	 * @param collection 执行操作的业务对象，公告集合对象。
	 * @param ids 要删除的公告对象的标识的数组。
	 */
	public void deleteAnnounce(AnnouncementCollection collection, List<Integer> ids);
	
	/** 移动公告对象到制定频道。
	 * @param collection 执行操作的业务对象，公告集合对象。
	 * @param ids 要移动的公告对象的标识的数组。
	 */
	public void moveAnnounce(AnnouncementCollection collection, List<Integer> ids, int channelId);

	// ************************* FeedbackCollection 留言的操作方法。 ***********************************************
	
	/** 添加新的留言。 */
	public void insertFeedback(FeedbackCollection collection, Feedback feedback);
	
	/** 更新留言。 */
	public void updateFeedback(FeedbackCollection collection, Feedback feedback);
	
	/** 更新一组留言的状态，状态（0：未审核；1：审核） */
	public void updateFeedbackStatus(FeedbackCollection collection, int[] ids, int status);

	/** 删除指定的留言。 */
	public int deleteFeedbacks(FeedbackCollection collection, int[] ids);

	// === 日志管理 ====================================================================
	
	/**
	 * 批量删除日志。
	 * @param log_coll
	 * @param ids
	 */
	public void batchDeleteLogs(LogCollection log_coll, List<Integer> ids);
	
	/**
	 * 清空所有日志。
	 * @param log_coll
	 */
	public void clearLogs(LogCollection log_coll);

	// === 自定义页面 ==================================================================
	
	/**
	 * 保存或更新一个自定义页面。
	 */
	public void saveWebPage(WebPageCollection webpage_coll, WebPage webpage);
	
	/**
	 * 新建一个扩展属性。
	 * @param prop_set
	 * @param prop
	 */
	public void createExtendProperty(ExtendPropertySet prop_set, ExtendProperty prop);
	
	/**
	 * 删除指定名字的扩展属性。
	 * @param prop_set
	 * @param prop_name
	 */
	public void deleteExtendProperty(ExtendPropertySet prop_set, String prop_name);

	/**
	 * 删除所有扩展属性。
	 * @param prop_set
	 */
	public void deleteAllExtendProperty(ExtendPropertySet prop_set);
}
