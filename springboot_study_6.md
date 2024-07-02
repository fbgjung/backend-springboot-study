### 스프링부트 개념정리 6강 - 영속성 컨텍스트란 무엇인가요?

#

## JPA란

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
    - 영속성 : 어떤 데이터를 영구적으로 저장하게 해주는 것<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      (자바에서는 데이터를 영구적으로 저장할 때 데이터베이스를 사용한다.)
        
    - 컨텍스트(context) : 대상의 모든 정보를 가지고 있는 것
    - 영속성 컨텍스트 : 자바가 데이터베이스에 데이터를 저장할 때 필요한 모든 데이터를 알고 있다.
    - 데이터 저장 방법 : 자바에서 데이터를 영속성 컨텍스트로 전달 → 영속성 컨텍스트는 DB에 전달 → DB에 데이터 저장
    - 영속성 컨텍스트 데이터(Java Object)와 DB 데이터(DB type)는 동기화가 된다.
        
        (영속성 컨텍스트 데이터 삭제 후 DB에 전달하면 DB 데이터 삭제)
        
- JPA는 DB와 OOP의 불일치성을 해결하기 위한 방법론을 제공한다. (DB는 객체저장 불가능)
    - DB의 특징 : 각 컬럼은 데이터 타입이 기본타입이다. (Object를 가지지 못한다.)
    - 자바는 Object를 저장할 수 있다. (Class Player { int id; String name; Team team; } )
    - ORM을 사용하면 Object를 저장할 수 있는 자바가 주도권을 가진 모델을 만들 수 있다.
        
        (JPA를 통해서 Object 타입을 기본 타입(참조키)으로 자동으로 변경하여 저장한다.)
        
- JPA는 OOP의 관점에서 모델링을 할 수 있게 해준다. (상속, 콤포지션, 연관관계)
- 방언 처리가 용이하여 Migration하기 좋다. (유지보수에도 좋다.)
- JPA는 쉽지만 어렵다.

#

### 영속성 컨텍스트

- 엔티티를 영구 저장하는 환경이라는 의미로, 애플리케이션과 DB사이에서 객체를 보관하는 가상의 DB 역할을 한다.

### 엔티티 생명주기

- 비영속(new) 상태 : 영속성 컨텍스트와 관계가 없는 상태
    - 순수한 엔티티 객체 상태로 아직 저장하지 않아 영속성 컨텍스트나 데이터베이스와 관계가 없다.
    
    ```java
    // 객체를 생성한 상태 (비영속)
    Member member = new Member();
    member.setId("member1");
    member.setUsername("회원1");
    ```
    
- 영속(managed) 상태 : 영속성 켄텍스트에 저장된 상태
    - 엔티티 매니저를 통해 엔티티를 영속성 컨텍스트에 저장한 상태로, 영속성 컨텍스트에 의해 관리된다.
    
    ```java
    //객체를 지정한 상태(영속)
    em.persist(member);
    ```
    
- 준영속(detached) 상태 : 영속성 컨텍스트에 저장되었다가 분리된 상태
    - 영속성 컨텍스트가 관리하던 영속 상태의 엔티티를 영속성 컨텍스트가 관리하지 않으면 준영속 상태가 된다.
    - em.detach()는 준영속 상태를 명시적으로 호출한다.
    - em.close()는 영속성 컨텍스트를 닫으며, em.clear()로 영속성 컨텍스트를 초기화한다.
    
    ```java
    // 회원 엔티티를 영속성 컨텍스트에서 분리, 준영속 상태
    em.detach(member);
    ```
    
- 삭제(removed) 상태 : 삭제된 상태
    - 엔티티를 영속성 컨텍스트와 데이터베이스에서 삭제한다.
    
    ```java
    // 객체를 삭제한 상태(삭제)
    em.remove(member);
    // 영속성 컨텍스트를 비워도 관리되던 엔티티는 준영속성 상태가 된다.
    em.clear();
    // 영속성 컨텍스트를 종료해도 관리되던 엔티티는 준영속 상태가 된다.
    em.close();
    ```
    
    <img src="./img/entity_lifecycle.png" width="700px" height="400px" title="springboot03_JPA.png"/><br>
    

### 영속성 컨텍스트 특징

1. 1차 캐시
    
    영속성 컨텍스트는 내부에 1차 캐시를 가지고 있으며 @Id 어노테이션을 식별자로 한다.
    
    엔티티를 조회하면 1차 캐시에서 데이터를 조회하고 값이 있으면 반환한다. 값이 없으면 데이터베이스에서 조회하여 1차 캐시에 저장한 다음 반환한다. 이를 통해 캐시된 데이터를 조회할 때에는 데이터베이스를 거치지 않아도 되므로 매우 빠르게 데이터를 조회할 수 있다.
    
