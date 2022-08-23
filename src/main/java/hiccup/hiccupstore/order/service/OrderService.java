package hiccup.hiccupstore.order.service;

import hiccup.hiccupstore.cart.dto.Cart;
import hiccup.hiccupstore.order.dao.OrderMapper;
import hiccup.hiccupstore.order.dto.Order;
import hiccup.hiccupstore.order.dto.OrderProduct;
import hiccup.hiccupstore.product.dto.Product;
import hiccup.hiccupstore.product.dto.ProductImage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderMapper orderMapper;
    public void insertOrder(Order order){
        orderMapper.insertOrder(order);
    }
    public void deleteOrder(int orderId){
        orderMapper.deleteOrder(orderId);
    }
    public Order getOrder(int orderId){
        return orderMapper.getOrder(orderId);
    }

    public void insertOrderProduct(OrderProduct orderProduct){
        orderMapper.insertOrderProduct(orderProduct);
    }
    public void deleteOrderProduct(int orderProductId){
        orderMapper.deleteOrderProduct(orderProductId);
    }
    public ArrayList<OrderProduct> getOrderProduct(int orderProductId){
        ArrayList<OrderProduct> orderProduct = orderMapper.getOrderProduct(orderProductId);
        return orderProduct;
    }

    public int getQuantity(int productId){ return orderMapper.getQuantity(productId); }

    public ArrayList<Cart> getCarts(int userId){
        return orderMapper.getCarts(userId);
    }

    public Cart getCart(int userId, int productId){
        return orderMapper.getCart(userId,productId);
    }


    public Product getProduct(int productId){
        return orderMapper.getProduct(productId);
    }

    public ProductImage getProductImage(int prodcutId) {
        return orderMapper.getProductImage(prodcutId);
    }

    public int getInsertOrderId() {return orderMapper.getInsertOrderId();}
}
