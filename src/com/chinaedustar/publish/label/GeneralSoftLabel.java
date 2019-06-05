package com.chinaedustar.publish.label;

import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.chinaedustar.common.util.StringHelper;
import com.chinaedustar.publish.PublishContext;
import com.chinaedustar.publish.model.*;
import com.chinaedustar.template.core.AbstractLabelElement;
import com.chinaedustar.template.core.InternalProcessEnvironment;
import com.chinaedustar.template.core.LocalContext;

/**
 * 一般 Soft 对象标签解释器集合。
 * @author liujunxing
 *
 */
public final class GeneralSoftLabel extends GroupLabelBase {
	/** 不需要实例化。 */
	private GeneralSoftLabel() {
	}
	
	/**
	 * 注册 LabelHandler.
	 *
	 */
	public static final void registerHandler(LabelHandlerMap map) {
		// logger.info("GeneralSoftLabel 注册了其包含的一组标签解释器。");
		
		map.put("SoftID", new SoftPropertyHandler());
		map.put("SoftTitle", new SoftPropertyHandler());
		map.put("SoftUrl", new SoftPropertyHandler());
		map.put("SoftVersion", new SoftPropertyHandler());
		map.put("SoftSize", new SoftPropertyHandler());
		map.put("SoftSize_M", new SoftPropertyHandler());
		map.put("SoftSize_K", new SoftPropertyHandler());
		map.put("DecompressPassword", new SoftPropertyHandler());
		map.put("OperatingSystem", new SoftPropertyHandler());
		map.put("SoftDayHits", new SoftPropertyHandler());
		map.put("SoftWeekHits", new SoftPropertyHandler());
		map.put("SoftMonthHits", new SoftPropertyHandler());
		map.put("SoftLink", new SoftPropertyHandler());
		map.put("SoftType", new SoftPropertyHandler());
		map.put("SoftLanguage", new SoftPropertyHandler());
		map.put("SoftProperty", new SoftPropertyHandler());
		map.put("DemoUrl", new SoftPropertyHandler());
		map.put("RegUrl", new SoftPropertyHandler());
		map.put("CopyrightType", new SoftPropertyHandler());
		
		map.put("SoftPicUrl", new SoftPicUrlLabelHandler());
		map.put("DownloadUrl", new DownloadUrlLabelHandler());
		map.put("PrevSoft", new PrevSoftHandler());
		map.put("NextSoft", new NextSoftHandler());
		map.put("CorrelativeSoft", new CorrelativeSoftLabelHandler());
	}
	
