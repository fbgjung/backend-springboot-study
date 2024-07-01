### 스프링부트 개념정리 8강 - HTTP가 무엇일까요? 정확히 알아야 해요

#

### 1. 스프링부트 동작 원리

### 1) 내장 톰켓을 가진다.

톰켓을 따로 설치할 필요 없이 바로 실행 가능하다.

- Socket : 운영체제가 가지고 있는 것이다.
- 예시 - A라는 대상과 B라는 대상이 있다. 서로 메시지를 교환하기 위해 운영체제가 제공하는 Soacket을 사용한다.
    - Socket 연결 방법
    1. A가 5000번 port로 Socket을 오픈한다. (port번호 필요)
    2. B는 IP주소와 port번호(5000번)로 연결한다. (IP주소:port번호)
    3. 연결이 완료되면 A와 B는 5000번 port로 서로 메시지 통신이 가능하다.
- 문제1 : C도 A와 통신하고 싶은 경우 A의 5000번 port는 B가 사용중이므로 통신이 불가능하다.
- 해결방법 : 5000번 port는 연결의 용도로 사용한다. 5000번 port로 B가 연결이 되는 순간 새로운 Socket을 랜덤으로 만들어 새로운 Socket(5001번)으로 메시지 통신을 한다.
- 문제2 : A와 B가 서로 통신중이므로 A는 C의 연결 요청을 받을 수 있는 자원이 없다.
- 해결방법 : 새로운 Socket(5001번)을 만들 때는 새로운 스레드(스레드1)를 만든다. (이때 5000번 port는 main 스레드에서 사용한다.) main 스레드는 사용자의 요청을 계속 받고 스레드1은 B와 통신한다. 따라서 C가 5000번 port에 접근하여 새로운 스레드(스레드2)와 소켓(5002번 port)을 랜덤하게 오픈한다. (port 지정 가능)
    - 참고 - 스레드는 시간을 쪼개서 동시에 동작하게 동작하고 있는 것처럼 보일 수 있다. (실제로 동시 동작이 아니고 시간을 나누어서 사용)

- HTTP 방식은 연결을 지속하지 않고 끊는 stateless방식을 사용한다. (HTTP 통신은 문서를 전달하는 통신이다.)
    - HTTP 통신 방법
        1. A가 a.txt 파일 요청
        2. C의 80 port로 요청을 전달받으면 다이렉트로 A에게 a.txt 파일 전달 후 소켓을 끊는다.
        3. B가 b.txt 파일 요청
        4. C의 80 port로 요청을 전달받으면 다이렉트로 B에게 b.txt 파일 전달 후 소켓을 끊는다.
    - HTTP 탄생
        
        스위스 Cern 연구소(입자물리연구소)에서는 학자가 논문을 낼 때 전세계에 있는 자신의 주제와 동일한 내용의 논문을 다 읽어봐야 한다. 다른 방에 있는 논문 또는 컴퓨터로 각각 찾아 봐야 했다. 
        
        각 방의 모든 컴퓨터를 하나의 서버에 연결하여 논문을 서버에 저장한다. 논문이 필요 시 컴퓨터에서 서버로 조회하여 찾아온다. 논문을 한 번 찾으면 연결을 지속할 필요가 없으므로 HTTP 통신으로 만든다. 
        
        HTTP의 목적은 html 확장자로 만들어진 문서를 필요한 사람에게 제공하는 것으로, 문서 전달의 목적이다.
        
        HTTP 창시자 - 팀 버너스리 (2018 개인이 데이터를 통제하는 새로운 웹 진행중)
        

- Socket 방식과 HTTP 방식 비교
    
    Socket 방식은 연결이 계속되므로 부하가 크지만, Socket에 한 번 연결(인증)되면 서버는 연결된 대상을 계속 알고 있다. 
    
    HTTP 방식은 연결이 끊기므로 부하가 적지만, 다시 연결(재연결)되면 항상 새로운 사람으로 인식한다.

#

### Socket 통신 방식

