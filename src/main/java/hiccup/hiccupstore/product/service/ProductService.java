package hiccup.hiccupstore.product.service;

import hiccup.hiccupstore.product.dao.ProductMapper;
import hiccup.hiccupstore.product.dto.Product;
import hiccup.hiccupstore.product.dto.ProductForView;
import hiccup.hiccupstore.product.dto.ProductImage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductMapper productMapper ;


    public void addProduct(Product product) {
        productMapper.insertProduct(product);
    }
    public void addProductImage(ProductImage productImage){
        productMapper.insertProductImage(productImage);
    }


    public void delProduct(int productId) {
        productMapper.deleteProduct(productId);
    }
    public void delProductImage(int productImageId) {
        productMapper.deleteProductImage(productImageId);
    }


    public void editProduct(Product product) {
        productMapper.updateProduct(product);
    }
    public void editProductImage(ProductImage productImage){
        productMapper.updateProductImage(productImage);
    }


    public Product getProductById(int productId) {
        return productMapper.selectById(productId);
    }

    public List<ProductForView> getProductListByCategory(HashMap<String, Object> map) {
        return productMapper.selectByCategory(map) ;
    }
    public ArrayList<ProductForView> getProductListByPriceRange(HashMap<String, Object> map) {
        return productMapper.selectByPriceRange(map);
    }
    public ArrayList<ProductForView> getProductListBySearch(HashMap<String, Object> map) {
        return productMapper.selectBySearch(map);
    }
    // order 기능 -> Product sellCount 늘려주는 메서드 & quantity 빼주는 메서드
    // quantity 확인 먼저( : isExist 메서드 -> 재고 있으면 hasBeenOrdered 실행 가능
    public void hasBeenOrdered(int productId) {
        Product p = productMapper.selectById(productId);
        p.setSellCount( p.getSellCount()+1 );
        p.setQuantity( p.getQuantity()-1 );
        productMapper.updateProduct(p);
    }
    public void hasBeenCanceled(int productId) {
        Product p = productMapper.selectById(productId);
        p.setSellCount( p.getSellCount()-1 );
        p.setQuantity( p.getQuantity()+1 );
        productMapper.updateProduct(p);
    }

    // user 기능 -> 찜 증감 메서드 필요

    /* 부가기능 메서드 */
    public int getSizeOfProductList(ArrayList<ProductForView> productList) {
        return productList.size() ;
    }
    public boolean isExist(int productId) {
        int stock = productMapper.selectById(productId).getQuantity();
        if(stock == 0) {
            return false ;
        }
        return true ;
    }
}
