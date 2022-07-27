package hiccup.hiccupstore.user.controller;

import hiccup.hiccupstore.user.dto.FindUserNameByEmailCompletedDto;
import hiccup.hiccupstore.user.dto.FindUserNameByEmailDto;
import hiccup.hiccupstore.user.dto.FindUserNameByPhoneDto;
import hiccup.hiccupstore.user.service.FindService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.HashMap;

@Controller
@Slf4j
@RequiredArgsConstructor
public class FindController {

    private final FindService findService;

    @GetMapping("/findusername")
    public String findUserName(){

        return "findusername";

    }


    @PostMapping("/findusernamebyemail")
    @ResponseBody
    public HashMap<String,Object> postFindUserNameByEmail(@RequestBody FindUserNameByEmailDto findUserNameByEmail){

        return findService.findusernamebyemail(findUserNameByEmail);

    }

    @PostMapping("/findusernamebyphone")
    @ResponseBody
    public HashMap<String,Object> postFindUserNameByPhone(@RequestBody FindUserNameByPhoneDto findUserNameByPhone){

        return findService.findusernameByPhone(findUserNameByPhone);

    }


    @PostMapping("/findpassword")
    @ResponseBody
    public String findPassword(){

        return "true";

    }


}
