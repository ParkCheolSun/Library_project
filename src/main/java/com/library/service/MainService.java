package com.library.service;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.library.constant.LibCode;
import com.library.dto.BookDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MainService {
	private final List<BookDto> bookDtoList;
	private final List<BookDto> bookDtoListToLib;
	private String key = "af2df55209c61c0726e45405de83247e14d2474c9c0bca948854d5495b6465a1";
	private String region = "24"; 			// 광주
	private String dtl_region = "24030";	// 남구
	private RestTemplate restTemplate;
	
	public List<BookDto> popularityBook() {
		String oriUrl = "http://data4library.kr/api/loanItemSrch";
		String pageSize = "10";
		String kdc = "9";
		String url = oriUrl + "?authKey="+ key +"&startDt=2022-01-01&endDt=2022-11-21&region="+region+"&dtl_region="+dtl_region + "&pageNo=1&pageSize=" + pageSize +
				"&kdc=" + kdc;
		
		// Spring boot에서 제공하는 RestTemplate
        restTemplate = new RestTemplate();
        
        // 1. api호출하여 결과를 가져오기
        // 대부분의 api는 get형태가 많다 = 정보를 가져오거나 받아오는 형태
        // RestTemplate.getForObject(URI url, Class<T> responseType) => (호출하는 url, 반환타입)
        String response = restTemplate.getForObject(url, String.class);
        
        // XML을 JSON Object로 변환하기
        JSONObject jobj = XML.toJSONObject(response);
        
        JSONObject jobj1 = jobj.getJSONObject("response").getJSONObject("docs");
        
        JSONArray jarr = jobj1.getJSONArray("doc");
        
        if(!bookDtoList.isEmpty()) {
        	bookDtoList.clear();
        }
        
        for (int i = 1; i < jarr.length(); i++) {
            String bookname = jarr.getJSONObject(i).getString("bookname");
            String authors = jarr.getJSONObject(i).getString("authors");
            String bookImageURL = jarr.getJSONObject(i).getString("bookImageURL");
            String isbn13 = jarr.getJSONObject(i).getString("isbn13");
            BookDto book = new BookDto(0L, bookname, authors, isbn13, bookImageURL);
            bookDtoList.add(book);
        }
        
        return bookDtoList;
	}
	
}
