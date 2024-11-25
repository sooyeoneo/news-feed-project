# news-feed-project 📱
## 친구들의 가장 최근 업데이트된 게시물을 볼 수 있는 페이지 만들기 🧑🏻‍💻

### **Lv 0. 목표 정하기! (Growth Mindset)**

1. **데이터베이스와 ORM**
    - [X]  데이터베이스 스키마를 설계할 수 있다.
    - [X]  JPA를 이용해 데이터베이스와 연동할 수 있다.
    - [X]  JPA를 통해 CRUD 작업을 할 수 있다.
2. **인증**
    - [X]  사용자 인증과 인가의 기본 원리와 차이점을 이해하고 있다.
    - [X]  Cookie/Session과 Filter를 이해하고 활용할 수 있다.
3. **REST API**
    - [X]  기능에 알맞게 REST API 설계를 할 수 있다.
    - [X]  Spring Boot를 이용해 REST API를 구현할 수 있다.
4. **협업 및 버전 관리**
    - [X]  Git을 사용해 소스 코드 버전 관리를 할 수 있다.
    - [X]  Git branch를 이용하여 브랜치 관리 및 원활한 협업을 할 수 있다.
    - [X]  Pull Request와 코드 리뷰 과정에 대해 이해할 수 있다.
------------
## 와이어프레임 📝
### Lv 1. 와이어프레임 작성 
 - [X]  **와이어프레임 작성하기**
    - [X]  효율적이고 최적화된 데이터 흐름을 설계하기
    - [X]  전체적인 시스템 아키텍처 이해
    - [X]  UI/UX를 고려한 기술적 결정 사항 Figma로 작성

