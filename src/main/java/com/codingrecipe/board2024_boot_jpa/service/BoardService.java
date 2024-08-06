package com.codingrecipe.board2024_boot_jpa.service;

import com.codingrecipe.board2024_boot_jpa.dto.BoardDTO;
import com.codingrecipe.board2024_boot_jpa.entity.BoardEntity;
import com.codingrecipe.board2024_boot_jpa.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
}
