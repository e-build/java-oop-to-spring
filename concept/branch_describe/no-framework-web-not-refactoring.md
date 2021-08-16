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

***7. HttpSession 구현하여 로그인 완성하기***
- 서블릿의 HttpSession API 중 몇가지 메서드를 직접 구현해볼 것
  - `String getId()` : 
    현재 세션에 할당되어 있는 고유한 세션 아이디를 반환
  - `void setAttribute(String name, Obejct value)` : 
    현재 세션에 value 인자로 전달되는 객체를 name 인자 이름으로 저장
  - `Object getAttribete(String name)` : 
    현재 세션에 name인자로 저장되어 있는 객체의 값을 반환
  - `void removeAttribete(String name)` : 
    현재 세션에 name인자로 저장되어 있는 객체의 값을 삭제
  - `void invalidate()` : 
    현재 세션에 저장되어 있는 모든 값을 삭제
- 쿠키와 세션을 활용하여 로그인 기능 완성하기
  - 세션은 클라이언트와 서버 간에 상태 값을 공유하기 위해 고유한 아이디를 활용하고, 이 고유한 아이디는 쿠키를 통해 공유한다.
  1. 고유한 아이디 생성
     - UUID 활용
  2. 쿠키를 활용해 아이디 전달
  3. 모든 클라이언트의 세션 데이터에 대한 저장소 추가
     서버는 다수의 클라이언트 세션을 지원해야 한다. 
     따라서 모든 클라이언트의 세션을 관리할 수 있는 저장소가 필요하다. 
     이 저장고는 모든 세션을 매번 생성하는 것이 아니라 한번 생성한 후 재사용 할 수 있어야 한다.
    ```java
    public class HttpSessions{
      private static Map<String, HttpSession> sessions = new HashMap<String, HttpSession>();
      public static HttpSession getSession(String id){
          ... 
        }
    }
    ```
  4. 클라이언트별 세션 저장소 추가
     - HttpRequest 에서 클라이언트에 해당하는 HttpSession에 접근 할 수 있도록 메서드 추가
    ```java
    public class HttpRequest {
        [...]
      public HttpSession getSession() {
            ...
      }
    }
    ```

***8. 데이터베이스 연결하여 저장하기***
- 필요한 의존성 추가
- JDBC API를 사용하여 H2 디비 커넥션.
- JDBC 로 User, Recipe 관련 비즈니스 로직의 데이터 퍼시스턴스 수행
  - 회원가입, 로그인, 로그아웃, 레시피 CRUD

***9. Ajax를 활용해 새로고침 없이 데이터 갱신하기***
- 레시피 등록, 레시피 리스트 조회, 레시피 상세 조회 Ajax를 활용해 구현
- HTTP 응답 메시지
  - Content-Type : application/json; charset=UTF-8
  - 응답본문에 queryString, json 형태의 데이터 전달하고, 클라이언트에서 파싱하여 사용함

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


