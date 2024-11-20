<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인 화면</title>
</head>
<body>
<div align="center">
   <h2>로그인</h2> <hr> 
   <form action="${pageContext.request.contextPath}/posteat/login.do" method="post">
   	   <c:if test="${errors.mismatch}"><p style="color: red;">암호가 일치하지 않습니다.</p></c:if> <p>	
   	   
	   <label for="id">ID:</label>
	   <input type="text" id="id" name="id" required>
	   <c:if test="${errors.email }">아이디를 입력하세요.</c:if> <p>
	   
	   <label for="password">Password:</label>
	   <input type="password" name="password" required>
	   <c:if test="${errors.password }">비밀번호를 입력하세요.</c:if> <p>
	   
	   <button type="submit">로그인</button>
	   <!-- 나중에 -->
	   <p>비밀번호가 기억이 나지 않으시나요? <a href="register.jsp">비밀번호 찾기</a></p>
	   <p>아직 계정이 없으신가요? <a href="${pageContext.request.contextPath}/posteat/register.do">회원가입</a></p>
   </form>
</div>
</body>
</html>