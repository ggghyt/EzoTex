package com.ezotex.delivery.web;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ezotex.comm.GridUtil;
import com.ezotex.comm.dto.PagingDTO;
import com.ezotex.comm.dto.UserDto;
import com.ezotex.delivery.dto.DeliveryRegistSearchDTO;
import com.ezotex.delivery.dto.DriverDeliveryDTO;
import com.ezotex.delivery.dto.DriverDeliverySearchDTO;
import com.ezotex.delivery.service.DriverService;
import com.ezotex.standard.dto.AddressListDTO;
import com.ezotex.standard.dto.CompanyDTO;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping("/driver/*")
public class DriverRestController {
	
	private DriverService service;
	
	//로그인기능
	@PostMapping("login")
	public Map<String, Object> driverInfo(UserDto loginInfo) {
		//사원코드, 기사이름, 아이디
		
		return null;
	}
	
	@GetMapping("list")
	public Map<String, Object> deliveryList(@RequestParam(name = "perPage", defaultValue = "1", required = false) int perPage,
									            @RequestParam(name = "page", defaultValue = "1")int page,
									            DriverDeliverySearchDTO searchDTO) {
		
		log.info("=================================================");
		log.info(searchDTO.toString());
		
		PagingDTO paging = new PagingDTO();
		
		paging.setPageUnit(perPage);	//페이지당 최대 건수
		paging.setPage(page);			//현재 페이지

		// 페이징 조건
		searchDTO.setStart(paging.getFirst());	//시작
		searchDTO.setEnd(paging.getLast());		//끝번호
		
		paging.setTotalRecord(service.getDeliveryListCount(searchDTO));

		return GridUtil.grid(paging.getPage(), paging.getTotalRecord(), service.deliveryList(searchDTO));
	}
	
	//@Value("${file_img}")
	//String file_img;
	
	// 배송완료
	@GetMapping("/completeDelivery")
	public String completeDelivery(String deliveryCode) throws Exception {
		
		//log.info("=================================================");
		//log.info(insertInfo.toString());
		
		//MultipartFile file = insertInfo.getImgFile();
		//UUID uuid = UUID.randomUUID();
		
		//이미지 url + 파일 이름 변경(중복방지)
		//String uuidFileName = uuid + "_" + file.getOriginalFilename();
		//file.transferTo(new File("c:\\images\\" + uuidFileName));
		//insertInfo.setImgUrl(uuidFileName);
		
		
		
		//return service.insertDeliver(insertInfo);
		service.updateDeliveryState(deliveryCode);
		return "success";
	}
	
	@GetMapping("/boxList")
	public List<DriverDeliveryDTO> boxList(@RequestParam(name = "deliveryCode")String deliveryCode) {
		log.info("=================================================");
		log.info(deliveryCode);
		
		return service.boxList(deliveryCode);
	}
}
