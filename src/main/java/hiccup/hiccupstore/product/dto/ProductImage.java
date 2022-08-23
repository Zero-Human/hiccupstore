package hiccup.hiccupstore.product.dto;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString

public class ProductImage {

    private int imageId;
    private int productId;
    private String imageName;
    private String imagePath;
    private String imageType;


}
