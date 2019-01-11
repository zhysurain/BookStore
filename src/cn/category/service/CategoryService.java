package cn.category.service;

import java.sql.SQLException;
import java.util.List;

import cn.category.dao.CategoryDao;
import cn.category.domain.Category;

public class CategoryService {
	private CategoryDao dao = new CategoryDao();
	
	public List<Category> listCategory() throws SQLException{
		return dao.getCategoryList();
	}
	
	public boolean editCname(int cid, String cname) throws SQLException {
		int result = dao.updateCnameByCid(cid, cname);
		if(result == 1) {
			return true;
		}
		return false;
	}
	
	public Category getCategoryByCname(String cname) throws SQLException {
		return dao.getCategoryByCname(cname);
	}
	
	public Category getCategoryByCid(int cid) throws SQLException {
		return dao.getCategoryByCid(cid);
	}
	
	public void addCname(String cname) throws SQLException {
		dao.insert(cname);
	}
	
	public void delCategory(int cid) throws SQLException {
		dao.deleteByCid(cid);
	}
}
