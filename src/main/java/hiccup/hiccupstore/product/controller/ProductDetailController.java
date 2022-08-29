package hiccup.hiccupstore.product.controller;

import hiccup.hiccupstore.board.dto.Image;
import hiccup.hiccupstore.board.dto.ProductQnA;
import hiccup.hiccupstore.board.dto.Review;
import hiccup.hiccupstore.commonutil.FindSecurityContext;
import hiccup.hiccupstore.commonutil.security.service.Oauth2UserContext;
import hiccup.hiccupstore.product.dto.Product;
import hiccup.hiccupstore.product.dto.page.Page;
import hiccup.hiccupstore.product.dto.page.ViewCriteria;
import hiccup.hiccupstore.product.service.ProductService;
import hiccup.hiccupstore.board.service.BoardService ;
import hiccup.hiccupstore.user.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductDetailController {
    //TODO : 상품 리뷰 & QnA 정보 반환하는 BoardSevice 연결
    //TODO : 상품 리뷰 & QnA 리스트 -> Pagination 생성 필요 ( [리뷰&문의] 절댓값 : 1페이지 당 10개 / 최신순 정렬 ++ [리뷰] 선택값 : 전체 & 포토 )
    //TODO : Security 활용 AJAX 적용

    private final ProductService productService ;
    private final BoardService boardService ;
    private final FindSecurityContext findSecurityContext;

//    @GetMapping("/create") // 상품등록 폼 페이지 이동
//    public void createForm (){
//    }
//    @PostMapping("/create") // 상품등록 시
//    public String createComplete(Product product,
//                                 Model model){
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

        UserDto user = findSecurityContext.getUserDto();


        Product product = productService.getProductById(Integer.parseInt(productId));
        model.addAttribute("user", user);
        model.addAttribute("product", product);
        return "/product/detail";
    }

    private List<HashMap<Review, ArrayList<Image>>> reviewList = new ArrayList<>();
//    @ResponseBody
    @GetMapping("/review")
    public String reviewLoadAjax(@RequestParam String productId, Model model){

        UserDto user = findSecurityContext.getUserDto();

        model.addAttribute("user", user);
        model.addAttribute("reviewList", reviewList) ;
        return "/product/productReview" ;
    }
    @GetMapping("/photo-review")
    public String photoReviewLoadAjax(@RequestParam String productId, Model model){

        UserDto user = findSecurityContext.getUserDto();

        model.addAttribute("user", user);
        model.addAttribute("reviewList", reviewList) ;
        return "/product/productReview" ;
    }
    @PostMapping("/review")
    public String reviewUpdateAjax(@RequestParam Map<String, Object> PostReview, Model model){
        UserDto user = findSecurityContext.getUserDto();

        model.addAttribute("user", user);
        model.addAttribute("reviewList", reviewList) ;
        return "/product/productReview" ;
    }
//    @ResponseBody
    @GetMapping("/qna")
    public String qnaLoadAjax(@RequestParam String productId, Model model){
        List<HashMap<ProductQnA, ArrayList<Image>>> qnaList = new ArrayList<>();

        return "/product/detail :: #qna" ;
    }
    @GetMapping("/qna-detail")
    public String qnaDetailLoadAjax(@RequestParam String boardId, Model model){
        return
    }

}

