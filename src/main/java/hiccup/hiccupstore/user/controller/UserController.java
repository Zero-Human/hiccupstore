package hiccup.hiccupstore.user.controller;

import hiccup.hiccupstore.user.dto.JoinFormDto;
import hiccup.hiccupstore.user.dto.LoginUserForm;
import hiccup.hiccupstore.user.service.JoinService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final JoinService joinservice;

    @GetMapping("/login")
    public String login(@ModelAttribute("User") LoginUserForm loginUserForm){
        return "login";
    }

    @GetMapping("/join")
    public String join(){
        return "register";
    }

    @GetMapping("/joinform")
    public String joinForm(@ModelAttribute JoinFormDto joinFormDto){
        return "registerinput";
    }

    @PostMapping("/joincomplete")
    public String joinComplete(@Validated @ModelAttribute JoinFormDto joinFormDto,BindingResult bindingResult){

        /** 글로벌 오류 비밀번호 == 비밀번호확인 */
        if(!joinFormDto.getPassword().equals(joinFormDto.getPasswordConfirm())){

            bindingResult.reject("NotMatchedPassWord",null,"비밀번호를 일치시켜주세요.");

        }

        /** 필드오류 */
        if(bindingResult.hasErrors()){

            log.info("bindingResult = {} ",bindingResult);

            return "registerinput";

        }

        System.out.println(joinFormDto);
        //Integer save = joinservice.userSave(joinFormDto);

        return "registercomplete";

    }

}
