package hiccup.hiccupstore.board.controller;

import hiccup.hiccupstore.board.dto.BoardWriteForm;
import hiccup.hiccupstore.board.dto.Image;
import hiccup.hiccupstore.board.service.BoardService;
import hiccup.hiccupstore.commonutil.file.FileStore;
import hiccup.hiccupstore.commonutil.file.UploadFile;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class ApiBoardController {
    private final FileStore fileStore;
    private final BoardService boardService;

    @PostMapping("api/review/add")
    public void addReview(@ModelAttribute BoardWriteForm boardWriteForm) throws IOException {
        //FIXME userId
        boardService.insertReview(boardWriteForm.toReview(1));
    }
    @PostMapping("api/review/edit")
    public void editReview(Model model, @RequestBody BoardWriteForm boardWriteForm) {
        // FIXME userId
        // FIXME 수정해야한다.
        boardService.editReview(boardWriteForm.toReview(1));
    }
    @PostMapping("api/review/delete")
    public void deleteReview(@RequestParam("boardId") Integer boardId){
        ArrayList<String> imageListName = boardService.getImageListNameByBoardId(boardId);
        for (String imageName: imageListName) {
            fileStore.deleteFile(fileStore.getFullPath(imageName));
        }
        boardService.deleteImageByBoardId(boardId);
        boardService.deleteReview(boardId);
    }

    @GetMapping("/board/{filename}")
    public Resource viewImage(@PathVariable String filename) throws MalformedURLException {
        return new UrlResource("file:"+fileStore.getFullPath(filename));
    }
}
