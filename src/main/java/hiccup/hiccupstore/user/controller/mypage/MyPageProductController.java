package hiccup.hiccupstore.user.controller.mypage;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
@RequiredArgsConstructor
public class MyPageProductController {

    @GetMapping("/mypageproduct")
    public String mypageproduct(){

        return "mypageproduct";

    }


}
