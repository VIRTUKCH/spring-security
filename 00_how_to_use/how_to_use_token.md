# Spring Security & JWT 사용법..😉

## 1. 회원가입

### 1. /signup 으로 회원가입
```http request
### 회원가입
POST http://localhost:7777/signup
Content-Type: application/json

{
  "userId" : "kch4731@gmail.com",
  "userPass" : "kch4731@gmail.com",
  "userName" : "KCH",
  "userEmail" : "kch4731@gmail.com",
  "role" : "USER"
}
```

## 2. 로그인
```http request
### 로그인
POST http://localhost:7777/login
Content-Type: application/json

{
  "id" : "kch4731@gmail.com",
  "pass" : "kch4731@gmail.com"
}
```

로그인 요청을 보내시면 response 로 매 요청에 꼭 붙여 주셔야 하는 암호 같은 게 같이 나갑니다.

암호는 response 의 header 에 Authorization 라는 이름(Key) 로 나가고, BEARER 라는 접두어? 뒤에 값이 길게 붙습니다.

로그인 이후, 매 Request 를 할 때마다 Header 에 Authorization 이라는 Key 와 함께 Value 에 BEARER ~~~~~ 를 담아 주시면 됩니다.


## 3. 로그인 이후 접근
```http request
### 테스트
GET http://localhost:7777/test
Content-Type: application/json
Authorization: BEARER eyJkYXRlIjoxNzI2MTE3MTk5Njg4LCJ0eXBlIjoiand0IiwiYWxnIjoiSFMyNTYifQ.eyJSb2xlIjoiVVNFUiIsInN1YiI6Im9oZ2lyYWZmZXJzIHRva2VuIDogMSIsInVzZXJFbWFpbCI6ImtjaDQ3MzFAZ21haWwuY29tIiwidXNlck5hbWUiOiJLQ0giLCJleHAiOjE3MjYyMDM1OTl9.ZX4Gt8cLYCxtYtAQ-r_UgzfkuVzI-gjs4ljlSvfgsl4
```