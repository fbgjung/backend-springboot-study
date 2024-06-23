## Springboot 개념정리
4, 5, 6강 
[(강의 링크)](https://www.inflearn.com/course/%EC%8A%A4%ED%94%84%EB%A7%81%EB%B6%80%ED%8A%B8-%EA%B0%9C%EB%85%90%EC%A0%95%EB%A6%AC/dashboard)    

### JAP란?  
<br>

#### 1. JPA (Java Persistence API)
   - `Java` : 자바에 있는 데이터 `Persistence` : 데이터를 영구적으로 저장(영속성) `API` (Application Programming Interface) : 프로그램을 만들기 위한 인터페이스  
   RAM(휘발성) → 하드디스크 DBMS (비휘발성)에 기록          
   - 왜 사용?   
     1. SQL 문법을 JPA가 대신 처리, 객체 생성(`생산성`)
     2. SQL에서 CRUD하면 관련 코드를 모두 변경해야하는 번거로움X (`유지보수`)
     3. 1,2번을 정리하면 DB에서 독립적인 개발을 할 수 있을 것으로 보임, 객체 중심적인 개발(`ORM`)  
     ![image](https://github.com/ssIIIn0-0/backend-springboot-study/assets/62862307/982a142d-4fb4-4274-a609-c4631b3307da)

```
+ 추가
프로토콜 vs 인터페이스
둘 다 어떤 프로그램을 사용하기 위한 약속 BUT 인터페이스는 상하관계가 존재, 프로토콜은 동등한 존재
```
<br>

#### 2. ORM (Object Relational Mapping)
   기존이라면, 데이터 테이블 먼저 만들고 필드를 생성
   - 자바 → 테이블 : Input, DML(Delete, Update, Insert) / 자바 ← 테이블 : Output, Select
   - 필드 생성 = DB세상에 있는 데이터를 자바세상에 모델링
        
   BUT ORM 기법을 이용하면 필드를 먼저 만들고 데이터 테이블을 만들 수 있다
   - ORM : 자바의 객체 - ORM - DB 이런 느낌
   - 자바만 사용해서 데이터베이스에 접근, 데이터 사용
      - SQL 변경등으로 추가작업X
      - 쿼리 부담감 ⇂
<br>

```
+ 추가
`entity manager` vs `repository 인터페이스`
java 프로그램과 DB사이에서 데이터의 상호작용을 담당, 메소드 library 느낌   
BUT
1. entity manager : JPA
   - ORM을 위한 API 제공, JPA만 단독으로 사용할 경우, JPA구현체(Hibernate등)을 사용하여 DB와 상호작용.
   - 이 과정에서 entity manager을 통해 DB 연산(CRUD)을 진행한다.
2. repository 인터페이스 : spring data JPA
   - JPA + spring framework의 추가기능
   - spring data JPA에서의 구현체는 springboot로 구현

<br>
```
// entity manager 단독코드
```j
import jakarta.persistence.*;

public class JpaExample {
    private static EntityManagerFactory entityManagerFactory;

    public static void main(String[] args) {
        entityManagerFactory = Persistence.createEntityManagerFactory("example-unit");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        entityManager.getTransaction().begin();

        User user = new User();
        user.setName("John Doe");
        entityManager.persist(user);

        entityManager.getTransaction().commit();
        entityManager.close();
        entityManagerFactory.close();
    }
}

@Entity
@Table(name = "users")
class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", nullable = false, length = 50)
    private String name;

    // Getters and Setters
}


```
// repository 인터페이스
```j
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jakarta.persistence.*;
import java.util.List;

@SpringBootApplication
public class SpringDataJpaExample {
    public static void main(String[] args) {
        SpringApplication.run(SpringDataJpaExample.class, args);
    }
}

@Entity
@Table(name = "users")
class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", nullable = false, length = 50)
    private String name;

    // Getters and Setters
}

@Repository
interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByName(String name);
}

```
<br>

#### 3. 반복적인 CRUD 생략
   - Select → Delete → Update → Insert... 자주, 반복적인 작업이다.
   - JPA의 함수를 통해 자바 ↔ DB 사이의 쿼리 전송, data전송등 반복적인 행위를 하지 않아도 된다

User 엔티티 클래스
```j
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
```
JPA 리포지토리 서비스 클래스
```j
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
```
서비스 클래스에서 필요한 메소드를 구현하는 컨트롤러 클래스
```j
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.saveUser(user);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }
```

<br>

#### 4. 영속성 컨텍스트 
   - 영속성 : 데이터를 영구적으로 저장, 자바에서는 file이 아니라 DB(mySQL)에 저장
   - 컨텍스트(context) : 어떤 대상에 대한 모든 정보를 가지고 있는 것
   ##### 엔티티의 생명주기를 관리하므로서 데이터를 APP 내에서 오래 지속되도록 보관
     
   - 자바 -> 영속성 컨텍스트(영구적으로 저장된 데이터에 대한 모든 것을 알고 있는 것) -> DB
      - 이 과정에서 영속성컨텍스트는 크게 4가지 상태로 데이터를 관리한다.
      1. 비영속 (new)   :   영속성 컨텍스트와 관계없는 상태 
      2. 영속 (managed)   :   영속성 컨텍스트가 관리중인 상태, DB와 동기화 완료 + 해당 데이터를 엔티티메니저가 관리X
      3. 준영속 (detached)   :   영속성 컨텍스트에 저장되었다가 분리된 상태, DB에 기존 데이터 존재 + 해당 데이터를 엔티티 매니저가 관리X (최신 데이터가 DB에 없을 수 있음)
      4. 삭제 (remove)   :   영속성컨텍스트와 DB에서 삭제된 상태
   ![image](https://github.com/ssIIIn0-0/backend-springboot-study/assets/62862307/f5c63555-df69-4c29-b48f-7b70e445296a)

   ![image](https://github.com/ssIIIn0-0/backend-springboot-study/assets/62862307/5a1249b3-81d1-4359-8995-517553e5a617)

```j
import jakarta.persistence.*;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    
    // Getters and Setters
}

public class JpaExample {
    public static void main(String[] args) {
        // 엔티티 매니저 팩토리 생성 (엔티티 매니저 인스턴스 생성)
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("example-unit");
        
        // 엔티티 매니저 생성
        EntityManager em = emf.createEntityManager();
        
        // 트랜잭션 시작
        em.getTransaction().begin();
        
        // 엔티티 생성 및 영속 상태로 전환
        User user = new User();
        user.setName("씬");
        em.persist(user); // 영속성 컨텍스트에 엔티티 추가
        
        // 트랜잭션 커밋 (영속성 컨텍스트의 변경 사항이 데이터베이스에 반영됨)
        em.getTransaction().commit();
        
        // 엔티티 분리 (준영속 상태로 전환)
        em.detach(user);
        
        // 엔티티 변경 (이 시점에서는 영속성 컨텍스트가 변경 사항을 추적하지 않음)
        user.setName("씬2");
        
        // 엔티티 병합 (다시 영속 상태로 전환)
        em.getTransaction().begin();
        em.merge(user);
        em.getTransaction().commit();
        
        // 엔티티 매니저 및 팩토리 종료
        em.close();
        emf.close();
    }
}
```
<br>

#### 5. DB와 OOP의 불일치성을 해결하기 위한 방법론 제공 (DB는 객체저장 불가능) : ORM 실행
   - DB와 java프로그램이 있음. 각각은 데이터를 저장할 수 있는데, 문제는 서로 저장하는 형식이 다름
   - DB는 key에 속성을 부여해서(FK, PK등) 한 테이블 내의 attribute가 다른 테이블과 무슨 관계인지 유추할 수 있음. 하지만, attribute에 다른 테이블을 넣지는 못함 (DB는 객체 저장 불가능)
   - java는 class의 필드로 데이터를 저장할 수 있음, 필드의 값 자체에 다른 클래스를 넣을 수 있기 때문에, 다른 클래스에 있는 필드값을 가져와서 무슨 관계인지 파악하는게 자연스러움.
   - 즉, DB와 java의 데이터 사이에 불일치성이 발생하고 이를 해결해주는게 ORM.
   
![image](https://github.com/ssIIIn0-0/backend-springboot-study/assets/62862307/6ab6f1cb-aa72-49a3-a65e-8225e571323d)

![image](https://github.com/ssIIIn0-0/backend-springboot-study/assets/62862307/dfb3bbc7-ca3c-4732-b3df-1cf4380f743b)























