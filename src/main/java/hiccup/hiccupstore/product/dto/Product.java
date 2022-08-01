package hiccup.hiccupstore.product.dto;

import lombok.*;
import org.apache.ibatis.type.Alias;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Product {
    private int productId ; // 수정될 수 없음
    private int categoryId ; // 수정될 수 없음 -> Enum 조회할 때 index로 사용하기
    private String productName ;
    private int price;
    private int quantity ;
    private int alcoholContent;
    private String brand ;
    private String description ;
    private long sellCount ; // 판매량
}
