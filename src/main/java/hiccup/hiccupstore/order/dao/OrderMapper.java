package hiccup.hiccupstore.order.dao;

import hiccup.hiccupstore.cart.dto.Cart;
import hiccup.hiccupstore.order.dto.Order;
import hiccup.hiccupstore.order.dto.OrderProduct;
import hiccup.hiccupstore.product.dto.Product;
import hiccup.hiccupstore.product.dto.ProductImage;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface OrderMapper {

    void insertOrder(Order order);
    void deleteOrder(int orderId);
    Order getOrder(int orderId);
    void insertOrderProduct(OrderProduct orderProduct);
    void deleteOrderProduct(int orderProductId);
    ArrayList<OrderProduct> getOrderProduct(int orderProductId);

    int getQuantity(int productId); //상품 갯수 확인하기
    //User getUser(int userId);//사용자 정보 불러오기

    ArrayList<Cart> getCarts(int userId);

    Cart getCart(int userId, int productId);

    Product getProduct(int productId);

    ProductImage getProductImage(int productId);

    int getInsertOrderId();
}
