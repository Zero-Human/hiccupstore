package hiccup.hiccupstore.user.controller.mypage;

import hiccup.hiccupstore.user.dto.Board1vs1Form;
import hiccup.hiccupstore.user.dto.UserDto;
import hiccup.hiccupstore.user.security.service.Oauth2UserContext;
import hiccup.hiccupstore.user.service.mypage.MyPage1vs1Service;
import hiccup.hiccupstore.user.util.FileStore;
import hiccup.hiccupstore.user.util.SessionConst;
import hiccup.hiccupstore.user.util.UploadFile;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MyPage1vs1Controller {

    private final MyPage1vs1Service myPage1vs1Service;
    private final FileStore fileStore;

    @GetMapping("/mypage1vs1")
    public String MyPage1vs1(Model model, Integer page){

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
        myPage1vs1Service.SeeBoard(model,boardid);

        return "mypage1vs1see";
    }

    @PostMapping("/mypage1vs1write")
    public String MyPage1vs1WritePost(@ModelAttribute Board1vs1Form board1vs1Form,Model model,HttpSession session) throws IOException {


        log.info("board1vs1Form = {} ",board1vs1Form);
        UserDto user = (UserDto) session.getAttribute(SessionConst.LOGIN_MEMBER);

        List<UploadFile> storeImageFiles = fileStore.storeFiles(board1vs1Form.getImageFiles());
        log.info("storeImageFiles = {} ",storeImageFiles);

        myPage1vs1Service.SaveBoard1vs1Form(board1vs1Form,storeImageFiles);

        myPage1vs1Service.FindBoard(model,1);

        return "mypage1vs1";

    }

    @ResponseBody
    @GetMapping("/testimage/{filename}")
    public Resource dowoloadImage(@PathVariable String filename) throws MalformedURLException {

        return new UrlResource("file:"+fileStore.getFullPath(filename));

    }


}
