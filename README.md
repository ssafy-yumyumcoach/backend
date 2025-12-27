# YumYumCoach Backend
식단·운동 기록과 AI 추천을 제공하는 SSAFY 14기 관통프로젝트 백엔드 API 서버입니다. 

## 기술 스택
- Spring Boot 4, Java 17, Groovy
- MyBatis, MySQL
- Spring Security, JWT
- AWS S3

```
docker compose -f docker-compose.yml up --build
```

- API: http://localhost:8080
- MySQL: localhost:3306 (user: root / password: ssafy)

## 로컬 실행
사전 준비: JDK 17, MySQL 8

1) DB 준비
```
mysql -u root -p < db/init.sql
```

2) 환경변수 (.env 또는 시스템 환경변수)
```
JWT_SECRET=your-jwt-secret
GMS_KEY=your-gms-key
AWS_ACCESS_KEY_ID=your-aws-access-key-id
AWS_SECRET_ACCESS_KEY=your-aws-secret-access-key
AWS_SESSION_TOKEN=optional
AI_SERVER_URL=http://localhost:8000
```

3) 실행
```
./gradlew bootRun
```

## 설정 파일
- `src/main/resources/application.yml`
  - 기본 profile: `dev`
  - 기본 DB: `jdbc:mysql://localhost:3306/yumyumcoach`

## 파일 구조
```
backend
├── db/                             # DB 초기화/시드 SQL
├── docker-compose.yml              # 백엔드 + MySQL 도커 구성
├── src/main/groovy/com/yumyumcoach
│   ├── domain/                     # 도메인별 기능 (auth, diet, exercise, ...)
│   └── global/                     # 공통 설정/보안/예외 처리
└── src/main/resources
    ├── application.yml             # Spring 설정
    └── mapper/                     # MyBatis SQL 매퍼
```

## 주요 API (요약)
| 도메인 | Base Path | 기능 요약 |
| --- | --- | --- |
| Auth | `/api/auth` | 로그인/회원가입, 토큰 갱신, 로그아웃, 탈퇴 |
| Users/Follow | `/api/users`, `/api/users/{userId}/follow` | 내 정보/프로필 조회, 팔로우/언팔로우, 팔로워/팔로잉 목록 |
| Diet | `/api/foods`, `/api/me/diets` | 음식 관리, 식단 기록 등록/조회/수정/삭제 |
| Exercise | `/api/exercises`, `/api/me/exercise-records` | 운동 목록/검색, 내 운동 기록 관리 |
| Community | `/api/posts`, `/api/posts/{postId}/comments` | 게시글/댓글 CRUD, 좋아요 |
| Challenge | `/api/challenges` | 챌린지 목록/상세, 참여/탈퇴 |
| Title | `/api/users/me/title`, `/api/users/me/titles` | 칭호 조회/설정 |
| AI | `/api/ai` | 음식 이미지 감지, 식단/운동 평가, 식단 추천 |
| Stats | `/api/me/stats/week` | 주간 요약 통계 |
| Image | `/api/images/presign` | S3 업로드용 Presign URL |
| AI Chatbot | `/api/ai/chatbot` | 챗봇 대화 시작/질문, 상태 조회 |

## DB 시드
추가 시드는 `db/*.sql`에 있습니다. 필요 시 순서대로 실행하세요.

## Collaborators

## 👥 Collaborators

<table>
    <tr height="160px">
      <td align="center" width="200px">
            <a href="https://github.com/rhfo0509"><img src="https://avatars.githubusercontent.com/u/85874042?v=4"/></a>
            <br/>
            <a href="https://github.com/rhfo0509"><strong>김응서</strong></a>
            <br />
        </td>
        <td align="center" width="200px">
            <a href="https://github.com/butterfly0327"><img src="https://avatars.githubusercontent.com/u/184582197?v=4"/></a>
            <br/>
            <a href="https://github.com/butterfly0327"><strong>박규동</strong></a>
            <br />
        </td>
        <td align="center" width="200px">
            <a href="https://github.com/exercit196"><img src="https://avatars.githubusercontent.com/u/219389779?v=4"/></a>
            <br/>
            <a href="https://github.com/exercit196"><strong>박규빈</strong></a>
            <br />
        </td>
        <td align="center" width="200px">
            <a href="https://github.com/eonseo"><img src="https://avatars.githubusercontent.com/u/139443553?v=4"/></a>
            <br/>
            <a href="https://github.com/eonseo"><strong>오언서</strong></a>
            <br />
        </td>
    </tr>
</table>

</div>
