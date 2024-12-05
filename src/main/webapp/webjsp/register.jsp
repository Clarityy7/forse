<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>회원가입</title>
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

        header h1 a {
            text-decoration: none;
            color: white;
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
            width: 80%; /* 입력 칸 크기 */
            padding: 10px;
            margin: 10px 0;
            border: 1px solid #ddd;
            border-radius: 5px;
        }

        form button {
            width: 80%; /* 버튼 크기 */
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

        p {
            text-align: center;
            margin-top: 10px;
        }

        p a {
            color: #FF8C00;
            text-decoration: none;
        }

        p a:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>
<header>
    <h1>
        <a href="${pageContext.request.contextPath}/posteat/main.do">POST EAT</a>
    </h1>
</header>
<div class="container">
    <h2>회원가입</h2>
    <form action="${pageContext.request.contextPath}/posteat/register.do" method="post">
        <input type="text" name="id" placeholder="아이디" required>
        <input type="password" name="password" placeholder="비밀번호" required>
        <input type="password" name="confirmPassword" placeholder="비밀번호 확인" required>
        <input type="text" name="nickname" placeholder="닉네임" required>
        <button type="submit">회원가입</button>
        <p>이미 계정이 있으신가요? <a href="${pageContext.request.contextPath}/posteat/login.do">로그인</a></p>
    </form>
</div>
</body>
</html>
