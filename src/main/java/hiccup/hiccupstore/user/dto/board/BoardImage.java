package hiccup.hiccupstore.user.dto.board;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class BoardImage {
    private int imageId;
    private int boardId;
    private String imageName;
    private String imagePath;
}
