# Interceptor, Filter, AOP의 차이
공통 기능을 모아서 처리할 수 있는 방법으로 Interceptor, Filter, AOP가 사용된다.  
하지만 이 세가지는 호출되는 시기가 다르다.  
- Filter는 Dispatcher Servlet 영역에 들어가기 전 Front Controller 앞 범위에서
- Interceptor는 스프링의 DispatcherServlet이 Controller를 호출하기 전
- AOP는 Controller 처리 이후 주로 비즈니스 로직에서 실행된다.

<img width="500" alt="1" src="https://github.com/fbgjung/study-notes/assets/104186871/f03a672a-4c01-421c-a4df-278d4f0d34f9">

## 1. Filter
- 스프링 컨텍스트 외부에 존재하여 스프링에 무관한 자원에 대해 동작
- `HTTP` 프로토콜로 들어오는 모든 요청을 가장 먼저 받아 처리함
- `DispatcherServlet` 영역에 들어가기 전 `FrontController` 앞 범위에서 수행
- 필터 적용 시 필터가 호출된 후에 서블릿이 호출된다.
- 서블릿 요청과 응답을 가로채서 전처리 또는 후처리를 수행한다. 

> DispatcherServlet  
> `스프링의 가장 앞단에 존재하는 FrontColtroller`
> 스프링 웹 애플리케이션의 중심에서 모든 요청을 관리하고 처리하는 중요한 역할을 한다.
> HTTP 프로토콜로 들어오는 모든 요청을 가장 먼저 받아 적합한 컨트롤러에 위임해주는 FrontController

### `메소드`
- doFilter(): 요청이 들어올때마다 해당 메소드가 호출된다. HTTP 요청이 오면 작동한다.
- init(), destroy(): 따로 구현하지 않아도 된다.

### `사용`
- 보안검사: 스프링 컨테이너까지 요청 전달이 안되고 차단되어 보안성이 높아진다.
- 인증/인가 공통 작업 (ex Spring Security: Spring MVC에 종속적이지 않아 필터 기반으로 인증/인가 처리)
- 암복호화 등 인코딩 변환처리
- 이미지/데이터 압출

## 2. Interceptor
- 요청 가로채기 (작업 전/후)
- 스프링 컨텍스트 영역 내부에서 Controller에 관한 요청과 응답에 대해 처리
- Interceptor는 DispatcherServlet에 N개 등록될 수 있다.
- 스프링의 모든 @Bean에 접근이 가능하다.

### `메소드`
- preHandler(): 컨트롤러 메소드가 실행되기 전, 전처리 작업이나 요청 정보를 가공하거나 추가하는 경우
- postHandler(): 컨트롤러 메소드 실행 직후 view 페이지 렌더링 되기 전 (후처리 작업)
- afterCompletion(): view 페이지가 렌더링 되고 난 후 (모든 뷰에서 최종 결과를 생성하는 일을 포함해 모든 작업이 완료된 후) 요청 처리 중에 사용한 리소스를 반환할 때

### `사용`
- 세부적인 보안 및 인증/인가 공통 작업: 권한 체크, 로그인 체크 (Controller에 제공하는 경로에 접근하기 전에 권한 체크)
- API 호출에 대한 로깅 또는 검사
- Controller로 넘겨주는 정보(데이터)의 가공

## 3. AOP
- Controller 처리 이후 주로 비즈니스 로직에서 실행된다.

### `사용`
- 로그 처리: ex. Service에서 수행하는 메소드가 각각 얼마나 걸리는지 시간을 측정하는 로그 뿌리기
- 트랜잭션
- 에러처리

## 비교
<img width="500" alt="2" src="https://github.com/fbgjung/study-notes/assets/104186871/eeb56710-5a5d-41af-9f66-b37d24f62496">


## 참고
[Interceptor, Filter, AOP의 차이](https://velog.io/@sweet_sumin/Interceptor-Filter-AOP%EC%9D%98-%EC%B0%A8%EC%9D%B4)