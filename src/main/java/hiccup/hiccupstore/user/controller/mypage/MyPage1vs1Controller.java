package hiccup.hiccupstore.user.controller.mypage;

import hiccup.hiccupstore.commonutil.FindSecurityContext;
import hiccup.hiccupstore.user.dto.board.Board1vs1Form;
import hiccup.hiccupstore.user.dto.board.BoardDto;
import hiccup.hiccupstore.user.dto.board.BoardimageUpdateForm;
import hiccup.hiccupstore.user.dto.UserDto;
import hiccup.hiccupstore.user.dto.board.User1vs1BoardDto;
import hiccup.hiccupstore.user.service.mypage.MyPage1vs1Service;
import hiccup.hiccupstore.commonutil.file.FileStore;
import hiccup.hiccupstore.commonutil.file.UploadFile;
import hiccup.hiccupstore.user.util.Paging;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MyPage1vs1Controller {

    private final MyPage1vs1Service myPage1vs1Service;
    private final FileStore fileStore;
    private final FindSecurityContext findSecurityContext;

    /** mypage 일대일문의게시판 들어가는 매서드 */
    @GetMapping("/mypage/mypage1vs1")
    public String MyPage1vs1(Model model, @RequestParam(defaultValue = "1") Integer page){

        Map<String, Object> boardDtoListAndBoardCountMap = myPage1vs1Service.FindBoard(page);

        Paging paging = new Paging(
                (Integer) boardDtoListAndBoardCountMap.get("boardTotalCount"),
                page-1,
                10);

        model.addAttribute("BoardDtoList",(List<BoardDto>)boardDtoListAndBoardCountMap.get("boardDtoList"));
        model.addAttribute("paging",paging);
        model.addAttribute("page",page);

        return "/mypage/mypage1vs1";

    }

    /** mypage 일대일문의 게시판쓰기들어가는 매서드*/
    @GetMapping("/mypage/mypage1vs1write")
    public String MyPage1vs1Write(){
        return "/mypage/mypage1vs1write";
    }

    /** mypage 일대일문의 게시글 저장하는 매서드*/
    @PostMapping("/mypage/mypage1vs1write")
    public String MyPage1vs1WritePost(@ModelAttribute Board1vs1Form board1vs1Form,Model model) throws IOException {

        List<UploadFile> storeImageFiles = fileStore.storeFiles(board1vs1Form.getImageFiles());
        myPage1vs1Service.SaveBoard1vs1(board1vs1Form,storeImageFiles);

        Map<String, Object> boardDtoListAndBoardCountMap = myPage1vs1Service.FindBoard(1);

        Paging paging = new Paging((Integer) boardDtoListAndBoardCountMap.get("boardTotalCount"), 0, 10);

        model.addAttribute("BoardDtoList",(List<BoardDto>)boardDtoListAndBoardCountMap.get("boardDtoList"));
        model.addAttribute("paging",paging);

        return "/mypage/mypage1vs1";

    }


    /** mypage 일대일문의 게시글보기  매서드*/
    @GetMapping("/mypage/mypage1vs1see/{boardId}")
    public String MyPage1vs1See(@PathVariable Integer boardId,
                                @RequestParam(defaultValue = "1") Integer page,Model model){

        UserDto user = findSecurityContext.getUserDto();
        List<User1vs1BoardDto> user1vs1BoardDtoList = myPage1vs1Service.SeeBoard(boardId);

        model.addAttribute("page",page);
        model.addAttribute("name",user.getNickName());
        model.addAttribute("boardid",boardId);
        model.addAttribute("boarddto",user1vs1BoardDtoList);
        checkIfImageIsOrNot(model, user1vs1BoardDtoList);

        return "/mypage/mypage1vs1see";
    }

    @GetMapping("/mypage/mypage1vs1update/{boardId}")
    public String MyPage1vs1Update(@PathVariable Integer boardId, Model model){

        UserDto user = findSecurityContext.getUserDto();
        List<User1vs1BoardDto> user1vs1BoardDtoList = myPage1vs1Service.SeeBoard(boardId);

        model.addAttribute("page",1);
        model.addAttribute("name",user.getNickName());
        model.addAttribute("boardid",boardId);
        checkIfImageIsOrNot(model, user1vs1BoardDtoList);

        return "/mypage/mypage1vs1update";
    }

    @PostMapping("/sss")
    public String MyPage1vs1WritePost(@ModelAttribute BoardimageUpdateForm boardimageUpdateForm) throws IOException {

        List<UploadFile> storeImageFiles = fileStore.storeFiles(boardimageUpdateForm.getImageFiles());

        if(boardimageUpdateForm.getDeleteImageFiles() != null){
            for (String deleteImageFileName : boardimageUpdateForm.getDeleteImageFiles()) {
                fileStore.deleteFile(fileStore.getFullPath(deleteImageFileName));
            }
        }

        myPage1vs1Service.UpdateBoard1vs1Form(boardimageUpdateForm,
                storeImageFiles,
                boardimageUpdateForm.getBoardid());

        return "index";

    }

    @ResponseBody
    @GetMapping("/testimage/{filename}")
    public Resource downLoadImage(@PathVariable String filename) throws MalformedURLException {

        return new UrlResource("file:"+fileStore.getFullPath(filename));

    }

    private void checkIfImageIsOrNot(Model model, List<User1vs1BoardDto> user1vs1BoardDtoList) {
        if(user1vs1BoardDtoList.get(0).getImageid() != null ){
            model.addAttribute("image",true);
        } else {
            model.addAttribute("image",false);
        }
    }

}
