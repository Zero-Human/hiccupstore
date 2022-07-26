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

}
