package hiccup.hiccupstore.user.service.mypage;


import hiccup.hiccupstore.user.dao.UserMapper;
import hiccup.hiccupstore.user.dto.*;
import hiccup.hiccupstore.user.util.FileStore;
import hiccup.hiccupstore.user.util.Paging;
import hiccup.hiccupstore.user.util.UploadFile;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MyPage1vs1Service {

    private final UserMapper userMapper;

    public void SaveBoard1vs1Form(UserDto user,Board1vs1Form board1vs1Form,List<UploadFile> storeImageFiles) throws IOException {


        userMapper.saveBoard(user.getUserId(),board1vs1Form.getBoardtitle(), board1vs1Form.getBoardcontent(), "2022-08-01");


        if(storeImageFiles.size() != 0){
            Integer boardid = userMapper.FindOneBoardByUserId(user.getUserId());
            for (UploadFile storeImageFile : storeImageFiles) {
                storeImageFile.setBoardid(boardid);
            }
            userMapper.saveBoardImage(storeImageFiles);
        }

    }

    public void FindBoard(UserDto user, Model model, Integer page){

        Integer boardtotcount = userMapper.FindBoardCountByUserId(user.getUserId());
        List<BoardDto> boardDtos = userMapper.FindBoardByUserId(user.getUserId(), (page - 1) * 10, 10);

        //getUser1vs1boardall

        model.addAttribute("BoardDtoList",boardDtos);

        Paging paging = new Paging(boardtotcount, page-1, 10);

        model.addAttribute("paging",paging);

    }

    public void SeeBoard(Model model,Integer boardid,Integer userid){

//        List<BoardDto2> boardDto2 = userMapper.getUser1vs1BoardOne(boardid);
//        model.addAttribute("boarddto",boardDto2);
//        model.addAttribute("image",true);
//        if(boardDto2.size() == 0){
//            List<BoardDto> boardDtos = userMapper.FindOneBoardByBoardidNotimage(boardid);
//            model.addAttribute("boarddto",boardDtos);
//            model.addAttribute("image","false");
//        }
        List<User1vs1BoardDto> user1vs1Boardlist = userMapper.getUser1vs1BoardOne(boardid);
        log.info("user1vs1BoardList = {} " ,user1vs1Boardlist);
        model.addAttribute("boarddto",user1vs1Boardlist);
        if(user1vs1Boardlist.get(0).getImageid() != null ){
            model.addAttribute("image",true);
        } else {
            model.addAttribute("image",false);
        }

    }

}
