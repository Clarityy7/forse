<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>로그인 화면</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/webcss/style.css">
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f9f9f9;
            margin: 0;
            padding: 0;
        }

        header {
            background-color: #FF8C00;
            color: white;
            padding: 1rem;
            text-align: center;
            position: relative;
        }

        .container {
            max-width: 400px;
            margin: 50px auto;
            background: white;
            padding: 20px;
            border-radius: 10px;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.2);
        }

        form input {
            width: 80%; /* 입력 칸 크기를 줄임 */
            padding: 10px;
            margin: 10px 0;
            border: 1px solid #ddd;
            border-radius: 5px;
        }

        form button {
            width: 80%; /* 버튼 크기도 동일하게 줄임 */
            padding: 10px;
            background-color: #FF8C00;
            color: white;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            font-size: 16px;
        }

        form button:hover {
            background-color: #e67e00;
        }

        /* 토스트 메시지 */
        #toast {
            visibility: hidden;
            min-width: 300px;
            margin: auto;
            background-color: #FF8C00;
            color: white;
            text-align: center;
            border-radius: 5px;
            padding: 15px;
            position: fixed;
            z-index: 1;
            left: 50%;
            top: 30%;
            transform: translate(-50%, -50%);
            font-size: 16px;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.2);
        }

        #toast.show {
            visibility: visible;
        }

        #toast button {
            margin-top: 10px;
            background-color: white;
            color: #FF8C00;
            border: none;
            padding: 10px 15px;
            border-radius: 5px;
            font-size: 14px;
            cursor: pointer;
        }

        #toast button:hover {
            background-color: #e67e00;
            color: white;
        }
    </style>
</head>
<body>
<header>
    <h1>
        <a href="${pageContext.request.contextPath}/posteat/main.do" style="text-decoration: none; color: white;">
            POST EAT
        </a>
    </h1>
</header>
<div class="container">
    <form action="${pageContext.request.contextPath}/posteat/login.do" method="post">
    <h2>로그인</h2>
    <input type="text" id="id" name="id" placeholder="아이디" required>
    <input type="password" id="password" name="password" placeholder="비밀번호" required>
    <button type="submit">로그인</button>
    <p>아직 계정이 없으신가요? <a href="${pageContext.request.contextPath}/posteat/register.do">회원가입</a></p>
</form>

</div>

<!-- 토스트 메시지 -->
<div id="toast">
    <p>아이디 또는 비밀번호가 일치하지 않습니다.</p>
    <button onclick="hideToast()">확인</button>
</div>

<script>
    // 토스트 메시지 표시
    function showToast() {
        const toast = document.getElementById('toast');
        toast.className = 'show';
    }

    // 토스트 메시지 숨기기
    function hideToast() {
        const toast = document.getElementById('toast');
        toast.className = '';
    }

    // 로그인 실패 시 서버에서 전달된 오류를 표시
    <c:if test="${not empty errors.mismatch}">
        showToast();
    </c:if>
</script>
</body>
</html>
