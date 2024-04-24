package kr.co.lotteon.controller;

import kr.co.lotteon.dto.*;
import kr.co.lotteon.entity.Orders;
import kr.co.lotteon.entity.Product;
import kr.co.lotteon.entity.User;
import kr.co.lotteon.service.AdminService;
import kr.co.lotteon.service.CartService;
import kr.co.lotteon.service.ProductService;
import kr.co.lotteon.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.Console;
import java.security.Principal;
import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Controller
public class ProductController {

    private final ProductService productService;
    private final CartService cartService;
    private final UserService userService;

    @PostMapping("/product/register")
    public String productRegister(ProductDTO productDTO,
                                  @RequestParam("imgMain") MultipartFile fileA,
                                  @RequestParam("imgSub1") MultipartFile fileB,
                                  @RequestParam("imgSub2") MultipartFile fileC,
                                  @RequestParam("imgDetail") MultipartFile fileD) {

        log.info("productRegister");
        log.info("" + productDTO);

        List<MultipartFile> files = new ArrayList<>();
        files.add(fileA);
        files.add(fileB);
        files.add(fileD);

        ProductimgDTO imgDTO = new ProductimgDTO();

        imgDTO.setPno(productDTO.getPno());
        imgDTO.setFiles(files);

        productService.imgUpload(imgDTO, productDTO.getCate());
        Product product = productService.insertProduct(productDTO);
        log.info("" + product.getPno());
        imgDTO.setPno(product.getPno());
        productService.insertImg(imgDTO);

        return "/admin/product/list";
    }



