<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>프로필 수정</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/webcss/style.css">
</head>
<body>
<div align="center">
   <h2>프로필 수정</h2> <hr>

<c:if test="${not empty errors}">
    <ul style="color: red;">
        <c:forEach var="error" items="${errors.values()}">
            <li>${error}</li>
        </c:forEach>
    </ul>
</c:if>

        <form action="${pageContext.request.contextPath}/posteat/modify.do" method="post">
            <p>
                <label for="nickname">닉네임:</label>
                <input type="text" id="nickname" name="nickname" value="${sessionScope.user.nickname}" required>
            </p>
            <p>
                <label for="password">비밀번호:</label>
                <input type="password" id="password" name="password" placeholder="새 비밀번호를 입력하세요">
            </p>
            <p>
                <label for="confirmPassword">비밀번호 확인:</label>
                <input type="password" id="confirmPassword" name="confirmPassword" placeholder="새 비밀번호를 다시 입력하세요">
            </p>
            <button type="submit">수정 완료</button>
        	<button type="button" onclick="location.href='${pageContext.request.contextPath}/posteat/profile.do'">취소</button>
        </form>

</div>
</body>
</html>