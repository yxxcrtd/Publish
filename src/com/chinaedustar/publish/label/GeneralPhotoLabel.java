package com.chinaedustar.publish.label;

import java.sql.SQLException;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.chinaedustar.common.util.StringHelper;
import com.chinaedustar.publish.PublishContext;
import com.chinaedustar.publish.model.*;
import com.chinaedustar.template.core.AbstractLabelElement;
import com.chinaedustar.template.core.AttributeCollection;
import com.chinaedustar.template.core.InternalProcessEnvironment;
import com.chinaedustar.template.core.LocalContext;

/**
 * 图片项目相关的标签。(Photo Object)
 * @author liujunxing
 *
 */
public class GeneralPhotoLabel extends GroupLabelBase {
	/** 不需要实例化。 */
	private GeneralPhotoLabel() {
	}
	
	/**
	 * 注册 LabelHandler.
	 *
	 */
	public static final void registerHandler(LabelHandlerMap map) {
		// logger.info("GeneralPhotoLabel 注册了其包含的一组标签解释器。");
		
		map.put("PhotoID", new PhotoPropertyHandler());
		map.put("PhotoTitle", new PhotoPropertyHandler());
		map.put("PhotoDayHits", new PhotoPropertyHandler());
		map.put("PhotoWeekHits", new PhotoPropertyHandler());
		map.put("PhotoMonthHits", new PhotoPropertyHandler());
		map.put("PhotoThumb", new PhotoPropertyHandler());
		
		map.put("PhotoUrl", new PhotoPropertyHandler());	// 带有计算
		
		map.put("ViewPhoto", new ViewPhotoLabelHandler());
		map.put("PhotoUrlList", new PhotoUrlListLabelHandler());
		map.put("PhotoThumbList", new PhotoThumbListLabelHandler());
		map.put("CorrelativePhoto", new CorrelativePhotoLabelHandler());
		map.put("PhotoSlide", new PhotoSlideLabelHandler());
		
		map.put("PrevPhoto", new PrevPhotoLabelHandler());
		map.put("NextPhoto", new NextPhotoLabelHandler());
		
		// TODO: 改版
		// map.put("ShowPicPhotoList", new ShowPicPhotoListHandler());
	}
	
	/**
	 * Photo 图片对象基本属性标签。
	 * @label #{PhotoID} - 显示当前图片标识。
	 * @label #{PhotoTitle} - 图片的标题。
	 * @label #{PhotoDayHits} - 显示图片日点击数。
	 * @label #{PhotoWeekHits} - 显示图片周点击数。
	 * @label #{PhotoMonthHits} - 显示图片月点击数。
	 * @label #{PhotoThumb} - 获得图片缩微图地址。
	 *
	 * 
	 * @label #{PhotoUrl} - 图片列表地址中的第一个地址
	 * 
	 * @author liujunxing
	 *
	 */
	static final class PhotoPropertyHandler extends AbstractSimpleLabelHandler {
		/*
		 * (non-Javadoc)
		 * @see com.chinaedustar.publish.itfc.LabelHandler#handleLabel(com.chinaedustar.publish.PublishContext, com.chinaedustar.template.core.InternalProcessEnvironment, com.chinaedustar.template.core.AbstractLabelElement)
		 */
		public int handleLabel(PublishContext pub_ctxt, InternalProcessEnvironment env, AbstractLabelElement label) {
			String label_name = label.getLabelName();
			Photo photo = AbstractLabelHandler.getCurrentPhoto(env);
			String result = "";
			
			if (photo == null) 
				result = "#{?? " + label_name + " 没有当前图片对象}";
			else if ("PhotoID".equals(label_name))
				result = String.valueOf(photo.getId());
			else if ("PhotoTitle".equals(label_name))
				result = StringHelper.htmlEncode(photo.getTitle());
			else if ("PhotoDayHits".equals(label_name))
				result = String.valueOf(photo.getDayHits());
			else if ("PhotoWeekHits".equals(label_name))
				result = String.valueOf(photo.getWeekHits());
			else if ("PhotoMonthHits".equals(label_name))
				result = String.valueOf(photo.getMonthHits());
			else if ("PhotoThumb".equals(label_name))
				result = pub_ctxt.getSite().resolveUrl(photo.getThumbPic());
			else if ("PhotoUrl".equals(label_name))
				result = photo_url(photo);
				
			env.getOut().write(result);
			return PROCESS_DEFAULT;
		}
		
