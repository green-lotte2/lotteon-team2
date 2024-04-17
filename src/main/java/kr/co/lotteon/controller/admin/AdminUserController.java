package kr.co.lotteon.controller.admin;


import kr.co.lotteon.service.AdminService;
import kr.co.lotteon.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import kr.co.lotteon.dto.UserDTO;
import org.springframework.web.bind.annotation.PostMapping;

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

    /*
    @PostMapping("/admin/user/list")
    public String adminUserList(Model model, String uid, String role, String keyword){

        List<UserDTO> users = adminService.selectUsers(role, uid);
        model.getAttribute(role);
        model.getAttribute(uid);
        model.getAttribute(keyword);
        log.info("role : " + role);
        log.info("uid : " + uid);
        log.info("keyword : " + keyword);

        return "/admin/user/list";
    }*/

    @GetMapping("/admin/user/list")
    public String adminUserList(Model model){
        List<UserDTO> users = adminService.selectUsers();
        log.info("adminUserList ..." + users);
        model.addAttribute("users", users);
        return "/admin/user/list";
    }

    @GetMapping("/admin/user/modify")
    public String adminUserModify(){
        return "/admin/user/modify";
    }
}
