package hiccup.hiccupstore.product.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductImage {
    private int imageId ;
    private int productId ;
    private String imageName ;
    private String imagePath ;
    private String imageType ;
}
