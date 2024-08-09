package com.codingrecipe.board2024_boot_jpa.dto;

import com.codingrecipe.board2024_boot_jpa.entity.BoardEntity;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

// DTO (Data Transfer Object), [VO, Bean]
// 데이터 전송 객체로 데이터 전송을 위해 존재하는 객체이다.
// 데이터를 주고 받을 때 따로 하나씩 받기에는 너무 관리가 쉽지않고,
// 엔티티와 같은 비즈니스적인 객체로 주고받기에는 주고받지 않는 요소가 존재할 수 있어서 사용한다.
public record BoardDTO(
        Long id,
        String boardWriter,
        String boardPass,
        String boardTitle,
        String boardContents,
        int boardHits,
        LocalDateTime boardCreatedTime,
        LocalDateTime boardUpdatedTime,
        MultipartFile boardFile, // save.html -> Controller로 넘어올 때 파일 담는 객체
        String originalFileName, // 원본 파일 이름
        String storedFileName,   // 서버 저장용 파일 이름
        // 서버 저장용 이름을 따로 두는 이유는 원본 파일 이름이 중복될 수 있기 때문이다.
        int fileAttached         // 파일 첨부 여부(첨부1, 미첨부0)
) {

//    public BoardDTO(Long id, String boardWriter, String boardTitle, int boardHits, LocalDateTime boardCreatedTime) {
//        this(id, boardWriter, "", boardTitle, "", boardHits, boardCreatedTime, null, null, "", "", 0);
//    }

//    public BoardDTO() {
//        this(null, null, null, 0, null);
//    }

    public static BoardDTO toBoardDTO(BoardEntity boardEntity) {
        BoardDTO boardDTO = new BoardDTO(boardEntity.getId(), boardEntity.getBoardWriter(),
                boardEntity.getBoardPass(), boardEntity.getBoardTitle(), boardEntity.getBoardContents(),
                boardEntity.getBoardHits(), boardEntity.getCreatedTime(), boardEntity.getUpdatedTime(),
                null, null, null, 0);

        return boardDTO;
    }
}

/*
// Lombok을 사용하면 데이터의 필드만 정의해주면 되므로 개발에 상당한 이점을 얻을 수 있다.
@Getter
@Setter
@ToString
@NoArgsConstructor // 기본 생성자
@AllArgsConstructor // 모든 필드를 매개변수로 하는 생성자
public class BoardDTO {
    private Long id;
    private String boardWriter;
    private String boardPass;
    private String boardTitle;
    private String boardContents;
    private int boardHits;
    private LocalDateTime boardCreatedTime;
    private LocalDateTime boardUpdatedTime;
}
*/
