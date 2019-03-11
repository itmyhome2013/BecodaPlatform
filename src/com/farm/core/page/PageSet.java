package com.farm.core.page;

/**
 * 封装一次页面请求的状态
 * 
 */
public class PageSet implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 页面类型
	 */
	private int pageType;
	/**
	 * 提交状态
	 */
	private int commitType;
	/**
	 * 跳转类型>0 下一个 <0上一个==0当前
	 */
	private int gotoType;
	/**
	 * 消息
	 */
	private String message = "";
	/**
	 * 记录集id集合
	 */
	private String ids;
	/**
	 * 当前位置
	 */
	private int currentEntity;
	/**
	 * 全部数量
	 */
	private int allEntity;
	/**
	 * 当前实体id
	 */
	private String currentKeyid;

	/**
	 * 当前主键
	 * 
	 * @return
	 */
	public String getCurrentKeyid() {
		return currentKeyid;
	}

	/**获得当前业务id
	 * @param currentKeyid
	 */
	public void setCurrentKeyid(String currentKeyid) {
		this.currentKeyid = currentKeyid;
	}

	public PageSet() {

	}

	/**设置错误信息
	 * @param pageset 请求对象
	 * @param e 异常对象
	 * @param message 消息内容
	 * @return
	 */
	public static PageSet setError(PageSet pageset, Exception e, String message) {
		if (pageset == null) {
			pageset = new PageSet(PageType.OTHER, CommitType.FALSE, e
					.toString()
					+ e.getMessage());
		} else {
			pageset.setCommitType(CommitType.FALSE.value);
		}
		return pageset;
	}

	/**构造函数
	 * @param pageType 页面类型对象
	 * @param commitType 提交状态对象
	 * @param message 消息内容
	 */
	public PageSet(PageType pageType, CommitType commitType, String message) {
		this.pageType = pageType.value;
		this.commitType = commitType.value;
		this.message = message;
	}

	/**构造函数
	 * @param pageType 页面类型对象
	 * @param commitType 提交状态对象
	 */
	public PageSet(PageType pageType, CommitType commitType) {
		this.pageType = pageType.value;
		this.commitType = commitType.value;
	}

	/**设置状态
	 * @param pageType 页面类型对象
	 * @param commitType 提交状态对象
	 */
	public void SetVar(PageType pageType, CommitType commitType) {
		if (pageType != null)
			this.pageType = pageType.value;
		if (commitType != null)
			this.commitType = commitType.value;
	}

	/**设置状态
	 * @param pageType 页面类型对象
	 * @param commitType 提交状态对象
	 * @param message 消息内容
	 */
	public void SetVar(PageType pageType, CommitType commitType, String message) {
		if (pageType != null)
			this.pageType = pageType.value;
		if (commitType != null)
			this.commitType = commitType.value;
		if (message != null)
			this.message = message;
	}

	/**获得消息
	 * @return
	 */
	public String getMessage() {
		return message;
	}

	/**设置消息内容
	 * @param message
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**获得页面类型对象
	 * @return
	 */
	public int getPageType() {
		return pageType;
	}

	/**设置页面类型对象
	 * @param pageType 页面类型对象
	 */
	public void setPageType(int pageType) {
		this.pageType = pageType;
	}

	/**获得提交状态对象
	 * @return
	 */
	public int getCommitType() {
		return commitType;
	}

	/**设置提交状态对象
	 * @param commitType
	 */
	public void setCommitType(int commitType) {
		this.commitType = commitType;
	}

	/**获得id集合
	 * @return
	 */
	public String getIds() {
		return ids;
	}

	/**设置id集合
	 * @param ids
	 */
	public void setIds(String ids) {
		this.ids = ids;
	}

	// --------------------------------------------------------------------------------------------
	

	public int getGotoType() {
		return gotoType;
	}

	public void setGotoType(int gotoType) {
		this.gotoType = gotoType;
	}

	public int getCurrentEntity() {
		return currentEntity;
	}

	public void setCurrentEntity(int currentEntity) {
		this.currentEntity = currentEntity;
	}

	public int getAllEntity() {
		return allEntity;
	}

	public void setAllEntity(int allEntity) {
		this.allEntity = allEntity;
	}

}
