package hiccup.hiccupstore.user.controller.managerpage;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ManagerPageNoticeController {

    @GetMapping("/managerpage/notice")
    public String ManagerpageNotice(){

        return "notice";
    }
}
