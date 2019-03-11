package com.farm.core.page;

import org.apache.log4j.Logger;

/**页面状态对象的工具类
 *
 */
public class PageSets {
	static final Logger log = Logger.getLogger(PageSets.class);

	/**
	 * 设置一个PageSet 而且PageSet可以为空
	 * 
	 * @param pageSet
	 *            页面状态对象
	 * @param pageType
	 *            页面类型
	 * @param commitType
	 *            提交状态
	 * @param e
	 *            异常
	 * @return pageSet 页面状态对象
	 */
	public static PageSet initPageSet(PageSet pageSet, PageType pageType,
			CommitType commitType, Exception e) {
		if (pageSet == null) {
			pageSet = new PageSet(PageType.OTHER, CommitType.NONE);
		}
		if (e != null) {
			pageSet.setMessage(e.getMessage());
			e.printStackTrace();
			log.error(pageSet.getMessage());
		}
		if (pageType != null) {
			pageSet.setPageType(pageType.value);
		}
		if (commitType != null) {
			pageSet.setCommitType(commitType.value);
		}
		return pageSet;
	}

	/**
	 * 设置一个PageSet 而且PageSet可以为空
	 * 
	 * @param pageSet
	 *            页面状态对象
	 * @param commitType
	 *            提交状态
	 * @return pageSet 页面状态对象
	 */
	public static PageSet initPageSet(PageSet pageSet, CommitType commitType) {
		pageSet = initPageSet(pageSet, null, commitType, null);
		return pageSet;
	}
	/**
	 * 设置一个PageSet 而且PageSet可以为空
	 * 
	 * @param pageSet
	 *            页面状态对象
	 * @param commitType
	 *            提交状态
	 * @return pageSet 页面状态对象
	 */
	public static PageSet initPageSet(PageSet pageSet, CommitType commitType,Exception e) {
		pageSet = initPageSet(pageSet, null, commitType, e);
		return pageSet;
	}
}
