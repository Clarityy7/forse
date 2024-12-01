<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<!-- forEach 사용해서 데베에 있는 레시피 전부 출력 적당한 개수로 페이지 나눌 것  -->
<div align="center">
    <h2>레시피 목록</h2> <hr>
    <p><a href="${pageContext.request.contextPath}/posteat/main.do">메인 화면으로</a></p>
   	<c:if test="${not empty user}">
        <p><a href="${pageContext.request.contextPath}/recipe/add.do">레시피 작성</a></p>
    </c:if>
    <table border="1" cellpadding="5" cellspacing="0">
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
                        <td>등록날짜 넣기</td>
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