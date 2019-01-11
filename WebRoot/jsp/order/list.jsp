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
    
    <title>My JSP 'list.jsp' starting page</title>
    
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
    <h1>我的订单</h1>
	<table border="1" width="100%" cellspacing="0" background="black">
	<c:forEach items="${orders }" var="order">
		<tr bgcolor="gray" bordercolor="gray">
			<td colspan="6">
				订单编号：${order.oid } 成交时间：${order.ordertime } 金额：<font color="red"><b>${order.total }</b></font>　
				<c:choose>
					<c:when test="${order.state eq 0 }">
						<a href="<c:url value='/OrderServlet?method=load&oid=${order.oid }'/>">付款</a>
					</c:when>
					<c:when test="${order.state eq 1 }">等待发货</c:when>
					<c:when test="${order.state eq 2 }">
						<a href="<c:url value='/OrderServlet?method=confirm&oid=${order.oid }'/>">确认收货</a>
					</c:when>
					<c:when test="${order.state eq 3 }">交易成功</c:when>
				</c:choose>
			</td>
		</tr>
	  <c:forEach items="${order.orderItem }" var="item">
		<tr bordercolor="gray" align="center">
			<td width="15%">
				<div><img src="<c:url value='${item.book.image }'/>" height="75"/></div>
			</td>
			<td>书名：${item.book.bname }</td>
			<td>单价：${item.book.price }元</td>
			<td>作者：${item.book.author }</td>
			<td>数量：${item.count }</td>
			<td>小计：${item.subtotal }元</td>
		</tr>
	  </c:forEach>
	</c:forEach>
	
	</table>
  </body>
</html>