- Socket을 통해 서버-클라이언트간 데이터를 주고받는 양방향 연결 지향성 통신이다.
- Socket은 클라이언트 소켓과 서버 소켓으로 구분된다.

### Socket의 구성 요소

- 프로토콜 : 데이터 전송을 위한 표준 집합 규칙으로 주로 TCP/IP, UDP/IP 프로토콜을 사용
    - TCP/IP는 연결 설정 후 통신이 가능한 연결형 프로토콜이다.
        
        신뢰성이 있는 데이터 전송의 특징을 가지고 있으며, 필요할 경우 데이터를 재전송한다. 데이터 경계의 구분이 없는 바이트 스트림(Byte-Stream) 서비스이다.
        
    - UDP/IP는 연결 설정이 없이 통신이 가능한 비연결형 프로토콜이다.
        
        TCP에 비해 신뢰성이 떨어지는 데이터 전송의 특징을 가지고 있으며, 데이터를 재전송하지 않는다. 데이터 경계를 구분하는 데이터그램(Datagram) 서비스이다.
        
- IP : 네트워크 상에서 클라이언트와 서버에 해당되는 컴퓨터를 식별하기 위해 사용
- Port : 해당 컴퓨터내에서 현재 통신에 사용되는 응용프로그램을 위해 사용

### Socket의 종류 및 흐름

1. 스트림 소켓(Stream Socket)
    - TCP를 사용하는 연결 지향방식의 소켓으로 송수신자의 연결을 보장하여 신뢰성있는 데이터 송수신이 가능하다.
    - 데이터의 순서가 보장되며, 대량의 데이터 전송에 적합하다.
    
    <img src="./img/8_TCP_socket_flow.png" width="500px" height="600px" title="TCP_socket_flow"/><br>
    
    - 스트림 소켓 함수
        - socket() : 소켓 생성
        - bind() : 사용할 IP address와 Port number 등록
        - listen() : 연결되지 않은 소켓을 요청 수신 대기 모드로 전환
        - connect() : Client에서 Server와 연결하기 위해 소켓과 목적지IP, Port 지정
        - accept() : Client의 요청 수락 후 실직적인 소켓 연결(통신을 위한 새로운 소켓 생성으로 처음 만들어진 소켓은 계속 Client의 요청을 대기)
        - send(), recv() : Client는 처음에 생성한 소켓으로 , Server는 새로 생성된 소켓으로 Client와 Server간에 데이터 송수신
        - close() : 소켓 닫기
2. 데이터그램 소켓(Datagram Socket)
    - UDP를 사용하는 비연결형 소켓으로 데이터의 순서와 신뢰성을 보장하기 어렵다.
        
        <img src="./img/8_UDP_socket_flow.png" width="500px" height="500px" title="UDP_socket_flow"/><br>
        
    - connect()과정이 필요 없기 때문에 소켓을 생성한 후 바로 데이터를 전송한다.

### Socket 구현 방법 - 스트림 소켓(TCP)

1. 서버 소켓
    
    ```jsx
    1) 서버소켓 생성
        ServerSocket serverSocket = new ServerSocket(8000);  // 포트번호
     
    2) 클라이언트 접속 대기
        Socket socket = serverSocket.accept( );
     
    3) 데이터 송수신을 위한 input/output 스트림 생성 
        InputStream in = socket.getInputStream( );
        OutputStream out = socket.getOutputStream( );
     
    4) input 스트림을 통한 데이터 수신 (클라이언트 → 서버)
        byte[ ] inputData = new byte[100];
        int length = in.read(inputData);
        String inputMessage = new String(inputData, 0, length);
     
    5) output 스트림을 통한 데이터 송신 (서버 → 클라이언트) 
        String outputMessage = "보낼메시지";
        out.write(outputMessage.getBytes( ));
        out.flush( );
     
    6) 통신 종료
        socket.close( );
        serverSocket.close( );
    ```
    
