# 8,9,10,11강 - 스프링부트 동작원리

[8] HTTP가 무엇일까요? 정확히 알아야 해요

[9] 톰켓이란 무엇인가요?

[10] 서블릿 객체의 생명주기가 궁금해요!

[11] 웹 배포서술사(web.xml)에 대해서 알려줘요!

[스프링부트 with JPA 3강 - Springboot 동작원리!](https://getinthere.tistory.com/11)

---

# 8강 - HTTP가 무엇인가요?

## ✅ HTTP란?

통신규약, 웹서비스에서 클라이언트와 서버 간의 정보를 요청하고 응답받기 위한 프로토콜

### [ 특징 ]

**1.  단방향 통신** (클라이언트 요청-서버 응답)

**2.  무상태 Stateless 프로토콜 지향** 

- 서버가 클라이언트의 연결/상태 정보를 보존하지 않는다.
- 장점 → 클라이언트 요청에 어떤 서버가 응답 해도 상관 없기 때문에 서버 확장성이 높음
- 단점 → 클라이언트가 많은 양의 추가 데이터를 전송해야 함

**3.  비연결성**

- 클라이언트가 서버에 요청하고 응답을 받으면 TCP/IP 연결을 끊어버린다.
- 서버 리소스 효율적으로 관리, 수많은 클라이언트 요청 대응 가능

**4.  HTTP 메서드 사용**





## ✅ 소켓 통신이란?

네트워크 상에서 동작하는 프로그램 간 통신의 종착점(End Poing), 접속의 끝 부분으로 운영체제가 갖고있다.

TCP/IP 통신에 사용 된다.

### [ 특징 ]

**1.  양방향 통신** (클라이언트, 서버 모두 요청-응답)

**2.  연결성** : 계속 연결을 유지한다. 

- 단점 → 리소스 소모 발생

**3.  운영체제 자체 제공**

**4.  스트리밍, 실시간 채팅 등 실시간 데이터 송.수신에 유용**

### [ 연결 방식 ]

![스크린샷 2024-07-01 18.10.03.png](%E1%84%89%E1%85%B3%E1%84%91%E1%85%B3%E1%84%85%E1%85%B5%E1%86%BC%E1%84%87%E1%85%AE%E1%84%90%E1%85%B3%20%E1%84%83%E1%85%A9%E1%86%BC%E1%84%8C%E1%85%A1%E1%86%A8%E1%84%8B%E1%85%AF%E1%86%AB%E1%84%85%E1%85%B5%20c84c6c5eeef2457db2ed9849025f3929/%25E1%2584%2589%25E1%2585%25B3%25E1%2584%258F%25E1%2585%25B3%25E1%2584%2585%25E1%2585%25B5%25E1%2586%25AB%25E1%2584%2589%25E1%2585%25A3%25E1%2586%25BA_2024-07-01_18.10.03.png)

IP주소와 포트번호를 넣고 연결이 되는 순간 통신이 가능하다.

(EX) 소켓 A(포트번호 5000)와 클라이언트 B, C가 있다고 가정하자.

- 다중 통신을 위해 메인소켓 (5000)은 통신 연결만을 위해 두고 새로운 스레드(5001, 5002 …)를 생성해 각자 포트에서 메세지를 주고 받는다.
- 연결상태를 유지해 부하가 크지만 새로 연결할 필요 없다.


---

# 9강 - 톰켓이란 무엇인가요?

### ✚ HTTP 통신

ex ) 갑의 컴퓨터에 있는 영상을 WWW로 연결 되어 있으니 을이 요청할 수 있다. 이때 갑은 웹서버이며 을이 원하는 데이터를 가지고 있다.

1. 을 → 갑에게 데이터를 받기 위해  `Request` : IP 주소 (URL) 필요
2. 갑 → 을에게 요청한 정보를 토대로 *Static자원을 `Response` 
3. 갑은 을이 요청하지 않으면 아무 것도 알 수 없음. 따라서 을의 주소를 알기 위해 연결이 지속되는 소켓을 사용

### ✚ WebServer

웹 브라우저로부터 HTTP 요청을 받아 정적인 컨텐츠를 전달

(*Static 정적파일 : html, javascript, css, image 물리적인 파일 등)

