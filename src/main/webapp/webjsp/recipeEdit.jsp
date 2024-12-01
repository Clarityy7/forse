<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<form action="${pageContext.request.contextPath}/recipe/update.do" method="post">
      <fieldset>
        <legend><h2>레시피 수정</h2></legend> <hr>
        
        <!-- 레시피 ID 전달 -->
        <input type="hidden" name="recipeID" value="${recipe.recipeID}" />
        
        <!-- 제목 -->
        <div class="form-control">
          <label for="title">레시피 이름: </label>
          <input type="text" id="title" name="title" placeholder="이름 입력" required value="${recipe.title}" />
        </div>

		<!-- 내용 -->
        <div class="form-control">
          <label for="description">레시피 내용 : </label> <p>
          <textarea
            id="description"
            name="description"
            cols="50"
            rows="10"
            placeholder="레시피 내용을 입력하세요"
            required
          >${recipe.description}</textarea>
        </div>
        
        <!-- 버튼 -->
        <input type="submit" value="등록" class="submit-btn" />
        <a href="${pageContext.request.contextPath}/recipe/view.do?recipeID=${recipe.recipeID}" class="submit-btn">취소</a>
      </fieldset>
</form>
</body>
</html>