package com.ezotex.comm.web;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ezotex.comm.dto.UserInfoDto;
import com.ezotex.standard.dto.AddressListDTO;
import com.ezotex.standard.dto.CompanyDTO;
import com.ezotex.standard.dto.DeptDTO;
import com.ezotex.standard.dto.EmpDTO;
import com.ezotex.standard.dto.PositionDTO;
import com.ezotex.standard.dto.ResetPasswordDTO;
import com.ezotex.standard.service.impl.StandardServiceImpl;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/login/*")
public class CommonController {

	final StandardServiceImpl service;
	
    // 로그인 메인 페이지
	@GetMapping("/main")
	public String login() {
		return "login/login";
	}

    // 회원가입 메인 페이지
	@GetMapping("/register_main")
	public String register_main() {
		return "/login/register_main";
	}

    // 공급업체 회원가입 페이지
	@GetMapping("/register_supply")
	public String register_supply() {
		return "/login/register_supply";
	}

	// 공급업체 회원가입 페이지
	@GetMapping("/register_emp")
	public String register_emp() {
		return "/login/register_emp";
	}
	
	// 로그인 중복 확인 메세지
	@ResponseBody
	@GetMapping("/searchId")
	public int searchId(@RequestParam(name="id") String id) {
		return service.searchId(id);
	}
	
	// 로그인 세션 정보 넘겨주기
	@ResponseBody
	@GetMapping("/userInfo")
	public UserInfoDto userInfo(HttpSession session) {
		UserInfoDto userInfo = new UserInfoDto();
		userInfo.setCode((String)session.getAttribute("code"));
		userInfo.setId((String)session.getAttribute("id"));
		userInfo.setName((String)session.getAttribute("name"));
		userInfo.setEmail((String)session.getAttribute("email"));
		userInfo.setImg((String)session.getAttribute("img"));
		
		return userInfo;
	}
	
	// 로그인 실패 세션 확인하기
	@GetMapping("/login_fail")
	public String Login_fail(RedirectAttributes attr) {
		attr.addFlashAttribute("login_fail", true);
		
		return "redirect:/login/main";
	}
	
	// dept 리스트
	@ResponseBody
	@GetMapping("/deptList")
	public List<DeptDTO> deptList() {
		return service.deptList();
	}
	
	// position 리스트
	@ResponseBody
	@GetMapping("/positionList")
	public List<PositionDTO> positionList() {
		return service.positionList();
	}
	
	// emp 회원 가입
	@PostMapping("/emp_submit")
	public String emp_submit(EmpDTO empDTO, RedirectAttributes attr) throws Exception {
		MultipartFile file = empDTO.getEmpImgFile();
		UUID uuid = UUID.randomUUID();
		
		String uuidFileName = uuid + "_" + file.getOriginalFilename();
		file.transferTo(new File("c:\\images\\" + uuidFileName));
		empDTO.setEmpImg(uuidFileName);
		
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(16);
		String result = encoder.encode(empDTO.getEmpPassword());
		empDTO.setEmpPassword(result);
		
		AddressListDTO addDTO = new AddressListDTO();
		addDTO.setAddressNumber(empDTO.getAddressNumber());
		addDTO.setAddressMain(empDTO.getAddressMain());
		addDTO.setAddressInfo(empDTO.getAddressInfo());
		addDTO.setAddressReference(empDTO.getAddressReference());
		
		service.insertEmp(empDTO, addDTO);
		attr.addFlashAttribute("result", true);
		
		return "redirect:/login/main";
	}
	
	@Value("${file_img}")
	String file_img;
	
	// company 회원 가입
	@PostMapping("/company_submit")
	public String company_submit(CompanyDTO companyDTO, RedirectAttributes attr) throws Exception {
		MultipartFile file = companyDTO.getCompanyImgFile();
		UUID uuid = UUID.randomUUID();
		
		String uuidFileName = uuid + "_" + file.getOriginalFilename();
		file.transferTo(new File(file_img + uuidFileName));
		companyDTO.setCompanyImg(uuidFileName);
		
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(16);
		String result = encoder.encode(companyDTO.getCompanyPassword());
		companyDTO.setCompanyPassword(result);
		
		companyDTO.setSection("CP01");
		
		AddressListDTO addDTO = new AddressListDTO();
		addDTO.setAddressNumber(companyDTO.getAddressNumber());
		addDTO.setAddressMain(companyDTO.getAddressMain());
		addDTO.setAddressInfo(companyDTO.getAddressInfo());
		addDTO.setAddressReference(companyDTO.getAddressReference());
		
		
		service.insertCompany(companyDTO, addDTO);
		
		attr.addFlashAttribute("result", true);
		
		return "redirect:/login/main";
	}
	
	// 인증 여부 확인
	@ResponseBody
	@GetMapping("/idApproval")
	public String idApproval(@RequestParam(name="id") String id) {
		return "{\"approval\" : \"" + service.idApproval(id) + "\"}";
	}
	
	// 비밀번호 재설정
	@GetMapping("/password_reset")
	public String password_reset() {
		return "/login/password_reset";
	}
	
	// 번경 메일 전송
	@Autowired
	private EmailService emailService;
	
	@PostMapping("/password_reset")
	public String password_reset_send(ResetPasswordDTO resetPasswordDTO, RedirectAttributes attr) {
		ResetPasswordDTO search_user_info = service.findNameEmail(resetPasswordDTO.getId());

		if (resetPasswordDTO.equals(search_user_info)) {
			// 이메일 전송
			int random_num = 0;
			String new_password = "";
			while (random_num < 10000000) {
				random_num = (int)(Math.random() * 100000000);
			}
			new_password = Integer.toString(random_num);
			String content = "비밀번호가 " + new_password + " 로 변경되었습니다.";
			System.out.println(content);
			emailService.sendEmail(search_user_info.getEmail(), "EzoTex 비밀번호 초기화 안내", content);
			
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(16);
			String result = encoder.encode(new_password);
			
			int update_result = service.passwordUpdate(search_user_info.getId(), result);
			if (update_result == 1) {
				attr.addFlashAttribute("reset", true);
			
				return "redirect:/login/main";
			} else {
				attr.addFlashAttribute("reset", true);
				
				return "redirect:/login/password_reset";
			}
		} else {
			attr.addFlashAttribute("reset", true);
			
			return "redirect:/login/password_reset";
		}
		
	}
}
