package hiccup.hiccupstore.user.controller.managerpage;

import hiccup.hiccupstore.user.dto.UserDto;
import hiccup.hiccupstore.commonutil.security.service.Oauth2UserContext;
import hiccup.hiccupstore.user.service.managerpage.ManagerPage1vs1Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@Slf4j
@RequiredArgsConstructor
public class ManagerPage1vs1Controller {

    private final ManagerPage1vs1Service managerPage1vs1Service;

    @GetMapping("/managerpage/managerpage1vs1")
    public String managerpage1vs1(Model model,@RequestParam(defaultValue = "1") Integer page){

        managerPage1vs1Service.findUser1vs1BoardAll(model,page);

        model.addAttribute("page",page);

        return "managerpage/managerpage1vs1";
    }


    @GetMapping("/managerpage/managerpage1vs1see/{boardid}")
    public String MyPage1vs1See(@PathVariable Integer boardid, Model model,@RequestParam(defaultValue = "1") Integer page){

        managerPage1vs1Service.SeeBoard(model,boardid);
        model.addAttribute("page",page);
        model.addAttribute("boardid",boardid);

        return "managerpage/managerpage1vs1see";
    }


    @GetMapping("/managerpage/managerpage1vs1seeandanswer/{boardid}")
    public String managerpage1vs1seeandanswer(@PathVariable Integer boardid, Model model,@RequestParam(defaultValue = "1") Integer page){

        managerPage1vs1Service.SeeBoard(model,boardid);
        model.addAttribute("page",page);
        model.addAttribute("boardid",boardid);

        return "managerpage/managerpage1vs1seeandanswer";

    }

    @PostMapping("/managerpage/managerpage1vs1write")
    public String manager1vs1write(String boardcontent,Integer boardid,Model model){


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDto user;
        try {
            user = (UserDto) authentication.getPrincipal();
        } catch (Exception exce){
            user = ((Oauth2UserContext) authentication.getPrincipal()).getAccount();
            System.out.println("classcastexception도 잡앗지롱");
        }

        managerPage1vs1Service.Save1vs1UserAnswer(boardid,user,boardcontent);
        managerPage1vs1Service.findUser1vs1BoardAll(model,1);

        model.addAttribute("page",1);

        return "managerpage/managerpage1vs1";
    }

}
