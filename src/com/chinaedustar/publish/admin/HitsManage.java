package com.chinaedustar.publish.admin;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.jsp.PageContext;

import com.chinaedustar.publish.DataAccessObject;
import com.chinaedustar.publish.ParamUtil;
import com.chinaedustar.publish.PublishUtil;
import com.chinaedustar.publish.util.DateUtil;
import com.chinaedustar.publish.util.UpdateHelper;

/**
 * 项目点击数提供。
 * 
 * @author liujunxing
 *
 */
public class HitsManage {
	private final PageContext pageContext;
	/**
	 * 构造函数。
	 * @param pageContext
	 */
	public HitsManage(PageContext pageContext) {
		this.pageContext = pageContext;
	}
	
	/**
	 * 更新文章项目点击数并返回当前点击数。
	 * @return 返回更新之前的点击数。
	 */
	public int updateArticleHitsAndGet() {
		ParamUtil param_util = new ParamUtil(pageContext);
		int itemId = param_util.safeGetIntParam("itemId");
		if (itemId == 0) return 0;
		
		return updateArticleHitsAndGet(param_util.getPublishContext().getDao(), itemId);
	}
	
	/**
	 * 更新文章项目点击数并返回当前点击数。
	 * @return 返回更新之前的点击数。
	 */
	public int updateArticleHitsAndGet(DataAccessObject dao, int itemId) {
		// ???: 将如下业务放到别的地方也许更好。
		// 1. 增加项目点击数。
		String hql = "UPDATE Item SET hits = hits + 1 WHERE id = " + itemId;
		int update_num = dao.bulkUpdate(hql);
		if (update_num == 0) return 0;	// 没有此标识的项目。
		
		// 2. 返回当前点击数。
		hql = "SELECT hits FROM Item WHERE id = " + itemId;
		int hits = PublishUtil.executeIntScalar(dao, hql);
		
		// hits - 1 得到更新前的点击数。
		return hits > 0 ? (hits - 1) : 0;
	}
	
	private static final int INDEX_HITS = 1;
	private static final int INDEX_DAY_HITS = 2;
	private static final int INDEX_WEEK_HITS = 3;
	private static final int INDEX_MONTH_HITS = 4;

	// 项目点击信息。
	public static final class ItemHitInfo {
		public Date lastHitTime;
		public int hits;
		public int dayHits;
		public int weekHits;
		public int monthHits;
		ItemHitInfo(Object[] hit_info) {
			lastHitTime = (Date)hit_info[0];
			hits = (Integer)hit_info[INDEX_HITS];
			dayHits = (Integer)hit_info[INDEX_DAY_HITS];
			weekHits = (Integer)hit_info[INDEX_WEEK_HITS];
			monthHits = (Integer)hit_info[INDEX_MONTH_HITS];
		}
	}
	
	/**
	 * 更新图片项目点击数并返回当前点击数。
	 *
	 */
	public ItemHitInfo updatePhotoHitsAndGet() {
		ParamUtil param_util = new ParamUtil(pageContext);
		int itemId = param_util.safeGetIntParam("itemId");
		if (itemId == 0) return null;
		
		return updatePhotoHitsAndGet(param_util.getPublishContext().getDao(), itemId);
	}
	
	/**
	 * 更新图片项目点击数并返回当前点击数。
	 *
	 */
	@SuppressWarnings("rawtypes")
	public ItemHitInfo updatePhotoHitsAndGet(DataAccessObject dao, int itemId) { 
		// 查询当前图片的点击数和最后点击时间。
		String hql = "SELECT lastHitTime, hits, dayHits, weekHits, monthHits " +
					" FROM Photo WHERE id = :id";
		List result = dao.queryByNamedParam(hql, new String[]{"id"}, new Object[]{itemId});
		if (result == null || result.size() == 0) return null;	// 此图片不存在。
		Object[] hit_data = (Object[])result.get(0);
		// hit_data[0] - lastHitTime, maybe null.
		// hit_data[1] - hits, INDEX_HITS
		// hit_data[2] - dayHits
		// hit_data[3] - weekHits
		// hit_data[4] - monthHits
		ItemHitInfo hit_info = new ItemHitInfo(hit_data);
		
		Date now = new Date();
		String updateClause = "UPDATE Photo SET lastHitTime = :lastHitTime, hits = hits + 1";
		++hit_info.hits;
		if (hit_info.lastHitTime == null) {
			// 如果没有得到上次点击的时间，则认为是初次点击
			updateClause += ", dayHits = dayHits + 1" +
							", weekHits = weekHits + 1" + 
							", monthHits = monthHits + 1";
			++hit_info.dayHits;
			++hit_info.weekHits;
			++hit_info.monthHits;
		} else {
			Calendar cNow = Calendar.getInstance();
			cNow.setTime(now);					// 当前时间
			Calendar cLast = Calendar.getInstance();
			cLast.get(Calendar.DAY_OF_WEEK);
			cLast.setTime(hit_info.lastHitTime);
			
			// 如果在同一天，则日计数+1，否则开始新的计数。
			boolean isSameDay = DateUtil.isSameDay(now, hit_info.lastHitTime);
			if (isSameDay) {
				updateClause += ", dayHits = dayHits + 1";
				++hit_info.dayHits;
			} else {
				updateClause += ", dayHits = 1";
				hit_info.dayHits = 1;
			}

			// 是否在同一周。
			boolean inSameWeek = false;
			int offsetYear = cNow.get(Calendar.YEAR) - cLast.get(Calendar.YEAR);
			if ((0 == offsetYear || (1 == offsetYear && 11 == cLast.get(Calendar.MONDAY)))
					&& cNow.get(Calendar.WEEK_OF_YEAR) == cLast.get(Calendar.WEEK_OF_YEAR)) {
				inSameWeek = true;
			}
			if (inSameWeek) {
				updateClause += ", weekHits = weekHits + 1";
				++hit_info.weekHits;
			} else {
				updateClause += ", weekHits = 1";
				hit_info.weekHits = 1;
			}

			// 是否在同一个月
			@SuppressWarnings("deprecation")
			boolean inSameMonth = (now.getYear() == hit_info.lastHitTime.getYear())
				&& (now.getMonth() == hit_info.lastHitTime.getMonth());
			if (inSameMonth) {
				updateClause += ", monthHits = monthHits + 1";
				++hit_info.monthHits;
			} else {
				updateClause += ", monthHits = 1";
				hit_info.monthHits = 1;
			}
		}

		// 执行更新。
		updateClause += " WHERE id = :id";
		UpdateHelper updator = new UpdateHelper(updateClause);
		updator.setDate("lastHitTime", now);
		updator.setInteger("id", itemId);
		updator.executeUpdate(dao);

		return hit_info;
	}
}
