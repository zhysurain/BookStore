package cn.category.dao;

import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import cn.category.domain.Category;
import cn.jdbc.JdbcUtils;

public class CategoryDao {
	private DataSource dataSource = JdbcUtils.getDataSource();
	QueryRunner qr = new QueryRunner(dataSource);
	
	public List<Category> getCategoryList() throws SQLException {
		String sql = "select * from category";
		List<Category> list = qr.query(sql, new BeanListHandler<Category>(Category.class));
		return list;
	}
	
	public int updateCnameByCid(int cid, String cname) throws SQLException {
		String sql = "UPDATE category SET cname = ? where cid = ? LIMIT 1";
		Object[] params = {cname, cid};
		return qr.update(sql, params);
	}
	
	public Category getCategoryByCname(String cname) throws SQLException {
		String sql = "SELECT * FROM category WHERE cname = ?";
		return qr.query(sql, cname, new BeanHandler<Category>(Category.class));
	}
	
	public void insert(String cname) throws SQLException {
		String sql = "INSERT INTO category(cname) VALUES(?)";
		qr.update(sql, cname);
	}
	
	public void deleteByCid(int cid) throws SQLException {
		String sql = "DELETE FROM category WHERE cid = ? LIMIT 1";
		qr.update(sql, cid);
	}
	
	public Category getCategoryByCid(int cid) throws SQLException {
		String sql = "SELECT * FROM category WHERE cid = ? LIMIT 1";
		return qr.query(sql, cid, new BeanHandler<Category>(Category.class));
	}
}
