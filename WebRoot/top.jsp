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
    
    <title>My JSP 'top.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<style type="text/css">
		body {
			background: #4682B4; 
		}
		a {
			text-transform:none;
			text-decoration:none;
		} 
		a:hover {
			text-decoration:underline;
		}
	</style>

  </head>
  	<h1 style="text-align: center;">BookStore</h1>
  	<div style="font-size: 10pt;">
		您好：${sessionScope.user.username }&nbsp;&nbsp;|&nbsp;&nbsp;
		<a href="javascript:void(0);" onclick = "goCartList()">我的购物车</a>&nbsp;&nbsp;|&nbsp;&nbsp;
		<a href="javascript:void(0);" onclick = "goOrderList()">我的订单</a>&nbsp;&nbsp;|&nbsp;&nbsp;
		<a href="<c:url value='/UserServlet?method=logout' />" target="_parent">退出</a>
		<br/>
		<c:if test="${empty sessionScope.user}">
			<a href="<c:url value='/jsp/user/login.jsp'/>" target="_parent">登录</a> |&nbsp; 
			<a href="<c:url value='/jsp/user/regist.jsp'/>" target="_parent">注册</a>
		</c:if>
</div>
  <body>
  </body>
  
  <script type="text/javascript">
  	function goCartList(){
  		window.parent.document.getElementById("body").src= "<c:url value='/CartServlet?method=list'/>";
  	}
  	
  	function goOrderList(){
  		window.parent.document.getElementById("body").src= "<c:url value='/OrderServlet?method=list'/>";
  	}
  </script>
</html>
