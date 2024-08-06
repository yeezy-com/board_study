package com.codingrecipe.board2024_boot_jpa.repository;

import com.codingrecipe.board2024_boot_jpa.entity.BoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;

// 리포지토리로 저장할 Entity 클래스와 pk 컬럼의 타입 지정
public interface BoardRepository extends JpaRepository<BoardEntity, Long> {
}
