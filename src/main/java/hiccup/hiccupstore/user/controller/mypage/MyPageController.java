package hiccup.hiccupstore.user.controller.mypage;


import hiccup.hiccupstore.commonutil.FindSecurityContext;
import hiccup.hiccupstore.user.dto.*;
import hiccup.hiccupstore.user.dto.order.OrderDto;
import hiccup.hiccupstore.user.dto.order.OrderFormDto;
import hiccup.hiccupstore.user.dto.order.OrderLatelyProductDto;
import hiccup.hiccupstore.user.service.mypage.MyPageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MyPageController {

    private final MyPageService myPageService;
    private final FindSecurityContext findSecurityContext;

    /** mypage main으로 들어가는 매서드입니다. */
    @GetMapping("/mypage")
    public String myPage(Model model){

        UserDto user = findSecurityContext.getUserDto();

        /** 최근주문리스트란 최근주문 5개의 리스트만 가져오겠다는 것입니다. */
        List<OrderLatelyProductDto> orderLatelyProductList = myPageService.GetOrderLatelyProductList(user);

        /** 최근주문된 상품에서 주문상태를 보여주기위한  Count List와 주문 List를 가져옵니다. */
        HashMap<String, List> StatusCountListAndOrderList = myPageService.GetOrderListAndStatusList(user);

        /** 최근주문된 5개의 리스트만 보여주는데 각 주문번호마다 여러개의 상품이 있을수있으므로
         * 그것을 하나의 Dto에 담기위해서 OrderFormDto를 만들어줘서 담아준다. 그래야 template에서도 쉽게 뿌려줄수있다.
         *
         * ex) 주문번호1번에 고객이 소주 3병,맥주 2병,오징어 3개를 샀다면
         *
         * OrderFormDto에는
         * orderid : 1번
         * orderdate : 2022-09-30
         * List<OrderLatelyProductDto> = [소주3병,맥주2병,오징어3개] -->이렇게 관련된 OrderLatelyProductDto가 담긴다.
         * status : 입금대기
         *
         * 그래서 makeOrderList는 위와같은 OrderFormDto를 만든다.
         *
         * 또 여러개의 주문도 가능하므로 최종적으로 ArrayList<OrderFormDto>를 만들어서 반환하게된다.
         * */
        ArrayList<OrderFormDto> orderFormList = makeOrderList(orderLatelyProductList,
                (List<OrderDto>) StatusCountListAndOrderList.get("orderList"));

        /**
         *         이 로직은 최근본상품에대한 로직으로
         *         String goods ="3/2/1"과같은 형태로 담기게된다면 쿠키에서 아래와같이 담기게 된다.
         *
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
//        String goods = "3/2/1";

        /** 쿠키에서 3/2/1이라는 string을 얻고 그것을 파싱해서 상품점보를 불러와 List로 변환한다음 뷰단에 뿌려줘서 최근본상품을 볼수있게하는
         * 매서드이다. (3/2/1 여기서 숫자는 상품id를 뜻한다. )*/
//        List<ProductDto> productDtoList = myPageService.LatelySeeProduct(goods);

        /** 화면에 최근주문된 상품에서 주문상태를 Count한것을 보여주기위한 model) */
        model.addAttribute("statusCountList",StatusCountListAndOrderList.get("statusList"));
        /** 화면에 주문목록을 보여주기위해 model에 담는다.*/
        model.addAttribute("orderFormList",orderFormList);
//        model.addAttribute("productDtoList",productDtoList);

        return "mypage/mypage";

    }

    /** productLatelyDto랑 orderDto를 합쳐서 ProductDtoList를 만든다. */
    ArrayList<OrderFormDto> makeOrderList(List<OrderLatelyProductDto> orderLatelyProductList,List<OrderDto> orderList){

        ArrayList<OrderFormDto> OrderFormDtoList = new ArrayList<>();
        HashMap<Integer, OrderFormDto> OrderIdAndOrderFormDtoMap = new HashMap<>();
        HashMap<Integer, List<OrderLatelyProductDto>> OrderIdAndOrderLatelyProductDtoListMap = new HashMap<>();

        for(int i = 0; i < orderList.size();i++){

            OrderFormDto orderFormDto = OrderFormDto.builder().orderId(orderList.get(i).getOrderid()).
                    orderdate(orderList.get(i).getOrderdate()).
                    status(orderList.get(i).getStatus()).
                    build();

            OrderIdAndOrderFormDtoMap.put(orderList.get(i).getOrderid(),orderFormDto);
            List<OrderLatelyProductDto> OrderLatelyProductDto = new ArrayList<>();
            OrderIdAndOrderLatelyProductDtoListMap.put(orderList.get(i).getOrderid(),OrderLatelyProductDto);
            OrderFormDtoList.add(orderFormDto);

        }

        for (OrderLatelyProductDto orderLatelyProductDto : orderLatelyProductList) {

            OrderFormDto orderFormDto = OrderIdAndOrderFormDtoMap.get(orderLatelyProductDto.getOrderid());

            List<OrderLatelyProductDto> orderLatelyProductDtoList =
                    OrderIdAndOrderLatelyProductDtoListMap.get(orderLatelyProductDto.getOrderid());
            orderLatelyProductDtoList.add(orderLatelyProductDto);

            if(orderFormDto.getOrderLatelyProductDto() == null){
                orderFormDto.setOrderLatelyProductDto(orderLatelyProductDtoList);
            }
        }

        return OrderFormDtoList;
    }
}


/** 여기서는 각 orderid에 맞게 orderFormDto에 ProductList에 Product를 넣는 과정이다.
 *
 * OrderIdAndOrderFormDtoMap{ 1 : OrderFormDto(orderid = 1 , orderdate = 2022-09-30 , List =null ,status = 입금대기)}
 * 여기서 주문번호 1번을 입력해서 OrderFormDto를 꺼낸다.
 *
 * OrderIdAndOrderLatelyProductDtoListMap{ 1 : []}
 * 여기서 주문번호 1번을 입력해서 ProductList를 꺼낸다.
 *
 * 주문번호와 상품Dto와맞다면 for문을 돌려서 연속해서 넣는다.
 *
 * 최종적으로는 OrderIdAndOrderLatelyProductDtoListMap{ 1 : [소주3병,맥주2병,오징어3개]} 된다.
 *
 * */
/** 여기서는 각 orderid에 맞게 orderFormDto에 ProductList에 Product를 넣는 과정이다.
 *
 * OrderIdAndOrderFormDtoMap{ 1 : OrderFormDto(orderid = 1 , orderdate = 2022-09-30 , List =null ,status = 입금대기)}
 * 여기서 주문번호 1번을 입력해서 OrderFormDto를 꺼낸다.
 *
 * OrderIdAndOrderLatelyProductDtoListMap{ 1 : []}
 * 여기서 주문번호 1번을 입력해서 ProductList를 꺼낸다.
 *
 * 주문번호와 상품Dto와맞다면 for문을 돌려서 연속해서 넣는다.
 *
 * 최종적으로는 OrderIdAndOrderLatelyProductDtoListMap{ 1 : [소주3병,맥주2병,오징어3개]} 된다.
 *
 * */