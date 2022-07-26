package hiccup.hiccupstore.user.controller;

import hiccup.hiccupstore.user.dto.LoginUserForm;
import hiccup.hiccupstore.user.dto.UserDto;
import hiccup.hiccupstore.user.service.LoginService;
import hiccup.hiccupstore.user.util.SessionConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
@Slf4j
public class LoginController {

    private final LoginService loginService;

    @PostMapping("/login")
    public String login(HttpSession session,
                        @Validated @ModelAttribute("User") LoginUserForm loginUserForm,
                        BindingResult bindingResult){

        log.info("loginuserform = " + loginUserForm);

        if(bindingResult.hasErrors()){

            log.info("BindingResult = {} ", bindingResult);

            return "login";
        }

        UserDto user = loginService.getUser(loginUserForm);

        if(user != null){

            log.info("로그인 성공된 아이디 = {} ", user);
            session.setAttribute(SessionConst.LOGIN_MEMBER,user);

        } else {

            bindingResult.reject("LoginFail","아이디가 없거나 비밀번호가 일치하지 않습니다.");
            return "login";

        }

        return "redirect:/";

    }

}