		// #{PhotoUrl}
		private String photo_url(Photo photo) {
			String urls = photo.getPictureUrls();
			if (urls == null || urls.trim().length() < 0) {
				return "#{?? PhotoUrlList 图片地址列表为空}";
			} else {
				String[] urlList = urls.split("\\$\\$\\$");
				
				if (urlList.length > 0) {
					String[] array = urlList[0].split("\\|");
					if (array.length > 1 && array[1] != null) {
						return array[1].trim();
					}
				}
			}
			return "";
		}
	}
	
	/**
	* 显示上一组图片的地址的一个按钮。
	* #{PrevPhoto}
	* <br>比如:
	* <input type="button" disabled='disabled' value="上一组图片" onclick="window.location='/PubWeb/photo/showPhoto.jsp?columnId=57&photoId=56'"/>
	* <br>若没有上一组图片，则按钮变灰。
	*/
	static final class PrevPhotoLabelHandler extends AbstractLabelHandler {
		/*
		 * (non-Javadoc)
		 * @see com.chinaedustar.publish.label.AbstractLabelHandler#handleLabel()
		 */
		@Override public int handleLabel() {
			Photo photo = getCurrentPhoto();
			if (photo == null) {
				out("#{?? PrevPhoto 没有当前图片}");
				return PROCESS_DEFAULT;
			}
			
			// 得到前一张图片。
			Photo prev_photo = photo.getPrevPhoto();
			if (label.hasChild() == false) {
				if (prev_photo != null)
					out("<input type='button' value='上一组图片' onclick=\"window.location='" 
							+ prev_photo.calcPageUrl() + "'\" />");
				else
					out("<input type='button' value='上一组图片' disabled='disabled' />");
			} else {
				// 给子节点提供数据为 photo, 让子节点自己负责显示。
				LocalContext local_ctxt = env.acquireLocalContext(label, 0);
				local_ctxt.setVariable("photo", prev_photo);
				local_ctxt.setVariable("item", prev_photo);
			}
			return PROCESS_DEFAULT;
		}
	}
	
	/**
	 * 显示下一组图片的地址的一个按钮。
	 * #{NextPhoto}
	 * <br>比如:
	 * <input type="button" value="下一组图片" disabled='disabled' onclick="window.location='/PubWeb/photo/showPhoto.jsp?columnId=57&photoId=56'"/>
	 * <br>若没有下一组图片，则按钮变灰。
	 */
	static final class NextPhotoLabelHandler extends AbstractLabelHandler {
		/*
		 * (non-Javadoc)
		 * @see com.chinaedustar.publish.label.AbstractLabelHandler#handleLabel()
		 */
		@Override public int handleLabel() {
			Photo photo = getCurrentPhoto();
			if (photo == null) {
				out("#{?? NextPhoto 没有当前图片}");
			}

			// 得到后一张图片。
			Photo next_photo = photo.getNextPhoto();
			if (label.hasChild() == false) {
				if (next_photo != null)
					out("<input type='button' value='下一组图片' onclick=\"window.location='" 
							+ next_photo.calcPageUrl() + "'\" />");
				else
					out("<input type='button' value='下一组图片' disabled='disabled' />");
			} else {
				// 给子节点提供数据为 photo, 让子节点自己负责显示。
				LocalContext local_ctxt = env.acquireLocalContext(label, 0);
				local_ctxt.setVariable("photo", next_photo);
				local_ctxt.setVariable("item", next_photo);
			}
			return PROCESS_DEFAULT;
		}
	}
	
	/**
	 * 显示图片查看框。
	 * #{ViewPhoto}
	 * <br>注意，此标签需要配合 PhotoUrlList 标签使用，且必须是PhotoUrlList标签位于此标签的前面。
	 * @param width 图片框的宽度。默认为600。
	 */
	static final class ViewPhotoLabelHandler extends AbstractLabelHandler {
		/*
		 * (non-Javadoc)
		 * @see com.chinaedustar.publish.label.AbstractLabelHandler#handleLabel()
		 */
		@Override public int handleLabel() {
			Photo photo = getCurrentPhoto();
			if (photo == null) {
				out("#{?? ViewPhoto 没有当前图片}");
				return PROCESS_DEFAULT;
			} 
			
			int width = label.getAttributes().safeGetIntAttribute("width", 600);
			String result = "<a target=\"_blank\" id=\"_PhotoLink\"><img id=\"_PhotoImg\" border=\"0\" width=\""
				+ width + "\"/></a>\n";
			result += "<script type=\"text/javascript\" >";
			result += "	addBodyOnloadEvent(function(){\n";
			result += "		PhotoUtil.init('_PhotoLink','_PhotoImg');\n";
			result += "		PhotoUtil.setPhotoWidth(" + width + ");\n";
			result += "		PhotoUtil.showFirstPicture();\n";
			result += "	});\n";
			result += "</script>\n";
			out(result);
			
			return PROCESS_DEFAULT;
		}
	}
	
