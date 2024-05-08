package kr.co.lotteon.controller.admin;


import jakarta.servlet.http.HttpServletRequest;
import kr.co.lotteon.dto.PageRequestDTO;
import kr.co.lotteon.service.AdminService;
import kr.co.lotteon.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import kr.co.lotteon.dto.UserDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class AdminUserController {

    private final AdminService adminService;

    @GetMapping("/admin/user/register")
    public String adminUserRegister(){
        return "/admin/user/register";
    }


    @GetMapping("/admin/user/list")
    public String adminUserList(String uid, PageRequestDTO pageRequestDTO){

        log.info("uid" +uid);
        adminService.adminSelectUsers(uid, pageRequestDTO);
        return "/admin/user/list";
    }


    // 🎈 user 수정
    @GetMapping("/admin/user/modify")
    public String adminUserSelect(@RequestParam String uid, Model model){
        UserDTO user = adminService.adminUserSelect(uid);
        user.setRegDate(user.getRegDate());
        model.addAttribute("user", user);
        log.info("user : " + user);
        return "/admin/user/modify";
    }

    @PostMapping("/admin/user/modify")
    public String adminUserUpdate(HttpServletRequest req, UserDTO userDTO){
        // 사용자 아이피 구하기
        String regip = req.getRemoteAddr();
        userDTO.setRegip(regip);

        UserDTO user = adminService.adminUserUpdate(userDTO);
        log.info("userUpdate : " + user);


        return "redirect:/admin/user/list";
    }



    // 🎈 user 삭제
    @PostMapping("/admin/user/delete")
    public String adminDeleteUser(@RequestParam List<String> checkbox){
        for(String uid : checkbox){
            adminService.adminDeleteUser(uid);
            log.info("deleteUid : " + uid);
        }
        log.info("deleteCheckBox : " + checkbox);

        return "redirect:/admin/user/list";
    }


}
