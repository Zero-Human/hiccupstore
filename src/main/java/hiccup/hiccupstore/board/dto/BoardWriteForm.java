package hiccup.hiccupstore.board.dto;

import hiccup.hiccupstore.board.util.BoardType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Setter
@Getter
public class BoardWriteForm {
    private Integer boardTypeId;
    private Integer productId;
    private Integer boardCategory;
    private String content;
    private String title;

    public ProductQnA toProductQnA(Integer userId){
        return ProductQnA.builder().
                boardCategory(this.boardCategory).
                boardTypeId(BoardType.PRODUCT.getValueNum()).
                productId(this.productId).
                boardContent(this.content).
                boardTitle(this.title).
                createDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))).
                userId(userId).
                build();
    }
    public  Review toReview(Integer userId){
        return Review.builder().
                boardTypeId(BoardType.REVIEW.getValueNum()).
                productId(this.productId).
                boardContent(this.content).
                createDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))).
                userId(userId).
                build();
    }
}
