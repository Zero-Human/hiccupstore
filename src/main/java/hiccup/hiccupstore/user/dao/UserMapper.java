package hiccup.hiccupstore.user.dao;

import hiccup.hiccupstore.user.dto.*;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserMapper {

    /** 회원 관련 SQL문 */

    public Integer save(UserDto userDto);

    @Select("select * from user where userName = #{userName}")
    public UserDto getUser(@Param("userName") String userName);

    @Select("select userName from user where userName = #{userName}")
    public String searchUserName(@Param("userName") String userName);

    @Select("select userName from user where nickname = #{nickname} and email = #{email}")
    public String searchUserNameByEmail(@Param("nickname") String nickname,@Param("email") String email);

    @Select("select userName from user where nickname = #{nickname} and phone = #{phone}")
    public String searchUserNameByPhone(@Param("nickname") String nickname,@Param("phone") String phone);

    @Select("select password from user where username = #{username} and email = #{email}")
    public String searchPasswordByEmail(@Param("username") String username,@Param("email") String email);

    @Select("select password from user where username = #{username} and phone = #{phone}")
    public String searchPasswordByPhone(@Param("username") String username,@Param("phone") String phone);

    /** 주문목록 관련 SQL문 */

    public Integer saveOrder(OrderDto order);

    @Select("select * from user_order where userId = #{userId}")
    public OrderDto getOrder(Integer userid);

    @Select("select * from user_order where userId = #{userId} order by orderid desc limit 0,5")
    public List<OrderDto> getOrderList(Integer userid);

    @Select("select * from user_order where userId = #{userId} order by orderid desc limit #{page},#{pagesize}")
    public List<OrderDto> getOrderListPage(Integer userId,Integer page,Integer pagesize);

    @Select("select count(*) from user_order where userid = #{userid}")
    public Integer getOrderListCount(Integer userId);

    public List<OrderLatelyProductDto> getOrderLatelyProductList(Integer userid);


    /** 최근본상품 관련 SQL문 */

    public List<ProductDto> getProductList(String[] LatelyProductSee);


    /** mypageorderlist */

    @Select("select * from test where testdate between \"2022-07-20\" and \"2022-07-31\"")
    public List<ProductDto> getproductlist();

    public List<OrderLatelyProductDto> getOrderLatelyProductListt(String startdate,String lastdate,Integer userid);

    public List<OrderLatelyProductDto> getOrderLatelyProductListtPage(String startdate,String lastdate,Integer userid,
                                                                      Integer page,Integer pagesize);










    public TestDto getTest(Integer userid);


}
