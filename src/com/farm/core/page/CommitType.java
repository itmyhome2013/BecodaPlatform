package com.farm.core.page;

/**
 * 提交状态，用来标志操作是否成功，多用于页面对后台操作的判断
 * 
 */
public enum CommitType {
	/**
	 * 成功
	 */
	TRUE(0), /**
				 * 失败
				 */
	FALSE(1), /**
				 * 无提交
				 */
	NONE(2);
	public int value;

	private CommitType(int var) {
		value = var;
	}
}
