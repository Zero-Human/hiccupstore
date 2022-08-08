package hiccup.hiccupstore.user.controller.mypage;


import hiccup.hiccupstore.user.dto.*;
import hiccup.hiccupstore.user.service.mypage.MyPageService;
import hiccup.hiccupstore.user.util.SessionConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MyPageController {

    private final MyPageService myPageService;

    @GetMapping("/mypage")
    public String myPage(HttpSession session, Model model){

        UserDto user = (UserDto) session.getAttribute(SessionConst.LOGIN_MEMBER);

        List<OrderLatelyProductDto> orderLatelyProductList = myPageService.MyPage(user);

        HashMap<String, Object> stringObjectHashMap = myPageService.MyPage2(user);
        model.addAttribute("statusTypeCountList",stringObjectHashMap.get("statusList"));

        ArrayList<OrderFormDto> orderFormList = makeOrderList(orderLatelyProductList,
                (List<OrderDto>) stringObjectHashMap.get("orderList"));
        model.addAttribute("orderFormList",orderFormList);

        log.info("statusTypeCountList : " + stringObjectHashMap.get("statusList"));
        log.info("orderList : " + orderFormList);


        /**
         *         String goods = "1";
         *         String[] mobNum = goods.split("/");
         *         String ret1 = mobNum[0];
         *         String ret2 = mobNum[1];
         *         String ret3 = mobNum[2];
         *
         *         log.info("" + ret1 + "/" + ret2 +"/" + ret3);
         *
         *         Cookie idCookie = new Cookie("test",goods);
         *         response.addCookie(idCookie);
         * */

        String goods = "3/2/1";

        List<ProductDto> productDtoList = myPageService.LatelySeeProduct(goods);
        model.addAttribute("productDtoList",productDtoList);

        return "mypage";

    }

    /** productLatelyDto랑 orderDto를 합쳐서 ProductDtoList를 만든다. */
    ArrayList<OrderFormDto> makeOrderList(List<OrderLatelyProductDto> orderLatelyProductList,List<OrderDto> orderList){

        ArrayList<OrderFormDto> OrderFormDtoList = new ArrayList<>();
        HashMap<Integer, OrderFormDto> objectObjectHashMap = new HashMap<>();
        HashMap<Integer, List<OrderLatelyProductDto>> objectObjectHashMap1 = new HashMap<>();

        for(int i = 0; i < orderList.size();i++){
            OrderFormDto orderFormDto = OrderFormDto.builder().orderId(orderList.get(i).getOrderid()).
                    orderdate(orderList.get(i).getOrderdate()).
                    status(orderList.get(i).getStatus()).
                    build();

            objectObjectHashMap.put(orderList.get(i).getOrderid(),orderFormDto);
            List<OrderLatelyProductDto> OrderLatelyProductDto = new ArrayList<>();
            objectObjectHashMap1.put(orderList.get(i).getOrderid(),OrderLatelyProductDto);
            OrderFormDtoList.add(orderFormDto);

        }

        for (OrderLatelyProductDto orderLatelyProductDto : orderLatelyProductList) {

            OrderFormDto orderFormDto = objectObjectHashMap.get(orderLatelyProductDto.getOrderid());
            List<OrderLatelyProductDto> orderLatelyProductDtoList =
                    objectObjectHashMap1.get(orderLatelyProductDto.getOrderid());
            orderLatelyProductDtoList.add(orderLatelyProductDto);

            if(orderFormDto.getOrderLatelyProductDto() == null){
                orderFormDto.setOrderLatelyProductDto(orderLatelyProductDtoList);
            }
        }

        return OrderFormDtoList;
    }
}
