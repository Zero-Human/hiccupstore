package hiccup.hiccupstore.user.service.mypage;

import hiccup.hiccupstore.user.dao.UserMapper;
import hiccup.hiccupstore.user.dto.board.BoardDto;
import hiccup.hiccupstore.user.dto.board.User1vs1BoardDto;
import hiccup.hiccupstore.user.dto.UserDto;
import hiccup.hiccupstore.commonutil.security.service.Oauth2UserContext;
import hiccup.hiccupstore.user.util.Paging;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MyPageProductService {

    private final UserMapper userMapper;

    public void FindBoard(Model model, Integer page) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDto user;
        try {
            user = (UserDto) authentication.getPrincipal();
        } catch (Exception exce){
            user = ((Oauth2UserContext) authentication.getPrincipal()).getAccount();
            System.out.println("classcastexception도 잡앗지롱");
        }

        Integer boardtotcount = userMapper.FindBoardCountByUserId(user.getUserId(),2);
        List<BoardDto> boardDtos = userMapper.FindBoardByUserId(user.getUserId(), (page - 1) * 10, 10,2);

        model.addAttribute("BoardDtoList",boardDtos);
        model.addAttribute("user",user);
        Paging paging = new Paging(boardtotcount, page-1, 10);

        model.addAttribute("paging",paging);

    }

    public void SeeBoard(Model model, Integer boardid) {
        //board에 상품productid도 필요하다.
        List<User1vs1BoardDto> user1vs1Boardlist = userMapper.getUserProductBoardOne(boardid);
        log.info("user1vs1BoardList = {} " ,user1vs1Boardlist);
        model.addAttribute("boarddto",user1vs1Boardlist);

    }

    public void deleteProductBoard(Integer boardid) {

        Integer integer = userMapper.deleteProductBoard(boardid);

        System.out.println("몇일까요?" + integer);

    }
}
