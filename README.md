# 개발환경

1. IDE: IntelliJ IDEA
2. Spring Boot 3.3.2
3. JDK 21
4. mySQL
5. Spring Data JPA
6. Thymeleaf

<br/>

# 게시판 주요기능
1. 글쓰기 `(/board/save)`
2. 글 목록 `(/board/)`
3. 글 조회 `(/board/{id})`
4. 글 수정 `(/board/update/{id})`
   - 상세화면에서 수정 버튼 클릭
   - 서버에서 해당 게시글의 정보를 가지고 수정 화면 출력
   - 제목, 내용 수정하도록 함
   - 수정 처리
5. 글 삭제 `(/board/delete/{id})`
6. 페이징 처리 `(/board/paging)`

<br>

---
#### 참고
[코딩레시피 - 스프링부트 게시판 프로젝트](https://youtube.com/playlist?list=PLV9zd3otBRt7jmXvwCkmvJ8dH5tR_20c0&si=NksYpw23bdCF0Ll_)
<br/>
