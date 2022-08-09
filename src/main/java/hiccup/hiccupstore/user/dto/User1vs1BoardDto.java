package hiccup.hiccupstore.user.dto;

import lombok.Data;

@Data
public class User1vs1BoardDto {

    private Integer userid;
    private String boardtitle;
    private String boardcontent;
    private String createdate;
    private Integer commentid;
    private String commentcontent;
    private String commtentcreatedate;
    private Integer imageid;
    private String imagename;

}
