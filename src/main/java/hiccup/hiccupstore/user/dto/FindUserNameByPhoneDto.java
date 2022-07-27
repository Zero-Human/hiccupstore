package hiccup.hiccupstore.user.dto;

import lombok.Data;

@Data
public class FindUserNameByPhoneDto {

    private String nickname;
    private String phone;

}
