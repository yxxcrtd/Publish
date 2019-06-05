package com.chinaedustar.publish.model;

import java.sql.SQLException;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

/**
 * 表示一个频道的上载文件集合对象。
 * 
 * @author 
 */
@SuppressWarnings("rawtypes")
public class UpFileCollection extends AbstractCollWithContainer {
	/**
	 * 获取一个上传文件对象。
	 * @param fileId 文件的表示。
	 * @return
	 */
	public UpFile loadUpFile(int fileId) {
		return (UpFile)super._getPublishContext().getDao().get(UpFile.class, fileId);
	}
	
	/**
	 * 通过上传文件的路径得到文件对象。
	 * @param filePath 文件的路径，如：/PubWeb/news/UploadFiles/2007/03/20070313102030123.txt 。
	 * @return
	 */
	public UpFile loadUpFile(final String filePath) {
		return (UpFile)super._getPublishContext().getDao().getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				String hql = "from UpFile where filePath=:filePath";
				Query query = session.createQuery(hql);
				query.setString("filePath", filePath);
				List list = query.list();
				if (list != null && list.size() > 0) {
					return list.get(0);
				} else {
					return null;
				}
			}
			
		});
	}
	/**
	 * 通过上传文件的目录得到文件List
	 * @param dirPath 文件的目录，如：/PubWeb/news/UploadFiles/2007/03/
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public java.util.List<UpFile> loadFiles(final String dirPath){
		return (List<UpFile>)super._getPublishContext().getDao().getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException,SQLException{
				String hql="from UpFile where filePath Like:dirPath";
				Query query=session.createQuery(hql);
				query.setString("dirPath", dirPath + "%");
				List list=query.list();
				if(list!=null && list.size()>0)
					return list;
				else
					return null;
			}
		});
	}


	/**
	 * 保存一个上传文件对象。
	 * 注意：需要事务支持。
	 * @param upFile
	 */
	public void insertUpFile(UpFile upFile) {
		super._getPublishContext().getDao().save(upFile);
	}
	
	
	/**
	 * 删除指定的文件对象。
	 * 注意：需要事务支持。
	 * @param fileId 文件对象的标识。
	 */
	public void delete(final int fileId) {
		super._getPublishContext().getDao().getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				String hql = "delete from UpFile where id=:id";
				Query query = session.createQuery(hql);
				query.setInteger("id", fileId);
				return query.executeUpdate();
			}
		});
	}
	
	/**
	 * 删除指定的上传文件对象。
	 * 注意：需要事务支持。
	 * @param filePath 文件的路径，如：/PubWeb/news/UploadFiles/2007/03/20070313102030123.txt 。
	 */
	public void delete(final String filePath) {
		super._getPublishContext().getDao().getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				String hql = "delete from UpFile where filePath=:filePath";
				Query query = session.createQuery(hql);
				query.setString("filePath", filePath);
				return query.executeUpdate();
			}
		});
	}
}
