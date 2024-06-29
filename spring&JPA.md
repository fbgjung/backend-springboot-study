## Spring + JPA 추가 학습
1, 2, 3, 4, 5, 6, 7강
[(강의 링크)](https://www.inflearn.com/course/%EC%8A%A4%ED%94%84%EB%A7%81%EB%B6%80%ED%8A%B8-%EA%B0%9C%EB%85%90%EC%A0%95%EB%A6%AC/dashboard)


[JPA vs Spring data JPA](https://ssin-estella.tistory.com/82)
- spring data JPA에서 사용하는 repository interface는 JPA에서 사용하는 EntityMAnager을 내부적으로 사용한다.
- 사용자가 spring data JPA를 사용하는 경우 repositoty interface를 사용하며 추가적으로 EntityManager도 사용하여 복잡한 쿼리를 작성할 수 있다.


[JPA 상속](https://ssin-estella.tistory.com/83)
- JPA는 OOP의 상속 구조를 DB에서 실현해줄 수 있다.
- 단일 테이블 전략, 조인 테이블 전략, 구체 클래스 전략 3가지가 있다.
- 단일 테이블 전략 : 모든 상속관계에 있는 entity를 하나의 테이블(부모 클래스)에 저장한다.
- 조인 테이블 전략 : 공통적인 entity는 상위 테이블(부모 클래스), 이외에는 하위 테이블들(자식 클래스)에 저장후, PK를 통해 상위 테이블과 하위 테이블을 join한다.
- 구체 클래스 전략 : 조인 테이블 전략에서 상위 테이블의 entity를 각각의 하위 테이블에 모두 넣어준다. (조인X)

[Entity 생명주기](https://ssin-estella.tistory.com/84)
- 데이터를 영구적으로 보관할 수 있도록 EntityManager 내에 영속성 컨텍스트 범위를 만들어서 데이터를 관리한다.
- 영속성 컨텍스트와 관련된 entity 상태는 비영속상태, 영속상태, 준영속상태, 삭제상태 4가지가 있다.
![image](https://github.com/ssIIIn0-0/backend-springboot-study/assets/62862307/db5bcbf3-2961-469e-bb8a-df351921de46)