- 하드웨어에서의 웹서버
    - 웹 서버 소프트웨어와 웹 사이트의 구성 요소 파일을 저장하는 `컴퓨터` 
    (예 : HTML 문서, 이미지, CSS 스타일 시트 및 JavaScript 파일)

- 소프트웨어에서의 웹서버
    - `HTTP 서버`
        - URL(웹주소) 및 HTTP(프로토콜 주소)를 이해하는 소프트웨어
        - 저장하는 웹 사이트의 도메인 이름을 통해 액세스
        - 호스팅 된 웹 사이트의 콘텐츠를 최종 사용자의 장치로 전달

## ✅ Apache (아파치)

Apache HTTP Server

- 클라이언트에서 요청하는 HTTP요청을 처리하는 **웹서버**를 의미
- **정적타입**(HTML, CSS, 이미지 등)의 데이터만 처리
- 정적타입이 아니라면 `⇒ 톰켓 등장` (제어권 넘김)

## ✅ Tomcat (톰켓)

**톰캣 WAS**(Web Application Server)

- 정적타입만 처리 가능한 아파치 서버와는 다르게
 DB연결, 다른 응용프로그램과 상호 작용 등 **동적인 기능 사용 가능**

### 톰켓 진행 방식

1. 자바 서블릿 실행해  .jsp 파일을 읽어들인다.
2. 읽어들인 동적 파일을 컴파일 한다.
3. 컴파일 된 결과를 html 문서로 재구성 한다.
4. 재구성 된 html 문서를 아파치에게 돌려주고 아파치는 서버에 response

---

# 10강 - 서블릿 객체의 생명주기가 궁금해요

## ✅  서블릿 (Servlet) 이란?

동적 웹 페이지를 만들 때 사용되는 JAVA 기반의 Web Application Programming 기술

웹 요청-응답의 흐름을 간단한 메서드 호출만으로 체계적으로 다뤄준다.

서블릿은 서버에서 실행 되다가 웹 브라우저에서 요청을 하면 해당 기능을 수행하고 다시 웹 브라우저에 결과를 전송 해준다.

ex) 로그인 시도를 할때 : 

`클라이언트가 입력한 아이디, 비밀번호 → 서버가 확인하고 결과를 응답`

`이러한 역할을 수행해주는 것이 서블릿이다.` 

### ✚ 서블릿 (Sevlet) 주요 특징

1. 클라이언트의 Request (요청)에 대해 동적으로 작동하는 웹 어플리케이션 컴포넌트
2. 기존의 정적 웹 프로그램 문제점을 보완해 → 동적인 여러 기능 제공
3. JAVA의 스레드를 이용해 동작
4. MVC(Model, View, Controller) 패턴에서 Controller로 이용
5. 컨테이너에서 실행
6. 보안 기능 적용 쉬움

### ✚ 서블릿 (Sevlet) 동작 과정

![Untitled](%E1%84%89%E1%85%B3%E1%84%91%E1%85%B3%E1%84%85%E1%85%B5%E1%86%BC%E1%84%87%E1%85%AE%E1%84%90%E1%85%B3%20%E1%84%83%E1%85%A9%E1%86%BC%E1%84%8C%E1%85%A1%E1%86%A8%E1%84%8B%E1%85%AF%E1%86%AB%E1%84%85%E1%85%B5%20c84c6c5eeef2457db2ed9849025f3929/Untitled.png)

클라이언트 요청 → 웹 서버 → 톰캣과 같은 WAS에 위임

WAS → 각 요청 별 서블릿 실행 → 서블릿 요청 기능 수행 후 결과 반환 → 클라이언트 

```markdown
1. 클라이언트 요청
2. HttpServletRequest, HttpServletResponse 객체 생성
3. Web.xml이 어느 서블릿에 대해 요청한 것인지 탐색
4. 해당하는 서블릿에서 service() 메소드 호출 
5. doGet() 또는 doPost() 호출 
6. 동적 페이지 생성 후 ServletResponse 객체에 응답 전송
7. HttpServletRequest, HttpServletResponse 객체 소멸
```

## ✅ 서블릿 컨테이너 (톰켓)

