package hiccup.hiccupstore.user.controller;

import hiccup.hiccupstore.user.dto.UserDto;
import hiccup.hiccupstore.user.util.SessionConst;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
public class Homecontroller {

    @GetMapping("/")
    public String home(HttpSession session){

        return "index";

    }

}
