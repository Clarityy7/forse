<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<form action="${pageContext.request.contextPath}/recipe/insert.do" method="post">
      <fieldset>
        <legend><h2>레시피 작성</h2></legend> <hr>
        <div class="">
          <label for="title">레시피 이름: </label>
          <input type="text" id="title" name="title" placeholder="이름 입력" required value="${param.title}" />
        </div>

      <!-- 분류 체크박스로? 한식, 일식, 양식 등 (구현 X 시간 남으면 나중에)  -->

        <div class="form-control">
          <label for="description">레시피 내용 : </label> <p>
          <textarea
            id="description"
            name="description"
            cols="50"
            rows="10"
            placeholder="레시피 내용을 입력하세요"
            required
          >${param.description}</textarea>
        </div>
        <input type="submit" value="등록" class="submit-btn" />
        <input type="button" value="취소" class="submit-btn" onclick="history.back();" />
      </fieldset>
</form>
</div>
</body>
</html>
