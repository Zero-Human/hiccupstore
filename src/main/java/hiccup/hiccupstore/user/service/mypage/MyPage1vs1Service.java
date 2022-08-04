package hiccup.hiccupstore.user.service.mypage;


import hiccup.hiccupstore.user.dao.UserMapper;
import hiccup.hiccupstore.user.dto.Board1vs1Form;
import hiccup.hiccupstore.user.dto.BoardDto;
import hiccup.hiccupstore.user.dto.BoardDto2;
import hiccup.hiccupstore.user.util.FileStore;
import hiccup.hiccupstore.user.util.Paging;
import hiccup.hiccupstore.user.util.UploadFile;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.jni.User;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MyPage1vs1Service {

    private final FileStore fileStore;
    private final UserMapper userMapper;

    public void SaveBoard1vs1Form(Board1vs1Form board1vs1Form) throws IOException {

        List<UploadFile> storeImageFiles = fileStore.storeFiles(board1vs1Form.getImageFiles());
        log.info("storeImageFiles = {} ",storeImageFiles);

        userMapper.saveBoard(board1vs1Form.getBoardtitle(), board1vs1Form.getBoardcontent(), "2022-08-01");
        if(storeImageFiles.size() != 0){
            userMapper.saveBoardImage(storeImageFiles);
        }

    }

    public void FindBoard(Model model,Integer page){

        Integer boardtotcount = userMapper.FindBoardCountByUserId(2);
        List<BoardDto> boardDtos = userMapper.FindBoardByUserId(2, (page - 1) * 10, 10);

        model.addAttribute("BoardDtoList",boardDtos);

        Paging paging = new Paging(boardtotcount, page-1, 10);

        model.addAttribute("paging",paging);

    }

    public void SeeBoard(Model model,Integer boardid,Integer userid){

        List<BoardDto2> boardDto2 = userMapper.FindOneBoardByBoardid(boardid);
        log.info("boradDto2 = {} ",boardDto2);
        model.addAttribute("boarddto",boardDto2);

    }

}
