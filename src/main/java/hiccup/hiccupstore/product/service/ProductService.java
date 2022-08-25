package hiccup.hiccupstore.product.service;

import hiccup.hiccupstore.product.dao.ProductMapper;
import hiccup.hiccupstore.product.dto.Product;
import hiccup.hiccupstore.product.dto.ProductAddForm;
import hiccup.hiccupstore.product.dto.ProductForView;
import hiccup.hiccupstore.product.dto.ProductImage;
import hiccup.hiccupstore.product.dto.page.PageCriteria;
import hiccup.hiccupstore.product.dto.page.ViewCriteria;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductMapper productMapper;


    @Transactional
    public void addProduct(ProductAddForm productAddForm, ProductImage productImage, ProductImage detailImage) {

        Product product = productAddForm.toProduct();
        productMapper.insertProduct(product);

        productImage.setProductId(product.getProductId());
        productMapper.insertProductImage(productImage);
        detailImage.setProductId(product.getProductId());
        productMapper.insertProductImage(detailImage);
    }

    public void addProductImage(ProductImage productImage) {

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

    public void editProductImage(ProductImage productImage) {
        productMapper.updateProductImage(productImage);
    }

    public ArrayList<ProductImage> getProductImageListById(int productId){
        return productMapper.getProductImageListById(productId);
    }

    public Product getProductById(int productId) {
        return productMapper.selectById(productId);
    }

    public ArrayList<ProductForView> getProductListByCategory(ViewCriteria criteria) {
        return productMapper.selectByCategory(criteria);
    }
//    public ArrayList<ProductForView> getProductListByCategory(ViewCriteria criteria) {
//        return productMapper.selectListInPageByCategory(criteria);
//    }
//    public ArrayList<ProductForView> getProductListByPriceRange(HashMap<String, Object> map) {
//        return productMapper.selectByPriceRange(map);
//    }
//    public ArrayList<ProductForView> getProductListByPriceRange(PageCriteria criteria) {
//        return productMapper.selectListInPageByPriceRange(criteria);
//    }

//    public ArrayList<ProductForView> getProductListBySearch(HashMap<String, Object> map) {
//        return productMapper.selectBySearch(map);
//    }
//    public ArrayList<ProductForView> getProductListBySearch(PageCriteria criteria) {
//        return productMapper.selectListInPageByCategory(criteria);
//    }

        // order 기능 -> Product sellCount 늘려주는 메서드 & quantity 빼주는 메서드
        // quantity 확인 먼저( : isExist 메서드 -> 재고 있으면 hasBeenOrdered 실행 가능
        public void hasBeenOrdered ( int productId){
            Product p = productMapper.selectById(productId);
            p.setSellCount(p.getSellCount() + 1);
            p.setQuantity(p.getQuantity() - 1);
            productMapper.updateProduct(p);
        }
        public void hasBeenCanceled ( int productId){
            Product p = productMapper.selectById(productId);
            p.setSellCount(p.getSellCount() - 1);
            p.setQuantity(p.getQuantity() + 1);
            productMapper.updateProduct(p);
        }

        // user 기능 -> 찜 증감 메서드 필요

        /* 부가기능 메서드 */
        public int getListSize (ViewCriteria criteria) {
            return productMapper.getListSize(criteria);
        }
        public boolean isExist ( int productId){
            int stock = productMapper.selectById(productId).getQuantity();
            if (stock == 0) {
                return false;
            }
            return true;
        }
    }
