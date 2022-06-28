package com.its.board.repository;

import com.its.board.entity.BoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BoardRepository extends JpaRepository<BoardEntity,Long> {

    // jpql(java persistence query language)
    @Modifying
    @Query(value = "update BoardEntity b set b.boardHits = b.boardHits + 1 where b.id = :id")
    int hits(@Param("id") Long id);

    // 검색 쿼리
    // select * from board_table where board_title like '%?%'
    List<BoardEntity> findByBoardTitleContaining(String q);
    // 제목 또는 내용이 포함된 검색

    // select * from board_table where board_title like '%?%' or board_contents like '%?%'
    // List<BoardEntity> findByBoardTitleContainingOOrBoardContentsContaining(String q , String q);
}
