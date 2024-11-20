CREATE TABLE User (
	id VARCHAR(255) PRIMARY KEY, -- 사용자ID, PK
	username VARCHAR(255) NOT NULL, -- 사용자 닉네임
	password VARCHAR(255) NOT NULL, -- 사용자 비밀번호
	regdate TIMESTAMP DEFAULT CURRENT_TIMESTAMP -- 계정 생성 시간
);