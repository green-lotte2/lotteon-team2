package kr.co.lotteon.controller;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kr.co.lotteon.dto.TermsDTO;
import kr.co.lotteon.dto.UserDTO;
import kr.co.lotteon.service.TermsService;
import kr.co.lotteon.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Log4j2
@RequestMapping("member")
@Controller
@AllArgsConstructor
public class UserController {

    private final TermsService termsService;
    private final UserService userService;

    //약관페이지
    @GetMapping("/terms")
    public String terms(Model model, String type){

        TermsDTO termsResult = termsService.selectTerms();

        model.addAttribute("type", type);
        model.addAttribute("termsResult", termsResult);

        return "/member/terms";
    }

    @PostMapping("/terms")
    public String terms(HttpSession session, TermsDTO termsDTO) {
        log.info("here...1 : " + termsDTO);

        String type = termsDTO.getType();

        // 위치정보이용약관 상태값 저장
        session.setAttribute("location", termsDTO.locationYesNo());

        if (type.equals("seller")) {
            return "redirect:/member/registerSeller";
        } else {
            return "redirect:/member/register";
        }
    }

    //회원 가입 페이지
    @GetMapping("/join")
    public String join(){
        return "/member/join";
    }


    //로그인 페이지
    @GetMapping("/login")
    public String login(){
        return "/member/login";
    }

    //일반회원 가입 폼페이지
    @GetMapping("/register")
    public String registerForm(){
        return "/member/register";
    }


    //일반회원 가입 처리
    @PostMapping("/register")
    public String register(HttpServletRequest req, UserDTO userDTO) {

        // 위치정보이용약관 상태값 가져오기
        String location = (String) req.getSession().getAttribute("location");

        // 사용자 아이피 구하기
        String regip = req.getRemoteAddr();

        userDTO.setRegip(regip);
        userDTO.setLocation(location);

        log.info(userDTO);

        userService.insertUser(userDTO);


        return "redirect:/member/login";
    }

    //판매자회원 가입 폼페이지
    @GetMapping("/registerSeller")
    public String registerSellerForm(){
        return "/member/registerSeller";
    }

    // 판매자 회원 가입 처리
    @PostMapping("/registerSeller")
    public String registerSeller(HttpServletRequest request) {
        boolean agree1 = Boolean.parseBoolean(request.getParameter("agree1"));
        boolean agree2 = Boolean.parseBoolean(request.getParameter("agree2"));
        boolean agree3 = Boolean.parseBoolean(request.getParameter("agree3"));

        if (agree1 && agree2 && agree3) {



            return "redirect:/member/login";
        } else {
            return "redirect:/member/registerSeller";
        }
    }


    @ResponseBody
    @GetMapping("/checkUid")
    public Map<String, Integer> checkUid(String uid) {
        log.info("uid : " + uid);

        // 아이디 존재 유무 확인 서비스 호출
        boolean exists = userService.existsById(uid);
        int result = exists ? 1 : 0;

        // JSON 출력
        Map<String, Integer> resultMap = new HashMap<>();
        resultMap.put("result", result);

        return resultMap;



    }

    @ResponseBody
    @GetMapping("/checkEmail")
    public Map<String, Integer> checkEmail(String email) {
        log.info("email: " + email);

        // 이메일 중복 여부 확인 서비스 호출
        boolean exists = userService.existsByEmail(email);
        int result = exists ? 1 : 0;

        // JSON 출력
        Map<String, Integer> resultMap = new HashMap<>();
        resultMap.put("result", result);

        return resultMap;
    }

    @ResponseBody
    @GetMapping("/checkHp")
    public Map<String, Integer> checkHp(String hp) {
        log.info("hp: " + hp);

        // 휴대폰 번호 중복 여부 확인 서비스 호출
        boolean exists = userService.existsByHp(hp);
        int result = exists ? 1 : 0;

        // JSON 출력
        Map<String, Integer> resultMap = new HashMap<>();
        resultMap.put("result", result);

        return resultMap;
    }





}