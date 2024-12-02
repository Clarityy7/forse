# POSTEAT

POSTEAT은 회원 간에 요리 레시피를 공유하고 소통할 수 있는 웹 애플리케이션입니다. 사용자들은 자신만의 레시피를 작성하고, 다른 회원들의 레시피를 확인하며, 댓글과 추천을 통해 상호 작용할 수 있습니다.

---

## 목차

- [프로젝트 소개](#프로젝트-소개)
- [주요 기능](#주요-기능)
- [사용 기술](#사용-기술)
- [설치 및 실행 방법](#설치-및-실행-방법)
  - [사전 요구 사항](#사전-요구-사항)
  - [프로젝트 클론](#프로젝트-클론)
  - [데이터베이스 설정](#데이터베이스-설정)
  - [프로젝트 설정](#프로젝트-설정)
  - [애플리케이션 배포 및 실행](#애플리케이션-배포-및-실행)
- [초기 데이터 삽입](#초기-데이터-삽입)
- [사용 방법](#사용-방법)
- [문의](#문의)

---

## 프로젝트 소개

POSTEAT은 요리에 관심 있는 사용자들이 모여 레시피를 공유하고, 서로의 레시피에 댓글과 추천을 남길 수 있는 커뮤니티 웹 사이트입니다.

---

## 주요 기능

### 회원 관리
- 회원가입
- 로그인 및 로그아웃
- 회원 정보 조회 및 수정

### 레시피 관리
- 레시피 등록, 수정, 삭제
- 레시피 목록 조회
- 레시피 상세 정보 보기
- 레시피 추천 기능

### 댓글 기능
- 레시피에 댓글 작성
- 작성한 댓글 삭제

---

## 사용 기술

### 프로그래밍 언어 및 프레임워크
- Java SE 8
- Java Servlet
- JSP (JavaServer Pages)

### 데이터베이스
- MySQL

### 웹 서버
- Apache Tomcat 10.1

### 빌드 및 관리 도구
- Maven

---

## 설치 및 실행 방법

### 사전 요구 사항
- JDK 8 이상 설치
- Apache Tomcat 10.1 설치
- MySQL 설치 및 설정
- Maven 설치 (필요한 경우)

### 프로젝트 클론

```
git clone https://github.com/Clarityy7/posteat.git
```
### 데이터베이스 설정
- MySQL 데이터베이스 생성
```
CREATE DATABASE posteat_db;
```
- 데이터베이스 테이블 생성
```
CREATE TABLE user (
    id VARCHAR(50) PRIMARY KEY,
    password VARCHAR(255) NOT NULL,
    nickname VARCHAR(45),
    regdate DATETIME
);

CREATE TABLE recipe (
    recipeID INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    imagePath VARCHAR(255),
    userID VARCHAR(50) NOT NULL,
    regdate DATETIME NOT NULL,
    FOREIGN KEY (userID) REFERENCES user(id)
);

CREATE TABLE comment (
    commentID INT AUTO_INCREMENT PRIMARY KEY,
    recipeID INT NOT NULL,
    userID VARCHAR(50) NOT NULL,
    content TEXT NOT NULL,
    regdate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (recipeID) REFERENCES recipe(recipeID),
    FOREIGN KEY (userID) REFERENCES user(id)
);

CREATE TABLE recipe_recommendation (
    recipeID INT NOT NULL,
    userID VARCHAR(50) NOT NULL,
    recommendDate DATETIME DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (recipeID, userID),
    FOREIGN KEY (recipeID) REFERENCES recipe(recipeID),
    FOREIGN KEY (userID) REFERENCES user(id)
);
```

### 프로젝트 설정
src/main/resources/db.properties 파일에서 데이터베이스 접속 정보를 설정합니다.
```
db.driver=com.mysql.cj.jdbc.Driver
db.url=jdbc:mysql://localhost:3306/posteat_db?serverTimezone=UTC
db.username=YOUR_DB_USERNAME
db.password=YOUR_DB_PASSWORD
```

### 애플리케이션 배포 및 실행
- Tomcat 서버에 프로젝트 배포
IDE(Eclipse, IntelliJ 등)에서 Tomcat 서버를 추가하고, POSTEAT 프로젝트를 배포합니다.
또는 WAR 파일을 생성하여 Tomcat의 webapps 디렉토리에 배포합니다.

- 서버 실행
Tomcat 서버를 실행합니다.

- 애플리케이션 접속
웹 브라우저에서 다음 주소로 접속합니다:
```
http://localhost:8080/posteat/posteat/main.do
```

---

## 초기 데이터 삽입
샘플 레시피 데이터
```
INSERT INTO user (id, password, nickname, regdate) VALUES ('testuser', 'testpass', '테스트유저', NOW());

INSERT INTO recipe (title, description, userID, regdate) VALUES ('맛있는 파스타', '파스타 만드는 법...', 'testuser', NOW());
```

---

## 사용 방법
- 회원가입 및 로그인
메인 페이지에서 회원가입을 진행합니다.
가입한 계정으로 로그인합니다.

- 레시피 작성
로그인 후 레시피 작성 페이지로 이동하여 새로운 레시피를 등록합니다.

- 레시피 조회 및 추천
레시피 목록에서 원하는 레시피를 클릭하여 상세 정보를 확인합니다.
마음에 드는 레시피에 추천을 누를 수 있습니다.

- 댓글 작성 및 삭제
레시피 상세 페이지에서 댓글을 작성할 수 있습니다.
본인이 작성한 댓글은 삭제할 수 있습니다.

- 레시피 수정 및 삭제
본인이 작성한 레시피에 한해 수정 및 삭제가 가능합니다.

---

## 문의
개발자 이메일: phm122335@gmail.com
