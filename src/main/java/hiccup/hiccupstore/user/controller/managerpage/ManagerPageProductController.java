package hiccup.hiccupstore.user.controller.managerpage;

import hiccup.hiccupstore.user.service.managerpage.ManagerPageProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Slf4j
@RequiredArgsConstructor
public class ManagerPageProductController {

    private final ManagerPageProductService managerPageProductService;

    @GetMapping("/managerpage/managerpageproduct")
    public String managerpage1vs1(Model model,@RequestParam(defaultValue = "1") Integer page){

        managerPageProductService.findUserProductBoardAll(model,page);
        model.addAttribute("page",page);

        return "managerpage/managerpageproduct";
    }


    @GetMapping("/managerpage/managerpageproductsee/{boardid}")
    public String MyPageProductSee(@PathVariable Integer boardid, Model model,@RequestParam(defaultValue = "1") Integer page){

        model.addAttribute("page",page);
        model.addAttribute("boardid",boardid);
        managerPageProductService.SeeBoard(model,boardid);

        return "managerpage/managerpageproductsee";
    }


    @GetMapping("/managerpage/managerpageproductseeandanswer/{boardid}")
    public String managerpage1vs1seeandanswer(@PathVariable Integer boardid, Model model,@RequestParam(defaultValue = "1") Integer page){

        model.addAttribute("page",page);
        model.addAttribute("boardid",boardid);
        managerPageProductService.SeeBoard(model,boardid);

        return "managerpage/managerpageproductseeandanswer";

    }

    @PostMapping("/managerpage/managerpageproductwrite")
    public String manager1vs1write(String boardcontent,Integer boardid,Model model){

        managerPageProductService.SaveProductUserAnswer(boardid,boardcontent);

        managerPageProductService.findUserProductBoardAll(model,1);

        model.addAttribute("page",1);

        return "managerpage/managerpageproduct";
    }

}
