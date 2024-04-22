package kr.co.lotteon.controller;

import kr.co.lotteon.dto.*;
import kr.co.lotteon.entity.Product;
import kr.co.lotteon.service.AdminService;
import kr.co.lotteon.service.CartService;
import kr.co.lotteon.service.ProductService;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Controller
public class ProductController {

    private final ProductService productService;
    private final CartService cartService;
    private final AdminService adminService;

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
    public String cart(Model model) {
        return "/product/cart";
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


        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                .pg(pg)
                .size(size)
                .build();

        PageResponseDTO responseDTO = productService.getList(pageRequestDTO, cate);
        log.info("response"+responseDTO);
        model.addAttribute("products", responseDTO.getDtoList());
        model.addAttribute("result", responseDTO);
        model.addAttribute("cate", productService.getCategoryList()); // 카테고리 리스트 추가

        return "/product/list";
    }





    @GetMapping("/product/order")
    public String order() {
        return "/product/order";
    }

    @PostMapping("/product/order")
    public String productorder() {
        return "/product/order";
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
        Optional<Product> productOpt = productService.findProductById(pno);
        if (productOpt.isPresent()) {
            Product product = productOpt.get();
            model.addAttribute("cate", productService.getCategoryList());
            model.addAttribute("product", product);
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

}

