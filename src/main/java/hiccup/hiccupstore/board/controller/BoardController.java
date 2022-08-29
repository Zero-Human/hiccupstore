package hiccup.hiccupstore.board.controller;

import hiccup.hiccupstore.board.dto.BoardWriteForm;
import hiccup.hiccupstore.board.dto.Image;
import hiccup.hiccupstore.board.service.BoardService;
import hiccup.hiccupstore.commonutil.file.FileStore;
import hiccup.hiccupstore.commonutil.file.UploadFile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class BoardController {
    private final FileStore fileStore;
    private final BoardService boardService;
    // 상품, 리뷰 작성
    @GetMapping("test")
    public String index(){
        return "product/QnA";
    }
    @PostMapping("/board/ProductQnA/add")
    public String addProductQnA(@ModelAttribute BoardWriteForm boardWriteForm,
                                @RequestParam( value = "images",required = false) ArrayList<MultipartFile>  images) throws IOException {
        //FIXME 유저 ID를 추가해야한다.
        List<UploadFile> uploadImages = fileStore.storeFiles(images);
        ArrayList<Image> imageList = new ArrayList<>();
        for (UploadFile item : uploadImages) {
            imageList.add(Image.builder().productId(boardWriteForm.getProductId()).
                    imageName(item.getStoreFileName()).
                    imagePath(fileStore.getFullPath(item.getStoreFileName())).build());

        }

        boardService.insertProductQnA(boardWriteForm.toProductQnA(1), imageList);

        return "redirect:/product/"+boardWriteForm.getProductId()+"/detail";
    }



}
