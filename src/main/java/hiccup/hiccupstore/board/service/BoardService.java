package hiccup.hiccupstore.board.service;

import hiccup.hiccupstore.board.dao.BoardMapper;
import hiccup.hiccupstore.board.dto.Image;
import hiccup.hiccupstore.board.dto.ProductQnA;
import hiccup.hiccupstore.board.dto.Review;
import hiccup.hiccupstore.product.dto.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardMapper boardMapper;

    public void insertReview(Review review){


    }
    @Transactional
    public void insertProductQnA(ProductQnA productQnA, ArrayList<Image> imageList){
        boardMapper.insertProductQnA(productQnA);
        for (Image image:imageList) {
            image.setBoardId(productQnA.getBoardId());
        }
        boardMapper.insertImage(imageList);
    }

//    public ArrayList<HashMap<>>
}
