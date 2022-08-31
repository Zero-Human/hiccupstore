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
                model.addAttribute("page",page);
            }

            return "managerpage/managerpageorder";

        } else {

            List<OrderLatelyProductDto> orderLatelyProductList = managerPageOrderService.getOrderLatelyProductListByDate(startdate, lastdate, page, pageSize);
            ArrayList<OrderFormDto> orderFormList = null;

            if (orderLatelyProductList.size() != 0) {
                List<OrderDto> orderDtos = managerPageOrderService.getOrderListByDate(startdate,lastdate,page,pageSize,model);

                orderFormList = makeOrderList(orderLatelyProductList,orderDtos);
                model.addAttribute("orderFormList",orderFormList);
                model.addAttribute("page",page);

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
        System.out.println("order" + orderStatusChangedDto);
        return "ok";

    }

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
