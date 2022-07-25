package hiccup.hiccupstore.cart.controller;

import hiccup.hiccupstore.cart.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@RequiredArgsConstructor
@Controller
public class CartController {
    private final CartService cartService;

}
