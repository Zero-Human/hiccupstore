package hiccup.hiccupstore.user.controller;

import hiccup.hiccupstore.commonutil.file.FileStore;
import hiccup.hiccupstore.commonutil.file.UploadFile;
import hiccup.hiccupstore.user.dto.NoticeDto;
import hiccup.hiccupstore.user.dto.NoticeUpdateDto;
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
    public String notice(@RequestParam(defaultValue = "1") Integer page,
                         @RequestParam(required = false) String SearchNoticeContent,
                         @RequestParam(required = false) String SearchNoticeCategory,
                         Model model){

        if(SearchNoticeCategory != null && SearchNoticeContent != null){
            Integer totalNoticeCount = noticeService.SearchNoticeBoardCountBySearchNoticeContent(SearchNoticeCategory, SearchNoticeContent);
            List<NoticeDto> noticeDtoList = noticeService.SearchNoticeBoardBySearchNoticeContent(SearchNoticeCategory, SearchNoticeContent, page-1, pageSize);

            Paging paging = new Paging(totalNoticeCount,page-1,pageSize);

            model.addAttribute("paging",paging);
            model.addAttribute("page",page);
            model.addAttribute("noticedtolist",noticeDtoList);
            model.addAttribute("noticecategory",SearchNoticeCategory);
            model.addAttribute("noticecontent",SearchNoticeContent);

            return "notice";
        }

        List<NoticeDto> noticeDtoList = noticeService.getNoticeBoardList(page,pageSize);
        Integer totalNoticeCount = noticeService.getTotalNoticeCount();

        Paging paging = new Paging(totalNoticeCount,page-1,pageSize);

        model.addAttribute("paging",paging);
        model.addAttribute("page",page);
        model.addAttribute("noticedtolist",noticeDtoList);
        model.addAttribute("noticecategory","boardtitle");

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

    @GetMapping("/notice/update/{noticeid}")
    public String noticeUpdate(@PathVariable Integer noticeid,Model model){

        NoticeDto noticeBoard = noticeService.getNoticeBoardSee(noticeid);
        model.addAttribute("noticedto",noticeBoard);
        if(noticeBoard.getImagename() != null ){
            model.addAttribute("image",true);
        } else {
            model.addAttribute("image",false);
        }

        //        noticeService.updateNoticeBoard(noticeid);
//        redirectAttributes.addFlashAttribute("update_success","update_success");

        return "noticeupdate";
    }

    @PostMapping("/notice/update/{noticeid}")
    public String noticeUpdatePost(@PathVariable Integer noticeid, @ModelAttribute NoticeUpdateDto noticeUpdateDto,RedirectAttributes redirectAttributes) throws IOException {

        List<UploadFile> storeImageFiles = fileStore.storeFiles(noticeUpdateDto.getImageFiles());

        if(noticeUpdateDto.getDeleteImageFiles() == null){
            /** 이미지 파일 교체*/
            if(!noticeUpdateDto.getImageFiles().get(0).getName().equals("")){
                if(noticeUpdateDto.getImagename() == null){
                    noticeService.updateNoticeBoard(noticeid,noticeUpdateDto,storeImageFiles);
                    return "redirect:/notice";
                }
                redirectAttributes.addFlashAttribute("maximage1","maximage1");
                return "redirect:/notice/update/"+noticeid;
            } else{
                noticeService.updateNoticeBoardNotImageUpdate(noticeid,noticeUpdateDto);
            }
        } else {
            if(noticeUpdateDto.getImageFiles().get(0).getName().equals("")){
                noticeService.updateNoticeBoard(noticeid,noticeUpdateDto,storeImageFiles);
            } else{
                fileStore.deleteFile(fileStore.getFullPath(noticeUpdateDto.getDeleteImageFiles()));
                noticeService.updateNoticeBoardDeleteImageUpdate(noticeid,noticeUpdateDto);
            }
        }
        redirectAttributes.addFlashAttribute("update_success","update_success");
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
