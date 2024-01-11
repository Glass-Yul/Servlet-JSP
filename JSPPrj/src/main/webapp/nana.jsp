<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String cnt_ = request.getParameter("cnt");
int cnt = 20;

if(cnt_ != null && !(cnt_ == ""))
	cnt = Integer.parseInt(cnt_);

%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>NANA</title>
</head>
<body>
	<%for(int i=0; i<cnt; i++){ %>
		안녕 Servlet!!<br>
		
	<%} %>
</body>
</html>