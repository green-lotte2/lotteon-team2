package kr.co.lotteon.service;

import kr.co.lotteon.dto.*;
import kr.co.lotteon.entity.*;
import kr.co.lotteon.mapper.AdminMapper;
import kr.co.lotteon.mapper.ProductMapper;
import kr.co.lotteon.repository.BannerRepository;
import kr.co.lotteon.repository.ProductimgRepository;
import kr.co.lotteon.repository.UserDetailRepository;
import kr.co.lotteon.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminService {

    private final AdminMapper adminMapper;
    private final BannerRepository bannerRepository;
    private final ProductimgRepository productimgRepository;
    private final ProductMapper productMapper;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final UserDetailRepository userDetailRepository;

    public List<UserDTO> selectUsers(){
        log.info("selectUsers... ");
        return adminMapper.adminSelectUsers();
    }


    public TypePageResponseDTO selectOrderGroup(PageRequestDTO pageRequestDTO) {
        List<OrdersDTO> ordersDTOS = adminMapper.selectOrderGroup(pageRequestDTO);
        return new TypePageResponseDTO(pageRequestDTO, ordersDTOS.get(0).getLine(), ordersDTOS);
    }

    // 🎈 회원 수정
    public UserDTO adminUserSelect(String uid) {
        return adminMapper.adminUserSelect(uid);
    }


    @Transactional
    public UserDTO adminUserUpdate(UserDTO userDTO) {

        // 비밀번호 암호화
        userDTO.setPass(passwordEncoder.encode(userDTO.getPass()));

        // 기본권한
        userDTO.setGrade(1);

        User user = modelMapper.map(userDTO, User.class);
        UserDetail userDetail = modelMapper.map(userDTO, UserDetail.class);

        log.info(user.toString());
        log.info(userDetail.toString());

        userRepository.save(user);
        userDetailRepository.save(userDetail);


        return userDTO;
    }


    // 🎈 회원 삭제
    public void adminDeleteUser(String uid){
        adminMapper.adminDeleteUser(uid);
    }

    public TypePageResponseDTO selectOrders(PageRequestDTO pageRequestDTO) {
        List<OrdersDTO> ordersDTOS = adminMapper.selectOrders(pageRequestDTO);
        return new TypePageResponseDTO(pageRequestDTO,ordersDTOS.get(0).getLine(),ordersDTOS);
    }

    public TypePageResponseDTO selectProductsBySearch(PageRequestDTO pageRequestDTO) {
        List<ProductDTO> productDTOS = adminMapper.selectProductsBySearch(pageRequestDTO);
        return new TypePageResponseDTO(pageRequestDTO,productDTOS.get(0).getLine(),productDTOS);
    }

    // 기간별 주문량 조회
    public List<OrdersDTO> selectOrderByMonth() {
        return adminMapper.selectOrderByMonth();
    }

    public List<OrdersDTO> selectOrderByWeek() {
        return adminMapper.selectOrderByWeek();
    }

    public List<OrdersDTO> selectCountAndCateName(){
        return adminMapper.selectCountAndCateName();
    }

    public TypePageResponseDTO selectOrderByProduct(PageRequestDTO pageRequestDTO) {
        List<OrdersDTO> ordersDTOS = adminMapper.selectOrderByProduct(pageRequestDTO);
        return new TypePageResponseDTO(pageRequestDTO,ordersDTOS.get(0).getLine(),ordersDTOS);
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

    @Value("uploads/banner/")
    private String fileUploadPath;

    public Banner insertBanner(BannerDTO bannerDTO, MultipartFile file) {
        if (!file.isEmpty()) {
            try {
                // 파일 확장자 추출
                String oName = file.getOriginalFilename();
                String ext = oName.substring(oName.lastIndexOf("."));
                // 고유한 파일 이름 생성
                String sName = UUID.randomUUID().toString() + ext;

                log.info("oName :" + oName);
                log.info("sName :" + sName);

                // 파일을 저장할 경로 설정
                String path = fileUploadPath + sName;
                // 파일 저장
                Files.copy(file.getInputStream(), Paths.get(path), StandardCopyOption.REPLACE_EXISTING);

                // 파일 이름을 DTO에 설정
                bannerDTO.setBfile(sName);
                bannerDTO.setBmanage(0); // 예시로 1로 설정, 실제로는 어떤 값이어야 할 것입니다.

                // 로그에 DTO 정보 출력
                log.info("BannerDTO : " + bannerDTO);

                // BannerDTO를 서비스로 전달하여 처리
                Banner banner = modelMapper.map(bannerDTO, Banner.class);
                Banner savedBanner = bannerRepository.save(banner);

                log.info("savedBanner : " + savedBanner);

                return savedBanner;
            } catch (IOException e) {
                e.printStackTrace();
                // 파일 저장 실패 시 예외 처리
                return null;
            }
        } else {
            // 파일이 업로드되지 않았을 때 처리할 로직
            return null;
        }
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












