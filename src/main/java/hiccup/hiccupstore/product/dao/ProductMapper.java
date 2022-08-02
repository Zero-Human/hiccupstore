package hiccup.hiccupstore.product.dao;

import hiccup.hiccupstore.product.dto.Product;
import hiccup.hiccupstore.product.dto.ProductForView;
import hiccup.hiccupstore.product.dto.ProductImage;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Mapper
@Repository
public interface ProductMapper {

    // INSERT
    void insertProduct(Product product) ;
    void insertProductImage(ProductImage productImage) ;

    //DELETE
    void deleteProduct(int productId) ;
    void deleteProductImage(int productImageId) ;

    //UPDATE
    void updateProduct(Product product);
    void updateProductImage(ProductImage productImage);


    /* SELECT */
    // 상세 상품 조회
    Product selectById(int productId) ;


    // 상품 조회 (카테고리)
    ArrayList<ProductForView> selectByCategory(HashMap<String, Object> map) ;

    // 상품 조회 (가격 범위)
    ArrayList<ProductForView> selectByPriceRange(HashMap<String, Object> map) ;

    ArrayList<ProductForView> selectBySearch(HashMap<String, Object> map) ;
}