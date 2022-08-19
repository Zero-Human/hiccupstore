package hiccup.hiccupstore.user.controller.mypage;

import hiccup.hiccupstore.user.dto.UserDto;
import hiccup.hiccupstore.user.service.mypage.MyPageProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@Slf4j
@Controller
@RequiredArgsConstructor
public class MyPageReviewController {

    private final MyPageProductService myPageProductService;

    @GetMapping("/mypagereview")
    public String mypageproduct(Model model, Integer page){

        if(page == null){
            page=1;
        }

        myPageProductService.FindBoard(model,page);

        model.addAttribute("page",page);
        return "mypageproduct";

    }

    @GetMapping("/mypagereview/{boardid}")
    public String MyPageproductsee(@PathVariable Integer boardid, Model model, Integer page){

        if(page == null){
            page=1;
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDto user = (UserDto) authentication.getPrincipal();

        model.addAttribute("page",page);
        model.addAttribute("name",user.getNickName());
        model.addAttribute("boardid",boardid);
        myPageProductService.SeeBoard(model,boardid);

        return "mypageproduct";
    }

    @GetMapping("/mypagereviewdelete")
    public String MyPageproductdelete(Integer boardid, Model model){


        myPageProductService.deleteProductBoard(boardid);
        model.addAttribute("confirm","DEL_OK");

        return "mypageproductsee";
    }

}