![Untitled](%E1%84%89%E1%85%B3%E1%84%91%E1%85%B3%E1%84%85%E1%85%B5%E1%86%BC%E1%84%87%E1%85%AE%E1%84%90%E1%85%B3%20%E1%84%83%E1%85%A9%E1%86%BC%E1%84%8C%E1%85%A1%E1%86%A8%E1%84%8B%E1%85%AF%E1%86%AB%E1%84%85%E1%85%B5%20c84c6c5eeef2457db2ed9849025f3929/Untitled%201.png)

클라이언트가 요청하면 서블릿컨테이너 (톰켓)이 요청을 받고 객체를 생성한다.

클라이언트의 최초요청이 아닐 시, 이미 생성된 객체를 재사용 한다. 

--

정적인 파일 (html, css, png 등)을 요청하게 되면 톰켓이 아닌 어파치가 일을 한다.

하지만 스프링은 정적인 파일, URL(자원접근)과 같은 특정한 파일을 요청할 수 없다.

스프링은 URI(식별자 접근) 통해 요청을 해야하는데, 요청시에는 무조건 자바를 거쳐하며 자바는 톰켓이 제어하기 때문에 아파치의 통제권이 톰켓에게 넘어간다. 

`→  URL 구조 : [http://naver.com /](http://naver.com/)a.png`

`→  URI 구조 :  [http://naver.com](http://naver.com/picture/a) /picture/a` 

--

## ✅ 서블릿 컨테이너의 객체 생성 방식

```markdown
[1] 클라이언트 : 최초 요청 (First Request)
		
		(1) Java 자원 -> 서블릿컨테이너 (톰켓) 실행
						(정적 자원 -> 아파치 실행)
				
				
		(2) 서블릿 객체 생성 <new!>
		
				(2-1) init() : 초기화 호출
				
				(2-2) 스레드1 생성
							(2-2-1) Service() : 서비스 호출 
										(-> Post, Get, Put, Delete)
				(2-3) get() : DB연결, 데이터 응답 등 
				
				
[2] 클라이언트 : 최초 요청이 아닐 때 (Re -Request)

		(1) Java 자원 -> 서블릿컨테이너 (톰켓) 실행
		
		(2) 서블릿 객체 <재사용!> ㅡ ㅡ ㅡ ㅡ ㅡ ㅡ ㅡ ㅡ ㅡ ㅡ ㅡ ㅡ 
		|
		|		(2-1) 스레드2 생성 
		|					(2-2-1) Service() : 서비스 호출 
		|								(-> Post, Get, Put, Delete)
		|								                        
		|		(2-3) get() : DB연결, 데이터 응답 등 
		|
		 ㅡ ㅡ ㅡ ㅡ ㅡ ㅡ ㅡ ㅡ ㅡ ㅡ ㅡ ㅡ ㅡ ㅡ ㅡ ㅡ ㅡ ㅡ ㅡ ㅡ ㅡ 
```

<aside>
<img src="https://www.notion.so/icons/forward_gray.svg" alt="https://www.notion.so/icons/forward_gray.svg" width="40px" /> EX) 톰켓 기본 설정에 있는 스레드를 Auto로 설정했을 때 스레드 20개까지 생성이 가능하고, 
Class A 내부에 hello() 메서드가 생성되어 있다. 

(스레드 개수는 컴퓨터 성능에 따라 달라진다.)

</aside>

---

⇒ 
new 메서드 생성 시 hello() 메서드가 heap에 뜨게 된다.

각각의 스레드가 요청으로 메서드를 "호출" 하게 되면 메서드들은 독립적으로 매서드 스택 공간에 각각 저장된다. 

### `서블릿 객체가 하나만 있어도 각각의 스레드가 독립적으로 호출해 사용할 수 있기 때문에 서블릿 객체 재사용이 가능한 것이다!`


---

스레드가 종료하는 시점 =  Response 받은 시점

⇒ 만일 20개의 스레드가 다 생성이 되고 21번째 스레드가 생성 되려면, 다른 스레드가 종료하기를 대기한다.

이때, 스레드 1이 종료하게 되면, 메모리에서 날리지 않고 21번째 스레드가 스레드1을 재사용 한다. = `Pooling 기법`

### 

## ✅ 서블릿 객체 생명주기 정리

< 최소 생성 > 

1. Request
2. 서블릿 객체 생성
3. 필요한 메서드 호출 (get(), Post() 등등)
4. 스레드 1 생성
5. Response
6. 스레드 1 제거하지 않고 재사용 대기.

