package hiccup.hiccupstore.cart.controller;

import hiccup.hiccupstore.cart.dto.Cart;
import hiccup.hiccupstore.cart.service.CartService;
import hiccup.hiccupstore.commonutil.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;

@RequiredArgsConstructor
@Controller
public class CartController {
    private final CartService cartService;

    @GetMapping("/cart")
    public String getCartList(HttpSession session, Model model){
       //  (usedto 넣기)session.getAttribute("user");

        ArrayList<Cart>cartList = cartService.GetCartListByUserId(1);
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