2. 클라이언트 소켓
    
    ```jsx
    1) 클라이언트 소켓 생성을 통한 서버접속
        Socket socket = new Socket("127.0.0.1", 8000);  // IP주소, 포트번호
     
    2) 데이터 송수신을 위한 input/output 스트림 생성 
        InputStream in = socket.getInputStream( );
        OutputStream out = socket.getOutputStream( );
     
    3) output 스트림을 통한 데이터 송신 (클라이언트 → 서버)
        String outputMessage = "보낼메시지";
        out.write(outputMessage.getBytes( ));
        out.flush( );
     
    4) input 스트림을 통한 데이터 수신 (서버 → 클라이언트)
        byte[ ] inputData = new byte[100];
        int length = in.read(inputData);
        String inputMessage = new String(inputData, 0, length);
     
    5) 통신 종료
        socket.close( );
    ```
    

- BufferedReader/BufferedWriter와 PrintWriter, PrintStream
    
    데이터 전송시 기본단위 바이트(byte)로 변환이 필요하다. 자바에는 변환을 편리하게 해주는 클래스로 InputstreamReader와 OutputStreamWriter가 있으며, 데이터 입출력의 효율을 위해 바로 전달하지 않고 중간에 버퍼를 이용하기 위해서 BufferedReader와 BufferedWriter 클래스를 함께 사용할 수 있다.
    
    데이터 출력의 경우에는 출력 포맷을 편리하게 해주는 기능이 있는 PrintWriter 또는 PrintStream 클래스를 사용할 수 있다.
    
    ```jsx
    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream( )));
    BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream( )));
    
    PrintWriter out = new PrintWriter(socket.getOutputStream( ));
    PrintStream out = new PrintStream(socket.getOutputStream( ));
    ```
    

- 예시 코드 (1:1 채팅 프로그램)
    1. 서버
    
    ```jsx
    public class MyServer {
    	public static void main(String[] args) {
    		BufferedReader in = null;
    		PrintWriter out = null;
    		
    		ServerSocket serverSocket = null;
    		Socket socket = null;
    		Scanner scanner = new Scanner(System.in);
    		
    		try {
    			serverSocket = new ServerSocket(8000);
    			
    			System.out.println("[Server실행] Client연결대기중...");
    			socket = serverSocket.accept();			// 연결대기
    
    			System.out.println("Client 연결됨.");
    			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    			out = new PrintWriter(socket.getOutputStream());
    						
    			while(true) {
    				String inputMessage = in.readLine();	// 수신데이터 한줄씩 읽기	
    				if ("quit".equalsIgnoreCase(inputMessage)) break;
    				
    				System.out.println("From Client: " + inputMessage);
    				System.out.print("전송하기>>> ");
    				
    				String outputMessage = scanner.nextLine();
    				out.println(outputMessage);
    				out.flush();
    				if ("quit".equalsIgnoreCase(outputMessage)) break;
    			}
    		} catch (IOException e) {
    			e.printStackTrace();
    		} finally {
    			try {
    				scanner.close();		// Scanner 닫기
    				socket.close();			// Socket 닫기
    				serverSocket.close();		// ServerSocket 닫기
    				System.out.println("연결종료");
    			} catch (IOException e) {
    				System.out.println("소켓통신에러");
    			}
    		}
    	}
    }
    ```
    
    1. 클라이언트
    
    ```jsx
    public class MyClient {
    	public static void main(String[] args) {
    		BufferedReader in = null;
    		PrintWriter out = null;
    		
    		Socket socket = null;
    		Scanner scanner = new Scanner(System.in);
    		
    		try {
    			socket = new Socket("127.0.0.1", 8000);
    			
    			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    			out = new PrintWriter(socket.getOutputStream());
    			
    			while(true) {
    				System.out.print("전송하기>>> ");
    				String outputMessage = scanner.nextLine();
    				out.println(outputMessage);
    				out.flush();
    				if ("quit".equalsIgnoreCase(outputMessage)) break;
    								
    				String inputMessage = in.readLine();
    				System.out.println("From Server: " + inputMessage);
    				if ("quit".equalsIgnoreCase(inputMessage)) break;
    			}
    		} catch (IOException e) {
    			System.out.println(e.getMessage());
    		} finally {
    			try {
    				scanner.close();
    				if (socket != null) socket.close();
    				System.out.println("서버연결종료");
    			} catch (IOException e) {
    				System.out.println("소켓통신에러");
    			}
    		}
    	}
    }
    ```
    

