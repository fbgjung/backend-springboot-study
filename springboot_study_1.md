## Spring이란
- Framework이다.
- 오픈 소스이다.
- IoC(Inversion of Control, 제어의 역전) 컨테이너를 가진다.<br>
	기본적으로 new로 객체를 생성하면 heap영역에 할당된 객체를 생성한 메서드에서 관리<br>
	(다른 메서드에서 생성한 객체를 사용하기 어렵다는 단점이 존재)<br>
	IoC는 생성한 클래스를 Spring에서 자동으로 객체를 heap 메모리에 할당하여 관리<br>
	(모든 메소드에서 동일한 인스턴스 사용 가능 => 싱글톤)
- DI(Dependency Injection, 의존성 주입)를 지원한다.
- 엄청나게 많은 필터를 가지고 있다.
- 엄청나게 많은 어노테이션을 가지고 있다.	
- MessageConverter를 가지고 있다. 기본값은 현재 Json이다.
- BufferedReader와 BufferedWriter를 쉽게 사용할 수 있다.
- 계속 발전중이다.
 
#

### IoC(Inversion of Control, 제어의 역전) 

IoC는 제어의 역전이라는 의미로 객체의 생성과 관리를 개발자가 아닌 프레임워크가 담당하는 것이다. <br>
코드의 유지보수성과 확장성을 높이는 데 도움이 된다.<br>
IoC 컨테이너는 객체의 생성을 책임지고, 의존성을 관리한다. <br>
객체 생성 코드가 없으므로 TDD가 용이하다.


### IoC의 분류 – DL(Dependency Lookup)과 DI(Dependency Injection)

DL : 저장소에 저장되어 있는 Bean에 접근하기 위해 컨테이너가 제공하는 API를 이용하여 Bean을 Lockup하는 것 <br>
DI : 각 클래스 간의 의존관계를 빈 설정 정보를 바탕으로 컨테이너가 자동으로 연결해주는 것
- Setter Injection(수정자 주입), Constructor Injection(생성자 주입), Field Injection(필드 주입)
* DL 사용 시 컨테이너 종속이 증가하기 때문에 주로 DI를 사용한다.
* 빈(Bean) : 스프링 컨테이너가 관리하는 객체 / 컨테이너(IoC) : 빈 팩토리(Bean Factory)
- 객체의 생성과 객체 사이의 런타임 관계를 DI관점에서 볼 때 컨테이너를 BeanFactory라 한다.
- BeanFactory에 여러가지 컨테이너 기능을 추가한 어플리케이션컨텍스트(ApplicationContext)가 있다.


### DI(Dependency Injection)

DI는 의존성 주입이라는 의미로, 객체가 필요로 하는 의존성을 외부에서 주입해주는 것이다.
객체 간의 결합도를 낮추고, 코드의 재상용성을 높이는 데 도움이 된다.
- 필드 주입 (Field Injection) : 클래스에 선언된 필드에 생성된 객체를 주입해주는 방식으로, @Autowired 어노테이션을 명시
	```
	@Controller
	public class PetController{
		@Autowired
    	private PetService petService;
	}
	```
- 수정자 주입(Setter Injection) : 클래스의 수정자를 통해서 의존성을 주입하는 방식
	```
	@Controller
	public class PetController{

    	private PetService petService;

		@Autowired
    	public void setPetService(PetService petService){
    		this.petService = petService;
    	}
	}
	```
- 생성자 주입(Constructor Injection) : 클래스의 생성자를 통해서 의존성을 주입해주는 방식으로, 인스턴스가 생성될 때 1회 호출되는 것이 보장 (생성자 주입 시 필드에 final 키워드 사용 가능)
	```
	@Controller
	public class PetController{

    	private final PetService petService;

		@Autowired
    	public PetController(PetService petService){
    		this.petService = petService;
    	}
	}
	```
Spring에서는 객체의 불변성, 순환 참조 문제 방지 가능, 테스트 용이 등의 이유로 생성자 주입 방식을 권장한다.


### BeanFactory와 ApplicationContext

BeanFactory 
- BeanFactory 계열의 인터페이스만 구현한 클래스는 단순히 컨테이너에서 객체를 생성하고 DI를 처리하는 기능만 제공, Bean을 등록, 생성, 조회, 반환 관리를 한다.
- 팩토리 디자인 패턴을 구현한 것으로 BeanFactory는 빈을 생성하고 분배하는 책임을 지는 클래스이다.
- Bean을 조회할 수 있는 getBean() 메소드가 정의되어 있다. 

ApplicationContext
- Bean을 등록, 생성, 조회 반환 관리하는 기능은 BeanFactory와 동일하다.
- 스프링의 각종 부가 가능을 추가로 제공한다.
- 국제화가 지원되는 텍스트 메시지를 관리해준다.
- 이미지 같은 파일 자원을 로드할 수 있는 포괄적인 방법을 제공해준다.
- 리스너로 둥록된 빈에게 이벤트 발생을 알려준다.

#
### 참조
<a href="https://www.inflearn.com/course/%EC%8A%A4%ED%94%84%EB%A7%81%EB%B6%80%ED%8A%B8-%EA%B0%9C%EB%85%90%EC%A0%95%EB%A6%AC">스프링부트 개념정리(이론)</a><br>
<a href="https://dev-coco.tistory.com/80">[Spring] IoC 컨테이너 (Inversion of Control)란?</a><br>
<a href="https://velog.io/@think2wice/Spring-%EC%9D%98%EC%A1%B4%EC%84%B1-%EC%A3%BC%EC%9E%85DI%EC%97%90-%EB%8C%80%ED%95%98%EC%97%AC">[Spring] 의존성 주입(Dependency Injection)에 대하여</a>