## Springboot 개념정리
8, 9, 10, 11, 12, 13, 14강
[(강의 링크)](https://www.inflearn.com/course/%EC%8A%A4%ED%94%84%EB%A7%81%EB%B6%80%ED%8A%B8-%EA%B0%9C%EB%85%90%EC%A0%95%EB%A6%AC/dashboard)    

### 스프링부트 동작원리
<br>

## 1. 내장 톰캣을 가진다.
`socket` : 운영체제가 가지고 있는 것으로, 통신을 위해 사용한다.
#### 소켓통신 : 실시간(양방향) 방식
![image](https://github.com/ssIIIn0-0/backend-springboot-study/assets/62862307/aab210ef-e24c-4156-808c-e875e96a3099)
A : 서버, B, C : 클라이언트
```
ex)
  ▪ A와 B가 통신을 하고 싶을 때
    1. A가 5000번에 해당하는 소켓의 포트를 연다.
    2. B가 A의 ip주소와 포트번호를 new socket에 넣으면 5000번 포트를 통해서 A와 B가 연결이 되고, message를 주고받는다.
  
  ▪ 만일 C도 A와 통신을 하고 싶다면?
    : 현재 A와 B는 main 스레드가 관리하는 5000번 포트로 연결되어 통신중 이므로 A는 다른 객체와 연결을 시작할 수 없다.
    : 따라서 소캣 통신에서는 멀티스레드 환경이 필요하다.
    1. main 스레드가 관리하는 5000번 포트는 단순히 A와 다른 객체의 연결만을 관리한다.
    2. A와 연결하고 싶은 B, C는 5000번 포트를 통해 연결을 받는다.
    3. 연결을 받은 B, C는 main 스레드가 아닌, 스레드 1, 2가 관리하는 포트 5001, 5002를 통해 message를 주고받는다.
    4. 이때 B, C는 A와 연결되어 (포트 5001, 5002에 연결되면) A의 5000번 포트와는 연결을 끊어서 추후 다른 객체가 연결을 받을 수 있게한다.

   - 멀티 스레드를 통해 A가 B, C와 동시동작처럼 보이지만, time slice(시간을 쪼개서)로 동작한다.
```
- `소켓 통신`은 한번 연결된 대상과는 끊임없이 통신할 수 있지만, 연결된 소캣이 늘어날수록 부하가 늘어난다.
---
#### HTTP 통신 : stateless 방식
```
  ▪ B, C가 A와 통신하고 싶을 때
    1. 먼저 요청한 순서대로 B가 A의 소켓에 요청을 넣으면, 요청한 message를 넘겨주고 해당 대상과의 연결을 끊는다.(stateless방식)
    2. 그 다음 C와도 동일한 방식으로 연결한다.

  ▪ stateless방식 때문에 A는 이전에 연결했던 대상의 상황을 알 수 없다.

  ▪ 갑(웹서버) 와 을(클라이언트)이 HTTP 방식으로 통신할 경우
    1. 을 `Request` to 갑 (Request : 갑의 IP 주소 + URL( 원하는데이터의 주소))
    2. 갑 `Response` to 을 with Static 자원 (을이 요청한 데이터로 html, js, css, image 등의 누구에게나 항상 같은 데이터인 물리적인 파일)
    ▫ 이 과정이 끝이다. 이를 통해 갑은 을의 IP주소를 알지 못하며, 알고싶다면 HTTP 통신이 아닌 socket통신을 이용해야 한다는 것을 알수 있다.
```
  - `HTTP 통신`은 특정 데이터(static 자원)를 Request를 해야 Response를 하는 구조이다. 
---
#### 웹서버
: 클라이언트로부터 HTTP 요청을 받아서 static 자원을 반환
웹서버에서는 `APACH`(아파치)가 사용된다.

##### APACH (static)
  - 정적인 데이터를 처리하는 웹서버
  - Request가 들어오면, static 자원에 대해 response해준다.
  - 그럼 static 자원 이외의 것을 요청하면? 응답못해준다.

##### TOMCAT (dynamic)
  - 동적인 데이터를 처리하는 웹서버 (WAS)
  - .jsp를 컴파일해서 요청한 데이터를 파악한 후 이를 HTML문서로 만든다.
  - 즉, TOMCAT은.jsp(자바 코드)로 구현할 수 있는 모든 동적인 데이터(DB, APP등)를 정적인 데이터 HTML로 변경해준다.

##### 따라서, 웹서버는 APACH와 TOMCAT을 사용해서 클라이언트의 요청에 응답한다.
- if 동적 데이터를 요청한 경우,
1. 자바코드로 구성된 데이터 요청
2. APACH에서 TOMCAT으로 제어권 변경
3. TOMCAT이 자바 컴파일
4. 컴파일한 데이터를 html(정적 데이터)로 만듬
5. 정적 데이터를 APACH에게 제공
6. APACH는 정적 데이터를 응답
<br>

## 2. 서블릿 컨테이너
![image](https://github.com/ssIIIn0-0/backend-springboot-study/assets/62862307/c2b5943c-564f-49e8-a1e6-989dd69bdb92)   
기존에는 이런식으로 요청이 들어오면 모든 요청에 대해 프로세스를 하나씩 만들었다.(CGI, Common Gateway Interface)
![image](https://github.com/ssIIIn0-0/backend-springboot-study/assets/62862307/17c40f3f-410a-4d76-a4a5-9944bbd4f981)   
하지만 서블릿을 통해 몇개의 요청이 들어오던 프로세스1개에 요청마다 스레드를 지정하여 response할 수 있게 되었다.

<결론>
각 요청마다 새로운 프로세스를 생성해서 높은 오버헤드가 유발된 CGI를 개선해서 1개의 프로세스에 여러개의 스레드를 생성&재사용하는 서블릿을 사용하게 된다.

#### 서블릿과 서블릿 컨테이너
![image](https://github.com/ssIIIn0-0/backend-springboot-study/assets/62862307/dd46364b-cffb-43e1-b1d2-dd440ae1cc4a)   
  - 서블릿 컨테이너 : 서블릿을 관리하고 실행하는 sw로 서블릿의 생명주기를 관리 (init(), service(), destroy() 실행)
  - 서블릿 : java로 작성된 클래스와 객체부분으로 DB상호작용, 로직 수행등의 역할(실질적인 데이터 요청을 수행하는 곳)

##### request response 과정 with 서블릿
![image](https://github.com/ssIIIn0-0/backend-springboot-study/assets/62862307/fcb39356-8a84-4bef-be75-f13d213cabe6)

  1. 클라이언트가 요청하면, 서블릿 컨테이너(톰캣)이 요청을 받아서 최초 요청일 경우 서블릿 객체를 생성하고(new), init()으로 초기화한다. 최초 요청이 아니라면 이미 있던 서블릿 객체를 재사용한다.

  2. 서블릿 컨테이너(톰캣)은 스레드를 만들고, service()를 호출하여 클라이언트의 요청을 처리한다.
      - doGet() : HTTP GET 요청처리, 데이터 읽고 가져올 때 사용 ex) 데이터 검색, 이미지 요청
      - doPost() : HTTP POST 요청처리, 데이터 생성할 때 사용 ex) 설문 폼 데이터 제출
      - doPut() : HTTP PUT 요청처리, 데이터를 업데이트할 때 사용 ex) 비밀번호 변경
      - doDelete() : HTTP DELETE 요청처리, 데이터를 삭제할 때 사용 ex) 장바구니 비우기 

  3. service()의 처리 내용에 따라 위 4가지 메서드를 서블릿 객체에서 처리한다.

  4. 처리된 데이터내용을 서블릿 컨테이너가 클라이언트에게 응답한다.

  5. 클라이언트의 요청에 대응해서 스레드는 계속만들어지고, 서블릿 객체는 최초 요청때 생성 후 계속 1개로 사용된다. (스레드는 여러개 가능, 서블릿 객체는 1개)

  6. 만약 서블릿 컨테이너(톰캣)이 설정한 스레드 개수보다 더 많은 요청이 들어온다면, 스레드를 재사용(pooling)할 수 있을 때까지 대기한다.

  7. 스레드와 서블릿객체는 각각 app이 끝나고 서블릿컨테이너가 destroy() 하기 전까지 재사용된다.

```
+추가
1. 클라이언트가 정적인 파일(html, css, .png)을 요청하게되면 아팟치가, 동적인 파일(자바 파일)을 요청하면 서블릿 컨테이너(톰캣)이 실행된다.
    • BUT! 스프링에서는 정적인 파일을 요청할 수 없다.
    • WHY? 스프링에서는 URL 접근을 막아뒀다. 그래서 URI를 통해 접근하게 되는데 이는 특정한 파일 요청은 할 수 없기 때문에 무조건 자바를 거친다.
    • So, 톰캣이 제어권을 갖는다.
      ▫ URL(Location) : 자원을 통해서 접근할 때 사용하는 주소, URI(Identify) : 식별자을 통해서 접근할 때 사용하는 주소
        - URL : http://naver.com/a.png, URI : http://naver.com/picture/a
        - 여기서 URL의 a.png == 자원, URI의 picture/a == 식별자

2. 서블릿 객체는 한번만 만들고 스레드는 클라이언트 요청마다 만드는 이유?
    • java에서 new를 이용해서 객체를 생성하면, static이 아닌 메서드는 heap에 한번만 저장되지만, 메서드를 실행할 경우 stack에 실행횟수만큼 저장된다.(메서드 실행으로 인해 메서드 내에서 필요한 메모리공간을 스택에서 사용하기 때문)
    • 즉, 메서드는 독립적이므로 서블릿 객체는 재사용할지라도 service()메서드부터는 요청마다 다른 스레드가 필요하다.
```

##### 그럼, 스레드를 많이 만들수록 응답속도가 빠르겠네? 스레드는 어떻게 해야 많이 만들 수 있어?
-> 컴퓨터 성능이 좋으면 됩니다. : scale-up (스레드를 1000개 만들 수 있는 컴퓨터 1개)   
-> 컴퓨터를 많이 두면 됩니다. : scale - out (스레드를 100개 만들 수 있는 컴퓨터 10개)

<br>

## 3. web.xml (웹 배포 서술자)
##### APP의 클래스, 리소스, 구성 및 웹 서버가 이를 사용해서 웹 요청을 처리하는 방법을 '기술'해둔 문서
```
하나의 성이 있다.
성에는 '관리자' 와 '문지기' 가 있다.
관리자는 문지기가 할 일을 문서로 만든다. 문지기는 관리자가 만든 문서[web.xml]을 읽고 일을 한다.
관리자가 변경될 때마다 문지기가 할 일이 달라질 수 있지만, 문지기는 달라지지 않는다.
```
여기서 문지기가 받은 문서 web.xml에 적힌 일은 다음과 같다.
##### ServletContext의 초기 파라미터
  - 정상적인 경로로 들어오지 않은 요청?은 초기 파라미터를 모르기 때문에 보안 문제를 해결할 수 있다.(암구호)
  - APP 전역에서 초기 파라미터를 사용해서 정상적인 경로와 아닌 경로로 들어온 요청?을 파악할 수 있다.
##### Session의 유효시간 설정
  - 정상적인 경로인 인증을 통해 들어오면, session을 할당받는다.
  - session만큼 내부에 있을 수 있으며, session 연장도 가능하다.
##### Servlet/JSP에 대한 정의
  - 원하는 자원의 식별자를 가지고 있는다.
##### Servlet/JSP 매핑
  - 요청한 자원의 식별자(원하는 자원)를 보고 해당 자원의 주소를 제공해서 원하는 데이터가 제공될 수 있도록 돕는다.
##### Mime(Multipurpose Internet Mail Extensions) Type 매핑
  - 데이터를 가지고 오는 타입
  - Mime type이 아니라면? → 데이터를 Read하러 오는 종류일 것이다. → HTTP 통신 중 Get(select)
  - Mime type을 통해서 가져온 자원의 저장을 어디로 해야할지 판단할 수 있다. 따라서 Mime type이 다르면 Error가 발생한다.
##### Welcome File list
  - 데이터와 목적없이 방문한 상태로, 관리자에 따라 다르게 설정된다.
##### Error Pages 처리
  - 문지기가 모르는 곳(관리자가 문서에 작성하지 않은 곳)에 방문하려고 하면, Error Pages로 보낸다.
##### 리스너/필터 설정
  - 필터 : 요청의 신분을 확인하는 것(보안), 부적절한 내용(총을 소지하고 있다거나 등등)이 있다면 제거후 들여보낸다.
  - 리스너 : 문지기 옆에서 특정 조건에 해당하는 요청만 파악하여 관리자에게 가져가는 일종의 대리인
##### 보안
  - 성을 지키기 위해 악성 코드와 같은 이상한 내용이 들어오면 막는다.
---
Web.xml : DD (Deployment Descripter 배포 설명자) Web APP의 설정파일.
1. Dispatcher Servlet : 클라이언트의 요청을 처리
2. ContextLoaderListener : Web APP 컨텍스트 단위의 설정을 로드
3. Filter : 보안 인증 인가

Web.xml 은 이런식으로 작성한다..
```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app version="2.5" xmlns="http://java.sun.com/xml/ns/javaee"
 xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
 xsi:schemaLocation="http://java.sun.com/xml/ns/javaee https://java.sun.com/xml/ns/javaee/web-app_2_5.xsd">
 
 <!-- Dispatcher Servlet 생성 -->
 <servlet>
     <servlet-name>myDispatcherServlet</servlet-name>
     <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
     <init-param>
         <param-name>contextConfigLocation</param-name>
         <param-value>classpath:/config/servlet-config.xml</param-value>
     </init-param>
     
     <load-on-startup>1</load-on-startup>
 </servlet>
 <servlet-mapping>
     <servlet-name>myDispatcherServlet</servlet-name>
     <url-pattern>/</url-pattern>
 </servlet-mapping>
 
 <!-- web application context -->
 <listener>
     <listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
 </listener>
 <context-param>
     <param-name>contextConfigLocation</param-name>
     <param-value>
         /WEB-INF/config/application-context.xml
     </param-value>
 </context-param>
 
 <!-- Encoding Filter 생성 -->
 <filter>
     <filter-name>encodingFilter</filter-name>
     <filter-class>
         org.springframework.web.filter.CharacterEncodingFilter
     </filter-class>
     <init-param>
         <param-name>encoding</param-name>
         <param-value>UTF-8</param-value>
     </init-param>
     <init-param>
         <param-name>forceEncoding</param-name>
         <param-value>true</param-value>
     </init-param>
 </filter>
 <filter-mapping>
     <filter-name>encodingFilter</filter-name>
     <url-pattern>/*</url-pattern>
 </filter-mapping>
</web-app>

```
<br>

## 4. FrontController 패턴
`최초 앞단에서 request 요청을 받아서 필요한 클래스에 전달 (web.xml에서 다 정의하기 힘들어서)
이때, 새로운 요청이 생기기 때문에 request와 response가 새롭게 new → Request Dispatcher 가 필요`
- request 에는 요청한 사람의 정보가 들어있음 (어떤 자원, 요청의 종류)
- FrontController 는 .do(..)를 확인 ex) FrontController 에 낚아 챈 a.do(특정 주소) 와 b.do(특정 주소) 가 있을 때

```
FrontController가 필요한 이유?
: 클라이언트의 다양한 요청마다 서블릿을 만들면 효율이 떨어진다.
이때, 프론트 컨트롤러 패턴을 사용해서 각각의 요청을 적절한 곳으로 위임해서 효율성을 높이고, 들어오는 요청들의 공통 기능을 한 곳에서 캡슐화 할 수 있다.

DispatcherServlet 또한 Spring에서 프론트 컨트롤러 패턴을 사용한 예시 중 하나이다.

DispatcherServlet이 Bean으로 등록되어 package를 scan하고 @Controller, @RestController 애노테이션을 확인하여 어떠한 요청이 들어왔을 때 적절한 Handler Method에 위임한다.
```

## 5. RequestDispatcher
`필요한 클래스 요청이 도달했을 때 FrontController에 도착한 request 와 reponst를 그대로 유지`
→ 이를 통해 페이지와 페이지 사이에 데이터 이동이 가능

## 6. DispatchServlet : 요청이 들어오면 주소를 분배
`FrontController 패턴 + RequestDispatcher = DispatchServlet`
- DispatcherServlet이 생성될 때, 수많은 객체(필터, 레포지토리, 컨트롤러 등)가 생성(IoC) 
- 해당 객체들은 직접 등록 or 자동 등록(기본적)

기존에는 모든 서블릿을 URL 매핑을 위해 web.xml에 등록해줬어야 했지만,
##### DispatchServlet 이 등장함에 따라 app에 들어오는 response을 핸들링해주고 공통 작업을 처리한다.
![image](https://github.com/ssIIIn0-0/backend-springboot-study/assets/62862307/de78dd53-3521-4a2b-a40d-5bc2eb3291db)
1. 클라이언트의 요청을 DispatcherServlet이 받음![image](https://github.com/ssIIIn0-0/backend-springboot-study/assets/62862307/09793274-ec0c-48c6-ab83-1f5d95d0a318) <br> (web context = 서블릿 컨텍스트)

2. 요청 정보를 통해 요청을 받을 컨트롤러를 찾음
3. 요청을 컨트롤러로 위임할 핸들러 어댑터를 찾아서 전달
4. 핸들러 어댑터가 컨트롤러로 요청을 전달
5. 비지니스 로직을 처리
6. 컨트롤러가 반환값을 제공
7. 핸들러 어댑터가 반환값을 처리
8. 서버의 응답을 클라이언트로 반환
결론 : DispatcherServlet을 통해 요청을 처리할 컨트롤러를 찾아서 위임하고, 그 결과를 받아옴


<br>

## 7. 스프링 컨테이너
dispatchServlet에 의해 생성되어지는 수 많은 객체들은 어디에서 관리될까?
- 외부에서 요청이 들어오면 DispatchServlet은 컴포넌트를 스캔을 통하여 모든 소스 파일의 @controller, @RestController, @Repository, @Service, @Component 등 어노테이션을 확인하여 객체를 생성하고 주소를 분배한다.
- dispatchServlet의 컴포넌트 스캔을 통하여 IoC로 메모리에 띄우면 스레드별로 따로 관리가 된다. 공통적으로 사용하는 내용의 경우, DB Connection을 사용해서 계속 new할 필요가 없다.
  - context Loader Listener : DispatchServlet에 진입하기 전에 스레드 생성, DB관련 공통 사용내용을 미리 띄운다.(root-applicationnContext 파일 읽음)

<br>

#### 1. ApplicationContext
- 수 많은 객체들이 ApplicationCopntext에 등록된다. 이것을 IoC라고 한다.   
- IoC란 제어의 역전을 의미한다. 개발자가 직접 new를 통해 객체를 생성하게 된다면 해당 객체를 가르키는 레퍼런스 변수를 관리하기 어렵다. 그래서 스프링이 직접 해당 객체를 관리한다.  
- 이때 우리는 주소를 몰라도 된다. 왜냐하면 필요할 때 DI 하면 되기 때문이다. 필요한 곳에서 ApplicationContext에 접근하여 필요한 객체를 가져올 수 있다. ApplicationContext는 싱글톤으로 관리되기 때문에 어디에서 접근하든 동일한 객체하는 것을 보장해준다.
<br>

![image](https://github.com/user-attachments/assets/ffc6431c-f93a-40eb-92ee-b49dd5ccbcad)

<br>

- ApplicationContext의 종류에는 두가지가 있는다. root-applicationContext, servlet-applicationContext   
  - `servelet-applicationContext`는 viewResolver, Interceptor, MultipartResolver 객체를 생성하고 웹과 관련된 어노테이션 controller, RestController를 스캔한다.   
    : 해당 파일은 DispatchServlet에 의해 실행된다.   
  - `root-applicationContext`는 해당 어노테이션을 제외한 어노테이션 service, Repository등을 스캔하고 DB관련 객체를 생성한다.(스캔 : 메모리에 로딩)
    - 스레드에서 공통적으로 사용하는 것을 메모리에 띄우고 IoC컨테이너에서 관리한다.   
    : 해당파일은 ContextLoaderListener에 의해 실행된다. ContextLoaderListener 실행은 web.xml이기 때문데 root-applicationContext는 servlet-applicationContext보다 먼저 로드된다. 당연히 Servlet-applicationContext에서는 root-applicationContext가 로드한 객체를 참조할 수 있지만, 그 반대는 불가능하다. (생성시점이 다르기 때문)

<br>

#### 2. Bean Factory
- 필요한 객체를 Bean Factory에 등록할 수 도 있다. 여기에 등록하면 초기에 메모리에 로드되지 않고 필요할 때 `getBean()이라는 메소드를 통하여 호출`하고 메모리에 로드할 수 있다. 이것 또한 IoC이다.
- 필요할 때 DI하여 사용할 수 있다.   
: ApplicationContext와 다른 점은 Bean Factory에 로드되는 객체들은 미리 로드되지 않고 필요할 때, 호출하여 로드하기 때문에 lazy-loading이 된다는 점이다.

<br>

## 8. 요청 주소에 따른 적절한 컨트롤로 요청 (Handler Mapping)
- Get 요청으로 http://localhost:8080/post/1 이라고 들어오면, post/1 이라는 식별자를 보고 적절한 컨트롤러의 함수를 찾아서 실행한다.

<br>

## 9. 응답
html 파일을 응답할지 data를 응답할지 결정해야 하는데, 
- html 파일을 응답하게 되면 `ViewResolver`가 관여
![image](https://github.com/user-attachments/assets/9e3573a3-4f14-4427-bc0b-81d80366e940)

- data 를 응답하게 되면 `MessageConverter`가 작동 (메세지컨버팅할 때는 json이 기본 전략)
  - 어노테이션 @ResponseBody 를 사용하는 경우 ViewResolver 대신 MessageConverter가 작동한다.
  - MessageConverter에는 다양한 종류가 있는데, 그중에서 HttpMessageConverter의 경우
    1. ByteArrayHttpMessageConverter : byte [] 처리 및 기타 처리
    2. StringHttpMessageConverter : 기본 문자 처리
    3. MappingJackson2 HttpMessageConverter : 기본 객체 처리
       있다.
       





