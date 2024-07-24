# 스프링부트 동작원리 보충

<aside>
<img src="https://www.notion.so/icons/forward_gray.svg" alt="https://www.notion.so/icons/forward_gray.svg" width="40px" />   스프링부트 동작원리 전체적인 흐름 파악하기

</aside>

🔥강의 들으면서 헷갈렸던 부분, 정리가 필요한 부분 모음🔥

🔥대단원이 끝날 때마다 키워드 모아서 정리한 후 이야기해보면서 제대로 이해했는지 서로 확인🔥

---

## **🔹 서블릿**

JAVA 코드 안에 HTML 코드를 작성할 수 있는 JAVA 프로그램이다.

서버(서블릿 컨테이너/웹컨테이너)에서 실행 되며, 클라이언트 요청에 따라 동적으로 서비스를 제공하는
`자바 클래스` 이다.

- Thread에 의해 서블릿에 있는 `service()` 메소드가 호출된다.
- 전송방식 요청에 맞게 `doGet()` 또는 `doPost()` 메소드를 호출한다.
- MVC(Model, View, Controller) 패턴에서 Controller로 이용

## **🔹** 서블릿 구조

서블릿 클래스는 `javax.servlet.http` 라는 패키지 안에 포함되어 있다.

![Untitled](https://github.com/user-attachments/assets/5e8ae426-4ca4-4285-88ae-30b63b4332c2)

1. Servlet Interface
    - 인터페이스를 구현하기 위해서는 아래 5개의 메서드인 `life-cycle` 를 구현 해야한다.
        - init()
        - service()
        - destroy()
        - getServeltConfig()
        - getServletinfo()

1. ServletConfig 인터페이스
    - 초기화 중에 서블릿에 정보를 전달하기 위해 서블릿 컨테이너에서 사용하는 서블릿 구성을 위한 객체

1. GenericServlet 추상 클래스
    - Servlet 인터페이스와 ServletConfig 인터페이스를 구현하여 만든 추상 클래스

1. HttpServlet 추상 클래스
    - GenericServlet를 상속하여 만든 추상 클래스

## **🔹** 서블릿 동작 과정

![Untitled 1](https://github.com/user-attachments/assets/51f682c0-818f-4135-96d5-4cb0900a5bbc)

> 참고
> 

[https://velog.io/@dbekdms17/Servlet](https://velog.io/@dbekdms17/Servlet)

---

## **🔹 서블릿 컨테이너**

Servlet Container는 Servlet을 이용해 작성된 프로그램을 실행, 관리해주는 주체이다.

- 쇼핑몰의 경우 상품등록, 장바구니, 게시판, 회원 가입 등의 다양한 기능
- 위 기능들을 구현 할 다양한 서블릿이 한 서버 안에서 작동
→ 이를 제어 하는 것이 서블릿 컨테이너 (웹 컨테이너, 톰켓) 이다.

`HTTP 요청`을 받고, 해당 요청을 처리할 `서블릿을 호출`하고, 클라이언트에게 `응답을 반환`하는 역할이다. 

- servlet 클래스의 규칙에 맞게 서블릿을 관리
- 클라이언트 요청 시, `HttpServletRequest`, `HttpServletResponse` 두 객체 생성 
→ post, get 여부에 따라 동적 페이지 생성하여 응답

## **🔹 서블릿 컨테이너 - 서블릿 클래스 - 서블릿 객체**

서블릿 컨테이너 안에는 - 여러 개의 서블릿 클래스가 존재한다. 

각 클래스에 대해 - 하나의 서블릿 객체가 생성된다.

- 서블릿 클래스는 특정 요청을 처리하는 하나의 단위
- 서블릿 객체(서블릿 클래스의 인스턴스) 는 요청을 처리하는 데 사용
- 여러 개의 서블릿을 만들고 싶다면 각각의 서블릿에 대해 별도의 서블릿 클래스를 정의

## **🔹 스프링 컨테이너**

---

## **🔹 web.xml**

- WebApplication의 설정을 위한 xml 형식의 파일
- 모든 Web application은 반드시 하나의 web.xml 파일을 가져야 한다.
- DD는 Web Application 실행 시 메모리에 로드된다.
- ServletContext의 초기 파라미터 , Session의 유효시간 설정 , Servlet/JSP에 대한 정의 , Servlet/JSP 매핑, Mime Type 매핑, Welcome File list, Error Pages 처리, 리스너/필터 설정, 보안 등을 제공 해준다.

              **~~~~**

- `web.xml` 은 `DispatcherServlet` 을 설정하고 관리하는 중요한 구성 요소
- `DispatcherServlet`이 클라이언트 요청을 처리하는 시작점이 된다.

---

## **🔹 Spring MVC 와의 관계**

### **스프링 MVC**

- 서블릿 기반의 프레임워크
- 요청을 처리하기 위해 DispatcherServlet 사용한다.
- 서블릿 컨테이너 위에서 작동한다.

— — — — — — — — — — — — — — — — — — — — —

### **DispatcherServlet**

- 스프링 MVC에서 프론트 컨트롤러 패턴을 구현한 Servlet이다.
- 클라이언트의 HTTP 요청을 받아 적절한 컨트롤러로 전달하고, 그 결과를 다시 클라이언트에게 응답한다.
- 주요 기능: 요청 라우팅, 핸들러 호출, 모델과 뷰 관리, 예외 처리, 인터셉터 지원 등

### **Handler Mapping**

- 클라이언트의 요청을 적절한 컨트롤러 메서드에 매핑하는 역할
- 요청 URL, HTTP 메서드 등을 기준으로 어떤 컨트롤러가 요청을 처리할지 결정한다.
- `DispatcherServlet`의 요청 처리를 보조하며, 요청이 들어올 때마다 매핑 된 ****Handler를 찾아낸다.

### **ApplicationContext**

- 스프링의 `IoC` (Inversion of Control) 컨테이너로 애플리케이션의 구성 요소를 관리한다.
- `의존성 주입 DI` (Dependency Injection)을 통해 객체를 생성한다.
- `빈(Bean)`으로 등록된 객체를 관리하고, 애플리케이션 전반에 걸쳐 필요한 서비스나 컴포넌트를 제공한다.
- 스프링 MVC에서 컨트롤러, 서비스, 리포지토리 등 다양한 구성 요소의 생명주기를 관리한다.
- `Servlet-ApplicationContext` , `Root-ApplicationContext`

> **IoC (Inversion of Control) : 제어의 역전**  /
DispatcherServlet에 의해 생성되어지는 수 많은 객체들은 ApplicationContext에서 관리된다. 
이것을 IoC 라고 한다.
> 

> **DI (Dependency Injection) : 의존성 주입** /
필요한 곳에서 ApplicationContext에 접근해 필요한 객체를 가져온다.
> 

## **🔹 DispatcherServlet - Handler Mapping - ApplicationContext**

### **Request 흐름**

- 클라이언트가 HTTP 요청을 보내면, 이 요청은 **DispatcherServlet** 으로 전달 된다.
- **DispatcherServlet** 은 요청 URL을 기반으로 **Handler Mapping** 을 사용하여 적절한 Controller 메서드를 찾는다.

### Handler 호출

- **Handler Mapping** 이 적절한 Controller를 찾으면, **DispatcherServlet**은 해당 Controller 메서드를 호출한다.
- 이때, Controller는 **ApplicationContext** 에서 관리되는 빈(Bean)으로서, 필요한 서비스/리포지토리에 접근 가능하다.

### Response 처리

- Controller가 비즈니스 로직을 처리하고, 모델 데이터와 뷰 이름을 반환하면,
- **DispatcherServlet**은 뷰 리졸버를 통해 적절한 뷰를 선택하고 최종 응답을 클라이언트에게 보낸다.

### → 결론

스프링 MVC에서 상호 작용 → **요청 처리**

DispatcherServlet (요청관리) - Handler Mapping (컨트롤러 연결)- ApplicationContext  (요청처리 지원)

---
