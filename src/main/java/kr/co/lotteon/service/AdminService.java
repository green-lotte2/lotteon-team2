package kr.co.lotteon.service;

import kr.co.lotteon.dto.ProductDTO;
import kr.co.lotteon.mapper.AdminMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import kr.co.lotteon.dto.UserDTO;
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


}
