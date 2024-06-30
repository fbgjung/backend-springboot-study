# 4,5,6,7강 - JPA

[4] JPA란 무엇인가요? 

[5] ORM이란 무엇인가요?

[6] 영속성 컨텍스트란 무엇인가요?

[7] OOP 관점에서 모델링이란 무엇일까요?

---

# 4강 - JPA란 무엇인가요?

> [ JPA는 Java Persistence API 이다. ]
> 

---

## ✅ 4-1 : JPA

- Java Persistence API : 자바 영속성 어플리케이션 프로그래밍 인터페이스
- DBMS로 관리하고, 비휘발성 영구적으로 저장 될 수 있게 하는 API

- API? Application Programming Interface
    - 인터페이스? 상하관계가 존재하는 규칙, 조건 ( 상의하지 않고 명령 )
    - 프로토콜? 동등한 관계인 규칙, 조건 ( 상의해서 조정 가능 )
    
- 즉, 자바 프로그램을 할 때 → 영구적으로 데이터를 저장하기 위해 필요한 **인터페이스**

## ✚ 4-2 : JPA와 Spring Data JPA의 차이점?

|  | JPA | Spring Data JPA |
| --- | --- | --- |
| 개념 | 객체-관계 매핑(ORM)을 위한 표준 명세인 인터페이스

= 자바 애플리케이션에서 관계형 DB를 사용하는 방식을 정의한 인터페이스+어노테이션의 표준 집합 | JPA 기반 애플리케이션 개발을 더 간편하게 만드는 라이브러리/프레임워크

= Spring에서 제공하는 모듈 중 하나로, 
JPA 위에 추가적인 기능을 제공해 개발을 더 간편하게 만드는 라이브러리/프레임워크 |
|  | 어떻게 사용하는지를 정의하는 방법. 
구현은 없다. | Repository = Spring Data JPA의 핵심 인터페이스
EntityManger를 다룰때 사용 |
|  | 다양한 프레임 워크 (Hibernate, EclipseLink, OpenJPA) 에서 구현할 수 있는 공통 API 제공 | JPA 리포지토리(CRUD 연산, 페이징, 정렬과 같은 )를 구현하는 데 필요한 반복적인코드를 줄이는 인터페이스와 클래스를 제공함 |
| 즉, | JAVA에서 객체-관계 매핑을 위한 표준 명세이며 > 인터페이스 < | JPA기반 애플리케이션 개발을 “쉽게” 만들기 위해 JPA 위에 추가적인 기능과 추상화를 제공하는 > 라이브러리/ 프레임워크 < |

### Hibernate와 JPA의 관계는?

  **: Hibernate = JPA의 구현체 (프레임워크)**

- JPA = 자바의 interface 
Hibernate = 해당 interface를 구현한 class 같은 관계
- JPA의 모든 기능을 지원한다.
- 객체와 관계형DB 간 매핑을 자동적으로 처리 → 매 번 SQL 쿼리 작업 필요X
- JPA를 구현할 때 꼭 Hibernate ‘만’ 사용해야한다? ❌❌

### 만일 Spring data JPA를 사용하지 않고 JPA만 사용한다면?

- JPA만 사용할 때
    - JPA를 사용할 때는 EntityManager를 사용해 → 데이터베이스 기능을 사용
- Spring Data JPA를 사용할 때
    - EntityManager 대신 → Repository 인터페이스를 사용
    - + Repository 안에 EntityManager 선언이 되어 작동한다
    - + 개발자가 더해서 직접 구현을 EntityManager를 따로 사용할 때도 있다.
    - CRUD 작업처럼 일반적인 DB작업 작성하지 않아도 되는 코드를 제공함

### Spring Data JPA example

