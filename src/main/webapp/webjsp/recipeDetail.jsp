<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>레시피 상세</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/webcss/style.css">
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f9f9f9;
        }

        header {
            background-color: #FF8C00;
            color: white;
            padding: 1rem;
            text-align: center;
            position: relative;
        }

        header h1 a {
            text-decoration: none;
            color: white;
        }

        header .nav-buttons {
            position: absolute;
            top: 15px;
            right: 20px;
            display: flex;
            gap: 10px;
        }

        header .nav-buttons button {
            background-color: white;
            color: #FF8C00;
            border: none;
            border-radius: 50%;
            width: 40px;
            height: 40px;
            font-size: 18px;
            font-weight: bold;
            cursor: pointer;
            display: flex;
            align-items: center;
            justify-content: center;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.2);
        }

        header .nav-buttons button:hover {
            background-color: #e67e00;
            color: white;
        }

        header .logout-button {
            background-color: white;
            color: #FF8C00;
            border: none;
            border-radius: 5px;
            padding: 10px 15px;
            font-size: 16px;
            cursor: pointer;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.2);
        }

        header .logout-button:hover {
            background-color: #e67e00;
            color: white;
        }

        .container {
            max-width: 800px;
            margin: 30px auto;
            background: white;
            padding: 20px;
            border-radius: 10px;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.2);
        }

        .recipe-title {
            font-size: 24px;
            font-weight: bold;
            color: #333;
            margin-bottom: 10px;
            text-align: center;
        }

        .recipe-info {
            font-size: 14px;
            color: #666;
            margin-bottom: 20px;
            text-align: center;
        }

        .recipe-content-container {
            border: 1px solid #ddd;
            border-radius: 5px;
            padding: 15px;
            margin-bottom: 20px;
            background-color: #fdfdfd;
        }

        .recipe-content {
            line-height: 1.6;
            font-size: 16px;
            color: #444;
        }

        .like-section {
    		display: inline-flex; /* 인라인 플렉스 사용 */
    		align-items: center; /* 세로 가운데 정렬 */
    		gap: 5px; /* 버튼과 좋아요 수 사이 간격 */
    		margin: 10px 0 20px; /* 상하 간격 */
		}


        .like-section button {
            background-color: #FF8C00;
            color: white;
            border: none;
            border-radius: 5px;
            padding: 10px 20px;
            cursor: pointer;
            font-size: 16px;
            text-align: center;
        }

        .like-section button:hover {
            background-color: #e67e00;
        }

        .like-count {
            font-size: 16px;
            color: #333;
        }
		
		.like-comment-section {
    		margin-top: 20px;
    		display: flex;
    		flex-direction: column;
    		gap: 10px; /* 댓글과 좋아요 사이의 간격을 줄임 */
		}

		.like-comment-section button {
    		background-color: #FF8C00;
    		color: white;
    		border: none;
    		padding: 10px;
    		border-radius: 5px;
    		cursor: pointer;
    		font-size: 16px;
    		margin-right: 10px;
    		align-self: flex-start; /* 좋아요 버튼을 왼쪽에 배치 */
		}	

		.comments {
    		margin-top: 20px;
		}

		.comments p {
    		background-color: #f1f1f1;
    		padding: 10px;
    		border-radius: 5px;
    		margin: 5px 0;
		}

		.comment-form-container {
    display: flex;
    justify-content: center;   /* 수평 중앙 정렬 */
    margin-top: 20px;           /* 위쪽 간격 */
}

/* 댓글 입력창과 등록 버튼을 수평 중앙으로 정렬 */
.comment-form {
    display: flex;
    justify-content: center;  /* 댓글 입력창과 등록 버튼 수평 중앙 정렬 */
    align-items: center;      /* 수직 중앙 정렬 */
    gap: 10px;                /* 댓글 입력창과 버튼 사이 간격 */
    width: 80%;               /* 댓글 폼 너비를 80%로 설정 */
    margin-top: 20px;         /* 위쪽 간격 추가 */
}

.comment-form textarea {
    width: 100%;              /* 텍스트 영역의 너비를 100%로 설정 */
    padding: 10px;
    font-size: 16px;
    border: 1px solid #ddd;
    border-radius: 5px;
    resize: none;
    box-sizing: border-box;   /* padding을 포함하도록 설정 */
}

