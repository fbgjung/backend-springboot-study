## Spring이란
- Framework이다.
- 오픈 소스이다.
- IoC(Inversion of Control, 제어의 역전) 컨테이너를 가진다.<br>
	기본적으로 new로 객체를 생성하면 heap영역에 할당된 객체를 생성한 메서드에서 관리<br>
	(다른 메서드에서 생성한 객체를 사용하기 어렵다는 단점이 존재)<br>
	IoC는 생성한 클래스를 Spring에서 자동으로 객체를 heap 메모리에 할당하여 관리<br>
	(모든 메소드에서 동일한 인스턴스 사용 가능 => 싱글톤)
- DI(Dependency Injection, 의존성 주입)를 지원한다.
- 엄청나게 많은 필터를 가지고 있다.<br>
    톰켓 - 명칭 : 필터(filter) / 톰켓에서 필터 기능을 하는 파일 : web.xml <br>
    스프링컨테이너 - 명칭 : 인터셉터(AOP) / 역할 : 권한 확인
- 엄청나게 많은 어노테이션을 가지고 있다. <br>	
    어노테이션 : 컴파일러가 체크할 수 있게 힌트를 주는 주석 <br>
    스프링에서는 어노테이션을 통해서 객체를 생성한다. (IoC를 사용하는 기법) <br>
    ex) @Compont : 클래스 메모리에 로딩 / @Autowired : 로딩된 객체 해당 변수에 할당 <br>
    리플렉션 : 클래스의 메서드, 필드, 어노테이션 등을 런타임 시 분석하는 기법
- MessageConverter를 가지고 있다. 기본값은 현재 Json이다.
- BufferedReader와 BufferedWriter를 쉽게 사용할 수 있다.
- 계속 발전중이다.
 
#

### Filter / Interceptor / AOP
Filter
- 요청과 응답을 거른 뒤 정제하는 역할 수행하며, Dispatcher Servlet에 요청이 전달되기 전/후에 url 패턴에 맞는 모든 요청에 대해 부가 작업을 처리할 수 있는 기능을 제공한다.
- Dispatcher Servlet은 스프링의 가장 앞단에 존재하는 프론트 컨트롤러이므로, 필터는 스프링 범위 밖(웹 컨테이너 ex) 톰켓)에서 처리된다. (스프링 빈으로 등록 가능)
- 보통 web.xml에 등록하고, 일반적으로 인코딩 변환 처리, XSS방어 등의 요청에 대한 처리로 사용된다.
- Filter의 메소드 종류<br>
    Filter를 사용하기 위해서는 javax.servlet의 Filter 인터페이스를 구현(implements)해야 하며, 다음과 같은 3가지 메소드를 가진다.
    ```
    public interface Filter {

        public default void init(FilterConfig filterConfig) throws ServletException {}

        public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException;

        public default void destroy() {}
    }
    ```
    1. init()<br>
        Filter 객체를 초기화하고 서비스에 추가하기 위한 메소드이다.<br>
        웹 컨테이너가 1회 init()을 호풀하여 필터 객체를 초기화하면 이후 요청들은 doFilter()를 통해 처리된다.
    2. doFilter()<br>
        url-pattern에 맞는 모든 HTTP 요청이 디스패처 서블릿으로 전달되기 전에 웹 컨테이너에 의해 실행되는 메소드이다.<br>
        doFilter의 파라미터로 FilterChain이 있는데, FilterChain의 doFilter를 통해 다음 대상으로 요청을 전달할 수 있게 된다.<br>
        chain.doFilter()로 전/후에 필요한 처리 과정을 추가하여 원하는 처리를 진행할 수 있다.
    3. destroy()<br> 
        필터 객체를 제거하고 사용하는 자원을 반환하기 위한 메소드이다.<br>
        웹 컨테이너가 1회 destroy()를 호출하여 필터 객체를 종료하면 이후에는 doFilter에 의해 처리되지 않는다.

Interceptor
- Spring이 제공하는 기술로, Dispatcher Servlet이 컨트롤러를 호출하기 전/후에 요청과 응답을 참조하거나 가공할 수 있는 기능을 제공한다. (스프링 컨텍스트에서 동작)
- Interceptor의 메소드 종류<br>
    인터셉터를 추가하기 위해서는 org.springframework.web.servlet의 HandlerInterceptor 인터페이스를 구현(implements)해야 하며, 다음과 같은 3가지 메소드를 가진다.
    ```
    public interface HandlerInterceptor {

        default boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return true;
        }

        default void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {}

        default void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {}
    }
    ```
    1. PreHandle()<br>
    Controller가 호출되기 전에 실행된다.<br>
    컨트롤러 이전에 처리해야 하는 전처리 작업이나 요청 정보를 가공, 추가하는 경우에 사용할 수 있다.
    2. postHandle()<br>
    Controller가 호출된 후에 실행된다.(View 렌더링 전)<br>
    컨트롤러 이후에 처리해야 하는 후처리 작업이 있을 때 사용하 수 있다.<br>
    (컨트롤러가 반환하는 ModelAndView 타입의 정보가 제공되나, 최근에는 JSON 형태로 데이터를 제공하는 RestAPI 기반의 컨트롤러(@RestController)를 만들면서 자주 사용되지 않는다.)
    3. afterCompletion()<br>
    모든 뷰에서 최종 결과를 생성하는 일을 포함해 모든 작업이 완료된 후에 실행된다.(View 렌더링 후)<br>
    요청 처리 중에 사용한 리소스를 반환할 때 사용할 수 있다.

AOP(Aspect Oriented Programming, 관점 지향 프로그래밍)
- 부가 기능을 핵심 기능에서 분리해 한 곳으로 관리하도록 하고, 이 부가 기능을 어디에 적용할지 선택하는 기능을 합한 하나의 모듈


참고 - Interceptor 대신 컨트롤러에 적용할 부가기능을 어드바이스로 만들어 AOP를 적용할 수도 있으나, 다음과 같은 이류로 컨트롤러의 호출 과정에 적용되는 부가 기능들은 Interceptor를 사용하는 편이 낫다.<br>   

1. 컨트롤러는 타입과 실행 메소드가 모두 제각각이라 포인트컷(적용할 메소드 선별)의 작성이 어렵다.
2. 컨트롤러는 파라미터나 리턴 값이 일정하지 않다.


### Filter와 Interceptor 정리
<img src="./img/Filter_Interceptor.png" width="700px" height="200px" title="Filter_Interceptor"/><br>
- Filter와 Interceptor 모두 비즈니스 로직과 분리되어 특정 요구사항(보안, 인증, 인코딩 등)을 만족시켜야 할 때 적용한다.
- Filter는 특정 요청과 컨트롤러에 관계없이 전역적으로 처리해야 하는 작업이나 웹 어플리케이션에 전반적으로 사용되는 기능을 구현할 때 적용한다.
- Interceptor는 클라이언트의 요청과 관련된 작업에 대해 추가적인 요구하상을 만족해야 할 때 적용한다.


### 어노테이션(Annotation)

#
### 참조
<a href="https://www.inflearn.com/course/%EC%8A%A4%ED%94%84%EB%A7%81%EB%B6%80%ED%8A%B8-%EA%B0%9C%EB%85%90%EC%A0%95%EB%A6%AC">스프링부트 개념정리(이론)</a><br>
<a href="https://dev-coco.tistory.com/173">[Spring] 필터(Filter)와 인터셉터(Interceptor)의 개념 및 차이</a><br>
<a href="https://mangkyu.tistory.com/173">[Spring] 필터(Filter) vs 인터셉터(Interceptor) 차이 및 용도 - (1)</a><br>