package cn.admin.servlet;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.Inet4Address;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import cn.book.domain.Book;
import cn.book.service.BookService;
import cn.category.domain.Category;
import cn.category.service.CategoryService;
import cn.order.domain.Order;
import cn.order.service.OrderService;
import cn.servlet.BaseServlet;
import cn.user.entity.User;
import cn.user.service.UserService;

public class AdminServlet extends BaseServlet {
	CategoryService cgService = new CategoryService();
	BookService bService = new BookService();
	OrderService oService = new OrderService();
	UserService uService = new UserService();
	
	//分类管理
	public String categoryList(HttpServletRequest request, HttpServletResponse response) throws SQLException {
		List<Category> list = cgService.listCategory();
		request.setAttribute("categoryList", list);
		return "/jsp/admin/categorylist.jsp";
	}
	
	public String categoryEdit(HttpServletRequest request, HttpServletResponse response) throws SQLException {
		int cid = Integer.valueOf(request.getParameter("cid"));
		String cname = request.getParameter("cname");
		boolean r = cgService.editCname(cid, cname);
		return "/CategoryServlet?method=list";
	}
	
	public String categoryAdd(HttpServletRequest request, HttpServletResponse response) throws SQLException {
		String cname = request.getParameter("cname");
		Category ct = cgService.getCategoryByCname(cname);
		if(ct == null) {
			cgService.addCname(cname);
		}
		return "/CategoryServlet?method=list";
	}
	
	public String categoryDel(HttpServletRequest request, HttpServletResponse response) throws SQLException {
		int cid = Integer.valueOf(request.getParameter("cid"));
		cgService.delCategory(cid);
		return "/CategoryServlet?method=list";
	}
	
	//图书管理
	public String bookList(HttpServletRequest request, HttpServletResponse response) throws SQLException {
		List<Book> list = bService.findAllBooks();
		request.setAttribute("bookList", list);
		return "/jsp/admin/booklist.jsp";
	}
	
	public String bookShowEdit(HttpServletRequest request, HttpServletResponse response) throws SQLException {
		String bid = request.getParameter("bid");
		if(bid != null) {
			Book book = bService.getBookByBid(Integer.valueOf(bid));
			request.setAttribute("book", book);
		}
		
		List<Category> CategoryList = cgService.listCategory();
		request.setAttribute("CategoryList", CategoryList);
		return "/jsp/admin/bookedit.jsp";
	}
	
	public String bookEdit(HttpServletRequest request, HttpServletResponse response) throws FileUploadException, UnsupportedEncodingException, SQLException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		List<FileItem> list = upload.parseRequest(request);
		
		Book book = new Book();
		for (FileItem item : list) {
			if(item.getFieldName().equals("bid") && !item.getString().equals("")) {
				int bid = Integer.valueOf(item.getString());
				book.setBid(bid);
			}else if(item.getFieldName().equals("bname")) {
				String bname = item.getString();
				book.setBname(typeConversion(bname, "iso-8859-1", "utf-8"));
			}else if(item.getFieldName().equals("author")) {
				String author = item.getString();
				book.setAuthor(typeConversion(author, "iso-8859-1", "utf-8"));
			}else if(item.getFieldName().equals("price")) {
				double price = Double.valueOf(item.getString());
				book.setPrice(price);
			}else if(item.getFieldName().equals("cid")) {
				int cid = Integer.valueOf(item.getString());
				Category cg = cgService.getCategoryByCid(cid);
				book.setCategory(cg);
			}else if(item.getFieldName().equals("image")) {
				String[] imagelist = item.getName().split("\\.");
				String filename = String.valueOf(new Date().getTime()) + "." + imagelist[imagelist.length-1];
				File file = new File(request.getServletContext().getRealPath("/book_img/"), filename);
				try {
					item.write(file);
				} catch (Exception e) {
					e.printStackTrace();
				}
				book.setImage("book_img/" + filename);
			}
		}
		System.out.println(book.toString());
		bService.editBook(book);
		
		return "/AdminServlet?method=bookList";
	}
	
	public String bookDel(HttpServletRequest request, HttpServletResponse response) throws SQLException {
		int bid = Integer.valueOf(request.getParameter("bid"));
		bService.deleteBook(bid);
		
		return "/AdminServlet?method=bookList";
	}
	
	//订单管理
	public String orderList(HttpServletRequest request, HttpServletResponse response) throws SQLException {
		String oid = request.getParameter("oid");
		String username = request.getParameter("username");
		String state = request.getParameter("state");
		String where = "";
		if(state!= null && !state.equals("")) {
			where += " AND state = " + state;
			request.setAttribute("state", state);
		}else {
			where += " AND state = 1";
			request.setAttribute("state", 1);
		}
		if(username != null && !username.equals("")) {
			User user = uService.getUserByName(username);
			if(user != null) {
				where += " AND uid = " + user.getUid();
			}
			request.setAttribute("username", username);
		}
		if(oid != null && !oid.equals("")) {
			where += " AND oid = '" + oid + "'" ;
			request.setAttribute("oid", oid);
		}
//		System.out.println("where:" + where);
		List<Order> orders = oService.getOrdersByWhere(where);
		request.setAttribute("orders", orders);
		
		
		return "/jsp/admin/orderlist.jsp";
	}
	
	public String orderDeliver(HttpServletRequest request, HttpServletResponse response) throws SQLException {
		String oid = request.getParameter("oid");
		oService.orderDeliver(oid);
		return "/AdminServlet?method=orderList";
	}
	
	//字符串类型转换
	private String typeConversion(String s, String from, String to) throws UnsupportedEncodingException {
		s = new String(s.getBytes(from), to);
		return s;
	}
}
