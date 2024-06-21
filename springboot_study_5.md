### 스프링부트 개념정리 5강 - ORM이란 무엇인가요?

#

### JPA란

- JPA는 Java Persistence API 이다.
    - Persistence(영속성) : 데이터를 생성한 프로그램의 실행이 종료되더라도 사라지지 않는 데이터의 특성을 의미한다.
    - JPA : 자바에 있는 데이터를 DB(영구)에 기록할 수 있는 환경을 제공하는 API이다.
    - API(Application programming interface) : 정의 및 프로토콜 집합을 사용하여 두 소프트웨어의 구성 요소가 서로 통신할 수 있게 하는 규칙
    - 프로토콜과 인터페이스 차이 (모두 규칙이다.)
        
        인터페이스 : 한 사람이 규칙(인터페이스)을 정하면 사용하는 사람은 규칙(인터페이스)을 따라야 한다. (상하관계가 존재하는 약속)
        
        프로토콜 : 서로 동등한 관계의 약속이다. 모두 동의할 수 있는 규칙(프로토콜)을 사용한다.
        
    - (정리) JPA는 JAVA 프로그래밍을 할 때 영구적으로 데이터를 저장하기 위해 필요한 인터페이스이다.
- JPA는 ORM 기술이다.
    - ORM(Object Relational Mapping) : Object를 데이터베이스에 연결하는 방법이다.
    - 모델링 : 추상적인 개념(클래스)을 현실 세계에 구현하는 것이다.
    - DB 테이블에 데이터를 저장, 변경, 조회, 삭제 하는 행위는 DML(Delete, Update, Insert)을 사용한다. 이때 자바와 DB의 데이터 타입이 다르므로 자바에서 클래스를 통하여 DB의 테이블을 모델링한다.
    - ORM은 클래스를 먼저 만들고 데이터베이스를 자동 생성할 수 있다. 이때 필요한 것이 JPA의 인터페이스이다.
- JPA는 반복적인 CRUD 작업을 생략하게 해준다.
    - DML은 자주 반복적으로 일어난다.
    - 데이터 입력, 변경, 삭제, 조회 과정
        1. 자바에서 DB로 커넥션 연결 요청
        2. DB가 세션을 오픈한다.
        3. 두 번째 요청 시 쿼리를 전송한다.
        4. DB는 작업을 수행하여 Data를 자바에 응답한다.
        5. 자바는 응답받은 Data와 가지고 있는 데이터 타입이 다르므로 데이터타입을 받아서 Java Object로 변경해야 한다.
    - JPA는 CRUD 일련의 작업을 함수로 제공해 주어 데이터 CRUD 처리 과정을 줄여준다.
- JPA는 영속성 컨텍스트를 가지고 있다.
- JPA는 DB와 OOP의 불일치성을 해결하기 위한 방법론을 제공한다. (DB는 객체저장 불가능)
- JPA는 OOP의 관점에서 모델링을 할 수 있게 해준다. (상속, 콤포지션, 연관관계)
- 방언 처리가 용이하여 Migration하기 좋다. (유지보수에도 좋다.)
- JPA는 쉽지만 어렵다.

#

### ORM(Object Relational Mapping)

- 자바의 객체와 데이터베이스를 연결하는 프로그래밍 기법이다.
- 객체와 데이터베이스를 연결하여 자바 언어로만 데이터베이스를 다룰 수 있게 하는 도구이다.

### ORM의 장점

1. SQL을 직접 작성하지 않고, 사용하는 언어로 데이터베이스에 접근할 수 있다.
2. 객체지향적으로 코드를 작성할 수 있기 때문에 비즈니스 로직에만 집중할 수 있다.
3. 데이터베이스 시스템에 대한 종속성이 줄어든다. 
    
    (데이터베이스 시스템이 추상화되어 있기 때문에 MySQL에서 PostgreSQL로 전환한다고 해도 추가로 드는 작업이 거의 없다.)
    
4. 매핑하는 정보가 명확하기 때문에 ERD에 대한 의존도를 낮출 수 있고, 유지보수할 때 유리하다.

### ORM의 단점

1. 프로젝트의 복잡성이 커질수록 사용 난이도도 올라간다.
2. 복잡하고 무거운 쿼리는 ORM으로 해결이 불가능한 경우가 있다.

### ORM 예시

- (예시) question 테이블
    
    
    | id | subject | content |
    | --- | --- | --- |
    | 1 | 안녕하세요 | 가입 인사드립니다 |
    | 2 | 질문 있습니다 | ORM이 궁금합니다 |
    | … | … | … |
