package com.library.entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.data.annotation.CreatedDate;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class BoardFile {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private Long boardId;
	private String origFileName;
	private String saveFileName;
	private int fileSize;
	private String fileExt;
	private String filePath;
	private String deleteYn;
	
	@CreatedDate
	private LocalDateTime registerTime;
	
	@Builder
	public BoardFile(Long id, Long boardId, String origFileName, String saveFileName, int fileSize, String fileExt,
			String filePath, String deleteYn, LocalDateTime registerTime) {
		this.id = id;
		this.boardId = boardId;
		this.origFileName = origFileName;
		this.saveFileName = saveFileName;
		this.fileSize = fileSize;
		this.fileExt = fileExt;
		this.filePath = filePath;
		this.deleteYn = deleteYn;
		this.registerTime = registerTime;
	}
}
