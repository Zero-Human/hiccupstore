package hiccup.hiccupstore.cart.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Cart {
    private int userId;
    private int itemId;
    private int quantity;

}