	/**
	 * 显示图片地址列表，可自定义。
	 * #{PhotoUrlList} 
	 *  <p>* 自定义标签会读出图片列表的各个地址，名称为url。比如：<br>
	 * #{PhotoUrlList}&lt;img src="#{url}" /&gt;#{/PhotoUrlList}<br>
	 * 这就会将图片的地址对应的图片显示出来。
	 * </p>
	 * <p>默认标签的输出结果为：<br>
	 * <pre>
	 * &lt;script type='text/javascript' src='/PubWeb/js/photoShow.js' &gt;&lt;/script&gt;
	 * < script type="text/javascript">
	 * addBodyOnloadEvent(function(){
	 * PhotoUtil.addPhoto('/PubWeb/photo/UploadDirs/2007/06/20070629041359640.jpg', '/PubWeb/photo/UploadDirs/2007/06/20070629041359640.jpg');
	 * PhotoUtil.addPhoto('/PubWeb/photo/UploadDirs/2007/05/20070521040055875.jpg', '/PubWeb/photo/UploadDirs/2007/05/20070521040055875.jpg');
	 * PhotoUtil.addPhoto('/PubWeb/photo/UploadDirs/2007/07/20070702041728681.jpg', '/PubWeb/photo/UploadDirs/2007/07/20070702041728681.jpg');
	 * });
	 * </script>
	 * </pre>
	 * </p>
	 */
	static final class PhotoUrlListLabelHandler extends AbstractLabelHandler {
		/*
		 * (non-Javadoc)
		 * @see com.chinaedustar.publish.label.AbstractLabelHandler#handleLabel()
		 */
		@Override public int handleLabel() {
			// 获得当前图片对象。
			Photo photo = getCurrentPhoto();
			if (photo == null) {
				out("#{?? PhotoUrlList 没有当前图片}");
				return PROCESS_DEFAULT;
			} 
			
			// 获得该图片的所有图片集合。
			String urls = photo.getPictureUrls();
			if (urls == null || urls.trim().length() < 0) {
				out("#{?? PhotoUrlList 图片地址列表为空}");
				return PROCESS_DEFAULT;
			} 
			
			String[] urlList = urls.split("\\$\\$\\$");
			java.util.List<String> list = new java.util.ArrayList<String>();
			for (int i = 0; i < urlList.length; i++) {
				String[] array = urlList[i].split("\\|");
				if (array.length > 1) {
					list.add(array[1]);
				}
			}
			if (label.hasChild()) {
				env.foreach(label, "url", list, RepeatNameSetter.Instance);
				return PROCESS_SIBLING;
			} else {
				Site site = pub_ctxt.getSite();
				StringBuilder sb_result = new StringBuilder();
				sb_result.append("<script type='text/javascript' src='" + pub_ctxt.getSite().getInstallDir() + "js/photoUtil.js'></script>\n");
				sb_result.append("<script type='text/javascript'>\n");
				sb_result.append("addBodyOnloadEvent( function(){ \n");
				
				for (int i = 0; i < list.size(); i++) {
					String url = site.resolveUrl(list.get(i));
					sb_result.append("PhotoUtil.addPhoto('" + url + "', '" + url + "');\n");
				}
				sb_result.append("});\n");
				sb_result.append("</script>\n");
				out(sb_result.toString());
			}

			return PROCESS_DEFAULT;
		}
	}
	
	/**
	 * 显示图片的缩略图列表。
	 * #{PhotoThumbList width='130' num='5'}
	 * <br>注意，此标签需要配合PhotoUrlList标签使用，且必须是PhotoUrlList标签位于此标签的前面。
	 * @param width 缩略图的宽度。默认为130。
	 * @param num 每页最多显示多少个缩略图。默认为5。
	 */
	static final class PhotoThumbListLabelHandler extends AbstractLabelHandler {
		@Override
		public int handleLabel() {
			Photo photo = getCurrentPhoto();
			if (photo == null) {
				out("#{??PhotoThumbList 没有当前图片}");
			} else {
				AttributeCollection coll = label.getAttributes();
				int width = coll.safeGetIntAttribute("width", 130);
				if (width <= 0) {
					width = 130;
				}
				int num = coll.safeGetIntAttribute("num", 5);
				if (num <= 0) {
					num = 5;
				}
				String result = "<div id=\"_ThumbList\" align=\"center\"></div>";
				result += "<script type=\"text/javascript\" >";
				result += "	addBodyOnloadEvent(function(){\n";
				result += "		PhotoUtil.setThumbPhotoWidth(" + width + ");\n";
				result += "		PhotoUtil.showThumbList('_ThumbList', " + num + ");\n";
				result += "	});\n";
				result += "</script>\n";
				out(result);
			}
			return PROCESS_DEFAULT;
		}
	}
	
