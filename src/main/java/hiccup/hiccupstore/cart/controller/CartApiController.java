package hiccup.hiccupstore.cart.controller;

import hiccup.hiccupstore.cart.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpSession;
import java.util.HashMap;

@RequiredArgsConstructor
@RestController
public class CartApiController {
    private final CartService cartService;
    @PostMapping("/api/cart/delete")
    public Boolean deleteCart(HttpSession session, @RequestBody HashMap<String, Integer> productMap){
        Integer userId = (Integer) session.getAttribute("User");
        Integer productId = productMap.get("productId");
        if(null !=userId && null != productId ){
            cartService.deleteCartByProductId(productId,userId);
            return true;
        }
        return false;
    }
    @PostMapping("/api/cart/deleteAll")
    public boolean deleteAllCart(HttpSession session){
        Integer userId = (Integer)session.getAttribute("User");
        if(null !=userId) {
            cartService.deleteAllCart(userId);
            return true;
        }
        return false;
    }
    @PostMapping("/api/cart/modify")
    public Boolean modifyCart(HttpSession session, @RequestBody HashMap<String, Integer> productMap){
        Integer userId = (Integer) session.getAttribute("User");
        Integer productId = productMap.get("productId");
        Integer quantity = productMap.get("quantity");
        if(null !=userId && null != productId ){
            cartService.modifyQuantity(productId,quantity);
            return true;
        }
        return false;
    }
    // TODO 나중에 등록하는 것도 추가해야한다.
}