### Socket 구현 방법 - 데이터그램 소켓(UDP)

1. 서버
    
    ```java
    public class UdpServer{
    	public void start() throws IOException {
    		// 포트 9999번을 사용하는 소켓 생성
    		DatagramSocket socket = new DatagramSocket(9999);
    		DatagramPacket inPacket, outPacket;
    		
    		byte[] inMessage = new byte[10];
    		byte[] outMessage;
    		
    		while(true) {
    			//데이터를 수신하기 위한 패킷 생성
    			inPacket = new DatagramPacket(inMessage, inMessage.length);
    			socket.receive(InPacket.getPort());
    			
    			// 수신한 패킷에서 클라이언트의 IP주소와 Port를 가져오기
    			InetAddress address = inPacket.getAddress();
    			int port = inPacket.getPort();
    			
    			//서버의 현재 시간을 시분초 형태로 반환
    			SimpleDateFormat simple = new SimpleDateFormat("[hh:mm:ss]");
    			String time = simple.format(new Date());
    			outMessage = time.getBytes(); // byte배열로 변환
    				
    			// 패킷을 생성하여 클라이언트에게 send
    			outPacket = new DatagramPacket(outMessage, outMessage.length, address, port);
    			socket.send(outPacket);
    			}	
    	}
    		
    	public static void main(String args[]) {
    		try {
    			new UdpServer().start(); // UDP서버를 실행
    		} catch(IOException e) {
    			e.printStackTrace();
    		}
    	}
    }
    ```
    
2. 클라이언트

    ```java
    public class UdpClient {
	    public void start() throws IOException, UnnownHostException {
		    DatagramSocket datagramSocket = new DatagramSocket();
		    InetAddress serverAddress = InetAddress.getByName("127.0.0.1");
    
		    // 데이터가 저장될 공간으로 임의로 지정한 크기의 byte배열을 생성한다.
		    // 한글은 1문자당 3byte로 저장
		    byte[] message = new byte[200];
		
		    DatagramPacket outPacket = new DatagramPacket(message, 1, seerverAddress, 9999);
		
		    DatagramPacket InPacket = new DatagramPacket(message, message.length);
		
		    // send메서드로 DatagramPacket을 송신
		    datagramSocket.send(outPacket);
		
		    // receive메서드로 DatagramPacket을 수신
		    datagramSocket.receive(inPacket);
		
		    System.out.println("서버의 현재 시간 : " + new String(inPacket.getData()));
		
		    datagramsocket.close();
	    }
	
	    public static void main(String args[]) {
		    try {
			    new UdpClient().start();
		    } catch (Exception e) {
			    e.printStackTrace();
		    }
	    }
    }
    ```

#

### 참조

<a href="https://www.inflearn.com/course/%EC%8A%A4%ED%94%84%EB%A7%81%EB%B6%80%ED%8A%B8-%EA%B0%9C%EB%85%90%EC%A0%95%EB%A6%AC">스프링부트 개념정리(이론)</a><br>
<a href="https://on1ystar.github.io/socket%20programming/2021/03/16/socket-1/">소켓 프로그래밍 - 소켓의 의미/특징/종류</a><br>
<a href="https://kadosholy.tistory.com/125">자바 - 소켓통신이란? 소켓(Socket) 개념과 사용방법</a><br>
<a href="https://velog.io/@newdana01/소켓이란-종류-통신-흐름-HTTP통신과의-차이">소켓이란? 종류, 통신 흐름, HTTP통신과의 차이</a><br>
<a href="https://shg-engineer.tistory.com/21">소켓(Socket)이란 뭘까?</a><br>
<a href="https://structuring.tistory.com/157">JAVA 문법 - 자바 TCP, UDP구현 :: 소켓 프로그래밍</a><br>
