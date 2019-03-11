package com.farm.core.util;

import java.sql.Connection;  
import java.sql.DriverManager;  
import java.sql.ResultSet;  
import java.sql.SQLException;  
import java.sql.Statement; 

public class DBUtil {
	public static final String DRIVER = "oracle.jdbc.driver.OracleDriver";
	public static final String URL = "jdbc:oracle:thin:@60.194.185.19:1521:orcl";
	public static final String USERNAME = "east";
	public static final String PASSWORD = "east";

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
	 * 获得Connection
	 * 
	 * @return
	 */
	public static Connection getConnection() {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}

	/**
	 * 获得Statement
	 * 
	 * @return
	 */
	public static Statement getStatement() {
		Statement st = null;
		try {
			st = getConnection().createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return st;
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
	 * 获得总记录数
	 * 
	 * @return
	 */
	public static int getRows() {
		int rows = 0;
		Statement sta = null;
		ResultSet rs = null;
		Connection conn = getConnection();
		try {
			sta = conn.createStatement();
			rs = sta.executeQuery("select * from e_user");
			rs.last();
			rows = rs.getRow();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rows;

	}
}
