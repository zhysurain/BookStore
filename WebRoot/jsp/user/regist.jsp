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
    
    <title>My JSP 'regist.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">

  </head>
  
  <body>
  	<h1>注册</h1>
  	<p style="color: red; font-weight: 900" id="msg">${msg}</p>
    <form action='<c:url value="/UserServlet"></c:url>' method="post" onsubmit="return checkForm()">
    	<input type="hidden" name="method" value="regist"/>
		　用户名：<input type="text" name="username" id="username" value="${user.username}" onblur="checkUser(this)" />
		<span style="color: red; font-weight: 900" id="username_msg"></span>
		<br/>
		密　　码：<input type="password" name="password" id="password" value="${user.password}"/>
		<span style="color: red; font-weight: 900" id="password_msg"></span>
		<br/>
		确认密码：<input type="password" name="password1" id="password1" value="${user.password}" onblur="checkPassword(this)" />
		<span style="color: red; font-weight: 900" id="password1_msg"></span>
		<br/>
		邮　　箱：<input type="text" name="email" id="email" value="${user.email}" onblur="checkUser(this)" />
		<span style="color: red; font-weight: 900" id="email_msg"></span>
		<br/>
		<input type="submit" value="注册"/>
		<input type="button" value="激活" onclick="activeUser()" />
    </form>
  </body>
  <script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
  <script type="text/javascript">
  	function checkUser(obj){
  		var type = obj.name;
  		var value = obj.value;
  		$.ajax({
  			url:"<c:url value='/AjaxUserServlet'></c:url>",
  			type:"post",
  			datatype:"json",
  			data:"method=checkUser&type=" + type + "&value=" + value,
  			success:function(data){
  				data = eval( "(" + data + ")" ); 
  				var msg = data.msg;
  				if(msg != ""){
  					document.getElementById(type+"_msg").innerHTML = msg;
  					obj.value = "";
  					$("#username").focus();
  				}else{
  					document.getElementById(type+"_msg").innerHTML = "";
  				}
  			}
  		});
  	}
  	
  	function checkPassword(obj){
  		if(obj.value != $("#password").val()){
  			document.getElementById("password1_msg").innerHTML = "密码不一致";
  			obj.value = "";
  			obj.focus();
  		}else{
  			document.getElementById("password1_msg").innerHTML = "";
  		}
  	}
  	
  	function checkForm(){
  		var username = $("#username");
  		if(username.val() == ""){
  			document.getElementById("username_msg").innerHTML = "用户名不能为空";
  			username.focus();
  			return false;
  		}
  		var password = $("#password").val();
  		if(password.length < 6 || password.length > 8){
  			document.getElementById("password_msg").innerHTML = "密码需为6到8位数";
  			$("#password").focus();
  			return false;
  		}
  		var password1 = $("#password1");
  		if(password1.val() != password){
  			document.getElementById("password1_msg").innerHTML = "密码不一致";
  			password1.focus();
  			return false;
  		}
  		var email = $("#email");
  		var reg = new RegExp("^[a-z0-9]+([._\\-]*[a-z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+.){1,63}[a-z0-9]+$");
  		if(email.val() == ""){
  			document.getElementById("email_msg").innerHTML = "邮箱不能为空";
  			email.focus();
  			return false;
  		}else if(!reg.test(email.val())){
  			document.getElementById("email_msg").innerHTML = "邮箱格式不正确";
  			email.focus();
  			return false;
  		}
  		return true;
  	}
  	//激活用户
  	function activeUser(){
  		//alert(${user.code});
  		$.ajax({
  			url:"<c:url value='UserServlet'></c:url>",
  			type:"post",
  			datatype:"json",
  			data:"method=active&code=${user.code}",
  			success:function(data){
  				data = eval("("+ data +")");
  				document.getElementById("msg").innerHTML = data.msg;
  				setTimeout(window.location.href="<c:url value='/jsp/user/login.jsp'></c:url>",5);
  			}
  		});
  	}
  </script>
</html>
