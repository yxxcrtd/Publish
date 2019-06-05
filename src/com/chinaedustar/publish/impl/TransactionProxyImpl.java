package com.chinaedustar.publish.impl;

import java.util.List;

import com.chinaedustar.publish.PublishContext;
import com.chinaedustar.publish.Result;
import com.chinaedustar.publish.TransactionProxy;
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
import com.chinaedustar.publish.model.LayoutCollection;
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
import com.chinaedustar.publish.pjo.Layout;

/**
 * 发布系统的代理类，主要是为了完成发布系统内的事务代理操作。
 * 发布系统的业务模型对象中不需要各自的配置事务处理，
 * 需要进行事务处理的方法全部需要交给发布系统代理类来处理，
 * 而代理类的实际处理还是调用业务模型对象的内部特定方法完成。
 * @author wangyi
 *
 */
public class TransactionProxyImpl implements TransactionProxy {
	/**
	 * 构造函数。
	 */
	public TransactionProxyImpl() {
		
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jpub.TransactionProxy#updateSite(cn.edustar.jpub.PublishContext, cn.edustar.jpub.model.Site)
	 */
	public void updateSite(PublishContext pub_ctxt, Site site) {
		pub_ctxt.updateSite(site);
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jpub.TransactionProxy#saveItem(cn.edustar.jpub.model.Channel, cn.edustar.jpub.model.Item)
	 */
	public void saveItem(Channel channel, Item item) {
		channel.saveItem(item);
	}
	
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jpub.TransactionProxy#deletePhoto(cn.edustar.jpub.model.Channel, cn.edustar.jpub.model.Photo)
	 */
	public boolean deleteItem(Channel channel, Item item) {
		return channel.deleteItem(item);
	}
	
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jpub.TransactionProxy#setItemTop(cn.edustar.jpub.model.Channel, cn.edustar.jpub.model.Item, boolean)
	 */
	public boolean setItemTop(Channel channel, Item item, boolean is_top) {
		return channel.setItemTop(item, is_top);
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jpub.TransactionProxy#setItemCommend(cn.edustar.jpub.model.Channel, cn.edustar.jpub.model.Item, boolean)
	 */
	public boolean setItemCommend(Channel channel, Item item, boolean is_commend) {
		return channel.setItemCommend(item, is_commend);
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jpub.TransactionProxy#setItemElite(cn.edustar.jpub.model.Channel, cn.edustar.jpub.model.Item, boolean)
	 */
	public boolean setItemElite(Channel channel, Item item, boolean is_elite) {
		return channel.setItemElite(item, is_elite);
	}
	
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jpub.TransactionProxy#rejectItem(cn.edustar.jpub.model.Channel, cn.edustar.jpub.model.Item)
	 */
	public boolean rejectItem(Channel channel, Item item) {
		return channel.rejectItem(item);
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jpub.TransactionProxy#destroyItem(cn.edustar.jpub.model.Channel, cn.edustar.jpub.model.Item)
	 */
	public boolean destroyItem(Channel channel, Item item) {
		return channel.destroyItem(item);
	}
	
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jpub.TransactionProxy#removeHtmlItem(cn.edustar.jpub.model.Channel, cn.edustar.jpub.model.Item)
	 */
	public void removeHtmlItem(Channel channel, Item item) {
		item.removeHtml();
	}

	
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jpub.TransactionProxy#updateColumn(cn.edustar.jpub.model.ColumnTree, cn.edustar.jpub.model.Column)
	 */
	public void updateColumn(ColumnTree columnTree, Column column) {
		columnTree.update(column);
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jpub.TransactionProxy#insertColumn(cn.edustar.jpub.model.ColumnTree, cn.edustar.jpub.model.Column)
	 */
	public void insertColumn(ColumnTree columnTree, Column column) {
		columnTree.insert(column);
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jpub.TransactionProxy#uniteColumn(cn.edustar.jpub.model.ColumnTree, int, int)
	 */
	public int uniteColumn(ColumnTree columnTree, int columnId, int targetColumnId) {
		// return columnTree.uniteColumn(columnId, targetColumnId);
		throw new UnsupportedOperationException();
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jpub.TransactionProxy#deleteColumn(cn.edustar.jpub.model.ColumnTree, cn.edustar.jpub.model.Column)
	 */
	public void deleteColumn(ColumnTree columnTree, Column column) {
		columnTree.delete(column);
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jpub.TransactionProxy#batchDeleteItems(cn.edustar.jpub.model.Channel, java.util.List)
	 */
	public List<Integer> batchDeleteItems(Channel channel, List<Integer> item_ids) {
		return channel.batchDeleteItems(item_ids);
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jpub.TransactionProxy#batchApproveItems(cn.edustar.jpub.model.Channel, java.util.List, boolean)
	 */
	public List<Integer> batchApproveItems(Channel channel, List<Integer> item_ids, boolean approved) {
		return channel.batchApproveItems(item_ids, approved);
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jpub.TransactionProxy#batchDestroyItems(cn.edustar.jpub.model.Channel, java.util.List)
	 */
	public List<Integer> batchDestroyItems(Channel channel, List<Integer> item_ids) {
		return channel.batchDestroyItems(item_ids);
	}

	/**
	 * 清空当前栏目下所有的内容项，放入回收站。
	 * 注意：需要事务的支持。
	 * @param column 执行操作的业务对象，栏目对象。
	 */
	public int clearItems(Column column) {
		return column.clearItems();
	}
	
	/**
	 * 
	 * 真是的从数据库中删除当前栏目下所有的内容项。
	 * 注意：需要事务支持。
	 * @param column 执行操作的业务对象，栏目对象。
	 * @return 更新的记录数。
	 */
	public int realClearItems(Column column) {
		return column.realClearItems();
	}

	/**
	 * 还原数据库中当前栏目下所有的内容项
	 * 注意：需要事务的支持。
	 * @param column 执行操作的业务对象，栏目对象。
	 * @return 更新的记录数。
	 */
	public int restoreAllItems(Column column) {
		return column.restoreAllItems();
	}
	
	/**
	 * 重新排序当前节点的子节点。
	 * @param column 执行操作的业务对象，栏目对象。
	 * @param ids 全部字节点的标识数组，按照排序的顺序。
	 * @return 更新的记录数。
	 * @throws Exception
	 */
	public int reorderColumnChild(Column column, int[] ids) {
		return column.orderChild(ids);
	}
	
	/**
	 * 更新内容项(Item)的推荐属性（elite）。
	 * 注意：需要事务的支持。
	 * @param column 执行操作的业务对象，栏目对象。
	 * @param itemId 内容项的标识。
	 * @param elite 是否推荐。
	 */
	public void eliteItem(Column column, int itemId, boolean elite) {
		// column.updateElite(itemId, elite);
		throw new UnsupportedOperationException();
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jpub.TransactionProxy#batchRestoreItems(cn.edustar.jpub.model.Channel, java.util.List)
	 */
	public List<Integer> batchRestoreItems(Channel channel, List<Integer> item_ids) {
		return channel.batchRestoreItems(item_ids);
	}

	/**
	 * 移动内容项到当前栏目中。
	 * 注意：需要事务的支持。
	 * @param column 执行操作的业务对象，栏目对象。
	 * @param itemId 内容项的标识。
	 */
	public void moveItem(Column column, int itemId) {
		column.moveItem(itemId);
	}
	
	/**
	 * 移动内容项到当前栏目中。
	 * 注意：需要事务的支持。
	 * @param column 执行操作的业务对象，栏目对象。
	 * @param itemIds 内容项的标识的数组。
	 */
	public void moveItems(Column column, int[] itemIds) {
		for (int i = 0; i < itemIds.length; i++) {
			column.moveItem(itemIds[i]);
		}
	}
	
	/**
	 * 更新内容项的状态，-2：退稿；-1：草稿；0：未审核；1：已审核；？？。
	 * 可以执行审核/取消审核的操作。
	 * 注意：需要事务的支持。
	 * @param column 执行操作的业务对象，栏目对象。
	 * @param itemId 内容项的标识。
	 * @param status 状态值。
	 */
	public void updateItemStatus(Column column, int itemId, String editor, int status) {
		column.updateStatus(itemId, editor, status);
	}
	
	/**
	 * 更新多个内容项的状态，-2：退稿；-1：草稿；0：未审核；1：已审核；？？。
	 * 可以执行审核/取消审核的操作。
	 * 注意：需要事务的支持。
	 * @param column 执行操作的业务对象，栏目对象。
	 * @param itemIds 内容项标识的数组。
	 * @param status 状态值。
	 */
	public void updateItemsStatus(Column column, int[] itemIds, String editor, int status) {
		column.updateStatus(itemIds, editor, status);
	}
	
	// ************ ChannelCollection **************************************//
	
	
	/** 创建一个频道对象。
	 * 注意：需要事务支持。
	 * @param channel_coll 执行操作的业务对象，频道集合对象。
	 * @param channel 频道对象。
	 */
	public void createChannel(ChannelCollection channel_coll, Channel channel) {
		channel_coll.createChannel(channel);
	}		
	
	/** 更新一个频道对象。
	 * 注意：需要事务支持。
	 * @param channel_coll 执行操作的业务对象，频道集合对象。
	 * @param channel 频道对象。
	 */
	public void updateChannel(ChannelCollection channel_coll, Channel channel) {
		channel_coll.updateChannel(channel);
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jpub.TransactionProxy#deleteChannel(cn.edustar.jpub.model.ChannelCollection, cn.edustar.jpub.model.Channel)
	 */
	public void deleteChannel(ChannelCollection channel_coll, Channel channel) {
		channel_coll.deleteChannel(channel);
	}
	
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jpub.TransactionProxy#destroyChannel(cn.edustar.jpub.model.ChannelCollection, cn.edustar.jpub.model.Channel)
	 */
	public void destroyChannel(ChannelCollection channel_coll, Channel channel) {
		channel_coll.destroyChannel(channel);
	}

	
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jpub.TransactionProxy#recoverChannel(cn.edustar.jpub.model.ChannelCollection, cn.edustar.jpub.model.Channel)
	 */
	public void recoverChannel(ChannelCollection channel_coll, Channel channel) {
		channel_coll.recoverChannel(channel);
	}
	
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jpub.TransactionProxy#enableChannel(cn.edustar.jpub.model.ChannelCollection, cn.edustar.jpub.model.Channel, boolean)
	 */
	public void enableChannel(ChannelCollection channel_coll, Channel channel, boolean enable_status) {
		channel_coll.enableChannel(channel, enable_status);
	}

	
	/** 实际地从数据库中删除一个频道对象。
	 * 注意：需要事务支持。
	 * @param collection 执行操作的业务对象，频道集合对象。
	 * @param ids 要删除的频道对象的标识数组。
	 */
	public void realDeleteChannel(ChannelCollection collection, int id) {
		collection.realDeleteChannel(id);
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jpub.TransactionProxy#reorderChannel(cn.edustar.jpub.model.ChannelCollection, java.util.List)
	 */
	public void reorderChannel(ChannelCollection collection, List<Integer> new_order_ids) {
		collection.reorderChannel(new_order_ids);
	}		

	// ************ SpecialCollection **************************************//
	
	/**
	 * 创建一个专题的信息。
	 * 注意：需要事务支持。
	 * @param collection 执行操作的业务对象，专题集合对象。
	 * @param special 专题对象。
	 */
	public void insertSpecial(SpecialCollection collection, SpecialWrapper special) {
		collection.insertSpecial(special);
	}		
	
	/**
	 * 更新一个专题的信息。
	 * 注意：需要事务支持。
	 * @param collection 执行操作的业务对象，专题集合对象。
	 * @param special 专题对象。
	 */
	public void updateSpecial(SpecialCollection collection, SpecialWrapper special) {
		collection.updateSpecial(special);
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jpub.TransactionProxy#deleteSpecial(cn.edustar.jpub.model.SpecialCollection, cn.edustar.jpub.model.Special)
	 */
	public int deleteSpecial(SpecialCollection collection, SpecialWrapper special) {
		return collection.deleteSpecial(special);
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jpub.TransactionProxy#clearSpecialItems(cn.edustar.jpub.model.SpecialCollection, cn.edustar.jpub.model.Special)
	 */
	public int clearSpecialItems(SpecialCollection collection, SpecialWrapper special) {
		return collection.clearItems(special);
	}
	
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jpub.TransactionProxy#deleteRefSpecialItems(cn.edustar.jpub.model.SpecialCollection, java.util.List)
	 */
	public int deleteRefSpecialItems(SpecialCollection collection, List<Integer> refids) {
		return collection.deleteRefSpecialItems(refids);
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jpub.TransactionProxy#copyMoveRefSpecialItems(cn.edustar.jpub.model.SpecialCollection, java.util.List, java.util.List, boolean)
	 */
	public int copyMoveRefSpecialItems(SpecialCollection spec_coll, List<Integer> refids, List<Integer> specialIds, boolean copy) {
		if (copy)
			return spec_coll.copyRefSpecialItems(refids, specialIds);
		else
			return spec_coll.moveRefSpecialItems(refids, specialIds);
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jpub.TransactionProxy#reorderSpecial(cn.edustar.jpub.model.SpecialCollection, java.util.List)
	 */
	public int reorderSpecial(SpecialCollection collection, List<Integer> special_ids) {
		return collection.reorder(special_ids);
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jpub.TransactionProxy#uniteSpecial(cn.edustar.jpub.model.SpecialCollection, cn.edustar.jpub.model.Special, cn.edustar.jpub.model.Special)
	 */
	public void uniteSpecial(SpecialCollection collection, SpecialWrapper source_special, SpecialWrapper target_special) {
		collection.uniteSpecial(source_special, target_special);
	}
	
	// ************ AuthorCollection ****************************************//

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jpub.TransactionProxy#insertAuthor(cn.edustar.jpub.model.AuthorCollection, cn.edustar.jpub.model.Author)
	 */
	public void insertAuthor(AuthorCollection collection, Author author) {
		collection.insertAuthor(author);
	}
	
	/**
	 * 更新一个作者。
	 * 注意：需要事务支持。
	 * @param collection 执行操作的业务对象，作者集合对象。
	 * @param author
	 */
	public void updateAuthor(AuthorCollection collection, Author author) {
		collection.updateAuthor(author);
	}
	
	/**
	 * 删除一个作者。
	 * 注意：需要事务支持。
	 * @param collection 执行操作的业务对象，作者集合对象。
	 * @param authorId 作者的标识。
	 */
	public void deleteAuthor(AuthorCollection collection, int authorId) {
		collection.deleteAuthor(authorId);
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jpub.TransactionProxy#deleteAuthors(cn.edustar.jpub.model.AuthorCollection, java.util.List)
	 */
	public void deleteAuthors(AuthorCollection collection, List<Integer> authorIds) {
		for (int i = 0; i < authorIds.size(); i++) {
			collection.deleteAuthor(authorIds.get(i));
		}
	}
	
	// ************ KeywordCollection ***************************************
	
	/**
	 * 创建关键字对象。
	 * 注意：需要事务支持。
	 * @param collection 执行操作的业务对象，关键字集合对象。
	 * @param keyword
	 */
	public void insertKeyword(KeywordCollection collection, Keyword keyword) {
		collection.insertKeyword(keyword);
	}
	
	/**
	 * 更新关键字对象。
	 * 注意：需要事务支持。
	 * @param collection 执行操作的业务对象，关键字集合对象。
	 * @param keyword
	 */
	public void updateKeyword(KeywordCollection collection, Keyword keyword) {
		collection.updateKeyword(keyword);
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jpub.TransactionProxy#deleteKeywords(cn.edustar.jpub.model.KeywordCollection, java.util.List)
	 */
	public void deleteKeywords(KeywordCollection collection, List<Integer> keywordIds) {
		collection.batchDeleteKeywords(keywordIds);
	}
	
	/**
	 * 清空当前频道下面的所有关键字。
	 * 注意：需要事务支持。
	 * @param collection 执行操作的业务对象，关键字集合对象。
	 * @return 更新的记录数。
	 */
	public int clearKeywords(KeywordCollection collection) {
		return collection.clearKeywords();
	}
	
	// ************ SourceCollection ****************************************//
	/**
	 * 创建来源对象。
	 * 注意：需要事务支持。
	 * @param collection 执行操作的业务对象，来源集合对象。
	 * @param source
	 */
	public void insertSource(SourceCollection collection, Source source) {
		collection.insertSource(source);
	}
	
	/**
	 * 更新来源对象。
	 * 注意：需要事务支持。
	 * @param collection 执行操作的业务对象，来源集合对象。
	 * @param source
	 */
	public void updateSource(SourceCollection collection, Source source) {
		collection.updateSource(source);
	}
	
	/**
	 * 删除指定的来源。
	 * 注意：需要事务支持。
	 * @param collection 执行操作的业务对象，来源集合对象。
	 * @param sourceId
	 */
	public void deleteSource(SourceCollection collection, int sourceId) {
		collection.deleteSource(sourceId);
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jpub.TransactionProxy#deleteSources(cn.edustar.jpub.model.SourceCollection, java.util.List)
	 */
	public void deleteSources(SourceCollection collection, List<Integer> sourceIds) {
		for (int i = 0; i < sourceIds.size(); i++) {
			collection.deleteSource(sourceIds.get(i));
		}
	}
	
	// ************ UpFileCollection ****************************************

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jpub.TransactionProxy#insertUpFile(cn.edustar.jpub.model.UpFileCollection, cn.edustar.jpub.model.UpFile)
	 */
	public void insertUpFile(UpFileCollection collection, UpFile upFile) {
		collection.insertUpFile(upFile);
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jpub.TransactionProxy#insertUpFiles(cn.edustar.jpub.model.UpFileCollection, cn.edustar.jpub.model.UpFile[])
	 */
	public void insertUpFiles(UpFileCollection collection, UpFile[] upFiles) {
		for (int i = 0; i < upFiles.length; i++) {
			collection.insertUpFile(upFiles[i]);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jpub.TransactionProxy#deleteUpFile(cn.edustar.jpub.model.UpFileCollection, int)
	 */
	public void deleteUpFile(UpFileCollection collection, int fileId) {
		collection.delete(fileId);
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jpub.TransactionProxy#deleteUpFiles(cn.edustar.jpub.model.UpFileCollection, int[])
	 */
	public void deleteUpFiles(UpFileCollection collection, int[] fileIds) {
		for (int i = 0; i < fileIds.length; i++) {
			collection.delete(fileIds[i]);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jpub.TransactionProxy#delete(cn.edustar.jpub.model.UpFileCollection, java.lang.String)
	 */
	public void delete(UpFileCollection collection, String filePath) {
		collection.delete(filePath);
	}
		
	// ************ TemplateThemeCollection **************************************

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jpub.TransactionProxy#saveTemplateTheme(cn.edustar.jpub.model.TemplateThemeCollection, cn.edustar.jpub.model.TemplateTheme)
	 */
	public void saveTemplateTheme(TemplateThemeCollection collection, TemplateTheme theme) {
		collection.saveTemplateTheme(theme);
	}		

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jpub.TransactionProxy#deleteTemplateTheme(cn.edustar.jpub.model.TemplateThemeCollection, cn.edustar.jpub.model.TemplateTheme)
	 */
	public void deleteTemplateTheme(TemplateThemeCollection collection, TemplateTheme theme) {
		collection.deleteTemplateTheme(theme);
	}
	
	// ************ PageTemplateCollection ****************************************

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jpub.TransactionProxy#savePageTemplate(cn.edustar.jpub.model.TemplateTheme, cn.edustar.jpub.model.PageTemplate)
	 */
	public void savePageTemplate(TemplateTheme theme, PageTemplate pageTemplate) {
		theme.savePageTemplate(pageTemplate);
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jpub.TransactionProxy#setDefaultTemplate(cn.edustar.jpub.model.TemplateThemeCollection, cn.edustar.jpub.model.PageTemplate)
	 */
	public boolean setDefaultTemplate(TemplateThemeCollection theme_coll, PageTemplate template) {
		return theme_coll.setDefaultTemplate(template);
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jpub.TransactionProxy#deletePageTemplate(cn.edustar.jpub.model.TemplateThemeCollection, java.util.List)
	 */
	public int deletePageTemplate(TemplateThemeCollection theme_coll, List<Integer> templateIds) {
		return theme_coll.deleteTemplates(templateIds);
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jpub.TransactionProxy#copyPageTemplate(cn.edustar.jpub.model.TemplateThemeCollection, cn.edustar.jpub.model.PageTemplate)
	 */
	public PageTemplate copyPageTemplate(TemplateThemeCollection theme_coll, PageTemplate src_tmpl) {
		return theme_coll.copyTemplate(src_tmpl);
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jpub.TransactionProxy#copyChannelTemplate(cn.edustar.jpub.model.TemplateThemeCollection, java.util.List, java.util.List)
	 */
	public void copyChannelTemplate(TemplateThemeCollection theme_coll, List<Integer> template_ids, List<Integer> target_channel_ids) {
		theme_coll.copyChannelTemplate(template_ids, target_channel_ids);
	}

	
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jpub.TransactionProxy#batchCopyMoveTemplate(cn.edustar.jpub.model.TemplateTheme, cn.edustar.jpub.model.TemplateTheme, java.util.List, boolean)
	 */
	public Result batchCopyMoveTemplate(TemplateTheme source_theme, TemplateTheme destin_theme, List<Integer> template_ids, boolean copy) {
		return source_theme.batchCopyMoveTemplate(destin_theme, template_ids, copy);
	}


	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jpub.TransactionProxy#realDeletePageTemplate(cn.edustar.jpub.model.TemplateThemeCollection, java.util.List)
	 */
	public void realDeletePageTemplate(TemplateThemeCollection theme_coll, List<Integer> ids) {
		theme_coll.realDeleteTemplates(ids);
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jpub.TransactionProxy#clearAllPageTemplate(cn.edustar.jpub.model.TemplateTheme)
	 */
	public void clearAllPageTemplate(TemplateTheme theme) {
		throw new UnsupportedOperationException();
		// TODO: theme.clearAll();
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jpub.TransactionProxy#restoreAllPageTemplate(cn.edustar.jpub.model.TemplateTheme)
	 */
	public void restoreAllPageTemplate(TemplateTheme theme) {
		throw new UnsupportedOperationException();
		// TODO: theme.restoreAll();
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jpub.TransactionProxy#restorePageTemplate(cn.edustar.jpub.model.TemplateThemeCollection, java.util.List)
	 */
	public void restorePageTemplate(TemplateThemeCollection theme_coll, List<Integer> templateIds) {
		theme_coll.restoreTemplates(templateIds);
	}

	
	/**
	 * 设置一个模板对象为默认模板。
	 * 注意：需要事务支持。
	 * @collection 执行操作的业务对象，来源集合对象。
	 * @param collection 执行操作的业务对象，来源集合对象。
	 * @param id 需要设为默认模板的模版对象标识。
	 *//*
	public void setDefaultPageTemplate(PageTemplateCollection collection, int id) {
		collection.setDefaultPageTemplate(id);
	}*/
	
	// ************ Label **********************************************//
	/**
	 * 创建一个标签对象
	 * 注意：需要事务支持。
	 * @collection 执行操作的业务对象，来源集合对象。
	 * @param collection 执行操作的业务对象，来源集合对象。
	 */
	public void insertLabel(LabelCollection collection, Label label) {
		collection.createLabel(label);
	}
	
	/**
	 * 更新一个标签对象
	 * 注意：需要事务支持。
	 * @collection 执行操作的业务对象，来源集合对象。
	 * @param collection 执行操作的业务对象，来源集合对象。
	 */
	public void updateLabel(LabelCollection collection, Label label) {
		collection.updateLabel(label);
	}
	
	/**
	 * 删除一个标签对象
	 * 注意：需要事务支持。
	 * @collection 执行操作的业务对象，来源集合对象。
	 * @param collection 执行操作的业务对象，来源集合对象。
	 * @param id 需要设为默认模板的模版对象标识。
	 */
	public void deleteLabel(LabelCollection collection, int id) {
		collection.deleteLabel(id);
	}
	
	// ************ Admin **********************************************//

	/**
	 * 添加管理员。
	 * 注意：需要事务支持。
	 * @param collection 执行操作的业务对象，管理员集合对象。
	 * @param admin 管理员对象。
	 */
	public void insertAdmin(AdminCollection collection, Admin admin) {
		collection.createAdmin(admin);
	}
	
	/**
	 * 修改管理员。
	 * 注意：需要事务支持。
	 * @param collection 执行操作的业务对象，管理员集合对象。
	 * @param admin 管理员对象。
	 */
	public void updateAdmin(AdminCollection collection, Admin admin) {
		collection.updateAdmin(admin);
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jpub.TransactionProxy#updateAdminRight(cn.edustar.jpub.model.Admin, cn.edustar.jpub.model.AdminRightCollection, cn.edustar.jpub.model.AdminRightCollection)
	 */
	public void updateAdminRight(Admin admin, AdminRightCollection new_rights, AdminRightCollection old_rights) {
		admin.updateRights(new_rights, old_rights);
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jpub.TransactionProxy#deleteAdmins(cn.edustar.jpub.model.AdminCollection, java.util.List)
	 */
	public void deleteAdmins(AdminCollection collection, List<Integer> adminIds) {
		collection.deleteAdmins(adminIds);
	}
	
	// ************ UserCollection **********************************************

	/**
	 * 更新用户的密码，返回更新的结果，密码是否更新成功。
	 * 注意：需要事务支持。 
	 * @param collection 执行操作的业务对象，用户集合对象。
	 * @param userId 用户的标识。
	 * @param oldPassword 用户的旧密码。
	 * @param newPassword 用户的新密码。
	 * @throws Exception
	 * @return 密码是否更新成功。
	 */
	public boolean updateUserPassword(UserCollection collection, int userId, String oldPassword, String newPassword) {
		return collection.updatePassword(userId, oldPassword, newPassword);
	}
	
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jpub.TransactionProxy#saveUser(cn.edustar.jpub.model.UserCollection, cn.edustar.jpub.model.User)
	 */
	public void saveUser(UserCollection user_coll, User user) {
		user_coll.saveUser(user);
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jpub.TransactionProxy#deleteUsers(cn.edustar.jpub.model.UserCollection, java.util.List)
	 */
	public int deleteUsers(UserCollection user_coll, List<Integer> userIds) {
		return user_coll.batchDeleteUser(userIds);
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jpub.TransactionProxy#updateUserStatus(cn.edustar.jpub.model.UserCollection, java.util.List, int)
	 */
	public void updateUserStatus(UserCollection collection, List<Integer> userIds, int status) {
		for (int i = 0; i < userIds.size(); i++) {
			collection.updateUserStatus(userIds.get(i), status);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jpub.TransactionProxy#updateUserInputer(cn.edustar.jpub.model.UserCollection, java.util.List, boolean)
	 */
	public void updateUserInputer(UserCollection collection, List<Integer> userIds, boolean inputer) {
		for (int i = 0; i < userIds.size(); i++) {
			collection.updateUserInputer(userIds.get(i), inputer);
		}
	}
	
	// === CommentCollection ==============================================================

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jpub.TransactionProxy#saveComment(cn.edustar.jpub.model.CommentCollection, cn.edustar.jpub.model.Comment)
	 */
	public void saveComment(CommentCollection collection, Comment comment) {
		collection.saveComment(comment);
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jpub.TransactionProxy#deleteComment(cn.edustar.jpub.model.CommentCollection, java.util.List)
	 */
	public void deleteComment(CommentCollection collection, List<Integer> ids) {
		collection.deleteComment(ids);
	}
	
	/**
	 * 清空项目中的所有评论。
	 * @param collection 执行操作的业务对象，评论集合对象。
	 * @throws Exception 
	 */
	public void clearCommentReplys(CommentCollection collection) {
		collection.clearReplys();
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jpub.TransactionProxy#updateCommentStatus(cn.edustar.jpub.model.CommentCollection, java.util.List, boolean)
	 */
	public void updateCommentStatus(CommentCollection collection, List<Integer> ids, boolean passed) {
		collection.updatePassed(ids, passed);
	}
	
	// === 风格管理 ======================================================================

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jpub.TransactionProxy#saveSkin(cn.edustar.jpub.model.TemplateThemeCollection, cn.edustar.jpub.model.Skin)
	 */
	public void saveSkin(TemplateThemeCollection coll, Skin skin) {
		coll.saveSkin(skin);
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jpub.TransactionProxy#deleteSkin(cn.edustar.jpub.model.TemplateThemeCollection, java.util.List)
	 */
	public void deleteSkin(TemplateThemeCollection coll, List<Integer> skinIds) {
		coll.deleteSkin(skinIds);
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jpub.TransactionProxy#setDefaultSkin(cn.edustar.jpub.model.TemplateThemeCollection, cn.edustar.jpub.model.Skin)
	 */
	public void setDefaultSkin(TemplateThemeCollection coll, Skin skin) {
		coll.setDefaultSkin(skin);
	}
	
	/**
	 * 刷新所有的皮肤CSS文件
	 * @param collection 执行操作的业务对象，风格集合对象。
	 * @param skinId 皮肤的标识
	 */
	public void refreshAllSkinCssFile(TemplateThemeCollection coll) {
		coll.refreshAllCssFile();
	}
	
	// ************ FriendSiteCollection **************************************//
	
	/** 创建/更新一个友情链接对象。
	 * 注意：需要事务支持。
	 * @param collection 执行操作的业务对象，友情链接集合对象。
	 * @param friendSite 友情链接对象。
	 */
	public void saveFriendSite(FriendSiteCollection collection, FriendSite friendSite) {
		collection.createOrUpdateFriendSite(friendSite);
	}	

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jpub.TransactionProxy#updateFriendSiteElite(cn.edustar.jpub.model.FriendSiteCollection, java.util.List, boolean)
	 */
	public int eliteFriendSite(FriendSiteCollection collection, List<Integer> ids, boolean elite_status) {
		return collection.eliteFriendSite(ids, elite_status);
	}
	
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jpub.TransactionProxy#updateFriendSiteApprove(cn.edustar.jpub.model.FriendSiteCollection, java.util.List, boolean)
	 */
	public int approveFriendSite(FriendSiteCollection collection, List<Integer> ids, boolean appr_status) {
		return collection.approveFriendSite(ids, appr_status);
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jpub.TransactionProxy#deleteFriendSite(cn.edustar.jpub.model.FriendSiteCollection, java.util.List)
	 */
	public int deleteFriendSite(FriendSiteCollection collection, List<Integer> ids) {
		return collection.deleteFriendSite(ids);
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jpub.TransactionProxy#moveFriendSiteKind(cn.edustar.jpub.model.FriendSiteCollection, java.util.List, int)
	 */
	public int moveFriendSiteKind(FriendSiteCollection collection, List<Integer> ids, int kindId) {
		return collection.moveFriendSiteKind(ids, kindId);
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jpub.TransactionProxy#moveFriendSiteSpecial(cn.edustar.jpub.model.FriendSiteCollection, java.util.List, int)
	 */
	public int moveFriendSiteSpecial(FriendSiteCollection collection, List<Integer> ids, int specialId) {
		return collection.moveFriendSiteSpecial(ids, specialId);
	}
	
	/** 对友情链接对象进行排序。
	 * 注意：需要事务支持。
	 * @param collection 执行操作的业务对象，友情链接集合对象。
	 * @param ids 要排序的友情链接对象的标识的数组。
	 */
	public void reorderFriendSite(FriendSiteCollection collection, List<Integer> ids) {
		collection.sortFriendSite(ids);
	}
	
	// FsKind 相关业务
	
	/** 创建/更新一个友情链接类别对象。
	 * 注意：需要事务支持。
	 * @param collection 执行操作的业务对象，友情链接集合对象。
	 * @param fsKind 友情链接类别对象。
	 */
	public void saveFsKind(FriendSiteCollection collection, FriendSiteKind kind) {
		collection.createOrUpdateFsKind(kind);
	}
	
	/** 删除一个友情链接类别对象。
	 * 注意：需要事务支持。
	 * @param collection 执行操作的业务对象，友情链接集合对象。
	 * @param id 要删除的友情链接类别对象的标识。
	 */
	public FriendSiteKind deleteFsKind(FriendSiteCollection collection, int kindId) {
		return collection.deleteKind(kindId);
	}
	
	/** 清空指定的友情链接类别，属于该类别的友情链接将被设置为 null 类别。
	 * 注意：需要事务支持。
	 * @param collection 执行操作的业务对象，友情链接集合对象。
	 * @param id 友情链接类别对象的标识。
	 */
	public FriendSiteKind clearFsKind(FriendSiteCollection collection, int kindId) {
		return collection.emptyKind(kindId);
	}
	
	// FsSpecial 相关业务
	
	/** 创建/更新一个友情链接专题对象。
	 * 注意：需要事务支持。
	 * @param collection 执行操作的业务对象，友情链接集合对象。
	 * @param  友情链接专题对象。
	 */
	public void saveFsSpecial(FriendSiteCollection collection, FriendSiteSpecial special) {
		collection.createOrUpdateFsSpecial(special);
	}
	
	/** 删除一个友情链接专题对象。
	 * 注意：需要事务支持。
	 * @param collection 执行操作的业务对象，友情链接集合对象。
	 * @param id 要删除的友情链接专题对象的标识。
	 */
	public FriendSiteSpecial deleteFsSpecial(FriendSiteCollection collection, int specialId) {
		return collection.deleteSpecial(specialId);
	}
	
	/** 清空指定的友情链接专题，属于该专题的友情链接将被设置为 null 专题。
	 * 注意：需要事务支持。
	 * @param collection 执行操作的业务对象，友情链接集合对象。
	 * @param id 友情链接专题对象的标识。
	 */
	public FriendSiteSpecial clearFsSpecial(FriendSiteCollection collection, int specialId) {
		return collection.emptySpecial(specialId);
	}
	
	// FsSpecial 相关业务
	
	/** 创建/更新一个调查对象。
	 * 注意：需要事务支持。
	 * @param collection 执行操作的业务对象，调查集合对象。
	 * @param  调查对象。
	 */
	public void saveVote(VoteCollection collection, VoteWrapper vote) {
		collection.createOrUpdateVote(vote);
	}

	// ************ AnnouncementCollection **************************************//
	
	/**
	 * 创建/更新一个公告对象。
	 * 注意：需要事务支持。
	 * @param collection 执行操作的业务对象，公告集合对象。
	 * @param announce 公告对象。
	 */
	public void saveAnnounce(AnnouncementCollection collection, Announcement announce) {
		collection.createOrUpdateAnnounce(announce);
	}	
	
	
	
	
	/* 更改公告对象的选定状态。
	 * 注意：需要事务支持。
	 * @param collection 执行操作的业务对象，公告集合对象。
	 * @param ids 公告对象标识的数组。
	 * @param appr_status 是否审核通过
	 */
	public void activeAnnounce(AnnouncementCollection collection, List<Integer> ids, boolean isSelected) {
		collection.setAnnounceSelected(ids, isSelected);
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jpub.TransactionProxy#updateAnnounceShowType(cn.edustar.jpub.model.AnnouncementCollection, java.util.List, int)
	 */
	public void changeAnnounceShowType(AnnouncementCollection collection, List<Integer> ids, int showType) {
		collection.setAnnounceShowType(ids, showType);
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jpub.TransactionProxy#deleteAnnounce(cn.edustar.jpub.model.AnnouncementCollection, java.util.List)
	 */
	public void deleteAnnounce(AnnouncementCollection collection, List<Integer> ids) {
		collection.deleteAnnounce(ids);
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jpub.TransactionProxy#moveAnnounce(cn.edustar.jpub.model.AnnouncementCollection, java.util.List, int)
	 */
	public void moveAnnounce(AnnouncementCollection collection, List<Integer> ids, int channelId) {
		collection.moveAnnounceToChannel(ids, channelId);		
	}
	
	// ************************* FeedbackCollection 留言的操作方法。 ***********************************************

	/** 
	 * 添加新的留言。 
	 */
	public void insertFeedback(FeedbackCollection collection, Feedback feedback) {
		collection.insertFeedback(feedback);
	}
	
	/** 
	 * 更新留言。 
	 */
	public void updateFeedback(FeedbackCollection collection, Feedback feedback) {
		collection.updateFeedback(feedback);
	}
	
	/** 
	 * 更新一组留言的状态，状态（0：未审核；1：审核） 
	 */
	public void updateFeedbackStatus(FeedbackCollection collection, int[] ids, int status) {
		collection.updateFeedbackStatus(ids, status);
	}
	
	/** 
	 * 删除指定的留言。 
	 */
	public int deleteFeedbacks(FeedbackCollection collection, int[] ids) {
		int num = 0;
		for (int i = 0; i < ids.length; i++) {
			num += collection.deleteFeedback(ids[i]);
		}
		return num;
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jpub.TransactionProxy#batchDeleteLogs(cn.edustar.jpub.model.LogCollection, java.util.List)
	 */
	public void batchDeleteLogs(LogCollection log_coll, List<Integer> ids) {
		log_coll.batchDeleteLogs(ids);
	}
	
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jpub.TransactionProxy#clearLogs(cn.edustar.jpub.model.LogCollection)
	 */
	public void clearLogs(LogCollection log_coll) {
		log_coll.clearLogs();
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jpub.TransactionProxy#saveWebPage(cn.edustar.jpub.model.WebPageCollection, cn.edustar.jpub.model.WebPage)
	 */
	public void saveWebPage(WebPageCollection webpage_coll, WebPage web_page) {
		webpage_coll.save(web_page);
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jpub.TransactionProxy#createExtendProperty(cn.edustar.jpub.model.ExtendPropertySet, cn.edustar.jpub.model.ExtendProperty)
	 */
	public void createExtendProperty(ExtendPropertySet prop_set, ExtendProperty prop) {
		prop_set.saveProperty(prop);
	}
	
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jpub.TransactionProxy#deleteExtendProperty(cn.edustar.jpub.model.ExtendPropertySet, java.lang.String)
	 */
	public void deleteExtendProperty(ExtendPropertySet prop_set, String prop_name) {
		prop_set.deleteProperty(prop_name);
	}

	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jpub.TransactionProxy#deleteAllExtendProperty(cn.edustar.jpub.model.ExtendPropertySet)
	 */
	public void deleteAllExtendProperty(ExtendPropertySet prop_set) {
		prop_set.deleteAllProperty();
	}
	
	// === 布局 =======================================================================
	
	/*
	 * (non-Javadoc)
	 * @see cn.edustar.jpub.TransactionProxy#saveLayout(cn.edustar.jpub.model.LayoutCollection, cn.edustar.jpub.pjo.Layout)
	 */
	public void saveLayout(LayoutCollection layout_coll, Layout layout) {
		layout_coll.saveLayout(layout);
	}
}
