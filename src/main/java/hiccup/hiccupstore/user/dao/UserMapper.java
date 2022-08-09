package hiccup.hiccupstore.user.dao;

import hiccup.hiccupstore.user.dto.*;
import hiccup.hiccupstore.user.util.UploadFile;
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

    @Select("select username from user where username = #{username}")
    public String searchUserName(@Param("username") String username);

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


    /** 1:1문의 SQL문 */

    @Insert("insert into board (userid,boardcategoryid,boardtitle,boardcontent,createdate)" +
            "values(#{userid},1,#{boardtitle},#{boardcontent},#{createdate})")
    public Integer saveBoard(Integer userid,String boardtitle,String boardcontent,String createdate);

    public Integer saveBoardImage(List<UploadFile> item);

    @Select("select count(*) from board where userid = #{userid}")
    public Integer FindBoardCountByUserId(Integer userid);

    @Select("select * from (select * from board where userid = #{userid} and boardcategoryid = 1) as b left join comment c on b.boardid = c.boardid order by b.boardid desc limit #{page},#{pagesize}")
    public List<BoardDto> FindBoardByUserId(Integer userid,Integer page,Integer pagesize);

    @Select("select boardid from board where userid = #{userid} order by boardid desc limit 0,1")
    public Integer FindOneBoardByUserId(Integer userid);

    @Select("select * from board inner join image where boardid = #{boardid} and userid = #{userid}")
    public BoardImageDto FindBoardByUserIdAndBoardId(Integer boardid,Integer userid);

    @Select("select board.boardid,userid,boardtitle,boardcontent,createdate,imagename from board inner join image on board.boardid = image.boardid where board.boardid = #{boardid}")
    public List<BoardDto2> FindOneBoardByBoardid(Integer boardid);

    @Select("select boardid,userid,boardtitle,boardcontent,createdate from board where boardid = #{boardid}")
    public List<BoardDto> FindOneBoardByBoardidNotimage(Integer boardid);



    /** 관리자페이지 OrderList쪽 SQL문 */
    public List<OrderLatelyProductDto> getOrderLatelyProductListtManagerPage(Integer page,Integer pagesize);

    @Select("select count(*) from user_order")
    public Integer getOrderListAllCount();

    @Select("select * from user_order order by orderid desc limit #{page},#{pagesize}")
    public List<OrderDto> getOrderListAllPage(Integer page,Integer pagesize);

    @Update("update user_order set status = #{status} where orderid = #{orderid}")
    public Integer updateOrderStatus(Integer orderid,String status);


    /** 관리자페이지 1vs1쪽 SQL문 */

    @Select("select count(*) from board where boardcategoryid = 1")
    public Integer getUser1vs1AllCount();

    @Select("select * from (select * from board where boardcategoryid = 1 ) as b left join comment c on b.boardid = c.boardid order by b.boardid desc limit #{page},#{pagesize}")
    public List<BoardDto> getUser1vs1boardall(Integer page,Integer pagesize);

    public List<User1vs1BoardDto> getUser1vs1BoardOne(Integer boardid);

    @Insert("insert into comment (userid,boardid,commentcontent,commentcreatedate)" +
            "values(0,#{boardid},#{BoardContent},'2022-08-09')")
    public Integer Save1vs1UserAnswer(String BoardContent,Integer boardid);

    public TestDto getTest(Integer userid);


}
