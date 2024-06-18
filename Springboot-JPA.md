## Springboot 개념정리
4강   
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

