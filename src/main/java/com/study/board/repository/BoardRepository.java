package com.study.board.repository;

import com.study.board.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<Board, Integer> {
    //findBy[컬럼명]Containing특정 단어가 포함되어 있으면 검색
    //findBy[컬럼명]: 키워드가 정확히 일치해야 검색
    Page<Board> findByTitleContaining(String searchKeyword, Pageable pageable);
}
