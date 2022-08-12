package hiccup.hiccupstore.product.controller;


import hiccup.hiccupstore.product.dto.ProductCategory;
import hiccup.hiccupstore.product.dto.ProductForView;
import hiccup.hiccupstore.product.dto.page.Page;
import hiccup.hiccupstore.product.dto.page.PageCriteria;
import hiccup.hiccupstore.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;

import static java.lang.Integer.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/product/productlist")
public class ProductController {
    private final ProductService productService ;


    // 나중에 REST API 적용하면 "PutMapping" / "DeleteMapping" / "PathMapping" 등을 활용해봅시다.


    // RequestParam 에서 Request에서는 다른 key값으로 값을 받았다면 어노테이션 속성 중 value로 Client에서의 키 변수값을 입력하면된다.

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
    public String defaultCategoryView(PageCriteria criteria,
                                      Model model,
                                      @RequestParam(name="pageNum", defaultValue = "1",required = false) String pageNum,
                                      @RequestParam(name="type", defaultValue = "-1", required=false) String category,
                                      @RequestParam(name="sort", defaultValue = "default", required=false) String sortValue,
                                      @RequestParam(name = "viewCnt", defaultValue = "16", required = false) String viewCount) {



        criteria.setPageNum(parseInt(pageNum));
        if (!category.equals("-1")) {
            criteria.setType(ProductCategory.valueOf(category.toUpperCase()).ordinal());
        }else{
            criteria.setType(-1);
        }
        criteria.setSort(sortValue);
        criteria.setAmountInOnePage(parseInt(viewCount));
        System.out.println(criteria);

        /*
//        paramMap.put("type", idx);
//        paramMap.put("sort", sortValue);

//        ArrayList<ProductForView> list = productService.getProductListByCategory(paramMap);
        */
        ArrayList<ProductForView> list = productService.getProductListByCategory(criteria);
        model.addAttribute("list",list) ;
        System.out.println(list);
        for (ProductForView pv : list) {
            System.out.println(pv);
        }
        /*
//        model.addAttribute("sort",sortValue) ;
//        model.addAttribute("viewCount", viewCount) ;
         */
        System.out.println(new Page(list.size(),10,criteria));
        model.addAttribute("page", new Page(list.size(),10,criteria));
        return "product/productlist/liquor";
    }

//    @PostMapping("/liquor")
//    public String categoryView(Model model,
//                              @RequestParam(name="type", defaultValue = "all", required=false) String category,
//                              @RequestParam(name="sort", defaultValue = "default", required=false) String sortValue,
//                              @RequestParam(defaultValue = "16", required=false) String viewCount){
//        if(!category.equals("all")){
//            model.addAttribute("type", category);
//            idx = ProductCategory.valueOf(category.toUpperCase()).ordinal() ;
//        } else {
//            model.addAttribute("type", "all");
//        }
//        paramMap.put("type", idx);
//        paramMap.put("sort", sortValue);
//
//        List<ProductForView> list = productService.getProductListByCategory(paramMap);
////        paramMap.put("", viewCount);
//
//        model.addAttribute("list",list) ;
//        model.addAttribute("viewCount", viewCount) ;
//        return "redirect:/product/productlist/liquor";
//    }






    /*
    * 가격대별 기본 조회
     */
    @GetMapping("/price")
    public String defaultPriceView(PageCriteria criteria,
                                   Model model,
                                   @RequestParam(name="p", defaultValue = "all", required=false) String priceRange,
                                   @RequestParam(name="sort", defaultValue = "default", required=false) String sortValue,
                                   @RequestParam(name="viewCnt", defaultValue = "16", required=false) String viewCount){
        if (!priceRange.equals("all")) {
            criteria.setP(parseInt(priceRange));
        }
        if(!sortValue.equals("default")){
            criteria.setSort(sortValue);
        }
        if (!viewCount.equals("16")){
            criteria.setPageNum(parseInt(viewCount));
        }
//        paramMap.put("p", p);
//        paramMap.put("sort", sortValue);
//
//        ArrayList<ProductForView> list = productService.getProductListByPriceRange(paramMap);
        ArrayList<ProductForView> list = productService.getProductListByPriceRange(criteria);
        model.addAttribute("list",list) ;
        for (ProductForView pv : list) {
            System.out.println(pv);
        }

//        model.addAttribute("sort",sortValue) ;
//        model.addAttribute("viewCount", viewCount) ;
        model.addAttribute("page", new Page(list.size(),10,criteria));
        return "product/productlist/price";
    }
//    @PostMapping("/price")
//    public String priceView(Model model,
//                          @RequestParam(name="p", defaultValue = "all", required=false) String priceRange,
//                          @RequestParam(name="sort", defaultValue = "default", required=false) String sortValue,
//                          @RequestParam(defaultValue = "16", required=false) String viewCount){
//        if(priceRange.equals("all")){
//            p = Integer.parseInt(priceRange) ;
//        }
//        paramMap.put("p", p);
//        paramMap.put("sort", sortValue);
//
//        ArrayList<ProductForView> list = productService.getProductListByPriceRange(paramMap);
//        model.addAttribute("list",list) ;
//        model.addAttribute("viewCount", viewCount) ;
//        return "redirect:/product/productlist/price";
//    }



    @GetMapping("/search")
    public String searchView(Model model,
                             @RequestParam String keyword,
                             @RequestParam(defaultValue = "rate", required=false) String sortValue,
                             @RequestParam(defaultValue = "16", required=false) String viewCount){
        return "redirect:/product/productlist/search";
    }

}
