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
    
    <title>My JSP 'categorylist.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<style type="text/css">
		body {background: rgb(254,238,189);}
		table {font-family: 宋体; font-size: 11pt; border-color: rgb(78,78,78);  width: 60%;}
		#th {background: rgb(78,78,78);}
	</style>

  </head>
  
  <body>
    <h2>分类管理</h2>
    <div align="right"><input type="button" id="add" value="添加分类" onclick="showAdd()" /></div>
    <div></div>
    <div>
      	<table width="80%" style="text-align:center" border="1" cellpadding="0" cellspacing="0">
    	<tr id="th" bordercolor="rgb(78,78,78)">
    		<th>分类名称</th>
    		<th>操作</th>
    	</tr>
		<c:forEach items="${categoryList }" var="c">    
	    	<tr bordercolor="rgb(78,78,78)">
	    		<td>${c.cname }</td>
	    		<td>
	    		  <input type="button" name="edit" value="修 改" onclick="showEdit('${c.cid }', '${c.cname }')" />
	    		  <input type="button" name="del" value="删 除" onclick="del('${c.cid }')" />
	    		</td>
	    	</tr>
		</c:forEach>
   		</table>
    </div>
    <br/><br/><br/>
    <div id="editDiv" style="display: none" >
    	<form id="editForm" action="<c:url value='/AdminServlet'></c:url>" method="post">
			分类名称: <input type="text" name="cname" id="cname" /><br/>
			<a href="javascript:void(0)" onclick="edit()">提 交</a>
			<a href="javascript:void(0)" onclick="$('#edit').css('display','none');" >返 回</a>
			<input type="hidden" id="cid" name="cid" value="" />
			<input type="hidden" id="method" name="method" value="" />
		</form>
	</div>
  </body>
  <script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
  <script type="text/javascript">
  	function showEdit(cid, cname) {
		//alert(cid + "," + cname);
		$('#editDiv').css('display','block');
		$('#cname').val(cname);
		$('#cid').val(cid);
		$("#method").val("categoryEdit");
　　 }
	
	function showAdd(){
		$('#editDiv').css('display','block');
		$('#cname').val("");
		$("#method").val("categoryAdd");
	}
	
	function edit(){
		var cname = $('#cname').val();
		var cid = $('#cid').val();
		if($('#cname').val() == ""){
			alert("分类名称为空，不能修改");
		}else{
			$("#editForm").submit();
		}
	}
	
	function del(cid){
		window.location.href = "<c:url value='/AdminServlet?method=categoryDel&cid=" + cid + "'></c:url>";
	}
  </script>
</html>
