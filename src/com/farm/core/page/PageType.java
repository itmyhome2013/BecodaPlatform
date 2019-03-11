package com.farm.core.page;

/**
 * 页面类型
 * 
 * 
 */
public enum PageType {
	/**
	 * 展示
	 */
	SHOW(0), /**
	 * 新增
	 */
	ADD(1), /**
	 * 修改
	 */
	UPDATE(2),
	/**
	 * 删除
	 */
	DEL(4),
	/**
	 * 其他
	 */
	OTHER(3);
	public int value;

	private PageType(int var) {
		value = var;
	}

	/**根据数值获得页面类型枚举对象
	 * @param type
	 * @return
	 */
	public static PageType getEnum(int type) {
		if (type == 0)
			return PageType.SHOW;
		if (type == 1)
			return PageType.ADD;
		if (type == 2)
			return PageType.UPDATE;
		return null;
	}
}
