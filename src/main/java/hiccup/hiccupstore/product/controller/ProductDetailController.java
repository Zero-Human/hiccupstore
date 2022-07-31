package hiccup.hiccupstore.product.controller;

import hiccup.hiccupstore.product.dto.Product;
import hiccup.hiccupstore.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductDetailController {

    private final ProductService productService ;

    @GetMapping("/detail")
    public String detailView(Model model,
                             @RequestParam int productId){

        return "redirect:/product/detail";
    }

    /*
    @PostMapping("/pick")
    public String productToUserPick(@ModelAttribute("pickProduct") UserPick pickProduct) {
        // UserPick 객체에서 필요한 값들을 detail html에서 form 데이터로 받아오기
        return ""
    }
    */


    /*
    @PostMapping("/cart")
    public String productToUserPick(@ModelAttribute("cartProduct") Cart c){
        // Cart객체에서 필요한 값들을 detail html에서 form 데이터로 받아오기
        return ""
    }
    */

    /*
    // 바로 구매의 경우 -> 주문(Order)측 메서드와 Controller 참고해서 필요한 정보 담아서 해당 URL로 Redirecting 시켜주기
    @PostMapping("/purchase")
    public String productDirectBuy(@ModelAttribute("
    */
}

