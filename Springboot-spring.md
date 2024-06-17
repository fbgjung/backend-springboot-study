## Springboot 개념정리
1, 2, 3강
[(강의 링크)](https://www.inflearn.com/course/%EC%8A%A4%ED%94%84%EB%A7%81%EB%B6%80%ED%8A%B8-%EA%B0%9C%EB%85%90%EC%A0%95%EB%A6%AC/dashboard)
                             
### 스프링이란?                    
    
#### 1. Framework : 틀 안에서 동작   

<br>

#### 2. 오픈소스 (공개된 소스), 내부를 고칠 수 있다 = 기여            

<br>

#### 3. IoC Inversion of Controll (Spring이 객체를 제어) Container               
   Class : 추상적 ex) 캐릭터           
   Object : 실체화가 가능한 것 ex) 오리아나, 티모..             
   Instance : 실체화 된 것 ex) 협곡에 뛰어다니는 오리아나               
                 
   <기존>   
   개발자가 직접 new를 사용해서 heap에 객체를 넣어준다               
   <Spring>           
   Spring이 개발자가 원하는 객체를 “직접” 탐색(scan)해서 “직접” heap 메모리에 넣어준다               
                
   ex)
   ```j
        public class Main {
        public static void main2(String[] args) {
            HelloWorld hello = new HelloWorld();
            hello.setMessage("hello world!!");
            System.out.println(hello.getMessage());
        }

        public static void main(String[] args) {
            ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
            HelloWorld hello = (HelloWorld)context.getBean("helloWorld");
            System.out.println(hello.getMessage());
        }
    }
   // main2 : 개발자가 직접 new를 이용해서 객체를 생성
   // main : beans.xml 을 읽어서 xml 안에 저장된 “helloWorld” bean을 객체화
   ```
                     
   <차이점>          
   기존의 방법에서 hello world!! 의 문자열을 바꾸려면 .java → .class → 실행파일 로 컴파일이 되는 과정이 필요하다       
                 
   반면, Spring을 사용하면 beans.xml에 저장된 bean의 value값만 변경 후, java파일에 넣으면 된다
   즉, java 파일이 수정되는 일은 없다

                   
   ```
   + 추가
   contianer?
   → 개발자 대신 객체의 생서과 소멸을 전담하는 역할
   bean?
   → Spring 컨테이너에 의해 생성, 관리되는 객체
```
<br>

#### 4. DI Dependency Injection             
   실행하려는 소스코드 “밖”에서 정의된 객체를 받아서 실행하는 구조          
   == 개발자가 new를 사용해서 직접 객체를 만들지 않고, 외부에서 생성된 bean(객체)를 IoC 컨테이너가 넣어줌               
   ∴ IoC & DI를 이용한 코딩을 하면, 개발자가 어느 메소드에서 객체를 사용하던 같은 객체를 사용할 수 있다.
                           
   그럼 DI를  사용하려면 객체가 있어야하네? 매우 맞음ㅇㅇ              
   >DI 방법
>   1. 스프링을에서 의존성 주입 : xml 이용 / Java코드에서 @annotation 사용
>   2. DI 사용 : 생성자 / Setter

<br>

#### 5. 필터 filter         
   필터 : 검열의 기능, 권한을 체크, 보안 및 인증/인가      
   - 톰캣 tomcat이 가진 필터 = filter, filter의 기능을 하는 파일 = web.xml        
   - Spring Containter가 가진 필터 = 인터셉터(AOP)           
                                       
   ```
   + 추가     
   tomcat?
   -> 일종의 웹서버 WAS(Web Application Service), JSP(Java Server Pages)에 잘 맞음 == 자바환경에 잘 맞는 서버           
   AOP?
   -> Aspect Oriented Programming (관점 지향 프로그래밍), 어떤 관점을 기준으로 코드를 모듈화 하는 것
      이를 통해 중복되는 영역을 줄일 수 있다(IoC, DI로 Java의 중복위험을 최소화시킨 것처럼)            
   ```                        
<br>

#### 6. 어노테이션 Annotation (reflection, compile checking)            
   - 어노테이션 vs 주석      
   주석 : 컴파일러가 무시, 어노테이션 : 주석 + 컴파일러가 체킹할 수 있는 힌트 (컴파일러가 무시x)                  
                               
   ex) java에서 @Override를 사용해서 이게 재정의된 메소드다, 라고 알려주는게 어노테이션임. 컴파일은 이 어노테이션을 보고 진짜 이게 재정의되고 있는지 확인하는데 이게 `compile checking`     
                           
   ex) 스프링에서는 Annotation을 통해 기능을 수행한다.                     
       - `@Component` -> [클래스를 메모리에 로딩 (new)] 이라고 Annotation과 그 기능을 정하면, java에서는 new를 사용해서 heap에 객체를 생성했던 것과 달리            
       spring에서는 IoC container가 Annotation을 스캔해서 알아서 객체를 생성한다. =  `IoC 행위`      
       <br>
       -  `@Autowired` -> [로딩된 객체를 가져와서 변수에 집어 넣음] 이라고 Annotation과 그 기능을 정하면, 외부클래스에서 객체를 선언만 하고(new로 객체생성X) 사용할 수 있다               
       spring에서는 IoC컨테이너가 클래스를 스캔할 때 분석(`reflection`)한다. (refelction : 어떤 해당 클래스가 어떤 필드, 어떤 메소드, 어떤 어노테이션을 들고 있는지 분석하는 기법 -> 런타임에서 분석함)             
       여기서 @Autowired가 붙은 객체를 발견하면, @Component로 생성한 객체가 있는 heap에서 같은 객체를 찾아서 변수에 넣어준다. = `DI 행위`                
       <br>
       <br>
#### 7. MessageConverter
   MessageConverter : 자바 object를 Json으로 변경 & Json데이터를 자바 object로 변경(개발자가 직접 변경할 필요가 없음, Spring 기본 라이브러리)
   - request, response ex) 자바 object ⇄ Json ⇄ 파이썬 object

<br>

#### 8. BufferReader BufferWriter
   - Byte Stream에서 문장열로 가변길이의 데이터를 받고 쓰게 해주는 클래스
   - @ResponseBody : Buffer writer, @RequestBody : BufferReader (annotation을 사용할 수 있음)
```
   +추가
   - Byte Stream(통신)에서 1byte = 8bit 로 받는다     
   - 통신방법에는 여러가지가 있는데, HTTP 요청일 때 어노테이션을 사용해서 통신을 수행한다
```  
   <통신 과정>   
   1. Input Stream을 통해 byte타입의 정보를 받는다   
      (원래라면 문자타입 char로 casting해야했겠지만...)   
      (InputStream Reader을 통해 byte 정보 → 문자 하나 로 문자열(배열)을 받는다 BUT 배열은 미리 크기를 정해야하고, 때문에 크기가 작은 정보는 소멸위험이 있어서...)    
   2. BufferReader로 byte 정보를 `가변길이의 문자`를 받는다 with. 함수 request.getReader()
   3. 반대로 정보를 쓸 때 BufferWriter(\n 내려쓰기 때문에 PrintWriter을 씀)로

<br>

#### 9. 스프링은 계속 발전중
