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
   - `/board/paging?page=2`
   - `/board/paging/2`
     - 페이지에 보여지는 게시글은 항상 다르다.
     - 그래서, 페이징을 rest api가 아닌 쿼리 스트링으로 넘김
7. 파일(이미지) 첨부하기
   - 단일 파일 첨부
   - 다중 파일 첨부
   - 파일 첨부와 관련하여 추가될 부분들
     - save.html
       - 글 작성 시 이미지 첨부
     - BoardDTO
       - 글 작성 후 서버에 전달
     - BoardService.save()
       - 파일을 체크하고, DB에 저장
     - BoardEntity
       - 파일과 관련된 컬럼 추가
     - BoardFileEntity, BoardFileRepository 추가
       - 파일 자체는 디렉토리에 저장되고, DB에는 파일의 이름이 저장 됨
       - 파일의 이름을 관리하는 엔티티.
       - board_table(부모) - board_file_table(자식)
    ```sql
    create table board_table
    (
    id               bigint auto_increment primary key,
    created_time     datetime     null,
    updated_time     datetime     null,
    board_contents   varchar(500) null,
    board_hits       int          null,
    board_pass       varchar(255) null,
    board_title      varchar(255) null,
    board_writer     varchar(20)  not null,
    file_attached    int          null
    );
   
    create table board_file_table
    (
    id                 bigint auto_increment primary key,
    created_time       datetime     null,
    updated_time       datetime     null,
    original_file_name varchar(255) null,
    stored_file_name   varchar(255) null,
    board_id           bigint       null,
    constraint FKcfxqly70ddd02xbou0jxgh4o3
        foreign key (board_id) references board_table (id) on delete cascade
    );
    ```
     - detail.html
       - 이미지를 글 상세화면에서 보여주기

<br>

---
#### 참고
[코딩레시피 - 스프링부트 게시판 프로젝트](https://youtube.com/playlist?list=PLV9zd3otBRt7jmXvwCkmvJ8dH5tR_20c0&si=NksYpw23bdCF0Ll_)
<br/>
