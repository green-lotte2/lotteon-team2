package kr.co.lotteon.service;

import kr.co.lotteon.dto.*;
import kr.co.lotteon.entity.Banner;
import kr.co.lotteon.entity.Product;
import kr.co.lotteon.entity.Productimg;
import kr.co.lotteon.mapper.AdminMapper;
import kr.co.lotteon.mapper.ProductMapper;
import kr.co.lotteon.repository.BannerRepository;
import kr.co.lotteon.repository.ProductimgRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminService {

    private final AdminMapper adminMapper;
    private final BannerRepository bannerRepository;
    private final ProductimgRepository productimgRepository;
    private final ProductMapper productMapper;

    public List<UserDTO> selectUsers(){
        log.info("selectUsers... ");
        return adminMapper.adminSelectUsers();
    }

    public TypePageResponseDTO selectProductsBySearch(PageRequestDTO pageRequestDTO) {
        List<ProductDTO> productDTOS = adminMapper.selectProductsBySearch(pageRequestDTO);
        return new TypePageResponseDTO(pageRequestDTO,productDTOS.get(0).getTotal(),productDTOS);
    }

    public List<ProductDTO> selectProducts(){
        log.info("selectProducts...");
        return  adminMapper.adminSelectProducts();
    }

    public void adminDeleteProduct(int pno){
        adminMapper.adminDeleteProduct(pno);
    }


    @Value("${img.upload.path}")
    private String imgUploadPath;

    public void adminDeleteProductImg(int pno, int cate){
        Optional<Productimg> optionalProductimg = productimgRepository.findById(pno);
        if(optionalProductimg.isPresent()){
            Productimg productimg = optionalProductimg.get();

            List<String> delImgList = new ArrayList<>();
            delImgList.add(productimg.getMainimg());
            delImgList.add(productimg.getDetailimg());

            String path = new File(imgUploadPath + "/" + cate + "/").getAbsolutePath();



            for(String img : delImgList){
                File delFile = new File(path + File.separator + img);
                log.info(delFile.toString());
                if(delFile.exists()){
                    if(delFile.delete()){
                        log.info("파일 삭제 완료");
                    }else{
                        log.error("파일 삭제 실패");
                    }
                }else{
                    log.warn("파일이 존재하지 않습니다.");
                }
            }
        }
        productimgRepository.deleteById(pno);
    }

    // 🎈배너 등록
    private final ModelMapper modelMapper;

    public Banner insertBanner(BannerDTO bannerDTO) {
        Banner banner = modelMapper.map(bannerDTO,Banner.class);
        Banner savedBanner = bannerRepository.save(banner);
        log.info("savedBanner : " + savedBanner);

        return savedBanner;
    }

    // 🎈 배너 리스트
    public List<BannerDTO> selectBanner() {
        return adminMapper.selectBanner();
    }

    // 🎈 배너 삭제
    public void deleteBanner(int bno){
        adminMapper.deleteBanner(bno);
    }

    // 🎈 배너 활성화
 /*   public void activateBanner(int bno) {
        Optional<Banner> optionalBanner = bannerRepository.findById(bno);
        if(optionalBanner.isPresent()) {
            Banner banner = optionalBanner.get();
            if(isBannerValid(banner)){
            //    banner.setBmanage(1);
                bannerRepository.save(banner);
            }
        }

    }*/

    /*
    private boolean isBannerValid(Banner banner) {
        LocalDate now = LocalDate.now();
        LocalDate startDate = banner.getBstartDate();
        LocalDate endDate = banner.getBendDate();
        LocalTime startTime = banner.getBstartTime();
        LocalTime endTime = banner.getBendTime();

    return now.isAfter(startDate) && now.isBefore(endDate)&&
            now.isAfter(startTime) && now.isBefore(endTime);
    }

     */

}












