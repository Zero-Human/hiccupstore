package hiccup.hiccupstore.user.controller;

import hiccup.hiccupstore.commonutil.file.FileStore;
import hiccup.hiccupstore.commonutil.file.UploadFile;
import hiccup.hiccupstore.user.dto.NoticeDto;
import hiccup.hiccupstore.user.dto.board.Board1vs1Form;
import hiccup.hiccupstore.user.dto.board.User1vs1BoardDto;
import hiccup.hiccupstore.user.service.NoticeService;
import hiccup.hiccupstore.user.util.Paging;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeService noticeService;
    private final Integer pageSize = 10;
    private final FileStore fileStore;

    @GetMapping("/notice")
    public String notice(@RequestParam(defaultValue = "1") Integer page,Model model){

        List<NoticeDto> noticeDtoList = noticeService.getNoticeBoardList(page,pageSize);
        Integer totalNoticeCount = noticeService.getTotalNoticeCount();

        Paging paging = new Paging(totalNoticeCount,page-1,pageSize);

        model.addAttribute("paging",paging);
        model.addAttribute("page",page);
        model.addAttribute("noticedtolist",noticeDtoList);

        return "notice";
    }

    @GetMapping("/noticesee/{noticeBoardId}")
    public String noticeSee(@PathVariable Integer noticeBoardId, @RequestParam(defaultValue = "1") Integer page, Model model){

        NoticeDto noticeBoardSee = noticeService.getNoticeBoardSee(noticeBoardId);

        model.addAttribute("noticedto",noticeBoardSee);
        model.addAttribute("page",page);
        checkIfImageIsOrNot(model, noticeBoardSee);
        return "noticesee";
    }

    @GetMapping("/notice/write")
    public String noticeWrite(){
        return "noticewrite";
    }

    @PostMapping("/notice/noticewrite")
    public String noticeWritePost(@ModelAttribute Board1vs1Form board1vs1Form  , RedirectAttributes redirectAttributes) throws IOException {

        board1vs1Form.setBoardcategory("새소식");
        List<UploadFile> storeImageFiles = fileStore.storeFiles(board1vs1Form.getImageFiles());
        noticeService.SaveNoticeBoard(board1vs1Form,storeImageFiles);

        redirectAttributes.addFlashAttribute("success","success");

        return "redirect:/notice";
    }

    @GetMapping("/notice/delete/{noticeid}")
    public String noticeDelete(@PathVariable Integer noticeid,RedirectAttributes redirectAttributes){

        noticeService.deleteNoticeBoard(noticeid);
        redirectAttributes.addFlashAttribute("del_success","del_success");

        return "redirect:/notice";
    }

    private void checkIfImageIsOrNot(Model model, NoticeDto noticeDto) {
        if(noticeDto.getImagename() != null ){
            model.addAttribute("image",true);
        } else {
            model.addAttribute("image",false);
        }
    }

}
