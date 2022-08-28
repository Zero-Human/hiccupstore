package hiccup.hiccupstore.user.service;

import hiccup.hiccupstore.commonutil.FindSecurityContext;
import hiccup.hiccupstore.commonutil.file.UploadFile;
import hiccup.hiccupstore.user.dao.UserMapper;
import hiccup.hiccupstore.user.dto.NoticeDto;
import hiccup.hiccupstore.user.dto.UserDto;
import hiccup.hiccupstore.user.dto.board.Board1vs1Form;
import hiccup.hiccupstore.user.dto.board.BoardDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class NoticeService {

    private final UserMapper userMapper;
    private final FindSecurityContext findSecurityContext;

    public NoticeDto getNoticeBoardSee(Integer noticeBoardId){

        NoticeDto noticeDto = userMapper.getnoticeBoardOne(noticeBoardId);

        return noticeDto;

    }

    public List<NoticeDto> getNoticeBoardList(Integer page, Integer pageSize) {

        return userMapper.getNoticeDtoListPaging((page-1)*10,pageSize);

    }

    public Integer getTotalNoticeCount() {
        return userMapper.getTotalNoticeCount();
    }

    public void SaveNoticeBoard(Board1vs1Form board1vs1Form, List<UploadFile> storeImageFiles) {

        UserDto user = findSecurityContext.getUserDto();
        /** 게시글을 저장하는 Mapper*/
        userMapper.saveBoard(user.getUserId(),board1vs1Form.getBoardtitle(),
                board1vs1Form.getBoardcontent(),board1vs1Form.getBoardcategory());
        userMapper.saveNotice(board1vs1Form.getBoardtitle(),board1vs1Form.getBoardcontent(),board1vs1Form.getBoardcategory(),
                storeImageFiles.get(0).getStoreFileName());

    }

    public void deleteNoticeBoard(Integer noticedid) {
        userMapper.deleteNotice(noticedid);
    }
}
