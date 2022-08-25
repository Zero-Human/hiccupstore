package hiccup.hiccupstore.user.dto.board;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class Board1vs1Form {

    private String boardtitle;
    private String category;
    private String boardcontent;
    private List<MultipartFile> imageFiles;

}
