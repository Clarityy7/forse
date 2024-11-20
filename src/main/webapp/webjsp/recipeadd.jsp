<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<form action="">
      <fieldset>
        <legend><h2>레시피 추가</h2></legend> <hr>
        <div class="">
          <label for="name">레시피 이름: </label>
          <input type="name" id="name" placeholder="이름 입력" required />
        </div>

      <!-- 분류 체크박스로? 한식, 일식, 양식 등 -->
        <div class="">
          <label for="email">Email</label>
          <input
            type="email"
            id="email"
            placeholder="Enter your email"
            required
          />
        </div>

        <div class="form-control">
          <label for="message">레시피 내용 : </label> <p>
          <textarea
            id="message"
            cols="30"
            rows="10"
            placeholder="레시피 내용을 입력하세요"
            required
          ></textarea>
        </div>
        <input type="submit" value="등록하기" class="submit-btn" />
        <input type="submit" value="취소" class="submit-btn" />
      </fieldset>
</form>
</div>
</body>
</html>