2. 영속 엔티티의 동일성 보장
    
    1차 캐시로 반복 가능한 읽기(Repeatable Read) 등급의 트랜잭션 격리 수준을 데이터베이스가 아닌 애플리케이션 차원에서 제공해 줄 수 있다.
    
    ```java
    Member a = em.find(Member.class, "member1");
    Member b = em.find(Member.class, "member1");
    
    System.out.println(a==b) // true
    ```
    
3. 트랜잭션을 지원하는 쓰기 지연
    
    엔티티 매니저는 em.persis()로 객체를 영속성 컨텍스트에 저장해도 트랜잭션을 commit()하기 전까지 DB에 바로 엔티티를 저장하지 않고, SQL 쿼리들을 모아놓았다가 flush될 때(영속성 컨텍스트의 변경 내용을 DB에 반영할 때) 모아둔 쿼리를 날린다.  
    
    ```java
    EntityManager em = emf.createEntityManager();
    EntityTransaction transaction = em.getTransaction();
    // 엔티티 매니저는 데이터 변경 시 트랜잭션을 시작해야 한다.
    transaction.begin(); // [트랜잭션] 시작
    
    em.persist(memberA);
    em.persist(memberB);
    // 여기까지 INSERT SQL을 데이터베이스에 보내지 않는다.
    
    // 커밋하는 순간 데이터베이스에 INSERT SQL을 보낸다.
    transaction.commit(); // [트랜잭션] 커밋
    ```
    
4. 변경 감지(Dirty Checking)
    
    flush 시점에 영속성 컨텍스트 내의 스냅샷과 엔티티를 비교하여 변경된 엔티티가 있으면 Update 쿼리를 자동으로 생성한다. (변경감지는 영속성 컨텍스트가 관리하는 영속 상태의 엔티티에만 적용된다.)
    
    ```java
    EntityManager em = emf.createEntityManager();
    EntityTransaction transaction = em.getTransaction();
    transaction.begin(); // [트랜잭션] 시작
    
    // 영속 엔티티 조회
    Member memberA = em.find(Member.class, "memberA");
    
    // 영속 엔티티 데이터 수정
    memberA.setUsername("hi");
    memberA.setAge(10);
    
    //em.update(member) 이런 코드가 있어야 하지 않을까?
    
    transaction.commit(); // [트랜잭션] 커밋
    ```
    
5. 지연 로딩(Lazy Loading)
    
    지연 로딩은 연관 관례 매핑되어 있는 엔티티를 조회 시 우선 프록시 객체를 반환하고, 실제로 필요할 때 쿼리를 날려 가져오는 기능이다.
    

### 플러시(Flush)

- 플러시는 영속성 컨텍스트의 변경 내용을 데이터베이스에 동기화한다.
- 플러시 흐름
    1. 변경 감지가 동작하여 스냅샷과 비교해서 수정된 엔티티를 찾는다.
    2. 수정된 엔티티에 대하여 수정 쿼리를 만들고 쓰기 지연 SQL 저장소에 등록한다.
    3. 쓰기 지연된 SQL 저장소의 쿼리(등록, 수정, 삭제 쿼리)를 데이터베이스에 전송한다.
- 플러시 호출 방법
    1. em.flush() 직접 호출 (거의 사용하지 않음)
    2. 트랜잭션 commit() 시 플러시 자동 호출
        
        트랜잭션을 commit()하기 전에 꼭 flush()를 호출하여 변경 내용을 데이터베이스에 반영해야 한다.
        
        JPA는 트랜잭션을 commit()할 때 flush()를 자동으로 호출한다.
        
    3. JPQL(Java Persistence Query Langauge) 쿼리 실행 시 플러시 자동 호출
        
        ```java
        em.persist(memberA);
        em.persist(memberB);
        em.persist(memberC);
        
        // 중간에 조회
        query = em.createQuery("select m from Member m", Member.class);
        List<Member> members = query.getResultList();
        ```
        

---

### 참조

<a href="https://www.inflearn.com/course/%EC%8A%A4%ED%94%84%EB%A7%81%EB%B6%80%ED%8A%B8-%EA%B0%9C%EB%85%90%EC%A0%95%EB%A6%AC">스프링부트 개념정리(이론)</a><br>
<a href="https://yozm.wishket.com/magazine/detail/2160/">스프링 데이터 JPA, 5분 만에 알아보기</a><br>
<a href="https://velog.io/@jinyeong-afk/기술-면접-영속성-컨텍스트란">[기술 면접] 영속성 컨텍스트란?</a><br>
<a href="https://code-lab1.tistory.com/290">[JPA] 영속성 컨텍스트란?</a><br>
<a href="https://code-lab1.tistory.com/52">[DB] 트랜잭션의 고립(격리) 수준(Isolation Level) | 고립 수준 예시</a><br>
<a href="https://ultrakain.gitbooks.io/jpa/content/chapter3/chapter3.4.html">3.4 영속성 컨텍스트의 특징</a><br>
<a href="https://ultrakain.gitbooks.io/jpa/content/chapter3/chapter3.5.html">3.5 플러시</a><br>