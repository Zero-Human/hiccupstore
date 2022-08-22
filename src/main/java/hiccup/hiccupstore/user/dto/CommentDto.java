package hiccup.hiccupstore.user.dto;

import lombok.Data;

@Data
public class CommentDto {

    private Integer commentid;
    private Integer userid;
    private Integer boardid;
    private String commentcontent;
    private String commentcreatedate;
    private String username;
    private String nickname;

}
