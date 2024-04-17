package kr.co.lotteon.controller;



import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MainController {

    @GetMapping(value = {"/","/index"})
    public String index(Model model){

        return "/index";
    }

}
