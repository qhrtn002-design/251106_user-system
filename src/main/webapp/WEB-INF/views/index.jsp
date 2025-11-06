<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>나의 페이지</title>
</head>
<body>
<nav>
    <a href="<%= request.getContextPath() %>/login">로그인</a>
    <a href="<%= request.getContextPath() %>/join">회원가입</a>
</nav>
<section>
   <p>환영합니다!</p>
</section>
</body>
</html>