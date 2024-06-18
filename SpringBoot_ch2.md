<1강 - JPA란?>
 
 [1] JPA는 Java Persistence API이다.
 
 [2] JPA는 ORM 기술이다.

 [3] JPA는 반복적인 CRUD 작업을 생략하게 해준다.

 [4] JPA는 영속성 컨텍스트를 가지고 있다.

 [5] JPA는 DB와 OOP의 불일치성을 해결하기 위한 방법론을 제공한다. (DB는 객체저장 불가능)

 [6] JPA는 OOP의 관점에서 모델링을 할 수 있게 해준다. (상속, 콤포지션, 연관관계)

 [7] 방언 처리가 용이하여 Migration하기 좋음. 유지보수에도 좋음.

 [8] JPA는 쉽지만 어렵다.

---------------------------------------------------------------------------

[1] JPA는 Java Persistence API이다.

 <h3>JPA</h3>
 * JPA : Java Persistence API
 => 자바에 있는 데이터를 영구히 저장할 수 있도록 환경을 제공하는 API
 => **자바를 통해 프로그래밍을 할 때, 데이터를 영구적으로 저장하기 위해서 필요한 인터페이스**

 * Persistence : 영속성. 데이터를 생성한 프로그램의 실행이 종료되더라도 사라지지 않는 데이터의 특성. 파일 시스템, 관계형 데이터베이스 혹은 객체 데이터베이스 등을 활용하여 구현함.

 * API : Application Programming Interface

 * Interface : 약속. (프로토콜과 다른 점 : 프로토콜은 동등한 관계, but 인터페이스는 상하관계. 다른 한쪽이 맞춰줘야 하는 관계임)

---------------------------------------------------------------------------

[2] JPA는 ORM 기술이다.

<h3>ORM</h3>
* ORM : Object Relational Mapping

**<개념>**
    객체와 관계형 데이터베이스의 데이터를 자동으로 매핑(연결)해주는 기법
    => 객체-관계 매핑


**<자바<-> DB 데이터 사용 시 문제점>**
    기본적 문제 : 데이터베이스가 갖는 데이터 타입(테이블) != 자바의 데이터 타입(클래스)
    그 외의 문제들은 아래와 같다

    1) 세분성 : 데이터베이스의 테이블 수 보다 클래스 객체 모델의 수가 더 많아서 생기는 불일치 문제
    2) 상속성 : 관계형 데이터베이스에 상속의 개념이 없어 생기는 불일치 문제
    3) 동일성 : 동일을 판단함에 있어서 데이터베이스와 자바의 고려 기준이 달라서 생기는 불일치 문제
        (db:기본키 비교 / java:주소값 비교 or 내용 비교)
    4) 연관성 : 데이터베이스에서는 외래키를 통해 양방향 참조 정의 가능, 객체지향에서는 단방향 참조만 가능해서 생기는 불일치 문제
    5) 탐색 : 데이터를 탐색하는 방법이 달라서 생기는 불일치 문제
        (db:join을 통해 여러 데이터집합을 불러와서 탐색 / java:하나의 객체에서 출발하여 그래프 형태로 탐색)

    ex) 문자열 : DB(varchar) != Java(String)


**<해결방법>**
    자바에 있는 데이터들을 데이터베이스 세상의 데이터로 모델링해 주는 것
    이때, JPA의 인터페이스를 통해 객체 간의 관계를 바탕으로 SQL을 자동으로 생성하여 불일치를 해결함.


[3] JPA는 반복적인 CRUD 작업을 생략하게 해준다.

**<반복적인 상황>**
    자바는 DB로부터 데이터를 얻기 위해 1)세션을 오픈한 후, 2)쿼리를 전송하여 3)Connection이 되었다면 4)데이터를 전송받을 수 있는 단순/반복적인 로직을 갖고 있다. 


**<해결방법>**
    JPA의 함수를 사용하여 SQL문 작성 없이 반복적인 작업을 생략할 수 있으며, 객체를 통해 간접적으로 데이터베이스를 조작할 수 있게 됨.

    ex) MySQL과 자바에서의 상황 비교
    - MySQL : SELECT * FROM Member;
    - JAVA : member.findall(); (member는 Member 테이블과 매핑된 객체)


**ORM의 장점**
    1) 객체지향적인 코드로 인해 더 직관적이고 로직에 집중할 수 있도록 도와줌
    2) 재사용 및 유지보수의 편리성이 증가함
    3) DBMS에 대한 종속성이 줄어듦


**ORM의 단점**
    1) ORM으로만 완벽하게 서비스를 구현하기는 어려움
    2) 프로시저가 많은 시스템에선 ORM의 객체 지향적인 장점을 활용하기 어려움


**ORM 프레임워크**
    1) JPA/Hibernate : JPA는 자바의 ORM 기술 표준으로 인터페이스의 모음임. 이러한 JPA 표준 명세를 구현한 구현체가 Hibernate임
    2) Sequelize : Sequelize는 Postgres, MySQL, MariaDB, SQLite 등을 지원하는 Promise에 기반한 비동기로 동작하는 node.js ORM임
    3) Django ORM : python 기반 프레임워크인 Django에서 자체적으로 지원하는 ORM임



<JPA에서 객체와 데이터베이스 테이블을 매핑하는 기본적인 방법 예시>

(1) 엔티티 클래스 정의
<p align="center">
  <img src="https://github.com/fbgjung/backend-springboot-study/assets/131326799/d8ab893b-df0a-4025-b1ba-45a7063fe57e">
</p>

(1-1) 엔티티 클래스의 어노테이션 설명
<p align="center">
  <img src="https://github.com/fbgjung/backend-springboot-study/assets/131326799/f0185c90-9c3c-485f-83fa-0837123f4567">
</p>

(2) 리포지토리 인터페이스 및 스프링 부트 애플리케이션 설정
<p align="center">
  <img src="https://github.com/fbgjung/backend-springboot-study/assets/131326799/a5dc0c03-3d6c-47ff-8e19-e55bab69a935">
</p>

(3) 생성
<p align="center">
  <img src="https://github.com/fbgjung/backend-springboot-study/assets/131326799/b9f4eeb0-7ec7-4fd9-96bf-61a2973fb369">
</p>

(4) 조회
<p align="center">
  <img src="https://github.com/fbgjung/backend-springboot-study/assets/131326799/23b2015f-0e77-4e67-ac45-e53aeace7c48">
</p>


(5) 삭제
<p align="center">
  <img src="https://github.com/fbgjung/backend-springboot-study/assets/131326799/73e85c18-2b56-4dd2-9307-0aa431bc1942">
</p>


(6) 수정
<p align="center">
  <img src="https://github.com/fbgjung/backend-springboot-study/assets/131326799/11ae082c-41f7-4fae-ab62-a45c67a95920">
</p>

---------------------------------------------------------------------------

[4] JPA는 영속성 컨텍스트를 가지고 있다.

---------------------------------------------------------------------------

[5] JPA는 DB와 OOP의 불일치성을 해결하기 위한 방법론을 제공한다. (DB는 객체저장 불가능)

---------------------------------------------------------------------------

[6] JPA는 OOP의 관점에서 모델링을 할 수 있게 해준다. (상속, 콤포지션, 연관관계)

---------------------------------------------------------------------------

[7] 방언 처리가 용이하여 Migration하기 좋음. 유지보수에도 좋음.

---------------------------------------------------------------------------

[8] JPA는 쉽지만 어렵다.