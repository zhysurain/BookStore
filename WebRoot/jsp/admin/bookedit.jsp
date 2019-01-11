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
    
    <title>My JSP 'bookedit.jsp' starting page</title>
    
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
    <h2>添加/修改图书</h2>
    <form action="AdminServlet?method=bookEdit" method="post" enctype="multipart/form-data" onsubmit="return formcheck()">
    	图书名：<input type="text" name="bname" id="bname" value="${book.bname }" /><br/>
    	作            者：<input type="text" name="author" id="author" value="${book.author }" /><br/>
    	价            格：<input type="text" name="price" id="price" value="${book.price }" /><br/>
    	分            类：<select name="cid" id="cid">
    		<option value="0">--请选择--</option>
    		<c:forEach var="item" items="${CategoryList }">
    			<option value="${item.cid }" <c:if test="${item.cid == book.category.cid }">selected="selected"</c:if>>${item.cname }</option>
    		</c:forEach>
    	</select><br/>
    	图书图片：<input type="file" name="image" id="image" /><br/>
    	<input type="submit" name="sub" value="提 交"  />
    	<input type="hidden" name="bid" value="${book.bid }">
    </form>
  </body>
  <script type="text/javascript">
  	function formcheck(){
  		var bname = document.getElementById("bname").value;
  		if(bname == ""){
  			alert("图书名不能为空");
  			return false;
  		}
  		var author = document.getElementById("author").value;
  		if(author == ""){
  			alert("作者不能为空");
  			return false;
  		}
  		var price = document.getElementById("price").value;
  		var reg = new RegExp("^[0-9]+(\.)[0-9]{2}$");
  		if(price == ""){
  			alert("价格不能为空");
  			return false;
  		}else if(!reg.test(price)){
  			alert("价格格式不正确，应为两位小数");
  			return false;
  		}
  		var image = document.getElementById("image").value;
  		if(image == ""){
  			alert("请上传图书图片");
  			return false;
  		}
  		return true;
  	}
  </script>
</html>
