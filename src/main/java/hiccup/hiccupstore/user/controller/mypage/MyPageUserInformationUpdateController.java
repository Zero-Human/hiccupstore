package hiccup.hiccupstore.user.controller.mypage;


import hiccup.hiccupstore.user.dto.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

@Controller
@Slf4j
public class MyPageUserInformationUpdateController {

    @GetMapping("/userinformationupadte")
    public String MyPageUserInformationUpdate(){

        return "userinformationupadte";

    }

    @PostMapping("/userinformationupadte")
    public String MyPageUserInformationUpdatePost(HttpSession session, String password, Model model){

        //Object user = session.getAttribute("user");

        UserDto userDto = UserDto.builder().userId(1).userName("ssoboro").nickName("오인석").address("햄스터동네").birth("93/06/18").
                email("ssoboro1@gmail.com").phone("01085264714").password("4863527wyc").build();

        if(password.equals("4863527wyc")){
            model.addAttribute("userdto",userDto);
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