- question 테이블에 데이터를 저장하는 SQL 쿼리
    
    ```java
    insert into question (id, subject, content) values (1, '안녕하세요', '가입 인사드립니다');
    insert into question (id, subject, content) values (2, '질문 있습니다', 'ORM이 궁금합니다');
    ```
    
- ORM을 사용하여 question 테이블에 데이터를 저장하는 코드
    
    ```java
    Question q1 = new Question(); 
    q1.setId(1);
    q1.setSubject("안녕하세요");
    q1.setContent("가입 인사드립니다 ^^");
    this.questionRepository.save(q1);
    
    Question q2 = new Question();
    q2.setId(2); 
    q2.setSubject("질문 있습니다"); 
    q2.setContent("ORM이 궁금합니다"); 
    this.questionRepository.save(q2);
    
    // Question은 자바 클래스이다.
    // 데이터를 관리하는데 사용하는 ORM의 자바 클래스이므로 Question은 엔티티(entity)이다.
    ```
    

### JPA의 CRUD 메소드 예시

- Update()는 Save()가 담당하며, 새로운 객체면 Insert하고 기존에 있는 객체이면 병합을 시도한다.
    
    ```java
    package com.jpasample;
    
    import com.jpasample.entity.User;
    import com.jpasample.repository.UserRepository;
    import org.junit.jupiter.api.Test;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.boot.test.context.SpringBootTest;
    
    import java.util.Optional;
    
    @SpringBootTest
    class JpasampleApplicationTests {
    
    	@Autowired
    	UserRepository userRepository;
    
    	@Test // 정보가 담긴 객체값을 넘겨줌으로써 INSERT
    	void jpaSave() {
    		for(long i = 0L; i<10; i++){
    			User user = User.builder().name("Hi"+i).build();
    			userRepository.save(user);
    		}
    	}
    
    	@Test // PK값을 기준으로 SELECT
    	void jpaFind(){
    		Optional<User> user = userRepository.findById(3L);
    		System.out.println(user.isPresent() ? user.get().toString() : "Nothing");
    	}
    
     
    	@Test // 필드값을 가지고 해당하는 데이터를 찾아 DELETE
    	void jpaDelete(){
    		userRepository.delete(User.builder().name("Hi4").id(5L).build());
    	}
    	
    	// Update()는 Save()가 담당한다.
    	// 새로운 객체면 Insert하고 기존에 있는 객체이면 병합을 시도한다.
    	
    }
    ```
    

### 변경감지(Dirty Checking)

- 트랜잭션 Commit시에 영속화되어 있는 Entity에서 가지고 있던 최초의 정보와 바뀐 Entity 정보를 비교하여 바뀐 부분을 자동으로 Update한다.
    
    ```java
    @Service
    public class DirtyChecking {
    
        @Autowired
        UserRepository userRepository;
    
        @Transactional // 트랙잭션으로 가둬줘야 변경감지가 가능하다.
        // JPA가 트랜잭션안에서 커밋된 시점에 flush를 할때, 
        // Entity의 변경된 점을 감지하고 UPDATE를 해주기 때문이다.
        public void updateUser(Long id, String name){
        
     		// DB에서 id값을 기준으로 데이터를 찾는다 (영속화)   
            Optional<User> user = userRepository.findById(id);
            
            // 만약 해당 값이 존재한다면 전달받은 name으로 set을 해준다.
            user.ifPresent(value -> value.setName(name));
        }
    ```
    
    ```java
    	@Test
    	void jpaDirtyChecking(){
    		dirtyChecking.updateUser(2L, "바뀌는 값");
    	}
    ```
    

#

### 참조

<a href="https://www.inflearn.com/course/%EC%8A%A4%ED%94%84%EB%A7%81%EB%B6%80%ED%8A%B8-%EA%B0%9C%EB%85%90%EC%A0%95%EB%A6%AC">스프링부트 개념정리(이론)</a><br>
<a href="https://yozm.wishket.com/magazine/detail/2160/">스프링 데이터 JPA, 5분 만에 알아보기</a><br>
<a href="https://wikidocs.net/160890">2-03 JPA로 데이터베이스 사용하기</a><br>
<a href="https://wooj-coding-fordeveloper.tistory.com/76#5. CRUD 메소드 생성 및 실행 !-1">[Spring] JPA의 기본 CRUD를 사용해보자 !</a><br>
<a href="https://wooj-coding-fordeveloper.tistory.com/77">[Spring] JPA 변경감지와, 병합을 통한 Update</a><br>