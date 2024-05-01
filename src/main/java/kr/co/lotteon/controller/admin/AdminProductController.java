package kr.co.lotteon.controller.admin;

import kr.co.lotteon.dto.CategoryDTO;
import kr.co.lotteon.dto.OrdersDTO;
import kr.co.lotteon.dto.ProductDTO;
import kr.co.lotteon.dto.ProductimgDTO;
import kr.co.lotteon.entity.Product;
import kr.co.lotteon.service.AdminService;
import kr.co.lotteon.service.OrdersService;
import kr.co.lotteon.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
public class AdminProductController {

    private final ProductService productService;
    private final AdminService adminService;

    private final OrdersService ordersService;


    @GetMapping("/admin/product/register")
    public String adminProductRegister(Model model){
        List<CategoryDTO> cate = productService.getCategoryList();
        model.addAttribute("cate", cate);
        log.info("adminProductRegister1.." + cate);

        return "/admin/product/register";
    }

    @PostMapping("/admin/product/register")
    public String adminProductRegister(ProductDTO productDTO,
                                       @RequestParam("mainImg") MultipartFile fileA,
                                       @RequestParam("detailImg") MultipartFile fileD){
        log.info("adminProductRegister2");
        log.info(productDTO.toString());
        log.info(fileA.isEmpty()+"");
        if(productDTO.getPno()!=0){
            Product product = productService.findProductById(productDTO.getPno()).get();
            productDTO.setRdate(product.getRdate());
        }

        Product product = productService.insertProduct(productDTO);

        if (!fileA.isEmpty()){
            List<MultipartFile> files = new ArrayList<>();
            files.add(fileA);
            files.add(fileD);

            ProductimgDTO imgDTO = new ProductimgDTO();
            imgDTO.setPno(productDTO.getPno());
            imgDTO.setFiles(files);
            imgDTO.setPno(product.getPno());

            productService.imgUpload(imgDTO, productDTO.getCate());
            productService.insertImg(imgDTO);
        }

        return "redirect:/admin/product/list";
    }


    @GetMapping("/admin/product/list")
    public String adminProductList(Model model){

        List<ProductDTO> adminProducts = adminService.selectProducts();
        log.info("adminProducts" + adminProducts);

        model.addAttribute("adminProducts", adminProducts);

        return "/admin/product/list";
    }

    @GetMapping("/admin/product/delete")
    public String adminDeleteProduct(int pno){
        adminService.adminDeleteProduct(pno);
        return "redirect:/admin/product/list";
    }

    @PostMapping("/admin/product/delete")
    public String adminDeleteProduct(@RequestParam List<String> checkbox){
        for(String pno : checkbox){
            int productId = Integer.parseInt(pno);
            adminService.adminDeleteProduct(productId);
            log.info("deletePno : " + productId);
        }
        log.info(checkbox.toString());

        return "redirect:/admin/product/list";
    }


    @GetMapping("/admin/product/modify")
    public String adminProductModify(Model model, int pno){

        Product product = productService.findProduct(pno);
        List<CategoryDTO> cate = productService.getCategoryList();

        model.addAttribute("product", product);
        model.addAttribute("cate", cate);

        return "/admin/product/modify";
    }

    @GetMapping("/admin/order/list")
    public String adminOrderList(Model model){

        List<OrdersDTO> ordersDTOS = ordersService.selectAllOrders();
        log.info(ordersDTOS.toString());
        model.addAttribute("orders", ordersDTOS);
        return "/admin/order/list";
    }

    @GetMapping("/admin/product/search")
    public String search(
            Model model,
            String search,
            @PageableDefault(size = 10, sort = "pname", direction = Sort.Direction.ASC) Pageable pageable) {

      /*  Page<Product> resultList = productService.findByPname(pageable, search);
        for (Product result : resultList) {
            log.info(result.toString());
        }
        model.addAttribute("products", resultList);
        model.addAttribute("page", resultList);
        log.info("검색....1"); */
        return "/admin/product/search";
    }



}
