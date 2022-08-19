package hiccup.hiccupstore.user.controller.managerpage;

import hiccup.hiccupstore.user.dto.UserDto;
import hiccup.hiccupstore.user.service.managerpage.ManagerPage1vs1Service;
import hiccup.hiccupstore.user.util.SessionConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;


@Controller
@Slf4j
@RequiredArgsConstructor
public class ManagerPage1vs1Controller {

    private final ManagerPage1vs1Service managerPage1vs1Service;

    @GetMapping("/managerpage1vs1")
    public String managerpage1vs1(Model model, Integer page){

        if(page == null){
            page=1;
        }

        managerPage1vs1Service.findUser1vs1BoardAll(model,page);

        model.addAttribute("page",page);

        return "managerpage1vs1";
    }


    @GetMapping("/managerpage1vs1see/{boardid}")
    public String MyPage1vs1See(@PathVariable Integer boardid, Model model, Integer page,HttpSession session){

        if(page == null){
            page=1;
        }

        UserDto user = (UserDto) session.getAttribute(SessionConst.LOGIN_MEMBER);
        model.addAttribute("page",page);
        model.addAttribute("boardid",boardid);
        //model.addAttribute("name",user.getNickName());
        managerPage1vs1Service.SeeBoard(model,boardid);

        return "managerpage1vs1see";
    }


    @GetMapping("/managerpage1vs1seeandanswer/{boardid}")
    public String managerpage1vs1seeandanswer(@PathVariable Integer boardid, Model model, Integer page){

        if(page == null){
            page=1;
        }

        model.addAttribute("page",page);
        model.addAttribute("boardid",boardid);
        //model.addAttribute("name",user.getNickName());
        managerPage1vs1Service.SeeBoard(model,boardid);

        return "managerpage1vs1seeandanswer";

    }

    @PostMapping("/managerpage1vs1write")
    public String manager1vs1write(String boardcontent,Integer boardid,Model model){


        System.out.println("잘들어왓나 = " + boardcontent);
        System.out.println("잘들어왓나 = " + boardid);
//        log.info("board1vs1Form = {} ",board1vs1Form);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        UserDto user = (UserDto) authentication.getPrincipal();

        managerPage1vs1Service.Save1vs1UserAnswer(boardid,user,boardcontent);

        managerPage1vs1Service.findUser1vs1BoardAll(model,1);

        model.addAttribute("page",1);

        return "managerpage1vs1";
    }

}
