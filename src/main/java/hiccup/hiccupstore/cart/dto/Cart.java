package hiccup.hiccupstore.cart.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Cart {

    private int cartId;
    private int userId;
    private int productId;
    private int quantity;


}
