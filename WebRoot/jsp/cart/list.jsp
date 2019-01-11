<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>购物车列表</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!-- 
	<style type="text/css">
		* {
			font-size: 11pt;
		}
		div {
			margin:20px;
			border: solid 2px gray;
			width: 150px;
			height: 150px;
			text-align: center;
		}
		li {
			margin: 10px;
		}
		
		#buy {
			background: url(<c:url value='/images/all.png'/>) no-repeat;
			display: inline-block;
			
			background-position: 0 -902px;
			margin-left: 30px;
			height: 36px;
			width: 146px;
		}
		#buy:HOVER {
			background: url(<c:url value='/images/all.png'/>) no-repeat;
			display: inline-block;
			
			background-position: 0 -938px;
			margin-left: 30px;
			height: 36px;
			width: 146px;
		}
	</style>
	 -->
  </head>
  
  <body>
    <c:choose>
    	<c:when test="${empty cartItemList or fn:length(cartItemList) eq 0 }">
    		<img src="<c:url value='/images/cart.png'/>" width="300" />
    	</c:when>
    	<c:otherwise>
    		<form id="itemform" action="OrderServlet" method="post">
	    		<table width="100%" style="text-align:center">
	    			<tr>
						<td colspan="7" align="right" style="font-size: 15pt; font-weight: 900">
							<a href="CartServlet?method=clearCart">清空购物车</a>
						</td>
					</tr>
					<tr>
						<th width="10%">选择</th>
						<th width="20%">图片</th>
						<th width="20%">书名</th>
						<th width="10%">作者</th>
						<th width="10%">单价</th>
						<th width="10%">数量</th>
						<th width="10%">小计</th>
						<th width="10%">操作</th>
					</tr>
					<c:forEach var="item" items="${cartItemList }">
						<tr>
							<td><input type="checkbox" name="cartitem" id="cartitem" value="${item.ciid }" /></td>
							<td><div><img src="<c:url value='${item.book.image }'/>"/></div></td>
							<td><c:out value="${item.book.bname }"></c:out></td>
							<td><c:out value="${item.book.author }"></c:out></td>
							<td><c:out value="${item.book.price }元"></c:out></td>
							<td><c:out value="${item.count }"></c:out></td>
							<td><fmt:formatNumber value="${item.book.price * item.count}" pattern="0.00"></fmt:formatNumber>元</td>
							<td><a href="CartServlet?method=deleteItem&ciid=<c:out value='${item.ciid }'></c:out>">删除</a></td>
						</tr>
					</c:forEach>
					<tr>
						<td colspan="7" align="right" style="font-size: 15pt; font-weight: 900">
							<a href="javascript:void(0)" onclick="addOrder()">一键结算</a>
							<input type="hidden" name="cartItems" id="cartItems" value="" />
							<input type="hidden" name="method" id="method" value="add" />
						</td>
					</tr>
	    		</table>
    		</form>
    	</c:otherwise>
    </c:choose>
  </body>
  <script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
  <script type="text/javascript">
  	function addOrder(){
  		var items = $("input[name='cartitem']");
  		$("#cartItems").val("");
  		//alert($("#cartItems").val());
  		for(var i=0; i< items.length; i++){
  			if(items[i].checked == true){
  				$("#cartItems").val($("#cartItems").val() + items[i].value + ",");
  			}
  		}
  		$("#itemform").submit();
  	}
  </script>
</html>
