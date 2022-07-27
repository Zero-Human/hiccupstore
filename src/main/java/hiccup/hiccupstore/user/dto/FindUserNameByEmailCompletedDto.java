package hiccup.hiccupstore.user.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
public class FindUserNameByEmailCompletedDto {

    public FindUserNameByEmailCompletedDto(String nickname, String username) {
        this.nickname = nickname;
        this.username = username;
    }

    private String nickname;
    private String username;

}