	/**
	 * 显示用来控制图片幻灯放映的按钮和文本框。
	 * #{PhotoSlide time='5' startText='开始播放' stopText='结束播放' auto='false'} 
	 * <br>
	 * <br>默认结果为：
	 * @param time 每隔多少秒变换图片。默认为5秒。
	 * @param startText 开始幻灯显示时按钮上的文字。默认为“幻灯放映”。
	 * @param stopText 结束幻灯显示时按钮上的文字。默认为“结束播放”。
	 * @param auto 是否自动放映。默认为false。
	 */
	static final class PhotoSlideLabelHandler extends AbstractLabelHandler {
		@Override public int handleLabel() {
			Photo photo = getCurrentPhoto();
			if (photo == null) {
				out("#{??PhotoSlide 没有当前图片}");
			} else {
				AttributeCollection coll = label.getAttributes();
				int time = coll.safeGetIntAttribute("time", 5);
				String startText = coll.safeGetStringAttribute("startText", "幻灯放映");
				String stopText = coll.safeGetStringAttribute("stopText", "结束播放");
				Boolean auto = coll.safeGetBoolAttribute("auto", false);
				
				String result = "<input type=\"button\" value=\"" + (auto ? stopText : startText) + "\" onclick=\"doSlide(this);\"/>\n";
				result += "<input type=\"text\" size=\"2\" value=\"" 
					+ time + "\" id=\"_SlideTime\" style=\"text-align:center\" onfocus=\"if(this.value != '')this.select();\"/>\n";
				result += "<script type=\"text/javascript\" >";
				result += "function doSlide(button) {\n";
				result += "	if (button.value == \"" + startText + "\") {\n";
				result += "		PhotoUtil.startSlide(document.getElementById(\"_SlideTime\").value);\n";
				result += "		button.value = \"" + stopText + "\";\n";
				result += "	} else {\n";
				result += "		PhotoUtil.stopSlide();\n";
				result += "		button.value = \"" + startText + "\";\n";
				result += "	}\n";
				result += "}\n";
				
				if (auto) {
					result += "	addBodyOnloadEvent(function(){\n";
					result += "		PhotoUtil.startSlide(" + time + ");\n";
					result += "	});\n";
				}
				
				result += "</script>\n";
				out(result);
			}
			
			return PROCESS_DEFAULT;
		}
	}
	
	/**
	 * 显示相关图片列表
	 * #{CorrelativePhoto photoCount=5 titleNum=0}
	 */
	static class CorrelativePhotoLabelHandler extends AbstractLabelHandler {
		@SuppressWarnings("rawtypes")
		@Override public int handleLabel() {
			//获取参数
			AttributeCollection coll = label.getAttributes();
			final int photoCount = coll.safeGetIntAttribute("photoCount", 5);
			int titleNum = coll.safeGetIntAttribute("titleNum", 0); //0为完整标题。
			//显示相关软件。
			final Photo photo = getCurrentPhoto();
			int id= photo.getId();
			@SuppressWarnings("unchecked")
			List <Photo> list = (List)pub_ctxt.getDao().getHibernateTemplate().execute(new HibernateCallback(){
				public Object doInHibernate(Session session)throws HibernateException, SQLException{
					String photokey = photo.getKeywords();
					String hql="from Photo as a where a.status=1 and a.deleted=0 and a.keywords like '%" +photokey+"%' order by a.createTime desc";
					Query query = session.createQuery(hql);
					query.setFirstResult(0);
					query.setMaxResults(photoCount);
					return query.list();
				}
			});
			String outstring = "";
			if(list.size() > 0){
				for(int i =0;i<list.size();i++){
					if(id != list.get(i).getId()){
						String title="";
						if(titleNum >0 && titleNum < list.get(i).getTitle().length()){
							title = list.get(i).getTitle().substring(0,titleNum);
						}else {
							title = list.get(i).getTitle();
						}
						
						outstring = outstring +"<a href = \"" + list.get(i).getPageUrl() +"\"> <li>"+ title +"</li></a><br/>";
					}
				}
			}else{
				outstring = "没有相关图片！";
			}
			out(outstring);
			return PROCESS_DEFAULT;
		}
	}
}
