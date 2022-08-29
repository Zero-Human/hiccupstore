package hiccup.hiccupstore.board.service;

import hiccup.hiccupstore.board.dao.BoardMapper;
import hiccup.hiccupstore.board.dto.Board;
import hiccup.hiccupstore.board.dto.Image;
import hiccup.hiccupstore.board.dto.ProductQnA;
import hiccup.hiccupstore.board.dto.Review;
import hiccup.hiccupstore.product.dto.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardMapper boardMapper;

    public void insertReview(Review review, ArrayList<Image> imageList){
        if (imageList != null){
            for (Image image:imageList) {
                image.setBoardId(review.getBoardId());
            }
            boardMapper.insertImage(imageList);
        }
        boardMapper.insertReview(review);
    }
    @Transactional
    public void insertProductQnA(ProductQnA productQnA, ArrayList<Image> imageList){
        if(imageList !=null){
            for (Image image:imageList) {
                image.setBoardId(productQnA.getBoardId());
            }
            boardMapper.insertImage(imageList);
        }
        boardMapper.insertProductQnA(productQnA);

    }

    @Transactional
    public void editProductQnA(ProductQnA productQnA, ArrayList<Image> imageList){
        if(imageList != null){
            for (Image image:imageList) {
                image.setBoardId(productQnA.getBoardId());
            }
            boardMapper.editImage(imageList);
        }
        boardMapper.editProductQnA(productQnA);

    }

    public void deleteProductQnA(Integer boardId){
        boardMapper.deleteProductQnA(boardId);
    }
    public void deleteImageByBoardId(Integer boardId){
        boardMapper.deleteImageByBoardId(boardId);
    }
    public ProductQnA getProductQnAById(Integer boardId){
        return boardMapper.getBoardById(boardId).toProductQnA();
    }
    public Review getReviewById(Integer boardId){
        return boardMapper.getBoardById(boardId).toReview();
    }
    public ArrayList<String> getImageListNameByBoardId(Integer boardId){
        return boardMapper.getImageListNameByBoardId(boardId);
    }
    public ArrayList<ProductQnA> getProductQnAByProduct(Integer productId){
        ArrayList<Board> boardList = boardMapper.getBoardByProductId(productId);
        ArrayList<ProductQnA> productQnAList = new ArrayList<>();
        for (Board board: boardList) {
            productQnAList.add(board.toProductQnA());
        }
        return productQnAList;
    }
    public ArrayList<Review> getReviewByProduct(Integer productId){
        ArrayList<Board> boardList = boardMapper.getBoardByProductId(productId);
        ArrayList<Review> reviewList = new ArrayList<>();
        for (Board board: boardList) {
            reviewList.add(board.toReview());
        }
        return reviewList;
    }
    public void deleteReview(Integer boardId){
        boardMapper.deleteReview(boardId);
    }
}
