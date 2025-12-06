# 🏫 방과후 수업 관리 시스템 (Backend)

> **After-School Class Management System API Server** > 고등학교 방과후 수업 개설, 수강신청, 출석 및 공지사항 관리를 위한 RESTful API 서버입니다.

<br/>

## 🛠 Tech Stack

| Category | Technology | Version |
| :--- | :--- | :--- |
| **Language** | Java | JDK 21 |
| **Framework** | Spring Boot | 3.5.6 |
| **Build Tool** | Maven | Latest |
| **Database** | Oracle Cloud DB (ATP) | 19c / 21c |
| **ORM** | Spring Data JPA | - |
| **Security** | Spring Security + JWT | - |
| **Docs** | Swagger (SpringDoc) | v3 (OpenAPI) |

<br/>

## ✨ Key Features

### 1. 인증 & 인가 (Authentication)
- **회원가입:** 학생, 교사, 관리자 분리 가입
  - 교사/관리자는 사전에 지정된 **인증 코드**(`TEACHER1234`, `ADMIN1234`) 검증 후 가입 가능
- **로그인:** JWT 기반 인증 (Single Token Strategy)
  - Access Token 발급 (Long-lived)
- **보안:** Spring Security Filter Chain을 통한 엔드포인트별 접근 권한 제어

### 2. 수업 관리 (Class Management)
- **수업 개설:** 교사가 수업 정보 및 시간표(요일/시간)를 설정하여 개설
- **수강 신청:**
  - 학생이 원하는 수업 신청
  - **동시성/정원 체크:** 최대 수강 인원 초과 시 신청 불가
  - 중복 신청 방지 로직 적용
- **수업 조회:** 전체 목록, 내가 개설한 수업(교사), 내가 신청한 수업(학생) 조회
- **수업 수정/삭제:** 개설자 본인만 수정/삭제 가능 (Cascade 설정을 통해 연관 데이터 자동 처리)

### 3. 공지사항 (Notice)
- **공지 등록:** 수업별 공지사항 등록 (일반, 휴강, 시간변경 태그 지원)
- **공지 조회:** 학생은 본인이 수강 중인 모든 수업의 공지를 최신순으로 모아보기 가능

### 4. 회원 관리 (User Management)
- 관리자(Admin) 전용 전체 회원(학생/교사) 리스트 조회 기능

<br/>

## ⚙️ Environment Setup

이 프로젝트는 **Oracle Cloud Wallet**과 **환경 변수(.env)** 설정이 필수입니다.

### 1. Oracle Wallet 설정
보안 담당자로부터 전달받은 `Wallet.zip` 파일을 압축 해제 후, 아래 경로에 위치시킵니다.
- **Windows:** `C:\Oracle\Wallet`
- **Mac/Linux:** `~/oracle/wallet`

### 2. 환경 변수 설정 (.env)
프로젝트 루트 경로(`pom.xml`과 같은 위치)에 `.env` 파일을 생성하고 아래 내용을 작성합니다.

```properties
# .env file
# DB 연결 정보 (TNS_ADMIN 경로는 본인 OS에 맞게 수정)
ORACLE_DB_URL=jdbc:oracle:thin:@db_alias?TNS_ADMIN=C:/Oracle/Wallet
ORACLE_DB_USERNAME=admin
ORACLE_DB_PASSWORD=your_db_password

# JWT 비밀키 (32자 이상의 임의 문자열)
JWT_SECRET=v3RyS3cr3tK3yF0rJs0nW3bT0k3nS1gn4tur3V4lu3
🚀 Getting Started
1. Clone Repository
Bash

git clone [https://github.com/your-repo/after-school-backend.git](https://github.com/your-repo/after-school-backend.git)
cd after-school-backend
2. Build Project
테스트 코드를 건너뛰고 빠르게 빌드하려면 아래 명령어를 사용합니다.

Bash

./mvnw clean package -DskipTests
3. Run Application
Bash

java -jar target/afterSchool-0.0.1-SNAPSHOT.jar
서버가 정상적으로 실행되면 Tomcat started on port 8080 로그가 출력됩니다.

📚 API Documentation (Swagger)
서버 실행 후 아래 주소로 접속하면 API 명세서를 확인하고 테스트할 수 있습니다.

Swagger UI: http://localhost:8080/swagger-ui/index.html

⚠️ 테스트 시 주의사항 (403 Forbidden 해결)

Swagger 페이지 우측 상단의 초록색 Authorize 버튼을 클릭합니다.

로그인 API를 통해 발급받은 Access Token을 입력합니다. (Bearer 없이 토큰 값만 입력)

Authorize -> Close 후 API를 요청합니다.

📂 Project Structure
Bash

src/main/java/com/example/afterSchool
├── config           # Security, Swagger 설정
├── controller       # API 엔드포인트 (Auth, Class, Notice, User)
├── dto              # 데이터 전송 객체 (Request/Response)
├── entity           # JPA 엔티티 및 Enum (Student, Teacher, Class...)
├── repository       # DB 접근 계층 (JPA Repository)
├── security         # JWT Provider, Auth Filter
└── service          # 비즈니스 로직
👨‍💻 Contributors
PM & Backend Lead: 김OO 팀장

Frontend: (Member Name)
