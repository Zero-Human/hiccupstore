package hiccup.hiccupstore.user.controller.mypage;


import hiccup.hiccupstore.user.dto.UserDto;
import hiccup.hiccupstore.user.util.SessionConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

@Controller
@Slf4j
@RequiredArgsConstructor
public class MyPageUserInformationUpdateController {

    @GetMapping("/userinformationupadte")
    public String MyPageUserInformationUpdate(){

        return "userinformationupadte";

    }

    @PostMapping("/userinformationupadte")
    public String MyPageUserInformationUpdatePost(HttpSession session, String password, Model model){

        UserDto user =(UserDto) session.getAttribute(SessionConst.LOGIN_MEMBER);

        if(password.equals("4863527wyc")){
            model.addAttribute("userdto",user);
            String[] addresssplit = user.getAddress().split("/");
            model.addAttribute("addresssplit",addresssplit);



            return "userinformationupadteform";
        }

        return "redirect:/userinformationupadte";

    }

    @GetMapping("/userwithdrawal")
    public String userWithdrawal(){

        return "userwithdrawal";
    }

    @PostMapping("/userwithdrawal")
    public String userWithdrawalPost(String password){

        if(password.equals("4863527wyc")){
            return "redirect:/";
        }

        return "userwithdrawal";

    }
}