	/**
	 * 通用 Soft 属性处理器。
	 * @label #SoftID - 软件标识。
	 * @label #SoftVersion - 软件版本。
	 * @label #SoftSize - 软件大小。
	 * @label #SoftSize_K - 显示以 K 为单位的软件大小。
	 * @label #SoftSize_M - 显示以 M 为单位的软件大小。
	 * @label #DecompressPassword - 显示解压缩密码。
	 * @label #OperatingSystem - 显示软件的操作系统。
	 * @label #SoftDayHits - 软件日下载数。
	 * @lbael #SoftWeekHits - 周下载数。
	 * @label #SoftMonthHits - 月下载数。
	 * @label #SoftLink - 显示“演示”和“注册”地址链接。
	 * @label #SoftType - 软件类型。
	 * @label #SoftLanguage - 软件语言。
	 * @label #SoftProperty - 显示下载属性（推荐、精华等）
	 * @label #DemoUrl - 演示地址。
	 * @label #RegUrl - 注册地址。
	 * @label #CopyrightType - 版权类型。
	 * @author liujunxing
	 *
	 */
	private static final class SoftPropertyHandler extends AbstractSimpleLabelHandler {
		/*
		 * (non-Javadoc)
		 * @see com.chinaedustar.publish.itfc.LabelHandler#handleLabel(com.chinaedustar.publish.PublishContext, com.chinaedustar.template.core.InternalProcessEnvironment, com.chinaedustar.template.core.AbstractLabelElement)
		 */
		public int handleLabel(PublishContext pub_ctxt, InternalProcessEnvironment env, AbstractLabelElement label) {
			String label_name = label.getLabelName();
			Soft soft = AbstractLabelHandler.getCurrentSoft(env);
			String result = "";
			
			if (soft == null)
				result = "#{?? " + label_name + " 没有找到当前软件对象}";
			else if ("SoftID".equals(label_name))
				result = String.valueOf(soft.getId());
			else if ("SoftTitle".equals(label_name))
				result = StringHelper.htmlEncode(soft.getTitle());
			else if ("SoftUrl".equals(label_name))
				result = soft.getPageUrl();
			else if ("SoftVersion".equals(label_name))
				result = soft.getVersion();
			else if ("SoftSize".equals(label_name))
				result = String.valueOf(soft.getSize());
			else if ("SoftSize_K".equals(label_name))
				result = softSizeK(soft);
			else if ("SoftSize_M".equals(label_name))
				result = softSizeM(soft);
			else if ("DecompressPassword".equals(label_name))
				result = soft.getDecompPwd();
			else if ("OperatingSystem".equals(label_name))
				result = soft.getOS();
			else if ("SoftDayHits".equals(label_name))
				result = String.valueOf(soft.getDayHits());
			else if ("SoftWeekHits".equals(label_name))
				result = String.valueOf(soft.getWeekHits());
			else if ("SoftMonthHits".equals(label_name))
				result = String.valueOf(soft.getMonthHits());
			else if ("SoftLink".equals(label_name))
				result = softLink(soft);
			else if ("SoftType".equals(label_name))
				result = soft.getType();
			else if ("SoftLanguage".equals(label_name))
				result = soft.getLanguage();
			else if ("SoftProperty".equals(label_name))
				result = softProperty(soft);
			else if ("DemoUrl".equals(label_name))
				result = soft.getDemoUrl();
			else if ("RegUrl".equals(label_name))
				result = soft.getRegistUrl();
			else if ("CopyrightType".equals(label_name))
				result = soft.getCopyrightType();
			
			if (result == null) result = "";
			env.getOut().write(result);
			return PROCESS_DEFAULT;
		}
		
		private static final int K = 1024;
		private static final int M = 1024*1024;
		private String softSizeK(Soft soft) {
			NumberFormat nf = NumberFormat.getInstance();
			nf.setMaximumFractionDigits(0);
			return nf.format(soft.getSize()/K);
		}
		private String softSizeM(Soft soft) {
			NumberFormat nf = NumberFormat.getInstance();
			nf.setMaximumFractionDigits(2);
			return nf.format(soft.getSize()/M);
		}
		private String softLink(Soft soft) {
			return "<a href=" + (soft.getDemoUrl()== null || soft.getDemoUrl().trim().length() < 1 ? "#" : soft.getDemoUrl()) +
				">演示地址</a>&nbsp;&nbsp;&nbsp;" + 
				"<a href=" + (soft.getRegistUrl() == null || soft.getRegistUrl().trim().length() < 1 ? "#" : soft.getRegistUrl()) +
				">注册地址</a>";
		}
		private String softProperty(Soft soft) {
			String str = "";
			if (soft.getTop()) str += "顶 ";
			if (soft.getCommend()) str += "荐 ";
			if (soft.getElite()) str += "精 ";
			if (soft.getHot()) str += "热 ";
			return str;
		}
	}
	
	/**
	 * 显示下载图片地址
	 * #{SoftPicUrl}
	 */
	static final class SoftPicUrlLabelHandler extends AbstractLabelHandler{
		@Override public int handleLabel(){
			Soft soft = getCurrentSoft();
			if (soft != null){
				out(soft.getThumbPic()+ "");
			}else{
				out("#(SoftPicUrl)");
			}
			return PROCESS_DEFAULT;
		}
	}
	
	
	
	
	/**
	 * 显示软件的下载地址.
	 * #{DownloadUrl}
	 */
	static final class DownloadUrlLabelHandler extends AbstractLabelHandler{
		@Override public int handleLabel(){
			Soft soft = getCurrentSoft();
			if (soft != null){
				String str = soft.getDownloadUrls();
				String downStr = null;
				if(str != null && (!"".equals(str))){
					String[] sArray = str.split("\\$\\$\\$");					
					for(int i = 0; i < sArray.length; i++){
						String[] s = sArray[i].split("\\|");
						//downStr =downStr+ "<a href=\"showSoft.jsp?channelId="+soft.getChannelId()+"&columnId="+soft.getColumnId()+"&softId="+soft.getId()+"&url=http://"+s[1]+"\""+"><li>"+ s[0]+"</li></a><br/>";
						downStr =downStr+ "<a href="+s[1]+" target=_blank><li>"+ s[0]+"</li></a><br/>";
					}
					downStr = downStr.substring(4);
				}else{
					downStr="还没有下载地址！";
				}

				out(downStr);
			}else{
				out("#(DoenloadUrl)");
			}
			return PROCESS_DEFAULT;
		}
	}
	
