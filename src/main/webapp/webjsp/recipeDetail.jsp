<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<!-- 조회한 사람이 레시피 작성자이면 수정 버튼 활성화? forEach로 댓글 쭉 나열 -->
<div align="center">
    <h2>${recipe.title} 레시피</h2> <hr>
    <p><a href="${pageContext.request.contextPath}/recipe/list.do">레시피 목록으로</a></p>
    <p>작성자: ${recipe.userID}</p>
    <p>등록일: ${recipe.regdate}</p>
    <p>수정일: </p>
    <div>
    	<p>${recipe.description}</p>
    </div>
    <c:if test="${not empty user && user.id == recipe.userID}">
    	<a href="${pageContext.request.contextPath}/recipe/edit.do?recipeID=${recipe.recipeID}">수정</a>
    	<a href="${pageContext.request.contextPath}/recipe/delete.do?recipeID=${recipe.recipeID}" onclick="return confirm('정말 삭제하시겠습니까?');">삭제</a>
	</c:if>
   <!-- 댓글 목록 -->
	<h3>댓글</h3>
	<c:forEach var="comment" items="${comments}">
    	<div>
        	<p><strong>${comment.userID}</strong>: ${comment.content}</p>
        	<p><fmt:formatDate value="${comment.regdate}" pattern="yyyy-MM-dd HH:mm:ss" /></p>
    	</div>
	</c:forEach>

	<!-- 댓글 작성 폼 -->
	<c:if test="${not empty user}">
    	<form action="${pageContext.request.contextPath}/comment/add.do" method="post">
        	<input type="hidden" name="recipeID" value="${recipe.recipeID}" />
        	<textarea name="content" required></textarea>
        	<button type="submit">댓글 작성</button>
    	</form>
	</c:if>
</div>
</body>
</html>