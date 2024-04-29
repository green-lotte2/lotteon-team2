package kr.co.lotteon.controller.admin;

import kr.co.lotteon.dto.BannerDTO;
import kr.co.lotteon.dto.NoticeDTO;
import kr.co.lotteon.dto.QnaDTO;
import kr.co.lotteon.entity.Banner;
import kr.co.lotteon.service.AdminService;
import kr.co.lotteon.service.CsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Slf4j
@Controller
public class AdminConfigController {

    @Autowired
    CsService csService;

    @Autowired
    AdminService adminService;

    @GetMapping(value = {"/admin", "/admin/index"})
    public String index(@RequestParam(name="pg", defaultValue = "1") String pg,
                        @RequestParam(name="cate1", required = false) String cate1, Model model){

        // 현재 페이지 번호
        int currentPage = csService.getCurrentPage(pg);
        log.info("currentPage : " + currentPage);

        // 시작 인덱스
        int start = csService.getStartNum(currentPage);
        log.info("start : " + start);

        // 전체 게시물 갯수
        int total;
        List<NoticeDTO> noticeDTOS;


        if(cate1 == null || cate1.isEmpty()){
            log.info("notice1");
            total = csService.selectNoticeTotal();
            noticeDTOS = csService.selectNoticeListAll(start);
        }else {
            log.info("notice2");
            log.info("notice2 cate1 : " + cate1);
            total = csService.selectNoticeTotalCate(Integer.parseInt(cate1));

            log.info("notice2 total : " + total);

            noticeDTOS = csService.selectNoticeListCate(Integer.parseInt(cate1), start);
        }

        // 마지막 페이지 번호
        int lastPageNum = csService.getLastPageNum(total);

        // 페이지 그룹 (현재 번호, 마지막 번호)
        int[] result = csService.getPageGroupNum(currentPage, lastPageNum);

        // 페이지 시작 번호
        int pageStartNum = csService.getPageStartNum(total, currentPage);

        model.addAttribute("noticeDTOS", noticeDTOS);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("lastPageNum", lastPageNum);
        model.addAttribute("pageGroupStart", result[0]);
        model.addAttribute("pageGroupEnd", result[1]);
        model.addAttribute("pageStartNum", pageStartNum+1);
        model.addAttribute("cate1", cate1);
        log.info("currentPage: " + currentPage);
        log.info("lastPage: " + lastPageNum);
        log.info("cate1last : " + cate1);

        List<QnaDTO> qnaDTOS = csService.adminSelectQnaList();
        model.addAttribute("qnaDTOS", qnaDTOS);



        log.info("index.. ");
        return "/admin/index";
    }


    @GetMapping("/admin/error/404")
    public String error(){
        return "/admin/error/404";
    }


    @GetMapping("/admin/config/banner")
    public String banner(){
        return "/admin/config/banner";
    }

    // 🎈banner 등록
   /* @PostMapping("/admin/config/banner")
    public List<BannerDTO> banner(BannerDTO bannerDTO){

        return adminService.insertBanner();
    }*/




    @GetMapping("/admin/config/info")
    public String info(){

        return "/admin/config/info";
    }

}
