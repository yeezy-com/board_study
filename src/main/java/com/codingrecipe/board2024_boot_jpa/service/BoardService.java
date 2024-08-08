package com.codingrecipe.board2024_boot_jpa.service;

import com.codingrecipe.board2024_boot_jpa.dto.BoardDTO;
import com.codingrecipe.board2024_boot_jpa.entity.BoardEntity;
import com.codingrecipe.board2024_boot_jpa.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    public void save(BoardDTO boardDTO) throws IOException {
        // repository는 Entity 클래스만 받는다.
        // DTO -> Entity 혹은 Entity -> DTO 변환 과정이 필요
        // 권고사항 - Entity 클래스는 DB와 깊은 연관이 있으므로 View 단으로 노출을 시키지마라.
        // 최대한 서비스 클래스까지만 오도록 코드를 짜자

        // DTO -> Entity (Entity Class에서)
        // Entity -> DTO (DTO Class에서)

        // 파일 첨부 여부에 따라 로직을 분리해야 함.
        if (boardDTO.boardFile().isEmpty()) {
            // 첨부 파일이 없는 경우
            BoardEntity saveEntity = BoardEntity.toSaveEntity(boardDTO);
            boardRepository.save(saveEntity);
        } else {
            // 첨부 파일이 있는 경우
            /*
                1. DTO에 담긴 파일을 꺼냄
                2. 파일의 이름을 가져옴
                3. 서버 저장용 이름을 만듦
                4. 저장 경로 설정
                5. 해당 경로에 파일 저장
                6. board_table에 해당 데이터 save 처리
                7. board_file_table에 해당 데이터 save 처리
             */

            Map<String, String> env = System.getenv();
            MultipartFile boardFile = boardDTO.boardFile(); // 1.
            String originalFilename = boardFile.getOriginalFilename(); // 2.
            String storedFileName = UUID.randomUUID().toString() + "_" + originalFilename; // 3.
            String savePath = "/Users/" + env.get("path") + "/Pictures/spring_pic/" + storedFileName; // 4.

            boardFile.transferTo(new File(savePath)); // 5.
        }
    }

    public List<BoardDTO> findAll() {
        List<BoardEntity> boardEntityList = boardRepository.findAll();
        List<BoardDTO> boardDTOList = new ArrayList<>();

        for (BoardEntity boardEntity : boardEntityList)
            boardDTOList.add(BoardDTO.toBoardDTO(boardEntity));

        return boardDTOList;
    }

    // 수동적인 쿼리 실행에는 트랜잭션 어노테이션을 추가해야 한다.
    @Transactional
    public void updateHits(Long id) {
        boardRepository.updateHits(id);
    }

    public BoardDTO findById(Long id) {
        Optional<BoardEntity> optionalBoardEntity = boardRepository.findById(id);
        if (optionalBoardEntity.isPresent())
            return BoardDTO.toBoardDTO(optionalBoardEntity.get());

        return null;
    }

    public BoardDTO update(BoardDTO boardDTO) {
        // id 값이 존재한 상태로 엔티티 객체를 save하면
        // JPA는 save가 아닌 update로 인식하여 update 쿼리를 보냄
        boardRepository.save(BoardEntity.toUpdateEntity(boardDTO));

        return findById(boardDTO.id());
    }

    public void delete(Long id) {
        boardRepository.deleteById(id);
    }

    public Page<BoardDTO> paging(Pageable pageable) {
        int page = pageable.getPageNumber() - 1; // 1빼는 이유? page 값이 0부터 시작함.
        int pageLimit = 3; // 한 페이지에 보여줄 글 개수

        // 한 페이지 당 3개씩 글을 보여주고, 정렬 기준은 id 기준으로 내림차순 정렬
        Page<BoardEntity> boardEntities =
                boardRepository.findAll(PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "id")));
                // page = 보여줄 페이지 넘버, pageLimit = 보여줄 글 개수
                // id를 기준으로 내림차순으로 페이지 결과를 정렬함. properties에 오는 이름은 Entity에 작성한 이름 기준.

        System.out.println("boardEntities.getContent() = " + boardEntities.getContent()); // 요청 페이지에 해당하는 글
        System.out.println("boardEntities.getTotalElements() = " + boardEntities.getTotalElements()); // 전체 글갯수
        System.out.println("boardEntities.getNumber() = " + boardEntities.getNumber()); // DB로 요청한 페이지 번호
        System.out.println("boardEntities.getTotalPages() = " + boardEntities.getTotalPages()); // 전체 페이지 갯수
        System.out.println("boardEntities.getSize() = " + boardEntities.getSize()); // 한 페이지에 보여지는 글 갯수
        System.out.println("boardEntities.hasPrevious() = " + boardEntities.hasPrevious()); // 이전 페이지 존재 여부
        System.out.println("boardEntities.isFirst() = " + boardEntities.isFirst()); // 첫 페이지 여부
        System.out.println("boardEntities.isLast() = " + boardEntities.isLast()); // 마지막 페이지 여부

        // 목록: id, writer, title, hits, createdTime
        // Page<BoardDTO> boardDTOS = boardEntities.map(board -> new BoardDTO(board.getId(), board.getBoardWriter(), board.getBoardTitle(),
        //        board.getBoardHits(), board.getCreatedTime()));
        Page<BoardDTO> boardDTOS = boardEntities.map(board -> BoardDTO.toBoardDTO(board));
        return boardDTOS;
    }
}
