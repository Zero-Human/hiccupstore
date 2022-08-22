package hiccup.hiccupstore.user.dto;

import lombok.Data;

@Data
public class OrderStatusChangedDto {

    private String orderstatus;
    private Integer orderid;

}
