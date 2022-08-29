package hiccup.hiccupstore.board.dao;

import hiccup.hiccupstore.board.dto.Comment;
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

    ArrayList<Review> selectReviewListByProductId(int productId);
    ArrayList<Review> selectPhotoReviewListByBoardId(ArrayList<Integer> boardIdList);

    ArrayList<Review> selectReviewListByUserId(int userId);
    ArrayList<Review> selectReviewListByType(int boardTypeId);


    ArrayList<ProductQnA> selectProductQnAListByProductId(int productId);
    ArrayList<ProductQnA> selectProductQnAListByUserId(int userId);

    ArrayList<ProductQnA> selectSystemQnAListByCategory(String boardCateory);


    ArrayList<Image> selectImageList(int boardId) ;

    /* Comment */
    ArrayList<Comment> selectCommentList(int boardId) ;
    ArrayList<Integer> selectUserCommentedBoardIdList(int userId); // 회원측 마이페이지에서 자신이 답글달았던 상품 List 조회
}
