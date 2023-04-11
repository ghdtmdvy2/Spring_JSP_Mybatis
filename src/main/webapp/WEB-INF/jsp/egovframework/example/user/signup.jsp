<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<form class="form-signin" action="/user/signup.do" method="POST">
		<input type="text" name="username" class="form-control" placeholder="ID" required autofocus>
		<input type="password" name="password" class="form-control" placeholder="Password" required>
		
		<button class="btn btn-lg btn-primary btn-block" type="submit">LOGIN</button>
		<c:if test="${param.err == true}">
			<p style="color: red">ID와 Password를 확인해주세요.</p>
		</c:if>
	</form>
</body>
</html>