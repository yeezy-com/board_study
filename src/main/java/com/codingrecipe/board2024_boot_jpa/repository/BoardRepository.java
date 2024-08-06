package com.codingrecipe.board2024_boot_jpa.repository;

import com.codingrecipe.board2024_boot_jpa.entity.BoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

// 리포지토리로 저장할 Entity 클래스와 pk 컬럼의 타입 지정
public interface BoardRepository extends JpaRepository<BoardEntity, Long> {

    // update board_table set board_hits=board_hits+1 where id=?
    @Modifying
    @Query(value = "update BoardEntity b set b.boardHits=b.boardHits+1 where b.id=:id")
    void updateHits(@Param("id") Long id);
}
