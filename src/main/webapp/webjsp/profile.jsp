<%-- <%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/webcss/style.css">
</head>
<body>
<div align="center">
    <h2>${sessionScope.user.nickname}님의 프로필</h2> <hr>
    아이디: ${sessionScope.user.id}<p> 
    닉네임: ${sessionScope.user.nickname}<p>
    가입일자: ${sessionScope.user.regdate} <p>
    <p><a href="${pageContext.request.contextPath}/posteat/modify.do">프로필 수정</a>
    <a href="${pageContext.request.contextPath}/posteat/main.do">메인으로</a></p>
    
    <!-- 해당 유저가 작성한 레시피가 있으면 표로 만들고 이름 클릭하면 레시피 정보로 하이퍼링크 걸리게  -->
    <h3>-작성한 레시피-</h3>
    <table border="1" cellpadding="5" cellspacing="0">
        <thead>
            <tr>
                <th>제목</th>
                <th>등록일</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="recipe" items="${recipes}">
                <tr>
                    <td><a href="${pageContext.request.contextPath}/recipe/view.do?recipeID=${recipe.recipeID}">${recipe.title}</a></td>
                    <td>${recipe.regdate}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</div>
<div class="container">
    <h2>${sessionScope.user.nickname}님의 마이페이지</h2>
    <p>아이디: ${sessionScope.user.id}</p>
    <p>가입일자: ${sessionScope.user.regdate}</p>

    <button onclick="location.href='${pageContext.request.contextPath}/posteat/modify.do'">정보 수정</button>
    <button onclick="location.href='${pageContext.request.contextPath}/posteat/main.do'">메인으로</button>

    <h3>내 레시피</h3>
    <table>
        <thead>
            <tr>
                <th>제목</th>
                <th>등록일</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="recipe" items="${recipes}">
                <tr>
                    <td><a href="${pageContext.request.contextPath}/recipe/view.do?recipeID=${recipe.recipeID}">${recipe.title}</a></td>
                    <td>${recipe.regdate}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html>--%>


<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>내 프로필</title>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/webcss/style.css">
    <style>
        /* 레시피 테이블 스타일 */
        .recipe-table {
            margin: 20px auto; /* 테이블을 가운데로 */
            border-collapse: collapse; /* 테이블 경계선 병합 */
            width: 80%; /* 테이블 너비 */
            max-width: 600px; /* 최대 너비 */
            text-align: center; /* 텍스트 정렬 */
        }

        .recipe-table th, .recipe-table td {
            border: 1px solid #ddd; /* 셀 경계선 */
            padding: 8px; /* 셀 여백 */
        }

        .recipe-table th {
            background-color: #FF8C00; /* 주황색 배경 */
            color: white; /* 흰색 텍스트 */
        }

        .recipe-table td a {
            color: #FF8C00; /* 링크 색상 */
            text-decoration: none;
        }

        .recipe-table td a:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>
<div class="container">
    <h2>${sessionScope.user.nickname}님의 마이페이지</h2>
    <p>아이디: ${sessionScope.user.id}</p>
    <p>가입일자: ${sessionScope.user.regdate}</p>

    <button onclick="location.href='${pageContext.request.contextPath}/posteat/modify.do'">정보 수정</button>
    <button onclick="location.href='${pageContext.request.contextPath}/posteat/main.do'">메인으로</button>

    <h3>내 레시피</h3>
    <table class="recipe-table">
        <thead>
            <tr>
                <th>제목</th>
                <th>등록일</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="recipe" items="${recipes}">
                <tr>
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
</div>
</body>
</html>
