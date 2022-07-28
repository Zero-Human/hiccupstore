package hiccup.hiccupstore.product.controller;

import hiccup.hiccupstore.product.dao.ProductMapper;
import hiccup.hiccupstore.product.dto.Product;
import hiccup.hiccupstore.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Controller
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductService productService ;

    // 나중에 REST API 적용하면 "PutMapping" / "DeleteMapping" / "PathMapping" 등을 활용해봅시다.

//    @GetMapping("/add")
//    public void addFrom(){
//    }
//    @PostMapping("/add")
//
//    @GetMapping("/edit")
//    @PostMapping("/edit")
//
//
//    @RequestMapping("/delete")
    /*
    * 카테고리 기본조회
     */

    /*
     * Parameter : 조회 기준 카테고리 & 조회 정렬 기준
     * Return : Parameter 카테고리 & 정렬 기준에 맞는 상품 리스트가 담긴 Model
     * [설명]
     * dropdown 메뉴가 아닌 기본 Header 의
     */

    @GetMapping("/liquor")
    public String categoryView(Model model,
                              @ModelAttribute
                              @RequestParam(defaultValue = "all", required=false) String category,
                              @RequestParam(defaultValue = "rate", required=false) String sortValue,
                              @RequestParam(defaultValue = "16", required=false) String viewCount){
        ArrayList<Product> list = new ArrayList<Product>();
        model.addAttribute("list",list) ;
        return "liquor" ;
    }

    /*
    * 가격대별 기본 조회
     */
    @GetMapping("/price")
    public String priceView(Model model,
                          @RequestParam(defaultValue = "0") int priceRange,
                          @RequestParam(defaultValue = "rate", required=false) String sortValue,
                          @RequestParam(defaultValue = "16", required=false) String viewCount){
        return "";
    }



    @GetMapping("/search")
    public String searchView(Model model,
                             @RequestParam String keyword,
                             @RequestParam(defaultValue = "rate", required=false) String sortValue,
                             @RequestParam(defaultValue = "16", required=false) String viewCount){
        return "";
    }

    @GetMapping("/detail")
    public String detailView(Model model,
                             @RequestParam int productId){
        return "";
    }


}
