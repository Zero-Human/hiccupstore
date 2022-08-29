package hiccup.hiccupstore.user.service.mypage;


import hiccup.hiccupstore.user.dao.UserMapper;
import hiccup.hiccupstore.user.dto.*;
import hiccup.hiccupstore.commonutil.security.service.Oauth2UserContext;
import hiccup.hiccupstore.user.dto.board.Board1vs1Form;
import hiccup.hiccupstore.user.dto.board.BoardDto;
import hiccup.hiccupstore.user.dto.board.BoardimageUpdateForm;
import hiccup.hiccupstore.user.dto.board.User1vs1BoardDto;
import hiccup.hiccupstore.user.util.Paging;
import hiccup.hiccupstore.commonutil.file.UploadFile;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MyPage1vs1Service {

    private final UserMapper userMapper;

    public void SaveBoard1vs1Form(Board1vs1Form board1vs1Form, List<UploadFile> storeImageFiles) throws IOException {


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDto user;
        try {
            user = (UserDto) authentication.getPrincipal();
        } catch (Exception exce){
            user = ((Oauth2UserContext) authentication.getPrincipal()).getAccount();
            System.out.println("classcastexception도 잡앗지롱");
        }

        userMapper.saveBoard(user.getUserId(),board1vs1Form.getBoardtitle(), board1vs1Form.getBoardcontent(), "2022-08-01");

        if(storeImageFiles.size() != 0){
            Integer boardid = userMapper.FindOneBoardByUserId(user.getUserId());
            for (UploadFile storeImageFile : storeImageFiles) {
                storeImageFile.setBoardid(boardid);
            }
            userMapper.saveBoardImage(storeImageFiles);
        }

    }

    public void FindBoard(Model model, Integer page){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDto user;
        try {
            user = (UserDto) authentication.getPrincipal();
        } catch (Exception exce){
            user = ((Oauth2UserContext) authentication.getPrincipal()).getAccount();
            System.out.println("classcastexception도 잡앗지롱");
        }

        Integer boardtotcount = userMapper.FindBoardCountByUserId(user.getUserId(),1);
        List<BoardDto> boardDtos = userMapper.FindBoardByUserId(user.getUserId(), (page - 1) * 10, 10,1);

        //getUser1vs1boardall

        model.addAttribute("BoardDtoList",boardDtos);

        Paging paging = new Paging(boardtotcount, page-1, 10);

        model.addAttribute("paging",paging);

    }

    public void SeeBoard(Model model,Integer boardid){

        List<User1vs1BoardDto> user1vs1Boardlist = userMapper.getUser1vs1BoardOne(boardid);
        model.addAttribute("boarddto",user1vs1Boardlist);

        if(user1vs1Boardlist.get(0).getImageid() != null ){
            model.addAttribute("image",true);
        } else {
            model.addAttribute("image",false);
        }

    }

    public void UpdateBoard1vs1Form(BoardimageUpdateForm boardimageUpdateForm,
                                    List<UploadFile> storeImageFiles, Integer boardid) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDto user;
        try {
            user = (UserDto) authentication.getPrincipal();
        } catch (Exception exce){
            user = ((Oauth2UserContext) authentication.getPrincipal()).getAccount();
            System.out.println("classcastexception도 잡앗지롱");
        }


        if(storeImageFiles.size() != 0){
            for (UploadFile storeImageFile : storeImageFiles) {
                storeImageFile.setBoardid(boardid);
            }
            userMapper.saveBoardImage(storeImageFiles);
        }

        if(boardimageUpdateForm.getDeleteImageFiles() != null){
            userMapper.deleteBoardImage(boardimageUpdateForm.getDeleteImageFiles());
        }

    }
}
