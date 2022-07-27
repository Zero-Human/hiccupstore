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

//        UserDto userDto = UserDto.builder().userId(1).userName("ssoboro").nickName("오인석").address("햄스터동네").birth("93/06/18").
//                email("ssoboro1@gmail.com").phone("01085264714").password("4863527wyc").build();
//
//        session.setAttribute(SessionConst.LOGIN_MEMBER,userDto);

        return "index";

    }

}
