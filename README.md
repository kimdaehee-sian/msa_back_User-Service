# User Service

Guidely MSA의 첫 번째 서비스로, 사용자 프로필 관리 기능을 제공합니다.

## 🚀 기능

- 사용자 생성 (`POST /users`)
- 사용자 조회 (`GET /users/{id}`)
- 사용자 정보 수정 (`PATCH /users/{id}`)

## 🛠 기술 스택

- **Spring Boot**: 3.5.5
- **Java**: 17
- **Gradle**: Groovy
- **JPA/Hibernate**: 데이터베이스 연동
- **H2 Database**: 개발/테스트용 인메모리 DB
- **PostgreSQL**: 운영 환경용 DB
- **Lombok**: 코드 간결화
- **Spring Boot Actuator**: 모니터링

## 📁 프로젝트 구조

```
src/main/java/com/guidely/userservice/
├── UserServiceApplication.java    # 메인 애플리케이션 클래스
├── config/
│   └── JpaConfig.java            # JPA 설정
├── controller/
│   └── UserController.java       # REST API 컨트롤러
├── service/
│   └── UserService.java          # 비즈니스 로직
├── repository/
│   └── UserRepository.java       # 데이터 접근 계층
├── entity/
│   └── User.java                 # 사용자 엔티티
├── dto/
│   ├── UserCreateRequest.java    # 사용자 생성 요청 DTO
│   ├── UserUpdateRequest.java    # 사용자 수정 요청 DTO
│   └── UserResponse.java         # 사용자 응답 DTO
└── exception/
    ├── UserNotFoundException.java # 사용자 없음 예외
    ├── GlobalExceptionHandler.java # 전역 예외 처리
    └── ErrorResponse.java        # 에러 응답 DTO
```

## 🏃‍♂️ 실행 방법

### 1. 프로젝트 빌드
```bash
./gradlew build
```

### 2. 애플리케이션 실행
```bash
./gradlew bootRun
```

### 3. 개발 서버 접속
- 애플리케이션: http://localhost:8081
- H2 콘솔: http://localhost:8081/h2-console
- Actuator: http://localhost:8081/actuator

## 📋 API 명세

### 사용자 생성
```http
POST /users
Content-Type: application/json

{
  "nickname": "사용자닉네임",
  "language": "ko"
}
```

### 사용자 조회
```http
GET /users/{id}
```

### 사용자 정보 수정
```http
PATCH /users/{id}
Content-Type: application/json

{
  "nickname": "새닉네임",
  "language": "en"
}
```

## 🗄 데이터베이스

### 개발 환경
- H2 인메모리 데이터베이스 사용
- 애플리케이션 시작 시 테이블 자동 생성
- 애플리케이션 종료 시 데이터 삭제

### 운영 환경
- PostgreSQL 데이터베이스 사용
- `application.yml`에서 주석 처리된 설정을 활성화하여 사용

## 🔧 설정

### 포트 변경
`application.yml`에서 `server.port` 값을 수정하여 포트를 변경할 수 있습니다.

### 데이터베이스 변경
1. `application.yml`에서 H2 설정을 주석 처리
2. PostgreSQL 설정의 주석을 해제
3. 데이터베이스 연결 정보 수정

## 📊 모니터링

Spring Boot Actuator를 통해 다음 엔드포인트를 제공합니다:
- `/actuator/health`: 애플리케이션 상태 확인
- `/actuator/info`: 애플리케이션 정보
- `/actuator/metrics`: 메트릭 정보

## 🧪 테스트

```bash
./gradlew test
```

## 📝 로그

애플리케이션 로그는 다음과 같이 설정되어 있습니다:
- SQL 쿼리 로그 출력
- 요청/응답 로그 출력
- 에러 로그 상세 출력 