< 재사용 >

1. 클라이언트의  25명 동시 접근 Request
2. 필요한 메서드 호출 (get(), Post() 등등)
3. 스레드 2~20 생성
4. 만들어둔 스레드 1은 재사용하고, 나머지 5명 대기
5. Response
6. 나머지 5명은 완료된 스레드 재사용

### ✚ Pooling + Scale-Up + Scale-Down

- Pooling
- Scale-Up
    - 기존 서버 사양 업그레이드 → 시스템 확장
- Scale-Down
    - 서버 여러대 추가 → 시스템 확장


--
"여러개의 Bean을 가지고 있는 스프링 컨테이너가 서블릿 컨테이너 뒤쪽에서 서블릿을 통해서 웹으로 들어온 요청을 받아서 **처리하고 결과를 다시 서블릿 컨테이너한테** 다시 넘겨준다." 

웹 요청 -> 서블릿 컨테이너 -> 스프링 컨테이너 -> 서블릿 컨테이너 -> 웹 응답

---

# 11강 - 웹배포 서술자 (web.xml)에 대해서 알려줘요

## ✅ WEB.XML

- **xml 파일 형식의 WebApplication의 설정을 위한 파일**
    - **`DispatcherServlet`**
    - **`ContextLoaderListener`**
    - **`Filter`**
- **DD : Deployment Descriptor (배포설명자)**
- 모든 Web application은 반드시 하나의 web.xml 파일을 가져야 한다.
- DD는 **Web Application 실행 시 메모리에 로드된다.**
- Web-INF 폴더 아래에 있다.
src - libraries - **WebContent (Web-INF)**
    
    ![Untitled](%E1%84%89%E1%85%B3%E1%84%91%E1%85%B3%E1%84%85%E1%85%B5%E1%86%BC%E1%84%87%E1%85%AE%E1%84%90%E1%85%B3%20%E1%84%83%E1%85%A9%E1%86%BC%E1%84%8C%E1%85%A1%E1%86%A8%E1%84%8B%E1%85%AF%E1%86%AB%E1%84%85%E1%85%B5%20c84c6c5eeef2457db2ed9849025f3929/Untitled%202.png)
    

### ✚ DD (배포 설명자)

- Application의 클래스, 리소스, 구성 및 웹 서버가 이를 이용해 웹 요청을 처리하는 방법을 기술하는 곳이다.
- 배포 할 때 서블릿의 정보를 설명한다.
- 브라우저가 Java Servlet에 접근하기 위해서는 WAS(Ex. Tomcat)에 필요한 정보 를 알려줘야, 해당하는 Servlet을 호출할 수 있다.
    - 배포할 서블릿이 무엇인지
    - 해당 서블릿이 어떤 URL에 매핑 되는가

### ✚ 서블릿 (Sevlet) 동작 과정

서블릿은 웹 컨테이너(Sevlet Containter)에 의해서 실행.

![Untitled](%E1%84%89%E1%85%B3%E1%84%91%E1%85%B3%E1%84%85%E1%85%B5%E1%86%BC%E1%84%87%E1%85%AE%E1%84%90%E1%85%B3%20%E1%84%83%E1%85%A9%E1%86%BC%E1%84%8C%E1%85%A1%E1%86%A8%E1%84%8B%E1%85%AF%E1%86%AB%E1%84%85%E1%85%B5%20c84c6c5eeef2457db2ed9849025f3929/Untitled.png)

```markdown
1. 클라이언트 요청
2. HttpServletRequest, HttpServletResponse 객체 생성
3. Web.xml이 어느 서블릿에 대해 요청한 것인지 탐색
4. 해당하는 서블릿에서 service() 메소드 호출 
5. doGet() 또는 doPost() 호출 
6. 동적 페이지 생성 후 ServletResponse 객체에 응답 전송
7. HttpServletRequest, HttpServletResponse 객체 소멸
```

## ✅ **web.xml 을 통해 제공할 수 있는 구성 정보들**

---

- ServletContext의 초기 파라미터
- Session의 유효시간 설정
- Servlet/JSP에 대한 정의
- Servlet/JSP 매핑
- Mime Type 매핑
- Welcome File list
- Error Pages 처리
- 리스너/필터 설정
- 보안

---

