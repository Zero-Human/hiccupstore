package hiccup.hiccupstore.order.controller;

import hiccup.hiccupstore.cart.dto.Cart;
import hiccup.hiccupstore.order.dto.Order;
import hiccup.hiccupstore.order.dto.OrderInfo;
import hiccup.hiccupstore.order.dto.OrderProduct;
import hiccup.hiccupstore.order.dto.OrderProductInfo;
import hiccup.hiccupstore.order.service.OrderService;
import hiccup.hiccupstore.product.dto.Product;
import hiccup.hiccupstore.product.dto.ProductImage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/test")
    public String Test1(){
       return "index";
    }

    /*
        method : orderList
        param : HttpServletRequest : 로그인 확인
        Return : String
        [설명]
        주문 페이지로 들어왔을 때 필요한 db와 함께 페이지 넘겨주는 기능
    */
    @GetMapping(value = "/list") //, method = RequestMethod.Post
    public String orderList(HttpServletRequest request) {
        //임시 로그인 정보
        HttpSession session = request.getSession();
        session.setAttribute("userId", 1);
        session.setAttribute("userName","hong");
        session.setAttribute("address","seoul");
        session.setAttribute("phone",101234);

        //로그인 확인
        //HttpSession session = request.getSession(false);
        int userId = (int)session.getAttribute("userId");

        if (session != null) {
            //주문목록 정보
            ArrayList<Cart> carts = orderService.getCarts(userId);
            OrderInfo orderInfo = new OrderInfo();
            int total = 0;
            for(int i = 0;i < carts.size();i++){
                OrderProductInfo orderProductInfo = new OrderProductInfo();
                int productId = carts.get(i).getProductId();

                Product product = orderService.getProduct(productId);
                ProductImage productImage = orderService.getProductImage(productId);

                orderProductInfo.setProductId(productId);
                orderProductInfo.setPrice(product.getPrice());
                orderProductInfo.setQuantity(carts.get(i).getQuantity());
                orderProductInfo.setProductName(product.getProductName());
                orderProductInfo.setImagePath(productImage.getImagePath());

                total = total + carts.get(i).getQuantity() * product.getPrice();

                orderInfo.getOrderProductInfo().add(orderProductInfo);
            }

            orderInfo.setTotal(total);

            request.setAttribute("orderInfo",orderInfo);
            
            return "/order/Order";
        }
        return "/order/Order"; //로그인 안되어있을 때 로그인 페이지로 이동
    }

    /*
        method : orderProduct
        param : HttpServletRequest : 로그인 정보,  order, orderProducts : db에 자료 저장하고 받아오기
        Return : 결제페이지로 이동
        [설명]
        결제 버튼을 눌렀을 때 db에 데이터를 저장해 주고 결제 완료 페이지로 이동시키는 메소드
    */
    @ResponseBody
    @PostMapping("/orderProduct")
    public int orderProduct(HttpServletRequest request, @RequestParam(value = "address" ) String address, @RequestBody List<OrderProduct> orderProducts) {
        HttpSession session = request.getSession(false);
        int userId = (int)session.getAttribute("userId");

        //orderProduct,order 에 데이터 저장
        Order order = new Order();
        order.setStatus("주문완료");
        order.setAddress(address);
        order.setOrderId(userId);

        orderService.insertOrder(order);

        int orderId = orderService.getInsertOrderId();//or

        for(int i =0;i<orderProducts.size();i++){
            OrderProduct orderProduct = new OrderProduct();

            orderProduct.setProductId(orderProducts.get(i).getProductId());
            orderProduct.setOrderId(orderId);
            orderProduct.setOrderPrice(orderProducts.get(i).getOrderPrice());
            orderProduct.setQuantity(orderProducts.get(i).getQuantity());

            orderService.insertOrderProduct(orderProduct);
        }

        return orderId;
    }

    /*
        method : orderResult
        param : HttpServletRequest : 로그인 정보,  orderId: 주문한 번호
        Return : 주문결과페이지로 이동
        [설명]
        db에 주문 결과를 무사히 넣은 후 주문 결과 화면으로 이동해주는 메서드
    */
    @GetMapping("/orderResult")
    public String orderResult(HttpServletRequest request,@RequestParam(value="orderId") int orderId){
        //결제된 상품 정보
        ArrayList<OrderProduct> returnOrderProduct = orderService.getOrderProduct(orderId);
        int count = returnOrderProduct.size();

        Integer total = 0;
        //user_order db에 total이 있었으면 좋겠지만 없으니 임시방편으로 사용
        for(int i=0;i<count;i++){
            total += returnOrderProduct.get(i).getOrderPrice();
        }
        
        Order returnOrder = orderService.getOrder(orderId);
        Product returnProduct = orderService.getProduct(returnOrderProduct.get(0).getProductId());

        String orderMessage = returnProduct.getProductName()+" 외 "+count+"개 상품의 주문이 완료되었습니다.";

        System.out.println("check data : "+orderId+" : "+orderMessage+" : "+total);

        request.setAttribute("orderId",orderId);
        request.setAttribute("orderMessage",orderMessage); //주문완료 메시지
        request.setAttribute("total",total); // 총합계금액
        //결제 페이지로 넘어가기
        return "/order/OrderResult";
    }

    /*
        method : checkOrder
        param : HttpServletRequest : 로그인 정보, orderId : 주문 번호 
        Return : 주문 확인 페이지로 이동
        [설명]
        선택한 주문목록의 상세페이지로 이동한다
    */
    @GetMapping("/check")
    public String checkOrder(HttpServletRequest request, @RequestParam(value="orderId")int orderId){
        //임시 로그인 정보
        HttpSession session = request.getSession();
        session.setAttribute("userId", 1);
        session.setAttribute("userName","honggildong");
        session.setAttribute("address","seoul");
        session.setAttribute("phone",010);
        //로그인 확인
        //HttpSession session = request.getSession(false);
        int userId = (int)session.getAttribute("userId");

        if (session != null) {
            System.out.println("test session success 2 : "+ session.getAttribute("phone"));
            //주문목록 정보
            List<OrderProduct> orderProducts = orderService.getOrderProduct(orderId);
            Order order = orderService.getOrder(orderId); //상태
            request.setAttribute("status",order.getStatus()); //상태 보내주기

            OrderInfo orderInfo = new OrderInfo();
            int total = 0;
            //주문정보 보내줄 데이터에 넣기
            for(int i = 0;i < orderProducts.size();i++){
                OrderProductInfo orderProductInfo = new OrderProductInfo();
                int productId = orderProducts.get(i).getProductId();

                Product product = orderService.getProduct(productId);
                ProductImage productImage = orderService.getProductImage(productId);

                orderProductInfo.setProductId(productId);
                orderProductInfo.setPrice(product.getPrice());
                orderProductInfo.setQuantity(orderProducts.get(i).getQuantity());
                orderProductInfo.setProductName(product.getProductName());
                orderProductInfo.setImagePath(productImage.getImagePath());

                total = total + orderProducts.get(i).getQuantity() * product.getPrice();

                orderInfo.getOrderProductInfo().add(orderProductInfo);
            }

            orderInfo.setTotal(total);

            request.setAttribute("orderInfo",orderInfo);

            return "/order/CheckOrder";
        }
        return "/order/Order"; //로그인 안되어있을 때 로그인 페이지로 이동


    }


}
