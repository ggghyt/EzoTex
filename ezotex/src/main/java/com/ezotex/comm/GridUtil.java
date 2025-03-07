package com.ezotex.comm;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class GridUtil {

	
	public static Map<String, Object> grid(int page, int total, List list) {
		//제이슨 형식의 문자열 선언
		String str = """
								{
				  "result": true,
				  "data": {
				    "contents": [],
				    "pagination": {
				      "page": 1,
				      "totalCount": 100
				    }
				  }
				}
								""";
		ObjectMapper objectMapper = new ObjectMapper();
		
		//데이터를 넣을 map생성
		Map<String, Object> map = null;
		
		try {
			//제이슨 형식의 문자열을 객체로 만듦
			map = objectMapper.readValue(str, Map.class);
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		//만든 객체의 "data"속성에 데이터를 넣기 위한 맵을 생성
		Map<String, Object> data = (Map) map.get("data");
		Map<String, Object> pagination = (Map) data.get("pagination");

		//각자의 위치에 맞는 데이터를 넣고, 화면으로 보내게 됨. tui grid에서는 이 값을 받아서 바로 페이징 + 화면에 데이터 출력 가능
		pagination.put("page", page);
		pagination.put("totalCount", total);
		data.put("contents", list);
		return map;
	}
	
}
