package kr.co.lotteon.controller;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kr.co.lotteon.dto.BannerDTO;
import kr.co.lotteon.dto.TermsDTO;
import kr.co.lotteon.dto.UserDTO;
import kr.co.lotteon.service.AdminService;
import kr.co.lotteon.service.TermsService;
import kr.co.lotteon.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j2
@RequestMapping("member")
@Controller
@AllArgsConstructor
public class UserController {

    private final TermsService termsService;
    private final UserService userService;
    private final AdminService adminService;

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
    public String loginForm(Model model){
        List<BannerDTO> banners = adminService.selectBanner();
        model.addAttribute("banners", banners);
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
    public String registerSeller(HttpServletRequest request, UserDTO userDTO) {


            // 사용자 아이피 구하기
            String regip = request.getRemoteAddr();

            userDTO.setRegip(regip);

            log.info(userDTO);

            userService.insertSeller(userDTO);


            return "redirect:/member/login";

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
    @GetMapping("/checkEmail/{value}")
    public ResponseEntity<?> checkEmail(HttpSession session, @PathVariable("value") String value){

        boolean isExist = userService.existsByEmail(value);
        log.info("isExist : " + isExist);

        // 중복 없으면 이메일 인증코드 발송
        if(!isExist){
            log.info("email : " + value);
            userService.sendEmailCode(session, value);
        }

        // Json 생성
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("result", isExist);

        return ResponseEntity.ok().body(resultMap);
    }

    @ResponseBody
    @GetMapping("/checkFindEmail/{value}")
    public ResponseEntity<?> checkFindEmail(HttpSession session, @PathVariable("value") String value){

        boolean isExist = userService.existsByEmail(value);
        log.info("isExist : " + isExist);

        // 중복 있으면 이메일 인증코드 발송
        if(isExist){
            log.info("email : " + value);
            userService.sendEmailCode(session, value);
        }

        // Json 생성
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("result", isExist);

        return ResponseEntity.ok().body(resultMap);
    }


    @ResponseBody
    @GetMapping("/checkEmailCode/{value}")
    public Map<String, Boolean> checkEmailCode(@PathVariable("value") String code, HttpSession session) {
        String sessionCode = (String) session.getAttribute("code");


        log.info("sessionCode : " + sessionCode);
        log.info("code : " + code);

        // 세션 코드와 입력된 코드 비교
        boolean result = sessionCode != null && sessionCode.equals(code);
        log.info("result : " + result);

        // JSON 출력
        Map<String, Boolean> resultMap = new HashMap<>();
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

    @ResponseBody
    @GetMapping("/checkCohp")
    public Map<String, Integer> checkCohp(String cohp) {
        log.info("cohp: " + cohp);

        boolean exists = userService.existsByCohp(cohp);
        int result = exists ? 1 : 0;

        // JSON 출력
        Map<String, Integer> resultMap = new HashMap<>();
        resultMap.put("result", result);

        return resultMap;
    }

    @ResponseBody
    @GetMapping("/checkRegnum")
    public Map<String, Integer> checkRegnum(String regnum) {
        log.info("regnum: " + regnum);

        boolean exists = userService.existsByRegnum(regnum);
        int result = exists ? 1 : 0;

        // JSON 출력
        Map<String, Integer> resultMap = new HashMap<>();
        resultMap.put("result", result);

        return resultMap;
    }

    @ResponseBody
    @GetMapping("/checkReportnum")
    public Map<String, Integer> checkReportnum(String reportnum) {
        log.info("reportnum: " + reportnum);

        boolean exists = userService.existsByReportnum(reportnum);
        int result = exists ? 1 : 0;

        // JSON 출력
        Map<String, Integer> resultMap = new HashMap<>();
        resultMap.put("result", result);

        return resultMap;
    }

    @ResponseBody
    @GetMapping("/checkFax")
    public Map<String, Integer> checkFax(String fax) {
        log.info("fax: " + fax);

        boolean exists = userService.existsByFax(fax);
        int result = exists ? 1 : 0;

        // JSON 출력
        Map<String, Integer> resultMap = new HashMap<>();
        resultMap.put("result", result);

        return resultMap;
    }

    @GetMapping("/findId")
    public String findId(){
        return "/member/findId";
    }
    @PostMapping("/findId")
    public ResponseEntity<UserDTO> findId(@RequestBody UserDTO userDTO){
        String name =userDTO.getName();
        log.info("findId...... :" + name);
        String email =userDTO.getEmail();
        UserDTO foundUserDTO = userService.findByNameAndEmail(name, email);

        if (foundUserDTO != null) {
            return ResponseEntity.ok(foundUserDTO);
            // 사용자를 찾은 경우 200 OK 응답으로 사용자 정보 반환
        } else {
            return ResponseEntity.notFound().build();
            // 사용자를 찾지 못한 경우 404 Not Found 응답 반환
        }
    }

    @PostMapping("/findIdResult")
    public String findIdResult(String name, String email, Model model){
        UserDTO userDTO = userService.findByNameAndEmail(name, email);
        log.info("result....:" + userDTO.toString());
        model.addAttribute("userDTO" , userDTO);
        log.info(userDTO.toString());

        return "/member/findIdResult";
    }

    @GetMapping("/findPassword")
    public String findPassword(){
        return "/member/findPassword";
    }

    @PostMapping("/findPassword")
    public ResponseEntity<UserDTO> findPassword(@RequestBody UserDTO userDTO){

        String uid = userDTO.getUid();
        String email = userDTO.getEmail();
        log.info("findPass.....uid: " + uid);
        log.info("findPass.....email: " + email);
        UserDTO foundUserDTO = userService.findPassword(uid, email);
        log.info("findPass...... :" + foundUserDTO);

        if (foundUserDTO != null) {
            return ResponseEntity.ok(foundUserDTO);
            // 사용자를 찾은 경우 200 OK 응답으로 사용자 정보 반환
        } else {
            return ResponseEntity.notFound().build();
            // 사용자를 찾지 못한 경우 404 Not Found 응답 반환
        }

    }

    @PostMapping("/findPasswordChange")
    public String findPasswordChange(String uid, String email, Model model){
        UserDTO userDTO = userService.findPassword(uid, email);
        log.info("result....:" + userDTO.toString());
        model.addAttribute("userDTO" , userDTO);
        log.info(userDTO.toString());

        return "/member/findPasswordChange";
    }

    @PutMapping("/updatePass")
    public ResponseEntity<?> putPass(@RequestBody UserDTO userDTO, HttpServletRequest req){
        return userService.updateUserPass(userDTO);
    }



}