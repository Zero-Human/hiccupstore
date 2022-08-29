package hiccup.hiccupstore.board.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ProductQnA {
    private Integer boardId;
    private Integer boardCategory;
    private Integer productId;
    private Integer boardTypeId;
    private String boardContent;
    private String boardTitle;
    private String createDate;
    private Integer userId;
}
