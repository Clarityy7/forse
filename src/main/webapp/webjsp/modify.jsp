<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>프로필 수정</title>
</head>
<body>
<div align="center">
   <h2>프로필 수정</h2> <hr>
   <c:if test="${not empty sessionScope.user}">
        <form action="userController?action=modifyProfile" method="post">
            <p>
                <label for="userID">아이디:</label>
                <input type="text" id="userID" name="userID" value="${sessionScope.user.userID}" required>
            </p>
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
            <button type="submit">수정</button>
            <button type="button" onclick="history.back()">취소</button>
        </form>
    </c:if>
</div>
</body>
</html>