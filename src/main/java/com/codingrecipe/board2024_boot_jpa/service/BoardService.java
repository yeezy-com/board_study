package com.codingrecipe.board2024_boot_jpa.service;

import com.codingrecipe.board2024_boot_jpa.dto.BoardDTO;
import com.codingrecipe.board2024_boot_jpa.entity.BoardEntity;
import com.codingrecipe.board2024_boot_jpa.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    public void save(BoardDTO boardDTO) {
        // repository는 Entity 클래스만 받는다.
        // DTO -> Entity 혹은 Entity -> DTO 변환 과정이 필요
        // 권고사항 - Entity 클래스는 DB와 깊은 연관이 있으므로 View 단으로 노출을 시키지마라.
        // 최대한 서비스 클래스까지만 오도록 코드를 짜자

        // DTO -> Entity (Entity Class에서)
        // Entity -> DTO (DTO Class에서)

        BoardEntity saveEntity = BoardEntity.toSaveEntity(boardDTO);
        boardRepository.save(saveEntity);
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
}