.comment-form button {
    padding: 10px 20px;          /* 버튼 안쪽 여백 */
    font-size: 16px;            /* 텍스트 크기 */
    height: auto;               /* 높이를 내용에 맞게 자동 설정 */
    min-width: 100px;           /* 버튼 최소 너비 */
    max-width: 200px;           /* 버튼 최대 너비 (옵션) */
    white-space: nowrap;        /* 텍스트 줄바꿈 방지 */
    overflow: hidden;           /* 넘치는 텍스트 숨김 */
    text-align: center;         /* 텍스트 중앙 정렬 */
    border-radius: 5px;         /* 모서리 둥글게 */
    background-color: #FF8C00;  /* 버튼 배경색 */
    color: white;               /* 텍스트 색상 */
    border: none;               /* 테두리 없음 */
    box-shadow: 0 2px 5px rgba(0, 0, 0, 0.2); /* 그림자 */
    cursor: pointer;            /* 포인터 커서 */
    display: inline-block;      /* 인라인 블록 요소로 설정 */
}

		.comment-form button:hover {
    		background-color: #e67e00;
		}

		.back-button {
    		margin-top: 30px; /* 목록으로 버튼과 다른 요소들 사이에 공간 추가 */
    		display: inline-block;
    		background-color: #FF8C00;
    		color: white;
    		border: none;
    		padding: 10px 20px;
    		font-size: 16px;
    		cursor: pointer;
    		border-radius: 5px;
    		text-align: center;
    		text-decoration: none;
    		box-shadow: 0 2px 5px rgba(0, 0, 0, 0.2);
		}

		.back-button:hover {
    		background-color: #e67e00;
		}

	/* 토스트 메시지 스타일 */
#toast {
    visibility: hidden;
    min-width: 300px;
    margin: auto;
    background-color: #FF8C00;
    color: white;
    text-align: center;
    border-radius: 5px;
    padding: 15px;
    position: fixed;
    z-index: 1;
    left: 50%;
    top: 30%;
    transform: translate(-50%, -50%);
    font-size: 16px;
    box-shadow: 0 2px 5px rgba(0, 0, 0, 0.2);
}

#toast.show {
    visibility: visible; /* 보이게 하기 */
}

#toast button {
    margin-top: 10px;
    background-color: white;
    color: #FF8C00;
    border: none;
    padding: 10px 15px;
    border-radius: 5px;
    font-size: 14px;
    cursor: pointer;
}

#toast button:hover {
    background-color: #e67e00;
    color: white;
}
	
    </style>
</head>
<body>
<header>
    <h1>
        <a href="${pageContext.request.contextPath}/posteat/main.do" style="text-decoration: none; color: white;">
            POST EAT
        </a>
    </h1>
    <div class="nav-buttons">
        <!-- 레시피 작성 버튼 -->
        <button onclick="location.href='${pageContext.request.contextPath}/recipe/add.do'">+</button>
        <!-- 내 프로필 보기 버튼 -->
        <button onclick="location.href='${pageContext.request.contextPath}/posteat/profile.do'">👤</button>
        <!-- 로그아웃 버튼 (로그인 상태에서만 표시) -->
        <c:if test="${!empty sessionScope.user}">
            <button class="logout-button" onclick="location.href='${pageContext.request.contextPath}/posteat/logout.do'">🚪</button>
        </c:if>
    </div>
</header>
<div class="container">
    <div class="recipe-title">
        ${recipe.title}
    </div>
    <div class="recipe-info">
        작성자: ${recipe.userID} | 등록일: ${recipe.regdate}
    </div>
    <div class="recipe-content-container">
        <div class="recipe-content">
            ${recipe.description}
        </div>
    </div>

    <!-- 좋아요 섹션 -->
	<div class="like-section">
    	<button onclick="location.href='${pageContext.request.contextPath}/recipe/recommend.do?recipeID=${recipe.recipeID}'">좋아요</button>
    	<span class="like-count">${recommendationCount}</span>
	</div>


    <!-- 댓글 입력 섹션 -->
	<div class="comment-form-container">
    <form class="comment-form" action="${pageContext.request.contextPath}/recipe/addComment.do" method="post">
        <input type="hidden" name="recipeID" value="${recipe.recipeID}">
        <textarea name="comment" placeholder="댓글을 입력하세요"></textarea>
		<button type="submit">등록</button>
    </form>
</div>

<!-- 토스트 메시지 HTML -->
<div id="toast">
    <p id="toast-message">좋아요를 이미 눌렀습니다.</p>
    <button onclick="hideToast()">확인</button>
</div>


	<a class="back-button" href="${pageContext.request.contextPath}/recipe/list.do">목록으로</a>
</div>

<script>
    // 토스트 메시지 표시
    function showToast(message) {
        const toast = document.getElementById('toast');
        const toastMessage = document.getElementById('toast-message');
        toastMessage.textContent = message;  // 메시지 내용을 전달받아 변경
        toast.className = 'show'; // 토스트 메시지 표시
    }

    // 토스트 메시지 숨기기
    function hideToast() {
        const toast = document.getElementById('toast');
        toast.className = ''; // 토스트 숨기기
    }

    // 이미 추천을 했을 때 토스트 메시지 표시
    <c:if test="${not empty error}">
        showToast("${error}");  // 이미 추천을 한 경우
    </c:if>
</script>
</body>
</html>
