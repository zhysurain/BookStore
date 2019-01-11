<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'left.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<style type="text/css">
		*{
			font-size:10pt;
			text-align: center;
		}
		div {
			background: #87CEFA; 
			margin: 3px; 
			padding: 3px;
		}
		a {
			text-decoration: none;
		}
	</style>

  </head>
  <body>
  	<div>
		<a href="javascript:void(0)" onclick="load('BookServlet?method=findAllBooks')" >全部分类</a>
	</div>
	<c:forEach var="item" items="${categoryList }">
		<div>
			<a href="javascript:void(0)" onclick="load('BookServlet?method=findBooksByCategory&cid=${item.cid }')" ><c:out value="${item.cname }"></c:out></a>
		</div>
	</c:forEach>
	<br/>
	<c:if test="${sessionScope.user.username eq 'admin' }">
		<div align="left">
			管理员列表
		</div>
		<div>
			<a href="javascript:void(0)" onclick="load('AdminServlet?method=categoryList')" >分类管理</a>
		</div>
		<div>
			<a href="javascript:void(0)" onclick="load('AdminServlet?method=bookList')" >图书管理</a>
		</div>
		<div>
			<a href="javascript:void(0)" onclick="load('AdminServlet?method=orderList&state=1')" >订单管理</a>
		</div>
 	</c:if>
		
  </body>
  
  <script type="text/javascript">
  	function load(url){
  		//alert(url);
  		window.parent.document.getElementById("body").src= "<c:url value='" + url + "'/>";
  	}
  </script>
</html>
