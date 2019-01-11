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
    
    <title>My JSP 'list.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<style type="text/css">
		body {
			font-size: 10pt;
		}
		.icon {
			margin:10px;
			border: solid 2px gray;
			width: 160px;
			height: 180px;
			text-align: center;
			float: left;
		}
	</style>

  </head>
  
  <body>
    <c:forEach items="${bookList }" var="book">
	  <div class="icon">
	    <a href="<c:url value='/BookServlet?method=load&bid=${book.bid }'/>"><img src="<c:url value='/${book.image }'/>" border="0"/></a>
	      <br/>
	   	<a href="<c:url value='/BookServlet?method=load&bid=${book.bid }'/>">${book.bname }</a>
	  </div>
	</c:forEach>
    
  </body>
</html>
