<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>마이페이지</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/webcss/style.css">
    <style>
    * {
    margin: 0;
    padding: 0;
    box-sizing: border-box; /* 요소 크기를 정확히 계산 */
}

body, html {
    margin: 0;
    padding: 0;
    width: 100%;
}
    
header {
    width: 100%; /* 페이지 전체 너비 */
    height: 110.88px; /* 원하는 높이 */
    background-color: #FF8C00; /* 주황색 배경 */
    color: white; /* 텍스트 색상 */
    text-align: center; /* 텍스트 중앙 정렬 */
    position: relative; /* 버튼 위치 조정 */
    box-sizing: border-box; /* 패딩과 테두리를 포함한 크기 계산 */
}

header h1 {
    font-family: Arial, sans-serif; /* 정확한 글꼴 지정 */
    font-size: 32px; /* 글씨 크기 */
    font-weight: 700; /* 텍스트 굵기 */
    line-height: 36px; /* 텍스트 높이 */
    height: 36px; /* 컨테이너 높이 */
    color: rgb(255, 255, 255); /* 텍스트 색상 (흰색) */
    text-align: center; /* 텍스트 중앙 정렬 */
    margin: 21.44px 0; /* 상하 마진 */
    display: block; /* 블록 요소로 설정 */
    width: 949.6px; /* 고정 너비 */
    margin-left: auto; /* 중앙 정렬 */
    margin-right: auto; /* 중앙 정렬 */
}


header h1 a {
    text-decoration: none;
    color: white;
    display: block;
    width: 100%;
}

header .nav-buttons {
    position: absolute;
    top: 40%;
    transform: translateY(-50%);
    right: 20px;
    display: flex;
    gap: 10px;
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
            text-align: center;
            margin-top: 20px;
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
    <h1>
        <a href="${pageContext.request.contextPath}/posteat/main.do" style="text-decoration: none; color: white;">
            POST EAT
        </a>
    </h1>
    <div class="nav-buttons">
        <!-- 레시피 작성 버튼 -->
        <button onclick="location.href='${pageContext.request.contextPath}/recipe/add.do'">+</button>
        <!-- 내 프로필 보기 버튼 -->
        <button onclick="location.href='${pageContext.request.contextPath}/posteat/profile.do'">👤</button>
        <!-- 로그아웃 버튼 -->
        <c:if test="${!empty sessionScope.user}">
            <button class="logout-button" onclick="location.href='${pageContext.request.contextPath}/posteat/logout.do'">🚪</button>
        </c:if>
    </div>
</header>
<div class="container">
    <h2>${sessionScope.user.nickname}님의 프로필</h2>
    <p>아이디: ${sessionScope.user.id}</p>
    <p>가입일자: ${sessionScope.user.regdate}</p>
    <button onclick="location.href='${pageContext.request.contextPath}/posteat/modify.do'">회원정보 수정</button>
    <hr>
    <!-- 작성한 레시피 목록 -->
    <h3>작성한 레시피</h3>
    <c:if test="${empty recipes}">
        <p>작성한 레시피가 없습니다.</p>
    </c:if>
    <c:if test="${!empty recipes}">
        <table class="recipe-table">
            <thead>
                <tr>
                    <th>번호</th>
                    <th>제목</th>
                    <th>등록일</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="recipe" items="${recipes}">
                    <tr>
                        <td>${recipe.recipeID}</td>
                        <td>
                            <a href="${pageContext.request.contextPath}/recipe/view.do?recipeID=${recipe.recipeID}">
                                ${recipe.title}
                            </a>
                        </td>
                        <td>${recipe.regdate}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </c:if>
</div>
</body>
</html>
