![image](https://github.com/user-attachments/assets/b85c0d16-b10c-4df4-b64b-31ed4ddbc042)<3강 - Springboot 동작원리>
 
 [1] HTTP가 무엇일까요? 정확히 알아야 해요 - Springboot 동작원리
 [2] 톰켓이란 무엇인가요?
 
 
---------------------------------------------------------------------------

(8강)<br>
[1] HTTP가 무엇일까요? 정확히 알아야 해요 - Springboot 동작원리 (1) <br> <br>

*스프링부트 동작원리 <br> <br>

**<1> 내장 톰켓을 가진다.** <br>
: 톰켓을 따로 설치할 필요 없이 바로 실행가능하다. <br> <br>

	* 소켓(socket) : 운영체제가 가지고 있는 것.
	  1) 통신 연결을 위해 B와 A가 소켓을 오픈하여 ip 주소를 연결함
	  2) C도 연결하기 위해 같은 주소를 이용하려 했지만 이미 사용하고 있어서 불가능
	  => TimeSlice 동작 : 메인 스레드와 다른 스레드를 두고 연결을 계속 이어감.
	  만약 다른 연결이 필요하면 기존 연결을 끊었다가 이었다가 하면서 통신 수행 

	* 소켓 통신의 문제점 
	: 연결 수가 늘어나게 되면 부하가 증가함 

  <p align="center">
  <img src="https://github.com/fbgjung/backend-springboot-study/assets/131326799/386cd4a1-9f36-491d-96d3-faeda46bce23"></p> 


<2> Http 통신 - stateless 방식 <br>
: http 통신이란 문서를 전달하는 통신을 의미 <br>
=> 통신 연결 후 필요한 작업을 수행하고 나면, 연결을 끊어버림 <br> <br> <br>


<3> 웹 서버 <br>
: 소켓과 http 통신의 단점을 해결하는 방식 <br>
: 클라이언트가 하나의 서버에 연결되어 있는 상태 <br>
=> 필요한 정보를 얻기 위헤 굳이 연결할 필요가 없음 <br> <br> <br>

---------------------------------------------------------------------------

(9강)<br>
[2] 톰켓이란 무엇인가요?<br><br>

<h3><0> 관련 단어</h3>
* 웹 서버란?<br>
: 웹 서버는 작성된 html페이지 등을 네트워크 망에 종속되지 않고, 웹서비스를 할 수 있도록 하는 어플리케이션으로, 클라이언트에게 요청을 받으면 해당 요청을 판단하여 정적인 자원은 WAS를 거치지 않고 바로 응답해주고, 동적인 자원은 WAS에 요청을 전달하여 WAS에서 받은 결과를 클라이언트에게 응답한다.<br>
: 클라이언트(웹 브라우저)로부터 HTTP 프로토콜로 요청을 받아, HTML 문서 등과 같은 정적 웹 페이지를 응답해주는 소프트웨어<br>
: 동적인 컨텐츠 제공을 위한 요청을 WAS에게 전달한 후, WAS가 처리한 결과를 클라이언트에게 응답함<br><br>
	
	(0-1) 하드웨어 측면에서의 웹 서버
	: 웹 서버의 소프트웨어와 웹사이트의 컴포넌트 파일들을 저장하는 컴퓨터
	(+ 컴포넌트 파일 : html, image, css, javaScript 등)
	
	(0-2) 소프트웨어 측면에서의 웹 서버
	: 웹 사용자가 호스트 파일들에 어떻게 접근하는지를 관리
	
	=> 브라우저(클라이언트)가 HTTP를 통해 파일 요청
	=> 요청이 올바르게 웹 서버에 도달 (하드웨어)
	=> HTTP 서버는 요쳥된 문서를 HTTP를 통해 응답 (소프트웨어)
	
  <img src="https://github.com/fbgjung/backend-springboot-study/assets/131326799/dda14edb-7ea8-4167-b0c6-53ce189f6386"></p> 
 
