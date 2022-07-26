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
    private long sellCount ; // 판매량
    private int reviewCount ; // 리뷰갯수 -> Board 에서 "상품리뷰" 카데고리의 form의 제출하면 증가하도록 설정해줘야 함.
}
