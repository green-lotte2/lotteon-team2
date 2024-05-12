package kr.co.lotteon.controller.admin;

import kr.co.lotteon.dto.*;
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
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Controller
public class AdminProductController {

    private final ProductService productService;
    private final AdminService adminService;

    private final OrdersService ordersService;


    @GetMapping("/admin/product/register")
    public String adminProductRegister(Model model) {
        List<CategoryDTO> cate = productService.getCategoryList();
        model.addAttribute("cate", cate);
        log.info("adminProductRegister1.." + cate);

        return "/admin/product/register";
    }

    @PostMapping("/admin/product/register")
    public String adminProductRegister(ProductDTO productDTO,
                                       Authentication authentication,
                                       @RequestParam("mainImg") MultipartFile fileA,
                                       @RequestParam("detailImg") MultipartFile fileD) {
        log.info("adminProductRegister2");
        log.info(productDTO.toString());
        log.info(fileA.isEmpty() + "");
        if (authentication == null || !authentication.isAuthenticated()) {
            return "redirect:/member/login"; // 사용자가 로그인하지 않았다면 로그인 페이지로 리다이렉트
        }
        productDTO.setSid(authentication.getName());
        if (productDTO.getPno() != 0) {
            Product product = productService.findProductById(productDTO.getPno()).get();
            productDTO.setRdate(product.getRdate());
        }

        Product product = productService.insertProduct(productDTO);

        if (!fileA.isEmpty()) {
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
    public String adminProductList(Model model, PageRequestDTO pageRequestDTO) {

        TypePageResponseDTO typePageResponseDTO = adminService.selectProductsBySearch(pageRequestDTO);
        model.addAttribute("adminProducts", typePageResponseDTO);

        return "/admin/product/list";
    }

    @GetMapping("/admin/product/delete")
    public String adminDeleteProduct(int pno) {
        Product product = productService.findProduct(pno);
        int cate = product.getCate();

        adminService.adminDeleteProductImg(pno, cate);
        adminService.adminDeleteProduct(pno);
        return "redirect:/admin/product/list";
    }

    @PostMapping("/admin/product/delete")
    public String adminDeleteProduct(@RequestParam List<String> checkbox) {
        for (String pno : checkbox) {
            int productId = Integer.parseInt(pno);
            adminService.adminDeleteProduct(productId);
            log.info("deletePno : " + productId);
        }
        log.info(checkbox.toString());

        return "redirect:/admin/product/list";
    }


    @GetMapping("/admin/product/modify")
    public String adminProductModify(Model model, int pno) {

        Product product = productService.findProduct(pno);
        List<CategoryDTO> cate = productService.getCategoryList();

        model.addAttribute("product", product);
        model.addAttribute("cate", cate);

        return "/admin/product/modify";
    }

    @GetMapping("/admin/order/list")
    public String adminOrderList(Model model, PageRequestDTO pageRequestDTO) {

        TypePageResponseDTO ordersDTOS = adminService.selectOrderGroup(pageRequestDTO);
        TypePageResponseDTO orderProduct = adminService.selectOrders(pageRequestDTO);
        model.addAttribute("orders", ordersDTOS);
        model.addAttribute("products", orderProduct);
        return "/admin/order/list";
    }

    @GetMapping("/admin/order/sales")
    public String adminOrderSaleList(Model model, PageRequestDTO pageRequestDTO) {

        List<OrdersDTO> monthSales = adminService.selectOrderByMonth();
        List<OrdersDTO> weekSales = adminService.selectOrderByWeek();
        model.addAttribute("monthSales", monthSales);
        model.addAttribute("weekSales", weekSales);

        return "/admin/order/sales";
    }



    //🎈 배송리스트
    @GetMapping("/admin/order/delivery")
    public String findDeliveryList(Model model,  @RequestParam(defaultValue = "1") int pageNumber,
                                         @RequestParam(defaultValue = "10") int pageSize) {

        Page<DeliveryDTO> deliveryPage = adminService.findDeliveryList(pageNumber, pageSize);
        log.info("deliveryPage : " + deliveryPage);

        // 유효성 검사
        if (deliveryPage == null) {
            // null 처리
            return "/admin/error/404";
        }

        // 각 배송 정보의 상태에 따라 stname 설정
        List<DeliveryDTO> deliveryDTOS = deliveryPage.getContent();
        for (DeliveryDTO delivery : deliveryDTOS) {
          delivery.setStname();
        }

        model.addAttribute("deliveryDTOS", deliveryDTOS);
        model.addAttribute("pageNumber", pageNumber);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("totalPages", deliveryPage.getTotalPages());
        model.addAttribute("totalItems", deliveryPage.getTotalElements());
        log.info("deliveryDTOS : " + deliveryDTOS);
        log.info("pageNumber : " + pageNumber);
        log.info("pageSize : " + pageSize);
        log.info("totalPages : " + deliveryPage.getTotalPages());
        log.info("totalItems : " + deliveryPage.getTotalElements());



        return "/admin/order/delivery";
    }


    @PostMapping("/admin/delivery/updateStatus")
    public String updateStatus(@RequestBody Map<String, String> requestBody) {
        String stname = requestBody.get("stname");


        int state = 0;
        switch (stname) {
            case "배송준비":
                state = 1;
                break;
            case "배송중":
                state = 2;
                break;
            case "배송완료":
                state = 3;
                break;
            case "반품":
                state = 4;
                break;
            case "환불":
                state = 5;
                break;
            default:
                state = 1;
                break;
        }


        // 적절한 응답 반환
        return "redirect:/admin/order/delivery";
    }


}








