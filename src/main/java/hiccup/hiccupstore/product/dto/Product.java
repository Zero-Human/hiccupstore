package hiccup.hiccupstore.product.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Product {
    private int productId ; // 수정될 수 없음
    private int categoryId ; // 수정될 수 없음
    private String productName ;
    private int price;
    private int quantity ;
    private int alcoholContent;
    private String brand ;
    private String description ;
    private long sellCount ;
}
