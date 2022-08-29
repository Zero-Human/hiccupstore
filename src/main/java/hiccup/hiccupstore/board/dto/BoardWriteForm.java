package hiccup.hiccupstore.board.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Setter
@Getter
public class BoardWriteForm {
    private Integer categoryId;
    private Integer productId;
    private String content;
    private String title;

    public ProductQnA toProductQnA(Integer userId){
        return ProductQnA.builder().
//                boardCategoryId(this.categoryId).
                productId(this.productId).
                boardContent(this.content).
                boardTitle(this.title).
                createDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))).
                userId(userId).
                build();
    }
    public  Review toReview(Integer userId){
        return Review.builder().
//                CategoryId(this.categoryId).
                productId(this.productId).
//                Content(this.content).
                createDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))).
                userId(userId).
                build();
    }
}
