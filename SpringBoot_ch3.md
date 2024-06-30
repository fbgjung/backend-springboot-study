<3강 - Springboot 동작원리>
 
 [1] HTTP가 무엇일까요? 정확히 알아야 해요 - Springboot 동작원리
 

---------------------------------------------------------------------------

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