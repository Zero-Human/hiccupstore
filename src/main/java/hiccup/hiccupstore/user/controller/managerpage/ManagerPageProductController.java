package hiccup.hiccupstore.user.controller.managerpage;

import hiccup.hiccupstore.user.dto.UserDto;
import hiccup.hiccupstore.user.service.managerpage.ManagerPageProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@Slf4j
@RequiredArgsConstructor
public class ManagerPageProductController {

    private final ManagerPageProductService managerPageProductService;

    @GetMapping("/managerpageproduct")
    public String managerpage1vs1(Model model, Integer page){

        if(page == null){
            page=1;
        }

        managerPageProductService.findUserProductBoardAll(model,page);

        model.addAttribute("page",page);

        return "managerpageproduct";
    }


    @GetMapping("/managerpageproductsee/{boardid}")
    public String MyPageProductSee(@PathVariable Integer boardid, Model model, Integer page){

        if(page == null){
            page=1;
        }

        model.addAttribute("page",page);
        model.addAttribute("boardid",boardid);
        //model.addAttribute("name",user.getNickName());
        managerPageProductService.SeeBoard(model,boardid);

        return "managerpageproductsee";
    }


    @GetMapping("/managerpageproductseeandanswer/{boardid}")
    public String managerpage1vs1seeandanswer(@PathVariable Integer boardid, Model model, Integer page){

        if(page == null){
            page=1;
        }

        model.addAttribute("page",page);
        model.addAttribute("boardid",boardid);
        //model.addAttribute("name",user.getNickName());
        managerPageProductService.SeeBoard(model,boardid);

        return "managerpageproductseeandanswer";

    }

    @PostMapping("/managerpageproductwrite")
    public String manager1vs1write(String boardcontent,Integer boardid,Model model){


        System.out.println("잘들어왓나 = " + boardcontent);
        System.out.println("잘들어왓나 = " + boardid);
//        log.info("board1vs1Form = {} ",board1vs1Form);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        UserDto user = (UserDto) authentication.getPrincipal();

        managerPageProductService.SaveProductUserAnswer(boardid,boardcontent);

        managerPageProductService.findUserProductBoardAll(model,1);

        model.addAttribute("page",1);

        return "managerpageproduct";
    }

}
