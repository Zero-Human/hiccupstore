package hiccup.hiccupstore.cart.controller;

import hiccup.hiccupstore.cart.dto.CartForm;
import hiccup.hiccupstore.cart.service.CartService;
import hiccup.hiccupstore.user.dto.UserDto;
import hiccup.hiccupstore.user.security.service.Oauth2UserContext;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ModelAttribute;
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
    public Boolean deleteCart(@RequestBody HashMap<String, Integer> productMap){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDto user;
        try {
            user = (UserDto) authentication.getPrincipal();
        } catch (Exception exce){
            user = ((Oauth2UserContext) authentication.getPrincipal()).getAccount();
        }
        Integer productId = productMap.get("productId");
        if(null !=user.getUserId() && null != productId ){
            cartService.deleteCartByProductId(productId, user.getUserId());
            return true;
        }
        return false;
    }
    @PostMapping("/api/cart/deleteAll")
    public boolean deleteAllCart(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDto user;
        try {
            user = (UserDto) authentication.getPrincipal();
        } catch (Exception exce){
            user = ((Oauth2UserContext) authentication.getPrincipal()).getAccount();
        }
        if(null !=user.getUserId()) {
            cartService.deleteAllCart(user.getUserId());
            return true;
        }
        return false;
    }
    @PostMapping("/api/cart/modify")
    public Boolean modifyCart(@ModelAttribute CartForm cartForm){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDto user;
        try {
            user = (UserDto) authentication.getPrincipal();
        } catch (Exception exce){
            user = ((Oauth2UserContext) authentication.getPrincipal()).getAccount();
        }
        Integer productId = cartForm.getProductId();
        Integer quantity = cartForm.getQuantity();
        if(null !=user.getUserId() && null != productId ){
            cartService.modifyQuantity(productId,quantity);
            return true;
        }
        return false;
    }
    @PostMapping("/api/cart/insert")
    public Boolean insert( @ModelAttribute CartForm cartForm)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDto user;
        try {
            user = (UserDto) authentication.getPrincipal();
        } catch (Exception exce){
            user = ((Oauth2UserContext) authentication.getPrincipal()).getAccount();
        }
        // TODO 나중에 등록하는 것도 추가해야한다.
        cartForm.setProductId(user.getUserId());
        if(null !=user.getUserId() && null != cartForm ){
            cartService.insert(cartForm);
            return true;
        }
        return false;
    }
}
