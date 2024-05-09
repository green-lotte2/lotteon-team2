package kr.co.lotteon.controller;

import kr.co.lotteon.dto.MyHomeDTO;
import kr.co.lotteon.dto.OrdersDTO;
import kr.co.lotteon.dto.QnaDTO;
import kr.co.lotteon.dto.UserDTO;
import kr.co.lotteon.entity.Orders;
import kr.co.lotteon.entity.User;
import kr.co.lotteon.security.MyUserDetails;
import kr.co.lotteon.service.CsService;
import kr.co.lotteon.service.MyService;
import kr.co.lotteon.service.OrdersService;
import kr.co.lotteon.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MyController {

    private final UserService userService;
    private final CsService csService;
    private final MyService myService;
    private final OrdersService ordersService;
    private final AuthenticationManager authenticationManager;

    @GetMapping(value = {"/mypage/","/mypage/home"})
    public String myPage(Model model, @AuthenticationPrincipal MyUserDetails myUserDetails){

        MyHomeDTO myHomeDTO = myService.getMyHomeInfo(myUserDetails.getUser().getUid());

        model.addAttribute("myHomeDTO", myHomeDTO);

        return "/mypage/home";
    }



    @GetMapping("/mypage/point")
    public String point(Model model, Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return "redirect:/member/login"; // 사용자가 로그인하지 않았다면 로그인 페이지로 리다이렉트
        }
        List<OrdersDTO> point = ordersService.selectPoint(authentication.getName());
        log.info("오늘은 이거다"+point);
        model.addAttribute("point", point);
        return "/mypage/point";
    }

    @GetMapping("/mypage/coupon")
    public String coupon() {
        return "/mypage/coupon";
    }

    @RequestMapping("/mypage/qna")
    public String qna(@AuthenticationPrincipal MyUserDetails myUserDetails,
                      @RequestParam(name="pg", defaultValue = "1") String pg,
                      Model model){
        if(myUserDetails == null) return "redirect:/index";

        String uid = myUserDetails.getUser().getUid();
        log.info("qna uid : " + uid);
        log.info("qna pg : " + pg);

        // 현재 페이지 번호
        int currentPage = csService.getCurrentPage(pg);
        log.info("currentPage : " + currentPage);

        // 시작 인덱스
        int start = csService.getStartNum(currentPage);
        log.info("start : " + start);

        // 전체 게시물 갯수
        int total;
        total = Integer.parseInt((csService.selectMyQnaTotal(uid)));
        log.info("qnaBoardTotal : " + total);

        List<QnaDTO> qnaDTOList = csService.selectMyQnaBoard(uid, start);
        log.info("qnaDTO : " + qnaDTOList.toString());

        // 마지막 페이지 번호
        int lastPageNum = csService.getLastPageNum(total);

        // 페이지 그룹 (현재 번호, 마지막 번호)
        int[] result = csService.getPageGroupNum(currentPage, lastPageNum);

        //페이지 시작번호
        int pageStartNum = csService.getPageStartNum(total, currentPage);

        model.addAttribute("qnaDTO", qnaDTOList);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("lastPageNum", lastPageNum);
        model.addAttribute("pageGroupStart", result[0]);
        model.addAttribute("pageGroupEnd", result[1]);
        model.addAttribute("pageStartNum", pageStartNum+1);

        return "/mypage/qna";
    }

    @ResponseBody
    @GetMapping("/qna/comment")
    public List<QnaDTO> commentBoard(@RequestParam("qnano") int qnano){
        log.info("commentBoard qnano : " + qnano);
        List<QnaDTO> commentBoard = csService.selectCsQnaComment(qnano);
        log.info("commentBoard : " + commentBoard.toString());
        return commentBoard;
    }

    @GetMapping("/mypage/infoAccessCheck")
    public String infoAccessCheck() {
        return "/mypage/infoAccessCheck";
    }

    @ResponseBody
    @PostMapping("/mypage/infoAccessCheck")
    public String infoAccessCheck(@RequestParam String uid, String inputPass) {

        Authentication authentication = new UsernamePasswordAuthenticationToken(uid, inputPass);
        Authentication result = authenticationManager.authenticate(authentication);

        if (result.isAuthenticated()) {
            return "true";
        } else {
            return "false";
        }
    }

    @GetMapping("/mypage/infoAccessCheckSeller")
    public String infoAccessCheckSeller() {
        return "/mypage/infoAccessCheckSeller";
    }

    @ResponseBody
    @PostMapping("/mypage/infoAccessCheckSeller")
    public String infoAccessCheckSeller(@RequestParam String uid, String inputPass) {

        Authentication authentication = new UsernamePasswordAuthenticationToken(uid, inputPass);
        Authentication result = authenticationManager.authenticate(authentication);

        if (result.isAuthenticated()) {
            return "true";
        } else {
            return "false";
        }
    }

    @GetMapping("/mypage/info")
    public String info() {
        return "/mypage/info";
    }
    @ResponseBody
    @PostMapping("/mypage/formMyinfoPassChange")
    public String formMyinfoPassChange(@RequestParam String uid, String inputPass) {
        userService.updatePass(uid, inputPass);
        return "success";
    }

    @ResponseBody
    @PostMapping("/mypage/withdraw")
    public String withdraw(@RequestParam String uid, String inputPass) {

        Authentication authentication = new UsernamePasswordAuthenticationToken(uid, inputPass);
        Authentication result = authenticationManager.authenticate(authentication);

        if (result.isAuthenticated()) {
            userService.updateWdate(uid);
            return "success";
        } else {
            return "fail";
        }
    }
    @ResponseBody
    @PostMapping("/mypage/withdrawFinal")
    public String withdrawFinal(@RequestBody UserDTO userDTO) {
        log.info("=========회원정보수정========== : "+userDTO);
        userService.updateUser(userDTO);
        return "success";
    }

    @GetMapping("/mypage/infoSeller")
    public String infoSeller(Model model, Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return "redirect:/member/login"; // 사용자가 로그인하지 않았다면 로그인 페이지로 리다이렉트
        }
        UserDTO sellerInfo = userService.selectSeller(authentication.getName());
        model.addAttribute("sellerInfo", sellerInfo);
        return "/mypage/infoSeller";
    }

}