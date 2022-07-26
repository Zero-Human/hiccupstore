package hiccup.hiccupstore.order.controller;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderController {

    /*
        method : orderList(임시)
        param : HttpServletRequest : 로그인 확인, @ModelMap : 장바구니 정보 보내주기
        Return : String
        [설명]
        주문 페이지로 들어왔을 때 필요한 db와 함께 페이지 넘겨주는 기능
    */
    @RequestMapping("order/list")
    @ResponseBody
    public String orderList(HttpServletRequest request, @RequestBody HashMap map, ModelMap modelMap) {
        System.out.println("임시이름");

        //로그인 확인
        HttpSession session = request.getSession(false);
        if(session != null){
            if(session.getAttribute("userId") != null) {

                // 장바구니 목록 불러오기 (json 형식으로 보내주기로 함)
                //List<Cart> cartList = (List<Cart>)
                // 주문자 정보 불러오기 (UserTable) 에서 검색해야함

                //주문자, 장바구니 정보 보내주기
                //modelMap.addAttribute("user",);
                //modelMap.addAttribute("cartList",);
                // cartList 는 Cart 와 Product 로 이루어져있는 List

                //Order.html 로 보내주기

                return "";
            }
        }
        return ""; //로그인 안되어있을 때
    }
    //결제 버튼을 눌렀을 때 데이터 저장



}
