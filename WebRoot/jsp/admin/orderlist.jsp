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
    
    <title>My JSP 'orderlist.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">

  </head>
  
  <body>
  	<div>
  		<form action="AdminServlet">
  			订单号:<input type="text" name="oid" value="${oid }" />&nbsp;&nbsp;
  			用户名：<input type="text" name="username" value="${username }" />&nbsp;&nbsp;
  			订单状态：<select name="state">
  						<option value="0" <c:if test="${state eq 0 }">selected="selected"</c:if> >未付款</option>
  						<option value="1" <c:if test="${state eq 1 }">selected="selected"</c:if> >已付款,待发货</option>
  						<option value="2" <c:if test="${state eq 2 }">selected="selected"</c:if> >已发货,待确认收货</option>
  						<option value="3" <c:if test="${state eq 3 }">selected="selected"</c:if> >已确认收货</option>
  					</select>&nbsp;&nbsp;
  			<input type="submit" value="查  询" />
  			<input type="hidden" name="method" value="orderList" />
  		</form>
  	</div>
  	<div>
	    <table width="100%" cellspacing="0" background="black">
			<c:forEach items="${orders }" var="order">
				<tr bgcolor="gray" bordercolor="gray">
					<td colspan="6">
						订单编号：${order.oid } 成交时间：${order.ordertime } 金额：<font color="red"><b>${order.total }</b></font>　
						<c:choose>
							<c:when test="${order.state eq 0 }">待付款</c:when>
							<c:when test="${order.state eq 1 }">
								<a href="<c:url value="AdminServlet?method=orderDeliver&oid=${order.oid }"></c:url>">发货</a>
							</c:when>
							<c:when test="${order.state eq 2 }">待确认收货</c:when>
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
	</div>
  </body>
</html>
