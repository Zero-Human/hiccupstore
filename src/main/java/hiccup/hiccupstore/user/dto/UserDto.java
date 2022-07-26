package hiccup.hiccupstore.user.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDto {

    private Integer userId;
    private String userName;
    private String nickName;
    private String address;
    private String email;
    private String birth;
    private String phone;
    private String password;

}