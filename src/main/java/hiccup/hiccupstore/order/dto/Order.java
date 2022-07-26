package hiccup.hiccupstore.order.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Date;

@Getter
@Setter
@ToString
public class Order {

    private int orderId;
    private int userId;
    private int deliveryId;
    private String status;
    private Date orderDate;

    private String address;

    public Order() {

    }

    public Order(int orderId, int userId, int deliveryId, String status, Date orderDate, String address) {
        this.orderId = orderId;
        this.userId = userId;
        this.deliveryId = deliveryId;
        this.status = status;
        this.orderDate = orderDate;
        this.address = address;
    }
}