<br>
	* 웹서버와 톰캣(웹 컨테이너)의 차이<br>
		=> 웹 서버는 정적 데이터를 처리하며, 동적 데이터 처리가 필요한 경우 웹 컨테이너에게 전달<br>
		=> 톰켓(웹 컨테이너)은 동적 데이터를 처리하며, jsp나 서블릿을 구동할 수 있는 환경을 제공함<br><br>

<h3><1> http 통신</h3>
* 개념<br>
: http(=>hypertext transfer protocol : 두 컴퓨터 간의 hypertext를 전송하는 프로토콜)<br>
: 클라이언트의 요청을 받아 서버가 응답하는 방식. 이때 응답하는 자원의 유형은 static임.<br><br>

<h3><2> 소켓 통신</h3>
* 개념<br>
: 웹 서버 쪽에서 클라이언트의 요청없이 컨택하고 싶을 때 사용하는, 연결을 지속하는 통신<br><br>

<h3><3> 웹 서버(아파치)와 톰켓</h3>
* 상황 : 클라이언트가 웹 서버에 jsp 파일을 요청 (또는 java 파일)<br>
1) 웹 서버가 톰켓에게 제어권을 넘김<br>
2) 톰켓은 jsp의 자바 코드를 컴파일한 후 .html에 덮어 씌움<br>
3) 생성한 html 파일을 아파치에게 전달함<br>
4) 아파치는 받은 html 파일을 클라이언트에게 넘김<br><br>

<h3><4> 정적 페이지와 동적 페이지</h3>
* Static Pages<br>
Web Server는 파일 경로 이름을 받아 경로와 일치하는 file contents를 반환하며, 항상 동일한 페이지를 반환한다.<br>
Ex) image, html, css, javascript 파일과 같이 컴퓨터에 저장되어 있는 파일들<br><br>
	 <p align="center">
  <img src="https://github.com/fbgjung/backend-springboot-study/assets/131326799/1b6e16eb-5521-409f-a230-35cf857551db"></p> 

**Dynamic Pages**
동적인 contents를 반환한다. 즉, 웹 서버에 의해서 실행되는 프로그램을 통해서 만들어진 결과물.<br>
개발자는 Servlet에 doGet()을 구현한다. (Servlet: WAS 위에서 돌아가는 Java Program)<br>
	(1) doGet : Get방식에서 호출되는 메소드, URL에 정보가 포함되어 보안에 약함. 기본 호출 메소드.
	(2) doPost : Post방식에서 호출되는 메소드, URL에 정보가 포함되지 않아 안전하며, 헤더에 정보를 실음.
  <p align="center">
  <img src="https://github.com/fbgjung/backend-springboot-study/assets/131326799/07e1437b-383c-4efa-89d8-671fbeffccd0"></p> 

**정적 페이지 vs 동적 페이지**
<p align="center">
  <img src="https://github.com/fbgjung/backend-springboot-study/assets/131326799/c0d9e7a7-260f-41d3-a98d-59beca8ec651"></p> 

<br>
<h3><5> WAS</h3>
* WAS(Web Application Server) : 동적인 컨텐츠를 제공하기 위해 만들어진 애플리케이션 서버<br>
	WAS : Web Server + Web Container(서블릿 컨테이너)<br>
	ex) DB 접속 기능 등<br><br>

### [톰캣 간단한 정적페이지 예제](https://goldsony.tistory.com/26)<br>
### [톰캣 간단한 jsp 파일 예제](https://wecanit.tistory.com/36#section3)<br><br><br>


### [스프링 프로젝트 생성](https://chb2005.tistory.com/58)<br>
### [스프링 정적페이지 생성 예제](https://chb2005.tistory.com/59)<br>
### [스프링 동적페이지 생성 예제(타임리프)](https://ittrue.tistory.com/85)<br><br>


<참고 사이트 - https://velog.io/@zzz0000227/%EC%9B%B9%EC%84%9C%EB%B2%84%EB%9E%80-%EB%AC%B4%EC%97%87%EC%9D%B8%EA%B0%80>


---------------------------------------------------------------------------

(10강)<br>
[3] 서블릿 객체의 생명주기가 궁금해요!<br>

