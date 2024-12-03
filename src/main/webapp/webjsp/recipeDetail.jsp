<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/webcss/style.css">
</head>
<body>
<!-- 레시피 상세 정보 표시 -->
<%--<div align="center">
    <h2>${recipe.title} 레시피</h2> <hr>
    <p><a href="${pageContext.request.contextPath}/recipe/list.do">레시피 목록으로</a></p>
    <p>작성자: ${recipe.userID}</p>
    <p>등록일: ${recipe.regdate}</p>
    <div>
    	<p>${recipe.description}</p>
    </div>
    <!-- 수정 및 삭제 버튼 (작성자만 보이도록) -->
    <c:if test="${not empty user && user.id == recipe.userID}">
    	<a href="${pageContext.request.contextPath}/recipe/edit.do?recipeID=${recipe.recipeID}">수정</a>
    	<a href="${pageContext.request.contextPath}/recipe/delete.do?recipeID=${recipe.recipeID}" onclick="return confirm('정말 삭제하시겠습니까?');">삭제</a>
	</c:if>
	<!-- 추천 -->
	<c:if test="${not empty user}">
	    <form action="${pageContext.request.contextPath}/recipe/recommend.do" method="post">
	        <input type="hidden" name="recipeID" value="${recipe.recipeID}" />
	        <button type="submit" onclick="return confirm('추천 하시겠습니까?');">추천</button>
	    </form>
	</c:if>
	<!-- 에러 메시지 표시 -->
	<c:if test="${not empty error}">
	    <p style="color:red;">${error}</p>
	</c:if>
	<p>추천 수: ${recommendationCount}</p>
    <!-- 댓글 목록 -->
	<h3>댓글</h3> <hr>
	<c:forEach var="comment" items="${comments}">
    	<div>
        	<p><strong>${comment.userID}</strong>: ${comment.content}</p>
        	<p>${comment.regdate}</p>
        	<!-- 삭제 버튼(댓글 작성자만 보이도록) -->
        	<c:if test="${not empty user && user.id == comment.userID}"> 
        		<form action="${pageContext.request.contextPath}/comment/delete.do" method="post">
        			<input type="hidden" name="recipeID" value="${recipe.recipeID}"/>
        			<input type="hidden" name="commentID" value="${comment.commentID}"/>
        			<button type="submit" onclick="return confirm('댓글을 삭제하시겠습니까?');">삭제</button>
        		</form>
        	</c:if>
    	</div>
	</c:forEach>

	<!-- 댓글 작성 폼 (로그인한 사용자만) -->
	<c:if test="${not empty user}">
    	<form action="${pageContext.request.contextPath}/comment/add.do" method="post">
        	<input type="hidden" name="recipeID" value="${recipe.recipeID}" />
        	<textarea name="content" required></textarea>
        	<button type="submit">댓글 작성</button>
    	</form>
	</c:if>
</div> --%>
<div class="container">
    <h2>${recipe.title} 레시피</h2>
    <img src="${recipe.imagePath}" alt="${recipe.title}" style="width:100%;max-width:300px;">
    <p>${recipe.description}</p>
    <p>작성자: ${recipe.userID}</p>
    <p>등록일: ${recipe.regdate}</p>

    <form action="${pageContext.request.contextPath}/recipe/recommend.do" method="post">
        <input type="hidden" name="recipeID" value="${recipe.recipeID}">
        <button type="submit">❤️</button>
    </form>
    <p>추천 수: ${recommendationCount}</p>

    <h3>댓글</h3>
    <c:forEach var="comment" items="${comments}">
        <p>${comment.userID}: ${comment.content}</p>
    </c:forEach>

    <c:if test="${!empty sessionScope.user}">
        <form action="${pageContext.request.contextPath}/comment/add.do" method="post">
            <textarea name="content" required></textarea>
            <button type="submit">댓글 등록</button>
        </form>
    </c:if>
    <!-- 뒤로 돌아가기 버튼 -->
    <button onclick="history.back()">이전화면으로</button>
</div>
</body>
</html>