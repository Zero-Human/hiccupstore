package hiccup.hiccupstore.user.controller;

import hiccup.hiccupstore.user.dto.LoginUserForm;
import hiccup.hiccupstore.user.dto.UserDto;
import hiccup.hiccupstore.user.service.LoginService;
import hiccup.hiccupstore.user.util.SessionConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
@Slf4j
public class LoginController {

    private final LoginService loginService;

    @GetMapping("/login")
    public String login(@ModelAttribute("User") LoginUserForm loginUserForm,
                        @RequestParam(required = false) String error,
                        @RequestParam(required = false) String exception,
                        Model model){

        model.addAttribute("error",error);
        model.addAttribute("exception",exception);

        return "login";
    }

    @GetMapping("/denied")
    public String denied(@RequestParam(required = false) String exception,Model model){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDto user = (UserDto) authentication.getPrincipal();
        model.addAttribute("username",user.getUserName());
        model.addAttribute("exception",exception);

        return "denied";

    }


}
