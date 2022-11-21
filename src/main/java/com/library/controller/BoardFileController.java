package com.library.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URLEncoder;
import java.util.HashMap;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.library.dto.BoardFileRequestDto;
import com.library.dto.BoardFileResponseDto;
import com.library.service.BoardFileService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class BoardFileController {
	
	private final BoardFileService boardFileService;
	
	@GetMapping("/file/download")
	public void downloadFile(@RequestParam() Long id, HttpServletResponse response) throws Exception {
		try {
			// 파일정보를 조회한다.
			BoardFileResponseDto fileInfo = boardFileService.findById(id);
			
			if (fileInfo == null) throw new FileNotFoundException("Empty FileData.");
			
			// 경로와 파일명으로 파일 객체를 생성한다.
			File dFile  = new File(fileInfo.getFilePath(), fileInfo.getSaveFileName());
			
			// 파일 길이를 가져온다.
			int fSize = (int) dFile.length();
			
			// 파일이 존재하면
			if (fSize > 0) {
				// 파일명을 URLEncoder 하여 attachment, Content-Disposition Header로 설정
				String encodedFilename = "attachment; filename*=" + "UTF-8" + "''" + URLEncoder.encode(fileInfo.getOrigFileName(), "UTF-8");
				
				// ContentType 설정
				response.setContentType("application/octet-stream; charset=utf-8");
				
				// Header 설정
				response.setHeader("Content-Disposition", encodedFilename);
				
				// ContentLength 설정
				response.setContentLengthLong(fSize);
	
				BufferedInputStream in = null;
				BufferedOutputStream out = null;
				
				/* BufferedInputStream
				 * 
					java.io의 가장 기본 파일 입출력 클래스
					입력 스트림(통로)을 생성해줌
					사용법은 간단하지만, 버퍼를 사용하지 않기 때문에 느림
					속도 문제를 해결하기 위해 버퍼를 사용하는 다른 클래스와 같이 쓰는 경우가 많음
				*/
				in = new BufferedInputStream(new FileInputStream(dFile));
				
				/* BufferedOutputStream
				 * 
					java.io의 가장 기본이 되는 파일 입출력 클래스
					출력 스트림(통로)을 생성해줌
					사용법은 간단하지만, 버퍼를 사용하지 않기 때문에 느림
					속도 문제를 해결하기 위해 버퍼를 사용하는 다른 클래스와 같이 쓰는 경우가 많음
				*/
				out = new BufferedOutputStream(response.getOutputStream());
				
				try {
					byte[] buffer = new byte[4096];
				 	int bytesRead=0;
				 	
				 	/*
						모두 현재 파일 포인터 위치를 기준으로 함 (파일 포인터 앞의 내용은 없는 것처럼 작동)
						int read() : 1byte씩 내용을 읽어 정수로 반환
						int read(byte[] b) : 파일 내용을 한번에 모두 읽어서 배열에 저장
						int read(byte[] b. int off, int len) : 'len'길이만큼만 읽어서 배열의 'off'번째 위치부터 저장
				 	 */
				 	while ((bytesRead = in.read(buffer))!=-1) {
						out.write(buffer, 0, bytesRead);
					}
					
				 	// 버퍼에 남은 내용이 있다면, 모두 파일에 출력					
				 	out.flush();
				}
				finally {
					/*
						현재 열려 in,out 스트림을 닫음
						메모리 누수를 방지하고 다른 곳에서 리소스 사용이 가능하게 만듬
					 */
					in.close();
					out.close();
				}
		    } else {
		    	throw new FileNotFoundException("Empty FileData.");
		    }
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}
	
	@ResponseBody
	@PostMapping("/file/delete.ajax")
	public HashMap<String, Integer> updateDeleteYn(Model model, BoardFileRequestDto boardFileRequestDto) throws Exception {
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		try {
			int result = boardFileService.updateDeleteYn(boardFileRequestDto.getIdArr());
			model.addAttribute("result", result);
			map.put("result", result);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		
		return map;
	}
}