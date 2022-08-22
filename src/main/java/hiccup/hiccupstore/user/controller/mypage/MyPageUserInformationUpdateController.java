package hiccup.hiccupstore.user.controller.mypage;


import hiccup.hiccupstore.user.dto.JoinFormDto;
import hiccup.hiccupstore.user.dto.UserDto;
import hiccup.hiccupstore.user.security.service.Oauth2UserContext;
import hiccup.hiccupstore.user.service.mypage.MyPageUserInformationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.http.HttpResponse;


@Controller
@Slf4j
@RequiredArgsConstructor
public class MyPageUserInformationUpdateController {

    private final PasswordEncoder passwordEncoder;
    private final MyPageUserInformationService myPageUserInformationService;

    @GetMapping("/userinformationupadte")
    public String MyPageUserInformationUpdate(){

        return "userinformationupadte";

    }

    @PostMapping("/userinformationupadte")
    public String MyPageUserInformationUpdatePost(String password, Model model){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDto user;
        try {
            user = (UserDto) authentication.getPrincipal();
        } catch (Exception exce){
            user = ((Oauth2UserContext) authentication.getPrincipal()).getAccount();
            System.out.println("classcastexception도 잡앗지롱");
        }

        if(passwordEncoder.matches(password,user.getPassword())){

            model.addAttribute("userdto",user);
            String[] addresssplit = user.getAddress().split("/");
            model.addAttribute("addresssplit",addresssplit);

            return "userinformationupadteform";

        }

        return "redirect:/userinformationupadte";

    }

    @PostMapping("/updateinformation")
    public String updateinformation(JoinFormDto joinFormDto,RedirectAttributes redirectAttributes){

        myPageUserInformationService.MyPageUserInformationUpdate(joinFormDto);
        redirectAttributes.addFlashAttribute("msg","update_ok");

        return "redirect:/mypage";
    }

    @GetMapping("/userwithdrawal")
    public String userWithdrawal(){

        return "userwithdrawal";
    }

    @PostMapping("/userwithdrawal")
    public String userWithdrawalPost(HttpServletRequest request,
                                     HttpServletResponse response,
                                     RedirectAttributes redirectAttributes,
                                     String password){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDto user;
        try {
            user = (UserDto) authentication.getPrincipal();
        } catch (Exception exce){
            user = ((Oauth2UserContext) authentication.getPrincipal()).getAccount();
            System.out.println("classcastexception도 잡앗지롱");
        }

        if(passwordEncoder.matches(password,user.getPassword())){

            myPageUserInformationService.MyPageUserDeleted(user.getUserName());

            if(authentication != null){
                new SecurityContextLogoutHandler().logout(request,response,authentication);
            }
            redirectAttributes.addFlashAttribute("msg","DEL_OK");
            return "redirect:/";
        }

        return "userwithdrawal";

    }

}
