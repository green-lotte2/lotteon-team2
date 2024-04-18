package kr.co.lotteon.service;

import groovy.lang.Tuple;
import kr.co.lotteon.dto.ProductDTO;
import kr.co.lotteon.dto.ProductimgDTO;
import kr.co.lotteon.entity.*;
import kr.co.lotteon.repository.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Optional;
import kr.co.lotteon.dto.CategoryResult;
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
    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;

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

            File Folder = new File(path);

            // 해당 디렉토리가 없다면 디렉토리를 생성.
            if (!Folder.exists()) {
                try{
                    Folder.mkdir(); //폴더 생성합니다. ("새폴더"만 생성)
                    log.info("폴더가 생성완료.");
                }
                catch(Exception e){
                    e.getStackTrace();
                }
            }else {
                log.info("폴더가 이미 존재합니다..");
            }

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
    }

    public Product insertProduct(ProductDTO productDTO) {

        Product product = modelMapper.map(productDTO, Product.class);
        Product savedProduct = productRepository.save(product);
        log.info(savedProduct.toString());
        return savedProduct;
    }

    public List<Product> findNewProduct(){
        return productRepository.findTop8ByOrderByRdateDesc();
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
        return productRepository.findByCateBetween(pageable, cate, cate + depth);
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

    public Page<Product> findProductsByCategoryName(String cname, Pageable pageable) {
        // 카테고리 이름에 해당하는 카테고리 목록을 찾습니다.
        List<Category> categories = categoryRepository.findByCname(cname);
        if (categories.isEmpty()) {
            return Page.empty();
        }
        // 여러 카테고리가 반환될 수 있으므로 첫 번째 카테고리의 ID를 사용하거나, 다른 로직을 구현할 수 있습니다.
        Category category = categories.get(0); // 첫 번째 카테고리를 사용.

        // 해당 카테고리 ID를 사용하여 상품을 검색합니다.
        return productRepository.findByCate(category.getCate(), pageable);
    }


    @Transactional(rollbackFor = Exception.class)
    public List<CategoryResult> getCategoryList() {
        List<CategoryResult> results = categoryRepository.findAll().stream().map(CategoryResult::of).collect(Collectors.toList());
        log.info(results.toString());
        return results;
    }


    public List<ProductDTO> getCartProducts(String uid) {
        return cartRepository.findByUid(uid).stream()
                .map(cart -> {
                    ProductDTO dto = modelMapper.map(cart.getUid(), ProductDTO.class);
                    return dto;
                })
                .collect(Collectors.toList());
    }
}


