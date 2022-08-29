package hiccup.hiccupstore.board.dao;

import hiccup.hiccupstore.board.dto.Board;
import hiccup.hiccupstore.board.dto.Image;
import hiccup.hiccupstore.board.dto.ProductQnA;
import hiccup.hiccupstore.board.dto.Review;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Mapper
@Repository
public interface BoardMapper {
// 일단 insert만 하면 된다.


    void insertReview(Review review);

    void insertProductQnA(ProductQnA productQnA);
    void insertImage(ArrayList<Image> image);

    void editProductQnA(ProductQnA productQnA);
    void editImage(ArrayList<Image> image);

    void deleteProductQnA(Integer boardId);
    void deleteImageByBoardId(Integer boardId);
    void deleteReview(Integer boardId);
    Board getBoardById(Integer boardId);
    ArrayList<Board> getBoardByProductId(Integer productId);
    ArrayList<String> getImageListNameByBoardId(Integer boardId);
}