	/**
	 * 显示前一软件。 
	 * #{PrevSoft } 
	 * */
	static final class PrevSoftHandler extends AbstractLabelHandler {
		@Override public int handleLabel() {
			Soft soft = getCurrentSoft();
			if (soft == null) {
				out("#{?? PrevSoft 没有当前下载}");
				return PROCESS_DEFAULT;
				
			} 
			
			Soft prevSoft = soft.getPrevSoft();
			if (label.hasChild()) {
				LocalContext local_ctxt = env.acquireLocalContext(label, 0);
				local_ctxt.setVariable("soft", prevSoft);
				local_ctxt.setVariable("item", prevSoft);
			} else {
				String outstring = "";
				if (prevSoft != null) {
					outstring = "前一个下载：" + "<a href = \"" + prevSoft.calcPageUrl()
						+"\">"+ prevSoft.getTitle()+"</a>";
				} else {
					outstring = "前一个下载：没有了！";
				}
				out(outstring);
			}
			return PROCESS_DEFAULT;
		}
	}
	
	/**
	 * 显示后一文章。 
	 * #{NextSoft } 
	 * */
	static final class NextSoftHandler extends AbstractLabelHandler {
		@Override public int handleLabel() {
			Soft soft = getCurrentSoft();
			if (soft == null) {
				out("#{?? NextSoft 没有当前下载}");
				return PROCESS_DEFAULT;
			} 
			
			Soft nextSoft = soft.getNextSoft();
			if (label.hasChild()) {
				LocalContext local_ctxt = env.acquireLocalContext(label, 0);
				local_ctxt.setVariable("soft", nextSoft);
				local_ctxt.setVariable("item", nextSoft);
			} else {
				String outstring = "";
				if (nextSoft != null) {
					outstring = "后一个下载：" +"<a href = \"" + nextSoft.calcPageUrl()
						+ "\">" + nextSoft.getTitle()+"</a>";	
				} else {
					outstring = "后一个下载：没有了！";
				}
				out(outstring);
			}
			return PROCESS_DEFAULT;
		}
	}
	

	
	/**
	 * 显示相关下载.
	 * #{CorrelativeSoft softCount=5 titleNum=0}
	 */
	static final class CorrelativeSoftLabelHandler extends AbstractLabelHandler{
		@SuppressWarnings("rawtypes")
		@Override public int handleLabel() {
			//获取参数
			String temp="";
			temp = label.getAttributes().getNamedAttribute("softCount");
			final int softCount = getIntAttribute(temp,5);
			temp = label.getAttributes().getNamedAttribute("titleNum");
			int titleNum = getIntAttribute(temp,0);//0为完整标题。
			//显示相关软件。
			final Soft soft = getCurrentSoft();
			int id= soft.getId();
			@SuppressWarnings("unchecked")
			List <Soft> list = (List)pub_ctxt.getDao().getHibernateTemplate().execute(new HibernateCallback(){
				public Object doInHibernate(Session session)throws HibernateException, SQLException{
					String softkey = soft.getKeywords();
					String hql="from Soft as a where a.deleted=0 and a.keywords like '%" +softkey+"%' order by a.createTime desc";
					Query query = session.createQuery(hql);
					query.setFirstResult(0);
					query.setMaxResults(softCount);
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
						
						outstring = outstring +"<a href = \"showSoft.jsp?softId="+ list.get(i).getId()+"\"> <li>"+ title +"</li></a><br/>";
					}
				}
			}else{
				outstring = "没有相关软件！";
			}
			out(outstring);
			return PROCESS_DEFAULT;
		}
	}
	
	
	
	/**
	 * 将字符串转化为 int 类型，如果字符串为空，返回默认值。
	 * @param str 属性的字符串。
	 * @param value 默认值。
	 * @return
	 */
	private static int getIntAttribute(String str, int value) {
		if (str == null || str.trim().length() < 1) {
			return value;
		} else {
			return Integer.parseInt(str);
		}
	}
}
