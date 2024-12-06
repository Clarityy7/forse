<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/webcss/style.css">
</head>
<body>
<div align="center">
   <h2>회원가입</h2> <hr>
   <form action="${pageContext.request.contextPath}/posteat/register.do" method="post">
      <!-- 아이디(중복확인 버튼 중복아니면 버튼 활성화), 비밀번호, 비밀번호 확인(둘이 동일하지 않으면 좌측에 텍스트 나오고 완료 버튼 비활성화), 닉네임, 회원가입 버튼  -->
      
         <input type="text" name="id" placeholder="아이디" value="${param.id}" required>          
         <input type="button" name="idCheck" value="중복확인"><p>
         
         <input type="password" name="password" placeholder="비밀번호" required> <p>
         
         <input type="password" name="confirmpassword" placeholder="비밀번호 확인"> <p>
         
		 <input type="text" name="nickname" placeholder="닉네임" value="${param.nickname}" required> <p>
		 
         <!-- 그냥 넣어봄 기능은 시간남으면 구현 -->
		<label>성별:</label>
	        <input type="radio" id="male" name="gender" value="male">
	        <label for="male">남성</label>
	        <input type="radio" id="female" name="gender" value="female">
	        <label for="female">여성</label><br>
		<label for="agreeTerms">약관 동의:</label>
        <input type="checkbox" id="agreeTerms" name="agreeTerms" required> 약관에 동의합니다.<br>

         <button type="submit">회원가입</button>
         <input type="button" name="register" value="취소"><p>
   </form>
</div>
</body>
</html>