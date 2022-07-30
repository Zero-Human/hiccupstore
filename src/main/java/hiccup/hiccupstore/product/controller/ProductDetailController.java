package hiccup.hiccupstore.product.controller;

import hiccup.hiccupstore.product.dto.Product;
import hiccup.hiccupstore.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductDetailController {

    private final ProductService productService ;

    @GetMapping("/detail")
    public String detailView(Model model,
                             @ModelAttribute("p") Product p,
                             @RequestParam int productId){

        return "redirect:/product/detail";
    }
}
