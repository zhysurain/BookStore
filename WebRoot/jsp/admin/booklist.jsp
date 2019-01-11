<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'booklist.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
    <h2>图书管理</h2>
    <div align="right"><a href="<c:url value='/AdminServlet?method=bookShowEdit'></c:url>">添加图书</a></div>
    <div></div>
    <div>
    	<table width="100%" style="text-align:center">
    		<tr>
    			<th>图书</th>
    			<th>书名</th>
    			<th>作者</th>
    			<th>单价</th>
    			<th>操作</th>
    		</tr>
	    	<c:if test="${bookList != null && fn:length(bookList) > 0 }">
	    		<c:forEach var="book" items="${bookList }" >
	    			<tr>
	    				<td><a href="<c:url value='/BookServlet?method=load&bid=${book.bid }'/>"><img src="<c:url value='/${book.image }'/>" border="0"/></a></td>
	    				<td><a href="<c:url value='/BookServlet?method=load&bid=${book.bid }'/>"><c:out value="${book.bname }"></c:out></a></td>
	    				<td><c:out value="${book.author }"></c:out></td>
	    				<td><c:out value="${book.price }"></c:out></td>
	    				<td>
	    					<a href="<c:url value='/AdminServlet?method=bookShowEdit&bid=${book.bid}'></c:url>">修 改</a>
	    					<a href="<c:url value='/AdminServlet?method=bookDel&bid=${book.bid}'></c:url>">删 除</a>
	    				</td>
	    			</tr>
	    		</c:forEach>
	    	</c:if>
    	</table>
    </div>
  </body>
  <script type="text/javascript">
  	function showEdit(bid){
  		//alert(bid);
  		window.location.href = "<c:url value='/AdminServlet?method=bookShowEdit&bid=" + bid + "'></c:url>";
  	}
  	
  	function showAdd(){
  		window.location.href = "<c:url value='/AdminServlet?method=bookShowEdit'></c:url>";
  	}
  </script>
</html>
