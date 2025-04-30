# 🛵 DeliSpring
Spring Boot 기반의 배달 서비스 백엔드 프로젝트입니다.

----

## 프로젝트 개요
- 기간 : 2025.04.22 ~ 2025.04.29
- 인원 : 1인 개발 (개인 프로젝트)
- 목표
  - JPA를 이용한 DB 연동과 CRUD 구현, AOP 기반 권한 검증 기능을 적용하였습니다.
  - JWT를 이용해 로그인, 인증, 인가 기능을 구현하고, Access/Refresh Token 관리를 설계하였습니다.
  - JUnit과 Mockito로 예외 상황을 포함한 테스트 코드를 작성하여 서비스의 안정성과 신뢰성을 높였습니다.


----

## 🛠 기술 스택
- Backend : Java, Spring Boot 3, Spring Security, JWT
- DB : MySQL
- ORM : JPA, Hibernate
- AOP : Spring AOP
- TEST : JUnit5, Mockito, SpringBootTest
- API DEVELOPMENT TOOL : Postman


---

## 🎨 와이어 프레임
![image](https://github.com/user-attachments/assets/c2d33bb2-a6f0-45a0-af49-ccc85aa49627)
![image](https://github.com/user-attachments/assets/67b90e73-6274-47df-a078-10a355cdf9d2)
![image](https://github.com/user-attachments/assets/d5a1f25a-cdd3-4e32-a69f-2a2ff1c38e45)

---

## 🧩 ERD
![ERD](https://github.com/user-attachments/assets/62b0166e-09be-4a1e-90bc-3a2368bb88f2)

---

## 🌐 API 명세서
 📮 [POSTMAN API 명세서 바로가기](https://documenter.getpostman.com/view/43269199/2sB2j3BXX3)

---

## 💡 주요 서비스 소개
#### 1. 회원가입 / 로그인 / 탈퇴 / 정보 수정
- 이메일 기반 회원가입 및 JWT 로그인 구현
- 비밀번호는 Bcrypt로 암호화하여 보안 강화
- 사용자 권한을 일반 사용자(USER)와 사장님(OWNER)으로 분리하여 서비스 제공
- 탈퇴 시 비밀번호 확인 후, Soft Delete 방식으로 처리

#### 2. 가게 관리 (사장님만 접근 가능)
- 사장님은 가게를 최대 3개까지 등록 가능
- 가게는 오픈/마감 시간, 최소 주문 금액 등의 정보를 포함
- 폐업 시 상태만 변경되며, 이후 다시 가게 등록 가능

#### 3. 메뉴 관리
- 메뉴 생성, 수정, 삭제는 사장님만 가능
- 메뉴 삭제는 실제 데이터를 삭제하는 것이 아니라, Soft Delete 방식으로 처리
- 메뉴는 가게 단건 조회 시에만 함께 조회됨

#### 4. 주문
- 고객은 메뉴를 선택하여 주문당 메뉴 하나씩만 주문 가능
- 주문 시 최소 주문 금액 및 영업 시간 체크
- 사장님은 주문 상태를 순차적으로 변경 가능 (`REQUESTED` → `ACCEPTED` → `COOKING` → `DELIVERING` → `COMPLETED`)
- 주문 상태 변경 시 OrderLog 엔티티에 자동으로 로그 기록 (AOP 적용)

#### 5. 리뷰
- 고객은 배달 완료된 주문에 대해서만 리뷰 작성 가능
- 리뷰는 별점(1~5점) 및 코멘트로 구성
- 리뷰는 가게 기준으로 최신순 정렬 및 별점 필터링 조회 가능
- 리뷰 작성시 별점 1~5점 가능하지만, 조회는 3~5점만 필터링 가능

---

## 💫 주요 개발 포인트

#### 1. 인증 및 권한 관리 (JWT)
- Spring Security와 JWT 기반으로 회원 인증 및 인가(Authorization)를 구현했습니다.
- 모든 API 요청은 `JwtAuthenticationFilter`를 통해 인증 과정을 처리합니다.
- `UserDetails`를 커스터마이징하여 인증된 사용자 정보에서 `userId`를 직접 꺼낼 수 있도록 개선했으며,  
  이로 인해 서비스 계층에서 매번 Repository를 조회할 필요 없이 효율적으로 사용자 정보를 활용할 수 있습니다.
- AccessToken/RefreshToken 발급 및 재발급 기능을 통해 토큰 기반 세션 관리를 설계했습니다.

#### 2. 공통 로직 분리 (AOP)
- 반복되는 권한 체크, 주문 상태 변경 기록의 로직을 `@Aspect` 기반 AOP로 분리했습니다.
- 사용자 권한(USER, OWNER)에 따라 API 접근을 분기 처리하여 보안을 강화했습니다.

#### 3. 주문 이력 관리
- 주문 생성 및 상태 변경 시마다 `OrderLog` 엔티티를 통해 변경 이력을 저장하였습니다.
- 추후에 주문의 흐름만 히스토리 기반으로 추적할 수 있어서 관리에 용이합니다.

#### 4. 예외 처리의 일관성
- 인증 실패, 권한 오류, 데이터 유효성 실패 등 다양한 예외 상황을 일관된 포맷으로 처리하였습니다.

#### 5. 테스트 코드 작성 (TDD 지향)
- `JUnit5`와 `Mockito`를 활용하여 단위 테스트를 작성했습니다.

---

## 🚀 트러블슈팅
🧾 [블로그 포스팅 확인하기](https://codinghanni.tistory.com/69)

## 🔖 회고
이번 프로젝트에서는 혼자서 JWT를 적용하고 AOP 기반 권한 검증을 구현하는 등, 전반적인 백엔드 구조를 익히는 데 중점을 두었습니다. 그리고 테스트 코드를 사용하는 것에 초점을 두고 진행하려고 노력했습니다. 혼자 진행한만큼 전체 흐름을 직접 설계하고 점검해볼 수 있어서 Spring Boot의 동작구조를 익히는데에 많은 도움이 되었습니다. 다만 개인으로 진행하다보니 모든 코드에 대하여 테스트코드를 작성하지 못한 점은 아쉬웠습니다. 또한 구현 당시에는 문제 없어 보였던 로직들도 Postman으로 테스트하면서 여러 에러를 마주하였습니다. 눈으로 로직을 보기에는 문제가 없어보여도 실제로 서버를 다양한 상황에서 동작시켜보면 예상치 못한 에러를 발견할 수 있다는 점을 체감할 수 있었습니다.

