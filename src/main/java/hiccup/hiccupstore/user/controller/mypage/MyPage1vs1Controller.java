package hiccup.hiccupstore.user.controller.mypage;

import hiccup.hiccupstore.user.dto.Board1vs1Form;
import hiccup.hiccupstore.user.repository.ItemRepository;
import hiccup.hiccupstore.user.service.mypage.MyPage1vs1Service;
import hiccup.hiccupstore.user.util.FileStore;
import hiccup.hiccupstore.user.util.UploadFile;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MyPage1vs1Controller {

    private final MyPage1vs1Service myPage1vs1Service;

    @GetMapping("/mypage1vs1")
    public String MyPage1vs1(Model model,Integer page){

        if(page == null){
            page=1;
        }

        myPage1vs1Service.FindBoard(model,page);

        model.addAttribute("page",page);

        return "mypage1vs1";

    }

    @GetMapping("/mypage1vs1write")
    public String MyPage1vs1Write(){
        return "mypage1vs1write";
    }

    @GetMapping("/mypage1vs1see/{boardid}")
    public String MyPage1vs1See(@PathVariable Integer boardid, Model model, Integer page){

        if(page == null){
            page=1;
        }

        model.addAttribute("page",page);
        model.addAttribute("name","오인석");
        myPage1vs1Service.SeeBoard(model,page,2);

        return "mypage1vs1see";
    }

    @PostMapping("/mypage1vs1write")
    public String MyPage1vs1WritePost(@ModelAttribute Board1vs1Form board1vs1Form,Model model) throws IOException {

        log.info("board1vs1Form = {} ",board1vs1Form);
        myPage1vs1Service.SaveBoard1vs1Form(board1vs1Form);
        myPage1vs1Service.FindBoard(model,1);

        return "mypage1vs1";

    }


}
