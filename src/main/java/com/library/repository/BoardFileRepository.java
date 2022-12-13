package com.library.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.library.entity.BoardFile;

public interface BoardFileRepository extends JpaRepository<BoardFile, Long> {
	
	static final String UPDATE_DELETE_YN= "UPDATE board_file "
			+ "SET DELETE_YN = 'Y' "
			+ "WHERE ID IN (:deleteIdList)";
	
	static final String DELETE_BOARD_FILE_YN= "UPDATE board_file "
			+ "SET DELETE_YN = 'Y' "
			+ "WHERE BOARD_ID IN (:boardIdList)";
	
	List<BoardFile> findByBoardId(@Param("boardId") Long boardId);
	
	@Transactional
	@Modifying
	public void deleteByIdIn(@Param("deleteIdList") Long[] deleteIdList);
	
	@Transactional
	@Modifying
	public void deleteByBoardIdIn(@Param("deleteIdList") Long[] deleteIdList);
	
	@Transactional
	@Modifying
	@Query(value = UPDATE_DELETE_YN, nativeQuery = true)
	public int updateDeleteYn(@Param("deleteIdList") Long[] deleteIdList);
	
	@Transactional
	@Modifying
	@Query(value = DELETE_BOARD_FILE_YN, nativeQuery = true)
	public int deleteBoardFileYn(@Param("boardIdList") Long[] boardIdList);
}
