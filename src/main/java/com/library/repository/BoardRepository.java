package com.library.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.library.entity.Board;
import com.library.entity.Category;

public interface BoardRepository extends JpaRepository<Board, Long> {


	static final String UPDATE_BOARD_READ_CNT_INC = "UPDATE Board " + "SET READ_CNT = READ_CNT + 1 "
			+ "WHERE BOARD_ID = :id";

	static final String DELETE_BOARD = "DELETE FROM Board " + "WHERE BOARD_ID IN (:deleteList)";

	@Transactional
	@Modifying
	@Query(value = UPDATE_BOARD_READ_CNT_INC, nativeQuery = true)
	public int updateBoardReadCntInc(@Param("id") Long id);

	@Transactional
	@Modifying
	@Query(value = DELETE_BOARD, nativeQuery = true)
	public int deleteBoard(@Param("deleteList") Long[] deleteList);

	// 전체 게시판
	public Page<Board> findByTitleContaining(Pageable pageable, String searchKeyword);

	public Page<Board> findByContentContaining(Pageable pageable, String searchKeyword);

	// 공지사항 게시판
	
	public Page<Board> findAllByCategory(Pageable pageable, Category category);

	public Page<Board> findByTitleContainingAndCategory(Pageable pageable, String searchKeyword, Category category);

	public Page<Board> findByContentContainingAndCategory(Pageable pageable, String searchKeyword, Category category);
	
	// 댓글 제외 게시글 출력
	public Optional<Board> findByBlevel(Long blevel);

}