```markdown
[Ex]

엄청나게 큰 성의 입구가 있다. 그리고 입구에는 문지기가 한 명있다. 
이 문지기는 관리자가 문서를 주고, 그 문서대로 일을 하라고 한다. 
일은 관리자에 따라 달라진다. 

( 문서 = web.xml, 관리자 = 서블릿(?) )
```

## 1.  ServletContext의 초기 파라미터

- 자바 웹 앱이 초기화될 때 전달할 정보를 정의한다.
- 초기 파라미터는 한번 설정하면 어플리케이션 전반에서 유지가 되고 어디서든지 사용할 수 있다.
- `web.xml` 파일에서 `<context-param>` 태그를 사용하여 정의

```xml
<context-param>
    <param-name>databaseUrl</param-name>
    <param-value>jdbc:mysql://localhost:3306/mydatabase</param-value>
</context-param>
```

- `param-name` : 초기 파라미터의 이름,  `param-value` : 초기 파라미터의 값 URL

```markdown
암구호가 “hi” 라면, 문지기를 통해 들어온 a는 내부에서 암구호를 대답해 유지되지만 
침입한 b는 암구호를 모르기 때문에 추방 당한다.
```

## 2.  Session의 유효시간 설정

- 세션의 유효기간은 사용자 세션이 유지되는 시간이다.
- HTTP 세션에 대한 옵션을 구성한다.
- 일정 시간 동안 사용자가 활동하지 않은 경우 세션이 자동으로 만료된다.

```xml
<session-config>
    <session-timeout>30</session-timeout>
</session-config>
```

- `session-timeout` : 세션 유효시간(분 단위)

```markdown
[Ex]

인증을 통해 들어오는게 Session 이며, 유효시간을 설정한다.
문지기를 통해 들어온 a의 유효시간이 3일이면, 3일 후 a는 추방 당한다. 

이때 유효시간을 연장하고 싶으면 문지기에게 가서 요청하게 되면,
세션이 초기화되어 연장이 된다.
```

## 3.  Servlet/JSP에 대한 정의 & 매핑

### 정의

- 서블릿을 정의하여 특정 URL 패턴에 대해 → 특정 서블릿이 처리되도록 설정

```xml
<servlet>
    <servlet-name>exampleServlet</servlet-name>
    <servlet-class>com.example.ExampleServlet</servlet-class>
</servlet>
```

- `servlet-name` : 서블릿의 이름 , `servlet-class` : 서블릿 클래스의 완전한 이름

### 매핑

- 서블릿을 URL 패턴과 연결하여 요청이 들어올 때 
→ 어떤 서블릿이 해당 요청을 처리할지 지정
- 이 정보를 통해 웹 어플리케이션은 특정한 URL을 처리할 수 있다.

```xml
<servlet-mapping>
    <servlet-name>exampleServlet</servlet-name>
    <url-pattern>/example</url-pattern>
</servlet-mapping>
```

- `servlet-name` : 매핑할 서블릿의 이름, `url-pattern` : 매핑할 URL 패턴

```markdown
[Ex]

가,나,다,라 라는 건물이 있는데 이때 a의 목적지가 “다” 라면, 
문지기는 해당 “다”의 위치가 어디인지 알려주는 것이 매핑이다.
 
= 즉, 요청한 자원, 로케이션, 식별자가 어디 위치에 있는지 정확히 알려주는 것

가는 서울 , 나는 대전, 다는 부산, 라는 정확한 위치가 web.xml에 정의되어 있다.
```

## 4.  Mime Type 매핑

- 들고 올 데이터의 타입
- 파일 확장자와 마임 타입 사이 매핑을 구성한다. → 특정 파일 형식을 올바르게 처리할 수 있도록.

```xml
<mime-mapping>
    <extension>html</extension>
    <mime-type>text/html</mime-type>
</mime-mapping>

<mime-mapping>
    <extension>jpg</extension>
    <mime-type>image/jpeg</mime-type>
</mime-mapping>

<mime-mapping>
    <extension>pdf</extension>
    <mime-type>application/pdf</mime-type>
</mime-mapping>
```

- `extension` : 파일 확장자 , `mime-type` : MIME 타입

