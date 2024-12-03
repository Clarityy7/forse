<%-- <%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/webcss/style.css">
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
        <input type="submit" value="취소" class="submit-btn" />
      </fieldset>
</form>
</div>
</body>
</html>--%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>레시피 작성</title>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/webcss/style.css">
    <style>
        /* 페이지 스타일 */
        body {
            font-family: Arial, sans-serif;
            background-color: #f9f9f9;
            margin: 0;
            padding: 0;
        }

        .container {
            width: 90%;
            max-width: 600px;
            margin: 50px auto;
            padding: 20px;
            background: white;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }

        h2 {
            color: #FF8C00;
            text-align: center;
            margin-bottom: 20px;
        }

        form {
            display: flex;
            flex-direction: column;
        }

        label {
            margin-bottom: 8px;
            font-weight: bold;
            color: #333;
        }

        input[type="text"],
        textarea {
            width: 100%;
            padding: 10px;
            margin-bottom: 20px;
            border: 1px solid #ddd;
            border-radius: 4px;
            font-size: 1rem;
        }

        textarea {
            resize: none;
        }

        button {
            background-color: #FF8C00;
            color: white;
            border: none;
            padding: 10px 20px;
            margin-top: 10px;
            cursor: pointer;
            border-radius: 4px;
            font-size: 1rem;
        }

        button:hover {
            background-color: #e67e00;
        }

        .actions {
            display: flex;
            justify-content: space-between;
        }

        .actions button {
            width: 48%;
        }
    </style>
</head>
<body>
<div class="container">
    <h2>레시피 작성</h2>
    <form action="${pageContext.request.contextPath}/recipe/insert.do" method="post">
        <label for="title">레시피 이름</label>
        <input type="text" id="title" name="title" placeholder="레시피 이름을 입력하세요" required>

        <label for="description">레시피 내용</label>
        <textarea id="description" name="description" rows="8" placeholder="레시피 내용을 입력하세요" required></textarea>

        <!-- 버튼 -->
        <div class="actions">
            <button type="submit">등록</button>
            <button type="button" onclick="location.href='${pageContext.request.contextPath}/recipe/list.do'">취소</button>
        </div>
    </form>
</div>
</body>
</html>
