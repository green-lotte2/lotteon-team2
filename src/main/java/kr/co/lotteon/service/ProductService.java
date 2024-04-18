package kr.co.lotteon.service;

import kr.co.lotteon.dto.ProductDTO;
import kr.co.lotteon.dto.ProductimgDTO;
import kr.co.lotteon.entity.Product;
import kr.co.lotteon.entity.Productimg;
import kr.co.lotteon.repository.ProductRepository;
import kr.co.lotteon.repository.ProductimgRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
import kr.co.lotteon.dto.CategoryResult;
import kr.co.lotteon.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductService {

    private final CategoryRepository categoryRepository;

    private final ProductRepository productRepository;


    @Value("${img.upload.path}")
    private String imgUploadPath;

    private final ModelMapper modelMapper;

    private final ProductimgRepository productimgRepository;

    public ProductimgDTO imgUpload(ProductimgDTO imgDTO, int cate) {

        // 파일 업로드 시스템 경로 구하기
        log.info(imgUploadPath);

        List<String> fileNames = new ArrayList<>();

        for(MultipartFile mf : imgDTO.getFiles()){

            String path = new File(imgUploadPath+"/"+cate+"/").getAbsolutePath();
            String oName = mf.getOriginalFilename();
            String ext = oName.substring(oName.lastIndexOf("."));

            String sName = UUID.randomUUID().toString() + ext;
            fileNames.add(sName);
            try {
                mf.transferTo(new File(path, sName));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        imgDTO.setMainimg(fileNames.get(0));
        imgDTO.setSubimg(fileNames.get(1));
        imgDTO.setDetailimg(fileNames.get(2));


        // 저장한 파일 정보 리스트 반환
        return imgDTO;
    }

    public void insertImg(ProductimgDTO imgDTO){
        Productimg img = modelMapper.map(imgDTO, Productimg.class);

        productimgRepository.save(img);
        log.info("img" + img);
    }

    public Product insertProduct(ProductDTO productDTO) {

        Product product = modelMapper.map(productDTO, Product.class);
        Product savedProduct = productRepository.save(product);

        log.info("savedProduct: " + savedProduct);
        return savedProduct;
    }


    public Page<Product> findAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    public Page<Product> findByPname(Pageable pageable,String name){

        return productRepository.findByPnameLike(pageable, "%"+name+"%");
    }

    public Optional<Product> findProductById(int pno) {
        return productRepository.findById(pno);
    }

    public Page<Product> findByCateBetween(Pageable pageable, int cate, int depth){
        return productRepository.findByCateBetween(pageable, cate, depth);
    }

    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    public Product updateProduct(Product product) {
        return productRepository.save(product);
    }

    public void deleteProduct(int productId) {
        productRepository.deleteById(productId);
    }

    @Transactional(rollbackFor = Exception.class)
    public List<CategoryResult> getCategoryList() {
        List<CategoryResult> results = categoryRepository.findAll().stream().map(CategoryResult::of).collect(Collectors.toList());
        log.info(results.toString());
        return results;
    }
}