#### 테이블 생성
![도전 기능 포함 와이어그램_박예진님  2024-11-22 오후 3 32 30](https://github.com/user-attachments/assets/f2734dbe-2723-4a50-85d6-11e388d76023)

------------
## ERD 📁
### Lv 2. ERD     
 - [X]  **ERD 작성하기**
    - [X]  Entity에 들어갈 요소 정하기
    - [X]  Entity의 관계 설정하기
    - [X]  ERD는 프로젝트 root(최상위) 경로의 `README.md` 에 첨부
<img width="1236" alt="뉴스피드 프로젝트 ERD 허수연 2024-11-24 오전 2 48 44" src="https://github.com/user-attachments/assets/13e094c8-a6c7-499f-807f-a4bf728e228d">

------------
## API 명세서 📋
### Lv 3. API 명세 작성  
 - [X]  **API 명세서 작성하기**
    - [X]  현실화할 수 있는 기능을 API 명세로 작성
    - [X]  API명세서는 프로젝트 root(최상위) 경로의 `README.md` 에 첨부
      
        [Postman news-feed-project API 명세서](https://documenter.getpostman.com/view/39375040/2sAYBRFDdo)
  
---------------
## 기능 구현 🖥  
### Lv 4. 본격적으로 백엔드 개발하기 
- Spring 이용하여 본격적으로 서버를 개발하기
- 구현하고자 하는 기능들을 JPA를 사용하여 어떻게 구현할 수 있을지 고민하기
- 기술 개발 및 코드 병합 과정 방법을 논의
  - 브랜치를 어떻게 나누고 어떻게 병합할 것인가?
  - 어느 시점에 브랜치 병합을 할 것인가?
  - 브랜치 나누기
    - 메인 브랜치(main) - 배포용
    - 개발 브랜치(develop)
    - 기능 단위 별 브랜치(feature)
  - 병합 순서는 대체로 이렇게 진행
    - feature → merge → develop → merge → main
  
### 1. 프로필 CRUD  `필수`
- **프로필 생성기능 POST**
    - 고유 식별자, 이름, 이메일, 비밀번호, 나이
        - 생성일, 수정일 : createTime updateTime (**LocalDateTime**)
- **프로필 조회 기능 GET**
    - 고유 식별자, 이름, 이메일, 나이
        - 다른 사용자의 프로필 조회 시, 민감한 정보는 표시되지 않는다. 
        **민감한 정보 : 비밀번호**
- **프로필 수정 기능 PUT**
    - 로그인한 사용자만 본인 정보 수정 가능.
    - **⚠️ 예외처리**
        - 비밀 번호 일치 여부 확인
        - 비밀 번호 형식에 맞지 않는 경우 (**대소문자, 숫자1, 특수문자 1개 이상, 총 8글자 이상**)
        - 현재 비밀번호와 **동일한 비밀번호**로 수정하는 경우
- **프로필 삭제 기능 DELETE**
    - DB에서 완전 삭제

### 2. 게시물 CRUD  `필수`
- **게시물 생성 POST**
    - 고유 식별자, 게시물 제목, 게시물 내용
- **게시물 조회 GET**
    - **뉴스피드 조회 기능, 고유 식별자**
        - 생성일자 ****기준으로 내림차순 정렬
        - 페이지네이션 10개씩 각 페이지당 뉴스피드 데이터 10개
- **게시물 수정 PUT**
    - 조건
        - 게시물 수정은 작성자 본인만 가능
    - **⚠️ 예외처리**
        - 작성자가 아닌 다른 사용자가 게시물 수정 → 불가
- **게시물 삭제 DELETE**
    - 조건
        - 게시물 삭제는 작성자 본인만 가능
    - **⚠️ 예외처리**
        - 작성자가 아닌 다른 사용자가 게시물 삭제 → 불가
          
### 3. 로그인 Session  `필수`
- **회원가입 기능**
    - 사용자 아이디
        - 사용자 아이디는 이메일 형식
    - 비밀번호
        - `Bcrypt`로 인코딩
            - 암호화를 위한 `PasswordEncoder`를 직접 만든다.
        - **대소문자 포함 영문 + 숫자 + 특수문자를 최소 1글자씩 포함**
        - 비밀번호는 **최소 8글자 이상**
    - **⚠️ 예외처리**
     - 중복된 `사용자 아이디`로 가입하는 경우
     - `사용자 아이디` 이메일과 비밀번호 형식이 올바르지 않은 경우
- **회원탈퇴 기능**
    - 조건
      - 탈퇴 전 게시물이 남아있는 경우 회원 탈퇴 불가
      - 탈퇴 처리 시 `비밀번호`를 확인한 후 일치할 때 탈퇴 → 이미 삭제된 이메일
      - 탈퇴한 사용자의 아이디는 재사용, 복구 불가
    - **⚠️ 예외처리**
      - `사용자 아이디`와 `비밀번호`가 일치하지 않는 경우
      - 이미 탈퇴한 `사용자 아이디`인 경우 

### 4. 친구 CRUD  `필수`
- **친구 생성 기능 POST**
    - 고유 식별자, userId
    - 특정 사용자를 친구로 추가 가능
- **친구 조회 기능 GET**
    - userId
    - 뉴스피드에 친구의 **최신 게시물을 우선으로 조회**
- **친구 수정 기능 PUT**
- **친구 삭제 기능 DELETE**
    - 특정 사용자를 친구로 삭제 가능
- **⚠️ 주의사항**
    - 친구는 상대방의 수락 기능이 있음.

### 5. 댓글 CRUD  `도전`
- 댓글 작성, 조회, 수정, 삭제
    - 사용자는 게시물에 댓글을 작성할 수 있고, 본인의 댓글은 **수정 및 삭제**를 할 수 있다.
    - **내용**만 수정이 가능
    - 댓글 수정, 삭제는 댓글의 작성자 혹은 게시글의 작성자만 가능
- 댓글 수정, 삭제는 댓글의 작성자 혹은 게시글의 작성자만 가능

### 6. 좋아요 CRUD  `도전`
- 사용자가 게시물이나 댓글에 좋아요를 남기거나 취소할 수 있다.
- 본인이 작성한 게시물과 댓글에 좋아요를 남길 수 없다.
- 같은 게시물에는 사용자당 한 번만 좋아요가 가능하다.
---------------
### Lv 5. 테스트 및 버그 수정하기
- 개발한 기능들이 제대로 작동하는지 테스트
- Postman에서 서버 요청을 통한 실행 테스트
- 그리고 버그나 오류를 찾아 수정하고, 사용성을 향상시킬 수 있는 부분이 있는지 찾아볼 것.

------------
## 팀원 👥

 |이름|Github|블로그|
 |------|------|------|
 |박예진 팀원|[hamuck](https://github.com/hamuck)|[velog](https://velog.io/@qkrdpwls2002/posts)|
 |한승완 팀원|[Dawnfeeling](https://github.com/Dawnfeeling)|[velog](https://velog.io/@swhan98/posts)|
 |허수연 팀장|[sooyeoneo](https://github.com/sooyeoneo)|[tistory](https://sooyeoneo.tistory.com/)|

------------
## WBS & Tasks ✍🏻
### 박예진
    1. 와이어프레임 (Figma)
    2. API Notion, Postman 수정
    3. 친구 관리 CRUD
    4. 좋아요 기능
    5. 예외처리 수정 
    6. 트러블 슈팅 작성 : 발표 자료 추가
    7. 발표자료 PPT
### 한승완
    1. API 설계 (Notion)
    2. 뉴스피드 게시물 관리 CRUD
    3. 프로필 관리 CRUD
    4. 댓글 CRUD
    5. 예외 처리 추가, 수정 
    6. 코드 리팩토링
    7. 시연 영상
### 허수연
    1. Github Repository 관리
    2. 프로젝트 초기 설정 : 패키지, 클래스, 인터페이스 생성, entity
    3. Postman 관리, 수정
    4. ERD (도전 기능 포함)
    5. 사용자 인증 Session
    6. 예외처리 수정
    7. 발표
 
------------
## 트러블 슈팅 🎯
[뉴스피드 프로젝트 트러블 슈팅 - 박예진](https://velog.io/@qkrdpwls2002/24111924-%EC%A1%B0%EB%B3%84%EA%B3%BC%EC%A0%9C-%ED%8A%B8%EB%9F%AC%EB%B8%94%EC%8A%88%ED%8C%85)

[뉴스피드 프로젝트 트러블 슈팅 - 허수연](https://sooyeoneo.tistory.com/73)

[뉴스피드 프로젝트 트러블 슈팅 - 한승완](https://velog.io/@swhan98/%EB%89%B4%EC%8A%A4%ED%94%BC%EB%93%9C-%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8-%ED%9A%8C%EA%B3%A0)

