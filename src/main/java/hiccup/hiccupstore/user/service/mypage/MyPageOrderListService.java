package hiccup.hiccupstore.user.service.mypage;

import hiccup.hiccupstore.user.dao.UserMapper;
import hiccup.hiccupstore.user.dto.order.OrderDto;
import hiccup.hiccupstore.user.dto.order.OrderLatelyProductDto;
import hiccup.hiccupstore.user.dto.UserDto;
import hiccup.hiccupstore.user.util.Paging;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MyPageOrderListService {

    private final UserMapper userMapper;

    public List<OrderLatelyProductDto> MyPage(String startdate, String lastdate,UserDto user,Integer page,Integer pagesize){

        return userMapper.getOrderLatelyProductListtPage(startdate,lastdate,user.getUserId(),page-1,pagesize);

    }

    public List<OrderDto> MyPage2(UserDto user, Integer page, Integer pagesize, Model model){

        Integer orderListCount = userMapper.getOrderListCount(user.getUserId());

        Paging paging = new Paging(orderListCount,page,pagesize);
        model.addAttribute("paging",paging);

        List<OrderDto> orderList = userMapper.getOrderListPage(user.getUserId(), page-1, pagesize);

        return orderList;

    }

    public void purchaseConfirm(Integer orderid){

        userMapper.updateOrderStatus(orderid,"구매확정");

    }

}
