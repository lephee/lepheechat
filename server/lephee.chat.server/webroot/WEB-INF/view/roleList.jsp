<%@page import="lephee.chat.persistence.role.Role"%>
<%@page import="java.util.List"%>
<%@page import="java.net.Socket"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

	<%
		List<Role> msg = (List<Role>) request.getAttribute("msg");
	%>

	<div>
		<table>
			<%
				for (Role r : msg) {
			%>
			<tr>
				<td><%=r.getId()%>, <%=r.getUsername()%>, <%=r.getNickname()%></td> 
				<td><a href="/chat/roleEdit?roleId=<%=r.getId()%>">Edit</a></td>
			</tr>
			<%
				}
			%>
		</table>
	</div>
</body>
</html>