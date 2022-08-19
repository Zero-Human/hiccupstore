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

//    @PostMapping("/login")
//    public String login(HttpSession session,
//                        @Validated @ModelAttribute("User") LoginUserForm loginUserForm,
//                        BindingResult bindingResult){
//
//        log.info("loginuserform = " + loginUserForm);
//
////        if(bindingResult.hasErrors()){
////
////            log.info("BindingResult = {} ", bindingResult);
////            return "login";
////
////        }
//
//        /** 아이디가 있고 비밀번호가있으면 user를 반환하고
//         * 아이디가 없거나 비밀번호가 틀리면 bindingresult에
//         * reject객체가 담겨서 나오고 null을 반환한다.*/
//        UserDto user = loginService.getUser(loginUserForm,bindingResult);
//
//        if(user == null){
//
//            log.info("로그인 실패 = {} ", user);
//            return "login";
//
//        }
//
//        log.info("로그인 성공된 아이디 = {} ", user);
//        session.setAttribute(SessionConst.LOGIN_MEMBER,user);
//
//        return "redirect:/";
//
//    }

    @GetMapping("/denied")
    public String denied(@RequestParam(required = false) String exception,Model model){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDto user = (UserDto) authentication.getPrincipal();
        model.addAttribute("username",user.getUserName());
        model.addAttribute("exception",exception);

        return "denied";

    }


}
