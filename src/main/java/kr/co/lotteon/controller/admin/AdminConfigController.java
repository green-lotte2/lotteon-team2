package kr.co.lotteon.controller.admin;

import kr.co.lotteon.dto.BannerDTO;
import kr.co.lotteon.dto.NoticeDTO;
import kr.co.lotteon.dto.QnaDTO;
import kr.co.lotteon.entity.Banner;
import kr.co.lotteon.service.AdminService;
import kr.co.lotteon.service.CsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class AdminConfigController {

    private final CsService csService;
    private final AdminService adminService;
    private final ModelMapper modelMapper;

    @GetMapping(value = {"/admin/", "/admin/index"})
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


    // 🎈banner 등록
    @PostMapping( "/admin/config/banner/register")
    public String banner(BannerDTO bannerDTO,
                         @RequestParam("file") MultipartFile file)  {
        bannerDTO.setBfile(String.valueOf(file));
        bannerDTO.setBmanage(Integer.toString(1));
        log.info("bfile : " + bannerDTO.getBfile());
        log.info("BannerDTO : " + bannerDTO);
        Banner savedBanner = adminService.insertBanner(bannerDTO);
        log.info("savedBanner" + savedBanner);


        BannerDTO savedBannerDTO = modelMapper.map(savedBanner, BannerDTO.class);
        log.info("savedBannerDTO : " + savedBannerDTO);
        return "redirect:/admin/config/bannerList";
    }

    // 🎈 banner 리스트
    @GetMapping("/admin/config/bannerList")
    public String selectBanner(Model model){
        List<BannerDTO> banners = adminService.selectBanner();
        model.addAttribute("banners", banners);
        log.info("banners: " + banners);
        return "/admin/config/bannerList";
    }

    // 🎈 banner 삭제
    @PostMapping("/admin/config/bannerDelete")
    public String deleteBanner(@RequestParam List<String> checkbox){
        for(String bno : checkbox){
            int bnoId = Integer.parseInt(bno);
            adminService.deleteBanner(bnoId);
            log.info("deleteBno : " + bnoId);
        }
        log.info("deleteCheckBox : " + checkbox);

        return "redirect:/admin/config/bannerList";
    }

    // 🎈 banner 활성
    @GetMapping("/admin/config/bannerList/active")
    public String activeBanner(Model model, int bno){
        model.addAttribute("bno", bno);
        log.info("activeBno: " + bno);
        return "redirect:/admin/config/bannerList";
    }

    // 🎈 banner 비활성
    @GetMapping("/admin/config/bannerList/deActivate")
    public String deActiveBanner(Model model, int bno){
        model.addAttribute("bno", bno);
        log.info("deActivateBno :" + bno);
        return "redirect:/admin/config/bannerList";
    }


    @GetMapping("/admin/config/info")
    public String info(){

        return "/admin/config/info";
    }

}
