package hiccup.hiccupstore.user.controller;

import hiccup.hiccupstore.user.dto.find.FindPasswordByEmailDto;
import hiccup.hiccupstore.user.dto.find.FindPasswordByPhoneDto;
import hiccup.hiccupstore.user.dto.find.FindUserNameByEmailDto;
import hiccup.hiccupstore.user.dto.find.FindUserNameByPhoneDto;
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

    /** 아이디찾기 매서드 */

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

    /** 비밀번호찾기 매서드 */

    @GetMapping("/findpassword")
    public String findpassword(){

        return "findpassword";

    }

    @PostMapping("/findpasswordbyemail")
    @ResponseBody
    public HashMap<String,Object> postFindPasswordByEmail(@RequestBody FindPasswordByEmailDto findPasswordByEmail){

        return findService.findpasswordbyemail(findPasswordByEmail);

    }

    @PostMapping("/findpasswordbyphone")
    @ResponseBody
    public HashMap<String,Object> postFindPasswordByPhone(@RequestBody FindPasswordByPhoneDto findPasswordByPhone){

        return findService.findpasswordByPhone(findPasswordByPhone);

    }

}
