<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Hello World!</title>
</head>
<body>
	<form action="/chat/chatHistory" method="get" >
		<input value="Chat History" type="submit">
	</form>
	<form action="/chat/roleList" method="get" onsubmit="return validate()">
		<input value="Role List" type="submit">
	</form>

</body>

</html>