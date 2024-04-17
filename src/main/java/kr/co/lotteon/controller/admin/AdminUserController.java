package kr.co.lotteon.controller.admin;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class AdminUserController {

    @GetMapping("/admin/user/register")
    public String adminUserRegister(){
        return "/admin/user/register";
    }

    @GetMapping("/admin/user/list")
    public String adminUserList(Model model, String role, String keyword){
        log.info("role : " + role);

        return "/admin/user/list";
    }

    @GetMapping("/admin/user/modify")
    public String adminUserModify(){
        return "/admin/user/modify";
    }
}
