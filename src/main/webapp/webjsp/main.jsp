<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>POST EAT - 메인</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/webcss/style.css">
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f9f9f9;
        }

        header {
            background-color: #FF8C00;
            color: white;
            padding: 1rem;
            text-align: center;
            position: relative;
        }

        header .nav-buttons {
            position: absolute;
            right: 20px;
            top: 15px;
            display: flex;
            gap: 10px;
        }

        header .nav-buttons button {
            background-color: white;
            color: #FF8C00;
            border: none;
            border-radius: 50%;
            width: 40px;
            height: 40px;
            font-size: 18px;
            font-weight: bold;
            cursor: pointer;
            display: flex;
            align-items: center;
            justify-content: center;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.2);
        }

        header .nav-buttons button:hover {
            background-color: #e67e00;
            color: white;
        }

        header .logout-button {
            background-color: white;
            color: #FF8C00;
            border: none;
            border-radius: 5px;
            padding: 10px 15px;
            font-size: 16px;
            cursor: pointer;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.2);
        }

        header .logout-button:hover {
            background-color: #e67e00;
            color: white;
        }

        .container {
            width: 90%;
            max-width: 1200px;
            margin: auto;
            padding: 20px;
        }

        .recipe-table {
            width: 100%;
            border-collapse: collapse;
            margin: 20px 0;
        }

        .recipe-table th, .recipe-table td {
            border: 1px solid #ddd;
            padding: 10px;
            text-align: center;
        }

        .recipe-table th {
            background-color: #FF8C00;
            color: white;
        }

        .recipe-table td a {
            text-decoration: none;
            color: #FF8C00;
        }

        .recipe-table td a:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>
<header>
    <h1>POST EAT</h1>
    <div class="nav-buttons">
        <!-- 레시피 작성 버튼 -->
        <button onclick="location.href='${pageContext.request.contextPath}/recipe/add.do'">+</button>
        <!-- 내 프로필 보기 버튼 -->
        <button onclick="location.href='${pageContext.request.contextPath}/posteat/profile.do'">👤</button>
        <!-- 로그아웃 버튼 (로그인 상태에서만 표시) -->
        <c:if test="${!empty sessionScope.user}">
            <button class="logout-button" onclick="location.href='${pageContext.request.contextPath}/posteat/logout.do'">🚪</button>
        </c:if>
    </div>
</header>
<div class="container">
    <!-- 레시피 목록 -->
    <c:if test="${empty recipes}">
        <p>현재 표시할 레시피가 없습니다.</p>
    </c:if>
    <c:if test="${!empty recipes}">
        <table class="recipe-table">
            <thead>
                <tr>
                    <th>번호</th>
                    <th>제목</th>
                    <th>작성자</th>
                    <th>등록일</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="recipe" items="${recipes}">
                    <tr>
                        <td>${recipe.recipeID}</td>
                        <td><a href="${pageContext.request.contextPath}/recipe/view.do?recipeID=${recipe.recipeID}">${recipe.title}</a></td>
                        <td>${recipe.userID}</td>
                        <td>${recipe.regdate}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </c:if>
    <!-- "레시피 전체 보기" 버튼 -->
    <div class="action-buttons">
        <button onclick="location.href='${pageContext.request.contextPath}/recipe/list.do'">레시피 전체 보기</button>
    </div>
</div>

</body>
</html>
 