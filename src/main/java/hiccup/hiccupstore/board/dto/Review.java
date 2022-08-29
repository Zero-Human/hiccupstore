package hiccup.hiccupstore.board.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
@Builder
public class Review {
    private Integer boardId;
    private Integer boardTypeId;
    private Integer productId;
    private String boardContent;
    private String createDate;
    private Integer userId;

}