```java
@Repository
public interface UserRepository extends JpaRepository<User, Long> {}

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

		// 1.  모든 엔티티 조회 .findAll()
    public List<User> findAll() {
        return userRepository.findAll();
    }
    
    // 2. 특정 id를 가진 엔티티 조회 .findById(Long id)
     public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

		// 3. 특정 이름으로 엔티티 조회 .findByName(String name)
		public List<User> findByName(String name) {
        return userRepository.findByName(name);
    }
    
    // 4. 엔티티 저장 .save()
    public User save(User user) {
        return userRepository.save(user);
    }
   
    // 5. 엔티티 업데이트 update()
    public User update(User user) {
        return userRepository.save(user);
    }
    
    // 6. 엔티티 삭제 .delete()
     public void delete(User user) {
        userRepository.delete(user);
    }
    
    // 7. 페이지네이션 기능을 이용해 엔티티 조회
    public Page<User> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }
    
    // 8. 정렬 기능을 이용하여 엔티티를 조회하는 방법
    public List<User> findAll(Sort sort) {
        return userRepository.findAll(sort);
    }
  }
  } 
    
    
    // 9.  Native Queries 네이티브 쿼리
	  // 10. Named Queries 네임드 쿼리
	  // 11. Auditing 감사 
	  // (엔티티를 생성하거나 업데이트할 때 자동으로 생성일과 수정일을 감사하는 방법 보여줌)
```

## ✚ 4-3 : EntityManager

### `EntityManager:`

- JPA에서 Entity를 관리하고 데이터베이스와 상호작용하는 핵심 인터페이스
- Entity의 생성, 조회, 수정, 삭제와 같은 작업을 수행

### 주요기능

- **persist**: 새로운 엔터티를 데이터베이스에 저장
- **find**: 엔터티를 데이터베이스에서 조회
- **merge**: 준영속 상태의 엔터티를 다시 영속 상태로 변경
- **remove**: 엔터티를 데이터베이스에서 삭제
- **createQuery**: JPQL 쿼리를 생성하고 실행

```java
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

public class MyService {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void saveEntity(MyEntity entity) {
        entityManager.persist(entity);
    }

    public MyEntity findEntity(Long id) {
        return entityManager.find(MyEntity.class, id);
    }

    @Transactional
    public void updateEntity(MyEntity entity) {
        entityManager.merge(entity);
    }

    @Transactional
    public void deleteEntity(Long id) {
        MyEntity entity = entityManager.find(MyEntity.class, id);
        if (entity != null) {
            entityManager.remove(entity);
        }
    }
}

```

---

# 5강 - ORM이란 무엇인가요?

> [ JPA는 ORM 기술이다. ]
> 

> [ JPA는 반복적인 CRUD 작업을 생략하게 해준다. ]
> 

---

## ✅ 5-1 : ORM 기술

- Object Relational Mapping: 객체-
- 관계 매핑
- 오브젝트를 데이터베이스에 연결하는 방법론
- 모델링 : 추상적 개념을 현실적으로 뽑아낸다.

- JAVA → (DML) input → DB  || JAVA ← (select) output ← DB
- 자바와 DB의 data Type이 달라 모델링 해줘야 함
- TRM (table relational Mapping) : DB type을 사용해 Java object 생성

- ORM : Java object를 미리 만들고 JPA를 지키면 자동으로 DB type으로 만들어 낼 수 있음 
→ 5-2:일련의 과정을 함수로 제공해 반복적인 CRUD 작업을 생략 가능

- ORM을 구현하는 대표적인 프레임워크 : Hibernate

## ✅ 5-2 :  반복적인 CRUD 작업을 생략

1. JAVA에서 DB에 요청을 하게 되면 DB는 신원 확인 후 세션을 오픈함
2. 세션에 연결된 자바는 (두번 째) 요청시에 쿼리를 전송할 수 있음
3. DB는 해당 쿼리를 통해 data를 만들며,  자바에 응답하게 되면 
4. 자바는 DB type과 다르므로 자바Object로 변환해야 함 (json같은..)

