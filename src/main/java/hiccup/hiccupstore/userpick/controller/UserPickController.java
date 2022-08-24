package hiccup.hiccupstore.userpick.controller;

import hiccup.hiccupstore.user.dto.UserDto;
import hiccup.hiccupstore.user.security.service.Oauth2UserContext;
import hiccup.hiccupstore.userpick.dto.UserPick;
import hiccup.hiccupstore.userpick.service.UserPickService;
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
public class UserPickController {
    private final UserPickService userPickService;

    @GetMapping("/userPick")

    public String getCartList(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDto user;
        try {
            user = (UserDto) authentication.getPrincipal();
        } catch (Exception exce){
            user = ((Oauth2UserContext) authentication.getPrincipal()).getAccount();
        }
        ArrayList<UserPick> ProductList = userPickService.findAllByUserId(user.getUserId());
        model.addAttribute("productList", ProductList);
        return "/mypagewishlist";
    }
}
