package hiccup.hiccupstore.user.controller.managerpage;

import hiccup.hiccupstore.user.dto.order.OrderDto;
import hiccup.hiccupstore.user.dto.order.OrderFormDto;
import hiccup.hiccupstore.user.dto.order.OrderLatelyProductDto;
import hiccup.hiccupstore.user.dto.order.OrderStatusChangedDto;
import hiccup.hiccupstore.user.service.managerpage.ManagerPageOrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MangerPageOrderController {

    private final ManagerPageOrderService managerPageOrderService;
    private final Integer pageSize = 5;

    /** 관리자페이지 주문관리페이지로 이동하는 매서드입니다. */
    @GetMapping("/managerpage/managerpageorder")
    public String managerPageOrder(String startdate, String lastdate,Model model,@RequestParam(defaultValue = "1") Integer page){


        if((startdate == null && lastdate == null) || ("".equals(startdate) && "".equals(lastdate))){

            List<OrderLatelyProductDto> orderLatelyProductList = managerPageOrderService.FirstManagerPageOrderList(page,pageSize);

            ArrayList<OrderFormDto> orderFormList = null;

            if (orderLatelyProductList.size() != 0) {
                List<OrderDto> orderDtos = managerPageOrderService.FirstManagerPageOrderList2(page,pageSize,model);
                orderFormList = makeOrderList(orderLatelyProductList,orderDtos);
                model.addAttribute("orderFormList",orderFormList);
            }

            return "managerpage/managerpageorder";

        } else {

            List<OrderLatelyProductDto> orderLatelyProductList = managerPageOrderService.MyPage(startdate, lastdate, page, pageSize);
            ArrayList<OrderFormDto> orderFormList = null;

            if (orderLatelyProductList.size() != 0) {
                List<OrderDto> orderDtos = managerPageOrderService.MyPage2(page,pageSize,model);

                orderFormList = makeOrderList(orderLatelyProductList,orderDtos);
                model.addAttribute("orderFormList",orderFormList);

            }

            model.addAttribute("startdate",startdate);
            model.addAttribute("lastdate",lastdate);
            return "managerpage/managerpageorder";
        }


    }

    /** 관리자페이지 주문상태를 변경하는 매서드입니다.*/
    @PostMapping("/managerpage/changedorderstatus")
    @ResponseBody
    public String changedorderstatus(@RequestBody OrderStatusChangedDto orderStatusChangedDto){

        managerPageOrderService.updateOrderStatus(orderStatusChangedDto);

        return "ok";

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
