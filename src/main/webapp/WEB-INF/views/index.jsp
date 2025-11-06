<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>나의 페이지</title>
</head>
<body>
<% if(session.getAttribute("username") == null) { %>
<nav>
    <a href="<%= request.getContextPath() %>/login">로그인</a>
    <a href="<%= request.getContextPath() %>/join">회원가입</a>
</nav>
<% } else { %>
<nav>
    <a href="<%= request.getContextPath() %>/logout">로그아웃</a>
</nav>
<% } %>
<section>
    <% if(session.getAttribute("username") == null) { %>
    <p>환영합니다!</p>
    <img width="320" src="<%= request.getContextPath() %>/images/cat.jpg">
    <% } else { %>
    <p>환영합니다! <%= session.getAttribute("username") %></p>
    <img width="320" src="<%= request.getContextPath() %>/images/welcome.jpg">
    <% } %>
</section>
</body>
</html>