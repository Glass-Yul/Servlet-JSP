<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!-- ---------------------------------------------------------- -->
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>스파게티 소스</title>
</head>
<%
	pageContext.setAttribute("result", "hello");
%>

<body>
	<%=request.getAttribute("result") %>입니다.
	${resultScopt.result }입니다.<br><br>
	${names[0] }
	${names[1] }<br><br>
	${notice.title }
	${notice.id }<br><br>
	${result }<br><br>
	${param.n }<br><br>
	${header.accept}<br><br>
</body>
</html>