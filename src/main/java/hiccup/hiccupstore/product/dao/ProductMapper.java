package hiccup.hiccupstore.product.dao;

import hiccup.hiccupstore.product.dto.Product;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Mapper
@Repository
public interface ProductMapper {

    void insert(Product product) ;


    // 상세 상품 조회
    Product selectById(int productId) ;


    // 상품 조회
    ArrayList<Product> selectByCategory(int categoryId) ;

    /* Parameter : int 타입 _ x : x만원대
     * Return : ArrayList<Product>
     */
    ArrayList<Product> selectByPriceRange(int priceRange) ;

    /* Parameter : int 타입 _ 카테고리 ID
     * Return : ArrayList<Product>
     * 설명
     * 상단 간편검색창으로 검색했을 경우
     * 세트 상품/유사이름 상품 (ex."안동소주" 검색 -> "명인 안동소주" , "안동소주 일품" )
     * -> Product 반환이 아닌 ArrayList로 반환
     */
    ArrayList<Product> selectByName(String productName) ;



}
