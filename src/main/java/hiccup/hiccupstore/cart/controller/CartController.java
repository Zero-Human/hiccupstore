package hiccup.hiccupstore.cart.controller;

import hiccup.hiccupstore.cart.dto.Cart;
import hiccup.hiccupstore.cart.service.CartService;
import hiccup.hiccupstore.user.dto.UserDto;
import hiccup.hiccupstore.user.security.service.Oauth2UserContext;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;

@RequiredArgsConstructor
@Controller
public class CartController {
    private final CartService cartService;

    @GetMapping("/cart")
    public String getCartList(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDto user;
        try {
            user = (UserDto) authentication.getPrincipal();
        } catch (Exception exce){
            user = ((Oauth2UserContext) authentication.getPrincipal()).getAccount();
        }
        ArrayList<Cart>cartList = cartService.GetCartListByUserId(user.getUserId());
        int price = cartService.sumPrice(cartList);
        model.addAttribute("productList", cartList);
        model.addAttribute("price", price);
        return "cart";
    }
    @GetMapping("/test")
    public String test(HttpSession session, Model model){
        return "Form";
    }
}