<h3><1> 개념 설명 </h3>
1) 클라이언트 : 서블릿 컨테이너에게 자원을 요청<br>
2) 서블릿 컨테이너(ex 톰켓)<br>
	: 최초의 요청인 경우에는 메모리 로딩 > 객체 생성 > init() > 서비스 수행(요청/응답)
	: 최초의 요청이 아닌 경우 > 서비스 수행 (객체 재사용)
	 <p align="center">
  <img src="https://github.com/fbgjung/backend-springboot-study/assets/131326799/1fcf9b93-e694-480e-aeb0-024e708a4563"></p> 
<br>
(+ .html / .css / .png 와 같은 파일 >> 톰켓이 수행하지 않음)<br><br>

* URL과 URI의 차이점<br>
	- URL : 자원에 접근할 때 사용하는 방식
	- URI : 식별자를 통해 접근할 때 사용하는 방식
	
	<br><br>
	
<h3><2> 서블릿 객체의 생명 주기 과정</h3>
<br>
<h4>(서블릿 객체가 호출되는 조건 : 자바 관련 자원 요청이 들어왔을 때)</h4>
	1) 클라이언트로부터 request가 오면, 서블릿 컨테이너에서 서블릿 객체 생성
	2) init()-> 초기화 함수
	3) Service() -> Post / Get / Put 중 어떤 방식으로 했는지에 따라
	4) 함수가 호출됨 (ex get() / ... / post() 등)
	5) DB 연결, 해당 데이터를 html에 담아서 응답
<br>
<h3>* 풀링 기법</h3>
	: 동시 생성 가능한 스레드 개수가 20개로 제한되어 있을때, 클라이언드 25명이 동시 접근하는 상황
	=> 스레드는 최대 20개가 돌아가다가, 서비스가 끝나면 해당 스레드를 재사용함. (컴퓨터 성능에 따라 다름)
	=> 재사용 가능한 서블릿 객체는 1개임


---------------------------------------------------------------------------

(11강)<br>
[4] 웹 배포서술자(web.xml)에 대해서 알려줘요!<br><br>

<h3>web.xml이 하는 일</h3>
<br>

  	<p align="center">
  	<img src="https://github.com/fbgjung/backend-springboot-study/assets/131326799/9402b242-ace8-4fb5-892e-35a717802d7c"></p> 
	* 문지기 = web. xml<br>
	1) 성을 관리하는 관리자와 성을 지키는 문지기가 있음<br>
	2) 관리자는 문지기에게 "~이러한 일을 하라"는 문서를 제공함<br>
	3) 관리자가 변경될 때마다 문지기가 하는 일이 달라짐 (but, 문지기가 변경되지는 않음)<br>

<br><br>
<br>
	1) ServletContext의 초기 파라미터<br>
		ex) 누군가 성에 들어올 때 암구호를 물어봄<br>
		: 한번 설정하면 변경되지 않음<br>
		: 한번 설정하면 어디에서든지 동작할 수 있음<br><br><br>

	2) Session의 유효시간 설정<br>
		* 세션(session) : 인증을 통해 들어오는 것<br>
		ex) 너 3일 동안 성 돌아다녀도 돼! -> 3일 뒤 -> 추방<br>
		ex2) 문지기(web.xml)에게 가서, 나 3일 더 있을래! -> OK<br><br><br>

	3) Servlet/JSP에 대한 정의 및 매핑<br>
		* (가/나/다/라) : 식별자나 특정 로케이션이 될 수 있음<br>
		ex) 대상 a(나)가 (가/나/다/라) 목적지 중에 나는 (나)로 가겠다고 함<br>
			-> 문지기는 a가 (나)까지 이동할 수 있도록 도움 (=> 매핑)<br><br><br>
	
	4) Mime Type 매핑<br>
		ex) a가 쌀을 많이 들고 오는 상황 -> Mime Type : 쌀<br>
			=> 기존의 쌀은 가공 전이므로, Mime Type 매핑을 통해 알맞게 가공함<br>
			(※ 주의 : 쌀이 물창고로 가지 않고 쌀 창고로 가도록 유의!)<br>
		ex2) a가 아무것도 들고 오지 않는 상황 -> get방식으로 데이터를 응답해 줌<br><br><br>

	5) Welcome File list 설정<br>
