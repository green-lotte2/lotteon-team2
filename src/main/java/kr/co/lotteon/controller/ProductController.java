package kr.co.lotteon.controller;

import kr.co.lotteon.dto.CategoryResult;
import kr.co.lotteon.dto.ProductDTO;
import kr.co.lotteon.dto.ProductimgDTO;
import kr.co.lotteon.entity.Product;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Controller
public class ProductController {

    private final ProductService productService;

    @PostMapping("/product/register")
    public String productRegister(ProductDTO productDTO,
                                  @RequestParam("imgMain") MultipartFile fileA,
                                  @RequestParam("imgSub1") MultipartFile fileB,
                                  @RequestParam("imgSub2") MultipartFile fileC,
                                  @RequestParam("imgDetail") MultipartFile fileD){

        log.info("productRegister");
        log.info(""+productDTO);

        List<MultipartFile> files = new ArrayList<>();
        files.add(fileA);
        files.add(fileB);
        files.add(fileD);

        ProductimgDTO imgDTO = new ProductimgDTO();

        imgDTO.setPno(productDTO.getPno());
        imgDTO.setFiles(files);

        productService.imgUpload(imgDTO, productDTO.getCate());
        Product product = productService.insertProduct(productDTO);
        imgDTO.setPno(product.getPno());
        productService.insertImg(imgDTO);

        return "/admin/product/list";
    }



    @GetMapping("/product/cart")
    public String cart(Model model) {
        return "/product/cart";
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
    public String list(String cate,
                        Model model,
                       @PageableDefault(size = 10, sort = "pname", direction = Sort.Direction.ASC) Pageable pageable) {
        Page<Product> product = null;
                productService.findAllProducts(pageable);
        log.info(cate);
        int depth = 0;
        if (cate!=null){
            int code = Integer.parseInt(cate);
            if(code % 10 != 0){
                depth = 1;
                product = productService.findByCateBetween(pageable, code, depth);
            }else if(code % 1000 != 0){
                depth = 100;
                product = productService.findByCateBetween(pageable, code, depth);
            }else{
                depth = 10000;
                product = productService.findByCateBetween(pageable, code, depth);
            }
        }else{
            product = productService.findAllProducts(pageable);
        }
        log.info("product112 : " + product);
        model.addAttribute("product", product);
        model.addAttribute("page", product);
        model.addAttribute("cate", productService.getCategoryList());
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

