package kr.co.lotteon.controller.cs;

import kr.co.lotteon.dto.Cate1DTO;
import kr.co.lotteon.dto.Cate2DTO;
import kr.co.lotteon.service.CsCateService;
import kr.co.lotteon.service.CsService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping("/cs")
@Log4j2
@RestController
public class CsCateController {

    @Autowired
    private CsCateService csCateService;

    @Autowired
    private CsService csService;

    @GetMapping("/getCsCates")
    public Map<String, Object> getCsCates(){
        return csCateService.getCsCates();
    }

    @ResponseBody
    @GetMapping("/cate1")
    public List<Cate1DTO> list(){
        List<Cate1DTO> cate1list = csService.selectCate1();
        log.info("listcate1list"+cate1list);
        return cate1list;
    }

    @ResponseBody
    @GetMapping("/cate2")
    public List<Cate2DTO> cate2list(@RequestParam("cate1") int cate1) {
        log.info("cate2cate2cate2???" + cate1);
        List<Cate2DTO> cate2list = csService.selectCate2(cate1);
        return cate2list;
    }
}
