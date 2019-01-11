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
    
    <title>My JSP 'login.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">

  </head>
  
  <body>
    <p style="color: red; font-weight: 900">${msg }</p>
	<div style="text-align:center; vertical-align: middle;">
		<form action="<c:url value='/UserServlet'/>" method="post">
			<input type="hidden" name="method" value="login"/>
			用户名：<input type="text" name="username" value="${user.username }"/><br/>
			密　码：<input type="password" name="password" value="${user.password }"/><br/>
			<input type="submit" value="登录" />
			<input type="button" value="注册" onclick="javascript:window.location.href='<c:url value="/jsp/user/regist.jsp"></c:url>'" />
		</form>
	</div>
  </body>
</html>