```markdown
[Ex]

a가 쌀을 들고왔다면, 이때 mime type은 쌀이다. 
그럼 문지기는 쌀 창고로 보내게 되고, 해당 쌀(데이터)를 가공한다. 

만일 성에 아무것도 들고 오지 않았다면 이건 구경을 위함 것이므로 get방식 사용
get() 방식: select 사용 : 데이터를 가져오지 않음 
```

- Mime type이 일치 하지 않음 = Error 처리
- 종류가 굉장히 많음

## 5.  Welcome File list

- 관리자가 누군지에 따라 설정하기 따름.
- 기본적으로 표시할 파일 목록을 정의하여 사용자가 특정 디렉토리에 접근할 때 자동으로 표시되는 파일을 지정한다.

```xml
<welcome-file-list>
    <welcome-file>index.html</welcome-file>
    <welcome-file>index.jsp</welcome-file>
</welcome-file-list>
```

- `welcome-file` : 디렉토리 접근 시 기본으로 표시할 파일

```markdown
[Ex]

아무 이유 없이 들어온 애들은 광장으로 보내라.
```

## 6.  Error Pages 처리

- 특정 오류가 발생했을 때 사용자에게 보여줄 커스템 엘 페이지를 지정한다.
- 404나 500 응답 시 노출되는 에러 페이지를 설정한다.

```xml
<error-page>
    <error-code>404</error-code>
    <location>/error/404.html</location>
</error-page>

<error-page>
    <exception-type>java.lang.Throwable</exception-type>
    <location>/error/error.html</location>
</error-page>
```

- `error-code` : HTTP 상태 코드 , `exception-type` : 예외 유형, `location` : 에러 페이지의 위치

```markdown
[Ex]

정의되지 않은 데이터(?)를 들고온 애들은 다 error page로 보낸다.
```

## 7.  리스너/필터 설정

### 필터

- 요청과 응답을 가로채 공통 로직을 적용하는 필터를 정의한다.
- 애플리케이션의 특정 이벤트나 요청을 처리하기 위해 리스너와 필터를 설정한다.

```xml
<filter>
    <filter-name>LoggingFilter</filter-name>
    <filter-class>com.example.LoggingFilter</filter-class>
</filter>

<filter-mapping>
    <filter-name>LoggingFilter</filter-name>
    <url-pattern>/*</url-pattern>
</filter-mapping>
```

- `filter-name` : 필터의 이름, `filter-class` : 필터 클래스의 완전한 이름, 
`url-pattern` : 필터가 적용될 URL 패턴

```xml
<listener>
    <listener-class>com.example.ExampleListener</listener-class>
</listener>
```

- `listener-class` : 리스너 클래스의 완전한 이름

```markdown
[Ex]

필터 = 솎음
만일 a가 b나라 소속이면 들어오지 못하게 문에서 거른다.
만일 a가 총을 들고 왔는데 총소지가 안되는 국가이므로, 총을 뺏은 후에 a를 들여보낸다.

리스너
문지기를 대신한 대리인. 
술 잘먹는 사람을 찾아내라는 해당 조건에 부합하는 것을 찾아내고 데려간다.
```

## 8.  보안 제약

- 특정한 리소스에 접근 할 때 인증 혹은 인가를 요구하도록 설정한다.
- 애플리케이션의 보안을 위해 특정 URL 패턴에 대한 접근 제어를 설정한다.
- 사용자에 대한 역할 및 어떤 사용자를 허가할 것인지도 포함할 수 있다.

```xml
<security-constraint>
    <web-resource-collection>
        <web-resource-name>Protected Resource</web-resource-name>
        <url-pattern>/protected/*</url-pattern>
    </web-resource-collection>
    <auth-constraint>
        <role-name>admin</role-name>
    </auth-constraint>
</security-constraint>

<security-role>
    <role-name>user</role-name>
</security-role>

<login-config>
    <auth-method>BASIC</auth-method>
</login-config>
```

- `security-constraint` : 보안 제약 사항을 정의
- `web-resource-collection` : 보호할 웹 리소스의 컬렉션
- `auth-constraint` : 인증 제약 사항
- `login-config` : 로그인 설정
- `auth-method` : 인증 방법 (예: BASIC, DIGEST, FORM, CLIENT-CERT)

```markdown
[Ex]

이상한 사람 들어오면 쫓아내고 침입을 막는다.
```

---

8-