    @GetMapping("/product/cart")
    public String cart(Authentication authentication, Model model) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return "redirect:/member/login"; // 사용자가 로그인하지 않았다면 로그인 페이지로 리다이렉트
        }

        // 사용자 정보 가져오기
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String uid = userDetails.getUsername();  // 인증된 사용자 ID 추출

        List<ProductDTO> cartProducts = productService.getCartProductsByUid(uid);  // 사용자 ID를 기반으로 장바구니 상품 조회

        model.addAttribute("cartProducts", cartProducts);  // 모델에 장바구니 상품 목록 추가
        model.addAttribute("cate", productService.getCategoryList()); // 카테고리 리스트 추가
        return "/product/cart"; // 장바구니 뷰 페이지 반환
    }


    @ResponseBody
    @PostMapping("/product/cart/insert")
    public ResponseEntity<CartDTO> insertCartItem(Principal principal, @RequestBody CartDTO cartDTO) {
        String uid = principal.getName();
        log.info("uid : " + uid);
        cartDTO.setUid(uid);
        log.info("insertCart : " + cartDTO);

        cartService.insertCart(cartDTO);

        return ResponseEntity.ok(cartDTO);
    }

    /*
    @PostMapping("/product/addToCart")
    public String addToCart(@RequestParam("uid") String uid, @RequestParam("pno") int pno,
                            @RequestParam("quantity") int quantity, RedirectAttributes redirectAttributes) {
        productService.addToCart(uid, pno, quantity);
        redirectAttributes.addFlashAttribute("successMessage", "Product added to cart successfully!");
        return "redirect:/product/cart";
    }

    @PostMapping("/product/updateCart")
    public ResponseEntity<?> updateCartQuantity(@RequestParam("uid") String uid, @RequestParam("pno") int pno,
                                                @RequestParam("quantity") int quantity) {
        try {
            productService.updateCartQuantity(uid, pno, quantity);
            return ResponseEntity.ok().body("Cart updated successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error updating cart: " + e.getMessage());
        }
    }

    @PostMapping("/product/removeFromCart")
    public String removeFromCart(@RequestParam("uid") String uid, @RequestParam("pno") int pno,
                                 RedirectAttributes redirectAttributes) {
        productService.removeFromCart(uid, pno);
        redirectAttributes.addFlashAttribute("successMessage", "Product removed from cart successfully!");
        return "redirect:/product/cart";
    }
        */


    @GetMapping("/product/complete")
    public String complete() {
        return "/product/complete";
    }


    @GetMapping("/product/list")
    public String list(@RequestParam(required = false) String cate,
                       Model model,
                       @RequestParam(defaultValue = "1") int pg,
                       @RequestParam(defaultValue = "10") int size) {

        // cate 문자열을 int로 변환, 유효하지 않은 경우 기본값 0을 사용
        int cateInt = 0;
        try {
            cateInt = Integer.parseInt(cate);
        } catch (NumberFormatException e) {
            log.error("Invalid category ID: {}", cate, e);
            // 필요한 경우 오류 처리 또는 기본값 설정
        }

        // PageRequestDTO 객체 생성
        ProductPageRequestDTO productPageRequestDTO = ProductPageRequestDTO.builder()
                .pg(pg)
                .size(size)
                .build();

        // 서비스 메소드 호출
        ProductPageResponseDTO responseDTO = productService.getList(productPageRequestDTO, cate);
        responseDTO.setCate(cateInt); // cate 값을 responseDTO에 설정
        model.addAttribute("products", responseDTO.getDtoList());
        model.addAttribute("result", responseDTO);
        model.addAttribute("cate", productService.getCategoryList()); // 카테고리 리스트 추가

        log.info("response : " + responseDTO);

        return "/product/list";
    }

    @GetMapping("/product/order")
    public String order(Authentication authentication, Model model) {

        if (authentication == null || !authentication.isAuthenticated()) {
            return "redirect:/member/login"; // 사용자가 로그인하지 않았다면 로그인 페이지로 리다이렉트
        }

        // 사용자 정보 가져오기
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String uid = userDetails.getUsername();  // 인증된 사용자 ID 추출

        List<ProductDTO> cartProducts = productService.getCartProductsByUid(uid);  // 사용자 ID를 기반으로 장바구니 상품 조회
        UserDTO user = userService.selectUserDetail(uid);
        log.info("dddddd"+user);
        model.addAttribute("user", user);
        model.addAttribute("cartProducts", cartProducts);  // 모델에 장바구니 상품 목록 추가
        model.addAttribute("cate", productService.getCategoryList());

        return "/product/order";
    }

    @PostMapping("/product/order")
    public String productOrder(OrdersDTO ordersDTO, @RequestParam List<String> checkbox) {

        Orders orders = productService.insertOrder(ordersDTO);
        int ono = orders.getOno();
        for(String select : checkbox){
            OrdersDTO ordersDTO1 = new OrdersDTO();
            ordersDTO1.setOno(ono);
            String[] productInfo = select.split("%");

            ordersDTO1.setPno(Integer.parseInt(productInfo[0]));
            ordersDTO1.setPcount(Integer.parseInt(productInfo[1]));
            ordersDTO1.setOptions(null);

            if (productInfo.length > 2){
                ordersDTO1.setOptions(productInfo[2]);
            }
            String uid = ordersDTO.getUid();
            userService.updateUserPoint(uid, ordersDTO.getUsepoint(), ordersDTO.getSavepoint());
            productService.insertOrderDetail(ordersDTO1);
            cartService.orderCartItems(uid, ordersDTO1.getPno());
        }
        return "redirect:/product/list";
    }

    @GetMapping("/product/search")
    public String search(
            Model model,
            String search,
            @PageableDefault(size = 10, sort = "pname", direction = Sort.Direction.ASC) Pageable pageable) {

        Page<Product> resultList = productService.findByPname(pageable, search);
        for (Product result : resultList) {
            log.info(result.toString());
        }
        model.addAttribute("product", resultList);
        model.addAttribute("page", resultList);
        return "/product/list";
    }

    @GetMapping("/product/view")
    public String viewProduct(@RequestParam("pno") int pno, Model model) {
        ProductDTO productDTO = productService.findProductDTOById(pno);
        if (productDTO != null) {
            model.addAttribute("cate", productService.getCategoryList());
            model.addAttribute("product", productDTO);
            model.addAttribute("sizes", List.of("S", "M", "L")); // 예시 사이즈
            model.addAttribute("colors", List.of("Red", "Blue", "Green")); // 예시 색상
            return "/product/view";
        } else {
            return "redirect:/product/list"; // 제품이 없을 경우 리다이렉트
        }
    }


    @PostMapping("/product/updateCartQuantity")
    @ResponseBody
    public ResponseEntity<?> updateCartQuantity(@RequestParam("pno") int pno, @RequestParam("quantity") int quantity) {
        try {
            Product product = productService.findProductById(pno).orElseThrow(() -> new Exception("Product not found"));
            if (quantity > 0 && quantity <= product.getStock()) {
                product.setStock(quantity); // 새로운 수량으로 업데이트
                productService.saveProduct(product);
                return ResponseEntity.ok().body("Updated successfully");
            } else {
                return ResponseEntity.badRequest().body("Invalid quantity");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating quantity: " + e.getMessage());
        }
    }

    // 장바구니에서 상품 삭제
    @PostMapping("/cart/delete")
    public ResponseEntity<?> deleteCart(Principal principal, @RequestBody Map<String, int[]> requestData) {
        int[] pnos = requestData.get("pnos");
        log.info("pnos : " + pnos);
        return cartService.deleteCartItems(principal.getName(), pnos);
    }

}

