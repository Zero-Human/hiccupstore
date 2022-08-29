package hiccup.hiccupstore.user.controller.mypage;

import hiccup.hiccupstore.user.dto.board.CommentDto;
import hiccup.hiccupstore.user.service.mypage.MyPageReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@Slf4j
@Controller
@RequiredArgsConstructor
public class MyPageReviewController {

    private final MyPageReviewService myPageReviewService;

    @GetMapping("/mypage/mypagereview")
    public String mypageproduct(Model model,@RequestParam(defaultValue = "1") Integer page){

        myPageReviewService.FindBoard(model,page);

        model.addAttribute("page",page);

        return "mypage/mypagereview";

    }

    @PostMapping("/mypage/searchcomment")
    public String MyPageproductsee(@RequestParam Map<String, Object> paramMap, Model model){

        Integer boardid = Integer.valueOf(paramMap.get("boardid").toString());
        List<CommentDto> commentDtos = myPageReviewService.getComment(boardid);
        model.addAttribute("commentdtos",commentDtos);

        if(commentDtos == null || commentDtos.size() == 0){
            return "/layout/mypagereviewcommentnull::#commentnull";
        }

        return "/layout/mypagereviewcomment::#commentTable";

    }


}
