package hiccup.hiccupstore.user.dao;

import hiccup.hiccupstore.user.dto.UserDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserMapper {

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

}
