package cn.jdbc;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class JdbcUtils {
	private static DataSource dataSource = new ComboPooledDataSource();
	private static ThreadLocal<Connection> tl = new ThreadLocal<Connection>();
	
	public static DataSource getDataSource() {
		return dataSource;
	}
	
	public static Connection getConnection() throws SQLException {
		Connection con = tl.get();
		if(con == null) {
			con = dataSource.getConnection();
		}
		return con;
	}
	
	public static void beginTransction() throws SQLException {
		Connection con = tl.get();
		if(con != null) {
			throw new SQLException("事务已经开启，在没有结束当前事务前，不能再开启事务");
		}
		con = dataSource.getConnection();
		con.setAutoCommit(false);
		tl.set(con);
	}
	
	public static void commitTransction() throws SQLException {
		Connection con = tl.get();
		if(con == null) {
			throw new SQLException("当前没有事务，所以不能提交事务");
		}
		con.commit();
		con.close();
		tl.remove();
	}
	
	public static void rollbackTransction() throws SQLException {
		Connection con = tl.get();
		if(con == null) {
			throw new SQLException("当前没有事务，所以不能回滚事务");
		}
		con.commit();
		con.close();
		tl.remove();
	}
}
