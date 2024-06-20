## Springboot 개념정리
4, 5강 
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

#### 2. 반복적인 CRUD 생략
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
