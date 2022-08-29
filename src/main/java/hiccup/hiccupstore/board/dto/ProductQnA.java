package hiccup.hiccupstore.board.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Builder
public class ProductQnA {
    private Integer boardId;
    private Integer userId ;
    private Integer boardTypeId;
    private Integer productId;
    private String boardTitle; // QnA 는 필수
    private String boardContent;
    private String createDate;
    private String boardCategory;
}
