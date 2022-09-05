package hiccup.hiccupstore.product.controller;

import hiccup.hiccupstore.product.dto.Product;
import hiccup.hiccupstore.product.dto.ProductImage;
import hiccup.hiccupstore.product.dto.page.Page;
import hiccup.hiccupstore.product.dto.page.ViewCriteria;
import hiccup.hiccupstore.product.service.ProductService;
import hiccup.hiccupstore.product.util.ImageType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Controller
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductDetailController {
    //TODO : 상품 리뷰 & QnA 정보 반환하는 BoardSevice 연결
    //TODO : 상품 리뷰 & QnA 리스트 -> Pagination 생성 필요 ( [리뷰&문의] 절댓값 : 1페이지 당 10개 / 최신순 정렬 ++ [리뷰] 선택값 : 전체 & 포토 )
    //TODO : Security 활용 AJAX 적용

    private final ProductService productService ;
    @GetMapping("/create") // 상품등록 폼 페이지 이동
    public void createForm (){
    }
//    @PostMapping("/create") // 상품등록 시
//    public String createComplete(Product product, Model model){
//        productService.addProduct(product);
//        int addedProductId = productService.getProductIdByName(product.getProductName());
//        Product addedProduct = productService.getProductById(addedProductId);
//        return addedProduct.getDetailLink() ;
//    }

    @RequestMapping("/delete")
    public String deleteProduct(Page page,
                                @RequestParam(name = "pid") String productId){
        productService.delProduct(Integer.parseInt(productId));
        return page.getListLink();
    }
//    @GetMapping("/edit")
//    public String editForm(Model model,
//                           @RequestParam(name = "pid") String productId){
//        model.addAttribute( "product", productService.getProductById(Integer.parseInt(productId)) );
//        return "product/edit";
//    }
//    @PostMapping("/edit")
//    public  String editComplete(Product product){
//        productService.editProduct(product);
//        return product.getDetailLink() ;
//    }

    //TODO : User & Security 적용 이후, 계정 정보 랜더링 시킨 다음에 detail.html ThymeLeaf 수정하기
    @GetMapping("/detail")
    public String detailView(Model model,
                             @RequestParam(name = "pid") String productId){
        Product product = productService.getProductById(Integer.parseInt(productId));
        ArrayList<ProductImage> productImages = productService.getProductImageListById(Integer.parseInt(productId));
        model.addAttribute("product", product);
        model.addAttribute("productImage",productImages.stream().
                filter(Image -> Image.getImageType().equals(ImageType.PRODUCT.getValue())).
                findFirst().orElse(new ProductImage()).getImageName());
        model.addAttribute("detailImage",productImages.stream().
                filter(Image -> Image.getImageType().equals(ImageType.DETAIL.getValue())).
                findFirst().orElse(new ProductImage()).getImageName());
        return "/product/detail";
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

