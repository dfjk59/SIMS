<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
	<%
response.setHeader("Pragma","No-cache");
response.setHeader("Cache-Control","no-cache");
response.setDateHeader("Expires", 0);
response.flushBuffer();
%>
<!DOCTYPE html>
<html lang="zh-cn">

<head>
<meta charset="UTF-8">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">   
<title>登陆</title>



<link rel="stylesheet" href="classic/style.css">
<script src="classic/md5.js"></script>


</head>

<body>
<body>
	<div class="container">
		<section id="content">
			<form action="login" method="post" onsubmit="return checkInput()">
				<h1>登陆</h1>
				<p>
					<font color="red">${ sessionScope.message}</font>
				</p>
				<div>
					<input type="text" placeholder="账号" required="" id="username"
						name="username" />
				</div>
				<div>
					<input type="password" placeholder="密码" required="" id="password" />
				</div>
				<input type="hidden" id="password_md5" name="password" />
				<div>
					<input type="submit" value="登陆" /> <input style="float: right; margin-right: 20px;" type="reset" value="重置">
				</div>
			</form>
			<!-- form -->
		</section>
		<!-- content -->
	</div>
	<!-- container -->
</body>



<script src="classic/index.js"></script>




</body>

</html>
