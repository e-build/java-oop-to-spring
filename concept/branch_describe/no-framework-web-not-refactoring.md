<i><u>***목차***</i></u>


# 요구사항
### HTTP 웹서버
***1. index.html 응답하기***
- `InputStream`을 한 줄 단위로 읽기위해 `BufferedReader`를 활용
- `br.readLine()` 메서드로 HTTP 요청 정보 읽어들임
- HTTP 요청정보의 첫 번째 라인에서 요청 URL 추출
- 요청 URL에 해당하는 파일을 webapp 디렉토리에서 읽어와서 전달. (파일 데이터를 byte[]로 읽는 방법 탐색)

***2. 사용자 입력 데이터 처리 :: GET***
- GET 요청을 통해 서버로 전달된 URL의 쿼리스트링 값을 파싱하여 Database에 저장

***3. 사용자 입력 데이터 처리 :: POST***
- POST 요청을 통해 서버로 전달된 Request Body의 쿼리스트링 값을 파싱하여 Database에 저장
- POST로 전달된 데이터는 HTTP 본문에 담김
- HTTP 본문은 HTTP 헤더 이후 빈 공백을 가지는 한 줄 다음부터 시작
- 본문의 길이는 헤더의 `Content-Length` 값

***4. 리다이렉트 구현***
- HTTP 응답 헤더의 `status code`를 302로 전달하여 리다이렉트 구현

***5. 쿠키 적용***
- 로그인 성공 여부에 따라 쿠키에 값을 세팅
  ex) login=true

***6. CSS 파일 지원하기***
- 응답헤더의 `Content-Type`을 `text/html`로 보내면 브라우저는 응답 본문을 HTML 파일로 인식함.
- CSS 파일인 경우 `Content-Type`을 `text/css`로 전송
- 다른 파일형식도 지원하고 싶다면 응답파일의 확장자나 요청 헤더의 `Accept` 활용

### 비즈니스 로직
1. 회원가입 페이지
2. 회원가입 API 구현
3. 로그인 페이지
4. 로그인 API 구현
5. 로그아웃 API 구현
6. 레시피 리스트 페이지 
7. 레시피 리스트 데이터 조회 API 구현
8. 레시피 상세 페이지
9. 레시피 상세 데이터 조회 API 구현

