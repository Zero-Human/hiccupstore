package hiccup.hiccupstore.user.dto;

import lombok.Data;

@Data
public class BoardDto {

    private Integer boardid;
    private Integer userid;
    private Integer boardcategoryid;
    private String boardtitle;
    private String boardcontent;
    private String createdate;

}
