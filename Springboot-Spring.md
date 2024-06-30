# 1,2,3강 - 스프링 기본 개념

[1] 스프링의 핵심은 무엇인가요?

[2] 필터란 무엇인가요?

[3] 메세지 컨버터가 무엇인가요?

---

# 1강 - 스프링의 핵심은 무엇인가요?

<aside>
<img src="https://www.notion.so/icons/forward_gray.svg" alt="https://www.notion.so/icons/forward_gray.svg" width="40px" /> 스프링은 프레임워크이다.

스프링은 오픈소스이다.

스프링은 IoC 컨테이너를 가진다.

스프링은 DI 를 지원한다.

</aside>

## ✅ 1-1 : 프레임워크

- 프레임워크? SW개발을 위한 일련의 규칙, 가이드라인, 툴 및 라이브러리의 모음이다.
- 프레임워크 사용 시 → 반복적인 작업을 줄이고 비즈니스 로직에 집중이 가능하다.

## ✅ 1-2 : 오픈소스

## ✅ 1-3 : 스프링 특징

### IoC 컨테이너

- 제어의 역전 (Inversion of Controll)
- 객체의 생성, 설정, 생명 주기 관리를 담당하는 컨테이너 
( 원래는 객체를 개발자가 직접 생성하고 관리해야 하지만, 이 작업을 대신 해줌)

- 주도권을 스프링에게 뺏김 =  프레임 워크가 코드의 제어 흐름을 담당
- ⇒ 객체 생성과 초기화 신경쓸 필요 없이 로직에 집중 가능

### DI

- Dependent Injection: 의존성 주입
- 실행하려는 소스 코드 밖에서 정의된 객체를 받아 실행
- 스프링은 생성자 주입, 세터 주입, 필드 주입 등의 다양한 방법으로 DI 지원
- Singleton : 스프링 기본적으로 Bean을 싱글톤으로 관리함
== 하나의 Bean 인스턴스만 생성되어 사용함 (메모리 사용 효율 up, 상태 공유 가능)

## ✚ 1-4 : 스프링 빈?

- IoC 컨테이너가 관리하는 객체
- 빈은 애플리케이션에서 필요한 구성 요소나 서비스 객체를 나타낸다.
- 스프링 컨테이너는 이러한 빈들을 생성, 설정하고 생명 주기를 관리한다.

`myService` 메서드는 `MyService` 인터페이스의 구현체인 `MyServiceImpl`의 빈을 정의 
→ 스프링 컨테이너는 이 빈을 관리하고 필요한 곳에 주입한다.

```java
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public MyService myService() { 
        return new MyServiceImpl();
    }
}

```

---

# 2강 - 필터란 무엇인가요?

<aside>
<img src="https://www.notion.so/icons/forward_gray.svg" alt="https://www.notion.so/icons/forward_gray.svg" width="40px" /> 스프링은 엄청나게 많은 필터를 가지고 있다.

스프링은 엄청나게 많은 어노테이션을 가지고 있다. (리플렉션, 컴파일체킹)

</aside>

## ✅ 2-1 : 필터

- 특정 조건을 기준으로 데이터를 걸러내는 기능
- 웹 애플리케이션의 요청과 응답을 가로채고 수정, 검증에 처리할 수 있다
- 웹 application의 여러 계층에서 공통적으로 수행해야하는 작업 처리에 유용
( 인증, 인가, 로깅, 데이터 압축 등)

- web.xml
- 스프링 자체가 가지고 있는 필터가 있다.
- 직접 필터를 만들어 사용할 수 있다.
- AOP : Aspect Oriented Programming : 관점 지향 프로그래밍

### 필터의 동작 원리

- 클라이언트 요청이 서블릿 또는 JSP와 같은 웹 리소스에 도달하기 전에 실행,
- 응답이 클라이언트에게 전달되기 전에 다시 실행됩니다.

→ 이를 통해 요청 및 응답을 가로채고 원하는 작업을 수행할 수 있습니다.

### 스프링에서의 필터

`javax.servlet.Filter` 인터페이스를 구현하여 필터를 정의할 수 있음

메서드

