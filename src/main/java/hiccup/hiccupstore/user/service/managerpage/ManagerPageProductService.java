package hiccup.hiccupstore.user.service.managerpage;

import hiccup.hiccupstore.user.dao.UserMapper;
import hiccup.hiccupstore.user.dto.board.BoardDto;
import hiccup.hiccupstore.user.dto.board.User1vs1BoardDto;
import hiccup.hiccupstore.user.util.Paging;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ManagerPageProductService {

    private final UserMapper userMapper;

    public void findUserProductBoardAll(Model model, Integer page){

        Integer boardtotcount = userMapper.getUserProductAllCount();
        List<BoardDto> user1vs1boardall = userMapper.getUserProductboardall((page - 1) * 10, 10);

        Paging paging = new Paging(boardtotcount, page-1, 10);

        model.addAttribute("paging",paging);

        model.addAttribute("BoardDtoList",user1vs1boardall);

    }

    public void SeeBoard(Model model,Integer boardid){

        List<User1vs1BoardDto> user1vs1Boardlist = userMapper.getUserProductBoardOne(boardid);
        log.info("user1vs1BoardList = {} " ,user1vs1Boardlist);
        model.addAttribute("boarddto",user1vs1Boardlist);
        if(user1vs1Boardlist.get(0).getImageid() != null ){
            model.addAttribute("image",true);
        } else {
            model.addAttribute("image",false);
        }

    }

    public void SaveProductUserAnswer(Integer boardid, String BoardContent) {

        userMapper.SaveProductUserAnswer(BoardContent,boardid);

    }

}
