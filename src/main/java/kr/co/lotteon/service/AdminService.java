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

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminService {

    private final AdminMapper adminMapper;

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
    private final BannerRepository bannerRepository;
    private final ModelMapper modelMapper;

    public Banner insertBanner(BannerDTO bannerDTO) {
        Banner banner = modelMapper.map(bannerDTO,Banner.class);
        Banner savedBanner = bannerRepository.save(banner);
        log.info("savedBanner : " + savedBanner);

        return savedBanner;
    }



}