- 해당 과정은 단순 반복로직 → JPA를 사용해 해결!
- 일련의 과정 (CRUD)을  함수로 제공
- Create(insert) Read(select) Update Delete

---

# 6강 - 영속성 컨텍스트란 무엇인가요?

> [ JPA는 영속성 컨텍스트를 가지고 있다. ]
> 

> [ JPA는 DB와 OOP의 불일치성을 해결하기 위한 방법론을 제공한다. (DB는 객체저장 불가능) ]
> 

---

## ✅ 6-1 : 영속성 컨텍스트

- 영속성(Persistence) : 데이터를 영구적으로 저장
- 컨텍스트(Context) : 대상에 대한 모든 정보를 가지고 있음
- 컨텍스트를 넘겨준다

- ex ) 동물 데이터를 DB에 넘겨주고 싶다. 다이렉트로 넘기는 대신 영속성 컨텍스트를 거치는데
- INPUT
1. 자바는 영속성 컨텍스트에 모든 정보를 넘긴다.
2. 영속성 컨텍스트는 해당 정보를 DB에 넘긴다.
3. DB에 동물 데이터가 저장되고  영속성 컨텍스트와 동기화가 된다. 
(영속성컨텍스트에서 동물데이터 삭제 시 DB에서도 삭제됨)
- OUTPUT
1. 자바에서 DB에 있는 과일 데이터를 select 하고 싶다.
2. DB는 영속성 컨텍스트에 데이터를 주고 영속성 컨텍스트에는 DB type이 아닌 Java Object로 저장된다.
3. 영속성 컨텍스트가 JAVA에게 과일 데이터를 넘겨준다
4. JAVA에서 과일 데이터를 수정하게 되면 영속성 컨텍스트도 자동적으로 변경된다 
5. 변경된 데이터는 DB에 update 문이 자동으로 호출 된다.

- 자바가 데이터베이스에 해야하는 것들은 영속성 컨텍스트를 거치게 되고 자동으로 처리하게 된다.
- flush() : 트랜잭션 COMMIT, flush()를 직접 호출할 때, JPQL 쿼리(?)를 사용할 때
    
    →
    

## ✚ 6-1-1 : 준영속상태

### 준영속 상태

- 엔터티가 더 이상 영속성 컨텍스트에 의해 관리되지 않는 상태
- 준영속 상태의 Entity는 DB와의 동기화가 이루어지지 않음
- 식별자 Entity Manager 를 추가하면 준영속상태가 된다.
- 트랜잭션이 COMMIT된 상태, DB에 데이터가 저장되기 전 = flush() 호출 전 상태

### 준영속 상태로의 전환

- **detach**: 특정 엔터티를 준영속 상태로 전환
- **clear**: 영속성 컨텍스트의 모든 엔터티 → 준영속 상태로 전환
- **close**: 영속성 컨텍스트를 종료하여 모든 엔터티를 준영속 상태로 전환
    
    ```java
    import javax.persistence.EntityManager;
    import javax.persistence.PersistenceContext;
    import javax.transaction.Transactional;
    
    public class MyService {
    
        @PersistenceContext
        private EntityManager entityManager;
    
        public void detachEntity(MyEntity entity) {
            entityManager.detach(entity);
        }
    
        public void clearContext() {
            entityManager.clear();
        }
    
        public void closeContext() {
            entityManager.close();
        }
    }
    
    ```
    

### 준영속 상태 → 영속 상태로 전환

- 준영속 상태의 엔터티 → 다시 영속 상태로 전환하려면 `merge` 메서드를 사용

```java
@Transactional
public void reattachEntity(MyEntity entity) {
    entityManager.merge(entity);
}
```

## ✅ 6-2 : DB와 OOP의 불일치성 해결 방법론 제시

- DB에서 foreign key(int)를 사용했지만 자바에서는 다른 테이블을 하나의 object로 불러올 수 있게 됨
- 하지만, DB와의 OOP(객체지향프로그래밍) 모순 (불일치성)이 일어나게 되는데 이를 **ORM**이 해결함

