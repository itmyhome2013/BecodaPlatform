package com.farm.core.sql.query;

/**
 * 查询工具工具类
 * 
 */
public class DataQuerys {
	/**
	 * 检查SQL注入风险
	 * 
	 * @param var
	 *            拼写SQL的值,仅仅用于对值的处理
	 */
	public static void wipeVirus(String var) {
		if (var.indexOf("(") + var.indexOf(")") + var.indexOf("||") > 0) {
			throw new RuntimeException("违反SQL注入风险约束！");
		}
	}
}
