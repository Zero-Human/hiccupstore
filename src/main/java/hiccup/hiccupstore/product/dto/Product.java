package hiccup.hiccupstore.product.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Product {
    private int productId ;
    private int categoryId ;
    private String productName ;
    private int price;
    private int quanity ;
    private int alcoholContent;
    private String brand ;
    private int imageId ;
    private String description ;

}
