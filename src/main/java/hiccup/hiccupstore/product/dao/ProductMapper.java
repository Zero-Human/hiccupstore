package hiccup.hiccupstore.product.dao;

import hiccup.hiccupstore.product.dto.Product;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Mapper
@Repository
public interface ProductMapper {
    void insert(Product p) ;
    Product selectById(int p_id) ;
    ArrayList<Product> selectByCategory(int c_id) ;
    ArrayList<Product> selectByName(String p_name) ;
    ArrayList<Product> selectByName(String p_name) ;
    ArrayList<Product> selectByName(String p_name) ;
    ArrayList<Product> selectByName(String p_name) ;
    ArrayList<Product> selectByName(String p_name) ;

}
