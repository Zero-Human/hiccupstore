package hiccup.hiccupstore.user.dto;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class OrderFormDto {

    private Integer orderId;
    private String orderdate;
    private List<OrderLatelyProductDto> OrderLatelyProductDto = new ArrayList<>();
    private String status;

}
