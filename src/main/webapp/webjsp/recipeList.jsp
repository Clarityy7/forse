<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/webcss/style.css">
<style>
        /* 레시피 테이블 스타일 */
        .recipe-table {
            margin: 20px auto; /* 테이블을 가운데 정렬 */
            border-collapse: collapse; /* 테이블 경계선 병합 */
            width: 80%; /* 테이블 너비 */
            max-width: 600px; /* 최대 너비 */
            text-align: center; /* 텍스트 가운데 정렬 */
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
<div align="center">
    <h2>레시피 목록</h2> <hr>
    <p><a href="${pageContext.request.contextPath}/posteat/main.do">메인 화면으로</a></p>
   	<c:if test="${not empty user}">
        <p><a href="${pageContext.request.contextPath}/recipe/add.do">레시피 작성</a></p>
    </c:if>-
    <!--  레시피 테이블 -->
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
        
        <!-- 페이징 처리 -->
        <c:if test="${totalPages > 1}">
            <div>
                <c:forEach var="i" begin="1" end="${totalPages}">
                    <c:choose>
                        <c:when test="${i == currentPage}">
                            <strong>[${i}]</strong>
                        </c:when>
                        <c:otherwise>
                            <a href="${pageContext.request.contextPath}/recipe/list.do?page=${i}">[${i}]</a>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
            </div>
        </c:if>
</div>
</body>
</html>