<p align="center">
  		<img src="https://github.com/fbgjung/backend-springboot-study/assets/131326799/69a150df-7c00-4a14-934e-0f4c1a80a720"></p> 
		ex) 아무것도 가져오지(예:쌀)않은 경우, 일거리를 주기 위해 광장으로 보냄<br><br><br>

	6) Error Pages 처리<br>
		ex) 성 내부에서 처리할 수 없는 (파)와 같은 것을 들고 오면 -> 에러페이지 광장으로 보냄<br><br><br>

	7) 리스너/필터 설정<br>
<p align="center">
  		<img src="https://github.com/fbgjung/backend-springboot-study/assets/131326799/c50773cd-fa3a-4262-a6d7-ca10e7a042f7"></p> 
		* 필터 예시 : A 나라 성에 B 나라 사람이 들어오는 경우 들어오지 못하게 하는 것<br>
		* 필터 예시 : A 나라 사람인 a이더라도, 소지 불가인 '총'을 가져오면 뺏는 것<br><br>

		* 리스너 : 관리자 양반이 문지기에게 "~한 사람 있으면 알아봐줘"<br>
					-> 문지기 왈 "할 일 너무 많음!"<br>
					-> 관리자 왈 "그러면 너 대신 알아보는 사람 붙여줄게"<br>
					=> 여기서 말하는 대리인 : 리스너<br><br>
		* 리스너 예시 : a, b, c, d, e가 순서대로 성으로 들어오는 중..<br>
					-> 문지기는 원래 하던 대로 들어오는 사람에 대한 기존 업무 진행<br>
					-> 리스너가 들어오는 사람 지켜보면서 너 "~한 일" 할 수 있니? 물어봄<br>
					-> 할 수 있다고 하는 사람이 오면 직접 확인해본 후, 문지기로부터 낚아채서 관리자에게 대령함<br><br><br>

	8) 보안 설정<br>
		ex) 내 성에 이상한 사람이 들어오면 쫓아내거나 감옥에 보내는 일

<br><br>

---------------------------------------------------------------------------

(12강)<br>
[5] 디스패처 서블릿이 무엇인가요?<br><br>

<p align="center">
  	<img src="https://github.com/fbgjung/backend-springboot-study/assets/131326799/33549048-859d-496a-9095-a70c6b3d9fb9"></p>

	
	* Servlet/JSP 매핑 : web.xml에 직접 매핑 or @WebServlet 어노테이션 사용

	Servlet/JSP 매핑 시에 모든 클래스에 매핑을 적용시키기에는 코드가 복잡해지므로,
	FrontController 패턴을 이용함.

	1) FrontController 패턴
		최초 앞단에서 request 요청을 받아서 필요한 클래스에 넘겨준다.
		-> web.xml에 다 정의하기 너무 많고 힘들기 때문

		이때, 새로운 요청이 생기기 때문에 request와 response가 새롭게 new될 수 있다.
		=> 그래서 RequestDispatcher가 필요하다.

		* request에는 요청한 사람의 정보가 들어 있음 (어떤 자원인지? 요청의 종류가 뭔지?)

		* FrontController는 .do(..)를 낚아 챔
		ex) FrontController에 낚아 챈 a.do(특정 주소)와 b.do(특정 주소)가 있을 때,
			특정 자원에 대해 request 할 수 있도록 함
			-> 이때 request가 또 수행되면, request는 사라지게 됨
			-> 이를 방지하기 위한 것이 RequestDispatcher임

	2) RequestDispatcher
<p align="center">
  		<img src="https://github.com/fbgjung/backend-springboot-study/assets/131326799/dfe34b52-8a44-4979-b9f1-ad782e101af6"></p>
		필요한 클래스 요청이 도달했을 때, FrontController에 도착한 request와 response를 그대로 유지시킴
		=> 페이지 간 데이터 이동이 가능하게 함

	3) DispatcherServlet
		* 개념 : FrontController 패턴 + RequestDispatcher
		* DispatcherServlet가 생성될 때, 수 많은 객체가 생성(IoC)된다. -> 보통 필터
		* 해당 필터들은 직접 등록 or 자동 등록(기본적)할 수 있다.

