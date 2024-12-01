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

<!--  로그인 안한 상태 -->
<c:if test="${empty sessionScope.user}">
<div align="center">
   <header><h2>POST EAT</h2> </header> 
    <main>
        <h2>환영합니다! POST EAT 서비스입니다.</h2>
        <p>로그인을 하시면 더 많은 기능을 이용하실 수 있습니다.</p>
        <p><a href="${pageContext.request.contextPath}/posteat/register.do">회원가입</a>     
        <a href="${pageContext.request.contextPath}/posteat/login.do">로그인</a></p>
    </main>
   <hr>
   <form action="searchRecipe" method="get">
      <a href="${pageContext.request.contextPath}/recipe/list.do">레시피 보러 가기</a><p>
      <!--  검색은 아직 -->
      레시피 검색: <input type="search" name="search_query" placeholder="검색할 내용을 입력">
      <button type="submit">검색</button>
   </form>
   <!-- 추천도 아직 -->
   <p><a href="recommendRecipe.jsp">레시피 추천받기</a></p>
</div>
</c:if>

<!--  로그인 한 상태  -->
<c:if test="${! empty sessionScope.user}">
<div align="center">
   <header><h2>POST EAT</h2> </header> <hr>
    <main>
        <h2>환영합니다, ${sessionScope.user.nickname}님!</h2>
        <a href="${pageContext.request.contextPath}/posteat/profile.do">프로필 보기</a>
        <a href="${pageContext.request.contextPath}/posteat/logout.do">로그아웃</a>
    </main>
   <hr>
   <form action="${pageContext.request.contextPath}/recipe/list.do" method="get">
      <a href="${pageContext.request.contextPath}/recipe/list.do">레시피 보러 가기</a><p>
      <!--  검색은 아직 -->
      레시피 검색: <input type="search" name="search_query" placeholder="검색할 내용을 입력">
      <button type="submit">검색</button>
   </form>
   <!-- 추천도 아직 -->
   <p><a href="recommendRecipe.jsp">레시피 추천받기</a></p>
</div>
</c:if>

<footer>
        <p>&copy; 2024 POST EAT</p>
</footer>

</body>
</html>