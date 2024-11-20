CREATE TABLE Recipe (
	recipeID VARCHAR(255) PRIMARY KEY, -- 레시피 ID, PK
	title VARCHAR(255) NOT NULL, -- 사용자 닉네임
	description TEXT NOT NULL , -- 레시피 설명
	imagePath VARCHAR(255) -- 이미지 url 주소
);