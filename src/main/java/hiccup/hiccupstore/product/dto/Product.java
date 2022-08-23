package hiccup.hiccupstore.product.dto;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Product {

    private int productId;
    private int categoryId;
    private String productName;
    private int price;
    private int quantity;
    private int alcoholContent;
    private String brand;
    private String description;

}