- ex) DB 테이블1, 테이블 2    foreignKey

| id | name | year |
| --- | --- | --- |
| 1 | 롯데 | 2005 |
| 2 | 한화 | 1999 |

| id | name | teamId |
| --- | --- | --- |
| 1 | 홍길동 | 1 |
| 2 | 영숙 | 2 |
- JAVA Class

```java
Class Team {
		int id;
		String name;
		int year;
}
```

```java
Class Player {
		int id;
		String name;
		Team team;
}
```

---

# 7강 - OOP 관점에서 모델링이란 무엇일까요?

> [ JPA는 OOP의 관점에서 모델링을 할 수 있게 해준다. (상속, 컴포지션, 연관관계) ]
> 

> [ 방언 처리가 용이하여 Migration, 유지보수에도 좋음. ]
> 

> [ JPA는 쉽지만 어렵다. ]
> 

---

## ✅ 7-1 : OOP의 관점에서 모델링 가능

- JPA는 객체지향적으로 설계한 내용 →  DB에 그대로 쉽게 반영 가능

- 상속 : JPA에서 상속 구조를 DB에 매핑 할 수 있음
- 콤포지션(결합) : 객체 간의 포함 관계 설정할 수 있음
- 연관관계 : 객체 간의 관계 정의 by JPA 어노테이션  (후에 코드로 설명)

- EntityDate → Car 상속
- Car에서 Engine 클래스를 나타내고 싶으면 컴포지션(결합) 이용

```java
Class Car extends EntityDate{
		int id;
		String name;
		String color;
		Engine engine;
}

Class Engine{
		int id;
		int power;
		~~TimeStamp createDate;
		TimeStamp updateDate;~~
}

Class EntityDate {
		TimeStamp createDate;
		TimeStamp updateDate;
}
```

### 데이터베이스 자동 생성 →

### Car 테이블

| id | name | color | engine_id | create_date | update_date |
| --- | --- | --- | --- | --- | --- |

### Engine 테이블

| id | power |
| --- | --- |

## ✚ 7-1-1 : JPA 상속

### JPA에서 상속이란?

- JPA는 Entity 간의 상속 구조를 데이터베이스 테이블에 매핑할 수 있는 기능을 제공

### 상속 전략

- **단일 테이블 전략 (Single Table)**: 모든 엔터티를 하나의 테이블에 매핑
- **조인 전략 (Joined)**: 상위 클래스와 하위 클래스마다 테이블을 생성하고 조인하여 조회
- **테이블 퍼 클래스 전략 (Table per Class)**: 각 클래스마다 독립적인 테이블 생성

## ✅ 7-2 : 방언 처리가 용이

- JPA는 다양한 DB 방언 (dialect) 지원  → 유지보수에 좋음
- JPA에 추상화 객체가 붙어 있어, 코드 변동 없이 그대로 사용 가능  → Migration하기 좋음

- 스프링 — JPA — DB 연결시 ⇒ JPA 설정만 변경하면 됨
    
                   JPA  → 추상화 객체 (DBMS: mySQL, Oracle 등..)
    

```java
// application.properties 예시
spring.jpa.database-platform=org.hibernate.dialect.MySQL5Dialect
// Oracle로 변경 시
spring.jpa.database-platform=org.hibernate.dialect.Oracle12cDialect
```

## ✅ 7-3 : 쉽지만 어렵다

- JPA는 객체 지향적인 설계와 DB 매핑을 쉽게 해주지만 깊이있는 학습을 요구함

---

- +++
    
    JPA vs Spring data JPA
    
    [https://lealea.tistory.com/238](https://lealea.tistory.com/238)
    
    [https://suhwan.dev/2019/02/24/jpa-vs-hibernate-vs-spring-data-jpa/](https://suhwan.dev/2019/02/24/jpa-vs-hibernate-vs-spring-data-jpa/)