- `init(FilterConfig filterConfig)`: 필터 초기화 메서드. 필터가 처음 생성될 때 한 번 호출
- `doFilter(ServletRequest request, ServletResponse response, FilterChain chain)`: 필터의 핵심 메서드. 요청을 가로채고 처리한 후, 다음 필터 또는 최종 리소스로 요청을 전달
- `destroy()`: 필터가 종료될 때 호출. 리소스를 해제하는 작업 수행가능.

```java
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

@WebFilter(urlPatterns = "/*")
public class MyFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // 필터 초기화
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) 
            throws IOException, ServletException {
        // 요청 전 처리
        System.out.println("Request received at " + new Date());

        // 다음 필터 또는 서블릿으로 요청 전달
        chain.doFilter(request, response);

        // 응답 후 처리
        System.out.println("Response sent at " + new Date());
    }

    @Override
    public void destroy() {
        // 필터 종료 시 처리
    }
}

```

## ✅ 2-2 : 어노테이션

- Annotation : 컴파일러에게 힌트를 줌 (무시X) : 주석+힌트
- 주석 : 컴파일러가 읽지 않음 (무시 O)
    
    `@override` → 재정의 된 메소드
    
    `@compont`  → 클래스 메모리에 로딩
    
    `@Autowired` → 로딩 된 객체를 해당 변수에 집어 넣음
    
- Reflection : 런타임 시 분석하는 기법
    
    클래스 내부에 있는 메서드, 필드, 어노테이션 등 분석 후 설정 가능
    

- Complie Checking : 어노테이션을 보고 재정의 되고 있음을 확인하는 과정

---

# 3강 - 메세지 컨버터가 무엇인가요?

<aside>
<img src="https://www.notion.so/icons/forward_gray.svg" alt="https://www.notion.so/icons/forward_gray.svg" width="40px" /> 스프링은 MessageConverter를 가지고 있다. 기본값은 현재 Json이다.

스프링은 BufferedReader와 BufferedWriter를 쉽게 사용할 수 있다.

스프링은 계속 발전중이다.

</aside>

## ✅ 3-1 : MessageConverter (JSON)

- 중간데이터 
중간 언어 : xml → json
- 모든 나라 사람들이 다 이해하기 쉬운 언어
- 자바 object ↔ 파이썬 object 변환이 어려운데, 중간 언어인 json을 사용
- 자바 object ↔ (messageconverter) ↔ **JSON ↔** 파이썬 object
- 전송, 응답 받을 때 모두 바꿔줌

![스크린샷 2024-06-25 22.39.06.png](1,2,3%E1%84%80%E1%85%A1%E1%86%BC%20-%20%E1%84%89%E1%85%B3%E1%84%91%E1%85%B3%E1%84%85%E1%85%B5%E1%86%BC%20%E1%84%80%E1%85%B5%E1%84%87%E1%85%A9%E1%86%AB%20%E1%84%80%E1%85%A2%E1%84%82%E1%85%A7%E1%86%B7%20bb0c73579ca047d5a17fc2db7df9087a/%25E1%2584%2589%25E1%2585%25B3%25E1%2584%258F%25E1%2585%25B3%25E1%2584%2585%25E1%2585%25B5%25E1%2586%25AB%25E1%2584%2589%25E1%2585%25A3%25E1%2586%25BA_2024-06-25_22.39.06.png)

## ✅ 3-2: BufferedReader와 BufferedWriter

- ByteStream : 1Byte = 8bit 전송
- InputStream : 읽을 때, 문자로 변형하기 위해 Char 로 바꾸기 번거로움
- → InputStreamReader : 문자 하나 + 배열로 여러개의 문자 받을 수 있음
    - 배열 : 여러개의 문자를 받을 수 있지만 고정적 길이
    - 낭비 심해서 안씀!

- BufferedReader : 문자열로 가변길이로 받을 수 있음
- BufferedWriter
- PrintWriter : BufferedWriter + Println() ← 얘를 더 많이 씀
- `@ResponseBody` 어노테이션 → BufferedWriter
- `@RequestBody` 어노테이션 → BufferedReader

```java

```

## ✅ 3-3 : Spring은 발전 중

---

- +++
    
    빈
    
    [🟢 [Spring] 스프링 빈(Bean) 이란?](https://dev-wnstjd.tistory.com/440)