[https://velog.io/@z2236519/Java-Spring-Boot에서-HTTP-요청-보내기](https://velog.io/@z2236519/Java-Spring-Boot%EC%97%90%EC%84%9C-HTTP-%EC%9A%94%EC%B2%AD-%EB%B3%B4%EB%82%B4%EA%B8%B0)

[https://sooolog.dev/HTTP-통신과-TCP-통신-그리고-웹-소켓에-대한-기본-개념-정리/](https://sooolog.dev/HTTP-%ED%86%B5%EC%8B%A0%EA%B3%BC-TCP-%ED%86%B5%EC%8B%A0-%EA%B7%B8%EB%A6%AC%EA%B3%A0-%EC%9B%B9-%EC%86%8C%EC%BC%93%EC%97%90-%EB%8C%80%ED%95%9C-%EA%B8%B0%EB%B3%B8-%EA%B0%9C%EB%85%90-%EC%A0%95%EB%A6%AC/)

9-

[https://yoo-hyeok.tistory.com/137](https://yoo-hyeok.tistory.com/137)

[https://growthjournal.tistory.com/entry/톰캣vs아파치-의미-차이점](https://growthjournal.tistory.com/entry/%ED%86%B0%EC%BA%A3vs%EC%95%84%ED%8C%8C%EC%B9%98-%EC%9D%98%EB%AF%B8-%EC%B0%A8%EC%9D%B4%EC%A0%90)

[https://velog.io/@kdhyo/Apache-Tomcat-둘이-무슨-차이지](https://velog.io/@kdhyo/Apache-Tomcat-%EB%91%98%EC%9D%B4-%EB%AC%B4%EC%8A%A8-%EC%B0%A8%EC%9D%B4%EC%A7%80)

[https://velog.io/@remon/개발-기본-지식-WEB아파치과-WAS톰캣-차이](https://velog.io/@remon/%EA%B0%9C%EB%B0%9C-%EA%B8%B0%EB%B3%B8-%EC%A7%80%EC%8B%9D-WEB%EC%95%84%ED%8C%8C%EC%B9%98%EA%B3%BC-WAS%ED%86%B0%EC%BA%A3-%EC%B0%A8%EC%9D%B4)

[https://www.inflearn.com/questions/825368/스프링-컨테이너-설명중-헷갈리는-부분이-있어-질문-드립니다](https://www.inflearn.com/questions/825368/%EC%8A%A4%ED%94%84%EB%A7%81-%EC%BB%A8%ED%85%8C%EC%9D%B4%EB%84%88-%EC%84%A4%EB%AA%85%EC%A4%91-%ED%97%B7%EA%B0%88%EB%A6%AC%EB%8A%94-%EB%B6%80%EB%B6%84%EC%9D%B4-%EC%9E%88%EC%96%B4-%EC%A7%88%EB%AC%B8-%EB%93%9C%EB%A6%BD%EB%8B%88%EB%8B%A4)

10-

[https://ittrue.tistory.com/234](https://ittrue.tistory.com/234)

[https://velog.io/@falling_star3/Tomcat-서블릿Servlet이란](https://velog.io/@falling_star3/Tomcat-%EC%84%9C%EB%B8%94%EB%A6%BFServlet%EC%9D%B4%EB%9E%80)

11-

[https://gmlwjd9405.github.io/2018/10/29/web-application-structure.html](https://gmlwjd9405.github.io/2018/10/29/web-application-structure.html)

[https://hipdizzy.tistory.com/61](https://hipdizzy.tistory.com/61)

[https://jake-seo-dev.tistory.com/436#web-xml%25--�%25-D%25--%25--�%25--%25B-�%25--%25B-%25--�%25A-%25-C�%25B-%25B-�%25--%25A-%25--�%25--%25--%25--�%25-E%25--�%25-A%25--%25--�%25B-��%25--%25B-%25--�%25A-%25--�%25B-%25B-�%25--%25A-](https://jake-seo-dev.tistory.com/436#web-xml%25--%EC%25-D%25--%25--%ED%25--%25B-%ED%25--%25B-%25--%EC%25A-%25-C%EA%25B-%25B-%ED%25--%25A-%25--%EC%25--%25--%25--%EC%25-E%25--%EB%25-A%25--%25--%EA%25B-%AC%EC%25--%25B-%25--%EC%25A-%25--%EB%25B-%25B-%EB%25--%25A-)
