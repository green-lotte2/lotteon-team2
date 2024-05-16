package kr.co.lotteon.service;

import com.querydsl.core.Tuple;
import kr.co.lotteon.dto.*;
import kr.co.lotteon.entity.*;

import kr.co.lotteon.mapper.ProductMapper;
import kr.co.lotteon.repository.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.*;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

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
    private final ProductMapper productMapper;


    private final ProductimgRepository productimgRepository;

    /////////////////////////상품 이미지////////////////////////////////
    public ProductimgDTO imgUpload(ProductimgDTO imgDTO, int cate) {

        // 파일 업로드 시스템 경로 구하기
        log.info(imgUploadPath);

        List<String> fileNames = new ArrayList<>();

        for (MultipartFile mf : imgDTO.getFiles()) {

            String path = new File(imgUploadPath + "/" + cate + "/").getAbsolutePath();

            File Folder = new File(path);

            // 해당 디렉토리가 없다면 디렉토리를 생성.
            if (!Folder.exists()) {
                try {
                    Folder.mkdir(); //폴더 생성합니다. ("새폴더"만 생성)
                    log.info("폴더가 생성완료.");
                } catch (Exception e) {
                    e.getStackTrace();
                }
            } else {
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
        imgDTO.setDetailimg(fileNames.get(1));


        // 저장한 파일 정보 리스트 반환
        return imgDTO;
    }

    public void insertImg(ProductimgDTO imgDTO) {
        Productimg img = modelMapper.map(imgDTO, Productimg.class);

        productimgRepository.save(img);
        log.info("img" + img);
    }


    /////////////////////////상품///////////////////////////////

    public Product findProduct(int pno){
        Optional<Product> optProduct = productRepository.findById(pno);
        Product product = null;
        if(optProduct.isPresent()){
            product = optProduct.get();
        }
        return product;
    }




    public Product insertProduct(ProductDTO productDTO) {

        Product product = modelMapper.map(productDTO, Product.class);
        Product savedProduct = productRepository.save(product);

        log.info("savedProduct: " + savedProduct);
        return savedProduct;
    }
    /*
        상품 조회
     */

    public List<ProductDTO> findNewestProduct(){
        return productMapper.selectProductsByNewest();
    }

    public List<ProductDTO> findBestProduct(){
        return productMapper.selectProductsByOrder();
    }

    public List<ProductDTO> findRecommendProduct(){
        return productMapper.selectProductsByScore();
    }

    public List<ProductDTO> findHighHitProduct(){
        return productMapper.selectProductsByHit();
    }

    public List<ProductDTO> findDiscountProduct(){
        return productMapper.selectProductsByDiscount();
    }

    public Page<Product> findAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable);
    }



    public Page<Product> findByPname(Pageable pageable,String name){
        log.info("findByPname...");
        return productRepository.findByPnameLike(pageable, "%"+name+"%");

    }

    public Optional<Product> findProductById(int pno) {
        return productRepository.findById(pno);
    }

    public Page<Product> findByCateBetween(Pageable pageable, int cate, int depth) {
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

    // 카테고리 ID와 계층의 깊이를 기준으로 상품 목록을 조회합니다.
    public List<CategoryDTO> calculateCategoryPath(int categoryId) {
        List<CategoryDTO> path = new ArrayList<>();
        Optional<Category> currentCategory = categoryRepository.findById(categoryId);

        while (currentCategory.isPresent()) {
            Category category = currentCategory.get();
            path.add(0, CategoryDTO.of(category));
            currentCategory = Optional.ofNullable(category.getParent());
        }

        return path;
    }


    // 엔티티를 DTO로 변환하는 메서드
    private ProductDTO convertToProductDTO(Product product) {
        ProductDTO dto = modelMapper.map(product, ProductDTO.class);
        // 추가적인 변환 로직 구현
        return dto;
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

    @Cacheable("cateCache")
    @Transactional(rollbackFor = Exception.class)
    public List<CategoryDTO> getCategoryList() {
        List<CategoryDTO> results = categoryRepository.findAll().stream().map(CategoryDTO::of).collect(Collectors.toList());
        log.info(results.toString());
        return results;
    }


    public List<ProductDTO> getCartProductsByUid(String uid) {
        return productMapper.selectCartWithProductsByUid(uid);
    }


    // ProductService.java
    public ProductPageResponseDTO getList(ProductPageRequestDTO productPageRequestDTO, int cate) {
        Pageable pageable = PageRequest.of(
                Math.max(productPageRequestDTO.getPg() - 1, 0), 12,
                Sort.by("pname").ascending()
        );

        Specification<Product> specification = Specification.where((root, query, criteriaBuilder) -> criteriaBuilder.conjunction());

        // 카테고리 필터링
        if (cate > 0) {
            int depth = (cate % 10 != 0) ? 1 : (cate % 1000 != 0) ? 100 : 10000;
            specification = specification.and((root, query, criteriaBuilder) -> criteriaBuilder.between(root.get("cate"), cate, cate + depth - 1));
        }

        // 검색 조건 필터링
        if (productPageRequestDTO.getSearch() != null && !productPageRequestDTO.getSearch().isEmpty()) {
            specification = specification.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.like(root.get("pname"), "%" + productPageRequestDTO.getSearch() + "%")
            );
        }

        if (productPageRequestDTO.getMinPrice() != null) {
            specification = specification.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.greaterThanOrEqualTo(root.get("price"), productPageRequestDTO.getMinPrice())
            );
        }

        if (productPageRequestDTO.getMaxPrice() != null) {
            specification = specification.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.lessThanOrEqualTo(root.get("price"), productPageRequestDTO.getMaxPrice())
            );
        }

        Page<Product> page = productRepository.findAll(specification, pageable);

        List<ProductDTO> productDTOs = page.getContent().stream()
                .map(product -> {
                    ProductDTO dto = convertToProductDTO(product);
                    // 이미지를 포함하도록 추가
                    Productimg img = product.getProductimg();
                    if (img != null) {
                        dto.setMainimg(img.getMainimg());
                        dto.setSubimg(img.getSubimg());
                        dto.setDetailimg(img.getDetailimg());
                    }
                    return dto;
                })
                .collect(Collectors.toList());

        return ProductPageResponseDTO.builder()
                .dtoList(productDTOs)
                .productPageRequestDTO(productPageRequestDTO)
                .pg(productPageRequestDTO.getPg())
                .size(page.getSize())
                .total((int) page.getTotalElements())
                .build();
    }


    private ProductDTO entityToDTO(Product product) {
        return ProductDTO.builder()
                .pno(product.getPno())
                .cate(product.getCate())
                .pname(product.getPname())
                .rdate(product.getRdate())
                .price(product.getPrice())
                .stock(product.getStock())
                .deliprice(product.getDeliprice())
                .company(product.getCompany())
                .discount(product.getDiscount()) // 할인율 추가
                .point(product.getPoint())
                .info(product.getInfo())
                .hit(product.getHit())
                .opname(product.getOpname())
                .opvalue(product.getOpvalue())
                .mainimg(null) // 초기 이미지 정보를 null로 설정
                .subimg(null)
                .detailimg(null)
                .build();
    }


    // 서비스 계층
    public ProductDTO findProductDTOById(int pno) {
        return productMapper.selectProductWithImagesById(pno);
    }

    public void productHitUpdate(ProductDTO productDTO){
        productDTO.setHit(productDTO.getHit() + 1);
        Product product = modelMapper.map(productDTO,Product.class);
        productRepository.save(product);
    }


    //🎈 상품 조회
    public PageResponseDTO selectArticles(PageRequestDTO pageRequestDTO){

        log.info("selectArticles...1");
        Pageable pageable = pageRequestDTO.getPageable("pg");

        log.info("selectArticles...2");
        Page<Tuple> pageProduct = productRepository.selectProducts(pageRequestDTO , pageable);

        log.info("selectArticles...3 : " + pageProduct);
        List<ProductDTO> dtoList = pageProduct.getContent().stream()
                .map(tuple ->
                        {
                            log.info("tuple : " + tuple);
                            Product product = tuple.get(0, Product.class);
                            int pno = tuple.get(1, int.class);
                            product.setPno(pno);

                            log.info("product : " + pno);

                            return modelMapper.map(product, ProductDTO.class);
                        }
                )
                .toList();

        log.info("selectArticles...4 : " + dtoList);

        int total = (int) pageProduct.getTotalElements();

        return PageResponseDTO.builder()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(dtoList)
                .total(total)
                .build();
    }

    // 상품 검색 서비스 메서드
    public List<ProductDTO> searchProducts(String search, Integer minPrice, Integer maxPrice, int cate) {
        return productMapper.searchProducts(search, minPrice, maxPrice, cate);
    }

    public int countSearchProducts(String search, Integer minPrice, Integer maxPrice, int cate) {
        return productMapper.countSearchProducts(search, minPrice, maxPrice, cate);
    }


}


