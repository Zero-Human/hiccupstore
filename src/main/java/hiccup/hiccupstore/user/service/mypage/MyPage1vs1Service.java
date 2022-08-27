package hiccup.hiccupstore.user.service.mypage;


import hiccup.hiccupstore.commonutil.FindSecurityContext;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class MyPage1vs1Service {

    private final UserMapper userMapper;
    private final FindSecurityContext findSecurityContext;

    /** Board일대일 문의글 저장하는 Service 매서드입니다. */
    public void SaveBoard1vs1(Board1vs1Form board1vs1Form, List<UploadFile> storeImageFiles){

        UserDto user = findSecurityContext.getUserDto();

        /** 게시글을 저장하는 Mapper*/
        userMapper.saveBoard(user.getUserId(),board1vs1Form.getBoardtitle(),
                board1vs1Form.getBoardcontent(), "2022-08-01");

        /** 다중image파일을 받아서 저장하는 Mapper*/
        if(storeImageFiles.size() != 0){
            Integer boardid = userMapper.FindOneBoardByUserId(user.getUserId());
            for (UploadFile storeImageFile : storeImageFiles) {
                storeImageFile.setBoardid(boardid);
            }
            userMapper.saveBoardImage(storeImageFiles);
        }

    }

    /** BoardDtoList를 받아오는 Service 매서드입니다. 페이징처리도 함께합니다.*/
    public Map<String,Object> FindBoard(Integer page){

        Map<String, Object> boardDtoListAndBoardTotalCountMap = new HashMap<>();

        UserDto user = findSecurityContext.getUserDto();

        Integer boardTotalCount = userMapper.FindBoardCountByUserId(user.getUserId(),1);
        List<BoardDto> boardDtoList = userMapper.FindBoardByUserId(user.getUserId(), (page - 1) * 10, 10,1);

        boardDtoListAndBoardTotalCountMap.put("boardTotalCount",boardTotalCount);
        boardDtoListAndBoardTotalCountMap.put("boardDtoList",boardDtoList);

        return boardDtoListAndBoardTotalCountMap;

    }

    /** 일대일문의 게시글을 보기위해 하나의 BoardDto를 받아옵니다. 그래서 boardid가 필요합니다.
     *  하나의 게시글을 보기위해서는 하나의 dto가 필요한데 List로 받아오는것은
     *  각 board에 달린 image들이 있기때문입니다.
     * */
    public List<User1vs1BoardDto> SeeBoard(Integer boardId){

        return userMapper.getUser1vs1BoardOne(boardId);

    }

    public void UpdateBoard1vs1Form(BoardimageUpdateForm boardimageUpdateForm,
                                    List<UploadFile> storeImageFiles, Integer boardid) {

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
