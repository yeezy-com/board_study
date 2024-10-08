package com.codingrecipe.board2024_boot_jpa.entity;

import com.codingrecipe.board2024_boot_jpa.dto.BoardDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

// DB의 테이블과 매핑하는 역할을 하는 객체이다.
// 이 엔티티 객체로 인해 DB 테이블을 객체지향적이게 활용할 수 있다.
@Entity
@Getter
@Setter
@Table(name = "board_table")
public class BoardEntity extends BaseEntity {

    @Id // pk 컬럼 지정. 필수
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment 지정 (mysql)
    private long id;

    @Column(length = 20, nullable = false) // 일반 컬럼 지정. 크기 20, not null
    private String boardWriter;

    @Column//(unique = true) // 디폴트는 크기 255, nullable = true
    private String boardPass;

    @Column
    private String boardTitle;

    @Column(length = 500)
    private String boardContents;

    @Column
    private int boardHits;

    @Column
    private int fileAttached; // 1 or 0

    // BoardFileEntity에서 @ManyToOne의 변수 이름과 맞추면 됨
    @OneToMany(mappedBy = "boardEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<BoardFileEntity> boardFileEntityList = new ArrayList<>();

    public static BoardEntity toSaveEntity(BoardDTO boardDTO) {
        BoardEntity boardEntity = new BoardEntity();
        boardEntity.setBoardWriter(boardDTO.boardWriter());
        boardEntity.setBoardPass(boardDTO.boardPass());
        boardEntity.setBoardTitle(boardDTO.boardTitle());
        boardEntity.setBoardContents(boardDTO.boardContents());
        boardEntity.setBoardHits(0);
        boardEntity.setFileAttached(0); // 파일 없음.

        return boardEntity;
    }

    public static BoardEntity toUpdateEntity(BoardDTO boardDTO) {
        BoardEntity boardEntity = new BoardEntity();
        boardEntity.setId(boardDTO.id());
        boardEntity.setBoardWriter(boardDTO.boardWriter());
        boardEntity.setBoardPass(boardDTO.boardPass());
        boardEntity.setBoardTitle(boardDTO.boardTitle());
        boardEntity.setBoardContents(boardDTO.boardContents());
        boardEntity.setBoardHits(boardDTO.boardHits());

        return boardEntity;
    }

    public static BoardEntity toSaveFileEntity(BoardDTO boardDTO) {
        BoardEntity boardEntity = new BoardEntity();
        boardEntity.setBoardWriter(boardDTO.boardWriter());
        boardEntity.setBoardPass(boardDTO.boardPass());
        boardEntity.setBoardTitle(boardDTO.boardTitle());
        boardEntity.setBoardContents(boardDTO.boardContents());
        boardEntity.setBoardHits(0);
        boardEntity.setFileAttached(1); // 파일 있음.

        return boardEntity;
    }
}
