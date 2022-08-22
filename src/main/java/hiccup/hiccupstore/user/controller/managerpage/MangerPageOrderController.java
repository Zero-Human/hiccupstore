package hiccup.hiccupstore.user.controller.managerpage;

import hiccup.hiccupstore.user.dto.*;
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

    @GetMapping("/managerpageorder")
    public String managerPageOrder(String startdate, String lastdate,Model model,@RequestParam(defaultValue = "1") Integer page){

        Integer pagesize = 5;

        if((startdate == null && lastdate == null) || ("".equals(startdate) && "".equals(lastdate))){

            List<OrderLatelyProductDto> orderLatelyProductList = managerPageOrderService.FirstManagerPageOrderList(page,pagesize);

            ArrayList<OrderFormDto> orderFormList = null;
            log.info("size는 얼마인가요? = {} ",orderLatelyProductList.size());

            if (orderLatelyProductList.size() != 0) {
                log.info("여기를 지나가는 orderFormList = {} ",orderLatelyProductList);
                List<OrderDto> orderDtos = managerPageOrderService.FirstManagerPageOrderList2(page,pagesize,model);

                orderFormList = makeOrderList(orderLatelyProductList,orderDtos);
                model.addAttribute("orderFormList",orderFormList);

            }

            return "managerpageorder";

        } else {

            List<OrderLatelyProductDto> orderLatelyProductList = managerPageOrderService.MyPage(startdate, lastdate, page, pagesize);

            ArrayList<OrderFormDto> orderFormList = null;

            if (orderLatelyProductList.size() != 0) {
                List<OrderDto> orderDtos = managerPageOrderService.MyPage2(page,pagesize,model);

                orderFormList = makeOrderList(orderLatelyProductList,orderDtos);
                model.addAttribute("orderFormList",orderFormList);

            }

            model.addAttribute("startdate",startdate);
            model.addAttribute("lastdate",lastdate);

        }

        return null;

    }


    @PostMapping("/changedorderstatus")
    @ResponseBody
    public String changedorderstatus(@RequestBody OrderStatusChangedDto orderStatusChangedDto){

        Integer integer = managerPageOrderService.updateOrderStatus(orderStatusChangedDto);

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
