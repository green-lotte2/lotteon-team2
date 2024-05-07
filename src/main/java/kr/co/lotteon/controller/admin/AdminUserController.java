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
import org.springframework.web.bind.annotation.RequestParam;

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
    public String adminUserList(Model model){
        List<UserDTO> users = adminService.selectUsers();
        log.info("adminUserList ..." + users);
        model.addAttribute("users", users);
        return "/admin/user/list";
    }

    // 🎈 user 수정
    @GetMapping("/admin/user/modify")
    public String adminUserSelect(@RequestParam String uid, Model model){
        UserDTO user = adminService.adminUserSelect(uid);
        model.addAttribute("user", user);
        log.info("user : " + user);
        return "/admin/user/modify";
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
