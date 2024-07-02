<3강 - Springboot 동작원리>
 
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

### [정적페이지 생성 예제](https://chb2005.tistory.com/59)<br>
### [동적페이지 생성 예제(타임리프)](https://ittrue.tistory.com/85)<br><br>



<참고 사이트 - https://velog.io/@zzz0000227/%EC%9B%B9%EC%84%9C%EB%B2%84%EB%9E%80-%EB%AC%B4%EC%97%87%EC%9D%B8%EA%B0%80>


---------------------------------------------------------------------------


