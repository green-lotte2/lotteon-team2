package kr.co.lotteon.service;

import kr.co.lotteon.dto.BannerDTO;
import kr.co.lotteon.dto.ProductDTO;
import kr.co.lotteon.entity.Banner;
import kr.co.lotteon.entity.Product;
import kr.co.lotteon.mapper.AdminMapper;
import kr.co.lotteon.repository.BannerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import kr.co.lotteon.dto.UserDTO;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminService {

    private final AdminMapper adminMapper;
    private final BannerRepository bannerRepository;

    public List<UserDTO> selectUsers(){
        log.info("selectUsers... ");
        return adminMapper.adminSelectUsers();
    }

    public List<ProductDTO> selectProducts(){
        log.info("selectProducts...");
        return  adminMapper.adminSelectProducts();
    }

    public void adminDeleteProduct(int pno){
        adminMapper.adminDeleteProduct(pno);
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