---------------------------------------------------------------------------

(13강)
[6] 애플리케이션 컨텍스트란 무엇인가요?<br>


	 디스패처 서블릿에 의해 생성되어지는 수 많은 객체들이 관리되는 곳

	 1) 애플리케이션 컨텍스트에서 관리
	 	: 수 많은 객체들이 ApplicationContext에 등록됨 (IoC)
		: 필요한 곳에서 ApplicationContext에 접근하여 필요한 객체를 가져올 수 있음
		: 어디에서 접근하든 동일한 객체를 보장함 (싱글톤으로 관리되기 떄문)

	2) 애플리테이션 컨텍스트의 종류
		* root-applicationContext

		* servlet-applicationContext : ViewResolver, Interceptor, MultipartResolver 객체를 생성
				+ 웹과 관련된 어노테이션 Controller와 RestController를 스캔함

<p align="center">
  		<img src="https://github.com/fbgjung/backend-springboot-study/assets/131326799/a38c26e3-94fc-46ea-95d4-65254582e76e"></p>

<br><br>

<p align="center">
  	<img src="https://github.com/fbgjung/backend-springboot-study/assets/131326799/ca0e84ec-4645-4ca4-9dd0-e0f54752374b"></p>

	* web.xml을 거쳐 디스패처 서블릿으로 들어오게 되면, 객체들을 생성하기 위해 컴포넌트들을 스캔함
	ex) 특정 주소가 들어왔을 때, 해당 주소로 가도록 분배함
	ex2) 객체를 생성할 때, new 생성하는 역할도 함
	
	* 디스패처 서블릿으로 들어가기 전에, ContextLoaderListener에 먼저 들어간다.
	ex) ContextLoaderListener에서 미리 DB에 관련된 것 or 공통적으로 사용해야 하는 것들을 띄움
<br><br>

---------------------------------------------------------------------------

(14강) <br>
[7] 스프링부트가 응답(response)하는 방법이 궁금해요! <br><br>

<h3> 과정 요약1 </h3>
<p align="center">
  	<img src="https://github.com/user-attachments/assets/3bdae314-0c60-422b-b745-0ee710a10ea6"></p>
<h3> 과정 요약2 </h3>
<p align="center">
	<img src="https://github.com/user-attachments/assets/5b1b5c98-d28a-4791-bba3-acf44ddd397d"></p>


<p align="center">
  	<img src="https://github.com/user-attachments/assets/4fa040cd-03ee-42bf-9ba1-b011ce238e16"></p>

<p align="center">
  	<img src="https://github.com/user-attachments/assets/9695e971-8d13-4751-8a7f-f67c38f363cc"></p>
<p>
	▷ 데이터를 응답하는 경우 : MessageConverter 이용
	▷ HTML을 응답하는 경우 : ViewResolver 이용
</p>
<h3> handlerMappings 종류 </h3>
<p align="center">
  	<img src="https://github.com/user-attachments/assets/54641d55-eb98-4ecb-bc29-09684fa0bb6c"></p>

<h3> handlerAdapters 종류 </h3>
<p align="center">
  	<img src="https://github.com/user-attachments/assets/3b073a0e-5707-4384-9b1e-562d5628b562"></p>

<h3> returnValueHandler 종류 </h3>
<p align="center">
  	<img src="https://github.com/user-attachments/assets/632e712a-4514-4695-9041-b035ebe715f3"></p>

<h3> Converter 종류 </h3>
<p align="center">
  	<img src="https://github.com/user-attachments/assets/79de85fc-6713-41c2-95a2-a8272cc821ad"></p>

[참고] <br>
### [Springboot HTTP Request & Response 과정](https://velog.io/@guswlsapdlf/Springboot-HTTP-Request-Response-%EA%B3%BC%EC%A0%95)
### [디스패처 서블릿 동작 과정 사진 참고](https://zzang9ha.tistory.com/441)
### [Spring에서 request 처리 과정](https://newwisdom.tistory.com/38)
