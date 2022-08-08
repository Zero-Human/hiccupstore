package hiccup.hiccupstore.user.service.mypage;


import hiccup.hiccupstore.user.dao.UserMapper;
import hiccup.hiccupstore.user.dto.OrderDto;
import hiccup.hiccupstore.user.dto.OrderLatelyProductDto;
import hiccup.hiccupstore.user.dto.ProductDto;
import hiccup.hiccupstore.user.dto.UserDto;
import hiccup.hiccupstore.user.util.StatusType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MyPageService {

    private final UserMapper userMapper;

    public List<OrderLatelyProductDto> MyPage(UserDto user){

        return userMapper.getOrderLatelyProductList(user.getUserId());

    }

    public HashMap<String, Object> MyPage2(UserDto user){

        List<OrderDto> orderList = userMapper.getOrderList(user.getUserId());

        HashMap<String, Object> OrderListHashMap = new HashMap<>();
        OrderListHashMap.put("orderList",orderList);

        ArrayList<Integer> statusList = new ArrayList<>();
        OrderListHashMap.put("statusList",statusList);

        for(int i = 0; i <6; i++){
            statusList.add(i);
            statusList.set(i,0);
        }

        for (OrderDto orderDto : orderList) {
            if(StatusType.Deposit_waiting.equals(orderDto.getStatus())){
                statusList.set(0,statusList.get(0)+1);
                break;
            } else if(StatusType.Complete_payment.equals(orderDto.getStatus())){
                statusList.set(1,statusList.get(1)+1);
                break;
            } else if(StatusType.preparing_for_delivery.equals(orderDto.getStatus())){
                statusList.set(2,statusList.get(2)+1);
                break;
            } else if(StatusType.shipping.equals(orderDto.getStatus())){
                statusList.set(3,statusList.get(3)+1);
                break;
            } else if(StatusType.Delivery_completed.equals(orderDto.getStatus())){
                statusList.set(4,statusList.get(4)+1);
                break;
            } else if(StatusType.Confirmation_of_purchase.equals(orderDto.getStatus())){
                statusList.set(5,statusList.get(5)+1);
                break;
            }
        }
        return OrderListHashMap;
    }

    public List<ProductDto> LatelySeeProduct(String goods){
        String[] mobNum = goods.split("/");

        List<ProductDto> productList = userMapper.getProductList(mobNum);

        log.info("productlist = " + productList);

        return productList;

    }

}
