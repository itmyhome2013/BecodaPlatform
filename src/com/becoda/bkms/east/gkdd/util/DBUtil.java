package com.becoda.bkms.east.gkdd.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.becoda.bkms.common.web.PageVO;

/**
 * 门禁出入管理数据库JDBC
 * <p>
 * Description:
 * </p>
 * 
 * @author liu_hq
 * @date 2017-9-19
 * 
 */
public class DBUtil {

	private static final String DRIVER = "com.microsoft.jdbc.sqlserver.SQLServerDriver";
	private static final String URL = "jdbc:microsoft:sqlserver://111.198.58.89:1433;DatabaseName=MJDataBase";
	private static final String USERNAME = "zdep";
	private static final String PASSWORD = "751751";

	/**
	 * 通过静态代码块 注册数据库驱动
	 */
	static {
		try {
			Class.forName(DRIVER);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 建立数据库连接
	 * 
	 * @return Connection
	 */
	public static Connection getConnection() {
		Connection conn = null;
		try {
			// 获取连接
			conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}

	/**
	 * 关闭ResultSet
	 * 
	 * @param rs
	 */
	public static void closeResultSet(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 关闭Statement
	 * 
	 * @param st
	 */
	public static void closeStatement(Statement st) {
		if (st != null) {
			try {
				st.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 关闭Connection
	 * 
	 * @param conn
	 */
	public static void closeConnection(Connection conn) {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 关闭全部
	 * 
	 * @param rs
	 * @param sta
	 * @param conn
	 */
	public static void closeAll(ResultSet rs, Statement sta, Connection conn) {
		closeResultSet(rs);
		closeStatement(sta);
		closeConnection(conn);
	}

	/**
	 * ResultSet转list<map>
	 * 
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	public static List convertList(ResultSet rs) throws SQLException {
		ResultSetMetaData md = rs.getMetaData();
		int num = md.getColumnCount();
		List listOfRows = new ArrayList();
		while (rs.next()) {
			Map mapOfColValues = new HashMap(num);
			for (int i = 1; i <= num; i++) {
				mapOfColValues.put(md.getColumnName(i), rs.getObject(i));
			}
			listOfRows.add(mapOfColValues);
		}
		return listOfRows;
	}

	/**
	 * 查询条数
	 * 
	 * @param sql
	 * @return
	 */
	public static int getSize(String sql) {
		Connection conn = getConnection();
		PreparedStatement sta = null;
		ResultSet rs = null;
		int size = 0;
		try {
			sta = conn.prepareStatement(sql);
			rs = sta.executeQuery();
			if (rs.next()) {
				size = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeAll(rs, sta, conn);
		}
		return size;
	}

//	/**
//	 * 分页查询
//	 * 
//	 * @param querySQL
//	 * @param countSQL
//	 * @param vo
//	 * @return ResultSet
//	 */
//	public static ResultSet queryPage(String querySQL, String countSQL, PageVO vo) {
//		int size = getSize(countSQL); //总记录数
//		int runtimePageSize = vo.getPageSize(); //每页显示条数
//		int totalPage = size / runtimePageSize; //总页数
//		if (size % runtimePageSize > 0) {
//			totalPage++;
//		}
//		if (vo.getCurrentPage() > totalPage && totalPage != 0) {
//			vo.setCurrentPage(totalPage);
//		}
//		vo.setTotalPage(totalPage);
//		vo.setTotalRecord(size);
//		Connection conn = getConnection();
//		PreparedStatement sta = null;
//		ResultSet rs = null;
//		try {
//			querySQL += " limit " + (vo.getCurrentPage() - 1) * runtimePageSize
//					+ "," + runtimePageSize + "";
//			sta = conn.prepareStatement(querySQL);
//			rs = sta.executeQuery();
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			closeAll(rs, sta, conn);
//		}
//		return rs;
//	}
}
