package hiccup.hiccupstore.user.controller.mypage;

import hiccup.hiccupstore.user.dto.UserDto;
import hiccup.hiccupstore.user.security.service.Oauth2UserContext;
import hiccup.hiccupstore.user.service.mypage.MyPage1vs1Service;
import hiccup.hiccupstore.user.service.mypage.MyPageProductService;
import hiccup.hiccupstore.user.util.FileStore;
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

@Slf4j
@Controller
@RequiredArgsConstructor
public class MyPageProductController {

    private final MyPageProductService myPageProductService;

    @GetMapping("/mypageproduct")
    public String mypageproduct(Model model,@RequestParam(defaultValue = "1") Integer page){

        myPageProductService.FindBoard(model,page);
        model.addAttribute("page",page);

        return "mypageproduct";

    }

    @GetMapping("/mypageproductsee/{boardid}")
    public String MyPageproductsee(@PathVariable Integer boardid, Model model,@RequestParam(defaultValue = "1") Integer page){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDto user;
        try {
            user = (UserDto) authentication.getPrincipal();
        } catch (Exception exce){
            user = ((Oauth2UserContext) authentication.getPrincipal()).getAccount();
            System.out.println("classcastexception도 잡앗지롱");
        }

        model.addAttribute("page",page);
        model.addAttribute("name",user.getNickName());
        model.addAttribute("boardid",boardid);
        myPageProductService.SeeBoard(model,boardid);

        return "mypageproductsee";
    }

    @PostMapping("/mypageproductdelete")
    public String MyPageproductdelete(Integer boardid, Model model){

        myPageProductService.deleteProductBoard(boardid);
        model.addAttribute("confirm","DEL_OK");
        myPageProductService.FindBoard(model,1);
        model.addAttribute("page",1);

        return "mypageproduct";
    }



}
