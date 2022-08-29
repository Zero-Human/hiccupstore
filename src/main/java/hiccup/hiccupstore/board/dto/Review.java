package hiccup.hiccupstore.board.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;


@Data
@Builder
public class Review {
    private Integer boardId;
    private Integer userId ;
    private Integer boardTypeId;
    private Integer productId;
    // Review : Title 없음
    private String boardContent;
    private String createDate;
    // Review : BoardCategory 없음
}


