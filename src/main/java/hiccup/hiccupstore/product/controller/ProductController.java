package hiccup.hiccupstore.product.controller;


import hiccup.hiccupstore.product.dto.ProductCategory;
import hiccup.hiccupstore.product.dto.ProductForView;
import hiccup.hiccupstore.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;

@Controller
@RequiredArgsConstructor
@RequestMapping("/product/productlist")
public class ProductController {

    private final ProductService productService ;
    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    private final HashMap<String, Object> paramMap = new HashMap<>() ;
    // 나중에 REST API 적용하면 "PutMapping" / "DeleteMapping" / "PathMapping" 등을 활용해봅시다.

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
                              @RequestParam(defaultValue = "all", required=false) String category,
                              @RequestParam(defaultValue = "default", required=false) String sortValue,
                              @RequestParam(defaultValue = "16", required=false) String viewCount){

        int idx = -1 ;
        if(category.equals("all")){
            idx = ProductCategory.valueOf(category.toUpperCase()).ordinal() ;
        }
        paramMap.put("type", idx);
        paramMap.put("sort", sortValue);

        ArrayList<ProductForView> list = productService.getProductListByCategory(paramMap);
//        paramMap.put("", viewCount);
        model.addAttribute("list",list) ;
        model.addAttribute("viewCount", viewCount) ;
        return "redirect:/product/productlist/liqour";
    }

    /*
    * 가격대별 기본 조회
     */
    @GetMapping("/price")
    public String priceView(Model model,
                          @RequestParam(defaultValue = "all", required=false) String priceRange,
                          @RequestParam(defaultValue = "default", required=false) String sortValue,
                          @RequestParam(defaultValue = "16", required=false) String viewCount){
        int p = -1 ;
        if(priceRange.equals("all")){
            p = Integer.parseInt(priceRange) ;
        }
        paramMap.put("p", p);
        paramMap.put("sort", sortValue);

        ArrayList<ProductForView> list = productService.getProductListByPriceRange(paramMap);
        model.addAttribute("list",list) ;
        model.addAttribute("viewCount", viewCount) ;
        return "redirect:/product/productlist/price";
    }


    /*
    @GetMapping("/search")
    public String searchView(Model model,
                             @RequestParam String keyword,
                             @RequestParam(defaultValue = "rate", required=false) String sortValue,
                             @RequestParam(defaultValue = "16", required=false) String viewCount){
        return "";
    }
    */
}
