package hiccup.hiccupstore.userpick.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserPick {
    private Integer productId;
    private Integer userId;
    private String productName;
    private Integer price;
    private String imagePath;
}
